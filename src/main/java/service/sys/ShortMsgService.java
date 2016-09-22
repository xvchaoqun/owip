package service.sys;

import bean.ApprovalResult;
import bean.ShortMsgBean;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import domain.abroad.ApplySelf;
import domain.abroad.Passport;
import domain.abroad.PassportApply;
import domain.abroad.PassportDraw;
import domain.base.ContentTpl;
import domain.cadre.Cadre;
import domain.sys.MetaType;
import domain.sys.ShortMsg;
import domain.sys.ShortMsgExample;
import domain.sys.SysUser;
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
import service.base.ContentTplService;
import service.cadre.CadreConcatService;
import service.cadre.CadreService;
import service.helper.ContextHelper;
import shiro.PasswordHelper;
import sys.constants.SystemConstants;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.IpUtils;
import sys.utils.PropertiesUtils;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;

@Service
public class ShortMsgService extends BaseMapper {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private CadreService cadreService;
    @Autowired
    private CadreConcatService cadreInfoService;
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

        SysUser sysUser = sysUserService.findByUsername(username);

        // 判断发短信频率
        Cache<String, String> findPassCache = cacheManager.getCache("FindPassDayCount");
        String cacheKey = DateUtils.formatDate(new Date(), DateUtils.YYYYMMDD) + "_" + sysUser.getMobile();
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
        bean.setReceiver(sysUser.getId());
        bean.setMobile(sysUser.getMobile());
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
        SysUser applyUser = applySelf.getUser();

        List<SysUser> cadreAdmin = sysUserService.findByRole("cadreAdmin");
        ContentTpl tpl = getShortMsgTpl(SystemConstants.CONTENT_TPL_APPLYSELF_SUBMIT_INFO);


        for (SysUser sysUser : cadreAdmin) {
            try {
                int userId = sysUser.getId();
                String mobile = sysUser.getMobile();

                Cadre cadre = cadreService.findByUserId(applyUser.getId());
                String msg = MessageFormat.format(tpl.getContent(), sysUser.getRealname(),
                        cadre.getUnit().getName(),applyUser.getRealname());

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
                    applyUser.getRealname(), sysUser.getRealname(), sysUser.getMobile(), ex.getMessage()
                });
            }
        }
    }

    // 因私申请如果通过全部领导的审批（下一个审批身份是管理员），则短信通知管理员
    @Async
    public void sendApplySelfPassMsgToCadreAdmin(int applySelfId, String ip){

        ApplySelf applySelf = applySelfService.get(applySelfId);
        SysUser applyUser = applySelf.getUser();

        List<SysUser> cadreAdmin = sysUserService.findByRole("cadreAdmin-menu1");
        ContentTpl tpl = getShortMsgTpl(SystemConstants.CONTENT_TPL_APPLYSELF_PASS_INFO);

        for (SysUser sysUser : cadreAdmin) {
            try {
                int userId = sysUser.getId();
                String mobile = sysUser.getMobile();

                Cadre cadre = cadreService.findByUserId(applyUser.getId());
                String msg = MessageFormat.format(tpl.getContent(), sysUser.getRealname(),
                        cadre.getUnit().getName(),applyUser.getRealname());

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
                        applyUser.getRealname(), sysUser.getRealname(), sysUser.getMobile(), ex.getMessage()
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

        if(StringUtils.equals(type, "passport")){

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

            SysUser user = passport.getUser();
            bean.setReceiver(user.getId()); // 覆盖
            MetaType passportClass = passport.getPassportClass();
            String msg = MessageFormat.format(tpl.getContent(), user.getRealname(), passportClass.getName());
            bean.setContent(msg);
            bean.setMobile(cadreInfoService.getCadreMobile(user.getId()));

        }else if(StringUtils.equals(type, "applySelf")){

            ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(id);
            String key = SystemConstants.CONTENT_TPL_APPLYSELF_PASS;
            Map<Integer, ApprovalResult> approvalResultMap = applySelfService.getApprovalResultMap(id);
            ApprovalResult lastVal = approvalResultMap.get(0);
            boolean status = (lastVal.getValue()!=null && lastVal.getValue()==1);
            if(!status)
                key = SystemConstants.CONTENT_TPL_APPLYSELF_UNPASS;
            ContentTpl tpl = getShortMsgTpl(key);
            bean.setType(tpl.getName());

            SysUser user = applySelf.getUser();
            bean.setReceiver(user.getId()); // 覆盖
            String msg = MessageFormat.format(tpl.getContent(), user.getRealname());
            bean.setContent(msg);
            bean.setMobile(cadreInfoService.getCadreMobile(user.getId()));
        }else if(StringUtils.equals(type, "passportApplyPass")){

            PassportApply passportApply = passportApplyMapper.selectByPrimaryKey(id);
            String key = SystemConstants.CONTENT_TPL_PASSPORTAPPLY_PASS;
            ContentTpl tpl = getShortMsgTpl(key);
            bean.setType(tpl.getName());

            SysUser user = passportApply.getApplyUser();
            bean.setReceiver(user.getId()); // 覆盖
            MetaType passportClass = passportApply.getPassportClass();
            String msg = MessageFormat.format(tpl.getContent(), user.getRealname(), passportClass.getName());
            bean.setContent(msg);
            bean.setMobile(cadreInfoService.getCadreMobile(user.getId()));
        }else if(StringUtils.equals(type, "passportApplyUnPass")){

            PassportApply passportApply = passportApplyMapper.selectByPrimaryKey(id);
            String key = SystemConstants.CONTENT_TPL_PASSPORTAPPLY_UNPASS;
            ContentTpl tpl = getShortMsgTpl(key);
            bean.setType(tpl.getName());

            SysUser user = passportApply.getApplyUser();
            bean.setReceiver(user.getId()); // 覆盖
            MetaType passportClass = passportApply.getPassportClass();
            String msg = MessageFormat.format(tpl.getContent(), user.getRealname(), passportClass.getName());
            bean.setContent(msg);
            bean.setMobile(cadreInfoService.getCadreMobile(user.getId()));
        }else if(StringUtils.equals(type, "passportApplyDraw")){
            PassportApply passportApply = passportApplyMapper.selectByPrimaryKey(id);
            String key = SystemConstants.CONTENT_TPL_PASSPORTAPPLY_DRAW;
            ContentTpl tpl = getShortMsgTpl(key);
            bean.setType(tpl.getName());

            SysUser user = passportApply.getApplyUser();
            bean.setReceiver(user.getId()); // 覆盖
            MetaType passportClass = passportApply.getPassportClass();
            String msg = MessageFormat.format(tpl.getContent(), user.getRealname(), passportClass.getName());
            bean.setContent(msg);
            bean.setMobile(cadreInfoService.getCadreMobile(user.getId()));
        }else if(StringUtils.equals(type, "passportApplySubmit")){
            PassportApply passportApply = passportApplyMapper.selectByPrimaryKey(id);
            String key = SystemConstants.CONTENT_TPL_PASSPORTAPPLY_SUBMIT;
            ContentTpl tpl = getShortMsgTpl(key);
            bean.setType(tpl.getName());

            SysUser user = passportApply.getApplyUser();
            bean.setReceiver(user.getId()); // 覆盖
            MetaType passportClass = passportApply.getPassportClass();
            String msg = MessageFormat.format(tpl.getContent(), user.getRealname(), passportClass.getName());
            bean.setContent(msg);
            bean.setMobile(cadreInfoService.getCadreMobile(user.getId()));
        }else if(StringUtils.equals(type, "passportDraw")){

            PassportDraw passportDraw = passportDrawMapper.selectByPrimaryKey(id);
            String key = SystemConstants.CONTENT_TPL_PASSPORTDRAW;
            ContentTpl tpl = getShortMsgTpl(key);
            bean.setType(tpl.getName());

            SysUser user = passportDraw.getUser();
            bean.setReceiver(user.getId()); // 覆盖
            MetaType passportClass = passportDraw.getPassportClass();
            String returnDate = DateUtils.formatDate(passportDraw.getReturnDate(), "yyyy年MM月dd日");
            String msg = MessageFormat.format(tpl.getContent(), user.getRealname(), passportClass.getName(), returnDate);
            bean.setContent(msg);
            bean.setMobile(cadreInfoService.getCadreMobile(user.getId()));
        }else if(StringUtils.equals(type, "passportDrawReturn")){

            PassportDraw passportDraw = passportDrawMapper.selectByPrimaryKey(id);
            String key = SystemConstants.CONTENT_TPL_PASSPORTDRAW_RETURN;
            ContentTpl tpl = getShortMsgTpl(key);
            bean.setType(tpl.getName());

            SysUser user = passportDraw.getUser();
            bean.setReceiver(user.getId()); // 覆盖
            //MetaType passportClass = passportDraw.getPassportClass();
            String drawTime = DateUtils.formatDate(passportDraw.getDrawTime(), "yyyy年MM月dd日");
            String returnDate = DateUtils.formatDate(passportDraw.getReturnDate(), "yyyy年MM月dd日");
            String msg = MessageFormat.format(tpl.getContent(), user.getRealname(), drawTime, returnDate,
                    passportDraw.getPassportClass().getName());
            bean.setContent(msg);
            bean.setMobile(cadreInfoService.getCadreMobile(user.getId()));
        }else if(StringUtils.equals(type, "passportDrawReturnSuccess")){

            PassportDraw passportDraw = passportDrawMapper.selectByPrimaryKey(id);
            String key = SystemConstants.CONTENT_TPL_PASSPORTDRAW_RETURN_SUCCESS;
            ContentTpl tpl = getShortMsgTpl(key);
            bean.setType(tpl.getName());

            SysUser user = passportDraw.getUser();
            bean.setReceiver(user.getId()); // 覆盖
            String realReturnDate = DateUtils.formatDate(passportDraw.getRealReturnDate(), "yyyy年MM月dd日");
            String msg = MessageFormat.format(tpl.getContent(), user.getRealname(), passportDraw.getPassportClass().getName(), realReturnDate);
            bean.setContent(msg);
            bean.setMobile(cadreInfoService.getCadreMobile(user.getId()));
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

            SysUser user = passportDraw.getUser();
            bean.setReceiver(user.getId()); // 覆盖
            MetaType passportClass = passportDraw.getPassportClass();
            String msg = MessageFormat.format(tpl.getContent(), user.getRealname(), passportClass.getName(), passportDraw.getId());
            bean.setContent(msg);
            bean.setMobile(cadreInfoService.getCadreMobile(user.getId()));
        }

        return bean;
    }

    public boolean send(ShortMsgBean shortMsgBean, String ip){

        Integer sender = shortMsgBean.getSender();
        int receiver = shortMsgBean.getReceiver();
        String content = shortMsgBean.getContent();
        String type = shortMsgBean.getType();

        SysUser sysUser = sysUserService.findById(receiver);
        if(sysUser==null){
            throw new RuntimeException("用户不存在。");
        }
        String mobile = shortMsgBean.getMobile();
        if(!FormUtils.match(PropertiesUtils.getString("mobile.regex"), mobile)){
            throw new RuntimeException("手机号码有误："+ mobile);
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
            throw new RuntimeException("系统错误。");
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
