<%@ page import="org.apache.shiro.web.servlet.ShiroHttpServletRequest" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%
	((HttpServletRequest) ((ShiroHttpServletRequest) request).getRequest()).getSession().invalidate();
	String redirectURL = "http://xxxx/authserver/logout?service=http://xxxxx";
	response.sendRedirect(redirectURL);
%>