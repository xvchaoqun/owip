package service.cg;

import domain.cg.CgRule;
import domain.cg.CgRuleExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CgRuleService extends CgBaseMapper {

    public boolean idDuplicate(Integer id, byte type, boolean isCurrent, Integer teamId){

        if(!isCurrent) return false;

        CgRuleExample example = new CgRuleExample();
        CgRuleExample.Criteria criteria = example.createCriteria().andTypeEqualTo(type)
                .andIsCurrentEqualTo(isCurrent).andTeamIdEqualTo(teamId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return cgRuleMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(CgRule record){

        Assert.isTrue(!idDuplicate(null, record.getType(), record.getIsCurrent(), record.getTeamId()), "duplicate");
        record.setSortOrder(getNextSortOrder("cg_rule",
                String.format("team_id=%s and is_current=%s",record.getTeamId(),record.getIsCurrent())));
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
           // Assert.isTrue(!idDuplicate(record.getId(), record.getType(), record.getIsCurrent()), "duplicate");
        cgRuleMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    public void changeOrder(int id, int addNum) {

        changeOrder("cg_rule", null, ORDER_BY_DESC, id, addNum);
    }

    @Transactional
    public void updateCgRuleStatus(Integer[] ids, boolean isCurrent){

        if(ids==null || ids.length==0) return;

        for (Integer id : ids){

            CgRule record = cgRuleMapper.selectByPrimaryKey(id);
            record.setIsCurrent(isCurrent);
            record.setSortOrder(getNextSortOrder("cg_rule",
                    String.format("team_id=%s and is_current=%s",record.getTeamId(), record.getIsCurrent())));
            cgRuleMapper.updateByPrimaryKeySelective(record);
        }
    }

    public List<Byte> getTypebyTeamId(Integer teamId){

        CgRuleExample cgRuleExample = new CgRuleExample();
        cgRuleExample.createCriteria().andIsCurrentEqualTo(true).andTeamIdEqualTo(teamId);
        List<CgRule> cgRules = cgRuleMapper.selectByExample(cgRuleExample);
        List<Byte> types = new ArrayList<>();

        for (CgRule cgRule : cgRules){
            types.add(cgRule.getType());
        }
        return types;
    }
}
