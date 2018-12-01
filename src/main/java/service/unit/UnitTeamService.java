package service.unit;

import domain.unit.UnitTeam;
import domain.unit.UnitTeamExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class UnitTeamService extends BaseMapper {

    /*private void resetPresentParty(int unitId) {

        // 去掉以前设置的现任班子状态
        UnitTeam _record = new UnitTeam();
        _record.setIsPresent(false);
        UnitTeamExample _example = new UnitTeamExample();
        _example.createCriteria().andUnitIdEqualTo(unitId);
        unitTeamMapper.updateByExampleSelective(_record, _example);
    }*/
    @Transactional
    @CacheEvict(value="UnitTeam:ALL", allEntries = true)
    public int insertSelective(UnitTeam record){

        /*if (record.getIsPresent()) {
            resetPresentParty(record.getUnitId());
        }*/

        record.setSortOrder(getNextSortOrder("unit_team", null));
        return unitTeamMapper.insertSelective(record);
    }
    @Transactional
    @CacheEvict(value="UnitTeam:ALL", allEntries = true)
    public void del(Integer id){

        unitTeamMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @CacheEvict(value="UnitTeam:ALL", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        UnitTeamExample example = new UnitTeamExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        unitTeamMapper.deleteByExample(example);
    }

    @Transactional
    @CacheEvict(value="UnitTeam:ALL", allEntries = true)
    public int updateByPrimaryKeySelective(UnitTeam record){

        /*if (record.getIsPresent()) {
            resetPresentParty(record.getUnitId());
        }*/
        return unitTeamMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="UnitTeam:ALL")
    public Map<Integer, UnitTeam> findAll() {

        UnitTeamExample example = new UnitTeamExample();
        example.setOrderByClause("sort_order desc");
        List<UnitTeam> unitTeames = unitTeamMapper.selectByExample(example);
        Map<Integer, UnitTeam> map = new LinkedHashMap<>();
        for (UnitTeam unitTeam : unitTeames) {
            map.put(unitTeam.getId(), unitTeam);
        }

        return map;
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * 3.sort_order = LAST_INSERT_ID()+1,
     * @param id
     * @param addNum
     */
    @Transactional
    @CacheEvict(value = "UnitTeam:ALL", allEntries = true)
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        UnitTeam entity = unitTeamMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        UnitTeamExample example = new UnitTeamExample();
        if (addNum > 0) {

            example.createCriteria().andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<UnitTeam> overEntities = unitTeamMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            UnitTeam targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum > 0)
                commonMapper.downOrder("unit_team", null, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("unit_team", null, baseSortOrder, targetEntity.getSortOrder());

            UnitTeam record = new UnitTeam();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            unitTeamMapper.updateByPrimaryKeySelective(record);
        }
    }
}
