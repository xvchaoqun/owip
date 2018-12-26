package service.crs;

import domain.crs.CrsRequireRule;
import domain.crs.CrsRequireRuleExample;
import domain.crs.CrsRuleItem;
import domain.crs.CrsRuleItemExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CrsRequireRuleService extends CrsBaseMapper {

    // 更新评估指标的数量
    public void updateItemNum(Integer id) {
        if (id != null) {
            CrsRuleItemExample example = new CrsRuleItemExample();
            example.createCriteria().andRequireRuleIdEqualTo(id);
            long itemNum = crsRuleItemMapper.countByExample(example);
            CrsRequireRule _main = new CrsRequireRule();
            _main.setId(id);
            _main.setItemNum((int) itemNum);
            crsRequireRuleMapper.updateByPrimaryKeySelective(_main);
        }
    }

    @Transactional
    @CacheEvict(value = "CrsRequireRules", allEntries = true)
    public void insertSelective(CrsRequireRule record) {

        record.setSortOrder(getNextSortOrder("crs_require_rule", "post_require_id=" + record.getPostRequireId()));
        record.setItemNum(0);
        crsRequireRuleMapper.insertSelective(record);
    }

    @Transactional
    @CacheEvict(value = "CrsRequireRules", allEntries = true)
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        {
            //删除包含条例
            CrsRuleItemExample example = new CrsRuleItemExample();
            example.createCriteria().andRequireRuleIdIn(Arrays.asList(ids));
            crsRuleItemMapper.deleteByExample(example);
        }

        {
            // 删除规则
            CrsRequireRuleExample example = new CrsRequireRuleExample();
            example.createCriteria().andIdIn(Arrays.asList(ids));
            crsRequireRuleMapper.deleteByExample(example);
        }

    }

    @Transactional
    @CacheEvict(value = "CrsRequireRules", allEntries = true)
    public int updateByPrimaryKeySelective(CrsRequireRule record) {

        record.setPostRequireId(null);
        record.setItemNum(null);
        return crsRequireRuleMapper.updateByPrimaryKeySelective(record);
    }

    // <规则ID， 规则内容>
    @Cacheable(value = "CrsRequireRules")
    public Map<Integer, CrsRequireRule> findAll(int postRequireId) {

        List<CrsRequireRule> crsRequireRules = new ArrayList<>();
        {
            CrsRequireRuleExample example = new CrsRequireRuleExample();
            example.createCriteria().andPostRequireIdEqualTo(postRequireId);
            example.setOrderByClause("sort_order desc");
            crsRequireRules = crsRequireRuleMapper.selectByExample(example);
        }

        Map<Integer, CrsRequireRule> map = new LinkedHashMap<>();
        for (CrsRequireRule crsRequireRule : crsRequireRules) {

            CrsRuleItemExample example = new CrsRuleItemExample();
            example.createCriteria().andRequireRuleIdEqualTo(crsRequireRule.getId());
            example.setOrderByClause("sort_order desc");
            List<CrsRuleItem> ruleItems = crsRuleItemMapper.selectByExample(example);
            crsRequireRule.setRuleItems(ruleItems);  // 评估内容包含的二级指标

            map.put(crsRequireRule.getId(), crsRequireRule);
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
    @CacheEvict(value = "CrsRequireRules", allEntries = true)
    public void changeOrder(int id, int addNum) {

        if (addNum == 0) return;

        CrsRequireRule entity = crsRequireRuleMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();
        Integer postRequireId = entity.getPostRequireId();

        CrsRequireRuleExample example = new CrsRequireRuleExample();
        if (addNum > 0) {

            CrsRequireRuleExample.Criteria criteria = example.createCriteria().andPostRequireIdEqualTo(postRequireId);
            criteria.andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        } else {

            CrsRequireRuleExample.Criteria criteria = example.createCriteria().andPostRequireIdEqualTo(postRequireId);
            criteria.andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<CrsRequireRule> overEntities = crsRequireRuleMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if (overEntities.size() > 0) {

            CrsRequireRule targetEntity = overEntities.get(overEntities.size() - 1);

            if (addNum > 0)
                commonMapper.downOrder("crs_require_rule", "post_require_id=" + postRequireId, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("crs_require_rule", "post_require_id=" + postRequireId, baseSortOrder, targetEntity.getSortOrder());

            CrsRequireRule record = new CrsRequireRule();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            crsRequireRuleMapper.updateByPrimaryKeySelective(record);
        }
    }
}
