<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/cet/cetTrain"
             data-url-export="${ctx}/cet/cetTrain_data"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query"
                   value="${not empty param.year ||not empty param.num ||not empty param.name || not empty param.code || not empty param.sort}"/>
            <div class="tabbable">
                <jsp:include page="menu.jsp"/>
                <div class="tab-content">
                    <div id="home4" class="tab-pane in active">
                        <div class="jqgrid-vertical-offset buttons">
                            <c:if test="${cls==1}">
                            <shiro:hasPermission name="cetTrain:edit">
                                <button class="popupBtn btn btn-info btn-sm" data-url="${ctx}/cet/cetTrain_au"><i
                                        class="fa fa-plus"></i> 创建培训班</button>
                                <button class="jqOpenViewBtn btn btn-primary btn-sm"
                                   data-url="${ctx}/cet/cetTrain_au"
                                   data-grid-id="#jqGrid"
                                   data-querystr="&"><i class="fa fa-edit"></i>
                                    修改</button>
                                <button id="pubBtn" class="jqItemBtn btn btn-success btn-sm"
                                        data-url="${ctx}/cet/cetTrain_pub"
                                        data-title="发布"
                                        data-msg="确定发布该培训班？"
                                        data-callback="_reload"
                                        data-grid-id="#jqGrid"
                                        data-querystr="pubStatus=1"><i class="fa fa-check"></i>
                                    发布</button>
                                <button id="unPubBtn" class="jqItemBtn btn btn-warning btn-sm"
                                        data-url="${ctx}/cet/cetTrain_pub"
                                        data-title="取消发布"
                                        data-msg="确定取消发布该培训班？"
                                        data-callback="_reload"
                                        data-grid-id="#jqGrid"
                                        data-querystr="pubStatus=2"><i class="fa fa-times"></i>
                                    取消发布</button>
                                <button id="finishBtn" class="jqItemBtn btn btn-primary btn-sm"
                                        data-url="${ctx}/cet/cetTrain_finish"
                                        data-title="结课"
                                        data-msg="确定培训班结课？"
                                        data-callback="_reload"
                                        data-grid-id="#jqGrid"
                                        data-querystr="&"><i class="fa fa-dot-circle-o"></i>
                                    结课</button>
                            </shiro:hasPermission>
                            </c:if>
                            <c:if test="${cls==1||cls==2}">
                                <shiro:hasPermission name="cetTrain:del">
                                    <button data-url="${ctx}/cet/cetTrain_fakeDel"
                                            data-title="删除"
                                            data-msg="确定删除这{0}条数据？"
                                            data-grid-id="#jqGrid"
                                            class="jqBatchBtn btn btn-danger btn-sm">
                                        <i class="fa fa-trash"></i> 删除
                                    </button>
                                </shiro:hasPermission>
                            </c:if>
                    <c:if test="${cls==3}">
                            <button data-url="${ctx}/cet/cetTrain_batchDel"
                                    data-title="彻底删除"
                                    data-msg="确定彻底删除这{0}条数据？（该培训班下的所有数据均将彻底删除，请谨慎操作！）"
                                    data-grid-id="#jqGrid"
                                    class="jqBatchBtn btn btn-danger btn-sm">
                                <i class="fa fa-trash"></i> 彻底删除
                            </button>
                        </c:if>
                            <a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                               data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                                <i class="fa fa-download"></i> 导出</a>
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
                                        <input type="hidden" name="cls" value="${cls}">
                                        <div class="form-group">
                                            <label>年度</label>
                                            <input class="form-control search-query" name="year" type="text"
                                                   value="${param.year}"
                                                   placeholder="请输入年度">
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
                                                <button type="button" class="resetBtn btn btn-warning btn-sm"
                                                        data-querystr="cls=${cls}">
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
        <div id="item-content"></div>
    </div>
</div>
<script>
    function _reload(){
        $("#jqGrid").trigger("reloadGrid");
    }
    $("#jqGrid").jqGrid({
        url: '${ctx}/cet/cetTrain_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '详情', name: '_detail', width:'80', formatter: function (cellvalue, options, rowObject) {
                return ('<button class="openView btn btn-success btn-xs" ' +
                        'data-url="${ctx}/cet/cetTrain_detail?trainId={0}"><i class="fa fa-search"></i> 详情</button>')
                        .format(rowObject.id);
            }, frozen: true},
            {label: '年度', name: 'year', width:'60', frozen: true},
            {label: '编号', name: 'sn', width: 200, frozen: true},
            {label: '培训班类型', name: 'type', width:200, formatter: $.jgrid.formatter.MetaType, frozen: true},
            {label: '培训班名称', name: 'name', width:200, align:'left', frozen: true},
            {label: '可选课人数', name: 'traineeCount', width: 90},
            {label: '选课情况', name: 'switchStatusText', formatter: function (cellvalue, options, rowObject) {
                var isOpen = (rowObject.switchStatus==${CET_TRAIN_ENROLL_STATUS_OPEN});
                var str = cellvalue;
                if(isOpen || rowObject.selectedCount>0){
                    str +="(" + rowObject.selectedCount + ")"
                    if(isOpen) str='<span class="text-success bolder">'+str+'</span>'
                }
                return (str==undefined)?'-':str;
            }},
            {label: '发布状态', name: '_pubStatus', formatter: function (cellvalue, options, rowObject) {
                if (rowObject.pubStatus == undefined) return '-';
                return ('<span class="{0}">' + _cMap.CET_TRAIN_PUB_STATUS_MAP[rowObject.pubStatus] + '</span>')
                        .format(rowObject.pubStatus!=${CET_TRAIN_PUB_STATUS_PUBLISHED}?'text-danger bolder':'text-success');

            }},

            <c:if test="${cls==3||cls==4}">
            {label: '状态', name: '_isFinished', width:110, formatter: function (cellvalue, options, rowObject) {
                var str = rowObject.isFinished?'已结课':'未结课';
                <c:if test="${cls==4}">
                str += rowObject.isDeleted?'(已删除)':'';
                </c:if>
                return str;
            }},
            </c:if>

            {label: '内容简介', name: '_summary', width: 80, formatter: function (cellvalue, options, rowObject) {
                var btnStr = "添加";
                var btnCss = "btn-success";
                var iCss = "fa-plus";
                if (rowObject.hasSummary){
                    btnStr = "查看";
                    btnCss = "btn-primary";
                    iCss = "fa-search";
                }

                return ('<button class="popupBtn btn {2} btn-xs" data-width="750" ' +
                'data-url="${ctx}/cet/cetTrain_summary?id={0}"><i class="fa {3}"></i> {1}</button>')
                        .format(rowObject.id, btnStr, btnCss, iCss);
            }, frozen:true},
            {label: '参训人员类型', name: 'traineeTypes', width:120, align:'left'},
            {label: '开课日期', name: 'startDate', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {label: '结课日期', name: 'endDate', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {label: '备注', name: 'remark', width:300}, {hidden: true, name: 'pubStatus'},
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
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();

    function _onSelectRow(grid) {
        var ids = $(grid).getGridParam("selarrrow");

        if (ids.length > 1) {
            $("#pubBtn,#unPubBtn,#finishBtn").prop("disabled", true);
        } else if (ids.length == 1) {
            var rowData = $(grid).getRowData(ids[0]);

            var pubStatus = rowData.pubStatus;
            var isFinished = (rowData.isFinished == "true");

            $("#pubBtn").prop("disabled", pubStatus==${CET_TRAIN_PUB_STATUS_PUBLISHED});
            $("#unPubBtn").prop("disabled", pubStatus!=${CET_TRAIN_PUB_STATUS_PUBLISHED});
            $("#finishBtn").prop("disabled", isFinished || pubStatus!=${CET_TRAIN_PUB_STATUS_PUBLISHED});
        }
    }
</script>