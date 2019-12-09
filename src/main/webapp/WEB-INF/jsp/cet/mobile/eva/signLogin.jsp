<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
	<head>
	<jsp:include page="/WEB-INF/jsp/common/m_head.jsp"></jsp:include>
	</head>
	<body class="login-layout blue-login" style="background-color: white">
		<div class="main-container">
			<div class="main-content">
				<div class="row">
					<div class="col-sm-10 col-sm-offset-1">
						<div class="login-container">
							<div class="center">
								<h1 class="blue">
									${cetTrain.name}
									<div style="font-size:smaller">培训课程签到</div>
								</h1>
							</div>

							<div class="space-6"></div>
							<div class="position-relative">
								<div id="login-box" class="login-box visible widget-box no-border">
									<div class="widget-body">
										<div class="widget-main">
											<h4 class="header blue lighter bigger">
												<i class="ace-icon fa fa-key green"></i>
												请输入工号登录（首次登录需输入微信号）
											</h4>
											<div class="space-6"></div>
											<form>
												<fieldset>
														<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input required type="text" name="code" class="form-control" placeholder="请输入学工号" />
															<i class="ace-icon fa fa-user"></i>
														</span>
														</label>
														<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="text" name="wxName" class="form-control" placeholder="请输入微信号" />
															<i class="ace-icon fa fa-user"></i>
														</span>
														</label>
														<div class="space"></div>
														<div class="center">
															<button type="button" class="width-35 btn btn-sm btn-primary">
																<span class="bigger-130" id="login_btn">确认</span>
															</button>
														</div>
													<div class="container" hidden align="center">
														<img id="qrcode" src="" style="width: 200px; height:200px;margin: 20px"/>
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
			$(".container").hide();
			$("#login_btn").click(function(){
				var $code = $("input[name=code]");
				var $wxName = $("input[name=wxName]");
				var $trainCourse = ${cetTrainCourse.id};
				var sign = '*';
				if($.trim($code.val())==""){
					$code.focus();
					return;
				}
				$.post("${ctx}/m/cet_eva/login",{code:$.trim($code.val()), wxName:$.trim($wxName.val()), trainCourse:$trainCourse},function(data){

					if(data.success){
						$(".container").show();
						var str = $code.val() + sign;
						console.log(str);
						$("#qrcode").attr("src", "${ctx}/m/qrcode?content=" + str);
					}
				});
			})
		</script>
	</body>
</html>
