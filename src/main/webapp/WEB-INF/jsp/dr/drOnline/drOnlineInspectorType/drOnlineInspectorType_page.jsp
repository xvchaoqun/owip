<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="<%=DrConstants.DR_ONLINE_INSPECTOR_TYPE_MAP%>" var="DR_ONLINE_INSPECTOR_TYPE_MAP"/>
<c:set value="<%=DrConstants.DR_ONLINE_INSPECTOR_TYPE_FORMAL%>" var="DR_ONLINE_INSPECTOR_TYPE_FORMAL"/>
<c:set value="<%=DrConstants.DR_ONLINE_INSPECTOR_TYPE_LOCK%>" var="DR_ONLINE_INSPECTOR_TYPE_LOCK"/>
<c:set value="<%=DrConstants.DR_ONLINE_INSPECTOR_TYPE_CANCEL%>" var="DR_ONLINE_INSPECTOR_TYPE_CANCEL"/>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.type || not empty param.code || not empty param.id || not empty param.sort }"/>
                <div class="tabbable">
                    <jsp:include page="menu.jsp"/>
                    <div class="tab-content multi-row-head-table">
                        <div class="tab-pane in active multi-row-head-table">
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="drOnlineInspectorType:edit">
                    <button class="popupBtn btn btn-info btn-sm"
                            data-url="${ctx}/dr/drOnlineInspectorType_au">
                        <i class="fa fa-plus"></i> 添加</button>
                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/dr/drOnlineInspectorType_au"
                       data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                        修改</button>
                    <button data-url="${ctx}/dr/drOnlineInspectorType_change?status=${DR_ONLINE_INSPECTOR_TYPE_LOCK}"
                            data-title="锁定"
                            data-msg="确定锁定这{0}条数据？"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-warning btn-sm">
                        <i class="fa fa-lock"></i> 锁定
                    </button>
                    <button data-url="${ctx}/dr/drOnlineInspectorType_change?status=${DR_ONLINE_INSPECTOR_TYPE_CANCEL}"
                            data-title="作废"
                            data-msg="确定作废这{0}条数据？"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-times"></i> 作废
                    </button>
                    <button data-url="${ctx}/dr/drOnlineInspectorType_change?status=${DR_ONLINE_INSPECTOR_TYPE_FORMAL}"
                            data-title="恢复"
                            data-msg="确定恢复这{0}条数据？"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-success btn-sm">
                        <i class="fa fa-check"></i> 恢复
                    </button>
                </shiro:hasPermission>
                <shiro:hasPermission name="drOnlineInspectorType:del">
                    <button data-url="${ctx}/dr/drOnlineInspectorType_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？<br/>（删除后无法恢复，请谨慎操作！！）"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
            </div>
            <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
                <div class="widget-header">
                    <h4 class="widget-title">搜索</h4>
                    <span class="widget-note">${note_searchbar}</span>
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
                                <label>参评人身份类型</label>
                                <div class="input-group">
                                    <select  data-width="230" data-rel="select2-ajax"
                                             data-ajax-url="${ctx}/dr/drOnlineInspectorType_selects"
                                             name="id" data-placeholder="请选择参评人身份类型">
                                        <option value="${inspectorType.id}">${inspectorType.type}</option>
                                    </select>
                                </div>
                                <script>
                                    $("#searchForm3 select[name=typeId]").val('${param.id}');
                                </script>
                            </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                   data-url="${ctx}/dr/drOnlineInspectorType?cls=2"
                                   data-target="#page-content"
                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/dr/drOnlineInspectorType?cls=2"
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
        url: '${ctx}/dr/drOnlineInspectorType_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
                { label: '类型名称',name: 'type',width: 150},
                { label: '状态',name: 'status',formatter: function (cellvalue, options, rowObject) {
                        if (cellvalue == ${DR_ONLINE_INSPECTOR_TYPE_FORMAL}) {
                            return '${DR_ONLINE_INSPECTOR_TYPE_MAP.get(DR_ONLINE_INSPECTOR_TYPE_FORMAL)}';
                        } else if (cellvalue == ${DR_ONLINE_INSPECTOR_TYPE_LOCK}) {
                            return '<font color="orange">${DR_ONLINE_INSPECTOR_TYPE_MAP.get(DR_ONLINE_INSPECTOR_TYPE_LOCK)}</front>';
                        } else if (cellvalue == ${DR_ONLINE_INSPECTOR_TYPE_CANCEL}) {
                            return '<font color="red">${DR_ONLINE_INSPECTOR_TYPE_MAP.get(DR_ONLINE_INSPECTOR_TYPE_CANCEL)}</front>';
                        }
                    }},
                { label:'排序', width: 80, formatter: $.jgrid.formatter.sortOrder,
                        formatoptions:{url:'${ctx}/dr/drOnlineInspectorType_changeOrder'},frozen:true }

        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    //$.register.date($('.date-picker'));
</script>