package service.abroad;

import bean.ShortMsgBean;
import domain.abroad.ApplySelf;
import domain.abroad.ApplySelfExample;
import domain.abroad.Passport;
import domain.abroad.PassportApply;
import domain.abroad.PassportApplyExample;
import domain.abroad.PassportDraw;
import domain.abroad.PassportDrawExample;
import domain.abroad.TaiwanRecord;
import domain.base.ContentTpl;
import domain.base.MetaType;
import domain.cadre.CadreView;
import domain.sys.SysUserView;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import persistence.abroad.common.ApprovalResult;
import service.BaseMapper;
import service.base.ContentTplService;
import service.base.ShortMsgService;
import service.cadre.CadreService;
import service.sys.UserBeanService;
import shiro.PasswordHelper;
import shiro.ShiroHelper;
import sys.constants.AbroadConstants;
import sys.constants.ContentTplConstants;
import sys.constants.SystemConstants;
import sys.service.ApplicationContextSupport;
import sys.utils.DateUtils;
import sys.utils.IpUtils;
import sys.utils.JSONUtils;
import sys.utils.RequestUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

@Service
public class AbroadShortMsgService extends BaseMapper {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ShortMsgService shortMsgService;
    @Autowired
    private UserBeanService userBeanService;
    @Autowired
    private CadreService cadreService;

    @Autowired
    private ContentTplService contentTplService;
    @Autowired
    protected PasswordHelper passwordHelper;
    @Autowired
    protected CacheManager cacheManager;

    // 给干部管理员发提醒，仅用于定时任务
    public void sendAbroadApprovalMsgToAdmin(){

        { // 干部提交办理证件申请，给干部管理员发短信提醒
            PassportApplyExample example = new PassportApplyExample();
            example.createCriteria().andStatusEqualTo(AbroadConstants.ABROAD_PASSPORT_APPLY_STATUS_INIT)
                    .andAbolishEqualTo(false).andIsDeletedEqualTo(false);
            List<PassportApply> passportApplies = passportApplyMapper.selectByExample(example);
            for (PassportApply passportApply : passportApplies) {
                sendPassportApplySubmitMsgToCadreAdmin(passportApply.getId(), null);
            }
        }
        {// 干部提交因私出国，给干部管理员发短信提醒

            ApplySelfExample example = new ApplySelfExample();
            example.createCriteria().andStatusEqualTo(true)
                    .andFlowNodeEqualTo(AbroadConstants.ABROAD_APPROVER_TYPE_ID_OD_FIRST)
                    .andIsDeletedEqualTo(false).andIsFinishEqualTo(false);
            List<ApplySelf> applySelfs = applySelfMapper.selectByExample(example);
            for (ApplySelf applySelf : applySelfs) {
                sendApplySelfSubmitMsgToCadreAdmin(applySelf.getId(), null);
            }
        }

        {// 因私申请如果通过全部领导的审批（下一个审批身份是管理员），则短信通知管理员

            ApplySelfExample example = new ApplySelfExample();
            example.createCriteria().andStatusEqualTo(true)
                    .andFlowNodeEqualTo(AbroadConstants.ABROAD_APPROVER_TYPE_ID_OD_LAST)
                    .andIsDeletedEqualTo(false).andIsFinishEqualTo(false);
            List<ApplySelf> applySelfs = applySelfMapper.selectByExample(example);
            for (ApplySelf applySelf : applySelfs) {
                sendApplySelfPassMsgToCadreAdmin(applySelf.getId(), null);
            }
        }

        {// 干部提交领取证件申请，给干部管理员发短信提醒

            PassportDrawExample example = new PassportDrawExample();
            example.createCriteria().andStatusEqualTo(AbroadConstants.ABROAD_PASSPORT_DRAW_STATUS_INIT)
                    .andIsDeletedEqualTo(false)
                    .andDrawStatusEqualTo(AbroadConstants.ABROAD_PASSPORT_DRAW_DRAW_STATUS_UNDRAW);
            List<PassportDraw> passportDraws = passportDrawMapper.selectByExample(example);
            for (PassportDraw passportDraw : passportDraws) {

                sendPassportDrawSubmitMsgToCadreAdmin(passportDraw.getId(), null);
            }
        }
    }

    // 干部提交办理证件申请，给干部本人发短信提醒
    @Async
    public void sendPassportApplySubmitMsgToCadre(Integer passportApplyId, HttpServletRequest request){

        //HttpServletRequest request = ContextHelper.getRequest();
        try {
            // 发送短信
            ShortMsgBean shortMsgBean = getShortMsgBean(ShiroHelper.getCurrentUserId(),
                    null, "passportApplySubmit", passportApplyId);
            shortMsgService.send(shortMsgBean, IpUtils.getRealIp(request));
        }catch (Exception ex){
            ex.printStackTrace();
            logger.error("短信发送失败, {}, {}, {}, {}, {}, {}, {}",
                    new Object[]{ShiroHelper.getCurrentUsername(), ex.getMessage(), request.getRequestURI(),
                            request.getMethod(),
                            JSONUtils.toString(request.getParameterMap(), false),
                            RequestUtils.getUserAgent(request), IpUtils.getRealIp(request)});
        }
    }

    // 干部提交办理证件申请，给干部管理员发短信提醒
    @Async
    public void sendPassportApplySubmitMsgToCadreAdmin(int passportApplyId, String ip){

        PassportApply passportApply = passportApplyMapper.selectByPrimaryKey(passportApplyId);
        SysUserView applyUser = passportApply.getCadre().getUser();

        MetaType passportClass = passportApply.getPassportClass();

        CadreView cadre = cadreService.dbFindByUserId(applyUser.getId());
        String cadreTitle = cadre.getTitle();

        ContentTpl tpl = shortMsgService.getTpl(ContentTplConstants.CONTENT_TPL_PASSPORTAPPLY_SUBMIT_ADMIN);
        List<SysUserView> receivers = contentTplService.getShorMsgReceivers(tpl.getId());

        for (SysUserView uv : receivers) {
            try {
                int userId = uv.getId();
                String mobile = userBeanService.getMsgMobile(userId);
                String msgTitle = userBeanService.getMsgTitle(userId);

                String msg = MessageFormat.format(tpl.getContent(), msgTitle,
                        cadreTitle, applyUser.getRealname(), passportClass.getName());

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
                logger.error("干部提交办理证件申请，给干部管理员发短信提醒失败。申请人：{}， 审核人：{}, {},{}", new Object[]{
                        applyUser.getRealname(), uv.getRealname(), uv.getMobile(), ex.getMessage()
                });
            }
        }
    }

    // 干部提交因私出国，给干部管理员发短信提醒
    @Async
    public void sendApplySelfSubmitMsgToCadreAdmin(int applySelfId, String ip){

        /*try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("发送短信开始。。。");*/

        ApplySelfService applySelfService = ApplicationContextSupport.getContext().getBean(ApplySelfService.class);
        ApplySelf applySelf = applySelfService.get(applySelfId);
        SysUserView applyUser = applySelf.getUser();

        CadreView cadre = cadreService.dbFindByUserId(applyUser.getId());
        String cadreTitle = cadre.getTitle();

        //List<SysUserView> cadreAdmin = sysUserService.findByRole(RoleConstants.ROLE_CADREADMIN);
        ContentTpl tpl = shortMsgService.getTpl(ContentTplConstants.CONTENT_TPL_APPLYSELF_SUBMIT_INFO);
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
                logger.error("干部提交因私出国，给干部管理员发短信提醒失败。申请人：{}， 审核人：{}, {},{}", new Object[]{
                    applyUser.getRealname(), uv.getRealname(), uv.getMobile(), ex.getMessage()
                });
            }
        }
    }

    // 因私申请如果通过全部领导的审批（下一个审批身份是管理员），则短信通知管理员
    @Async
    public void sendApplySelfPassMsgToCadreAdmin(int applySelfId, String ip){

        ApplySelfService applySelfService = ApplicationContextSupport.getContext().getBean(ApplySelfService.class);
        ApplySelf applySelf = applySelfService.get(applySelfId);
        SysUserView applyUser = applySelf.getUser();

        CadreView cadre = cadreService.dbFindByUserId(applyUser.getId());
        String cadreTitle = cadre.getTitle();

        //List<SysUserView> cadreAdmin = sysUserService.findByRole("cadreAdmin-menu1");
        ContentTpl tpl = shortMsgService.getTpl(ContentTplConstants.CONTENT_TPL_APPLYSELF_PASS_INFO);
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
                logger.error("干部提交因私出国，给干部管理员发短信提醒失败。申请人：{}， 审核人：{}, {}, {}", new Object[]{
                        applyUser.getRealname(), uv.getRealname(), uv.getMobile(), ex.getMessage()
                });
            }
        }
    }

    // 干部提交领取证件申请，给干部管理员发短信提醒
    @Async
    public void sendPassportDrawSubmitMsgToCadreAdmin(int passportDrawId, String ip){

        /*try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("发送短信开始。。。");*/

        PassportDraw passportDraw = passportDrawMapper.selectByPrimaryKey(passportDrawId);
        SysUserView applyUser = passportDraw.getCadre().getUser();

        CadreView cadre = cadreService.dbFindByUserId(applyUser.getId());
        String cadreTitle = cadre.getTitle();

        //List<SysUserView> cadreAdmin = sysUserService.findByRole(RoleConstants.ROLE_CADREADMIN);
        ContentTpl tpl = shortMsgService.getTpl(ContentTplConstants.CONTENT_TPL_PASSPORTDRAW_SUBMIT_INFO);
        List<SysUserView> receivers = contentTplService.getShorMsgReceivers(tpl.getId());

        for (SysUserView uv : receivers) {
            try {
                int userId = uv.getId();
                String mobile = userBeanService.getMsgMobile(userId);
                String msgTitle = userBeanService.getMsgTitle(userId);

                String msg = MessageFormat.format(tpl.getContent(), msgTitle,
                        cadreTitle,applyUser.getRealname(),
                        passportDraw.getPassportClass().getName(),
                        AbroadConstants.ABROAD_PASSPORT_DRAW_TYPE_MAP.get(passportDraw.getType()));

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
                logger.error("干部提交因私出国，给干部管理员发短信提醒失败。申请人：{}， 审核人：{}, {},{}", new Object[]{
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

        if(StringUtils.equals(type, "passportInfo")){ // 发送证件信息

            Passport passport = passportMapper.selectByPrimaryKey(id);
            String key = ContentTplConstants.CONTENT_TPL_PASSPORT_INFO;

            ContentTpl tpl = shortMsgService.getTpl(key);
            bean.setRelateId(tpl.getId());
            bean.setType(tpl.getName());

            SysUserView uv = passport.getUser();
            bean.setReceiver(uv.getId()); // 覆盖
            String msgTitle = userBeanService.getMsgTitle(uv.getId());
            String mobile = userBeanService.getMsgMobile(uv.getId());

            MetaType passportClass = passport.getPassportClass();
            String msg = MessageFormat.format(tpl.getContent(), msgTitle, passportClass.getName(), passport.getCode(),
                    DateUtils.formatDate(passport.getIssueDate(), DateUtils.YYYY_MM_DD_CHINA),
                    DateUtils.formatDate(passport.getExpiryDate(), DateUtils.YYYY_MM_DD_CHINA));
            bean.setContent(msg);
            bean.setMobile(mobile);

        }else if(StringUtils.equals(type, "passport")){ // 取消集中管理-证件到期/不再担任职务/作废

            Passport passport = passportMapper.selectByPrimaryKey(id);
            String key = ContentTplConstants.CONTENT_TPL_PASSPORT_EXPIRE;
            if(passport.getCancelType() == AbroadConstants.ABROAD_PASSPORT_CANCEL_TYPE_DISMISS){
                key = ContentTplConstants.CONTENT_TPL_PASSPORT_DISMISS;
            }
            if(passport.getCancelType() == AbroadConstants.ABROAD_PASSPORT_CANCEL_TYPE_ABOLISH){
                key = ContentTplConstants.CONTENT_TPL_PASSPORT_ABOLISH;
            }

            ContentTpl tpl = shortMsgService.getTpl(key);
            bean.setRelateId(tpl.getId());
            bean.setType(tpl.getName());

            SysUserView uv = passport.getUser();
            bean.setReceiver(uv.getId()); // 覆盖
            String msgTitle = userBeanService.getMsgTitle(uv.getId());
            String mobile = userBeanService.getMsgMobile(uv.getId());

            MetaType passportClass = passport.getPassportClass();
            String msg = MessageFormat.format(tpl.getContent(), msgTitle, passportClass.getName());
            bean.setContent(msg);
            bean.setMobile(mobile);

        }else if(StringUtils.equals(type, "passportKeep")){ // 证件集中管理-添加证件/新办证件交回

            Passport passport = passportMapper.selectByPrimaryKey(id);
            String key = ContentTplConstants.CONTENT_TPL_PASSPORT_KEEP_ADD;
            if(passport.getApplyId()!=null){
                key = ContentTplConstants.CONTENT_TPL_PASSPORT_KEEP_APPLY;
            }

            ContentTpl tpl = shortMsgService.getTpl(key);
            bean.setRelateId(tpl.getId());
            bean.setType(tpl.getName());

            SysUserView uv = passport.getUser();
            bean.setReceiver(uv.getId()); // 覆盖
            String msgTitle = userBeanService.getMsgTitle(uv.getId());
            String mobile = userBeanService.getMsgMobile(uv.getId());

            MetaType passportClass = passport.getPassportClass();
            String msg = MessageFormat.format(tpl.getContent(), msgTitle,
                    passportClass.getName(), passport.getCode(),
                    DateUtils.formatDate(passport.getIssueDate(), DateUtils.YYYY_MM_DD_CHINA),
                    DateUtils.formatDate(passport.getExpiryDate(), DateUtils.YYYY_MM_DD_CHINA));
            bean.setContent(msg);
            bean.setMobile(mobile);

        }else if(StringUtils.equals(type, "applySelf")){

            ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(id);
            String key = ContentTplConstants.CONTENT_TPL_APPLYSELF_PASS;

            ApplySelfService applySelfService = ApplicationContextSupport.getContext().getBean(ApplySelfService.class);
            Map<Integer, ApprovalResult> approvalResultMap = applySelfService.getApprovalResultMap(id);
            ApprovalResult lastVal = approvalResultMap.get(0);

            SysUserView uv = applySelf.getUser();
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
                key = ContentTplConstants.CONTENT_TPL_APPLYSELF_UNPASS;
                ContentTpl tpl = shortMsgService.getTpl(key);
                bean.setRelateId(tpl.getId());
                bean.setType(tpl.getName());
                String msg = MessageFormat.format(tpl.getContent(), msgTitle,
                        StringUtils.defaultIfBlank(applySelf.getApprovalRemark(), "无"));
                bean.setContent(msg);
            }

            bean.setMobile(mobile);
        }else if(StringUtils.equals(type, "passportApplyPass")){

            PassportApply passportApply = passportApplyMapper.selectByPrimaryKey(id);
            String key = ContentTplConstants.CONTENT_TPL_PASSPORTAPPLY_PASS;
            ContentTpl tpl = shortMsgService.getTpl(key);
            bean.setRelateId(tpl.getId());
            bean.setType(tpl.getName());

            SysUserView uv = passportApply.getApplyUser();
            bean.setReceiver(uv.getId()); // 覆盖
            String msgTitle = userBeanService.getMsgTitle(uv.getId());
            String mobile = userBeanService.getMsgMobile(uv.getId());

            MetaType passportClass = passportApply.getPassportClass();
            String msg = MessageFormat.format(tpl.getContent(), msgTitle,
                    passportClass.getName(), DateUtils.formatDate(passportApply.getExpectDate(), DateUtils.YYYY_MM_DD_CHINA));
            bean.setContent(msg);
            bean.setMobile(mobile);
        }else if(StringUtils.equals(type, "passportApplyUnPass")){

            PassportApply passportApply = passportApplyMapper.selectByPrimaryKey(id);
            String key = ContentTplConstants.CONTENT_TPL_PASSPORTAPPLY_UNPASS;
            ContentTpl tpl = shortMsgService.getTpl(key);
            bean.setRelateId(tpl.getId());
            bean.setType(tpl.getName());

            SysUserView uv = passportApply.getApplyUser();
            bean.setReceiver(uv.getId()); // 覆盖
            String msgTitle = userBeanService.getMsgTitle(uv.getId());
            String mobile = userBeanService.getMsgMobile(uv.getId());

            MetaType passportClass = passportApply.getPassportClass();
            String msg = MessageFormat.format(tpl.getContent(), msgTitle, passportClass.getName());
            bean.setContent(msg);
            bean.setMobile(mobile);
        }else if(StringUtils.equals(type, "passportApplyDraw")){
            PassportApply passportApply = passportApplyMapper.selectByPrimaryKey(id);
            String key = ContentTplConstants.CONTENT_TPL_PASSPORTAPPLY_DRAW;
            ContentTpl tpl = shortMsgService.getTpl(key);
            bean.setRelateId(tpl.getId());
            bean.setType(tpl.getName());

            SysUserView uv = passportApply.getApplyUser();
            bean.setReceiver(uv.getId()); // 覆盖
            String msgTitle = userBeanService.getMsgTitle(uv.getId());
            String mobile = userBeanService.getMsgMobile(uv.getId());

            MetaType passportClass = passportApply.getPassportClass();
            String msg = MessageFormat.format(tpl.getContent(), msgTitle, passportClass.getName(),
                    DateUtils.formatDate(passportApply.getApproveTime(), DateUtils.YYYY_MM_DD_CHINA),
                    DateUtils.formatDate(passportApply.getExpectDate(), DateUtils.YYYY_MM_DD_CHINA));
            bean.setContent(msg);
            bean.setMobile(mobile);
        }else if(StringUtils.equals(type, "taiwanRecordHandle")){ // 因公赴台备案-催交证件
            TaiwanRecord taiwanRecord = taiwanRecordMapper.selectByPrimaryKey(id);
            String key = ContentTplConstants.CONTENT_TPL_TAIWANRECORD_HANDLE;
            ContentTpl tpl = shortMsgService.getTpl(key);
            bean.setRelateId(tpl.getId());
            bean.setType(tpl.getName());

            SysUserView uv = taiwanRecord.getUser();
            bean.setReceiver(uv.getId()); // 覆盖
            String msgTitle = userBeanService.getMsgTitle(uv.getId());
            String mobile = userBeanService.getMsgMobile(uv.getId());

            String msg = MessageFormat.format(tpl.getContent(), msgTitle,
                    DateUtils.formatDate(taiwanRecord.getRecordDate(), DateUtils.YYYY_MM_DD_CHINA),
                    DateUtils.formatDate(taiwanRecord.getStartDate(), DateUtils.YYYY_MM_DD_CHINA),
                    DateUtils.formatDate(taiwanRecord.getEndDate(), DateUtils.YYYY_MM_DD_CHINA),
                    DateUtils.formatDate(taiwanRecord.getExpectDate(), DateUtils.YYYY_MM_DD_CHINA));
            bean.setContent(msg);
            bean.setMobile(mobile);
        }else if(StringUtils.equals(type, "passportApplySubmit")){
            PassportApply passportApply = passportApplyMapper.selectByPrimaryKey(id);
            String key = ContentTplConstants.CONTENT_TPL_PASSPORTAPPLY_SUBMIT; // 发给干部
            ContentTpl tpl = shortMsgService.getTpl(key);
            bean.setRelateId(tpl.getId());
            bean.setType(tpl.getName());

            SysUserView uv = passportApply.getApplyUser();
            bean.setReceiver(uv.getId()); // 覆盖
            String msgTitle = userBeanService.getMsgTitle(uv.getId());
            String mobile = userBeanService.getMsgMobile(uv.getId());

            MetaType passportClass = passportApply.getPassportClass();
            String msg = MessageFormat.format(tpl.getContent(), msgTitle, passportClass.getName());
            bean.setContent(msg);
            bean.setMobile(mobile);
        }else if(StringUtils.equals(type, "passportDraw")){

            PassportDraw passportDraw = passportDrawMapper.selectByPrimaryKey(id);
            String key = ContentTplConstants.CONTENT_TPL_PASSPORTDRAW;
            ContentTpl tpl = shortMsgService.getTpl(key);
            bean.setRelateId(tpl.getId());
            bean.setType(tpl.getName());

            SysUserView uv = passportDraw.getUser();
            bean.setReceiver(uv.getId()); // 覆盖
            String msgTitle = userBeanService.getMsgTitle(uv.getId());
            String mobile = userBeanService.getMsgMobile(uv.getId());

            MetaType passportClass = passportDraw.getPassportClass();
            String returnDate = DateUtils.formatDate(passportDraw.getReturnDate(), "yyyy年MM月dd日");
            String msg = MessageFormat.format(tpl.getContent(), msgTitle, passportClass.getName(), returnDate);
            bean.setContent(msg);
            bean.setMobile(mobile);
        }else if(StringUtils.equals(type, "passportDrawReturn")){

            PassportDraw passportDraw = passportDrawMapper.selectByPrimaryKey(id);
            String key = ContentTplConstants.CONTENT_TPL_PASSPORTDRAW_RETURN;
            ContentTpl tpl = shortMsgService.getTpl(key);
            bean.setRelateId(tpl.getId());
            bean.setType(tpl.getName());

            SysUserView uv = passportDraw.getUser();
            bean.setReceiver(uv.getId()); // 覆盖
            String msgTitle = userBeanService.getMsgTitle(uv.getId());
            String mobile = userBeanService.getMsgMobile(uv.getId());

            //MetaType passportClass = passportDraw.getPassportClass();
            String drawTime = DateUtils.formatDate(passportDraw.getDrawTime(), "yyyy年MM月dd日");
            String returnDate = DateUtils.formatDate(passportDraw.getReturnDate(), "yyyy年MM月dd日");
            String msg = MessageFormat.format(tpl.getContent(), msgTitle, drawTime, returnDate,
                    passportDraw.getPassportClass().getName());
            bean.setContent(msg);
            bean.setMobile(mobile);
        }else if(StringUtils.equals(type, "passportDrawReturnSuccess")){

            PassportDraw passportDraw = passportDrawMapper.selectByPrimaryKey(id);
            String key = ContentTplConstants.CONTENT_TPL_PASSPORTDRAW_RETURN_SUCCESS;
            ContentTpl tpl = shortMsgService.getTpl(key);
            bean.setRelateId(tpl.getId());
            bean.setType(tpl.getName());

            SysUserView uv = passportDraw.getUser();
            bean.setReceiver(uv.getId()); // 覆盖
            String msgTitle = userBeanService.getMsgTitle(uv.getId());
            String mobile = userBeanService.getMsgMobile(uv.getId());

            //String realReturnDate = DateUtils.formatDate(passportDraw.getRealReturnDate(), "yyyy年MM月dd日");
            String msg = MessageFormat.format(tpl.getContent(), msgTitle, passportDraw.getPassportClass().getName());
            bean.setContent(msg);
            bean.setMobile(mobile);
        }else if(StringUtils.equals(type, "passportDrawApply")){

            PassportDraw passportDraw = passportDrawMapper.selectByPrimaryKey(id);
            String key = "";
            if(passportDraw.getStatus()==AbroadConstants.ABROAD_PASSPORT_DRAW_STATUS_PASS){

                if(BooleanUtils.isTrue(passportDraw.getNeedSign())){
                    key = ContentTplConstants.CONTENT_TPL_PASSPORTDRAW_PASS_NEEDSIGN;
                }else{
                    key = ContentTplConstants.CONTENT_TPL_PASSPORTDRAW_PASS;
                }
            }
            if(passportDraw.getStatus()==AbroadConstants.ABROAD_PASSPORT_DRAW_STATUS_NOT_PASS){

                if(BooleanUtils.isTrue(passportDraw.getNeedSign())){
                    key = ContentTplConstants.CONTENT_TPL_PASSPORTDRAW_UNPASS_NEEDSIGN;
                }else{
                    key = ContentTplConstants.CONTENT_TPL_PASSPORTDRAW_UNPASS;
                }
            }

            ContentTpl tpl = shortMsgService.getTpl(key);
            bean.setRelateId(tpl.getId());
            bean.setType(tpl.getName());

            SysUserView uv = passportDraw.getUser();
            bean.setReceiver(uv.getId()); // 覆盖
            String msgTitle = userBeanService.getMsgTitle(uv.getId());
            String mobile = userBeanService.getMsgMobile(uv.getId());

            MetaType passportClass = passportDraw.getPassportClass();
            String msg = MessageFormat.format(tpl.getContent(), msgTitle, passportClass.getName(), passportDraw.getId());
            bean.setContent(msg);
            bean.setMobile(mobile);
        }

        return bean;
    }
}
