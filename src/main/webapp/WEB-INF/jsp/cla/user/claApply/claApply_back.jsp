<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
	<button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
	<h3>销假</h3>
</div>
<div class="modal-body">
	<form class="form-horizontal"  action="${ctx}/user/cla/claApply_back" id="modalForm" method="post">
		<input type="hidden" name="id" value="${claApply.id}">
		<input type="hidden" name="cadreId" value="${claApply.cadreId}">
		<div class="form-group">
			<label class="col-xs-3 control-label"><span class="star">*</span>实际出发时间</label>
			<div class="col-xs-6">
				<div class="input-group">
					<input required class="form-control datetime-picker" name="realStartTime" type="text"
						   value="${cm:formatDate(claApply.realStartTime,'yyyy-MM-dd HH:mm')}" />
					<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
				</div>
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label"><span class="star">*</span>实际返校时间</label>
			<div class="col-xs-6">
				<div class="input-group">
					<input required class="form-control datetime-picker" name="realEndTime" type="text"
						   value="${cm:formatDate(claApply.realEndTime,'yyyy-MM-dd HH:mm')}" />
					<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
				</div>
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label">备注</label>
			<div class="col-xs-6">
				<textarea class="form-control limited" name="realRemark" maxlength="100" rows="5">${claApply.realRemark}</textarea>
			</div>
		</div>
	</form>
</div>
<div class="modal-footer">
	<shiro:lacksRole name="${ROLE_CADREADMIN}">
	<div style="font-size: 14pt;font-weight: bolder;color: red;text-align: left;padding: 10px;">
		注意：提交后内容不可以修改
	</div>
	</shiro:lacksRole>
	<a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
	<input id="submitBtn" type="submit" class="btn btn-primary" value="提交"/>
</div>
<script>
	$.register.datetime($('.datetime-picker'));
	$('textarea.limited').inputlimiter();
	$("#modalForm").validate({
		submitHandler: function (form) {
			$(form).ajaxSubmit({
				success:function(ret){
					if(ret.success){
						page_reload();
						//SysMsg.success('操作成功。', '成功');
					}
				}
			});
		}
	});
</script>