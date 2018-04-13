<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="widget-box transparent">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:" class="openView btn btn-xs btn-success"
               data-url="${ctx}/cet/cetProject_detail?projectId=${cetProjectPlan.projectId}">
                <i class="ace-icon fa fa-backward"></i> 返回</a>
        </h4>
        <span class="text text-info bolder" style="cursor: auto;padding-left: 20px;">
                    ${CET_PROJECT_PLAN_TYPE_MAP.get(cetProjectPlan.type)}
                    （${cm:formatDate(cetProjectPlan.startDate, "yyyy-MM-dd")} ~ ${cm:formatDate(cetProjectPlan.endDate, "yyyy-MM-dd")}，${cetProject.name}）
        </span>

        <div class="widget-toolbar no-border">
            <ul class="nav nav-tabs" id="detail-ul">
                <li class="active">
                    <a href="javascript:;">研讨会列表</a>
                </li>
            </ul>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main padding-12 no-padding-left no-padding-right no-padding-bottom">
            <c:set var="_query"
                   value="${not empty param.name || not empty param.code || not empty param.sort}"/>
            <div class="jqgrid-vertical-offset buttons">
                    <button class="popupBtn btn btn-info btn-sm"
                            data-url="${ctx}/cet/cetDiscuss_au?planId=${param.planId}">
                        <i class="fa fa-plus"></i> 添加
                    </button>
                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                            data-url="${ctx}/cet/cetDiscuss_au"
                            data-grid-id="#jqGrid2"><i class="fa fa-edit"></i>
                        修改
                    </button>
                    <button data-url="${ctx}/cet/cetDiscuss_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid2"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
            </div>
            <div class="space-4"></div>
            <table id="jqGrid2" class="jqGrid2 table-striped"></table>
            <div id="jqGridPager2"></div>
        </div>
        <div id="body-content-view"></div>
    </div>
</div>
<script>
    $("#jqGrid2").jqGrid({
        rownumbers:true,
        pager: "jqGridPager2",
        url: '${ctx}/cet/cetDiscuss_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {
                label: '分组详情', name: '_detail', width: '80', formatter: function (cellvalue, options, rowObject) {
                return ('<button class="openView btn btn-success btn-xs" ' +
                'data-url="${ctx}/cet/cetDiscussGroup?discussId={0}"><i class="fa fa-search"></i> 详情</button>')
                        .format(rowObject.id);
            }, frozen: true},
            {label: '开始日期', name: 'startDate', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}, frozen: true},
            {label: '结束日期', name: 'endDate', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}, frozen: true},
            {
                label: '排序', align: 'center',formatter: $.jgrid.formatter.sortOrder,
                formatoptions: {grid:'#jqGrid2', url: "${ctx}/cet/cetDiscuss_changeOrder"}, frozen: true
            },
            {label: '研讨会名称', name: 'name', width:300},
            {label: '负责单位', name: 'unitType', formatter: function (cellvalue, options, rowObject) {
                if(cellvalue==undefined) return '-'
                return _cMap.CET_DISCUSS_UNIT_TYPE_MAP[cellvalue]
            } },
            {label: '学时', name: 'period'},
            {label: '备注', name: 'remark', width:300}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");
    //$('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
</script>