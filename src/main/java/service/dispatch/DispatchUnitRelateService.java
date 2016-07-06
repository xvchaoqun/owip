package service.dispatch;

import domain.dispatch.DispatchUnitRelate;
import domain.dispatch.DispatchUnitRelateExample;
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
public class DispatchUnitRelateService extends BaseMapper {

    public boolean idDuplicate(Integer id, int dispatchUnitId, int unitId){

        DispatchUnitRelateExample example = new DispatchUnitRelateExample();
        DispatchUnitRelateExample.Criteria criteria = example.createCriteria().andUnitIdEqualTo(unitId).
                andDispatchUnitIdEqualTo(dispatchUnitId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return dispatchUnitRelateMapper.countByExample(example) > 0;
    }

    @Transactional
    @CacheEvict(value="DispatchUnitRelate:ALL", allEntries = true)
    public int insertSelective(DispatchUnitRelate record){

        dispatchUnitRelateMapper.insertSelective(record);

        Integer id = record.getId();
        DispatchUnitRelate _record = new DispatchUnitRelate();
        _record.setId(id);
        _record.setSortOrder(id);
        return dispatchUnitRelateMapper.updateByPrimaryKeySelective(_record);
    }
    @Transactional
    @CacheEvict(value="DispatchUnitRelate:ALL", allEntries = true)
    public void del(Integer id){

        dispatchUnitRelateMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @CacheEvict(value="DispatchUnitRelate:ALL", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        DispatchUnitRelateExample example = new DispatchUnitRelateExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        dispatchUnitRelateMapper.deleteByExample(example);
    }

    @Transactional
    @CacheEvict(value="DispatchUnitRelate:ALL", allEntries = true)
    public int updateByPrimaryKeySelective(DispatchUnitRelate record){
        return dispatchUnitRelateMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="DispatchUnitRelate:ALL")
    public Map<Integer, DispatchUnitRelate> findAll() {

        DispatchUnitRelateExample example = new DispatchUnitRelateExample();
        example.setOrderByClause("sort_order desc");
        List<DispatchUnitRelate> dispatchUnitRelatees = dispatchUnitRelateMapper.selectByExample(example);
        Map<Integer, DispatchUnitRelate> map = new LinkedHashMap<>();
        for (DispatchUnitRelate dispatchUnitRelate : dispatchUnitRelatees) {
            map.put(dispatchUnitRelate.getId(), dispatchUnitRelate);
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
    @CacheEvict(value = "DispatchUnitRelate:ALL", allEntries = true)
    public void changeOrder(int id, int dispatchUnitId, int addNum) {

        if(addNum == 0) return ;

        DispatchUnitRelate entity = dispatchUnitRelateMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        DispatchUnitRelateExample example = new DispatchUnitRelateExample();
        DispatchUnitRelateExample.Criteria criteria = example.createCriteria().andDispatchUnitIdEqualTo(dispatchUnitId);
        if (addNum > 0) {

            criteria.andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            criteria.andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<DispatchUnitRelate> overEntities = dispatchUnitRelateMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            DispatchUnitRelate targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum > 0)
                commonMapper.downOrder_dispatchUnit(dispatchUnitId, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder_dispatchUnit(dispatchUnitId, baseSortOrder, targetEntity.getSortOrder());

            DispatchUnitRelate record = new DispatchUnitRelate();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            dispatchUnitRelateMapper.updateByPrimaryKeySelective(record);
        }
    }
}
