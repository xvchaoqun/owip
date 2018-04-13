<#assign TableName=tbn(tablesqlname, "TableName")>
<#assign tableName=tbn(tablesqlname, "tableName")>
<#assign tablename=tbn(tablesqlname, "tablename")>
<#assign _resFolder="">
<#if resFolder?? && resFolder?trim!=''><#assign _resFolder="${resFolder?trim}/"></#if>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers" data-querystr="&{cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="&{<#list searchColumnBeans as column>not empty param.${tbn(column.name, "tableName")} ||</#list> not empty param.code || not empty param.sort}"/>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="${tableName}:edit">
                    <button class="popupBtn btn btn-info btn-sm"
                            data-url="&{ctx}/${_resFolder}${tableName}_au">
                        <i class="fa fa-plus"></i> 添加</button>
                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="&{ctx}/${_resFolder}${tableName}_au"
                       data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                        修改</button>
                </shiro:hasPermission>
                <shiro:hasPermission name="${tableName}:del">
                    <button data-url="&{ctx}/${_resFolder}${tableName}_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
                <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-url="&{ctx}/${_resFolder}${tableName}_data"
                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出</button>
            </div>
            <div class="jqgrid-vertical-offset widget-box &{_query?'':'collapsed'} hidden-sm hidden-xs">
                <div class="widget-header">
                    <h4 class="widget-title">搜索</h4>

                    <div class="widget-toolbar">
                        <a href="#" data-action="collapse">
                            <i class="ace-icon fa fa-chevron-&{_query?'up':'down'}"></i>
                        </a>
                    </div>
                </div>
                <div class="widget-body">
                    <div class="widget-main no-padding">
                        <form class="form-inline search-form" id="searchForm">
                        <#list searchColumnBeans as column>
                        <div class="form-group">
                            <label>${column.comments}</label>
                            <input class="form-control search-query" name="${tbn(column.name, "tableName")}" type="text" value="&{param.${tbn(column.name, "tableName")}}"
                                   placeholder="请输入${column.comments}">
                        </div>
                        </#list>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                   data-url="&{ctx}/${_resFolder}${tableName}"
                                   data-target="#page-content"
                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="&{_query}">&nbsp;
                                    <button type="button" class="resetBtn btn btn-warning btn-sm"
                                            data-url="&{ctx}/${_resFolder}${tableName}"
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
        url: '&{ctx}/${_resFolder}${tableName}_data?callback=?&&{cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            <#list tableColumns as column>
            <#if column.name=='sort_order'>
                <c:if test="&{!_query}">
                { label:'排序',align:'center', formatter: $.jgrid.formatter.sortOrder,
                    formatoptions:{url:'&{ctx}/${_resFolder}${tableName}_changeOrder'},frozen:true },
                </c:if>
            <#else>
                { label: '${column.comments}',name: '${tbn(column.name, "tableName")}'}<#if column_has_next>,</#if>
            </#if>
            </#list>
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    //$('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$.register.date($('.date-picker'));
</script>