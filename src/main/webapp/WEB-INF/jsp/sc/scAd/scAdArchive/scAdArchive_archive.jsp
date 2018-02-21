<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>正式归档扫描件</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/sc/scAdArchive_archive" id="modalForm" method="post">
        <input type="hidden" name="id" value="${scAdArchive.id}">

			<div class="form-group">
				<label class="col-xs-5 control-label">干部</label>
				<div class="col-xs-6 label-text">
                        ${cm:getCadreById(scAdArchive.cadreId).realname}
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-5 control-label">干部任免审批表(word文档)</label>
				<div class="col-xs-6">
					<input class="form-control" type="file" name="_filePath"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-5 control-label">干部任免审批表签字扫描件</label>
				<div class="col-xs-6">
					<input class="form-control" type="file" name="_signFilePath"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-5 control-label">干部考察材料(word文档)</label>
				<div class="col-xs-6">
					<input class="form-control" type="file" name="_cisFilePath"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-5 control-label">干部考察材料签字扫描件</label>
				<div class="col-xs-6">
					<input class="form-control" type="file" name="_cisSignFilePath"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-5 control-label">备注</label>
				<div class="col-xs-6">
					<textarea class="form-control limited"
							  name="remark">${scAdArchive.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${scAdArchive!=null}">确定</c:if><c:if test="${scAdArchive==null}">添加</c:if></button>
</div>

<script>

	$.fileInput($("#modalForm input[name=_filePath], #modalForm input[name=_cisFilePath]"), {
		no_file: '请上传WORD文件 ...',
		allowExt: ['doc', 'docx']
	});

	$.fileInput($("#modalForm input[name=_signFilePath], #modalForm input[name=_cisSignFilePath]"), {
		no_file: '请上传PDF文件 ...',
		allowExt: ['pdf'],
		allowMime: ['application/pdf']
	});

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
</script>