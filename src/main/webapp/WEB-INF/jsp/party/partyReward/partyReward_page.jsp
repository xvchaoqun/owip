<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="OW_PARTY_REPU_PARTY" value="<%=OwConstants.OW_PARTY_REPU_PARTY%>"/>
<c:set var="OW_PARTY_REPU_BRANCH" value="<%=OwConstants.OW_PARTY_REPU_BRANCH%>"/>
<c:set var="OW_PARTY_REPU_MEMBER" value="<%=OwConstants.OW_PARTY_REPU_MEMBER%>"/>
<shiro:hasPermission name="partyReward:edit">
             <div class="jqgrid-vertical-offset buttons">
                 <c:if test="${cls==OW_PARTY_REPU_PARTY}">
                    <button class="popupBtn btn btn-info btn-sm"
                            data-url="${ctx}/party/partyReward_au?partyId=${param.partyId}">
                        <i class="fa fa-plus"></i> 添加</button>
                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/party/partyReward_au?partyId=${param.partyId}"
                       data-grid-id="#jqGrid_reward"><i class="fa fa-edit"></i>
                        修改</button>
                 </c:if>
                 <c:if test="${cls==OW_PARTY_REPU_BRANCH}">
                 <button class="popupBtn btn btn-info btn-sm"
                         data-url="${ctx}/party/partyReward_au?branchId=${param.branchId}&cls=2">
                     <i class="fa fa-plus"></i> 添加</button>
                 <button class="jqOpenViewBtn btn btn-primary btn-sm"
                         data-url="${ctx}/party/partyReward_au?branchId=${param.branchId}&cls=2"
                         data-grid-id="#jqGrid_reward"><i class="fa fa-edit"></i>
                     修改</button>
                 </c:if>
                 <c:if test="${cls==OW_PARTY_REPU_MEMBER}">
                     <button class="popupBtn btn btn-info btn-sm"
                             data-url="${ctx}/party/partyReward_au?userId=${param.userId}&cls=3">
                         <i class="fa fa-plus"></i> 添加</button>
                     <button class="jqOpenViewBtn btn btn-primary btn-sm"
                             data-url="${ctx}/party/partyReward_au?userId=${param.userId}&cls=3"
                             data-grid-id="#jqGrid_reward"><i class="fa fa-edit"></i>
                         修改</button>
                 </c:if>
                    <button data-url="${ctx}/party/partyReward_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid_reward"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                <%--<button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-url="${ctx}/party/partyReward_data"
                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出</button>--%>
            </div>
</shiro:hasPermission>
            <div class="space-4"></div>
            <table id="jqGrid_reward" class="jqGrid2 table-striped"></table>
            <div id="jqGridPager_reward"></div>
<jsp:include page="/WEB-INF/jsp/party/partyReward/colModels.jsp"/>
<script>
    $("#jqGrid_reward").jqGrid({
        ondblClickRow: function () {
        },
        pager: "jqGridPager_reward",
        url: '${ctx}/party/partyReward_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: colModels.partyReward,
        rowattr: function (rowData, currentObj, rowId) {
            if (rowData.isPresent) {
                //console.log(rowData)
                return {'class': 'success'}
            }
        }
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $.register.fancybox();
    function _reload() {
        $("#modal").modal('hide');
        $("#partyMemberViewContent").loadPage("${ctx}/party/partyReward?${cm:encodeQueryString(pageContext.request.queryString)}");
    }
</script>