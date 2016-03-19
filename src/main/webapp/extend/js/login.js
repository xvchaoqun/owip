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

    $('img.captcha').click(function () {
        $("input[name=captcha]").val('').focus();
        $(this).attr('src', '/captcha.jpg?' + Math.floor(Math.random()*100) );
    })
    $(".login_btn").click(function(){

        var $username = $("input[name=username]");
        var $passwd = $("input[name=passwd]");
        var $captcha = $("input[name=captcha]");
        if($.trim($username.val())==""){
            $username.focus();
            return;
        }
        if($passwd.val()==""){
            $passwd.focus();
            return;
        }
        /*$passwd.val(hex_md5($passwd.val()));*/

        if($.trim($captcha.val())==""){
             $captcha.focus();
             return;
         }
        $(".form").ajaxSubmit({
            success:function(data){
                //alert(data)
                try {
                    data = JSON.parse(data)
                }catch(e){
                    location.reload();
                }
                //console.log(ret)
                if(data.success){
                    location.href = data.url;
                }else{
                    alert(data.msg)
                    $('img.captcha').click()
                }
            }
        });
    });
    $(document).keyup(function(event){
        if(event.keyCode ==13){
            $(".login_btn").trigger("click");
        }
    });
})