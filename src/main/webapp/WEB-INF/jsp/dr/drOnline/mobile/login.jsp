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
						<div class="login-container" style="margin-top: 50px">
							<div class="center">
								<div style="padding-top: 20px">
									<t:img src="/img/logo_white.png"/>
								</div>
								<h1 class="white">
									<div style="font-size:smaller">线上民主推荐投票</div>
								</h1>
							</div>
							<div class="space-10"></div>
							<div class="position-relative">
								<div id="login-box" class="login-box visible widget-box no-border">
									<div class="widget-body">
										<div class="widget-main">
											<h4 class="header blue lighter bigger">
												<i class="ace-icon fa fa-key green"></i> 请使用参评人账号密码登录
											</h4>
											<div class="space-6"></div>
											<div class="login-error" style="display: none">
												<i class="fa fa-times"></i>${error}</div>
											<form method="post">
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
							<%--<div class="center" style="padding-top: 50px">
								<h4 class="white" id="id-company-text">${_sysConfig.schoolName}党委组织部</h4>
							</div>--%>
						</div>
					</div><!-- /.col -->
				</div><!-- /.row -->
			</div><!-- /.main-content -->
		</div><!-- /.main-container -->
		<script src="${ctx}/assets/js/jquery.js"></script>
		<script type="text/javascript">
			<c:if test="${not empty error}">
			alert('${error}');
			</c:if>
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
				$.post("${ctx}/dr/drOnline/login?isMobile=1", {"username": $username.val(), "passwd": $passwd.val()}, function(ret){
				    console.log(ret)
					if (ret.success) {
						console.log(ret)
						location.href = "${ctx}/dr/drOnline/drOnlineIndex?isMobile=1";
					}else {
						SysMsg.success(ret.msg, '登陆失败');
					}
				})
			})
		</script>
	</body>
</html>
