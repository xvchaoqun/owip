package service.abroad;

import domain.Passport;
import domain.PassportExample;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class PassportService extends BaseMapper {

    public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code));

        PassportExample example = new PassportExample();
        PassportExample.Criteria criteria = example.createCriteria().andCodeEqualTo(code);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return passportMapper.countByExample(example) > 0;
    }

    @Transactional
    public int insertSelective(Passport record){

        Assert.isTrue(!idDuplicate(null, record.getCode()));
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
            Assert.isTrue(!idDuplicate(record.getId(), record.getCode()));
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
}
