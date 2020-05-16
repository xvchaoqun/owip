<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<shiro:hasPermission name="partyPost:edit">
            <div class="jqgrid-vertical-offset buttons">
                    <button class="popupBtn btn btn-info btn-sm"
                            data-url="${ctx}/party/partyPost_au?userId=${param.userId}">
                        <i class="fa fa-plus"></i> 添加任职经历</button>
                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/party/partyPost_au?userId=${param.userId}"
                       data-grid-id="#jqGrid_post"><i class="fa fa-edit"></i>
                        修改</button>
                    <button data-url="${ctx}/party/partyPost_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid_post"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                <%--<button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-url="${ctx}/partyPost_data"
                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出</button>--%>
            </div>
</shiro:hasPermission>
            <div class="space-4"></div>
            <table id="jqGrid_post" class="jqGrid2 table-striped"></table>
            <div id="jqGridPager_post"></div>
<jsp:include page="/WEB-INF/jsp/party/partyPost/colModels.jsp"/>
<script>
    $("#jqGrid_post").jqGrid({
        ondblClickRow: function () {
        },
        pager: "jqGridPager_post",
        url: '${ctx}/party/partyPost_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: colModels.partyPost,
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
        $("#partyMemberViewContent").loadPage("${ctx}/party/partyPost?${cm:encodeQueryString(pageContext.request.queryString)}");
    }
</script>