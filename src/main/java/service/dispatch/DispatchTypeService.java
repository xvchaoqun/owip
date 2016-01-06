package service.dispatch;

import domain.DispatchType;
import domain.DispatchTypeExample;
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
public class DispatchTypeService extends BaseMapper {

    public boolean idDuplicate(Integer id, String name){

        Assert.isTrue(StringUtils.isNotBlank(name));

        DispatchTypeExample example = new DispatchTypeExample();
        DispatchTypeExample.Criteria criteria = example.createCriteria().andNameEqualTo(name);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return dispatchTypeMapper.countByExample(example) > 0;
    }

    @Transactional
    @CacheEvict(value = "DispatchType:ALL", allEntries = true)
    public int insertSelective(DispatchType record){

        Assert.isTrue(!idDuplicate(null, record.getName()));
        dispatchTypeMapper.insertSelective(record);

        Integer id = record.getId();
        DispatchType _record = new DispatchType();
        _record.setId(id);
        _record.setSortOrder(id);
        return dispatchTypeMapper.updateByPrimaryKeySelective(_record);
    }
    @Transactional
    @CacheEvict(value = "DispatchType:ALL", allEntries = true)
    public void del(Integer id){

        dispatchTypeMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @CacheEvict(value = "DispatchType:ALL", allEntries = true)
    public void batchDel(Integer[] ids){
        if(ids==null || ids.length==0) return;
        DispatchTypeExample example = new DispatchTypeExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        dispatchTypeMapper.deleteByExample(example);
    }

    @Transactional
    @CacheEvict(value = "DispatchType:ALL", allEntries = true)
    public int updateByPrimaryKeySelective(DispatchType record){
        if(StringUtils.isNotBlank(record.getName()))
            Assert.isTrue(!idDuplicate(record.getId(), record.getName()));
        return dispatchTypeMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="DispatchType:ALL")
    public Map<Integer, DispatchType> findAll() {

        DispatchTypeExample example = new DispatchTypeExample();
        example.createCriteria();
        example.setOrderByClause("sort_order desc");
        List<DispatchType> dispatchTypees = dispatchTypeMapper.selectByExample(example);
        Map<Integer, DispatchType> map = new LinkedHashMap<>();
        for (DispatchType dispatchType : dispatchTypees) {
            map.put(dispatchType.getId(), dispatchType);
        }

        return map;
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    @CacheEvict(value = "DispatchType:ALL",allEntries = true)
    public void changeOrder(int id, int addNum, short year) {

        if(addNum == 0) return ;

        DispatchType entity = dispatchTypeMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        DispatchTypeExample example = new DispatchTypeExample();
        if (addNum > 0) {

            example.createCriteria().andYearEqualTo(year).andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andYearEqualTo(year).andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<DispatchType> overEntities = dispatchTypeMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            DispatchType targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum > 0)
                commonMapper.downOrder_dispatchType(year, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder_dispatchType(year, baseSortOrder, targetEntity.getSortOrder());

            DispatchType record = new DispatchType();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            dispatchTypeMapper.updateByPrimaryKeySelective(record);
        }
    }
}
