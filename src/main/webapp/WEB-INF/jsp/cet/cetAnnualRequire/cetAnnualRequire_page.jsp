<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<c:set var="_query" value="${not empty param.adminLevel || not empty param.code || not empty param.sort}"/>
<div class="jqgrid-vertical-offset buttons">

</div>
<%--<div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
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
                    <label>行政级别</label>
                    <input class="form-control search-query" name="adminLevel" type="text" value="${param.adminLevel}"
                           placeholder="请输入行政级别">
                </div>
                <div class="clearfix form-actions center">
                    <a class="jqSearchBtn btn btn-default btn-sm"
                       data-url="${ctx}/cet/cetAnnualRequire"
                       data-target="#page-content"
                       data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                    <c:if test="${_query}">&nbsp;
                        <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                data-url="${ctx}/cet/cetAnnualRequire"
                                data-target="#page-content">
                            <i class="fa fa-reply"></i> 重置
                        </button>
                    </c:if>
                </div>
            </form>
        </div>
    </div>
</div>--%>
<div class="space-4"></div>
<table id="jqGrid2" class="jqGrid2 table-striped"></table>
<div id="jqGridPager2"></div>
<script>
    $("#jqGrid2").jqGrid({
        pager:"#jqGridPager2",
        rownumbers: true,
        url: '${ctx}/cet/cetAnnualRequire_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '行政级别', name: 'adminLevel', formatter: $.jgrid.formatter.MetaType},
            {label: '年度学习任务', name: 'period'},
            {label: '党校专题<br/>培训学时上限', name: 'maxSpecialPeriod'},
            {label: '党校日常<br/>培训学时上限', name: 'maxDailyPeriod'},
            {label: '二级党校/党委<br/>培训学时上限', name: 'maxPartyPeriod'},
            {label: '二级单位<br/>培训学时上限', name: 'maxUnitPeriod'},
            {label: '上级调训<br/>学时上限', name: 'maxUpperPeriod'},
            {label: '影响人数', name: 'relateCount'},
            {label: '备注', name: 'remark', width:250},
            {label: '操作时间', name: 'opTime', width:150},
            {label: '操作人', name: 'opUser.realname'},
            {label: 'ip', name: 'ip'}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$.register.date($('.date-picker'));
</script>