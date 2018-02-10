package service.crs;

import bean.ShortMsgBean;
import domain.base.ContentTpl;
import domain.crs.CrsApplicantView;
import domain.crs.CrsPost;
import domain.crs.CrsShortMsg;
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
import sys.constants.ContentTplConstants;
import sys.constants.SystemConstants;
import sys.utils.DateUtils;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

@Service
public class CrsShortMsgService extends BaseMapper {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected ContentTplService contentTplService;
    @Autowired
    protected CrsApplicantService crsApplicantService;
    @Autowired
    protected UserBeanService userBeanService;
    @Autowired
    protected ShortMsgService shortMsgService;

   /* // 获取已发送短信记录
    public List<CrsShortMsg> getList(int postId, String tplKey) {

        CrsShortMsgExample example = new CrsShortMsgExample();
        example.createCriteria().andPostIdEqualTo(postId).andTplKeyEqualTo(tplKey);
        example.setOrderByClause("send_time desc");

        return crsShortMsgMapper.selectByExample(example);
    }*/

    // 短信内容
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

    // 发送短信
    @Transactional
    public int send(int postId, String tplKey) {

        CrsPost crsPost = crsPostMapper.selectByPrimaryKey(postId);
        if (crsPost == null) return 0;

        ContentTpl tpl = shortMsgService.getShortMsgTpl(tplKey);
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
            }catch (Exception ex){
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
