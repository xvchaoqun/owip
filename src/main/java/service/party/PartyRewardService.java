package service.party;

import domain.party.*;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class PartyRewardService extends BaseMapper {

    public boolean idDuplicate(Integer id, Integer pbu){

        PartyRewardExample example = new PartyRewardExample();
        PartyRewardExample.Criteria criteria = example.createCriteria();
        if(id!=null) criteria.andIdNotEqualTo(id);

        return partyRewardMapper.countByExample(example) > 0;
    }

    public PartyRewardView getById(Integer id){
        PartyRewardViewExample example = new PartyRewardViewExample();
        example.createCriteria().andIdEqualTo(id);
        List<PartyRewardView> partyRewardViews = partyRewardViewMapper.selectByExample(example);
        PartyRewardView record = new PartyRewardView();
        if (partyRewardViews.size() > 0)
            record=partyRewardViews.get(0);

        return record;
    }

    @Transactional
    @CacheEvict(value="PartyReward:ALL", allEntries = true)
    public void insertSelective(PartyReward record){

        //Assert.isTrue(!idDuplicate(record.getId(), null), "duplicate");
        record.setSortOrder(getNextSortOrder("ow_party_reward", null));
        partyRewardMapper.insertSelective(record);
    }

    @Transactional
    @CacheEvict(value="PartyReward:ALL", allEntries = true)
    public void del(Integer id){

        partyRewardMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @CacheEvict(value="PartyReward:ALL", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        PartyRewardExample example = new PartyRewardExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        partyRewardMapper.deleteByExample(example);
    }

    @Transactional
    @CacheEvict(value="PartyReward:ALL", allEntries = true)
    public void updateByPrimaryKeySelective(PartyReward record){
        //if(StringUtils.isNotBlank(record.getName()))
            //Assert.isTrue(!idDuplicate(record.getId(), null), "duplicate");
        partyRewardMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="PartyReward:ALL")
    public Map<Integer, PartyReward> findAll() {

        PartyRewardExample example = new PartyRewardExample();
        example.createCriteria();
        example.setOrderByClause("sort_order desc");
        List<PartyReward> records = partyRewardMapper.selectByExample(example);
        Map<Integer, PartyReward> map = new LinkedHashMap<>();
        for (PartyReward record : records) {
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
    @CacheEvict(value = "PartyReward:ALL", allEntries = true)
    public void changeOrder(int id, int addNum) {

        changeOrder("ow_party_reward", null, ORDER_BY_DESC, id, addNum);
    }
}
