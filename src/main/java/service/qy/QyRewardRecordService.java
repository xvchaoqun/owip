package service.qy;

import domain.qy.QyRewardRecord;
import domain.qy.QyRewardRecordExample;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class QyRewardRecordService extends QyBaseMapper {

    public boolean idDuplicate(Integer id, Integer yearRewardId){

        Assert.isTrue(StringUtils.isNotBlank(String.valueOf(yearRewardId)), "null");

        QyRewardRecordExample example = new QyRewardRecordExample();
        QyRewardRecordExample.Criteria criteria = example.createCriteria().andYearRewardIdEqualTo(yearRewardId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return qyRewardRecordMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(QyRewardRecord record){

        Assert.isTrue(!idDuplicate(null, record.getYearRewardId()), "duplicate");
        qyRewardRecordMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        qyRewardRecordMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        QyRewardRecordExample example = new QyRewardRecordExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        qyRewardRecordMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(QyRewardRecord record){
        if(record.getYearRewardId()!=null)
            Assert.isTrue(!idDuplicate(record.getId(), record.getYearRewardId()), "duplicate");
        qyRewardRecordMapper.updateByPrimaryKeySelective(record);
    }


    public Map<Integer, QyRewardRecord> findAll() {

        QyRewardRecordExample example = new QyRewardRecordExample();
        example.setOrderByClause("sort_order desc");
        List<QyRewardRecord> records = qyRewardRecordMapper.selectByExample(example);
        Map<Integer, QyRewardRecord> map = new LinkedHashMap<>();
        for (QyRewardRecord record : records) {
            map.put(record.getId(), record);
        }

        return map;
    }
}
