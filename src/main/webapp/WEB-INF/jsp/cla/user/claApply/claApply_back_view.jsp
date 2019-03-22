<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
	<button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
	<h3>已销假</h3>
</div>
<div class="modal-body">
	<form class="form-horizontal"  action="${ctx}/cla/claApply_back" autocomplete="off" disableautocomplete id="modalForm" method="post">
		<div class="form-group">
			<label class="col-xs-3 control-label">实际出发时间</label>
			<div class="col-xs-6 label-text">
				${cm:formatDate(claApply.realStartTime,'yyyy-MM-dd HH:mm')}
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label">实际返校时间</label>
			<div class="col-xs-6 label-text">
				${cm:formatDate(claApply.realEndTime,'yyyy-MM-dd HH:mm')}
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label">备注</label>
			<div class="col-xs-2 label-text">
				${claApply.realRemark}
			</div>
		</div>
	</form>
</div>
<div class="modal-footer">
	<a href="javascript:;" data-dismiss="modal" class="btn btn-default">关闭</a>
</div>