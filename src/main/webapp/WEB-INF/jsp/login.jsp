<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<fmt:message key="login.useCaptcha" bundle="${spring}" var="useCaptcha"/>
<shiro:user>
    <c:redirect url="/"/>
</shiro:user>
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
    <script type="text/javascript">location.href = "${ctx}/jsp/browsers.jsp?type=unsupport";</script>
    <![endif]-->
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
            <form id="login-form" method="POST" action="${ctx}/login">
                <dt>登录账号</dt>
                <dd>
                    <div class="input_box"><span class="account"></span><input name="username" class="account"
                                                                               type="text"/></div>
                </dd>
                <dt>登录密码</dt>
                <dd>
                    <div class="input_box"><span class="password"></span><input name="passwd" class="password"
                                                                                type="password"/></div>
                </dd>
                <dt>验证码</dt>
                <dd><input name="captcha" class="yz" type="text" maxlength="4" autocomplete="off" disableautocomplete
                           <c:if test="${!useCaptcha}">value="test"</c:if> />
                    <img class="captcha" src="${ctx}/captcha" title="点击刷新" alt="验证码"/></dd>
                <dt></dt>
                <dd><input name="rememberMe" type="checkbox" value="true"><span class="txt">下次自动登录</span></dd>
                </dt>
                <dt></dt>
                <dd><a href="javascript:;" class="submit_btn" id="login_btn"></a></dd>
                <dt></dt>
                <dd>
                    <a href="${ctx}/jsp/browsers.jsp" target="_blank" class="to_reg_btn" style="float: left">推荐浏览器</a>
                    <a href="" class="to_reg_btn" data-target="#reg">立即注册</a>
                    <a href="${ctx}/find_pass" class="to_reg_btn">忘记密码</a>
                </dd>
            </form>

            <div class="msg">
                ${_sysConfig.loginMsg}
            </div>
        </div>
        <div class="login-layout" id="reg">
            <div class="reg-tip">
                <ul>
                    <li>
                        <i class="ace-icon fa fa-exclamation-triangle"></i> 如果您已经有信息门户的账号，请不要在此注册。
                    </li>
                    <li><i class="ace-icon fa fa-phone"></i> 手机号码可用于密码找回，请正确填写。</li>
                </ul>
            </div>
            <form id="reg-form" method="POST" action="${ctx}/member_reg">
                <dt>登录账号</dt>
                <dd>
                    <div class="input_box"><input name="username" type="text"/></div>
                </dd>
                <dt>登录密码</dt>
                <dd>
                    <div class="input_box"><input name="passwd" type="password"/></div>
                </dd>
                <dt>密码确认</dt>
                <dd>
                    <div class="input_box"><input name="repasswd" type="password"/></div>
                </dd>
                <dt>类别</dt>
                <dd>
                    <div class="input_box">
                        <select name="type">
                            <option value="">请选择类别</option>
                            <option value="1">教职工</option>
                            <option value="2">本科生</option>
                            <option value="3">研究生</option>
                        </select>
                    </div>
                </dd>
                <dt>真实姓名</dt>
                <dd>
                    <div class="input_box"><input name="realname" type="text"/></div>
                </dd>
                <dt>身份证号码</dt>
                <dd>
                    <div class="input_box"><input name="idcard" type="text"/></div>
                </dd>
                <dt>手机号码</dt>
                <dd>
                    <div class="input_box"><input name="phone" type="text"/></div>
                </dd>

                <dt>联系分党委</dt>
                <dd>
                    <div class="input_box">
                        <select name="party">
                            <option value="">请选择</option>
                            <c:forEach var="entity" items="${partyMap}">
                                <c:if test="${!entity.value.isDeleted}">
                                    <option value="${entity.key}">${entity.value.name}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </div>
                </dd>
                <dt>验证码</dt>
                <dd><input name="captcha" class="yz" type="text" maxlength="4"
                           <c:if test="${!useCaptcha}">value="test"</c:if> />
                    <img class="captcha" src="${ctx}/captcha" title="点击刷新" alt="验证码"/></dd>
                <dt></dt>
                <dd><a href="javascript:;" class="submit_btn" id="reg_btn"></a></dd>
                <dt></dt>
                <dd><a href="" class="to_login_btn" data-target="#login">返回登录</a></dd>
            </form>
        </div>
    </div>
</div>
<!--[if !IE]> -->
<script type="text/javascript">
    window.jQuery || document.write("<script src='${ctx}/assets/js/jquery.js'>" + "<" + "/script>");
</script>
<!-- <![endif]-->
<!--[if IE]>
<script type="text/javascript">
    window.jQuery || document.write("<script src='${ctx}/assets/js/jquery1x.js'>" + "<" + "/script>");
</script>
<![endif]-->
<script src="${ctx}/extend/js/jquery.form.js"></script>
<t:script src="/js/login.js"/>
</body>
</html>
