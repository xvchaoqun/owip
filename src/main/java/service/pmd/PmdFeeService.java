package service.pmd;

import domain.pmd.PmdFee;
import domain.pmd.PmdFeeExample;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class PmdFeeService extends PmdBaseMapper {

    public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code), "null");

        PmdFeeExample example = new PmdFeeExample();
        PmdFeeExample.Criteria criteria = example.createCriteria();
        if(id!=null) criteria.andIdNotEqualTo(id);

        return pmdFeeMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(PmdFee record){

        pmdFeeMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        pmdFeeMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        PmdFeeExample example = new PmdFeeExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        pmdFeeMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(PmdFee record){
        if(StringUtils.isNotBlank(record.getRemark()))
            Assert.isTrue(!idDuplicate(record.getId(), record.getRemark()), "duplicate");
        pmdFeeMapper.updateByPrimaryKeySelective(record);
    }

    public Map<Integer, PmdFee> findAll() {

        PmdFeeExample example = new PmdFeeExample();
        example.createCriteria().andStatusEqualTo((byte)1);
        example.setOrderByClause("sort_order desc");
        List<PmdFee> records = pmdFeeMapper.selectByExample(example);
        Map<Integer, PmdFee> map = new LinkedHashMap<>();
        for (PmdFee record : records) {
            map.put(record.getId(), record);
        }

        return map;
    }
}
