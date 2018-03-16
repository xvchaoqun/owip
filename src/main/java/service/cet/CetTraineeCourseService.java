package service.cet;

import controller.global.OpException;
import domain.cet.CetTrain;
import domain.cet.CetTrainCourse;
import domain.cet.CetTrainee;
import domain.cet.CetTraineeCourse;
import domain.cet.CetTraineeCourseExample;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.sys.SysApprovalLogService;
import sys.constants.CetConstants;
import sys.constants.SystemConstants;
import sys.utils.ContextHelper;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class CetTraineeCourseService extends BaseMapper {

    @Autowired
    private CetTraineeService cetTraineeService;
    @Autowired
    private SysApprovalLogService sysApprovalLogService;

    public CetTraineeCourse get(int traineeId, int trainCourseId){

        CetTraineeCourseExample example = new CetTraineeCourseExample();
        example.createCriteria().andTraineeIdEqualTo(traineeId).andTrainCourseIdEqualTo(trainCourseId);
        List<CetTraineeCourse> cetTraineeCourses = cetTraineeCourseMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));

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

    // 更新已选课程数量
    private int updateCourseCount(int traineeId){

        CetTraineeCourseExample example = new CetTraineeCourseExample();
        example.createCriteria().andTraineeIdEqualTo(traineeId);
        int courseCount = (int)cetTraineeCourseMapper.countByExample(example);

        CetTrainee record = new CetTrainee();
        record.setId(traineeId);
        record.setCourseCount(courseCount);
        return cetTraineeMapper.updateByPrimaryKeySelective(record);
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

        CetTrainee cetTrainee = cetTraineeService.get(userId, trainId);
        if(cetTrainee==null) return;
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

        updateCourseCount(traineeId);
    }

    // 选课/退课
    @Transactional
    public void applyItem(int userId, int trainCourseId, boolean isApply) {

        CetTrainCourse cetTrainCourse = cetTrainCourseMapper.selectByPrimaryKey(trainCourseId);
        int trainId = cetTrainCourse.getTrainId();

        checkApplyIsOpen(trainId);

        CetTrainee cetTrainee = cetTraineeService.get(userId, trainId);
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
            record.setChooseTime(now);
            record.setIp(ip);

            cetTraineeCourseMapper.insertSelective(record);

            sysApprovalLogService.add(traineeId, userId,
                    SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_SELF,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_CET_TRAINEE,
                    "选课", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                    cetTrainCourse.getCetCourse().getName());
        }else{

            if(cetTraineeCourse==null){
                throw new OpException("课程不存在。");
            }

            if(cetTraineeCourse.getIsFinished()){
                throw new OpException("课程已完成，不可退课。");
            }

            cetTraineeCourseMapper.deleteByPrimaryKey(cetTraineeCourse.getId());

            sysApprovalLogService.add(traineeId, userId,
                    SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_SELF,
                    SystemConstants.SYS_APPROVAL_LOG_TYPE_CET_TRAINEE,
                    "退课", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                    cetTrainCourse.getCetCourse().getName());
        }

        updateCourseCount(traineeId);
    }

    // 退出培训班
    @Transactional
    public void quit(int userId, int traineeId) {

        CetTrainee cetTrainee = cetTraineeMapper.selectByPrimaryKey(traineeId);
        if(cetTrainee.getUserId() != userId){
            throw new UnauthorizedException();
        }

        checkApplyIsOpen(cetTrainee.getTrainId());

        List<CetTraineeCourse> selectedCetTraineeCourses = iCetMapper.selectedCetTraineeCourses(traineeId);

        for (CetTraineeCourse tc : selectedCetTraineeCourses) {

            if(tc.getIsFinished()){
                throw new OpException("您已经完成培训课程[{0}]，不可退出培训班。");
            }

            //退课
            applyItem(userId, tc.getTrainCourseId(), false);
        }

        sysApprovalLogService.add(traineeId, userId,
                SystemConstants.SYS_APPROVAL_LOG_USER_TYPE_SELF,
                SystemConstants.SYS_APPROVAL_LOG_TYPE_CET_TRAINEE,
                "退出培训班", SystemConstants.SYS_APPROVAL_LOG_STATUS_NONEED,
                "");
    }
}
