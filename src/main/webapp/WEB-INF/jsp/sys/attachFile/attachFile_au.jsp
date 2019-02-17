<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${attachFile!=null}">编辑</c:if><c:if test="${attachFile==null}">添加</c:if>系统附件</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/attachFile_au" id="modalForm" method="post" enctype="multipart/form-data">
        <input type="hidden" name="id" value="${attachFile.id}">

        <div class="form-group">
            <label class="col-xs-3 control-label">附件</label>
            <div class="col-xs-6">
                <input ${empty attachFile.id?"required":""} class="form-control" type="file" name="_file" />
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">下载文件名</label>
            <div class="col-xs-6">
                <input class="form-control" type="text" name="filename" value="${attachFile.filename}" />
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">唯一标识</label>
            <div class="col-xs-6">
                <input class="form-control" type="text" name="code" value="${attachFile.code}" />
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">类别</label>
            <div class="col-xs-6">
                <select required name="type" data-placeholder="请选择" class="select2 tag-input-style" data-rel="select2">
                    <option></option>
                    <c:forEach items="<%=SystemConstants.ATTACH_FILE_TYPE_MAP%>" var="_type">
                        <option value="${_type.key}">${_type.value}</option>
                    </c:forEach>
                </select>
                <script>
                    $("#modalForm select[name=type]").val('${attachFile.type}');
                </script>
            </div>
        </div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
                    <textarea class="form-control limited"name="remark">${attachFile.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary">${attachFile!=null?"确定":"添加"}</button>
</div>

<script>

    $('textarea.limited').inputlimiter();
    $.fileInput($('#modalForm input[type=file]'))
    $("#modal #submitBtn").click(function(){$("#modalForm").submit();return false;})
    $("#modalForm").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $("#jqGrid").trigger("reloadGrid");
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
</script>