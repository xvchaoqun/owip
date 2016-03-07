package service.abroad;

import domain.Passport;
import domain.PassportApply;
import domain.PassportExample;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;
import sys.constants.SystemConstants;

import java.util.*;

@Service
public class PassportService extends BaseMapper {

    public List<Passport> findByCadreId(int cadreId){

       return selectMapper.selectPassportList(cadreId, null, null, null, false, new RowBounds());
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
            org.eclipse.jdt.internal.core.Assert.isTrue(_passportApply.getCadreId().intValue() == record.getCadreId().intValue());
            org.eclipse.jdt.internal.core.Assert.isTrue(_passportApply.getClassId().intValue() == record.getClassId().intValue());

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
                SystemConstants.PASSPORT_TYPE_KEEP, false, new RowBounds());
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
