<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="space-4"></div>
<div class="jqgrid-vertical-offset buttons">
<c:if test="${cm:isPermitted(PERMISSION_CADREADMIN) || hasDirectModifyCadreAuth}">
    <shiro:lacksRole name="${ROLE_ONLY_CADRE_VIEW}">
    <a class="popupBtn btn btn-warning btn-sm"
       data-width="800"
       data-url="${ctx}/hf_content?code=hf_cadre_company">
        <i class="fa fa-info-circle"></i> 填写说明</a>
    <shiro:hasPermission name="cadreCompany:edit">
        <button class="popupBtn btn btn-success btn-sm"
           data-url="${ctx}/cadreCompany_au?cadreId=${param.cadreId}"><i class="fa fa-plus"></i>
            添加</button>
        <button class="jqOpenViewBtn btn btn-primary btn-sm"
           data-url="${ctx}/cadreCompany_au"
           data-grid-id="#jqGrid_cadreCompany"
           data-querystr="&cadreId=${param.cadreId}"><i class="fa fa-edit"></i>
            修改</button>
    </shiro:hasPermission>
    <shiro:hasPermission name="cadreCompany:del">
        <button data-url="${ctx}/cadreCompany_batchDel"
                data-title="删除"
                data-msg="确定删除这{0}条数据？"
                data-grid-id="#jqGrid_cadreCompany"
                data-querystr="cadreId=${param.cadreId}"
                class="jqBatchBtn btn btn-danger btn-sm">
            <i class="fa fa-times"></i> 删除
        </button>
    </shiro:hasPermission>
    </shiro:lacksRole>
    </c:if>
<shiro:lacksRole name="${ROLE_ONLY_CADRE_VIEW}">
    <input type="checkbox" data-name="company" name="check" class="cadre-info-check"> 无此类情况
</shiro:lacksRole>
</div>
<div class="space-4"></div>
<table id="jqGrid_cadreCompany" class="jqGrid2"></table>
<div id="jqGridPager_cadreCompany"></div>

<script>
    <c:if test="${!canUpdate}">
    $("button.btn").prop("disabled", true);
    </c:if>
    $(".cadre-info-check").prop("checked", ${!canUpdate});
    <c:if test="${!canUpdateInfoCheck}">
    $(".cadre-info-check").prop("disabled", true);
    </c:if>

    $("#jqGrid_cadreCompany").jqGrid({
        <c:if test="${!cm:isPermitted(PERMISSION_CADREADMIN) && !hasDirectModifyCadreAuth}">
        multiselect:false,
        </c:if>
        ondblClickRow: function () {
        },
        pager: "#jqGridPager_cadreCompany",
        url: '${ctx}/cadreCompany_data?${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: colModels.cadreCompany
    });
    $(window).triggerHandler('resize.jqGrid2');

    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>