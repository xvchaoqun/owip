<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="MEMBER_OUT_STATUS_BACK" value="<%=MemberConstants.MEMBER_OUT_STATUS_BACK%>"/>
<div class="row" style="width: 950px">
<c:if test="${memberOut.status==MEMBER_OUT_STATUS_BACK}">
<div class="alert alert-danger">
	<button type="button" class="close" data-dismiss="alert">
		<i class="ace-icon fa fa-times"></i>
	</button>
	<strong><i class="ace-icon fa fa-times"></i>返回修改</strong><c:if test="${not empty memberOut.reason}">: ${memberOut.reason}</c:if>
	<br>
</div>
</c:if>
<div class="widget-box">
	<div class="widget-header">
		<h4 class="widget-title"><i class="fa fa-paw blue"></i> 组织关系转出申请</h4>

		<%--<div class="widget-toolbar">
			<a href="javascript:;" data-action="collapse">
				<i class="ace-icon fa fa-chevron-up"></i>
			</a>
		</div>--%>
	</div>
	<div class="widget-body">
		<div class="widget-main">
			<form class="form-horizontal" action="${ctx}/user/memberOut_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
				<input type="hidden" name="id" value="${memberOut.id}">
				<div class="row">

					<div class="col-xs-6">
						<div class="form-group">
							<label class="col-xs-5 control-label"><span class="star">*</span>党员本人联系电话</label>
							<div class="col-xs-7">
								<input required class="form-control" maxlength="20" type="text" name="phone" value="${memberOut.phone}">
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-5 control-label"><span class="star">*</span>类别</label>
							<div class="col-xs-7">
								<select required data-rel="select2" name="type" data-placeholder="请选择"  data-width="100%">
									<option></option>
									<c:import url="/metaTypes?__code=mc_member_in_out_type"/>
								</select>
								<script>
									$("#modalForm select[name=type]").val(${memberOut.type});
								</script>
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-5 control-label"><span class="star">*</span>转入单位</label>
							<div class="col-xs-7">
								<textarea required class="form-control limited" rows="4" type="text" name="toUnit">${memberOut.toUnit}</textarea>
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-5 control-label"><span class="star">*</span>转入单位抬头</label>
							<div class="col-xs-7">
								<textarea required class="form-control limited"
										  rows="4" type="text" name="toTitle">${memberOut.toTitle}</textarea>
								<span class="green">注：如果类别是京外，则抬头必须是区县级以上组织部门</span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-5 control-label"><span class="star">*</span>转出单位</label>
							<div class="col-xs-7">
								<c:set var="defaultAddress" value="${userBean.party.name}"/>
								<textarea required class="form-control limited" rows="4"
									   type="text" name="fromUnit">${empty memberOut?defaultAddress:memberOut.fromUnit}</textarea>
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-5 control-label"><span class="star">*</span>转出单位地址</label>
							<div class="col-xs-7">
								<textarea required class="form-control limited"  rows="4" name="fromAddress">${empty memberOut?defaultAddress:memberOut.fromAddress}</textarea>
							</div>
						</div>

					</div>
					<div class="col-xs-5">
						<div class="form-group">
							<label class="col-xs-8 control-label"><span class="star">*</span>转出单位联系电话（${_sysConfig.schoolShortName}）</label>
							<div class="col-xs-4">
								<input required class="form-control" maxlength="20" type="text" name="fromPhone" value="${memberOut.fromPhone}">
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-8 control-label"><span class="star">*</span>转出单位传真（${_sysConfig.schoolShortName}）</label>
							<div class="col-xs-4">
								<input required class="form-control" maxlength="20" type="text" name="fromFax" value="${memberOut.fromFax}">
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-8 control-label"><span class="star">*</span>转出单位邮编（${_sysConfig.schoolShortName}）</label>
							<div class="col-xs-4">
								<input required class="form-control" type="text" name="fromPostCode"
									   value="${empty memberOut.fromPostCode?"100875":memberOut.fromPostCode}">
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-8 control-label"><span class="star">*</span>党费缴纳至年月</label>
							<div class="col-xs-4">
								<div class="input-group">
									<input required class="form-control date-picker" name="_payTime" type="text"
										   data-date-format="yyyy-mm"
										   data-date-min-view-mode="1"
										   value="${cm:formatDate(memberOut.payTime,'yyyy-MM')}" />
									<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
								</div>
							</div>
						</div>

						<div class="form-group">
							<label class="col-xs-8 control-label"><span class="star">*</span>介绍信有效期天数</label>
							<div class="col-xs-4">
								<input required class="form-control digits" type="text" name="validDays" value="${memberOut.validDays}">
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-8 control-label"><span class="star">*</span>办理时间</label>
							<div class="col-xs-4">
								<div class="input-group">
									<input required class="form-control date-picker" name="_handleTime" type="text"
										   data-date-format="yyyy-mm-dd"
										   value="${cm:formatDate(memberOut.handleTime,'yyyy-MM-dd')}" />
									<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
								</div>
							</div>
						</div>
					</div></div>

				<div class="clearfix form-actions center">
					<button id="submitBtn" class="btn btn-info" type="button" data-loading-text="提交中..." autocomplete="off">
						<i class="ace-icon fa fa-check bigger-110"></i>
						提交
					</button>
				</div>
			</form>
		</div>
	</div>
</div>
</div>
<script>
	$('textarea.limited').inputlimiter({limit:50});
	$.register.date($('.date-picker'));
	$("#submitBtn").click(function(){
		var $btn = $(this).button('loading');
		$("#modalForm").submit();
		setTimeout(function () { $btn.button('reset'); },1000);
		return false;});
	$("#modalForm").validate({
		submitHandler: function (form) {
			$(form).ajaxSubmit({
				success:function(ret){
					$("#submitBtn").button("reset");
					if(ret.success){
						bootbox.alert('提交成功。',function(){
							$.hashchange();
						});
					}
				}
			});
		}
	});
	$('#modalForm [data-rel="select2"]').select2();
	$('[data-rel="tooltip"]').tooltip();
</script>
