<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
  <jsp:include page="/WEB-INF/jsp/common/meta.jsp"/>
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
  <meta charset="utf-8"/>
  <title>${_plantform_name}</title>
  <link href="${ctx}/assets/css/bootstrap.css" rel="stylesheet" type="text/css" />
  <t:link href="/extend/css/faq.css"/>
  <link href="${ctx}/assets/css/font-awesome.css" rel="stylesheet" type="text/css" />
</head>
<body style="background-color: #f8f8f8">
<div class="top">
  <div class="w1000">
    <div class="logo"><t:img src="/img/logo.png"/></div>
    <div class="txt">${_plantform_name}</div>
  </div>
</div>
<c:if test="${empty param.type}">
  <c:redirect url="/"/>
</c:if>
<c:set var="htmlFragment" value="${cm:getHtmlFragment(param.type)}"/>
<c:if test="${empty htmlFragment}">
  <c:redirect url="/"/>
</c:if>

<div class="container" style="background-color: #fff">
<div class="row" style="padding: 0 100px 0 100px">
${htmlFragment.content}
</div>
</div>
<script src="${ctx}/assets/js/jquery.js"></script>
</body>
</html>
