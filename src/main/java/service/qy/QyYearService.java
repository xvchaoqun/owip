package service.qy;

import domain.qy.QyYear;
import domain.qy.QyYearExample;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class QyYearService extends QyBaseMapper {

    public boolean idDuplicate(Integer id, Integer year){

        Assert.isTrue(StringUtils.isNotBlank(String.valueOf(year)), "null");

        QyYearExample example = new QyYearExample();
        QyYearExample.Criteria criteria = example.createCriteria().andYearEqualTo(year);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return qyYearMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(QyYear record){

        Assert.isTrue(!idDuplicate(null, record.getYear()), "duplicate");
        qyYearMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        qyYearMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        QyYearExample example = new QyYearExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        qyYearMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(QyYear record){
        if(record.getYear()!=null)
            Assert.isTrue(!idDuplicate(record.getId(), record.getYear()), "duplicate");
        qyYearMapper.updateByPrimaryKeySelective(record);
    }

    public Map<Integer, QyYear> findAll() {

        QyYearExample example = new QyYearExample();
        example.setOrderByClause("sort_order desc");
        List<QyYear> records = qyYearMapper.selectByExample(example);
        Map<Integer, QyYear> map = new LinkedHashMap<>();
        for (QyYear record : records) {
            map.put(record.getId(), record);
        }

        return map;
    }
}
