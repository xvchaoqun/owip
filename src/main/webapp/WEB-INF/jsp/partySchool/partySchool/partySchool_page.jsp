<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <div class="tabbable">
                <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                    <li class="<c:if test="${!isHistory}">active</c:if>">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/partySchool?isHistory=0"><i
                                class="fa fa-circle-o-notch fa-spin"></i> 正在运转</a>
                    </li>
                    <li class="<c:if test="${isHistory}">active</c:if>">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/partySchool?isHistory=1"><i
                                class="fa fa-history"></i> 历史</a>
                    </li>
                </ul>
                <div class="tab-content">
                    <div class="tab-pane in active">
                        <c:set var="_query"
                               value="${not empty param.name || not empty param.code || not empty param.sort}"/>
                        <div class="jqgrid-vertical-offset buttons">
                            <shiro:hasPermission name="partySchool:edit">
                                <button class="popupBtn btn btn-info btn-sm"
                                        data-url="${ctx}/partySchool_au">
                                    <i class="fa fa-plus"></i> 添加
                                </button>
                                <button class="jqOpenViewBtn btn btn-primary btn-sm"
                                        data-url="${ctx}/partySchool_au"
                                        data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                                    修改
                                </button>
                            </shiro:hasPermission>
                            <c:if test="${!isHistory}">
                                <shiro:hasPermission name="partySchool:history">
                                    <button class="jqBatchBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/partySchool_history?isHistory=1" data-title="转移"
                                            data-msg="确定将这{0}个二级党校转移到历史记录吗？">
                                        <i class="fa fa-recycle"></i> 转移
                                    </button>
                                </shiro:hasPermission>
                            </c:if>
                            <shiro:hasPermission name="partySchool:del">
                                <button data-url="${ctx}/partySchool_batchDel"
                                        data-title="删除"
                                        data-msg="确定删除这{0}条数据？"
                                        data-grid-id="#jqGrid"
                                        class="jqBatchBtn btn btn-danger btn-sm">
                                    <i class="fa fa-trash"></i> 删除
                                </button>
                            </shiro:hasPermission>
                            <%--<button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                                    data-url="${ctx}/partySchool_data"
                                    data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                                <i class="fa fa-download"></i> 导出
                            </button>--%>
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
                                            <label>二级党校名称</label>
                                            <input class="form-control search-query" name="name" type="text"
                                                   value="${param.name}"
                                                   placeholder="请输入二级党校名称">
                                        </div>
                                        <div class="clearfix form-actions center">
                                            <a class="jqSearchBtn btn btn-default btn-sm"
                                               data-url="${ctx}/partySchool?isHistory=${isHistory}"
                                               data-target="#page-content"
                                               data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                            <c:if test="${_query}">&nbsp;
                                                <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                        data-url="${ctx}/partySchool?isHistory=${isHistory}"
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
                </div>
            </div>
        </div>
        <div id="body-content-view"></div>
    </div>
</div>
<script>
    $("#jqGrid").jqGrid({
        rownumbers:true,
        url: '${ctx}/partySchool_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '二级党校名称', name: 'name', width:300, align:'left'},
            {label: '设立日期', name: 'foundDate', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y-m-d'}},
            <c:if test="${!_query}">
            { label:'排序', formatter: $.jgrid.formatter.sortOrder,
                formatoptions:{url:'${ctx}/partySchool_changeOrder'},frozen:true },
            </c:if>
            { label: '备注', align:'left', name: 'remark', width: 500 }
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>