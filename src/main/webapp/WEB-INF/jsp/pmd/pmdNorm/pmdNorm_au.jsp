<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${pmdNorm!=null}">编辑</c:if><c:if test="${pmdNorm==null}">添加</c:if>
        ${PMD_NORM_TYPE_MAP.get(type)}</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/pmd/pmdNorm_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${pmdNorm.id}">
        <input type="hidden" name="type" value="${param.type}">

        <div class="form-group">
            <label class="col-xs-3 control-label">标准名称</label>

            <div class="col-xs-6">
                <input required class="form-control" type="text" name="name" value="${pmdNorm.name}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">额度设定类型</label>

            <c:if test="${pmdNorm==null}">
                <div class="col-xs-6">
                    <select data-rel="select2" name="setType"
                            data-width="120"
                            data-placeholder="请选择">
                        <option></option>
                        <c:forEach items="${PMD_NORM_SET_TYPE_MAP}" var="_setType">
                            <c:if test="${!(type==PMD_NORM_TYPE_PAY && _setType.key==PMD_NORM_SET_TYPE_FREE)}">
                            <option value="${_setType.key}">${_setType.value}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>
            </c:if>
            <c:if test="${pmdNorm!=null}">
                <div class="col-xs-6 label-text">
                        ${PMD_NORM_SET_TYPE_MAP.get(pmdNorm.setType)}
                </div>
            </c:if>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> <c:if
            test="${pmdNorm!=null}">确定</c:if><c:if test="${pmdNorm==null}">添加</c:if></button>
</div>

<script>
    $("#submitBtn").click(function () {
        $("#modalForm").submit();
        return false;
    });
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        $("#modal").modal('hide');
                        $("#jqGrid").trigger("reloadGrid");
                    }
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
</script>