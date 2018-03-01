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
                    <a href="javascript:;">各支部缴费详情</a>
                </li>
            </ul>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main padding-4">
            <div class="multi-row-head-table tab-content padding-8">
                <div class="jqgrid-vertical-offset buttons">
                    <c:if test="${param.monthId==_pmdMonth.id}">
                    <button class="popupBtn btn btn-warning btn-sm"
                        ${(empty _pmdMonth)?'disabled':''}
                                      data-url="${ctx}/pmd/pmdSendMsg_notifyBranchAdmins?partyId=${param.partyId}"
                            ><i class="fa fa-send"></i> 通知支部管理员
                    </button>
                    <shiro:hasPermission name="pmdBranch:del">
                        <button id="delBtn" data-url="${ctx}/pmd/pmdBranch_batchDel"
                            ${(empty _pmdMonth)?'disabled':''}
                                data-title="删除"
                                data-grid-id="#jqGrid2"
                                data-msg="<div class='model-alert-tip'>确定删除这{0}个党支部？（将删除所有未缴费的缴费记录，删除后不可恢复，请谨慎操作）</div>"
                                data-callback="_reload2"
                                class="jqBatchBtn btn btn-danger btn-sm">
                            <i class="fa fa-trash"></i> 删除
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
<jsp:include page="pmdBranch_colModel.jsp"/>
<script>
    function _reload2(){
        SysMsg.info("操作成功。");
        $("#jqGrid2").trigger("reloadGrid");
    }
    $("#jqGrid2").jqGrid({
        pager: "jqGridPager2",
        url: '${ctx}/pmd/pmdBranch_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: colModel
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");
</script>