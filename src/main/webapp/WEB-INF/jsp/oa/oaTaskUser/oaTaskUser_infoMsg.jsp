<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
	<button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
	<h3>下发任务短信通知</h3>
</div>
<div class="modal-body">
	<form class="form-horizontal" action="${ctx}/oa/oaTaskUser_infoMsg" autocomplete="off" disableautocomplete id="modalForm" method="post">
		<input type="hidden" name="taskId" value="${oaTask.id}">
		<div class="form-group">
			<label class="col-xs-3 control-label">任务名称</label>
			<div class="col-xs-8 label-text">
				${oaTask.name}
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label"><span class="star">*</span>短信内容</label>
			<div class="col-xs-8">
				<textarea required class="form-control" name="msg" rows="8"></textarea>
			</div>
		</div>
	</form>
</div>
<div class="modal-footer">
	<a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
	<button type="button" id="submitBtn" class="btn btn-primary"
			data-loading-text="<i class='fa fa-spinner fa-spin '></i> 发送中，请稍后"
			data-success-text="发送成功" autocomplete="off">
		确定发送
	</button>
</div>
<script>
	$("#submitBtn").click(function(){$("#modalForm").submit();return false;});
	$("#modalForm").validate({
		submitHandler: function (form) {

			var $btn = $("#submitBtn").button('loading');
			$(form).ajaxSubmit({
				success: function (ret) {
					if (ret.success) {
						$btn.button("success").addClass("btn-success");

						$("#modal").modal('hide');
						//$("#jqGrid").trigger("reloadGrid");
						SysMsg.info("共发送{0}条短信，其中发送成功{1}条".format(ret.totalCount, ret.successCount))
					}else{
						$btn.button('reset');
					}
				}
			});
		}
	});
</script>