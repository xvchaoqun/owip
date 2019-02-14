<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" class="myTableDiv"
                 data-url-page="${ctx}/oa/oaTaskMsg"
                 data-url-export="${ctx}/oa/oaTaskMsg_data"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.taskId ||not empty param.userId || not empty param.code || not empty param.sort}"/>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="oaTaskMsg:edit">
                    <a class="popupBtn btn btn-info btn-sm"  data-url="${ctx}/oa/oaTaskMsg_au"><i class="fa fa-plus"></i> 添加</a>
                    <a class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/oa/oaTaskMsg_au"
                       data-grid-id="#jqGrid"
                       ><i class="fa fa-edit"></i>
                        修改</a>
                </shiro:hasPermission>
                <shiro:hasPermission name="oaTaskMsg:del">
                    <button data-url="${ctx}/oa/oaTaskMsg_batchDel"
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
                            <label>任务</label>
                            <input class="form-control search-query" name="taskId" type="text" value="${param.taskId}"
                                   placeholder="请输入任务">
                        </div>
                        <div class="form-group">
                            <label>接收人</label>
                            <input class="form-control search-query" name="userId" type="text" value="${param.userId}"
                                   placeholder="请输入接收人">
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
        url: '${ctx}/oa/oaTaskMsg_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '任务',name: 'taskId'},
            { label: '接收人',name: 'userId'},
            { label: '类别',name: 'type'},
            { label: '短信内容',name: 'content'},
            { label: '是否发送成功',name: 'success'},
            { label: '发送人',name: 'sendUserId'},
            { label: '发送时间',name: 'sendTime'},
            { label: 'IP',name: 'sendIp'}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>