package service.cr;

import domain.cr.CrRuleItem;
import domain.cr.CrRuleItemExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CrRuleItemService extends CrBaseMapper {

    @Autowired
    private CrRequireRuleService crRequireRuleService;

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "CrRuleItems", allEntries = true),
            @CacheEvict(value = "CrRequireRules", allEntries = true)
    })
    public void insertSelective(CrRuleItem record) {

        record.setSortOrder(getNextSortOrder("cr_rule_item", "require_rule_id=" + record.getRequireRuleId()));
        crRuleItemMapper.insertSelective(record);

        crRequireRuleService.updateItemNum(record.getRequireRuleId());
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "CrRuleItems", allEntries = true),
            @CacheEvict(value = "CrRequireRules", allEntries = true)
    })
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        List<CrRuleItem> crRuleItems = null;
        {
            CrRuleItemExample example = new CrRuleItemExample();
            example.createCriteria().andIdIn(Arrays.asList(ids));
            crRuleItems = crRuleItemMapper.selectByExample(example);

            crRuleItemMapper.deleteByExample(example);
        }

        {
            // 更新规则包含的条例数量
            if (crRuleItems != null) {
                for (CrRuleItem crRuleItem : crRuleItems) {
                    crRequireRuleService.updateItemNum(crRuleItem.getRequireRuleId());
                }
            }
        }

    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "CrRuleItems", allEntries = true),
            @CacheEvict(value = "CrRequireRules", allEntries = true)
    })
    public int updateByPrimaryKeySelective(CrRuleItem record) {

        record.setRequireRuleId(null);
        return crRuleItemMapper.updateByPrimaryKeySelective(record);
    }

    // <条例ID， 条例内容>
    @Cacheable(value = "CrRuleItems")
    public Map<Integer, CrRuleItem> findAll(int requireRuleId) {

        List<CrRuleItem> crRuleItemes = new ArrayList<>();
        {
            CrRuleItemExample example = new CrRuleItemExample();
            example.createCriteria().andRequireRuleIdEqualTo(requireRuleId);
            example.setOrderByClause("sort_order desc");
            crRuleItemes = crRuleItemMapper.selectByExample(example);
        }

        Map<Integer, CrRuleItem> map = new LinkedHashMap<>();
        int topIndex = 0;
        for (CrRuleItem crRuleItem : crRuleItemes) {

            map.put(crRuleItem.getId(), crRuleItem);
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
            @CacheEvict(value = "CrRuleItems", allEntries = true),
            @CacheEvict(value = "CrRequireRules", allEntries = true)
    })
    public void changeOrder(int id, int addNum) {

        if (addNum == 0) return;

        CrRuleItem entity = crRuleItemMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();
        Integer requireRuleId = entity.getRequireRuleId();

        CrRuleItemExample example = new CrRuleItemExample();
        if (addNum > 0) {

            CrRuleItemExample.Criteria criteria = example.createCriteria().andRequireRuleIdEqualTo(requireRuleId);
            criteria.andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        } else {

            CrRuleItemExample.Criteria criteria = example.createCriteria().andRequireRuleIdEqualTo(requireRuleId);
            criteria.andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<CrRuleItem> overEntities = crRuleItemMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if (overEntities.size() > 0) {

            CrRuleItem targetEntity = overEntities.get(overEntities.size() - 1);

            if (addNum > 0)
                commonMapper.downOrder("cr_rule_item", "require_rule_id=" + requireRuleId, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("cr_rule_item", "require_rule_id=" + requireRuleId, baseSortOrder, targetEntity.getSortOrder());

            CrRuleItem record = new CrRuleItem();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            crRuleItemMapper.updateByPrimaryKeySelective(record);
        }
    }
}
