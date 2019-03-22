<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
	<button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
	<h3>定时提醒</h3>
</div>
<div class="modal-body">
	<form class="form-horizontal" action="${ctx}/oaTaskUser_clockMsg" autocomplete="off" disableautocomplete id="modalForm" method="post">
		<input type="hidden" name="taskId" value="${param.taskId}">
		<div class="form-group">
			<label class="col-xs-3 control-label"><span class="star">*</span>提醒时间</label>
			<div class="col-xs-8">
				<div class="input-group">
					<input required class="form-control datetime-picker" type="text" name="deadline"
						   autocomplete="off" disableautocomplete
						   value="${cm:formatDate(oaTask.deadline, "yyyy-MM-dd HH:mm")}">
								<span class="input-group-addon">
								<i class="fa fa-calendar bigger-110"></i>
							</span>
				</div>
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label"><span class="star">*</span>提醒内容</label>
			<div class="col-xs-8">
				<textarea required class="form-control limit" maxlength="200" name="msg" rows="5"></textarea>
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label">重复</label>
			<div class="col-xs-8">
				<input type="radio" name="repeat" value="1">永不（只提醒一次）
				<input type="radio" name="repeat" value="2">每天一次
				<input type="radio" name="repeat" value="3">每隔3小时
			</div>
		</div>
	</form>
</div>
<div class="modal-footer">
	<a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
	<button type="button" id="submitBtn" class="btn btn-primary">确定</button>
</div>
<script>
	$('textarea.limited').inputlimiter();

	$("#submitBtn").click(function(){$("#modalForm").submit();return false;});
	$("#modalForm").validate({
		submitHandler: function (form) {
			$(form).ajaxSubmit({
				success: function (ret) {
					if (ret.success) {
						$("#modal").modal('hide');
						SysMsg.info("设置成功");
					}
				}
			});
		}
	});
</script>