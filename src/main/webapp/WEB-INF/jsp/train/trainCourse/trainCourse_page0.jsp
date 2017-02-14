<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content" class="myTableDiv"
                 data-url-page="${ctx}/trainCourse_page"
                 data-url-export="${ctx}/trainCourse_data"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.name || not empty param.code || not empty param.sort}"/>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="trainCourse:edit">
                    <a class="popupBtn btn btn-info btn-sm"  data-url="${ctx}/trainCourse_au"><i class="fa fa-plus"></i> 添加</a>
                    <a class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/trainCourse_au"
                       data-grid-id="#jqGrid"
                       data-querystr="&"><i class="fa fa-edit"></i>
                        修改</a>
                </shiro:hasPermission>
                <shiro:hasPermission name="trainCourse:del">
                    <button data-url="${ctx}/trainCourse_batchDel"
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
                        <a href="#" data-action="collapse">
                            <i class="ace-icon fa fa-chevron-${_query?'up':'down'}"></i>
                        </a>
                    </div>
                </div>
                <div class="widget-body">
                    <div class="widget-main no-padding">
                        <form class="form-inline search-form" id="searchForm">
                        <div class="form-group">
                            <label>名称</label>
                            <input class="form-control search-query" name="name" type="text" value="${param.name}"
                                   placeholder="请输入名称">
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
<script>
    $("#jqGrid").jqGrid({
        url: '${ctx}/trainCourse_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '培训班次',name: 'trainId'},
            { label: '名称',name: 'name'},
            { label: '教师名称',name: 'teacher'},
            { label: '开始时间',name: 'startTime'},
            { label: '结束时间',name: 'endTime'},
            { label: '评估表',name: 'evaTableId'},
            { label: '参评人数量',name: 'totalCount'},
            { label: '已完成测评的参评人数量',name: 'finishCount'},
            { label: '是否关闭评课',name: 'isClosed'},
            { label: '评课启动时间',name: 'openTime'},
            { label: '评课关闭时间',name: 'closeTime'},
            { label: '测评说明',name: 'note'}
        ]
    }).jqGrid("setFrozenColumns").on("initGrid",function(){
        $(window).triggerHandler('resize.jqGrid');
    })

    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>