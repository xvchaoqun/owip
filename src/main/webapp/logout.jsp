<%@ page import="shiro.ShiroHelper" %>
<%--
  Created by IntelliJ IDEA.
  User: fafa
  Date: 2016/6/9
  Time: 12:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<%
  ShiroHelper.logout();
  response.sendRedirect("/");
%>
</body>
</html>
