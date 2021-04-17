package service.parttime;

import bean.ShortMsgBean;
import domain.abroad.ApproverType;
import domain.base.ContentTpl;
import domain.cadre.CadreView;
import domain.cla.ClaApply;
import domain.cla.ClaApproverType;
import domain.parttime.ParttimeApply;
import domain.parttime.ParttimeApproverType;
import domain.sys.SysUserView;
import ext.service.ShortMsgService;
import mixin.MixinUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import persistence.abroad.ApproverTypeMapper;
import persistence.cla.common.ClaApprovalResult;
import persistence.parttime.ParttimeApplyMapper;
import persistence.parttime.ParttimeApproverTypeMapper;
import persistence.parttime.common.ParttimeApprovalResult;
import service.base.ContentTplService;
import service.cadre.CadreService;
import service.cla.ClaApplyService;
import service.sys.UserBeanService;
import sys.constants.ClaConstants;
import sys.constants.ContentTplConstants;
import sys.constants.ParttimeConstants;
import sys.constants.SystemConstants;
import sys.service.ApplicationContextSupport;
import sys.utils.JSONUtils;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ParttimeShortMsgService {
    @Autowired
    private CadreService cadreService;
    @Autowired
    private ShortMsgService shortMsgService;
    @Autowired
    private ContentTplService contentTplService;
    @Autowired
    private UserBeanService userBeanService;
    @Autowired
    private ParttimeApplyMapper parttimeApplyMapper;
    @Autowired
    private ParttimeApplyService parttimeApplyService;
    @Autowired
    private ParttimeApproverTypeMapper parttimeApproverTypeMapper;

    private Logger logger = LoggerFactory.getLogger(getClass());

    // 干部请假如果通过全部领导的审批（下一个审批身份是管理员），则短信通知管理员
    @Async
    public void sendClaApplyPassMsgToCadreAdmin(int applyId, String ip){

        ParttimeApplyService parttimeApplyService = ApplicationContextSupport.getContext().getBean(ParttimeApplyService.class);
        ParttimeApply claApply = parttimeApplyService.get(applyId);
        SysUserView applyUser = claApply.getUser();

        CadreView cadre = cadreService.dbFindByUserId(applyUser.getId());
        String cadreTitle = cadre.getTitle();

        //List<SysUserView> cadreAdmin = sysUserService.findByRole("cadreAdmin-menu1");
        ContentTpl tpl = shortMsgService.getTpl(ContentTplConstants.CONTENT_TPL_PARTTIME_APPLY_PASS_INFO);
        List<SysUserView> receivers = contentTplService.getShorMsgReceivers(tpl.getId());

        for (SysUserView uv : receivers) {
            try {
                int userId = uv.getId();
                String mobile = userBeanService.getMsgMobile(userId);
                String msgTitle = userBeanService.getMsgTitle(userId);

                String msg = MessageFormat.format(tpl.getContent(), msgTitle,
                        cadreTitle,applyUser.getRealname());

                ShortMsgBean bean = new ShortMsgBean();
                shortMsgService.initShortMsgBeanParams(bean, tpl);

                bean.setSender(applyUser.getId());
                bean.setReceiver(userId);
                bean.setMobile(mobile);
                bean.setContent(msg);
                bean.setRelateId(tpl.getId());
                bean.setRelateType(SystemConstants.SHORT_MSG_RELATE_TYPE_CONTENT_TPL);
                bean.setTypeStr(tpl.getName());

                shortMsgService.send(bean, ip);
            }catch (Exception ex){
                logger.error("异常", ex);
                logger.error("干部提交兼职申报，给干部管理员发短信提醒失败。申请人：{}， 审核人：{}, {}, {}", new Object[]{
                        applyUser.getRealname(), uv.getRealname(), uv.getMobile(), ex.getMessage()
                });
            }
        }
    }

    public ShortMsgBean getShortMsgBean(Integer sender, Integer receiver, String type, Integer id){

        ShortMsgBean bean = new ShortMsgBean();
        bean.setSender(sender);
        bean.setReceiver(receiver);
        bean.setRelateType(SystemConstants.SHORT_MSG_RELATE_TYPE_CONTENT_TPL);

        if(StringUtils.equals(type, "apply")){

            ParttimeApply parttimeApply = parttimeApplyMapper.selectByPrimaryKey(id);
            String key = ContentTplConstants.CONTENT_TPL_CLA_APPLY_PASS;

            ParttimeApplyService claApplyService = ApplicationContextSupport.getContext().getBean(ParttimeApplyService.class);
            Map<Integer, ParttimeApprovalResult> approvalResultMap = parttimeApplyService.getApprovalResultMap(id);
            ParttimeApprovalResult lastVal = approvalResultMap.get(0);

            SysUserView uv = parttimeApply.getUser();
            bean.setReceiver(uv.getId()); // 覆盖
            String msgTitle = userBeanService.getMsgTitle(uv.getId());
            String mobile = userBeanService.getMsgMobile(uv.getId());

            boolean status = (lastVal.getValue()!=null && lastVal.getValue()==1);
            if(status) {
                ContentTpl tpl = shortMsgService.getTpl(key);
                shortMsgService.initShortMsgBeanParams(bean, tpl);
                bean.setRelateId(tpl.getId());
                bean.setTypeStr(tpl.getName());
                String msg = MessageFormat.format(tpl.getContent(), msgTitle);
                bean.setContent(msg);
            }else{
                key = ContentTplConstants.CONTENT_TPL_CLA_APPLY_UNPASS;
                ContentTpl tpl = shortMsgService.getTpl(key);
                shortMsgService.initShortMsgBeanParams(bean, tpl);
                bean.setRelateId(tpl.getId());
                bean.setTypeStr(tpl.getName());
                String msg = MessageFormat.format(tpl.getContent(), msgTitle,
                        StringUtils.defaultIfBlank(parttimeApply.getApprovalRemark(), "无"));
                bean.setContent(msg);
            }

            bean.setMobile(mobile);
        }

        return bean;
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

        ParttimeApply apply = parttimeApplyMapper.selectByPrimaryKey(applyId);
        if (apply.getIsDeleted() || !apply.getStatus()
                || apply.getIsFinish() || apply.getFlowNode() <= 0) {
            return resultMap;
        }

        SysUserView applyUser = apply.getUser();
        Integer flowNode = apply.getFlowNode();
        ParttimeApproverType approverType = parttimeApproverTypeMapper.selectByPrimaryKey(flowNode);
        Byte type = approverType.getType();
        List<SysUserView> approvers = parttimeApplyService.findApprovers(apply.getCadreId(), flowNode);
        int size = approvers.size();
        String key = null; // 短信模板代码
        if (size > 0) {
            if (type == ParttimeConstants.PARTTIME_APPROVER_TYPE_UNIT) { // 院系组织部审批
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
            } else if (type == ParttimeConstants.PARTTIME_APPROVER_TYPE_FOREIGN) {//外事部门审批
                key = ContentTplConstants.CONTENT_TPL_PARTTIME_APPLY_APPROVAL_MASTER;
            }

            // 校验用，以防万一
            if (size > 1 && !StringUtils.equals(key, ContentTplConstants.CONTENT_TPL_CLA_APPLY_APPROVAL_UNIT_2)) {
                logger.error("干部请假审批系统发送短信异常："
                        + JSONUtils.toString(apply, MixinUtils.baseMixins(), false));
                return resultMap;
            }
        }
        if (key != null) {
            ContentTpl tpl = shortMsgService.getTpl(key);
            for (SysUserView approver : approvers) {

                int userId = approver.getId();
                String mobile = userBeanService.getMsgMobile(userId);
                String msgTitle = userBeanService.getMsgTitle(userId);
                ShortMsgBean bean = new ShortMsgBean();
                shortMsgService.initShortMsgBeanParams(bean, tpl);
                bean.setSender(null);
                bean.setReceiver(userId);

                String msgTpl = tpl.getContent();
                bean.setRelateId(tpl.getId());
                bean.setRelateType(SystemConstants.SHORT_MSG_RELATE_TYPE_CONTENT_TPL);
                bean.setTypeStr(tpl.getName());
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

    // 干部提交干部请假，给干部管理员发短信提醒
    @Async
    public void sendApplySubmitMsgToCadreAdmin(int applyId, String ip){

        /*try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            logger.error("异常", e);
        }
        System.out.println("发送短信开始。。。");*/

        ParttimeApplyService parttimeApplyService = ApplicationContextSupport.getContext().getBean(ParttimeApplyService.class);
        ParttimeApply parttimeApply = parttimeApplyService.get(applyId);
        SysUserView applyUser = parttimeApply.getUser();

        CadreView cadre = cadreService.dbFindByUserId(applyUser.getId());
        String cadreTitle = cadre.getTitle();

        ContentTpl tpl = shortMsgService.getTpl(ContentTplConstants.CONTENT_TPL_PARTTIME_APPLY_SUBMIT_INFO);
        List<SysUserView> receivers = contentTplService.getShorMsgReceivers(tpl.getId());

        for (SysUserView uv : receivers) {
            try {
                int userId = uv.getId();
                String mobile = userBeanService.getMsgMobile(userId);
                String msgTitle = userBeanService.getMsgTitle(userId);

                String msg = MessageFormat.format(tpl.getContent(), msgTitle,
                        cadreTitle, applyUser.getRealname());

                ShortMsgBean bean = new ShortMsgBean();
                shortMsgService.initShortMsgBeanParams(bean, tpl);

                bean.setSender(applyUser.getId());
                bean.setReceiver(userId);
                bean.setMobile(mobile);
                bean.setContent(msg);
                bean.setRelateId(tpl.getId());
                bean.setRelateType(SystemConstants.SHORT_MSG_RELATE_TYPE_CONTENT_TPL);
                bean.setTypeStr(tpl.getName());

                shortMsgService.send(bean, ip);
            }catch (Exception ex){
                logger.error("异常", ex);
                logger.error("干部提交兼职申报，给干部管理员发短信提醒失败。申请人：{}， 审核人：{}, {},{}", new Object[]{
                        applyUser.getRealname(), uv.getRealname(), uv.getMobile(), ex.getMessage()
                });
            }
        }
    }
}
