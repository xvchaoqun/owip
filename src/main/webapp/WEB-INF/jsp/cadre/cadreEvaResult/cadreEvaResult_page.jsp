<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
            <c:set var="_query" value="${not empty param.code || not empty param.sort}"/>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="cadreEvaResult:edit">
                    <button class="popupBtn btn btn-info btn-sm"
                            data-url="${ctx}/cadreEvaResult_au?cadreId=${param.cadreId}&type=${param.type}">
                        <i class="fa fa-plus"></i> 添加</button>
                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/cadreEvaResult_au?type=${param.type}"
                       data-grid-id="#jqGrid_evaResult"><i class="fa fa-edit"></i>
                        修改</button>
                </shiro:hasPermission>
                <shiro:hasPermission name="cadreEvaResult:del">
                    <button data-url="${ctx}/cadreEvaResult_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid_evaResult"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
               <%-- <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-url="${ctx}/cadreEvaResult_data"
                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出</button>--%>
            </div>
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
                       <%-- <div class="form-group">
                            <label>所属干部</label>
                            <input class="form-control search-query" name="cadreId" type="text" value="${param.cadreId}"
                                   placeholder="请输入所属干部">
                        </div>--%>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                   data-url="${ctx}/cadreEvaResult"
                                   data-target="#page-content"
                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/cadreEvaResult"
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
            <table id="jqGrid_evaResult" class="jqGrid2 table-striped"></table>
            <div id="jqGridPager_evaResult"></div>

    </div>
</div>
<script>
    var type = ${cm:toJSONObject(param.type)};
    $("#jqGrid_evaResult").jqGrid({
        pager: "#jqGridPager_evaResult",
        rownumbers:true,
        url: '${ctx}/cadreEvaResult_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
                { label: '年份',name: 'year'},
                { label: '测评类别',name: 'groupName',width: 300,align:"left"},
                { label:'排名', name:'sortOrder',width: 80,},
                { label: (type == 0 ? '总人数' : '班子总人数'),name: 'num'},
                { label: (type == 0 ? '时任单位及职务' : '班子名称'),name: 'title',width:300},
                { label: '备注',name: 'remark',width: 350}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid_evaResult", "jqGridPager_evaResult");
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$.register.date($('.date-picker'));
</script>