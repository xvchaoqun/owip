<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${dispatchWorkFile!=null}">编辑</c:if><c:if test="${dispatchWorkFile==null}">添加</c:if>干部工作文件</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/dispatchWorkFile_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${dispatchWorkFile.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">发文单位</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="unitType" value="${dispatchWorkFile.unitType}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">年度</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="year" value="${dispatchWorkFile.year}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">所属专项工作</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="workType" value="${dispatchWorkFile.workType}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">排序</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="sortOrder" value="${dispatchWorkFile.sortOrder}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">发文号</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="code" value="${dispatchWorkFile.code}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">发文日期</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="pubDate" value="${dispatchWorkFile.pubDate}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">文件名</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="fileName" value="${dispatchWorkFile.fileName}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">文件</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="filePath" value="${dispatchWorkFile.filePath}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">保密级别</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="privacyType" value="${dispatchWorkFile.privacyType}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="remark" value="${dispatchWorkFile.remark}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${dispatchWorkFile!=null}">确定</c:if><c:if test="${dispatchWorkFile==null}">添加</c:if>"/>
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