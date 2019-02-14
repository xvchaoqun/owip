<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="_query" value="${not empty param.type || not empty param.code || not empty param.sort}"/>
<div class="jqgrid-vertical-offset buttons">
    <shiro:hasPermission name="cetProjectPlan:edit">
        <button class="popupBtn btn btn-info btn-sm"
                data-url="${ctx}/cet/cetProjectPlan_au?projectId=${param.projectId}">
            <i class="fa fa-plus"></i> 添加
        </button>
        <button class="jqOpenViewBtn btn btn-primary btn-sm"
                data-url="${ctx}/cet/cetProjectPlan_au"
                data-grid-id="#jqGrid2"><i class="fa fa-edit"></i>
            修改
        </button>
    </shiro:hasPermission>
    <shiro:hasPermission name="cetProjectPlan:del">
        <button data-url="${ctx}/cet/cetProjectPlan_batchDel"
                data-title="彻底删除"
                data-msg="确定彻底删除这{0}条数据？（该培训方案下的所有数据均将彻底删除，删除后无法恢复，请谨慎操作！）"
                data-grid-id="#jqGrid2"
                class="jqBatchBtn btn btn-danger btn-sm">
            <i class="fa fa-trash"></i> 彻底删除
        </button>
    </shiro:hasPermission>
    <%--<button class="jqExportBtn btn btn-success btn-sm tooltip-success"
            data-url="${ctx}/cet/cetProjectPlan_data"
            data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
        <i class="fa fa-download"></i> 导出
    </button>--%>
</div>
<%--<div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
    <div class="widget-header">
        <h4 class="widget-title">搜索</h4>

        <div class="widget-toolbar">
            <a href="#" data-action="collapse">
                <i class="ace-icon fa fa-chevron-${_query?'up':'down'}"></i>
            </a>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main no-padding">
            <form class="form-inline search-form" id="searchForm">
                <div class="form-group">
                    <label>培训形式</label>
                    <input class="form-control search-query" name="type" type="text" value="${param.type}"
                           placeholder="请输入培训形式">
                </div>
                <div class="clearfix form-actions center">
                    <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>
                    <c:if test="${_query}">&nbsp;
                        <button type="button" class="reloadBtn btn btn-warning btn-sm">
                            <i class="fa fa-reply"></i> 重置
                        </button>
                    </c:if>
                </div>
            </form>
        </div>
    </div>
</div>--%>
<div class="space-4"></div>
<table id="jqGrid2" class="jqGrid2 table-striped"></table>
<div id="jqGridPager2"></div>
<script>
    $("#jqGrid2").jqGrid({
        rownumbers:true,
        pager: "jqGridPager2",
        url: '${ctx}/cet/cetProjectPlan_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '详情',name: '_detail', width: 80, formatter: function (cellvalue, options, rowObject) {
                return ('<button class="openView btn btn-success btn-xs" ' +
                'data-url="${ctx}/cet/cetProjectPlan_detail?planId={0}"><i class="fa fa-search"></i> 详情</button>')
                        .format(rowObject.id);
            }},
            { label: '培训时间',name: 'startDate', width: 200, formatter: function (cellvalue, options, rowObject) {
                return '{0} ~ {1}'.format($.date(rowObject.startDate, "yyyy-MM-dd"), $.date(rowObject.endDate, "yyyy-MM-dd"))
            }, frozen: true},
            {label: '培训形式', name: 'type', width: 180, formatter: function (cellvalue, options, rowObject) {
                return _cMap.CET_PROJECT_PLAN_TYPE_MAP[cellvalue];
            }, frozen: true},
            {label: '培训内容', name: '_summary', width: 80, formatter: function (cellvalue, options, rowObject) {
                var btnStr = "添加";
                var btnCss = "btn-success";
                var iCss = "fa-plus";
                if (rowObject.hasSummary){
                    btnStr = "查看";
                    btnCss = "btn-primary";
                    iCss = "fa-search";
                }
                return ('<button class="popupBtn btn {2} btn-xs" data-width="750" ' +
                'data-url="${ctx}/cet/cetProjectPlan_summary?id={0}"><i class="fa {3}"></i> {1}</button>')
                        .format(rowObject.id, btnStr, btnCss, iCss);
            }},
            {label: '学时', name: 'period'},
            {
                label: '排序', align: 'center', index: 'sort', formatter: $.jgrid.formatter.sortOrder,
                formatoptions:{grid:'#jqGrid2',url: "${ctx}/cet/cetProjectPlan_changeOrder"}
            },
            {label: '备注', name: 'remark', width: 400}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>