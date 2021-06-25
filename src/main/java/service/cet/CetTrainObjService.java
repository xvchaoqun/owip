package service.cet;

import controller.global.OpException;
import domain.cet.*;
import domain.sys.SysUserView;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import persistence.cet.common.ICetTrainCourse;
import service.sys.SysApprovalLogService;
import service.sys.SysUserService;
import shiro.ShiroHelper;
import sys.constants.CetConstants;
import sys.constants.SystemConstants;
import sys.utils.ContextHelper;
import sys.utils.DateUtils;

import java.util.*;

@Service
public class CetTrainObjService extends CetBaseMapper {

    @Autowired
    private SysApprovalLogService sysApprovalLogService;
    @Autowired
    private CetProjectObjService cetProjectObjService;
    @Autowired
    private SysUserService sysUserService;

    public CetTrainObjView get(int userId, int trainCourseId) {

        CetTrainObjViewExample example = new CetTrainObjViewExample();
        example.createCriteria().andUserIdEqualTo(userId).andTrainCourseIdEqualTo(trainCourseId);
        List<CetTrainObjView> cetTrainObjViews = cetTrainObjViewMapper
                .selectByExampleWithRowbounds(example, new RowBounds(0, 1));

        return cetTrainObjViews.size() > 0 ? cetTrainObjViews.get(0) : null;
    }

    @Transactional
    public void insertSelective(CetTrainObj record) {

        cetTrainObjMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id) {

        cetTrainObjMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        CetTrainObjExample example = new CetTrainObjExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cetTrainObjMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CetTrainObj record) {
        return cetTrainObjMapper.updateByPrimaryKeySelective(record);
    }

    //
    @SuppressWarnings("checkstyle:RegexpSingleline")
    private void checkApplyIsOpen(boolean isApply, int trainCourseId) {

        CetTrainCourse cetTrainCourse = cetTrainCourseMapper.selectByPrimaryKey(trainCourseId);
        Integer trainId = cetTrainCourse.getTrainId();
        Integer projectId = cetTrainCourse.getProjectId();

        byte applyStatus = cetTrainCourse.getApplyStatus();
        if (isApply && applyStatus == CetConstants.CET_TRAIN_COURSE_APPLY_STATUS_CLOSE_APPLY) {
            throw new OpException("选课已关闭。");
        } else if (!isApply && applyStatus == CetConstants.CET_TRAIN_COURSE_APPLY_STATUS_CLOSE_QUIT) {
            throw new OpException("退课已关闭。");
        } else if (applyStatus == CetConstants.CET_TRAIN_COURSE_APPLY_STATUS_CLOSE_ALL) {
            throw new OpException("选课/退课已关闭。");
        }
        boolean applyOpen = false;
        if (trainId != null) {
            CetTrain cetTrain = cetTrainMapper.selectByPrimaryKey(trainId);
            applyOpen = cetTrain.getIsApplyOpen();

        } else {
            CetProject cetProject = cetProjectMapper.selectByPrimaryKey(projectId);
            applyOpen = cetProject.getIsApplyOpen();
        }

        if (!applyOpen) {
            throw new OpException("当前不在选课时间范围");
        }
    }

    // 参训人课程列表（用于网页、手机选课页面）
    public void trainDetail(Integer trainId, Integer projectId, ModelMap modelMap) {

        if(trainId!=null) {
            CetTrain cetTrain = cetTrainMapper.selectByPrimaryKey(trainId);
            modelMap.put("cetTrain", cetTrain);
            // 是否结课
            modelMap.put("isFinished", DateUtils.compareDate(new Date(), cetTrain.getEndTime()));
            modelMap.put("isApplyOpen", cetTrain.getIsApplyOpen());

            Integer planId = cetTrain.getPlanId();
            CetProjectPlan cetProjectPlan = cetProjectPlanMapper.selectByPrimaryKey(planId);
            modelMap.put("cetProjectPlan", cetProjectPlan);
            CetProject cetProject = cetProjectMapper.selectByPrimaryKey(cetProjectPlan.getProjectId());
            modelMap.put("cetProject", cetProject);

        }else{
            CetProject cetProject = cetProjectMapper.selectByPrimaryKey(projectId);
            modelMap.put("cetProject", cetProject);
            modelMap.put("isFinished", DateUtils.compareDate(new Date(), cetProject.getEndDate()));
            modelMap.put("isApplyOpen", cetProject.getIsApplyOpen());
        }

        int userId = ShiroHelper.getCurrentUserId();

        List<ICetTrainCourse> selectedCetTrainCourses = iCetMapper.selectedCetTrainCourses(trainId, projectId, userId);
        List<CetTrainCourse> unSelectedCetTrainCourses = iCetMapper.unSelectedCetTrainCourses(trainId, projectId, userId);

        modelMap.put("selectedCetTrainCourses", selectedCetTrainCourses);
        modelMap.put("unSelectedCetTrainCourses", unSelectedCetTrainCourses);
    }

    // 选课/退课
    @Transactional
    public void applyItem(int userId, int trainCourseId, boolean isApply, boolean isAdmin, boolean canQuit, String remark) {

        CetTrainCourse cetTrainCourse = cetTrainCourseMapper.selectByPrimaryKey(trainCourseId);
        int projectId = cetTrainCourse.getProjectId();

        if (!isAdmin) {
            // 非管理员选课
            checkApplyIsOpen(isApply, trainCourseId);
        }

        CetProjectObj cetProjectObj = cetProjectObjService.get(userId, projectId);
        int objId = cetProjectObj.getId();
        if (cetProjectObj == null) return;

        Date now = new Date();
        String ip = ContextHelper.getRealIp();

        CetTrainObjView cetTrainObj = get(userId, trainCourseId);

        if (isApply) {

            if (!isAdmin) {
                // 判断是否超出选课人数上限
                Integer applyLimit = cetTrainCourse.getApplyLimit();
                List<Integer> applyUserIds = iCetMapper.applyUserIds(trainCourseId);
                if (applyLimit != null && applyUserIds.size() >= applyLimit) {
                    throw new OpException("已超出选课人数上限。");
                }
            }

            if (cetTrainObj != null) {
                //throw new OpException("重复选课。");
                return;
            }

            CetTrainObj record = new CetTrainObj();
            record.setObjId(objId);
            record.setUserId(userId);
            record.setTrainCourseId(trainCourseId);
            record.setIsFinished(CetConstants.CET_FINISHED_STATUS_NOT);
            record.setCanQuit(canQuit);
            record.setChooseTime(now);
            record.setChooseUserId(ShiroHelper.getCurrentUserId());
            record.setIp(ip);

            cetTrainObjMapper.insertSelective(record);

            sysApprovalLogService.add(objId, userId,
                    isAdmin ? SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN
                            : SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_SELF,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_CET_OBJ,
                    remark + "(" + cetTrainCourse.getName() + ")", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                    cetTrainCourse.getName());
        } else {

            if (cetTrainObj == null) {
                throw new OpException("退课异常，已选课程中无此课程。");
            }

            String courseName = cetTrainCourse.getName();
            if (cetTrainObj.getIsFinished() == CetConstants.CET_FINISHED_STATUS_YES) {
                throw new OpException("[{0}]已完成，不可退课。", courseName);
            }

            if (!isAdmin && !cetTrainObj.getCanQuit()) {
                throw new OpException("[{0}]是必选课程，不可退课。", courseName);
            }

            if (!isAdmin && cetTrainCourse.getStartTime() != null) {
                if (cetTrainCourse.getStartTime().before(new Date())) {
                    throw new OpException("[{0}]已开课，不可退课。", courseName);
                }
            }

            cetTrainObjMapper.deleteByPrimaryKey(cetTrainObj.getId());

            sysApprovalLogService.add(objId, userId,
                    isAdmin ? SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN
                            : SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_SELF,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_CET_OBJ,
                    remark + "(" + cetTrainCourse.getName() + ")", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                    cetTrainCourse.getName());
        }

        // 更新选课人数
        iCetMapper.refreshTrainCourseSelectedCount(trainCourseId);
    }

    // 签到/还原
    @Transactional
    public void sign(Integer trainCourseId, Integer[] trainObjIds, boolean sign, byte signType, Date signTime) {

        List<CetTrainObj> cetTrainObjs = null;
        if (trainObjIds == null || trainObjIds.length == 0) { // 全部签到/全部还原
            CetTrainObjExample example = new CetTrainObjExample();
            example.createCriteria().andTrainCourseIdEqualTo(trainCourseId);
            cetTrainObjs = cetTrainObjMapper.selectByExample(example);
        } else {
            CetTrainObjExample example = new CetTrainObjExample();
            example.createCriteria().andIdIn(Arrays.asList(trainObjIds));
            cetTrainObjs = cetTrainObjMapper.selectByExample(example);
        }

        if (cetTrainObjs == null || cetTrainObjs.size() == 0) return;

        for (CetTrainObj cetTrainObj : cetTrainObjs) {

            int trainObjId = cetTrainObj.getId();
            int userId = cetTrainObj.getUserId();
            int objId = cetTrainObj.getObjId();
            trainCourseId = cetTrainObj.getTrainCourseId();

            CetTrainObj record = new CetTrainObj();
            record.setIsFinished(sign?CetConstants.CET_FINISHED_STATUS_YES:CetConstants.CET_FINISHED_STATUS_NOT);
            record.setSignType(signType);
            record.setSignTime(signTime);

            CetTrainObjExample example = new CetTrainObjExample();
            example.createCriteria().andIdEqualTo(trainObjId)
                    .andIsFinishedNotEqualTo(sign?CetConstants.CET_FINISHED_STATUS_YES:CetConstants.CET_FINISHED_STATUS_NOT); // 只对未签到/已签到的进行操作

            cetTrainObjMapper.updateByExampleSelective(record, example);
            commonMapper.excuteSql("update cet_train_obj set remark = null where id=" + trainObjId);

            if (!sign) {
                commonMapper.excuteSql("update cet_train_obj set sign_time=null, sign_type=null where id=" + trainObjId);
            }

            CetTrainCourse cetTrainCourse = cetTrainCourseMapper.selectByPrimaryKey(trainCourseId);
            sysApprovalLogService.add(objId, userId,
                    SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_CET_OBJ,
                    (sign ? "签到" : "还原") + "(" + cetTrainCourse.getName() + ")",
                    SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, cetTrainCourse.getName());
        }
        // 更新签到人数
        iCetMapper.refreshTrainCourseSelectedCount(trainCourseId);
    }

    // 批量签到
    @Transactional
    public Map<String, Object> signImport(int trainCourseId, List<Map<Integer, String>> xlsRows) {

        CetTrainCourse cetTrainCourse = cetTrainCourseMapper.selectByPrimaryKey(trainCourseId);
        int projectId = cetTrainCourse.getProjectId();
        String courseName = cetTrainCourse.getName();

        int success = 0;
        List<Map<Integer, String>> failedXlsRows = new ArrayList<>();
        for (Map<Integer, String> xlsRow : xlsRows) {

            String code = StringUtils.trim(xlsRow.get(0));
            if (StringUtils.isBlank(code)) continue;
            SysUserView uv = sysUserService.findByCode(code);
            if (uv == null) {
                failedXlsRows.add(xlsRow);
                continue;
            }
            int userId = uv.getId();
            CetProjectObj cetProjectObj = cetProjectObjService.get(userId, projectId);
            if (cetProjectObj == null) {
                failedXlsRows.add(xlsRow);
                continue;
            }

            CetTrainObj record = new CetTrainObj();
            record.setIsFinished(CetConstants.CET_FINISHED_STATUS_YES);
            record.setSignType(CetConstants.CET_TRAINEE_SIGN_TYPE_IMPORT);
            record.setSignTime(new Date());

            CetTrainObjExample example = new CetTrainObjExample();
            example.createCriteria().andUserIdEqualTo(userId)
                    .andTrainCourseIdEqualTo(trainCourseId);

            int ret = cetTrainObjMapper.updateByExampleSelective(record, example);
            if (ret == 1) {
                success++;
            } else {
                failedXlsRows.add(xlsRow);
            }

            CetTrainObjView cetTrainObjView = get(userId, trainCourseId);

            sysApprovalLogService.add(cetTrainObjView.getObjId(), userId,
                    SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_CET_OBJ,
                    "签到(导入)(" + cetTrainCourse.getName() + ")", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, courseName);
        }

        // 更新签到人数
        iCetMapper.refreshTrainCourseSelectedCount(trainCourseId);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success", success);
        resultMap.put("failedXlsRows", failedXlsRows);
        return resultMap;
    }
}
