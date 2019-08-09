<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <div class="tabbable">
                <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                    <li class="<c:if test="${isCurrent}">active</c:if>">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/cg/cgTeam?isCurrent=1">
                            <i class="fa fa-circle-o-notch fa-spin"></i>
                            正在运转委员会和领导小组</a>
                    </li>
                    <li class="<c:if test="${!isCurrent}">active</c:if>">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/cg/cgTeam?isCurrent=0">
                            <i class="fa fa-history"></i>
                            已撤销委员会和领导小组</a>
                    </li>
                </ul>
                <div class="tab-content">
                    <div class="tab-pane in active">
                        <c:set var="_query" value="${not empty param.name ||not empty param.type ||not empty param.category || not empty param.code || not empty param.sort}"/>
                        <div class="jqgrid-vertical-offset buttons">
                            <shiro:hasPermission name="cgTeam:edit">
                                <button class="popupBtn btn btn-info btn-sm"
                                        data-url="${ctx}/cg/cgTeam_au"><i class="fa fa-plus"></i>
                                    添加</button>
                                <button class="jqOpenViewBtn btn btn-primary btn-sm"
                                        data-url="${ctx}/cg/cgTeam_au"
                                        data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                                    修改</button>
                            </shiro:hasPermission>
                            <button class="jqBatchBtn btn btn-warning btn-sm"
                                    data-url="${ctx}/cg/cgTeam_plan"
                                    data-title="撤销"
                                    data-msg="确定撤销这{0}条数据？"
                                    data-grid-id="#jqGrid"><i class="fa fa-recycle"></i>
                                撤销</button>
                            <button class="popupBtn btn btn-info btn-sm tooltip-info"
                                    data-url="${ctx}/ps/psInfo_import"
                                    data-rel="tooltip" data-placement="top" title="批量导入"><i class="fa fa-upload"></i>
                                批量导入
                            </button>
                            <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                                    title="导出选中记录或所有搜索结果"
                                    data-url="${ctx}/cg/cgTeam_data"
                                    data-rel="tooltip"
                                    data-placement="top"><i class="fa fa-download"></i>
                            导出</button>
                            <shiro:hasPermission name="cgTeam:del">
                                <button class="jqBatchBtn btn btn-danger btn-sm"
                                        data-url="${ctx}/cg/cgTeam_batchDel"
                                        data-title="删除"
                                        data-msg="确定删除这{0}条数据？"
                                        data-grid-id="#jqGrid"><i class="fa fa-trash"></i>
                                    删除</button>
                            </shiro:hasPermission>

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
                                        <div class="form-group">
                                            <label>类型</label>
                                            <input class="form-control search-query" name="type" type="text" value="${param.type}"
                                                   placeholder="请输入类型">
                                        </div>
                                        <div class="form-group">
                                            <label>类别</label>
                                            <input class="form-control search-query" name="category" type="text" value="${param.category}"
                                                   placeholder="请输入类别">
                                        </div>
                                        <div class="clearfix form-actions center">
                                            <a class="jqSearchBtn btn btn-default btn-sm"
                                               data-url="${ctx}/cg/cgTeam"
                                               data-target="#page-content"
                                               data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                            <c:if test="${_query}">&nbsp;
                                                <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                        data-url="${ctx}/cg/cgTeam"
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
        rownumbers:true,
        pager:"jqGridPager",
        url: '${ctx}/cg/cgTeam_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
                { label: '委员会和领导小组名称',name: 'name',
                        formatter:function(cellvalue, options, rowObject){
                            return ('<a href="javascript:;" class="openView" ' +
                                'data-url="${ctx}/cg/cgTeam_view?id={0}">{1}</a>')
                            .format(rowObject.id, cellvalue);},width:300,align:'left'},
                <c:if test="${!_query}">
                    { label:'排序', width: 80, formatter: $.jgrid.formatter.sortOrder,
                        formatoptions:{url:'${ctx}/cg/cgTeam_changeOrder'}},
                </c:if>
                { label: '类型',name: 'type',width:150,
                    formatter: function (cellvalue, options, rowObject)
                    {return rowObject.type == <%=CgConstants.CG_TEAM_TYPE_MEMBER%>?"委员会":"领导小组"}},
                { label: '类别',name: 'category',width:200,formatter: $.jgrid.formatter.MetaType},
                { label: '概况',name: 'cgTeamBase'},
                { label: '是否需要调整',name: 'needAdjust',
                    formatter: function (cellvalue, options, rowObject) {
                        return cellvalue == true?"<span class='badge badge-danger'> </span>":"--";
                    }},
                { label: '挂靠单位',name: 'cgUnit',width:300,align:'left'},
                { label: '办公室主任',name: 'cgLeader'},
                { label: '联系方式',name: 'cgLeaderPhone'},
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