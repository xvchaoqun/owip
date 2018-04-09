<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<!-- PAGE CONTENT BEGINS -->
<div class="widget-box transparent" id="useLogs">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:;" class="hideView btn btn-xs btn-success">
                <i class="ace-icon fa fa-backward"></i>
                返回</a>
        </h4>

        <div class="widget-toolbar no-border">
            <ul class="nav nav-tabs">
                <li class="active">
                    <a href="javascript:;">培训课程</a>
                </li>
            </ul>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main padding-4">
            <div class="tab-content padding-8">
                <shiro:hasPermission name="cetTrainCourse:edit">
                    <a class="popupBtn btn btn-info btn-sm"
                       data-url="${ctx}/cet/cetTrainCourse_au?trainId=${cetTrain.id}"><i class="fa fa-plus"></i> 添加</a>
                    <a class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/cet/cetTrainCourse_au"
                       data-grid-id="#jqGrid2"
                       ><i class="fa fa-edit"></i>
                        修改</a>
                    <a class="jqOpenViewBatchBtn btn btn-warning btn-sm"
                       data-url="${ctx}/cet/cetTrainCourse_evaTable"
                       data-grid-id="#jqGrid2"
                       data-querystr="trainId=${cetTrain.id}&grid=jqGrid2"><i class="fa fa-table"></i>
                        设置评估表</a>

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
                <shiro:hasPermission name="cetTrainCourse:edit">
                    <button class="popupBtn btn btn-primary btn-sm tooltip-success"
                       data-url="${ctx}/cet/cetTrainCourse_import?trainId=${cetTrain.id}"
                       data-rel="tooltip" data-placement="top" title="从Excel导入"><i class="fa fa-upload"></i> 从Excel导入</button>
                    <button class="popupBtn btn btn-info btn-sm tooltip-success"
                       data-url="${ctx}/cet/cetTrainCourse_selectCourses?trainId=${cetTrain.id}" data-width="1200"
                       data-rel="tooltip" data-placement="top" title="从课程中心导入"><i class="fa fa-search-plus"></i> 从课程中心导入</button>
                </shiro:hasPermission>
                <a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-url="${ctx}/cet/cetTrainCourse_data"
                   data-querystr="trainId=${cetTrain.id}"
                   data-grid-id="#jqGrid2"
                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出课程</a>
                <div class="space-4"></div>
                <table id="jqGrid2" class="jqGrid2 table-striped"></table>
                <div id="jqGridPager2"></div>
            </div>
        </div>
        <!-- /.widget-main -->
    </div>
    <!-- /.widget-body -->
</div>
<!-- /.widget-box -->
<script>
    $.register.date($('.date-picker'));
    $("#jqGrid2").jqGrid({
        //forceFit:true,
        pager: "jqGridPager2",
        url: '${ctx}/cet/cetTrainCourse_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '课程名称', name: 'name', width: 300, align:'left', frozen: true,formatter:function(cellvalue, options, rowObject){
                var str = '<i class="fa fa-star red"></i>&nbsp;';
                return (rowObject.isGlobal)?str+cellvalue:cellvalue;
            }},
            {label: '教师名称', name: 'teacher', width: 120, frozen: true, formatter: function (cellvalue, options, rowObject) {
                if(rowObject.isGlobal || cellvalue==undefined) return '-'
                return cellvalue;
            }},
            {label: '课程来源', name: 'courseId', width: 90, frozen: true,formatter:function(cellvalue, options, rowObject){
                return (cellvalue==undefined)?'添加':'课程中心';
            }},
            {label: '开始时间', name: 'startTime', width: 130, formatter: 'date', formatoptions: {srcformat:'Y-m-d H:i',newformat:'Y-m-d H:i'}, frozen: true},
            {label: '结束时间', name: 'endTime', width: 130, frozen: true, formatter: function (cellvalue, options, rowObject) {
                if(rowObject.isGlobal) return '-'
                if(cellvalue==undefined) return ''
                return cellvalue.substr(0,16);
            }},
            {
                label: '排序', width: 80, formatter: $.jgrid.formatter.sortOrder,
                formatoptions:{url: "${ctx}/cet/cetTrainCourse_changeOrder"}, frozen: true
            },
            {label: '评估表', name: 'trainEvaTable', width: 200, formatter: function (cellvalue, options, rowObject) {
                if(cellvalue==undefined) return '-'
                return '<a href="javascript:void(0)" class="popupBtn" data-width="700" data-url="${ctx}/cet/cetTrainEvaTable_preview?id={0}">{1}</a>'
                        .format(cellvalue.id, cellvalue.name);
            }},

            {label: '测评情况（已测评/总数）', name: '_eva', width: 200, formatter: function (cellvalue, options, rowObject) {
                if('${cetTrain.evaCount}'=='') return '-'
                return '{0}/${cetTrain.evaCount}'.format(rowObject.evaFinishCount==undefined?0:rowObject.evaFinishCount);
            }}

        ]
    }).jqGrid("setFrozenColumns")
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");

</script>