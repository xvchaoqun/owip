<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<div class="widget-box transparent">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:;" class="hideView btn btn-xs btn-success">
                <i class="ace-icon fa fa-backward"></i>
                返回</a>
        </h4>
        <div class="widget-toolbar no-border">
            <ul class="nav nav-tabs">
                <li class="active">
                    <a href="javascript:;">${branch.name}-党小组</a>
                </li>
            </ul>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main padding-4">
            <shiro:hasPermission name="branchGroup:edit">
                <button class="popupBtn btn btn-info btn-sm"
                        data-url="${ctx}/branchGroup_au?branchId=${branch.id}"><i class="fa fa-plus"></i>
                    添加</button>
                <button class="jqOpenViewBtn btn btn-primary btn-sm"
                        data-url="${ctx}/branchGroup_au"
                        data-grid-id="#jqGrid2"><i class="fa fa-edit"></i>
                    修改</button>
            </shiro:hasPermission>
            <a class="jqExportBtn btn btn-success btn-sm tooltip-success"
               data-url="${ctx}/branchGroup_data?branchId=${branch.id}"
               data-grid-id="#jqGrid2"
               data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                <i class="fa fa-download"></i> 导出</a>
            <shiro:hasPermission name="branchGroup:del">
                <button data-url="${ctx}/branchGroup_batchDel"
                        data-title="删除"
                        data-msg="确定删除这{0}条数据？"
                        data-grid-id="#jqGrid2"
                        class="jqBatchBtn btn btn-danger btn-sm"><i class="fa fa-trash"></i>
                    删除</button>
            </shiro:hasPermission>
        </div>

        <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
            <div class="widget-header">
                <h4 class="widget-title">搜索</h4><span class="widget-note">${note_searchbar}</span>
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
                            <label>小组名称</label>
                            <input class="form-control search-query" name="name" type="text" value="${param.name}"
                                   placeholder="请输入小组名称">
                        </div>

                        <div class="form-group">
                            <label>创建人</label>
                            <input class="form-control search-query" name="userId" type="text" value="${param.userId}"
                                   placeholder="请输入创建人">
                        </div>

                        <div class="clearfix form-actions center">
                            <button class="jqSearchBtn btn btn-default btn-sm"
                                    data-url="${ctx}/branchGroup"
                                    data-target="#page-content"
                                    data-form="#searchForm"><i class="fa fa-search"></i>
                                查找</button>
                            <c:if test="${_query}">&nbsp;
                                <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                        data-url="${ctx}/branchGroup"
                                        data-target="#page-content"><i class="fa fa-reply"></i>
                                    重置</button>
                                </c:if>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div class="space-4"></div>
        <table id="jqGrid2" class="jqGrid2 table-striped"></table>
        <div id="jqGridPager2"></div>
    </div>
    <div id="body-content-view"></div>
</div>
<script>
    $("#jqGrid2").jqGrid({
        rownumbers:true,
        pager: "jqGridPager2",
        url: '${ctx}/branchGroup_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
                { label: '小组名称',name: 'name',width: 200},
                { label: '排序', width: 85, formatter: $.jgrid.formatter.sortOrder,
                formatoptions:{grid:'#jqGrid2',url:'${ctx}/branchGroup_changeOrder'}
                },
                { label: '小组成员', formatter: function (cellvalue, options, rowObject) {
                    return ('<button class="popupBtn btn btn-success btn-xs"' +
                        'data-url="${ctx}/branchGroupMember?groupId={0}">' +
                        '<i class="fa fa-search"></i> 查看({1})</button>')
                        .format(rowObject.id, rowObject.countMember == undefined?"0":rowObject.countMember);}},
                { label: '创建人',name: 'user.realname'},
                { label: '创建时间',name: 'createDate',width: 150,
                    formatter: $.jgrid.formatter.date,
                    formatoptions: {srcformat: 'Y.m.d H:i', newformat: 'Y.m.d H:i'}}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$.register.date($('.date-picker'));
</script>