<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="multi-row-head-table">
<ul class="jqgrid-vertical-offset nav nav-tabs padding-12 tab-color-blue background-blue">
    <li class="${cls==1?"active":""}">
        <a href="javascript:;" class="loadPage"
           data-load-el="#tab-content"
           data-url="${ctx}/cadreCompany?cls=1&cadreId=${param.cadreId}"><i class="fa fa-list"></i> 正在兼职</a>
    </li>
    <li class="${cls==2?"active":""}">
        <a href="javascript:;" class="loadPage"
           data-load-el="#tab-content"
           data-url="${ctx}/cadreCompany?cls=2&cadreId=${param.cadreId}"><i class="fa fa-history"></i> 历史兼职</a>
    </li>
    <c:if test="${cm:isPermitted(PERMISSION_CADREADMIN) || hasDirectModifyCadreAuth}">
    <shiro:lacksPermission name="${PERMISSION_CADREONLYVIEW}">
        <div class="buttons" style="position:absolute;left:250px;">
            <a class="popupBtn btn btn-warning btn-sm"
               data-width="800"
               data-url="${ctx}/hf_content?code=hf_cadre_company">
                <i class="fa fa-info-circle"></i> 填写说明</a>
        </div>
    </shiro:lacksPermission>
    </c:if>
</ul>
<div class="space-4"></div>
<div class="jqgrid-vertical-offset buttons">
<c:if test="${cm:isPermitted(PERMISSION_CADREADMIN) || hasDirectModifyCadreAuth}">
    <shiro:lacksPermission name="${PERMISSION_CADREONLYVIEW}">
    <%--<a class="popupBtn btn btn-warning btn-sm"
       data-width="800"
       data-url="${ctx}/hf_content?code=hf_cadre_company">
        <i class="fa fa-info-circle"></i> 填写说明</a>--%>
    <shiro:hasPermission name="cadreCompany:edit">

        <button class="popupBtn btn btn-success btn-sm" data-width="800"
           data-url="${ctx}/cadreCompany_au?cadreId=${param.cadreId}&grid=jqGrid_cadreCompany&isFinished=${cls==1?0:1}"><i class="fa fa-plus"></i>
            添加</button>

        <button class="jqOpenViewBtn btn btn-primary btn-sm"
           data-url="${ctx}/cadreCompany_au"
           data-grid-id="#jqGrid_cadreCompany" data-width="800"
           data-querystr="&cadreId=${param.cadreId}&grid=jqGrid_cadreCompany"><i class="fa fa-edit"></i>
            修改</button>
    </shiro:hasPermission>
        <c:if test="${cm:isPermitted(PERMISSION_CADREADMIN)}">
    <shiro:hasPermission name="cadreCompany:finish">
        <c:if test="${cls==1}">
        <button class="jqOpenViewBtn btn btn-warning btn-sm"
                data-url="${ctx}/cadreCompany_finish"
                data-grid-id="#jqGrid_cadreCompany"
                data-querystr="&cadreId=${param.cadreId}&grid=jqGrid_cadreCompany"><i class="fa fa-dot-circle-o"></i>
            兼职结束</button>
        </c:if>
        <c:if test="${cls==2}">
        <button class="jqItemBtn btn btn-success btn-sm"
                data-msg="确认返回正在兼职？"
                data-url="${ctx}/cadreCompany_finish"
                data-grid-id="#jqGrid_cadreCompany"
                data-callback="_reload2"
                data-querystr="&cadreId=${param.cadreId}&isFinished=0"><i class="fa fa-reply"></i>
            返回正在兼职</button>
        </c:if>
    </shiro:hasPermission>
        </c:if>

    <shiro:hasPermission name="cadreCompany:del">
        <button data-url="${ctx}/cadreCompany_batchDel"
                data-title="删除"
                data-msg="确定删除这{0}条数据？<br/>（删除后无法恢复，请谨慎操作！！）"
                data-grid-id="#jqGrid_cadreCompany"
                data-querystr="cadreId=${param.cadreId}"
                class="jqBatchBtn btn btn-danger btn-sm">
            <i class="fa fa-times"></i> 删除
        </button>
    </shiro:hasPermission>
    </shiro:lacksPermission>
    </c:if>
<c:if test="${cls==1}">
    <a class="downloadBtn btn btn-success btn-sm tooltip-success" data-method="get"
                           data-url="${ctx}/cadreCompany_data?cadreId=${param.cadreId}&export=1">
                            <i class="fa fa-download"></i> 导出</a>
    <shiro:hasPermission name="export:cadreCompanyConfirm">
    <c:if test="${cm:getCadreById(param.cadreId).status==CADRE_STATUS_CJ}">
        <a class="downloadBtn btn btn-success btn-sm tooltip-success" data-method="get"
                           data-url="${ctx}/cadreCompany_data?cadreId=${param.cadreId}&export=1&formatType=1">
                            <i class="fa fa-download"></i> 导出确认表</a>
        </c:if>
    </shiro:hasPermission>
    <shiro:lacksPermission name="${PERMISSION_CADREONLYVIEW}">
    <input type="checkbox" data-name="company" name="check" class="cadre-info-check"> 无此类情况
</shiro:lacksPermission>
</c:if>
</div>
<div class="space-4"></div>
<table id="jqGrid_cadreCompany" class="jqGrid2"></table>
<div id="jqGridPager_cadreCompany"></div>
</div>
<style>
    .multi-row-head-table #jqGrid_cadreCompany_cb{
        padding: 10px!important;
        padding-right: 2px!important;
    }
    <c:if test="${!cm:isPermitted(PERMISSION_CADREADMIN) && !hasDirectModifyCadreAuth}">
    .multi-row-head-table .frozen-div .ui-jqgrid-labels th{
        padding: 10px!important;
        padding-right: 2px!important;
    }
    </c:if>
</style>
<jsp:include page="/WEB-INF/jsp/cadre/cadreCompany/colModels.jsp"/>
<script>
    function _reload2(){
        $("#jqGrid_cadreCompany").trigger("reloadGrid");
    }
    <c:if test="${!canUpdate}">
    $(".cadreView button.btn").prop("disabled", true);
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
        colModel: colModels_cadreCompany
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');

    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>