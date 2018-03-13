<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/cet/cetTrain"
             data-url-export="${ctx}/cet/cetTrain_data"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query"
                   value="${not empty param.year ||not empty param.num ||not empty param.name || not empty param.code || not empty param.sort}"/>
            <div class="tabbable">
                <jsp:include page="menu.jsp"/>
                <div class="tab-content">
                    <div id="home4" class="tab-pane in active">
                        <div class="jqgrid-vertical-offset buttons">
                            <c:if test="${cls==1}">
                            <shiro:hasPermission name="cetTrain:edit">
                                <a class="popupBtn btn btn-info btn-sm" data-url="${ctx}/cet/cetTrain_au"><i
                                        class="fa fa-plus"></i> 创建培训班</a>
                                <a class="jqOpenViewBtn btn btn-primary btn-sm"
                                   data-url="${ctx}/cet/cetTrain_au"
                                   data-grid-id="#jqGrid"
                                   data-querystr="&"><i class="fa fa-edit"></i>
                                    修改</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="cetTrain:del">
                                <button data-url="${ctx}/cet/cetTrain_batchDel"
                                        data-title="删除"
                                        data-msg="确定删除这{0}条数据？"
                                        data-grid-id="#jqGrid"
                                        class="jqBatchBtn btn btn-danger btn-sm">
                                    <i class="fa fa-trash"></i> 删除
                                </button>
                            </shiro:hasPermission>
                            </c:if>
                            <a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                               data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                                <i class="fa fa-download"></i> 导出</a>
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
                                        <input type="hidden" name="cls" value="${cls}">
                                        <div class="form-group">
                                            <label>年度</label>
                                            <input class="form-control search-query" name="year" type="text"
                                                   value="${param.year}"
                                                   placeholder="请输入年度">
                                        </div>
                                        <div class="form-group">
                                            <label>编号</label>
                                            <input class="form-control search-query" name="num" type="text"
                                                   value="${param.num}"
                                                   placeholder="请输入编号">
                                        </div>
                                        <div class="form-group">
                                            <label>培训班名称</label>
                                            <input class="form-control search-query" name="name" type="text"
                                                   value="${param.name}"
                                                   placeholder="请输入培训班名称">
                                        </div>
                                        <div class="clearfix form-actions center">
                                            <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i>
                                                查找</a>

                                            <c:if test="${_query}">&nbsp;
                                                <button type="button" class="resetBtn btn btn-warning btn-sm"
                                                        data-querystr="cls=${cls}">
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
        <div id="item-content"></div>
    </div>
</div>
<script>
    $("#jqGrid").jqGrid({
        url: '${ctx}/cet/cetTrain_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '详情', name: '_detail', width:'80', formatter: function (cellvalue, options, rowObject) {
                return ('<button class="openView btn btn-success btn-xs" ' +
                        'data-url="${ctx}/cet/cetTrain_detail?trainId={0}"><i class="fa fa-search"></i> 详情</button>')
                        .format(rowObject.id);
            }, frozen: true},
            {label: '年度', name: 'year', width:'60', frozen: true},
            {
                label: '编号', name: 'num', formatter: function (cellvalue, options, rowObject) {
                if(rowObject.type==undefined || rowObject.type<=0) return ''
                return _cMap.metaTypeMap[rowObject.type].name + "[" + rowObject.year + "]" + rowObject.num + "号";

            }, width: 200, frozen: true
            },
            {label: '培训班类型', name: 'type', width:200, formatter: $.jgrid.formatter.MetaType},
            {label: '培训班名称', name: 'name', width:200, align:'left'},
            {label: '培训主题', name: 'subject', width:200},
            {label: '参训人类型', name: 'traineeTypes', width:200},
            {label: '开课日期', name: 'startDate', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {label: '结课日期', name: 'endDate', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {label: '可选课人数', name: '_count', width:120},
            {label: '选课情况', name: '_select'},
            {label: '发布状态', name: '_pubStatus'},
            {label: '发布', name: '_pub'},
            {label: '状态', name: '_status'},
            {label: '备注', name: 'remark', width:300}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>