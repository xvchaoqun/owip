<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="/WEB-INF/jsp/common/meta.jsp"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta charset="utf-8"/>
    <title>${_plantform_name}</title>
    <link href="${ctx}/assets/css/bootstrap.css" rel="stylesheet" type="text/css"/>
    <t:link href="/extend/css/faq.css"/>
    <link href="${ctx}/assets/css/font-awesome.css" rel="stylesheet" type="text/css"/>
    <style>${_pMap['loginCss']}</style>
    <style>${_pMap['globalCss']}</style>
</head>
<body style="background-color: #f8f8f8">
<div class="top"
     style="background:url(${ctx}/img/login_top.jpg?_=${cm:lastModified(cm:getAbsolutePath('/img/login_top.jpg'))}) ${_sysConfig.loginTopBgColor} top right no-repeat">
    <div class="w1000">
        <div class="logo"><t:img src="/img/logo.png"/></div>
        <div class="separator"></div>
        <div class="txt">${_plantform_name}</div>
    </div>
</div>
<div class="container" style="background-color: #fff">
    <div class="page-content" id="page-content">
        <c:import url="${_path}_page">
            <c:param name="__includePage" value="true"/>
        </c:import>
    </div>
</div>
<script src="${ctx}/assets/js/jquery.js"></script>
</body>
</html>