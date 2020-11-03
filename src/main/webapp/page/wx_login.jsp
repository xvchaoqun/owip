<%@ page import="sys.tags.CmTag" %>
<%@ page import="service.base.WeixinService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    WeixinService weixinService = CmTag.getBean(WeixinService.class);
    String redirectUrL = CmTag.getStringProperty("siteHome") + "/wxLogin?url=/pmd/pmdMember";
    //String redirectUrL = CmTag.getStringProperty("siteHome") + "/page/wxLogin.jsp";
    String wxUrl = weixinService.getAuthorizeUrl(redirectUrL);

    //System.out.println("wxUrl = " + wxUrl);

    request.getSession().setAttribute("wxUrl", wxUrl);
    response.sendRedirect(wxUrl);
%>
</body>
</html>
