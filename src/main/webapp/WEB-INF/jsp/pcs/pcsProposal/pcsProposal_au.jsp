<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${pcsProposal!=null}">编辑</c:if><c:if test="${pcsProposal==null}">添加</c:if>提案</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/pcsProposal_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${pcsProposal.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">所属党代会</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="configId" value="${pcsProposal.configId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">提案编号</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="code" value="${pcsProposal.code}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">用户</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="userId" value="${pcsProposal.userId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">标题</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="name" value="${pcsProposal.name}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">关键字</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="keywords" value="${pcsProposal.keywords}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">提案类型</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="type" value="${pcsProposal.type}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${pcsProposal!=null}">确定</c:if><c:if test="${pcsProposal==null}">添加</c:if>"/>
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