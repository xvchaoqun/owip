<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="widget-box transparent" id="useLogs">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:;" class="hideView btn btn-xs btn-success">
                <i class="ace-icon fa fa-backward"></i>
                返回</a>
        </h4>
        <div class="widget-toolbar no-border">
            <ul class="nav nav-tabs">
                <li class="active">
                    <a href="javascript:;">${oaTask.name}-任务对象列表</a>
                </li>
            </ul>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main padding-4">
            <div class="tab-content padding-8">
                <div class="jqgrid-vertical-offset buttons">
                    <a class="popupBtn btn btn-warning btn-sm"
                       data-url="${ctx}/oa/oaTaskUser_unreportMsg?taskId=${oaTask.id}"><i class="fa fa-send"></i>
                        短信催促未报送对象</a>
                    <a class="jqOpenViewBatchBtn btn btn-primary btn-sm"
                       data-querystr="taskId=${oaTask.id}"
                       data-ids-name="taskUserIds[]"
                       data-grid-id="#jqGrid2"
                       data-url="${ctx}/oa/oaTaskUser_check"><i class="fa fa-check-square-o"></i> 批量审批</a>
                     <span style="margin-left: 20px;">
                            任务对象共${totalCount}个， 完成报送共${hasReportCount}个（通过审核共${passCount}个） ， 未报送${totalCount-hasReportCount}个
                    </span>
                </div>
                <div class="space-4"></div>
                <table id="jqGrid2" class="jqGrid2 table-striped"></table>
                <div id="jqGridPager2"></div>
            </div>
        </div>
    </div>
</div>
<script>
    function _oaTaskUser_reload(){
        $("#jqGrid2").trigger("reloadGrid");
    }
    $.register.date($('.date-picker'));
    $("#jqGrid2").jqGrid({
        rownumbers:true,
        //forceFit:true,
        pager: "jqGridPager2",
        url: '${ctx}/oa/oaTaskUser_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '工作证号', name: 'code', width:120},
            {label: '姓名', name: 'realname', width:120},
            {label: '所在单位及职务', name: 'title', width:350},
            {label: '手机号码', name: 'mobile', width:120},
            {label: '指定负责人', name: 'assignRealname', width:180, formatter: function (cellvalue, options, rowObject) {
                if(cellvalue==undefined) return '-';
                return cellvalue + "({0})".format(rowObject.assignCode)
            }},
            {label: '指定负责人手机号', name: 'assignUserMobile', width:140, formatter: function (cellvalue, options, rowObject) {
                if(cellvalue==undefined) return '-';
                return cellvalue
            }},
            {label: '报送情况', name: 'hasReport', formatter: function (cellvalue, options, rowObject) {

                if(!cellvalue) return '未报送';

                return '<button class="popupBtn btn btn-success btn-xs" data-width="800" ' +
                        'data-url="${ctx}/oa/oaTaskUser_report?id={0}"><i class="fa fa-search"></i> 已报送</button>'
                                .format(rowObject.id)
            }},
            {label: '报送人', name: 'reportRealname', width:120, formatter: function (cellvalue, options, rowObject) {
                if(cellvalue==undefined) return '-';
                return cellvalue
            }},
            {label: '审核情况', name: 'status', formatter: function (cellvalue, options, rowObject) {
                if(cellvalue==undefined) return '-'
                if(cellvalue=='${OA_TASK_USER_STATUS_INIT}'){
                    return '<button class="popupBtn btn btn-primary btn-xs"' +
                            'data-url="${ctx}/oa/oaTaskUser_check?taskId={0}&taskUserIds[]={1}"><i class="fa fa-check-square-o"></i> 审核</button>'
                                    .format(rowObject.taskId, rowObject.userId)
                }

                return _cMap.OA_TASK_USER_STATUS_MAP[cellvalue];
            }},
            { label: '短信提醒',name: '_sendMsg', formatter: function (cellvalue, options, rowObject) {
                if(rowObject.status=='${OA_TASK_USER_STATUS_DENY}')
                    return ('<button class="popupBtn btn btn-warning btn-xs" ' +
                    'data-url="${ctx}/oa/oaTaskUser_denyMsg?id={0}"><i class="fa fa-send"></i> 短信提醒</button>')
                            .format(rowObject.id);
                return "-";
            }},
            { label: '退回',name: '_back', formatter: function (cellvalue, options, rowObject) {

                if(rowObject.isBack) return '已退回'
                if(!rowObject.hasReport) return '-'
                return ('<button class="confirm btn btn-danger btn-xs" data-callback="_oaTaskUser_reload"  data-title="退回"  data-msg="确定退回“{1}”的报送？"' +
                'data-url="${ctx}/oa/oaTaskUser_back?id={0}"><i class="fa fa-reply"></i> 退回</button>')
                        .format(rowObject.id, rowObject.realname);
            }},{hidden:true, name: 'userId', key:true}
        ]
    }).jqGrid("setFrozenColumns");

    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");

</script>