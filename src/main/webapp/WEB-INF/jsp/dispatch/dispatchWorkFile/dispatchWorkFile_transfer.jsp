<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>干部工作文件批量转移</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/dispatchWorkFile_transfer" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="ids" value="${param.ids}">
        <c:set var="count" value="${fn:length(fn:split(param.ids,\",\"))}"/>
        <c:if test="${count>1}">
            <div class="form-group">
                <label class="col-xs-3 control-label">转移文件</label>

                <div class="col-xs-4 label-text">
                        ${count} 个
                </div>
            </div>
        </c:if>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>转移至分类</label>

            <div class="col-xs-4">
                <select required data-rel="select2" name="type" data-placeholder="请选择" data-width="273">
                    <option></option>
                    <c:import url="/metaTypes?__id=${cm:getMetaClassByCode('mc_work_file_type').id}&extraAttr=${cm:getMetaType(param.type).extraAttr}"/>
                </select>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">

    <input type="submit" class="btn btn-primary" value="确定"/>
</div>

<script>
    $('#modalForm [data-rel="select2"]').select2();
    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        $("#modal").modal("hide");
                        $("#jqGrid").trigger("reloadGrid");
                    }
                }
            });
        }
    });
</script>