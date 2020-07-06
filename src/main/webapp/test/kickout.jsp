<%@ page import="java.util.Set" %>
<%@ page import="java.util.HashSet" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="shiro.ShiroHelper" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    String username = request.getParameter("username");
    Set<String> usernames = new HashSet<>();
    usernames.add(username);
    if(usernames.size()>0) {
        ShiroHelper.kickOutUser(usernames);
        System.out.println("踢下线：" + StringUtils.join(usernames, ","));
    }
%>
</body>
</html>
