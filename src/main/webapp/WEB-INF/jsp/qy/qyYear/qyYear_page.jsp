<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.year || not empty param.code || not empty param.sort}"/>
            <div class="tabbable">

                <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                    <li class="${cls==1?'active':''}">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/qyReward" }><i
                                class="fa fa-list"></i> 奖项设置</a>
                    </li>

                    <li class="${cls==2?'active':''}">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/qyYear" }><i
                                class="fa fa-calendar-o"></i> 年度设置</a>
                    </li>
                </ul>

                <div class="tab-content">
                    <div class="tab-pane in active">

                        <div class="jqgrid-vertical-offset buttons">
                            <shiro:hasPermission name="qyReward:edit">
                                <button class="popupBtn btn btn-info btn-sm"
                                        data-url="${ctx}/qyYear_au">
                                    <i class="fa fa-plus"></i> 添加
                                </button>
                                <button class="jqOpenViewBtn btn btn-primary btn-sm"
                                        data-url="${ctx}/qyYear_au"
                                        data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                                    修改
                                </button>

                                <button data-url="${ctx}/qyYear_batchDel"
                                        data-title="删除"
                                        data-msg="确定删除这{0}条数据？(删除年度数据时，该数据关联的文件将一并删除，请谨慎操作！)"
                                        data-grid-id="#jqGrid"
                                        class="jqBatchBtn btn btn-danger btn-sm">
                                    <i class="fa fa-trash"></i> 删除
                                </button>
                            </shiro:hasPermission>
                          <%--  <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                                    data-url="${ctx}/qyYear_data"
                                    data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                                <i class="fa fa-download"></i> 导出
                            </button>--%>
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
                                            <input required class="date-picker"
                                                   name="year" type="text"
                                                   data-date-start-view="2"
                                                   data-date-min-view-mode="2"
                                                   data-date-max-view-mode="2"
                                                   data-date-format="yyyy"
                                                   style="width: 100px"
                                                   value="${param.year}"/>
                                        </div>
                                        <div class="clearfix form-actions center">
                                            <a class="jqSearchBtn btn btn-default btn-sm"
                                               data-url="${ctx}/qyYear"
                                               data-target="#page-content"
                                               data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                            <c:if test="${_query}">&nbsp;
                                                <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                        data-url="${ctx}/qyYear"
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
                </div>
            </div>
        </div>
        <div id="body-content-view"></div>
    </div>
</div>
<script>
    $("#jqGrid").jqGrid({
        rownumbers: true,
        url: '${ctx}/qyYear_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '年度', name: 'year'},
            {
                label: '表彰方案文件', name: 'planPdf',width:110,formatter: function (cellvalue, options, rowObject) {
                        return ('<button class="popupBtn btn btn-success btn-xs"data-width="650" ' +
                            'data-url="${ctx}/qyYear_file?yearId={0}&type=1">'
                            + '<i class="fa fa-search"></i> 详情</button>')
                            .format(rowObject.id);
                }
            },
            {
                label: '表彰奖项', name: 'reward', formatter: function (cellvalue, options, rowObject) {
                    return ('<button class="openView btn btn-warning btn-xs" ' +
                        'data-url="${ctx}/qyYearReward?yearId={0}">'
                        + '<i class="fa fa-cog"></i> 设置</button>')
                        .format(rowObject.id);
                }
            },
            {label: '表彰结果文件', name: 'resultPdf',width:110,formatter: function (cellvalue, options, rowObject) {
                        return ('<button class="popupBtn btn btn-success btn-xs" data-width="650"' +
                            'data-url="${ctx}/qyYear_file?yearId={0}&type=2">'
                            + '<i class="fa fa-search"></i> 详情</button>')
                            .format(rowObject.id);
                }},
            {label: '备注', width:300,name: 'remark'}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    $.register.date($('.date-picker'));
</script>