<%@ page import="com.google.gson.JsonObject" %>
<%@ page import="sys.utils.HttpUtils" %>
<%@ page import="com.google.gson.JsonElement" %>
<%@ page import="sys.gson.GsonUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0,user-scalable=no"/>
    <title>组工系统</title>
    <script src="${ctx}/assets/js/jquery.js"></script>
</head>
<body style="word-break:break-all;word-wrap:break-word">
<%
    out.write("微信OAuth2链接：" + request.getSession().getAttribute("wxUrl"));
    String code = request.getParameter("code");
    out.write("<br/>返回code：" + code);

    String corpId = "";
    String corpSecret = "";

    String requestUrl = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid={corpId}&corpsecret={corpSecret}"
            .replace("{corpId}", corpId).replace("{corpSecret}", corpSecret);
    JsonObject jsonObject = HttpUtils.get(requestUrl);
    String accessToken = GsonUtils.getAsString(jsonObject, "access_token");

    out.write("<br/><br/>第一步请求：" + requestUrl);
    out.write("<br/>返回：" + jsonObject.toString());

    requestUrl = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token={ACCESS_TOKEN}&code={CODE}"
            .replace("{ACCESS_TOKEN}", accessToken).replace("{CODE}", code);
    jsonObject = HttpUtils.get(requestUrl);

    out.write("<br/><br/>第二步请求：" + requestUrl);
    out.write("<br/>返回：" + jsonObject.toString());

    JsonElement element = jsonObject.get("UserId");

    if(element!=null) {
        out.write("<br/><br/>返回用户ID：" + element.getAsString());
    }
%>
</>
