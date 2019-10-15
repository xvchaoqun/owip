package service.party;

import domain.party.PartyPunish;
import domain.party.PartyPunishExample;
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
public class PartyPunishService extends BaseMapper {

    public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code), "null");

        PartyPunishExample example = new PartyPunishExample();
        PartyPunishExample.Criteria criteria = example.createCriteria();
        if(id!=null) criteria.andIdNotEqualTo(id);

        return partyPunishMapper.countByExample(example) > 0;
    }

    @Transactional
    @CacheEvict(value="PartyPunish:ALL", allEntries = true)
    public void insertSelective(PartyPunish record){

        //Assert.isTrue(!idDuplicate(null, record.getName()), "duplicate");
        record.setSortOrder(getNextSortOrder("ow_party_punish", null));
        partyPunishMapper.insertSelective(record);
    }

    @Transactional
    @CacheEvict(value="PartyPunish:ALL", allEntries = true)
    public void del(Integer id){

        partyPunishMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @CacheEvict(value="PartyPunish:ALL", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        PartyPunishExample example = new PartyPunishExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        partyPunishMapper.deleteByExample(example);
    }

    @Transactional
    @CacheEvict(value="PartyPunish:ALL", allEntries = true)
    public void updateByPrimaryKeySelective(PartyPunish record){
        //if(StringUtils.isNotBlank(record.getName()))
            //Assert.isTrue(!idDuplicate(record.getId(), record.getName()), "duplicate");
        partyPunishMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="PartyPunish:ALL")
    public Map<Integer, PartyPunish> findAll() {

        PartyPunishExample example = new PartyPunishExample();
        example.createCriteria();
        example.setOrderByClause("sort_order desc");
        List<PartyPunish> records = partyPunishMapper.selectByExample(example);
        Map<Integer, PartyPunish> map = new LinkedHashMap<>();
        for (PartyPunish record : records) {
            map.put(record.getId(), record);
        }

        return map;
    }
    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    @CacheEvict(value = "PartyPunish:ALL", allEntries = true)
    public void changeOrder(int id, int addNum) {

        changeOrder("ow_party_punish", null, ORDER_BY_DESC, id, addNum);
    }
}
