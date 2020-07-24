<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
                <c:set var="_query" value="${not empty param.name || not empty param.onlineType||not empty param.postType
                ||not empty param.unitTypes||not empty param.unitId||not empty param.adminLevel}"/>
            <div class="tabbable">
                <jsp:include page="menu.jsp"/>
                <div class="tab-content multi-row-head-table">
                    <div class="tab-pane in active">
                        <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
                            <div class="widget-header">
                                <h4 class="widget-title">搜索</h4>
                                <span class="widget-note">${note_searchbar}</span>
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
                                            <label>推荐职务</label>
                                            <input class="form-control search-query" name="name" type="text" value="${param.name}"
                                                   placeholder="请输入推荐职务">
                                        </div>
                                        <div class="clearfix form-actions center">
                                            <a class="jqSearchBtn btn btn-default btn-sm"
                                               data-url="${ctx}/dr/drOnline?cls=2"
                                               data-target="#page-content"
                                               data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                            <c:if test="${_query}">&nbsp;
                                                <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                        data-url="${ctx}/dr/drOnline?cls=2"
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
        <table id="jqGrid" class="jqGrid table-striped" data-height-reduce="35"></table>
        <div id="jqGridPager"></div>
                    </div>
                </div>
            </div>
        </div>
        <div id="body-content-view"></div>
    </div>
</div>
<script>

    $.register.multiselect($('#searchForm select[name=unitTypes]'), ${cm:toJSONArray(selectUnitTypes)});

    $("#jqGrid").jqGrid({
        pager: "jqGridPager",
        url: '${ctx}/dr/drOnlinePost_data?callback=?&cls=2&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
                { label: '职务名称',name: 'name', width: 350, frozen: true, align:'left'},
                { label: '最多推荐<br/>人数',name: 'headCount',width:75},
                { label: '推荐类型',name: 'drOnline.type', frozen: true, width: 105, formatter: $.jgrid.formatter.MetaType},
                { label: '关联岗位',name: 'unitPost.name', width: 350, align:'left'},
                { label: '岗位编码',name: 'unitPost.code', width: 120},
                { label: '岗位级别',name: 'unitPost.adminLevel', width: 100, formatter: $.jgrid.formatter.MetaType},
                {hidden: true, key: true, name: 'id'}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $.register.date($('.date-picker'));
</script>