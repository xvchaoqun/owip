<%@ page import="sys.tags.CmTag" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%
    String username = request.getParameter("username");
    boolean b = CmTag.validUsername(username);

    out.write(username + " is " + b);
%>
</body>
</html>
