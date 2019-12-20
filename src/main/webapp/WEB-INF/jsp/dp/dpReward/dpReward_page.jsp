<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<jsp:include page="/WEB-INF/jsp/dp/dpColModels.jsp"/>
        <div class="space-4"></div>
         <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="dpReward:edit">
                    <button class="popupBtn btn btn-info btn-sm"
                            data-url="${ctx}/dp/dpReward_au?userId=${param.userId}">
                        <i class="fa fa-plus"></i> 添加</button>
                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/dp/dpReward_au?userId=${param.userId}"
                       data-grid-id="#jqGrid_dpReward"><i class="fa fa-edit"></i>
                        修改</button>
                </shiro:hasPermission>
                <shiro:hasPermission name="dpReward:del">
                    <button data-url="${ctx}/dp/dpReward_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid_dpReward"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
            </div>
            <div class="space-4"></div>
            <table id="jqGrid_dpReward" class="jqGrid2"></table>
            <div id="jqGridPager_dpReward"></div>
<script>
    $("#jqGrid_dpReward").jqGrid({
        ondblClickRow: function () {
        },
        pager: "#jqGridPager_cadrePunish",
        url: '${ctx}/dp/dpReward_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: dpColModels.dpReward,
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $.register.fancybox();
    //$.register.date($('.date-picker'));
</script>