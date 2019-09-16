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
        <span class="text text-info bolder" style="cursor: auto;padding-left: 20px;">
            所属分类：${topLayerType.name}
        </span>
    </div>
    <div class="widget-body">
        <c:set var="_query" value="${not empty param.name || not empty param.code || not empty param.sort}"/>
        <div class="tabbable">
            <div class="tab-content">
                <div id="home4" class="tab-pane in active">
                    <div class="jqgrid-vertical-offset buttons">
                        <shiro:hasPermission name="layerType:edit">
                            <a class="popupBtn btn btn-info btn-sm" data-url="${ctx}/layerType_au?fid=${param.fid}"><i
                                    class="fa fa-plus"></i> 添加</a>
                            <a class="jqOpenViewBtn btn btn-primary btn-sm"
                               data-url="${ctx}/layerType_au"
                               data-grid-id="#jqGrid2"><i class="fa fa-edit"></i>
                                修改</a>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="layerType:del">
                            <button data-url="${ctx}/layerType_batchDel"
                                    data-title="删除"
                                    data-msg="确定删除这{0}条数据？(相关数据均删除，且数据不可恢复，请谨慎操作)"
                                    data-grid-id="#jqGrid2"
                                    class="jqBatchBtn btn btn-danger btn-sm">
                                <i class="fa fa-trash"></i> 删除
                            </button>
                        </shiro:hasPermission>
                        <%--<a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                           data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                            <i class="fa fa-file-excel-o"></i> 导出</a>--%>
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
                                        <label>名称</label>
                                        <input class="form-control search-query" name="name" type="text"
                                               value="${param.name}"
                                               placeholder="请输入名称">
                                    </div>
                                    <div class="clearfix form-actions center">
                                        <a class="jqSearchBtn btn btn-default btn-sm"
                                           data-url="${ctx}/layerType_detail?fid=${param.fid}"
                                           data-target="#body-content-view"
                                           data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                        <c:if test="${_query}">&nbsp;
                                            <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                    data-url="${ctx}/layerType_detail?fid=${param.fid}"
                                                    data-target="#body-content-view">
                                                <i class="fa fa-reply"></i> 重置
                                            </button>
                                        </c:if>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                    <div class="space-4"></div>
                    <table id="jqGrid2" class="jqGrid2 table-striped" data-height-reduce="30"></table>
                    <div id="jqGridPager2"></div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    $("#jqGrid2").jqGrid({
        rownumbers:true,
        pager:"#jqGridPager2",
        url: '${ctx}/layerType_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '编号', name: 'code', frozen: true},
            {label: '${topLayerType.firstLevel}', name: 'name', width: 250, frozen: true},
            <c:if test="${!_query}">
            {
                  label: '排序',formatter: $.jgrid.formatter.sortOrder,
                  formatoptions: {url: "${ctx}/layerType_changeOrder", grid:'#jqGrid2'}, frozen:true
              },
            </c:if>
            {
                label: '${topLayerType.secondLevel}', name: '_detail', formatter: function (cellvalue, options, rowObject) {
                return '<button class="popupBtn btn btn-primary btn-xs"' +
                        'data-url="${ctx}/layerType_detail?fid={0}&popup=1"><i class="fa fa-search"></i> 编辑({1})</button>'
                                .format(rowObject.id, rowObject.num)
            }},
            {label: '备注', name: 'remark', width: 250}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>