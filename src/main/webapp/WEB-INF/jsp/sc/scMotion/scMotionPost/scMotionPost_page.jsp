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
            ${motion.code}
        </span>

        <div class="widget-toolbar no-border">
            <ul class="nav nav-tabs">
                <li class="active">
                    <a href="javascript:;">动议详情</a>
                </li>
            </ul>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main padding-4">
            <div class="tab-content padding-8">
                <div class="jqgrid-vertical-offset buttons">
                    <shiro:hasPermission name="scMotionPost:edit">
                        <button class="popupBtn btn btn-success btn-sm"
                                data-url="${ctx}/sc/scMotionPost_au?motionId=${motion.id}">
                            <i class="fa fa-plus"></i> 拟调整岗位
                        </button>
                        <button class="jqOpenViewBtn btn btn-primary btn-sm"
                                data-url="${ctx}/sc/scMotionPost_au"
                                data-grid-id="#jqGrid2"><i class="fa fa-edit"></i>
                            修改
                        </button>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="scMotionPost:del">
                        <button data-url="${ctx}/sc/scMotionPost_batchDel"
                                data-title="删除"
                                data-msg="确定删除这{0}条数据？"
                                data-grid-id="#jqGrid2"
                                data-callback="_reload2"
                                class="jqBatchBtn btn btn-danger btn-sm">
                            <i class="fa fa-trash"></i> 删除
                        </button>
                    </shiro:hasPermission>
                </div>
                <div class="space-4"></div>
                <table id="jqGrid2" class="jqGrid2 table-striped"></table>
                <div id="jqGridPager2"></div>
            </div>
        </div>
    </div>
</div>
<script>
    function _reload2(){
        $("#modal").modal('hide');
        $("#jqGrid2").trigger("reloadGrid");
    }
    $("#jqGrid2").jqGrid({
        pager: "jqGridPager2",
        rownumbers: true,
        url: '${ctx}/sc/scMotionPost_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '拟调整岗位编号', name: 'unitPost.code', width: 120},
            {label: '拟调整岗位名称', name: 'unitPost.name', align: 'left', width: 150},
            {label: '分管工作', name: 'unitPost.job', align: 'left', width: 450},
            {label: '正/副职', name: 'unitPost.isPrincipalPost', width: 80, formatter: $.jgrid.formatter.TRUEFALSE,
                formatoptions:{on:'正职', off:'副职'}},
            {label: '行政级别', name: 'unitPost.adminLevel', formatter: $.jgrid.formatter.MetaType},
            {label: '职务属性', name: 'unitPost.postType', width: 150, formatter: $.jgrid.formatter.MetaType},
            {label: '职务类别', name: 'unitPost.postClass', formatter: $.jgrid.formatter.MetaType},
            {label: '是否占干部职数', name: 'unitPost.isCpc', width: 120, formatter: $.jgrid.formatter.TRUEFALSE},
            {label: '备注', name: 'remark', align:'left', width: 250}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$.register.date($('.date-picker'));
</script>