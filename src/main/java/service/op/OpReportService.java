package service.op;

import domain.op.OpReport;
import domain.op.OpReportExample;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class OpReportService extends OpBaseMapper {

    public boolean idDuplicate(Integer id, String code){

        OpReportExample example = new OpReportExample();
        OpReportExample.Criteria criteria = example.createCriteria();
        if(id!=null) criteria.andIdNotEqualTo(id);

        return opReportMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(OpReport record){

        //Assert.isTrue(!idDuplicate(null, null), "duplicate");
        //record.setSortOrder(getNextSortOrder("op_report", null));
        opReportMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        opReportMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        OpReportExample example = new OpReportExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        opReportMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(OpReport record){
        if(StringUtils.isNotBlank(null))
            Assert.isTrue(!idDuplicate(record.getId(),null), "duplicate");
        opReportMapper.updateByPrimaryKeySelective(record);
    }

    public Map<Integer, OpReport> findAll() {

        OpReportExample example = new OpReportExample();
        example.createCriteria();
        example.setOrderByClause("sort_order desc");
        List<OpReport> records = opReportMapper.selectByExample(example);
        Map<Integer, OpReport> map = new LinkedHashMap<>();
        for (OpReport record : records) {
            map.put(record.getId(), record);
        }

        return map;
    }
}
