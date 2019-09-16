<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

            <div class="space-4"></div>
<table id="jqGrid_cadreWork" data-width-reduce="60" class="jqGrid2"></table>
<div id="jqGridPager_cadreWork"></div>
<script>

    $("#jqGrid_cadreWork").jqGrid({
        multiselect:false,
        ondblClickRow: function () {
        },
        pager: "#jqGridPager_cadreWork",
        url: '${ctx}/cadreWork_data?unitId=${param.unitId}&fid=-1&isCadre=1',
        colModel: [
            {label: '姓名', name: 'cadre.realname', frozen: true},
            {label: '工作证号', name: 'cadre.code', width: 110, frozen: true},
            {label: '开始日期', name: 'startTime', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}},
            {label: '结束日期', name: 'endTime', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}},
            {label: '工作单位及担任职务（或专技职务）', name: 'detail', width: 380, align:'left'},
            {label: '行政级别', name: 'typeId', formatter: $.jgrid.formatter.MetaType, width: 200},
            {label: '工作类型', name: 'workTypes', formatter: $.jgrid.formatter.MetaType, width: 200},
            {
                label: '所属内设机构', name: 'unitIds',formatter: function (cellvalue, options, rowObject) {

                if($.trim(cellvalue)=='') return '--'
                return ($.map(cellvalue.split(","), function(unitId){
                    return $.jgrid.formatter.unit(unitId);
                })).join("，")

            }, width: 500, align:'left'}
        ]
    });
    $(window).triggerHandler('resize.jqGrid2');
</script>