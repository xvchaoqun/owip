<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">

            <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                <li class="<c:if test="${cls==1}">active</c:if>">
                    <a href="javascript:;" class="loadPage" data-url="${ctx}/sc/scRecord?cls=1"><i class="fa fa-sliders"></i> 单个岗位调整</a>
                </li>
                <li class="<c:if test="${cls==2}">active</c:if>">
                    <a href="javascript:;" class="loadPage" data-url="${ctx}/sc/scRecord?cls=2"><i class="fa fa-star"></i> 党委班子换届</a>
                </li>
                <li class="<c:if test="${cls==3}">active</c:if>">
                    <a href="javascript:;" class="loadPage" data-url="${ctx}/sc/scRecord?cls=3"><i class="fa fa-link"></i> 行政班子换届</a>
                </li>
                <li class="<c:if test="${cls==4}">active</c:if>">
                    <a href="javascript:;" class="loadPage" data-url="${ctx}/sc/scRecord?cls=4"><i class="fa fa-gear"></i> 参数设置</a>
                </li>
            </ul>
            <div class="space-4"></div>
            <c:set var="_query" value="${not empty param.year ||not empty param.motionId ||not empty param.status || not empty param.code || not empty param.sort}"/>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="scRecord:edit">
                    <button class="popupBtn btn btn-success btn-sm"
                            data-url="${ctx}/sc/scRecord_au">
                        <i class="fa fa-plus"></i> 添加</button>
                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/sc/scRecord_au"
                       data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                        修改</button>
                </shiro:hasPermission>
                <shiro:hasPermission name="scRecord:del">
                    <button data-url="${ctx}/sc/scRecord_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
                <%--<button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-url="${ctx}/sc/scRecord_data"
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
                            <input class="form-control search-query" name="year" type="text" value="${param.year}"
                                   placeholder="请输入年份">
                        </div>
                        <div class="form-group">
                            <label>所属动议</label>
                            <input class="form-control search-query" name="motionId" type="text" value="${param.motionId}"
                                   placeholder="请输入所属动议">
                        </div>
                        <div class="form-group">
                            <label>状态</label>
                            <input class="form-control search-query" name="status" type="text" value="${param.status}"
                                   placeholder="请输入状态">
                        </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                   data-url="${ctx}/sc/scRecord"
                                   data-target="#page-content"
                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/sc/scRecord"
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
        url: '${ctx}/sc/scRecord_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '年份', name: 'year', width: 80, frozen:true},
            {label: '状态', name: 'status', formatter: function (cellvalue, options, rowObject) {
                    return _cMap.SC_RECORD_STATUS_MAP[cellvalue]
            }},
            {label: '纪实编号', name: 'code', width: 200, frozen:true},
            { label: '纪实详情',name: '_detail'},
            { label: '纪实进度',name: '_progress'},
            {label: '选任启动日期', name: 'holdDate', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
            {label: '选任岗位',name: 'postName', align:'left', width: 300, frozen:true},
            {label: '分管工作', align:'left', name: 'job', width: 200 },
            {label: '行政级别', name: 'adminLevel', width: 85, formatter: $.jgrid.formatter.MetaType},
            {label: '职务属性', name: 'postType', width: 120, formatter: $.jgrid.formatter.MetaType},
            {label: '所属单位', name: 'unitId', width: 200, align: 'left', formatter: $.jgrid.formatter.unit},
            {label: '单位类型', name: 'unitType', width: 120, frozen: true, formatter: $.jgrid.formatter.MetaType},
            {label: '选拔任用方式', name: 'scType', width: 120, formatter: $.jgrid.formatter.MetaType},
            {label: '备注', name: 'remark', width: 350, align: 'left'}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$.register.date($('.date-picker'));
</script>