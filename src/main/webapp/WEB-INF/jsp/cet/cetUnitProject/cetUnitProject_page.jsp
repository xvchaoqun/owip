<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers multi-row-head-table" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.year ||not empty param.unitId || not empty param.code || not empty param.sort}"/>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="cetUnitProject:edit">
                    <button class="popupBtn btn btn-success btn-sm" data-width="900"
                            data-url="${ctx}/cet/cetUnitProject_au?addType=${addType}">
                        <i class="fa fa-plus"></i> 添加</button>
                    <button class="jqOpenViewBtn btn btn-primary btn-sm" data-width="900"
                       data-url="${ctx}/cet/cetUnitProject_au?addType=${addType}"
                       data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                        修改</button>
                </shiro:hasPermission>
                <shiro:hasPermission name="cetUnitProject:del">
                    <button data-url="${ctx}/cet/cetUnitProject_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？（删除后不可恢复，请谨慎操作！）"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
                <button class="jqOpenViewBtn btn btn-info btn-sm"
                        data-url="${ctx}/sysApprovalLog"
                        data-querystr="&type=<%=SystemConstants.SYS_APPROVAL_LOG_TYPE_CET_UNIT_TRAIN%>"
                        data-open-by="page">
                    <i class="fa fa-search"></i> 操作记录
                </button>
                <%--<button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-url="${ctx}/cet/cetUnitProject_data"
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
                                   placeholder="请输入">
                        </div>
                        <div class="form-group">
                            <label>培训班主办方</label>
                            <input class="form-control search-query" name="unitId" type="text" value="${param.unitId}"
                                   placeholder="请输入培训班主办方">
                        </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                   data-url="${ctx}/cet/cetUnitProject"
                                   data-target="#page-content"
                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/cet/cetUnitProject"
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
        url: '${ctx}/cet/cetUnitProject_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
                {label: '详情', name: '_detail', width:'80', formatter: function (cellvalue, options, rowObject) {
                    return ('<button class="openView btn btn-success btn-xs" ' +
                    'data-url="${ctx}/cet/cetUnitTrain?projectId={0}&addType=${addType}"><i class="fa fa-search"></i> 详情</button>')
                            .format(rowObject.id);
                }, frozen: true},
                { label: '年度',name: 'year', width: 60, frozen: true},
                { label: '培训班主办方',name: 'partyId',align:'left', width: 250, formatter:function(cellvalue, options, rowObject){
                    return $.party(rowObject.partyId);
                }, frozen: true},
                { label: '主办单位',name: 'unitId', width: 150, align:'left', formatter: $.jgrid.formatter.unit},
                {label: '培训项目名称', name: 'projectName', align: 'left',width: 350},
                {label: '培训<br/>开始时间', name: 'startDate', width: 120, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
                {label: '培训<br/>结束时间', name: 'endDate', width: 120, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
                {
                  label: '培训天数', name: '_day', width: 80, formatter: function (cellvalue, options, rowObject) {
                    return $.dayDiff(rowObject.startDate, rowObject.endDate);
                }
                },
                {label: '培训班类型', name: 'projectType', width: 150, formatter: $.jgrid.formatter.MetaType},
                {label: '报告名称', name: 'reportName', width: 150},
                {label: '主讲人', name: 'reporter', width: 80},
                {label: '培训学时', name: 'period', width: 80},
                {label: '培训地点', name: 'address', align: 'left', width: 180},
                /*{ label: '参训人数',name: 'totalCount'},*/
                {label: '是否计入<br/>年度学习任务', name: 'isValid', formatter: function (cellvalue, options, rowObject) {
                  if (cellvalue==undefined) {
                    return '--'
                  }
                  return cellvalue?'是':'否'
                }},
                { label: '备注',name: 'remark', align: 'left', width: 150},
                {label: '操作人', name: 'addUser.realname'},
                {label: '添加时间', name: 'addTime', width: 150},
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$.register.date($('.date-picker'));
</script>