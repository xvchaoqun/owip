<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.year ||not empty param.traineeTypeId || not empty param.code || not empty param.sort}"/>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="cetAnnual:edit">
                    <button class="popupBtn btn btn-success btn-sm"
                            data-url="${ctx}/cet/cetAnnual_au">
                        <i class="fa fa-plus"></i> 添加</button>
                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/cet/cetAnnual_au"
                       data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                        修改</button>
                </shiro:hasPermission>
                <shiro:hasPermission name="cetAnnual:del">
                    <button data-url="${ctx}/cet/cetAnnual_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>

                <button class="jqExportItemBtn btn btn-info btn-sm"
                data-url="${ctx}/cet/cetAnnual_exportObjs">
            <i class="fa fa-download"></i> 导出学时情况统计表</button>

                <%--<button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-url="${ctx}/cet/cetAnnual_data"
                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出</button>--%>
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
                            <label>年度</label>
                            <input class="form-control search-query" name="year" type="text" value="${param.year}"
                                   placeholder="请输入年度">
                        </div>
                        <div class="form-group">
                            <label>培训对象类型</label>
                            <input class="form-control search-query" name="traineeTypeId" type="text" value="${param.traineeTypeId}"
                                   placeholder="请输入培训对象类型">
                        </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                   data-url="${ctx}/cet/cetAnnual"
                                   data-target="#page-content"
                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="resetBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/cet/cetAnnual"
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
    var traineeTypeMap = ${cm:toJSONObject(traineeTypeMap)};
    $("#jqGrid").jqGrid({
        rownumbers:true,
        url: '${ctx}/cet/cetAnnual_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
                {label: '详情', name: '_detail', width:'80', formatter: function (cellvalue, options, rowObject) {
                    return ('<button class="openView btn btn-success btn-xs" ' +
                    'data-url="${ctx}/cet/cetAnnual_detail?annualId={0}"><i class="fa fa-search"></i> 详情</button>')
                            .format(rowObject.id);
                }, frozen: true},
                { label: '年度',name: 'year', width:80},
                { label: '培训对象类型', name: 'traineeTypeId', formatter: function (cellvalue, options, rowObject) {
                    return traineeTypeMap[cellvalue].name
                }},
                { label: '培训对象人数',name: 'objCount', width:120},
                { label: '备注',name: 'remark', width:220}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$.register.date($('.date-picker'));
</script>