package service.cet;

import domain.cet.CetProjectPlan;
import domain.cet.CetProjectPlanExample;
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
    public void batchDel(int projectId, Integer[] ids){

        if(ids==null || ids.length==0) return;

        CetProjectPlanExample example = new CetProjectPlanExample();
        example.createCriteria().andProjectIdEqualTo(projectId).andIdIn(Arrays.asList(ids));
        cetProjectPlanMapper.deleteByExample(example);

        iCetMapper.updateProjectTotalPeriodByPlan(projectId);
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

        CetProjectPlan entity = cetProjectPlanMapper.selectByPrimaryKey(id);
        changeOrder("cet_project_plan", "project_id=" + entity.getProjectId(), ORDER_BY_ASC, id, addNum);
    }
}
