<%@ page import="domain.base.OneSend" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page import="service.base.OneSendService" %>
<%@ page import="sys.tags.CmTag" %>
<%@ page import="sys.utils.JSONUtils" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html>
<head>
    <meta charset="utf-8"/>
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
