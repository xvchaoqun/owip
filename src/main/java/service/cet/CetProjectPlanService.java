package service.cet;

import domain.cet.CetProjectPlan;
import domain.cet.CetProjectPlanExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CetProjectPlanService extends CetBaseMapper {

    public boolean idDuplicate(Integer id, int projectId, byte type){

        CetProjectPlanExample example = new CetProjectPlanExample();
        CetProjectPlanExample.Criteria criteria = example.createCriteria().andProjectIdEqualTo(projectId).andTypeEqualTo(type);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return cetProjectPlanMapper.countByExample(example) > 0;
    }

    @Transactional
    @CacheEvict(value="CetProjectPlans", allEntries = true)
    public void insertSelective(CetProjectPlan record){

        Assert.isTrue(!idDuplicate(null, record.getProjectId(), record.getType()), "duplicate");
        record.setSortOrder(getNextSortOrder("cet_project_plan", "project_id=" + record.getProjectId()));
        cetProjectPlanMapper.insertSelective(record);
    }

    @Transactional
    @CacheEvict(value="CetProjectPlans", allEntries = true)
    public void del(Integer id){

        cetProjectPlanMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @CacheEvict(value="CetProjectPlans", allEntries = true)
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CetProjectPlanExample example = new CetProjectPlanExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cetProjectPlanMapper.deleteByExample(example);
    }

    @Transactional
    @CacheEvict(value="CetProjectPlans", allEntries = true)
    public int updateByPrimaryKeySelective(CetProjectPlan record){

        if(record.getProjectId()!=null && record.getType()!=null)
            Assert.isTrue(!idDuplicate(record.getId(), record.getProjectId(), record.getType()), "duplicate");
        return cetProjectPlanMapper.updateByPrimaryKeySelective(record);
    }

    @Cacheable(value = "CetProjectPlans", key = "#projectId")
    public Map<Integer, CetProjectPlan> findAll(int projectId){

        CetProjectPlanExample example = new CetProjectPlanExample();
        example.createCriteria().andProjectIdEqualTo(projectId);
        example.setOrderByClause("sort_order asc");
        List<CetProjectPlan> cetProjectPlans = cetProjectPlanMapper.selectByExample(example);
        Map<Integer, CetProjectPlan> resultMap = new HashMap<>();
        for (CetProjectPlan cetProjectPlan : cetProjectPlans) {

            resultMap.put(cetProjectPlan.getId(), cetProjectPlan);
        }
        return resultMap;
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    @CacheEvict(value="CetProjectPlans", allEntries = true)
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        byte orderBy = ORDER_BY_ASC;

        CetProjectPlan entity = cetProjectPlanMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();
        Integer projectId = entity.getProjectId();

        CetProjectPlanExample example = new CetProjectPlanExample();
        if (addNum*orderBy > 0) {

            example.createCriteria().andProjectIdEqualTo(projectId).andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andProjectIdEqualTo(projectId).andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<CetProjectPlan> overEntities = cetProjectPlanMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            CetProjectPlan targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum*orderBy > 0)
                commonMapper.downOrder("cet_project_plan", "project_id=" + projectId, baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("cet_project_plan", "project_id=" + projectId, baseSortOrder, targetEntity.getSortOrder());

            CetProjectPlan record = new CetProjectPlan();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            cetProjectPlanMapper.updateByPrimaryKeySelective(record);
        }
    }
}
