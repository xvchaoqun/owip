package service.party;

import domain.party.PartyPost;
import domain.party.PartyPostExample;
import org.apache.commons.lang.StringUtils;
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
public class PartyPostService extends BaseMapper {

    public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code), "null");

        PartyPostExample example = new PartyPostExample();
        PartyPostExample.Criteria criteria = example.createCriteria();
        if(id!=null) criteria.andIdNotEqualTo(id);

        return partyPostMapper.countByExample(example) > 0;
    }

    @Transactional
    @CacheEvict(value="PartyPost:ALL", allEntries = true)
    public void insertSelective(PartyPost record){

        //Assert.isTrue(!idDuplicate(null, String.valueOf(record.getUserId())), "duplicate");
        //record.setSortOrder(getNextSortOrder("ow_party_post", null));
        partyPostMapper.insertSelective(record);
    }

    @Transactional
    @CacheEvict(value="PartyPost:ALL", allEntries = true)
    public void del(Integer id){

        partyPostMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @CacheEvict(value="PartyPost:ALL", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        PartyPostExample example = new PartyPostExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        partyPostMapper.deleteByExample(example);
    }

    @Transactional
    @CacheEvict(value="PartyPost:ALL", allEntries = true)
    public void updateByPrimaryKeySelective(PartyPost record){
        //if(StringUtils.isNotBlank(String.valueOf(record.getId())))
            //Assert.isTrue(!idDuplicate(record.getId(), String.valueOf(record.getId())), "duplicate");
        partyPostMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="PartyPost:ALL")
    public Map<Integer, PartyPost> findAll() {

        PartyPostExample example = new PartyPostExample();
        example.createCriteria();
        example.setOrderByClause("sort_order desc");
        List<PartyPost> records = partyPostMapper.selectByExample(example);
        Map<Integer, PartyPost> map = new LinkedHashMap<>();
        for (PartyPost record : records) {
            map.put(record.getId(), record);
        }

        return map;
    }
}
