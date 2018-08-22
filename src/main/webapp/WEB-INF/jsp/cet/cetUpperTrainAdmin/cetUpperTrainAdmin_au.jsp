<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cetUpperTrainAdmin!=null}">编辑</c:if><c:if test="${cetUpperTrainAdmin==null}">添加</c:if>上级调训单位管理员</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cet/cetUpperTrainAdmin_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${cetUpperTrainAdmin.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">类型</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="type" value="${cetUpperTrainAdmin.type}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">所属单位</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="unitId" value="${cetUpperTrainAdmin.unitId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">所属校领导</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="leaderId" value="${cetUpperTrainAdmin.leaderId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">管理员</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="userId" value="${cetUpperTrainAdmin.userId}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${cetUpperTrainAdmin!=null}">确定</c:if><c:if test="${cetUpperTrainAdmin==null}">添加</c:if></button>
</div>
<script>
    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
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
    //$("#modalForm :checkbox").bootstrapSwitch();
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#modalForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$('textarea.limited').inputlimiter();
    //$.register.date($('.date-picker'));
</script>