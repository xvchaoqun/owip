<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="_query" value="${not empty param.type ||not empty param.userId || not empty param.code || not empty param.sort}"/>
<ul class="jqgrid-vertical-offset nav nav-tabs padding-12 tab-color-blue background-blue">
    <li class="<c:if test="${!isHistory}">active</c:if>">
        <a href="javascript:;" class="loadPage"
           data-load-el="#tab-content"
           data-url="${ctx}/ps/psAdmin?isHistory=0&psId=${param.psId}">
            <i class="fa fa-circle-o-notch"></i> 现任管理员</a>
    </li>
    <li class="<c:if test="${isHistory}">active</c:if>">
        <a href="javascript:;" class="loadPage"
           data-load-el="#tab-content"
           data-url="${ctx}/ps/psAdmin?isHistory=1&psId=${param.psId}">
            <i class="fa fa-history"></i> 离任管理员</a>
    </li>
</ul>
<div class="space-4"></div>
<div class="jqgrid-vertical-offset buttons">
    <shiro:hasPermission name="psAdmin:edit">
    <button class="popupBtn btn btn-info btn-sm"
            data-url="${ctx}/ps/psAdmin_au?psId=${param.psId}">
        <i class="fa fa-plus"></i>
        添加</button>
    <button class="jqOpenViewBtn btn btn-primary btn-sm"
            data-url="${ctx}/ps/psAdmin_au"
            data-grid-id="#jqGrid2">
        <i class="fa fa-edit"></i>
        修改</button>
    </shiro:hasPermission>
    <shiro:hasPermission name="psAdmin:del">
    <button class="jqBatchBtn btn btn-danger btn-sm"
            data-url="${ctx}/ps/psAdmin_batchDel"
            data-title="删除"
            data-msg="确定删除这{0}条数据？"
            data-grid-id="#jqGrid2">
        <i class="fa fa-trash"></i>
        删除</button>
    </shiro:hasPermission>
    <shiro:hasPermission name="	psAdmin:history">
    <button class="jqOpenViewBatchBtn btn btn-warning btn-sm"
            data-grid-id="#jqGrid2"
            data-url="${ctx}/ps/psAdmin_history">
        <i class="fa fa-hand-stop-o"></i>
        任职结束</button>
    </shiro:hasPermission>
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
                    <label>所属二级党校</label>
                    <input class="form-control search-query" name="psId" type="text" value="${param.psId}"
                           placeholder="请输入所属二级党校"/>
                </div>
                <div class="form-group">
                    <label>类型</label>
                    <input class="form-control search-query" name="type" type="text" value="${param.type}"
                           placeholder="请输入类型"/>
                </div>
                <div class="form-group">
                    <label>管理员</label>
                    <input class="form-control search-query" name="userId" type="text" value="${param.userId}"
                           placeholder="请输入管理员"/>
                </div>
                <div class="clearfix form-actions center">
                    <a class="jqSearchBtn btn btn-default btn-sm"
                       data-url="${ctx}/ps/psAdmin"
                       data-target="#page-content"
                       data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                    <c:if test="${_query}">&nbsp;
                        <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                data-url="${ctx}/ps/psAdmin"
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
<div id="body-content-view"></div>
<script>
    $("#jqGrid2").jqGrid({
        rownumbers:true,
        url: '${ctx}/ps/psAdmin_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        pager:"jqGridPager2",
        colModel: [
                { label: '管理员类型',name: 'type',width:200, formatter: function (cellvalue, options, rowObject) {return rowObject.type == 1?"二级党校管理员":"院系级党委管理员" }},
                { label: '姓名',name: 'user.realname'},
                { label:'排序', width: 85, formatter: $.jgrid.formatter.sortOrder,
                    formatoptions:{grid:'#jqGrid2',url:'${ctx}/ps/psAdmin_changeOrder'},frozen:true },
                { label: '所在单位及职务',name: 'title',width: 200},
                { label: '管理的单位', name: 'memberCount', width: 150, formatter: function (cellvalue, options, rowObject) {
                        return ('<button class="popupBtn btn btn-success btn-xs"' +
                            'data-url="${ctx}/ps/psAdminParty?adminId={0}" ' +
                            'data-width="800"><i class="fa fa-search"></i> ' +
                            '{1}({2})</button>').format(rowObject.id, rowObject.id==null?'编辑':'查看',rowObject.id);
                    }},
                { label: '任职起始时间',name: 'startDate',width: 150, formatter: $.jgrid.formatter.date,formatoptions: {newformat: 'Y-m-d'}},
                { label: '联系方式',name: 'mobile',width: 200},
                { label: '备注',name: 'remark',width: 250}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$.register.date($('.date-picker'));
    function openView(adminId, pageNo){
        pageNo = pageNo||1;
        $.loadModal( "${ctx}/ps/psAdminParty?adminId="+adminId + "&pageNo="+pageNo, 800);
    }
</script>