package service.abroad;

import bean.XlsPassport;
import domain.*;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;
import service.cadre.CadreService;
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
            SysUser sysUser = sysUserService.findByUsername(userCode);
            if(sysUser== null) throw  new RuntimeException("工作证号："+userCode+"不存在");
            Cadre cadre = cadreService.findByUserId(sysUser.getId());
            if(cadre== null) throw  new RuntimeException("工作证号：" +userCode +" 姓名："+sysUser.getRealname() +"不是干部");
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
                if(sysUser== null) throw  new RuntimeException("保险柜："+safeBox.getCode()+"不存在");
            record.setSafeBoxId(safeBox.getId());
            record.setCreateTime(new Date());
            record.setAbolish(false);
            record.setCancelConfirm(false);

            if (idDuplicate(null, record.getType(), record.getCadreId(), record.getClassId(), record.getCode())) {
                MetaType mcPassportType = CmTag.getMetaType("mc_passport_type", passportType);
                throw  new RuntimeException("导入失败，工作证号："+uRow.getUserCode() + "["+ mcPassportType.getName() + "]重复");
            }

            add(record, null);

            success++;
        }

        return success;
    }
    public List<Passport> findByCadreId(int cadreId){

       return selectMapper.selectPassportList(cadreId, null, null,
               SystemConstants.PASSPORT_TYPE_KEEP, null, null, false, new RowBounds());
    }

    public boolean idDuplicate(Integer id, Byte type, int cadreId, int classId, String code){

        Assert.isTrue(StringUtils.isNotBlank(code));

        // 证件号码不允许重复
        PassportExample example = new PassportExample();
        PassportExample.Criteria criteria = example.createCriteria().andCodeEqualTo(code).andAbolishEqualTo(false);
        if(id!=null){
            criteria.andIdNotEqualTo(id);
            Passport passport = passportMapper.selectByPrimaryKey(id);
            type = passport.getType();
        }else{
            if(type==null)
                type=SystemConstants.PASSPORT_TYPE_KEEP;
        }

        if(type==SystemConstants.PASSPORT_TYPE_KEEP) {
            //“集中管理证件”中不存在同一个人有两本护照（或者港澳通行证、台湾通行证）就可以。
            // 其他三个“取消集中管理、丢失证件、作废证件”中，一个人可以有两本护照。
            PassportExample example2 = new PassportExample();
            PassportExample.Criteria criteria2 =
                    example2.createCriteria().andCadreIdEqualTo(cadreId).andTypeEqualTo(type)
                            .andClassIdEqualTo(classId).andAbolishEqualTo(false);
            if (id != null) criteria2.andIdNotEqualTo(id);

            return passportMapper.countByExample(example) > 0 || passportMapper.countByExample(example2) > 0;
        }

        return passportMapper.countByExample(example) > 0;
    }

    @Transactional
    public int add(Passport record, Integer applyId){

        Assert.isTrue(!idDuplicate(null, record.getType(), record.getCadreId(), record.getClassId(), record.getCode()));

        if(applyId!=null){ // 交证件
            PassportApply _passportApply = passportApplyMapper.selectByPrimaryKey(applyId);
            Assert.isTrue(_passportApply.getCadreId().intValue() == record.getCadreId().intValue());
            Assert.isTrue(_passportApply.getClassId().intValue() == record.getClassId().intValue());

            PassportApply passportApply = new PassportApply();
            passportApply.setId(applyId);
            passportApply.setHandleDate(new Date());
            passportApplyMapper.updateByPrimaryKeySelective(passportApply);

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
        record.setAbolish(true);

        passportMapper.updateByExampleSelective(record, example);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        PassportExample example = new PassportExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        passportMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(Passport record){
        if(StringUtils.isNotBlank(record.getCode()))
            Assert.isTrue(!idDuplicate(record.getId(), record.getType(), record.getCadreId(), record.getClassId(), record.getCode()));
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
        List<Passport> passports = selectMapper.selectPassportList(null, null, null,
                SystemConstants.PASSPORT_TYPE_KEEP, null, null, false, new RowBounds());
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
