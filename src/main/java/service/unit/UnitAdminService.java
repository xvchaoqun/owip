package service.unit;

import domain.unit.UnitAdmin;
import domain.unit.UnitAdminExample;
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
public class UnitAdminService extends BaseMapper {


    @Transactional
    @CacheEvict(value="UnitAdmin:ALL", allEntries = true)
    public int insertSelective(UnitAdmin record){

        record.setSortOrder(getNextSortOrder("unit_admin", "group_id=" + record.getGroupId()));
        return unitAdminMapper.insertSelective(record);
    }
    @Transactional
    @CacheEvict(value="UnitAdmin:ALL", allEntries = true)
    public void del(Integer id){

        unitAdminMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @CacheEvict(value="UnitAdmin:ALL", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        UnitAdminExample example = new UnitAdminExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        unitAdminMapper.deleteByExample(example);
    }

    @Transactional
    @CacheEvict(value="UnitAdmin:ALL", allEntries = true)
    public int updateByPrimaryKeySelective(UnitAdmin record){
        return unitAdminMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="UnitAdmin:ALL")
    public Map<Integer, UnitAdmin> findAll() {

        UnitAdminExample example = new UnitAdminExample();
        example.setOrderByClause("sort_order desc");
        List<UnitAdmin> unitAdmines = unitAdminMapper.selectByExample(example);
        Map<Integer, UnitAdmin> map = new LinkedHashMap<>();
        for (UnitAdmin unitAdmin : unitAdmines) {
            map.put(unitAdmin.getId(), unitAdmin);
        }

        return map;
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    @CacheEvict(value = "UnitAdmin:ALL", allEntries = true)
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        UnitAdmin entity = unitAdminMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();
        int groupId = entity.getGroupId();

        UnitAdminExample example = new UnitAdminExample();
        if (addNum > 0) {

            example.createCriteria().andGroupIdEqualTo(groupId).andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andGroupIdEqualTo(groupId).andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<UnitAdmin> overEntities = unitAdminMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            UnitAdmin targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum > 0)
                commonMapper.downOrder("unit_admin", "group_id=" + groupId, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("unit_admin", "group_id=" + groupId, baseSortOrder, targetEntity.getSortOrder());

            UnitAdmin record = new UnitAdmin();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            unitAdminMapper.updateByPrimaryKeySelective(record);
        }
    }
}
