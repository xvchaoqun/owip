package service.cet;

import bean.ShortMsgBean;
import domain.base.ContentTpl;
import domain.cet.CetCourse;
import domain.cet.CetProject;
import domain.cet.CetProjectExample;
import domain.cet.CetProjectObj;
import domain.cet.CetShortMsg;
import domain.cet.CetTrainCourse;
import domain.cet.CetTrainee;
import domain.cet.CetTraineeCourse;
import domain.cet.CetTraineeCourseExample;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.base.ContentTplService;
import service.base.ShortMsgService;
import service.sys.UserBeanService;
import sys.constants.CetConstants;
import sys.constants.ContentTplConstants;
import sys.constants.SystemConstants;
import sys.utils.DateUtils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class CetShortMsgService extends BaseMapper {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected ContentTplService contentTplService;
    @Autowired
    protected UserBeanService userBeanService;
    @Autowired
    protected ShortMsgService shortMsgService;
    @Autowired
    protected CetCourseService cetCourseService;
    @Autowired
    protected CetTraineeCourseService cetTraineeCourseService;

    /**
     * 通知2： 每堂课开课前通知
     *
     * 各位老师： 您好！ 您所选的[“一带一路”与全球化转型]课程将于2018年3月19日15:00在京师大
        厦三层第六会议室开课， 请安排好工作按时上课。 联系电话： 58806798。 谢谢！ [系统短信， 请勿直接回复]
     */
    @Transactional
    public boolean sendMsg_2(int traineeId, int trainCourseId) {

        ContentTpl tpl = shortMsgService.getTpl(ContentTplConstants.CONTENT_TPL_CET_MSG_2);
        if (tpl == null) return false;

        CetTrainee cetTrainee = cetTraineeMapper.selectByPrimaryKey(traineeId);
        if (cetTrainee == null) return false;
        CetProjectObj cetProjectObj = cetProjectObjMapper.selectByPrimaryKey(cetTrainee.getObjId());
        if (cetProjectObj == null) return false;
        CetTraineeCourse cetTraineeCourse = cetTraineeCourseService.get(traineeId, trainCourseId);
        // 已签到则不发
        if(cetTraineeCourse==null || cetTraineeCourse.getIsFinished()) return false;

        CetTrainCourse cetTrainCourse = cetTraineeCourse.getCetTrainCourse();
        if(cetTrainCourse==null && cetTrainCourse.getStartTime().after(new Date())) return false;

        String startTime = DateUtils.formatDate(cetTrainCourse.getStartTime(), "MM月dd日 HH:mm");
        String address = cetTrainCourse.getAddress();
        int trainId = cetTrainCourse.getTrainId();

        CetCourse cetCourse = cetTrainCourse.getCetCourse();
        if(cetCourse==null) return false;
        String courseName = cetCourse.getName();

        String msg = MessageFormat.format(tpl.getContent(),
                courseName, startTime, address);
        if (StringUtils.isBlank(msg)) return false;

        ShortMsgBean bean = new ShortMsgBean();
        bean.setSender(null);
        bean.setRelateType(SystemConstants.SHORT_MSG_RELATE_TYPE_CONTENT_TPL);
        bean.setRelateId(tpl.getId());
        bean.setType(tpl.getName());
        bean.setContent(msg);

        CetShortMsg csm = new CetShortMsg();
        csm.setContentTplId(tpl.getId());
        csm.setTplKey(tpl.getCode());
        csm.setRecordId(trainId);
        csm.setMsg(msg);

        int userId = cetProjectObj.getUserId();
        bean.setReceiver(userId);
        String mobile = userBeanService.getMsgMobile(userId);
        bean.setMobile(mobile);
        boolean success = false;
        try {
            success = shortMsgService.send(bean, "127.0.0.1");
        }catch (Exception ex){
            logger.error("干部教育培训短信发送失败", ex);
        }

        csm.setSendTime(new Date());
        csm.setUserId(userId);
        csm.setSuccess(success);
        cetShortMsgMapper.insertSelective(csm); // 保存日志

        return success;
    }

    /**
     * 通知1： 培训班开班前一天通知 （ 通知每个人第二天的第一堂课）
     *
     各位老师： 您好！ [新发展理念与现代化经济体系建设]培训班将于2018年3月19日开班。 您所
     选的第一堂课[“一带一路”与全球化转型]将于2018年3月19日15:00在京师大厦三层第六会议室开课， 请安排
     好工作按时上课。 联系电话： 58806798。 谢谢！ [系统短信， 请勿直接回复]
      */
    /*@Transactional
    public boolean sendMsg_1(int traineeId) {

        ContentTpl tpl = shortMsgService.getTpl(ContentTplConstants.CONTENT_TPL_CET_MSG_1);
        if (tpl == null) return false;

        CetTrainee cetTrainee = cetTraineeMapper.selectByPrimaryKey(traineeId);
        if (cetTrainee == null) return false;
        CetProjectObj cetProjectObj = cetProjectObjMapper.selectByPrimaryKey(cetTrainee.getObjId());
        if (cetProjectObj == null) return false;

        CetTrainCourse cetTrainCourse = iCetMapper.getTomorrowFirstCourse(traineeId);
        if(cetTrainCourse==null) return false;

        CetTraineeCourse cteec = cetTraineeCourseService.get(traineeId, cetTrainCourse.getId());
        if(cteec==null) return false;
        // 已签到
        if(cteec.getIsFinished()) return false;

        String startTime = DateUtils.formatDate(cetTrainCourse.getStartTime(), "MM月dd日 HH:mm");
        String address = cetTrainCourse.getAddress();

        Integer courseId = cetTrainCourse.getCourseId();
        CetCourse cetCourse = cetCourseService.get(courseId);
        if(cetCourse==null) return false;
        String courseName = cetCourse.getName();


        Integer trainId = cetTrainee.getTrainId();
        CetTrain cetTrain = cetTrainMapper.selectByPrimaryKey(trainId);
        String trainName = cetTrain.getName();
        String trainStartDate = DateUtils.formatDate(cetTrain.getStartDate(), DateUtils.YYYY_MM_DD_CHINA);

        String msg = MessageFormat.format(tpl.getContent(),
                trainName, trainStartDate, courseName, startTime, address);
        if (StringUtils.isBlank(msg)) return false;

        ShortMsgBean bean = new ShortMsgBean();
        bean.setSender(null);
        bean.setRelateType(SystemConstants.SHORT_MSG_RELATE_TYPE_CONTENT_TPL);
        bean.setRelateId(tpl.getId());
        bean.setType(tpl.getName());
        bean.setContent(msg);

        CetShortMsg csm = new CetShortMsg();
        csm.setContentTplId(tpl.getId());
        csm.setTplKey(tpl.getCode());
        csm.setTrainId(trainId);
        csm.setMsg(msg);

        int userId = cetProjectObj.getUserId();
        bean.setReceiver(userId);
        String mobile = userBeanService.getMsgMobile(userId);
        bean.setMobile(mobile);
        boolean success = false;
        try {
            success = shortMsgService.send(bean, "127.0.0.1");
        }catch (Exception ex){
            logger.error("干部教育培训短信发送失败", ex);
        }

        csm.setSendTime(new Date());
        csm.setUserId(userId);
        csm.setSuccess(success);
        cetShortMsgMapper.insertSelective(csm); // 保存日志

        return success;
    }*/


    /**
     * 各位老师：您好！ 学校定于{0}至{1}举办{2}。开班仪式将于{3}在{4}举行，请安排好工作按时参加。
     * 联系电话：58806798。谢谢！[系统短信，请勿直接回复]
     * @return
     */
    @Transactional
    public int sendMsg_1(CetProject cetProject) {

        ContentTpl tpl = shortMsgService.getTpl(ContentTplConstants.CONTENT_TPL_CET_MSG_1);
        if (tpl == null) return 0;

        Integer projectId = cetProject.getId();
        String name = cetProject.getName();
        String startDate = DateUtils.formatDate(cetProject.getStartDate(), DateUtils.YYYY_MM_DD_CHINA);
        String endDate = DateUtils.formatDate(cetProject.getEndDate(), DateUtils.YYYY_MM_DD_CHINA);

        String openTime = DateUtils.formatDate(cetProject.getOpenTime(), "MM月dd日 HH:mm");
        String openAddress = cetProject.getOpenAddress();
        String msg = MessageFormat.format(tpl.getContent(), startDate, endDate,
                name, openTime, openAddress);

        if (StringUtils.isBlank(msg)) return 0;

        ShortMsgBean bean = new ShortMsgBean();
        bean.setSender(null);
        bean.setRelateType(SystemConstants.SHORT_MSG_RELATE_TYPE_CONTENT_TPL);
        bean.setRelateId(tpl.getId());
        bean.setType(tpl.getName());
        bean.setContent(msg);

        CetShortMsg csm = new CetShortMsg();
        csm.setContentTplId(tpl.getId());
        csm.setTplKey(tpl.getCode());
        csm.setRecordId(projectId);
        csm.setMsg(msg);

        List<Integer> userIds = iCetMapper.getCetProjectHasApplyUserIds(projectId);
        int successCount = 0;
        for (int userId : userIds) {

            bean.setReceiver(userId);
            String mobile = userBeanService.getMsgMobile(userId);
            bean.setMobile(mobile);
            boolean success = false;
            try {
                success = shortMsgService.send(bean, "127.0.0.1");
            }catch (Exception ex){
                logger.error("干部教育培训短信发送失败", ex);
            }

            csm.setSendTime(new Date());
            csm.setUserId(userId);
            csm.setSuccess(success);
            cetShortMsgMapper.insertSelective(csm); // 保存日志

            if(success) successCount++;
        }


        return successCount;
    }

    // 通知1： 培训班开班前一天通知
    public int projectOpenMsg(Integer projectId) {

        // 获取第二天开班的培训班列表
        List<CetProject> cetProjects = new ArrayList<>();
        {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH, 1);
            CetProjectExample example = new CetProjectExample();
            CetProjectExample.Criteria criteria = example.createCriteria()
                    .andPubStatusEqualTo(CetConstants.CET_PROJECT_PUB_STATUS_PUBLISHED)
                    .andStatusEqualTo(CetConstants.CET_PROJECT_STATUS_START);
            if(projectId!=null){
                // 通知指定班
                criteria.andIdEqualTo(projectId);
            }else {
                // 通知所有班
                criteria.andStartDateEqualTo(cal.getTime());
            }

            cetProjects=cetProjectMapper.selectByExample(example);
        }

        int successCount = 0;
        for (CetProject cetProject : cetProjects) {

            successCount += sendMsg_1(cetProject);
        }
        return successCount;
    }

    // （系统自动发送：每天早上8点发送当天的课）
    public int todayCourse(Integer trainId){

        // 当天还未开课的课程
        List<CetTrainCourse> todayTrainCourseList = iCetMapper.getTodayTrainCourseList(trainId);

        int successCount = 0;
        for (CetTrainCourse cetTrainCourse : todayTrainCourseList) {

            int trainCourseId = cetTrainCourse.getId();
            CetTraineeCourseExample example = new CetTraineeCourseExample();
            example.createCriteria().andTrainCourseIdEqualTo(trainCourseId)
                    .andIsFinishedEqualTo(false);
            List<CetTraineeCourse> cetTraineeCourses = cetTraineeCourseMapper.selectByExample(example);

            for (CetTraineeCourse cetTraineeCourse : cetTraineeCourses) {
                if(sendMsg_2(cetTraineeCourse.getTraineeId(), trainCourseId)) successCount++;
            }
        }
        return successCount;
    }
}
