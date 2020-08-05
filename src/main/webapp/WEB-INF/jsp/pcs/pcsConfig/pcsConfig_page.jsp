<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" class="myTableDiv"
                 data-url-page="${ctx}/pcsConfig_page"
                 data-url-export="${ctx}/pcsConfig_data"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.name || not empty param.code || not empty param.sort}"/>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="pcsConfig:edit">
                    <a class="popupBtn btn btn-info btn-sm"  data-url="${ctx}/pcsConfig_au"><i class="fa fa-plus"></i> 添加</a>
                    <a class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/pcsConfig_au"
                       data-grid-id="#jqGrid"
                       ><i class="fa fa-edit"></i>
                        修改</a>
                </shiro:hasPermission>
                <shiro:hasPermission name="pcsConfig:del">
                    <button data-url="${ctx}/pcsConfig_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
                <shiro:hasPermission name="pcsAdmin:*">
                【注：更改了当前党代会之后，需要重新同步党代会管理员】
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
                            <label>党代会名称</label>
                            <input class="form-control search-query" name="name" type="text" value="${param.name}"
                                   placeholder="请输入党代会名称">
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
        url: '${ctx}/pcsConfig_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '届数',name: 'name', width:300},
            <shiro:hasPermission name="pcsAdmin:*">
            { label: '是否当前党代会',name: 'isCurrent', width:150, formatter:$.jgrid.formatter.TRUEFALSE},
            </shiro:hasPermission>
            <shiro:hasPermission name="pcsProposal:menu">
            {label: '提交提案时间', name: 'proposalSubmitTime', width: 130, formatter: $.jgrid.formatter.date,
                formatoptions: {srcformat:'Y-m-d H:i',newformat:'Y-m-d H:i'}},
            {label: '征集附议人时间', name: 'proposalSupportTime', width: 130, formatter: $.jgrid.formatter.date,
                formatoptions: {srcformat:'Y-m-d H:i',newformat:'Y-m-d H:i'}},
            {label: '立案附议人数', name: 'proposalSupportCount', width: 120},
            </shiro:hasPermission>
            { label: '创建时间',name: 'createTime', width:150},
            { label: '备注',name: 'remark', width:500}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>