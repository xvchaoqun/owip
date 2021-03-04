<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
	<button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
	<h3>更换学工号</h3>
</div>
<div class="modal-body">
	<form class="form-horizontal" action="${ctx}/member_changeCode" autocomplete="off" disableautocomplete id="modalForm" method="post">
		<c:set var="sysUser" value="${cm:getUserById(member.userId)}"/>
		<input type="hidden" name="userId" value="${member.userId}">
		<div class="form-group">
			<label class="col-xs-3 control-label">姓名</label>
			<div class="col-xs-6 label-text">
				${sysUser.realname}
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label">当前学工号</label>
			<div class="col-xs-6 label-text">
				${sysUser.code}<br/>（身份证号码：<t:mask src="${sysUser.idcard}" type="idCard"/>）
			</div>
		</div>

		<div class="form-group">
			<label class="col-xs-3 control-label"><span class="star">*</span>新学工号</label>
			<div class="col-xs-6">
				<select required data-rel="select2-ajax"
					   data-ajax-url="${ctx}/notMember_selects"
					   name="newUserId" data-width="270"
					   data-placeholder="请输入账号或姓名或学工号">
						<option></option>
				</select>
				<span class="help-block">注：此处只显示非党员的学工号</span>
			</div>
		</div>

		<div class="form-group">
			<label class="col-xs-3 control-label">修改原因</label>
			<div class="col-xs-6">
				<textarea class="form-control limited" name="remark">${remark}</textarea>
			</div>
		</div>
	</form>
</div>
<div class="modal-footer">
	<div class="note">
		注：会变更前后账号的角色（非党员<->党员）
	</div>
	<input type="submit" class="btn btn-primary" value="确定"/>
</div>
<script>
	$.register.user_select($('#modalForm select[name=newUserId]'));

	$("#modal form").validate({
		submitHandler: function (form) {
			$(form).ajaxSubmit({
				success:function(ret){
					if(ret.success){
						$("#modal").modal("hide")
						$("#jqGrid").trigger("reloadGrid");
					}
				}
			});
		}
	});
	$('[data-rel="select2"]').select2();
	$('textarea.limited').inputlimiter();
</script>