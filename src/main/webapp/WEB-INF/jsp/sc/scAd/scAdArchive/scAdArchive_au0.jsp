<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${scAdArchive!=null}">编辑</c:if><c:if test="${scAdArchive==null}">添加</c:if>干部任免审批表</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/sc/scAdArchive_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${scAdArchive.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>党委常委会</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="committeeId" value="${scAdArchive.committeeId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>干部</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="cadreId" value="${scAdArchive.cadreId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>干部任免审批表归档</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="filePath" value="${scAdArchive.filePath}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>干部任免审批表签字扫描件</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="signFilePath" value="${scAdArchive.signFilePath}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>干部考察材料归档</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="cisFilePath" value="${scAdArchive.cisFilePath}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>干部考察材料签字扫描件</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="cisSignFilePath" value="${scAdArchive.cisSignFilePath}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>备注</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="remark" value="${scAdArchive.remark}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${scAdArchive!=null}">确定</c:if><c:if test="${scAdArchive==null}">添加</c:if></button>
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