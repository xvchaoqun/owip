package service.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import domain.base.OneSend;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.xfire.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import persistence.base.OneSendMapper;
import shiro.ShiroHelper;
import sys.gson.GsonUtils;
import sys.utils.JSONUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lm on 2017/12/11.
 */
@Service
public class OneSendService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private OneSendMapper oneSendMapper;

    private static ObjectMapper mapper = new ObjectMapper();
    private final static String sendMsgUrl = "http://onesend.bnu.edu.cn/tp_mp/service/SmsService?wsdl";
    private final static String sendEmailUrl = "http://onesend.bnu.edu.cn/tp_mp/service/EmailService?wsdl";

    // userList格式：工号|手机号
    public OneSend sendMsg(List<String> userList, List<String> realnameList, String content) {

        if(userList.size()==0) return null;

        OneSendResult oneSendResult = sendMsg(userList.toArray(new String[]{}), content);

        OneSend _oneSend = new OneSend();
        _oneSend.setSendUserId(ShiroHelper.getCurrentUserId());
        _oneSend.setContent(content);
        _oneSend.setType(oneSendResult.getType());
        _oneSend.setRecivers(StringUtils.join(realnameList, ","));
        _oneSend.setCodes(StringUtils.join(userList, ","));
        _oneSend.setSendTime(new Date());
        _oneSend.setIsSuccess(oneSendResult.isSuccess());
        _oneSend.setRet(oneSendResult.getRet());

        oneSendMapper.insertSelective(_oneSend);

        return _oneSend;
    }


    // 使用校园统一通讯平台发送短信
    public OneSendResult sendMsg(String[] codes, String msg) {

        OneSendResult oneSendResult = new OneSendResult();
        if (codes == null || codes.length == 0 || StringUtils.isBlank(msg)) return oneSendResult;
        oneSendResult.setType("短信");
        try {

            Resource resource = new UrlResource(sendMsgUrl);
            Client client = new Client(resource.getInputStream(), null);

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("tp_name", "onesendbnuwebservicesimple");
            map.put("person_info", StringUtils.join(codes, "^@^"));
            map.put("sms_info", msg);
            String json = mapper.writeValueAsString(map);
            Object[] results = client.invoke("saveSmsInfo", new Object[]{json});
            logger.info(JSONUtils.toString(results, false));
            client.close();

            oneSendResult.setRet((String) results[0]);

            // {"result":true,"msg_id":"11638347911168","msg":"发布成功"}
            JsonObject jsonObject = GsonUtils.toJsonObject(oneSendResult.getRet());
            JsonElement result = jsonObject.get("result");

            oneSendResult.setSuccess(BooleanUtils.isTrue(result.getAsBoolean()));

        } catch (Exception ex) {

            logger.error("短信发送失败。", ex);
        }

        return oneSendResult;
    }

    // 使用校园统一通讯平台发送邮件
    public OneSendResult sendEmail(String[] codes, String title, String content) {

        OneSendResult oneSendResult = new OneSendResult();
        if (codes == null || codes.length == 0 || StringUtils.isBlank(content)) return oneSendResult;
        oneSendResult.setType("邮件");
        try {

            Resource resource = new UrlResource(sendEmailUrl);
            Client client = new Client(resource.getInputStream(), null);

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("tp_name", "onesendbnuwebservicesimple");
            map.put("recieve_person_info", StringUtils.join(codes, "^@^"));
            map.put("emial_title", title);
            map.put("email_info", content);

            String json = mapper.writeValueAsString(map);
            Object[] results = client.invoke("saveEmailInfo", new Object[]{json});
            logger.info(JSONUtils.toString(results, false));
            client.close();

            oneSendResult.setRet((String) results[0]);

            // {"result":true,"msg_id":"13006982860800","msg":"发布成功"}
            JsonObject jsonObject = GsonUtils.toJsonObject(oneSendResult.getRet());
            JsonElement result = jsonObject.get("result");

            oneSendResult.setSuccess(BooleanUtils.isTrue(result.getAsBoolean()));

        } catch (Exception ex) {

            logger.error("邮件发送失败。", ex);
        }

        return oneSendResult;
    }
}
