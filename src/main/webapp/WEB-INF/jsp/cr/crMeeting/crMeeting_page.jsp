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
                    data-url="${ctx}/crMeeting_au">
                <i class="fa fa-plus"></i> 添加
            </button>
            <button class="jqOpenViewBtn btn btn-primary btn-sm"
                    data-url="${ctx}/crMeeting_au"
                    data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                修改
            </button>
        </shiro:hasPermission>
        <shiro:hasPermission name="crMeeting:del">
            <button data-url="${ctx}/crMeeting_batchDel"
                    data-title="删除"
                    data-msg="确定删除这{0}条数据？"
                    data-grid-id="#jqGrid"
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
    $("#jqGrid2").jqGrid({
        pager: "jqGridPager2",
        rownumbers: true,
        url: '${ctx}/crMeeting_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '招聘会日期', name: 'meetingDate'},
            {label: '招聘岗位', name: 'postIds'},
            {label: '招聘会人数要求', name: 'applyCount'},
            {label: '备注', name: 'remark'}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$.register.date($('.date-picker'));
</script>