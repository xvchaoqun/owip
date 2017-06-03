<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/recruitPost"
             data-url-export="${ctx}/recruitPost_data"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query"
                   value="${not empty param.year ||not empty param.name || not empty param.code || not empty param.sort}"/>
            <div class="tabbable">
                <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                    <li class="<c:if test="${status==RECRUIT_POST_STATUS_NORMAL}">active</c:if>">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/recruitPost?status=${RECRUIT_POST_STATUS_NORMAL}"><i class="fa fa-circle-o-notch fa-spin"></i> 正在招聘</a>
                    </li>
                    <li class="<c:if test="${status==RECRUIT_POST_STATUS_FINISH}">active</c:if>">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/recruitPost?status=${RECRUIT_POST_STATUS_FINISH}"><i class="fa fa-check"></i> 完成招聘</a>
                    </li>
                    <li class="<c:if test="${status==RECRUIT_POST_STATUS_DELETE}">active</c:if>">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/recruitPost?status=${RECRUIT_POST_STATUS_DELETE}"><i class="fa fa-times"></i> 已删除</a>
                    </li>
                    <li class="">
                        <a href="javascript:;"><i class="fa fa-exclamation-circle"></i> 报名规则</a>
                    </li>
                </ul>

                <div class="tab-content">
                    <div id="home4" class="tab-pane in active rownumbers">
                        <div class="jqgrid-vertical-offset buttons">
                            <shiro:hasPermission name="recruitPost:edit">
                                <a class="popupBtn btn btn-info btn-sm" data-url="${ctx}/recruitPost_au"><i
                                        class="fa fa-plus"></i> 添加</a>
                                <a class="jqOpenViewBtn btn btn-primary btn-sm"
                                   data-url="${ctx}/recruitPost_au"
                                   data-grid-id="#jqGrid"
                                   data-querystr="&"><i class="fa fa-edit"></i>
                                    修改</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="recruitPost:del">
                                <button data-url="${ctx}/recruitPost_batchDel"
                                        data-title="删除"
                                        data-msg="确定删除这{0}条数据？"
                                        data-grid-id="#jqGrid"
                                        class="jqBatchBtn btn btn-danger btn-sm">
                                    <i class="fa fa-trash"></i> 删除
                                </button>
                            </shiro:hasPermission>
                            <a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                               data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                                <i class="fa fa-download"></i> 导出</a>
                        </div>
                        <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
                            <div class="widget-header">
                                <h4 class="widget-title">搜索</h4>

                                <div class="widget-toolbar">
                                    <a href="javascript:;" data-action="collapse">
                                        <i class="ace-icon fa fa-chevron-${_query?'up':'down'}"></i>
                                    </a>
                                </div>
                            </div>
                            <div class="widget-body">
                                <div class="widget-main no-padding">
                                    <form class="form-inline search-form" id="searchForm">
                                        <div class="form-group">
                                            <label>年度</label>
                                            <input class="form-control search-query" name="year" type="text"
                                                   value="${param.year}"
                                                   placeholder="请输入年度">
                                        </div>
                                        <div class="form-group">
                                            <label>招聘岗位</label>
                                            <input class="form-control search-query" name="name" type="text"
                                                   value="${param.name}"
                                                   placeholder="请输入招聘岗位">
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
        <div id="item-content"></div>
    </div>
</div>
<script>
    $("#jqGrid").jqGrid({
        rownumbers: true,
        url: '${ctx}/recruitPost_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '年度', name: 'year', width:'60'},
            {label: '编号', name: 'id', width:'60', formatter: function (cellvalue, options, rowObject) {
                return 'P{0}'.format(cellvalue);
            }},
            {label: '招聘岗位', name: 'name', width:'300'},
            {
                label: '行政级别', name: 'adminLevel', formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '-';
                return _cMap.adminLevelMap[cellvalue].name;
            }
            },
            {label: '所属单位', name: 'unit.name', width: 200},
            {label: '部门属性', name: 'unit.unitType.name', width: 150},
            {label: '基本条件', name: 'requirement', formatter: function (cellvalue, options, rowObject) {
                var op=($.trim(cellvalue)=='') ?'编辑':'查看'
                return '<a href="javascript:void(0)" class="popupBtn" data-width="750" data-url="${ctx}/recruitPost_requirement?id={0}">{1}</a>'
                        .format(rowObject.id, op);
            }},
            {label: '任职资格', name: 'qualification', formatter: function (cellvalue, options, rowObject) {
                var op=($.trim(cellvalue)=='') ?'编辑':'查看'
                return '<a href="javascript:void(0)" class="popupBtn" data-width="750" data-url="${ctx}/recruitPost_qualification?id={0}">{1}</a>'
                        .format(rowObject.id, op);
            }},

            {label: '报名情况', name: 'signStatus', formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '-';
                return _cMap.RECRUIT_POST_SIGN_STATUS_MAP[cellvalue];
            }},
            {label: '招聘会情况', name: 'meetingStatus', formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '-';
                return cellvalue ? "已召开" : "未召开";
            }},
            {label: '常委会情况', name: 'committeeStatus', formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '-';
                return cellvalue ? "已上会" : "未上会";
            }},
            {label: '发布状态', name: 'isPublish', formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '-';
                return cellvalue ? "已发布" : "未发布";
            }},
            {label: '岗位状态', name: 'status', formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '-';
                return _cMap.RECRUIT_POST_STATUS_MAP[cellvalue];
            }},
            {label: '备注', name: 'remark', width: 350}
        ]
    }).jqGrid("setFrozenColumns").on("initGrid", function () {
        $(window).triggerHandler('resize.jqGrid');
    })
    $.initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>