<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

            <div class="modal-header">
                <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
                <h3><c:if test="${pcsConfig!=null}">编辑</c:if><c:if test="${pcsConfig==null}">添加</c:if>大会材料清单</h3>
            </div>
            <div class="modal-body">
            <form class="form-horizontal" action="${ctx}/pcsPrFileTemplate_au" id="modalForm" method="post"
                  enctype="multipart/form-data">
                    <input type="hidden" name="id" value="${pcsPrFileTemplate.id}">
                    <div class="form-group">
                        <label class="col-xs-3 control-label"><span class="star">*</span>材料名称</label>

                        <div class="col-xs-6">
                            <input required class="form-control" type="text" name="name"
                                   value="${pcsPrFileTemplate.name}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-3 control-label">${empty pcsPrFileTemplate.filePath?"*":""}上传材料</label>

                        <div class="col-xs-6">
                            <input ${empty pcsPrFileTemplate.filePath?"required":""}
                                    class="form-control" type="file" name="_file"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-3 control-label">备注</label>
                        <div class="col-xs-6">
                            <textarea class="form-control limited" name="remark">${pcsPrFileTemplate.remark}</textarea>
                        </div>
                    </div>
            </form>
            </div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="上传模板"/>
</div>

<script>
    $('textarea.limited').inputlimiter();
    $.fileInput($('#modalForm input[type=file]'));

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