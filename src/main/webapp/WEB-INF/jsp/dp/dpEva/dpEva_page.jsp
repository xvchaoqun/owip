<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="space-4"></div>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="dpEva:edit">
                    <button class="popupBtn btn btn-info btn-sm"
                            data-url="${ctx}/dp/dpEva_au?userId=${userId}">
                        <i class="fa fa-plus"></i> 添加</button>
                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/dp/dpEva_au?userId=${userId}"
                       data-grid-id="#jqGrid2"><i class="fa fa-edit"></i>
                        修改</button>
                </shiro:hasPermission>
                <shiro:hasPermission name="dpEva:del">
                    <button data-url="${ctx}/dp/dpEva_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？<br/>（删除后无法恢复，请谨慎操作！！）"
                            data-grid-id="#jqGrid2"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
            </div>
            <div class="space-4"></div>
            <table id="jqGrid2" class="jqGrid2" data-height-reduce="30"></table>
            <div id="jqGridPager2"></div>
<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>
<script>
    $("#jqGrid2").jqGrid({
        pager: "jqGridPager2",
        url: '${ctx}/dp/dpEva_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
                { label: '年份',name: 'year'},
                { label: '考核情况',name: 'type', formatter:$.jgrid.formatter.MetaType},
                { label: '时任职务',name: 'title', width:400},
                { label: '备注',name: 'remark', width:300},{hidden: true, key: true, name: 'id'},{hidden:true, name: 'userId'}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2')
</script>