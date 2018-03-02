<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<!-- PAGE CONTENT BEGINS -->
<div class="widget-box transparent" id="useLogs">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:;" class="hideView btn btn-xs btn-success">
                <i class="ace-icon fa fa-backward"></i>
                返回</a>
        </h4>
        <div class="widget-toolbar no-border">
            <ul class="nav nav-tabs">
                <li class="active">
                    <a href="javascript:;">各党委缴费详情</a>
                </li>
            </ul>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main padding-4">
            <div class="multi-row-head-table tab-content padding-8">
                <div class="jqgrid-vertical-offset buttons">
                    <c:if test="${param.monthId==_pmdMonth.id}">
                    <shiro:hasPermission name="pmdParty:delay">
                        <button data-url="${ctx}/pmd/pmdParty_delay"
                                data-grid-id="#jqGrid2"
                                class="jqOpenViewBtn btn btn-info btn-sm">
                            <i class="fa fa-hourglass-1"></i> 批量延迟缴费
                        </button>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="pmdParty:forceReport">
                        <button data-url="${ctx}/pmd/pmdParty_forceReport"
                                data-grid-id="#jqGrid2"
                                class="jqOpenViewBtn btn btn-success btn-sm">
                            <i class="fa fa-hand-paper-o"></i> 强制报送
                        </button>
                    </shiro:hasPermission>
                    </c:if>
                </div>
                <div class="space-4"></div>
                <table id="jqGrid2" class="jqGrid2 table-striped"></table>
                <div id="jqGridPager2"></div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="pmdParty_colModel.jsp"/>
<script>
    $("#jqGrid2").jqGrid({
        pager: "jqGridPager2",
        url: '${ctx}/pmd/pmdParty_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: colModel
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");
</script>