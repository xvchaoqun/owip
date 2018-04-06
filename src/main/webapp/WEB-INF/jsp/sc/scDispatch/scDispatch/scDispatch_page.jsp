<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12 rownumbers">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/sc/scDispatch"
             data-url-export="${ctx}/sc/scDispatch_data"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query"
                   value="${not empty param.year ||not empty param.dispatchTypeId || not empty param.code || not empty param.sort}"/>
            <div class="tabbable">
                <jsp:include page="/WEB-INF/jsp/dispatch/dispatch_menu.jsp"/>
                <div class="tab-content">
                    <div id="home4" class="tab-pane in active">
                        <div class="jqgrid-vertical-offset buttons">
                            <shiro:hasPermission name="scDispatch:edit">
                                <a class="openView btn btn-info btn-sm" data-url="${ctx}/sc/scDispatch_au"><i
                                        class="fa fa-plus"></i> 起草任免文件</a>
                                <a class="jqOpenViewBtn btn btn-primary btn-sm"
                                   data-url="${ctx}/sc/scDispatch_au"
                                   data-grid-id="#jqGrid"
                                   data-open-by="page"
                                   ><i class="fa fa-edit"></i>
                                    修改信息</a>
                            </shiro:hasPermission>
                            <button class="jqLinkItemBtn btn btn-success btn-sm" type="button"
                                    data-url="${ctx}/sc/scDispatch_exportSign"
                                    data-id-name="dispatchId">
                                <i class="ace-icon fa fa-file-excel-o bigger-110"></i>
                                生成签发单
                            </button>
                            <button class="jqLinkItemBtn btn btn-warning btn-sm" type="button"
                                    data-url="${ctx}/sc/scDispatchUser_export" data-id-name="dispatchId">
                                <i class="fa fa-download"></i> 导出任免对象
                            </button>
                            <shiro:hasPermission name="scDispatch:del">
                                <button data-url="${ctx}/sc/scDispatch_batchDel"
                                        data-title="删除"
                                        data-msg="确定删除这{0}条数据？"
                                        data-grid-id="#jqGrid"
                                        class="jqBatchBtn btn btn-danger btn-sm">
                                    <i class="fa fa-trash"></i> 删除
                                </button>
                            </shiro:hasPermission>
                           <%-- <a class="jqExportBtn btn btn-success btn-sm tooltip-success"
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
                                            <label>年份</label>

                                            <div class="input-group" style="width: 150px">
                                                <input required class="form-control date-picker" placeholder="请选择年份"
                                                       name="year"
                                                       type="text"
                                                       data-date-format="yyyy" data-date-min-view-mode="2"
                                                       value="${param.year}"/>
                                                <span class="input-group-addon"> <i
                                                        class="fa fa-calendar bigger-110"></i></span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label>发文类型</label>
                                            <c:set var="dispatchType"
                                                   value="${dispatchTypeMap.get(dispatchTypeId)}"/>
                                            <select data-rel="select2-ajax"
                                                    data-ajax-url="${ctx}/dispatchType_selects"
                                                    name="dispatchTypeId" data-placeholder="请选择发文类型">
                                                <option value="${dispatchType.id}">${dispatchType.name}</option>
                                            </select>
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
        <div id="body-content-view"></div>
    </div>
</div>
<jsp:include page="scDispatch_colModel.jsp?type=admin"/>
<script>
    $.register.date($('.date-picker'));
    $.register.dispatchType_select($('#searchForm select[name=dispatchTypeId]'), $("#searchForm input[name=year]"));

    // 序号、 年度、 发文类型、 发文号、 党委常委会日期、 起草日期、 任命人数、 免职人数、 文件签发稿、 签发单、 正式签发、 备注
    $("#jqGrid").jqGrid({
        rownumbers:true,
        url: '${ctx}/sc/scDispatch_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: colModel
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>