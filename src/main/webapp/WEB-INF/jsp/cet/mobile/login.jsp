<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
	<head>
	<jsp:include page="/WEB-INF/jsp/common/m_head.jsp"></jsp:include>
	</head>
	<body class="login-layout blue-login">
		<div class="main-container">
			<div class="main-content">
				<div class="row">
					<div class="col-sm-10 col-sm-offset-1">
						<div class="login-container">
							<div class="center">
								<div style="padding-top: 20px">
									<t:img src="/img/logo_white.png"/>
								</div>
								<h1 class="white">
									${_sysConfig.mobilePlantformName}
									<div style="font-size:smaller">（培训课程）</div>
								</h1>
							</div>

							<div class="space-6"></div>
							<div class="position-relative">
								<div id="login-box" class="login-box visible widget-box no-border">
									<div class="widget-body">
										<div class="widget-main">
											<h4 class="header blue lighter bigger">
												<i class="ace-icon fa fa-key green"></i>
												${empty train?'请使用评课账号密码登录':(train.isOnCampus?'请输入工作证号登录':'请输入手机号登录')}
											</h4>

											<div class="space-6"></div>

											<form>
												<fieldset>
													<c:if test="${empty train}">
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
													</c:if>
													<c:if test="${not empty train}">
														<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															${train.name}
														</span>
														</label>

														<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="number" name="mobile" class="form-control" placeholder="${train.isOnCampus?'请输入工作证号':'请输入手机号'}" />
															<i class="ace-icon fa fa-lock"></i>
														</span>
														</label>
													</c:if>
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
								<h4 class="white" id="id-company-text">${_sysConfig.schoolName}党委组织部</h4>
							</div>
						</div>
					</div><!-- /.col -->
				</div><!-- /.row -->
			</div><!-- /.main-content -->
		</div><!-- /.main-container -->

		<!-- basic scripts -->

		<!-- inline scripts related to this page -->
		<script type="text/javascript">
			<c:if test="${not empty error}">
			alert('${error}');
			</c:if>
			$("#login_btn").click(function(){

				<c:if test="${empty train}">
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
				$.post("${ctx}/m/cet/login",{username:$.trim($username.val()), password:$passwd.val()},function(data){

					if(data.success){
						location.href = "${ctx}/m/cet/index";
					}
				});
				</c:if>
				<c:if test="${not empty train}">
				var $mobile = $("input[name=mobile]");
				if($.trim($mobile.val())==""){
					$mobile.focus();
					return;
				}
				$.post("${ctx}/m/cet/login",{mobile:$.trim($mobile.val()), trainId:'${train.id}'},function(data){

					if(data.success){
						location.href = "${ctx}/m/cet/index";
					}
				});
				</c:if>
			})
		</script>
	</body>
</html>
