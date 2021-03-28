<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.relatePost ||not empty param.unitPostId ||not empty param.userId ||not empty param.phone ||not empty param.confirmDate || not empty param.code || not empty param.sort}"/>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="cgLeader:edit">
                    <button class="popupBtn btn btn-info btn-sm"
                            data-url="${ctx}/cg/cgLeader_au?teamId=${param.teamId}"><i class="fa fa-plus"></i>
                        添加</button>

                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                            data-url="${ctx}/cg/cgLeader_au"
                            data-grid-id="#jqGrid_cgLeader"><i class="fa fa-edit"></i>
                        修改</button>

                </shiro:hasPermission>
                <button class="jqBatchBtn btn btn-warning btn-sm"
                        data-url="${ctx}/cg/cgLeader_plan?isCurrent=0"
                        data-title="撤销"
                        data-msg="确定撤销这{0}条数据？"
                        data-grid-id="#jqGrid_cgLeader"><i class="fa fa-recycle"></i>
                    撤销</button>
                <shiro:hasPermission name="cgLeader:del">
                    <button data-url="${ctx}/cg/cgLeader_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？<br/>（删除后无法恢复，请谨慎操作！！）"
                            data-grid-id="#jqGrid_cgLeader"
                            class="jqBatchBtn btn btn-danger btn-sm"><i class="fa fa-trash"></i>
                        删除</button>

                </shiro:hasPermission>
            </div>
            <div class="space-4"></div>
            <table id="jqGrid_cgLeader" class="jqGrid2 table-striped" data-height-reduce="30" data-width-reduce="20"></table>
            <div id="jqGridPager_cgLeader"></div>
        </div>
    </div>
</div>
<script>

    $("#jqGrid_cgLeader").jqGrid({
        rownumbers:true,
        pager:"jqGridPager_cgLeader",
        url: '${ctx}/cg/cgLeader_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
                { label: '是否席位制',name: 'isPost',formatter: $.jgrid.formatter.TRUEFALSE},
                { label: '关联岗位',name: 'unitPost.name',width: 300,align:'left'},
                { label: '办公室主任',name: 'user.realname'},
                { label: '联系方式',name: 'phone', width:150},
                { label: '确定时间',name: 'confirmDate',width: 150,formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
                { label: '任职状态',name: 'isCurrent', formatter:function (cellvalue, options, rowObject){
                    return cellvalue?"现任":"离任"}},
                { label: '备注',name: 'remark'}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid_cgLeader", "jqGridPager_cgLeader");
</script>