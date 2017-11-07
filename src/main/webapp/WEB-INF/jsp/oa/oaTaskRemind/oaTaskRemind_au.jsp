<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${oaTaskRemind!=null}">编辑</c:if><c:if test="${oaTaskRemind==null}">添加</c:if>任务对象本人设置的短信提醒</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/oa/oaTaskRemind_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${oaTaskRemind.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">任务</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="taskId" value="${oaTaskRemind.taskId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">任务对象</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="userId" value="${oaTaskRemind.userId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">提醒内容</label>
				<div class="col-xs-6">
                        <textarea class="form-control" name="content">${oaTaskRemind.content}</textarea>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">提醒时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="startTime" value="${oaTaskRemind.startTime}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">是否结束</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="isFinish" value="${oaTaskRemind.isFinish}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${oaTaskRemind!=null}">确定</c:if><c:if test="${oaTaskRemind==null}">添加</c:if>"/>
</div>

<script>
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $("#jqGrid").trigger("reloadGrid");
                    }
                }
            });
        }
    });
    $("#modalForm :checkbox").bootstrapSwitch();
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>