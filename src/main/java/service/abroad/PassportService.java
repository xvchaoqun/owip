package service.abroad;

import bean.XlsPassport;
import controller.global.OpException;
import domain.abroad.Passport;
import domain.abroad.PassportApply;
import domain.abroad.PassportDraw;
import domain.abroad.PassportDrawExample;
import domain.abroad.PassportExample;
import domain.abroad.SafeBox;
import domain.abroad.TaiwanRecord;
import domain.base.MetaType;
import domain.cadre.CadreView;
import domain.sys.SysUserView;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import persistence.common.PassportSearchBean;
import service.BaseMapper;
import service.cadre.CadreService;
import service.sys.SysUserService;
import shiro.ShiroHelper;
import sys.constants.AbroadConstants;
import sys.tags.CmTag;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PassportService extends BaseMapper {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private CadreService cadreService;
    @Autowired
    private SafeBoxService safeBoxService;
    @Autowired
    private PassportApplyService passportApplyService;

    @Transactional
    public int importPassports(final List<XlsPassport> passports, byte type) {

        //int duplicate = 0;
        int success = 0;
        for (XlsPassport uRow : passports) {

            Passport record = new Passport();

            String userCode = uRow.getUserCode();
            SysUserView uv = sysUserService.findByCode(userCode);
            if (uv == null) throw new OpException("工作证号{0}不存在", userCode);
            CadreView cadre = cadreService.dbFindByUserId(uv.getId());
            if (cadre == null) throw new OpException("工作证号：{0} 姓名：{1} 不是干部", userCode, uv.getRealname());
            record.setCadreId(cadre.getId());
            record.setType(type);

            int passportType = uRow.getPassportType();
            record.setClassId(passportType);

            record.setCode(uRow.getCode());

            record.setAuthority(uRow.getAuthority());

            record.setIssueDate(uRow.getIssueDate());
            record.setExpiryDate(uRow.getExpiryDate());
            record.setKeepDate(uRow.getKeepDate());

            //
            if(StringUtils.isBlank(uRow.getSafeCode())){
                throw new OpException("工作证号：{0} 姓名：{1} 保险柜编号为空", userCode, uv.getRealname());
            }
            SafeBox safeBox = safeBoxService.getByCode(uRow.getSafeCode());
            if (safeBox == null)
                if (uv == null) throw new OpException("工作证号：{0} 姓名：{1} 保险柜编号{2}不存在", userCode, uv.getRealname(), safeBox.getCode());
            record.setSafeBoxId(safeBox.getId());
            record.setCreateTime(new Date());
            record.setIsLent(false);
            record.setCancelConfirm(false);

            if (idDuplicate(null, record.getType(), record.getCadreId(), record.getClassId(), record.getCode()) > 0) {
                MetaType mcPassportType = CmTag.getMetaType(passportType);
                throw new OpException("导入失败，工作证号：" + uRow.getUserCode() + "[" + mcPassportType.getName() + "]重复");
            }

            add(record, null, null);

            success++;
        }

        return success;
    }

    // <passportClassId, Passport>
    public Map<Integer, Passport> findByCadreId(int cadreId) {

        PassportSearchBean bean = new PassportSearchBean(null, cadreId, null, null,
                AbroadConstants.ABROAD_PASSPORT_TYPE_KEEP, null, null, null);
        Map<Integer, Passport> passportMap = new HashMap<>();
        List<Passport> passports = iAbroadMapper.selectPassportList(bean, new RowBounds());
        for (Passport passport : passports) {
            passportMap.put(passport.getClassId(), passport);
        }

        return passportMap;
    }

    // 查找干部的台湾通行证
    public Passport findTwPassport(int cadreId){

        MetaType passportTwType = CmTag.getMetaTypeByCode("mt_passport_tw");
        Map<Integer, Passport> passportMap = findByCadreId(cadreId);

        return passportMap.get(passportTwType.getId());
    }

    // 判断是否重复（更新时，不修改类别） 0 不重复  1证件号码重复 2证件类别重复
    public int idDuplicate(Integer id, Byte type, int cadreId, int classId, String code) {
        //Assert.isTrue(StringUtils.isNotBlank(code));

        if (id != null) {
            Passport passport = passportMapper.selectByPrimaryKey(id);
            type = passport.getType();
        } else {
            if (type == null)
                type = AbroadConstants.ABROAD_PASSPORT_TYPE_KEEP; // 默认存入集中保管
        }

        return updateIdDuplicate(id, type, cadreId, classId, code);
    }

    // 判断是否重复（修改类别） 0 不重复  1证件号码重复 2证件类别重复
    public int updateIdDuplicate(Integer id, Byte type, int cadreId, int classId, String code) {

        if (StringUtils.isNotBlank(code)) {
            // 证件号码不允许重复
            PassportExample example = new PassportExample();
            PassportExample.Criteria criteria = example.createCriteria().andCodeEqualTo(code);
            if (id != null) criteria.andIdNotEqualTo(id);
            if (passportMapper.countByExample(example) > 0) return 1;
        }

        if (type == AbroadConstants.ABROAD_PASSPORT_TYPE_KEEP) {
            //“集中管理证件”中不存在同一个人有两本护照（或者港澳通行证、台湾通行证）就可以。
            // 其他三个“取消集中管理、丢失证件、作废证件”中，一个人可以有两本护照。
            PassportExample example2 = new PassportExample();
            PassportExample.Criteria criteria2 =
                    example2.createCriteria().andCadreIdEqualTo(cadreId).andTypeEqualTo(type)
                            .andClassIdEqualTo(classId);
            if (id != null) criteria2.andIdNotEqualTo(id);

            if (passportMapper.countByExample(example2) > 0) return 2;
        }

        return 0;
    }

    @Transactional
    public int add(Passport record, Integer applyId, Integer taiwanRecordId) {

        Assert.isTrue(0 == idDuplicate(null, record.getType(), record.getCadreId(), record.getClassId(), record.getCode()), "duplicate");

        int cadreId = record.getCadreId();
        int classId = record.getClassId();

        if (applyId != null) { // 交证件
            PassportApply _passportApply = passportApplyMapper.selectByPrimaryKey(applyId);
            Assert.isTrue(_passportApply.getCadreId().intValue() == cadreId, "wrong cadreId");
            Assert.isTrue(_passportApply.getClassId().intValue() == classId, "wrong classId");

            PassportApply passportApply = new PassportApply();
            passportApply.setId(applyId);
            passportApply.setHandleDate(new Date());
            passportApply.setHandleUserId(ShiroHelper.getCurrentUserId());
            passportApplyMapper.updateByPrimaryKeySelective(passportApply);

            record.setKeepDate(new Date()); // 集中保管日期为交证件日期
            record.setCadreId(_passportApply.getCadreId()); // 确认
            record.setApplyId(applyId);
        }else if (taiwanRecordId != null) { // 因公赴台备案-交证件
            TaiwanRecord _taiwanRecord = taiwanRecordMapper.selectByPrimaryKey(taiwanRecordId);

            if(_taiwanRecord==null || _taiwanRecord.getIsDeleted()
                    || _taiwanRecord.getHandleDate() != null){
                // 上交后不允许修改
                throw new OpException("因公赴台备案状态异常");
            }

            MetaType passportTwType = CmTag.getMetaTypeByCode("mt_passport_tw");
            Assert.isTrue(_taiwanRecord.getCadreId().intValue() == cadreId, "wrong cadreId");
            Assert.isTrue(passportTwType.getId().intValue() == classId, "wrong classId");

            TaiwanRecord taiwanRecord = new TaiwanRecord();
            taiwanRecord.setId(taiwanRecordId);
            taiwanRecord.setHandleDate(new Date());
            taiwanRecord.setHandleUserId(ShiroHelper.getCurrentUserId());
            taiwanRecordMapper.updateByPrimaryKeySelective(taiwanRecord);

            record.setKeepDate(new Date()); // 集中保管日期为交证件日期
            record.setCadreId(_taiwanRecord.getCadreId()); // 确认
            record.setTaiwanRecordId(taiwanRecordId);
        }else{
            /**
             * 2017.05.23 直接添加证件时，系统检测一下在“批准办理新证件（未交证件）”中是否有这个人申请办理此类证件的记录。
             * 如果有，就不允许在这里添加；如果没有，就可以在这里添加
             */
            if (passportApplyService.checkApplyPassButNotHandle(cadreId, classId)) {
                MetaType passportClass = CmTag.getMetaType(classId);
                throw new OpException("该干部已经申请办理了" + passportClass.getName() + "，当前申请已通过，请办理证件交回");
            }
        }

        return passportMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id) {

        passportMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void abolish(Integer id, Byte cancelType,
                        String cancelTypeOther) {

        if (id != null && cancelType != null
                && (cancelType == AbroadConstants.ABROAD_PASSPORT_CANCEL_TYPE_ABOLISH
                || cancelType == AbroadConstants.ABROAD_PASSPORT_CANCEL_TYPE_OTHER)) {

            Passport passport = passportMapper.selectByPrimaryKey(id);

            // “未借出”状态下取消集中管理 ，转移到未确认
            Passport record = new Passport();
            record.setId(id);
            record.setType(AbroadConstants.ABROAD_PASSPORT_TYPE_CANCEL);
            record.setCancelType(cancelType);
            if (cancelType == AbroadConstants.ABROAD_PASSPORT_CANCEL_TYPE_OTHER)
                record.setCancelTypeOther(cancelTypeOther);

            // “借出”状态下取消集中管理，转移到已确认，并加备注
            if (BooleanUtils.isTrue(passport.getIsLent())) {
                record.setCancelConfirm(true);
                record.setCancelTime(new Date());
                record.setCancelRemark("在证件借出的情况下取消集中管理");
                record.setCancelUserId(ShiroHelper.getCurrentUserId());
            }

            passportMapper.updateByPrimaryKeySelective(record);
        }
    }

    @Transactional
    public Passport unabolish(Integer id) {

        if (id != null) {
            Passport passport = passportMapper.selectByPrimaryKey(id);

            if (updateIdDuplicate(id, AbroadConstants.ABROAD_PASSPORT_TYPE_KEEP,
                    passport.getCadreId(), passport.getClassId(), passport.getCode()) > 0) {
                MetaType mcPassportType = CmTag.getMetaType(passport.getClassId());
                throw new OpException("返回集中管理失败，" + passport.getUser().getRealname()
                        + "[" + mcPassportType.getName() + "]证件重复");
            }

            passport.setType(AbroadConstants.ABROAD_PASSPORT_TYPE_KEEP);
            passport.setCancelType(null);
            passport.setCancelTypeOther(null);
            passport.setCancelConfirm(false);
            passport.setCancelPic(null);
            passport.setCancelTime(null);
            passport.setCancelRemark(null);
            passport.setCancelUserId(null);

            PassportExample example = new PassportExample();
            example.createCriteria().andIdEqualTo(id).andTypeEqualTo(AbroadConstants.ABROAD_PASSPORT_TYPE_CANCEL);
            passportMapper.updateByExample(passport, example);

            return passport;
        }
        return null;
    }

    @Transactional
    public void lost(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        PassportExample example = new PassportExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));

        Passport record = new Passport();
        record.setType(AbroadConstants.ABROAD_PASSPORT_TYPE_LOST);

        passportMapper.updateByExampleSelective(record, example);
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        for (Integer id : ids) {

            Passport passport = passportMapper.selectByPrimaryKey(id);
            if (!(passport.getType() == AbroadConstants.ABROAD_PASSPORT_TYPE_KEEP
                    || (passport.getType() == AbroadConstants.ABROAD_PASSPORT_TYPE_LOST
                    && passport.getLostType() == AbroadConstants.ABROAD_PASSPORT_LOST_TYPE_ADD))) {
                // 只有集中管理证件 或 从 后台添加的 丢失证件，可以更新
                throw new OpException("只有集中管理库或后台添加的丢失证件可以进行删除操作");
            }
        }

        PassportExample example = new PassportExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        passportMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(Passport record) {
        if (StringUtils.isNotBlank(record.getCode()))
            Assert.isTrue(0 == idDuplicate(record.getId(), record.getType(), record.getCadreId(), record.getClassId(), record.getCode()), "duplicate");
        //record.setType(null);
        return passportMapper.updateByPrimaryKeySelective(record);
    }

    //证件找回
    @Transactional
    public int back(Passport record) {
        if (StringUtils.isNotBlank(record.getCode()))
            Assert.isTrue(0 == updateIdDuplicate(record.getId(), AbroadConstants.ABROAD_PASSPORT_TYPE_KEEP,
                    record.getCadreId(), record.getClassId(), record.getCode()), "duplicate");

        Passport passport = passportMapper.selectByPrimaryKey(record.getId());
        Assert.isTrue(passport.getType() == AbroadConstants.ABROAD_PASSPORT_TYPE_LOST, "wrong type");

        // 如果该证件找回之前被借出了，则找回时，应该修改借出状态为已归还
        if (passport.getIsLent()) {

            PassportDrawExample example = new PassportDrawExample();
            example.createCriteria().andPassportIdEqualTo(passport.getId());
            List<PassportDraw> passportDraws = passportDrawMapper.selectByExample(example);

            PassportDraw _record = new PassportDraw();
            _record.setId(passportDraws.get(0).getId()); // 证件在归还之前只能借出一次
            _record.setReturnRemark("证件被找回");
            _record.setRealReturnDate(new Date());
            _record.setDrawStatus(AbroadConstants.ABROAD_PASSPORT_DRAW_DRAW_STATUS_RETURN);
            passportDrawMapper.updateByPrimaryKeySelective(_record);
        }

        record.setType(AbroadConstants.ABROAD_PASSPORT_TYPE_KEEP);
        record.setIsLent(false);
        record.setHasFind(true);
        record.setFindTime(new Date());
        return passportMapper.updateByPrimaryKeySelective(record);
    }

   /* public Map<Integer, Passport> findAll() {

        PassportExample example = new PassportExample();
        example.createCriteria();
        example.setOrderByClause("sort_order desc");
        List<Passport> passportes = passportMapper.selectByExample(example);
        Map<Integer, Passport> map = new LinkedHashMap<>();
        for (Passport passport : passportes) {
            map.put(passport.getId(), passport);
        }

        return map;
    }*/

    // 证件过期扫描， 过期的证件转移到取消集中管理证件数据库
    @Transactional
    public void expire() {

        Date now = new Date();

        PassportSearchBean bean = new PassportSearchBean(null, null, null, null,
                AbroadConstants.ABROAD_PASSPORT_TYPE_KEEP, null, null, null);

        List<Passport> passports = iAbroadMapper.selectPassportList(bean, new RowBounds());
        for (Passport passport : passports) {
            Date expiryDate = passport.getExpiryDate();
            if (expiryDate.before(now)) {

                // 未借出状态，转移到取消未确认
                Passport record = new Passport();
                record.setId(passport.getId());
                record.setType(AbroadConstants.ABROAD_PASSPORT_TYPE_CANCEL);
                record.setCancelType(AbroadConstants.ABROAD_PASSPORT_CANCEL_TYPE_EXPIRE);

                if (BooleanUtils.isTrue(passport.getIsLent())) {
                    // 借出状态，转移到取消已确认，并加备注
                    record.setCancelTime(new Date());
                    record.setCancelConfirm(true);
                    record.setCancelRemark("在证件借出的情况下取消集中管理");
                }

                passportMapper.updateByPrimaryKeySelective(record);
            }
        }
    }
}
