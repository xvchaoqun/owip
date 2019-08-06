<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.teamId ||not empty param.post ||not empty param.type ||not empty param.unitPostId ||not empty param.userId || not empty param.code || not empty param.sort}"/>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="cgMember:edit">
                    <button class="popupBtn btn btn-info btn-sm"
                            data-url="${ctx}/cg/cgMember_au">
                        <i class="fa fa-plus"></i> 添加</button>
                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/cg/cgMember_au"
                       data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                        修改</button>
                </shiro:hasPermission>
                <shiro:hasPermission name="cgMember:del">
                    <button data-url="${ctx}/cg/cgMember_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
                <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-url="${ctx}/cg/cgMember_data"
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
                            <label>职务</label>
                            <input class="form-control search-query" name="post" type="text" value="${param.post}"
                                   placeholder="请输入职务">
                        </div>
                        <div class="form-group">
                            <label>人员类型</label>
                            <input class="form-control search-query" name="type" type="text" value="${param.type}"
                                   placeholder="请输入人员类型">
                        </div>
                        <div class="form-group">
                            <label>关联岗位</label>
                            <input class="form-control search-query" name="unitPostId" type="text" value="${param.unitPostId}"
                                   placeholder="请输入关联岗位">
                        </div>
                        <div class="form-group">
                            <label>现任干部</label>
                            <input class="form-control search-query" name="userId" type="text" value="${param.userId}"
                                   placeholder="请输入现任干部">
                        </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                   data-url="${ctx}/cg/cgMember"
                                   data-target="#page-content"
                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/cg/cgMember"
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
        url: '${ctx}/cg/cgMember_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
                { label: '所属委员会或领导小组',name: 'teamId'},
                { label: '职务',name: 'post'},
                { label: '人员类型',name: 'type'},
                { label: '关联岗位',name: 'unitPostId'},
                { label: '现任干部',name: 'userId'},
                { label: '代表类型',name: 'tag'},
                { label: '添加日期',name: 'startDate'},
                { label: '移除日期',name: 'endDate'},
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