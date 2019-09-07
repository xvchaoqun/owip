package ext.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import controller.global.OpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.HtmlUtils;
import sys.entity.SendMsgResult;
import sys.gson.GsonUtils;
import sys.utils.HttpUtils;
import sys.utils.PropertiesUtils;

/**
 * Created by lm on 2017/10/24.
 */
public class SendMsgUtils {

    private static Logger logger = LoggerFactory.getLogger(SendMsgUtils.class);

    public static SendMsgResult sendMsg(String mobile, String content) {

        SendMsgResult sendMsgResult = new SendMsgResult();

        String url = PropertiesUtils.getString("shortMsg.url");
        String formStatusData = "{\"mobile\":\"" + mobile + "\", \"content\":\"" + HtmlUtils.htmlUnescape(content) + "\"}";

        try {
            String ret = HttpUtils.doPost(url, formStatusData);
            JsonObject jsonObject = GsonUtils.toJsonObject(ret);
            JsonElement errcode = jsonObject.get("errcode");

            sendMsgResult.setSuccess((errcode.getAsInt() == 0));
            sendMsgResult.setMsg(ret);
            return sendMsgResult;
        } catch (Exception e) {
            logger.error("异常", e);
            throw new OpException("系统错误:" + e.getMessage());
        }
    }
}
