package service.base;

import domain.base.MetaClass;
import domain.base.MetaClassExample;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fafa on 2015/11/10.
 */
@Service
public class MetaClassService extends BaseMapper {

    public String genCode(){

        String prefix = "mc";
        String code = "";
        long count = 0;
        do {
            code = prefix + "_" + RandomStringUtils.randomAlphanumeric(6).toLowerCase();
            MetaClassExample example = new MetaClassExample();
            example.createCriteria().andCodeEqualTo(code).andAvailableEqualTo(true);
            count = metaClassMapper.countByExample(example);
        } while(count>0);
        return code;
    }

    public boolean codeAvailable(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code), "code is blank");

        MetaClassExample example = new MetaClassExample();
        MetaClassExample.Criteria criteria = example.createCriteria().andCodeEqualTo(code).andAvailableEqualTo(true);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return metaClassMapper.countByExample(example) == 0;
    }

    @Transactional
    @Caching(evict= {
            @CacheEvict(value = "MetaClass:ALL", allEntries = true),
            @CacheEvict(value = "MetaClass:Code:ALL", allEntries = true),
            @CacheEvict(value = "MetaTyes", allEntries = true)
    })
    public int insertSelective(MetaClass record){

        Assert.isTrue(codeAvailable(null, record.getCode()), "wrong code");
        record.setSortOrder(getNextSortOrder("base_meta_class", null));
        return metaClassMapper.insertSelective(record);
    }

    @Transactional
    @Caching(evict= {
            @CacheEvict(value = "MetaClass:ALL", allEntries = true),
            @CacheEvict(value = "MetaClass:Code:ALL", allEntries = true),
            @CacheEvict(value = "MetaTyes", allEntries = true)
    })
    public void del(Integer id){

        metaClassMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @Caching(evict= {
            @CacheEvict(value = "MetaClass:ALL", allEntries = true),
            @CacheEvict(value = "MetaClass:Code:ALL", allEntries = true)
    })
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        MetaClassExample example = new MetaClassExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        metaClassMapper.deleteByExample(example);
    }

    @Transactional
    @Caching(evict= {
            @CacheEvict(value = "MetaClass:ALL", allEntries = true),
            @CacheEvict(value = "MetaClass:Code:ALL", allEntries = true),
            @CacheEvict(value = "MetaTyes", allEntries = true)
    })
    public int updateByPrimaryKeySelective(MetaClass record){
        if(StringUtils.isNotBlank(record.getCode()))
            Assert.isTrue(codeAvailable(record.getId(), record.getCode()), "wrong code");
        return metaClassMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="MetaClass:Code:ALL")
    public Map<String, MetaClass> codeKeyMap() {

        MetaClassExample example = new MetaClassExample();
        example.createCriteria().andAvailableEqualTo(true);
        example.setOrderByClause("sort_order desc");
        List<MetaClass> metaClasses = metaClassMapper.selectByExample(example);
        Map<String, MetaClass> map = new LinkedHashMap<>();
        for (MetaClass metaClass : metaClasses) {
            map.put(metaClass.getCode(), metaClass);
        }

        return map;
    }

    @Cacheable(value="MetaClass:ALL")
    public Map<Integer, MetaClass> findAll() {

        MetaClassExample example = new MetaClassExample();
        example.createCriteria().andAvailableEqualTo(true);
        example.setOrderByClause("sort_order desc");
        List<MetaClass> metaClasses = metaClassMapper.selectByExample(example);
        Map<Integer, MetaClass> map = new LinkedHashMap<>();
        for (MetaClass metaClass : metaClasses) {
            map.put(metaClass.getId(), metaClass);
        }

        return map;
    }

    @Transactional
    @Caching(evict={
            @CacheEvict(value = "MetaClass:ALL", allEntries = true),
            @CacheEvict(value = "MetaClass:Code:ALL", allEntries = true),
            @CacheEvict(value = "MetaTyes", allEntries = true)
    })
    public void updateRoles(int id, int roleId){

        MetaClass metaClass = new MetaClass();
        metaClass.setId(id);
        metaClass.setRoleId(roleId);

        metaClassMapper.updateByPrimaryKeySelective(metaClass);
    }
    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * 3.sort_order = LAST_INSERT_ID()+1,
     * @param id
     * @param addNum
     */
    @Transactional
    @Caching(evict= {
            @CacheEvict(value = "MetaClass:ALL", allEntries = true),
            @CacheEvict(value = "MetaClass:Code:ALL", allEntries = true)
    })
    public void changeOrder(int id, int addNum) {

        changeOrder("base_meta_class", null, ORDER_BY_DESC, id, addNum);
    }
}
