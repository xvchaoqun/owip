<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/cet/constants.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" class="myTableDiv"
                 data-url-page="${ctx}/cet/cetProjectType"
                 data-url-export="${ctx}/cet/cetProjectType_data"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.name || not empty param.code}"/>
                <div class="tabbable">
                    <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                        <li class="<c:if test="${cls==1}">active</c:if>">
                            <a href="javascript:;" class="loadPage" data-url="${ctx}/cet/cetProjectType?cls=1"><i class="fa fa-list"></i> 党校专题培训</a>
                        </li>
                        <li class="<c:if test="${cls==2}">active</c:if>">
                            <a href="javascript:;" class="loadPage" data-url="${ctx}/cet/cetProjectType?cls=2"><i class="fa fa-list"></i> 党校日常培训</a>
                        </li>
                        <li class="<c:if test="${cls==3}">active</c:if>">
                            <a href="javascript:;" class="loadPage" data-url="${ctx}/cet/cetProjectType?cls=3"><i class="fa fa-list"></i> 二级党委专题培训</a>
                        </li>
                        <li class="<c:if test="${cls==4}">active</c:if>">
                            <a href="javascript:;" class="loadPage" data-url="${ctx}/cet/cetProjectType?cls=4"><i class="fa fa-list"></i> 二级党委日常培训</a>
                        </li>
                    </ul>
                    <div class="tab-content">
                        <div class="tab-pane in active multi-row-head-table">
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="cetProjectType:edit">
                    <a class="popupBtn btn btn-info btn-sm"  data-url="${ctx}/cet/cetProjectType_au?cls=${cls}"><i class="fa fa-plus"></i> 添加</a>
                    <a class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/cet/cetProjectType_au"
                       data-grid-id="#jqGrid"
                       ><i class="fa fa-edit"></i>
                        修改</a>
                </shiro:hasPermission>
                <shiro:hasPermission name="cetProjectType:del">
                    <button data-url="${ctx}/cet/cetProjectType_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？<br/>（删除后无法恢复，请谨慎操作！！）"
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
                                <label>名称</label>
                                <input class="form-control search-query" name="name" type="text" value="${param.name}"
                                       placeholder="请输入">
                            </div>
                            <c:if test="${_p_cetSupportCert}">
                            <div class="form-group">
                                <label>证书编码</label>
                                <input class="form-control search-query" name="code" type="text" value="${param.code}"
                                       placeholder="请输入">
                            </div>
                            </c:if>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>

                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm">
                                        <i class="fa fa-reply"></i> 重置
                                    </button>
                                </c:if>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="space-4"></div>
            <table id="jqGrid" class="jqGrid table-striped" data-height-reduce="5"></table>
            <div id="jqGridPager"></div>
        </div>
        <div id="body-content-view"></div>
    </div>
</div>
<script>
    $("#jqGrid").jqGrid({
        url: '${ctx}/cet/cetProjectType_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '培训班类型',name: 'name', width:400, align:'left'},
            <c:if test="${_p_cetSupportCert}">
            { label: '证书编码',name: 'code'},
            </c:if>
            {
                label: '排序', index: 'sort', formatter: $.jgrid.formatter.sortOrder,
                formatoptions:{url: "${ctx}/cet/cetProjectType_changeOrder"}
            },
            /*{ label: '课程',name: 'courseNum'},
            { label: '选课人次',name: 'traineeCount'},*/
            { label: '备注',name: 'remark', width:300, align:'left'}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>