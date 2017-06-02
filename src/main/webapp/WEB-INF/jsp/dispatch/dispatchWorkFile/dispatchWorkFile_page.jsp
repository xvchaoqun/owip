<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content" class="myTableDiv"
                 data-url-page="${ctx}/dispatchWorkFile"
                 data-url-export="${ctx}/dispatchWorkFile_data"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.type ||not empty param.status ||not empty param.unitType ||not empty param.year ||not empty param.workType ||not empty param.privacyType || not empty param.code || not empty param.sort}"/>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="dispatchWorkFile:edit">
                    <a class="popupBtn btn btn-info btn-sm"  data-url="${ctx}/dispatchWorkFile_au"><i class="fa fa-plus"></i> 添加</a>
                    <a class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/dispatchWorkFile_au"
                       data-grid-id="#jqGrid"
                       data-querystr="&"><i class="fa fa-edit"></i>
                        修改</a>
                </shiro:hasPermission>
                <shiro:hasPermission name="dispatchWorkFile:del">
                    <button data-url="${ctx}/dispatchWorkFile_batchDel"
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
                            <label>类别</label>
                            <input class="form-control search-query" name="type" type="text" value="${param.type}"
                                   placeholder="请输入类别">
                        </div>
                        <div class="form-group">
                            <label>状态</label>
                            <input class="form-control search-query" name="status" type="text" value="${param.status}"
                                   placeholder="请输入状态">
                        </div>
                        <div class="form-group">
                            <label>发文单位</label>
                            <input class="form-control search-query" name="unitType" type="text" value="${param.unitType}"
                                   placeholder="请输入发文单位">
                        </div>
                        <div class="form-group">
                            <label>年度</label>
                            <input class="form-control search-query" name="year" type="text" value="${param.year}"
                                   placeholder="请输入年度">
                        </div>
                        <div class="form-group">
                            <label>所属专项工作</label>
                            <input class="form-control search-query" name="workType" type="text" value="${param.workType}"
                                   placeholder="请输入所属专项工作">
                        </div>
                        <div class="form-group">
                            <label>保密级别</label>
                            <input class="form-control search-query" name="privacyType" type="text" value="${param.privacyType}"
                                   placeholder="请输入保密级别">
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
        url: '${ctx}/dispatchWorkFile_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '发文单位',name: 'unitType'},
            { label: '年度',name: 'year'},
            { label: '所属专项工作',name: 'workType'},
            { label: '排序',name: 'sortOrder'},
            { label: '发文号',name: 'code'},
            { label: '发文日期',name: 'pubDate'},
            { label: '文件名',name: 'fileName'},
            { label: '文件',name: 'filePath'},
            { label: '保密级别',name: 'privacyType'},
            { label: '备注',name: 'remark'}
        ]
    }).jqGrid("setFrozenColumns").on("initGrid",function(){
        $(window).triggerHandler('resize.jqGrid');
    })
    _initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>