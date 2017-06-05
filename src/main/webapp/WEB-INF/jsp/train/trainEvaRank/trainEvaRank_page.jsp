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
                    <a href="javascript:;">${trainEvaTable.name}-评估等级</a>
                </li>
            </ul>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main padding-4">
            <div class="tab-content padding-8">
                <shiro:hasPermission name="trainEvaRank:edit">
                    <a class="popupBtn btn btn-info btn-sm"  data-url="${ctx}/trainEvaRank_au?evaTableId=${trainEvaTable.id}"><i class="fa fa-plus"></i> 添加</a>
                    <a class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/trainEvaRank_au"
                       data-grid-id="#jqGrid2"
                       data-querystr="&"><i class="fa fa-edit"></i>
                        修改</a>
                </shiro:hasPermission>
                <shiro:hasPermission name="trainEvaRank:del">
                    <button data-url="${ctx}/trainEvaRank_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid2"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
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
<script type="text/template" id="sort_tpl">
<a href="javascript:;" class="jqOrderBtn" data-grid-id="#jqGrid2" data-url="{{=url}}" data-id="{{=id}}" data-direction="1" title="上升"><i class="fa fa-arrow-up"></i></a>
<input type="text" value="1" class="order-step tooltip-success" data-rel="tooltip" data-placement="top"
           title="修改操作步长">
<a href="javascript:;" class="jqOrderBtn" data-grid-id="#jqGrid2" data-url="{{=url}}" data-id="{{=id}}" data-direction="-1" title="下降"><i class="fa fa-arrow-down"></i></a>
</script>
<script>
    register_date($('.date-picker'));
    $("#jqGrid2").jqGrid({
        //forceFit:true,
        pager: "jqGridPager2",
        url: '${ctx}/trainEvaRank_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '名称',name: 'name'},
            { label: '得分',name: 'score'},
            { label: '得分显示内容',name: 'scoreShow', width: 120},
            {
                label: '排序', width: 80, index: 'sort', formatter: function (cellvalue, options, rowObject) {
                return _.template($("#sort_tpl").html().NoMultiSpace())({id: rowObject.id, url:"${ctx}/trainEvaRank_changeOrder"})
            }, frozen: true
            },
            { label: '备注',name: 'remark'}
        ]
    }).jqGrid("setFrozenColumns").on("initGrid",function(){
        $(window).triggerHandler('resize.jqGrid2');
    });
    $.initNavGrid("jqGrid2", "jqGridPager2");

</script>