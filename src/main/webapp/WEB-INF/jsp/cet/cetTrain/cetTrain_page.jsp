<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/cet/constants.jsp" %>
<div class="widget-box transparent">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:;" class="openView btn btn-xs btn-success"
               data-url="${ctx}/cet/cetProject_detail?projectId=${cetProjectPlan.projectId}">
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
            <c:set var="_query"
                   value="${not empty param.year ||not empty param.num ||not empty param.name || not empty param.code || not empty param.sort}"/>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="cetTrain:edit">
                    <button class="popupBtn btn btn-info btn-sm"
                            data-url="${ctx}/cet/cetTrain_au?planId=${param.planId}"><i
                            class="fa fa-plus"></i> 创建培训班
                    </button>
                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                            data-url="${ctx}/cet/cetTrain_au"
                            data-grid-id="#jqGrid2"><i class="fa fa-edit"></i>
                        修改
                    </button>

                    <button id="finishBtn" class="jqItemBtn btn btn-warning btn-sm"
                            data-url="${ctx}/cet/cetTrain_finish?isFinished=1"
                            data-title="结课"
                            data-msg="确定培训班结课？"
                            data-callback="_reload3"
                            data-grid-id="#jqGrid2">
                        <i class="prompt fa fa-question-circle"
                           data-prompt="结课后，该培训班将不显示在参训人员的[学习培训中心-选课中心]"></i>
                        结课
                    </button>

                    <button id="unfinishBtn" class="jqItemBtn btn btn-success btn-sm"
                            data-url="${ctx}/cet/cetTrain_finish?isFinished=0"
                            data-title="未结课"
                            data-msg="确定培训班未结课？"
                            data-callback="_reload3"
                            data-grid-id="#jqGrid2">
                        <i class="fa fa-reply"></i>
                        未结课
                    </button>
                </shiro:hasPermission>

                <button data-url="${ctx}/cet/cetTrain_batchDel"
                        data-title="彻底删除"
                        data-msg="确定彻底删除这{0}条数据？（该培训班下的所有数据均将彻底删除，删除后无法恢复，请谨慎操作！）"
                        data-grid-id="#jqGrid2"
                        class="jqBatchBtn btn btn-danger btn-sm">
                    <i class="fa fa-trash"></i> 彻底删除
                </button>
                <%--<a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出</a>--%>
            </div>
            <%--<div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
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
                        <form class="form-inline search-form" id="searchForm">
                            <input type="hidden" name="cls" value="${cls}">
                            <div class="form-group">
                                <label>年度</label>
                                <input class="form-control search-query" name="year" type="text"
                                       value="${param.year}"
                                       placeholder="请输入">
                            </div>
                            <div class="form-group">
                                <label>编号</label>
                                <input class="form-control search-query" name="num" type="text"
                                       value="${param.num}"
                                       placeholder="请输入编号">
                            </div>
                            <div class="form-group">
                                <label>培训班名称</label>
                                <input class="form-control search-query" name="name" type="text"
                                       value="${param.name}"
                                       placeholder="请输入培训班名称">
                            </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i>
                                    查找</a>

                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                            data-querystr="cls=${cls}">
                                        <i class="fa fa-reply"></i> 重置
                                    </button>
                                </c:if>
                            </div>
                        </form>
                    </div>
                </div>
            </div>--%>
            <div class="space-4"></div>
            <table id="jqGrid2" class="jqGrid2 table-striped"></table>
            <div id="jqGridPager2"></div>
        </div>
    </div>
</div>
<script>
    function _reload3() {
        $("#jqGrid2").trigger("reloadGrid");
    }
    $("#jqGrid2").jqGrid({
        rownumbers:true,
        pager: "jqGridPager2",
        url: '${ctx}/cet/cetTrain_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {
                label: '详情', name: '_detail', width:80, formatter: function (cellvalue, options, rowObject) {
                return ('<button class="openView btn btn-success btn-xs" ' +
                'data-url="${ctx}/cet/cetTrain_detail?trainId={0}"><i class="fa fa-search"></i> 详情</button>')
                        .format(rowObject.id);
            }, frozen: true
            },

            {
                label: '结课状态', name: '_isFinished', width: 80, formatter: function (cellvalue, options, rowObject) {
                return rowObject.isFinished ? '已结课' : '未结课';
            }, frozen: true},
            {label: '培训班类型', name: 'type', width: 250, formatter: $.jgrid.formatter.MetaType, frozen: true},
            {label: '培训班名称', name: 'name', width: 300, align: 'left', frozen: true},
            {label: '可选课人数', name: 'objCount', width: 90},
            {
                label: '选课情况',
                name: '_switchStatus',
                width: 120,
                formatter: function (cellvalue, options, rowObject) {

                    var str = _cMap.CET_TRAIN_ENROLL_STATUS_MAP[rowObject.switchStatus] +
                            "(" + Math.trimToZero(rowObject.traineeCount) + ")"
                    if (rowObject.switchStatus ==${CET_TRAIN_ENROLL_STATUS_OPEN}){
                        str = '<span class="text-success bolder">' + str + '</span>'
                    }

                    return str;
                }
            },

            {
                label: '内容简介', name: '_summary', width: 80, formatter: function (cellvalue, options, rowObject) {
                var btnStr = "添加";
                var btnCss = "btn-success";
                var iCss = "fa-plus";
                if (rowObject.hasSummary) {
                    btnStr = "查看";
                    btnCss = "btn-primary";
                    iCss = "fa-search";
                }

                return ('<button class="popupBtn btn {2} btn-xs" data-width="750" ' +
                'data-url="${ctx}/cet/cetTrain_summary?id={0}"><i class="fa {3}"></i> {1}</button>')
                        .format(rowObject.id, btnStr, btnCss, iCss);
            }, frozen: true
            },
           /* {label: '参训人员类型', name: 'traineeTypes', width: 220, align: 'left'},*/
            /*{label: '开班时间', name: 'openTime', width: 150, formatter: $.jgrid.formatter.date,
                formatoptions: {srcformat: 'Y-m-d H:i', newformat: 'Y-m-d H:i'},},
            {label: '开班地点', name: 'openAddress', width: 220, align: 'left'},*/
            {label: '开课日期', name: 'startDate', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
            {label: '结课日期', name: 'endDate', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
            {label: '备注', name: 'remark', width: 300}, {hidden: true, name: 'pubStatus'},
            {hidden: true, name: 'isFinished'}
        ],
        onSelectRow: function (id, status) {
            saveJqgridSelected("#" + this.id, id, status);
            _onSelectRow(this)
        },
        onSelectAll: function (aRowids, status) {
            saveJqgridSelected("#" + this.id);
            _onSelectRow(this)
        }
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();

    function _onSelectRow(grid) {
        var ids = $(grid).getGridParam("selarrrow");

        if (ids.length > 1) {
            $("#finishBtn,#unfinishBtn").prop("disabled", true);
        } else if (ids.length == 1) {
            var rowData = $(grid).getRowData(ids[0]);
            var isFinished = (rowData.isFinished == "true");

            $("#finishBtn").prop("disabled", isFinished);
            $("#unfinishBtn").prop("disabled", !isFinished);
        }
    }
</script>