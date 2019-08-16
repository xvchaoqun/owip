<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.quarter || not empty param.code || not empty param.sort}"/>
            <div class="jqgrid-vertical-offset buttons">

                    <a class="btn btn-info btn-sm"
                            href="${ctx}/pmQuarter_au?partyId=17&type=2">
                        <i class="fa fa-plus"></i> 添加</a>

                <shiro:hasPermission name="pmQuarter:del">
                    <button data-url="${ctx}/pmQuarter_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
                <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-url="${ctx}/pmQuarter_data"
                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出</button>
            </div>
            <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
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
                            <label>季度</label>
                            <input class="form-control search-query" name="quarter" type="text" value="${param.quarter}"
                                   placeholder="请输入季度">
                        </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                   data-url="${ctx}/pmQuarter"
                                   data-target="#page-content"
                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/pmQuarter"
                                            data-target="#page-content">
                                        <i class="fa fa-reply"></i> 重置
                                    </button>
                                </c:if>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="space-4"></div>
            <table id="jqGrid" class="jqGrid table-striped"></table>
            <div id="jqGridPager"></div>
        </div>
        <div id="body-content-view"></div>
    </div>
</div>
<script>
    $("#jqGrid").jqGrid({
        rownumbers:true,
        url: '${ctx}/pmQuarter_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '详情', name: '_detail', width: 80, formatter:function(cellvalue, options, rowObject){

                    return '<button class="openView btn btn-success btn-xs" data-url="${ctx}/pmQuarterBranch?id={0}&partyId={2}"><i class="fa fa-search"></i> {1}</button>'
                        .format(rowObject.id, '详情',rowObject.partyId);
                },frozen:true },
                { label: '年度',name: 'year',align:'left'},
                { label: '季度',name: 'quarter',align:'left'},
                { label: '数量(分党委或支部数量)',name: 'num', width:200,align:'left',
                     formatter: function (cellvalue, options, rowObject) {
                        return (rowObject.isFinished)?cellvalue:rowObject.f.branchCount;}
                },
                { label: '应召开党员大会支部数量',name: 'dueNum',width:200,align:'left',
                    formatter: function (cellvalue, options, rowObject) {
                        return (rowObject.isFinished)?cellvalue:rowObject.f.cludeCount;}
                },
                { label: '已召开党员大会数量',name: 'finishNum',width:200, align:'left',
                    formatter: function (cellvalue, options, rowObject) {
                        return (rowObject.isFinished)?cellvalue:rowObject.f.isExcludeCount;}
                },
                { label: '备注',name: 'remark',width:200, align:'left'}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    //$.register.date($('.date-picker'));
</script>