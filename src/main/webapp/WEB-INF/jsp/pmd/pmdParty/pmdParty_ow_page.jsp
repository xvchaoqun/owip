<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

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
                    <%--<shiro:hasPermission name="pmdParty:delay">
                        <button data-url="${ctx}/pmd/pmdParty_delay"
                                data-grid-id="#jqGrid2"
                                class="jqOpenViewBtn btn btn-info btn-sm">
                            <i class="fa fa-hourglass-1"></i> 批量延迟缴费
                        </button>
                    </shiro:hasPermission>--%>
                        <c:if test="${pmdMonth.payStatus}">
                            <button class="jqOpenViewBatchBtn btn btn-info btn-sm"
                                    data-url="${ctx}/pmd/pmdParty_payStatus"
                                    data-callback="_reload2"
                                    data-grid-id="#jqGrid2"><i class="fa fa-toggle-off"></i> 缴费开关</button>
                        </c:if>
                    <shiro:hasPermission name="pmdParty:report">
                        <button id="unreportBtn" data-url="${ctx}/pmd/pmdParty_unreport"
                                data-grid-id="#jqGrid2"
                                data-title="撤销报送"
                                data-msg="确定撤销该党委的报送？"
                                data-callback="_reload2"
                                class="jqItemBtn btn btn-warning btn-sm">
                            <i class="fa fa-reply"></i> 撤销报送
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
                    <shiro:hasRole name="${ROLE_SUPER}">
                                <button class="jqItemBtn btn btn-danger btn-sm"
                                        data-title="更新报送"
                                        data-msg="确定更新报送？（仅更新汇总数据）"
                                   data-url="${ctx}/pmd/pmdParty_report?update=1"
                                        data-callback="_reload2"
                                   data-grid-id="#jqGrid2"><i class="fa fa-refresh"></i>
                                    更新报送</button>
                    </shiro:hasRole>
                    <shiro:hasPermission name="pmdParty:del">
                    <button data-url="${ctx}/pmd/pmdParty_del"
                            data-title="删除"
                            data-msg="确定删除此缴费党委？"
                            data-grid-id="#jqGrid2"
                            data-callback="_reload2"
                            class="jqItemBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
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
    function _reload2(){
        $("#jqGrid2").trigger("reloadGrid");
    }
    $("#jqGrid2").jqGrid({
        pager: "jqGridPager2",
        url: '${ctx}/pmd/pmdParty_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: colModel,
        onSelectRow: function (id, status) {
            saveJqgridSelected("#" + this.id, id, status);
            _onSelectRow(this)
        },
        onSelectAll: function (aRowids, status) {
            saveJqgridSelected("#" + this.id);
            _onSelectRow(this)
        }
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");
    function _onSelectRow(grid) {
        var ids = $(grid).getGridParam("selarrrow");

        if (ids.length > 1) {
            $("#unreportBtn").prop("disabled", true);
        } else if (ids.length == 1) {
            var rowData = $(grid).getRowData(ids[0]);
            var hasReport = (rowData.hasReport == "true");
            $("#unreportBtn").prop("disabled", !hasReport);
        }
    }
</script>