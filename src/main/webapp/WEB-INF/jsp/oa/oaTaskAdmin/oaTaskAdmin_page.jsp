<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers multi-row-head-table"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query"
                   value="${not empty param.userId ||not empty param.type
                   || not empty param.code || not empty param.sort}"/>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="oaTaskAdmin:edit">
                    <button class="popupBtn btn btn-info btn-sm"
                            data-url="${ctx}/oa/oaTaskAdmin_au">
                        <i class="fa fa-plus"></i> 添加
                    </button>
                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                            data-url="${ctx}/oa/oaTaskAdmin_au"
                            data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                        修改
                    </button>
                </shiro:hasPermission>
                <shiro:hasPermission name="oaTaskAdmin:del">
                    <button data-url="${ctx}/oa/oaTaskAdmin_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
                <%--<button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                        data-url="${ctx}/oa/oaTaskAdmin_data"
                        data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出
                </button>--%>
            </div>
            <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
                <div class="widget-header">
                    <h4 class="widget-title">搜索</h4><span class="widget-note">${note_searchbar}</span>

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
                                <label>用户ID</label>
                                <select data-rel="select2-ajax" data-width="200"
                                        data-ajax-url="${ctx}/sysUser_selects?types=${USER_TYPE_JZG}"
                                        name="userId" data-placeholder="请输入账号或姓名或工号">
                                    <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>工作类型</label>
                                <select data-rel="select2"
                                        name="type" data-placeholder="请选择">
                                    <option></option>
                                    <c:import url="/metaTypes?__code=mc_oa_task_type"/>
                                </select>
                                <script type="text/javascript">
                                    $("#searchForm select[name=type]").val(${param.type});
                                </script>
                            </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                   data-url="${ctx}/oa/oaTaskAdmin"
                                   data-target="#page-content"
                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/oa/oaTaskAdmin"
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
        rownumbers: true,
        url: '${ctx}/oa/oaTaskAdmin_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '管理员姓名', name: 'user.realname'},
            {label: '学工号', name: 'user.code', width: 120},
            {label: '允许查看<br/>所有任务', name: 'showAll', width: 80, formatter:$.jgrid.formatter.TRUEFALSE},
            {
                label: '工作类型', name: 'types', width: 500, align:'left', formatter: function (cellvalue, options, rowObject) {
                    var types = rowObject.types;
                    if ($.trim(types) == '') return '--'
                    var typeArray = $.map(types.split(","), function (type) {
                        return $.jgrid.formatter.MetaType(type);
                    })
                    return typeArray.join("、")
                }
            },{name:'userId', hidden:true, key:true}
        ]
    }).jqGrid("setFrozenColumns").jqGrid("setLabel", "types", "", {'text-align':'left'});
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$.register.date($('.date-picker'));
</script>