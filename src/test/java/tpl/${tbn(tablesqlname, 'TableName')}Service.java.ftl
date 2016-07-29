<#assign TableName=tbn(tablesqlname, "TableName")>
<#assign tableName=tbn(tablesqlname, "tableName")>
<#assign tablename=tbn(tablesqlname, "tablename")>
package service.${folder};

import domain.${folder}.${TableName};
import domain.${folder}.${TableName}Example;
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
public class ${TableName}Service extends BaseMapper {

    public boolean idDuplicate(Integer ${tbn(key, "tableName")}, String code){

        Assert.isTrue(StringUtils.isNotBlank(code));

        ${TableName}Example example = new ${TableName}Example();
        ${TableName}Example.Criteria criteria = example.createCriteria().andCodeEqualTo(code).andStatusEqualTo(true);
        if(${tbn(key, "tableName")}!=null) criteria.and${tbn(key, "TableName")}NotEqualTo(${tbn(key, "tableName")});

        return ${tableName}Mapper.countByExample(example) > 0;
    }

    @Transactional
    @CacheEvict(value="${TableName}:ALL", allEntries = true)
    public int insertSelective(${TableName} record){

        Assert.isTrue(!idDuplicate(null, record.getCode()));
        ${tableName}Mapper.insertSelective(record);

        Integer id = record.getId();
        ${TableName} _record = new ${TableName}();
        _record.setId(id);
        _record.setSortOrder(id);
        return ${tableName}Mapper.updateByPrimaryKeySelective(_record);
    }
    @Transactional
    @CacheEvict(value="${TableName}:ALL", allEntries = true)
    public void del(Integer ${tbn(key, "tableName")}){

        ${tableName}Mapper.deleteByPrimaryKey(${tbn(key, "tableName")});
    }

    @Transactional
    @CacheEvict(value="${TableName}:ALL", allEntries = true)
    public void batchDel(Integer[] ${tbn(key, "tableName")}s){

        if(ids==null || ids.length==0) return;

        ${TableName}Example example = new ${TableName}Example();
        example.createCriteria().and${tbn(key, "TableName")}In(Arrays.asList(${tbn(key, "tableName")}s));
        ${tableName}Mapper.deleteByExample(example);
    }

    @Transactional
    @CacheEvict(value="${TableName}:ALL", allEntries = true)
    public int updateByPrimaryKeySelective(${TableName} record){
        if(StringUtils.isNotBlank(record.getCode()))
            Assert.isTrue(!idDuplicate(record.getId(), record.getCode()));
        return ${tableName}Mapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="${TableName}:ALL")
    public Map<Integer, ${TableName}> findAll() {

        ${TableName}Example example = new ${TableName}Example();
        example.createCriteria().andStatusEqualTo(true);
        example.setOrderByClause("sort_order desc");
        List<${TableName}> ${tableName}es = ${tableName}Mapper.selectByExample(example);
        Map<Integer, ${TableName}> map = new LinkedHashMap<>();
        for (${TableName} ${tableName} : ${tableName}es) {
            map.put(${tableName}.getId(), ${tableName});
        }

        return map;
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    @CacheEvict(value = "${TableName}:ALL", allEntries = true)
    public void changeOrder(int ${tbn(key, "tableName")}, int addNum) {

        if(addNum == 0) return ;

        ${TableName} entity = ${tableName}Mapper.selectByPrimaryKey(${tbn(key, "tableName")});
        Integer baseSortOrder = entity.getSortOrder();

        ${TableName}Example example = new ${TableName}Example();
        if (addNum > 0) {

            example.createCriteria().andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<${TableName}> overEntities = ${tableName}Mapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            ${TableName} targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum > 0)
                commonMapper.downOrder("${tablePrefix}${tablesqlname}", baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("${tablePrefix}${tablesqlname}", baseSortOrder, targetEntity.getSortOrder());

            ${TableName} record = new ${TableName}();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            ${tableName}Mapper.updateByPrimaryKeySelective(record);
        }
    }
}
