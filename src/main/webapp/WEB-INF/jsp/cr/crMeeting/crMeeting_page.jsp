<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="widget-box transparent">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:;" class="hideView btn btn-xs btn-success">
                <i class="ace-icon fa fa-backward"></i>
                返回</a>

        </h4>
        <span class="text text-info bolder" style="cursor: auto;padding-left: 20px;">
                    ${cm:formatDate(crInfo.addDate, "yyyy.MM.dd")} 招聘信息
                </span>
    </div>
    <div class="space-4"></div>
    <c:set var="_query" value="${not empty param.meetingDate || not empty param.code || not empty param.sort}"/>
    <div class="jqgrid-vertical-offset buttons">
        <shiro:hasPermission name="crMeeting:edit">
            <button class="popupBtn btn btn-info btn-sm"
                    data-url="${ctx}/crMeeting_au?infoId=${param.infoId}">
                <i class="fa fa-plus"></i> 添加
            </button>
            <button class="jqOpenViewBtn btn btn-primary btn-sm"
                    data-url="${ctx}/crMeeting_au"
                    data-grid-id="#jqGrid2"><i class="fa fa-edit"></i>
                修改
            </button>
            <a class="jqOpenViewBtn btn btn-info btn-sm"
                                   data-url="${ctx}/crMeeting_msg"
                                   data-grid-id="#jqGrid2" data-width="800"
                                   data-id-name="meetingId"><i class="fa fa-send"></i>
                                    下发任务通知</a>

        </shiro:hasPermission>
        <shiro:hasPermission name="crMeeting:del">
            <button data-url="${ctx}/crMeeting_batchDel"
                    data-title="删除"
                    data-msg="确定删除这{0}条数据？"
                    data-grid-id="#jqGrid2"
                    data-callback="_delCallback"
                    class="jqBatchBtn btn btn-danger btn-sm">
                <i class="fa fa-trash"></i> 删除
            </button>
        </shiro:hasPermission>
    </div>

    <div class="space-4"></div>
    <table id="jqGrid2" class="jqGrid2 table-striped" data-height-reduce="30"></table>
    <div id="jqGridPager2"></div>
</div>
<script>
    function _delCallback(){
        $("#jqGrid2").trigger("reloadGrid");
        $("#jqGrid").trigger("reloadGrid");
    }
    var postMap = ${cm:toJSONObject(postMap)}
    $("#jqGrid2").jqGrid({
        pager: "jqGridPager2",
        rownumbers: true,
        url: '${ctx}/crMeeting_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '招聘会日期', name: 'meetingDate', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}, frozen: true},
            {label: '招聘岗位', name: 'postIds', width:600, align:'left', formatter: function (cellvalue, options, rowObject) {
                if(cellvalue==undefined) return '--'
                return $.map(cellvalue.split(","), function(postId){
                    if(postMap[postId]==undefined) return '--'
                    return postMap[postId].name
                })
            }},
            /*{label: '招聘会人数要求', name: 'requireNum'},*/
            {label: '备注', name: 'remark', width:200, align:'left'}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$.register.date($('.date-picker'));
</script>