<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="tabbable">
    <ul class="jqgrid-vertical-offset nav nav-tabs padding-12 tab-color-blue background-blue">
        <li class="${type==1?"active":""}">
            <a href="javascript:;" onclick="_innerPage(1)"><i class="fa fa-flag"></i> 工作经历</a>
        </li>
        <li class="${type==2?"active":""}">
            <a href="javascript:;" onclick="_innerPage(2)"><i class="fa fa-flag"></i> 预览</a>
        </li>
    </ul>
    <c:if test="${type==1}">
    <div class="space-4"></div>
<div class="jqgrid-vertical-offset buttons">
    <shiro:hasPermission name="cadreWork:edit">
        <a class="popupBtn btn btn-success btn-sm"
           data-url="${ctx}/cadreWork_au?cadreId=${param.cadreId}"><i class="fa fa-plus"></i>
            添加工作经历</a>
        <a class="jqOpenViewBtn btn btn-primary btn-sm"
           data-url="${ctx}/cadreWork_au"
           data-grid-id="#jqGrid_cadreWork"
           data-querystr="&cadreId=${param.cadreId}"><i class="fa fa-edit"></i>
            修改工作经历</a>
        <a class="jqOpenViewBtn btn btn-success btn-sm"
           data-grid-id="#jqGrid_cadreWork"
           data-url="${ctx}/cadreWork_au"
           data-id-name="fid"
           data-querystr="&cadreId=${param.cadreId}"><i class="fa fa-plus"></i>
            添加期间工作</a>
    </shiro:hasPermission>
    <shiro:hasPermission name="cadreWork:del">
        <button data-url="${ctx}/cadreWork_batchDel"
                data-title="删除"
                data-msg="确定删除这{0}条数据？"
                data-grid-id="#jqGrid_cadreWork"
                class="jqBatchBtn btn btn-danger btn-sm">
            <i class="fa fa-times"></i> 删除
        </button>
    </shiro:hasPermission>
    <span style="padding-left: 50px">点击列表第二列图标 <i class="fa fa-folder-o"></i> 显示/隐藏期间工作经历 </span>
</div>
<div class="space-4"></div>
<table id="jqGrid_cadreWork" class="jqGrid2"></table>
<div id="jqGridPager_cadreWork"></div>
</div>
    </c:if>
    <c:if test="${type==2}">
        <div class="row two-frames">
            <div class="left">
                <div class="widget-box">
                    <div class="widget-header">
                        <h4 class="smaller">
                            参考
                        </h4>
                    </div>
                    <div class="widget-body">
                        <div class="widget-main" style="min-height: 647px">
                           <c:forEach items="${cadreWorks}" var="cadreWork">
                               <p>${cm:formatDate(cadreWork.startTime, "yyyy.MM")}${(cadreWork.endTime!=null)?"-":"-至今"}${cm:formatDate(cadreWork.endTime, "yyyy.MM")}
                               &nbsp;&nbsp;${cadreWork.unit}${cadreWork.post}</p>
                               <c:if test="${fn:length(cadreWork.subCadreWorks)>0}">
                                   <c:forEach items="${cadreWork.subCadreWorks}" var="subCadreWork" varStatus="vs">
                                       <c:if test="${vs.first}"><p style="text-indent: 2em">期间：</c:if>
                                       <c:if test="${!vs.first}"><p style="text-indent: 5em"></c:if>
                                       ${cm:formatDate(subCadreWork.startTime, "yyyy.MM")}${(subCadreWork.endTime!=null)?"-":""}${cm:formatDate(subCadreWork.endTime, "yyyy.MM")}
                                       &nbsp;&nbsp;${subCadreWork.unit}${subCadreWork.post}</p>
                                   </c:forEach>
                               </c:if>
                           </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
            <div class="right">
                <div class="widget-box">
                    <div class="widget-header">
                        <h4 class="smaller">
                            编辑区域
                        </h4>
                    </div>
                    <div class="widget-body">
                        <div class="widget-main" style="margin-bottom: 10px">
                            <textarea id="content">
                                <c:if test="${not empty cadreInfo.work}">${cadreInfo.work}</c:if>
                                <c:if test="${empty cadreInfo.work}">
                                <c:forEach items="${cadreWorks}" var="cadreWork">
                                    <p>${cm:formatDate(cadreWork.startTime, "yyyy.MM")}${(cadreWork.endTime!=null)?"-":"-至今"}${cm:formatDate(cadreWork.endTime, "yyyy.MM")}
                                        &nbsp;&nbsp;${cadreWork.unit}${cadreWork.post}</p>
                                    <c:if test="${fn:length(cadreWork.subCadreWorks)>0}">
                                        <c:forEach items="${cadreWork.subCadreWorks}" var="subCadreWork" varStatus="vs">
                                            <c:if test="${vs.first}"><p style="text-indent: 2em">期间：</c:if>
                                            <c:if test="${!vs.first}"><p style="text-indent: 5em"></c:if>
                                            ${cm:formatDate(subCadreWork.startTime, "yyyy.MM")}${(subCadreWork.endTime!=null)?"-":""}${cm:formatDate(subCadreWork.endTime, "yyyy.MM")}
                                            &nbsp;&nbsp;${subCadreWork.unit}${subCadreWork.post}</p>
                                        </c:forEach>
                                    </c:if>
                                </c:forEach>
                                </c:if>
                            </textarea>
                            <input type="hidden" name="content">
                        </div>
                        <div class="modal-footer center">
                            <input type="button" onclick="updateCadreInfoWork()" class="btn btn-primary" value="${empty cadreInfo.work?"提交":"修改"}"/>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </c:if>
<style>
    .table > tbody > tr.ui-subgrid.active > td,
    .table tbody tr.ui-subgrid:hover td, .table tbody tr.ui-subgrid:hover th {
        background-color: inherit !important;
    }

    .ui-subgrid tr.success td {
        background-color: inherit !important;
    }
</style>
<script type="text/template" id="subgrid_op_tpl">
<button class="popupBtn btn btn-xs btn-primary"
        data-url="${ctx}/cadreWork_au?id={{=id}}&cadreId={{=cadreId}}&&fid={{=parentRowKey}}"><i
        class="fa fa-edit"></i> 编辑
</button>&nbsp;
<button class="confirm btn btn-xs btn-danger"
        data-parent="{{=parentRowKey}}"
        data-url="${ctx}/cadreWork_batchDel?ids[]={{=id}}"
        data-msg="确定删除该记录？"
        data-callback="_delCallback"><i class="fa fa-times"></i> 删除
</button>
</script>

<script type="text/template" id="dispatch_select_tpl">
<button class="popupBtn btn btn-warning btn-xs"
        data-url="${ctx}/cadreWork_addDispatchs?id={{=id}}&cadreId={{=cadreId}}"
        data-width="1000"><i class="fa fa-link"></i>
    关联任免文件
</button>
</script>
<script type="text/template" id="dispatch_show_tpl">
<button class="popupBtn btn btn-success btn-xs"
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
<c:if test="${type==2}">
    <style>
        .two-frames{
            padding: 10px 20px;
            max-width: 1330px;
        }
        .two-frames .left, .two-frames .right{
            float: left;
        }
        .two-frames .left{
            width: 630px;
            margin-right: 25px;
        }
        .two-frames .right{
            width: 630px;
        }
    </style>
<script type="text/javascript" src="${ctx}/kindeditor/kindeditor.js"></script>
<script>
    KE.init({
        id: 'content',
        height: '550px',
        resizeMode: 1,
        width: '600px',
        //scriptPath:"${ctx}/js/kindeditor/",
        //skinsPath : KE.scriptPath + 'skins/',
        items: [
            'fontname', 'fontsize', '|', 'textcolor', 'bgcolor', 'bold', 'italic', 'underline',
            'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'image', 'link', 'unlink', 'fullscreen']
    });
    KE.create('content');
    function updateCadreInfoWork(){
        $.post("${ctx}/cadreInfo_updateWork",{cadreId:'${param.cadreId}',work:KE.util.getData('content')},function(ret){
            if(ret.success){
                SysMsg.info("操作成功");
            }
        });
    }
</script>
</c:if>
<c:if test="${type==1}">
<script>
    function _innerPage(type){
        $("#view-box .tab-content").load("${ctx}/cadreWork_page?cadreId=${param.cadreId}&type="+type)
    }
    $("#jqGrid_cadreWork").jqGrid({
        ondblClickRow: function () {
        },
        pager: "#jqGridPager_cadreWork",
        url: '${ctx}/cadreWork_data?${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '开始日期', name: 'startTime', formatter: 'date', formatoptions: {newformat: 'Y.m'}},
            {label: '结束日期', name: 'endTime', formatter: 'date', formatoptions: {newformat: 'Y.m'}},
            {label: '工作单位', name: 'unit' ,width: 280},
            {label: '担任职务或者专技职务', name: 'post', width: 280},
            {
                label: '行政级别', name: 'typeId', formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return ''
                return _metaTypeMap[cellvalue]
            }, width: 200
            },
            {
                label: '工作类型', name: 'workType', formatter: function (cellvalue, options, rowObject) {
                return _metaTypeMap[cellvalue]
            }, width: 200
            },
            {
                label: '干部任职', name: 'isCadre', formatter: function (cellvalue, options, rowObject) {
                return cellvalue ? "是" : "否"
            }
            },
            {
                label: '关联任免文件', name: 'dispatchCadreRelates', formatter: function (cellvalue, options, rowObject) {
                var count = cellvalue.length;
                return count > 0 ? _.template($("#dispatch_show_tpl").html().replace(/\n|\r|(\r\n)/g, ''))
                ({id: rowObject.id, cadreId: rowObject.cadreId, count: count})
                        : _.template($("#dispatch_select_tpl").html().replace(/\n|\r|(\r\n)/g, ''))
                ({id: rowObject.id, cadreId: rowObject.cadreId});
            }, width: 200
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
        subGridRowColapsed:subGridRowColapsed,
        subGridOptions: {
            // configure the icons from theme rolloer
            plusicon: "fa fa-folder-o",
            minusicon: "fa fa-folder-open-o"
            // selectOnExpand:true
            //openicon: "ui-icon-arrowreturn-1-e"
        }
    }).on("initGrid", function () {
        $(window).triggerHandler('resize.jqGrid2');
        $('.noSubWork [aria-describedby="jqGrid_cadreWork_subgrid"]').removeClass();

        //console.log(currentExpandRows)
        for(i in currentExpandRows)
            $("#jqGrid_cadreWork").expandSubGridRow(currentExpandRows[i])
    });

    var currentExpandRows = [];
    function subGridRowColapsed(parentRowID, parentRowKey){
        currentExpandRows.remove(parentRowKey);
    }
    // the event handler on expanding parent row receives two parameters
    // the ID of the grid tow  and the primary key of the row
    function subGridRowExpanded(parentRowID, parentRowKey) {

        currentExpandRows.push(parentRowKey);

        var childGridID = parentRowID + "_table";
        var childGridPagerID = parentRowID + "_pager";

        var childGridURL = '${ctx}/cadreWork_data?fid={0}&${cm:encodeQueryString(pageContext.request.queryString)}'.format(parentRowKey);

        $('#' + parentRowID).append('<table id=' + childGridID + '></table><div id=' + childGridPagerID + ' class=scroll></div>');
        $("#" + childGridID).jqGrid({
            ondblClickRow: function () {
            },
            multiselect: false,
            url: childGridURL,
            colModel: [
                {label: '开始日期', name: 'startTime', formatter: 'date', formatoptions: {newformat: 'Y.m'}},
                {label: '结束日期', name: 'endTime', formatter: 'date', formatoptions: {newformat: 'Y.m'}},
                {label: '工作单位', name: 'unit',width: 280},
                {label: '担任职务或者专技职务', name: 'post', width: 180},
                {
                    label: '行政级别', name: 'typeId', formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue == undefined) return ''
                    return _metaTypeMap[cellvalue]
                }
                },
                {
                    label: '工作类型', name: 'workType', formatter: function (cellvalue, options, rowObject) {
                    return _metaTypeMap[cellvalue]
                }, width: 120
                },
                {
                    label: '干部任职', name: 'isCadre', formatter: function (cellvalue, options, rowObject) {
                    return cellvalue ? "是" : "否"
                }
                }, {
                    label: '关联任免文件', name: 'dispatchCadreRelates', formatter: function (cellvalue, options, rowObject) {
                        var count = cellvalue.length;
                        return count > 0 ? _.template($("#dispatch_show_tpl").html().replace(/\n|\r|(\r\n)/g, ''))
                        ({id: rowObject.id, cadreId: rowObject.cadreId, count: count})
                                : _.template($("#dispatch_select_tpl").html().replace(/\n|\r|(\r\n)/g, ''))
                        ({id: rowObject.id, cadreId: rowObject.cadreId});
                    }, width: 120
                },
                {
                    label: '操作', name: 'op', formatter: function (cellvalue, options, rowObject) {
                    //alert(rowObject.id)
                    return _.template($("#subgrid_op_tpl").html().replace(/\n|\r|(\r\n)/g, ''))
                    ({id: rowObject.id, parentRowKey: parentRowKey, cadreId: rowObject.cadreId})
                }, width: 150
                }
            ],
            pager: null
        });
    }

    function _delCallback(target) {
        //_reloadSubGrid($(target).data("parent"))
        $("#jqGrid_cadreWork").trigger("reloadGrid");
    }

    function _reloadSubGrid(fid) {
        if (fid > 0) $("#jqGrid_cadreWork").collapseSubGridRow(fid).expandSubGridRow(fid)
    }

    // 删除期间工作时调用
    function showSubWork(id) {
        loadModal("${ctx}/cadreWork_page?cadreId=${param.cadreId}&fid=" + id, 1000);
    }

    function showDispatch(_param) {
        //alert(_param)
        loadModal("${ctx}/cadreWork_addDispatchs?cadreId=${param.cadreId}&" + _param, 1000);
    }

    function closeSwfPreview(close) {
        if(close==0){
            $("#modal").modal("hide")
        }else {
            showDispatch(_param);
        }
    }

    /*function _reload() {
        $("#modal").modal('hide');
        $("#view-box .tab-content").load("${ctx}/cadreWork_page?${cm:encodeQueryString(pageContext.request.queryString)}");
    }*/

    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>
</c:if>