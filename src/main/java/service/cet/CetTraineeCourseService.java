package service.cet;

import controller.global.OpException;
import domain.cet.CetProjectObj;
import domain.cet.CetTrain;
import domain.cet.CetTrainCourse;
import domain.cet.CetTrainee;
import domain.cet.CetTraineeCourse;
import domain.cet.CetTraineeCourseExample;
import domain.cet.CetTraineeCourseView;
import domain.cet.CetTraineeCourseViewExample;
import domain.cet.CetTraineeView;
import domain.sys.SysUserView;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.sys.SysApprovalLogService;
import service.sys.SysUserService;
import shiro.ShiroHelper;
import sys.constants.CetConstants;
import sys.constants.SystemConstants;
import sys.utils.ContextHelper;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CetTraineeCourseService extends BaseMapper {

    @Autowired
    private CetTraineeService cetTraineeService;
    @Autowired
    private SysApprovalLogService sysApprovalLogService;
    @Autowired
    private SysUserService sysUserService;

    public CetTraineeCourse get(int traineeId, int trainCourseId){

        CetTraineeCourseExample example = new CetTraineeCourseExample();
        example.createCriteria().andTraineeIdEqualTo(traineeId).andTrainCourseIdEqualTo(trainCourseId);
        List<CetTraineeCourse> cetTraineeCourses = cetTraineeCourseMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));

        return cetTraineeCourses.size()>0?cetTraineeCourses.get(0):null;
    }

    public CetTraineeCourseView getCetTraineeCourseView(int userId, int trainCourseId){

        CetTraineeCourseViewExample example = new CetTraineeCourseViewExample();
        example.createCriteria().andUserIdEqualTo(userId).andTrainCourseIdEqualTo(trainCourseId);
        List<CetTraineeCourseView> cetTraineeCourses = cetTraineeCourseViewMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));

        return cetTraineeCourses.size()>0?cetTraineeCourses.get(0):null;
    }

    @Transactional
    public void insertSelective(CetTraineeCourse record){

        cetTraineeCourseMapper.insertSelective(record);
    }

    @Transactional
    public void del(Integer id){

        cetTraineeCourseMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        CetTraineeCourseExample example = new CetTraineeCourseExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        cetTraineeCourseMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(CetTraineeCourse record){
        return cetTraineeCourseMapper.updateByPrimaryKeySelective(record);
    }

    //
    private void checkApplyIsOpen(int trainId){

        CetTrain cetTrain = cetTrainMapper.selectByPrimaryKey(trainId);
        if(cetTrain.getPubStatus()!=CetConstants.CET_TRAIN_PUB_STATUS_PUBLISHED ||
                cetTrain.getSwitchStatus() != CetConstants.CET_TRAIN_ENROLL_STATUS_OPEN){
            throw new OpException("未开启选课");
        }
    }

    //参训人员报名
    @Transactional
    public void apply(int userId, int trainId, Integer[] trainCourseIds) {

        if(trainCourseIds==null || trainCourseIds.length==0) return;

        checkApplyIsOpen(trainId);
        CetTraineeView cetTrainee = cetTraineeService.createIfNotExist(userId, trainId);
        int traineeId = cetTrainee.getId();

        Date now = new Date();
        String ip = ContextHelper.getRealIp();

        for (Integer trainCourseId : trainCourseIds) {

            CetTraineeCourse record = new CetTraineeCourse();
            record.setTraineeId(traineeId);
            record.setTrainCourseId(trainCourseId);
            record.setIsFinished(false);
            record.setChooseTime(now);
            record.setIp(ip);

            cetTraineeCourseMapper.insertSelective(record);
        }

        sysApprovalLogService.add(traineeId, userId,
                SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_SELF,
                SystemConstants.SYS_APPROVAL_LOG_TYPE_CET_TRAINEE,
                "报名", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                MessageFormat.format("已选{0}门课", trainCourseIds.length));
    }

    // 选课/退课
    @Transactional
    public void applyItem(int userId, int trainCourseId, boolean isApply, boolean isAdmin, String remark) {

        CetTrainCourse cetTrainCourse = cetTrainCourseMapper.selectByPrimaryKey(trainCourseId);
        int trainId = cetTrainCourse.getTrainId();

        if(!isAdmin){
            // 非管理员选课
            checkApplyIsOpen(trainId);
        }

        CetTraineeView cetTrainee = cetTraineeService.get(userId, trainId);
        if(cetTrainee==null) return;
        int traineeId = cetTrainee.getId();
        Date now = new Date();
        String ip = ContextHelper.getRealIp();

        CetTraineeCourse cetTraineeCourse = get(traineeId, trainCourseId);

        if(isApply){

            if(cetTraineeCourse!=null){
                throw new OpException("重复选课。");
            }

            CetTraineeCourse record = new CetTraineeCourse();
            record.setTraineeId(traineeId);
            record.setTrainCourseId(trainCourseId);
            record.setIsFinished(false);
            record.setCanQuit(!isAdmin);
            record.setChooseTime(now);
            record.setChooseUserId(ShiroHelper.getCurrentUserId());
            record.setIp(ip);

            cetTraineeCourseMapper.insertSelective(record);

            if(cetTrainee.getIsQuit()){
                CetTrainee _record = new CetTrainee();
                _record.setId(traineeId);
                _record.setIsQuit(false);
                cetTraineeMapper.updateByPrimaryKeySelective(_record);
            }

            sysApprovalLogService.add(traineeId, userId,
                    isAdmin?SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN
                            :SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_SELF,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_CET_TRAINEE,
                    remark, SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                    cetTrainCourse.getCetCourse().getName());
        }else{

            if(cetTraineeCourse==null){
                throw new OpException("课程不存在。");
            }

            String courseName = cetTrainCourse.getCetCourse().getName();
            if(cetTraineeCourse.getIsFinished()){
                throw new OpException("[{0}]已完成，不可退课。", courseName);
            }

            if(!isAdmin && !cetTraineeCourse.getCanQuit()){
                throw new OpException("[{0}]是必选课程，不可退课。", courseName);
            }

            cetTraineeCourseMapper.deleteByPrimaryKey(cetTraineeCourse.getId());

            List<CetTrainCourse> selectedCetTrainCourses = iCetMapper.selectedCetTrainCourses(traineeId);
            if(selectedCetTrainCourses.size()==0){
                // 退出培训班
                CetTrainee record = new CetTrainee();
                record.setId(traineeId);
                record.setIsQuit(true);
                cetTraineeMapper.updateByPrimaryKeySelective(record);
            }
            sysApprovalLogService.add(traineeId, userId,
                    isAdmin?SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN
                            :SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_SELF,
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
        if(cetProjectObj.getUserId() != userId){
            throw new UnauthorizedException();
        }

        checkApplyIsOpen(cetTrainee.getTrainId());

        List<CetTrainCourse> selectedCetTrainCourses = iCetMapper.selectedCetTrainCourses(traineeId);

        for (CetTrainCourse tc : selectedCetTrainCourses) {

            int trainCourseId = tc.getId();
            CetTraineeCourse ctee = get(traineeId, trainCourseId);

            if(ctee.getIsFinished()){
                throw new OpException("您已经完成培训课程[{0}]，不可退出培训班。", tc.getCetCourse().getName());
            }
            if(!ctee.getCanQuit()){
                throw new OpException("培训课程[{0}]是必选课程，不可退出培训班。", tc.getCetCourse().getName());
            }

            //退课
            applyItem(userId, trainCourseId, false, false, "退课");
        }

        sysApprovalLogService.add(traineeId, userId,
                SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_SELF,
                SystemConstants.SYS_APPROVAL_LOG_TYPE_CET_TRAINEE,
                "退出培训班", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                "");
    }

    // 签到/还原
    @Transactional
    public void sign(Integer[] traineeCourseIds, boolean sign, byte signType) {

        for (Integer traineeCourseId : traineeCourseIds) {

            CetTraineeCourse cetTraineeCourse = cetTraineeCourseMapper.selectByPrimaryKey(traineeCourseId);
            int traineeId = cetTraineeCourse.getTraineeId();
            CetTrainee cetTrainee = cetTraineeMapper.selectByPrimaryKey(traineeId);
            CetProjectObj cetProjectObj = cetProjectObjMapper.selectByPrimaryKey(cetTrainee.getObjId());
            int userId = cetProjectObj.getUserId();
            String courseName = cetTraineeCourse.getCetTrainCourse().getCetCourse().getName();

            CetTraineeCourse record = new CetTraineeCourse();
            record.setIsFinished(sign);
            record.setSignType(signType);
            record.setSignTime(new Date());

            CetTraineeCourseExample example = new CetTraineeCourseExample();
            example.createCriteria().andIdEqualTo(traineeCourseId).andIsFinishedNotEqualTo(sign);
            cetTraineeCourseMapper.updateByExampleSelective(record, example);

            if(!sign){
                commonMapper.excuteSql("update cet_trainee_course set sign_time=null, sign_type=null where id="
                        + traineeCourseId);
            }

            sysApprovalLogService.add(traineeId, userId,
                    SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_CET_TRAINEE,
                    sign?"签到":"还原", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, courseName);
        }
    }

    // 批量签到
    @Transactional
    public int signImport(int trainCourseId, List<Map<Integer, String>> xlsRows) {

        CetTrainCourse cetTrainCourse = cetTrainCourseMapper.selectByPrimaryKey(trainCourseId);
        Integer trainId = cetTrainCourse.getTrainId();
        String courseName = cetTrainCourse.getCetCourse().getName();

        int success = 0;
        for (Map<Integer, String> xlsRow : xlsRows) {

            String code = StringUtils.trim(xlsRow.get(0));
            if(StringUtils.isBlank(code)) continue;
            SysUserView uv = sysUserService.findByCode(code);
            if(uv==null) continue;
            int userId = uv.getId();
            CetTraineeView cetTrainee = cetTraineeService.get(userId, trainId);
            if(cetTrainee==null) continue;
            int traineeId = cetTrainee.getId();

            CetTraineeCourse record = new CetTraineeCourse();
            record.setIsFinished(true);
            record.setSignType(CetConstants.CET_TRAINEE_SIGN_TYPE_IMPORT);
            record.setSignTime(new Date());

            CetTraineeCourseExample example = new CetTraineeCourseExample();
            example.createCriteria().andTraineeIdEqualTo(traineeId)
                    .andTrainCourseIdEqualTo(trainCourseId).andIsFinishedEqualTo(false);
            success += cetTraineeCourseMapper.updateByExampleSelective(record, example);

            sysApprovalLogService.add(traineeId, userId,
                    SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_ADMIN,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_CET_TRAINEE,
                    "签到(导入)", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED, courseName);
        }

        return success;
    }
}
