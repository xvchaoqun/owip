package service;

import domain.Unit;
import domain.UnitExample;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;

@Service
public class UnitService extends BaseMapper {


    public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code));

        UnitExample example = new UnitExample();
        UnitExample.Criteria criteria = example.createCriteria().andCodeEqualTo(code)/*.andStatusEqualTo(SystemConstants.UNIT_STATUS_RUN)*/;
        if(id!=null) criteria.andIdNotEqualTo(id);

        return unitMapper.countByExample(example) > 0;
    }

    @Transactional
    @CacheEvict(value="Unit:ALL", allEntries = true)
    public int insertSelective(Unit record){

        unitMapper.insertSelective(record);

        Integer id = record.getId();
        Unit _record = new Unit();
        _record.setId(id);
        _record.setSortOrder(id);
        return unitMapper.updateByPrimaryKeySelective(_record);
    }
    @Transactional
    @CacheEvict(value="Unit:ALL", allEntries = true)
    public void del(Integer id){

        unitMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @CacheEvict(value="Unit:ALL", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        UnitExample example = new UnitExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        unitMapper.deleteByExample(example);
    }

    @Transactional
    @CacheEvict(value="Unit:ALL", allEntries = true)
    public int updateByPrimaryKeySelective(Unit record){

        return unitMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="Unit:ALL")
    public Map<Integer, Unit> findAll() {

        UnitExample example = new UnitExample();
        example.createCriteria()/*.andStatusEqualTo(SystemConstants.UNIT_STATUS_RUN)*/;
        example.setOrderByClause("sort_order desc");
        List<Unit> unites = unitMapper.selectByExample(example);
        Map<Integer, Unit> map = new LinkedHashMap<>();
        for (Unit unit : unites) {
            map.put(unit.getId(), unit);
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
    @CacheEvict(value = "Unit:ALL", allEntries = true)
    public void changeOrder(int id, byte status, int addNum) {

        if(addNum == 0) return ;

        Unit entity = unitMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        UnitExample example = new UnitExample();
        if (addNum > 0) {

            example.createCriteria().andStatusEqualTo(status).andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andStatusEqualTo(status).andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<Unit> overEntities = unitMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            Unit targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum > 0)
                commonMapper.downOrder_unit(status, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder_unit(status, baseSortOrder, targetEntity.getSortOrder());

            Unit record = new Unit();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            unitMapper.updateByPrimaryKeySelective(record);
        }
    }
}
