<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="myTableDiv multi-row-head-table"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.code ||not empty param.name
            ||not empty param.typeId || not empty param.sort}"/>

            <jsp:include page="menu.jsp"/>
            <div class="space-4"></div>
            <div class="jqgrid-vertical-offset buttons">

                <shiro:hasPermission name="dispatchWorkFile:list">
                    <button class="popupBtn btn btn-warning btn-sm " data-width="750"
                            data-url="${ctx}/cadreCompanyFiles?type=${module==1?1:0}"><i class="fa fa-files-o"></i>
                        ${module==1?'校领导兼职管理文件':'中层干部兼职管理文件'}
                    </button>
                </shiro:hasPermission>

                <%--<shiro:hasPermission name="cadreCompany:edit">
                    <c:if test="${cls==1}">
                        <button class="popupBtn btn btn-success btn-sm"
                                data-url="${ctx}/cadreCompany_au?cadreId=${param.cadreId}"><i class="fa fa-plus"></i>
                            添加</button>
                    </c:if>
                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                            data-url="${ctx}/cadreCompany_au"
                            data-grid-id="#jqGrid_cadreCompany"
                            data-querystr="&cadreId=${param.cadreId}"><i class="fa fa-edit"></i>
                        修改</button>
                </shiro:hasPermission>--%>

                <%--<shiro:hasPermission name="cadreCompany:finish">
                    <c:if test="${cls==1}">
                        <button class="jqOpenViewBtn btn btn-warning btn-sm"
                                data-url="${ctx}/cadreCompany_finish"
                                data-grid-id="#jqGrid_cadreCompany"
                                data-querystr="&cadreId=${param.cadreId}"><i class="fa fa-dot-circle-o"></i>
                            兼职结束</button>
                    </c:if>
                    <c:if test="${cls==2}">
                        <button class="jqItemBtn btn btn-success btn-sm"
                                data-msg="确认返回正在兼职？"
                                data-url="${ctx}/cadreCompany_finish"
                                data-grid-id="#jqGrid_cadreCompany"
                                data-callback="_reload2"
                                data-querystr="&cadreId=${param.cadreId}&isFinished=0"><i class="fa fa-reply"></i>
                            返回正在兼职</button>
                    </c:if>
                </shiro:hasPermission>--%>

                <%--<shiro:hasPermission name="cadreCompany:del">
                    <button data-url="${ctx}/cadreCompany_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid_cadreCompany"
                            data-querystr="cadreId=${param.cadreId}"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-times"></i> 删除
                    </button>
                </shiro:hasPermission>--%>

                <c:if test="${cls==1}">
                <c:if test="${module==1}">
                <div class="btn-group">
                    <button data-toggle="dropdown"
                            data-rel="tooltip" data-placement="top" data-html="true"
                            title="<div style='width:180px'>导出选中记录或所有搜索结果</div>"
                            class="btn btn-info btn-sm dropdown-toggle tooltip-info">
                        <i class="fa fa-download"></i> 导出  <span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu dropdown-success" role="menu">
                        <li>
                            <a href="javascript:;" class="jqExportBtn"
                               data-need-id="false" data-url="${ctx}/cadreCompany_data"
                               data-querystr="module=${module}&cadreStatus=<%=CadreConstants.CADRE_STATUS_LEADER%>">
                                <i class="fa fa-file-excel-o"></i> 导出现任校领导</a>
                        </li>
                        <li>
                            <a href="javascript:;" class="jqExportBtn"
                               data-need-id="false" data-url="${ctx}/cadreCompany_data"
                               data-querystr="module=${module}&cadreStatus=<%=CadreConstants.CADRE_STATUS_LEADER_LEAVE%>">
                                <i class="fa fa-file-excel-o"></i> 导出离任校领导</a>
                        </li>
                    </ul>
                </div>
                </c:if>
                <c:if test="${module==2}">
                <a class="jqExportBtn btn btn-info btn-sm tooltip-success"
                   data-url="${ctx}/cadreCompany_data"
                   data-querystr="module=${module}"
                   data-rel="tooltip" data-placement="top" title="导出当前搜索的全部结果">
                    <i class="fa fa-download"></i> 导出</a>
                </c:if>
                </c:if>
            </div>
            <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
                <div class="widget-header">
                    <h4 class="widget-title">搜索</h4>
                    <div class="widget-toolbar">
                        <a href="javascript:;" data-action="collapse">
                            <i class="ace-icon fa fa-chevron-${_query?'up':'down'}"></i>
                        </a>
                    </div>
                </div>
                <div class="widget-body">
                    <div class="widget-main no-padding">
                        <form class="form-inline search-form" id="searchForm">

                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query || not empty param.sort}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                            data-querystr="module=${module}&cls=${cls}">
                                        <i class="fa fa-reply"></i> 重置
                                    </button>
                                </c:if>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <%--<div class="space-4"></div>--%>
            <table id="jqGrid" class="jqGrid"></table>
            <div id="jqGridPager"></div>
        </div>
        <div id="body-content-view"></div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/cadre/cadreCompany/colModels.jsp?type=list"/>
<script>
    function _reload2(){
        $("#jqGrid").trigger("reloadGrid");
    }
    $("#jqGrid").jqGrid({
        //forceFit:true,
        pager: "#jqGridPager",
        url: '${ctx}/cadreCompany_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: colModels_cadreCompany
    }).jqGrid("setFrozenColumns");

    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>