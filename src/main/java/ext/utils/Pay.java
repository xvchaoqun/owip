package ext.utils;

import com.edu.bnu.pay.Base64;
import com.edu.bnu.pay.SignUtilsImpl;
import com.edu.bnu.pay.SymmtricCryptoUtil;
import com.google.gson.Gson;
import controller.global.OpException;
import domain.sys.SysUserView;
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
import service.SpringProps;
import shiro.ShiroHelper;
import sys.tags.CmTag;
import sys.utils.FormUtils;
import sys.utils.JSONUtils;

import javax.crypto.Cipher;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
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
        
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("thirdorderid", sn);
        paramMap.put("thirdsystem", thirdsystem);
        
        List<BasicNameValuePair> params = new ArrayList<>();
        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
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
    
    public OrderQueryResult orderQuery(String sn){
    
        OrderQueryResult result = new OrderQueryResult();
        
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("thirdorderid", sn);
        paramMap.put("orderid", "");
        paramMap.put("thirdsystem", thirdsystem);
        
        List<BasicNameValuePair> params = new ArrayList<>();
        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
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
                result.setAmt(paramValueMap.get("actulamt"));
                String state = paramValueMap.get("state");
                result.setHasPay(StringUtils.equals(state, "1"));
                result.setPayerCode(paramValueMap.get("sno"));
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

        Map<String, String> paramMap = new HashMap<>();
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

    public String sign(Map<String, String> paramMap){
        
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

    public String testCallbackParams(String orderNo, String orderParams){

        Gson gson = new Gson();
        Map<String, String> params =  gson.fromJson(orderParams, Map.class);
        Map<String, String> callbackMap = new LinkedHashMap<>(params);
        callbackMap.remove("ordertype");
        callbackMap.remove("sign");
        callbackMap.put("orderid", orderNo + "back");
        callbackMap.put("state", "1");
        try {
            callbackMap.put("sign", URLEncoder.encode(Pay.getInstance().sign(callbackMap), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        callbackMap.put("actulamt", params.get("tranamt")); // 实际交易金额

        return FormUtils.requestParams(callbackMap);
    }

    // 计算服务器通知签名
    public boolean verifyNotify(HttpServletRequest request){

        String sign = request.getParameter("sign");
        if(org.apache.commons.lang3.StringUtils.isBlank(sign)) return false;

        String tranamt = request.getParameter("tranamt");
        String orderid = request.getParameter("orderid");
        String account = request.getParameter("account");
        String sno = request.getParameter("sno");
        String toaccount = request.getParameter("toaccount");
        String thirdsystem = request.getParameter("thirdsystem");
        String thirdorderid = request.getParameter("thirdorderid");
        String state = request.getParameter("state");
        String orderdesc = request.getParameter("orderdesc");
        String praram1 = request.getParameter("praram1");

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("tranamt", tranamt);
        paramMap.put("orderid", orderid);
        paramMap.put("account", account);
        paramMap.put("sno", sno);
        paramMap.put("toaccount", toaccount);
        paramMap.put("thirdsystem", thirdsystem);
        paramMap.put("thirdorderid", thirdorderid);
        paramMap.put("state", state);
        paramMap.put("orderdesc", orderdesc);
        paramMap.put("praram1", praram1);

        String verifySign = sign(paramMap);

        boolean ret = false;
        try {
            ret = org.apache.commons.lang3.StringUtils.equalsIgnoreCase(sign, verifySign);
            if(!ret) ret = org.apache.commons.lang3.StringUtils.equalsIgnoreCase(URLDecoder.decode(sign, "UTF-8"), verifySign);
        } catch (UnsupportedEncodingException e) {
            logger.error("异常", e);
        }
        if (!ret) {
            logger.warn("签名校验失败，{}, verifySign={}, sign={}",
                    JSONUtils.toString(request.getParameterMap(), false), verifySign, sign);
        }

        return ret;
    }

    @Override
    public OrderNotifyBean pageNotifyBean(HttpServletRequest request) {

        String orderNo = request.getParameter("thirdorderid");
        String payerCode = request.getParameter("sno");
        String amt = request.getParameter("actulamt"); // 单位 分
        String state = request.getParameter("state");

        OrderNotifyBean notifyBean = new OrderNotifyBean();
        notifyBean.setOrderNo(orderNo);
        notifyBean.setPayerCode(payerCode);
        notifyBean.setAmt(amt);
        notifyBean.setStatusCode(state);
        notifyBean.setHasPay(StringUtils.equals(state, "1"));

        return notifyBean;
    }

    @Override
    public OrderNotifyBean serverNotifyBean(HttpServletRequest request) {

        return pageNotifyBean(request);
    }

    public void payConfirmCheck(int[] pmdMemberIds, boolean isSelfPay, boolean isBatch) {

        Integer currentUserId = ShiroHelper.getCurrentUserId();
        if (currentUserId == null) {
            logger.error("缴费异常，currentUserId = null.");
            throw new OpException("操作失败，请您重新登录系统后再试。");
        }
        SysUserView uv = CmTag.getUserById(currentUserId);
        if (uv == null) {
            logger.error("缴费异常，currentUserId={} but uv = null.", currentUserId);
            throw new OpException("操作失败，请您重新登录系统后再试。");
        }

        SpringProps springProps = CmTag.getBean(SpringProps.class);
        if (!uv.isCasUser() && !springProps.devMode) {
            throw new OpException("您的账号是系统注册账号，不能使用校园卡支付。");
        }
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
