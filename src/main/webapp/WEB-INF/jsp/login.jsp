<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<shiro:user>
    <c:redirect url="/"/>
</shiro:user>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/jsp/common/meta.jsp"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<meta charset="utf-8"/>
<title>组织工作管理与服务一体化平台</title>
<link href="${ctx}/extend/css/login.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="top">
    <div class="w1000">
        <div class="logo"><img src="${ctx}/extend/img/logo.png" /></div>
        <div class="txt">组织工作管理与服务一体化平台</div>
    </div>
</div>
<div class="login_box">
    <div class="bg">
        <img src="${ctx}/extend/img/login_bg.jpg" />
    </div>
    <div class="w1000 login_con">
        <div class="login">
            <form class="form account-form" method="POST" action="${ctx}/login">
                <dt>登录账号</dt><dd><div class="input_box"><span class="account"></span><input name="username" class="account" type="text"/></div></dd>
                <dt>登录密码</dt><dd><div class="input_box"><span class="password"></span><input name="passwd"class="password" type="password"/></div></dd>
                <dt>验证码</dt><dd><input  name="captcha" class="yz" type="text" maxlength="4"/>
                <img class="captcha" src="${ctx}/captcha.jpg" title="点击刷新" alt="验证码"/></dd>

            <dt></dt><dd><a href="javascript:;" class="login_btn"></a></dd>
            </form>
        </div>
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
<script src="${ctx}/extend/js/jquery.form.js"></script>
<script src="${ctx}/extend/js/login.js"></script>
</body>
</html>
