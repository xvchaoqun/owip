<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>上传大会材料</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/pcsPrFile_au" id="modalForm" method="post"
          enctype="multipart/form-data">
        <input type="hidden" name="id" value="${pcsPrFile.id}">
        <input type="hidden" name="templateId" value="${pcsPrFileTemplate.id}">
        <div class="form-group">
            <label class="col-xs-3 control-label">材料名称</label>
            <div class="col-xs-6 label-text">
                ${pcsPrFileTemplate.name}
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>上传材料(pdf格式)</label>

            <div class="col-xs-6">
                <input required class="form-control" type="file" name="_file"/>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="上传"/>
</div>
<script>
    $.fileInput($('#modalForm input[type=file]'),{
        allowExt: ['pdf'],
        allowMime: ['application/pdf']
    });
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        $.hashchange();
                    }
                }
            });
        }
    });
</script>