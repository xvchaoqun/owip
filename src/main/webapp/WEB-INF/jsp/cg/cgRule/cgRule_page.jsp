<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="<%=CgConstants.CG_RULE_TYPE_MAP%>" var="CG_RULE_TYPE_MAP"/>
<div class="widget-box">
    <div class="widget-header">
        <h4 class="widget-title"><i class="fa fa-user"></i> 当前规则
            <div class="buttons" id="hostUnit">
                <shiro:hasPermission name="cgRule:edit">
                    <button class="popupBtn btn btn-success btn-sm"
                            data-url="${ctx}/cg/cgRule_au?teamId=${param.teamId}">
                        <i class="fa fa-plus"></i>
                        添加</button>

                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                            data-url="${ctx}/cg/cgRule_au"
                            data-grid-id="#jqGrid_current">
                        <i class="fa fa-edit"></i>
                        修改</button>

                </shiro:hasPermission>
                <shiro:hasPermission name="cgRule:history">
                    <button class="jqBatchBtn btn btn-warning btn-sm"
                            data-callback="_statusChange"
                            data-grid-id="#jqGrid_current"
                            data-title="弃用"
                            data-msg="确定弃用这{0}条数据？"
                            data-url="${ctx}/cg/cgRule_plan?isCurrent=0">
                        <i class="fa fa-recycle"></i>
                        弃用</button>

                </shiro:hasPermission>
                <shiro:hasPermission name="cgRule:del">
                    <button class="jqBatchBtn btn btn-danger btn-sm"
                            data-url="${ctx}/cg/cgRule_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid_current">
                        <i class="fa fa-trash"></i>
                        删除</button>

                </shiro:hasPermission>
            </div>
        </h4>
    </div>
    <div class="widget-body">
        <div class="widget-main table-nonselect">
            <table id="jqGrid_current" class="jqGrid4" data-width-reduce="50"></table>
            <div id="jqGridPager_current"></div>
        </div>
    </div>
</div>
<div class="space-4"></div>
<div class="widget-box">
    <div class="widget-header">
        <h4 class="widget-title"><i class="fa fa-users"></i> 历史规则
            <div class="buttons">
                <button class="jqBatchBtn btn btn-success btn-sm"
                        data-callback="_statusChange"
                        data-grid-id="#jqGrid_history"
                        data-title="返回"
                        data-msg="确定重新启用这{0}条数据？"
                        data-url="${ctx}/cg/cgRule_plan?isCurrent=1">
                    <i class="fa fa-backward"></i>
                    返回</button>

                <shiro:hasPermission name="cgRule:del">
                    <button class="jqBatchBtn btn btn-danger btn-sm"
                            data-url="${ctx}/cg/cgRule_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid_history">
                        <i class="fa fa-trash"></i>
                        删除</button>
                </shiro:hasPermission>
            </div>
        </h4>
    </div>
    <div class="widget-body">
        <div class="widget-main table-nonselect">
            <table id="jqGrid_history" class="jqGrid4" data-width-reduce="50"></table>
            <div id="jqGridPager_history"></div>
        </div>
    </div>
</div>
<script>

    var rule_type = ${cm:toJSONObject(CG_RULE_TYPE_MAP)};

    $("#jqGrid_current").jqGrid({
        rownumbers:true,
        pager:"jqGridPager_current",
        url: '${ctx}/cg/cgRule_data?isCurrent=1&callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
                { label: '规程类型',name: 'type',width: 150,formatter:function(cellvalue, options, rowObject){
                    return rule_type[cellvalue];
                    }},
                <c:if test="${!_query}">
                { label:'排序', width: 80, formatter: $.jgrid.formatter.sortOrder,
                    formatoptions:{grid:'#jqGrid_current', url:'${ctx}/cg/cgRule_changeOrder'}},
                </c:if>

                { label: '规程确定时间',name: 'confirmDate',
                    formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}
                    },
                { label: '规程内容',name: 'content',width: 80,formatter:function(cellvalue, options, rowObject){

                        return '<button class="popupBtn btn btn-{3} btn-xs" data-width="800" data-url="${ctx}/cg/cgRule_content?id={0}"><i class="fa fa-{2}"></i> {1}</button>'
                            .format(rowObject.id,cellvalue==null?"编辑":"查看", cellvalue==null?"edit":"search", cellvalue==null?"primary":"warning");
                    }},
                { label: '相关文件',name: 'filePath',formatter: function (cellvalue, options, rowObject) {

                    /*var fileName = rule_type[rowObject.type];*/
                    if (cellvalue==null) return "--";

                        return ('<button class="downloadBtn btn btn-xs btn-success" ' +
                            'data-url="${ctx}/cg/cgRule_download?id={0}"><i class="fa fa-download"></i> 下载</button>')
                            .format(rowObject.id);
                    }},
                { label: '备注',name: 'remark',width:200}
        ]
    }).jqGrid("setFrozenColumns");

    $("#jqGrid_history").jqGrid({
        rownumbers:true,
        pager:"jqGridPager_history",
        url: '${ctx}/cg/cgRule_data?isCurrent=0&callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
                { label: '规程类型',name: 'type',width: 150,formatter:function(cellvalue, options, rowObject){
                        return rule_type[cellvalue];
                    }},
                { label: '规程确定时间',name: 'confirmDate',
                    formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}
                    },
                { label: '规程内容',name: 'content',width: 80,formatter:function(cellvalue, options, rowObject){

                        return '<button class="popupBtn btn btn-warning btn-xs" data-width="800" data-url="${ctx}/cg/cgRule_content?isView=1&id={0}"><i class="fa fa-search"></i> {1}</button>'
                            .format(rowObject.id, '查看');
                    }},
                { label: '相关文件',name: 'filePath',formatter: function (cellvalue, options, rowObject) {

                        /*var fileName = rule_type[rowObject.type];*/
                        if (cellvalue==null) return "--";

                        return ('<button class="downloadBtn btn btn-xs btn-success" ' +
                            'data-url="${ctx}/cg/cgRule_download?id={0}"><i class="fa fa-download"></i> 下载</button>')
                            .format(rowObject.id);
                    }},
                { label: '备注',name: 'remark',width:200}
        ]
    }).jqGrid("setFrozenColumns");

    function _statusChange(){

        $("#jqGrid_current").trigger("reloadGrid");
        $("#jqGrid_history").trigger("reloadGrid");
    }

    $(window).triggerHandler('resize.jqGrid4');
    $.initNavGrid("jqGrid_current", "jqGridPager_current");
    $.initNavGrid("jqGrid_history", "jqGridPager_history");
</script>