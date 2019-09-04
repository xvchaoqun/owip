<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/shortMsgTpl"
             data-url-export="${ctx}/shortMsgTpl_data"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.content || not empty param.code || not empty param.sort}"/>
            <div class="tabbable">
                <jsp:include page="menu.jsp"/>
                <div class="tab-content">
                    <div class="tab-pane in active">
                        <div class="jqgrid-vertical-offset buttons">
                            <shiro:hasPermission name="shortMsgTpl:edit">
                                <a class="openView btn btn-info btn-sm" data-url="${ctx}/shortMsgTpl_au"><i
                                        class="fa fa-plus"></i> 添加</a>
                                <a class="jqOpenViewBtn btn btn-primary btn-sm"
                                   data-open-by="page"
                                   data-url="${ctx}/shortMsgTpl_au"
                                   data-grid-id="#jqGrid"
                                   ><i class="fa fa-edit"></i>
                                    修改</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="shortMsgTpl:del">
                                <button data-url="${ctx}/shortMsgTpl_batchDel"
                                        data-title="删除"
                                        data-msg="确定删除这{0}条数据？"
                                        data-grid-id="#jqGrid"
                                        class="jqBatchBtn btn btn-danger btn-sm">
                                    <i class="fa fa-trash"></i> 删除
                                </button>
                            </shiro:hasPermission>
                            <%--<a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                               data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                                <i class="fa fa-download"></i> 导出</a>--%>
                        </div>
                        <%--<div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
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
                                        <label>发送内容</label>
                                        <input class="form-control search-query" name="content" type="text" value="${param.content}"
                                               placeholder="请输入发送内容">
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
                        </div>--%>
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
        url: '${ctx}/shortMsgTpl_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '类型', name: 'type', width: 80,  formatter: function (cellvalue, options, rowObject) {
                return _cMap.CONTENT_TPL_TYPE_MAP[cellvalue];
            }},
            { label: '标题（微信）', name: 'wxTitle', width: 150, align:'left', formatter: function (cellvalue, options, rowObject) {
                if(rowObject.type=='<%=ContentTplConstants.CONTENT_TPL_TYPE_MSG%>') return '--'
                var str = "";
                if(rowObject.wxPic!=undefined){
                    str += '<a href="{0}" target="_blank"><img src="{0}" width="40"/></a> '.format(rowObject.wxPic)
                }
                str += $.trim(cellvalue) + "，" + $.trim(rowObject.wxUrl);
                return str;
            }},
            {label: '模板名称', name: 'name', width: 350, align:'left',frozen:true},
            {
                label: '排序', width: 90, index: 'sort', formatter: $.jgrid.formatter.sortOrder,
                formatoptions: {url: "${ctx}/shortMsgTpl_changeOrder"}
            },
            {
                label: '定向发送', name: '_verify', width: 170, formatter: function (cellvalue, options, rowObject) {
                return ('<button class="popupBtn btn btn-success btn-xs" data-width="700" ' +
                    'data-url="${ctx}/shortMsgTpl_send?id={0}"><i class="fa fa-user"></i> 个人</button>')
                        .format(rowObject.id)
                    + ('&nbsp;&nbsp;<button class="popupBtn btn btn-warning btn-xs" data-width="700" ' +
                    'data-url="${ctx}/shortMsgTpl_send?id={0}&type=batch"><i class="fa fa-users"></i> 批量(干部)</button>')
                        .format(rowObject.id);
            }},
            {label: '发送内容', name: 'content', width: 850, formatter: $.jgrid.formatter.NoMultiSpace},
            {label: '发送次数', name: 'sendCount', width: 90},
            {label: '发送人次', name: 'sendUserCount', width: 90},
            {label: '创建时间', name: 'createTime', width: 150},
            {label: '创建人', name: 'user.realname'},
            {label: '备注', name: 'remark', width: 250}
        ]
    }).jqGrid("setFrozenColumns")
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>