<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="space-4"></div>
<div class="jqgrid-vertical-offset buttons">
    <shiro:hasPermission name="cadreCompany:edit">
        <a class="popupBtn btn btn-success btn-sm"
           data-url="${ctx}/cadreCompany_au?cadreId=${param.cadreId}"><i class="fa fa-plus"></i>
            添加</a>
        <a class="jqOpenViewBtn btn btn-primary btn-sm"
           data-url="${ctx}/cadreCompany_au"
           data-grid-id="#jqGrid_cadreCompany"
           data-querystr="&cadreId=${param.cadreId}"><i class="fa fa-edit"></i>
            修改</a>
    </shiro:hasPermission>
    <shiro:hasPermission name="cadreCompany:del">
        <button data-url="${ctx}/cadreCompany_batchDel"
                data-title="删除"
                data-msg="确定删除这{0}条数据？"
                data-grid-id="#jqGrid_cadreCompany"
                class="jqBatchBtn btn btn-danger btn-sm">
            <i class="fa fa-times"></i> 删除
        </button>
    </shiro:hasPermission>
</div>
<div class="space-4"></div>
<table id="jqGrid_cadreCompany" class="jqGrid2"></table>
<div id="jqGridPager_cadreCompany"></div>
<script>

    $("#jqGrid_cadreCompany").jqGrid({
        ondblClickRow: function () {
        },
        pager: "#jqGridPager_cadreCompany",
        url: '${ctx}/cadreCompany_data?${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '兼职起始时间', name: 'startTime', width: 120, formatter: 'date', formatoptions: {newformat: 'Y.m'},frozen:true },
            {label: '兼职单位及职务', name: 'unit', width: 350},
            {label: '报批单位', name: 'reportUnit', width: 280},
            {label: '批复文件', name: 'paper', width: 250,
                formatter: function (cellvalue, options, rowObject) {
                    if(rowObject.paper==undefined) return '-';
                    return '<a href="${ctx}/attach/download?path={0}&filename={1}">{1}</a>'
                            .format(rowObject.paper,rowObject.paperFilename);
                }},
            {label: '备注', name: 'remark', width: 350}
        ]
    }).on("initGrid", function () {
        $(window).triggerHandler('resize.jqGrid2');
    });

    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>