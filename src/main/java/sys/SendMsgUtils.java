package sys;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import controller.global.OpException;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.web.util.HtmlUtils;
import sys.utils.PropertiesUtils;

import java.io.IOException;

/**
 * Created by lm on 2017/10/24.
 */
public class SendMsgUtils {

    public static SendMsgResult sendMsg(String mobile, String content) {

        SendMsgResult sendMsgResult = new SendMsgResult();

        String url = PropertiesUtils.getString("shortMsg.url");
        String formStatusData = "{\"mobile\":\"" + mobile + "\", \"content\":\"" + HtmlUtils.htmlUnescape(content) + "\"}";

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url);
        httppost.setEntity(new StringEntity(formStatusData, "UTF-8"));
        httppost.addHeader("content-type", "application/json");
        try {
            CloseableHttpResponse res = httpclient.execute(httppost);
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

                String ret = EntityUtils.toString(res.getEntity());
                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(ret, JsonObject.class);
                JsonElement errcode = jsonObject.get("errcode");

                sendMsgResult.setSuccess((errcode.getAsInt() == 0));
                sendMsgResult.setMsg(ret);

                return sendMsgResult;
            }
        } catch (IOException e) {

            e.printStackTrace();
            throw new OpException("系统错误:" + e.getMessage());
        }

        return null;
    }
}
