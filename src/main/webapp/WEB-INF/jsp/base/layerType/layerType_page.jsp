<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/layerType_page"
             data-url-export="${ctx}/layerType_data"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.name || not empty param.code || not empty param.sort}"/>
            <div class="tabbable">
                <div class="tab-content">
                    <div id="home4" class="tab-pane in active">
                        <div class="jqgrid-vertical-offset buttons">
                            <shiro:hasPermission name="layerType:edit">
                                <a class="popupBtn btn btn-info btn-sm" data-url="${ctx}/layerType_au"><i
                                        class="fa fa-plus"></i> 添加</a>
                                <a class="jqOpenViewBtn btn btn-primary btn-sm"
                                   data-url="${ctx}/layerType_au"
                                   data-grid-id="#jqGrid"
                                   data-querystr="&level=${level}"><i class="fa fa-edit"></i>
                                    修改</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="layerType:del">
                                <button data-url="${ctx}/layerType_batchDel"
                                        data-title="删除"
                                        data-msg="确定删除这{0}条数据？(${level==1?'关联二级学科都将删除，':''}数据不可恢复，请谨慎操作)"
                                        data-grid-id="#jqGrid"
                                        class="jqBatchBtn btn btn-danger btn-sm">
                                    <i class="fa fa-trash"></i> 删除
                                </button>
                            </shiro:hasPermission>
                            <a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                               data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                                <i class="fa fa-file-excel-o"></i> 导出</a>
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
                                        <input type="hidden" name="level" value="${level}">

                                        <div class="form-group">
                                            <label>学科名称</label>
                                            <input class="form-control search-query" name="name" type="text"
                                                   value="${param.name}"
                                                   placeholder="请输入学科名称">
                                        </div>
                                        <div class="clearfix form-actions center">
                                            <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i>
                                                查找</a>

                                            <c:if test="${_query}">&nbsp;
                                                <button type="button" class="resetBtn btn btn-warning btn-sm"
                                                        data-querystr="level=${level}">
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
        url: '${ctx}/layerType_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '学科名称', name: 'name', width:250, frozen: true},
            {label: '编码', name: 'code', width:150, frozen: true},
            <c:if test="${!_query}">
            {
                  label: '排序',formatter: $.jgrid.formatter.sortOrder,
                  formatoptions: {url: "${ctx}/layerType_changeOrder"}, frozen:true
              },
            </c:if>
            {
                label: '详情', name: '_detail', formatter: function (cellvalue, options, rowObject) {

                return '<button class="openView btn btn-success btn-xs"' +
                        'data-url="${ctx}/layerType_detail?fid={0}"><i class="fa fa-search"></i> 详情({1})</button>'
                                .format(rowObject.id, rowObject.num)
            }},
            {label: '一级类别', name: 'firstLevel', width:150},
            {label: '二级类别', name: 'secondLevel', width:150},
            {label: '备注', name: 'remark', width:250}
        ]
    }).jqGrid("setFrozenColumns")
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>