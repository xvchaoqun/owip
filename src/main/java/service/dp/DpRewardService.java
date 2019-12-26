package service.dp;

import domain.dp.DpReward;
import domain.dp.DpRewardExample;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import sys.constants.SystemConstants;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class DpRewardService extends DpBaseMapper {

    public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code), "null");

        DpRewardExample example = new DpRewardExample();
        DpRewardExample.Criteria criteria = example.createCriteria();
        if(id!=null) criteria.andIdNotEqualTo(id);

        return dpRewardMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(DpReward record){

        record.setStatus(SystemConstants.RECORD_STATUS_FORMAL);
        record.setSortOrder(getNextSortOrder("dp_reward", null));
        dpRewardMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        dpRewardMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        DpRewardExample example = new DpRewardExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        dpRewardMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(DpReward record){

        dpRewardMapper.updateByPrimaryKeySelective(record);
    }

    public Map<Integer, DpReward> findAll() {

        DpRewardExample example = new DpRewardExample();
        example.createCriteria();
        example.setOrderByClause("sort_order desc");
        List<DpReward> records = dpRewardMapper.selectByExample(example);
        Map<Integer, DpReward> map = new LinkedHashMap<>();
        for (DpReward record : records) {
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

        changeOrder("dp_reward", null, ORDER_BY_DESC, id, addNum);
    }

    //党派内奖励和其他奖励
    public List<DpReward> list(Integer userId){

        DpRewardExample example = new DpRewardExample();
        example.createCriteria().andUserIdEqualTo(userId);
        example.setOrderByClause("reward_type asc");
        List<DpReward> dpRewards = dpRewardMapper.selectByExample(example);
        return dpRewards.size() > 0 ? dpRewards : null;
    }
}
