<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<jsp:include page="/WEB-INF/jsp/cadre/colModels.jsp"/>
<div class="widget-box">
    <div class="widget-header">
        <h4 class="widget-title"><i class="fa fa-user"></i> 主建单位
            <div class="buttons">
                <shiro:hasPermission name="psParty:edit">
                <button class="popupBtn btn btn-success btn-sm"
                   data-url="${ctx}/ps/psParty_au?psId=${param.psId}&isHost=1">
                    <i class="fa fa-plus"></i>
                    添加</button>
                <button class="jqOpenViewBtn btn btn-primary btn-sm"
                   data-url="${ctx}/ps/psParty_au"
                   data-grid-id="#jqGrid_hostUnit">
                    <i class="fa fa-edit"></i>
                    修改</button>
                </shiro:hasPermission>
                <shiro:hasPermission name="psParty:history">
                <button class="jqOpenViewBatchBtn btn btn-warning btn-sm"
                        data-grid-id="#jqGrid_hostUnit"
                        data-url="${ctx}/ps/psParty_history">
                    <i class="fa fa-recycle"></i>
                    撤销</button>
                </shiro:hasPermission>
                <shiro:hasPermission name="psParty:del">
                <button class="jqBatchBtn btn btn-danger btn-sm"
                        data-url="${ctx}/ps/psParty_batchDel"
                        data-title="删除"
                        data-msg="确定删除这{0}条数据？"
                        data-grid-id="#jqGrid_hostUnit">
                    <i class="fa fa-trash"></i>
                    删除</button>
                </shiro:hasPermission>
            </div>
        </h4>
    </div>
    <div class="widget-body">
        <div class="widget-main table-nonselect">
            <table id="jqGrid_hostUnit" class="jqGrid4" data-width-reduce="50"></table>
            <div id="jqGridPager_hostUnit"></div>
        </div>
    </div>
</div>
<div class="space-4"></div>
<div class="widget-box">
    <div class="widget-header">
        <h4 class="widget-title"><i class="fa fa-users"></i> 联合建设单位
            <div class="buttons">
                <shiro:hasPermission name="psParty:edit">
                <button class="popupBtn btn btn-success btn-sm"
                        data-url="${ctx}/ps/psParty_au?psId=${param.psId}">
                    <i class="fa fa-plus"></i>
                    添加</button>
                <button class="jqOpenViewBtn btn btn-primary btn-sm"
                        data-url="${ctx}/ps/psParty_au"
                        data-grid-id="#jqGrid_jointUnit">
                    <i class="fa fa-edit"></i>
                    修改</button>
                </shiro:hasPermission>
                <shiro:hasPermission name="psParty:history">
                <button class="jqOpenViewBatchBtn btn btn-warning btn-sm"
                        data-grid-id="#jqGrid_jointUnit"
                        data-url="${ctx}/ps/psParty_history">
                    <i class="fa fa-recycle"></i>
                    撤销</button>
                </shiro:hasPermission>
                <shiro:hasPermission name="psParty:del">
                <button class="jqBatchBtn btn btn-danger btn-sm"
                        data-url="${ctx}/ps/psParty_batchDel"
                        data-title="删除"
                        data-msg="确定删除这{0}条数据？"
                        data-grid-id="#jqGrid_jointUnit">
                    <i class="fa fa-trash"></i>
                    删除</button>
                </shiro:hasPermission>
            </div>
        </h4>
    </div>
    <div class="widget-body">
        <div class="widget-main table-nonselect">
            <table id="jqGrid_jointUnit" class="jqGrid4" data-width-reduce="50"></table>
            <div id="jqGridPager_jointUnit"></div>
        </div>
    </div>
</div>
<script>
    $("#jqGrid_hostUnit").jqGrid({
        rownumbers:true,
        pager: "#jqGridPager_hostUnit",
        url: '${ctx}/ps/psParty_data?callback=?&isHost=1&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '主建单位名称', name: 'partyId', width: 250, frozen: true, formatter:function(cellvalue, options, rowObject){
                    return cellvalue==undefined?"":_cMap.partyMap[cellvalue].name;}},
            {label: '开始时间', name: 'startDate', formatter:$.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}, frozen:true},
            {label: '结束时间', name: 'endDate', formatter:$.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}, frozen:true},
            {label: '状态', name: 'isFinish', formatter: function (cellvalue, options, rowObject) {
                    return cellvalue == true?"已结束":"未结束";
                }},
            {label: '备注', name: 'remark', width: 400}, {hidden: true, key: true, name: 'id'}
            ]
    }).jqGrid("setFrozenColumns");
    $("#jqGrid_jointUnit").jqGrid({
        rownumbers:true,
        pager: "#jqGridPager_jointUnit",
        url: '${ctx}/ps/psParty_data?callback=?&isHost=0&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '联合建设单位名称', name: 'partyId', width: 250, frozen: true, formatter:function(cellvalue, options, rowObject){
                    return cellvalue==undefined?"":_cMap.partyMap[cellvalue].name;}},
            {label: '开始时间', name: 'startDate', formatter:$.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}, frozen:true},
            {label: '结束时间', name: 'endDate', formatter:$.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}, frozen:true},
            {label: '状态', name: 'isFinish', formatter: function (cellvalue, options, rowObject) {
                    return cellvalue == true?"已结束":"未结束";
                }},
            {label: '备注', name: 'remark', width: 400}, {hidden: true, key: true, name: 'id'}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid4');
</script>