<%@ page import="sys.utils.RequestUtils" %>
<%@ page import="sys.utils.IpUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ip</title>
</head>
<body>
<%=IpUtils.getRealIp(request)%>
</body>
</html>
