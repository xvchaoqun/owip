package service.cet;

import domain.cet.CetPlanCourse;
import domain.cet.CetPlanCourseObj;
import domain.cet.CetPlanCourseObjExample;
import domain.cet.CetPlanCourseObjResult;
import domain.cet.CetPlanCourseObjResultExample;
import domain.cet.CetProjectObj;
import domain.cet.CetProjectPlan;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;

import java.util.Arrays;
import java.util.List;

@Service
public class CetPlanCourseObjService extends BaseMapper {

    @Autowired
    private CetProjectObjService cetProjectObjService;

    public CetPlanCourseObj getByUserId(Integer userId, Integer planCourseId) {

        CetPlanCourse cetPlanCourse = cetPlanCourseMapper.selectByPrimaryKey(planCourseId);
        Integer planId = cetPlanCourse.getPlanId();
        CetProjectPlan cetProjectPlan = cetProjectPlanMapper.selectByPrimaryKey(planId);
        Integer projectId = cetProjectPlan.getProjectId();

        CetProjectObj cetProjectObj = cetProjectObjService.get(userId, projectId);
        if(cetProjectObj!=null){

            return get(cetProjectObj.getId(), planCourseId);
        }

        return null;
    }

    public CetPlanCourseObj get(Integer objId, Integer planCourseId){

        CetPlanCourseObjExample example = new CetPlanCourseObjExample();
        example.createCriteria().andPlanCourseIdEqualTo(planCourseId).andObjIdEqualTo(objId);
        List<CetPlanCourseObj> cetPlanCourseObjs = cetPlanCourseObjMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));

        return cetPlanCourseObjs.size()==1?cetPlanCourseObjs.get(0):null;
    }

    public List<CetPlanCourseObjResult> getResults(int planCourseObjId){

        CetPlanCourseObjResultExample example = new CetPlanCourseObjResultExample();
        example.createCriteria().andPlanCourseObjIdEqualTo(planCourseObjId);

        return cetPlanCourseObjResultMapper.selectByExample(example);
    }

    public boolean idDuplicate(Integer id, int planCourseId, int objId){

        CetPlanCourseObjExample example = new CetPlanCourseObjExample();
        CetPlanCourseObjExample.Criteria criteria =
                example.createCriteria().andPlanCourseIdEqualTo(planCourseId).andObjIdEqualTo(objId);
        if(id!=null) criteria.andIdNotEqualTo(id);

        return cetPlanCourseObjMapper.countByExample(example) > 0;
    }

    @Transactional
    public void insertSelective(CetPlanCourseObj record){

        cetPlanCourseObjMapper.insertSelective(record);
    }

    public void del(Integer id){

        cetPlanCourseObjMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CetPlanCourseObj record){
        return cetPlanCourseObjMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional
    public void finish(Integer[] objIds, boolean finish, int projectId, int planCourseId) {

        if(objIds==null || objIds.length==0){
            objIds = iCetMapper.getCetProjectObjIds(projectId).toArray(new Integer[0]);
        }

        CetPlanCourseObjExample example = new CetPlanCourseObjExample();
        example.createCriteria().andPlanCourseIdEqualTo(planCourseId)
                .andObjIdIn(Arrays.asList(objIds));

        CetPlanCourseObj record = new CetPlanCourseObj();
        record.setIsFinished(finish);
        cetPlanCourseObjMapper.updateByExampleSelective(record, example);
    }
}
