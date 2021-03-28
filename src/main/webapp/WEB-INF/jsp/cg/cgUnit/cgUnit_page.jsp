<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div class="rownumbers" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.unitId ||not empty param.confirmDate || not empty param.code || not empty param.sort}"/>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="cgUnit:edit">
                    <button class="popupBtn btn btn-info btn-sm"
                            data-url="${ctx}/cg/cgUnit_au?teamId=${param.teamId}">
                        <i class="fa fa-plus"></i> 添加</button>
                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/cg/cgUnit_au"
                       data-grid-id="#jqGrid_cgUnit"><i class="fa fa-edit"></i>
                        修改</button>
                </shiro:hasPermission>

                <button class="jqBatchBtn btn btn-warning btn-sm"
                        data-grid-id="#jqGrid_cgUnit"
                        data-title="撤销"
                        data-msg="确定撤销这{0}条数据？"
                        data-url="${ctx}/cg/cgUnit_plan?isCurrent=0">
                    <i class="fa fa-recycle"></i>
                    撤销</button>

                <shiro:hasPermission name="cgUnit:del">
                    <button data-url="${ctx}/cg/cgUnit_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？<br/>（删除后无法恢复，请谨慎操作！！）"
                            data-grid-id="#jqGrid_cgUnit"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
            </div>
            <div class="space-4"></div>
            <table id="jqGrid_cgUnit" class="jqGrid2 table-striped" data-height-reduce="30" data-width-reduce="20"></table>
            <div id="jqGridPager_cgUnit"></div>
        </div>
    </div>
</div>
<script>

        $("#jqGrid_cgUnit").jqGrid({
        rownumbers:true,
        pager:"jqGridPager_cgUnit",
        url: '${ctx}/cg/cgUnit_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
                { label: '单位',name: 'unit.name',width: 250},
                { label: '确定时间',name: 'confirmDate',width: 150,formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
                { label: '状态',name: 'isCurrent',width: 150,formatter: function (cellvalue, options, rowObject){
                    return cellvalue==true?"当前挂靠单位":"历史挂靠单位";
                    }},
                { label: '备注',name: 'remark',width: 200}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid_cgUnit", "jqGridPager_cgUnit");
</script>