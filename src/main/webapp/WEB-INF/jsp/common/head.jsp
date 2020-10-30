<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@include file="taglibs.jsp"%>
<jsp:include page="meta.jsp"/>
<title>${_plantform_name}</title>
<c:set var="favicon" value="${ctx}/favicon.ico"/>
<link rel="shortcut icon" href="${favicon}?_=${cm:lastModified(favicon)}" type="image/x-icon" />
<t:link href="/css/main.css"/>
<t:link href="/css/a.min.css"/>
<!--[if lte IE 9]>
<link rel="stylesheet" href="${ctx}/css/ie9.css"/>
<![endif]-->
<t:link href="/css/extend.css"/>
<t:link href="/css/setup.css"/>
<style>.avatar {width: ${_p_avatarWidth}px;}</style>
<style>${_pMap['globalCss']}</style>