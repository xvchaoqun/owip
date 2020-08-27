<%@ page import="org.apache.shiro.web.servlet.ShiroHttpServletRequest" %>
<%@ page import="shiro.ShiroHelper" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<meta http-equiv="Access-Control-Allow-Origin" content="*">
<script type="text/javascript" src="http://cas.bnu.edu.cn/cas/logout"></script>
<%
	ShiroHelper.logout();
	((HttpServletRequest) ((ShiroHttpServletRequest) request).getRequest()).getSession().invalidate();
%>
<script>
	location.href='https://zzgz.bnu.edu.cn'
</script>