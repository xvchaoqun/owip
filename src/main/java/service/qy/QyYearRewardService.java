package service.qy;

import domain.qy.QyYearReward;
import domain.qy.QyYearRewardExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class QyYearRewardService extends QyBaseMapper {

    public boolean idDuplicate(Integer id, Integer yearId,Integer rewardId){

        if(yearId==null || rewardId==null ) return false;

        QyYearRewardExample example = new QyYearRewardExample();
        QyYearRewardExample.Criteria criteria = example.createCriteria().andYearIdEqualTo(yearId).andRewardIdEqualTo(rewardId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return qyYearRewardMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(QyYearReward record){
        Assert.isTrue(!idDuplicate(null, record.getYearId(), record.getRewardId()), "duplicate");
        record.setSortOrder(getNextSortOrder("qy_year_reward", null));
        qyYearRewardMapper.insertSelective(record);
    }

    @Transactional

    public void del(Integer id){

        qyYearRewardMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        QyYearRewardExample example = new QyYearRewardExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        qyYearRewardMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(QyYearReward record){
        if(record.getYearId()!=null && record.getRewardId()!=null)
            Assert.isTrue(!idDuplicate(null, record.getYearId(), record.getRewardId()), "duplicate");
        qyYearRewardMapper.updateByPrimaryKeySelective(record);
    }

    public Map<Integer, QyYearReward> findAll() {

        QyYearRewardExample example = new QyYearRewardExample();

        example.setOrderByClause("sort_order desc");
        List<QyYearReward> records = qyYearRewardMapper.selectByExample(example);
        Map<Integer, QyYearReward> map = new LinkedHashMap<>();
        for (QyYearReward record : records) {
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

        changeOrder("qy_year_reward", null, ORDER_BY_DESC, id, addNum);
    }
}
