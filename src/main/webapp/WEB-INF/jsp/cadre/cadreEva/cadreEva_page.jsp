<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <c:set var="_query" value="${not empty param.cadreId || not empty param.code || not empty param.sort}"/>
        <c:if test="${cm:isPermitted(PERMISSION_CADREADMIN)}">
        <shiro:lacksPermission name="${PERMISSION_CADREONLYVIEW}">
        <div class="jqgrid-vertical-offset buttons">
            <shiro:hasPermission name="cadreEva:edit">
                <button class="popupBtn btn btn-success btn-sm"
                        data-url="${ctx}/cadreEva_au?cadreId=${param.cadreId}">
                    <i class="fa fa-plus"></i> 添加
                </button>
                <button class="jqOpenViewBtn btn btn-primary btn-sm"
                        data-url="${ctx}/cadreEva_au" data-querystr="&cadreId=${param.cadreId}"
                        data-grid-id="#jqGrid_eva"><i class="fa fa-edit"></i>
                    修改
                </button>
            </shiro:hasPermission>
            <shiro:hasPermission name="cadreEva:del">
                <button data-url="${ctx}/cadreEva_batchDel"
                        data-title="删除"
                        data-msg="确定删除这{0}条数据？"
                        data-grid-id="#jqGrid_eva"
                        class="jqBatchBtn btn btn-danger btn-sm">
                    <i class="fa fa-trash"></i> 删除
                </button>
            </shiro:hasPermission>
            <%--<button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                    data-url="${ctx}/cadreEva_data"
                    data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                <i class="fa fa-download"></i> 导出
            </button>--%>
        </div>
        </shiro:lacksPermission>
        </c:if>
        <div class="space-4"></div>
        <table id="jqGrid_eva" class="jqGrid2"></table>
        <div id="jqGridPager_eva"></div>
    </div>
</div>
<script>
    $("#jqGrid_eva").jqGrid({
        pager: "#jqGridPager_eva",
        rownumbers: true,
        url: '${ctx}/cadreEva_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '年份', name: 'year'},
            {label: '考核情况', name: 'type', formatter:$.jgrid.formatter.MetaType},
            {label: '时任职务', name: 'title', width:400, align:'left'},
            {label: '备注', name: 'remark', width:300}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid_eva", "jqGridPager_eva");
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$.register.date($('.date-picker'));
</script>