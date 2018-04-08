<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="jqgrid-vertical-offset buttons">
    <c:if test="${cls==1}">
    <shiro:hasPermission name="cetTrainCourse:edit">
        <a class="popupBtn btn btn-info btn-sm"
           data-width="1200"
           data-url="${ctx}/cet/cetTrainCourse_selectCourses?trainId=${cetTrain.id}&grid=jqGrid2"><i
                class="fa fa-plus"></i> 添加课程</a>
        <a class="jqOpenViewBtn btn btn-primary btn-sm"
           data-url="${ctx}/cet/cetTrainCourse_info"
           data-grid-id="#jqGrid2"
           data-id-name="trainCourseId"><i class="fa fa-edit"></i>
            编辑课程信息</a>
    </shiro:hasPermission>
    <shiro:hasPermission name="cetTrainCourse:del">
        <button data-url="${ctx}/cet/cetTrainCourse_batchDel"
                data-title="删除"
                data-msg="确定删除这{0}门课程？（课程下的所有选课数据均将彻底删除，请谨慎操作！）"
                data-grid-id="#jqGrid2"
                class="jqBatchBtn btn btn-danger btn-sm">
            <i class="fa fa-trash"></i> 删除
        </button>
    </shiro:hasPermission>
    <a class="jqExportBtn btn btn-success btn-sm tooltip-success"
       data-url="${ctx}/cet/cetTrainCourse_data"
       data-querystr="trainId=${cetTrain.id}"
       data-grid-id="#jqGrid2"
       data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
        <i class="fa fa-download"></i> 导出课程</a>

        <%--<a class="jqOpenViewBtn btn btn-warning btn-sm"
           data-url="${ctx}/cet/cetTrainCourse_selectObjs"
           data-grid-id="#jqGrid2"
           data-id-name="trainCourseId"><i class="fa fa-users"></i>
            设置参训人员</a>--%>
    </c:if>
    <c:if test="${cls==2}">
        <a class="jqOpenViewBatchBtn btn btn-warning btn-sm"
           data-url="${ctx}/cet/cetTrainCourse_evaTable"
           data-grid-id="#jqGrid2"
           data-querystr="trainId=${cetTrain.id}&grid=jqGrid2"><i class="fa fa-table"></i>
            设置评估表</a>
    </c:if>
</div>
<div class="space-4"></div>
<table id="jqGrid2" class="jqGrid2 table-striped"></table>
<div id="jqGridPager2"></div>

<script>
    var objCount = ${cetTrain.objCount};
    var projectId = ${cetTrain.projectId};
    $.register.date($('.date-picker'));
    $("#jqGrid2").jqGrid({
        //forceFit:true,
        rownumbers:true,
        pager: "jqGridPager2",
        url: '${ctx}/cet/cetTrainCourse_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            <c:if test="${cls==1}">
            {
                label: '选课情况', name: 'selectedCount', formatter: function (cellvalue, options, rowObject) {
                if(cellvalue==undefined) return '-';
                return ('<button class="openView btn btn-primary btn-xs" ' +
                'data-url="${ctx}/cet/cetProject_detail/obj?projectId={0}&trainCourseId={1}">已选课({2}/{3})</button>')
                        .format(projectId, rowObject.id, cellvalue, objCount);
            }, frozen:true},
            {label: '签到情况', name: '_sign', frozen:true, formatter: function (cellvalue, options, rowObject) {
                if(rowObject.finishCount==undefined) return '-'
                return ('<button class="popupBtn btn btn-success btn-xs" data-width="1000" ' +
                'data-url="${ctx}/cet/cetTrainCourse_trainee?trainCourseId={0}">已签到({1}/{2})</button>')
                        .format(rowObject.id, rowObject.finishCount, rowObject.selectedCount);
            }},
            {label: '课程编号', name: 'cetCourse.sn', frozen:true},
            </c:if>
            {
                label: '课程名称',
                name: 'cetCourse.name',
                width: 300,
                align: 'left', frozen:true
            },
            <c:if test="${cls==1}">
            {label: '课程要点', name: '_summary', width: 80, formatter: function (cellvalue, options, rowObject) {

                if (rowObject.cetCourse.hasSummary==false) return '-'

                return ('<button class="popupBtn btn btn-primary btn-xs" data-width="750" ' +
                'data-url="${ctx}/cet/cetCourse_summary?id={0}&view=1"><i class="fa fa-search"></i> 查看</button>')
                        .format(rowObject.cetCourse.id);
            }, frozen:true},
            <c:if test="${cls==1}">
            {
                label: '排序', width: 80, align: 'center', index: 'sort', formatter: $.jgrid.formatter.sortOrder,
                formatoptions: {url: "${ctx}/cet/cetTrainCourse_changeOrder", grid:'#jqGrid2'}, frozen:true
            },
            </c:if>
            </c:if>

            {label: '主讲人', name: 'cetCourse.cetExpert.realname', frozen:true},
            <c:if test="${cls==1}">
            {label: '所在单位', name: 'cetCourse.cetExpert.unit', width: 300, align: 'left'},
            {label: '职务和职称', name: 'cetCourse.cetExpert.post', width: 120, align: 'left'},
            {label: '授课方式', name: 'cetCourse.teachMethod', formatter: $.jgrid.formatter.MetaType},
            {label: '学时', name: 'cetCourse.period', width: 70},
            {
                label: '专题分类', name: 'cetCourse.courseTypeId', formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return ''
                var courseTypeMap = ${cm:toJSONObject(courseTypeMap)};
                return courseTypeMap[cellvalue].name
            }
            },
            </c:if>
            {
                label: '开始时间',
                name: 'startTime',
                width: 130,
                formatter: 'date',
                formatoptions: {srcformat: 'Y-m-d H:i', newformat: 'Y-m-d H:i'}
            },
            {
                label: '结束时间',
                name: 'endTime',
                width: 130,
                formatter: 'date',
                formatoptions: {srcformat: 'Y-m-d H:i', newformat: 'Y-m-d H:i'},
            },

            <c:if test="${cls==1}">
            {label: '上课地点', name: 'address', align: 'left', width: 250, formatter: function (cellvalue, options, rowObject) {
                return (rowObject.cetCourse.isOnline==false)? $.trim(cellvalue):'-'
            }},
            </c:if>
            <c:if test="${cls==2}">
            {label: '评估表', name: 'trainEvaTable', width: 200, formatter: function (cellvalue, options, rowObject) {
                if(cellvalue==undefined) return '-'
                return '<a href="javascript:void(0)" class="popupBtn" data-width="700" data-url="${ctx}/cet/cetTrainEvaTable_preview?id={0}">{1}</a>'
                        .format(cellvalue.id, cellvalue.name);
            }},

            {label: '测评情况（已测评/总数）', name: '_eva', width: 200, formatter: function (cellvalue, options, rowObject) {
                if('${cetTrain.evaCount}'=='') return '-'
                return '{0}/${cetTrain.evaCount}'.format(rowObject.evaFinishCount==undefined?0:rowObject.evaFinishCount);
            }}
            </c:if>
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");

</script>