<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
	<button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
	<h3>审核未通过短信提醒</h3>
</div>
<div class="modal-body">
	<form class="form-horizontal" action="${ctx}/oaTaskUser_denyMsg" id="modalForm" method="post">
		<input type="hidden" name="id" value="${param.id}">
		<div class="form-group">
			<label class="col-xs-3 control-label">任务对象</label>
			<div class="col-xs-8 label-text">
				${sysUser.realname}
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label">短信内容</label>
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

						SysMsg.info(ret.sendSuccess?"发送成功":"发送失败");
					}else{
						$btn.button('reset');
					}
				}
			});
		}
	});
</script>