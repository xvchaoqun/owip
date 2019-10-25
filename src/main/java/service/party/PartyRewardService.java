package service.party;

import domain.base.MetaType;
import domain.party.PartyReward;
import domain.party.PartyRewardExample;
import domain.party.PartyRewardView;
import domain.party.PartyRewardViewExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.base.MetaTypeService;
import sys.constants.OwConstants;

import java.util.*;

@Service
public class PartyRewardService extends BaseMapper {

    @Autowired
    private MetaTypeService metaTypeService;

    // 获取情况（用于信息采集表）
    public List<PartyReward> list(int userId) {

        Map<String, MetaType> codeKeyMap = metaTypeService.codeKeyMap();
        MetaType gjj = codeKeyMap.get("mc_party_reward_gjj"); // 国家级
        MetaType sbj = codeKeyMap.get("mc_party_reward_sbj"); // 省部级
        MetaType dtj = codeKeyMap.get("mc_party_reward_dtj"); // 地厅级
        List<Integer> rewardLevels = new ArrayList<>();
        rewardLevels.add(gjj.getId());
        rewardLevels.add(sbj.getId());
        rewardLevels.add(dtj.getId());

        PartyRewardExample example = new PartyRewardExample();
        example.createCriteria().andUserIdEqualTo(userId)
                .andTypeEqualTo(OwConstants.OW_PARTY_REPU_MEMBER)
                .andRewardLevelIn(rewardLevels);
        example.setOrderByClause("reward_time desc");

        return partyRewardMapper.selectByExample(example);
    }

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
    public void insertSelective(PartyReward record){

        //Assert.isTrue(!idDuplicate(record.getId(), null), "duplicate");
        record.setSortOrder(getNextSortOrder("ow_party_reward", null));
        partyRewardMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        partyRewardMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        PartyRewardExample example = new PartyRewardExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        partyRewardMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(PartyReward record){
        //if(StringUtils.isNotBlank(record.getName()))
            //Assert.isTrue(!idDuplicate(record.getId(), null), "duplicate");
        partyRewardMapper.updateByPrimaryKeySelective(record);
    }

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
    public void changeOrder(int id, int addNum) {

        changeOrder("ow_party_reward", null, ORDER_BY_DESC, id, addNum);
    }
}
