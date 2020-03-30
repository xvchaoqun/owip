<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="<%=DrConstants.INSPECTOR_STATUS_INIT%>" var="INSPECTOR_STATUS_INIT"/>
<c:set value="<%=DrConstants.INSPECTOR_STATUS_ABOLISH%>" var="INSPECTOR_STATUS_ABOLISH"/>
<c:set value="<%=DrConstants.INSPECTOR_STATUS_FINISH%>" var="INSPECTOR_STATUS_FINISH"/>
<c:set value="<%=DrConstants.INSPECTOR_STATUS_SAVE%>" var="INSPECTOR_STATUS_SAVE"/>
<c:set value="<%=DrConstants.INSPECTOR_PUB_STATUS_RELEASE%>" var="INSPECTOR_PUB_STATUS_RELEASE"/>
<c:set value="<%=DrConstants.INSPECTOR_PUB_STATUS_NOT_RELEASE%>" var="INSPECTOR_PUB_STATUS_NOT_RELEASE"/>
<div class="widget-box transparent" id="view-box">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:;" class="openView btn btn-xs btn-success"
               data-url="${ctx}/dr/drOnlineInspectorLog_menu?onlineId=${onlineId}">
                <i class="ace-icon fa fa-backward"></i> 返回</a>
        </h4>
    </div>
    <div class="widget-body">
        <div class="widget-main padding-12 no-padding-left no-padding-right no-padding-bottom">
            <c:set var="_query" value="${not empty param.id ||not empty param.pubStatus ||not empty param.status ||not empty param.username || not empty param.code || not empty param.sort}"/>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="drOnlineInspector:edit">
                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                            data-url="${ctx}/dr/drOnlineInspector_au"
                            data-grid-id="#jqGrid2"><i class="fa fa-edit"></i>
                        修改密码</button>
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
                       href="${ctx}/dr/drOnlineInspector_print?logId=${logId}&onlineId=${onlineId}" title="导出该页所有搜索结果">
                        <i class="fa fa-download"></i> 导出
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
                    <form class="form-inline search-form" id="searchForm">
                    <div class="form-group">
                        <label>ID</label>
                        <input class="form-control search-query" name="id" type="text" value="${param.id}"
                               placeholder="请输入ID">
                    </div>
                    <div class="form-group">
                        <label>分发状态</label>
                        <input class="form-control search-query" name="pubStatus" type="text" value="${param.pubStatus}"
                               placeholder="请输入分发状态（0未分发 1已分发）">
                    </div>
                    <div class="form-group">
                        <label>状态</label>
                        <input class="form-control search-query" name="status" type="text" value="${param.status}"
                               placeholder="请输入状态">
                    </div>
                    <div class="form-group">
                        <label>登陆账号</label>
                        <input class="form-control search-query" name="username" type="text" value="${param.username}"
                               placeholder="请输入登陆账号">
                    </div>
                        <div class="clearfix form-actions center">
                            <a class="jqSearchBtn btn btn-default btn-sm"
                               data-url="${ctx}/dr/drOnlineInspector"
                               data-target="#page-content"
                               data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                            <c:if test="${_query}">&nbsp;
                                <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                        data-url="${ctx}/dr/drOnlineInspector"
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
        <table id="jqGrid2" class="jqGrid2 table-striped"></table>
        <div id="jqGridPager2"></div>
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
                { label: '更改密码方式',name: 'passwdChangeType', formatter: function(cellvalue, options, rowObject) {
                        if (cellvalue == 1) {
                            return "本人更改";
                        }else if (cellvalue == 2) {
                            return "管理员重置";
                        }else {
                            return "未修改";
                        }
                    }},
                { label: '推荐人身份类型',name: 'inspectorType.type', width: 150},
                { label: '所属单位',name: 'unitId', formatter: $.jgrid.formatter.unit, width: 250},
                { label: '测评状态',name: 'status', formatter: function(cellvalue, options, rowObject) {
                        var isMobile = rowObject.isMobile;
                        var str = "";
                        if (isMobile){
                            str = "<i class='fa fa-mobile-phone'></i>";
                        }

                    
                        if (cellvalue == ${INSPECTOR_STATUS_INIT}) {
                            return "可使用";
                        }else if (cellvalue == ${INSPECTOR_STATUS_ABOLISH}) {
                            return "已作废";
                        }else if (cellvalue == ${INSPECTOR_STATUS_FINISH}) {
                            return str == null ? "" : str += "已完成";
                        }else if (cellvalue == ${INSPECTOR_STATUS_SAVE}) {
                            return str == null ? "" : str += "暂存";
                        }
                    }},
                { label: '分发状态',name: 'pubStatus', formatter: function (cellvalue, options, rowObject) {
                        if (cellvalue == ${INSPECTOR_PUB_STATUS_NOT_RELEASE})
                            return "未分发";
                        else
                            return "已分发";
                    }}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $.register.date($('.date-picker'));
</script>