<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" class="myTableDiv">
            <div class="tabbable">
                <jsp:include page="/WEB-INF/jsp/modify/modifyTableApply/menu.jsp"/>
                <div class="space-4"></div>
                <div class="jqgrid-vertical-offset buttons">
                        <a class="popupBtn btn btn-success btn-sm"
                           data-url="${ctx}/cadreWork_au?module=${param.module}&toApply=1&cadreId=${cadre.id}"><i class="fa fa-plus"></i>
                            添加工作经历</a>
                        <a class="jqOpenViewBtn btn btn-primary btn-sm"
                           data-url="${ctx}/cadreWork_au"
                           data-grid-id="#jqGrid_cadreWork"
                           data-querystr="module=${param.module}&toApply=1&cadreId=${cadre.id}"><i class="fa fa-edit"></i>
                            修改工作经历</a>
                    <a class="jqOpenViewBtn btn btn-success btn-sm"
                       data-grid-id="#jqGrid_cadreWork"
                       data-url="${ctx}/cadreWork_au"
                       data-id-name="fid"
                       data-querystr="module=${param.module}&toApply=1&cadreId=${cadre.id}"><i class="fa fa-plus"></i>
                        添加其间工作</a>
                        <button data-url="${ctx}/user/modifyTableApply_del"
                                data-title="删除"
                                data-msg="申请删除这条工作经历？"
                                data-grid-id="#jqGrid_cadreWork"
                                data-querystr="module=${module}"
                                data-callback="_delCallback"
                                class="jqItemBtn btn btn-danger btn-sm">
                            <i class="fa fa-trash"></i> 删除
                        </button>
                </div>
                <div class="space-4"></div>
                <table id="jqGrid_cadreWork" class="jqGrid"></table>
                <div id="jqGridPager_cadreWork"></div>
            </div>
        </div>
        <div id="body-content-view">
        </div>
    </div>
</div>
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
        <span>查看其间工作</span>({{=count}})
    </button>
</script>
<script type="text/template" id="subgrid_op_tpl">
    <button class="popupBtn btn btn-xs btn-primary"
            data-url="${ctx}/cadreWork_au?toApply=1&id={{=id}}&cadreId={{=cadreId}}&&fid={{=parentRowKey}}"><i
            class="fa fa-edit"></i> 修改
    </button>
    <button class="confirm btn btn-xs btn-danger"
            data-parent="{{=parentRowKey}}"
            data-url="${ctx}/user/modifyTableApply_del?id={{=id}}&module=${module}"
            data-msg="申请删除该记录？"
            data-callback="_delCallback"><i class="fa fa-times"></i> 删除
    </button>
</script>
<script type="text/template" id="dispatch_select_tpl">
    <button class="popupBtn btn {{=(count>0)?'btn-warning':'btn-success'}} btn-xs"
            data-url="${ctx}/cadreWork_addDispatchs?id={{=id}}&cadreId={{=cadreId}}"
            data-width="1000"><i class="fa fa-link"></i>
        任免文件({{=count}})
    </button>
</script>
<style>
    .noSubWork [aria-describedby="jqGrid_cadreWork_subgrid"] a {
        display: none;
    }
</style>
<script>
    function _delCallback(type) {
        $("#modal").modal("hide");
        $.hashchange('cls=1&module=${module}');
    }
    $("#jqGrid_cadreWork").jqGrid({
        ondblClickRow: function () {
        },
        pager: "#jqGridPager_cadreWork",
        url: '${ctx}/cadreWork_data?cadreId=${cadre.id}&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {
                label: '其间工作', name: '&nbsp;', formatter: function (cellvalue, options, rowObject) {
                if (rowObject.subWorkCount == 0) return '--';
                return _.template($("#switch_tpl").html().NoMultiSpace())({
                    id: rowObject.id,
                    count: rowObject.subWorkCount
                });
            }, width: 130
            },
            {label: '开始日期', name: 'startTime', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}},
            {label: '结束日期', name: 'endTime', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}},
            {label: '工作单位及担任职务（或专技职务）', name: 'detail', width: 380},
            {label: '工作类型', name: 'workType', width: 140, formatter: $.jgrid.formatter.MetaType},
            {
                label: '是否担任领导职务', name: 'isCadre', width: 150, formatter: function (cellvalue, options, rowObject) {
                return cellvalue ? "是" : "否"
            }
            },
            {label: '备注', name: 'remark', width: 150},

            {
                label: '干部任免文件', name: 'dispatchCadreRelates', formatter: function (cellvalue, options, rowObject) {
                if(cellvalue==undefined) return '--'
                var count = cellvalue.length;
                <shiro:lacksPermission name="${PERMISSION_CADREADMIN}">
                if(count==0) return '--'
                </shiro:lacksPermission>
                return  _.template($("#dispatch_select_tpl").html().NoMultiSpace())
                ({id: rowObject.id, cadreId: rowObject.cadreId, count: count});
            }, width: 120
            }

        ],
        rowattr: function (rowData, currentObj, rowId) {
            //console.log(currentObj)
            if (currentObj.subWorkCount == 0) {
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
    }).on("initGrid", function () {

        $('.noSubWork [aria-describedby="jqGrid_cadreWork_subgrid"]').removeClass();
        //console.log(currentExpandRows)
        currentExpandRows.forEach(function(item, i){
            $("#jqGrid_cadreWork").expandSubGridRow(item)
        })
    });
    $(window).triggerHandler('resize.jqGrid');
    function _swtich(id, btn) {

        if (!$("i", btn).hasClass("fa-folder-open-o")) {
            $("#jqGrid_cadreWork").expandSubGridRow(id)
        } else {
            $("#jqGrid_cadreWork").collapseSubGridRow(id)
        }
        $.getEvent().stopPropagation();
    }

    var currentExpandRows = [];
    function subGridRowColapsed(parentRowID, parentRowKey) {
        $(".switchBtn i", '#jqGrid_cadreWork #' + parentRowKey).removeClass("fa-folder-open-o");
        $(".switchBtn span", '#jqGrid_cadreWork #' + parentRowKey).html("查看其间工作");
        currentExpandRows.remove(parentRowKey);
    }
    // the event handler on expanding parent row receives two parameters
    // the ID of the grid tow  and the primary key of the row
    function subGridRowExpanded(parentRowID, parentRowKey) {

        $(".switchBtn i", '#jqGrid_cadreWork #' + parentRowKey).addClass("fa-folder-open-o");
        $(".switchBtn span", '#jqGrid_cadreWork #' + parentRowKey).html("隐藏其间工作");
        currentExpandRows.push(parentRowKey);

        var childGridID = parentRowID + "_table";
        var childGridPagerID = parentRowID + "_pager";

        var childGridURL = '${ctx}/cadreWork_data?cadreId=${cadre.id}&fid={0}&${cm:encodeQueryString(pageContext.request.queryString)}'.format(parentRowKey);

        $('#' + parentRowID).append('<table id=' + childGridID + '></table><div id=' + childGridPagerID + ' class=scroll></div>');
        $("#" + childGridID).jqGrid({
            ondblClickRow: function () {
            },
            multiselect: false,
            url: childGridURL,
            colModel: [
                {label: '开始日期', name: 'startTime', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}},
                {label: '结束日期', name: 'endTime', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}},
                {label: '工作单位及担任职务（或专技职务）', name: 'detail', width: 380, align:'left'},
                {label: '工作类型', name: 'workType', formatter: $.jgrid.formatter.MetaType, width: 120},
                {label: '是否担任领导职务', name: 'isCadre', width: 140, formatter: $.jgrid.formatter.TRUEFALSE},
                {label: '备注', name: 'remark', width: 150},
                {
                    label: '干部任免文件',
                    name: 'dispatchCadreRelates',
                    formatter: function (cellvalue, options, rowObject) {
                        if(cellvalue==undefined) return '--'
                        var count = cellvalue.length;
                        <shiro:lacksPermission name="${PERMISSION_CADREADMIN}">
                        if(count==0) return '--'
                        </shiro:lacksPermission>
                        return  _.template($("#dispatch_select_tpl").html().NoMultiSpace())
                        ({id: rowObject.id, cadreId: rowObject.cadreId, count: count});
                    },
                    width: 120
                },
                {
                    label: '操作', name: 'op', formatter: function (cellvalue, options, rowObject) {
                    //alert(rowObject.id)
                    return _.template($("#subgrid_op_tpl").html().NoMultiSpace())
                    ({id: rowObject.id, parentRowKey: parentRowKey, cadreId: rowObject.cadreId})
                }, width: 150
                }
            ],
            pager: null
        });
    }
</script>