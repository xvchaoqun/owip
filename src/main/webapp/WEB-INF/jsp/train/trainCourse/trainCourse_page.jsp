<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<!-- PAGE CONTENT BEGINS -->
<div class="widget-box transparent" id="useLogs">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:;" class="closeView btn btn-xs btn-success">
                <i class="ace-icon fa fa-backward"></i>
                返回</a>
        </h4>

        <div class="widget-toolbar no-border">
            <ul class="nav nav-tabs">
                <li class="active">
                    <a href="javascript:;">${train.name}-培训课程</a>
                </li>
            </ul>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main padding-4">
            <div class="tab-content padding-8">
                <shiro:hasPermission name="trainCourse:edit">
                    <a class="popupBtn btn btn-info btn-sm"  data-url="${ctx}/trainCourse_au?trainId=${train.id}"><i class="fa fa-plus"></i> 添加</a>
                    <a class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/trainCourse_au"
                       data-grid-id="#jqGrid2"
                       data-querystr="&"><i class="fa fa-edit"></i>
                        修改</a>
                    <a class="jqOpenViewBtn btn btn-warning btn-sm"
                       data-url="${ctx}/trainCourse_evaTable"
                       data-grid-id="#jqGrid2"
                       data-querystr="&"><i class="fa fa-table"></i>
                        设置评估表</a>

                </shiro:hasPermission>
                <shiro:hasPermission name="trainCourse:del">
                    <button data-url="${ctx}/trainCourse_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid2"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
                <a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-url="${ctx}/trainCourse_data"
                   data-querystr="trainId=${train.id}"
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
    register_date($('.date-picker'));
    $("#jqGrid2").jqGrid({
        //forceFit:true,
        pager: "jqGridPager2",
        url: '${ctx}/trainCourse_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '名称', name: 'name', width: 200, frozen: true},
            {label: '教师名称', name: 'teacher', width: 120, frozen: true},
            {label: '开始时间', name: 'startTime', width: 130, formatter: 'date', formatoptions: {srcformat:'Y-m-d H:i',newformat:'Y-m-d H:i'}, frozen: true},
            {label: '结束时间', name: 'endTime', width: 130, formatter: 'date', formatoptions: {srcformat:'Y-m-d H:i',newformat:'Y-m-d H:i'}, frozen: true},

            {label: '评估表', name: 'evaTableId', width: 130, formatter: function (cellvalue, options, rowObject) {
                if(cellvalue==undefined) return '-'

                return '<a href="javascript:void(0)" class="popupBtn" data-width="700" data-url="${ctx}/trainEvaTable_preview?id={0}">{1}</a>'
                        .format(cellvalue, _cMap.trainEvaTableMap[cellvalue].name);
            }},

            {label: '评课情况（已测评/总数）', name: '_eva', width: 200, formatter: function (cellvalue, options, rowObject) {
                if('${train.totalCount}'=='') return '-'
                return '{0}/${train.totalCount}'.format(rowObject.finishCount);
            }}

        ]
    }).jqGrid("setFrozenColumns").on("initGrid",function(){
        $(window).triggerHandler('resize.jqGrid2');
    });
    _initNavGrid("jqGrid2", "jqGridPager2");

</script>