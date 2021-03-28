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
        <c:if test="${!isHistory}">
    <button class="popupBtn btn btn-info btn-sm"
            data-url="${ctx}/ps/psAdmin_au?psId=${param.psId}">
        <i class="fa fa-plus"></i>
        添加</button>
        </c:if>
    <button class="jqOpenViewBtn btn btn-primary btn-sm"
            data-url="${ctx}/ps/psAdmin_au"
            data-grid-id="#jqGrid2">
        <i class="fa fa-edit"></i>
        修改</button>
    </shiro:hasPermission>
    <c:if test="${!isHistory}">
    <shiro:hasPermission name="	psAdmin:history">
    <button class="jqOpenViewBatchBtn btn btn-warning btn-sm"
            data-grid-id="#jqGrid2"
            data-url="${ctx}/ps/psAdmin_history">
        <i class="fa fa-hand-stop-o"></i>
        任职结束</button>
    </shiro:hasPermission>
    </c:if>
    <shiro:hasPermission name="psAdmin:del">
    <button class="jqBatchBtn btn btn-danger btn-sm"
            data-url="${ctx}/ps/psAdmin_batchDel"
            data-title="删除"
            data-msg="确定删除这{0}条数据？<br/>（删除后无法恢复，请谨慎操作！！）"
            data-grid-id="#jqGrid2">
        <i class="fa fa-trash"></i>
        删除</button>
    </shiro:hasPermission>

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
                { label: '管理员类型',name: 'type',width:140, formatter: function (cellvalue, options, rowObject) {return rowObject.type == 1?"二级党校管理员":"院系级党委管理员" }},
                { label: '姓名',name: 'user.realname'},
                { label: '学工号',name: 'user.code', width: 120},
                <shiro:hasPermission name="psAdmin:edit">
                { label:'排序', width: 85, formatter: $.jgrid.formatter.sortOrder,
                    formatoptions:{grid:'#jqGrid2',url:'${ctx}/ps/psAdmin_changeOrder'},frozen:true },
                </shiro:hasPermission>
                { label: '所在单位及职务',name: 'title',width: 250, align:'left'},
                { label: '管理的单位', name: 'memberCount', width: 150, formatter: function (cellvalue, options, rowObject) {
                    if (rowObject.type == '<%=PsConstants.PS_ADMIN_TYPE_PARTY%>')  return '--'
                        var notEmptyParty = (rowObject.countParty == 0);
                        return ('<button class="popupBtn btn btn-{2} btn-xs"' +
                            'data-url="${ctx}/ps/psAdminParty?adminId={0}" ' +
                            'data-width="800"><i class="fa fa-{3}"></i> ' +
                            '{1}</button>').format(rowObject.id,
                            notEmptyParty?'编辑':'查看('+rowObject.countParty+')',
                            notEmptyParty?'primary':'success ',
                            notEmptyParty?'edit':'search');
                    }},
                { label: '任职起始时间',name: 'startDate',width: 150, formatter: $.jgrid.formatter.date,formatoptions: {newformat: 'Y.m.d'}},
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