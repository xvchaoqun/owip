<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="jqgrid-vertical-offset buttons">
    <shiro:hasPermission name="cetTrainCourse:edit">
        <a class="popupBtn btn btn-info btn-sm" data-url="${ctx}/cet/cetTrainCourse_add?trainId=${cetTrain.id}"><i
                class="fa fa-plus"></i> 添加</a>
        <a class="jqOpenViewBtn btn btn-primary btn-sm"
           data-url="${ctx}/cet/cetTrainCourse_info"
           data-grid-id="#jqGrid2"
           data-id-name="trainCourseId"
           data-querystr="&"><i class="fa fa-edit"></i>
            编辑上课信息</a>
    </shiro:hasPermission>
    <shiro:hasPermission name="cetTrainCourse:del">
        <button data-url="${ctx}/cet/cetTrainCourse_batchDel"
                data-title="删除"
                data-msg="确定删除这{0}条数据？"
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
            {label: '课程编号', name: 'cetCourse.sn'},
            {
                label: '课程名称',
                name: 'cetCourse.name',
                width: 300,
                align: 'left',
                formatter: function (cellvalue, options, rowObject) {
                    var str = '<i class="fa fa-star red"></i>&nbsp;';
                    return (rowObject.isGlobal) ? str + cellvalue : cellvalue;
                }
            },
            {
                label: '排序', width: 80, align: 'center', index: 'sort', formatter: $.jgrid.formatter.sortOrder,
                formatoptions: {url: "${ctx}/cet/cetTrainCourse_changeOrder"}
            },
            {label: '主讲人', name: 'cetCourse.cetExpert.realname'},
            {label: '所在单位', name: 'cetCourse.cetExpert.unit', width: 300, align: 'left'},
            {label: '职务和职称', name: 'cetCourse.cetExpert.post', width: 120, align: 'left'},
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
            {label: '上课地点', name: 'address', width: 200},
            {
                label: '选课情况', name: 'selectCount', formatter: function (cellvalue, options, rowObject) {

                if(cellvalue==0) return '0';
                return ('<button class="popupBtn btn btn-primary btn-xs" ' +
                'data-url="${ctx}/cet/cetTrainCourse_selectList?trainCourseId={0}"><i class="fa fa-search"></i> 查看</button>')
                        .format(rowObject.id);
            }
            }

        ]
    }).jqGrid("setFrozenColumns").on("initGrid", function () {
        $(window).triggerHandler('resize.jqGrid2');
    });
    $.initNavGrid("jqGrid2", "jqGridPager2");

</script>