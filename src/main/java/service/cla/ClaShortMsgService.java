package service.cla;

import bean.ShortMsgBean;
import domain.base.ContentTpl;
import domain.cadre.CadreView;
import domain.cla.ClaApply;
import domain.cla.ClaApplyExample;
import domain.cla.ClaApproverType;
import domain.sys.SysUserView;
import mixin.MixinUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import persistence.cla.common.ClaApprovalResult;
import service.BaseMapper;
import service.base.ContentTplService;
import service.base.ShortMsgService;
import service.cadre.CadreService;
import service.sys.UserBeanService;
import shiro.PasswordHelper;
import sys.constants.ClaConstants;
import sys.constants.ContentTplConstants;
import sys.constants.SystemConstants;
import sys.service.ApplicationContextSupport;
import sys.utils.JSONUtils;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClaShortMsgService extends BaseMapper {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ShortMsgService shortMsgService;
    @Autowired
    private UserBeanService userBeanService;
    @Autowired
    private CadreService cadreService;
    @Autowired
    private ClaApplyService claApplyService;

    @Autowired
    private ContentTplService contentTplService;
    @Autowired
    protected PasswordHelper passwordHelper;
    @Autowired
    protected CacheManager cacheManager;

    // 给干部管理员发提醒，仅用于定时任务
    public void sendClaApprovalMsgToAdmin(){

        {// 干部提交干部请假，给干部管理员发短信提醒

            ClaApplyExample example = new ClaApplyExample();
            example.createCriteria().andStatusEqualTo(true)
                    .andFlowNodeEqualTo(ClaConstants.CLA_APPROVER_TYPE_ID_OD_FIRST)
                    .andIsDeletedEqualTo(false).andIsFinishEqualTo(false);
            List<ClaApply> claApplys = claApplyMapper.selectByExample(example);
            for (ClaApply claApply : claApplys) {
                sendApplySubmitMsgToCadreAdmin(claApply.getId(), null);
            }
        }

        {// 干部请假如果通过全部领导的审批（下一个审批身份是管理员），则短信通知管理员

            ClaApplyExample example = new ClaApplyExample();
            example.createCriteria().andStatusEqualTo(true)
                    .andFlowNodeEqualTo(ClaConstants.CLA_APPROVER_TYPE_ID_OD_LAST)
                    .andIsDeletedEqualTo(false).andIsFinishEqualTo(false);
            List<ClaApply> claApplys = claApplyMapper.selectByExample(example);
            for (ClaApply claApply : claApplys) {
                sendClaApplyPassMsgToCadreAdmin(claApply.getId(), null);
            }
        }
    }

    // 干部提交干部请假，给干部管理员发短信提醒
    @Async
    public void sendApplySubmitMsgToCadreAdmin(int applyId, String ip){

        /*try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("发送短信开始。。。");*/

        ClaApplyService claApplyService = ApplicationContextSupport.getContext().getBean(ClaApplyService.class);
        ClaApply claApply = claApplyService.get(applyId);
        SysUserView applyUser = claApply.getUser();

        CadreView cadre = cadreService.dbFindByUserId(applyUser.getId());
        String cadreTitle = cadre.getTitle();

        //List<SysUserView> cadreAdmin = sysUserService.findByRole(RoleConstants.ROLE_CADREADMIN);
        ContentTpl tpl = shortMsgService.getTpl(ContentTplConstants.CONTENT_TPL_CLA_APPLY_SUBMIT_INFO);
        List<SysUserView> receivers = contentTplService.getShorMsgReceivers(tpl.getId());

        for (SysUserView uv : receivers) {
            try {
                int userId = uv.getId();
                String mobile = userBeanService.getMsgMobile(userId);
                String msgTitle = userBeanService.getMsgTitle(userId);

                String msg = MessageFormat.format(tpl.getContent(), msgTitle,
                        cadreTitle, applyUser.getRealname());

                ShortMsgBean bean = new ShortMsgBean();
                bean.setSender(applyUser.getId());
                bean.setReceiver(userId);
                bean.setMobile(mobile);
                bean.setContent(msg);
                bean.setRelateId(tpl.getId());
                bean.setRelateType(SystemConstants.SHORT_MSG_RELATE_TYPE_CONTENT_TPL);
                bean.setType(tpl.getName());

                shortMsgService.send(bean, ip);
            }catch (Exception ex){
                ex.printStackTrace();
                logger.error("干部提交干部请假，给干部管理员发短信提醒失败。申请人：{}， 审核人：{}, {},{}", new Object[]{
                    applyUser.getRealname(), uv.getRealname(), uv.getMobile(), ex.getMessage()
                });
            }
        }
    }

    // 干部请假如果通过全部领导的审批（下一个审批身份是管理员），则短信通知管理员
    @Async
    public void sendClaApplyPassMsgToCadreAdmin(int applyId, String ip){

        ClaApplyService claApplyService = ApplicationContextSupport.getContext().getBean(ClaApplyService.class);
        ClaApply claApply = claApplyService.get(applyId);
        SysUserView applyUser = claApply.getUser();

        CadreView cadre = cadreService.dbFindByUserId(applyUser.getId());
        String cadreTitle = cadre.getTitle();

        //List<SysUserView> cadreAdmin = sysUserService.findByRole("cadreAdmin-menu1");
        ContentTpl tpl = shortMsgService.getTpl(ContentTplConstants.CONTENT_TPL_CLA_APPLY_PASS_INFO);
        List<SysUserView> receivers = contentTplService.getShorMsgReceivers(tpl.getId());

        for (SysUserView uv : receivers) {
            try {
                int userId = uv.getId();
                String mobile = userBeanService.getMsgMobile(userId);
                String msgTitle = userBeanService.getMsgTitle(userId);

                String msg = MessageFormat.format(tpl.getContent(), msgTitle,
                        cadreTitle,applyUser.getRealname());

                ShortMsgBean bean = new ShortMsgBean();
                bean.setSender(applyUser.getId());
                bean.setReceiver(userId);
                bean.setMobile(mobile);
                bean.setContent(msg);
                bean.setRelateId(tpl.getId());
                bean.setRelateType(SystemConstants.SHORT_MSG_RELATE_TYPE_CONTENT_TPL);
                bean.setType(tpl.getName());

                shortMsgService.send(bean, ip);
            }catch (Exception ex){
                ex.printStackTrace();
                logger.error("干部提交干部请假，给干部管理员发短信提醒失败。申请人：{}， 审核人：{}, {}, {}", new Object[]{
                        applyUser.getRealname(), uv.getRealname(), uv.getMobile(), ex.getMessage()
                });
            }
        }
    }

    /**
     * 仅用于定时任务，给需要审批的人员发短信
     * <p>
     * 如果领导没有审批，那么从第二天开始，每天早上8点发送一次
     */
    public void sendApprovalMsg() {

        logger.debug("====干部请假审批短信通知...start====");
        int success = 0, total = 0; // 成功条数，总条数
        //Date today = new Date();

        ClaApplyExample example = new ClaApplyExample();
        example.createCriteria().andIsDeletedEqualTo(false) // 没有删除
                .andStatusEqualTo(true) // 已经提交的
                .andIsFinishEqualTo(false) // 还未完成审批的
                .andFlowNodeGreaterThan(0); // 当前不是组织部审批的
        List<ClaApply> applys = claApplyMapper.selectByExample(example);

        for (ClaApply apply : applys) {

            Map<String, Integer> resultMap = sendApprovalMsg(apply.getId());
            success += resultMap.get("success");
            total += resultMap.get("total");
        }

        logger.debug(String.format("====干部请假审批短信通知，发送成功%s/%s条...end====", success, total));
    }

    /**
     * 给一条申请记录的下一步审批人发短信
     *
     * @param applyId
     * @return 发送短信的数目
     */
    public Map<String, Integer> sendApprovalMsg(int applyId) {

        int success = 0, total = 0; // 成功条数，总条数
        Map<String, Integer> resultMap = new HashMap<String, Integer>();
        resultMap.put("id", applyId);
        resultMap.put("success", success);
        resultMap.put("total", total);

        ClaApply apply = claApplyMapper.selectByPrimaryKey(applyId);
        if (apply.getIsDeleted() || !apply.getStatus()
                || apply.getIsFinish() || apply.getFlowNode() <= 0) {
            return resultMap;
        }

        SysUserView applyUser = apply.getUser();
        Integer flowNode = apply.getFlowNode();
        ClaApproverType approverType = claApproverTypeMapper.selectByPrimaryKey(flowNode);
        Byte type = approverType.getType();
        List<SysUserView> approvers = claApplyService.findApprovers(apply.getCadreId(), flowNode);
        int size = approvers.size();
        String key = null; // 短信模板代码
        if (size > 0) {
            if (type == ClaConstants.CLA_APPROVER_TYPE_UNIT) { // 本单位正职审批

                if (size > 1) { // 多个正职审批
                    key = ContentTplConstants.CONTENT_TPL_CLA_APPLY_APPROVAL_UNIT_2;
                } else { // 单个正职审批
                    key = ContentTplConstants.CONTENT_TPL_CLA_APPLY_APPROVAL_UNIT_1;
                }
            } else if (type == ClaConstants.CLA_APPROVER_TYPE_LEADER) {  // 校领导审批

                key = ContentTplConstants.CONTENT_TPL_CLA_APPLY_APPROVAL_LEADER;
            } else if (type == ClaConstants.CLA_APPROVER_TYPE_SECRETARY) { // 书记审批

                key = ContentTplConstants.CONTENT_TPL_CLA_APPLY_APPROVAL_SECRETARY;
            } else if (type == ClaConstants.CLA_APPROVER_TYPE_MASTER) { // 校长审批

                key = ContentTplConstants.CONTENT_TPL_CLA_APPLY_APPROVAL_MASTER;
            }

            // 校验用，以防万一
            if (size > 1 && !StringUtils.equals(key, ContentTplConstants.CONTENT_TPL_CLA_APPLY_APPROVAL_UNIT_2)) {
                logger.error("干部请假审批系统发送短信异常："
                        + JSONUtils.toString(apply, MixinUtils.baseMixins(), false));
                return resultMap;
            }
        }
        if (key != null) {
            for (SysUserView approver : approvers) {

                int userId = approver.getId();
                String mobile = userBeanService.getMsgMobile(userId);
                String msgTitle = userBeanService.getMsgTitle(userId);
                ShortMsgBean bean = new ShortMsgBean();
                bean.setSender(null);
                bean.setReceiver(userId);
                ContentTpl tpl = shortMsgService.getTpl(key);
                String msgTpl = tpl.getContent();
                bean.setRelateId(tpl.getId());
                bean.setRelateType(SystemConstants.SHORT_MSG_RELATE_TYPE_CONTENT_TPL);
                bean.setType(tpl.getName());
                String msg = null;
                switch (key) {
                    case ContentTplConstants.CONTENT_TPL_CLA_APPLY_APPROVAL_UNIT_1:
                    case ContentTplConstants.CONTENT_TPL_CLA_APPLY_APPROVAL_UNIT_2:
                        msg = MessageFormat.format(msgTpl, msgTitle, applyUser.getRealname());
                        break;
                    default:
                        CadreView applyCadre = cadreService.dbFindByUserId(applyUser.getId());
                        msg = MessageFormat.format(msgTpl, msgTitle, applyCadre.getTitle(), applyUser.getRealname());
                        break;
                }
                bean.setContent(msg);
                bean.setMobile(mobile);
                try {
                    total++;
                    boolean ret = shortMsgService.send(bean, "127.0.0.1");
                    logger.info(String.format("系统发送短信[%s]：%s", ret ? "成功" : "失败", bean.getContent()));

                    if (ret) success++;
                } catch (Exception ex) {
                    logger.error("干部请假审批系统发送短信失败", ex);
                }
            }
        }

        resultMap.put("success", success);
        resultMap.put("total", total);

        return resultMap;
    }

    public ShortMsgBean getShortMsgBean(Integer sender, Integer receiver, String type, Integer id){

        ShortMsgBean bean = new ShortMsgBean();
        bean.setSender(sender);
        bean.setReceiver(receiver);
        bean.setRelateType(SystemConstants.SHORT_MSG_RELATE_TYPE_CONTENT_TPL);

        if(StringUtils.equals(type, "apply")){

            ClaApply claApply = claApplyMapper.selectByPrimaryKey(id);
            String key = ContentTplConstants.CONTENT_TPL_CLA_APPLY_PASS;

            ClaApplyService claApplyService = ApplicationContextSupport.getContext().getBean(ClaApplyService.class);
            Map<Integer, ClaApprovalResult> approvalResultMap = claApplyService.getApprovalResultMap(id);
            ClaApprovalResult lastVal = approvalResultMap.get(0);

            SysUserView uv = claApply.getUser();
            bean.setReceiver(uv.getId()); // 覆盖
            String msgTitle = userBeanService.getMsgTitle(uv.getId());
            String mobile = userBeanService.getMsgMobile(uv.getId());

            boolean status = (lastVal.getValue()!=null && lastVal.getValue()==1);
            if(status) {
                ContentTpl tpl = shortMsgService.getTpl(key);
                bean.setRelateId(tpl.getId());
                bean.setType(tpl.getName());
                String msg = MessageFormat.format(tpl.getContent(), msgTitle);
                bean.setContent(msg);
            }else{
                key = ContentTplConstants.CONTENT_TPL_CLA_APPLY_UNPASS;
                ContentTpl tpl = shortMsgService.getTpl(key);
                bean.setRelateId(tpl.getId());
                bean.setType(tpl.getName());
                String msg = MessageFormat.format(tpl.getContent(), msgTitle,
                        StringUtils.defaultIfBlank(claApply.getApprovalRemark(), "无"));
                bean.setContent(msg);
            }

            bean.setMobile(mobile);
        }

        return bean;
    }

    // 返校时间第二天之后的每天9:00， 如果没有销假， 那么自动发送短信提醒
    public void applyBackMsg() {

        String key = ContentTplConstants.CONTENT_TPL_CLA_APPLY_BACK;
        ContentTpl tpl = shortMsgService.getTpl(key);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date today = calendar.getTime();

        ClaApplyExample example = new ClaApplyExample();
        example.createCriteria().andStatusEqualTo(true)
                .andFlowNodeEqualTo(ClaConstants.CLA_APPROVER_TYPE_ID_OD_LAST)
                .andIsDeletedEqualTo(false).andIsFinishEqualTo(true).andIsBackEqualTo(false)
                .andEndTimeLessThan(today);

        int success = 0, total = 0; // 成功条数，总条数
        List<ClaApply> claApplys = claApplyMapper.selectByExample(example);
        for (ClaApply claApply : claApplys) {

            int userId = claApply.getCadre().getUserId();
            String mobile = userBeanService.getMsgMobile(userId);
            String msgTitle = userBeanService.getMsgTitle(userId);

            ShortMsgBean bean = new ShortMsgBean();
            bean.setSender(null);
            bean.setReceiver(userId);

            String msgTpl = tpl.getContent();
            bean.setRelateId(tpl.getId());
            bean.setRelateType(SystemConstants.SHORT_MSG_RELATE_TYPE_CONTENT_TPL);
            bean.setType(tpl.getName());
            String msg = MessageFormat.format(msgTpl, msgTitle);

            bean.setContent(msg);
            bean.setMobile(mobile);
            try {
                total++;
                boolean ret = shortMsgService.send(bean, "127.0.0.1");
                logger.info(String.format("系统发送短信[%s]：%s", ret ? "成功" : "失败", bean.getContent()));

                if (ret) success++;
            } catch (Exception ex) {
                logger.error("干部请假审批系统发送短信失败", ex);
            }
        }

        logger.info("干部销假提醒完成。总共{}条记录, 成功发送{}条短信", total, success);
    }
}
