package service.op;

import domain.op.OpRecord;
import domain.op.OpRecordExample;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class OpRecordService extends OpBaseMapper {

    public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code), "null");

        OpRecordExample example = new OpRecordExample();
        OpRecordExample.Criteria criteria = example.createCriteria();
        if(id!=null) criteria.andIdNotEqualTo(id);

        return opRecordMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(OpRecord record){

        //Assert.isTrue(!idDuplicate(null, null), "duplicate");
        //record.setSortOrder(getNextSortOrder("op_record", null));
        opRecordMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        opRecordMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        OpRecordExample example = new OpRecordExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        opRecordMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(OpRecord record){
        if(StringUtils.isNotBlank(null))
            Assert.isTrue(!idDuplicate(record.getId(), null), "duplicate");
        opRecordMapper.updateByPrimaryKeySelective(record);
    }

    public Map<Integer, OpRecord> findAll() {

        OpRecordExample example = new OpRecordExample();
        example.createCriteria();
        example.setOrderByClause("sort_order desc");
        List<OpRecord> records = opRecordMapper.selectByExample(example);
        Map<Integer, OpRecord> map = new LinkedHashMap<>();
        for (OpRecord record : records) {
            map.put(record.getId(), record);
        }

        return map;
    }
}
