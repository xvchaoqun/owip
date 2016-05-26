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
        <div class="logo"><img src="${ctx}<fmt:message key="site.logo" bundle="${spring}"/>" /></div>
        <div class="txt">组织工作管理与服务一体化平台</div>
    </div>
</div>
<div class="login_box">
    <div class="bg">
        <img src="${ctx}/extend/img/login_bg.jpg" />
    </div>
    <div class="w1000 login_con">
        <div id="login" class="visible login-layout" >
            <form id="login-form" method="POST" action="${ctx}/login">
                <dt>登录账号</dt><dd><div class="input_box"><span class="account"></span><input name="username" class="account" type="text"/></div></dd>
                <dt>登录密码</dt><dd><div class="input_box"><span class="password"></span><input name="passwd"class="password" type="password"/></div></dd>
                <dt>验证码</dt><dd><input  name="captcha" class="yz" type="text" maxlength="4" <c:if test="${!useCaptcha}">value="test"</c:if> />
                <img class="captcha" src="${ctx}/captcha.jpg" title="点击刷新" alt="验证码"/></dd>
                <dt></dt><dd><input name="rememberMe" type="checkbox" value="true"><span class="txt">下次自动登录</span></dd></dt>
                <dt></dt><dd><a href="javascript:;" class="submit_btn" id="login_btn"></a></dd>
                <dt></dt><dd><a href="" class="to_reg_btn" data-target="#reg">立即注册</a></dd>
            </form>
        </div>
        <div class="login-layout" id="reg">
            <form id="reg-form" method="POST" action="${ctx}/reg">
                <dt>登录账号</dt><dd><div class="input_box"><input name="username" type="text"/></div></dd>
                <dt>登录密码</dt><dd><div class="input_box"><input name="passwd" type="password"/></div></dd>
                <dt>密码确认</dt><dd><div class="input_box"><input name="repasswd" type="password"/></div></dd>
                <dt>类别</dt><dd><div class="input_box">
                <select name="type">
                    <option value="">请选择类别</option>
                    <option value="1">教职工</option>
                    <option value="2">本科生</option>
                    <option value="3">研究生</option>
                </select>
            </div></dd>
                <dt>真实姓名</dt><dd><div class="input_box"><input name="realname" type="text"/></div></dd>
                <dt>身份证号码</dt><dd><div class="input_box"><input name="idcard" type="text"/></div></dd>
                <dt>手机号码</dt><dd><div class="input_box"><input name="phone" type="text"/></div></dd>
                <dt>选择分党委</dt><dd><div class="input_box">
                <select name="party">
                    <option value="">请选择所属分党委</option>
                    <c:forEach var="entity" items="${partyMap}">
                        <option value="${entity.key}">${entity.value.name}</option>
                    </c:forEach>
                </select>
                </div></dd>
                <dt>验证码</dt><dd><input name="captcha" class="yz" type="text" maxlength="4" <c:if test="${!useCaptcha}">value="test"</c:if> />
                <img class="captcha" src="${ctx}/captcha.jpg" title="点击刷新" alt="验证码"/></dd>
                <dt></dt><dd><a href="javascript:;" class="submit_btn" id="reg_btn"></a></dd>
                <dt></dt><dd><a href="" class="to_login_btn" data-target="#login">返回登录</a></dd>
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
