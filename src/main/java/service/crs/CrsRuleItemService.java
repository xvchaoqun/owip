package service.crs;

import domain.crs.CrsRuleItem;
import domain.crs.CrsRuleItemExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CrsRuleItemService extends BaseMapper {

    @Autowired
    private CrsRequireRuleService crsRequireRuleService;

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "CrsRuleItems", allEntries = true),
            @CacheEvict(value = "CrsRequireRules", allEntries = true)
    })
    public void insertSelective(CrsRuleItem record) {

        record.setSortOrder(getNextSortOrder("crs_rule_item", "require_rule_id=" + record.getRequireRuleId()));
        crsRuleItemMapper.insertSelective(record);

        crsRequireRuleService.updateItemNum(record.getRequireRuleId());
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "CrsRuleItems", allEntries = true),
            @CacheEvict(value = "CrsRequireRules", allEntries = true)
    })
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        List<CrsRuleItem> crsRuleItems = null;
        {
            CrsRuleItemExample example = new CrsRuleItemExample();
            example.createCriteria().andIdIn(Arrays.asList(ids));
            crsRuleItems = crsRuleItemMapper.selectByExample(example);

            crsRuleItemMapper.deleteByExample(example);
        }

        {
            // 更新规则包含的条例数量
            if (crsRuleItems != null) {
                for (CrsRuleItem crsRuleItem : crsRuleItems) {
                    crsRequireRuleService.updateItemNum(crsRuleItem.getRequireRuleId());
                }
            }
        }

    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "CrsRuleItems", allEntries = true),
            @CacheEvict(value = "CrsRequireRules", allEntries = true)
    })
    public int updateByPrimaryKeySelective(CrsRuleItem record) {

        record.setRequireRuleId(null);
        return crsRuleItemMapper.updateByPrimaryKeySelective(record);
    }

    // <条例ID， 条例内容>
    @Cacheable(value = "CrsRuleItems")
    public Map<Integer, CrsRuleItem> findAll(int requireRuleId) {

        List<CrsRuleItem> crsRuleItemes = new ArrayList<>();
        {
            CrsRuleItemExample example = new CrsRuleItemExample();
            example.createCriteria().andRequireRuleIdEqualTo(requireRuleId);
            example.setOrderByClause("sort_order desc");
            crsRuleItemes = crsRuleItemMapper.selectByExample(example);
        }

        Map<Integer, CrsRuleItem> map = new LinkedHashMap<>();
        int topIndex = 0;
        for (CrsRuleItem crsRuleItem : crsRuleItemes) {

            map.put(crsRuleItem.getId(), crsRuleItem);
        }

        return map;
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     *
     * @param id
     * @param addNum
     */
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "CrsRuleItems", allEntries = true),
            @CacheEvict(value = "CrsRequireRules", allEntries = true)
    })
    public void changeOrder(int id, int addNum) {

        if (addNum == 0) return;

        CrsRuleItem entity = crsRuleItemMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();
        Integer requireRuleId = entity.getRequireRuleId();

        CrsRuleItemExample example = new CrsRuleItemExample();
        if (addNum > 0) {

            CrsRuleItemExample.Criteria criteria = example.createCriteria().andRequireRuleIdEqualTo(requireRuleId);
            criteria.andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        } else {

            CrsRuleItemExample.Criteria criteria = example.createCriteria().andRequireRuleIdEqualTo(requireRuleId);
            criteria.andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<CrsRuleItem> overEntities = crsRuleItemMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if (overEntities.size() > 0) {

            CrsRuleItem targetEntity = overEntities.get(overEntities.size() - 1);

            if (addNum > 0)
                commonMapper.downOrder("crs_rule_item", "require_rule_id=" + requireRuleId, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("crs_rule_item", "require_rule_id=" + requireRuleId, baseSortOrder, targetEntity.getSortOrder());

            CrsRuleItem record = new CrsRuleItem();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            crsRuleItemMapper.updateByPrimaryKeySelective(record);
        }
    }
}
