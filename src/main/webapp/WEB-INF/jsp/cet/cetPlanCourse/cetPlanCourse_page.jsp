<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/cet/constants.jsp" %>
<div class="widget-box transparent">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:" class="openView btn btn-xs btn-success"
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
                    <a href="javascript:;">课程列表</a>
                </li>
            </ul>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main padding-12 no-padding-left no-padding-right no-padding-bottom">
            <c:set var="_query"
                   value="${not empty param.year ||not empty param.num ||not empty param.name || not empty param.code || not empty param.sort}"/>
            <div class="jqgrid-vertical-offset buttons">

                <button class="popupBtn btn btn-info btn-sm"
                        data-width="1200"
                        data-url="${ctx}/cet/cetPlanCourse_selectCourses?planId=${cetProjectPlan.id}"><i
                        class="fa fa-plus"></i> 添加课程</button>
                <button data-url="${ctx}/cet/cetPlanCourse_batchDel"
                        data-title="彻底删除"
                        data-msg="确定彻底删除这{0}条数据？（该课程下的所有数据均将彻底删除，删除后无法恢复，请谨慎操作！）"
                        data-grid-id="#jqGrid2"
                        class="jqBatchBtn btn btn-danger btn-sm">
                    <i class="fa fa-trash"></i> 彻底删除
                </button>
                <button class="jqOpenViewBtn btn btn-primary btn-sm"
                        data-url="${ctx}/cet/cetPlanCourse_info"
                        data-grid-id="#jqGrid2"
                        data-id-name="planCourseId"><i class="fa fa-edit"></i>
                    编辑课程信息</button>
            </div>

            <div class="space-4"></div>
            <table id="jqGrid2" class="jqGrid2 table-striped"></table>
            <div id="jqGridPager2"></div>
        </div>
    </div>
</div>
<script>
    var objCount = ${cetProject.objCount};
    var projectId = ${cetProject.id};
    $("#jqGrid2").jqGrid({
        rownumbers:true,
        pager: "jqGridPager2",
        url: '${ctx}/cet/cetPlanCourse_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            <c:if test="${cetProjectPlan.type==CET_PROJECT_PLAN_TYPE_SELF}">
            {
                label: '学习情况', name: 'selectedCount', width: 120, formatter: function (cellvalue, options, rowObject) {

                if(cellvalue==undefined) cellvalue=0;

                <c:if test="${cetProjectPlan.type == CET_PROJECT_PLAN_TYPE_SPECIAL}">var cls = 3;</c:if>
                <c:if test="${cetProjectPlan.type == CET_PROJECT_PLAN_TYPE_SELF}">var cls = 6;</c:if>
                return ('<button class="openView btn btn-primary btn-xs" ' +
                'data-url="${ctx}/cet/cetProject_detail_obj?cls={4}&projectId={0}&planCourseId={1}">已选课({2}/{3})</button>')
                        .format(projectId, rowObject.id, cellvalue, objCount, cls);

            }},
            {label: '编号', name: 'cetCourse.sn'},
            {label: '名称', name: 'cetCourse.name', width: 300, align: 'left'},
            {label: '学习内容', name: '_content', width: 80, formatter: function (cellvalue, options, rowObject) {
                return ('<button type="button" data-url="${ctx}/cet/cetCourseFile?view=1&courseId={0}" ' +
                'class="popupBtn btn btn-xs btn-success"><i class="ace-icon fa fa-search"></i> 详情</button>')
                        .format(rowObject.cetCourse.id)
            }},
            {
                label: '排序', align: 'center',formatter: $.jgrid.formatter.sortOrder,
                formatoptions: {url: "${ctx}/cet/cetPlanCourse_changeOrder"}
            },
            {label: '学时', name: 'cetCourse.period', width: 70},
            { label: '学习时间',name: '_time', width: 280, formatter: function (cellvalue, options, rowObject) {
                return '{0} ~ {1}'.format($.date(rowObject.startTime, "yyyy-MM-dd HH:mm"), $.date(rowObject.endTime, "yyyy-MM-dd HH:mm"))
            }},
            {label: '是否要求上传学习心得', name: 'needNote',formatter: $.jgrid.formatter.TRUEFALSE, width: 170}
            </c:if>
            <c:if test="${cetProjectPlan.type==CET_PROJECT_PLAN_TYPE_SPECIAL}">
            {
                label: '学习情况', name: 'selectedCount', formatter: function (cellvalue, options, rowObject) {
                if(cellvalue==undefined) cellvalue=0;
                return ('<button class="openView btn btn-primary btn-xs" ' +
                'data-url="${ctx}/cet/cetProject_detail_obj?cls=3&projectId={0}&planCourseId={1}">已选课({2}/{3})</button>')
                        .format(projectId, rowObject.id, cellvalue, objCount);
            }, frozen: true},
            {label: '编号', name: 'cetCourse.sn', frozen: true},
            {label: '网上专题培训班名称', name: 'cetCourse.name', width: 300, align: 'left', frozen: true},
            {
                label: '排序', align: 'center',formatter: $.jgrid.formatter.sortOrder,
                formatoptions: {url: "${ctx}/cet/cetPlanCourse_changeOrder"}, frozen: true
            },
            {label: '上级单位名称', name: 'cetCourse.address', width: 300, align: 'left'},
            {label: '总学时', name: 'cetCourse.totalPeriod', width: 70},
            {label: '专题班', name: '_items', width: 80, formatter: function (cellvalue, options, rowObject) {
                return ('<button type="button" data-url="${ctx}/cet/cetCourseItem?courseId={0}" ' +
                'class="popupBtn btn btn-xs btn-success"><i class="ace-icon fa fa-search"></i> 详情</button>')
                        .format(rowObject.cetCourse.id)
            }},
            { label: '学习时间',name: '_time', width: 280, formatter: function (cellvalue, options, rowObject) {
                return '{0} ~ {1}'.format($.date(rowObject.startTime, "yyyy-MM-dd HH:mm"), $.date(rowObject.endTime, "yyyy-MM-dd HH:mm"))
            }},
            {label: '附件', name: '_file', formatter: function (cellvalue, options, rowObject) {
                var str = "";
                if(rowObject.filePath!=undefined){
                    str += ("<button class='linkBtn btn btn-xs btn-success' " +
                    "data-url='${ctx}/attach/download?path={0}&filename={1}'>"+
                            "<i class='fa fa-download'></i> 下载</button> &nbsp;").format(rowObject.filePath, rowObject.fileName)
                }
                return str + ('<button class="popupBtn btn btn-primary btn-xs" ' +
                'data-url="${ctx}/cet/cetPlanCourse_upload?planCourseId={0}"><i class="fa fa-upload"></i> 上传</button>')
                        .format(rowObject.id);
            }},
            {label: '备注', name: 'remark', width: 400}
            </c:if>
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>