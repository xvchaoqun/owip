package service.op;

import domain.op.OpAttatch;
import domain.op.OpAttatchExample;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;

@Service
public class OpAttatchService extends OpBaseMapper {

    public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code), "null");

        OpAttatchExample example = new OpAttatchExample();
        OpAttatchExample.Criteria criteria = example.createCriteria();
        if(id!=null) criteria.andIdNotEqualTo(id);

        return opAttatchMapper.countByExample(example) > 0;
    }

    @Transactional
    public List<OpAttatch> getByRecordId(Integer recordId){

        OpAttatchExample example = new OpAttatchExample();
        example.createCriteria().andRecordIdEqualTo(recordId);
        List<OpAttatch> opAttatchs = opAttatchMapper.selectByExample(example);

        return opAttatchs;
    }

    @Transactional
    public void insertSelective(OpAttatch record){

        Assert.isTrue(!idDuplicate(null, String.valueOf(record.getRecordId())), "duplicate");
        //record.setSortOrder(getNextSortOrder("op_attatch", null));
        opAttatchMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        opAttatchMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        OpAttatchExample example = new OpAttatchExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        opAttatchMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(OpAttatch record){
        if(StringUtils.isNotBlank(String.valueOf(record.getRecordId())))
            Assert.isTrue(!idDuplicate(record.getId(), String.valueOf(record.getRecordId())), "duplicate");
        opAttatchMapper.updateByPrimaryKeySelective(record);
    }

    public Map<Integer, OpAttatch> findAll() {

        OpAttatchExample example = new OpAttatchExample();
        example.createCriteria();
        example.setOrderByClause("sort_order desc");
        List<OpAttatch> records = opAttatchMapper.selectByExample(example);
        Map<Integer, OpAttatch> map = new LinkedHashMap<>();
        for (OpAttatch record : records) {
            map.put(record.getId(), record);
        }

        return map;
    }

    @Transactional
    public List<Integer> getIdByRecordId(Integer[] recordIds){

        List<Integer> opAttatchIds = new ArrayList<>();
        OpAttatchExample example = new OpAttatchExample();
        example.createCriteria().andRecordIdIn(Arrays.asList(recordIds));
        List<OpAttatch> opAttatches = opAttatchMapper.selectByExample(example);
        for (OpAttatch opAttatch : opAttatches){
            opAttatchIds.add(opAttatch.getId());
        }

        return opAttatchIds;
    }
}
