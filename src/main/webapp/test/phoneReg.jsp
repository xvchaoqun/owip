<%@ page import="sys.tags.CmTag" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%
    String phone = request.getParameter("phone");
    boolean b = CmTag.validMobile(phone);

    out.write(phone + " is " + b);
%>
</body>
</html>
