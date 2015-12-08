<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<shiro:user>
    <c:redirect url="/"/>
</shiro:user>
<html>
<head>
<jsp:include page="/WEB-INF/jsp/common/head.jsp"></jsp:include>
<jsp:include page="/WEB-INF/jsp/common/scripts.jsp"></jsp:include>

</head>
<body class="login-body">
<br class="xs-100">
<div class="jumbotron">
<div class="container">
<div class="account-wrapper">

    <div class="account-body">

        <h2>组织工作管理与服务一体化平台</h2>

        <form class="form account-form" method="POST" action="${ctx}/login">

            <div class="form-group">
                <input class="form-control" required name="username" type="text"  placeholder="输入账号" tabindex="1">
            </div> <!-- /.form-group -->

            <div class="form-group">
                <input required name="passwd" type="password" class="form-control" placeholder="输入密码" tabindex="2">
            </div> <!-- /.form-group -->
            <div class="form-inline">
                <input name="captcha" type="text" maxlength="4" class="col-xs-12 form-control" placeholder="输入验证码" tabindex="3">
                <img src="${ctx}/captcha.jpg" class="captcha"/>
            </div> <!-- /.form-group -->

            <div class="form-group">
                <button type="button" class="submitBtn btn btn-primary btn-block btn-lg btn-shadow" tabindex="4">
                    登录
                </button>
            </div> <!-- /.form-group -->

        </form>


    </div> <!-- /.account-body -->


</div>
</div>
</div>
</body>
<script>
  $(function(){
      $('img.captcha').click(function () {
          $("input[name=captcha]").val('').focus();
          $(this).attr('src', '/captcha.jpg?' + Math.floor(Math.random()*100) );
      })
	  $(".submitBtn").click(function(){$("form").submit();return false;});
	  $("form").validate({
			submitHandler: function (form) {
				$(form).ajaxSubmit({
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
                            //toastr.error(data.msg, '提示');
                            //alert(data.message)
                            $('#captcha').click()
                        }
					}
				});
			},showErrors:function(){}
		});
  })
  </script>
</html>
