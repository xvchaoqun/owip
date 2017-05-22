<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cpcAllocation!=null}">编辑</c:if><c:if test="${cpcAllocation==null}">添加</c:if>干部职数配置情况</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cpcAllocation_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${cpcAllocation.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">单位</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="unitId" value="${cpcAllocation.unitId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">行政级别</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="postId" value="${cpcAllocation.postId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">数量</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="num" value="${cpcAllocation.num}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${cpcAllocation!=null}">确定</c:if><c:if test="${cpcAllocation==null}">添加</c:if>"/>
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