<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12 rownumbers">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content" class="myTableDiv"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query"
                   value="${not empty param.userId ||not empty param.type ||not empty param.name || not empty param.code || not empty param.sort}"/>
            <div class="tabbable">
                <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                    <li class="<c:if test="${cls==1}">active</c:if>">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/user/oa/oaTask?cls=1"><i
                                class="fa fa-clock-o"></i> 待办事项</a>
                    </li>
                    <li class="<c:if test="${cls==2}">active</c:if>">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/user/oa/oaTask?cls=2"><i
                                class="fa fa-check-square-o"></i> 已办事项</a>
                    </li>
                </ul>

                <div class="tab-content">
                    <div class="tab-pane in active">
                        <c:if test="${cls==1}">
                            <div class="jqgrid-vertical-offset buttons">
                                <button id="assinBtn" class="jqOpenViewBtn btn btn-warning btn-sm"
                                   data-url="${ctx}/user/oa/oaTaskUser_assign"
                                   data-grid-id="#jqGrid"
                                   data-id-name="taskId"
                                   ><i class="fa fa-user"></i>
                                    指定负责人</button>
                                <button id="assinMsgBtn" class="jqOpenViewBtn btn btn-info btn-sm"
                                   data-url="${ctx}/user/oa/oaTaskUser_assignMsg"
                                   data-grid-id="#jqGrid"
                                   data-id-name="taskId"
                                   ><i class="fa fa-send"></i>
                                    短信通知指定负责人</button>
                            </div>
                        </c:if>
                        <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
                            <div class="widget-header">
                                <h4 class="widget-title">搜索</h4>

                                <div class="widget-toolbar">
                                    <a href="#" data-action="collapse">
                                        <i class="ace-icon fa fa-chevron-${_query?'up':'down'}"></i>
                                    </a>
                                </div>
                            </div>
                            <div class="widget-body">
                                <div class="widget-main no-padding">
                                    <form class="form-inline search-form" id="searchForm">

                                        <div class="form-group">
                                            <label>工作类型</label>
                                            <select required class="form-control" name="type"
                                                    data-rel="select2"
                                                    data-width="150"
                                                    data-placeholder="请选择">
                                                <option></option>
                                                <c:forEach items="${OA_TASK_TYPE_MAP}" var="type">
                                                    <option value="${type.key}">${type.value}</option>
                                                </c:forEach>
                                            </select>
                                            <script>
                                                $("#searchForm select[name=type]").val('${param.type}');
                                            </script>
                                        </div>
                                        <div class="form-group">
                                            <label>标题</label>
                                            <input class="form-control search-query" name="name" type="text"
                                                   value="${param.name}"
                                                   placeholder="请输入标题">
                                        </div>
                                        <div class="clearfix form-actions center">
                                            <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i>
                                                查找</a>
                                            <c:if test="${_query}">&nbsp;
                                                <button type="button" class="resetBtn btn btn-warning btn-sm">
                                                    <i class="fa fa-reply"></i> 重置
                                                </button>
                                            </c:if>
                                        </div>
                                    </form>
                                </div>
                            </div>
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
        $(window).triggerHandler('resize.jqGrid');
    }

    $("#jqGrid").jqGrid({
        rownumbers: true,
        url: '${ctx}/user/oa/oaTask_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '发布日期', name: 'taskPubDate', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}, frozen: true},
            {label: '标题', name: 'taskName', width: 300, frozen: true},
            {
                label: '工作类型', name: 'taskType', formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return ''
                return _cMap.OA_TASK_TYPE_MAP[cellvalue];
            }, frozen: true
            },
            {
                label: '详情', name: '_detail', formatter: function (cellvalue, options, rowObject) {

                return '<button class="openView btn btn-primary btn-xs"' +
                        'data-url="${ctx}/user/oa/oaTaskUser_report?taskId={0}"><i class="fa fa-search"></i> 查看</button>'
                                .format(rowObject.taskId)
            }
            },
           /* <c:if test="${cls==1}">
            {
                label: '短信提醒', name: '_op', formatter: function (cellvalue, options, rowObject) {

                return '<button class="confirm btn btn-success btn-xs" data-msg="确定撤回？" data-callback="_reload"' +
                        'data-url="${ctx}/user/oa/oaTaskUser_back?taskId={0}"><i class="fa fa-clock-o"></i> 定时提醒</button>'
                                .format(rowObject.taskId)
            }
            },
            </c:if>*/
            <c:if test="${cls==2}">
            {
                label: '撤回', name: '_op', formatter: function (cellvalue, options, rowObject) {

                if (rowObject.status == '${OA_TASK_USER_STATUS_PASS}') return '-'

                return '<button class="confirm btn btn-warning btn-xs" data-msg="确定撤回？" data-callback="_reload"' +
                        'data-url="${ctx}/user/oa/oaTaskUser_back?taskId={0}"><i class="fa fa-reply"></i> 撤回</button>'
                                .format(rowObject.taskId)
            }
            },
            </c:if>
            {
                label: '应完成时间',
                name: 'taskDeadline',
                width: 140,
                formatter: 'date',
                formatoptions: {srcformat: 'Y-m-d H:i', newformat: 'Y-m-d H:i'}
            },
            {label: '联系方式', name: 'taskContact', width: 250},

            {
                label: '指定负责人',
                name: 'assignRealname',
                width: 120,
                formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue == undefined) return '-';
                    return cellvalue
                }
            },
            {
                label: '指定负责人手机号',
                name: 'assignUserMobile',
                width: 140,
                formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue == undefined) return '-';
                    return cellvalue
                }
            },
            {
                label: '审核情况', name: 'status', width: 130, formatter: function (cellvalue, options, rowObject) {
                if (rowObject.isBack) return '已退回'
                if (cellvalue == undefined) return '-'
                return _cMap.OA_TASK_USER_STATUS_MAP[cellvalue];
            }
            },{hidden:true, name: 'taskId', key:true}, {hidden: true, name: 'userId'}
        ],
        onSelectRow: function (id, status) {

            var ids = $(this).getGridParam("selarrrow");
            if (ids.length > 1) {
                $("#assinBtn, #assinMsgBtn").prop("disabled", true);
            } else if (ids.length==1) {
                var rowData = $(this).getRowData(ids[0]);
                $("#assinBtn").prop("disabled", rowData.userId != "${_user.id}");
                $("#assinMsgBtn").prop("disabled", rowData.userId != "${_user.id}"|| $.trim(rowData.assignRealname)=='-');
            }
        },
        onSelectAll: function (aRowids, status) {
            var ids = $(this).getGridParam("selarrrow");
            if (ids.length > 1) {
                $("#assinBtn, #assinMsgBtn").prop("disabled", true);
            } else if (ids.length==1) {
                var rowData = $(this).getRowData(ids[0]);
                $("#assinBtn").prop("disabled", rowData.userId != "${_user.id}");
                $("#assinMsgBtn").prop("disabled", rowData.userId != "${_user.id}" || $.trim(rowData.assignRealname)=='-');
            }
        }
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>