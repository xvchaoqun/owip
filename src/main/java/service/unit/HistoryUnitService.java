package service.unit;

import domain.unit.HistoryUnit;
import domain.unit.HistoryUnitExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;
import java.util.List;

@Service
public class HistoryUnitService extends BaseMapper {

    public boolean idDuplicate(Integer id, Integer unitId, Integer oldUnitId){

        HistoryUnitExample example = new HistoryUnitExample();
        HistoryUnitExample.Criteria criteria = example.createCriteria().andUnitIdEqualTo(unitId)
                .andOldUnitIdEqualTo(oldUnitId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return historyUnitMapper.countByExample(example) > 0;
    }

    @Transactional
    @CacheEvict(value="HistoryUnit", key = "#record.unitId")
    public int insertSelective(HistoryUnit record){

        historyUnitMapper.insertSelective(record);

        Integer id = record.getId();
        HistoryUnit _record = new HistoryUnit();
        _record.setId(id);
        _record.setSortOrder(id);
        return historyUnitMapper.updateByPrimaryKeySelective(_record);
    }

    @Transactional
    @CacheEvict(value="HistoryUnit", key = "#unitId")
    public void del(Integer id, int unitId){

        historyUnitMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @CacheEvict(value="Leader:ALL", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        HistoryUnitExample example = new HistoryUnitExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        historyUnitMapper.deleteByExample(example);
    }
    @Transactional
    @Caching(evict= {
            @CacheEvict(value = "HistoryUnit", key = "#record.unitId"),
            @CacheEvict(value = "HistoryUnit", key = "#oldUnitId")
    })
    public int updateByPrimaryKeySelective(HistoryUnit record, int oldUnitId){

        return historyUnitMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="HistoryUnit", key = "#unitId")
    public List<HistoryUnit> findAll(Integer unitId) {

        HistoryUnitExample example = new HistoryUnitExample();
        example.createCriteria().andUnitIdEqualTo(unitId);
        example.setOrderByClause("sort_order desc");

        return historyUnitMapper.selectByExample(example);
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * 3.sort_order = LAST_INSERT_ID()+1,
     * @param id
     * @param addNum
     */
    @Transactional
    @CacheEvict(value = "HistoryUnit", key = "#unitId")
    public void changeOrder(int id, int unitId, int addNum) {

        if(addNum == 0) return ;

        HistoryUnit entity = historyUnitMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        HistoryUnitExample example = new HistoryUnitExample();
        HistoryUnitExample.Criteria criteria = example.createCriteria().andUnitIdEqualTo(unitId);
        if (addNum > 0) {

            criteria.andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            criteria.andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<HistoryUnit> overEntities = historyUnitMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            HistoryUnit targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum > 0)
                commonMapper.downOrder_historyUnit(unitId, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder_historyUnit(unitId, baseSortOrder, targetEntity.getSortOrder());

            HistoryUnit record = new HistoryUnit();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            historyUnitMapper.updateByPrimaryKeySelective(record);
        }
    }
}
