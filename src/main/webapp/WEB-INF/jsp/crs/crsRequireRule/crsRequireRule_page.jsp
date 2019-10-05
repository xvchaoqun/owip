<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

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
                    <a href="javascript:;">${crsPostRequire.name}-岗位招聘规则</a>
                </li>
            </ul>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main padding-4">
            <div class="tab-content padding-8">
                <shiro:hasPermission name="crsRequireRule:edit">
                    <a class="popupBtn btn btn-info btn-sm"
                       data-url="${ctx}/crsRequireRule_au?postRequireId=${crsPostRequire.id}"><i class="fa fa-plus"></i> 添加规则</a>
                    <a class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/crsRequireRule_au"
                       data-grid-id="#jqGrid2"><i class="fa fa-edit"></i>
                        修改规则</a>
                </shiro:hasPermission>
                <shiro:hasPermission name="crsRequireRule:del">
                    <button data-url="${ctx}/crsRequireRule_batchDel"
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
<style>
    .table > tbody > tr.ui-subgrid.active > td,
    .table tbody tr.ui-subgrid:hover td, .table tbody tr.ui-subgrid:hover th {
        background-color: inherit !important;
    }

    .ui-subgrid tr.success td {
        background-color: inherit !important;
    }
</style>
<script type="text/template" id="switch_tpl">
    <button class="switchBtn btn btn-info btn-xs" onclick="_swtich({{=id}}, this)"
            data-id="{{=id}}"><i class="fa fa-folder-o"></i>
        <span>详情</span>({{=count}})
    </button>
</script>
<script type="text/template" id="subgrid_op_tpl">
    <button class="popupBtn btn btn-xs btn-primary"
            data-url="${ctx}/crsRuleItem_au?id={{=id}}&&fid={{=parentRowKey}}"><i
            class="fa fa-edit"></i> 编辑
    </button>
    <button class="confirm btn btn-xs btn-danger"
            data-parent="{{=parentRowKey}}"
            data-url="${ctx}/crsRuleItem_batchDel?ids[]={{=id}}"
            data-msg="确定删除该具体条件？"
            data-callback="_delCallback"><i class="fa fa-times"></i> 删除
    </button>
</script>
<script>
    $.register.date($('.date-picker'));
    $("#jqGrid2").jqGrid({
        //forceFit:true,
        pager: "jqGridPager2",
        url: '${ctx}/crsRequireRule_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {
                label: '详情', name: '&nbsp;', formatter: function (cellvalue, options, rowObject) {
                if (rowObject.itemNum == 0) return '--';
                return _.template($("#switch_tpl").html().NoMultiSpace())({
                    id: rowObject.id,
                    count: rowObject.itemNum
                });
            }, width: 90},
            {
                label: '排序', width: 80, formatter: $.jgrid.formatter.sortOrder,
                formatoptions:{grid:'#jqGrid2', url: "${ctx}/crsRequireRule_changeOrder"}, frozen: true
            },
            { label: '类别名称',name: 'name', width: 250},
            {label: '具体条件', name: '_items', width: 90, formatter: function (cellvalue, options, rowObject) {
                if(rowObject.normNum==0||rowObject.rankNum==0) return '--'
                return '<button class="popupBtn btn btn-success btn-xs" data-width="700" data-url="${ctx}/crsRuleItem_au?requireRuleId={0}"><i class="fa fa-plus"></i> 添加</button>'
                        .format(rowObject.id);
            }},
            { label: '备注',name: 'remark', width: 480}
        ],
        rowattr: function (rowData, currentObj, rowId) {
            //console.log(currentObj)
            if (currentObj.itemNum == 0) {
                //console.log(rowId)
                //console.log($("#"+rowId).text())
                return {'class': 'noSubWork'}
            }
        },
        subGrid: true,
        subGridRowExpanded: subGridRowExpanded,
        subGridRowColapsed: subGridRowColapsed,
        subGridOptions: {
            // configure the icons from theme rolloer
            plusicon: "fa fa-folder-o",
            minusicon: "fa fa-folder-open-o"
            // selectOnExpand:true
            //openicon: "ui-icon-arrowreturn-1-e"
        }
    }).jqGrid("setFrozenColumns").on("initGrid",function(){
        $(window).triggerHandler('resize.jqGrid2');
        $('.noSubWork [aria-describedby="jqGrid2_subgrid"]').removeClass();

        currentExpandRows.forEach(function(item, i){
            $("#jqGrid2").expandSubGridRow(item)
        })
    });
    $.initNavGrid("jqGrid2", "jqGridPager2");

    function _swtich(id, btn) {
        //console.log(id, "=" + $(btn).html())
        if (!$("i", btn).hasClass("fa-folder-open-o")) {
            $("#jqGrid2").expandSubGridRow(id)
        } else {
            $("#jqGrid2").collapseSubGridRow(id)
        }
        $.getEvent().stopPropagation();
    }

    var currentExpandRows = [];
    function subGridRowColapsed(parentRowID, parentRowKey) {
        $(".switchBtn i", '#jqGrid2 #' + parentRowKey).removeClass("fa-folder-open-o");
        $(".switchBtn span", '#jqGrid2 #' + parentRowKey).html("详情");
        currentExpandRows.remove(parentRowKey);
        //console.log(currentExpandRows)
    }
    // the event handler on expanding parent row receives two parameters
    // the ID of the grid tow  and the primary key of the row
    function subGridRowExpanded(parentRowID, parentRowKey) {
        //console.log("parentRowKey=" + parentRowKey)
        //console.log($('#jqGrid2 #' + parentRowKey).html())
        $(".switchBtn i", '#jqGrid2 #' + parentRowKey).addClass("fa-folder-open-o");
        $(".switchBtn span", '#jqGrid2 #' + parentRowKey).html("隐藏");
        currentExpandRows.remove(parentRowKey);
        currentExpandRows.push(parentRowKey);
        //console.log(currentExpandRows)
        var childGridID = parentRowID + "_table";
        var childGridPagerID = parentRowID + "_pager";

        var childGridURL = '${ctx}/crsRuleItem_data?requireRuleId={0}&${cm:encodeQueryString(pageContext.request.queryString)}'.format(parentRowKey);

        $('#' + parentRowID).append('<table id=' + childGridID + '></table><div id=' + childGridPagerID + ' class=scroll></div>');
        $("#" + childGridID).jqGrid({
            ondblClickRow: function () {
            },
            multiselect: false,
            url: childGridURL,
            colModel: [
                { label: '类别',name: 'type', width: 180, formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue == undefined) return '--';
                    return _cMap.CRS_POST_RULE_TYPE_MAP[cellvalue];
                }},
                { label: '规格',name: 'val', width: 350, align:'left'},
                {
                    label: '排序', width: 80, formatter: $.jgrid.formatter.sortOrder,
                    formatoptions:{grid:'#jqGrid2', url: "${ctx}/crsRuleItem_changeOrder"}, frozen: true
                },
                { label: '备注',name: 'remark'},
                {
                    label: '操作', name: 'op', formatter: function (cellvalue, options, rowObject) {
                    //alert(rowObject.id)
                    return _.template($("#subgrid_op_tpl").html().NoMultiSpace())
                    ({id: rowObject.id, parentRowKey: parentRowKey})
                }, width: 150
                }
            ],
            pager: null
        });
    }

    function _delCallback(target) {
        //_reloadSubGrid($(target).data("parent"))
        $("#jqGrid2").trigger("reloadGrid");
    }

</script>