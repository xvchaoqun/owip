<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="tabbable myTableDiv">
    <ul class="jqgrid-vertical-offset nav nav-tabs padding-12 tab-color-blue background-blue">
        <li class="${type==1?"active":""}">
            <a href="javascript:" onclick="_innerPage(1)"><i class="fa fa-flag"></i> 任现职情况</a>
        </li>
        <li class="${type==2?"active":""}">
            <a href="javascript:" onclick="_innerPage(2)"><i class="fa fa-flag"></i> 任职经历</a>
        </li>
        <li class="${type==3?"active":""}">
            <a href="javascript:" onclick="_innerPage(3)"><i class="fa fa-flag"></i> 任职级经历</a>
        </li>
    </ul>
    <div class="space-4"></div>
    <c:if test="${type==1}">
        <div class="widget-box">
            <div class="widget-header">
                <h4 class="widget-title"><i class="fa fa-battery-full"></i> 主职
                    <div class="buttons">
                        <c:if test="${empty mainCadrePost}">
                            <a class="popupBtn btn btn-info btn-sm"
                               data-url="${ctx}/cadrePost_au?isMainPost=1&cadreId=${param.id}"><i
                                    class="fa fa-plus"></i> 添加主职</a>
                        </c:if>
                        <button class="popupBtn btn btn-warning btn-sm"
                                data-url="${ctx}/cadrePost_au?id=${mainCadrePost.id}&isMainPost=1&cadreId=${param.id}">
                            <i class="fa fa-edit"></i> 修改
                        </button>
                        <button class="confirm btn btn-danger btn-sm"
                                data-url="${ctx}/cadrePost_batchDel?ids[]=${mainCadrePost.id}"
                                data-title="删除主职"
                                data-msg="确定删除主职吗？"
                                data-callback="_reload">
                            <i class="fa fa-trash"></i> 删除
                        </button>
                    </div>
                </h4>

                <div class="widget-toolbar">
                    <a href="#" data-action="collapse">
                        <i class="ace-icon fa fa-chevron-up"></i>
                    </a>
                </div>
            </div>
            <div class="widget-body">
                <div class="widget-main table-nonselect">
                    <table id="jqGrid_mainCadrePost" data-width-reduce="50" class="jqGrid4"></table>
                </div>
            </div>
        </div>
        <div class="widget-box collapsed">
            <div class="widget-header">
                <h4 class="widget-title"><i class="fa fa-battery-half"></i> 兼职
                    <div class="buttons">
                        <a class="popupBtn btn  btn-sm btn-info"
                           data-url="${ctx}/cadrePost_au?isMainPost=0&cadreId=${param.id}"><i
                                class="fa fa-plus"></i> 添加兼职</a>
                        <button class="jqOpenViewBtn btn  btn-sm btn-warning"
                                data-url="${ctx}/cadrePost_au"
                                data-grid-id="#jqGrid_subCadrePosts"
                                data-querystr="&isMainPost=0&cadreId=${param.id}">
                            <i class="fa fa-edit"></i> 修改
                        </button>
                        <button data-url="${ctx}/cadrePost_batchDel"
                                data-title="删除"
                                data-msg="确定删除这{0}条数据？"
                                data-grid-id="#jqGrid_subCadrePosts"
                                data-callback="_reload"
                                class="jqBatchBtn btn btn-danger btn-sm">
                            <i class="fa fa-times"></i> 删除
                        </button>
                    </div>
                </h4>

                <div class="widget-toolbar">
                    <a href="#" data-action="collapse">
                        <i class="ace-icon fa fa-chevron-up"></i>
                    </a>
                </div>
            </div>
            <div class="widget-body">
                <div class="widget-main">
                    <table id="jqGrid_subCadrePosts" data-width-reduce="50" class="jqGrid4"></table>
                </div>
            </div>
        </div>
    </c:if>
    <c:if test="${type==2}">
        <div class="space-4"></div>
            <div class="jqgrid-vertical-offset buttons">
                <button class="jqOpenViewBtn btn  btn-sm btn-warning"
                        data-url="${ctx}/cadreWork_updateUnitId"
                        data-grid-id="#jqGrid_cadreWork"
                        data-querystr="&cadreId=${param.id}">
                    <i class="fa fa-edit"></i> 修改对应现运行单位
                </button>
            </div>
        <div class="space-4"></div>
        <table id="jqGrid_cadreWork" data-width-reduce="60" class="jqGrid2"></table>
        <div id="jqGridPager_cadreWork"></div>
    </c:if>
    <c:if test="${type==3}">
        <div class="space-4"></div>
        <div class="jqgrid-vertical-offset buttons">
            <a class="popupBtn btn  btn-sm btn-info"
               data-url="${ctx}/cadreAdminLevel_au?cadreId=${param.id}"><i class="fa fa-plus"></i>
                添加任职级经历</a>
            <button class="jqOpenViewBtn btn  btn-sm btn-warning"
                    data-url="${ctx}/cadreAdminLevel_au"
                    data-grid-id="#jqGrid_cadreAdminLevels"
                    data-querystr="&cadreId=${param.id}">
                <i class="fa fa-edit"></i> 修改
            </button>
            <button data-url="${ctx}/cadreAdminLevel_batchDel"
                    data-title="删除"
                    data-msg="确定删除这{0}条数据？"
                    data-grid-id="#jqGrid_cadreAdminLevels"
                    data-callback="_reload"
                    class="jqBatchBtn btn btn-danger btn-sm">
                <i class="fa fa-times"></i> 删除
            </button>
        </div>
        <div class="space-4"></div>
         <table id="jqGrid_cadreAdminLevels" data-width-reduce="60" class="jqGrid2"></table>
    </c:if>
</div>
<script type="text/template" id="dispatch_select_tpl">
    <button class="popupBtn btn btn-warning btn-xs"
            data-url="${ctx}/cadrePost_addDispatchs?id={{=id}}&cadreId={{=cadreId}}"
            data-width="1000"><i class="fa fa-link"></i>
        关联任命文件
    </button>
</script>
<script type="text/template" id="dispatch_show_tpl">
    <button class="popupBtn btn btn-success btn-xs"
            data-url="${ctx}/cadrePost_addDispatchs?id={{=id}}&cadreId={{=cadreId}}"
            data-width="1000"><i class="fa fa-link"></i>
        任命文件({{=count}})
    </button>
</script>
<script type="text/template" id="dispatch_adminLevel_tpl">
    <button class="popupBtn btn btn-xs btn-{{=!hasStart?'success':'primary'}}"
            data-url="${ctx}/cadreAdminLevel_addDispatchs?cls=start&id={{=id}}&cadreId={{=cadreId}}"
            data-width="1000">
        <i class="fa fa-link"></i>
        {{=!hasStart?"关联始任文件":"修改始任文件"}}
    </button>
    <button class="popupBtn btn btn-xs btn-{{=!hasEnd?'success':'primary'}}"
            data-url="${ctx}/cadreAdminLevel_addDispatchs?cls=end&id={{=id}}&cadreId={{=cadreId}}"
            data-width="1000">
        <i class="fa fa-link"></i>
        {{=!hasEnd?"关联结束文件":"修改结束文件"}}
    </button>
</script>

<style>
    .table-nonselect .table > tbody > tr.active > td,
    .table-nonselect .table tbody tr:hover td, .table-nonselect .table tbody tr:hover th {
        background-color: inherit !important;
    }

    .table-nonselect tr.success td {
        background-color: inherit !important;
    }
</style>
<c:set value="${cm:toJSONObject(mainCadrePost)}" var="mainCadrePostStr"/>
<script>
    function _innerPage(type) {
        $("#view-box .tab-content").load("${ctx}/cadrePost_page?id=${param.id}&type=" + type)
    }

    <c:if test="${type==1}">
    var mainCadrePost = ${mainCadrePostStr};
    $("#jqGrid_mainCadrePost").jqGrid({
        pager: null,
        ondblClickRow: function () {
        },
        height: 120,
        multiselect: false,
        datatype: "local",
        data: [${mainCadrePost==null?"":mainCadrePostStr}], // 防止出现[{}]，造成空行
        colModel: [
            {label: '职务', name: 'post', frozen: true},
            {
                label: '职务属性', width: 120, name: 'postId', formatter: function (cellvalue, options, rowObject) {
                return _cMap.metaTypeMap[cellvalue].name
            }, frozen: true
            },
            {
                label: '行政级别', name: 'adminLevelId', formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '';
                return _cMap.adminLevelMap[cellvalue].name
            }, frozen: true
            },
            {
                label: '是否正职', name: 'postId', formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '';
                return _cMap.postMap[cellvalue].boolAttr ? "是" : "否"
            }
            },
            {
                label: '职务类别', name: 'postClassId', formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '';
                return _cMap.postClassMap[cellvalue].name
            }
            },
            {
                label: '所在单位', name: 'unitId', formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '';
                return _cMap.unitMap[cellvalue].name
            }, width: 250
            },
            {
                label: '任职日期',
                name: 'dispatchCadreRelateBean.last.workTime',
                formatter: 'date',
                formatoptions: {newformat: 'Y-m-d'}
            },
            {
                label: '现任职务年限',
                width: 120,
                name: 'dispatchCadreRelateBean.last.workTime',
                formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue == undefined) return '';
                    var year = yearOffNow(cellvalue);
                    return year == 0 ? "未满一年" : year;
                }
            },
            {
                label: '现任职务始任日期',
                width: 150,
                name: 'dispatchCadreRelateBean.first.workTime',
                formatter: 'date',
                formatoptions: {newformat: 'Y-m-d'}
            },
            {
                label: '现任职务始任年限',
                width: 150,
                name: 'dispatchCadreRelateBean.first.workTime',
                formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue == undefined) return '';
                    var year = yearOffNow(cellvalue);
                    return year == 0 ? "未满一年" : year;
                }
            },
            {
                label: '现任职务始任文件',
                width: 150,
                name: 'dispatchCadreRelateBean.first',
                formatter: function (cellvalue, options, rowObject) {
                    if (!cellvalue || cellvalue.id == undefined) return '';
                    var dispatchCode = cellvalue.dispatchCode;
                    if (cellvalue.fileName && cellvalue.fileName != '')
                        return '<a href="javascript:void(0)" onclick="swf_preview({0}, \'file\')">{1}</a>'.format(cellvalue.id, dispatchCode);
                    else return dispatchCode;
                }
            },
            {
                label: '关联任命文件',
                name: 'dispatchCadreRelateBean.all',
                formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue == undefined) return '';
                    var count = cellvalue.length;
                    return count > 0 ? _.template($("#dispatch_show_tpl").html().NoMultiSpace())
                    ({id: rowObject.id, cadreId: rowObject.cadreId, count: count})
                            : _.template($("#dispatch_select_tpl").html().NoMultiSpace())
                    ({id: rowObject.id, cadreId: rowObject.cadreId});
                },
                width: 120
            },
            {
                label: '是否双肩挑', name: 'isDouble', formatter: function (cellvalue, options, rowObject) {
                return cellvalue ? "是" : "否";
            }
            },
            {
                label: '双肩挑单位', name: 'doubleUnitId', width: 150, formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '';
                return _cMap.unitMap[cellvalue].name
            }
            }
        ]
    }).jqGrid("setFrozenColumns");

    $("#jqGrid_subCadrePosts").jqGrid({
        pager: null,
        ondblClickRow: function () {
        },
        datatype: "local",
        height: 120,
        data:${cm:toJSONArray(subCadrePosts)},
        colModel: [
            {
                label: '兼任单位', width: 200, name: 'unitId', formatter: function (cellvalue, options, rowObject) {
                return _cMap.unitMap[cellvalue].name
            }, frozen: true
            },
            {label: '兼任职务', name: 'post', frozen: true},
            {
                label: '职务属性', width: 120, name: 'postId', formatter: function (cellvalue, options, rowObject) {
                return _cMap.metaTypeMap[cellvalue].name
            }
            },
            {
                label: '职务类别', name: 'postClassId', formatter: function (cellvalue, options, rowObject) {
                return _cMap.postClassMap[cellvalue].name
            }
            },
            {
                label: '兼任职务任职日期',
                width: 150,
                name: 'dispatchCadreRelateBean.last.workTime',
                formatter: 'date',
                formatoptions: {newformat: 'Y-m-d'}
            },
            {
                label: '兼任职务年限',
                width: 120,
                name: 'dispatchCadreRelateBean.last.workTime',
                formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue == undefined) return '';
                    var year = yearOffNow(cellvalue);
                    return year == 0 ? "未满一年" : year;
                }
            },
            {
                label: '兼任职务始任日期',
                width: 150,
                name: 'dispatchCadreRelateBean.first.workTime',
                formatter: 'date',
                formatoptions: {newformat: 'Y-m-d'}
            },
            {
                label: '兼任职务始任年限',
                width: 150,
                name: 'dispatchCadreRelateBean.first.workTime',
                formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue == undefined) return '';
                    var year = yearOffNow(cellvalue);
                    return year == 0 ? "未满一年" : year;
                }
            },
            {
                label: '兼任职务始任文件',
                width: 150,
                name: 'dispatchCadreRelateBean.first',
                formatter: function (cellvalue, options, rowObject) {
                    if (!cellvalue || cellvalue.id == undefined) return '';
                    var dispatchCode = cellvalue.dispatchCode;
                    if (cellvalue.fileName && cellvalue.fileName != '')
                        return '<a href="javascript:void(0)" onclick="swf_preview({0}, \'file\')">{1}</a>'.format(cellvalue.id, dispatchCode);
                    else return dispatchCode;
                }
            },
            {
                label: '关联任命文件',
                name: 'dispatchCadreRelateBean.all',
                formatter: function (cellvalue, options, rowObject) {
                    var count = cellvalue.length;
                    return count > 0 ? _.template($("#dispatch_show_tpl").html().NoMultiSpace())
                    ({id: rowObject.id, cadreId: rowObject.cadreId, count: count})
                            : _.template($("#dispatch_select_tpl").html().NoMultiSpace())
                    ({id: rowObject.id, cadreId: rowObject.cadreId});
                },
                width: 120
            }
        ]
    });

    $(window).triggerHandler('resize.jqGrid4');
    </c:if>
    <c:if test="${type==2}">
    $("#jqGrid_cadreWork").jqGrid({
        ondblClickRow: function () {
        },
        pager: "#jqGridPager_cadreWork",
        url: '${ctx}/cadreWork_data?fid=-1&isCadre=1&cadreId=${param.id}',
        colModel: [
            {label: '开始日期', name: 'startTime', formatter: 'date', formatoptions: {newformat: 'Y.m'}},
            {label: '结束日期', name: 'endTime', formatter: 'date', formatoptions: {newformat: 'Y.m'}},
            {label: '任职单位', name: 'unit', width: 280},
            {label: '担任职务', name: 'post', width: 280},
            {
                label: '行政级别', name: 'typeId', formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '';
                return _cMap.metaTypeMap[cellvalue].name
            }, width: 200
            },
            {
                label: '工作类型', name: 'workType', formatter: function (cellvalue, options, rowObject) {
                return _cMap.metaTypeMap[cellvalue].name
            }, width: 200
            },
            {
                label: '对应现运行单位', name: 'unitId', formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '';
                return _cMap.unitMap[cellvalue].name
            }, width: 200
            }
        ]
    });
    $(window).triggerHandler('resize.jqGrid2');
    </c:if>
    <c:if test="${type==3}">
    $("#jqGrid_cadreAdminLevels").jqGrid({
        pager: null,
        ondblClickRow: function () {
        },
        datatype: "local",
        data:${cm:toJSONArray(cadreAdminLevels)},
        colModel: [
            {
                label: '行政级别', name: 'adminLevelId', formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '';
                return _cMap.adminLevelMap[cellvalue].name
            }
            },
            {
                label: '是否现任职级', width: 120, name: 'isNow', formatter: function (cellvalue, options, rowObject) {
                return (rowObject.adminLevelId == mainCadrePost.adminLevelId) ? "是" : "否";
            }
            },
            {
                label: '职级始任日期',
                width: 120,
                name: 'startDispatch.workTime',
                formatter: 'date',
                formatoptions: {newformat: 'Y-m-d'}
            },
            {label: '职级始任职务', width: 120, name: 'startDispatchCadre.post'},
            {
                label: '职级始任文件',
                width: 120,
                name: 'startDispatch',
                formatter: function (cellvalue, options, rowObject) {
                    if (!cellvalue || cellvalue.id == undefined) return '';
                    var dispatchCode = cellvalue.dispatchCode;
                    if (cellvalue.fileName && cellvalue.fileName != '')
                        return '<a href="javascript:void(0)" onclick="swf_preview({0}, \'file\')">{1}</a>'.format(cellvalue.id, dispatchCode);
                    else return dispatchCode;
                }
            },
            {
                label: '职级结束日期',
                width: 120,
                name: 'endDispatch.workTime',
                formatter: 'date',
                formatoptions: {newformat: 'Y-m-d'}
            },
            {
                label: '职级结束文件', width: 150, name: 'endDispatch', formatter: function (cellvalue, options, rowObject) {
                if (!cellvalue || cellvalue.id == undefined) return '';
                var dispatchCode = cellvalue.dispatchCode;
                if (cellvalue.fileName && cellvalue.fileName != '')
                    return '<a href="javascript:void(0)" onclick="swf_preview({0}, \'file\')">{1}</a>'.format(cellvalue.id, dispatchCode);
                else return dispatchCode;
            }
            },
            {
                label: '任职级年限',
                width: 120,
                name: 'workYear',
                formatter: function (cellvalue, options, rowObject) {
                    //console.log(rowObject.endDispatch)
                    var end;
                    if( rowObject.endDispatch!=undefined)
                        end = rowObject.endDispatch.workTime;
                    if(rowObject.adminLevelId == mainCadrePost.adminLevelId)
                        end = new Date().format("yyyy-MM-dd");
                    if (rowObject.startDispatch==undefined|| end==undefined) return '';

                    var month = MonthDiff(rowObject.startDispatch.workTime, end);
                    //console.log("month="+month)
                    var year = Math.floor(month / 12);
                    return year == 0 ? "未满一年" : year;
                }
            },
            {
                label: '关联任免文件', name: 'selectDispatch', formatter: function (cellvalue, options, rowObject) {

                var hasStart = rowObject.startDispatchCadreId > 0;
                var hasEnd = rowObject.endDispatchCadreId > 0;
                return _.template($("#dispatch_adminLevel_tpl").html().NoMultiSpace())
                ({id: rowObject.id, hasStart: hasStart, hasEnd: hasEnd, cadreId: rowObject.cadreId});
            }, width: 250
            },
            {label: '备注', name: 'remark', width: 250}
        ]
    });
    $(window).triggerHandler('resize.jqGrid2');
    </c:if>


    function closeSwfPreview(close) {
        //$("#modal").modal('hide');
        //console.log(_url)
        if (close == 0) {
            $("#modal").modal("hide")
        } else {
            loadModal(_url, 1000);
        }
    }

    function _reload() {
        $("#modal").modal('hide');
        $("#view-box .tab-content").load("${ctx}/cadrePost_page?${cm:encodeQueryString(pageContext.request.queryString)}");
    }

    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>