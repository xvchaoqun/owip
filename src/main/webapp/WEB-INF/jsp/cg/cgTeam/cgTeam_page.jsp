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
                        <c:set var="_query" value="${not empty param.name ||not empty param.type ||not empty param.category || not empty param.unitId || not empty param.userId}"/>
                        <div class="jqgrid-vertical-offset buttons">
                            <shiro:hasPermission name="cgTeam:edit">
                                <c:if test="${isCurrent}">
                                <button class="popupBtn btn btn-info btn-sm"
                                        data-url="${ctx}/cg/cgTeam_au"><i class="fa fa-plus"></i>
                                    添加</button>

                                </c:if>
                                <button class="jqOpenViewBtn btn btn-primary btn-sm"
                                        data-url="${ctx}/cg/cgTeam_au"
                                        data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                                    修改</button>

                            </shiro:hasPermission>
                            <c:if test="${isCurrent}">
                            <button class="jqBatchBtn btn btn-warning btn-sm"
                                    data-url="${ctx}/cg/cgTeam_plan?isCurrent=0"
                                    data-title="撤销"
                                    data-msg="确定撤销这{0}条数据？"
                                    data-grid-id="#jqGrid"><i class="fa fa-recycle"></i>
                                撤销</button>

                                <button class="popupBtn btn btn-info btn-sm tooltip-info"
                                        data-url="${ctx}/cg/cgTeam_import"
                                        data-rel="tooltip" data-placement="top" title="批量导入"><i class="fa fa-upload"></i>
                                批量导入</button>

                            </c:if>
                            <c:if test="${!isCurrent}">
                                <button class="jqBatchBtn btn btn-warning btn-sm"
                                        data-url="${ctx}/cg/cgTeam_plan?isCurrent=1"
                                        data-title="返回"
                                        data-msg="确定重新使用这{0}条数据？"
                                        data-grid-id="#jqGrid"><i class="fa fa-backward"></i>
                                    返回</button>

                            </c:if>
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
                                        <input class="hidden" name="isCurrent" value="${param.isCurrent}">
                                        <div class="form-group">
                                            <label>名称</label>
                                            <input class="form-control search-query" name="name" type="text" value="${param.name}"
                                                   placeholder="请输入名称">
                                        </div>
                                        <div class="form-group">
                                            <label>类型</label>
                                            <select id="typeSelect" name="type" data-placeholder="请选择管理员类型"
                                                    data-rel="select2">
                                                <option></option>
                                                <c:forEach items="<%=CgConstants.CG_TEAM_TYPE_MAP%>" var="cgTeamType">
                                                    <option value="${cgTeamType.key}">${cgTeamType.value}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <script>
                                            $("#typeSelect").val('${param.type}');
                                        </script>
                                        <div class="form-group">
                                            <label>类别</label>
                                            <select class="col-xs-6" name="category"
                                                    data-rel="select2" data-placeholder="请选择">
                                                <option></option>
                                                <c:import url="/metaTypes?__code=mc_cg_type"/>
                                            </select>
                                        </div>
                                        <script>
                                            $("select[name=category]").val('${param.category}');
                                        </script>
                                        <div class="form-group">
                                            <label>挂靠单位</label>
                                            <select data-rel="select2-ajax"
                                                    data-ajax-url="${ctx}/unit_selects"
                                                    name="unitId" data-placeholder="请选择单位">
                                                <option value="${param.unitId}">${unit.name}</option>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label>办公室主任</label>
                                            <select data-rel="select2-ajax"
                                                    data-ajax-url="${ctx}/cadre_selects?key=1"
                                                    name="userId" data-placeholder="请选择">
                                                <option value="${param.userId}">${user.realname}</option>
                                            </select>
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
                { label: '委员会和领导小组名称',name: 'name', formatter:function(cellvalue, options, rowObject){

                    return ('<a href="javascript:;" class="openView" data-url="${ctx}/cg/cgTeam_view?teamId={0}">{1}</a>')
                            .format(rowObject.id, cellvalue);},width:300,align:'left'
                },
                <c:if test="${!_query}">
                    { label:'排序', width: 80, formatter: $.jgrid.formatter.sortOrder,
                        formatoptions:{url:'${ctx}/cg/cgTeam_changeOrder'}},
                </c:if>
                { label: '类型',name: 'type', formatter: function (cellvalue, options, rowObject) {

                    return rowObject.type == <%=CgConstants.CG_TEAM_TYPE_MEMBER%>?"委员会":"领导小组"
                }},
                { label: '类别',name: 'category',width: 200,formatter: $.jgrid.formatter.MetaType},
                { label: '概况',name: 'cgTeamBase',width: 80,formatter:function(cellvalue, options, rowObject){

                        return '<button class="openView btn btn-success btn-xs" data-url="${ctx}/cg/cgTeam_base?id={0}"><i class="fa fa-search"></i> {1}</button>'
                            .format(rowObject.id, '详情');
                }},
                { label: '是否需要调整',name: 'countNeedAdjust', formatter: function (cellvalue, options, rowObject) {

                    return cellvalue != 0?"<span class='badge badge-danger'>"+cellvalue+"</span>":"--";
                }},
                { label: '挂靠单位',name: 'unit.name',width:250,align:'left'},
                { label: '办公室主任',name: 'user.realname'},
                { label: '联系方式',name: 'phone',width: 130},
                { label: '备注',name: 'remark',width: 200}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
    $.register.user_select($('[data-rel="select2-ajax"]'));
</script>