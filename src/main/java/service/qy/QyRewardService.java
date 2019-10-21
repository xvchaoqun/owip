package service.qy;

import domain.qy.QyReward;
import domain.qy.QyRewardExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class QyRewardService extends QyBaseMapper {


    @Transactional
    public void insertSelective(QyReward record){

        qyRewardMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        qyRewardMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        QyRewardExample example = new QyRewardExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        qyRewardMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(QyReward record){

        qyRewardMapper.updateByPrimaryKeySelective(record);
    }

    public Map<Integer, QyReward> findAll() {

        QyRewardExample example = new QyRewardExample();

        example.setOrderByClause("sort_order desc");
        List<QyReward> records = qyRewardMapper.selectByExample(example);
        Map<Integer, QyReward> map = new LinkedHashMap<>();
        for (QyReward record : records) {
            map.put(record.getId(), record);
        }

        return map;
    }
}
