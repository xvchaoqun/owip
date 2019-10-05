<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="myTableDiv"
             data-url-export="${ctx}/ps/psInfo_data"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <div class="tabbable">
                <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                    <li class="<c:if test="${!isHistory}">active</c:if>">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/ps/psInfo?isHistory=0"><i
                                class="fa fa-circle-o-notch fa-spin"></i> 正在运转</a>
                    </li>
                    <li class="<c:if test="${isHistory}">active</c:if>">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/ps/psInfo?isHistory=1"><i
                                class="fa fa-history"></i> 历史</a>
                    </li>
                </ul>
                <div class="tab-content">
                    <div class="tab-pane in active">
                        <c:set var="_query"
                               value="${not empty param.partyId || not empty param.name || not empty param.code || not empty param.sort}"/>
                        <div class="jqgrid-vertical-offset buttons">
                            <c:if test="${!isHistory}">
                            <shiro:hasPermission name="psInfo:edit">
                                <button class="popupBtn btn btn-info btn-sm"
                                        data-url="${ctx}/ps/psInfo_au">
                                    <i class="fa fa-plus"></i> 添加
                                </button>
                                <button class="jqOpenViewBtn btn btn-primary btn-sm"
                                        data-url="${ctx}/ps/psInfo_au"
                                        data-grid-id="#jqGrid">
                                    <i class="fa fa-edit"></i> 修改
                                </button>
                            </shiro:hasPermission>
                            </c:if>
                            <c:if test="${!isHistory}">
                                <shiro:hasPermission name="psInfo:history">
                                    <button class="jqOpenViewBatchBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/ps/psInfo_history">
                                        <i class="fa fa-recycle"></i> 撤销
                                    </button>
                                </shiro:hasPermission>
                            </c:if>
                            <shiro:hasPermission name="psInfo:edit">
                            <c:if test="${isHistory}">
                            <button class="jqBatchBtn btn btn-success btn-sm"
                                    data-title="返回"
                                    data-msg="确定恢复这{0}条数据？"
                                    data-grid-id="#jqGrid"
                                    data-url="${ctx}/ps/psInfo_history?isHistory=0">
                                <i class="fa fa-reply"></i> 返回
                            </button>
                            </c:if>
                                <c:if test="${!isHistory}">
                            <button class="popupBtn btn btn-info btn-sm tooltip-info"
                                    data-url="${ctx}/ps/psInfo_import"
                                    data-rel="tooltip" data-placement="top" title="批量导入"><i class="fa fa-upload"></i>
                                批量导入
                            </button>
                                </c:if>
                            </shiro:hasPermission>

                            <a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                               data-rel="tooltip"
                               data-placement="top"
                               title="导出当前搜索的全部结果（按照当前排序）"><i class="fa fa-download"></i>
                                导出</a>
                            <shiro:hasPermission name="psInfo:del">
                                <button data-url="${ctx}/ps/psInfo_batchDel"
                                        data-title="删除"
                                        data-msg="确定删除这{0}条数据？"
                                        data-grid-id="#jqGrid"
                                        class="jqBatchBtn btn btn-danger btn-sm">
                                    <i class="fa fa-trash"></i> 删除
                                </button>
                            </shiro:hasPermission>
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
                                            <label>二级党校名称</label>
                                            <input class="form-control search-query" name="name" type="text"
                                                   value="${param.name}"
                                                   placeholder="请输入二级党校名称">
                                        </div>
                                        <div class="form-group">
                                            <label>建设单位</label>
                                            <select data-rel="select2-ajax" data-ajax-url="${ctx}/ps/psParty_selects"
                                                    name="partyId" data-placeholder="请输入建设单位名称">
                                                <option value="${party.id}">${party.name}</option>
                                            </select>
                                        </div>
                                        <div class="clearfix form-actions center">
                                            <a class="jqSearchBtn btn btn-default btn-sm"
                                               data-url="${ctx}/ps/psInfo?isHistory=${isHistory}"
                                               data-target="#page-content"
                                               data-form="#searchForm">
                                                <i class="fa fa-search"></i> 查找
                                            </a>
                                            <c:if test="${_query}">&nbsp;
                                                <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                        data-url="${ctx}/ps/psInfo?isHistory=${isHistory}"
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
        url: '${ctx}/ps/psInfo_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '二级党校名称', name: 'name',
                <shiro:hasPermission name="psInfo:view">
                    formatter:function(cellvalue, options, rowObject){
                    return ('<a href="javascript:;" class="openView" ' +
                        'data-url="${ctx}/ps/psInfo_view?id={0}">{1}</a>')
                            .format(rowObject.id, cellvalue);},
                </shiro:hasPermission> width:300, align:'left',frozen:true},
            <shiro:hasPermission name="psInfo:changeOrder">
            <c:if test="${!_query}">
            { label:'排序', formatter: $.jgrid.formatter.sortOrder,
                formatoptions:{url:'${ctx}/ps/psInfo_changeOrder'},frozen:true },
            </c:if>
            </shiro:hasPermission>
            { label: '主建单位', align:'left', name: 'hostId',width:300,
                formatter:function(cellvalue, options, rowObject){
                    return $.party(cellvalue);
                }},
            { label: '联合建设单位', align:'left', name: 'jointIds', width: 500,
                formatter:function (cellvalue, options, rowObject) {

                if (cellvalue == null || cellvalue == undefined) return "--";

                    var jointName = '';
                    var jointIds = cellvalue.split(",");

                    for (let jointId of jointIds) {

                        if (jointName !='') {jointName += '、'}

                            jointName += $.party(jointId);
                        }
                    return jointName;
                }},
            {label: '成立时间', name: 'foundDate', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
            { label: '批次', name: 'seq'},
            { label: '师生党员总人数', width: 120, name: 'countNumber'},
            { label: '校长', name: 'rectorUser.realname'},
            { label: '校长所在单位及职务', name: 'rectorTitle', align:'left', width: 300 },
            { label: '校长联系方式', width: 120, name: 'rectorMobile'},
            { label: '管理员', name: 'adminUser.realname'},
            { label: '管理员联系方式', width: 120, name: 'adminMobile'},
            { label: '备注', align:'left', name: 'remark', width: 500 }
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $.register.user_select($('[data-rel="select2-ajax"]'));
</script>