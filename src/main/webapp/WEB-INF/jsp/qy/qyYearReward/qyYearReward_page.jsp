<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="widget-box transparent">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:;" class="hideView btn btn-xs btn-success">
                <i class="ace-icon fa fa-backward"></i>
                返回</a>
        </h4>
    </div>
    <div class="widget-body">
        <div class="widget-main padding-4">
            <div class="tab-content padding-8">
                <shiro:hasPermission name="qyReward:edit">
                    <button class="popupBtn btn btn-info btn-sm"
                            data-url="${ctx}/qyYearReward_au?yearId=${param.yearId}">
                        <i class="fa fa-plus"></i> 添加
                    </button>
                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                            data-url="${ctx}/qyYearReward_au"
                            data-grid-id="#jqGrid2"><i class="fa fa-edit"></i>
                        修改
                    </button>
                    <button data-url="${ctx}/qyYearReward_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？(删除奖项时，该年度奖项关联的获奖记录及对象将一并删除，请谨慎操作！)"
                            data-grid-id="#jqGrid2"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
              <%--  <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                        data-url="${ctx}/qyYearReward_data"
                        data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出
                </button>--%>

            <%--    <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
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
                                    <label>奖励对象</label>
                                    <input class="form-control search-query" name="rewardId" type="text"
                                           value="${param.rewardId}"
                                           placeholder="请输入奖项id">
                                </div>
                                <div class="clearfix form-actions center">
                                    <a class="jqSearchBtn btn btn-default btn-sm"
                                       data-url="${ctx}/qyYearReward"
                                       data-target="#page-content"
                                       data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                    <c:if test="${_query}">&nbsp;
                                        <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                data-url="${ctx}/qyYearReward"
                                                data-target="#page-content">
                                            <i class="fa fa-reply"></i> 重置
                                        </button>
                                    </c:if>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>--%>
                <div class="space-4"></div>
                <table id="jqGrid2" class="jqGrid2 table-striped"></table>
                <div id="jqGridPager2"></div>
            </div>
        </div>
    </div>
</div>
<script>
    $("#jqGrid2").jqGrid({
        pager:"#jqGridPager2",
        rownumbers: true,
        url: '${ctx}/qyYearReward_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '年度', name: 'qyYear.year'},
            {label: '奖项', name: 'qyReward.name',align:'left',width: 250,},
            {label: '奖励对象', name: 'qyReward.type',formatter: function (cellvalue, options, rowObject) {
                    if(cellvalue==undefined) return '-'
                    return _cMap.QY_REWARD_MAP[cellvalue];
                }},
            <c:if test="${!_query}">
            {
                label: '排序', width: 80, formatter: $.jgrid.formatter.sortOrder,
                formatoptions: {grid:'#jqGrid2',url: '${ctx}/qyYearReward_changeOrder'}, frozen: true
            },
            </c:if>
            {label: '备注', name: 'remark',width:300,align:'left'}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");
    /*$.register.user_select($('[data-rel="select2-ajax"]'));
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $.register.date($('.date-picker'));*/
</script>