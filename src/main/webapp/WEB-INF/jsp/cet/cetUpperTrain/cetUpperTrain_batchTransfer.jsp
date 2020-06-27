<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="CET_TYPE_T_MAP" value="<%=CetConstants.CET_TYPE_T_MAP%>"/>
<c:set var="CET_TYPE_T_PARTY_SCHOOL" value="<%=CetConstants.CET_TYPE_T_PARTY_SCHOOL%>"/>
<c:set var="CET_TYPE_T_PARTY" value="<%=CetConstants.CET_TYPE_T_PARTY%>"/>
<c:set var="CET_PROJECT_TYPE_SPECIAL" value="<%=CetConstants.CET_PROJECT_TYPE_SPECIAL%>"/>
<c:set var="CET_PROJECT_TYPE_DAILY" value="<%=CetConstants.CET_PROJECT_TYPE_DAILY%>"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>批量转移</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cet/cetUpperTrain_batchTransfer" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="addType" value="${addType}">
        <input type="hidden" name="ids[]" value="${param['ids[]']}">
        <c:set var="count" value="${fn:length(fn:split(param['ids[]'],\",\"))}"/>
        <c:if test="${count>1}">
            <div class="form-group">
                <label class="col-xs-4 control-label">转移数量</label>

                <div class="col-xs-8 label-text">
                        ${count} 个
                </div>
            </div>
        </c:if>
        <div class="form-group">
            <label class="col-xs-4 control-label"><span class="star">*</span>选择培训总类名称</label>
            <div class="col-xs-8">
                <select required data-rel="select2"
                        name="cetType" data-placeholder="请选择">
                    <option></option>
                    <c:forEach items="${CET_TYPE_T_MAP}" var="entity">
                        <option value="${entity.key}">${entity.value}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label"><span class="star">*</span>选择培训类别</label>
            <div class="col-xs-8">
                <div class="input-group">
                    <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                        <input type="radio" name="specialType" id="specialType0"
                               value="${CET_PROJECT_TYPE_SPECIAL}">
                        <label for="specialType0">
                            专题培训
                        </label>
                    </div>
                    <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                        <input type="radio" name="specialType" id="specialType1"
                               value="${CET_PROJECT_TYPE_DAILY}">
                        <label for="specialType1">
                            日常培训
                        </label>
                    </div>
                </div>
            </div>
        </div>
        <div class="form-group trainClass">
            <label class="col-xs-4 control-label"><span class="star">*</span>选择培训班（报告名称）</label>
            <div class="col-xs-8">
                <select data-rel="select2-ajax"
                        data-width="372"
                        data-ajax-url="${ctx}/cet/cetUnitProject_selects"
                        name="projectId" data-placeholder="请选择">
                </select>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">

    <input type="submit" class="btn btn-primary" value="确定"/>
</div>

<script>

    $("#specialType0").click();

    function isShow() {
        var cetType = $("select[name=cetType]").val();
        //console.log(cetType)
        if (cetType == '${CET_TYPE_T_PARTY}') {
            $(".trainClass").removeClass("hidden");
            $(".trainClass select[name=projectId]").attr("required", 'required');
        }else {
            $(".trainClass").addClass("hidden");
            $(".trainClass select[name=projectId]").removeAttr("required");
        }
    }
    $("select[name=cetType]").on("change", function () {
        isShow();
    });
    isShow();

    $.register.del_select($('#modalForm select[name=partyId]'), 300);
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
    $('[data-rel="select2"]').select2();
    $.register.ajax_select($('[data-rel="select2-ajax"]'));
</script>