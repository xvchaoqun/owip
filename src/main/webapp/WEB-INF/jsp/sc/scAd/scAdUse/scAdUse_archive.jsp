<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>正式归档扫描件</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/sc/scAdUse_archive" id="modalForm" method="post">
        <input type="hidden" name="id" value="${scAdUse.id}">

			<div class="form-group">
				<label class="col-xs-5 control-label">干部</label>
				<div class="col-xs-6 label-text">
                        ${scAdUse.cadre.realname}
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
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${scAdUse!=null}">确定</c:if><c:if test="${scAdUse==null}">添加</c:if></button>
</div>

<script>

	$.fileInput($("#modalForm input[name=_filePath]"), {
		no_file: '请上传WORD文件 ...',
		allowExt: ['doc', 'docx']
	});

	$.fileInput($("#modalForm input[name=_signFilePath]"), {
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