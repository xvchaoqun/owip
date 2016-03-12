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

            if (idDuplicate(null, record.getCadreId(), record.getClassId(), record.getCode())) {
                MetaType mcPassportType = CmTag.getMetaType("mc_passport_type", passportType);
                throw  new RuntimeException("导入失败，工作证号："+uRow.getUserCode() + "["+ mcPassportType.getName() + "]重复");
            }

            add(record, null);

            success++;
        }

        return success;
    }
    public List<Passport> findByCadreId(int cadreId){

       return selectMapper.selectPassportList(cadreId, null, null, null, null, false, new RowBounds());
    }

    public boolean idDuplicate(Integer id, int cadreId, int classId, String code){

        Assert.isTrue(StringUtils.isNotBlank(code));

        PassportExample example = new PassportExample();
        PassportExample.Criteria criteria = example.createCriteria().andCodeEqualTo(code).andAbolishEqualTo(false);
        if(id!=null) criteria.andIdNotEqualTo(id);

        PassportExample example2 = new PassportExample();
        PassportExample.Criteria criteria2 =
                example2.createCriteria().andCadreIdEqualTo(cadreId)
                        .andClassIdEqualTo(classId).andAbolishEqualTo(false);
        if(id!=null) criteria2.andIdNotEqualTo(id);

        return passportMapper.countByExample(example) > 0 || passportMapper.countByExample(example2) > 0;
    }

    @Transactional
    public int add(Passport record, Integer applyId){

        Assert.isTrue(!idDuplicate(null, record.getCadreId(), record.getClassId(), record.getCode()));

        if(applyId!=null){ // 交证件
            PassportApply _passportApply = passportApplyMapper.selectByPrimaryKey(applyId);
            Assert.isTrue(_passportApply.getCadreId().intValue() == record.getCadreId().intValue());
            Assert.isTrue(_passportApply.getClassId().intValue() == record.getClassId().intValue());

            PassportApply passportApply = new PassportApply();
            passportApply.setId(applyId);
            passportApply.setHandleDate(new Date());
            passportApplyMapper.updateByPrimaryKeySelective(passportApply);

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

        passportMapper.updateByExample(record, example);
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
            Assert.isTrue(!idDuplicate(record.getId(), record.getCadreId(), record.getClassId(), record.getCode()));
        return passportMapper.updateByPrimaryKeySelective(record);
    }

    public Map<Integer, Passport> findAll() {

        PassportExample example = new PassportExample();
        example.createCriteria();
        example.setOrderByClause("sort_order desc");
        List<Passport> passportes = passportMapper.selectByExample(example);
        Map<Integer, Passport> map = new LinkedHashMap<>();
        for (Passport passport : passportes) {
            map.put(passport.getId(), passport);
        }

        return map;
    }

    // 证件过期扫描， 过期的证件转移到取消集中管理证件数据库
    @Transactional
    public void expire(){

        Date now = new Date();
        List<Passport> passports = selectMapper.selectPassportList(null, null, null,
                SystemConstants.PASSPORT_TYPE_KEEP, null, false, new RowBounds());
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
