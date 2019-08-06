<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.teamId ||not empty param.relatePost ||not empty param.unitPostId ||not empty param.userId ||not empty param.phone ||not empty param.confirmDate || not empty param.code || not empty param.sort}"/>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="cgLeader:edit">
                    <button class="popupBtn btn btn-info btn-sm"
                            data-url="${ctx}/cg/cgLeader_au">
                        <i class="fa fa-plus"></i> 添加</button>
                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/cg/cgLeader_au"
                       data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                        修改</button>
                </shiro:hasPermission>
                <shiro:hasPermission name="cgLeader:del">
                    <button data-url="${ctx}/cg/cgLeader_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
                <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-url="${ctx}/cg/cgLeader_data"
                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出</button>
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
                            <label>所属委员会或领导小组</label>
                            <input class="form-control search-query" name="teamId" type="text" value="${param.teamId}"
                                   placeholder="请输入所属委员会或领导小组">
                        </div>
                        <div class="form-group">
                            <label>是否席位制</label>
                            <input class="form-control search-query" name="relatePost" type="text" value="${param.relatePost}"
                                   placeholder="请输入是否席位制">
                        </div>
                        <div class="form-group">
                            <label>关联岗位</label>
                            <input class="form-control search-query" name="unitPostId" type="text" value="${param.unitPostId}"
                                   placeholder="请输入关联岗位">
                        </div>
                        <div class="form-group">
                            <label>用户ID</label>
                            <input class="form-control search-query" name="userId" type="text" value="${param.userId}"
                                   placeholder="请输入用户ID">
                        </div>
                        <div class="form-group">
                            <label>联系方式</label>
                            <input class="form-control search-query" name="phone" type="text" value="${param.phone}"
                                   placeholder="请输入联系方式">
                        </div>
                        <div class="form-group">
                            <label>确定时间</label>
                            <input class="form-control search-query" name="confirmDate" type="text" value="${param.confirmDate}"
                                   placeholder="请输入确定时间">
                        </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                   data-url="${ctx}/cg/cgLeader"
                                   data-target="#page-content"
                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/cg/cgLeader"
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
        rownumbers:true,
        url: '${ctx}/cg/cgLeader_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
                { label: '所属委员会或领导小组',name: 'teamId'},
                { label: '是否席位制',name: 'relatePost'},
                { label: '关联岗位',name: 'unitPostId'},
                { label: '用户ID',name: 'userId'},
                { label: '联系方式',name: 'phone'},
                { label: '确定时间',name: 'confirmDate'},
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