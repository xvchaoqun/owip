<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="space-4"></div>
<div class="jqgrid-vertical-offset buttons">
    <shiro:hasPermission name="crsApplicant:edit">
        <a class="popupBtn btn btn-info btn-sm"
           data-url="${ctx}/crsPostExpert_au?postId=${param.id}">
            <i class="fa fa-plus"></i> 添加</a>
        <a class="jqOpenViewBtn btn btn-primary btn-sm"
           data-url="${ctx}/crsPostExpert_au"
           data-grid-id="#jqGrid2"
           data-querystr="&postId=${param.id}"><i class="fa fa-edit"></i>
            修改</a>
    </shiro:hasPermission>
    <shiro:hasPermission name="crsApplicant:del">
        <button data-url="${ctx}/crsPostExpert_batchDel"
                data-title="删除"
                data-msg="确定删除这{0}条数据？"
                data-grid-id="#jqGrid2"
                class="jqBatchBtn btn btn-danger btn-sm">
            <i class="fa fa-trash"></i> 删除
        </button>
    </shiro:hasPermission>
</div>
<div class="space-4"></div>
<table id="jqGrid2" class="jqGrid2 table-striped" data-height-reduce="40"></table>
<div id="jqGridPager2"></div>

<script type="text/template" id="sort_tpl">
    <a href="javascript:;" class="jqOrderBtn" data-grid-id="#jqGrid2" data-url="{{=url}}" data-id="{{=id}}" data-direction="1" title="上升"><i class="fa fa-arrow-up"></i></a>
    <input type="text" value="1" class="order-step tooltip-success" data-rel="tooltip" data-placement="top"
           title="修改操作步长">
    <a href="javascript:;" class="jqOrderBtn" data-grid-id="#jqGrid2" data-url="{{=url}}" data-id="{{=id}}" data-direction="-1" title="下降"><i class="fa fa-arrow-down"></i></a>
</script>
<script>
    $("#jqGrid2").jqGrid({
        pager: "#jqGridPager2",
        url: '${ctx}/crsPostExpert_data?callback=?&postId=${param.id}&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '姓名', name: 'user.realname'},
            {label: '工作证号', name: 'user.code', width: 100, frozen: true},
            {label: '角色', name: 'role', width: 180, formatter: function (cellvalue, options, rowObject) {
                return _cMap.CRS_POST_EXPERT_ROLE_MAP[cellvalue]
            }},
            {
                label: '排序', width: 80, index: 'sort', formatter: function (cellvalue, options, rowObject) {
                return _.template($("#sort_tpl").html().NoMultiSpace())({id: rowObject.id,
                    url:"${ctx}/crsPostExpert_changeOrder"})
            }, frozen: true
            },
            { label: '备注',name: 'remark'}
        ]
    }).jqGrid("setFrozenColumns")
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>