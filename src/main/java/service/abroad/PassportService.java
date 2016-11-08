package service.abroad;

import bean.XlsPassport;
import domain.abroad.*;
import domain.cadre.Cadre;
import domain.sys.MetaType;
import domain.sys.SysUser;
import domain.sys.SysUserView;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import persistence.abroad.PassportDrawMapper;
import persistence.common.PassportSearchBean;
import service.BaseMapper;
import service.cadre.CadreService;
import service.helper.ShiroSecurityHelper;
import service.sys.SysUserService;
import sys.constants.SystemConstants;
import sys.tags.CmTag;

import java.util.*;

@Service
public class PassportService extends BaseMapper {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private CadreService cadreService;
    @Autowired
    private SafeBoxService safeBoxService;
    @Transactional
    public int importPassports(final List<XlsPassport> passports, byte type) {

        //int duplicate = 0;
        int success = 0;
        for(XlsPassport uRow: passports){

            Passport record = new Passport();

            String userCode = uRow.getUserCode();
            SysUserView uv = sysUserService.findByCode(userCode);
            if(uv== null) throw  new RuntimeException("工作证号："+userCode+"不存在");
            Cadre cadre = cadreService.findByUserId(uv.getId());
            if(cadre== null) throw  new RuntimeException("工作证号：" +userCode +" 姓名："+uv.getRealname() +"不是干部");
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
            SafeBox safeBox = safeBoxService.getByCode(uRow.getSafeCode());
            if(safeBox==null)
                if(uv== null) throw  new RuntimeException("保险柜："+safeBox.getCode()+"不存在");
            record.setSafeBoxId(safeBox.getId());
            record.setCreateTime(new Date());
            record.setIsLent(false);
            record.setCancelConfirm(SystemConstants.PASSPORT_CANCEL_CONFIRM_NOT);

            if (idDuplicate(null, record.getType(), record.getCadreId(), record.getClassId(), record.getCode())>0) {
                MetaType mcPassportType = CmTag.getMetaType(passportType);
                throw  new RuntimeException("导入失败，工作证号："+uRow.getUserCode() + "["+ mcPassportType.getName() + "]重复");
            }

            add(record, null);

            success++;
        }

        return success;
    }
    public List<Passport> findByCadreId(int cadreId){

        PassportSearchBean bean = new PassportSearchBean(null, cadreId, null, null,
                SystemConstants.PASSPORT_TYPE_KEEP, null, null, null);

       return selectMapper.selectPassportList(bean, new RowBounds());
    }

    // 0 不重复  1证件号码重复 2证件类别重复
    public int idDuplicate(Integer id, Byte type, int cadreId, int classId, String code){
        //Assert.isTrue(StringUtils.isNotBlank(code));

        if (id != null) {
            Passport passport = passportMapper.selectByPrimaryKey(id);
            type = passport.getType();
        } else {
            if (type == null)
                type = SystemConstants.PASSPORT_TYPE_KEEP; // 默认存入集中保管
        }

        if(StringUtils.isNotBlank(code)) {
            // 证件号码不允许重复
            PassportExample example = new PassportExample();
            PassportExample.Criteria criteria = example.createCriteria().andCodeEqualTo(code);
            if(id!=null) criteria.andIdNotEqualTo(id);
            if (passportMapper.countByExample(example) > 0) return 1;
        }

        if(type==SystemConstants.PASSPORT_TYPE_KEEP) {
            //“集中管理证件”中不存在同一个人有两本护照（或者港澳通行证、台湾通行证）就可以。
            // 其他三个“取消集中管理、丢失证件、作废证件”中，一个人可以有两本护照。
            PassportExample example2 = new PassportExample();
            PassportExample.Criteria criteria2 =
                    example2.createCriteria().andCadreIdEqualTo(cadreId).andTypeEqualTo(type)
                            .andClassIdEqualTo(classId);
            if (id != null) criteria2.andIdNotEqualTo(id);

            if(passportMapper.countByExample(example2) > 0) return 2;
        }

        return 0;
    }

    @Transactional
    public int add(Passport record, Integer applyId){

        Assert.isTrue(0==idDuplicate(null, record.getType(), record.getCadreId(), record.getClassId(), record.getCode()));

        if(applyId!=null){ // 交证件
            PassportApply _passportApply = passportApplyMapper.selectByPrimaryKey(applyId);
            Assert.isTrue(_passportApply.getCadreId().intValue() == record.getCadreId().intValue());
            Assert.isTrue(_passportApply.getClassId().intValue() == record.getClassId().intValue());

            PassportApply passportApply = new PassportApply();
            passportApply.setId(applyId);
            passportApply.setHandleDate(new Date());
            passportApply.setHandleUserId(ShiroSecurityHelper.getCurrentUserId());
            passportApplyMapper.updateByPrimaryKeySelective(passportApply);

            record.setKeepDate(new Date()); // 集中保管日期为交证件日期
            record.setCadreId(_passportApply.getCadreId()); // 确认
            record.setApplyId(applyId);
        }

        return passportMapper.insertSelective(record);
    }
    @Transactional
    public void del(Integer id){

        passportMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void abolish(Integer[] ids){

        if(ids==null || ids.length==0) return;

        PassportExample example = new PassportExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));

        Passport record = new Passport();
        record.setType(SystemConstants.PASSPORT_TYPE_CANCEL);
        record.setCancelType(SystemConstants.PASSPORT_CANCEL_TYPE_ABOLISH);

        passportMapper.updateByExampleSelective(record, example);
    }
    @Transactional
    public void lost(Integer[] ids){

        if(ids==null || ids.length==0) return;

        PassportExample example = new PassportExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));

        Passport record = new Passport();
        record.setType(SystemConstants.PASSPORT_TYPE_LOST);

        passportMapper.updateByExampleSelective(record, example);
    }
    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        for (Integer id : ids) {

            Passport passport = passportMapper.selectByPrimaryKey(id);
            if(!(passport.getType()==SystemConstants.PASSPORT_TYPE_KEEP
                    || (passport.getType()==SystemConstants.PASSPORT_TYPE_LOST
                    && passport.getLostType()==SystemConstants.PASSPORT_LOST_TYPE_ADD))){
                // 只有集中管理证件 或 从 后台添加的 丢失证件，可以更新
                throw new RuntimeException("只有集中管理库或后台添加的丢失证件可以进行删除操作");
            }
        }

        PassportExample example = new PassportExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        passportMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(Passport record){
        if(StringUtils.isNotBlank(record.getCode()))
            Assert.isTrue(0==idDuplicate(record.getId(), record.getType(), record.getCadreId(), record.getClassId(), record.getCode()));
        return passportMapper.updateByPrimaryKeySelective(record);
    }

    //证件找回
    @Transactional
    public int back(Passport record){
        if(StringUtils.isNotBlank(record.getCode()))
            Assert.isTrue(0==idDuplicate(record.getId(), record.getType(), record.getCadreId(), record.getClassId(), record.getCode()));

        Passport passport = passportMapper.selectByPrimaryKey(record.getId());
        Assert.isTrue(passport.getType()==SystemConstants.PASSPORT_TYPE_LOST);

        // 如果该证件找回之前被借出了，则找回时，应该修改借出状态为已归还
        if(passport.getIsLent()){

            PassportDrawExample example = new PassportDrawExample();
            example.createCriteria().andPassportIdEqualTo(passport.getId());
            List<PassportDraw> passportDraws = passportDrawMapper.selectByExample(example);

            PassportDraw _record = new PassportDraw();
            _record.setId(passportDraws.get(0).getId()); // 证件在归还之前只能借出一次
            _record.setReturnRemark("证件被找回");
            _record.setRealReturnDate(new Date());
            _record.setDrawStatus(SystemConstants.PASSPORT_DRAW_DRAW_STATUS_RETURN);
            passportDrawMapper.updateByPrimaryKeySelective(_record);
        }

        record.setType(SystemConstants.PASSPORT_TYPE_KEEP);
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
    public void expire(){

        Date now = new Date();

        PassportSearchBean bean = new PassportSearchBean(null, null, null, null,
                SystemConstants.PASSPORT_TYPE_KEEP, null, null, null);

        List<Passport> passports = selectMapper.selectPassportList(bean, new RowBounds());
        for (Passport passport : passports) {
            Date expiryDate = passport.getExpiryDate();
            if(expiryDate.before(now)){
                Passport record = new Passport();
                record.setId(passport.getId());
                record.setType(SystemConstants.PASSPORT_TYPE_CANCEL);
                record.setCancelType(SystemConstants.PASSPORT_CANCEL_TYPE_EXPIRE);
                //record.setCancelTime(new Date());

                passportMapper.updateByPrimaryKeySelective(record);
            }
        }
    }
}
