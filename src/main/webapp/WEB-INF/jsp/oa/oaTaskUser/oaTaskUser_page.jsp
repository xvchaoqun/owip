<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="OA_TASK_STATUS_ABOLISH" value="<%=OaConstants.OA_TASK_STATUS_ABOLISH%>"/>
<c:set var="OA_TASK_STATUS_FINISH" value="<%=OaConstants.OA_TASK_STATUS_FINISH%>"/>
<c:set var="taskCanEdit" value="${oaTask.status!=OA_TASK_STATUS_ABOLISH&&oaTask.status!=OA_TASK_STATUS_FINISH}"/>
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
                    <a href="javascript:;">${oaTask.name}-报送详情</a>
                </li>
            </ul>
        </div>
    </div>
    <div class="widget-body">
         <c:set var="_query"
                   value="${not empty param.userId ||not empty param.mobile ||not empty param.hasReport ||not empty param.status}"/>
        <div class="widget-main padding-4">
            <div class="tab-content padding-8">
                <div class="jqgrid-vertical-offset buttons">
                    <c:if test="${taskCanEdit}">
                    <a class="popupBtn btn btn-warning btn-sm"
                       data-url="${ctx}/oa/oaTaskUser_unreportMsg?taskId=${oaTask.id}"><i class="fa fa-send"></i>
                        催促未报送对象</a>
                    <a class="jqBatchBtn btn btn-success btn-sm"
                           data-querystr="taskId=${oaTask.id}"
                           data-title="批量报送"
                           data-msg="确定报送这{0}个任务？（即批量设置任务为已报送，相应记录转入已办事项列表）"
                           data-url="${ctx}/oa/oaTaskUser_isReport"
                           data-grid-id="#jqGrid2"><i class="fa fa-external-link"></i>
                            批量报送</a>
                    <a class="jqOpenViewBatchBtn btn btn-primary btn-sm"
                       data-querystr="taskId=${oaTask.id}"
                       data-ids-name="taskUserIds"
                       data-grid-id="#jqGrid2"
                       data-url="${ctx}/oa/oaTaskUser_check"><i class="fa fa-check-square-o"></i> 批量审批</a>
                    </c:if>
                    <button type="button" class="jqExportBtn btn btn-success btn-sm tooltip-success"
                        data-grid-id="#jqGrid2" data-search-form-id="#searchForm2"
                            data-url="${ctx}/oa/oaTaskUser_data?taskId=${oaTask.id}"
                               data-rel="tooltip" data-placement="top" title="导出当前搜索的全部结果（按照当前排序）">
                                <i class="fa fa-download"></i> 导出</button>

                    <button type="button" class="jqExportBtn btn btn-info btn-sm tooltip-success"
                        data-grid-id="#jqGrid2" data-search-form-id="#searchForm2"
                            data-grid-id="#jqGrid2"
                            data-url="${ctx}/oa/oaTaskUser_data?taskId=${oaTask.id}"
                            data-export="2" data-rel="tooltip" data-placement="top" title="导出当前搜索的全部结果（按照当前排序）">
                                <i class="fa fa-file-zip-o"></i> 打包下载附件</button>

                     <span style="margin-left: 20px;">
                            任务对象共${totalCount}个，完成报送共${hasReportCount}个（通过审核共${passCount}个），未报送${totalCount-hasReportCount}个
                    </span>
                </div>
                <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
                            <div class="widget-header">
                                <h4 class="widget-title">搜索</h4><span class="widget-note">${note_searchbar}</span>

                                <div class="widget-toolbar">
                                    <a href="#" data-action="collapse">
                                        <i class="ace-icon fa fa-chevron-${_query?'up':'down'}"></i>
                                    </a>
                                </div>
                            </div>
                            <div class="widget-body">
                                <div class="widget-main no-padding">
                                    <form class="form-inline search-form" id="searchForm2">
                                        <div class="form-group">
                                            <label>姓名</label>
                                            <select data-rel="select2-ajax" data-width="200"
                                                    data-ajax-url="${ctx}/sysUser_selects?types=${USER_TYPE_JZG},${USER_TYPE_RETIRE}"
                                                    name="userId" data-placeholder="请输入账号或姓名或工号">
                                                <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label>手机号码</label>
                                            <input class="form-control search-query" name="mobile" type="text"
                                                   value="${param.mobile}"
                                                   placeholder="请输入">
                                        </div>
                                        <div class="form-group">
                                            <label>报送情况</label>
                                            <select name="hasReport" data-width="100" data-rel="select2"
                                                            data-placeholder="请选择">
                                                <option></option>
                                                <option value="1">已报送</option>
                                                <option value="0">未报送</option>
                                            </select>
                                            <script>
                                                $("#searchForm2 select[name=hasReport]").val('${param.hasReport}');
                                            </script>
                                        </div>
                                        <div class="form-group">
                                            <label>审批状态</label>
                                           <select class="form-control" name="status"
                                                    data-rel="select2"
                                                    data-width="150"
                                                    data-placeholder="请选择">
                                                <option></option>
                                                <c:forEach items="<%=OaConstants.OA_TASK_USER_STATUS_MAP%>" var="_status">
                                                    <option value="${_status.key}">${_status.value}</option>
                                                </c:forEach>
                                            </select>
                                            <script>
                                                $("#searchForm2 select[name=status]").val('${param.status}');
                                            </script>
                                        </div>
                                        <div class="clearfix form-actions center">
                                            <a class="jqSearchBtn btn btn-default btn-sm"
                                               data-target="#body-content-view"
                                               data-form="#searchForm2"
                                            data-url="${ctx}/oa/oaTaskUser?taskId=${oaTask.id}"><i class="fa fa-search"></i>
                                                查找</a>

                                            <c:if test="${_query}">&nbsp;
                                                <button type="button"
                                                        data-url="${ctx}/oa/oaTaskUser?taskId=${oaTask.id}"
                                                         data-target="#body-content-view"
                                                        class="reloadBtn btn btn-warning btn-sm">
                                                    <i class="fa fa-reply"></i> 重置
                                                </button>
                                            </c:if>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                <div class="space-4"></div>
                <table id="jqGrid2" class="jqGrid2 table-striped"></table>
                <div id="jqGridPager2"></div>
            </div>
        </div>
    </div>
</div>
<script>
    $.register.user_select($("#searchForm2 select[name=userId]"));
    $('#searchForm2 [data-rel="select2"]').select2();
    function _oaTaskUser_reload(){
        $("#jqGrid2").trigger("reloadGrid");
    }
    $.register.date($('.date-picker'));
    $("#jqGrid2").jqGrid({
        rownumbers:true,
        multiselect:${taskCanEdit},
        //forceFit:true,
        pager: "jqGridPager2",
        url: '${ctx}/oa/oaTaskUser_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '工作证号', name: 'code', width:120},
            {label: '姓名', name: 'realname', width:120},
            {label: '所在单位及职务', name: 'title', width:280, align:'left'},
            {label: '手机号码', name: 'mobile', width:120},
            {label: '指定负责人', name: 'assignRealname', width:180, formatter: function (cellvalue, options, rowObject) {
                if(cellvalue==undefined) return '--';
                return cellvalue + "({0})".format(rowObject.assignCode)
            }},
            {label: '指定负责人手机号', name: 'assignUserMobile', width:140, formatter: function (cellvalue, options, rowObject) {
                if(cellvalue==undefined) return '--';
                return cellvalue
            }},
            {
                label: '报送', name: '_report', formatter: function (cellvalue, options, rowObject) {

                    var hasChecked = (rowObject.status!=undefined && rowObject.status=='<%=OaConstants.OA_TASK_USER_STATUS_PASS%>')
                    if(hasChecked) return '已审批'

                    return ('<button class="openView btn {2} btn-xs"' +
                        'data-url="${ctx}/user/oa/oaTaskUser_report?taskId={0}&userId={4}&type=report"><i class="fa {3}"></i> {1}</button>')
                        .format(rowObject.taskId, (rowObject.hasReport?'修改':'报送'),
                            (rowObject.hasReport?'btn-primary':'btn-success'),
                            (rowObject.hasReport?'fa-edit':'fa-check'),rowObject.userId)
                }
            },
            {label: '报送情况', name: 'hasReport', formatter: function (cellvalue, options, rowObject) {

                if(!cellvalue) return '未报送';

                return '<button class="popupBtn btn btn-success btn-xs" data-width="800" ' +
                        'data-url="${ctx}/oa/oaTaskUser_report?id={0}"><i class="fa fa-search"></i> 已报送</button>'
                                .format(rowObject.id)
            }},
            {label: '报送人', name: 'reportRealname', width:120, formatter: function (cellvalue, options, rowObject) {
                if(cellvalue==undefined) return '--';
                return cellvalue
            }},
            {label: '审核情况', name: 'status', formatter: function (cellvalue, options, rowObject) {
                <c:if test="${!taskCanEdit}"> return '--'</c:if>
                if(cellvalue==undefined) return '--'
                if(cellvalue=='<%=OaConstants.OA_TASK_USER_STATUS_INIT%>'){
                    return '<button class="popupBtn btn btn-primary btn-xs"' +
                            'data-url="${ctx}/oa/oaTaskUser_check?taskId={0}&taskUserIds={1}"><i class="fa fa-check-square-o"></i> 审核</button>'
                                    .format(rowObject.taskId, rowObject.userId)
                }

                return _cMap.OA_TASK_USER_STATUS_MAP[cellvalue];
            }},
            <c:if test="${_show_msg_btns}">
            { label: '通知提醒',name: '_sendMsg', formatter: function (cellvalue, options, rowObject) {
                if(rowObject.status=='<%=OaConstants.OA_TASK_USER_STATUS_DENY%>')
                    return ('<button class="popupBtn btn btn-warning btn-xs" ' +
                    'data-url="${ctx}/oa/oaTaskUser_denyMsg?id={0}"><i class="fa fa-send"></i> 通知提醒</button>')
                            .format(rowObject.id);
                return "-";
            }},
            </c:if>
            { label: '退回',name: '_back', formatter: function (cellvalue, options, rowObject) {

                <c:if test="${!taskCanEdit}"> return '--'</c:if>

                if(rowObject.isBack) return '已退回'
                if(!rowObject.hasReport) return '--'
                return ('<button class="confirm btn btn-danger btn-xs" data-callback="_oaTaskUser_reload"  data-title="退回"  data-msg="确定退回“{1}”的报送？"' +
                'data-url="${ctx}/oa/oaTaskUser_back?id={0}"><i class="fa fa-reply"></i> 退回</button>')
                        .format(rowObject.id, rowObject.realname);
            }},{hidden:true, name: 'userId', key:true}
        ]
    }).jqGrid("setFrozenColumns");

    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");
    $('[data-rel="tooltip"]').tooltip();
</script>