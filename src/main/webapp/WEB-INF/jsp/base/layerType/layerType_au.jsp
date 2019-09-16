<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${layerType!=null}">编辑</c:if><c:if test="${layerType==null}">添加</c:if>类别</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/layerType_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${layerType.id}">
        <input type="hidden" name="fid" value="${fid}">
        <c:if test="${not empty topLayerType}">
            <div class="form-group">
                <label class="col-xs-3 control-label">所属上级类别</label>
                <div class="col-xs-6 label-text">
                        ${topLayerType.name}
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">编号</label>
                <div class="col-xs-6">
                    <input maxlength="50" class="form-control" type="text" name="code"
                           value="${layerType.code}">
                </div>
            </div>
        </c:if>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>类别名称</label>
            <div class="col-xs-6">
                <input required maxlength="50" class="form-control" type="text" name="name" value="${layerType.name}">
            </div>
        </div>
        <c:if test="${empty topLayerType}">
            <div class="form-group">
                <label class="col-xs-3 control-label"><span class="star">*</span>编码</label>
                <div class="col-xs-6">
                    <input required maxlength="50" class="form-control" type="text" name="code"
                           value="${layerType.code}">
                    <span class="help-block">* 唯一，仅用于编程</span>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label"><span class="star">*</span>一级类别名称</label>
                <div class="col-xs-6">
                    <input required maxlength="50" class="form-control" type="text" name="firstLevel"
                           value="${layerType.firstLevel}">
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label"><span class="star">*</span>二级类别名称</label>
                <div class="col-xs-6">
                    <input required maxlength="50" class="form-control" type="text" name="secondLevel"
                           value="${layerType.secondLevel}">
                </div>
            </div>
        </c:if>

        <div class="form-group">
            <label class="col-xs-3 control-label">备注</label>
            <div class="col-xs-6">
                <textarea maxlength="200" class="form-control limited" name="remark">${layerType.remark}</textarea>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary"
           value="<c:if test="${layerType!=null}">确定</c:if><c:if test="${layerType==null}">添加</c:if>"/>
</div>

<script>
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        $("#modal").modal('hide');
                        $("#jqGrid${empty fid?'':'2'}").trigger("reloadGrid");
                    }
                }
            });
        }
    });
    $('textarea.limited').inputlimiter();
    $.register.ajax_select($('[data-rel="select2-ajax"]'))
</script>