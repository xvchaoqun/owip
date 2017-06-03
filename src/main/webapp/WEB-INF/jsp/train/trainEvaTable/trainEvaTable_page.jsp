<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content" class="myTableDiv"
                 data-url-page="${ctx}/trainEvaTable"
                 data-url-export="${ctx}/trainEvaTable_data"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.name || not empty param.code || not empty param.sort}"/>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="trainEvaTable:edit">
                    <a class="popupBtn btn btn-info btn-sm"  data-url="${ctx}/trainEvaTable_au"><i class="fa fa-plus"></i> 添加</a>
                    <a class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/trainEvaTable_au"
                       data-grid-id="#jqGrid"
                       data-querystr="&"><i class="fa fa-edit"></i>
                        修改</a>
                </shiro:hasPermission>
                <shiro:hasPermission name="trainEvaTable:del">
                    <button data-url="${ctx}/trainEvaTable_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
                <a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出</a>
            </div>
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
                        <div class="form-group">
                            <label>评估表名称</label>
                            <input class="form-control search-query" name="name" type="text" value="${param.name}"
                                   placeholder="请输入评估表名称">
                        </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>

                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="resetBtn btn btn-warning btn-sm">
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
        <div id="item-content"></div>
    </div>
</div>
<script type="text/template" id="sort_tpl">
<a href="javascript:;" class="jqOrderBtn" data-url="{{=url}}" data-id="{{=id}}" data-direction="1" title="上升"><i class="fa fa-arrow-up"></i></a>
<input type="text" value="1" class="order-step tooltip-success" data-rel="tooltip" data-placement="top"
           title="修改操作步长">
<a href="javascript:;" class="jqOrderBtn" data-url="{{=url}}" data-id="{{=id}}" data-direction="-1" title="下降"><i class="fa fa-arrow-down"></i></a>
</script>
<script>
    $("#jqGrid").jqGrid({
        url: '${ctx}/trainEvaTable_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '评估表名称',name: 'name', width: 300, align:'left', frozen: true},

            {
                label: '排序', width: 80, index: 'sort', formatter: function (cellvalue, options, rowObject) {
                return _.template($("#sort_tpl").html().NoMultiSpace())({id: rowObject.id, url:"${ctx}/trainEvaTable_changeOrder"})
            }, frozen: true
            },
            {label: '评估指标', name: 'normNum', formatter: function (cellvalue, options, rowObject) {
                var num = 0;
                if(cellvalue!=undefined) num=cellvalue;
                if (num==0)
                    return '<a href="javascript:void(0)" class="openView" data-url="${ctx}/trainEvaNorm?evaTableId={0}">编辑指标</a>'
                            .format(rowObject.id);
                else
                    return '<a href="javascript:void(0)" class="openView" data-url="${ctx}/trainEvaNorm?evaTableId={0}">查看指标（{1}）</a>'
                            .format(rowObject.id, cellvalue);
            }, width: 200},
            {label: '评估等级', name: 'rankNum', formatter: function (cellvalue, options, rowObject) {
                var num = 0;
                if(cellvalue!=undefined) num=cellvalue;
                if (num==0)
                    return '<a href="javascript:void(0)" class="openView" data-url="${ctx}/trainEvaRank?evaTableId={0}">编辑评估等级</a>'
                            .format(rowObject.id);
                else
                    return '<a href="javascript:void(0)" class="openView" data-url="${ctx}/trainEvaRank?evaTableId={0}">查看评估等级（{1}）</a>'
                            .format(rowObject.id, cellvalue);
            }, width: 200},
            {label: '评估表', name: 'rankNum', formatter: function (cellvalue, options, rowObject) {
                if(rowObject.normNum==0||rowObject.rankNum==0) return '-'
                return '<a href="javascript:void(0)" class="popupBtn" data-width="700" data-url="${ctx}/trainEvaTable_preview?id={0}">预览</a>'
                        .format(rowObject.id);
            }},
            { label: '备注',name: 'remark', width: 300, frozen: true}
        ]
    }).jqGrid("setFrozenColumns").on("initGrid",function(){
        $(window).triggerHandler('resize.jqGrid');
    })
    $.initNavGrid("jqGrid", "jqGridPager");

    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>