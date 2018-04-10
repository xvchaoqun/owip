package service.cet;

import domain.cet.CetPlanCourse;
import domain.cet.CetPlanCourseExample;
import domain.cet.CetPlanCourseObj;
import domain.cet.CetPlanCourseObjExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import shiro.ShiroHelper;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class CetPlanCourseService extends BaseMapper {

    @Autowired
    private CetPlanCourseObjService cetPlanCourseObjService;

    public CetPlanCourse get(int trainId, int courseId){

        CetPlanCourseExample example = new CetPlanCourseExample();
        example.createCriteria().andPlanIdEqualTo(trainId).andCourseIdEqualTo(courseId);
        List<CetPlanCourse> cetPlanCourses = cetPlanCourseMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));

        return (cetPlanCourses.size()>0)?cetPlanCourses.get(0):null;
    }

    public boolean idDuplicate(Integer id, int planId, int courseId ){


        CetPlanCourseExample example = new CetPlanCourseExample();
        CetPlanCourseExample.Criteria criteria = example.createCriteria()
                .andPlanIdEqualTo(planId).andCourseIdEqualTo(courseId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return cetPlanCourseMapper.countByExample(example) > 0;
    }

    public Long getSelectedCount(int planCourseId){

        CetPlanCourseObjExample example = new CetPlanCourseObjExample();
        example.createCriteria().andPlanCourseIdEqualTo(planCourseId);

        return cetPlanCourseObjMapper.countByExample(example);
    }

    @Transactional
    public void insertSelective(CetPlanCourse record){

        record.setSortOrder(getNextSortOrder("cet_plan_course", "plan_id="+ record.getPlanId()));
        cetPlanCourseMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        cetPlanCourseMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CetPlanCourseExample example = new CetPlanCourseExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cetPlanCourseMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CetPlanCourse record){
        return cetPlanCourseMapper.updateByPrimaryKeySelective(record);
    }

    // 添加课程
    @Transactional
    public void selectCourses(int planId, Integer[] courseIds) {

        if(courseIds==null || courseIds.length==0) return;
        for (Integer courseId : courseIds) {

            CetPlanCourse cetPlanCourse = get(planId, courseId);
            if(cetPlanCourse!=null) continue;

            CetPlanCourse record = new CetPlanCourse();
            record.setPlanId(planId);
            record.setCourseId(courseId);

            record.setSortOrder(getNextSortOrder("cet_plan_course", "plan_id="+planId));
            cetPlanCourseMapper.insertSelective(record);
        }
    }

    // 选择学员/取消选择
    @Transactional
    public void selectObjs(Integer[] objIds, boolean select, int planCourseId) {

        if(objIds==null || objIds.length==0) return ;

        if(select){
            Date chooseTime = new Date();
            int chooseUserId = ShiroHelper.getCurrentUserId();
            for (Integer objId : objIds) {

                CetPlanCourseObj cetPlanCourseObj = cetPlanCourseObjService.get(objId, planCourseId);
                if (cetPlanCourseObj!=null) continue;

                CetPlanCourseObj record = new CetPlanCourseObj();
                record.setObjId(objId);
                record.setPlanCourseId(planCourseId);
                record.setChooseTime(chooseTime);
                record.setChooseUserId(chooseUserId);
                cetPlanCourseObjMapper.insertSelective(record);
            }
        }else{

            CetPlanCourseObjExample example = new CetPlanCourseObjExample();
            example.createCriteria().andPlanCourseIdEqualTo(planCourseId)
                    .andObjIdIn(Arrays.asList(objIds));
            cetPlanCourseObjMapper.deleteByExample(example);
        }
    }

    /**
     * 排序 ，要求 1、sort_order>0且不可重复  2、sort_order 降序排序
     * @param id
     * @param addNum
     */
    @Transactional
    public void changeOrder(int id, int addNum) {

        if(addNum == 0) return ;

        byte orderBy = ORDER_BY_ASC;

        CetPlanCourse entity = cetPlanCourseMapper.selectByPrimaryKey(id);
        Integer baseSortOrder = entity.getSortOrder();

        CetPlanCourseExample example = new CetPlanCourseExample();
        if (addNum*orderBy > 0) {

            example.createCriteria().andSortOrderGreaterThan(baseSortOrder);
            example.setOrderByClause("sort_order asc");
        }else {

            example.createCriteria().andSortOrderLessThan(baseSortOrder);
            example.setOrderByClause("sort_order desc");
        }

        List<CetPlanCourse> overEntities = cetPlanCourseMapper.selectByExampleWithRowbounds(example, new RowBounds(0, Math.abs(addNum)));
        if(overEntities.size()>0) {

            CetPlanCourse targetEntity = overEntities.get(overEntities.size()-1);

            if (addNum*orderBy > 0)
                commonMapper.downOrder("cet_plan_course", "plan_id="+ entity.getPlanId(), baseSortOrder, targetEntity.getSortOrder());
            else
                commonMapper.upOrder("cet_plan_course", "plan_id="+ entity.getPlanId(), baseSortOrder, targetEntity.getSortOrder());

            CetPlanCourse record = new CetPlanCourse();
            record.setId(id);
            record.setSortOrder(targetEntity.getSortOrder());
            cetPlanCourseMapper.updateByPrimaryKeySelective(record);
        }
    }
}
