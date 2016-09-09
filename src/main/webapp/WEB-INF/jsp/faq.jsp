<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
  <jsp:include page="/WEB-INF/jsp/common/meta.jsp"/>
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
  <meta charset="utf-8"/>
  <title>组织工作管理与服务一体化平台</title>
  <link href="${ctx}/assets/css/bootstrap.css" rel="stylesheet" type="text/css" />
<%--  <link rel="stylesheet" href="${ctx}/extend/css/bootstrap-theme-3.3.5.css" />--%>
  <link href="${ctx}/extend/css/faq.css" rel="stylesheet" type="text/css" />
  <link href="${ctx}/assets/css/font-awesome.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="top">
  <div class="w1000">
    <div class="logo"><img src="${ctx}<fmt:message key="site.logo" bundle="${spring}"/>" /></div>
    <div class="txt">组织工作管理与服务一体化平台</div>
  </div>
</div>
<c:choose>
  <c:when test="${param.type==1}">
    <c:set var="noteType" value="${HTML_FRAGMENT_MEMBER_IN_NOTE_FRONT_TEACHER}"/>
  </c:when>
  <c:when test="${param.type==2}">
    <c:set var="noteType" value="${HTML_FRAGMENT_MEMBER_IN_NOTE_FRONT_STUDENT}"/>
  </c:when>
  <c:otherwise>
    <c:redirect url="/"/>
  </c:otherwise>
</c:choose>
<div class="container">
<div class="row">
${cm:getHtmlFragment(noteType).content}
</div>
</div>
<!--[if !IE]> -->
<script type="text/javascript">
  window.jQuery || document.write("<script src='${ctx}/assets/js/jquery.js'>"+"<"+"/script>");
</script>
<!-- <![endif]-->
<!--[if IE]>
<script type="text/javascript">
  window.jQuery || document.write("<script src='${ctx}/assets/js/jquery1x.js'>"+"<"+"/script>");
</script>
<![endif]-->
</body>
</html>
