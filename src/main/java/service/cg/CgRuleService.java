package service.cg;

import domain.cg.CgRule;
import domain.cg.CgRuleExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Arrays;

@Service
public class CgRuleService extends CgBaseMapper {

    public boolean idDuplicate(Integer id, byte type, boolean isCurrent){

        if(!isCurrent) return false;

        CgRuleExample example = new CgRuleExample();
        CgRuleExample.Criteria criteria = example.createCriteria().andTypeEqualTo(type)
                .andIsCurrentEqualTo(isCurrent);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return cgRuleMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(CgRule record){

        Assert.isTrue(!idDuplicate(null, record.getType(), record.getIsCurrent()), "duplicate");
        record.setSortOrder(getNextSortOrder("cg_rule", null));
        cgRuleMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        cgRuleMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CgRuleExample example = new CgRuleExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cgRuleMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(CgRule record){
            Assert.isTrue(!idDuplicate(record.getId(), record.getType(), record.getIsCurrent()), "duplicate");
        cgRuleMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    public void changeOrder(int id, int addNum) {

        changeOrder("cg_rule", null, ORDER_BY_ASC, id, addNum);
    }
}
