<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/schedulerLog"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <jsp:include page="../schedulerJob/menu.jsp"/>
            <div class="space-4"></div>
            <c:set var="_query"
                   value="${not empty param.triggerTime || not empty param.jobId || not empty param.status}"/>
            <div class="col-sm-12">
                <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
                    <div class="widget-header">
                        <h4 class="widget-title">搜索</h4>
                        <div class="widget-toolbar">
                            <a href="javascript:;" data-action="collapse">
                                <i class="ace-icon fa fa-chevron-${_query?'up':'down'}"></i>
                            </a>
                        </div>
                    </div>
                    <div class="widget-body">
                        <div class="widget-main no-padding">
                            <form class="form-inline search-form" id="searchForm">
                                <input type="hidden" name="cls" value="${cls}">
                                <div class="form-group">
                                    <label>执行时间</label>
                                    <div class="input-group tooltip-success" data-rel="tooltip" title="执行时间范围">
                                        <span class="input-group-addon">
                                            <i class="fa fa-calendar bigger-110"></i>
                                        </span>
                                        <input placeholder="请选择执行时间范围" data-rel="date-range-picker"
                                               class="form-control date-range-picker"
                                               type="text" name="triggerTime" value="${param.triggerTime}"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label>任务名称</label>
                                    <select data-rel="select2-ajax"
                                            data-ajax-url="${ctx}/schedulerJob_selects"
                                            name="jobId" data-placeholder="请选择">
                                        <option value="${job.id}">${job.name}</option>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label>执行状态</label>
                                    <select data-rel="select2" name="status" data-placeholder="请选择">
                                        <option></option>
                                        <c:forEach items="<%=SystemConstants.SCHEDULER_JOB_MAP%>" var="entity">
                                            <option value="${entity.key}">${entity.value}</option>
                                        </c:forEach>
                                    </select>
                                    <script>
                                        $("#searchForm select[name=status]").val(${param.status});
                                    </script>
                                </div>
                                <div class="clearfix form-actions center">
                                    <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i>
                                        查找</a>
                                    <c:if test="${_query}">&nbsp;
                                        <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                data-querystr="cls=${cls}">
                                            <i class="fa fa-reply"></i> 重置
                                        </button>
                                    </c:if>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
                <%--<div class="space-4"></div>--%>
                <table id="jqGrid" class="jqGrid table-striped"></table>
                <div id="jqGridPager"></div>
            </div>
        </div>
        <div id="body-content-view"></div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>
<script>
    $("#jqGrid").jqGrid({
        multiselect: false,
        url: '${ctx}/schedulerLog_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '任务名称', name: 'job.name', width: 300, align: 'left'},
            {
                label: '触发方式', name: 'isManualTrigger', width: 80, formatter: $.jgrid.formatter.TRUEFALSE,
                formatoptions: {
                    on: '<span class="text text-danger bolder">手动</span>',
                    off: '<span class="text text-success bolder">自动</span>'
                }
            },
            {label: '发生时间', name: 'triggerTime', width: 160},
            {
                label: '执行状态', name: 'status', formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue == undefined) return '--'
                    return _cMap.SCHEDULER_JOB_MAP[cellvalue];
                }
            },
            {label: 'jobName', name: 'jobName', width: 550, align: 'left'},
            {label: 'JobGroup', name: 'jobGroup', width: 250, align: 'left'},
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');

    $('#searchForm [data-rel="select2"]').select2();
    $.register.ajax_select($('[data-rel="select2-ajax"]'));
    $.initNavGrid("jqGrid", "jqGridPager");
</script>