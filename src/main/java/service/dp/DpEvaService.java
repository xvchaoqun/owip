package service.dp;

import controller.global.OpException;
import domain.dp.DpEva;
import domain.dp.DpEvaExample;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class DpEvaService extends DpBaseMapper {

    public boolean idDuplicate(Integer id, String title){

        Assert.isTrue(StringUtils.isNotBlank(title), "null");

        DpEvaExample example = new DpEvaExample();
        DpEvaExample.Criteria criteria = example.createCriteria().andTitleEqualTo(title);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return dpEvaMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(DpEva record){

        Integer year = record.getYear();
        DpEvaExample example = new DpEvaExample();
        example.createCriteria().andYearEqualTo(year).andUserIdEqualTo(record.getUserId());
        List<DpEva> dpEvas = dpEvaMapper.selectByExample(example);
        if (dpEvas.size() > 0){
            throw new OpException("{0}年度测评已存在。", year);
        }
        //Assert.isTrue(!idDuplicate(null, record.getTitle()), "duplicate");
        //record.setSortOrder(getNextSortOrder("dp_eva", null));
        dpEvaMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        dpEvaMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        DpEvaExample example = new DpEvaExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        dpEvaMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(DpEva record){
        DpEvaExample example = new DpEvaExample();
        example.createCriteria().andYearEqualTo(record.getYear()).andUserIdEqualTo(record.getUserId());
        List<DpEva> dpEvas = dpEvaMapper.selectByExample(example);
        if (dpEvas.size() > 0) {
            throw new OpException("{0}年度测评已存在。", record.getYear());
        }

        dpEvaMapper.updateByPrimaryKeySelective(record);
    }

    public Map<Integer, DpEva> findAll() {

        DpEvaExample example = new DpEvaExample();
        example.createCriteria();
        example.setOrderByClause("sort_order desc");
        List<DpEva> records = dpEvaMapper.selectByExample(example);
        Map<Integer, DpEva> map = new LinkedHashMap<>();
        for (DpEva record : records) {
            map.put(record.getId(), record);
        }

        return map;
    }
}
