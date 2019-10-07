package service.cr;

import domain.cr.CrRequireRule;
import domain.cr.CrRequireRuleExample;
import domain.cr.CrRuleItem;
import domain.cr.CrRuleItemExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CrRequireRuleService extends CrBaseMapper {

    // 更新评估指标的数量
    public void updateItemNum(Integer id) {
        if (id != null) {
            CrRuleItemExample example = new CrRuleItemExample();
            example.createCriteria().andRequireRuleIdEqualTo(id);
            long itemNum = crRuleItemMapper.countByExample(example);
            CrRequireRule _main = new CrRequireRule();
            _main.setId(id);
            _main.setItemNum((int) itemNum);
            crRequireRuleMapper.updateByPrimaryKeySelective(_main);
        }
    }

    @Transactional
    @CacheEvict(value = "CrRequireRules", allEntries = true)
    public void insertSelective(CrRequireRule record) {

        record.setSortOrder(getNextSortOrder("cr_require_rule", "require_id=" + record.getRequireId()));
        record.setItemNum(0);
        crRequireRuleMapper.insertSelective(record);
    }

    @Transactional
    @CacheEvict(value = "CrRequireRules", allEntries = true)
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        {
            //删除包含条例
            CrRuleItemExample example = new CrRuleItemExample();
            example.createCriteria().andRequireRuleIdIn(Arrays.asList(ids));
            crRuleItemMapper.deleteByExample(example);
        }

        {
            // 删除规则
            CrRequireRuleExample example = new CrRequireRuleExample();
            example.createCriteria().andIdIn(Arrays.asList(ids));
            crRequireRuleMapper.deleteByExample(example);
        }

    }

    @Transactional
    @CacheEvict(value = "CrRequireRules", allEntries = true)
    public int updateByPrimaryKeySelective(CrRequireRule record) {

        record.setRequireId(null);
        record.setItemNum(null);
        return crRequireRuleMapper.updateByPrimaryKeySelective(record);
    }

    // <规则ID， 规则内容>
    @Cacheable(value = "CrRequireRules")
    public Map<Integer, CrRequireRule> findAll(int postRequireId) {

        List<CrRequireRule> crRequireRules = new ArrayList<>();
        {
            CrRequireRuleExample example = new CrRequireRuleExample();
            example.createCriteria().andRequireIdEqualTo(postRequireId);
            example.setOrderByClause("sort_order desc");
            crRequireRules = crRequireRuleMapper.selectByExample(example);
        }

        Map<Integer, CrRequireRule> map = new LinkedHashMap<>();
        for (CrRequireRule crRequireRule : crRequireRules) {

            CrRuleItemExample example = new CrRuleItemExample();
            example.createCriteria().andRequireRuleIdEqualTo(crRequireRule.getId());
            example.setOrderByClause("sort_order desc");
            List<CrRuleItem> ruleItems = crRuleItemMapper.selectByExample(example);
            crRequireRule.setRuleItems(ruleItems);  // 评估内容包含的二级指标

            map.put(crRequireRule.getId(), crRequireRule);
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
    @CacheEvict(value = "CrRequireRules", allEntries = true)
    public void changeOrder(int id, int addNum) {

        if (addNum == 0) return;

        CrRequireRule entity = crRequireRuleMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();
        Integer postRequireId = entity.getRequireId();

        CrRequireRuleExample example = new CrRequireRuleExample();
        if (addNum > 0) {

            CrRequireRuleExample.Criteria criteria = example.createCriteria().andRequireIdEqualTo(postRequireId);
            criteria.andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        } else {

            CrRequireRuleExample.Criteria criteria = example.createCriteria().andRequireIdEqualTo(postRequireId);
            criteria.andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<CrRequireRule> overEntities = crRequireRuleMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if (overEntities.size() > 0) {

            CrRequireRule targetEntity = overEntities.get(overEntities.size() - 1);

            if (addNum > 0)
                commonMapper.downOrder("cr_require_rule", "require_id=" + postRequireId, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("cr_require_rule", "require_id=" + postRequireId, baseSortOrder, targetEntity.getSortOrder());

            CrRequireRule record = new CrRequireRule();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            crRequireRuleMapper.updateByPrimaryKeySelective(record);
        }
    }
}
