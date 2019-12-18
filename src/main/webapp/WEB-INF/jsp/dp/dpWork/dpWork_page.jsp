<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<%--<ul class="jqgrid-vertical-offset nav nav-tabs padding-12 tab-color-blue background-blue">
    <li class="active">
        <a href="javascript:;" onclick="_innerPage(1)"><i class="fa fa-list"></i> 工作经历</a>
    </li>
</ul>--%>
            <div class="space-4"></div>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="dpWork:edit">
                    <a class="popupBtn btn btn-success btn-sm"
                       data-url="${ctx}/dp/dpWork_au?userId=${param.userId}"><i class="fa fa-plus"></i>
                        添加工作经历</a>
                    <a class="jqOpenViewBtn btn btn-info btn-sm"
                       data-grid-id="#jqGrid_dpWork"
                       data-url="${ctx}/dp/dpWork_au"
                       data-id-name="fid"
                       data-querystr="&userId=${param.userId}"><i class="fa fa-plus"></i>
                        添加其间工作</a>
                </shiro:hasPermission>
            </div>
    <div class="space-4"></div>
    <table id="jqGrid_dpWork" class="jqGrid2"  data-height-reduce="30"></table>
    <div id="jqGridPager_dpWork"></div>

<script type="text/template" id="switch_tpl">
    <button class="switchBtn btn btn-info btn-xs" onclick="_swtich({{=id}}, this)"
            data-id="{{=id}}"><i class="fa fa-folder-o"></i>
        <span>查看其间工作</span>({{=subWorkCount}})
    </button>
</script>
<script type="text/template" id="op_tpl">
<shiro:hasPermission name="cadreWork:edit">
    <button class="popupBtn btn btn-xs btn-primary"
            data-url="${ctx}/dp/dpWork_au?id={{=id}}&userId=${param.userId}&&fid={{=parentRowKey}}"><i
            class="fa fa-edit"></i> 编辑
    </button>
    <%--<shiro:hasRole name="${ROLE_CADREADMIN}">
    {{if(parentRowKey>0){}}
    <button class="confirm btn btn-xs btn-info"
            data-msg="确定转移至主要工作经历？"
            data-callback="_callback"
            data-url="${ctx}/dp/dpWork_transfer?id={{=id}}&userId={{=userId}}"><i
            class="fa fa-reply"></i> 转移
    </button>
    {{}else if(subWorkCount==0){}}
        <button class="popupBtn btn btn-xs btn-info"
                data-url="${ctx}/dp/dpWork_transferToSubWork?id={{=id}}&cadreId={{=cadreId}}"><i
                class="fa fa-reply"></i> 转移
        </button>
    {{}}}
    </shiro:hasRole>--%>
</shiro:hasPermission>
<shiro:hasPermission name="dpWork:del">
    <button class="confirm btn btn-xs btn-danger"
            data-parent="{{=parentRowKey}}"
            data-url="${ctx}/dp/dpWork_batchDel?ids[]={{=id}}&userId=${param.userId}"
            data-msg="确定删除该工作经历？"
            data-callback="_callback"><i class="fa fa-times"></i> 删除
    </button>
    </shiro:hasPermission>
</script>
<%--<script type="text/template" id="dispatch_select_tpl">
    <button class="popupBtn btn {{=(count>0)?'btn-warning':'btn-success'}} btn-xs"
            data-url="${ctx}/dp/dpWork_addDispatchs?id={{=id}}&userId={{=userId}}"
            data-width="1000"><i class="fa fa-link"></i>
        任免文件({{=count}})
    </button>
</script>--%>
<style>
    .noSubWork [aria-describedby="jqGrid_dpWork_subgrid"] a {
        display: none;
    }
    .table > tbody > tr.ui-subgrid.active > td,
    .table tbody tr.ui-subgrid:hover td, .table tbody tr.ui-subgrid:hover th {
        background-color: inherit !important;
    }

    .ui-subgrid tr.success td {
        background-color: inherit !important;
    }
    .resume p {
         text-indent: -9em;
         margin: 0 0 0 9em;
    }
</style>

    <script>
        function _innerPage(type, fn) {
            $("#view-box .tab-content").loadPage({url:"${ctx}/dp/dpWork?userId=${param.userId}&type=" + type, callback:fn})
        }
        $("#jqGrid_dpWork").jqGrid({
            <c:if test="${!cm:isPermitted(PERMISSION_CADREADMIN) && !hasDirectModifyCadreAuth}">
            multiselect: false,
            </c:if>
            ondblClickRow: function () {
            },
            pager: "#jqGridPager_dpWork",
            url: '${ctx}/dp/dpWork_data?${cm:encodeQueryString(pageContext.request.queryString)}',
            colModel: [
                {
                    label: '其间工作', name: '&nbsp;', formatter: function (cellvalue, options, rowObject) {
                    if (rowObject.subWorkCount == 0) return '--';
                    //console.log(rowObject)
                    return _.template($("#switch_tpl").html().NoMultiSpace())({
                        id: rowObject.id,
                        subWorkCount: rowObject.subWorkCount
                    });
                }, width: 130
                },
                {label: '开始日期', name: 'startTime', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}},
                {label: '结束日期', name: 'endTime', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}},
                {label: '工作单位及担任职务（或专技职务）', name: 'detail', width: 380, align: 'left'},
                {label: '工作类型', name: 'workTypes', align:'left', width: 270, formatter: function (cellvalue, options, rowObject) {
                    if($.trim(cellvalue)=='') return '--'
                    return ($.map(cellvalue.split(","), function(workType){
                        return $.jgrid.formatter.MetaType(workType);
                    })).join("，")
                }},
                {label: '是否担任领导职务', name: 'isCadre', width: 150, formatter: $.jgrid.formatter.TRUEFALSE},
                {label: '备注', name: 'remark', width: 150},
                        {
                            label: '操作', name: 'op', formatter: function (cellvalue, options, rowObject) {
                            //alert(rowObject.id)
                            return _.template($("#op_tpl").html().NoMultiSpace())
                            ({id: rowObject.id, parentRowKey: null, subWorkCount: rowObject.subWorkCount, cadreId: rowObject.cadreId})
                        }, width: ${cm:hasRole(ROLE_CADREADMIN)?200:150}
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
                plusicon: "fa fa-folder-o",
                minusicon: "fa fa-folder-open-o"
                // selectOnExpand:true
            }
        }).on("initGrid", function () {

            $('.noSubWork [aria-describedby="jqGrid_dpWork_subgrid"]').removeClass();

            //console.log(currentExpandRows)
            currentExpandRows.forEach(function(item, i){
                $("#jqGrid_dpWork").expandSubGridRow(item)
            })
        });

        $(window).triggerHandler('resize.jqGrid2');

        function _swtich(id, btn) {

            if (!$("i", btn).hasClass("fa-folder-open-o")) {
                $("#jqGrid_dpWork").expandSubGridRow(id)
            } else {
                $("#jqGrid_dpWork").collapseSubGridRow(id)
            }
            $.getEvent().stopPropagation();
        }

        var currentExpandRows = [];
        function subGridRowColapsed(parentRowID, parentRowKey) {
            $(".switchBtn i", '#jqGrid_dpWork #' + parentRowKey).removeClass("fa-folder-open-o");
            $(".switchBtn span", '#jqGrid_dpWork #' + parentRowKey).html("查看其间工作");
            currentExpandRows.remove(parentRowKey);
        }
        // the event handler on expanding parent row receives two parameters
        // the ID of the grid tow  and the primary key of the row
        function subGridRowExpanded(parentRowID, parentRowKey) {

            $(".switchBtn i", '#jqGrid_dpWork #' + parentRowKey).addClass("fa-folder-open-o");
            $(".switchBtn span", '#jqGrid_dpWork #' + parentRowKey).html("隐藏其间工作");
            currentExpandRows.remove(parentRowKey);
            currentExpandRows.push(parentRowKey);

            var childGridID = parentRowID + "_table";
            var childGridPagerID = parentRowID + "_pager";

            var childGridURL = '${ctx}/dp/dpWork_data?isEduWork=0&fid={0}&${cm:encodeQueryString(pageContext.request.queryString)}'.format(parentRowKey);

            $('#' + parentRowID).append('<table id=' + childGridID + '></table><div id=' + childGridPagerID + ' class=scroll></div>');
            $("#" + childGridID).jqGrid({
                ondblClickRow: function () {
                },
                multiselect: false,
                url: childGridURL,
                colModel: [
                    {label: '开始日期', name: 'startTime', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}},
                    {label: '结束日期', name: 'endTime', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}},
                    {label: '工作单位及担任职务（或专技职务）', name: 'detail', width: 380, align: 'left'},
                    {label: '工作类型', name: 'workTypes', width: 270, formatter: function (cellvalue, options, rowObject) {
                        if($.trim(cellvalue)=='') return '--'
                        return ($.map(cellvalue.split(","), function(workType){
                            return $.jgrid.formatter.MetaType(workType);
                        })).join("，")
                    }},
                    {label: '是否担任领导职务', name: 'isCadre', width: 140, formatter: $.jgrid.formatter.TRUEFALSE},
                    {label: '备注', name: 'remark', width: 150},
                    {
                        label: '干部任免文件',
                        name: 'dispatchCadreRelates',
                        formatter: function (cellvalue, options, rowObject) {
                            if(cellvalue==undefined) return '--'
                            var count = cellvalue.length;
                            <shiro:lacksPermission name="${PERMISSION_CADREADMIN}">
                            if (count == 0) return '--'
                            </shiro:lacksPermission>
                            return _.template($("#dispatch_select_tpl").html().NoMultiSpace())
                            ({id: rowObject.id, cadreId: rowObject.cadreId, count: count});
                        },
                        width: 120
                    },
                    <c:if test="${cm:isPermitted(PERMISSION_CADREADMIN) || hasDirectModifyCadreAuth}">
                    <shiro:lacksPermission name="${PERMISSION_CADREONLYVIEW}">
                    {
                        label: '操作', name: 'op', formatter: function (cellvalue, options, rowObject) {
                        //alert(rowObject.id)
                        return _.template($("#op_tpl").html().NoMultiSpace())
                        ({id: rowObject.id, parentRowKey: parentRowKey, cadreId: rowObject.cadreId})
                    }, width: ${cm:hasRole(ROLE_CADREADMIN)?200:150}
                    }
                    </shiro:lacksPermission>
                    </c:if>
                ],
                pager: null
            });
        }

        function _callback(target) {
            //_reloadSubGrid($(target).data("parent"))
            $("#jqGrid_dpWork").trigger("reloadGrid");
        }

        function _reloadSubGrid(fid) {
            if (fid > 0) $("#jqGrid_dpWork").collapseSubGridRow(fid).expandSubGridRow(fid)
        }

        // 删除其间工作时调用
        function showSubWork(id) {
            $.loadModal("${ctx}/dp/dpWork?userId=${param.userId}&fid=" + id, 1000);
        }

        $('#searchForm [data-rel="select2"]').select2();
        $('[data-rel="tooltip"]').tooltip();
    </script>
