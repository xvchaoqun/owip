<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="space-4"></div>
<table id="jqGrid_dispatchUnit" data-width-reduce="20" class="jqGrid2"></table>
<div id="jqGridPager_dispatchUnit"></div>
<jsp:include page="/WEB-INF/jsp/dispatch/dispatchUnit/dispatchUnit_colModel.jsp?type=all"/>
<script>
    $("#jqGrid_dispatchUnit").jqGrid({
        multiselect:false,
        ondblClickRow: function () {
        },
        pager: "#jqGridPager_dispatchUnit",
        url: '${ctx}/dispatchUnit_data?unitId=${param.unitId}&category=<%=DispatchConstants.DISPATCH_CATEGORY_UNIT%>',
        colModel:colModel
    }).jqGrid("setFrozenColumns");
    $.initNavGrid("jqGrid_dispatchUnit", "jqGridPager_dispatchUnit");
    $(window).triggerHandler('resize.jqGrid2');
</script>