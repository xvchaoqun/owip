<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12 rownumbers">

        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/oa/oaTask"
             data-url-export="${ctx}/oa/oaTask_data"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query"
                   value="${not empty param.userId ||not empty param.type ||not empty param.name || not empty param.code || not empty param.sort}"/>
            <div class="tabbable">
                <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                    <li class="<c:if test="${cls==1}">active</c:if>">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/oa/oaTask?cls=1&showAll=${showAll?1:0}"><i
                                class="fa fa-list"></i> 新建任务</a>
                    </li>
                    <li class="<c:if test="${cls==2}">active</c:if>">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/oa/oaTask?cls=2&showAll=${showAll?1:0}"><i
                                class="fa fa-check-square-o"></i> 已完成</a>
                    </li>
                    <li class="<c:if test="${cls==3}">active</c:if>">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/oa/oaTask?cls=3&showAll=${showAll?1:0}"><i
                                class="fa fa-trash-o"></i> 作废</a>
                    </li>
                    <c:if test="${oaTaskAdmin.showAll}">
                    <div class="type-select">
                            <span class="typeCheckbox ${showAll?"checked":""}">
                            <input class="big" ${showAll?"checked":""} type="checkbox"
                                                                         value="1"> 显示全部任务
                            </span>
                    </div>
                    </c:if>
                </ul>

                <div class="tab-content">
                    <div class="tab-pane in active">
                        <div class="jqgrid-vertical-offset buttons">
                            <c:if test="${cls==1}">
                            <shiro:hasPermission name="oaTask:edit">
                                <a class="openView btn btn-success btn-sm"
                                   data-url="${ctx}/oa/oaTask_au"><i class="fa fa-plus"></i> 新建</a>
                                <a class="jqOpenViewBtn btn btn-primary btn-sm"
                                   data-url="${ctx}/oa/oaTask_au"
                                   data-grid-id="#jqGrid"
                                   data-open-by="page"><i class="fa fa-edit"></i> 修改</a>
                                <a class="jqBatchBtn btn btn-danger btn-sm"
                                   data-title="作废"
                                   data-msg="确定作废这{0}个任务？"
                                   data-url="${ctx}/oa/oaTask_abolish"
                                   data-grid-id="#jqGrid"><i class="fa fa-times"></i>
                                    作废</a>
                                <a class="jqOpenViewBtn btn btn-info btn-sm"
                                   data-url="${ctx}/oa/oaTaskUser_infoMsg"
                                   data-grid-id="#jqGrid" data-width="800"
                                   data-id-name="taskId"><i class="fa fa-send"></i>
                                    下发任务通知</a>
                            </shiro:hasPermission>
                            </c:if>
                            <button class="jqOpenViewBtn btn btn-warning btn-sm"
                                   data-url="${ctx}/oa/oaTaskUser"
                                   data-id-name="taskId"
                                   data-open-by="page"><i class="fa fa-search"></i> 报送详情</button>
                            <c:if test="${cls==1}">
                                <button id="finishBtn" class="jqItemBtn btn btn-success btn-sm"
                                   data-url="${ctx}/oa/oaTask_finish"
                                   data-msg="确定任务完结？（转移至已完成列表）"data-callback="_reload"
                                   data-open-by="page"><i class="fa fa-check-square-o"></i> 任务完结</button>
                            </c:if>
                            <c:if test="${cls==2}">
                                <button class="jqItemBtn btn btn-success btn-sm"
                                   data-url="${ctx}/oa/oaTask_finish?isFinish=0"
                                   data-msg="确定返回任务列表？"data-callback="_reload"
                                   data-open-by="page"><i class="fa fa-reply"></i> 返回任务列表</button>
                            </c:if>
                            <c:if test="${cls==3}">
                            <shiro:hasPermission name="oaTask:del">
                                <a class="jqBatchBtn btn btn-success btn-sm"
                                   data-title="返回任务列表"
                                   data-msg="确定重新启用这{0}个任务？"
                                   data-url="${ctx}/oa/oaTask_abolish?isAbolish=0"
                                   data-grid-id="#jqGrid"><i class="fa fa-reply"></i>
                                    返回任务列表</a>
                                <button data-url="${ctx}/oa/oaTask_batchDel"
                                        data-title="删除"
                                        data-msg="确定删除这{0}个任务？"
                                        data-grid-id="#jqGrid"
                                        class="jqBatchBtn btn btn-danger btn-sm">
                                    <i class="fa fa-times"></i> 删除
                                </button>
                            </shiro:hasPermission>
                            </c:if>
                            <%--<a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                               data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                                <i class="fa fa-download"></i> 导出</a>--%>
                        </div>
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
                                        <input type="hidden" name="showAll" value="${showAll?1:0}">
                                        <div class="form-group">
                                            <label>工作类型</label>
                                            <select class="form-control" name="type"
                                                    data-rel="select2"
                                                    data-width="150"
                                                    data-placeholder="请选择">
                                                <option></option>
                                                <c:forEach items="${oaTaskTypes}" var="oaTaskType">
                                                    <c:set var="_type" value="${cm:getMetaType(oaTaskType)}"/>
                                                    <option value="${_type.id}">${_type.name}</option>
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
                                                <button type="button" class="reloadBtn btn btn-warning btn-sm">
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
<style>
    .type-select {
        float: right;
        padding: 5px 50px 0px 5px;
    }

    .type-select a {
        padding-left: 20px;
    }

    .type-select .typeCheckbox {
        padding: 10px;
        font-size: 20px;
    }

    .type-select .typeCheckbox.checked {
        color: darkred;
        font-weight: bolder;
    }
</style>
<script>
    $(":checkbox", ".typeCheckbox").click(function () {
        $("#searchForm input[name=showAll]").val($(this).prop("checked") ? 1 : 0);
        $("#searchForm .jqSearchBtn").click();
    })
    function _reload(){
        $(window).triggerHandler('resize.jqGrid');
    }

    $("#jqGrid").jqGrid({
        rownumbers: true,
        url: '${ctx}/oa/oaTask_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '发布日期', name: 'pubDate', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}, frozen:true},
            {label: '标题', name: 'name', width:300, align:'left', frozen:true, formatter: function (cellvalue, options, rowObject) {

                return '<a href="javascript:;" class="openView" ' +
                        'data-url="${ctx}/oa/oaTask_au?id={0}">{1}</a>'
                                .format(rowObject.id, rowObject.name);
            }},
            <c:if test="${cls==1}">
            {
                label: '发布', name: '_publish', width: 80, formatter: function (cellvalue, options, rowObject) {
                if (rowObject.isPublish) return '已发布';
                return '<button class="confirm btn btn-success btn-xs" data-msg="确定发布？" data-callback="_reload"' +
                        'data-url="${ctx}/oa/oaTask_publish?id={0}&publish=1"><i class="fa fa-check"></i> 发布</button>'
                                .format(rowObject.id)
            }},
            {
                label: '召回', name: '_publish', width: 80, formatter: function (cellvalue, options, rowObject) {
                if (!rowObject.isPublish) return '--';
                return '<button class="confirm btn btn-danger btn-xs" data-msg="确定召回？" data-callback="_reload"' +
                        'data-url="${ctx}/oa/oaTask_publish?id={0}&publish=0"><i class="fa fa-reply"></i> 召回</button>'
                                .format(rowObject.id)
            }},
            </c:if>
            {
                label: '共享任务', name: '_share', width: 90, formatter: function (cellvalue, options, rowObject) {
                if (rowObject.userId!='${_user.id}') return rowObject.user.realname;
                var len = 0;
                if(rowObject.userIds){
                    len = rowObject.userIds.split(",").length
                }
                return '<button class="popupBtn btn btn-success btn-xs" ' +
                        'data-url="${ctx}/oa/oaTask_share?taskId={0}"><i class="fa fa-share-alt"></i> 共享({1})</button>'
                                .format(rowObject.id, len)
            }},
            {label: '工作类型', name: 'type', formatter: $.jgrid.formatter.MetaType},
            {
                label: '应完成时间',
                name: 'deadline',
                width: 140,
                formatter: $.jgrid.formatter.date,
                formatoptions: {srcformat: 'Y-m-d H:i', newformat: 'Y-m-d H:i'}
            },
            {label: '联系方式', name: 'contact', width: 250},
            {
                label: '附件', name: '_files', formatter: function (cellvalue, options, rowObject) {

                return '<button class="popupBtn btn btn-info btn-xs" data-width="500" data-callback="_reload"' +
                        'data-url="${ctx}/oa/oaTaskFiles?taskId={0}"><i class="fa fa-search"></i> 附件{1}</button>'
                                .format(rowObject.id, rowObject.fileCount>0?"("+rowObject.fileCount+")":"")
            }},
            {
                label: '任务对象', name: '_users', width: 120, formatter: function (cellvalue, options, rowObject) {

                return '<button class="openView btn btn-warning btn-xs" ' +
                        'data-url="${ctx}/oa/oaTask_users?id={0}"><i class="fa fa-search"></i> 任务对象{1}</button>'
                                .format(rowObject.id, rowObject.userCount>0?"("+rowObject.userCount+")":"")
            }},
            {label: '已完成数', name: 'finishCount', width: 80},
            {label: '完成率', name: '_rate', width: 80, formatter: function (cellvalue, options, rowObject) {
                if(rowObject.userCount==0) return '--'
                return parseFloat(rowObject.finishCount*100/rowObject.userCount).toFixed(2) + "%";
            }},
            /*{
                label: '报送详情', name: '_detail', formatter: function (cellvalue, options, rowObject) {
                if(rowObject.userCount==0) return '--'
                return '<button class="openView btn btn-success btn-xs"' +
                        'data-url="${ctx}/oa/oaTaskUser?taskId={0}"><i class="fa fa-search"></i> 查看</button>'
                                .format(rowObject.id)
            }},*/
            <c:if test="${cls==1}">
            {
                label: '任务完结', name: '_finish', width: 80, formatter: function (cellvalue, options, rowObject) {

                if(rowObject.userCount==0) return '--'
                return (rowObject.finishCount<rowObject.userCount)?"否":"是"
            }},
            </c:if>
            {label: '创建时间', name: 'createTime', width: 180}, {name: 'userCount', hidden:true}
        ]/*,
        onSelectRow: function (id, status) {
            saveJqgridSelected("#" + this.id, id, status);
            _onSelectRow(this)
        },
        onSelectAll: function (aRowids, status) {
            saveJqgridSelected("#" + this.id);
            _onSelectRow(this)
        }*/
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");

    /*function _onSelectRow(grid) {
        var ids = $(grid).getGridParam("selarrrow");
        if (ids.length > 1) {
            $("#finishBtn").prop("disabled", true);
        } else if (ids.length == 1) {
            var rowData = $(grid).getRowData(ids[0]);
            var isFinish = (rowData.finishCount==rowData.userCount);
            $("#finishBtn").prop("disabled", !isFinish);
        }
    }*/

    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>