<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="_query" value="${not empty param.post ||not empty param.type ||not empty param.unitPostId ||not empty param.userId || not empty param.code || not empty param.sort}"/>
    <ul class="jqgrid-vertical-offset nav nav-tabs padding-12 tab-color-blue background-blue">
    <li class="<c:if test="${isCurrent}">active</c:if>">
        <a href="javascript:;" class="loadPage"
           data-load-el="#tab-content"
           data-url="${ctx}/cg/cgMember?isCurrent=1&teamId=${param.teamId}">
            <i class="fa fa-circle-o-notch"></i> 现任成员
        </a>
    </li>
    <li class="<c:if test="${!isCurrent}">active</c:if>">
        <a href="javascript:;" class="loadPage"
           data-load-el="#tab-content"
           data-url="${ctx}/cg/cgMember?isCurrent=0&teamId=${param.teamId}">
            <i class="fa fa-history"></i> 离任成员
        </a>
    </li>
</ul>
<div class="space-4"></div>
<div class="jqgrid-vertical-offset buttons">

    <shiro:hasPermission name="cgMember:edit">
        <c:if test="${isCurrent}">
        <button class="popupBtn btn btn-info btn-sm"
                data-url="${ctx}/cg/cgMember_au?teamId=${param.teamId}"><i class="fa fa-plus"></i>
            添加</button>

        </c:if>
        <button class="jqOpenViewBtn btn btn-primary btn-sm"
                data-url="${ctx}/cg/cgMember_au"
                data-grid-id="#jqGrid2"><i class="fa fa-edit"></i>
            修改</button>

    </shiro:hasPermission>
    <c:if test="${isCurrent}">
        <button class="jqBatchBtn btn btn-warning btn-sm"
                data-url="${ctx}/cg/cgMember_plan?isCurrent=0"
                data-title="撤销"
                data-msg="确定撤销这{0}条数据？"
                data-grid-id="#jqGrid2"><i class="fa fa-recycle"></i>
            撤销</button>

    </c:if>
    <c:if test="${!isCurrent}">
        <button class="jqBatchBtn btn btn-warning btn-sm"
                data-url="${ctx}/cg/cgMember_plan?isCurrent=1"
                data-title="返回"
                data-msg="确定返回这{0}条数据？"
                data-grid-id="#jqGrid2"><i class="fa fa-backward"></i>
            返回</button>

    </c:if>
    <%--<button class="jqExportBtn btn btn-success btn-sm tooltip-success"
            title="导出选中记录或所有搜索结果"
            data-url="${ctx}/cg/cgMember_data"
            data-rel="tooltip"
            data-placement="top"><i class="fa fa-download"></i>
        导出</button>--%>

    <shiro:hasPermission name="cgMember:del">
        <button class="jqBatchBtn btn btn-danger btn-sm"
                data-url="${ctx}/cg/cgMember_batchDel"
                data-title="删除"
                data-msg="确定删除这{0}条数据？"
                data-grid-id="#jqGrid2"><i class="fa fa-trash"></i>
            删除</button>

    </shiro:hasPermission>
</div>
<div class="multi-row-head-table">
    <div class="space-4"></div>
    <table id="jqGrid2" class="jqGrid2 table-striped" data-height-reduce="30" data-width-reduce="20"></table>
    <div id="jqGridPager2"></div>
</div>
<script>
    $("#jqGrid2").jqGrid({
        rownumbers:true,
        pager:"jqGridPager2",
        url: '${ctx}/cg/cgMember_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
                { label: '职务',name: 'post',width:150, formatter: $.jgrid.formatter.MetaType},
                { label:'排序', width: 85, formatter: $.jgrid.formatter.sortOrder,
                formatoptions:{grid:'#jqGrid2',url:'${ctx}/cg/cgMember_changeOrder'}
                },
                { label: '人员类型',name: 'type',formatter: function (cellvalue, options, rowObject)
                {return cellvalue == <%=CgConstants.CG_MEMBER_TYPE_CADRE%>?'现任干部':'各类代表'}
                },
                { label: '代表类型',name: 'tag'},
                { label: '姓名',name: 'user.realname'},
                { label: '关联岗位名称',name: 'unitPost.name',width: 300,align:'left'},
        ]}).jqGrid("setFrozenColumns");

    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");
</script>