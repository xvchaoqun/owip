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
public class ${TableName}Service extends ${tbn(resFolder?trim, "TableName")}BaseMapper {

    public boolean idDuplicate(Integer ${tbn(key, "tableName")}, String code){

        Assert.isTrue(StringUtils.isNotBlank(code), "null");

        ${TableName}Example example = new ${TableName}Example();
        ${TableName}Example.Criteria criteria = example.createCriteria().andCodeEqualTo(code).andStatusEqualTo(true);
        if(${tbn(key, "tableName")}!=null) criteria.and${tbn(key, "TableName")}NotEqualTo(${tbn(key, "tableName")});

        return ${tableName}Mapper.countByExample(example) > 0;
    }

    @Transactional
    @CacheEvict(value="${TableName}:ALL", allEntries = true)
    public void insertSelective(${TableName} record){

        Assert.isTrue(!idDuplicate(null, record.getCode()), "duplicate");
        record.setSortOrder(getNextSortOrder("${tablePrefix}${tablesqlname}", null));
        ${tableName}Mapper.insertSelective(record);
    }

    <#--@Transactional
    @CacheEvict(value="${TableName}:ALL", allEntries = true)
    public void del(Integer ${tbn(key, "tableName")}){

        ${tableName}Mapper.deleteByPrimaryKey(${tbn(key, "tableName")});
    }-->

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
    public void updateByPrimaryKeySelective(${TableName} record){
        if(StringUtils.isNotBlank(record.getCode()))
            Assert.isTrue(!idDuplicate(record.getId(), record.getCode()), "duplicate");
        ${tableName}Mapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="${TableName}:ALL")
    public Map<Integer, ${TableName}> findAll() {

        ${TableName}Example example = new ${TableName}Example();
        example.createCriteria().andStatusEqualTo(true);
        example.setOrderByClause("sort_order desc");
        List<${TableName}> records = ${tableName}Mapper.selectByExample(example);
        Map<Integer, ${TableName}> map = new LinkedHashMap<>();
        for (${TableName} record : records) {
            map.put(record.getId(), record);
        }

        return map;
    }
    <#if tableColumnsMap['sort_order']??>
    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    @CacheEvict(value = "${TableName}:ALL", allEntries = true)
    public void changeOrder(int ${tbn(key, "tableName")}, int addNum) {

        changeOrder("${tablePrefix}${tablesqlname}", null, ORDER_BY_DESC, ${tbn(key, "tableName")}, addNum);
    }
    </#if>
}
