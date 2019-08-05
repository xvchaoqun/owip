package service.crs;

import bean.ShortMsgBean;
import domain.base.ContentTpl;
import domain.cadre.CadreView;
import domain.crs.*;
import domain.sys.SysUserView;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.base.ContentTplService;
import ext.service.ShortMsgService;
import service.cadre.CadreService;
import service.sys.UserBeanService;
import sys.constants.ContentTplConstants;
import sys.constants.CrsConstants;
import sys.constants.SystemConstants;
import sys.utils.DateUtils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class CrsShortMsgService extends CrsBaseMapper {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected ContentTplService contentTplService;
    @Autowired
    @Lazy
    protected CrsApplicantService crsApplicantService;
    @Autowired
    protected UserBeanService userBeanService;
    @Autowired
    protected ShortMsgService shortMsgService;
    @Autowired
    protected CadreService cadreService;

    /*public static void main(String[] args) {

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, 0);
        Date endTime = cal.getTime();
        cal.add(Calendar.MINUTE, -10);
        Date startTime = cal.getTime();

        System.out.println(DateUtils.formatDate(startTime, DateUtils.YYYY_MM_DD_HH_MM));
        System.out.println(DateUtils.formatDate(endTime, DateUtils.YYYY_MM_DD_HH_MM));
    }*/

    /**
     * 各位老师： 您好！{0}岗位招聘会定于{1}在{2}召开， 上传应聘PPT截止时间是{3}。
     * 请按时将应聘PPT上传至组织工作管理与服务一体化平台。
     * 联系电话：58806879。谢谢！[系统短信， 请勿直接回复]
     * <p>
     * 截止时间前1天（24小时） ， 发送一次，只发给还没有上传的人
     * 截止时间前1个小时， 发送一次，只发给还没有上传的人
     * <p>
     * <p>
     * 各位老师：您好！{0}岗位招聘会定于{1}在{2}召开，上传应聘PPT截止时间已到，您还未上传。
     * 请尽快将应聘PPT上传至组织工作管理与服务一体化平台。联系电话：58806879。谢谢！[系统短信， 请勿直接回复]
     * <p>
     * 截止时间刚到，只发给还没有上传的人
     * 后面每两个小时催一次(2小时、4小时发，共2次)
     */
    // 上传应聘PPT提醒和催交（每10分钟运行一次）
    @Async
    public void sendUploadPptMsg() {

        // 截止时间前1天（24小时） ， 发送一次，只发给还没有上传的人
        sendPptMsg(24);

        // 截止时间前1个小时， 发送一次，只发给还没有上传的人
        sendPptMsg(1);

        // 截止时间刚到，只发给还没有上传的人，后面每两个小时催一次(共两次)
        sendUploadPptUrgeMsg();
    }

    // 截止时间刚到，只发给还没有上传的人 或  每两个小时催一次
    private void sendUploadPptUrgeMsg() {

        Calendar cal = Calendar.getInstance();
        Date now = cal.getTime();
        cal.add(Calendar.HOUR, -4);
        Date startTime = cal.getTime();

        // 已发布、正在招聘、未关闭上传、已截止上传PPT（过去了4个小时以内）的岗位
        CrsPostExample example = new CrsPostExample();
        example.createCriteria().andStatusEqualTo(CrsConstants.CRS_POST_STATUS_NORMAL)
                .andPubStatusEqualTo(CrsConstants.CRS_POST_PUB_STATUS_PUBLISHED)
                .andPptUploadClosedEqualTo(false)
                .andPptDeadlineGreaterThan(startTime)
                .andPptDeadlineLessThanOrEqualTo(now);
        List<CrsPost> crsPosts = crsPostMapper.selectByExample(example);

        for (CrsPost crsPost : crsPosts) {

            int postId = crsPost.getId();
            Date pptDeadline = crsPost.getPptDeadline();
            // minute在 [0, 10) 区间时， 是截止时间刚到
            long minute = (now.getTime() - pptDeadline.getTime()) / (1000 * 60);
            minute = minute % 120;
            if (minute >= 0 && minute < 10) { // 每过去（2小时~2小时10分钟），发送短信

                List<CrsApplicantView> passedCrsApplicants = crsApplicantService.getPassedCrsApplicants(postId);
                for (CrsApplicantView applicant : passedCrsApplicants)

                    if (StringUtils.isBlank(applicant.getPpt())) {

                        SysUserView uv = applicant.getUser();
                        sendPptUrgeMsg(uv, crsPost);
                    }
            }
        }
    }

    private void sendPptMsg(int deadHour) {

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, deadHour);
        Date endTime = cal.getTime();
        cal.add(Calendar.MINUTE, -10);
        Date startTime = cal.getTime();

        // 已发布、正在招聘、未关闭上传PPT的岗位
        CrsPostExample example = new CrsPostExample();
        example.createCriteria().andStatusEqualTo(CrsConstants.CRS_POST_STATUS_NORMAL)
                .andPubStatusEqualTo(CrsConstants.CRS_POST_PUB_STATUS_PUBLISHED)
                .andPptUploadClosedEqualTo(false)
                .andPptDeadlineGreaterThan(startTime)
                .andPptDeadlineLessThanOrEqualTo(endTime);
        List<CrsPost> crsPosts = crsPostMapper.selectByExample(example);

        for (CrsPost crsPost : crsPosts) {

            int postId = crsPost.getId();
            List<CrsApplicantView> passedCrsApplicants = crsApplicantService.getPassedCrsApplicants(postId);
            for (CrsApplicantView applicant : passedCrsApplicants) {

                if (StringUtils.isBlank(applicant.getPpt())) {

                    SysUserView uv = applicant.getUser();
                    sendPptMsg(uv, crsPost);
                }
            }
        }
    }

    private void sendPptMsg(SysUserView uv, CrsPost crsPost) {

        String postName = crsPost.getName();
        String meetingTime = DateUtils.formatDate(crsPost.getMeetingTime(), "yyyy年MM月dd日 HH:mm");
        String meetingAddress = crsPost.getMeetingAddress();
        String pptDeadline = DateUtils.formatDate(crsPost.getPptDeadline(), "yyyy年MM月dd日 HH:mm");

        // 如果没有设置招聘会相关信息，则不发送短信
        if(StringUtils.isBlank(meetingTime)
                || StringUtils.isBlank(meetingAddress)
                || StringUtils.isBlank(pptDeadline)){
            return;
        }

        ContentTpl tpl = shortMsgService.getTpl(ContentTplConstants.CONTENT_TPL_CRS_PPT_MSG);
        try {
            int userId = uv.getId();
            String mobile = userBeanService.getMsgMobile(userId);

            String msg = MessageFormat.format(tpl.getContent(), postName,
                    meetingTime, meetingAddress, pptDeadline);

            ShortMsgBean bean = new ShortMsgBean();
            bean.setReceiver(userId);
            bean.setMobile(mobile);
            bean.setContent(msg);
            bean.setRelateId(tpl.getId());
            bean.setRelateType(SystemConstants.SHORT_MSG_RELATE_TYPE_CONTENT_TPL);
            bean.setType(tpl.getName());

            shortMsgService.send(bean, "127.0.0.1");
        } catch (Exception ex) {
            logger.error("异常", ex);
            logger.error("竞争上岗，给未上传PPT的应聘人员发短信提醒失败。接收人：{}, {},{}", new Object[]{
                    uv.getRealname(), uv.getMobile(), ex.getMessage()
            });
        }
    }

    private void sendPptUrgeMsg(SysUserView uv, CrsPost crsPost) {

        String postName = crsPost.getName();
        String meetingTime = DateUtils.formatDate(crsPost.getMeetingTime(), "yyyy年MM月dd日 HH:mm");
        String meetingAddress = crsPost.getMeetingAddress();

        // 如果没有设置招聘会相关信息，则不发送短信
        if(StringUtils.isBlank(meetingTime)
                || StringUtils.isBlank(meetingAddress)){
            return;
        }

        ContentTpl tpl = shortMsgService.getTpl(ContentTplConstants.CONTENT_TPL_CRS_PPT_URGE_MSG);
        try {
            int userId = uv.getId();
            String mobile = userBeanService.getMsgMobile(userId);

            String msg = MessageFormat.format(tpl.getContent(), postName, meetingTime, meetingAddress);

            ShortMsgBean bean = new ShortMsgBean();
            bean.setReceiver(userId);
            bean.setMobile(mobile);
            bean.setContent(msg);
            bean.setRelateId(tpl.getId());
            bean.setRelateType(SystemConstants.SHORT_MSG_RELATE_TYPE_CONTENT_TPL);
            bean.setType(tpl.getName());

            shortMsgService.send(bean, "127.0.0.1");
        } catch (Exception ex) {
            logger.error("异常", ex);
            logger.error("竞争上岗，给未上传PPT的应聘人员发催交短信失败。接收人：{}, {},{}", new Object[]{
                    uv.getRealname(), uv.getMobile(), ex.getMessage()
            });
        }
    }

    /**
     * {0}，您好！{1}老师{2}报名应聘{3}。请您直接点击http://zzgz.xxx.edu.cn
     * 在手机浏览器上登陆系统查看报名详情。谢谢！[系统短信，请勿回复]
     */
    // 报名时，给干部管理员发短信提醒
    @Async
    public void sendApplySubmitMsgToAdmin(int applicantId, String ip) {

        CrsApplicant crsApplicant = crsApplicantMapper.selectByPrimaryKey(applicantId);
        CrsPost post = crsApplicant.getPost();
        SysUserView applyUser = crsApplicant.getUser();

        CadreView cadre = cadreService.dbFindByUserId(applyUser.getId());
        String cadreTitle = cadre.getTitle();

        ContentTpl tpl = shortMsgService.getTpl(ContentTplConstants.CONTENT_TPL_CRS_APPLY_MSG);
        List<SysUserView> receivers = contentTplService.getShorMsgReceivers(tpl.getId());

        for (SysUserView uv : receivers) {
            try {
                int userId = uv.getId();
                String mobile = userBeanService.getMsgMobile(userId);
                String msgTitle = userBeanService.getMsgTitle(userId);

                String msg = MessageFormat.format(tpl.getContent(), msgTitle,
                        applyUser.getRealname(), StringUtils.isNotBlank(cadreTitle) ? String.format("(%s)", cadreTitle) : "",
                        post.getName());

                ShortMsgBean bean = new ShortMsgBean();
                bean.setSender(applyUser.getId());
                bean.setReceiver(userId);
                bean.setMobile(mobile);
                bean.setContent(msg);
                bean.setRelateId(tpl.getId());
                bean.setRelateType(SystemConstants.SHORT_MSG_RELATE_TYPE_CONTENT_TPL);
                bean.setType(tpl.getName());

                shortMsgService.send(bean, ip);
            } catch (Exception ex) {
                logger.error("异常", ex);
                logger.error("竞争上岗报名，给干部管理员发短信提醒失败。申请人：{}， 接收人：{}, {},{}", new Object[]{
                        applyUser.getRealname(), uv.getRealname(), uv.getMobile(), ex.getMessage()
                });
            }
        }
    }

    /**
     * 李老师， 您好！ 信息网络中心副主任等10个岗位报名已截止， 共有80人报名。 请您直接点击
     * http://zzgz.xxx.edu.cn 在手机浏览器上登陆系统查看报名详情。 谢谢！ [系统短信， 请勿回复]
     */
    // 竞争上岗报名截止通知领导（8~18点 每10分钟检查一次）
    public void sendApplyFinishMsg() {

        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        cal.add(Calendar.MINUTE, -10);

        List<CrsPost> crsPosts = new ArrayList<>();
        {
            CrsPostExample example = new CrsPostExample();
            example.createCriteria().andPubStatusEqualTo(CrsConstants.CRS_POST_PUB_STATUS_PUBLISHED)
                    .andStatusEqualTo(CrsConstants.CRS_POST_STATUS_NORMAL)
                    .andEnrollStatusEqualTo(CrsConstants.CRS_POST_ENROLL_STATUS_DEFAULT)
                    .andEndTimeGreaterThan(cal.getTime())
                    .andEndTimeLessThanOrEqualTo(now); // 10分钟前的时刻 < 结束时间 <= 当前时刻
            example.setOrderByClause("end_time asc, id asc");
            crsPosts = crsPostMapper.selectByExample(example);
        }
        int count = crsPosts.size();
        List<Integer> postIds = new ArrayList<>();
        if (count > 0) {
            CrsPost crsPost = crsPosts.get(0);
            String firstPostName = crsPost.getName();
            for (CrsPost post : crsPosts) {
                postIds.add(post.getId());
            }
            CrsApplicantExample example = new CrsApplicantExample();
            example.createCriteria().andPostIdIn(postIds).andStatusEqualTo(CrsConstants.CRS_APPLICANT_STATUS_SUBMIT)
                    .andIsQuitEqualTo(false);
            int applicantCount = (int) crsApplicantMapper.countByExample(example);

            ContentTpl tpl = shortMsgService.getTpl(ContentTplConstants.CONTENT_TPL_CRS_APPLY_END);
            List<SysUserView> receivers = contentTplService.getShorMsgReceivers(tpl.getId());

            for (SysUserView uv : receivers) {
                try {
                    int userId = uv.getId();
                    String mobile = userBeanService.getMsgMobile(userId);
                    String msgTitle = userBeanService.getMsgTitle(userId);

                    String msg = MessageFormat.format(tpl.getContent(), msgTitle,
                            firstPostName + (count > 1 ? String.format("等%s个", count) : ""),
                            applicantCount);

                    ShortMsgBean bean = new ShortMsgBean();
                    bean.setSender(null);
                    bean.setReceiver(userId);
                    bean.setMobile(mobile);
                    bean.setContent(msg);
                    bean.setRelateId(tpl.getId());
                    bean.setRelateType(SystemConstants.SHORT_MSG_RELATE_TYPE_CONTENT_TPL);
                    bean.setType(tpl.getName());

                    shortMsgService.send(bean, "127.0.0.1");
                } catch (Exception ex) {
                    logger.error("异常", ex);
                    logger.error("竞争上岗报名结束，给管理员发短信提醒失败。接收人：{}, {},{}", new Object[]{
                            uv.getRealname(), uv.getMobile(), ex.getMessage()
                    });
                }
            }
        }
    }

    // 给报名通过的人员发送的短信内容
    public String getMsg(ContentTpl tpl, CrsPost crsPost) {

        String postName = crsPost.getName();
        String meetingTime = DateUtils.formatDate(crsPost.getMeetingTime(), "MM月dd日 HH:mm");
        String meetingAddress = crsPost.getMeetingAddress();

        String tplKey = tpl.getCode();
        String tplContent = tpl.getContent();
        String msg = null;
        switch (tplKey) {
            case ContentTplConstants.CONTENT_TPL_CRS_MSG_1:
                msg = MessageFormat.format(tplContent, postName);
                break;
            case ContentTplConstants.CONTENT_TPL_CRS_MSG_2:
                msg = MessageFormat.format(tplContent, postName, meetingTime);
                break;
            case ContentTplConstants.CONTENT_TPL_CRS_MSG_3:
                msg = MessageFormat.format(tplContent, postName, meetingTime, meetingAddress);
                break;
            case ContentTplConstants.CONTENT_TPL_CRS_MSG_4:
                msg = MessageFormat.format(tplContent, postName, meetingTime, meetingAddress);
                break;
            case ContentTplConstants.CONTENT_TPL_CRS_MSG_5:
                msg = MessageFormat.format(tplContent, postName, meetingTime, meetingAddress);
                break;
        }

        return msg;
    }

    // 给报名通过的人员发送短信
    @Transactional
    public int send(int postId, String tplKey) {

        CrsPost crsPost = crsPostMapper.selectByPrimaryKey(postId);
        if (crsPost == null) return 0;

        ContentTpl tpl = shortMsgService.getTpl(tplKey);
        if (tpl == null) return 0;


        String msg = getMsg(tpl, crsPost);
        if (StringUtils.isBlank(msg)) return 0;

        ShortMsgBean bean = new ShortMsgBean();
        bean.setSender(null);
        bean.setRelateType(SystemConstants.SHORT_MSG_RELATE_TYPE_CONTENT_TPL);
        bean.setRelateId(tpl.getId());
        bean.setType(tpl.getName());
        bean.setContent(msg);

        CrsShortMsg csm = new CrsShortMsg();
        csm.setContentTplId(tpl.getId());
        csm.setTplKey(tpl.getCode());
        csm.setPostId(postId);
        csm.setMsg(msg);

        List<CrsApplicantView> applicants = crsApplicantService.getPassedCrsApplicants(postId);
        int successCount = 0;
        for (CrsApplicantView applicant : applicants) {

            int userId = applicant.getUserId();
            bean.setReceiver(userId);
            String mobile = userBeanService.getMsgMobile(userId);
            bean.setMobile(mobile);
            boolean success = false;
            try {
                success = shortMsgService.send(bean, "127.0.0.1");
            } catch (Exception ex) {
                logger.error("干部招聘短信发送失败", ex);
            }

            if (success) {
                successCount++;
            }

            csm.setSendTime(new Date());
            csm.setUserId(userId);
            csm.setSuccess(success);
            crsShortMsgMapper.insertSelective(csm); // 保存日志
        }

        return successCount;
    }
}
