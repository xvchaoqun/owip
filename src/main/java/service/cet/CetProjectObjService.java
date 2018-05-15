package service.cet;

import controller.global.OpException;
import domain.cet.CetProject;
import domain.cet.CetProjectObj;
import domain.cet.CetProjectObjExample;
import domain.cet.CetProjectObjView;
import domain.cet.CetProjectObjViewExample;
import domain.cet.CetProjectPlan;
import domain.cet.CetProjectPlanExample;
import domain.cet.CetTrain;
import domain.cet.CetTrainCourse;
import domain.cet.CetTraineeCourse;
import domain.cet.CetTraineeCourseView;
import domain.cet.CetTraineeView;
import domain.sys.SysUserView;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistence.cet.common.ICetProjectObj;
import service.BaseMapper;
import service.sys.SysApprovalLogService;
import service.sys.SysUserService;
import shiro.ShiroHelper;
import sys.constants.CetConstants;
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class CetProjectObjService extends BaseMapper {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private CetTraineeService cetTraineeService;
    @Autowired
    private CetTrainCourseService cetTrainCourseService;
    @Autowired
    private CetTraineeCourseService cetTraineeCourseService;
    @Autowired
    private SysApprovalLogService sysApprovalLogService;


    public CetProjectObj get(int userId, int projectId){

        CetProjectObjExample example = new CetProjectObjExample();
        example.createCriteria().andUserIdEqualTo(userId).andProjectIdEqualTo(projectId);
        List<CetProjectObj> cetTrainees = cetProjectObjMapper.selectByExample(example);

        return cetTrainees.size()>0?cetTrainees.get(0):null;
    }

    @Transactional
    public void insertSelective(CetProjectObj record){

        cetProjectObjMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        cetProjectObjMapper.deleteByPrimaryKey(id);
    }

    // 删除心得体会
    @Transactional
    public void clearWrite(Integer[] ids) {
        for (Integer id : ids) {
            commonMapper.excuteSql("update cet_project_obj set word_write=null, pdf_write=null where id=" + id);
        }
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CetProjectObjExample example = new CetProjectObjExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cetProjectObjMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CetProjectObj record){

        return cetProjectObjMapper.updateByPrimaryKeySelective(record);
    }

    // 培训对象列表
    public List<CetProjectObj> cetProjectObjList(int projectId, int traineeTypeId){

        CetProjectObjExample example = new CetProjectObjExample();
        example.createCriteria().andProjectIdEqualTo(projectId)
                .andTraineeTypeIdEqualTo(traineeTypeId);
        example.setOrderByClause("id asc");

        return cetProjectObjMapper.selectByExample(example);
    }

    public Set<Integer> getSelectedProjectObjUserIdSet(int projectId) {

        Set<Integer> userIdSet = new HashSet<>();

        CetProjectObjExample example = new CetProjectObjExample();
        example.createCriteria().andProjectIdEqualTo(projectId);
        List<CetProjectObj> cetProjectObjs = cetProjectObjMapper.selectByExample(example);

        for (CetProjectObj cetProjectObj : cetProjectObjs) {
            userIdSet.add(cetProjectObj.getUserId());
        }

        return userIdSet;
    }

    @Transactional
    public void addOrUpdate(int projectId, int traineeTypeId, Integer[] userIds) {
        if(userIds==null || userIds.length==0) return;

        // 已选人员
        Set<Integer> selectedProjectObjUserIdSet = getSelectedProjectObjUserIdSet(projectId);
        // 提交更新人员
        Set<Integer> specialeeUserIdSet = new HashSet<>();
        specialeeUserIdSet.addAll(Arrays.asList(userIds));

        List<CetTrain> cetTrains = iCetMapper.getCetTrain(projectId);

        for (Integer userId : userIds) {

            if(selectedProjectObjUserIdSet.contains(userId)) continue;

            CetProjectObj record = new CetProjectObj();
            record.setProjectId(projectId);
            record.setUserId(userId);
            record.setTraineeTypeId(traineeTypeId);
            cetProjectObjMapper.insertSelective(record);

            sysUserService.addRole(userId, RoleConstants.ROLE_CET_TRAINEE);

            // 同步至每个培训班的学员列表
            for (CetTrain cetTrain : cetTrains) {
                cetTraineeService.createIfNotExist(userId, cetTrain.getId());
            }

            sysApprovalLogService.add(record.getId(), record.getUserId(),
                    SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_CET_SPECIAL_OBJ,
                    "添加培训对象", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                    "新建");
        }

        // 需要删除的人员
        selectedProjectObjUserIdSet.removeAll(specialeeUserIdSet);
        for (Integer userId : selectedProjectObjUserIdSet) {
            CetProjectObjExample example = new CetProjectObjExample();
            example.createCriteria().andProjectIdEqualTo(projectId).andUserIdEqualTo(userId);
            cetProjectObjMapper.deleteByExample(example);

            delRoleIfNotTrainee(userId);
        }

    }

    // 退出
    @Transactional
    public void quit(boolean isQuit, Integer[] ids) {

        CetProjectObj record = new CetProjectObj();
        record.setIsQuit(isQuit);

        CetProjectObjExample example = new CetProjectObjExample();
        example.createCriteria().andIdIn(Arrays.asList(ids)).andIsQuitEqualTo(!isQuit);
        cetProjectObjMapper.updateByExampleSelective(record, example);
    }

    // 检查是否删除参训人员角色
    public void delRoleIfNotTrainee(int userId){

        CetProjectObjExample example = new CetProjectObjExample();
        example.createCriteria().andUserIdEqualTo(userId);
        if(cetProjectObjMapper.countByExample(example)==0) {
            sysUserService.delRole(userId, RoleConstants.ROLE_CET_TRAINEE);
        }
    }

    // 设置为必选学员/退课
    public void canQuit(int projectId, Integer[] objIds, boolean canQuit, int trainCourseId) {

        List<CetProjectObj> cetProjectObjs = null;
        if(objIds==null || objIds.length==0){
            CetProjectObjExample example = new CetProjectObjExample();
            example.createCriteria().andProjectIdEqualTo(projectId).andIsQuitEqualTo(false);
            cetProjectObjs = cetProjectObjMapper.selectByExample(example);
        }else {
            CetProjectObjExample example = new CetProjectObjExample();
            example.createCriteria().andIdIn(Arrays.asList(objIds));
            cetProjectObjs = cetProjectObjMapper.selectByExample(example);
        }

        CetTrainCourse cetTrainCourse = cetTrainCourseMapper.selectByPrimaryKey(trainCourseId);
        int trainId = cetTrainCourse.getTrainId();
        // 已选课学员
        Map<Integer, CetTraineeCourseView> trainees = cetTrainCourseService.findTrainees(trainCourseId);

        for (CetProjectObj cetProjectObj : cetProjectObjs) {

            int userId = cetProjectObj.getUserId();

            // 如果还没选过课，则先创建参训人
            CetTraineeView cetTrainee = cetTraineeService.createIfNotExist(userId, trainId);
            int traineeId = cetTrainee.getId();

            CetTraineeCourseView ctc = trainees.get(userId);
            if(ctc==null){
                if(canQuit) continue;
                // 选课
                cetTraineeCourseService.applyItem(userId, trainCourseId, true, true, "设为必选课程");
            }else {
                if (ctc.getIsFinished()) {
                    SysUserView uv = sysUserService.findById(userId);
                    throw new OpException("学员{0}已上课签到，无法操作。", uv.getRealname());
                }
                // 可选->可选
                if (ctc.getCanQuit() && canQuit){
                    if(ctc.getChooseUserId()!=null){ // 已选课
                        // 退课
                        cetTraineeCourseService.applyItem(userId, trainCourseId, false, true, "退课");
                    }
                    continue;
                }
                // 必选->必选
                if (!ctc.getCanQuit() && !canQuit) continue;
                // 可选->必选
                if(ctc.getCanQuit() && !canQuit){

                    // 修改为必选课
                    CetTraineeCourse record = new CetTraineeCourse();
                    record.setId(ctc.getId());
                    record.setCanQuit(false);
                    record.setChooseTime(new Date());
                    record.setChooseUserId(ShiroHelper.getCurrentUserId());
                    cetTraineeCourseMapper.updateByPrimaryKeySelective(record);

                    sysApprovalLogService.add(traineeId, userId,
                            SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                            SystemConstants.SYS_APPROVAL_LOG_TYPE_CET_TRAINEE,
                            "修改为必选课程", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                            cetTrainCourse.getCetCourse().getName());
                }
                // 必选->可选
                if(!ctc.getCanQuit() && canQuit){

                    // 退课
                    cetTraineeCourseService.applyItem(userId, trainCourseId, false, true, "设为可选课程");
                }
            }
        }
    }

    // 获取培训对象在一个培训方案中已完成的学时
    public BigDecimal getPlanFinishPeriod(CetProjectPlan cetProjectPlan, int objId) {

        //int projectId = cetProjectPlan.getProjectId();
        int planId = cetProjectPlan.getId();
        byte planType = cetProjectPlan.getType();
        switch (planType){
            case CetConstants.CET_PROJECT_PLAN_TYPE_OFFLINE: // 线下培训
            case CetConstants.CET_PROJECT_PLAN_TYPE_ONLINE: // 线上培训
            case CetConstants.CET_PROJECT_PLAN_TYPE_PRACTICE: // 实践教学
                return iCetMapper.getPlanFinishPeriod(planId, objId);

            case CetConstants.CET_PROJECT_PLAN_TYPE_SELF: // 自主学习
                return iCetMapper.getSelfFinishPeriod(planId, objId);

            case CetConstants.CET_PROJECT_PLAN_TYPE_SPECIAL: // 上级网上专题班
                return iCetMapper.getSpecialFinishPeriod(planId, objId);

            case CetConstants.CET_PROJECT_PLAN_TYPE_GROUP: // 分组研讨
                return iCetMapper.getGroupFinishPeriod(planId, objId);

            case CetConstants.CET_PROJECT_PLAN_TYPE_WRITE: // 撰写心得体会
                return iCetMapper.getWriteFinishPeriod(planId, objId);
        }

        return null;
    }

    // 获取培训对象在一个培训中已完成的学时
    public ICetProjectObj getICetProjectObj(int projectId, int userId) {

        ICetProjectObj cetProjectObj = new ICetProjectObj();
        try {
            ConvertUtils.register(new BigDecimalConverter(null), java.math.BigDecimal.class);
            BeanUtils.copyProperties(cetProjectObj, get(userId, projectId));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        int objId = cetProjectObj.getId();
        cetProjectObj.setFinishPeriod(getFinishPeriod(projectId, objId));
        return cetProjectObj;
    }

    public BigDecimal getFinishPeriod(int projectId, int objId){

        CetProjectPlanExample example = new CetProjectPlanExample();
        example.createCriteria().andProjectIdEqualTo(projectId);
        List<CetProjectPlan> cetProjectPlans = cetProjectPlanMapper.selectByExample(example);

        BigDecimal finishPeriod = BigDecimal.ZERO;
        for (CetProjectPlan cetProjectPlan : cetProjectPlans) {

            BigDecimal planFinishPeriod = getPlanFinishPeriod(cetProjectPlan, objId);
            if(planFinishPeriod!=null){
                finishPeriod = finishPeriod.add(planFinishPeriod);
            }
        }

        return finishPeriod;
    }

    // 设置应完成学时
    @Transactional
    public void setShouldFinishPeriod(int projectId, Integer[] ids, BigDecimal shouldFinishPeriod) {

        if(ids==null || ids.length==0){
            List<Integer> objIds = iCetMapper.getCetProjectObjIds(projectId);
            ids = objIds.toArray(new Integer[]{});
        }

        if(ids==null || ids.length==0) return;

        if(shouldFinishPeriod!=null){

            CetProjectObj record = new CetProjectObj();
            record.setShouldFinishPeriod(shouldFinishPeriod);

            CetProjectObjExample example = new CetProjectObjExample();
            example.createCriteria().andIdIn(Arrays.asList(ids));
            cetProjectObjMapper.updateByExampleSelective(record, example);
        }else{
            commonMapper.excuteSql("update cet_project_obj set should_finish_period=null where id in("
                    + StringUtils.join(ids, ",") + ")");
        }
    }

    // 自动结业
    @Transactional
    public void autoGraduate(int projectId) {

        CetProject cetProject = cetProjectMapper.selectByPrimaryKey(projectId);
        BigDecimal requirePeriod = cetProject.getRequirePeriod();

        List<Integer> finishIds = new ArrayList<>();
        {
            CetProjectObjViewExample example = new CetProjectObjViewExample();
            example.createCriteria().andProjectIdEqualTo(projectId).andIsQuitEqualTo(false);
            List<CetProjectObjView> cetProjectObjViews = cetProjectObjViewMapper.selectByExample(example);
            for (CetProjectObjView cetProjectObjView : cetProjectObjViews) {

                BigDecimal finishPeriod = cetProjectObjView.getFinishPeriod();

                if (requirePeriod != null && finishPeriod != null && requirePeriod.compareTo(finishPeriod) <= 0) {
                    finishIds.add(cetProjectObjView.getId());
                }
            }
        }
        if(finishIds.size()>0) {
            CetProjectObj record = new CetProjectObj();
            record.setIsGraduate(true);
            CetProjectObjExample example = new CetProjectObjExample();
            example.createCriteria().andIdIn(finishIds);
            cetProjectObjMapper.updateByExampleSelective(record, example);
        }
    }

    // 手动结业
    @Transactional
    public void forceGraduate(Integer[] ids) {

        if(ids==null || ids.length==0) return;

        CetProjectObj record = new CetProjectObj();
        record.setIsGraduate(true);
        CetProjectObjExample example = new CetProjectObjExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cetProjectObjMapper.updateByExampleSelective(record, example);
    }
}
