<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set value="${empty _pMap['cas_url']?'/cas':_pMap['cas_url']}" var="_p_casUrl"/>
<c:set value="${_pMap['default_login_btns']=='true'}" var="_p_defaultLoginBtns"/>
<c:set value="${_pMap['mobile_login_useCas']=='true'}" var="_p_mobileLoginUseCas"/>
<!DOCTYPE html>
<html lang="en">
	<head>
		<jsp:include page="/WEB-INF/jsp/common/m_head.jsp"></jsp:include>
		<t:link href="/mobile/css/setup.css"/>
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
								</h1>
							</div>

							<div class="space-6"></div>
							<div class="position-relative">
								<div id="login-box" class="login-box visible widget-box no-border">
									<div class="widget-body">
										<div class="widget-main">
											<div class="row">
													<div class="login-btns" ${_p_defaultLoginBtns?'':'hidden'} >
														<div class="cas" onclick="location.href='${_p_casUrl}'"><i class="ace-icon fa fa-user"></i> 统一身份认证登录</div>
														<div class="form"><i class="ace-icon fa fa-key"></i> 其他用户登录</div>
													</div>
											</div>

											<form id="login-form" ${_p_defaultLoginBtns?'hidden':''}>
												<h4 class="header blue lighter bigger" style="white-space: nowrap">
													<i class="ace-icon fa fa-key green"></i>
													${!_p_mobileLoginUseCas?'请使用校园门户账号密码登录':'请使用系统账号密码登录'}
												</h4>
												<div class="space-6"></div>
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

													<input name="rememberMe" type="checkbox" value="true"><span class="txt">下次自动登录</span>
													<div class="space"></div>

													<div class="center">
														<button type="button" class="width-35 btn btn-sm btn-primary">
															<span class="bigger-130" id="login_btn">登录</span>
														</button>
													</div>
													<c:if test="${_p_mobileLoginUseCas}">
													<div style="position: absolute;right: 20px;top: 188px;">
														<a href="javascript:;" class="cas to_reg_btn">统一身份认证</a>
													</div>
													</c:if>
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
		<script type="text/javascript">

			$('.login-btns .cas, #login-form .cas').click(function(){
				$('#login-form').hide();
				$('.login-btns').show();
			});
			$('.login-btns .form').click(function(){
					$('.login-btns').hide();
					$('#login-form').show();
			});

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
				$.post("${ctx}/m/login",{username:$.trim($username.val()),
					password:$passwd.val(),
					rememberMe: $("input[name=rememberMe]").val()},function(data){

					if(data.success){
						location.href = data.url;
					}
				});
			})
		</script>
	</body>
</html>
