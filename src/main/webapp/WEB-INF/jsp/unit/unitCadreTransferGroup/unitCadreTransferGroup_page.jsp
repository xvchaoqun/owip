<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

            <div class="space-4"></div>
<table id="jqGrid_dispatchCadre" data-width-reduce="20" class="jqGrid2"></table>
<div id="jqGridPager_dispatchCadre"></div>
<jsp:include page="/WEB-INF/jsp/dispatch/dispatchCadre/dispatchCadre_columns.jsp?type1=team"/>
<script>
    $("#jqGrid_dispatchCadre").jqGrid({
        multiselect:false,
        ondblClickRow: function () {
        },
        pager: "#jqGridPager_dispatchCadre",
        url: '${ctx}/dispatchCadre_data?unitId=${param.unitId}',
        colModel:colModel
    });
    $.initNavGrid("jqGrid_dispatchCadre", "jqGridPager_dispatchCadre");
    $(window).triggerHandler('resize.jqGrid2');
</script>