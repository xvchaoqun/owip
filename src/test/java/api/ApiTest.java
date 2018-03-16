package api;

import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import sys.utils.MD5Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lm on 2018/3/15.
 */
public class ApiTest {

    @Test
    public void abroad() throws IOException {

        String url = "http://localhost:8080/api/abroad/approve_count";
        String app = "oa";
        String key = "b887e286bf5d82b7b9712ed03d3e6e0e";
        String code = "111120130691";
        //String code = "zzbgz";
        String _signStr = String.format("app=%s&code=%s&key=%s", app, code, key);

        String sign = MD5Util.md5Hex(_signStr, "utf-8");

        List<BasicNameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("code", code));
        urlParameters.add(new BasicNameValuePair("app", app));
        urlParameters.add(new BasicNameValuePair("sign", sign));
        HttpEntity postParams = new UrlEncodedFormEntity(urlParameters);

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url);
        httppost.setEntity(postParams);
        CloseableHttpResponse res = httpclient.execute(httppost);

        System.out.println(res.getStatusLine().getStatusCode());

        System.out.println(_signStr);

        // {"Message":"该用户不是干部","Success":false}
        // {"Message":"系统访问出错","Success":false}
        // {"Message":"没有这个学工号","Success":false}
        // {"Success":true,"Count":0}

        System.out.println(EntityUtils.toString(res.getEntity()));
    }
}
