<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cetDiscussGroupObj!=null}">编辑</c:if><c:if test="${cetDiscussGroupObj==null}">添加</c:if>小组成员</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cet/cetDiscussGroupObj_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${cetDiscussGroupObj.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">分组讨论</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="discussId" value="${cetDiscussGroupObj.discussId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">研讨小组</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="discussGroupId" value="${cetDiscussGroupObj.discussGroupId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">小组成员</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="objId" value="${cetDiscussGroupObj.objId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">是否实际完成</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="isFinished" value="${cetDiscussGroupObj.isFinished}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="remark" value="${cetDiscussGroupObj.remark}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${cetDiscussGroupObj!=null}">确定</c:if><c:if test="${cetDiscussGroupObj==null}">添加</c:if></button>
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