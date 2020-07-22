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
                <c:set var="_query" value="${not empty param.unitName || not empty param.unitPostId || not empty param.realname
                 || not empty param.scoreRate}"/>
                <div class="jqgrid-vertical-offset buttons">
                    <shiro:hasPermission name="drOnlineResult:edit">
                        <button id="exportResult" class="btn btn-success btn-sm tooltip-success"
                                data-url="${ctx}/dr/drOnline/drOnlineResult_data?onlineId=${param.onlineId}"
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
                                <input type="hidden" name="onlineId" value="${param.onlineId}"/>
                                <div class="form-group">
                                    <label>推荐职务</label>
                                    <div class="input-group">
                                        <select  data-width="230" data-rel="select2-ajax"
                                                 data-ajax-url="${ctx}/dr/drOnline/unitPost_selects?onlineId=${param.onlineId}"
                                                 name="unitPostId" data-placeholder="请选择推荐职务">
                                            <option value="${unitPost.id}" delete="${unitPost.status!=UNIT_POST_STATUS_NORMAL}">${unitPost.code}-${unitPost.name}</option>
                                        </select>
                                    </div>
                                    <script>         $("#searchForm select[name=unitPostId]").val('${unitPost.id}');     </script>
                                </div>
                                <div class="form-group">
                                    <label>推荐人选</label>
                                    <input class="form-control search-query" name="realname" type="text" value="${param.realname}"
                                           placeholder="请输入推荐人选姓名">
                                </div>
                                <div class="clearfix form-actions center">
                                    <a class="jqSearchBtn btn btn-default btn-sm"
                                       data-url="${ctx}/dr/drOnline/drOnlineResult"
                                       data-target="#body-content-view"
                                       data-form="#searchForm2"><i class="fa fa-search"></i> 查找</a>
                                    <c:if test="${_query}">&nbsp;
                                        <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                data-url="${ctx}/dr/drOnline/drOnlineResult?onlineId=${param.onlineId}"
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
    $("#jqGrid2").jqGrid({
        multiselect:false,
        rownumbers: false,
        pager: "jqGridPager2",
        url: '${ctx}/dr/drOnline/drOnlineResult_data?&callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
                { label: '推荐职务',name: 'postName', width: 250},
                { label: '推荐人选',name: 'realname', width: 160},
                { label: '得票',name: 'ballot'}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $('#searchForm2 [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    //$.register.date($('.date-picker'));
</script>