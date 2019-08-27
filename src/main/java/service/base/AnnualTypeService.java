package service.base;

import domain.base.AnnualType;
import domain.base.AnnualTypeExample;
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

        AnnualType entity = annualTypeMapper.selectByPrimaryKey(id);
        changeOrder("base_annual_type", "module="+ entity.getModule(), ORDER_BY_DESC, id, addNum);
    }
}
