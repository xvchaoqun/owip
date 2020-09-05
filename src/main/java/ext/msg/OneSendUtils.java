package ext.msg;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.xfire.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import sys.gson.GsonUtils;
import sys.utils.JSONUtils;

import java.util.HashMap;
import java.util.Map;

public class OneSendUtils {

    private static Logger logger = LoggerFactory.getLogger(OneSendUtils.class);

    private static ObjectMapper mapper = new ObjectMapper();
    private final static String sendMsgUrl = "http://onesend.bnu.edu.cn/tp_mp/service/SmsService?wsdl";
    private final static String sendEmailUrl = "http://onesend.bnu.edu.cn/tp_mp/service/EmailService?wsdl";
    private final static String sendWechatUrl = "http://onesend.bnu.edu.cn/tp_mp/service/WechatService?wsdl";

    // 使用校园统一通讯平台发送短信
    public static OneSendResult sendMsg(String[] codes, String msg) {

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
    public static OneSendResult sendEmail(String[] codes, String title, String content) {

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

    // 使用校园统一通讯平台发送微信
    public static OneSendResult sendWechat(String[] codes, String title, String content) {

        OneSendResult oneSendResult = new OneSendResult();
        if (codes == null || codes.length == 0 || StringUtils.isBlank(content)) return oneSendResult;
        oneSendResult.setType("微信");
        try {

            Resource resource = new UrlResource(sendWechatUrl);
            Client client = new Client(resource.getInputStream(), null);

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("person_info", StringUtils.join(codes, "^@^"));
            map.put("tp_name", "onesendbnuwebservicesimple");
            map.put("wechat_info", content);

            String json = mapper.writeValueAsString(map);
            Object[] results = client.invoke("saveWechatInfo", new Object[]{json});
            logger.info(JSONUtils.toString(results, false));
            client.close();

            oneSendResult.setRet((String) results[0]);

            // {"result":true,"msg_id":"13006982860800","msg":"发布成功"}
            JsonObject jsonObject = GsonUtils.toJsonObject(oneSendResult.getRet());
            JsonElement result = jsonObject.get("result");

            oneSendResult.setSuccess(BooleanUtils.isTrue(result.getAsBoolean()));

        } catch (Exception ex) {

            logger.error("微信发送失败。", ex);
        }

        return oneSendResult;
    }
}
