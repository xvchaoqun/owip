<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<ul class="jqgrid-vertical-offset nav nav-tabs padding-12 tab-color-blue background-blue">
    <li class="${type==1?"active":""}">
        <a href="javascript:;" onclick="_innerPage(1)"><i class="fa fa-flag"></i> 任现职情况</a>
    </li>
    <shiro:hasPermission name="${PERMISSION_CADREADMIN}">
        <shiro:lacksPermission name="${PERMISSION_CADREONLYVIEW}">
    <li class="${type==2?"active":""}">
        <a href="javascript:;" onclick="_innerPage(2)"><i class="fa fa-flag"></i> 任职经历</a>
    </li>
        </shiro:lacksPermission>
    </shiro:hasPermission>
    <li class="${type==3?"active":""}">
        <a href="javascript:;" onclick="_innerPage(3)"><i class="fa fa-flag"></i> 任职级经历</a>
    </li>
<shiro:lacksPermission name="${PERMISSION_CADREONLYVIEW}">
    <div class="buttons" style="position:absolute;left: 360px;">
        <a class="popupBtn btn btn-warning btn-sm"
           data-width="800"
           data-url="${ctx}/hf_content?code=hf_cadre_post">
            <i class="fa fa-info-circle"></i> 填写说明</a>
    </div>
</shiro:lacksPermission>
</ul>
<div class="space-4"></div>
<c:if test="${type==1}">
    <div class="widget-box">
        <div class="widget-header">
            <h4 class="widget-title"><i class="fa fa-battery-full"></i> 主职
                <shiro:hasPermission name="${PERMISSION_CADREADMIN}">
                    <shiro:lacksPermission name="${PERMISSION_CADREONLYVIEW}">
                <div class="buttons">
                    <c:if test="${empty mainCadrePost}">
                        <a class="popupBtn btn btn-info btn-sm"
                           data-url="${ctx}/cadrePost_au?isMainPost=1&cadreId=${param.cadreId}"><i
                                class="fa fa-plus"></i> 添加主职</a>
                    </c:if>
                    <c:if test="${not empty mainCadrePost}">
                    <button class="popupBtn btn btn-warning btn-sm"
                            data-url="${ctx}/cadrePost_au?id=${mainCadrePost.id}&isMainPost=1&cadreId=${param.cadreId}">
                        <i class="fa fa-edit"></i> 修改
                    </button>
                    </c:if>
                    <button class="confirm btn btn-danger btn-sm"
                            data-url="${ctx}/cadrePost_batchDel?ids[]=${mainCadrePost.id}"
                            data-title="删除主职"
                            data-msg="确定删除主职吗？"
                            data-callback="_reload">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </div>
                    </shiro:lacksPermission>
                </shiro:hasPermission>
            </h4>

            <div class="widget-toolbar">
                <a href="javascript:;" data-action="collapse">
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
    <div class="widget-box">
        <div class="widget-header">
            <h4 class="widget-title"><i class="fa fa-battery-half"></i> 兼职
                <shiro:hasPermission name="${PERMISSION_CADREADMIN}">
                    <shiro:lacksPermission name="${PERMISSION_CADREONLYVIEW}">
                <div class="buttons">
                    <a class="popupBtn btn  btn-sm btn-info"
                       data-url="${ctx}/cadrePost_au?isMainPost=0&cadreId=${param.cadreId}"><i
                            class="fa fa-plus"></i> 添加兼职</a>
                    <button class="jqOpenViewBtn btn  btn-sm btn-warning"
                            data-url="${ctx}/cadrePost_au"
                            data-grid-id="#jqGrid_subCadrePosts"
                            data-querystr="&isMainPost=0&cadreId=${param.cadreId}">
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
                    </shiro:lacksPermission>
                </shiro:hasPermission>
            </h4>

            <div class="widget-toolbar">
                <a href="javascript:;" data-action="collapse">
                    <i class="ace-icon fa fa-chevron-up"></i>
                </a>
            </div>
        </div>
        <div class="widget-body">
            <div class="widget-main">
                <table id="jqGrid_subCadrePosts" data-width-reduce="50" class="jqGrid4"></table>
                <div id="jqGridPager_subCadrePosts"></div>
            </div>
        </div>
    </div>
</c:if>
<c:if test="${type==2}">
    <shiro:hasPermission name="${PERMISSION_CADREADMIN}">
        <shiro:lacksPermission name="${PERMISSION_CADREONLYVIEW}">
    <div class="space-4"></div>
    <div class="jqgrid-vertical-offset buttons">
        <button class="jqOpenViewBtn btn  btn-sm btn-warning"
                data-url="${ctx}/cadreWork_updateUnitId"
                data-grid-id="#jqGrid_cadreWork" data-width="800"
                data-querystr="&cadreId=${param.cadreId}">
            <i class="fa fa-edit"></i> 修改所属内设机构
        </button>
    </div>
        </shiro:lacksPermission>
    </shiro:hasPermission>
    <div class="space-4"></div>
    <table id="jqGrid_cadreWork" data-width-reduce="60" class="jqGrid2"></table>
    <div id="jqGridPager_cadreWork"></div>
</c:if>
<c:if test="${type==3}">
    <shiro:hasPermission name="${PERMISSION_CADREADMIN}">
        <shiro:lacksPermission name="${PERMISSION_CADREONLYVIEW}">
    <div class="space-4"></div>
    <div class="jqgrid-vertical-offset buttons">
        <a class="popupBtn btn  btn-sm btn-info"
           data-url="${ctx}/cadreAdminLevel_au?cadreId=${param.cadreId}"><i class="fa fa-plus"></i>
            添加任职级经历</a>
        <button class="jqOpenViewBtn btn  btn-sm btn-warning"
                data-url="${ctx}/cadreAdminLevel_au"
                data-grid-id="#jqGrid_cadreAdminLevels"
                data-querystr="&cadreId=${param.cadreId}">
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
        </shiro:lacksPermission>
    </shiro:hasPermission>
    <div class="space-4"></div>
    <table id="jqGrid_cadreAdminLevels" data-width-reduce="60" class="jqGrid2"></table>
</c:if>

<script type="text/template" id="dispatch_select_tpl">
    <button class="popupBtn btn {{=(count>0)?'btn-warning':'btn-success'}} btn-xs"
            data-url="${ctx}/cadrePost_addDispatchs?id={{=id}}&cadreId={{=cadreId}}"
            data-width="1000"><i class="fa fa-link"></i>
        任免文件({{=count}})
    </button>
</script>
<script type="text/template" id="dispatch_adminLevel_tpl">
    {{if(isAdmin || hasStart){}}<button class="popupBtn btn btn-xs btn-{{=!hasStart?'success':'warning'}}"
            data-url="${ctx}/cadreAdminLevel_addDispatchs?cls=start&id={{=id}}&cadreId={{=cadreId}}"
            data-width="1000">
        <i class="fa fa-link"></i>
        始任文件{{=!hasStart?'(0)':'(1)'}}
    </button>{{}}}
    {{if(isAdmin || hasEnd){}}<button class="popupBtn btn btn-xs btn-{{=!hasEnd?'success':'warning'}}"
            data-url="${ctx}/cadreAdminLevel_addDispatchs?cls=end&id={{=id}}&cadreId={{=cadreId}}"
            data-width="1000">
        <i class="fa fa-link"></i>
        结束文件{{=!hasEnd?'(0)':'(1)'}}
    </button>{{}}}
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
        $("#view-box .tab-content").loadPage("${ctx}/cadrePost_page?cadreId=${param.cadreId}&type=" + type)
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
            {label: '职务', name: 'post', width: 250, align:'left', cellattr: function (rowId, val, rowObject, cm, rdata) {
                if(rowObject.unitPostId==undefined)
                    return "class='warning'";
            }, frozen: true},
            {label: '职务属性', width: 130, name: 'postType', formatter: $.jgrid.formatter.MetaType, frozen: true},
            {label: '行政级别', name: 'adminLevel', formatter:$.jgrid.formatter.MetaType, frozen: true},
            {
                label: '是否正职', name: 'postType', formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '--';
                return _cMap.metaTypeMap[cellvalue].boolAttr ? "是" : "否"
            }
            },
            {label: '职务类别', name: 'postClassId', formatter: $.jgrid.formatter.MetaType},
            {
                label: '所在单位', name: 'unitId', formatter: $.jgrid.formatter.unit, width: 250
            },
            {
                label: '任职日期',
                name: 'dispatchCadreRelateBean.last.workTime',
                formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: 'Y-m-d'}
            },
            {
                label: '现任职务年限',
                width: 120,
                name: 'dispatchCadreRelateBean.last.workTime',
                formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue == undefined) return '--';
                    var year = $.yearOffNow(cellvalue);
                    return year == 0 ? "未满一年" : year;
                }
            },
            {
                label: '现职务任职文件',
                width: 150,
                name: 'dispatchCadreRelateBean.last',
                formatter: function (cellvalue, options, rowObject) {
                    if (!cellvalue || cellvalue.id == undefined) return '--';
                    var dispatchCode = cellvalue.dispatchCode;

                    return $.swfPreview(cellvalue.file, cellvalue.fileName, dispatchCode, dispatchCode);
                }
            },
            {
                label: '现任职务始任日期',
                width: 150,
                name: 'dispatchCadreRelateBean.first.workTime',
                formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: 'Y-m-d'}
            },
            {
                label: '现任职务始任年限',
                width: 150,
                name: 'dispatchCadreRelateBean.first.workTime',
                formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue == undefined) return '--';
                    var year = $.yearOffNow(cellvalue);
                    return year == 0 ? "未满一年" : year;
                }
            },
            {
                label: '现任职务始任文件',
                width: 150,
                name: 'dispatchCadreRelateBean.first',
                formatter: function (cellvalue, options, rowObject) {
                    if (!cellvalue || cellvalue.id == undefined) return '--';
                    var dispatchCode = cellvalue.dispatchCode;

                    return $.swfPreview(cellvalue.file, cellvalue.fileName, dispatchCode, dispatchCode);
                }
            },
            {
                label: '干部任免文件',
                name: 'dispatchCadreRelateBean.all',
                formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue == undefined) return '--';
                    var count = cellvalue.length;
                    <shiro:lacksPermission name="${PERMISSION_CADREADMIN}">
                    if(count==0) return ''
                    </shiro:lacksPermission>
                    return _.template($("#dispatch_select_tpl").html().NoMultiSpace())
                    ({id: rowObject.id, cadreId: rowObject.cadreId, count: count});
                },
                width: 120
            },
            {
                label: '是否双肩挑', name: 'isDouble', formatter: function (cellvalue, options, rowObject) {
                return cellvalue ? "是" : "否";
            }
            },
            /*{
                label: '双肩挑单位', name: 'doubleUnitId', width: 150, formatter: function (cellvalue, options, rowObject) {
                if (!rowObject.isDouble) return '--'
                return $.jgrid.formatter.unit(cellvalue)
            }
            }*/
            {
                label: '双肩挑单位', name: 'doubleUnitIds',formatter: function (cellvalue, options, rowObject) {

                if($.trim(cellvalue)=='') return '--'
                return ($.map(cellvalue.split(","), function(unitId){
                    return $.jgrid.formatter.unit(unitId);
                })).join("，")

            }, width: 500, align:'left'}
        ]
    }).jqGrid("setFrozenColumns");

    $("#jqGrid_subCadrePosts").jqGrid({
        <shiro:lacksPermission name="${PERMISSION_CADREADMIN}">
        multiselect:false,
        </shiro:lacksPermission>
        pager: "#jqGridPager_subCadrePosts",
        ondblClickRow: function () {
        },
        height: 120,
        url: '${ctx}/cadrePost_data?${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {
                label: '兼任单位', width: 200, name: 'unitId', formatter: $.jgrid.formatter.unit, frozen: true
            },
            {label: '兼任职务', name: 'post', width: 250, align:'left', cellattr: function (rowId, val, rowObject, cm, rdata) {
                    if(rowObject.unitPostId==undefined)
                        return "class='warning'";
                }, frozen: true},
            {label: '职务级别', name: 'adminLevel', formatter:$.jgrid.formatter.MetaType},
            {
                label: '是否占职数', name: 'isCpc', formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '--';
                return cellvalue ? "是" : "否"
            }
            },
            <shiro:lacksPermission name="${PERMISSION_CADREONLYVIEW}">
            {
                label: '排序', width: 80, formatter: $.jgrid.formatter.sortOrder,
                formatoptions:{grid:'#jqGrid_subCadrePosts', url: "${ctx}/cadrePost_changeOrder"}, frozen: true
            },
            </shiro:lacksPermission>
            {label: '职务属性', width: 120, name: 'postType', formatter: $.jgrid.formatter.MetaType},
            {label: '职务类别', name: 'postClassId', formatter: $.jgrid.formatter.MetaType},
            {
                label: '兼任职务任职日期',
                width: 150,
                name: 'dispatchCadreRelateBean.last.workTime',
                formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: 'Y-m-d'}
            },
            {
                label: '兼任职务年限',
                width: 120,
                name: 'dispatchCadreRelateBean.last.workTime',
                formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue == undefined) return '--';
                    var year = $.yearOffNow(cellvalue);
                    return year == 0 ? "未满一年" : year;
                }
            },
            {
                label: '兼任职务任职文件',
                width: 150,
                name: 'dispatchCadreRelateBean.last',
                formatter: function (cellvalue, options, rowObject) {
                    if (!cellvalue || cellvalue.id == undefined) return '--';
                    var dispatchCode = cellvalue.dispatchCode;

                    return $.swfPreview(cellvalue.file, cellvalue.fileName, dispatchCode, dispatchCode);
                }
            },
            {
                label: '兼任职务始任日期',
                width: 150,
                name: 'dispatchCadreRelateBean.first.workTime',
                formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: 'Y-m-d'}
            },
            {
                label: '兼任职务始任年限',
                width: 150,
                name: 'dispatchCadreRelateBean.first.workTime',
                formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue == undefined) return '--';
                    var year = $.yearOffNow(cellvalue);
                    return year == 0 ? "未满一年" : year;
                }
            },
            {
                label: '兼任职务始任文件',
                width: 150,
                name: 'dispatchCadreRelateBean.first',
                formatter: function (cellvalue, options, rowObject) {
                    if (!cellvalue || cellvalue.id == undefined) return '--';
                    var dispatchCode = cellvalue.dispatchCode;
                    return $.swfPreview(cellvalue.file, cellvalue.fileName, dispatchCode, dispatchCode);
                }
            },
            {
                label: '干部任免文件',
                name: 'dispatchCadreRelateBean.all',
                formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue == undefined) return '--';
                    var count = cellvalue.length;
                    <shiro:lacksPermission name="${PERMISSION_CADREADMIN}">
                    if(count==0) return ''
                    </shiro:lacksPermission>
                    return _.template($("#dispatch_select_tpl").html().NoMultiSpace())
                    ({id: rowObject.id, cadreId: rowObject.cadreId, count: count});
                },
                width: 120
            }
        ],gridComplete:function(){
             <c:if test="${empty subCadrePosts}">
            $("#jqGrid_subCadrePosts").closest(".widget-box").addClass("collapsed")
            </c:if>
        }
    }).jqGrid("setFrozenColumns");

    $(window).triggerHandler('resize.jqGrid4');
    </c:if>
    <c:if test="${type==2}">
    $("#jqGrid_cadreWork").jqGrid({
        <shiro:lacksPermission name="${PERMISSION_CADREADMIN}">
        multiselect:false,
        </shiro:lacksPermission>
        ondblClickRow: function () {
        },
        pager: "#jqGridPager_cadreWork",
        url: '${ctx}/cadreWork_data?fid=-1&isCadre=1&cadreId=${param.cadreId}',
        colModel: [
            {label: '开始日期', name: 'startTime', formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: 'Y.m'}, frozen:true},
            {label: '结束日期', name: 'endTime', formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: 'Y.m'}, frozen:true},
            {label: '工作单位及担任职务（或专技职务）', name: 'detail', width: 380, align:'left', frozen:true},
            {label: '行政级别', name: 'typeId', formatter: $.jgrid.formatter.MetaType, width: 200},
            {label: '工作类型', name: 'workType', formatter: $.jgrid.formatter.MetaType, width: 200},
            {
                label: '所属内设机构', name: 'unitIds',formatter: function (cellvalue, options, rowObject) {

                if($.trim(cellvalue)=='') return '--'
                return ($.map(cellvalue.split(","), function(unitId){
                    return $.jgrid.formatter.unit(unitId);
                })).join("，")

            }, width: 500, align:'left'}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    </c:if>
    <c:if test="${type==3}">
    $("#jqGrid_cadreAdminLevels").jqGrid({
        <shiro:lacksPermission name="${PERMISSION_CADREADMIN}">
        multiselect:false,
        </shiro:lacksPermission>
        pager: null,
        ondblClickRow: function () {
        },
        datatype: "local",
        data:${cm:toJSONArray(cadreAdminLevels)},
        colModel: [
            {label: '行政级别', name: 'adminLevel', formatter:$.jgrid.formatter.MetaType, frozen:true},
            {
                label: '是否现任职级', width: 120, name: 'isNow', formatter: function (cellvalue, options, rowObject) {
                return (rowObject.adminLevel == '${cadre.adminLevel}') ? "是" : "否";
                //return (rowObject.adminLevel == mainCadrePost.adminLevel) ? "是" : "否";
            }, frozen:true
            },
            {
                label: '职级始任日期',
                width: 120,
                name: 'startDispatch.workTime',
                formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: 'Y-m-d'}, frozen:true
            },
            {label: '职级始任职务', width: 200, align:'left', name: 'startDispatchCadre.post', frozen:true},
            {
                label: '职级始任文件',
                width: 150,
                name: 'startDispatch',
                formatter: function (cellvalue, options, rowObject) {
                    if (!cellvalue || cellvalue.id == undefined) return '--';
                    var dispatchCode = cellvalue.dispatchCode;
                    return $.swfPreview(cellvalue.file, cellvalue.fileName, dispatchCode, dispatchCode);
                }
            },
            {
                label: '职级结束日期',
                width: 120,
                name: 'endDispatch.workTime',
                formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: 'Y-m-d'}
            },
            {
                label: '职级结束文件', width: 150, name: 'endDispatch', formatter: function (cellvalue, options, rowObject) {
                if (!cellvalue || cellvalue.id == undefined) return '--';
                var dispatchCode = cellvalue.dispatchCode;
                return $.swfPreview(cellvalue.file, cellvalue.fileName, dispatchCode, dispatchCode);
            }
            },
            {
                label: '任职级年限',
                width: 120,
                name: 'workYear',
                formatter: function (cellvalue, options, rowObject) {
                    //console.log(rowObject.endDispatch)
                    var end;
                    if (rowObject.endDispatch != undefined)
                        end = rowObject.endDispatch.workTime;
                    if (rowObject.adminLevel == mainCadrePost.adminLevel)
                        end = new Date().format("yyyy-MM-dd");
                    if (rowObject.startDispatch == undefined || end == undefined) return '--';

                    var month = $.monthDiff(rowObject.startDispatch.workTime, end);
                    //console.log("month="+month)
                    var year = Math.floor(month / 12);
                    return year == 0 ? "未满一年" : year;
                }
            },
            {
                label: '干部任免文件', name: 'selectDispatch', formatter: function (cellvalue, options, rowObject) {

                var hasStart = rowObject.startDispatchCadreId > 0;
                var hasEnd = rowObject.endDispatchCadreId > 0;
                <shiro:hasPermission name="${PERMISSION_CADREADMIN}">
                var isAdmin = true;
                </shiro:hasPermission>
                <shiro:lacksPermission name="${PERMISSION_CADREADMIN}">
                var isAdmin = false;
                </shiro:lacksPermission>
                return _.template($("#dispatch_adminLevel_tpl").html().NoMultiSpace())
                ({id: rowObject.id, hasStart: hasStart, hasEnd: hasEnd, isAdmin:isAdmin, cadreId: rowObject.cadreId});
            }, width: 250 },
            {label: '备注', name: 'remark', width: 250}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    </c:if>

    function _reload() {
        $("#modal").modal('hide');
        $("#view-box .tab-content").loadPage("${ctx}/cadrePost_page?${cm:encodeQueryString(pageContext.request.queryString)}");
    }

    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>