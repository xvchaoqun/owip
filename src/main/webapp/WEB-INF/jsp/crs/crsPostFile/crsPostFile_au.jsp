<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${crsPostFile!=null}">编辑</c:if><c:if test="${crsPostFile==null}">添加</c:if>招聘会记录文件</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/crsPostFile_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${crsPostFile.id}">
        <input type="hidden" name="postId" value="${postId}">
        <div class="form-group">
            <label class="col-xs-3 control-label">文件名</label>
            <div class="col-xs-6">
                <input class="form-control" type="text" name="fileName" value="${crsPostFile.fileName}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">文件</label>
            <div class="col-xs-6">
                <input class="form-control" type="file" name="_file" />
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">类别</label>
            <div class="col-xs-6">
                <select class="form-control" name="type"
                        data-rel="select2" data-placeholder="请选择">
                    <option></option>
                    <c:forEach items="${CRS_POST_FILE_TYPE_MAP}" var="type">
                        <option value="${type.key}">${type.value}</option>
                    </c:forEach>
                </select>
                <script>
                    $("#modalForm select[name=type]").val('${crsPostFile.type}');
                </script>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">备注</label>
            <div class="col-xs-6">
                <textarea class="form-control limited" type="text" name="remark" >${crsPostFile.remark}</textarea>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary"
           value="<c:if test="${crsPostFile!=null}">确定</c:if><c:if test="${crsPostFile==null}">添加</c:if>"/>
</div>

<script>
    $.fileInput($('#modalForm input[type=file]'));
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        $("#modal").modal('hide');
                        $("#jqGrid2").trigger("reloadGrid");
                    }
                }
            });
        }
    });
    $("#modalForm :checkbox").bootstrapSwitch();
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>