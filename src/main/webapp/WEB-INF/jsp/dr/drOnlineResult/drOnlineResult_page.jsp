<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="<%=DrConstants.DR_ONLINE_FINISH%>" var="DR_ONLINE_FINISH"/>
<div class="widget-box transparent" id="view-box">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:;" class="hideView btn btn-xs btn-success">
                <i class="ace-icon fa fa-backward"></i>
                返回</a>
        </h4>
    </div>
    <div class="widget-body">
        <div class="widget-main padding-4">
            <div class="tab-content padding-8">
                <div class="jqgrid-vertical-offset buttons">
                    <shiro:hasPermission name="drOnlineResult:edit">
                        <button class="popupBtn btn btn-info btn-sm"
                                data-url="${ctx}/dr/drOnline/drOnlineResult_filter?onlineId=${onlineId}">
                            <i class="fa fa-filter"></i> 参评人过滤</button>
                        <c:if test="${drOnline.status==DR_ONLINE_FINISH}">
                            <button download="11" class="jqExportBtn btn btn-success btn-sm tooltip-success"
                                    data-url="${ctx}/dr/drOnline/drOnlineResult_export?&onlineId=${onlineId}&cls=1"
                                    data-rel="tooltip" data-placement="top" title="导出本批次最终统计结果">
                                <i class="fa fa-download"></i> 导出</button>
                        </c:if>
                    </shiro:hasPermission>
                </div>
                <div class="space-4"></div>
                <table id="jqGrid2" class="jqGrid2 table-striped" data-height-reduce="20"></table>
                <div id="jqGridPager2"></div>
            </div>
        </div>
    </div>
</div>
<script>
    $("#jqGrid2").jqGrid({
        pager: "jqGridPager2",
        url: '${ctx}/dr/drOnline/drOnlineResult_data?&callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
                { label: '推荐职务',name: 'post.name', width: 200},
                { label: '推荐人选',name: 'user.realname', width: 80},
                { label: '得票',name: 'optionSum'},
                { label: '得票比率',name: '_rate',formatter: function (cellvalue, options, rowObject) {
                        var val = (rowObject.optionSum/rowObject.finishCounts).toFixed(4);
                        return (val*100)+"%";
                    }}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    //$.register.date($('.date-picker'));
</script>