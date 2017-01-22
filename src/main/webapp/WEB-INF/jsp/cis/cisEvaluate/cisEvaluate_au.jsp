<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cisEvaluate!=null}">编辑</c:if><c:if test="${cisEvaluate==null}">添加</c:if>现实表现材料和评价</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cisEvaluate_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${cisEvaluate.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">形成日期</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="createDate" value="${cisEvaluate.createDate}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">考察对象</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="cadreId" value="${cisEvaluate.cadreId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">材料类型</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="type" value="${cisEvaluate.type}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">材料内容</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="filePath" value="${cisEvaluate.filePath}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">file_name</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="fileName" value="${cisEvaluate.fileName}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="remark" value="${cisEvaluate.remark}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${cisEvaluate!=null}">确定</c:if><c:if test="${cisEvaluate==null}">添加</c:if>"/>
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