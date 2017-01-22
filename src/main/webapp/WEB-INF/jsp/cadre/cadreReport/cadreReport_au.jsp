<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cadreReport!=null}">编辑</c:if><c:if test="${cadreReport==null}">添加</c:if>干部工作总结</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cadreReport_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${cadreReport.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">形成日期</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="createDate" value="${cadreReport.createDate}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">所属干部</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="cadreId" value="${cadreReport.cadreId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">材料内容</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="filePath" value="${cadreReport.filePath}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">file_name</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="fileName" value="${cadreReport.fileName}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="remark" value="${cadreReport.remark}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${cadreReport!=null}">确定</c:if><c:if test="${cadreReport==null}">添加</c:if>"/>
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