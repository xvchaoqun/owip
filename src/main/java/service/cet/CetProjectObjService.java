package service.cet;

import controller.global.OpException;
import domain.cet.CetProjectObj;
import domain.cet.CetProjectObjExample;
import domain.cet.CetTrainCourse;
import domain.cet.CetTraineeCourse;
import domain.cet.CetTraineeCourseView;
import domain.cet.CetTraineeView;
import domain.sys.SysUserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.sys.SysApprovalLogService;
import service.sys.SysUserService;
import shiro.ShiroHelper;
import sys.constants.RoleConstants;
import sys.constants.SystemConstants;

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

        for (Integer userId : userIds) {

            if(selectedProjectObjUserIdSet.contains(userId)) continue;

            CetProjectObj record = new CetProjectObj();
            record.setProjectId(projectId);
            record.setUserId(userId);
            record.setTraineeTypeId(traineeTypeId);
            cetProjectObjMapper.insertSelective(record);

            sysUserService.addRole(userId, RoleConstants.ROLE_CET_TRAINEE);

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

    // 设置为必选学员/取消必选
    public void canQuit(Integer[] ids, boolean canQuit, int trainCourseId) {

        CetProjectObjExample example = new CetProjectObjExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        List<CetProjectObj> cetProjectObjs = cetProjectObjMapper.selectByExample(example);

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
                if (ctc.getCanQuit() && canQuit) continue;
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
}
