<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="<%=DrConstants.DR_ONLINE_FINISH%>" var="DR_ONLINE_FINISH"/>
<c:set value="<%=DrConstants.DR_ONLINE_RATE_MAP%>" var="DR_ONLINE_RATE_MAP"/>
<div class="widget-box transparent">
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
                <c:set var="_query" value="${not empty param.unitName || not empty param.unitPostId || not empty param.candidate
                 || not empty param.scoreRate}"/>
                <div class="jqgrid-vertical-offset buttons">
                    <shiro:hasPermission name="drOnlineResult:edit">
                        <button id="inspectorFilter" class="popupBtn btn btn-info btn-sm"
                                data-url="${ctx}/dr/drOnline/drOnlineResult_filter?onlineId=${onlineId}">
                            <i class="fa fa-filter"></i> 参评人过滤</button>
                        <button id="exportResult" class="btn btn-success btn-sm tooltip-success"
                                data-url="${ctx}/dr/drOnline/drOnlineResult_data?&onlineId=${onlineId}"
                                data-rel="tooltip" data-placement="top" title="导出统计结果">
                            <i class="fa fa-download"></i> 导出</button>
                    </shiro:hasPermission>
                </div>
                <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
                    <div class="widget-header">
                        <h4 class="widget-title">搜索</h4>
                        <span class="widget-note">${note_searchbar}</span>
                        <div class="widget-toolbar">
                            <a href="#" data-action="collapse">
                                <i class="ace-icon fa fa-chevron-${_query?'up':'down'}"></i>
                            </a>
                        </div>
                    </div>
                    <div class="widget-body">
                        <div class="widget-main no-padding">
                            <form class="form-inline search-form" id="searchForm2">
                                <input type="hidden" name="onlineId" value="${onlineId}"/>
                                <div class="form-group">
                                    <label>推荐职务</label>
                                    <div class="input-group">
                                        <select  data-width="230" data-rel="select2-ajax"
                                                 data-ajax-url="${ctx}/dr/drOnline/unitPost_selects?onlineId=${onlineId}"
                                                 name="unitPostId" data-placeholder="请选择推荐职务">
                                            <option value="${unitPost.id}" delete="${unitPost.status!=UNIT_POST_STATUS_NORMAL}">${unitPost.code}-${unitPost.name}</option>
                                        </select>
                                    </div>
                                    <script>         $("#searchForm select[name=unitPostId]").val('${unitPost.id}');     </script>
                                </div>
                                <div class="form-group">
                                    <label>推荐人选</label>
                                    <input class="form-control search-query" name="candidate" type="text" value="${candidate}"
                                           placeholder="请输入推荐人选姓名">
                                </div>
                                <div class="form-group">
                                    <label>得票比率</label>
                                    <select data-rel="select2" data-width="135"
                                            name="scoreRate" data-placeholder="请选择得票比率">
                                        <option></option>
                                        <c:forEach items="<%=DrConstants.DR_ONLINE_RATE_MAP%>" var="rate">
                                            <option value="${rate.key}">${rate.value}</option>
                                        </c:forEach>
                                    </select>
                                    <script>
                                        $("#searchForm select[name=scoreRate]").val('${param.scoreRate}');
                                    </script>
                                </div>
                                <div class="clearfix form-actions center">
                                    <a class="jqSearchBtn btn btn-default btn-sm"
                                       data-url="${ctx}/dr/drOnline/drOnlineResult"
                                       data-target="#body-content-view"
                                       data-form="#searchForm2"><i class="fa fa-search"></i> 查找</a>
                                    <c:if test="${_query}">&nbsp;
                                        <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                data-url="${ctx}/dr/drOnline/drOnlineResult?onlineId=${onlineId}"
                                                data-target="#body-content-view">
                                            <i class="fa fa-reply"></i> 重置
                                        </button>
                                    </c:if>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="space-4"></div>
                <table id="jqGrid2" class="jqGrid2 table-striped" data-height-reduce="55"></table>
                <div id="jqGridPager2"></div>
            </div>
        </div>
    </div>
</div>
<script>

    $("#exportResult").on("click", function (e) {

        var $this = $(this);

        var gridId = $this.data("grid-id") || "#jqGrid";
        var grid = $(gridId);
        var ids = grid.getGridParam("selarrrow");
        var idsName = $(this).data("ids-name") || 'ids[]';
        var _export = $(this).data("export") || '1';
        var type = $(this).data("type") || 'export';
        //console.log(_typeIds);

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
    //console.log(_typeIds)
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
                { label: '推荐职务',name: 'post.name', width: 250},
                { label: '推荐人选',name: 'candidate', width: 160},
                { label: '得票',name: 'options'},
                { label: '得票比率',name: 'scoreRate',formatter: function (cellvalue, options, rowObject) {
                        return cellvalue + "%";
                    }}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $('#searchForm2 [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    //$.register.date($('.date-picker'));
</script>