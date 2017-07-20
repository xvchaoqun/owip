function _resize(){
    var ww = $(window).width();
    var hh = $(window).height()-109;
    $(".login_box").css("width", ww);
    $(".login_box").css("height", hh);
    if(ww/hh>1920/890){
        $(".bg img").css("width", "100%");
        $(".bg img").css("height", "auto");
        $(".bg img").css("margin-left", 0);
    }
    if(ww/hh<1920/890){
        $(".bg img").css("width", "auto");
        $(".bg img").css("height", "100%");
        $(".bg img").css("margin-top", 0);
        $(".bg img").css("margin-left", -((1920/890*hh)-ww)/2);
    }
}


$(function(){
    window.onresize=_resize;
    _resize();
   /* var ch = $(window).height();
    $(".login_box").css("height", ch);*/

    if (!navigator.cookieEnabled) {
        alert("由于您的浏览器禁用了Cookie，此网站无法正常访问，请尝试开启Cookie后访问。");
        $(".login-error").append("请尝试开启Cookie后访问。").show()
    }else {
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
                    //alert(data)
                    try {
                        data = JSON.parse(data)
                    } catch (e) {
                        location.reload();
                    }
                    //console.log(ret)
                    if (data.success) {
                        location.href = data.url + location.hash;
                    } else {
                        $(".login-error").html("<i class=\"fa fa-times\"></i>" + data.msg).show();
                        $('img.captcha', $form).click()
                    }
                }
            });
        });

        $("#reg_btn").click(function () {

            var $form = $("#reg-form");

            var $username = $("input[name=username]", $form);
            var $passwd = $("input[name=passwd]", $form);
            var $repasswd = $("input[name=repasswd]", $form);
            var $type = $("select[name=type]", $form);
            var $realname = $("input[name=realname]", $form);
            var $idcard = $("input[name=idcard]", $form);
            var $phone = $("input[name=phone]", $form);
            var $party = $("select[name=party]", $form);
            var $captcha = $("input[name=captcha]", $form);
            if ($.trim($username.val()) == "") {
                $username.val('').focus();
                return;
            }
            if ($.trim($passwd.val()) == "") {
                $passwd.val('').focus();
                return;
            }
            if ($.trim($repasswd.val()) == "") {
                $repasswd.val('').focus();
                return;
            }
            if ($.trim($repasswd.val()) != $.trim($passwd.val())) {
                $repasswd.focus();
                alert("两次输入的密码不相同，请重新输入。");
                return;
            }
            if ($.trim($type.val()) == "") {
                alert("请选择类别。");
                return;
            }
            if ($.trim($realname.val()) == "") {
                $realname.val('').focus();
                return;
            }
            if ($.trim($idcard.val()) == "") {
                $idcard.val('').focus();
                return;
            }
            if ($.trim($phone.val()) == "") {
                $phone.val('').focus();
                return;
            }
            if ($.trim($party.val()) == "") {
                alert("请选择分党委。");
                return;
            }

            if ($.trim($captcha.val()) == "") {
                $captcha.val('').focus();
                return;
            }
            $form.ajaxSubmit({
                success: function (data) {

                    if (data.success) {
                        alert("注册成功，请耐心等待分党委审核。")
                        location.reload();
                    } else {
                        alert(data.msg)
                        $("input[name=captcha]").val('');
                        $('img.captcha').attr('src', '/captcha?' + Math.floor(Math.random() * 100));
                    }
                }
            });
        });

        $(document).keyup(function (event) {
            if (event.keyCode == 13) {
                $(".login-layout.visible .submit_btn").trigger("click");
            }
        });

        $(document).on('click', 'a[data-target]', function (e) {
            e.preventDefault();
            var target = $(this).data('target');
            $('#reg, #login').removeClass('visible');//hide others
            $(target).addClass('visible');//show target
            target == '#login' ? $("#note").show() : $("#note").hide();

            $("input[name=captcha]").val('');
            $('img.captcha').attr('src', '/captcha?' + Math.floor(Math.random() * 100));
        });
    }
})