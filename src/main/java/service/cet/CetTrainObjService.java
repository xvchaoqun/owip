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

import java.util.*;

@Service
public class CetTrainObjService extends CetBaseMapper {

    @Autowired
    private SysApprovalLogService sysApprovalLogService;
    @Autowired
    private SysUserService sysUserService;

    public CetTrainObjView get(int userId, int trainCourseId) {

        CetTrainObjViewExample example = new CetTrainObjViewExample();
        example.createCriteria().andUserIdEqualTo(userId).andTrainCourseIdEqualTo(trainCourseId);
        List<CetTrainObjView> cetTrainObjViews = cetTrainObjViewMapper
                .selectByExampleWithRowbounds(example, new RowBounds(0, 1));

        return cetTrainObjViews.size() > 0 ? cetTrainObjViews.get(0) : null;
    }

    public CetTrainObjView getCetTrainObjView(int userId, int trainCourseId) {

        CetTrainObjViewExample example = new CetTrainObjViewExample();
        example.createCriteria().andUserIdEqualTo(userId).andTrainCourseIdEqualTo(trainCourseId);
        List<CetTrainObjView> cetTrainObjs = cetTrainObjViewMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));

        return cetTrainObjs.size() > 0 ? cetTrainObjs.get(0) : null;
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

        List<ICetTrainCourse> selectedCetTrainCourses = iCetMapper.selectedCetTrainCourses(trainId, userId);
        List<CetTrainCourse> unSelectedCetTrainCourses = iCetMapper.unSelectedCetTrainCourses(trainId, userId);

        modelMap.put("selectedCetTrainCourses", selectedCetTrainCourses);
        modelMap.put("unSelectedCetTrainCourses", unSelectedCetTrainCourses);
    }

    // 选课/退课
    @Transactional
    public void applyItem(int userId, int trainCourseId, boolean isApply, boolean isAdmin, boolean canQuit, String remark) {

        CetTrainCourse cetTrainCourse = cetTrainCourseMapper.selectByPrimaryKey(trainCourseId);
        int trainId = cetTrainCourse.getTrainId();

        if (!isAdmin) {
            // 非管理员选课
            checkApplyIsOpen(isApply, trainId, trainCourseId);
        }

        CetProjectObj cetProjectObj = iCetMapper.getCetProjectObj(userId, trainId);
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
                throw new OpException("重复选课。");
            }

            CetTrainObj record = new CetTrainObj();
            record.setTrainId(trainId);
            record.setObjId(objId);
            record.setUserId(userId);
            record.setTrainCourseId(trainCourseId);
            record.setIsFinished(false);
            record.setCanQuit(canQuit);
            record.setChooseTime(now);
            record.setChooseUserId(ShiroHelper.getCurrentUserId());
            record.setIp(ip);

            cetTrainObjMapper.insertSelective(record);

            sysApprovalLogService.add(objId, userId,
                    isAdmin ? SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN
                            : SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_SELF,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_CET_OBJ,
                    remark+"("+cetTrainCourse.getName()+")", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                    cetTrainCourse.getCetCourse().getName());
        } else {

            if (cetTrainObj == null) {
                throw new OpException("退课异常，已选课程中无此课程。");
            }

            String courseName = cetTrainCourse.getCetCourse().getName();
            if (cetTrainObj.getIsFinished()) {
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
                    remark+"("+cetTrainCourse.getName()+")", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                    cetTrainCourse.getCetCourse().getName());
        }

        // 更新选课人数
        iCetMapper.refreshTrainCourseSelectedCount(trainCourseId);
    }

    // 签到/还原
    @Transactional
    public void sign(Integer trainCourseId, Integer[] trainObjIds, boolean sign, byte signType, Date signTime) {

        List<CetTrainObj> cetTrainObjs = null;
        if(trainObjIds==null || trainObjIds.length==0){ // 全部签到/全部还原
            CetTrainObjExample example = new CetTrainObjExample();
            example.createCriteria().andTrainCourseIdEqualTo(trainCourseId);
            cetTrainObjs = cetTrainObjMapper.selectByExample(example);
        }else{
            CetTrainObjExample example = new CetTrainObjExample();
            example.createCriteria().andIdIn(Arrays.asList(trainObjIds));
            cetTrainObjs = cetTrainObjMapper.selectByExample(example);
        }

        if(cetTrainObjs==null || cetTrainObjs.size()==0) return;

        for (CetTrainObj cetTrainObj : cetTrainObjs) {

            int trainObjId = cetTrainObj.getId();
            int userId = cetTrainObj.getUserId();
            int objId = cetTrainObj.getObjId();

            CetTrainObj record = new CetTrainObj();
            record.setIsFinished(sign);
            record.setSignType(signType);
            record.setSignTime(signTime);

            CetTrainObjExample example = new CetTrainObjExample();
            example.createCriteria().andIdEqualTo(trainObjId)
                    .andIsFinishedNotEqualTo(sign); // 只对未签到/已签到的进行操作

            cetTrainObjMapper.updateByExampleSelective(record, example);

            if (!sign) {
                commonMapper.excuteSql("update cet_train_obj set sign_time=null, sign_type=null where id=" + trainObjId);
            }

            CetTrainCourse cetTrainCourse = cetTrainCourseMapper.selectByPrimaryKey(trainCourseId);
            sysApprovalLogService.add(objId, userId,
                    SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_CET_OBJ,
                    (sign ? "签到" : "还原") + "("+cetTrainCourse.getName()+")",
                    SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, cetTrainCourse.getName());
        }
        // 更新签到人数
        iCetMapper.refreshTrainCourseSelectedCount(trainCourseId);
    }

    // 批量签到
    @Transactional
    public Map<String, Object> signImport(int trainCourseId, List<Map<Integer, String>> xlsRows) {

        CetTrainCourse cetTrainCourse = cetTrainCourseMapper.selectByPrimaryKey(trainCourseId);
        int trainId = cetTrainCourse.getTrainId();
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
            CetProjectObj cetProjectObj = iCetMapper.getCetProjectObj(userId, trainId);
            if (cetProjectObj == null){
                failedXlsRows.add(xlsRow);
                continue;
            }

            CetTrainObj record = new CetTrainObj();
            record.setIsFinished(true);
            record.setSignType(CetConstants.CET_TRAINEE_SIGN_TYPE_IMPORT);
            record.setSignTime(new Date());

            CetTrainObjExample example = new CetTrainObjExample();
            example.createCriteria().andUserIdEqualTo(userId)
                    .andTrainCourseIdEqualTo(trainCourseId);

            int ret = cetTrainObjMapper.updateByExampleSelective(record, example);
            if(ret==1) {
                success++;
            }else{
                failedXlsRows.add(xlsRow);
            }

            CetTrainObjView cetTrainObjView = get(userId, trainCourseId);

            sysApprovalLogService.add(cetTrainObjView.getObjId(), userId,
                    SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_CET_OBJ,
                    "签到(导入)("+cetTrainCourse.getName()+")", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, courseName);
        }

        // 更新签到人数
        iCetMapper.refreshTrainCourseSelectedCount(trainCourseId);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success", success);
        resultMap.put("failedXlsRows", failedXlsRows);
        return resultMap;
    }
}
