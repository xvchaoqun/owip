<%@ page import="org.apache.http.client.methods.CloseableHttpResponse" %>
<%@ page import="org.apache.http.client.methods.HttpPost" %>
<%@ page import="org.apache.http.entity.StringEntity" %>
<%@ page import="org.apache.http.impl.client.CloseableHttpClient" %>
<%@ page import="org.apache.http.impl.client.HttpClients" %>
<%@ page import="org.apache.http.util.EntityUtils" %>
<%--
  Created by IntelliJ IDEA.
  User: fafa
  Date: 2016/3/14
  Time: 10:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<%
  String url="https://weixin.bnu.edu.cn/sms/massms.php?id=2";
  String formStatusData="{\"mobile\":\"13810487549\", \"content\":\"孙秋瑞同志，您好！您提交的领取使用大陆居民往来台湾通行证的申请（编码为：D34）已通过审批，请派人携带有效证件（教工卡、学生卡或身份证）并凭此短信到组织部（主楼A306）领取证件。谢谢！\"}";
  StringEntity params =new StringEntity(formStatusData,"UTF-8");
  CloseableHttpClient httpclient = HttpClients.createDefault();
  HttpPost httppost = new HttpPost(url);
  httppost.setEntity(params);
  httppost.addHeader("content-type", "application/json");
  CloseableHttpResponse res = httpclient.execute(httppost);
  out.println(EntityUtils.toString(res.getEntity()));
%>
</body>
</html>
