<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
            <c:set var="_query" value="${not empty param.code || not empty param.sort}"/>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="cesResult:edit">
                    <button class="popupBtn btn btn-info btn-sm"
                            data-url="${ctx}/cesResult_au?cadreId=${param.cadreId}&unitId=${param.unitId}&type=${param.type}">
                        <i class="fa fa-plus"></i> 添加</button>
                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/cesResult_au?type=${param.type}"
                       data-grid-id="#jqGrid_evaResult"><i class="fa fa-edit"></i>
                        修改</button>
                </shiro:hasPermission>
                <shiro:hasPermission name="cesResult:del">
                    <button data-url="${ctx}/cesResult_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid_evaResult"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
            </div>
            <div class="space-4"></div>
            <table id="jqGrid_evaResult" class="jqGrid2 table-striped"></table>
            <div id="jqGridPager_evaResult"></div>
    </div>
</div>
<script>
    $("#jqGrid_evaResult").jqGrid({
        pager: "#jqGridPager_evaResult",
        rownumbers:true,
        url: '${ctx}/cesResult_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
                { label: '年份',name: 'year',width: 80},
                { label: '测评类别',name: 'name',width: 300,align:"left"},
                { label:'排名', name:'rank',width: 80},
                { label: "${param.type == CES_RESULT_TYPE_CADRE ? '总人数' : '班子总数'}", name: 'num'},
                <c:if test="${param.type == CES_RESULT_TYPE_CADRE}">
                { label: "时任单位", name: 'unitId', align:"left", width: 150, formatter: function (cellvalue, options, rowObject) {
                    if(cellvalue==undefined) return '--'
                    return _cMap.unitMap[cellvalue].name;
                }},
                </c:if>
                { label: "${param.type == CES_RESULT_TYPE_CADRE ? '时任职务' : '班子名称'}", name: 'title', align:"left", width:200},
                { label: '备注',name: 'remark',width: 350}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid_evaResult", "jqGridPager_evaResult");
</script>