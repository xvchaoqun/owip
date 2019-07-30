package service.sys;

import domain.sys.SysProperty;
import domain.sys.SysPropertyExample;
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
public class SysPropertyService extends BaseMapper {

    public boolean idDuplicate(Integer id, String code){

        Assert.isTrue(StringUtils.isNotBlank(code), "null");

        SysPropertyExample example = new SysPropertyExample();
        SysPropertyExample.Criteria criteria = example.createCriteria().andCodeEqualTo(code);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return sysPropertyMapper.countByExample(example) > 0;
    }

    @Transactional
    @CacheEvict(value="SysProperty:ALL", allEntries = true)
    public void insertSelective(SysProperty record){

        Assert.isTrue(!idDuplicate(null, record.getCode()), "duplicate");
        record.setSortOrder(getNextSortOrder("sys_property", null));
        sysPropertyMapper.insertSelective(record);
    }

    @Transactional
    @CacheEvict(value="SysProperty:ALL", allEntries = true)
    public void del(Integer id){

        sysPropertyMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @CacheEvict(value="SysProperty:ALL", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        SysPropertyExample example = new SysPropertyExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        sysPropertyMapper.deleteByExample(example);
    }

    @Transactional
    @CacheEvict(value="SysProperty:ALL", allEntries = true)
    public int updateByPrimaryKeySelective(SysProperty record){
        if(StringUtils.isNotBlank(record.getCode()))
            Assert.isTrue(!idDuplicate(record.getId(), record.getCode()), "duplicate");
        return sysPropertyMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="SysProperty:ALL")
    public Map<String, String> findAll() {

        SysPropertyExample example = new SysPropertyExample();
        example.createCriteria();
        example.setOrderByClause("sort_order desc");
        List<SysProperty> sysPropertyes = sysPropertyMapper.selectByExample(example);
        Map<String, String> map = new LinkedHashMap<>();
        for (SysProperty sysProperty : sysPropertyes) {
            map.put(sysProperty.getCode(), sysProperty.getContent());
        }

        return map;
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    @CacheEvict(value = "SysProperty:ALL", allEntries = true)
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        byte orderBy = ORDER_BY_DESC;

        SysProperty entity = sysPropertyMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        String tableName = "sys_property";
        String whereSql = null;
        adjustSortOrder(tableName, whereSql);
        if(baseSortOrder==null) return;

        SysPropertyExample example = new SysPropertyExample();
        if (addNum*orderBy > 0) {

            example.createCriteria().andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<SysProperty> overEntities = sysPropertyMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            SysProperty targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum*orderBy > 0)
                commonMapper.downOrder(tableName, whereSql, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder(tableName, whereSql, baseSortOrder, targetEntity.getSortOrder());

            SysProperty record = new SysProperty();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            sysPropertyMapper.updateByPrimaryKeySelective(record);
        }
    }
}
