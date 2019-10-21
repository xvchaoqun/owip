<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.year || not empty param.rewardId||not empty param.code || not empty param.sort}"/>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="qyReward:edit">
                    <button class="popupBtn btn btn-info btn-sm"
                            data-url="${ctx}/qyRewardRecord_au?type=${param.type}">
                        <i class="fa fa-plus"></i> 添加</button>
                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/qyRewardRecord_au?type=${param.type}"
                       data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                        修改</button>
                    <button data-url="${ctx}/qyRewardRecord_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
                <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-url="${ctx}/qyRewardObj_data?type=${param.type}&exportType=1"
                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出</button>
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
                            <input required class="date-picker"
                                   name="year" type="text"
                                   data-date-start-view="2"
                                   data-date-min-view-mode="2"
                                   data-date-max-view-mode="2"
                                   data-date-format="yyyy"
                                   style="width: 100px"
                                   value="${param.year}"/>
                        </div>
                            <div class="form-group">
                                <label>奖项</label>
                                <select required data-rel="select2-ajax" data-ajax-url="${ctx}/qyReward_selects?type=${param.type}"
                                        data-width="180" name="rewardId" data-placeholder="请选择奖项">
                                    <option value="${param.rewardId}">${qyReward.name}</option>
                                </select>
                            </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                   data-url="${ctx}/qyRewardRecord?type=${param.type}"
                                   data-target="#page-content"
                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/qyRewardRecord?type=${param.type}"
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
        url: '${ctx}/qyRewardRecord_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
                { label: '年度',name: 'year'},
                { label: '奖项',name: 'rewardName', width: 250,align:'left'},
            <c:if test="${param.type==1}">
                { label: '获表彰院系级党委',width:135,name: 'partyId', formatter: function (cellvalue, options, rowObject) {
                        return ('<button class="openView btn btn-success btn-xs"' +
                            'data-url="${ctx}/qyRewardObj?recordId={0}&type={1}">'
                            + '<i class="fa fa-cog"></i> 设置</button>')
                            .format(rowObject.id,${param.type});
                    }},
            </c:if>
            <c:if test="${param.type==2}">
                { label: '获表彰党支部',name: 'branchId', width:120,formatter: function (cellvalue, options, rowObject) {
                        return ('<button class="openView btn btn-success btn-xs"' +
                            'data-url="${ctx}/qyRewardObj?recordId={0}&type={1}">'
                            + '<i class="fa fa-cog"></i> 设置</button>')
                            .format(rowObject.id,${param.type});
                    }},
            </c:if>
            <c:if test="${param.type==3}">
                { label: '获表彰党员',name: 'userId',width:90, formatter: function (cellvalue, options, rowObject) {
                        return ('<button class="openView btn btn-success btn-xs"' +
                            'data-url="${ctx}/qyRewardObj?recordId={0}&type={1}">'
                            + '<i class="fa fa-cog"></i> 设置</button>')
                            .format(rowObject.id,${param.type});
                    }},
            </c:if>
            <c:if test="${param.type==4}">
                { label: '获表彰党日活动',name: 'meetingName',width:120, formatter: function (cellvalue, options, rowObject) {
                        return ('<button class="openView btn btn-success btn-xs"' +
                            'data-url="${ctx}/qyRewardObj?recordId={0}&type={1}">'
                            + '<i class="fa fa-cog"></i> 设置</button>')
                            .format(rowObject.id,${param.type});
                    }},
            </c:if>
                { label: '备注',width:300,name: 'remark',align:'left'},
                ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $.register.ajax_select($('[data-rel="select2-ajax"]'));
    //$('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    $.register.date($('.date-picker'));
</script>