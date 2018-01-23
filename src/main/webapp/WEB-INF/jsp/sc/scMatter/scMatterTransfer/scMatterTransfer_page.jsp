<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/sc/scMatterTransfer"
             data-url-export="${ctx}/sc/scMatterTransfer_data"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.userId || not empty param.code || not empty param.sort}"/>
            <div class="tabbable">
                <jsp:include page="../scMatterAccess/menu.jsp"/>
                <div class="tab-content">
                    <div id="home4" class="tab-pane in active">
                        <div class="jqgrid-vertical-offset buttons">
                            <shiro:hasPermission name="scMatterTransfer:edit">
                                <a class="popupBtn btn btn-info btn-sm" data-url="${ctx}/sc/scMatterTransfer_au"><i
                                        class="fa fa-plus"></i> 添加</a>
                                <a class="jqOpenViewBtn btn btn-primary btn-sm"
                                   data-url="${ctx}/sc/scMatterTransfer_au"
                                   data-grid-id="#jqGrid"
                                   data-querystr="&"><i class="fa fa-edit"></i>
                                    修改</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="scMatterTransfer:del">
                                <button data-url="${ctx}/sc/scMatterTransfer_batchDel"
                                        data-title="删除"
                                        data-msg="确定删除这{0}条数据？"
                                        data-grid-id="#jqGrid"
                                        class="jqBatchBtn btn btn-danger btn-sm">
                                    <i class="fa fa-trash"></i> 删除
                                </button>
                            </shiro:hasPermission>
                            <%--<a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                               data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                                <i class="fa fa-download"></i> 导出</a>--%>
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
                                        <input type="hidden" name="cls" value="${cls}">

                                        <div class="form-group">
                                            <label>移交对象</label>
                                            <input class="form-control search-query" name="userId" type="text"
                                                   value="${param.userId}"
                                                   placeholder="请输入移交对象">
                                        </div>
                                        <div class="clearfix form-actions center">
                                            <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i>
                                                查找</a>

                                            <c:if test="${_query}">&nbsp;
                                                <button type="button" class="resetBtn btn btn-warning btn-sm"
                                                        data-querystr="cls=${cls}">
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
                </div>
            </div>
        </div>
        <div id="item-content"></div>
    </div>
</div>
<script>
    $("#jqGrid").jqGrid({
        url: '${ctx}/sc/scMatterTransfer_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '移交对象', name: 'user.realname'},
            {label: '移交日期', name: 'transferDate', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {
                label: '移交填报记录', name: '_items', formatter: function (cellvalue, options, rowObject) {
                return ('<button class="popupBtn btn btn-link btn-xs" data-width="900" ' +
                'data-url="${ctx}/sc/scMatterTransfer_items?transferId={0}">编辑</button>')
                        .format(rowObject.id);
            }
            },
            {label: '移交地点', name: 'location'},
            {label: '移交原因', name: 'reason'}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>