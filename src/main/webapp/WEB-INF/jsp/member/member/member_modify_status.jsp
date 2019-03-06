<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
	<button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
	<h3>修改党籍状态</h3>
</div>
<div class="modal-body">
	<form class="form-horizontal" action="${ctx}/member_modify_status" id="modalForm" method="post">
		<c:set var="sysUser" value="${cm:getUserById(member.userId)}"/>
		<input type="hidden" name="userId" value="${member.userId}">
		<div class="form-group">
			<label class="col-xs-3 control-label">姓名</label>
			<div class="col-xs-6 label-text">
				${sysUser.realname}
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label">学工号</label>
			<div class="col-xs-6 label-text">
				${sysUser.code}
			</div>
		</div>

		<div class="form-group">
			<label class="col-xs-3 control-label"><span class="star">*</span>学工号</label>
			<div class="col-xs-6">
				<select required data-rel="select2" name="politicalStatus" data-placeholder="请选择"  data-width="120">
					<option></option>
					<c:forEach items="${MEMBER_POLITICAL_STATUS_MAP}" var="_status">
						<option value="${_status.key}">${_status.value}</option>
					</c:forEach>
				</select>
				<script>
					$("#modalForm select[name=politicalStatus]").val(${member.politicalStatus});
				</script>
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
	<input type="submit" class="btn btn-primary" value="确定"/>
</div>
<script>

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