<%@ page import="sys.tags.CmTag" %>
<%@ page import="service.base.OneSendService" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="domain.base.OneSend" %>
<%@ page import="sys.utils.JSONUtils" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%--
  Created by IntelliJ IDEA.
  User: lm
  Date: 2018/1/11
  Time: 11:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html>
<head>
    <title></title>
</head>
<body>
<%
  OneSendService oneSendService = CmTag.getBean(OneSendService.class);
  String user = request.getParameter("user");
  String content = request.getParameter("content");
  String ret = null;
  if(StringUtils.isNotBlank(user) && StringUtils.isNotBlank(content)) {
    List<String> userList = new ArrayList<>();
    List<String> realnameList = new ArrayList<>();
    userList.add(user);

    OneSend oneSend = oneSendService.sendMsg(userList, realnameList, content);
    request.setAttribute("oneSend", oneSend);

    ret = JSONUtils.toString(oneSend, false);
  }
%>
<form method="post">
  学工号：<input type="text" name="user"><br/><br/>
  内容：<textarea name="content" rows="5" cols="50">测试发送短信[onesend]</textarea><br/><br/>
  <input type="submit" value="send">
</form>
<br/><br/>
<%=ret%>
</body>
</html>
