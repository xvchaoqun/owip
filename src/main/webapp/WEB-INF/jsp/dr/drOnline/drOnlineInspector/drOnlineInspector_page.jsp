<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="<%=DrConstants.INSPECTOR_STATUS_MAP%>" var="INSPECTOR_STATUS_MAP"/>
<div class="widget-box transparent">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:;" class="openView btn btn-xs btn-success"
               data-url="${ctx}/dr/drOnlineInspectorLog_menu?onlineId=${onlineId}">
                <i class="ace-icon fa fa-backward"></i> 返回</a>
        </h4>
    </div>
    <div class="widget-body">
        <div class="widget-main padding-4">
            <div class="tab-content padding-8">

            <c:set var="_query" value="${not empty param.unitId || not empty param.typeId || not empty param.id ||not empty param.status ||not empty param.username || not empty param.code || not empty param.sort}"/>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="drOnlineInspector:edit">
                    <button data-url="${ctx}/dr/drOnlineInspector_cancel"
                            data-title="作废"
                            data-msg="确定作废这{0}条数据？"
                            data-grid-id="#jqGrid2"
                            class="jqBatchBtn btn btn-warning btn-sm">
                        <i class="fa fa-history"></i> 作废
                    </button>
                </shiro:hasPermission>
                <shiro:hasPermission name="drOnlineInspector:del">
                    <button data-url="${ctx}/dr/drOnlineInspector_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid2"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
                <shiro:hasPermission name="drOnlineInspector:edit">
                    <a class="btn btn-primary btn-sm" target="_blank"
                       href="${ctx}/dr/drOnlineInspector_print?logId=${logId}&onlineId=${onlineId}"
                       data-grid-id="#jqGrid2"
                       data-rel="tooltip" title="打印该页所有搜索结果">
                        <i class="fa fa-download"></i> 打印
                    </a>
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
                    <form class="form-inline search-form" id="searchForm3">
                        <input type="hidden" name="onlineId" value="${onlineId}"/>
                        <input type="hidden" name="logId" value="${logId}"/>
                        <div class="form-group">
                            <label>登陆账号</label>
                            <input class="form-control search-query" name="username" type="text" value="${param.username}"
                                   placeholder="请输入登陆账号">
                        </div>
                        <div class="form-group">
                            <label>类别</label>
                            <div class="input-group">
                                <select  data-width="230" data-rel="select2-ajax"
                                         data-ajax-url="${ctx}/dr/drOnlineInspectorType_selects"
                                         name="typeId" data-placeholder="请选择参评人身份类型">
                                    <option value="${inspectorType.id}">${inspectorType.type}</option>
                                </select>
                            </div>
                            <script>
                                $("#searchForm3 select[name=typeId]").val('${param.typeId}');
                            </script>
                        </div><%--
                        <div class="form-group">
                            <label>所属单位</label>
                            <select name="unitId" data-rel="select2-ajax" data-ajax-url="${ctx}/unit_selects"
                                    data-placeholder="请选择所属单位">
                                <option value="${unit.id}" delete="${unit.status==UNIT_STATUS_HISTORY}">${unit.name}</option>
                            </select>
                            <script>
                                $.register.del_select($("#searchForm select[name=unitId]").val('${unit.id}'), 250)
                            </script>
                        </div>--%>
                    <div class="form-group">
                        <label>状态</label>
                        <select name="status" data-width="100" data-rel="select2"
                                data-placeholder="请选择">
                            <option></option>
                            <c:forEach items="${INSPECTOR_STATUS_MAP}" var="entity">
                                <option value="${entity.key}">${entity.value}</option>
                            </c:forEach>
                        </select>
                        <script>
                            $("#searchForm3 select[name=status]").val('${param.status}');
                        </script>
                    </div>
                        <div class="clearfix form-actions center">
                            <a class="jqSearchBtn btn btn-default btn-sm"
                               data-url="${ctx}/dr/drOnlineInspector?onlineId=${onlineId}&logId=${logId}"
                               data-target="#body-content-view"
                               data-form="#searchForm3"><i class="fa fa-search"></i> 查找</a>
                            <c:if test="${_query}">&nbsp;
                                <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                        data-url="${ctx}/dr/drOnlineInspector?onlineId=${onlineId}&logId=${logId}"
                                        data-target="#body-content-view">
                                    <i class="fa fa-reply"></i> 重置
                                </button>
                            </c:if>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div class="space-4"></div>
        <table id="jqGrid2" class="jqGrid2 table-striped" data-height-reduce="55"></table>
        <div id="jqGridPager2"></div>
            </div>
        </div>
    </div>
</div>
<script>

    $("#jqGrid2").jqGrid({
        pager: "jqGridPager2",
        url: '${ctx}/dr/drOnlineInspector_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
                { label: '登陆账号',name: 'username'},
                { label: '登陆密码',name: 'passwd'},
                { label: '参评人身份类型',name: 'inspectorType.type', width: 150},
                { label: '所属单位', name: 'unitId', align:'left', formatter: $.jgrid.formatter.unit, width: 200},
                { label: '状态',name: 'status', formatter: function(cellvalue, options, rowObject) {

                        return (rowObject.isMobile?"<i class='fa fa-mobile-phone'></i> ":"")
                            + _cMap.INSPECTOR_STATUS_MAP[cellvalue];
                }},
                { label: '提交时间',name: 'submitTime', width: 150, formatter: $.jgrid.formatter.date, formatoptions: {srcformat:'Y.m.d H:i:s',newformat: 'Y.m.d H:i:s'}},
                { label: 'IP',name: 'submitIp', width:120},
                { label: '创建时间',name: 'createTime', width: 150, formatter: $.jgrid.formatter.date, formatoptions: {srcformat:'Y.m.d H:i:s',newformat: 'Y.m.d H:i:s'}},
                { label: '备注',name: 'remark'}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");
    $.register.ajax_select($('[data-rel="select2-ajax"]'));
    $('#searchForm3 [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $.register.date($('.date-picker'));
</script>