package service.cet;

import controller.global.OpException;
import domain.cet.*;
import domain.sys.SysUserView;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.UnauthorizedException;
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

import java.util.*;

@Service
public class CetTraineeCourseService extends CetBaseMapper {

    @Autowired
    private CetTraineeService cetTraineeService;
    @Autowired
    private SysApprovalLogService sysApprovalLogService;
    @Autowired
    private SysUserService sysUserService;

    public CetTraineeCourse get(int traineeId, int trainCourseId) {

        CetTraineeCourseExample example = new CetTraineeCourseExample();
        example.createCriteria().andTraineeIdEqualTo(traineeId).andTrainCourseIdEqualTo(trainCourseId);
        List<CetTraineeCourse> cetTraineeCourses = cetTraineeCourseMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));

        return cetTraineeCourses.size() > 0 ? cetTraineeCourses.get(0) : null;
    }

    public CetTraineeCourseView getCetTraineeCourseView(int userId, int trainCourseId) {

        CetTraineeCourseViewExample example = new CetTraineeCourseViewExample();
        example.createCriteria().andUserIdEqualTo(userId).andTrainCourseIdEqualTo(trainCourseId);
        List<CetTraineeCourseView> cetTraineeCourses = cetTraineeCourseViewMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));

        return cetTraineeCourses.size() > 0 ? cetTraineeCourses.get(0) : null;
    }

    @Transactional
    public void insertSelective(CetTraineeCourse record) {

        cetTraineeCourseMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id) {

        cetTraineeCourseMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids) {

        if (ids == null || ids.length == 0) return;

        CetTraineeCourseExample example = new CetTraineeCourseExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cetTraineeCourseMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CetTraineeCourse record) {
        return cetTraineeCourseMapper.updateByPrimaryKeySelective(record);
    }

    //
    private void checkApplyIsOpen(boolean isApply, int trainId, int trainCourseId) {

        CetTrainCourse cetTrainCourse = cetTrainCourseMapper.selectByPrimaryKey(trainCourseId);
        byte applyStatus = cetTrainCourse.getApplyStatus();
        if(isApply && applyStatus == CetConstants.CET_TRAIN_COURSE_APPLY_STATUS_CLOSE_APPLY){
            throw new OpException("选课已关闭。");
        }else if(!isApply && applyStatus == CetConstants.CET_TRAIN_COURSE_APPLY_STATUS_CLOSE_QUIT){
            throw new OpException("退课已关闭。");
        }else if(applyStatus == CetConstants.CET_TRAIN_COURSE_APPLY_STATUS_CLOSE_ALL){
            throw new OpException("选课/退课已关闭。");
        }

        CetTrain cetTrain = cetTrainMapper.selectByPrimaryKey(trainId);

        Byte switchStatus = cetTrain.getSwitchStatus();
        if(switchStatus != CetConstants.CET_TRAIN_ENROLL_STATUS_OPEN){

            String str = "未开启选课";
            switch (switchStatus){

                case CetConstants.CET_TRAIN_ENROLL_STATUS_NOT_BEGIN:
                    str = "未开始选课";
                    break;
                case CetConstants.CET_TRAIN_ENROLL_STATUS_PAUSE:
                    str = "已暂停选课";
                    break;
                case CetConstants.CET_TRAIN_ENROLL_STATUS_CLOSED:
                    str = "选课已结束";
                    break;
            }

            throw new OpException(str);
        }
    }

    // 参训人课程列表（用于网页、手机选课页面）
    public void trainDetail(int trainId, ModelMap modelMap){

        CetTrain cetTrain = cetTrainMapper.selectByPrimaryKey(trainId);
        modelMap.put("cetTrain", cetTrain);
        Integer planId = cetTrain.getPlanId();
        CetProjectPlan cetProjectPlan = cetProjectPlanMapper.selectByPrimaryKey(planId);
        modelMap.put("cetProjectPlan", cetProjectPlan);
        CetProject cetProject = cetProjectMapper.selectByPrimaryKey(cetProjectPlan.getProjectId());
        modelMap.put("cetProject", cetProject);

        int userId = ShiroHelper.getCurrentUserId();
        CetTraineeView cetTrainee = cetTraineeService.get(userId, trainId);
        if (cetTrainee == null) {
            throw new UnauthorizedException();
        }
        modelMap.put("cetTrainee", cetTrainee);

        int traineeId = cetTrainee.getId();

        List<ICetTrainCourse> selectedCetTrainCourses = iCetMapper.selectedCetTrainCourses(traineeId);
        List<CetTrainCourseView> unSelectedCetTrainCourses = iCetMapper.unSelectedCetTrainCourses(trainId, traineeId);

        modelMap.put("selectedCetTrainCourses", selectedCetTrainCourses);
        modelMap.put("unSelectedCetTrainCourses", unSelectedCetTrainCourses);
    }

    //参训人员报名
    /*@Transactional
    public void apply(int userId, int trainId, Integer[] trainCourseIds) {

        if (trainCourseIds == null || trainCourseIds.length == 0) return;

        checkApplyIsOpen(trainId);
        CetTraineeView cetTrainee = cetTraineeService.createIfNotExist(userId, trainId);
        int traineeId = cetTrainee.getId();

        Date now = new Date();
        String ip = ContextHelper.getRealIp();
        Integer currentUserId = ShiroHelper.getCurrentUserId();
        for (Integer trainCourseId : trainCourseIds) {

            CetTraineeCourse record = new CetTraineeCourse();
            record.setTraineeId(traineeId);
            record.setTrainCourseId(trainCourseId);
            record.setIsFinished(false);
            record.setChooseTime(now);
            record.setChooseUserId(currentUserId);
            record.setIp(ip);

            cetTraineeCourseMapper.insertSelective(record);
        }

        sysApprovalLogService.add(traineeId, userId,
                SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_SELF,
                SystemConstants.SYS_APPROVAL_LOG_TYPE_CET_TRAINEE,
                "报名", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                MessageFormat.format("已选{0}门课", trainCourseIds.length));
    }*/

    // 选课/退课
    @Transactional
    public void applyItem(int userId, int trainCourseId, boolean isApply, boolean isAdmin, boolean canQuit, String remark) {

        CetTrainCourse cetTrainCourse = cetTrainCourseMapper.selectByPrimaryKey(trainCourseId);
        int trainId = cetTrainCourse.getTrainId();

        if (!isAdmin) {
            // 非管理员选课
            checkApplyIsOpen(isApply, trainId, trainCourseId);
        }

        CetTraineeView cetTrainee = cetTraineeService.get(userId, trainId);
        if (cetTrainee == null) return;
        int traineeId = cetTrainee.getId();
        Date now = new Date();
        String ip = ContextHelper.getRealIp();

        CetTraineeCourse cetTraineeCourse = get(traineeId, trainCourseId);

        if (isApply) {

            if (!isAdmin) {
                // 判断是否超出选课人数上限
                Integer applyLimit = cetTrainCourse.getApplyLimit();
                List<Integer> applyUserIds = iCetMapper.applyUserIds(trainCourseId);
                if (applyLimit != null && applyUserIds.size() >= applyLimit) {
                    throw new OpException("已超出选课人数上限。");
                }
            }

            if (cetTraineeCourse != null) {
                throw new OpException("重复选课。");
            }

            CetTraineeCourse record = new CetTraineeCourse();
            record.setTraineeId(traineeId);
            record.setTrainCourseId(trainCourseId);
            record.setIsFinished(false);
            record.setCanQuit(canQuit);
            record.setChooseTime(now);
            record.setChooseUserId(ShiroHelper.getCurrentUserId());
            record.setIp(ip);

            cetTraineeCourseMapper.insertSelective(record);

            if (cetTrainee.getIsQuit()) {
                CetTrainee _record = new CetTrainee();
                _record.setId(traineeId);
                _record.setIsQuit(false);
                cetTraineeMapper.updateByPrimaryKeySelective(_record);
            }

            sysApprovalLogService.add(traineeId, userId,
                    isAdmin ? SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN
                            : SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_SELF,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_CET_TRAINEE,
                    remark, SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                    cetTrainCourse.getCetCourse().getName());
        } else {

            if (cetTraineeCourse == null) {
                throw new OpException("退课异常，已选课程中无此课程。");
            }

            String courseName = cetTrainCourse.getCetCourse().getName();
            if (cetTraineeCourse.getIsFinished()) {
                throw new OpException("[{0}]已完成，不可退课。", courseName);
            }

            if (!isAdmin && !cetTraineeCourse.getCanQuit()) {
                throw new OpException("[{0}]是必选课程，不可退课。", courseName);
            }

            if (!isAdmin && cetTrainCourse.getStartTime() != null) {
                if (cetTrainCourse.getStartTime().before(new Date())) {
                    throw new OpException("[{0}]已开课，不可退课。", courseName);
                }
            }

            cetTraineeCourseMapper.deleteByPrimaryKey(cetTraineeCourse.getId());

            List<ICetTrainCourse> selectedCetTrainCourses = iCetMapper.selectedCetTrainCourses(traineeId);
            if (selectedCetTrainCourses.size() == 0) {
                // 退出培训班
                CetTrainee record = new CetTrainee();
                record.setId(traineeId);
                record.setIsQuit(true);
                cetTraineeMapper.updateByPrimaryKeySelective(record);
            }
            sysApprovalLogService.add(traineeId, userId,
                    isAdmin ? SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN
                            : SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_SELF,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_CET_TRAINEE,
                    remark, SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                    cetTrainCourse.getCetCourse().getName());
        }
    }

    // 退出培训班
    @Transactional
    public void quit(int userId, int traineeId) {

        CetTrainee cetTrainee = cetTraineeMapper.selectByPrimaryKey(traineeId);
        CetProjectObj cetProjectObj = cetProjectObjMapper.selectByPrimaryKey(cetTrainee.getObjId());
        if (cetProjectObj.getUserId() != userId) {
            throw new UnauthorizedException();
        }

        List<ICetTrainCourse> selectedCetTrainCourses = iCetMapper.selectedCetTrainCourses(traineeId);

        for (CetTrainCourse tc : selectedCetTrainCourses) {

            int trainCourseId = tc.getId();
            CetTraineeCourse ctee = get(traineeId, trainCourseId);

            if (ctee.getIsFinished()) {
                throw new OpException("您已经完成培训课程[{0}]，不可退出培训班。", tc.getCetCourse().getName());
            }
            if (!ctee.getCanQuit()) {
                throw new OpException("培训课程[{0}]是必选课程，不可退出培训班。", tc.getCetCourse().getName());
            }

            //退课
            applyItem(userId, trainCourseId, false, false, true, "退课");
        }

        sysApprovalLogService.add(traineeId, userId,
                SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_SELF,
                SystemConstants.SYS_APPROVAL_LOG_TYPE_CET_TRAINEE,
                "退出培训班", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                "");
    }

    // 签到/还原
    @Transactional
    public void sign(Integer trainCourseId, Integer[] traineeCourseIds, boolean sign, byte signType, Date signTime) {

        List<CetTraineeCourse> cetTraineeCourses = null;
        if(traineeCourseIds==null || traineeCourseIds.length==0){ // 全部签到/全部还原
            CetTraineeCourseExample example = new CetTraineeCourseExample();
            example.createCriteria().andTrainCourseIdEqualTo(trainCourseId);
            cetTraineeCourses = cetTraineeCourseMapper.selectByExample(example);
        }else{
            CetTraineeCourseExample example = new CetTraineeCourseExample();
            example.createCriteria().andIdIn(Arrays.asList(traineeCourseIds));
            cetTraineeCourses = cetTraineeCourseMapper.selectByExample(example);
        }

        if(cetTraineeCourses==null || cetTraineeCourses.size()==0) return;

        for (CetTraineeCourse cetTraineeCourse : cetTraineeCourses) {

            Integer traineeCourseId = cetTraineeCourse.getId();
            int traineeId = cetTraineeCourse.getTraineeId();
            CetTrainee cetTrainee = cetTraineeMapper.selectByPrimaryKey(traineeId);
            CetProjectObj cetProjectObj = cetProjectObjMapper.selectByPrimaryKey(cetTrainee.getObjId());
            int userId = cetProjectObj.getUserId();
            String courseName = cetTraineeCourse.getCetTrainCourse().getCetCourse().getName();

            CetTraineeCourse record = new CetTraineeCourse();
            record.setIsFinished(sign);
            record.setSignType(signType);
            record.setSignTime(signTime);

            CetTraineeCourseExample example = new CetTraineeCourseExample();
            example.createCriteria().andIdEqualTo(traineeCourseId)
                    .andIsFinishedNotEqualTo(sign); // 只对未签到/已签到的进行操作

            cetTraineeCourseMapper.updateByExampleSelective(record, example);

            if (!sign) {
                commonMapper.excuteSql("update cet_trainee_course set sign_time=null, sign_type=null where id="
                        + traineeCourseId);
            }

            sysApprovalLogService.add(traineeId, userId,
                    SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_CET_TRAINEE,
                    sign ? "签到" : "还原", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, courseName);
        }
    }

    // 批量签到
    @Transactional
    public Map<String, Object> signImport(int trainCourseId, List<Map<Integer, String>> xlsRows) {

        CetTrainCourse cetTrainCourse = cetTrainCourseMapper.selectByPrimaryKey(trainCourseId);
        Integer trainId = cetTrainCourse.getTrainId();
        String courseName = cetTrainCourse.getCetCourse().getName();

        int success = 0;
        List<Map<Integer, String>> failedXlsRows = new ArrayList<>();
        for (Map<Integer, String> xlsRow : xlsRows) {

            String code = StringUtils.trim(xlsRow.get(0));
            if (StringUtils.isBlank(code)) continue;
            SysUserView uv = sysUserService.findByCode(code);
            if (uv == null){
                failedXlsRows.add(xlsRow);
                continue;
            }
            int userId = uv.getId();
            CetTraineeView cetTrainee = cetTraineeService.get(userId, trainId);
            if (cetTrainee == null){
                failedXlsRows.add(xlsRow);
                continue;
            }
            int traineeId = cetTrainee.getId();

            CetTraineeCourse record = new CetTraineeCourse();
            record.setIsFinished(true);
            record.setSignType(CetConstants.CET_TRAINEE_SIGN_TYPE_IMPORT);
            record.setSignTime(new Date());

            CetTraineeCourseExample example = new CetTraineeCourseExample();
            example.createCriteria().andTraineeIdEqualTo(traineeId)
                    .andTrainCourseIdEqualTo(trainCourseId);

            int ret = cetTraineeCourseMapper.updateByExampleSelective(record, example);
            if(ret==1) {
                success++;
            }else{
                failedXlsRows.add(xlsRow);
            }

            sysApprovalLogService.add(traineeId, userId,
                    SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_CET_TRAINEE,
                    "签到(导入)", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, courseName);
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success", success);
        resultMap.put("failedXlsRows", failedXlsRows);
        return resultMap;
    }
}
