<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content" class="myTableDiv"
                 data-url-page="${ctx}/sc/scGroupMember"
                 data-url-export="${ctx}/sc/scGroupMember_data"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.userId ||not empty param.isCurrent || not empty param.code || not empty param.sort}"/>
            <div class="jqgrid-vertical-offset buttons">
                <c:if test="${param.isCurrent==1}">
                <shiro:hasPermission name="scGroupMember:edit">
                    <a class="popupBtn btn btn-info btn-sm"  data-url="${ctx}/sc/scGroupMember_au"><i class="fa fa-plus"></i> 添加</a>
                    <a class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/sc/scGroupMember_au"
                       data-grid-id="#jqGrid"
                       ><i class="fa fa-edit"></i>
                        修改</a>
                </shiro:hasPermission>
                    <button data-url="${ctx}/sc/scGroupMember_transfer?isCurrent=0"
                            data-title="转移"
                            data-msg="确定将这{0}个成员转移到小组会过去成员中？"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-warning btn-sm">
                        <i class="fa fa-share"></i> 转移
                    </button>
                </c:if>
                <c:if test="${param.isCurrent!=1}">
                    <button data-url="${ctx}/sc/scGroupMember_transfer?isCurrent=1"
                            data-title="重新任用"
                            data-msg="确定将这{0}个成员转移到小组会现有成员中？"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-warning btn-sm">
                        <i class="fa fa-reply"></i> 转移至现有成员
                    </button>
                </c:if>
                <shiro:hasPermission name="scGroupMember:del">
                    <button data-url="${ctx}/sc/scGroupMember_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}个成员（不可恢复，请谨慎操作）？"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
                <%--<a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出</a>--%>
            </div>

            <div class="space-4"></div>
            <table id="jqGrid" class="table-striped"></table>
            <div id="jqGridPager"></div>
        </div>
        <div id="body-content-view"></div>
    </div>
</div>
<script>
    $("#jqGrid").jqGrid({
        responsive:false,
        width:485,
        height:500,
        url: '${ctx}/sc/scGroupMember_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '姓名', name: 'user.realname'},
            {label: '工作证号', name: 'user.code', width: 150},
            { label: '是否组长',name: 'isLeader', formatter: $.jgrid.formatter.TRUEFALSE},
            {
                label: '排序', width: 90, formatter: $.jgrid.formatter.sortOrder,
                formatoptions:{url: "${ctx}/sc/scGroupMember_changeOrder"}}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>