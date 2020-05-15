<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.cadreId || not empty param.code || not empty param.sort}"/>
                <div class="tabbable">
                    <jsp:include page="/WEB-INF/jsp/verify/verify_menu.jsp"/>
                    <div class="tab-content">
                        <div class="tab-pane in active">
                            <div class="jqgrid-vertical-offset buttons">
                                <shiro:hasPermission name="verifyJoinPartyTime:edit">
                                    <button class="popupBtn btn btn-info btn-sm"
                                            data-url="${ctx}/verify/verifyJoinPartyTime_au">
                                        <i class="fa fa-plus"></i> 添加认定</button>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="verifyJoinPartyTime:del">
                                    <button data-url="${ctx}/verify/verifyJoinPartyTime_batchDel"
                                            data-title="删除"
                                            data-msg="确定删除这{0}条数据？"
                                            data-grid-id="#jqGrid"
                                            class="jqBatchBtn btn btn-danger btn-sm">
                                        <i class="fa fa-trash"></i> 删除
                                    </button>
                                </shiro:hasPermission>
                                <button class="jqOpenViewBtn btn btn-warning btn-sm"
                                        data-url="${ctx}/verify/verifyJoinPartyTimeLog"
                                        data-open-by="page">
                                    <i class="fa fa-search"></i> 认定记录
                                </button>
                                <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                                   data-url="${ctx}/verify/verifyJoinPartyTime_data"
                                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                                    <i class="fa fa-download"></i> 导出</button>
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
                                                <label>选择干部</label>
                                                <select data-rel="select2-ajax"
                                                        data-ajax-url="${ctx}/cadre_selects"
                                                        name="cadreId" data-placeholder="请输入账号或姓名或学工号"  data-width="270">
                                                    <option value="${cadre.id}">${cadre.realname}-${cadre.code}</option>
                                                </select>
                                            </div>
                                            <div class="clearfix form-actions center">
                                                <a class="jqSearchBtn btn btn-default btn-sm"
                                                   data-url="${ctx}/verify/verifyJoinPartyTime"
                                                   data-target="#page-content"
                                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                                <c:if test="${_query}">&nbsp;
                                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                            data-url="${ctx}/verify/verifyJoinPartyTime"
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
        url: '${ctx}/verify/verifyJoinPartyTime_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
                { label: '工作证号',name: 'cadre.code'},
                { label: '姓名',name: 'cadre.realname'},
                { label: '所在单位及职务',name: 'cadre.title', align: 'left', width: 350},
                { label: '认定前入党时间',name: 'oldJoinTime',width:180,formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
                { label: '认定后入党时间',name: 'verifyJoinTime',width:180,formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}},
                { label: '认定',name: '_verify',formatter: function (cellvalue, options, rowObject) {
                        if ($.trim(rowObject.oldJoinTime)=='')
                            return '<button class="openView btn btn-success btn-xs" data-url="${ctx}/verify/verifyJoinPartyTime_verify?id={0}"><i class="fa fa-check"></i> 认定</button>'
                                .format(rowObject.id);
                        else
                            return '<button class="openView btn btn-primary btn-xs" data-url="${ctx}/verify/verifyJoinPartyTime_verify?id={0}"><i class="fa fa-search"></i> 查看</button>'
                                .format(rowObject.id, cellvalue);
                    }},
                { label: '备注',name: 'remark',width: 500}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$.register.date($('.date-picker'));
</script>