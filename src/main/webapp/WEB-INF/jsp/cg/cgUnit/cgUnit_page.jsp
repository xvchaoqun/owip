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
                       data-grid-id="#jqGrid2"><i class="fa fa-edit"></i>
                        修改</button>
                </shiro:hasPermission>

                <button class="jqBatchBtn btn btn-warning btn-sm"
                        data-grid-id="#jqGrid2"
                        data-title="撤销"
                        data-msg="确定撤销这{0}条数据？"
                        data-url="${ctx}/cg/cgUnit_plan?isCurrent=0">
                    <i class="fa fa-recycle"></i>
                    撤销</button>

                <shiro:hasPermission name="cgUnit:del">
                    <button data-url="${ctx}/cg/cgUnit_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid2"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
                <%--<button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-url="${ctx}/cg/cgUnit_data"
                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出</button>--%>
            </div>
            <div class="space-4"></div>
            <table id="jqGrid2" class="jqGrid2 table-striped" data-height-reduce="30" data-width-reduce="20"></table>
            <div id="jqGridPager2"></div>
        </div>
    </div>
</div>
<script>
    $("#jqGrid2").jqGrid({
        rownumbers:true,
        pager:"jqGridPager2",
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
    $.initNavGrid("jqGrid2", "jqGridPager2");
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$.register.date($('.date-picker'));
</script>