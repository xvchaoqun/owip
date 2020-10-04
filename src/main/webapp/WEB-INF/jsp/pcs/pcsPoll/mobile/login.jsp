<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/pcs/constants.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title>${_p_pcsSiteName}</title>
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
									<div style="font-size:smaller">${_p_pcsSiteName}</div>
								</h1>
							</div>
							<div class="space-10"></div>
							<div class="position-relative">
								<div id="login-box" class="login-box visible widget-box no-border">
									<div class="widget-body">
										<c:if test="${param.isFinished}">
											<div class="widget-main">
											<div style="margin:55px 10px; font-size: 16pt">
												<i class="fa fa-check-circle text-success"></i> 您已完成投票，感谢您对工作的大力支持！
											</div>
											</div>
										</c:if>
										<c:if test="${!param.isFinished}">
										<div class="widget-main">
											<h4 class="header blue lighter bigger">
												<i class="ace-icon fa fa-key green"></i> 请使用投票人账号密码登录
											</h4>
											<div class="login-error" style="color:red; display: ${empty error?'none':''}">
												<i class="fa fa-times"></i> <span class="txt">${error}</span></div>
											<form method="post">
												<fieldset>
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="text" name="username"
																   class="form-control" placeholder="账号" value="${param.u}" />
															<i class="ace-icon fa fa-user"></i>
														</span>
													</label>
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="password" name="passwd" class="form-control"
																   placeholder="密码"  value="${param.p}" />
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
										</c:if>
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

				$.post("${ctx}/ddh?isMobile=1", {"username": $username.val(), "passwd": $passwd.val()}, function(ret){
					if (ret.success) {
						location.href = "${ctx}/user/pcs/index?isMobile=1";
					}else {
						$(".login-error").show();
						$(".login-error .txt").html(ret.msg);
					}
				})
			})
		</script>
	</body>
</html>
