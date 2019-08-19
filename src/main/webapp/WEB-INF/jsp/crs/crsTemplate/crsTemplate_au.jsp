<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/crs/constants.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${crsTemplate!=null}">编辑</c:if><c:if test="${crsTemplate==null}">添加</c:if>招聘条件通用模板</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/crsTemplate_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${crsTemplate.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>类别</label>
				<div class="col-xs-6">
                    <select required data-rel="select2" name="type" data-placeholder="请选择">
                        <option></option>
                        <c:forEach items="${CRS_TEMPLATE_TYPE_MAP}" var="entry">
                        <option value="${entry.key}">${entry.value}</option>
                        </c:forEach>
                    </select>
                    <script type="text/javascript">
                        $("#modal form select[name=type]").val(${crsTemplate.type});
                    </script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>模板名称</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="name" value="${crsTemplate.name}">
				</div>
			</div>
			<div class="form-group">
                <div class="col-xs-6">
                    <input type="hidden" name="content">
                    <textarea id="contentId">${crsTemplate.content}</textarea>
                </div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${crsTemplate!=null}">确定</c:if><c:if test="${crsTemplate==null}">添加</c:if>"/>
</div>
<script>
    var ke = KindEditor.create('#contentId', {
        allowFileManager: true,
        uploadJson: '${ctx}/ke/upload_json',
        fileManagerJson: '${ctx}/ke/file_manager_json',
        height: '400px',
        width: '720px'
    });

    $("#modalForm").validate({
        submitHandler: function (form) {

            $("#modalForm input[name=content]").val(ke.html());

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