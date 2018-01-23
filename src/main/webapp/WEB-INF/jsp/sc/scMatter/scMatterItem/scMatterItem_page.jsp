<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/sc/scMatterItem"
             data-url-export="${ctx}/sc/scMatterItem_data"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.matterId || not empty param.code || not empty param.sort}"/>
            <div class="tabbable">
                <jsp:include page="../scMatter/menu.jsp"/>
                <div class="tab-content">
                    <div id="home4" class="tab-pane in active">
                        <div class="jqgrid-vertical-offset buttons">
                            <a class="jqOpenViewBtn btn btn-warning btn-sm"
                               data-url="${ctx}/sc/scMatter_au"
                               data-grid-id="#jqGrid"
                               data-querystr="&"><i class="fa fa-send"></i>
                                通知填报</a>
                            <a class="jqOpenViewBtn btn btn-warning btn-sm"
                               data-url="${ctx}/sc/scMatter_au"
                               data-grid-id="#jqGrid"
                               data-querystr="&"><i class="fa fa-send"></i>
                                催交短信</a>
                            <a class="jqOpenViewBtn btn btn-success btn-sm"
                               data-url="${ctx}/sc/scMatterAccess"
                               data-grid-id="#jqGrid"
                               data-width="900"
                               data-id-name="matterItemId"
                               data-querystr="&cls=-1"><i class="fa fa-search"></i>
                                调阅记录</a>
                            <shiro:hasPermission name="scMatterItem:del">
                                <button data-url="${ctx}/sc/scMatterItem_batchDel"
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
                                            <label>事项</label>
                                            <input class="form-control search-query" name="matterId" type="text"
                                                   value="${param.matterId}"
                                                   placeholder="请输入事项">
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
<jsp:include page="scMatterItem_colModel.jsp?type=admin"/>
<script>
    $("#jqGrid").jqGrid({
        url: '${ctx}/sc/scMatterItem_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: colModel
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>