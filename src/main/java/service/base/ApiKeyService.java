package service.base;

import domain.base.ApiKey;
import domain.base.ApiKeyExample;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ApiKeyService extends BaseMapper {

    public boolean idDuplicate(Integer id, String name){


        Assert.isTrue(StringUtils.isNotBlank(name), "null");

        ApiKeyExample example = new ApiKeyExample();
        ApiKeyExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(name);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return apiKeyMapper.countByExample(example) > 0;
    }

    @Transactional
    //@CacheEvict(value="ApiKey:ALL", allEntries = true)
    public void insertSelective(ApiKey record){

        Assert.isTrue(!idDuplicate(null,"duplicate"));

        apiKeyMapper.insertSelective(record);
    }

    @Transactional
    //@CacheEvict(value="ApiKey:ALL", allEntries = true)
    public void del(Integer id){

        apiKeyMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    //@CacheEvict(value="ApiKey:ALL", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ApiKeyExample example = new ApiKeyExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        apiKeyMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(ApiKey record){

        apiKeyMapper.updateByPrimaryKeySelective(record);
    }

    //@Cacheable(value="ApiKey:ALL")
    public Map<Integer, ApiKey> findAll() {

        ApiKeyExample example = new ApiKeyExample();
        example.createCriteria();

        List<ApiKey> records = apiKeyMapper.selectByExample(example);
        Map<Integer, ApiKey> map = new LinkedHashMap<>();
        for (ApiKey record : records) {
            map.put(record.getId(), record);
        }

        return map;
    }

}
