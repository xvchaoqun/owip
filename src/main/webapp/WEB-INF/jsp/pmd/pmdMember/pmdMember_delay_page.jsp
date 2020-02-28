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
            <jsp:include page="menu.jsp"/>
        </div>
    </div>
    <form class="form-inline search-form" id="searchForm2" style="position: absolute;top:-6px;left:80px;">
        <input type="hidden" name="partyId" value="${param.partyId}">
        <input type="hidden" name="branchId" value="${param.branchId}">
        <input type="hidden" name="monthId" value="${param.monthId}">
        <input type="hidden" name="cls" value="${cls}">

        <div class="form-group">
            <label>姓名</label>
            <select data-rel="select2-ajax"
                    data-ajax-url="${ctx}/member_selects?noAuth=1&partyId=${param.partyId}&branchId=${param.branchId}&status=${MEMBER_STATUS_NORMAL}"
                    name="userId" data-placeholder="请输入账号或姓名或学工号">
                <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
            </select>
        </div>
        <c:if test="${canAdmin && param.monthId==_pmdMonth.id}">
        <div class="form-group">
            <label>缴费状态</label>
            <select data-rel="select2" name="hasPay"
                    data-width="100"
                    data-placeholder="请选择">
                <option></option>
                <option value="0">未缴费</option>
                <option value="1">已缴费</option>
            </select>
            <script>
                $("#searchForm2 select[name=hasPay]").val("${param.hasPay}")
            </script>
        </div>
        </c:if>
        <c:set var="_query" value="${not empty param.userId ||not empty param.hasPay
             || not empty param.isDelay}"/>
        <div class="form-group">
            <button type="button" data-url="${ctx}/pmd/pmdMember"
                    data-target="#body-content-view" data-form="#searchForm2"
                    class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找
            </button>
            <c:if test="${_query}">
                <button type="button"
                        data-url="${ctx}/pmd/pmdMember"
                        data-querystr="cls=${cls}&partyId=${param.partyId}&branchId=${param.branchId}&monthId=${param.monthId}"
                        data-target="#body-content-view"
                        class="reloadBtn btn btn-warning btn-sm">
                    <i class="fa fa-reply"></i> 重置
                </button>
            </c:if>
        </div>
    </form>
    <div class="widget-body">
        <div class="widget-main padding-4">
            <div class="tab-content padding-8">
                <div class="jqgrid-vertical-offset buttons">
                <c:if test="${canAdmin && param.monthId==_pmdMonth.id}">
                    <button id="delayCashBtn" class="jqOpenViewBtn btn btn-success btn-sm"
                            data-url="${ctx}/user/pmd/payConfirm"
                            data-querystr="&isSelfPay=0"
                            data-grid-id="#jqGrid2">
                        <i class="fa fa-rmb"></i> 代缴党费
                    </button>
                    <button id="helpBatchPayBtn" class="jqOpenViewBatchBtn btn btn-success btn-sm"
                            data-url="${ctx}/user/pmd/payConfirm_batch"
                            data-querystr="isDelay=1"
                            data-grid-id="#jqGrid2">
                        <i class="fa fa-rmb"></i> 批量代缴党费
                    </button>
                    <shiro:hasPermission name="pmdMember:setIsOnlinePay">
                    <button id="selectMemberTypeBtn" class="jqOpenViewBatchBtn btn btn-warning btn-sm"
                            data-url="${ctx}/pmd/pmdMember_setIsOnlinePay"
                            data-grid-id="#jqGrid2">
                        <i class="fa fa-edit"></i> 修改缴费方式
                    </button>
                    <button id="setDuePayBtn" class="jqOpenViewBatchBtn btn btn-danger btn-sm"
                            data-url="${ctx}/pmd/pmdMember_changeDuePay"
                            data-grid-id="#jqGrid2">
                        <i class="fa fa-edit"></i> 修改应交金额
                    </button>
                    </shiro:hasPermission>
                </c:if>
                    <button class="jqOpenViewBtn btn btn-info btn-sm"
                            data-grid-id="#jqGrid2"
                            data-url="${ctx}/sysApprovalLog"
                            data-width="850"
                            data-querystr="&displayType=1&hideStatus=1&type=<%=SystemConstants.SYS_APPROVAL_LOG_TYPE_PMD_MEMBER%>">
                        <i class="fa fa-history"></i> 操作记录
                    </button>
                </div>
                <div class="space-4"></div>
                <table id="jqGrid2" class="jqGrid2 table-striped"></table>
                <div id="jqGridPager2"></div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="pmdMember_colModel.jsp?type=admin&self=1"/>
<script>
    $.register.user_select($('#searchForm2 select[name=userId]'));
    $('#searchForm2 [data-rel="select2"]').select2();
    function _reload2() {
        $("#jqGrid2").trigger("reloadGrid");
    }
    $("#jqGrid2").jqGrid({
        <c:if test="${!canAdmin || param.monthId!=_pmdMonth.id}">
        multiselect:false,
        </c:if>
        pager: "jqGridPager2",
        url: '${ctx}/pmd/pmdMember_delay_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
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
            $("#delayCashBtn").prop("disabled", true);
        } else if (ids.length == 1) {
            var rowData = $(grid).getRowData(ids[0]);
            var hasPay = (rowData.hasPay == "true");
            //console.log(isCurrentMonth)
            $("#delayCashBtn").prop("disabled", hasPay);
        }

        var canBatchPay = true;
        $.each(ids, function(i, id){
            var rowData = $(grid).getRowData(id);
            var hasPay = (rowData.hasPay == "true");
            if (hasPay) {
                canBatchPay = false;
                return;
            }
        })

        $("#helpBatchPayBtn").prop("disabled", !canBatchPay || ids.length==1);
        $("#setDuePayBtn").prop("disabled", !canBatchPay);
    }
</script>