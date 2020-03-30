<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<fmt:message key="login.useCaptcha" bundle="${spring}" var="useCaptcha"/>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="/WEB-INF/jsp/common/meta.jsp"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta charset="utf-8"/>
    <title>${_plantform_name}</title>
    <link href="${ctx}/assets/css/font-awesome.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/assets/css/bootstrap.css" rel="stylesheet" type="text/css"/>
    <t:link href="/css/login.css"/>
    <!--[if lt IE 9]>
    <script type="text/javascript">location.href = "${ctx}/page/browsers.jsp?type=unsupport";</script>
    <![endif]-->
    <script src="${ctx}/assets/js/jquery.js"></script>
    <style>${_pMap['loginCss']}</style>
</head>
<body>
<div class="top"
     style="background:url(${ctx}/img/login_top.jpg?_=${cm:lastModified(cm:getAbsolutePath('/img/login_top.jpg'))}) ${_sysConfig.loginTopBgColor} top right no-repeat">
    <div class="w1000">
        <div class="logo"><t:img src="/img/logo.png"/></div>
        <div class="separator"></div>
        <div class="txt">${_plantform_name}</div>
    </div>
</div>
<div class="login_box ${_sysConfig.displayLoginMsg?'':'no-msg'}">

    <div class="bg">
        <t:img src="/img/login_bg.jpg"/>
    </div>
    <div class="w1000 login_con">
        <div id="login" class="visible login-layout">
            <div class="login-error" style="display: none">
                <i class="fa fa-times"></i> ${error}</div>
            <form id="login-form" method="POST" action="${ctx}/dr/drOnline/login" autocomplete="off" disableautocomplete>
                <dt>登录账号</dt>
                <dd>
                    <div class="input_box"><span class="account"></span>
                        <input name="username" class="account" type="text"/></div>
                </dd>
                <dt>登录密码</dt>
                <dd>
                    <div class="input_box"><span class="password"></span>
                        <input name="passwd" class="password" type="password" <c:if test="${useCaptcha}">autocomplete="new-password"</c:if>/></div>
                </dd>
                <dt>验证码</dt>
                <dd><input name="captcha" class="yz" type="text" maxlength="4"
                           <c:if test="${!useCaptcha}">value="test"</c:if> />
                    <img class="captcha" src="${ctx}/captcha" title="点击刷新" alt="验证码"/></dd>
                <dt></dt>
                <dd></dd>
                </dt>
                <dt></dt>
                <dd><a href="javascript:;" class="submit_btn" id="login_btn"></a></dd>
                <dt></dt>
                <dd>
                    <a href="${ctx}/page/browsers.jsp" target="_blank" class="to_reg_btn" style="float: left">推荐浏览器</a>
                </dd>
            </form>

            <div class="msg">
                需要修改文本
                ${_sysConfig.loginMsg}
            </div>
        </div>
    </div>
</div>
<script src="${ctx}/extend/js/jquery.form.js"></script>
<t:script src="/js/login.js"/>
</body>
</html>
