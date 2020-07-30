package service.cet;

import domain.cet.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shiro.ShiroHelper;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class CetPlanCourseService extends CetBaseMapper {

    @Autowired
    private CetPlanCourseObjService cetPlanCourseObjService;
    @Autowired
    private CetCourseService cetCourseService;

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

        for (int courseId : courseIds) {

            CetCourse cetCourse = cetCourseService.get(courseId);

            CetPlanCourse record = new CetPlanCourse();
            record.setPlanId(planId);
            record.setCourseId(courseId);
            record.setPeriod(cetCourse.getPeriod());
            record.setName(cetCourse.getName());
            record.setUnit(cetCourse.getAddress()); // 针对上级网上专题

            CetPlanCourse cetPlanCourse = get(planId, courseId);
            if(cetPlanCourse!=null){

                record.setId(cetPlanCourse.getId());
                cetPlanCourseMapper.updateByPrimaryKeySelective(record);
            }else {
                record.setSortOrder(getNextSortOrder("cet_plan_course", "plan_id=" + planId));
                cetPlanCourseMapper.insertSelective(record);
            }
        }
    }

    // 选择学员/取消选择
    @Transactional
    public void selectObjs(Integer[] objIds, boolean select, int projectId, int planCourseId) {

        if(objIds==null || objIds.length==0){
            objIds = iCetMapper.getCetProjectObjIds(projectId).toArray(new Integer[0]);
        }

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
                    .andObjIdIn(Arrays.asList(objIds)).andIsFinishedEqualTo(false);
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

        CetPlanCourse entity = cetPlanCourseMapper.selectByPrimaryKey(id);
        changeOrder("cet_plan_course", "plan_id="+ entity.getPlanId(), ORDER_BY_ASC, id, addNum);
    }
}
