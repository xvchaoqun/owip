package pay;

import org.apache.commons.lang3.StringUtils;
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
import sys.utils.PropertiesUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lm on 2018/1/18.
 */
public class QueryOrderTest {

    public final static String paycode = PropertiesUtils.getString("pay.campuscard.paycode");
    public final static String queryUrl = PropertiesUtils.getString("pay.campuscard.query.url");

    public final static String closeTradeUrl = PropertiesUtils.getString("pay.campuscard.closeTrade.url");
    public final static String closeTradeAppid = PropertiesUtils.getString("pay.campuscard.closeTrade.appid");
    public final static String closeTradeSalt = PropertiesUtils.getString("pay.campuscard.closeTrade.salt");

    @Test
    public void closeTrade() throws IOException {

        // ?paycode=&sn=&payertype=&payer=&sign=
        // md5(paycode+payertype+payer+sn)
        // return: {ret=true/false,msg=’错误信息’ Data={Paycode=,sn=,payertype=,payer=,paid=,paidtime=, amt= }}
       // String sn = "2018010210240864";
        //String sn = "2018010210240575";
        //String sn = "2018010210240870";

        //String sn = "2018010210240568";
        String sn = "201801021024056801";



        //String sn = "2018010210240579"; // 关闭已支付的订单号

        String _signStr = String.format("appId=%s&orderNo=%s&salt=%s",
                closeTradeAppid, sn, closeTradeSalt);

        String sign = MD5Util.md5Hex(_signStr, "utf-8");

        List<BasicNameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("appId", closeTradeAppid));
        urlParameters.add(new BasicNameValuePair("orderNo", sn));
        urlParameters.add(new BasicNameValuePair("sign", sign));
        HttpEntity postParams = new UrlEncodedFormEntity(urlParameters);

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(closeTradeUrl);
        httppost.setEntity(postParams);
        CloseableHttpResponse res = httpclient.execute(httppost);

        System.out.println(res.getStatusLine().getStatusCode());

        System.out.println(_signStr);

        // {"code":"3001","desc":"该交易已关闭，请勿重复关闭"}
        // {"code":"3001","desc":"该订单不存在"}
        // {"code":"0000","desc":"成功"}
        // {"code":"0000","desc":"该交易已成功，请确认"}

        System.out.println(EntityUtils.toString(res.getEntity()));
    }

    @Test
    public void query() throws IOException {

        // ?paycode=&sn=&payertype=&payer=&sign=
        // md5(paycode+payertype+payer+sn)
        // return: {ret=true/false,msg=’错误信息’ Data={Paycode=,sn=,payertype=,payer=,paid=,paidtime=, amt= }}
       /* String sn = "2018010210240575";
        String payer = "11112013069";  */

        String sn = "2018010210240805";
        String payer = "88040";

        //String sn = "201801021024080501";
        //String payer = "11312015074";

        //String sn = "201801021024080501";
        //String payer = "11312012117";


       // String sn = "2018010210240864";
       // String payer = "11312012117";

        String payertype = "1";
        String sign = MD5Util.md5Hex(paycode+payertype+payer+sn, "utf-8");

        List<BasicNameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("paycode", paycode));
        urlParameters.add(new BasicNameValuePair("sn", sn));
        urlParameters.add(new BasicNameValuePair("payertype", payertype));
        urlParameters.add(new BasicNameValuePair("payer", payer));
        urlParameters.add(new BasicNameValuePair("sign", sign));
        HttpEntity postParams = new UrlEncodedFormEntity(urlParameters);

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(queryUrl);
        httppost.setEntity(postParams);
        //httppost.addHeader("content-type", "application/json");
        CloseableHttpResponse res = httpclient.execute(httppost);

        System.out.println(res.getEntity().getContent());
        String ret = EntityUtils.toString(res.getEntity());
        System.out.println(ret);
        System.out.println(StringUtils.isNotBlank(ret));
    }


}
