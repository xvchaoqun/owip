<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>线上民主推荐</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/css/main.css" />
    <script src="${ctx}/assets/js/jquery.js"></script>
    <link rel="stylesheet" type="text/css" href="${ctx}/css/dr.css" />
</head>
<body class="login_body">
    <form id="login-form" method="post" action="${ctx}/dr/drOnline/login" autocomplete="off" disableautocomplete>
    <table class="dr_login_bg" border="0" cellspacing="0" cellpadding="0" width="100%" align="center">
        <tbody>
            <tr>
                <td class="login_top" height="125" align="center">
                    <div class="login_logo">
                        <div class="logo" style="cursor: pointer;" onclick="location.href='#'">
                            <t:img src="/img/logo_white.png"/></div>
                        <div class="txt">线&nbsp;上&nbsp;民&nbsp;主&nbsp;推&nbsp;荐&nbsp;投&nbsp;票</div>
                    </div>
                </td>
            </tr>
            <tr>
                <td>
                    <table class="login_bg_mid" border="0" cellspacing="0" cellpadding="0" width="820"
                           align="center" height="368">
                        <tbody>
                            <tr>
                                <td width="100%" align= "left">
                                    <div class="login_bg_mid_div" align= "right">
                                        <table style="background-color: rgba(0, 0, 0, 0);float: right;margin-top: 0px"  border="0" cellspacing="0" cellpadding="0" width="100%" align="right">
                                            <tbody>
                                                <tr height="14px">
                                                    <td style=";color: red;">
                                                        <div class="login-error" style="display: none;">
                                                            <i class="fa fa-times"></i> ${error}</div>
                                                    </td>
                                                </tr>
                                                <tr height="30">
                                                    <td style="color: #000000; font-size: 14px" align="center">
                                                        账&nbsp;&nbsp;&nbsp;号：
                                                        <input class="input" required name="username" autocomplete="off" disableautocomplete type="text"  placeholder="输入账号"/>

                                                    </td>
                                                </tr>
                                                <tr height="30">
                                                    <td style="color: #000000; font-size: 14px" align="center">
                                                        密&nbsp;&nbsp;&nbsp;码：
                                                        <input class="input" required name="passwd" autocomplete="off" disableautocomplete type="password"  placeholder="输入密码"/>
                                                        </td>
                                                </tr>
                                                <tr height="30">
                                                    <td style="color: #000000; font-size: 14px" align="center">
                                                       验证码：
                                                        <input type="text" name="captcha"
                                                                         required placeholder="输入验证码" autocomplete="off" disableautocomplete class="input yz" maxlength="4" />
                                                    </td>
                                                </tr>
                                                <tr>
                                                     <td>
                                                         <img style="margin-left: 60px;height: 30px;" id="captcha" src="${ctx}/captcha" class="captcha"/></td>
                                                </tr>
                                                <tr height="30">
                                                    <td align="center" style="padding-top: 10px;">
                                                        <input class="submit_btn btn btn-sm btn-success" value="登 录" id="login_btn" />
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </td>
            </tr>
        </tbody>
    </table>
    </form>
    <script src="${ctx}/extend/js/jquery.form.js"></script>
    <t:script src="/js/login.js"/>
<script>
    $('.login_bg_mid').css("background-size","840px 383px")
</script>
</body>
</html>
