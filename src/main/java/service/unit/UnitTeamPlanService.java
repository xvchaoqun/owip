package service.unit;

import domain.unit.UnitTeamPlan;
import domain.unit.UnitTeamPlanExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;

@Service
public class UnitTeamPlanService extends BaseMapper {

    /*public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code), "null");

        UnitTeamPlanExample example = new UnitTeamPlanExample();
        UnitTeamPlanExample.Criteria criteria = example.createCriteria().andCodeEqualTo(code).andStatusEqualTo(true);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return unitTeamPlanMapper.countByExample(example) > 0;
    }*/

    @Transactional
    public void insertSelective(UnitTeamPlan record){

        unitTeamPlanMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        unitTeamPlanMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        UnitTeamPlanExample example = new UnitTeamPlanExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        unitTeamPlanMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(UnitTeamPlan record){
        return unitTeamPlanMapper.updateByPrimaryKeySelective(record);
    }

  
}
