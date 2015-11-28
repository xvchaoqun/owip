package service.dispatch;

import domain.DispatchUnit;
import domain.DispatchUnitExample;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import service.BaseMapper;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class DispatchUnitService extends BaseMapper {

    /*public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code));

        DispatchUnitExample example = new DispatchUnitExample();
        DispatchUnitExample.Criteria criteria = example.createCriteria().andCodeEqualTo(code).andStatusEqualTo(true);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return dispatchUnitMapper.countByExample(example) > 0;
    }*/

    @Transactional
    @CacheEvict(value="DispatchUnit:ALL", allEntries = true)
    public int insertSelective(DispatchUnit record){

        dispatchUnitMapper.insertSelective(record);

        Integer id = record.getId();
        DispatchUnit _record = new DispatchUnit();
        _record.setId(id);
        _record.setSortOrder(id);
        return dispatchUnitMapper.updateByPrimaryKeySelective(_record);
    }
    @Transactional
    @CacheEvict(value="DispatchUnit:ALL", allEntries = true)
    public void del(Integer id){

        dispatchUnitMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @CacheEvict(value="DispatchUnit:ALL", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        DispatchUnitExample example = new DispatchUnitExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        dispatchUnitMapper.deleteByExample(example);
    }

    @Transactional
    @CacheEvict(value="DispatchUnit:ALL", allEntries = true)
    public int updateByPrimaryKeySelective(DispatchUnit record){
        return dispatchUnitMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="DispatchUnit:ALL")
    public Map<Integer, DispatchUnit> findAll() {

        DispatchUnitExample example = new DispatchUnitExample();
        example.setOrderByClause("sort_order desc");
        List<DispatchUnit> dispatchUnites = dispatchUnitMapper.selectByExample(example);
        Map<Integer, DispatchUnit> map = new LinkedHashMap<>();
        for (DispatchUnit dispatchUnit : dispatchUnites) {
            map.put(dispatchUnit.getId(), dispatchUnit);
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
    @CacheEvict(value = "DispatchUnit:ALL", allEntries = true)
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        DispatchUnit entity = dispatchUnitMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        DispatchUnitExample example = new DispatchUnitExample();
        if (addNum > 0) {

            example.createCriteria().andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<DispatchUnit> overEntities = dispatchUnitMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            DispatchUnit targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum > 0)
                commonMapper.downOrder("base_dispatch_unit", baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("base_dispatch_unit", baseSortOrder, targetEntity.getSortOrder());

            DispatchUnit record = new DispatchUnit();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            dispatchUnitMapper.updateByPrimaryKeySelective(record);
        }
    }
}
