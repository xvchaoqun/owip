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
                        <button id="inspectorFilter" class="popupBtn btn btn-info btn-sm"
                                data-url="${ctx}/dr/drOnline/drOnlineResult_filter?onlineId=${onlineId}">
                            <i class="fa fa-filter"></i> 参评人过滤</button>
                        <c:if test="${drOnline.status==DR_ONLINE_FINISH}">
                            <button id="exportResult" download="11" class="btn btn-success btn-sm tooltip-success"
                                    data-url="${ctx}/dr/drOnline/drOnlineResult_data?&onlineId=${onlineId}"
                                    data-rel="tooltip" data-placement="top" title="导出统计结果">
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

    $(document).on("click", "#exportResult", function (e) {

        var $this = $(this);

        var gridId = $this.data("grid-id") || "#jqGrid";
        var grid = $(gridId);
        var ids = grid.getGridParam("selarrrow");
        var idsName = $(this).data("ids-name") || 'ids[]';
        var _export = $(this).data("export") || '1';
        var type = $(this).data("type") || 'export';
        console.log(_typeIds);

        var url = $this.data("url") || $(this).closest(".myTableDiv").data("url-export");
        var queryString = $this.data("querystr");
        if($.trim(queryString)!='') url += (url.indexOf("?") > 0 ? "&" : "?") + queryString;

        //var searchFormId = $this.data("search-form-id") || "div.myTableDiv #searchForm";
        var searchFormId = $this.data("search-form-id") || "#searchForm";

        url = url + (url.indexOf("?") > 0 ? "&" : "?") + "export="+ _export +"&_typeIds="+ _typeIds +"&"
            encodeURI(idsName)+"=" + ids + "&" + $(searchFormId).serialize();

        $this.download(url, type);

        e.stopPropagation();
        return false;
    });

    var _typeIds = new Array();
    function changeUrl(queryData){

        $.each(queryData,function () {
            //console.log(this)
            _typeIds.length=0;
            _typeIds.push(this);
        })
    }


    $("#jqGrid2").jqGrid({
        multiselect:false,
        rownumbers: false,
        pager: "jqGridPager2",
        url: '${ctx}/dr/drOnline/drOnlineResult_data?&callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
                { label: '推荐职务',name: 'post.name', width: 200},
                { label: '推荐人选',name: 'candidate', width: 160},
                { label: '得票',name: 'options'},
                { label: '得票比率',name: 'scoreRate',formatter: function (cellvalue, options, rowObject) {
                        return cellvalue + "%";
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