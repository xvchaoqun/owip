<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
	<head>
	<jsp:include page="/WEB-INF/jsp/common/m_head.jsp"></jsp:include>
	</head>
	<style>
		h1[id],h2[id]{
			padding-top:95px;
			margin-top:-95px;
		}
		#survey th,td{
			margin: 0px;
			padding: 0px;
			text-align: center;
			width: 50%;
			height: 50px;
		}
		#survey table{
			background-color: white;
			border: 1px black;
		}
		.changePasswd .td1{
			width: 100%;
			text-align: right;
		}
		.postName{
			font-size: 17px;
		}
	</style>
	<body class="no-skin">
		<!-- #section:basics/navbar.layout -->
		<div id="navbar" class="navbar navbar-default">
			<script type="text/javascript">
				try{ace.settings.check('navbar' , 'fixed')}catch(e){}
			</script>

			<div class="navbar-container" id="navbar-container">
				<div class="navbar-header pull-left">
					<a href="javascript:;" class="navbar-brand">
						<span style="font-size: 16px; font-weight: bold"><i class="ace-icon fa fa-signal"></i>
							线上民主推荐-手机版
						</span>
					</a>
				</div>
				<!-- #section:basics/navbar.dropdown -->
				<div class="navbar-buttons navbar-header pull-right" role="navigation">
					<ul class="nav ace-nav">
						<!-- #section:basics/navbar.user_menu -->
						<li class="light-blue">
							<a data-toggle="dropdown" href="javascript:;" class="dropdown-toggle">
								<img class="nav-user-photo" src="${ctx}/extend/img/default.png" width="90" alt="头像" />
								<i class="ace-icon fa fa-caret-down"></i>
							</a>
							<ul class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
								<li>
									<a href="javascript:void(0)" onclick="drOnline_eva()"><i class="ace-icon fa fa-check"></i>测评</a>
								</li>
								<li>
									<a href="javascript:void(0)" onclick="drOnline_notice()"><i class="ace-icon fa fa-file-powerpoint-o"></i>测评说明</a>
								</li>
								<li>
									<a href="javascript:void(0)" onclick="drOnline_changePasswd()"><i class="ace-icon fa fa fa-key"></i> 修改密码</a>
								</li>
								<li>
									<a href="${ctx}/dr/drOnline/logout?isMobile=1">
										<i class="ace-icon fa fa-power-off"></i>
										安全退出
									</a>
								</li>
							</ul>
						</li>
					</ul>
				</div>
			</div><!-- /.navbar-container -->
		</div>
		<div class="main-container" id="main-container">
			<div class="main-content">
				<div class="main-content-inner">
					<!-- #section:basics/content.breadcrumbs -->
					<div class="breadcrumbs" style="text-align: center">
						干部民主推荐表<a href="#" class="tag"><font size="2">${drOnline.code}</font></a>
					</div>
					<div class="page-content" id="page-content">
						<div class="eva">
							<div>
								<div>
									<form id="evaluateForm"  method="post">
										<c:if test="${!tempResult.agree}">
											<div>
												<div class="modal-header" style="padding-top: 30px!important;" align="center">
													<h2>测评说明</h2>
												</div>
												<div class="modal-body" style="word-wrap:break-word">
													${drOnline.notice}
												</div>
											</div>
											<div class="span12" style="margin-top: 30px;font: 20px Verdana, Arial, Helvetica, sans-serif;">
												<center>
													<input type="checkbox" id="agree" name="agree" style="width: 20px; height: 20px; marginp: 0;"> 我确认已阅读测评说明
												</center>
											</div>
											<div class="span12" style="margin-top: 30px;font:bold 20px Verdana, Arial, Helvetica, sans-serif;">
												<center>
													<button class="btn btn-large btn-success" onclick="_confirm()" type="button">进入测评页面</button></center>
											</div>
										</c:if>
									</form>
									<div style="height: 30px;"></div>
									<form id="survey" method="post">
										<c:if test="${tempResult.agree}">
											<table class="table-bordered" style="width: 90%;margin: 0 auto;" >
												<tbody>
												<c:forEach items="${postViews}" var="postView">
													<c:if test="${postView.competitiveNum == 1}">
														<tr>
															<td class="postName" colspan="2"><strong>${postView.name}</strong></td>
														</tr>
														<tr>
															<c:if test="${!postView.hasCandidate}">
																<td>
																	<input type="text" value="<c:forEach items="${tempResult.otherResultMap}" var="to"><c:if test="${to.key==postView.id}">${to.value}</c:if></c:forEach>" postId="${postView.id}" style="width: 100%;height: 100%;" name="candidateCode" class="form-field-tags"  placeholder="输入后选择候选人或按回车 ..." />
																</td>
																<td>
																	<div>
																		<label>--</label>
																	</div>
																</td>
															</c:if>
															<c:if test="${postView.hasCandidate}">
																<c:forEach items="${candidateMap}" var="candidateMap">
																	<c:if test="${candidateMap.key == postView.id}">
																		<td>${candidateMap.value.user.realname}</td>
																		<td style="text-align: center;">
																			<div>
																				<input type="radio" name="${postView.id}_${candidates.userId}" id="${postView.id}_${candidates.userId}_1" value="1">
																				<label for="${postView.id}_${candidates.userId}_1">同&nbsp;&nbsp;&nbsp;意</label>
																			</div>
																			<div>
																				<input type="radio" name="${postView.id}_${candidates.userId}" id="${postView.id}_${candidates.userId}_0" value="0">
																				<label for="${postView.id}_${candidates.userId}_0">不同意</label>
																			</div>
																		</td>
																	</c:if>
																</c:forEach>
															</c:if>
														</tr>
													</c:if>
													<c:if test="${postView.competitiveNum > 1}">
														<c:if test="${postView.hasCandidate}">
															<tr>
																<td class="postName" colspan="2"><strong>${postView.name}</strong></td>
															</tr>
															<c:forEach items="${candidateMap}" var="candidateMap">
																<c:forEach items="${candidateMap.value}" var="candidates" begin="0" end="0">
																	<c:if test="${candidateMap.key == postView.id}">
																		<tr>
																			<td>${candidates.user.realname}</td>
																			<td style="text-align: center;">
																				<div >
																					<input type="radio" name="${postView.id}_${candidates.userId}" id="${postView.id}_${candidates.userId}_1" value="1">
																					<label for="${postView.id}_${candidates.userId}_1">同意</label>
																				</div>
																				<div>
																					<input type="radio" name="${postView.id}_${candidates.userId}" id="${postView.id}_${candidates.userId}_0" value="0">
																					<label for="${postView.id}_${candidates.userId}_0">不同意</label>
																				</div>
																			</td>
																		</tr>
																	</c:if>
																</c:forEach>
															</c:forEach>
															<c:forEach items="${candidateMap}" var="candidateMap">
																<c:forEach items="${candidateMap.value}" var="candidates" begin="1" end="${postView.existNum}">
																	<c:if test="${candidateMap.key == postView.id}">
																		<tr>
																			<td>${candidates.user.realname}</td>
																			<td style="text-align: center;">
																				<div>
																					<input type="radio" name="${postView.id}_${candidates.userId}" id="${postView.id}_${candidates.userId}_1" value="1">
																					<label for="${postView.id}_${candidates.userId}_1">同意</label>
																				</div>
																				<div>
																					<input type="radio" name="${postView.id}_${candidates.userId}" id="${postView.id}_${candidates.userId}_0" value="0">
																					<label for="${postView.id}_${candidates.userId}_0">不同意</label>
																				</div>
																			</td>
																		</tr>
																	</c:if>
																</c:forEach>
															</c:forEach>
															<c:if test="${postView.competitiveNum > postView.existNum}">
																<tr>
																	<td>
																		<input type="text" value="<c:forEach items="${tempResult.otherResultMap}" var="to"><c:if test="${to.key==postView.id}">${to.value}</c:if></c:forEach>" postId="${postView.id}" style="width: 100%;height: 100%;" name="candidateCode" class="form-field-tags"  placeholder="输入后选择候选人或按回车 ..." />
																	</td>
																	<td>
																		<div>
																			<label>--</label>
																		</div>
																	</td>
																</tr>
															</c:if>
														</c:if>
														<c:if test="${!postView.hasCandidate}">
															<tr>
																<td class="postName" colspan="2"><strong>${postView.name}</strong></td>
															</tr>
															<tr>
																<td>
																	<input type="text" value="<c:forEach items="${tempResult.otherResultMap}" var="to"><c:if test="${to.key==postView.id}">${to.value}</c:if></c:forEach>" postId="${postView.id}" style="width: 100%;height: 100%;" name="candidateCode" class="form-field-tags"  placeholder="输入后选择候选人或按回车 ..." />
																</td>
																<td>
																	<div>
																		<label>--</label>
																	</div>
																</td>
															</tr>
														</c:if>
													</c:if>
												</c:forEach>
												</tbody>
												<tfoot>
												<tr>
													<td colspan="3">
														<button class="btn btn-sm btn-primary"
																style="font-weight: bolder; font-size: medium; color: white" type="button" onclick="doTempSave()">保存</button>
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														<button class="btn btn-sm btn-primary"
																style="font-weight: bolder; font-size: medium; color: white" type="button"
																onclick="doTempSubmit()">提交</button>
													</td>
												</tr>
												</tfoot>
											</table>
										</c:if>
									</form>
								</div>
							</div>
						</div>
						<div class="notice" disabled hidden>
							<div>
								<div >
									<div class="modal-header" style="padding-top: 30px!important;" align="center">
										<h2>干部民主推荐说明</h2>
									</div>
									<div class="modal-body" style="word-wrap:break-word">
										${drOnline.notice}
									</div>
								</div>
							</div>
						</div>
						<div class="changePasswd" disabled hidden>
							<div>
								<div>
									<form class="form-horizontal" id="form" action="${ctx}/dr/drOnline/user/changePasswd?isMobile=1"  method="post">
										<div>
											<div>
												<div>
													<div class="modal-header" align="center" >
														<h2>修改密码</h2>
													</div>
													<div class="form-group"></div><div class="form-group"></div>
													<div style="padding-right: 50px">
														<table>
															<tr>
																<td class="td1"><span class="star">*</span>原密码：</td>
																<td class="td2"><input required type="password" name="oldPasswd"></td>
															</tr>
															<tr>
																<td class="td1"><span class="star">*</span>新密码：</td>
																<td><input required type="password" name="oldPasswd"></td>
															</tr>
															<tr>
																<td class="td1"><span class="star">*</span>新密码确认：</td>
																<td><input required type="password" name="repasswd"></td>
															</tr>
														</table>
													</div>
												</div>
											</div>
										</div>

										<div class="clearfix form-actions" style="background-color: white;">
											<div class="col-md-offset-3 col-md-9" style="padding-left: 50px">
												<button class="btn" type="reset">
													<i class="ace-icon fa fa-undo bigger-110"></i>
													重置
												</button>
												&nbsp; &nbsp;
												<button class="btn btn-info" type="submit">
													<i class="ace-icon fa fa-check bigger-110"></i>
													保存
												</button>
											</div>
										</div>
									</form>
								</div>
							</div>
						</div>
					</div><!-- /.page-content -->
				</div>
			</div><!-- /.main-content -->

			<div class="footer">
				<div class="footer-inner">
					<div class="footer-content">
						<span class="bigger-120">
							${_sysConfig.schoolName}党委组织部
						</span>
					</div>
				</div>
			</div>

			<a href="javascript:;" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
				<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
			</a>
		</div><!-- /.main-container -->
		<div id="modal" class="modal fade">
			<div class="modal-dialog" role="document" <%--style="min-width: 650px;"--%>>
				<div class="modal-content">
				</div>
			</div>
		</div>
	<script src="${ctx}/assets/js/bootstrap-tag.js"></script>
	<script src="${ctx}/assets/js/ace/elements.typeahead.js"></script>
	<script type="text/javascript">
		console.log($("#survey .form-field-tags").parent().attr("class"))


		function drOnline_eva() {
			$('.changePasswd').attr("disabled", "disabled");
			$('.changePasswd').attr("hidden", "hidden");

			$('.notice').attr("disabled", "disabled");
			$('.notice').attr("hidden", "hidden");

			$('.eva').removeAttr("disabled");
			$('.eva').removeAttr("hidden");
		}


		function drOnline_notice() {
			$('.changePasswd').attr("disabled", "disabled");
			$('.changePasswd').attr("hidden", "hidden");

			$('.notice').removeAttr("disabled");
			$('.notice').removeAttr("hidden");

			$('.eva').attr("disabled", "disabled");
			$('.eva').attr("hidden", "hidden");
		}

		function drOnline_changePasswd() {
			$('.changePasswd').removeAttr("disabled");
			$('.changePasswd').removeAttr("hidden");

			$('.notice').attr("disabled", "disabled");
			$('.notice').attr("hidden", "hidden");

			$('.eva').attr("disabled", "disabled");
			$('.eva').attr("hidden", "hidden");
		}

		function _confirm() {
			if ($('#agree').is(':checked') == false){
				$('#agree').qtip({content: '请您确认您已阅读测评说明！', show: true});
				return false;
			}
			$("#evaluateForm").ajaxSubmit({
				url: "${ctx}/dr/drOnline/agree?isMobile=1",
				success: function (data) {
					if (data.success) {
						//console.log(data)
						location.reload();
					}
				}
			});
		}

		var tag_input = $('.form-field-tags');
		try{
			tag_input.tag(
					{
						placeholder:tag_input.attr('placeholder'),
						source: ${sysUser}
					}
			)
		} catch(e) {
			tag_input.after('<textarea id="'+tag_input.attr('id')+'" style="border: 0px white;margin: 0px;width: 100%;height: 95%;" name="'+tag_input.attr('name')+'" rows="3">'+tag_input.val()+'</textarea>').remove();
		}

		//保存临时数据
		var isSubmit = 0;
		function doTempSave(){
			var onlineId = ${drOnline.id};
			var datas = new Array();
			$("table input:checked").each(function () {
				var radioName = $(this).attr("name");
				datas.push($(this).attr("id"));
				//console.log(datas);
			})

			var count = 0;
			var others = new Array();
			$("input[name=candidateCode]").each(function(){
				var postId = $(this).attr("postId");
				var user;
				var userIds = ($(this).val()).split(",");
				if ($.trim(userIds).length == 0)
					user = userIds;
				else {
					user = postId + "-" + userIds;
					count++;
				}
				//console.log(user)
				others.push(user)
			})
			/*console.log(datas)
			console.log(others)*/
			if(isSubmit == 1){
				if ($("#survey tr").length - 3 > datas.length + count.length) {
					SysMsg.info('请完成推荐表后，再进行提交。', '提示')
					return;
				}else {
					$.post("${ctx}/dr/drOnline/doTempSave?&isMobile=1&isSubmit=" + isSubmit,{"datas[]": datas, "others[]": others, "onlineId": onlineId},function(ret) {
						if (ret.success) {
							//location.reload();
							SysMsg.success('提交成功。退出后账号将不能登录。', '成功', function(){
								location.href ="${ctx}/dr/drOnline/logout?isMobile=1"
							});
						}
					})
				}
			}else{
				$.post("${ctx}/dr/drOnline/doTempSave?&isMobile=1&isSubmit=" + isSubmit,{"datas[]": datas, "others[]": others, "onlineId": onlineId},function(ret) {
					if (ret.success) {
						SysMsg.success('保存成功。', '成功')
						//location.reload();
					}
				})
			}
		}

		//提交推荐数据
		function doTempSubmit(){
			isSubmit = 1;
			doTempSave();
			isSubmit = 0;
		}

		//添加候选人，动态改变name和id属性
		$(function () {
			$("#survey select").on("change", function () {
				//alert($(this).val().length)
				//console.log($(this).val().length)
				var arr = $(this).parent().parent().next().children().children();
				//console.log($(this).parent().parent().next().children().children())
				var postViewId = ((arr[0]).name.split("_"))[0];
				if ($(this).val().length != 0) {
					$(arr[0]).attr("name", postViewId + "_" + this.value).attr("id", postViewId + "_" + this.value + "_1");
					$(arr[1]).attr("for", postViewId + "_" + this.value + "_1");
					$(arr[2]).attr("name", postViewId + "_" + this.value).attr("id", postViewId + "_" + this.value + "_0");
					$(arr[3]).attr("for", postViewId + "_" + this.value + "_0");
				}else  {
					$(arr[0]).removeAttr("id");
					$(arr[2]).removeAttr("id");
				}
			})
		})

		//接收临时数据(管理员设置的候选人)，并在页面显示
		var tempResult=${cm:toJSONObject(tempResult)};
		//console.log(tempResult)
		if (tempResult.tempInspectorResultMap != undefined){
			$.each(tempResult.tempInspectorResultMap, function (onlineId, val) {
				$.each(val.optionIdMap, function (key, value) {
					var radioName, radioValue, userId, postId;
					radioName = key;
					radioValue = value;
					var keys = key.split("_");
					if (keys.length == 2) {
						postId = keys[0];
						userId = keys[1];
					}else {
						return true; //数据有误
					}
					$("[name=" + radioName + "][value=" + radioValue + "]").click();
					//console.log($("[name=" + radioName + "][value=" + radioValue + "]"))
				})
			})
		}

		$("#form button[type=submit]").click(function(){$("#form").submit();return false;});
		$("#form").validate({
			rules: {
				repasswd:{
					equalTo:'#passwd'
				}
			},
			submitHandler: function (form) {

				$(form).ajaxSubmit({
					success:function(data){
						//console.log(data)
						if(data.success){
							SysMsg.success('修改密码成功，请重新登录。', '成功', function(){
								location.href ="${ctx}/dr/drOnline/logout?isMobile=1"
							});
						}
					}
				});
			}
		});
	</script>

	</body>
</html>
