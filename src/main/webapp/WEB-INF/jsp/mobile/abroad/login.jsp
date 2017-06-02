<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<shiro:user>
	<c:redirect url="/m/index"/>
</shiro:user>
<!DOCTYPE html>
<html lang="en">
	<head>
	<jsp:include page="/WEB-INF/jsp/mobile/head.jsp"></jsp:include>
	</head>

	<body class="login-layout blue-login">
		<div class="main-container">
			<div class="main-content">
				<div class="row">
					<div class="col-sm-10 col-sm-offset-1">
						<div class="login-container">
							<div class="center">
								<div style="padding-top: 20px">
									<img src="${ctx}/extend/img/logo_white.png">
								</div>
								<h1 class="white">
									组织工作一体化平台
									<div style="font-size:smaller">（因私出国境审批）</div>
								</h1>
							</div>

							<div class="space-6"></div>
							<div class="position-relative">
								<div id="login-box" class="login-box visible widget-box no-border">
									<div class="widget-body">
										<div class="widget-main">
											<h4 class="header blue lighter bigger">
												<i class="ace-icon fa fa-key green"></i>
												请使用信息门户账号密码登录
											</h4>

											<div class="space-6"></div>

											<form>
												<fieldset>
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="text" name="username" class="form-control" placeholder="账号" />
															<i class="ace-icon fa fa-user"></i>
														</span>
													</label>

													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="password" name="passwd" class="form-control" placeholder="密码" />
															<i class="ace-icon fa fa-lock"></i>
														</span>
													</label>

													<div class="space"></div>

													<div class="center">
														<button type="button" class="width-35 btn btn-sm btn-primary">
															<span class="bigger-130" id="login_btn">登录</span>
														</button>
													</div>

													<div class="space-4"></div>
												</fieldset>
											</form>
										</div><!-- /.widget-main -->
									</div><!-- /.widget-body -->
								</div><!-- /.login-box -->
							</div><!-- /.position-relative -->
							<div class="center" style="padding-top: 50px">
								<h4 class="white" id="id-company-text"><fmt:message key="site.school" bundle="${spring}"/>党委组织部<span class="blue bolder">&copy;2016</span></h4>
							</div>
						</div>
					</div><!-- /.col -->
				</div><!-- /.row -->
			</div><!-- /.main-content -->
		</div><!-- /.main-container -->

		<!-- basic scripts -->

		<!-- inline scripts related to this page -->
		<script type="text/javascript">
			$("#login_btn").click(function(){
				var $username = $("input[name=username]");
				var $passwd = $("input[name=passwd]");
				if($.trim($username.val())==""){
					$username.focus();
					return;
				}
				if($passwd.val()==""){
					$passwd.focus();
					return;
				}
				$.post("${ctx}/m/login",{username:$.trim($username.val()), password:$passwd.val()},function(data){

					if(data.success){
						location.href = data.url;
					}
				});
			})
		</script>
	</body>
</html>
