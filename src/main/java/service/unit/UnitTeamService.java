package service.unit;

import domain.unit.UnitTeam;
import domain.unit.UnitTeamExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import sys.utils.DateUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class UnitTeamService extends BaseMapper {

    // 设置当前现任班子
    private void resetPresentTeam(int unitId) {

        // 去掉以前设置的现任班子状态
        UnitTeam _record = new UnitTeam();
        _record.setIsPresent(false);
        UnitTeamExample _example = new UnitTeamExample();
        _example.createCriteria().andUnitIdEqualTo(unitId).andIsPresentEqualTo(true);
        unitTeamMapper.updateByExampleSelective(_record, _example);
        
        //设置现任班子
        UnitTeamExample example = new UnitTeamExample();
        example.createCriteria().andUnitIdEqualTo(unitId);
        example.setOrderByClause("year desc");
        List<UnitTeam> unitTeams = unitTeamMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        if(unitTeams.size()==1){
            UnitTeam unitTeam = unitTeams.get(0);
            // 同时满足两个条件才能是现任班子。1 届数最新  2  没有免职。不满足这两条都不是。
            if(unitTeam.getDeposeDispatchCadreId()==null
                    && DateUtils.between(new Date(), unitTeam.getAppointDate(), unitTeam.getDeposeDate())){
                UnitTeam record = new UnitTeam();
                record.setId(unitTeam.getId());
                record.setIsPresent(true);
                
                unitTeamMapper.updateByPrimaryKeySelective(record);
            }
        }
    }
    
    @Transactional
    public void insertSelective(UnitTeam record){

        record.setSortOrder(getNextSortOrder("unit_team", null));
        unitTeamMapper.insertSelective(record);
        
        resetPresentTeam(record.getUnitId());
    }
    @Transactional
    public void del(Integer id){

        unitTeamMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        UnitTeamExample example = new UnitTeamExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        unitTeamMapper.deleteByExample(example);
    }

    @Transactional
    public void updateByPrimaryKeySelective(UnitTeam record){

        unitTeamMapper.updateByPrimaryKeySelective(record);
        resetPresentTeam(record.getUnitId());
    }

   /* @Cacheable(value="UnitTeam:ALL")
    public Map<Integer, UnitTeam> findAll() {

        UnitTeamExample example = new UnitTeamExample();
        example.setOrderByClause("sort_order desc");
        List<UnitTeam> unitTeames = unitTeamMapper.selectByExample(example);
        Map<Integer, UnitTeam> map = new LinkedHashMap<>();
        for (UnitTeam unitTeam : unitTeames) {
            map.put(unitTeam.getId(), unitTeam);
        }

        return map;
    }*/
    
}
