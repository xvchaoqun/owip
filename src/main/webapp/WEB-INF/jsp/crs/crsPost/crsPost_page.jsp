<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/crsPost"
             data-url-export="${ctx}/crsPost_data"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query"
                   value="${not empty param.year ||not empty param.name || not empty param.expertUserId || not empty param.sort}"/>
            <div class="tabbable">
                <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                    <li class="<c:if test="${status==CRS_POST_STATUS_NORMAL}">active</c:if>">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/crsPost?status=${CRS_POST_STATUS_NORMAL}"><i class="fa fa-circle-o-notch fa-spin"></i> 正在招聘</a>
                    </li>
                    <li class="<c:if test="${status==CRS_POST_STATUS_FINISH}">active</c:if>">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/crsPost?status=${CRS_POST_STATUS_FINISH}"><i class="fa fa-check"></i> 完成招聘</a>
                    </li>
                    <li class="<c:if test="${status==CRS_POST_STATUS_DELETE}">active</c:if>">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/crsPost?status=${CRS_POST_STATUS_DELETE}"><i class="fa fa-times"></i> 已删除</a>
                    </li>
                    <li class="<c:if test="${status==-1}">active</c:if>">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/crsPost?status=-1"><i class="fa fa-circle-o-notch"></i> 全部</a>
                    </li>
                </ul>

                <div class="tab-content">
                    <div id="home4" class="tab-pane in active rownumbers">
                        <div class="jqgrid-vertical-offset buttons">
                            <c:if test="${status==CRS_POST_STATUS_NORMAL}">
                            <shiro:hasPermission name="crsPost:edit">
                                <a class="popupBtn btn btn-info btn-sm" data-url="${ctx}/crsPost_au"><i
                                        class="fa fa-plus"></i> 添加招聘岗位</a>
                                <a class="jqOpenViewBtn btn btn-primary btn-sm"
                                   data-url="${ctx}/crsPost_au"
                                   data-grid-id="#jqGrid"
                                   data-querystr="&"><i class="fa fa-edit"></i>
                                    修改</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="crsPost:del">
                                <button data-url="${ctx}/crsPost_batchDel"
                                        data-title="删除"
                                        data-msg="确定删除这{0}条数据？"
                                        data-grid-id="#jqGrid"
                                        class="jqBatchBtn btn btn-danger btn-sm">
                                    <i class="fa fa-trash"></i> 删除
                                </button>
                            </shiro:hasPermission>
                                </c:if>
                            <c:if test="${status==CRS_POST_STATUS_DELETE}">
                            <shiro:hasPermission name="crsPost:del">
                                <button data-url="${ctx}/crsPost_realDel"
                                        data-title="删除"
                                        data-msg="确定删除这{0}条数据？"
                                        data-grid-id="#jqGrid"
                                        class="jqBatchBtn btn btn-danger btn-sm">
                                    <i class="fa fa-trash"></i> 完全删除
                                </button>
                            </shiro:hasPermission>
                            </c:if>
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
                                            <label>年份</label>

                                            <div class="input-group">
                                                    <span class="input-group-addon"> <i
                                                            class="fa fa-calendar bigger-110"></i></span>
                                                <input class="form-control date-picker" placeholder="请选择年份"
                                                       name="year" type="text"
                                                       data-date-format="yyyy" data-date-min-view-mode="2"
                                                       value="${param.year}"/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label>招聘岗位</label>
                                            <input class="form-control search-query" name="name" type="text"
                                                   value="${param.name}"
                                                   placeholder="请输入招聘岗位">
                                        </div>
                                        <div class="form-group">
                                            <label>参与专家</label>
                                            <select data-rel="select2-ajax"
                                                    data-ajax-url="${ctx}/crsExpert_selects"
                                                    name="expertUserId" data-placeholder="请输入账号或姓名或工号">
                                                <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                            </select>
                                            <script type="text/javascript">
                                                $("#searchForm select[name=expertUserId]").val(${param.expertUserId});
                                            </script>
                                        </div>
                                        <div class="form-group">
                                            <label>招聘会时间</label>
                                            <div class="input-group tooltip-success" data-rel="tooltip" title="招聘会时间范围">
                                                <span class="input-group-addon"><i class="fa fa-calendar bigger-110"></i></span>
                                                <input placeholder="请选择招聘会时间范围" data-rel="date-range-picker"
                                                       class="form-control date-range-picker" type="text"
                                                       name="meetingTime" value="${param.meetingTime}"/>
                                            </div>
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
<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>
<script  type="text/template" id="publish_tpl">
    <button class="confirm btn btn-{{=isPublish?'danger':'success'}} btn-xs"
            data-msg="{{=isPublish?'确定取消发布（此记录将移至已删除）？':'确定发布？'}}" data-callback="_reload"
    data-url="${ctx}/crsPost_publish?id={{=id}}&publish={{=isPublish?0:1}}">
        <i class="fa fa-{{=isPublish?'times':'check'}}"></i> {{=isPublish?'取消发布':'发布'}}</button>
</script>
<script>
    register_user_select($('#searchForm select[name=expertUserId]'));
    register_date($('.date-picker'));

    function _reload(){
        $("#modal").modal('hide');
        $("#jqGrid").trigger("reloadGrid");
    }

    $("#jqGrid").jqGrid({
        rownumbers: true,
        url: '${ctx}/crsPost_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '详情', name: '_detail', width:'60', formatter: function (cellvalue, options, rowObject) {
                return '<a href="javascript:void(0)" class="openView" data-url="${ctx}/crsPost_detail?id={0}">详情</a>'
                        .format(rowObject.id);
            }, frozen: true},
            {label: '年度', name: 'year', width:'60', frozen: true},
            {
                label: '编号', name: 'seq', formatter: function (cellvalue, options, rowObject) {
                var type = _cMap.CRS_POST_TYPE_MAP[rowObject.type];
                return type + "[" + rowObject.year + "]" + rowObject.seq + "号";

            }, width: 180, frozen: true
            },
            {label: '招聘岗位', name: 'name', width:'300'},
            {
                label: '行政级别', name: 'adminLevel', formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '-';
                return _cMap.adminLevelMap[cellvalue].name;
            }
            },
            {label: '所属单位', name: 'unit.name', width: 200},
            {label: '部门属性', name: 'unit.unitType.name', width: 150},
           /* {label: '基本条件', name: 'requirement', formatter: function (cellvalue, options, rowObject) {
                var op=($.trim(cellvalue)=='') ?'编辑':'查看'
                return '<a href="javascript:void(0)" class="popupBtn" data-width="750" data-url="${ctx}/crsPost_requirement?id={0}">{1}</a>'
                        .format(rowObject.id, op);
            }},
            {label: '任职资格', name: 'qualification', formatter: function (cellvalue, options, rowObject) {
                var op=($.trim(cellvalue)=='') ?'编辑':'查看'
                return '<a href="javascript:void(0)" class="popupBtn" data-width="750" data-url="${ctx}/crsPost_qualification?id={0}">{1}</a>'
                        .format(rowObject.id, op);
            }},*/

            {label: '报名状态', name: 'signStatus', formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '-';
                return _cMap.CRS_POST_ENROLL_STATUS_MAP[cellvalue];
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
            {label: '发布', name: '_publish', formatter: function (cellvalue, options, rowObject) {
                return _.template($("#publish_tpl").html().NoMultiSpace())({id: rowObject.id, isPublish:rowObject.isPublish})
            }},{label: '状态', name: 'status', formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '';
                return _cMap.CRS_POST_STATUS_MAP[cellvalue];
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