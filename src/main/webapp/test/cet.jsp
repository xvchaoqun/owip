<%@ page import="service.cet.CetRecordService" %>
<%@ page import="sys.tags.CmTag" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    CetRecordService cetRecordService = CmTag.getBean(CetRecordService.class);

    cetRecordService.syncAllUpperTrain();
%>
</body>
</html>
