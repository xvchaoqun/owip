package service.sys;

import bean.ApprovalResult;
import bean.ShortMsgBean;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import domain.*;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BaseMapper;
import service.SpringProps;
import service.abroad.ApplySelfService;
import service.abroad.PassportService;
import service.cadre.CadreInfoService;
import sys.ShortMsgPropertyUtils;
import sys.constants.SystemConstants;
import sys.utils.DateUtils;
import sys.utils.FormUtils;
import sys.utils.PropertiesUtils;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;

@Service
public class ShortMsgService extends BaseMapper {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private PassportService passportService;
    @Autowired
    private CadreInfoService cadreInfoService;
    @Autowired
    private ApplySelfService applySelfService;
    @Autowired
    private SpringProps springProps;

    public ShortMsgBean getShortMsgBean(Integer sender, Integer receiver, String type, Integer id){

        ShortMsgBean bean = new ShortMsgBean();
        bean.setSender(sender);
        bean.setReceiver(receiver);

        if(StringUtils.equals(type, "passport")){

            Passport passport = passportMapper.selectByPrimaryKey(id);
            String key = SystemConstants.SHORT_MSG_KEY_PASSPORT_EXPIRE;
            if(passport.getCancelType() == SystemConstants.PASSPORT_CANCEL_TYPE_DISMISS){
                key = SystemConstants.SHORT_MSG_KEY_PASSPORT_DISMISS;
            }
            bean.setType(SystemConstants.SHORT_MSG_KEY_MAP.get(key));
            String msgTpl = ShortMsgPropertyUtils.msg(key);
            SysUser user = passport.getUser();
            bean.setReceiver(user.getId()); // 覆盖
            MetaType passportClass = passport.getPassportClass();
            String msg = MessageFormat.format(msgTpl, user.getRealname(), passportClass.getName());
            bean.setContent(msg);
            bean.setMobile(cadreInfoService.getCadreMobile(user.getId()));

        }else if(StringUtils.equals(type, "applySelf")){

            ApplySelf applySelf = applySelfMapper.selectByPrimaryKey(id);
            String key = SystemConstants.SHORT_MSG_KEY_APPLYSELF_PASS;
            Map<Integer, ApprovalResult> approvalResultMap = applySelfService.getApprovalResultMap(id);
            ApprovalResult lastVal = approvalResultMap.get(0);
            boolean status = (lastVal.getValue()!=null && lastVal.getValue()==1);
            if(!status)
                key = SystemConstants.SHORT_MSG_KEY_APPLYSELF_UNPASS;
            bean.setType(SystemConstants.SHORT_MSG_KEY_MAP.get(key));

            String msgTpl = ShortMsgPropertyUtils.msg(key);
            SysUser user = applySelf.getUser();
            bean.setReceiver(user.getId()); // 覆盖
            String msg = MessageFormat.format(msgTpl, user.getRealname());
            bean.setContent(msg);
            bean.setMobile(cadreInfoService.getCadreMobile(user.getId()));
        }else if(StringUtils.equals(type, "passportApplyPass")){

            PassportApply passportApply = passportApplyMapper.selectByPrimaryKey(id);
            String key = SystemConstants.SHORT_MSG_KEY_PASSPORTAPPLY_PASS;
            bean.setType(SystemConstants.SHORT_MSG_KEY_MAP.get(key));
            String msgTpl = ShortMsgPropertyUtils.msg(key);
            SysUser user = passportApply.getApplyUser();
            bean.setReceiver(user.getId()); // 覆盖
            MetaType passportClass = passportApply.getPassportClass();
            String msg = MessageFormat.format(msgTpl, user.getRealname(), passportClass.getName());
            bean.setContent(msg);
            bean.setMobile(cadreInfoService.getCadreMobile(user.getId()));
        }else if(StringUtils.equals(type, "passportApplyUnPass")){

            PassportApply passportApply = passportApplyMapper.selectByPrimaryKey(id);
            String key = SystemConstants.SHORT_MSG_KEY_PASSPORTAPPLY_UNPASS;
            bean.setType(SystemConstants.SHORT_MSG_KEY_MAP.get(key));
            String msgTpl = ShortMsgPropertyUtils.msg(key);
            SysUser user = passportApply.getApplyUser();
            bean.setReceiver(user.getId()); // 覆盖
            MetaType passportClass = passportApply.getPassportClass();
            String msg = MessageFormat.format(msgTpl, user.getRealname(), passportClass.getName());
            bean.setContent(msg);
            bean.setMobile(cadreInfoService.getCadreMobile(user.getId()));
        }else if(StringUtils.equals(type, "passportApplyDraw")){
            PassportApply passportApply = passportApplyMapper.selectByPrimaryKey(id);
            String key = SystemConstants.SHORT_MSG_KEY_PASSPORTAPPLY_DRAW;
            bean.setType(SystemConstants.SHORT_MSG_KEY_MAP.get(key));
            String msgTpl = ShortMsgPropertyUtils.msg(key);
            SysUser user = passportApply.getApplyUser();
            bean.setReceiver(user.getId()); // 覆盖
            MetaType passportClass = passportApply.getPassportClass();
            String msg = MessageFormat.format(msgTpl, user.getRealname(), passportClass.getName());
            bean.setContent(msg);
            bean.setMobile(cadreInfoService.getCadreMobile(user.getId()));
        }else if(StringUtils.equals(type, "passportDraw")){

            PassportDraw passportDraw = passportDrawMapper.selectByPrimaryKey(id);
            String key = SystemConstants.SHORT_MSG_KEY_PASSPORTDRAW;
            bean.setType(SystemConstants.SHORT_MSG_KEY_MAP.get(key));
            String msgTpl = ShortMsgPropertyUtils.msg(key);
            SysUser user = passportDraw.getUser();
            bean.setReceiver(user.getId()); // 覆盖
            MetaType passportClass = passportDraw.getPassportClass();
            String returnDate = DateUtils.formatDate(passportDraw.getReturnDate(), "yyyy年MM月dd日");
            String msg = MessageFormat.format(msgTpl, user.getRealname(), passportClass.getName(), returnDate);
            bean.setContent(msg);
            bean.setMobile(cadreInfoService.getCadreMobile(user.getId()));
        }else if(StringUtils.equals(type, "passportDrawReturn")){

            PassportDraw passportDraw = passportDrawMapper.selectByPrimaryKey(id);
            String key = SystemConstants.SHORT_MSG_KEY_PASSPORTDRAW_RETURN;
            bean.setType(SystemConstants.SHORT_MSG_KEY_MAP.get(key));
            String msgTpl = ShortMsgPropertyUtils.msg(key);
            SysUser user = passportDraw.getUser();
            bean.setReceiver(user.getId()); // 覆盖
            //MetaType passportClass = passportDraw.getPassportClass();
            String drawTime = DateUtils.formatDate(passportDraw.getDrawTime(), "yyyy年MM月dd日");
            String returnDate = DateUtils.formatDate(passportDraw.getReturnDate(), "yyyy年MM月dd日");
            String msg = MessageFormat.format(msgTpl, user.getRealname(), drawTime, returnDate);
            bean.setContent(msg);
            bean.setMobile(cadreInfoService.getCadreMobile(user.getId()));
        }else if(StringUtils.equals(type, "passportDrawApply")){

            PassportDraw passportDraw = passportDrawMapper.selectByPrimaryKey(id);
            String key = "";
            if(passportDraw.getStatus()==SystemConstants.PASSPORT_DRAW_STATUS_PASS){
                key = SystemConstants.SHORT_MSG_KEY_PASSPORTDRAW_PASS;
            }
            if(passportDraw.getStatus()==SystemConstants.PASSPORT_DRAW_STATUS_NOT_PASS){
                key = SystemConstants.SHORT_MSG_KEY_PASSPORTDRAW_UNPASS;
            }

            bean.setType(SystemConstants.SHORT_MSG_KEY_MAP.get(key));
            String msgTpl = ShortMsgPropertyUtils.msg(key);
            SysUser user = passportDraw.getUser();
            bean.setReceiver(user.getId()); // 覆盖
            MetaType passportClass = passportDraw.getPassportClass();
            String msg = MessageFormat.format(msgTpl, user.getRealname(), passportClass.getName(), passportDraw.getId());
            bean.setContent(msg);
            bean.setMobile(cadreInfoService.getCadreMobile(user.getId()));
        }

        return bean;
    }

    public boolean send(ShortMsgBean shortMsgBean, String ip){

        int sender = shortMsgBean.getSender();
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
