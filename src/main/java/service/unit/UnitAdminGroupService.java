package service.unit;

import domain.unit.UnitAdminGroup;
import domain.unit.UnitAdminGroupExample;
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
public class UnitAdminGroupService extends BaseMapper {

    private void resetPresentParty(int unitId) {

        // 去掉以前设置的现任班子状态
        UnitAdminGroup _record = new UnitAdminGroup();
        _record.setIsPresent(false);
        UnitAdminGroupExample _example = new UnitAdminGroupExample();
        _example.createCriteria().andUnitIdEqualTo(unitId);
        unitAdminGroupMapper.updateByExampleSelective(_record, _example);
    }
    @Transactional
    @CacheEvict(value="UnitAdminGroup:ALL", allEntries = true)
    public int insertSelective(UnitAdminGroup record){

        if (record.getIsPresent()) {
            resetPresentParty(record.getUnitId());
        }

        record.setSortOrder(getNextSortOrder("unit_admin_group", "1=1"));
        return unitAdminGroupMapper.insertSelective(record);
    }
    @Transactional
    @CacheEvict(value="UnitAdminGroup:ALL", allEntries = true)
    public void del(Integer id){

        unitAdminGroupMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @CacheEvict(value="UnitAdminGroup:ALL", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        UnitAdminGroupExample example = new UnitAdminGroupExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        unitAdminGroupMapper.deleteByExample(example);
    }

    @Transactional
    @CacheEvict(value="UnitAdminGroup:ALL", allEntries = true)
    public int updateByPrimaryKeySelective(UnitAdminGroup record){

        if (record.getIsPresent()) {
            resetPresentParty(record.getUnitId());
        }
        return unitAdminGroupMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="UnitAdminGroup:ALL")
    public Map<Integer, UnitAdminGroup> findAll() {

        UnitAdminGroupExample example = new UnitAdminGroupExample();
        example.setOrderByClause("sort_order desc");
        List<UnitAdminGroup> unitAdminGroupes = unitAdminGroupMapper.selectByExample(example);
        Map<Integer, UnitAdminGroup> map = new LinkedHashMap<>();
        for (UnitAdminGroup unitAdminGroup : unitAdminGroupes) {
            map.put(unitAdminGroup.getId(), unitAdminGroup);
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
    @CacheEvict(value = "UnitAdminGroup:ALL", allEntries = true)
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        UnitAdminGroup entity = unitAdminGroupMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        UnitAdminGroupExample example = new UnitAdminGroupExample();
        if (addNum > 0) {

            example.createCriteria().andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<UnitAdminGroup> overEntities = unitAdminGroupMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            UnitAdminGroup targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum > 0)
                commonMapper.downOrder("unit_admin_group", null, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("unit_admin_group", null, baseSortOrder, targetEntity.getSortOrder());

            UnitAdminGroup record = new UnitAdminGroup();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            unitAdminGroupMapper.updateByPrimaryKeySelective(record);
        }
    }
}
