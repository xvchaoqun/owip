<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.unitId ||not empty param.name ||not empty param.adminLevel ||not empty param.postType ||not empty param.postClass || not empty param.code || not empty param.sort}"/>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="unitPost:edit">
                    <button class="popupBtn btn btn-info btn-sm"
                            data-url="${ctx}/unitPost_au">
                        <i class="fa fa-plus"></i> 添加</button>
                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/unitPost_au"
                       data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                        修改</button>
                </shiro:hasPermission>
                <shiro:hasPermission name="unitPost:del">
                    <button data-url="${ctx}/unitPost_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
                <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-url="${ctx}/unitPost_data"
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
                            <label>所属单位</label>
                            <input class="form-control search-query" name="unitId" type="text" value="${param.unitId}"
                                   placeholder="请输入所属单位">
                        </div>
                        <div class="form-group">
                            <label>岗位名称</label>
                            <input class="form-control search-query" name="name" type="text" value="${param.name}"
                                   placeholder="请输入岗位名称">
                        </div>
                        <div class="form-group">
                            <label>行政级别</label>
                            <input class="form-control search-query" name="adminLevel" type="text" value="${param.adminLevel}"
                                   placeholder="请输入行政级别">
                        </div>
                        <div class="form-group">
                            <label>职务属性</label>
                            <input class="form-control search-query" name="postType" type="text" value="${param.postType}"
                                   placeholder="请输入职务属性">
                        </div>
                        <div class="form-group">
                            <label>职务类别</label>
                            <input class="form-control search-query" name="postClass" type="text" value="${param.postClass}"
                                   placeholder="请输入职务类别">
                        </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                   data-url="${ctx}/unitPost"
                                   data-target="#page-content"
                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="resetBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/unitPost"
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
        url: '${ctx}/unitPost_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
                { label: '岗位编号',name: 'code'},
                { label: '岗位名称',name: 'name'},
                { label: '分管工作',name: 'job'},
                { label: '是否正职',name: 'isPrincipalPost'},
                { label: '行政级别',name: 'adminLevel'},
                { label: '职务属性',name: 'postType'},
                { label: '职务类别',name: 'postClass'},
                { label: '是否占干部职数',name: 'isCpc'},
                { label: '状态',name: 'status'},
                <c:if test="${!_query}">
                { label:'排序',align:'center', formatter: $.jgrid.formatter.sortOrder,
                    formatoptions:{url:'${ctx}/unitPost_changeOrder'},frozen:true },
                </c:if>
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