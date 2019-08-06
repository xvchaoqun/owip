<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.name ||not empty param.year ||not empty param.psIds || not empty param.code || not empty param.sort}"/>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="psTask:edit">
                    <button class="popupBtn btn btn-info btn-sm"
                            data-url="${ctx}/ps/psTask_au">
                        <i class="fa fa-plus"></i> 添加</button>
                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/ps/psTask_au"
                       data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                        修改</button>
                </shiro:hasPermission>
                <shiro:hasPermission name="psTask:del">
                    <button data-url="${ctx}/ps/psTask_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
                <%--<button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-url="${ctx}/ps/psTask_data"
                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出</button>--%>
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
                            <input class="form-control search-query" name="name" type="text" value="${param.name}"
                                   placeholder="请输入名称">
                        </div>
                        <div class="form-group">
                            <label>年度</label>
                            <input class="form-control search-query" name="year" type="text" value="${param.year}"
                                   placeholder="请输入年度">
                        </div>
                        <div class="form-group">
                            <label>发布范围</label>
                            <input class="form-control search-query" name="psIds" type="text" value="${param.psIds}"
                                   placeholder="请输入发布范围">
                        </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                   data-url="${ctx}/ps/psTask"
                                   data-target="#page-content"
                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/ps/psTask"
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
        url: '${ctx}/ps/psTask_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
                { label: '名称',name: 'name', width: 250, align: 'left'},
                { label: '年度',name: 'year',width: 80},
                { label: '发布范围',name: 'psIds',formatter: function (cellvalue, options, rowObject) {
                    var count = 0;
                    if ($.trim(rowObject.psIds)!=''){
                        count = rowObject.psIds.split(',').length;
                    }
                     return ('<button class="popupBtn btn {2} btn-xs" data-width="500" data-callback="_reload"' +
                            'data-url="${ctx}/ps/psTaskScope_au?taskId={0}"><i class="fa fa-{3}"></i> {1}</button>')
                                .format(rowObject.id, count==0?"添加":"编辑("+count+")",
                                    count==0?"btn-info":"btn-success", count == 0?"plus":"edit");
                    }},
                {label: '发布时间', name: 'releaseDate', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y-m-d'}},
                { label: '附件',name: 'files',formatter: function (cellvalue, options, rowObject) {
                        return '<button class="popupBtn btn btn-warning btn-xs" data-width="500" data-callback="_reload"' +
                            'data-url="${ctx}/ps/psTaskFiles?taskId={0}"><i class="fa fa-search"></i> 附件{1}</button>'
                                .format(rowObject.id, rowObject.countFile>0?"("+rowObject.countFile+")":"")
                    }},
                { label: '是否发布',name: 'isPublish', width: 80, formatter: $.jgrid.formatter.TRUEFALSE},
                { label: '备注',name: 'remark', width: 250}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$.register.date($('.date-picker'));
</script>