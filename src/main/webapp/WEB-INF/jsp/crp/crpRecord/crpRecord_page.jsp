<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/crpRecord_page"
             data-url-export="${ctx}/crpRecord_data"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query"
                   value="${not empty param.cadreId  ||not empty param.isDeleted || not empty param.code || not empty param.sort}"/>
            <div class="tabbable">
                <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                    <li class="<c:if test="${!isFinished}">active</c:if>">
                        <a href="?isFinished=0"><i class="fa fa-circle-o-notch fa-spin"></i> 正在挂职</a>
                    </li>
                    <li class="<c:if test="${isFinished}">active</c:if>">
                        <a href="?isFinished=1"><i class="fa fa-history"></i> 挂职结束</a>
                    </li>
                </ul>

                <div class="tab-content">
                    <div id="home4" class="tab-pane in active">
                        <div class="jqgrid-vertical-offset buttons">
                            <shiro:hasPermission name="crpRecord:edit">
                                <c:if test="${!isFinished}">
                                    <a class="popupBtn btn btn-info btn-sm"
                                       data-url="${ctx}/crpRecord_au?type=${param.type}"><i class="fa fa-plus"></i> 添加</a>
                                </c:if>
                                <a class="jqOpenViewBtn btn btn-primary btn-sm"
                                   data-url="${ctx}/crpRecord_au"
                                   data-grid-id="#jqGrid"
                                   data-querystr="&type=${param.type}"><i class="fa fa-edit"></i>
                                    修改</a>
                            </shiro:hasPermission>
                            <a class="jqOpenViewBtn btn btn-warning btn-sm"
                               data-url="${ctx}/crpRecord_finish"
                               data-grid-id="#jqGrid"><i class="fa fa-power-off"></i>
                                挂职结束</a>
                            <shiro:hasPermission name="crpRecord:del">
                                <button data-url="${ctx}/crpRecord_batchDel"
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
                                        <input name="type" type="hidden" value="${param.type}"/>
                                        <input name="isFinished" type="hidden" value="${isFinished}"/>

                                        <div class="form-group">
                                            <label>关联干部</label>
                                            <input class="form-control search-query" name="cadreId" type="text"
                                                   value="${param.cadreId}"
                                                   placeholder="请输入关联干部">
                                        </div>

                                        <div class="form-group">
                                            <label>是否删除</label>
                                            <input class="form-control search-query" name="isDeleted" type="text"
                                                   value="${param.isDeleted}"
                                                   placeholder="请输入是否删除">
                                        </div>
                                        <div class="clearfix form-actions center">
                                            <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i>
                                                查找</a>

                                            <c:if test="${_query}">&nbsp;
                                                <button type="button" class="resetBtn btn btn-warning btn-sm"
                                                        data-querystr="type=${param.type}&isFinished=${isFinished}">
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
<c:if test="${param.type==CES_TEMP_POST_TYPE_OUT}">
    <c:set var="unitCodeOther" value="mt_temppost_out_unit_other"/>
    <c:set var="postCodeOther" value="mt_temppost_out_post_other"/>
</c:if>
<c:if test="${param.type==CES_TEMP_POST_TYPE_IN}">
    <c:set var="unitCodeOther" value="mt_temppost_in_unit_other"/>
    <c:set var="postCodeOther" value="mt_temppost_in_post_other"/>
</c:if>
<c:if test="${param.type==CES_TEMP_POST_TYPE_TRANSFER}">
    <c:set var="unitCodeOther" value="mt_temppost_transfer_unit_other"/>
    <c:set var="postCodeOther" value="mt_temppost_transfer_post_other"/>
</c:if>
<script>
    $("#jqGrid").jqGrid({
        url: '${ctx}/crpRecord_data?type=${param.type}&isFinished=${isFinished}&callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {
                label: '姓名', name: 'realname', width: 120, formatter: function (cellvalue, options, rowObject) {
                if(rowObject.type=='${CES_TEMP_POST_TYPE_TRANSFER}'){
                    return cellvalue;
                }
                return '<a href="javascript:;" class="openView" data-url="${ctx}/cadre_view?cadreId={0}">{1}</a>'
                        .format(rowObject.cadre.id, rowObject.cadre.realname);
            }, frozen: true
            },
            {label: '是否现任干部', name: 'isPresentCadre', formatter: $.jgrid.formatter.TRUEFALSE},
            {label: '时任职务', name: 'presentPost', width: 350},
            {
                label: '委派单位', name: 'toUnitType', formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '-';
                return _cMap.metaTypeMap[cellvalue].name +
                        ((cellvalue == '${cm:getMetaTypeByCode(unitCodeOther).id}') ? ("：" + rowObject.toUnit) : "");
            }, width: 150
            },
            {
                label: '挂职类别', name: 'tempPostType', formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '-';
                return _cMap.metaTypeMap[cellvalue].name +
                        ((cellvalue == '${cm:getMetaTypeByCode(postCodeOther).id}') ? ("：" + rowObject.tempPost) : "");
            }, width: 150
            },
            {label: '挂职单位及所任职务', name: 'title', width: 200},
            {label: '挂职开始时间', name: 'startDate', width: 120, formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            <c:if test="${!isFinished}">
            {label: '挂职拟结束时间', name: 'endDate', width: 120, formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            </c:if>
            <c:if test="${isFinished}">
            {label: '挂职实际结束时间', name: 'realEndDate', width: 150, formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            </c:if>
            {label: '备注', name: 'remark', width: 300}
        ]
    }).jqGrid("setFrozenColumns").on("initGrid", function () {
        $(window).triggerHandler('resize.jqGrid');
    })
    _initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>