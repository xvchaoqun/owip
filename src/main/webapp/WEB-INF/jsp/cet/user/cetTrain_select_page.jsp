<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/cet/constants.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" class="myTableDiv">
            <div class="tabbable">
                <div class="tab-content">
                    <div class="tab-pane in active rownumbers">
                        <div class="jqgrid-vertical-offset buttons">

                        </div>
                        <div class="space-4"></div>
                        <table id="jqGrid" class="jqGrid table-striped"></table>
                        <div id="jqGridPager"></div>
                    </div>
                </div>
            </div>
        </div>
        <div id="body-content-view"></div>
    </div>
</div>
<script>

    function _reload() {
        $("#modal").modal('hide');
        $("#jqGrid").trigger("reloadGrid");
    }

    // 查看详情和报名、 年度、 编号、 培训班名称、 培训主题、 参训人员类型、 开课日期、 结课日期、 选课截止时间
    $("#jqGrid").jqGrid({
        rownumbers: true,
        url: '${ctx}/user/cet/cetTrain_select_data?callback=?&isFinished=${isFinished?1:0}&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '查看详情和报名', name: '_applyDetail', formatter: function (cellvalue, options, rowObject) {

                return ('<button class="openView btn {1} btn-xs" ' +
                        'data-url="${ctx}/user/cet/cetTrain_detail?cls=1&trainId={0}"><i class="fa fa-sign-in"></i> 进入</button>')
                        .format(rowObject.id, rowObject.courseCount>0?'btn-primary':'btn-success')
            }, width: 125, frozen: true},
            {label: '已选课数', name: 'courseCount', formatter: function (cellvalue, options, rowObject) {
                return cellvalue>0?cellvalue:'-'
            }, width: 90, frozen: true},
            {
                label: '结课状态', name: '_isFinished', width: 80, formatter: function (cellvalue, options, rowObject) {
                return rowObject.isFinished ? '已结课' : '未结课';
            }, frozen: true},
            {label: '年度', name: 'year', width:'60', frozen: true},
            {label: '开课日期', name: 'startDate', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
            {label: '结课日期', name: 'endDate', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
            {label: '培训班名称', name: 'name', width:350, align:'left'},
            {label: '内容简介', name: '_summary', formatter: function (cellvalue, options, rowObject) {
                if(rowObject.hasSummary==false) return'-'
                return ('<button class="popupBtn btn btn-primary btn-xs" data-width="750" ' +
                'data-url="${ctx}/cet/cetTrain_summary?id={0}&view=1"><i class="fa fa-search"></i> 查看</button>')
                        .format(rowObject.id);
            }},
            {label: '选课截止时间', name: 'endTime', width: 150, formatter: function (cellvalue, options, rowObject) {
                if(cellvalue==undefined) return '--'
                return $.date(cellvalue, "yyyy-MM-dd HH:mm");
            }},
            /*{label: '参训人员类型', name: 'traineeTypes', width:200},*/
            {label: '所属专题培训/年度培训', name: '_project', formatter: function (cellvalue, options, rowObject) {
                if(rowObject.cetProject==undefined) return '--'
                return ('<a href="javascript:;" class="openView" ' +
                'data-url="${ctx}/user/cet/cetProjectPlan?projectId={0}">{1}</a>')
                        .format(rowObject.cetProject.id, rowObject.cetProject.name)
            }, width:400, align:'left'}
        ]
    }).jqGrid("setFrozenColumns");
    $.initNavGrid("jqGrid", "jqGridPager");
    $(window).triggerHandler('resize.jqGrid');
</script>