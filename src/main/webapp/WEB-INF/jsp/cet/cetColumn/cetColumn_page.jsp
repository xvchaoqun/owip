<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="${param.columnType==1?'重点专题':'特色栏目'}" var="typeName"/>
<c:set value="${param.columnType==1?'重点子专题':'特色子栏目'}" var="subTypeName"/>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/cet/cetColumn"
             data-url-export="${ctx}/cet/cetColumn_data"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.name ||not empty param.code || not empty param.sort}"/>
            <div class="tabbable">
                <jsp:include page="../cetCourse/menu.jsp"/>
                <div class="tab-content">
                    <div class="tab-pane in active">
                        <div class="jqgrid-vertical-offset buttons">
                            <shiro:hasPermission name="cetColumn:edit">
                                <a class="popupBtn btn btn-success btn-sm"
                                   data-url="${ctx}/cet/cetColumn_au?isOnline=${param.isOnline}&columnType=${param.columnType}">
                                    <i class="fa fa-plus"></i> 添加</a>
                                <a class="jqOpenViewBtn btn btn-info btn-sm"
                                   data-grid-id="#jqGrid"
                                   data-url="${ctx}/cet/cetColumn_au"
                                   data-id-name="fid"
                                   data-querystr="&isOnline=${param.isOnline}&columnType=${param.columnType}"><i class="fa fa-plus"></i>
                                    添加${subTypeName}</a>

                                <a class="jqOpenViewBtn btn btn-primary btn-sm"
                                   data-url="${ctx}/cet/cetColumn_au"
                                   data-grid-id="#jqGrid"
                                   ><i class="fa fa-edit"></i>
                                    修改</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="cetColumn:del">
                                <button data-url="${ctx}/cet/cetColumn_batchDel"
                                        data-title="删除"
                                        data-msg="确定删除这{0}条数据？"
                                        data-grid-id="#jqGrid"
                                        class="jqBatchBtn btn btn-danger btn-sm">
                                    <i class="fa fa-trash"></i> 删除
                                </button>
                            </shiro:hasPermission>

                        </div>
                        <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
                            <div class="widget-header">
                                <h4 class="widget-title">搜索</h4><span class="widget-note">${note_searchbar}</span>

                                <div class="widget-toolbar">
                                    <a href="#" data-action="collapse">
                                        <i class="ace-icon fa fa-chevron-${_query?'up':'down'}"></i>
                                    </a>
                                </div>
                            </div>
                            <div class="widget-body">
                                <div class="widget-main no-padding">
                                    <form class="form-inline search-form" id="searchForm">
                                        <input type="hidden" name="cls" value="${param.cls}">
                                        <input type="hidden" name="columnType" value="${param.columnType}">
                                        <input type="hidden" name="type" value="${param.type}">
                                        <div class="form-group">
                                            <label>名称</label>
                                            <input class="form-control search-query" name="name" type="text"
                                                   value="${param.name}"
                                                   placeholder="请输入">
                                        </div>
                                        <div class="clearfix form-actions center">
                                            <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i>
                                                查找</a>

                                            <c:if test="${_query}">&nbsp;
                                                <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                        data-querystr="cls=${param.cls}&type=${param.type}&columnType=${param.columnType}">
                                                    <i class="fa fa-reply"></i> 重置
                                                </button>
                                            </c:if>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="space-4"></div>
            <table id="jqGrid" class="jqGrid table-striped"></table>
            <div id="jqGridPager"></div>
        </div>
        <div id="body-content-view"></div>
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
        <span>查看${subTypeName}</span>({{=count}})
    </button>
</script>
<script type="text/template" id="subgrid_op_tpl">
    <button class="popupBtn btn btn-xs btn-primary"
            data-url="${ctx}/cet/cetColumn_au?id={{=id}}&&fid={{=parentRowKey}}"><i
            class="fa fa-edit"></i> 编辑
    </button>
    <button class="popupBtn btn btn-xs btn-success"
            data-url="${ctx}/cet/cetColumnCourse?columnId={{=id}}"><i
            class="fa fa-search"></i> 包含课程({{=courseNum}})
    </button>
    <button class="confirm btn btn-xs btn-danger"
            data-parent="{{=parentRowKey}}"
            data-url="${ctx}/cet/cetColumn_batchDel?ids[]={{=id}}"
            data-msg="确定删除？"
            data-callback="_delCallback"><i class="fa fa-times"></i> 删除
    </button>
</script>

<script>
    $("#jqGrid").jqGrid({
        url: '${ctx}/cet/cetColumn_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {
                label: '${subTypeName}', name: '&nbsp;', formatter: function (cellvalue, options, rowObject) {
                if (rowObject.childNum == 0) return '--';
                return _.template($("#switch_tpl").html().NoMultiSpace())({
                    id: rowObject.id,
                    count: rowObject.childNum
                });
            }, width: 140},
            {label: '${typeName}', name: 'name', width:400,align:'left'},
            {
                label: '排序', index: 'sort', formatter: $.jgrid.formatter.sortOrder,
                formatoptions:{url: "${ctx}/cet/cetColumn_changeOrder"}
            },
            {label: '备注', name: 'remark', width:400,align:'left'}
        ],
        rowattr: function (rowData, currentObj, rowId) {
            //console.log(currentObj)
            if (currentObj.childNum == 0) {
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
        $('.noSubWork [aria-describedby="jqGrid_subgrid"]').removeClass();
        currentExpandRows.forEach(function(item, i){
            $("#jqGrid").expandSubGridRow(item)
        })
    });
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");

    function _swtich(id, btn) {

        if (!$("i", btn).hasClass("fa-folder-open-o")) {
            $("#jqGrid").expandSubGridRow(id)
        } else {
            $("#jqGrid").collapseSubGridRow(id)
        }
        $.getEvent().stopPropagation();
    }

    var currentExpandRows = [];
    function subGridRowColapsed(parentRowID, parentRowKey) {
        $(".switchBtn i", '#jqGrid #' + parentRowKey).removeClass("fa-folder-open-o");
        $(".switchBtn span", '#jqGrid #' + parentRowKey).html("查看${subTypeName}");
        currentExpandRows.remove(parentRowKey);
        //console.log(currentExpandRows)
    }
    // the event handler on expanding parent row receives two parameters
    // the ID of the grid tow  and the primary key of the row
    function subGridRowExpanded(parentRowID, parentRowKey) {

        $(".switchBtn i", '#jqGrid #' + parentRowKey).addClass("fa-folder-open-o");
        $(".switchBtn span", '#jqGrid #' + parentRowKey).html("隐藏${subTypeName}");
        currentExpandRows.remove(parentRowKey);
        currentExpandRows.push(parentRowKey);
        //console.log(currentExpandRows)
        var childGridID = parentRowID + "_table";
        var childGridPagerID = parentRowID + "_pager";

        var childGridURL = '${ctx}/cet/cetColumn_data?fid={0}&${cm:encodeQueryString(pageContext.request.queryString)}'.format(parentRowKey);

        $('#' + parentRowID).append('<table id=' + childGridID + '></table><div id=' + childGridPagerID + ' class=scroll></div>');
        $("#" + childGridID).jqGrid({
            ondblClickRow: function () {
            },
            multiselect: false,
            url: childGridURL,
            colModel: [
                {label: '${typeName}', name: 'name', width:300,align:'left'},
                {
                    label: '排序', index: 'sort', formatter: $.jgrid.formatter.sortOrder,
                    formatoptions:{url: "${ctx}/cet/cetColumn_changeOrder"}
                },
                {label: '备注', name: 'remark', width:150,align:'left'},
                {
                    label: '操作', name: 'op', formatter: function (cellvalue, options, rowObject) {
                    //alert(rowObject.id)
                    return _.template($("#subgrid_op_tpl").html().NoMultiSpace())
                    ({id: rowObject.id, courseNum:rowObject.courseNum, parentRowKey: parentRowKey})
                }, width: 250
                }
            ],
            pager: null
        });
    }

    function _delCallback(target) {
        //_reloadSubGrid($(target).data("parent"))
        $("#jqGrid").trigger("reloadGrid");
    }
</script>