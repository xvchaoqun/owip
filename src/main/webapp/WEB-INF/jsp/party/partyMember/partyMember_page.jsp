<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="widget-box transparent">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:;" class="hideView btn btn-xs btn-success">
                <i class="ace-icon fa fa-backward"></i>
                返回</a>
        </h4>

        <div class="widget-toolbar no-border">
            <ul class="nav nav-tabs">
                <li class="active">
                    <a href="javascript:;">${partyMemberGroup.name}-委员管理</a>
                </li>
            </ul>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main padding-4">
            <div class="tab-content padding-8">
                <c:if test="${!partyMemberGroup.isDeleted}">
                <shiro:hasPermission name="partyMember:edit">
                    <a class="popupBtn btn btn-info btn-sm"
                       data-url="${ctx}/partyMember_au?gridId=jqGrid2&groupId=${partyMemberGroup.id}">
                        <i class="fa fa-plus"></i> 添加</a>
                    <a class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/partyMember_au?gridId=jqGrid2"
                       data-grid-id="#jqGrid2"><i class="fa fa-edit"></i>
                        修改</a>
                    <button data-url="${ctx}/partyMember_dismiss?dismiss=1"
                            data-grid-id="#jqGrid2"
                            class="jqOpenViewBtn btn btn-warning btn-sm">
                        <i class="fa fa-sign-out"></i> 离任
                    </button>
                    <button data-url="${ctx}/partyMember_dismiss?dismiss=0"
                            data-grid-id="#jqGrid2"
                            class="jqOpenViewBtn btn btn-success btn-sm">
                        <i class="fa fa-reply"></i> 重新任用
                    </button>
                </shiro:hasPermission>
                <shiro:hasPermission name="partyMember:del">
                    <button data-url="${ctx}/partyMember_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？<br/>（删除后无法恢复，请谨慎操作！！）"
                            data-grid-id="#jqGrid2"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-times"></i> 删除
                    </button>
                </shiro:hasPermission>
                </c:if>
                <div class="space-4"></div>
                <table id="jqGrid2" class="jqGrid2 table-striped"></table>
                <div id="jqGridPager2"></div>
            </div>
        </div>
        <!-- /.widget-main -->
    </div>
    <!-- /.widget-body -->
</div>
<!-- /.widget-box -->
<jsp:include page="partyMember_colModel.jsp"/>
<script>
    function _adminCallback(){
        $("#modal").modal("hide")
        $("#jqGrid2").trigger("reloadGrid");
    }
    $.register.date($('.date-picker'));
    $("#jqGrid2").jqGrid({
        //forceFit:true,
        pager: "jqGridPager2",
        url: '${ctx}/partyMember_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: colModel
    }).jqGrid("setFrozenColumns")
     $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");
</script>