<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/cet/constants.jsp" %>
<div class="jqgrid-vertical-offset buttons">
    <c:if test="${cls==1}">

        <c:if test="${!cetProject.isPartyProject}">
        <button class="popupBtn btn btn-info btn-sm"
           data-width="1200"
           data-url="${ctx}/cet/cetTrainCourse_selectCourses?trainId=${cetTrain.id}"><i
                class="fa fa-plus"></i> 选择课程</button>
        </c:if>

        <button class="popupBtn btn btn-info btn-sm"
           data-url="${ctx}/cet/cetTrainCourse_au?projectId=${cetProject.id}&trainId=${cetTrain.id}">
            <i class="fa fa-plus"></i> 添加课程</button>

        <button class="jqOpenViewBtn btn btn-primary btn-sm"
           data-url="${ctx}/cet/cetTrainCourse_au"
           data-grid-id="#jqGrid2"
           data-id-name="trainCourseId"><i class="fa fa-edit"></i> 修改课程信息</button>

        <button class="jqOpenViewBtn btn btn-success btn-sm"
           data-url="${ctx}/cet/cetTrainCourse_applyStatus"
           data-grid-id="#jqGrid2"
           data-id-name="trainCourseId"><i class="fa fa-hourglass-1"></i> 选课/退课状态</button>

        <c:if test="${!cetProject.isPartyProject}">
        <button class="jqOpenViewBatchBtn btn btn-warning btn-sm"
           data-url="${ctx}/cet/cetTrainCourse_applyMsg?projectId=${cetProject.id}"
           data-grid-id="#jqGrid2"
           data-ids-name="trainCourseIds[]"><i class="fa fa-send"></i>
            补选课通知</button>
        <button data-url="${ctx}/cet/cetTrain_detail/msg_list?tplKey=cet_tc_apply_msg"
                data-width="800"
                data-grid-id="#jqGrid2"
                data-id-name="recordId"
                class="jqOpenViewBtn btn btn-info btn-sm">
            <i class="ace-icon fa fa-history"></i>
            补选课通知记录
        </button>
        </c:if>
        <button class="jqExportItemBtn btn btn-success btn-sm"
                data-url="${ctx}/cet/cetTrainCourse_exportChosenObjs"
                data-grid-id="#jqGrid2"
                data-id-name="trainCourseId"><i class="fa fa-download"></i>
            导出已选课学员</button>
        <button data-url="${ctx}/cet/cetTrainCourse_batchDel?projectId=${cetProject.id}&trainId=${cetTrain.id}"
                data-title="删除"
                data-msg="确定删除这{0}门课程？（课程下的所有选课数据均将彻底删除，请谨慎操作！）"
                data-grid-id="#jqGrid2"
                class="jqBatchBtn btn btn-danger btn-sm">
            <i class="fa fa-trash"></i> 删除
        </button>
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
<table id="jqGrid2" class="jqGrid2 table-striped" data-height-reduce="${not empty param.trainId?'-30':'10'}"></table>
<div id="jqGridPager2"></div>

<script>
    var objCount = ${cetProject.objCount};
    var projectId = ${cetProject.id};
    $.register.date($('.date-picker'));
    $("#jqGrid2").jqGrid({
        //forceFit:true,
        rownumbers:true,
        pager: "jqGridPager2",
        url: '${ctx}/cet/cetTrainCourse_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            <c:if test="${empty cetProjectPlan || cetProjectPlan.type==CET_PROJECT_PLAN_TYPE_OFFLINE
            || cetProjectPlan.type==CET_PROJECT_PLAN_TYPE_ONLINE}">
            <c:if test="${cls==1}">
            {
                label: '选课情况', name: 'selectedCount', formatter: function (cellvalue, options, rowObject) {
                if(cellvalue==undefined) cellvalue=0;
                return ('<button class="openView btn btn-primary btn-xs" ' +
                'data-url="${ctx}/cet/cetProjectObj_list_page?cls=2&projectId={0}&trainCourseId={1}">已选课({2}/{3})</button>')
                        .format(projectId, rowObject.id, cellvalue, objCount);
            }, width: 130, frozen:true},
            <c:if test="${empty cetProjectPlan || cetProjectPlan.type==CET_PROJECT_PLAN_TYPE_OFFLINE}">
            {label: '签到情况', name: '_sign', width: 130, frozen:true, formatter: function (cellvalue, options, rowObject) {
                var finishCount = (rowObject.finishCount==undefined)?0:rowObject.finishCount;
                var selectedCount = (rowObject.selectedCount==undefined)?0:rowObject.selectedCount;
                return ('<button class="popupBtn btn btn-success btn-xs" data-width="1000" ' +
                'data-url="${ctx}/cet/cetTrainCourse_trainee?trainCourseId={0}&projectId={3}">已签到({1}/{2})</button>')
                        .format(rowObject.id, finishCount, selectedCount, projectId);
            }},
            </c:if>
            {
                label: '选课时间', name: '_time', formatter: function (cellvalue, options, rowObject) {

                return '${cm:formatDate(startTime, "yyyy-MM-dd HH:mm")} ~ ${cm:formatDate(endTime, "yyyy-MM-dd HH:mm")}'
            }, width: 130, frozen:true},
            {
                label: '选课/退课状态', name: 'applyStatus', formatter: function (cellvalue, options, rowObject) {
                //if(cellvalue==${CET_TRAIN_COURSE_APPLY_STATUS_DEFAULT}) return '--'
                return _cMap.CET_TRAIN_COURSE_APPLY_STATUS_MAP[cellvalue];
            }, width: 130, frozen:true},
            </c:if>

            {label: '课程名称', name: 'name', width: 300, align: 'left' },

            <c:if test="${cetProjectPlan.type==CET_PROJECT_PLAN_TYPE_ONLINE}">
            {label: '播放', name: 'duration', width: 60, formatter: function (cellvalue, options, rowObject){

                return ('<button class="linkBtn btn btn-xs btn-success" data-url="${ctx}/cet/cetCourse_video?id={0}&_={1}" '
                +' data-target="_blank"><i class="fa fa-play-circle"></i> 播放</button>')
                    .format(rowObject.courseId, new Date().getTime());
            }, frozen:true},
            </c:if>
            <c:if test="${cls==1}">
            {
                label: '排序', width: 80, index: 'sort', formatter: $.jgrid.formatter.sortOrder,
                formatoptions: {url: "${ctx}/cet/cetTrainCourse_changeOrder", grid:'#jqGrid2'}
            },
            </c:if>
            {label: '主讲人', name: 'teacher'},
             <c:if test="${empty cetProjectPlan}">
            { label: '培训形式', name: 'isOnline', width: 90, formatter:$.jgrid.formatter.TRUEFALSE, formatoptions:{on:'<span class="green bolder">线上培训</span>', off:'线下培训'}},
            </c:if>
            <c:if test="${cls==1}">
            {label: '学时', name: 'period', width: 70},
            </c:if>
            {label: '选课人数上限', name: 'applyLimit'},
            {label: '开始时间', name: 'startTime', width: 150, formatter: $.jgrid.formatter.date, formatoptions: {srcformat: 'Y-m-d H:i', newformat: 'Y-m-d H:i'}},
            {label: '结束时间', name: 'endTime', width: 150, formatter: $.jgrid.formatter.date, formatoptions: {srcformat: 'Y-m-d H:i', newformat: 'Y-m-d H:i'},},
           <c:if test="${cetProjectPlan.type!=CET_PROJECT_PLAN_TYPE_ONLINE}">
           <c:if test="${cls==1}">
            {label: '上课地点', name: 'address', align: 'left', width: 250, formatter: function (cellvalue, options, rowObject) {
                return rowObject.isOnline? '--':$.trim(cellvalue)
            }},
            </c:if>
            </c:if>

            <c:if test="${cls==2}">
            {label: '评估表', name: 'trainEvaTable', width: 200, formatter: function (cellvalue, options, rowObject) {
                if(cellvalue==undefined) return '--'
                return '<a href="javascript:void(0)" class="popupBtn" data-width="700" data-url="${ctx}/cet/cetTrainEvaTable_preview?id={0}">{1}</a>'
                        .format(cellvalue.id, cellvalue.name);
            }},

            {label: '测评情况（已测评/总数）', name: '_eva', width: 200, formatter: function (cellvalue, options, rowObject) {
                if('${cetTrain.evaCount}'=='') return '--'
                return '{0}/${cetTrain.evaCount}'.format(rowObject.evaFinishCount==undefined?0:rowObject.evaFinishCount);
            }}
            </c:if>
            </c:if>
            <c:if test="${cetProjectPlan.type==CET_PROJECT_PLAN_TYPE_PRACTICE}">
            {
                label: '选课情况', name: 'selectedCount', formatter: function (cellvalue, options, rowObject) {
                if(cellvalue==undefined) cellvalue=0;
                return ('<button class="openView btn btn-primary btn-xs" ' +
                'data-url="${ctx}/cet/cetProjectObj_list_page?cls=2&projectId={0}&trainCourseId={1}">已选课({2}/{3})</button>')
                        .format(projectId, rowObject.id, cellvalue, objCount);
            }, width: 130, frozen:true},
            {
                label: '选课/退课状态', name: 'applyStatus', formatter: function (cellvalue, options, rowObject) {
                //if(cellvalue==${CET_TRAIN_COURSE_APPLY_STATUS_DEFAULT}) return '--'
                return _cMap.CET_TRAIN_COURSE_APPLY_STATUS_MAP[cellvalue];
            }, width: 130, frozen:true},
            {label: '签到情况', name: '_sign', width: 130, frozen:true, formatter: function (cellvalue, options, rowObject) {
                var finishCount = (rowObject.finishCount==undefined)?0:rowObject.finishCount;
                var selectedCount = (rowObject.selectedCount==undefined)?0:rowObject.selectedCount;
                return ('<button class="popupBtn btn btn-success btn-xs" data-width="1000" ' +
                'data-url="${ctx}/cet/cetTrainCourse_trainee?trainCourseId={0}&projectId={3}">已签到({1}/{2})</button>')
                        .format(rowObject.id, finishCount, selectedCount, projectId);
            }},
            {
                label: '实践教学名称',
                name: 'name',
                width: 300,
                align: 'left', frozen:true
            },
            {
                label: '实践教学地点',
                name: 'address',
                width: 300,
                align: 'left', frozen:true
            },
            {
                label: '排序', width: 80, index: 'sort', formatter: $.jgrid.formatter.sortOrder,
                formatoptions: {url: "${ctx}/cet/cetTrainCourse_changeOrder", grid:'#jqGrid2'}, frozen:true
            },
            {label: '学时', name: 'period', width: 70},
            {label: '选课人数上限', name: 'applyLimit'},
            {
                label: '开始时间',
                name: 'startTime',
                width: 150,
                formatter: $.jgrid.formatter.date,
                formatoptions: {srcformat: 'Y-m-d H:i', newformat: 'Y-m-d H:i'}
            },
            {
                label: '结束时间',
                name: 'endTime',
                width: 150,
                formatter: $.jgrid.formatter.date,
                formatoptions: {srcformat: 'Y-m-d H:i', newformat: 'Y-m-d H:i'},
            },
            {label: '教学照片', name: '_images', width: 80, formatter: function (cellvalue, options, rowObject) {
                return ('<button type="button" data-url="${ctx}/cet/cetTrainCourseFile?trainCourseId={0}" ' +
                'class="popupBtn btn btn-xs btn-success"><i class="ace-icon fa fa-search"></i> 详情</button>')
                        .format(rowObject.id)
            }},
            </c:if>
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");

    $.register.fancybox();

</script>