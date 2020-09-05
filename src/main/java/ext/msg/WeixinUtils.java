package ext.msg;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeixinUtils {

    private static Logger logger = LoggerFactory.getLogger(WeixinUtils.class);

    public static String GetResponse(String jsonStr) throws IOException {

        String path = "https://weixin.bnu.edu.cn/api/sendmsg.php?id=2";
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("Charset", "UTF-8");
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        conn.setRequestMethod("POST");

        conn.setDoOutput(true);
        conn.setDoInput(true);
        OutputStream os = conn.getOutputStream();
        os.write(jsonStr.getBytes("utf-8"));
        os.flush();
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));

        StringBuffer strBuffer = new StringBuffer("");
        String strLine = null;
        while ((strLine = br.readLine()) != null) {

            strBuffer.append(strLine);
        }
        String str = new String(strBuffer);
        return str;
    }

    public static SendMsgResult send(String[] codes, String content) {

        SendMsgResult sendMsgResult = new SendMsgResult();
        String jsonStr = "{" +
                "\"username\"" + ":\"" + StringUtils.join(codes, "|") + "\"," +
                "\"content\"" + ":\"" + content + "\"" +
                "}";
        try {

            jsonStr = GetResponse(jsonStr);

            JsonObject jsonObject = new JsonParser().parse(jsonStr).getAsJsonObject();
            JsonElement errcode = jsonObject.get("errcode");
            sendMsgResult.setSuccess((errcode.getAsInt() == 0));
            sendMsgResult.setMsg(jsonStr);

            logger.info("weixin response:{}", jsonStr);
            return sendMsgResult;

        } catch (Exception e) {
            logger.info("weixin error", e);
        }

        return sendMsgResult;
    }
}
