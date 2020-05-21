<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div>
            <c:set var="_query" value="${not empty param.unitName || not empty param.id ||not empty param.typeId ||not empty param.unitId || not empty param.code || not empty param.sort}"/>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="drOnlineInspectorLog:edit">
                    <button class="popupBtn btn btn-info btn-sm"
                            data-url="${ctx}/dr/selectUnitIdsAndInspectorTypeIds?onlineId=${onlineId}">
                        <i class="fa fa-plus"></i> 列表生成</button>
                    <button class="popupBtn btn btn-primary btn-sm"
                       data-url="${ctx}/dr/drOnlineInspectorLog_au?onlineId=${onlineId}"
                       data-grid-id="#jqGrid2"><i class="fa fa-plus"></i>
                        个别生成</button>
                    <button data-url="${ctx}/dr/inspectorLog_changeStatus"
                            data-title="发布"
                            data-msg="确定发布这{0}条数据（除已作废的账号）？"
                            data-grid-id="#jqGrid2"
                            class="jqBatchBtn btn btn-success btn-sm">
                        <i class="fa fa-share"></i> 发布
                    </button>
                </shiro:hasPermission>
                <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-url="${ctx}/dr/drOnlineInspectorLog_data?onlineId=${onlineId}"
                        data-grid-id="#jqGrid2"
                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出参评人账号</button>
                <shiro:hasPermission name="drOnlineInspectorLog:del">
                    <button data-url="${ctx}/dr/drOnlineInspectorLog_batchDel"
                            data-title="删除"
                            data-msg="删除这{0}条信息，将会删除它的参评人信息，参评人投票信息。请谨慎删除！"
                            data-grid-id="#jqGrid2"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
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
                                <label>参评人身份类型</label>
                                <div class="input-group">
                                    <select  data-width="230" data-rel="select2-ajax"
                                             data-ajax-url="${ctx}/dr/drOnlineInspectorType_selects"
                                             name="typeId" data-placeholder="参评人身份类型">
                                        <option value="${inspectorType.id}">${inspectorType.type}</option>
                                    </select>
                                </div>
                                <script>         $("#searchForm select[name=typeId]").val('${param.typeId}');     </script>
                            </div>
                            <div class="form-group">
                                <label>所属单位</label>
                                <select name="unitId" data-rel="select2-ajax" data-ajax-url="${ctx}/unit_selects"
                                        data-placeholder="请选择所属单位">
                                    <option value="${unit.id}" delete="${unit.status==UNIT_STATUS_HISTORY}">${unit.name}</option>
                                </select>
                                <script>
                                    $.register.del_select($("#searchForm select[name=unitId]").val('${unit.id}'), 250)
                                </script>
                            </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                   data-url="${ctx}/dr/drOnlineInspectorLog_menu"
                                   data-target="#body-content-view"
                                   data-form="#searchForm2"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/dr/drOnlineInspectorLog_menu?onlineId=${onlineId}"
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
            <table id="jqGrid2" class="jqGrid2 table-striped" data-height-reduce="52"></table>
            <div id="jqGridPager2"></div>
</div>
<script>
    function _reload() {
        $("#jqGrid2").trigger("reloadGrid");
    }

    var openWindow = null;
    function open_list_gen(onlineId, unitIds, inspectorTypeIds) {

        if (openWindow != null)
            openWindow.close();

        openWindow = openwindow("${ctx}/dr/inspector_gen?onlineId={0}&unitIds={1}&inspectorTypeIds={2}".format(onlineId, unitIds, inspectorTypeIds), "列表生成");
    }

    function openwindow(url, name, iWidth, iHeight){
        iWidth = iWidth || (screen.availWidth- 10);
        iHeight = iHeight || (screen.availHeight- 30);
        win = window.open(url,name,'width='+ iWidth +',height='+ iHeight +'fullscreen=yes,toolbar=no,menubar=no,scrollbars=yes, resizable=yes,location=no,status=no');
        return win;
    }

    $("#jqGrid2").jqGrid({
        pager: "jqGridPager2",
        url: '${ctx}/dr/drOnlineInspectorLog_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
                { label: '参评人身份类型',name: 'inspectorType.type', width: 150},
                { label: '所属单位',name: 'unitId', width: 250, formatter: $.jgrid.formatter.unit},
                {
                    label: '已生成', name: 'totalCount', width:110, formatter: function (cellvalue, options, rowObject) {
                        var str = '<button class="openView btn btn-info btn-xs" data-url="${ctx}/dr/drOnlineInspector?onlineId={0}&logId={2}"><i class="fa fa-search"></i> 查看({1})</button>'
                            .format(rowObject.onlineId, cellvalue, rowObject.id);
                        return  str;
                    }},
                { label: '已发布/已完成',name: '_pubFinishCount', formatter: function (cellvalue, options, object) {
                    //console.log(object)
                        var rate = object.pubCount + "/" + object.finishCount;
                    if (rate == undefined ||rate == null)
                        return "--";
                    else
                        return rate;
                    }, width: 110},
                { label: '生成时间',name: 'createTime', width: 150, formatter: $.jgrid.formatter.date, formatoptions: {srcformat:'Y.m.d H:i:s',newformat: 'Y.m.d H:i:s'}}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $('#searchForm2 [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $.register.date($('.date-picker'));

</script>