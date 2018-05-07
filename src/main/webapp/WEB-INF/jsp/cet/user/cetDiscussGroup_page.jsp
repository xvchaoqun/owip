<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="widget-box transparent">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:" class="openView btn btn-xs btn-success"
               data-url="${ctx}/user/cet/cetProjectPlan?projectId=${cetProjectPlan.projectId}">
                <i class="ace-icon fa fa-backward"></i> 返回</a>
        </h4>
        <span class="text text-info bolder" style="cursor: auto;padding-left: 20px;">
                    ${CET_PROJECT_PLAN_TYPE_MAP.get(cetProjectPlan.type)}
                    （${cm:formatDate(cetProjectPlan.startDate, "yyyy-MM-dd")} ~ ${cm:formatDate(cetProjectPlan.endDate, "yyyy-MM-dd")}，${cetProject.name}）
        </span>
        <div class="widget-toolbar no-border">
            <ul class="nav nav-tabs" id="detail-ul">
                <li class="active">
                    <a href="javascript:;">讨论小组列表</a>
                </li>
            </ul>
        </div>
    </div>
    <div class="widget-body">
        <div class="rownumbers widget-main padding-12 no-padding-left no-padding-right no-padding-bottom">
            <div class="jqgrid-vertical-offset buttons">

            </div>

            <div class="space-4"></div>
            <table id="jqGrid2" class="jqGrid2 table-striped"></table>
            <div id="jqGridPager2"></div>
        </div>
    </div>
</div>
<script>

    var cetDiscussGroups = ${cm:toJSONArray(cetDiscussGroups)};
    $("#jqGrid2").jqGrid({
        rownumbers:true,
        pager: null,
        datatype: "local",
        data: cetDiscussGroups,
        colModel: [
            { label: '参会情况',name: 'isFinished', width: 80, formatter: function (cellvalue, options, rowObject) {
                if(cellvalue!=undefined){
                    return cellvalue?"已参会":"未参会"
                }
                return '-'
            }, frozen: true},
            { label: '完成学时数',name: 'period', formatter: function (cellvalue, options, rowObject) {
                if(rowObject.isFinished==undefined) return '-'
                return rowObject.isFinished?cellvalue:0
            }, frozen: true},
            {label: '组别', name: 'name', frozen: true},
            {label: '召集人', name: 'holdUser.realname', frozen: true},
            {label: '研讨主题', name: 'subject',width: 250, align:'left', frozen: true},
            {label: '召开时间', name: 'discussTime',width: 150, formatter: 'date',
                formatoptions: {srcformat: 'Y-m-d H:i', newformat: 'Y-m-d H:i'}},
            {label: '召开地点', name: 'discussAddress',width: 250, align:'left'}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>