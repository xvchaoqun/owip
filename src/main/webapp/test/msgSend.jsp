<%@ page import="bean.ShortMsgBean" %>
<%@ page import="domain.base.ContentTpl" %>
<%@ page import="domain.sys.SysUserView" %>
<%@ page import="ext.service.ShortMsgService" %>
<%@ page import="service.sys.SysUserService" %>
<%@ page import="sys.constants.ContentTplConstants" %>
<%@ page import="sys.constants.SystemConstants" %>
<%@ page import="sys.tags.CmTag" %>
<%@ page import="java.text.MessageFormat" %>
<%@ page import="org.apache.commons.lang.RandomStringUtils" %>
<%@ page import="org.apache.commons.lang.math.RandomUtils" %>
<%--
  Created by IntelliJ IDEA.
  User: fafa
  Date: 2016/3/14
  Time: 10:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<%
    ShortMsgService shortMsgService = CmTag.getBean(ShortMsgService.class);
    SysUserService sysUserService = CmTag.getBean(SysUserService.class);

    String username = "zzbgz";
    SysUserView uv = sysUserService.findByUsername(username);
    String code = RandomStringUtils.randomNumeric(4);
    int seq = RandomUtils.nextInt();
    ContentTpl tpl = shortMsgService.getTpl(ContentTplConstants.CONTENT_TPL_FIND_PASS);
    String msg = MessageFormat.format(tpl.getContent(), username, code, seq);

    ShortMsgBean bean = new ShortMsgBean();
    bean.setReceiver(uv.getId());
    bean.setMobile(uv.getMobile());
    bean.setContent(msg);
    bean.setRelateId(tpl.getId());
    bean.setRelateType(SystemConstants.SHORT_MSG_RELATE_TYPE_CONTENT_TPL);
    bean.setType(tpl.getName());

    boolean send = shortMsgService.send(bean, request.getRemoteAddr());

    out.write("send="+send);
%>
</body>
</html>
