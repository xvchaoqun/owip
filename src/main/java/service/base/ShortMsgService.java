package service.base;

import bean.ApprovalResult;
import bean.ShortMsgBean;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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
import domain.base.ShortMsg;
import domain.base.ShortMsgExample;
import domain.cadre.CadreView;
import domain.sys.SysUserView;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.SpringProps;
import service.abroad.ApplySelfService;
import service.cadre.CadreService;
import service.sys.SysUserService;
import service.sys.UserBeanService;
import shiro.PasswordHelper;
import shiro.ShiroHelper;
import sys.constants.SystemConstants;
import sys.utils.ContextHelper;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.IpUtils;
import sys.utils.JSONUtils;
import sys.utils.PropertiesUtils;
import sys.utils.RequestUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShortMsgService extends BaseMapper {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private UserBeanService userBeanService;
    @Autowired
    private CadreService cadreService;
    @Autowired
    private ApplySelfService applySelfService;
    @Autowired
    private SpringProps springProps;
    @Autowired
    private ContentTplService contentTplService;
    @Autowired
    protected PasswordHelper passwordHelper;
    @Autowired
    protected CacheManager cacheManager;

    // 修改密码
    public int changePassword(String username){

        SysUserView uv = sysUserService.findByUsername(username);

        // 判断发短信频率
        Cache<String, String> findPassCache = cacheManager.getCache("FindPassDayCount");
        String cacheKey = DateUtils.formatDate(new Date(), DateUtils.YYYYMMDD) + "_" + uv.getMobile();
        String cacheVal = findPassCache.get(cacheKey);
        int seq = 0;
        if(cacheVal!=null){
            seq = Integer.parseInt(cacheVal.split("_")[0]);
        }
        if(seq >= 5){
            throw new RuntimeException("该账号修改密码发送短信今日已发送了5次，请明天再试。");
        }

        seq = seq+1;

        String code = RandomStringUtils.randomNumeric(4);
        ContentTpl tpl = getShortMsgTpl(SystemConstants.CONTENT_TPL_FIND_PASS);
        String msg = MessageFormat.format(tpl.getContent(), username, code, seq);

        ShortMsgBean bean = new ShortMsgBean();
        bean.setReceiver(uv.getId());
        bean.setMobile(uv.getMobile());
        bean.setContent(msg);
        bean.setType(tpl.getName());
        try {

            boolean send = send(bean, IpUtils.getRealIp(ContextHelper.getRequest()));
            if(send){

                findPassCache.put(cacheKey, seq+"_"+code);
                return seq;
            }
        }catch (Exception ex){
            logger.error("修改密码发送短信失败，{}, {}", new Object[]{username, ex.getMessage()});
        }

        return 0;
    }

    // 给干部管理员发提醒，仅用于定时任务
    public void sendAbroadApprovalMsgToAdmin(){

        { // 干部提交办理证件申请，给干部管理员发短信提醒
            PassportApplyExample example = new PassportApplyExample();
            example.createCriteria().andStatusEqualTo(SystemConstants.PASSPORT_APPLY_STATUS_INIT)
                    .andAbolishEqualTo(false).andIsDeletedEqualTo(false);
            List<PassportApply> passportApplies = passportApplyMapper.selectByExample(example);
            for (PassportApply passportApply : passportApplies) {
                sendPassportApplySubmitMsgToCadreAdmin(passportApply.getId(), null);
            }
        }
        {// 干部提交因私出国，给干部管理员发短信提醒

            ApplySelfExample example = new ApplySelfExample();
            example.createCriteria().andStatusEqualTo(true)
                    .andFlowNodeEqualTo(SystemConstants.APPROVER_TYPE_ID_OD_FIRST)
                    .andIsDeletedEqualTo(false).andIsFinishEqualTo(false);
            List<ApplySelf> applySelfs = applySelfMapper.selectByExample(example);
            for (ApplySelf applySelf : applySelfs) {
                sendApplySelfSubmitMsgToCadreAdmin(applySelf.getId(), null);
            }
        }

        {// 因私申请如果通过全部领导的审批（下一个审批身份是管理员），则短信通知管理员

            ApplySelfExample example = new ApplySelfExample();
            example.createCriteria().andStatusEqualTo(true)
                    .andFlowNodeEqualTo(SystemConstants.APPROVER_TYPE_ID_OD_LAST)
                    .andIsDeletedEqualTo(false).andIsFinishEqualTo(false);
            List<ApplySelf> applySelfs = applySelfMapper.selectByExample(example);
            for (ApplySelf applySelf : applySelfs) {
                sendApplySelfPassMsgToCadreAdmin(applySelf.getId(), null);
            }
        }

        {// 干部提交领取证件申请，给干部管理员发短信提醒

            PassportDrawExample example = new PassportDrawExample();
            example.createCriteria().andStatusEqualTo(SystemConstants.PASSPORT_DRAW_STATUS_INIT)
                    .andIsDeletedEqualTo(false)
                    .andDrawStatusEqualTo(SystemConstants.PASSPORT_DRAW_DRAW_STATUS_UNDRAW);
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
            send(shortMsgBean, IpUtils.getRealIp(request));
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

        ContentTpl tpl = getShortMsgTpl(SystemConstants.CONTENT_TPL_PASSPORTAPPLY_SUBMIT_ADMIN);
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
                bean.setType(tpl.getName());

                send(bean, ip);
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

        ApplySelf applySelf = applySelfService.get(applySelfId);
        SysUserView applyUser = applySelf.getUser();

        CadreView cadre = cadreService.dbFindByUserId(applyUser.getId());
        String cadreTitle = cadre.getTitle();

        //List<SysUserView> cadreAdmin = sysUserService.findByRole(SystemConstants.ROLE_CADREADMIN);
        ContentTpl tpl = getShortMsgTpl(SystemConstants.CONTENT_TPL_APPLYSELF_SUBMIT_INFO);
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
                bean.setType(tpl.getName());

                send(bean, ip);
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

        ApplySelf applySelf = applySelfService.get(applySelfId);
        SysUserView applyUser = applySelf.getUser();

        CadreView cadre = cadreService.dbFindByUserId(applyUser.getId());
        String cadreTitle = cadre.getTitle();

        //List<SysUserView> cadreAdmin = sysUserService.findByRole("cadreAdmin-menu1");
        ContentTpl tpl = getShortMsgTpl(SystemConstants.CONTENT_TPL_APPLYSELF_PASS_INFO);
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
                bean.setType(tpl.getName());

                send(bean, ip);
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

        //List<SysUserView> cadreAdmin = sysUserService.findByRole(SystemConstants.ROLE_CADREADMIN);
        ContentTpl tpl = getShortMsgTpl(SystemConstants.CONTENT_TPL_PASSPORTDRAW_SUBMIT_INFO);
        List<SysUserView> receivers = contentTplService.getShorMsgReceivers(tpl.getId());

        for (SysUserView uv : receivers) {
            try {
                int userId = uv.getId();
                String mobile = userBeanService.getMsgMobile(userId);
                String msgTitle = userBeanService.getMsgTitle(userId);

                String msg = MessageFormat.format(tpl.getContent(), msgTitle,
                        cadreTitle,applyUser.getRealname(),
                        passportDraw.getPassportClass().getName(),
                        SystemConstants.PASSPORT_DRAW_TYPE_MAP.get(passportDraw.getType()));

                ShortMsgBean bean = new ShortMsgBean();
                bean.setSender(applyUser.getId());
                bean.setReceiver(userId);
                bean.setMobile(mobile);
                bean.setContent(msg);
                bean.setType(tpl.getName());

                send(bean, ip);
            }catch (Exception ex){
                ex.printStackTrace();
                logger.error("干部提交因私出国，给干部管理员发短信提醒失败。申请人：{}， 审核人：{}, {},{}", new Object[]{
                        applyUser.getRealname(), uv.getRealname(), uv.getMobile(), ex.getMessage()
                });
            }
        }
    }


    public ContentTpl getShortMsgTpl(String key){

        ContentTpl contentTpl = contentTplService.codeKeyMap().get(key);
        if(contentTpl == null || StringUtils.isBlank(contentTpl.getContent()))
            throw new NullPointerException(String.format("读取模板（CODE：%s）异常", key));

        return contentTpl;
    }

    public ShortMsgBean getShortMsgBean(Integer sender, Integer receiver, String type, Integer id){

        ShortMsgBean bean = new ShortMsgBean();
        bean.setSender(sender);
        bean.setReceiver(receiver);

        if(StringUtils.equals(type, "passportInfo")){ // 发送证件信息

            Passport passport = passportMapper.selectByPrimaryKey(id);
            String key = SystemConstants.CONTENT_TPL_PASSPORT_INFO;

            ContentTpl tpl = getShortMsgTpl(key);
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
            String key = SystemConstants.CONTENT_TPL_PASSPORT_EXPIRE;
            if(passport.getCancelType() == SystemConstants.PASSPORT_CANCEL_TYPE_DISMISS){
                key = SystemConstants.CONTENT_TPL_PASSPORT_DISMISS;
            }
            if(passport.getCancelType() == SystemConstants.PASSPORT_CANCEL_TYPE_ABOLISH){
                key = SystemConstants.CONTENT_TPL_PASSPORT_ABOLISH;
            }

            ContentTpl tpl = getShortMsgTpl(key);
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
            String key = SystemConstants.CONTENT_TPL_PASSPORT_KEEP_ADD;
            if(passport.getApplyId()!=null){
                key = SystemConstants.CONTENT_TPL_PASSPORT_KEEP_APPLY;
            }

            ContentTpl tpl = getShortMsgTpl(key);
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
            String key = SystemConstants.CONTENT_TPL_APPLYSELF_PASS;
            Map<Integer, ApprovalResult> approvalResultMap = applySelfService.getApprovalResultMap(id);
            ApprovalResult lastVal = approvalResultMap.get(0);

            SysUserView uv = applySelf.getUser();
            bean.setReceiver(uv.getId()); // 覆盖
            String msgTitle = userBeanService.getMsgTitle(uv.getId());
            String mobile = userBeanService.getMsgMobile(uv.getId());

            boolean status = (lastVal.getValue()!=null && lastVal.getValue()==1);
            if(status) {
                ContentTpl tpl = getShortMsgTpl(key);
                bean.setType(tpl.getName());
                String msg = MessageFormat.format(tpl.getContent(), msgTitle);
                bean.setContent(msg);
            }else{
                key = SystemConstants.CONTENT_TPL_APPLYSELF_UNPASS;
                ContentTpl tpl = getShortMsgTpl(key);
                bean.setType(tpl.getName());
                String msg = MessageFormat.format(tpl.getContent(), msgTitle,
                        StringUtils.defaultIfBlank(applySelf.getApprovalRemark(), "无"));
                bean.setContent(msg);
            }

            bean.setMobile(mobile);
        }else if(StringUtils.equals(type, "passportApplyPass")){

            PassportApply passportApply = passportApplyMapper.selectByPrimaryKey(id);
            String key = SystemConstants.CONTENT_TPL_PASSPORTAPPLY_PASS;
            ContentTpl tpl = getShortMsgTpl(key);
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
            String key = SystemConstants.CONTENT_TPL_PASSPORTAPPLY_UNPASS;
            ContentTpl tpl = getShortMsgTpl(key);
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
            String key = SystemConstants.CONTENT_TPL_PASSPORTAPPLY_DRAW;
            ContentTpl tpl = getShortMsgTpl(key);
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
            String key = SystemConstants.CONTENT_TPL_TAIWANRECORD_HANDLE;
            ContentTpl tpl = getShortMsgTpl(key);
            bean.setType(tpl.getName());

            SysUserView uv = taiwanRecord.getUser();
            bean.setReceiver(uv.getId()); // 覆盖
            String msgTitle = userBeanService.getMsgTitle(uv.getId());
            String mobile = userBeanService.getMsgMobile(uv.getId());

            String msg = MessageFormat.format(tpl.getContent(), msgTitle,
                    DateUtils.formatDate(taiwanRecord.getExpectDate(), DateUtils.YYYY_MM_DD_CHINA));
            bean.setContent(msg);
            bean.setMobile(mobile);
        }else if(StringUtils.equals(type, "passportApplySubmit")){
            PassportApply passportApply = passportApplyMapper.selectByPrimaryKey(id);
            String key = SystemConstants.CONTENT_TPL_PASSPORTAPPLY_SUBMIT; // 发给干部
            ContentTpl tpl = getShortMsgTpl(key);
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
            String key = SystemConstants.CONTENT_TPL_PASSPORTDRAW;
            ContentTpl tpl = getShortMsgTpl(key);
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
            String key = SystemConstants.CONTENT_TPL_PASSPORTDRAW_RETURN;
            ContentTpl tpl = getShortMsgTpl(key);
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
            String key = SystemConstants.CONTENT_TPL_PASSPORTDRAW_RETURN_SUCCESS;
            ContentTpl tpl = getShortMsgTpl(key);
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
            if(passportDraw.getStatus()==SystemConstants.PASSPORT_DRAW_STATUS_PASS){

                if(BooleanUtils.isTrue(passportDraw.getNeedSign())){
                    key = SystemConstants.CONTENT_TPL_PASSPORTDRAW_PASS_NEEDSIGN;
                }else{
                    key = SystemConstants.CONTENT_TPL_PASSPORTDRAW_PASS;
                }
            }
            if(passportDraw.getStatus()==SystemConstants.PASSPORT_DRAW_STATUS_NOT_PASS){

                if(BooleanUtils.isTrue(passportDraw.getNeedSign())){
                    key = SystemConstants.CONTENT_TPL_PASSPORTDRAW_UNPASS_NEEDSIGN;
                }else{
                    key = SystemConstants.CONTENT_TPL_PASSPORTDRAW_UNPASS;
                }
            }

            ContentTpl tpl = getShortMsgTpl(key);
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

    public boolean send(ShortMsgBean shortMsgBean, String ip){

        Integer sender = shortMsgBean.getSender();
        Integer receiver = shortMsgBean.getReceiver();
        String content = shortMsgBean.getContent();
        String type = shortMsgBean.getType();

        SysUserView uv = null;
        if(receiver!=null) {
            uv = sysUserService.findById(receiver);
            if (uv == null) {
                throw new RuntimeException("用户不存在。");
            }
        }
        String mobile = shortMsgBean.getMobile();
        if(!FormUtils.match(PropertiesUtils.getString("mobile.regex"), mobile)){
            throw new RuntimeException("手机号码有误："+ mobile+", realname="+uv.getRealname() + ", code="+ uv.getCode());
        }
        if(StringUtils.isBlank(content)){
            throw new RuntimeException("发送内容为空。");
        }

        String url= springProps.shortMsgUrl;
        String formStatusData="{\"mobile\":\""+ mobile +"\", \"content\":\""+ content +"\"}";

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url);
        httppost.setEntity(new StringEntity(formStatusData,"UTF-8"));
        httppost.addHeader("content-type", "application/json");
        CloseableHttpResponse res = null;
        try {
            if(springProps.shortMsgSend){
                res = httpclient.execute(httppost);
                if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

                    String ret = EntityUtils.toString(res.getEntity());
                    Gson gson = new Gson();
                    JsonObject jsonObject = gson.fromJson(ret, JsonObject.class);
                    JsonElement errcode = jsonObject.get("errcode");
                    boolean status = (errcode.getAsInt() == 0);

                    ShortMsg record = new ShortMsg();
                    record.setCreateTime(new Date());
                    record.setMobile(mobile);
                    record.setContent(content);
                    record.setReceiverId(receiver);
                    record.setSenderId(sender);
                    record.setType(type);
                    record.setStatus(status);
                    record.setRet(ret);
                    record.setIp(ip);
                    shortMsgMapper.insertSelective(record);

                    return status;
                }
            }else{
                ShortMsg record = new ShortMsg();
                record.setCreateTime(new Date());
                record.setMobile(mobile);
                record.setContent(content);
                record.setReceiverId(receiver);
                record.setSenderId(sender);
                record.setType(type);
                record.setRemark("test");
                record.setStatus(false);
                record.setIp(ip);
                shortMsgMapper.insertSelective(record);

                return true;
            }
        } catch (IOException e) {

            e.printStackTrace();
            throw new RuntimeException("系统错误:"+ e.getMessage());
        }
        return false;
    }

    @Transactional
    public int insertSelective(ShortMsg record){

        return shortMsgMapper.insertSelective(record);
    }
    @Transactional
    public void del(Integer id){

        shortMsgMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void batchDel(Integer[] ids){

        if(ids==null || ids.length==0) return;

        ShortMsgExample example = new ShortMsgExample();
        example.createCriteria().andIdIn(Arrays.asList(ids));
        shortMsgMapper.deleteByExample(example);
    }

    @Transactional
    public int updateByPrimaryKeySelective(ShortMsg record){
        return shortMsgMapper.updateByPrimaryKeySelective(record);
    }

    public Map<Integer, ShortMsg> findAll() {

        ShortMsgExample example = new ShortMsgExample();
        example.createCriteria().andStatusEqualTo(true);
        example.setOrderByClause("sort_order desc");
        List<ShortMsg> shortMsges = shortMsgMapper.selectByExample(example);
        Map<Integer, ShortMsg> map = new LinkedHashMap<>();
        for (ShortMsg shortMsg : shortMsges) {
            map.put(shortMsg.getId(), shortMsg);
        }

        return map;
    }
}
