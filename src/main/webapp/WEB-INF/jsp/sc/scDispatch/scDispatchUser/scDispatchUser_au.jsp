<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${scDispatchUser!=null}">编辑</c:if><c:if test="${scDispatchUser==null}">添加</c:if>任免对象</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/sc/scDispatchUser_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${scDispatchUser.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">文件起草签发</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="dispatchId" value="${scDispatchUser.dispatchId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">关联表决记录</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="voteId" value="${scDispatchUser.voteId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">类别</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="type" value="${scDispatchUser.type}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">排序</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="sortOrder" value="${scDispatchUser.sortOrder}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${scDispatchUser!=null}">确定</c:if><c:if test="${scDispatchUser==null}">添加</c:if></button>
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