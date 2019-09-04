<%@ page import="service.base.WeixinService" %>
<%@ page import="sys.tags.CmTag" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page import="service.SpringProps" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>wechat</title>
</head>
<%
    SpringProps springProps = CmTag.getBean(SpringProps.class);
    WeixinService weixinService = CmTag.getBean(WeixinService.class);
    String title = request.getParameter("title");
    String pic = request.getParameter("pic");
    String _codes = request.getParameter("codes");
    String content = request.getParameter("content");
    String redirectUrl = request.getParameter("redirectUrl");
    String _redirectUrl = null;
    String siteHome = "http://192.168.1.2:8080";
    if(StringUtils.isNotBlank(redirectUrl)){
        if(!springProps.devMode) {
            siteHome = CmTag.getStringProperty("siteHome");
        }
        _redirectUrl = siteHome + "/cas?url=" + redirectUrl;
    }
    if(StringUtils.isNotBlank(content) && StringUtils.isNotBlank(_codes)) {
        weixinService.sendNews(title, content, _codes, pic, _redirectUrl);
    }
%>
<body>
<form method="post">
    title:<textarea type="text" name="title" rows="5" style="width: 500px">==组工消息提醒==</textarea>
    <br/>
    codes:<textarea type="text" name="codes" rows="5" style="width: 500px"></textarea> 多个账号请使用分隔符：|
    <br/>
    <br/>
    content:<textarea name="content" rows="10" style="width: 500px"></textarea>
    <br/>
    pic:<textarea name="pic" rows="10" style="width: 500px"></textarea>
    <br/>
    跳转地址:<%=siteHome%><textarea name="redirectUrl" rows="2" style="width: 300px">/m/pmd/pmdMember</textarea>
    <br/>
    <input type="submit" value="发送">
</form>
</body>
</html>
