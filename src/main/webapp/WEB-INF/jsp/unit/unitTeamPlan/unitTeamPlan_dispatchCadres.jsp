<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<jsp:include page="menu.jsp"/>
<div class="space-4"></div>
<table id="jqGrid_dispatchCadre" <c:if test="${param.load=='view'}"> data-height-reduce="10" data-width-reduce="60"</c:if> class="jqGrid2"></table>
<div id="jqGridPager_dispatchCadre"></div>
<jsp:include page="/WEB-INF/jsp/dispatch/dispatchCadre/dispatchCadre_columns.jsp?type1=team"/>
<script>
    $("#jqGrid_dispatchCadre").jqGrid({
        multiselect:false,
        ondblClickRow: function () {
        },
        pager: "#jqGridPager_dispatchCadre",
        url: '${ctx}/dispatchCadre_data?unitId=${unitTeam.unitId}'
            +'&type=<%=DispatchConstants.DISPATCH_CADRE_TYPE_APPOINT%>'
            +'&postTeam=xz'
            +'&workTimeStart=${cm:formatDate(unitTeam.appointDate, "yyyy-MM-dd")}'
            +'&workTimeEnd=${cm:formatDate(unitTeam.deposeDate, "yyyy-MM-dd")}',
        colModel:colModel
    });
    $.initNavGrid("jqGrid_dispatchCadre", "jqGridPager_dispatchCadre");
    $(window).triggerHandler('resize.jqGrid2');
</script>