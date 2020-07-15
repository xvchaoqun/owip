<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
	<button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
	<h3>下发任务通知</h3>
</div>
<div class="modal-body">
	<form class="form-horizontal" action="${ctx}/oa/oaTaskUser_infoMsg" autocomplete="off" disableautocomplete id="msgForm" method="post">
		<input type="hidden" name="taskId" value="${oaTask.id}">
		<div class="form-group">
			<label class="col-xs-3 control-label">任务名称</label>
			<div class="col-xs-8 label-text">
				${oaTask.name}
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label">选择消息模板</label>
			<div class="col-xs-8">
				<select data-rel="select2-ajax" data-ajax-url="${ctx}/shortMsgTpl_selects" data-width="503"
					name="relateId" data-placeholder="请选择">
					<option value=""></option>
				</select>
				<span class="help-block">注：从[系统管理-定向消息-消息模板]中选择</span>
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label"><span class="star">*</span>通知内容</label>
			<div class="col-xs-8">
				<textarea required class="form-control" name="msg" rows="12"></textarea>
			</div>
		</div>
	</form>
</div>
<div class="modal-footer">
	<a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
	<button type="button" id="msgSubmitBtn" class="btn btn-primary"
			data-loading-text="<i class='fa fa-spinner fa-spin '></i> 发送中，请稍后"
			data-success-text="发送成功" autocomplete="off">
		确定发送
	</button>
</div>
<script>
	$.register.ajax_select($('#msgForm select[name=relateId]'));
	$('#msgForm select[name=relateId]').change(function(){
		if($(this).val()>0){
		var content = $(this).select2("data")[0]['content'] || '';
		$("#msgForm textarea[name=msg]").val(content);
		}
	});
	$("#msgSubmitBtn").click(function(){$("#msgForm").submit();return false;});
	$("#msgForm").validate({
		submitHandler: function (form) {

			var $btn = $("#msgSubmitBtn").button('loading');
			$(form).ajaxSubmit({
				success: function (ret) {
					if (ret.success) {
						$btn.button("success").addClass("btn-success");

						$("#modal").modal('hide');
						//$("#jqGrid").trigger("reloadGrid");
						SysMsg.info("共发送{0}条通知，其中发送成功{1}条".format(ret.totalCount, ret.successCount))
					}else{
						$btn.button('reset');
					}
				}
			});
		}
	});
</script>