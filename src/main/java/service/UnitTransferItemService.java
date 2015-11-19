package service;

import domain.UnitTransferItem;
import domain.UnitTransferItemExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class UnitTransferItemService extends BaseMapper {

    public boolean idDuplicate(Integer id, int transferId, int dispatchUnitid){

        UnitTransferItemExample example = new UnitTransferItemExample();
        UnitTransferItemExample.Criteria criteria = example.createCriteria()
                .andTransferIdEqualTo(transferId).andDispatchUnitIdEqualTo(dispatchUnitid);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return unitTransferItemMapper.countByExample(example) > 0;
    }

    @Transactional
    @CacheEvict(value="UnitTransferItem:ALL", allEntries = true)
    public int insertSelective(UnitTransferItem record){

        unitTransferItemMapper.insertSelective(record);

        Integer id = record.getId();
        UnitTransferItem _record = new UnitTransferItem();
        _record.setId(id);
        _record.setSortOrder(id);
        return unitTransferItemMapper.updateByPrimaryKeySelective(_record);
    }
    @Transactional
    @CacheEvict(value="UnitTransferItem:ALL", allEntries = true)
    public void del(Integer id){

        unitTransferItemMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @CacheEvict(value="UnitTransferItem:ALL", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        UnitTransferItemExample example = new UnitTransferItemExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        unitTransferItemMapper.deleteByExample(example);
    }

    @Transactional
    @CacheEvict(value="UnitTransferItem:ALL", allEntries = true)
    public int updateByPrimaryKeySelective(UnitTransferItem record){
        return unitTransferItemMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="UnitTransferItem:ALL")
    public Map<Integer, UnitTransferItem> findAll() {

        UnitTransferItemExample example = new UnitTransferItemExample();
        example.setOrderByClause("sort_order desc");
        List<UnitTransferItem> unitTransferItemes = unitTransferItemMapper.selectByExample(example);
        Map<Integer, UnitTransferItem> map = new LinkedHashMap<>();
        for (UnitTransferItem unitTransferItem : unitTransferItemes) {
            map.put(unitTransferItem.getId(), unitTransferItem);
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
    @CacheEvict(value = "UnitTransferItem:ALL", allEntries = true)
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        UnitTransferItem entity = unitTransferItemMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        UnitTransferItemExample example = new UnitTransferItemExample();
        if (addNum > 0) {

            example.createCriteria().andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<UnitTransferItem> overEntities = unitTransferItemMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            UnitTransferItem targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum > 0)
                commonMapper.downOrder("base_unitTransfer_item", baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("base_unitTransfer_item", baseSortOrder, targetEntity.getSortOrder());

            UnitTransferItem record = new UnitTransferItem();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            unitTransferItemMapper.updateByPrimaryKeySelective(record);
        }
    }
}
