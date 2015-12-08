<%@ page import="sys.constants.SystemConstants" %>
<%--
  Created by IntelliJ IDEA.
  User: fafa
  Date: 2015/12/7
  Time: 13:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="USER_TYPE_JZG" value="<%=SystemConstants.USER_TYPE_JZG%>"/>
<c:set var="OR_STATUS_MAP" value="<%=SystemConstants.OR_STATUS_MAP%>"/>
<c:set var="GENDER_MAP" value="<%=SystemConstants.GENDER_MAP%>"/>
<c:set var="MEMBER_POLITICAL_STATUS_MAP" value="<%=SystemConstants.MEMBER_POLITICAL_STATUS_MAP%>"/>
<c:set var="MEMBER_INOUT_TYPE_MAP" value="<%=SystemConstants.MEMBER_INOUT_TYPE_MAP%>"/>
<c:set var="MEMBER_OUT_STATUS_BACK" value="<%=SystemConstants.MEMBER_OUT_STATUS_BACK%>"/>
<c:if test="${memberOut.status==MEMBER_OUT_STATUS_BACK}">
<div class="alert alert-danger">
	<button type="button" class="close" data-dismiss="alert">
		<i class="ace-icon fa fa-times"></i>
	</button>
	<strong>
		<i class="ace-icon fa fa-times"></i>
		返回修改
	</strong>
	<c:if test="${not empty memberOut.reason}">
		:${memberOut.reason}
	</c:if>

	<br>
</div>
</c:if>
<div class="widget-box">
	<div class="widget-header">
		<h4 class="widget-title"><i class="fa fa-paw blue"></i> 党员流出申请</h4>

		<div class="widget-toolbar">
			<a href="#" data-action="collapse">
				<i class="ace-icon fa fa-chevron-up"></i>
			</a>
		</div>
	</div>
	<div class="widget-body">
		<div class="widget-main">
			<form class="form-horizontal" action="${ctx}/user/memberOut_au" id="modalForm" method="post">
				<input type="hidden" name="id" value="${memberOut.id}">
				<div class="row">
					<div class="col-xs-4">
						<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right"> ${(sysUser.type==USER_TYPE_JZG)?"教工号":"学号"}</label>
						<div class="col-sm-6">
							<input readonly disabled type="text" value="${sysUser.code}" />
						</div>
					</div>
						<div class="form-group">
							<label class="col-xs-3 control-label">介绍信编号</label>
							<div class="col-xs-6">
								<input required class="form-control" type="text" name="code" value="${memberOut.code}">
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-3 control-label">姓名</label>
							<div class="col-xs-6">
								<input required class="form-control" type="text" name="realname" value="${memberOut.realname}">
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-3 control-label">性别</label>
							<div class="col-xs-6">
								<select required data-rel="select2" name="gender" data-placeholder="请选择">
									<option></option>
									<c:forEach items="${GENDER_MAP}" var="_gender">
										<option value="${_gender.key}">${_gender.value}</option>
									</c:forEach>
								</select>
								<script>
									$("#modalForm select[name=gender]").val(${memberOut.gender});
								</script>
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-3 control-label">年龄</label>
							<div class="col-xs-6">
								<input required class="form-control digits"
									   data-rule-max="100" data-rule-min="18"
									   type="text" name="age" value="${memberOut.age}">
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-3 control-label">民族</label>
							<div class="col-xs-6">
								<input required class="form-control" type="text" name="nation" value="${memberOut.nation}">
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-3 control-label">政治面貌</label>
							<div class="col-xs-6">
								<select required data-rel="select2" name="politicalStatus" data-placeholder="请选择" >
									<option></option>
									<c:forEach items="${MEMBER_POLITICAL_STATUS_MAP}" var="_status">
										<option value="${_status.key}">${_status.value}</option>
									</c:forEach>
								</select>
								<script>
									$("#modalForm select[name=politicalStatus]").val(${memberOut.politicalStatus});
								</script>
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-3 control-label">身份证号</label>
							<div class="col-xs-6">
								<input required class="form-control" type="text" name="idcard" value="${memberOut.idcard}">
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-3 control-label">类别</label>
							<div class="col-xs-6">
								<select required data-rel="select2" name="type" data-placeholder="请选择"  >
									<option></option>
									<c:forEach items="${MEMBER_INOUT_TYPE_MAP}" var="_type">
										<option value="${_type.key}">${_type.value}</option>
									</c:forEach>
								</select>
								<script>
									$("#modalForm select[name=type]").val(${memberOut.type});
								</script>
							</div>
						</div>
					</div>
					<div class="col-xs-4">

						<div class="form-group">
							<label class="col-xs-4 control-label">转入单位抬头</label>
							<div class="col-xs-6">
								<input required class="form-control" type="text" name="toTitle" value="${memberOut.toTitle}">
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-4 control-label">转入单位</label>
							<div class="col-xs-6">
								<input required class="form-control" type="text" name="toUnit" value="${memberOut.toUnit}">
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-4 control-label">转出单位</label>
							<div class="col-xs-6">
								<input required class="form-control" type="text" name="fromUnit" value="${memberOut.fromUnit}">
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-4 control-label">转出单位地址</label>
							<div class="col-xs-6">
								<input required class="form-control" type="text" name="fromAddress" value="${memberOut.fromAddress}">
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-4 control-label">转出单位联系电话</label>
							<div class="col-xs-6">
								<input required class="form-control" type="text" name="fromPhone" value="${memberOut.fromPhone}">
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-4 control-label">转出单位传真</label>
							<div class="col-xs-6">
								<input required class="form-control" type="text" name="fromFax" value="${memberOut.fromFax}">
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-4 control-label">转出单位邮编</label>
							<div class="col-xs-6">
								<input required class="form-control" type="text" name="fromPostCode"
									   value="${empty memberOut.fromPostCode?"100875":memberOut.fromPostCode}">
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-4 control-label">党费缴纳至年月</label>
							<div class="col-xs-6">
								<div class="input-group">
									<input required class="form-control date-picker" name="_payTime" type="text"
										   data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberOut.payTime,'yyyy-MM-dd')}" />
									<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
								</div>
							</div>
						</div>
					</div>
					<div class="col-xs-4">
						<div class="form-group">
							<label class="col-xs-4 control-label">介绍信有效期天数</label>
							<div class="col-xs-6">
								<input required class="form-control digits" type="text" name="validDays" value="${memberOut.validDays}">
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-4 control-label">办理时间</label>
							<div class="col-xs-6">
								<div class="input-group">
									<input required class="form-control date-picker" name="_handleTime" type="text"
										   data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberOut.handleTime,'yyyy-MM-dd')}" />
									<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
								</div>
							</div>
						</div>
					</div></div>

				<div class="clearfix form-actions center">
					<button class="btn btn-info" type="submit">
						<i class="ace-icon fa fa-check bigger-110"></i>
						提交
					</button>
				</div>
			</form>
		</div>
	</div>
</div>
<script>
	$('textarea.limited').inputlimiter();
	register_date($('.date-picker'));
	$("#modalForm").validate({
		submitHandler: function (form) {

			$(form).ajaxSubmit({
				success:function(ret){
					if(ret.success){
						bootbox.alert('提交成功。',function(){
							_reload();
						});
					}
				}
			});
		}
	});
	$('#modalForm [data-rel="select2"]').select2();
	$('[data-rel="tooltip"]').tooltip();
</script>
