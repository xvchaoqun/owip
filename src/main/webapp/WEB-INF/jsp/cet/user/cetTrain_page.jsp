<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/cet/constants.jsp" %>
<div class="widget-box transparent">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:;" class="openView btn btn-xs btn-success"
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
                    <a href="javascript:;">培训班列表</a>
                </li>
            </ul>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main padding-12 no-padding-left no-padding-right no-padding-bottom">
            <div class="space-4"></div>
            <table id="jqGrid2" class="jqGrid2 table-striped"></table>
            <div id="jqGridPager2"></div>
        </div>
    </div>
</div>
<script>
    $("#jqGrid2").jqGrid({
        rownumbers:true,
        multiselect:false,
        pager: "jqGridPager2",
        url: '${ctx}/user/cet/cetTrain_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {
                label: '详情', name: '_detail', width: '90', formatter: function (cellvalue, options, rowObject) {
                return ('<button class="openView btn btn-success btn-xs" ' +
                'data-url="${ctx}/user/cet/cetTrain_detail?cls=2&trainId={0}"><i class="fa fa-search"></i> 详情</button>')
                        .format(rowObject.trainId);
            }, frozen: true},
            {
                label: '结课状态', name: '_isFinished', width: 80, formatter: function (cellvalue, options, rowObject) {
                return rowObject.cetTrain.isFinished ? '已结课' : '未结课';
            }, frozen: true},
            {label: '开课日期', name: 'cetTrain.startDate', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y-m-d'}, frozen: true},
            {label: '结课日期', name: 'cetTrain.endDate', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y-m-d'}, frozen: true},
            {label: '培训班名称', name: 'cetTrain.name', width: 300, align: 'left', frozen: true},
            {
                label: '内容简介', name: '_summary', width: 80, formatter: function (cellvalue, options, rowObject) {
                if (!rowObject.cetTrain.hasSummary) return '--';
                return ('<button class="popupBtn btn btn-primary btn-xs" data-width="750" ' +
                'data-url="${ctx}/cet/cetTrain_summary?view=1&id={0}"><i class="fa fa-search"></i> 查看</button>')
                        .format(rowObject.trainId);
            }},
            { label: '选课总学时',name: 'totalPeriod'},
            { label: '完成学时数',name: 'finishPeriod'},
            { label: '学习进度',name: '_finish',formatter: function (cellvalue, options, rowObject) {
                if(Math.trimToZero(rowObject.totalPeriod)==0) return '--'
                var progress = Math.formatFloat(rowObject.finishPeriod*100/rowObject.totalPeriod, 1) + "%";
               return ('<div class="progress progress-striped pos-rel" data-percent="{0}">' +
                '<div class="progress-bar progress-bar-success" style="width:{0};"></div></div>').format(progress)
            }},
            { name: 'trainId', key:true, hidden:true}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");
</script>