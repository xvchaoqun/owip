<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/cet/constants.jsp" %>
<div class="widget-box transparent">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:" class="openView btn btn-xs btn-success"
               data-url="${ctx}/user/cet/cetProjectPlan?projectId=${cetProjectPlan.projectId}">
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
        <div class="rownumbers widget-main padding-12 no-padding-left no-padding-right no-padding-bottom">
            <div class="jqgrid-vertical-offset buttons">
<c:if test="${cetProjectPlan.type==CET_PROJECT_PLAN_TYPE_SELF}">
                <button id="uploadNoteBtn" class="jqOpenViewBtn btn btn-info btn-sm tooltip-success"
                        data-url="${ctx}/user/cet/cetPlanCourseObj_uploadNote"
                        data-grid-id="#jqGrid2"
                        data-id-name="planCourseId"
                        data-rel="tooltip" data-placement="top"
                        title="上传学习心得"><i class="fa fa-upload"></i> 上传学习心得</button>
    </c:if>
            </div>

            <div class="space-4"></div>
            <table id="jqGrid2" class="jqGrid2 table-striped"></table>
            <div id="jqGridPager2"></div>
        </div>
    </div>
</div>
<script>

    var projectId = ${cetProject.id};
    $("#jqGrid2").jqGrid({
        rownumbers:true,
        pager: "jqGridPager2",
        url: '${ctx}/user/cet/cetPlanCourse_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            <c:if test="${cetProjectPlan.type==CET_PROJECT_PLAN_TYPE_SELF}">
            { label: '学习心得',name: 'objInfo.note', width: 80, formatter: function (cellvalue, options, rowObject) {
                if(!rowObject.needNote) return '-'
                if($.trim(rowObject.objInfo.planCourseObjId)=='') return '-'
                return ($.trim(cellvalue)=='')?"未上传": $.swfPreview(cellvalue,
                        "学习心得({0})".format(rowObject.realname), '<button class="btn btn-xs btn-primary"><i class="fa fa-search"></i> 查看</button>')
            },frozen: true},
            {label: '编号', name: 'cetCourse.sn'},
            {label: '名称', name: 'cetCourse.name', width: 300, align: 'left'},
            {label: '学习内容', name: '_content', width: 80, formatter: function (cellvalue, options, rowObject) {
                return ('<button type="button" data-url="${ctx}/cet/cetCourseFile?view=1&courseId={0}" ' +
                'class="popupBtn btn btn-xs btn-success"><i class="ace-icon fa fa-search"></i> 详情</button>')
                        .format(rowObject.cetCourse.id)
            }},
            {label: '学时', name: 'cetCourse.period', width: 70},
            { label: '学习时间',name: '_time', width: 280, formatter: function (cellvalue, options, rowObject) {
                return '{0} ~ {1}'.format($.date(rowObject.startTime, "yyyy-MM-dd hh:mm"), $.date(rowObject.endTime, "yyyy-MM-dd hh:mm"))
            }},
            {name: 'needNote', hidden:true},
            </c:if>
            <c:if test="${cetProjectPlan.type==CET_PROJECT_PLAN_TYPE_SPECIAL}">

            {label: '编号', name: 'cetCourse.sn', frozen: true},
            {label: '网上专题培训班名称', name: 'cetCourse.name', width: 300, align: 'left', frozen: true},
            {label: '上级单位名称', name: 'cetCourse.address', width: 300, align: 'left'},
            {label: '总学时', name: 'cetCourse.totalPeriod', width: 70},
            /*{label: '专题班', name: '_items', width: 80, formatter: function (cellvalue, options, rowObject) {
                return ('<button type="button" data-url="${ctx}/cet/cetCourseItem?courseId={0}" ' +
                'class="popupBtn btn btn-xs btn-success"><i class="ace-icon fa fa-search"></i> 详情</button>')
                        .format(rowObject.cetCourse.id)
            }},*/
            { label: '学习时间',name: '_time', width: 280, formatter: function (cellvalue, options, rowObject) {
                return '{0} ~ {1}'.format($.date(rowObject.startTime, "yyyy-MM-dd hh:mm"), $.date(rowObject.endTime, "yyyy-MM-dd hh:mm"))
            }},
            {label: '附件', name: '_file', formatter: function (cellvalue, options, rowObject) {
                var str = "";
                if(rowObject.filePath!=undefined){
                    str += ("<button class='linkBtn btn btn-xs btn-success' " +
                    "data-url='${ctx}/attach/download?path={0}&filename={1}'>"+
                            "<i class='fa fa-download'></i> 下载</button> &nbsp;").format(rowObject.filePath, rowObject.fileName)
                }
                return '-';
            }},
            {label: '备注', name: 'remark', width: 400}
            </c:if>
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
            $("#uploadNoteBtn").prop("disabled", true);
        } else if (ids.length == 1) {
            var rowData = $(grid).getRowData(ids[0]);
            var needNote = (rowData.needNote=="true");
            $("#uploadNoteBtn").prop("disabled",!needNote);
        }
    }
</script>