<%@ page import="ext.common.pay.OrderQueryResult" %>
<%@ page import="service.pmd.PmdOrderService" %>
<%@ page import="sys.tags.CmTag" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%
    String sn = request.getParameter("sn");

    PmdOrderService pmdOrderService = CmTag.getBean(PmdOrderService.class);
    OrderQueryResult queryResult = pmdOrderService.query(sn);

    out.write(queryResult.getRet());
%>
</body>
</html>
