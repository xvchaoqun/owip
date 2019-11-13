<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.id ||not empty param.reportDate ||not empty param.unit || not empty param.code || not empty param.sort}"/>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="opReport:edit">
                    <button class="popupBtn btn btn-info btn-sm"
                            data-url="${ctx}/op/opReport_au">
                        <i class="fa fa-plus"></i> 添加</button>
                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/op/opReport_au"
                       data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                        修改</button>
                    <button data-url="${ctx}/op/opReport_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
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
                            <div class="form-group">
                                <label>报送日期</label>
                                <div class="input-group tooltip-success" data-rel="tooltip" title="成立时间范围">
                                                    <span class="input-group-addon">
                                                        <i class="fa fa-calendar bigger-110"></i>
                                                    </span>
                                    <input placeholder="请输入报送日期" data-rel="date-range-picker" class="form-control date-range-picker" type="text" name="reportDate" value="${param.reportDate}"/>
                                </div>
                            </div>
                        <div class="form-group">
                            <label>报送上级单位</label>
                            <input class="form-control search-query" name="unit" type="text" value="${param.unit}"
                                   placeholder="请输入报送上级单位">
                        </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                   data-url="${ctx}/op/opReport"
                                   data-target="#page-content"
                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/op/opReport"
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
<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>
<script>
    $("#jqGrid").jqGrid({
        url: '${ctx}/op/opReport_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
                { label: '报送日期',name: 'reportDate',formatter:$.jgrid.formatter.date,formatoptions:{newformat:'Y.m.d'}},
                { label: '报送上级单位',name: 'unit',width:350},
                { label: '数据统计开始时间',name: 'startDate',width:150,formatter:$.jgrid.formatter.date,formatoptions:{newformat:'Y.m.d'}},
                { label: '数据统计截止时间',name: 'endDate',width:150,formatter:$.jgrid.formatter.date,formatoptions:{newformat:'Y.m.d'}},
                { label: '报送材料',name: 'files',formatter: function (cellvalue, options, rowObject) {
                        return '<button class="popupBtn btn btn-warning btn-xs" data-width="500" data-callback="_reload"' +
                            'data-url="${ctx}/op/opReportFiles?id={0}"><i class="fa fa-search"></i> 材料{1}</button>'
                                .format(rowObject.id, rowObject.countFile>0?"("+rowObject.countFile+")":"")
                    }},
                { label: '备注',name: 'remark',width:200},{hidden:true,key:true,name:'id'}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $.register.date($('.date-picker'));
</script>