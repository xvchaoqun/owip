<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<ul class="jqgrid-vertical-offset nav nav-tabs padding-12 tab-color-blue background-blue">
    <li class="${type==1?"active":""}">
        <a href="javascript:" onclick="_innerPage(1)"><i
                class="fa fa-flag"></i> 干部考察报告</a>
    </li>
    <li class="${type==2?"active":""}">
        <a href="javascript:" onclick="_innerPage(2)"><i class="fa fa-flag"></i>
            现实表现材料和评价</a>
    </li>
    <li class="${type==3?"active":""}">
        <a href="javascript:" onclick="_innerPage(3)"><i class="fa fa-flag"></i>
            本人工作总结</a>
    </li>
</ul>
    <div class="space-4"></div>
    <table id="jqGrid_cadreInspectInfo" data-width-reduce="50" class="jqGrid2"></table>
    <div id="jqGridPager_cadreInspectInfo"></div>
<div class="footer-margin"/>
<jsp:include page="/WEB-INF/jsp/cadre/colModels.jsp"/>
<script>
    function _innerPage(type, fn) {
        $("#view-box .tab-content").load("${ctx}/cadreInspectInfo_page?cadreId=${param.cadreId}&type=" + type, null, fn)
    }
    <c:if test="${type==1}">
    var url='${ctx}/cadreInspectObj_data?${cm:encodeQueryString(pageContext.request.queryString)}';
    var colModel = colModels.cisInspectObj;
    </c:if>
    <c:if test="${type==2}">
    var url='${ctx}/cisEvaluate_data?${cm:encodeQueryString(pageContext.request.queryString)}';
    var colModel = colModels.cisEvaluate;
    </c:if>
    <c:if test="${type==3}">
    var url='${ctx}/cadreReport_data?${cm:encodeQueryString(pageContext.request.queryString)}';
    var colModel = colModels.cadreReport;
    </c:if>
    $("#jqGrid_cadreInspectInfo").jqGrid({
        multiselect:false,
        ondblClickRow: function () {
        },
        pager: "#jqGridPager_cadreInspectInfo",
        url: url,
        colModel: colModel
    }).on("initGrid", function () {
        $(window).triggerHandler('resize.jqGrid2');
    });

    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>