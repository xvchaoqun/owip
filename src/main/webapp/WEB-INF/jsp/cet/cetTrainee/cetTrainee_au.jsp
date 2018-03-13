<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cetTrainee!=null}">编辑</c:if><c:if test="${cetTrainee==null}">添加</c:if>可选课人员</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cet/cetTrainee_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${cetTrainee.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">所属培训班</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="trainId" value="${cetTrainee.trainId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">可选课人员</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="userId" value="${cetTrainee.userId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">已选课程数量</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="courseCount" value="${cetTrainee.courseCount}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="remark" value="${cetTrainee.remark}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${cetTrainee!=null}">确定</c:if><c:if test="${cetTrainee==null}">添加</c:if></button>
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
    $("#modalForm :checkbox").bootstrapSwitch();
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>