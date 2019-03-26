<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div id="div-content">
<div class="jqgrid-vertical-offset buttons">
    <shiro:hasPermission name="unitTeam:edit">
        <button class="popupBtn btn btn-success btn-sm" data-url="${ctx}/unitTeam_au?unitId=${param.unitId}">
            <i class="fa fa-plus"></i> 添加</button>
    </shiro:hasPermission>
    <shiro:hasPermission name="unitTeam:edit">
        <button class="jqOpenViewBtn btn btn-primary btn-sm"
                data-grid-id="#jqGrid2"
                data-url="${ctx}/unitTeam_au?unitId=${param.unitId}">
            <i class="fa fa-edit"></i> 修改
        </button>
    </shiro:hasPermission>
    <shiro:hasPermission name="unitTeam:del">
        <button type="button" class="jqBatchBtn btn btn-danger btn-sm"
            data-grid-id="#jqGrid2"
           data-url="${ctx}/unitTeam_batchDel" data-title="删除"
                data-callback="_reload2"
           data-msg="确定删除这{0}条记录吗？"><i class="fa fa-trash"></i> 删除</button>
    </shiro:hasPermission>
     <shiro:hasPermission name="unitTeam:edit">
        <%--<button class="jqOpenViewBtn btn btn-info btn-sm"
                data-grid-id="#jqGrid2"
                data-url="${ctx}/unitTeam_au?auType=1&unitId=${param.unitId}">
            <i class="fa fa-clock-o"></i> 设定应换届时间
        </button>--%>
        <button class="jqOpenViewBtn btn btn-warning btn-sm"
                data-grid-id="#jqGrid2" data-width="1100"
                data-url="${ctx}/unitTeam_au?auType=2&unitId=${param.unitId}">
            <i class="fa fa-edit"></i> 编辑任职信息
        </button>
        <button class="jqOpenViewBtn btn btn-warning btn-sm"
                data-grid-id="#jqGrid2" data-width="1100"
                data-url="${ctx}/unitTeam_au?auType=3&unitId=${param.unitId}">
            <i class="fa fa-edit"></i> 编辑免职信息
        </button>
    </shiro:hasPermission>
</div>
<div class="space-4"></div>
<table id="jqGrid2" class="jqGrid2 table-striped"></table>
<div id="jqGridPager2"></div>
</div>
<div id="div-content-view"></div>
<jsp:include page="colModel.jsp?load=view"/>
<script>
    $("#jqGrid2").jqGrid({
        rownumbers: true,
        pager:"#jqGridPager2",
        url: '${ctx}/unitTeam_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: colModel
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");

    function _reload2() {
        $("#modal").modal('hide');
       $("#jqGrid2").trigger("reloadGrid");
    }

    //$('#searchForm [data-rel="select2"]').select2();
    // $('[data-rel="tooltip"]').tooltip();
</script>