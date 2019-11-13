<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${sysMsg!=null?'编辑':'添加'}系统提醒</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/sysMsg_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${sysMsg.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label"> 内容</label>
				<div class="col-xs-6">
                        <textarea required class="form-control" name="content" rows="8">${sysMsg.content}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <button id="affirmBtn" class="btn btn-primary"><i class="fa fa-check"></i>确定</button>
</div>
<script>

	$("#affirmBtn").click(function () {

		$("#modal").modal('hide');
		$("#jqGrid").trigger("reloadGrid");
	});
</script>