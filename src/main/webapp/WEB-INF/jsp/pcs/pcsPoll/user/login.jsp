<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/pcs/constants.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>${_p_pcsPollSiteName}</title>
    <t:link href="/css/main.css"/>
    <t:link href="/css/dr.css"/>
    <!--[if lt IE 9]>
    <script type="text/javascript">location.href = "${ctx}/page/browsers.jsp?type=unsupport&url=/ddh";</script>
    <![endif]-->
</head>
<body class="login_body">
<form id="login-form" method="post" action="${ctx}/user/ddh" autocomplete="off" disableautocomplete>
    <table class="dr_login_bg" border="0" cellspacing="0" cellpadding="0" width="100%" align="center">
        <tbody>
        <tr>
            <td class="login_top" height="125" align="center">
                <div class="login_logo">
                    <div class="logo" style="cursor: pointer;" onclick="location.href='#'">
                        <t:img src="/img/logo_white.png"/></div>
                    <div class="txt">${_p_pcsSiteName}</div>
                </div>
            </td>
        </tr>
        <tr>
            <td>
                <table class="login_bg_mid"
                       style="background:url(/img/pcs_login_bg.png?_=${cm:lastModified(cm:getAbsolutePath('/img/pcs_login_bg.png'))})"
                       border="0" cellspacing="0" cellpadding="0" width="820"
                       align="center" height="368">
                    <tbody>
                    <tr>
                        <td width="100%" align="left">
                            <div class="login_bg_mid_div" align="right">
                                <table style="background-color: rgba(0, 0, 0, 0);float: right;margin-top: 0px"
                                       border="0" cellspacing="0" cellpadding="0" width="100%" align="right">
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
                                            <input class="input" required name="username" autocomplete="off"
                                                   disableautocomplete type="text" placeholder="输入账号"/>

                                        </td>
                                    </tr>
                                    <tr height="30">
                                        <td style="color: #000000; font-size: 14px" align="center">
                                            密&nbsp;&nbsp;&nbsp;码：
                                            <input class="input" required name="passwd" autocomplete="off"
                                                   disableautocomplete type="password" placeholder="输入密码"/>
                                        </td>
                                    </tr>
                                    <tr height="30">
                                        <td style="color: #000000; font-size: 14px" align="center">
                                            验证码：
                                            <input type="text" name="captcha"
                                                   required placeholder="输入验证码" autocomplete="off" disableautocomplete
                                                   class="input yz" maxlength="4"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <img style="margin-left: 60px;height: 30px;" id="captcha"
                                                 src="${ctx}/captcha" class="captcha"/></td>
                                    </tr>
                                    <tr height="30">
                                        <td align="center" style="padding-top: 10px;">
                                            <input type="button" class="submit_btn btn btn-sm btn-success" value="登&nbsp;&nbsp;&nbsp;录"
                                                   id="login_btn"/>
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
<script src="${ctx}/assets/js/jquery.js"></script>
<script src="${ctx}/extend/js/jquery.form.js"></script>
<script>
    $(document).keyup(function (event) {
        if (event.keyCode == 13) {
            $(".submit_btn").trigger("click");
        }
    });
    $('img.captcha').click(function () {
        $("input[name=captcha]").val('').focus();
        $(this).attr('src', '/captcha?' + Math.floor(Math.random() * 100));
    })
    $("#login_btn").click(function () {
        var $form = $("#login-form");
        var $username = $("input[name=username]", $form);
        var $passwd = $("input[name=passwd]", $form);
        var $captcha = $("input[name=captcha]", $form);
        if ($.trim($username.val()) == "") {
            $username.focus();
            return;
        }
        if ($passwd.val() == "") {
            $passwd.focus();
            return;
        }

        if ($.trim($captcha.val()) == "") {
            $captcha.focus();
            return;
        }
        //$form.submit();
        $form.ajaxSubmit({
            success: function (data) {
                if (data.success) {
                    location.href = data.url + location.hash;
                } else {
                    if (data.msg == undefined) {  // 解决sslvpn情况下，无法登录
                        location.reload();
                        return;
                    }
                    $(".login-error").html("<i class=\"fa fa-times\"></i> " + data.msg).show();
                    $('img.captcha', $form).click()
                }
            }
        });
    });
</script>
</body>
</html>
