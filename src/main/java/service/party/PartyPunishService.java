package service.party;

import domain.party.PartyPunish;
import domain.party.PartyPunishExample;
import domain.party.PartyPunishView;
import domain.party.PartyPunishViewExample;
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

    // 受处分情况（用于任免审批表）
    public List<PartyPunish> list(int userId) {

        PartyPunishExample example = new PartyPunishExample();
        example.createCriteria().andUserIdEqualTo(userId);
        example.setOrderByClause("punish_time desc");

        return partyPunishMapper.selectByExample(example);
    }

    public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code), "null");

        PartyPunishExample example = new PartyPunishExample();
        PartyPunishExample.Criteria criteria = example.createCriteria();
        if(id!=null) criteria.andIdNotEqualTo(id);

        return partyPunishMapper.countByExample(example) > 0;
    }

    public PartyPunishView getById(Integer id){
        PartyPunishViewExample example = new PartyPunishViewExample();
        example.createCriteria().andIdEqualTo(id);
        List<PartyPunishView> partyPunishViews = partyPunishViewMapper.selectByExample(example);
        PartyPunishView record = new PartyPunishView();
        if (partyPunishViews.size() > 0)
            record=partyPunishViews.get(0);

        return record;
    }

    @Transactional
    public void insertSelective(PartyPunish record){

        //Assert.isTrue(!idDuplicate(null, record.getName()), "duplicate");
        record.setSortOrder(getNextSortOrder("ow_party_punish", null));
        partyPunishMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        partyPunishMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        PartyPunishExample example = new PartyPunishExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        partyPunishMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(PartyPunish record){
        //if(StringUtils.isNotBlank(record.getName()))
            //Assert.isTrue(!idDuplicate(record.getId(), record.getName()), "duplicate");
        partyPunishMapper.updateByPrimaryKeySelective(record);
    }

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
    public void changeOrder(int id, int addNum) {

        changeOrder("ow_party_punish", null, ORDER_BY_DESC, id, addNum);
    }
}
