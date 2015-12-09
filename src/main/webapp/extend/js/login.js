$(function(){
    var ch = $(window).height();
    $(".login_box").css("height", ch);

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
        /*if($.trim($captcha.val())==""){
         $captcha.focus();
         return;
         }*/
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
                    $captcha.click()
                }
            }
        });
    });

})