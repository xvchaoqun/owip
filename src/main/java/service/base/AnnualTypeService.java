package service.base;

import domain.base.AnnualType;
import domain.base.AnnualTypeExample;
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
public class AnnualTypeService extends BaseMapper {

    @Transactional
    @CacheEvict(value="AnnualTypes", allEntries = true)
    public void insertSelective(AnnualType record){

        record.setSortOrder(getNextSortOrder("base_annual_type", "module="+ record.getModule()));
        annualTypeMapper.insertSelective(record);
    }

    @Transactional
    @CacheEvict(value="AnnualTypes", allEntries = true)
    public void del(Integer id){

        annualTypeMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @CacheEvict(value="AnnualTypes", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        AnnualTypeExample example = new AnnualTypeExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        annualTypeMapper.deleteByExample(example);
    }

    @Transactional
    @CacheEvict(value="AnnualTypes", allEntries = true)
    public void updateByPrimaryKeySelective(AnnualType record){

        annualTypeMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value="AnnualTypes", key = "#module")
    public Map<Integer, AnnualType> findAll(byte module) {

        AnnualTypeExample example = new AnnualTypeExample();
        example.createCriteria().andModuleEqualTo(module);
        example.setOrderByClause("sort_order desc");
        List<AnnualType> annualTypees = annualTypeMapper.selectByExample(example);
        Map<Integer, AnnualType> map = new LinkedHashMap<>();
        for (AnnualType annualType : annualTypees) {
            map.put(annualType.getId(), annualType);
        }

        return map;
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    @CacheEvict(value = "AnnualTypes", allEntries = true)
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        byte orderBy = ORDER_BY_DESC;

        AnnualType entity = annualTypeMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();
        byte module = entity.getModule();

        AnnualTypeExample example = new AnnualTypeExample();
        if (addNum*orderBy > 0) {

            example.createCriteria().andModuleEqualTo(module).andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andModuleEqualTo(module).andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<AnnualType> overEntities = annualTypeMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            AnnualType targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum*orderBy > 0)
                commonMapper.downOrder("base_annual_type", "module="+ module, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("base_annual_type", "module="+ module, baseSortOrder, targetEntity.getSortOrder());

            AnnualType record = new AnnualType();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            annualTypeMapper.updateByPrimaryKeySelective(record);
        }
    }
}
