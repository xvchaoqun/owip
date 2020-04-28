<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/cr/constants.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.year ||not empty param.addDate || not empty param.code || not empty param.sort}"/>
             <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                <li class="<c:if test="${cls==1}">active</c:if>">
                    <a href="javascript:;" class="loadPage" data-url="${ctx}/crInfo?cls=1"><i class="fa fa-circle-o-notch fa-spin"></i> 招聘信息</a>
                </li>
                <li class="<c:if test="${cls==2}">active</c:if>">
                    <a href="javascript:;" class="loadPage" data-url="${ctx}/crInfo?cls=2"><i class="fa fa-check"></i> 完成招聘</a>
                </li>
                <li class="<c:if test="${cls==3}">active</c:if>">
                    <a href="javascript:;" class="loadPage" data-url="${ctx}/crInfo?cls=3"><i class="fa fa-trash"></i> 已作废</a>
                </li>
                <li class="<c:if test="${cls==-1}">active</c:if>">
                    <a href="javascript:;" class="loadPage" data-url="${ctx}/crInfo?cls=-1"><i class="fa fa-circle-o-notch"></i> 全部</a>
                </li>
            </ul>
            <div class="space-4"></div>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="crInfo:edit">
                    <button class="popupBtn btn btn-info btn-sm"
                            data-url="${ctx}/crInfo_au">
                        <i class="fa fa-plus"></i> 添加</button>
                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/crInfo_au"
                       data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                        修改</button>
                </shiro:hasPermission>
                <shiro:hasPermission name="crInfo:del">
                    <button data-url="${ctx}/crInfo_batchDel"
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
                            <label>年度</label>
                            <div class="input-group">
                                    <span class="input-group-addon"> <i
                                            class="fa fa-calendar bigger-110"></i></span>
                                <input class="form-control date-picker" placeholder="请选择年份"
                                       name="year" type="text"
                                       data-date-format="yyyy" data-date-min-view-mode="2"
                                       value="${param.year}"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label>添加日期</label>
                            <div class="input-group tooltip-success" data-rel="tooltip" title="添加日期范围">
                                    <span class="input-group-addon"><i class="fa fa-calendar bigger-110"></i></span>
                                    <input placeholder="请选择添加日期范围" data-rel="date-range-picker"
                                           class="form-control date-range-picker" type="text"
                                           name="addDate" value="${param.addDate}"/>
                                </div>
                        </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                   data-url="${ctx}/crInfo"
                                   data-target="#page-content"
                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/crInfo"
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
        <div id="body-content-view2"></div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>
<script>
    $("#jqGrid").jqGrid({
        rownumbers:true,
        url: '${ctx}/crInfo_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
                {label: '年度', name: 'year', width:'60', frozen: true},
                {label: '表头名称', name: 'name', width:'160', align:'left', frozen: true},
                {label: '添加日期', name: 'addDate', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}, frozen: true},
                {
                    label: '招聘通知', name: 'notice', width: 80, formatter: function (cellvalue, options, rowObject) {

                        return $.pdfPreview(rowObject.notice, "招聘通知", "查看");
                    }, frozen: true
                },
                {label: '招聘岗位', name: '_detail', formatter: function (cellvalue, options, rowObject) {
                    return ('<button class="openView btn btn-success btn-xs" ' +
                    'data-url="${ctx}/crPost?infoId={0}"><i class="fa fa-list"></i> 查看({1})</button>')
                            .format(rowObject.id, rowObject.postNum);
                }, frozen: true},
                {label: '招聘人数',name: 'requireNum', width: 80},
                {label: '应聘情况', name: '_applicants', formatter: function (cellvalue, options, rowObject) {
                    return ('<button class="openView btn btn-warning btn-xs" ' +
                    'data-url="${ctx}/crApplicant?infoId={0}"><i class="fa fa-search"></i> 查看({1})</button>')
                            .format(rowObject.id, rowObject.applyNum);
                }, frozen: true},

                {label: '报名截止时间', name: 'endTime', width: 150, formatter: function (cellvalue, options, rowObject) {
                    if(cellvalue==undefined) return '--'
                    return $.date(cellvalue, "yyyy-MM-dd HH:mm");
                }},
                {label: '招聘会', name: '_meetings', formatter: function (cellvalue, options, rowObject) {
                    return ('<button class="openView btn btn-info btn-xs" ' +
                    'data-url="${ctx}/crMeeting?infoId={0}"><i class="fa fa-search"></i> 查看({1})</button>')
                            .format(rowObject.id, rowObject.meetingNum);
                }, frozen: true},
                { label: '状态',name: 'status', formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue == undefined) return '--';
                    return _cMap.CR_INFO_STATUS_MAP[cellvalue];
                }},
                { label: '备注',name: 'remark', width: 280}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    $.register.date($('.date-picker'));
</script>