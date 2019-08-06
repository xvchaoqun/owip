<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.teamId ||not empty param.type ||not empty param.confirmDate ||not empty param.content ||not empty param.filePath || not empty param.code || not empty param.sort}"/>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="cgRule:edit">
                    <button class="popupBtn btn btn-info btn-sm"
                            data-url="${ctx}/cg/cgRule_au">
                        <i class="fa fa-plus"></i> 添加</button>
                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/cg/cgRule_au"
                       data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                        修改</button>
                </shiro:hasPermission>
                <shiro:hasPermission name="cgRule:del">
                    <button data-url="${ctx}/cg/cgRule_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
                <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-url="${ctx}/cg/cgRule_data"
                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出</button>
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
                            <label>所属委员会或领导小组</label>
                            <input class="form-control search-query" name="teamId" type="text" value="${param.teamId}"
                                   placeholder="请输入所属委员会或领导小组">
                        </div>
                        <div class="form-group">
                            <label>类型</label>
                            <input class="form-control search-query" name="type" type="text" value="${param.type}"
                                   placeholder="请输入类型">
                        </div>
                        <div class="form-group">
                            <label>规程确定时间</label>
                            <input class="form-control search-query" name="confirmDate" type="text" value="${param.confirmDate}"
                                   placeholder="请输入规程确定时间">
                        </div>
                        <div class="form-group">
                            <label>规程内容</label>
                            <input class="form-control search-query" name="content" type="text" value="${param.content}"
                                   placeholder="请输入规程内容">
                        </div>
                        <div class="form-group">
                            <label>相关文件</label>
                            <input class="form-control search-query" name="filePath" type="text" value="${param.filePath}"
                                   placeholder="请输入相关文件">
                        </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                   data-url="${ctx}/cg/cgRule"
                                   data-target="#page-content"
                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/cg/cgRule"
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
        url: '${ctx}/cg/cgRule_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
                { label: '所属委员会或领导小组',name: 'teamId'},
                { label: '类型',name: 'type'},
                { label: '规程确定时间',name: 'confirmDate'},
                { label: '规程内容',name: 'content'},
                { label: '相关文件',name: 'filePath'},
                <c:if test="${!_query}">
                { label:'排序', width: 80, formatter: $.jgrid.formatter.sortOrder,
                    formatoptions:{url:'${ctx}/cg/cgRule_changeOrder'},frozen:true },
                </c:if>
                { label: '备注',name: 'remark'}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$.register.date($('.date-picker'));
</script>