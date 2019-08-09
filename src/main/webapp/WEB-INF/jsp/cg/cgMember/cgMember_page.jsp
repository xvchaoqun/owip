<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.post ||not empty param.type ||not empty param.unitPostId ||not empty param.userId || not empty param.code || not empty param.sort}"/>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="cgMember:edit">
                    <button class="popupBtn btn btn-info btn-sm"
                            data-url="${ctx}/cg/cgMember_au?teamId=${param.teamId}">
                        <i class="fa fa-plus"></i> 添加</button>
                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                            data-url="${ctx}/cg/cgMember_au"
                            data-grid-id="#jqGrid2"><i class="fa fa-edit"></i>
                        修改</button>
                </shiro:hasPermission>
                <shiro:hasPermission name="cgMember:del">
                    <button class="jqBatchBtn btn btn-danger btn-sm"
                            data-url="${ctx}/cg/cgMember_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid2"><i class="fa fa-trash"></i>
                        删除</button>
                </shiro:hasPermission>
                <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                        title="导出选中记录或所有搜索结果"
                        data-url="${ctx}/cg/cgMember_data"
                        data-rel="tooltip"
                        data-placement="top"><i class="fa fa-download"></i>
                    导出</button>
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
                                <input class="form-control search-query" name="teamId" type="text" value=""
                                       placeholder="请输入所属委员会或领导小组">
                            </div>
                            <div class="form-group">
                                <label>职务</label>
                                <input class="form-control search-query" name="post" type="text" value=""
                                       placeholder="请输入职务">
                            </div>
                            <div class="form-group">
                                <label>人员类型</label>
                                <input class="form-control search-query" name="type" type="text" value=""
                                       placeholder="请输入人员类型">
                            </div>
                            <div class="form-group">
                                <label>关联岗位</label>
                                <input class="form-control search-query" name="unitPostId" type="text" value=""
                                       placeholder="请输入关联岗位">
                            </div>
                            <div class="form-group">
                                <label>现任干部</label>
                                <input class="form-control search-query" name="userId" type="text" value=""
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
                                            data-target="#page-content"><i class="fa fa-reply"></i>
                                        重置</button>
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
    <div id="body-content-view"></div>
</div>
<script>
    $("#jqGrid2").jqGrid({
        rownumbers:true,
        pager:"jqGridPager2",
        url: '${ctx}/cg/cgMember_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
                { label: '职务',name: 'post', formatter: $.jgrid.formatter.MetaType},
                { label:'排序', width: 85, formatter: $.jgrid.formatter.sortOrder,
                formatoptions:{grid:'#jqGrid2',url:'${ctx}/cg/cgMember_changeOrder'}
                },
                { label: '人员类型',name: 'type',formatter: function (cellvalue, options, rowObject)
                {return cellvalue == <%=CgConstants.CG_MEMBER_TYPE_CADRE%>?'现任干部':'各类代表'}
                },
                { label: '关联岗位名称',name: 'unitPost.name',width: 250,align:'left'},
                { label: '现任干部',name: 'user.realname'},
                { label: '代表类型',name: 'tag'},
                {label: '代表姓名',name: 'userIds'}
        ]}).jqGrid("setFrozenColumns");

    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");
</script>