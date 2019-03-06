<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/pmd/constants.jsp"%>
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
            <label class="col-xs-3 control-label"><span class="star">*</span>标准名称</label>

            <div class="col-xs-6">
                <input required class="form-control" type="text" name="name" value="${pmdNorm.name}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">额度设定类型</label>

            <c:if test="${pmdNorm==null}">
                <div class="col-xs-6">
                    <select data-rel="select2" name="setType"
                            data-width="270"
                            data-placeholder="请选择">
                        <option></option>
                        <c:forEach items="${PMD_NORM_SET_TYPE_MAP}" var="_setType">
                            <c:if test="${!(type==PMD_NORM_TYPE_PAY && _setType.key==PMD_NORM_SET_TYPE_FREE)
                            && !(type==PMD_NORM_TYPE_REDUCE && _setType.key==PMD_NORM_SET_TYPE_FORMULA)}">
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
        <c:if test="${pmdNorm==null || pmdNorm.setType == PMD_NORM_SET_TYPE_FORMULA}">
            <div class="form-group" id="formulaDiv">
                <label class="col-xs-3 control-label">公式类别</label>
                <c:if test="${pmdNorm==null}">
                    <div class="col-xs-6">
                        <select data-rel="select2" name="formulaType"
                                data-width="270"
                                data-placeholder="请选择">
                            <option></option>
                            <c:forEach items="${PMD_FORMULA_TYPE_MAP}" var="_type">
                                <option value="${_type.key}">${_type.value}</option>
                            </c:forEach>
                        </select>
                    </div>
                </c:if>
                <c:if test="${pmdNorm!=null}">
                    <div class="col-xs-6 label-text">
                            ${PMD_FORMULA_TYPE_MAP.get(pmdNorm.formulaType)}
                    </div>
                </c:if>
            </div>
        </c:if>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> <c:if
            test="${pmdNorm!=null}">确定</c:if><c:if test="${pmdNorm==null}">添加</c:if></button>
</div>

<script>

    <c:if test="${pmdNorm==null || pmdNorm.setType ==PMD_NORM_SET_TYPE_FORMULA}">
    function setTypeChange() {

        var $setType = $("select[name=setType]");
        if ($setType.val() == '${PMD_NORM_SET_TYPE_FORMULA}') {
            $("select[name=formulaType]", "#formulaDiv").attr("required", "required");
            $("#formulaDiv").show();
        } else {
            $("select[name=formulaType]", "#formulaDiv").removeAttr("required");
            $("#formulaDiv").hide();
        }
    }
    $("select[name=setType]").change(function () {
        setTypeChange();
    });
    setTypeChange();
    </c:if>

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