package ext.utils;

import com.edu.bnu.pay.Base64;
import com.edu.bnu.pay.SignUtilsImpl;
import com.edu.bnu.pay.SymmtricCryptoUtil;
import com.google.gson.Gson;
import ext.common.pay.*;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.GeneralSecurityException;
import java.util.*;

public class Pay implements IPay {
    
    private static Logger logger = LoggerFactory.getLogger(Pay.class);
    private static Pay pay;

    public static String toaccount = "1000329";
    public static String thirdsystem = "partyfee";

    public static String privateKeyUp = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAK/gErIchYSnQvbO4M10B4xkvbiPWFDQGfozlufzieR/wbhLgmc+KmBMAHs/AB3sQGiy9UcygSrbHcQD3nvbupoShtlmNAT9/f3stjttZ/q4n9wIlsIQ28aHHrPWxq3vg0DjAJgdGbYZ3+4NKOC1H4wh3/QgeT6T/EoGQ5Fpo/xjAgMBAAECgYEAhYGuD4CxJiqmHZ6bbbq3hC5xCmneG7JtFc4VrsvjkA4fKtw/CEpbdrAa6XPAjfZqSlAW+03uWW7t4H8jY5g/xDU5wWeadUUi5p5q3mmfJCqLiCDjTtirZ6KbhBRaOzr4jxfJkzEAAy8sSWcv1ftubvc/Ws/wyFCKUB5m8nIUbmECQQDYKtadHX1mEz6xmgYlr/A7pRB/vkdcy8xRF1BFC68CLCWo9AY7OkiymAquVrEhadp4gFKjbfHblA40JozXEPbxAkEA0EiOb7dFSp2LmzTCf/XC3B4Lsh47gJuiOkC4Y4D73UK33obsIyLktrhBNmY4Y/L6kxXW2rc/ERj1cAH6MCkwkwJBAIUt92V7Orv91V1kcK8dc1u7+atKVvskEHBRdcHkTeF/w4ARQBmTciCeLc51WNImPlSJcuB/p0fKMuoMai9Co3ECQFZ1B8sPxE+Ivh6a8/GxzkUYo7o4GnL0J48Otnt3WxUpULGqR/L91PqT2V3/aID0p1bOxfTcA+3Q8nCgIX5EWskCQA6mlzfNAaFpjasSVzFsUpfan14rxl3GzXPav0HCJMG1AjrgE1vrOVlxDIRqgBVqCiIgLruiDCPiFYIOzqCgCtY=";
    public static String deskey = "NmJhYmVlMjllMjdiNDU0MGI5MzQ3ODZj";
    public static String KEY_TYPE = "SHA1withRSA";

    public static String payURL = "https://pay.bnu.edu.cn/Order/CreateOrder";
    public static String orderQueryURL = "http://newpay.bnu.edu.cn:9000/Order/OrderQuery";
    public static String qrcodeURL = "http://newpay.bnu.edu.cn:9000/Order/CreteteMerrQrcode";
    public static String closeOrderURL = "http://newpay.bnu.edu.cn:9000/Order/CloseOrder";

    public static Pay getInstance(){

        if(pay ==null){
            pay = new Pay();
        }
        return pay;
    }

    public OrderCloseResult closeOrder(String sn) throws IOException {
    
        OrderCloseResult result = new OrderCloseResult();
        
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("thirdorderid", sn);
        paramMap.put("thirdsystem", thirdsystem);
        
        List<BasicNameValuePair> params = new ArrayList<>();
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            params.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
        }
        params.add(new BasicNameValuePair("sign", sign(paramMap)));

        String ret = null;
        try {
            HttpEntity postParams = new UrlEncodedFormEntity(params);
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost httppost = new HttpPost(closeOrderURL);
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(5000).setConnectionRequestTimeout(10000)
                    .setSocketTimeout(5000).build();
            httppost.setConfig(requestConfig);
            httppost.setEntity(postParams);
            CloseableHttpResponse res = httpclient.execute(httppost);
            result.setHttpStatus(res.getStatusLine().getStatusCode());
    
            ret = EntityUtils.toString(res.getEntity());
            result.setRet(ret);
            Gson gson = new Gson();
            Map<String, String> jo = gson.fromJson(ret, HashMap.class);
            String promptcode = jo.get("promptcode");
            
            result.setSuccess(StringUtils.equals(promptcode, "D0020")
                    || StringUtils.equals(promptcode, "D0027"));
            
        }catch (Exception ex){
            logger.error("关闭订单失败, ret:" + ret, ex);
            result.setException(ex.getMessage());
            throw ex;
        }
        
        return result;
    }
    
    public static String qrcode(String sn) throws IOException {
    
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("toaccount", toaccount);
        paramMap.put("thirdsystem", thirdsystem);
        paramMap.put("timeout", "");
        paramMap.put("trjnnum", "");
        paramMap.put("thirdorderid", sn);
        
        List<BasicNameValuePair> params = new ArrayList<>();
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            params.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
        }
        params.add(new BasicNameValuePair("sign", sign(paramMap)));
        HttpEntity postParams = new UrlEncodedFormEntity(params);

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(qrcodeURL);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000).setConnectionRequestTimeout(10000)
                .setSocketTimeout(5000).build();
        httppost.setConfig(requestConfig);

        httppost.setEntity(postParams);
        CloseableHttpResponse res = httpclient.execute(httppost);

        return EntityUtils.toString(res.getEntity());
    }
    
    public OrderQueryResult orderQuery(String sn){
    
        OrderQueryResult result = new OrderQueryResult();
        
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("thirdorderid", sn);
        paramMap.put("orderid", "");
        paramMap.put("thirdsystem", thirdsystem);
        
        List<BasicNameValuePair> params = new ArrayList<>();
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            params.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
        }
        params.add(new BasicNameValuePair("state", "all"));
        params.add(new BasicNameValuePair("sign", sign(paramMap)));
        
        try {
            HttpEntity postParams = new UrlEncodedFormEntity(params);
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost httppost = new HttpPost(orderQueryURL);

            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(5000).setConnectionRequestTimeout(10000)
                    .setSocketTimeout(5000).build();
            httppost.setConfig(requestConfig);

            httppost.setEntity(postParams);
            CloseableHttpResponse res = httpclient.execute(httppost);
    
            result.setHttpStatus(res.getStatusLine().getStatusCode());
            String ret = EntityUtils.toString(res.getEntity(), "UTF-8");
            result.setRet(ret);
            
            Gson gson = new Gson();
            HashMap map = gson.fromJson(ret, HashMap.class);
            boolean IsSucceed = (boolean) map.get("IsSucceed");
            if(IsSucceed){
                List msgs = (List) map.get("msg");
                if(msgs==null || msgs.size()==0) return result;

                String msg = (String) msgs.get(0);
                String[] msgParams = msg.split("&");
                Map<String, String> paramValueMap = new HashMap<>();
                for (String msgParam : msgParams) {
                    String[] entity = msgParam.split("=");
                    paramValueMap.put(entity[0], (entity.length>1)?entity[1]:"");
                }
                String state = paramValueMap.get("state");
                result.setHasPay(StringUtils.equals(state, "1"));
                result.setPayer(paramValueMap.get("name"));
                result.setAbolish(StringUtils.equals(state, "2"));
                result.setStatus(state);
                //System.out.println("state = " + state);
                //System.out.println(JSONUtils.toString(paramValueMap, false));
            }
        } catch (Exception ex){
            
            logger.error("查询订单异常", ex);
            result.setException(ex.getMessage());
        }
        
        return result;
    }

    // amount 单位：人民币元
    public OrderFormBean createOrder(String orderNo, String amount, String code, boolean isMobile) {

        OrderFormBean bean = new OrderFormBean();
        // 元 -> 分
        BigDecimal fen = new BigDecimal(amount).multiply(BigDecimal.valueOf(100));
        if(new BigDecimal(fen.intValue()).compareTo(fen)!=0){
            throw new RuntimeException("缴费金额有误。");
        }

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("tranamt", fen.intValue()+"");
        paramMap.put("account", "");
        paramMap.put("sno", code);
        paramMap.put("toaccount", toaccount);
        paramMap.put("thirdsystem", thirdsystem);
        paramMap.put("thirdorderid", orderNo);
        paramMap.put("ordertype", isMobile?"phone":"pc");
        paramMap.put("orderdesc", "");
        paramMap.put("praram1", "");

        bean.setParamMap(paramMap);
        bean.setSign(sign(paramMap));
        
        return bean;
    }
    
    public static String sign(Map<String, Object> paramMap){
        
        Map<String, Object> sortedParamMap = new TreeMap<String, Object>(new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareTo(s2); // 从小到大排序
            }
        });
        sortedParamMap.putAll(paramMap);
        
        List<String> paramList = new ArrayList<String>();
        for (Map.Entry<String, Object> entry : sortedParamMap.entrySet()) {
            Object value = entry.getValue();
            paramList.add(entry.getKey() + "=" + (value == null ? "" : value.toString()));
        }
        String abc = encrypt(StringUtils.join(paramList, "&"), deskey, "DESede");
        SignUtilsImpl SignUtils = new SignUtilsImpl();
        
        return SignUtils.sign(abc, privateKeyUp, KEY_TYPE);
    }
    
    public static String encrypt(String text, String key, String algorithm){
        
        try {
            byte[] bytes = text.getBytes("UTF-8"); //
            byte[] keyData = Base64.decode(key); //
            byte[] cipherBytes = SymmtricCryptoUtil.symmtricCrypto(bytes,
                    keyData, algorithm, Cipher.ENCRYPT_MODE);
            return Base64.encode(cipherBytes);
        } catch (GeneralSecurityException e) {
            return "";
        } catch (UnsupportedEncodingException e1) {
            return "";
        }
    }
}
