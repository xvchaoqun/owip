package service.unit;

import domain.unit.UnitCadreTransferGroup;
import domain.unit.UnitCadreTransferGroupExample;
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
public class UnitCadreTransferGroupService extends BaseMapper {

    public boolean idDuplicate(Integer id, int unitId, String name){

        UnitCadreTransferGroupExample example = new UnitCadreTransferGroupExample();
        UnitCadreTransferGroupExample.Criteria criteria = example.createCriteria()
                .andUnitIdEqualTo(unitId).andNameEqualTo(name);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return unitCadreTransferGroupMapper.countByExample(example) > 0;
    }

    @Transactional
    @CacheEvict(value="UnitCadreTransferGroup:ALL", allEntries = true)
    public int insertSelective(UnitCadreTransferGroup record){

        record.setSortOrder(getNextSortOrder("unit_cadre_transfer_group", "1=1"));
        return unitCadreTransferGroupMapper.insertSelective(record);
    }
    @Transactional
    @CacheEvict(value="UnitCadreTransferGroup:ALL", allEntries = true)
    public void del(Integer id){

        unitCadreTransferGroupMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @CacheEvict(value="UnitCadreTransferGroup:ALL", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        UnitCadreTransferGroupExample example = new UnitCadreTransferGroupExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        unitCadreTransferGroupMapper.deleteByExample(example);
    }

    @Transactional
    @CacheEvict(value="UnitCadreTransferGroup:ALL", allEntries = true)
    public int updateByPrimaryKeySelective(UnitCadreTransferGroup record){
        return unitCadreTransferGroupMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="UnitCadreTransferGroup:ALL")
    public Map<Integer, UnitCadreTransferGroup> findAll() {

        UnitCadreTransferGroupExample example = new UnitCadreTransferGroupExample();
        example.setOrderByClause("sort_order desc");
        List<UnitCadreTransferGroup> unitCadreTransferGroupes = unitCadreTransferGroupMapper.selectByExample(example);
        Map<Integer, UnitCadreTransferGroup> map = new LinkedHashMap<>();
        for (UnitCadreTransferGroup unitCadreTransferGroup : unitCadreTransferGroupes) {
            map.put(unitCadreTransferGroup.getId(), unitCadreTransferGroup);
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
    @CacheEvict(value = "UnitCadreTransferGroup:ALL", allEntries = true)
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        UnitCadreTransferGroup entity = unitCadreTransferGroupMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        UnitCadreTransferGroupExample example = new UnitCadreTransferGroupExample();
        if (addNum > 0) {

            example.createCriteria().andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<UnitCadreTransferGroup> overEntities = unitCadreTransferGroupMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            UnitCadreTransferGroup targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum > 0)
                commonMapper.downOrder("unit_cadre_transfer_group",null, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("unit_cadre_transfer_group",null, baseSortOrder, targetEntity.getSortOrder());

            UnitCadreTransferGroup record = new UnitCadreTransferGroup();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            unitCadreTransferGroupMapper.updateByPrimaryKeySelective(record);
        }
    }
}
