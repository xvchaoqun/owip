<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
    <div class="jqgrid-vertical-offset buttons">
        <shiro:hasPermission name="drOnlineResult:edit">
            <button class="popupBtn btn btn-info btn-sm"
                    data-url="${ctx}/dr/drOnlineResult_au">
                <i class="fa fa-filter"></i> 参评人过滤</button>
        </shiro:hasPermission>
    </div>
    <div class="space-4"></div>
    <table id="jqGrid2" class="jqGrid2 table-striped"></table>
    <div id="jqGridPager2"></div>
<script>
    $("#jqGrid2").jqGrid({
        pager: "jqGridPager2",
        url: '${ctx}/dr/drOnline/drOnlineResult_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
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
    //$('[data-rel="tooltip"]').tooltip();
    //$.register.date($('.date-picker'));
</script>