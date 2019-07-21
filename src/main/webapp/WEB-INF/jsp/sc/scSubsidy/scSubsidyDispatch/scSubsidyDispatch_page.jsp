<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.year ||not empty param.hrType ||not empty param.hrNum
            ||not empty param.feType ||not empty param.feNum
            || not empty param.code || not empty param.sort}"/>
            <jsp:include page="../scSubsidy/menu.jsp"/>
            <div class="space-4"></div>
            <div class="jqgrid-vertical-offset buttons">
                <%--<shiro:hasPermission name="scSubsidyDispatch:edit">
                    <button class="popupBtn btn btn-info btn-sm"
                            data-url="${ctx}/sc/scSubsidyDispatch_au">
                        <i class="fa fa-plus"></i> 添加</button>
                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/sc/scSubsidyDispatch_au"
                       data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                        修改</button>
                </shiro:hasPermission>
                <shiro:hasPermission name="scSubsidyDispatch:del">
                    <button data-url="${ctx}/sc/scSubsidyDispatch_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
                <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-url="${ctx}/sc/scSubsidyDispatch_data"
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
                                <label>年份</label>
                                <div class="input-group">
                                                    <span class="input-group-addon"> <i
                                                            class="fa fa-calendar bigger-110"></i></span>
                                    <input class="form-control date-picker" style="width: 80px;" autocomplete="off" placeholder="请选择年份"
                                           name="year" type="text"
                                           data-date-format="yyyy" data-date-min-view-mode="2"
                                           value="${param.year}"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label>发人事处通知文号</label>
                                <select data-rel="select2-ajax"
                                        data-ajax-url="${ctx}/annualType_selects?module=<%=SystemConstants.ANNUAL_TYPE_MODULE_SUBSIDY%>"
                                        name="hrType" data-placeholder="请选择文号" data-width="150">
                                    <option value="${hrAnnualType.id}">${hrAnnualType.name}</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>发人事处通知编号</label>
                                <input class="form-control num" type="text" name="hrNum" style="width: 50px" value="${param.hrNum}">
                            </div>

                            <div class="form-group">
                                <label>发财经处通知文号</label>
                                <select data-rel="select2-ajax"
                                        data-ajax-url="${ctx}/annualType_selects?module=<%=SystemConstants.ANNUAL_TYPE_MODULE_SUBSIDY%>"
                                        name="feType" data-placeholder="请选择文号" data-width="150">
                                    <option value="${feAnnualType.id}">${feAnnualType.name}</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>发财经处通知编号</label>
                                <input class="form-control num" type="text" name="feNum" style="width: 50px" value="${param.feNum}">
                            </div>

                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                   data-url="${ctx}/sc/scSubsidyDispatch?cls=${cls}"
                                   data-target="#page-content"
                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/sc/scSubsidyDispatch?cls=${cls}"
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
        url: '${ctx}/sc/scSubsidyDispatch_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '年份', name: 'year',width:80, frozen: true},
            {
                label: '干部任免文件', name: '_dispatch', width: 180, formatter: function (cellvalue, options, rowObject) {
                    if(rowObject.dispatch==undefined) return '--'
                return $.swfPreview(rowObject.dispatch.file, rowObject.dispatch.fileName,
                        rowObject.dispatch.dispatchCode, rowObject.dispatch.dispatchCode);
            }, frozen: true},
            {label: '任免日期', name: 'dispatch.workTime', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
            {label: '通知日期', name: 'infoDate', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
            {label: '发人事处通知', name: 'hrCode', width: 200},
            {label: '发财经处通知', name: 'feCode', width: 200},
            /*{ label: '备注',name: 'remark', width:300, align:'left'}*/
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    $.register.date($('.date-picker'));
    $.register.dispatchType_select($('#searchForm select[name=hrType]'), $("#searchForm input[name=year]"));
    $.register.dispatchType_select($('#searchForm select[name=feType]'), $("#searchForm input[name=year]"));
</script>