<%@ page import="sys.utils.RequestUtils" %>
<%@ page import="sys.utils.IpUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ip</title>
</head>
<body>

RequestUtils.getHomeURL(request):<%=RequestUtils.getHomeURL(request)%>
<br/>
request.getHeader("X-Forwarded-Scheme"):<%=request.getHeader("X-Forwarded-Scheme")%>
<br/>
request.getServerName():<%=request.getServerName()%>
<br/>
request.getServerPort():<%=request.getServerPort()%>
<br/>
request.getContextPath():<%=request.getContextPath()%>
<br/>
request.getRemoteAddr():<%=request.getRemoteAddr()%>
<br/>
request.getRequestURL():<%=request.getRequestURL()%>
<br/>
IpUtils.getRealIp(request):<%=IpUtils.getRealIp(request)%>
</body>
</html>
