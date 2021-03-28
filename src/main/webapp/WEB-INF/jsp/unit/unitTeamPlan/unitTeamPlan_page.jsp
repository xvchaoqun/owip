<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<jsp:include page="menu.jsp"/>
<div class="space-4"></div>
<div class="jqgrid-vertical-offset buttons">
    <shiro:hasPermission name="unitTeam:edit">
        <button class="popupBtn btn btn-success btn-sm"
                data-url="${ctx}/unitTeamPlan_au?unitTeamId=${param.unitTeamId}">
            <i class="fa fa-plus"></i> 添加
        </button>
        <button class="jqOpenViewBtn btn btn-primary btn-sm"
                data-url="${ctx}/unitTeamPlan_au"
                data-grid-id="#jqGrid_plan"><i class="fa fa-edit"></i>
            修改
        </button>
         <button data-url="${ctx}/unitTeamPlan_abolish"
                data-grid-id="#jqGrid_plan"
                class="jqOpenViewBtn btn btn-warning btn-sm">
            <i class="fa fa-stop-circle-o"></i> 废止
        </button>
    </shiro:hasPermission>
    <shiro:hasPermission name="unitTeam:del">
        <button data-url="${ctx}/unitTeamPlan_batchDel"
                data-title="删除"
                data-msg="确定删除这{0}条数据？<br/>（删除后无法恢复，请谨慎操作！！）"
                data-grid-id="#jqGrid_plan"
                class="jqBatchBtn btn btn-danger btn-sm">
            <i class="fa fa-trash"></i> 删除
        </button>
    </shiro:hasPermission>
    <%--<button class="jqExportBtn btn btn-success btn-sm tooltip-success"
            data-url="${ctx}/unitTeamPlan_data"
            data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
        <i class="fa fa-download"></i> 导出
    </button>--%>
</div>
<div class="space-4"></div>
<table id="jqGrid_plan" class="jqGrid2 table-striped" <c:if test="${param.load=='view'}"> data-height-reduce="20" data-width-reduce="30"</c:if>></table>
<div id="jqGridPager_plan"></div>
<script>
    $("#jqGrid_plan").jqGrid({
        rownumbers: true,
        pager:'#jqGridPager_plan',
        url: '${ctx}/unitTeamPlan_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '起始时间', name: 'startDate', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
            {label: '废止时间', name: 'endDate', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
            {label: '行政正职', name: 'mainPosts', formatter: function (cellvalue, options, rowObject) {
                   if(cellvalue==undefined) return '--'
                    return ('<button class="popupBtn btn btn-xs btn-primary" ' +
                        'data-url="${ctx}/unitTeamPlan_posts?id={0}&type=0"><i class="fa fa-search"></i> 查看({1})</button>')
                        .format(rowObject.id, cellvalue.split(",").length);
             }},
            {label: '行政副职', name: 'vicePosts', formatter: function (cellvalue, options, rowObject) {
                   if(cellvalue==undefined) return '--'
                    return ('<button class="popupBtn btn btn-xs btn-primary" ' +
                        'data-url="${ctx}/unitTeamPlan_posts?id={0}&type=1"><i class="fa fa-search"></i> 查看({1})</button>')
                        .format(rowObject.id, cellvalue.split(",").length);
             }},
            {label: '备注', name: 'remark', align: 'left', width: 300}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid_plan", "jqGridPager_plan");
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$.register.date($('.date-picker'));
</script>