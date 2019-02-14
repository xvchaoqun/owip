<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" class="myTableDiv"
                 data-url-page="${ctx}/cisObjInspector"
                 data-url-export="${ctx}/cisObjInspector_data"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.objId || not empty param.code || not empty param.sort}"/>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="cisObjInspector:edit">
                    <a class="popupBtn btn btn-info btn-sm"  data-url="${ctx}/cisObjInspector_au"><i class="fa fa-plus"></i> 添加</a>
                    <a class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/cisObjInspector_au"
                       data-grid-id="#jqGrid"

                       data-width="900"><i class="fa fa-edit"></i>
                        修改</a>
                </shiro:hasPermission>
                <shiro:hasPermission name="cisObjInspector:del">
                    <button data-url="${ctx}/cisObjInspector_batchDel"
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
                            <label>obj_id</label>
                            <input class="form-control search-query" name="objId" type="text" value="${param.objId}"
                                   placeholder="请输入obj_id">
                        </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>

                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm">
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
        url: '${ctx}/cisObjInspector_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: 'obj_id',name: 'objId'},
            { label: 'inspector_id',name: 'inspectorId'}
        ]
    }).jqGrid("setFrozenColumns").on("initGrid",function(){
        $(window).triggerHandler('resize.jqGrid');
    })

    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>