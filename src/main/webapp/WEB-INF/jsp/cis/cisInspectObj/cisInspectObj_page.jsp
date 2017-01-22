<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/cisInspectObj_page"
             data-url-export="${ctx}/cisInspectObj_data"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query"
                   value="${not empty param.cadreId ||not empty param.typeId ||not empty param.inspectDate ||not empty param.seq || not empty param.code || not empty param.sort}"/>
            <div class="tabbable">
                <jsp:include page="/WEB-INF/jsp/cis/menu.jsp"/>
                <div class="tab-content">
                    <div id="home4" class="tab-pane in active rownumbers">
                        <div class="jqgrid-vertical-offset buttons">
                            <shiro:hasPermission name="cisInspectObj:edit">
                                <a class="popupBtn btn btn-info btn-sm" data-url="${ctx}/cisInspectObj_au"><i
                                        class="fa fa-plus"></i> 添加</a>
                                <a class="jqOpenViewBtn btn btn-primary btn-sm"
                                   data-url="${ctx}/cisInspectObj_au"
                                   data-grid-id="#jqGrid"
                                   data-querystr="&"><i class="fa fa-edit"></i>
                                    修改</a>
                                <a class="jqOpenViewBtn btn btn-info btn-sm"
                                   data-url="${ctx}/cisObjInspectors"
                                   data-grid-id="#jqGrid"
                                   data-querystr="&"
                                   data-id-name="objId"><i class="fa fa-edit"></i>
                                    编辑考察组成员</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="cisInspectObj:del">
                                <button data-url="${ctx}/cisInspectObj_batchDel"
                                        data-title="删除"
                                        data-msg="确定删除这{0}条数据？"
                                        data-grid-id="#jqGrid"
                                        class="jqBatchBtn btn btn-danger btn-sm">
                                    <i class="fa fa-trash"></i> 删除
                                </button>
                            </shiro:hasPermission>
                            <a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                               data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                                <i class="fa fa-download"></i> 导出</a>
                        </div>
                        <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
                            <div class="widget-header">
                                <h4 class="widget-title">搜索</h4>

                                <div class="widget-toolbar">
                                    <a href="#" data-action="collapse">
                                        <i class="ace-icon fa fa-chevron-${_query?'up':'down'}"></i>
                                    </a>
                                </div>
                            </div>
                            <div class="widget-body">
                                <div class="widget-main no-padding">
                                    <form class="form-inline search-form" id="searchForm">
                                        <div class="form-group">
                                            <label>考察对象</label>
                                            <input class="form-control search-query" name="cadreId" type="text"
                                                   value="${param.cadreId}"
                                                   placeholder="请输入考察对象">
                                        </div>
                                        <div class="form-group">
                                            <label>考察类型</label>
                                            <input class="form-control search-query" name="typeId" type="text"
                                                   value="${param.typeId}"
                                                   placeholder="请输入考察类型">
                                        </div>
                                        <div class="form-group">
                                            <label>考察日期</label>
                                            <input class="form-control search-query" name="inspectDate" type="text"
                                                   value="${param.inspectDate}"
                                                   placeholder="请输入考察日期">
                                        </div>
                                        <div class="form-group">
                                            <label>编号</label>
                                            <input class="form-control search-query" name="seq" type="text"
                                                   value="${param.seq}"
                                                   placeholder="请输入编号">
                                        </div>
                                        <div class="clearfix form-actions center">
                                            <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i>
                                                查找</a>

                                            <c:if test="${_query}">&nbsp;
                                                <button type="button" class="resetBtn btn btn-warning btn-sm">
                                                    <i class="fa fa-reply"></i> 重置
                                                </button>
                                            </c:if>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <div class="space-4"></div>
                        <table id="jqGrid" class="jqGrid table-striped"></table>
                        <div id="jqGridPager"></div>
                    </div>
                </div>
            </div>
        </div>
        <div id="item-content"></div>
    </div>
</div>
<script>
    $("#jqGrid").jqGrid({
        rownumbers: true,
        url: '${ctx}/cisInspectObj_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '编号', name: 'seq', formatter: function (cellvalue, options, rowObject) {
                var type = _cMap.metaTypeMap[rowObject.typeId].name;
                return type+"["+rowObject.year + "]"+rowObject.seq + "号";

            }, width:180, frozen: true},
            {label: '考察日期', name: 'inspectDate', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}, frozen: true},
            {label: '考察对象', name: 'cadre.user.realname', frozen: true},
            {label: '所在单位及职务', name: 'cadre.title', align: 'left', width: 300},
            {label: '考察主体', name: 'inspectorType', formatter: function (cellvalue, options, rowObject) {
                var type = _cMap.CIS_INSPECTOR_TYPE_MAP[cellvalue];
                if(cellvalue=='${CIS_INSPECTOR_TYPE_OTHER}'){
                    type +="："+rowObject.otherInspectorType;
                }
                return type;
            }, width:200},
            {label: '考察组负责人', name: 'chiefCadre.user.realname', width:120},
            {label: '考察组成员', name: 'inspectors', formatter:function(cellvalue, options, rowObject){

                if(cellvalue==undefined || cellvalue.length==0) return '';
                var names = []
                for(var i in cellvalue){
                    var inspector = cellvalue[i];
                    if(inspector.realname)
                        names.push(inspector.realname)
                }
                return names.join("，")
            }, width:150},
            {label: '谈话人数', name: 'talkUserCount'},
            {label: '考察材料', name: 'summary', formatter: function (cellvalue, options, rowObject) {
                if (rowObject.summary && rowObject.summary != '')
                    return '<a href="javascript:void(0)" class="openView" data-url="${ctx}cisInspectObj_summary?objId={0}">查看</a>'
                                    .format(rowObject.id)
                            + '&nbsp;<a href="${ctx}/cisInspectObj_summary_export?id={0}">导出</a>'
                                    .format(rowObject.id);
                else return '<a href="javascript:void(0)" class="openView" data-url="${ctx}cisInspectObj_summary?objId={0}">编辑</a>'
                        .format(rowObject.id)
            }
            },
            {label: '备注', name: 'remark'}
        ]
    }).jqGrid("setFrozenColumns").on("initGrid", function () {
        $(window).triggerHandler('resize.jqGrid');
    })

    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>