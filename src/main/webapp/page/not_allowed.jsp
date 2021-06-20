<%@ page import="sys.utils.IpUtils" %>
<%@ page import="shiro.ShiroHelper" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0,user-scalable=no"/>
    <meta name="format-detection" content="telephone=no"/>
    <title>禁止访问</title>
</head>
<body>
禁止访问，请联系管理员
<br/>
<%=IpUtils.getRealIp(request)%>
<%
    ShiroHelper.logout();
%>
</body>
</html>
