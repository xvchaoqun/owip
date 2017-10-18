package service.crs;

import domain.crs.CrsApplyRule;
import domain.crs.CrsApplyRuleExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;

@Service
public class CrsApplyRuleService extends BaseMapper {


    @Transactional
    public void insertSelective(CrsApplyRule record){

        crsApplyRuleMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        crsApplyRuleMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CrsApplyRuleExample example = new CrsApplyRuleExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        crsApplyRuleMapper.deleteByExample(example);
    }

    @Transactional

    public int updateByPrimaryKeySelective(CrsApplyRule record){

        return crsApplyRuleMapper.updateByPrimaryKeySelective(record);
    }
}
