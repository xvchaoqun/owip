<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="space-4"></div>
<div class="jqgrid-vertical-offset buttons">
    <c:if test="${cls==1}">
    <shiro:hasPermission name="cetTrainCourse:edit">
        <a class="popupBtn btn btn-info btn-sm"
           data-width="1200"
           data-url="${ctx}/cet/cetTrainCourse_selectCourses?trainId=${cetTrain.id}"><i
                class="fa fa-plus"></i> 添加课程</a>
        <a class="jqOpenViewBtn btn btn-primary btn-sm"
           data-url="${ctx}/cet/cetTrainCourse_info"
           data-grid-id="#jqGrid2"
           data-id-name="trainCourseId"
           data-querystr="&"><i class="fa fa-edit"></i>
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
    </c:if>
</div>
<div class="space-4"></div>
<table id="jqGrid2" class="jqGrid2 table-striped"></table>
<div id="jqGridPager2"></div>

<script>
    register_date($('.date-picker'));
    $("#jqGrid2").jqGrid({
        //forceFit:true,
        pager: "jqGridPager2",
        url: '${ctx}/cet/cetTrainCourse_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '签到情况', name: '_sign', frozen:true, formatter: function (cellvalue, options, rowObject) {
                if(rowObject.finishCount==undefined) return '-'
                return ('<button class="popupBtn btn btn-success btn-xs" data-width="1200" ' +
                'data-url="${ctx}/cet/cetTrainCourse_trainee?trainCourseId={0}"><i class="fa fa-search"></i> 已签到({1})</button>')
                        .format(rowObject.id, rowObject.finishCount);
            }},
            {label: '课程编号', name: 'cetCourse.sn', frozen:true},
            {
                label: '课程名称',
                name: 'cetCourse.name',
                width: 300,
                align: 'left', frozen:true
            },
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
            {label: '主讲人', name: 'cetCourse.cetExpert.realname', frozen:true},
            {
                label: '选课情况', name: 'selectedCount', width: 80,formatter: function (cellvalue, options, rowObject) {
                if(cellvalue==undefined) return '-';
                return '已选课({0})'.format(cellvalue);
            }
            },
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
            {label: '上课地点', name: 'address', align: 'left', width: 250, formatter: function (cellvalue, options, rowObject) {
                return (rowObject.cetCourse.isOnline==false)? $.trim(cellvalue):'-'
            }}

        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");

</script>