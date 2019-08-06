<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="_query" value="${not empty param.seq ||not empty param.type ||not empty param.userId || not empty param.code || not empty param.sort}"/>
<ul class="jqgrid-vertical-offset nav nav-tabs padding-12 tab-color-blue background-blue">
    <li class="<c:if test="${!isHistory}">active</c:if>">
        <a href="javascript:;" class="loadPage"
           data-load-el="#tab-content"
           data-url="${ctx}/ps/psMember?isHistory=0&psId=${param.psId}">
            <i class="fa fa-circle-o-notch"></i> 现任班子成员
        </a>
    </li>
    <li class="<c:if test="${isHistory}">active</c:if>">
        <a href="javascript:;" class="loadPage"
           data-load-el="#tab-content"
           data-url="${ctx}/ps/psMember?isHistory=1&psId=${param.psId}">
            <i class="fa fa-history"></i> 离任班子成员
        </a>
    </li>
</ul>
<div class="space-4"></div>
<div class="jqgrid-vertical-offset buttons">
    <c:if test="${!isHistory}">
        <shiro:hasPermission name="psMember:edit">
        <button class="popupBtn btn btn-info btn-sm"
                data-url="${ctx}/ps/psMember_au?psId=${param.psId}">
            <i class="fa fa-plus"></i>
            添加</button>
        <button class="jqOpenViewBtn btn btn-primary btn-sm"
                data-url="${ctx}/ps/psMember_au?psId=${param.psId}"
                data-grid-id="#jqGrid2">
            <i class="fa fa-edit"></i>
            修改</button>
        </shiro:hasPermission>
    </c:if>
    <shiro:hasPermission name="psMember:history">
    <c:if test="${!isHistory}">
        <button class="jqOpenViewBatchBtn btn btn-warning btn-sm"
                data-grid-id="#jqGrid2"
                data-url="${ctx}/ps/psMember_history">
            <i class="fa fa-hand-stop-o"></i>
            任职结束</button>
    </c:if>
    </shiro:hasPermission>
    <shiro:hasPermission name="psMember:del">
    <button class="jqBatchBtn btn btn-danger btn-sm"
            data-url="${ctx}/ps/psMember_batchDel"
            data-title="删除"
            data-msg="确定删除这{0}条数据？"
            data-grid-id="#jqGrid2">
        <i class="fa fa-trash"></i>
        删除</button>
    </shiro:hasPermission>
</div>
<div class="space-4"></div>
<table id="jqGrid2" class="jqGrid2 table-striped" data-height-reduce="30" data-width-reduce="20"></table>
<div id="jqGridPager2"></div>
<script>
    $("#jqGrid2").jqGrid({
        rownumbers:true,
        pager:"jqGridPager2",
        url: '${ctx}/ps/psMember_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
                { label: '党校职务',name: 'type',formatter: $.jgrid.formatter.MetaType},
                { label: '姓名',name: 'user.realname'},
                {label: '学工号', name: 'user.code'},
                { label:'排序', width: 85, formatter: $.jgrid.formatter.sortOrder,
                formatoptions:{grid:'#jqGrid2',url:'${ctx}/ps/psMember_changeOrder'},frozen:true },
                { label: '所在单位及职务',name: 'title',align: 'left', width: 250},
                { label: '任职起始时间',name: 'startDate',
                    formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y-m'}},
            <c:if test="${isHistory}">
                { label: '任职结束时间',name: 'endDate',
                    formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y-m'}},
            </c:if>
                { label: '联系方式',name: 'mobile',width: 150},
                { label: '备注',name: 'remark',width: 250}
        ]
    }).jqGrid("setFrozenColumns");
    $("#jqGrid2").trigger("reloadGrid");
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");
</script>