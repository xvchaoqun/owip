<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/sc/scAdUse"
             data-url-export="${ctx}/sc/scAdUse_data"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query"
                   value="${not empty param.year ||not empty param.useDate || not empty param.code || not empty param.sort}"/>
            <div class="tabbable">
                <jsp:include page="../scAdArchive/menu.jsp"/>
                <div class="tab-content">
                    <div class="tab-pane in active">
                        <div class="jqgrid-vertical-offset buttons">
                            <shiro:hasPermission name="scAdUse:edit">
                                <a class="popupBtn btn btn-info btn-sm" data-url="${ctx}/sc/scAdUse_au"><i
                                        class="fa fa-plus"></i> 添加</a>
                                <a class="jqOpenViewBtn btn btn-primary btn-sm"
                                   data-url="${ctx}/sc/scAdUse_au"
                                   data-grid-id="#jqGrid"
                                   ><i class="fa fa-edit"></i>
                                    修改</a>
                            </shiro:hasPermission>
                            <a class="jqOpenViewBtn btn btn-success btn-sm"
                               data-url="${ctx}/sc/scAdUse_archive"
                               data-grid-id="#jqGrid"
                               ><i class="fa fa-edit"></i> 正式归档</a>
                            <shiro:hasPermission name="scAdUse:del">
                                <button data-url="${ctx}/sc/scAdUse_batchDel"
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
                                        <input type="hidden" name="cls" value="${cls}">
                                        <div class="form-group">
                                            <label>年份</label>
                                            <input class="form-control search-query" name="year" type="text"
                                                   value="${param.year}"
                                                   placeholder="请输入年份">
                                        </div>
                                        <div class="form-group">
                                            <label>日期</label>
                                            <input class="form-control search-query" name="useDate" type="text"
                                                   value="${param.useDate}"
                                                   placeholder="请输入日期">
                                        </div>
                                        <div class="clearfix form-actions center">
                                            <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i>
                                                查找</a>

                                            <c:if test="${_query}">&nbsp;
                                                <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                        data-querystr="cls=${cls}">
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
        <div id="body-content-view"></div>
    </div>
</div>
<script>
    $("#jqGrid").jqGrid({
        url: '${ctx}/sc/scAdUse_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '年份', name: 'year'},
            {label: '日期', name: 'useDate', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}, frozen: true},
            {label: '使用单位类型', name: 'isOnCampus', width:120, formatter: $.jgrid.formatter.TRUEFALSE,
                formatoptions: {on: '校内单位', off:'校外单位'}},
            {label: '使用单位', name: '_unit', width:200, formatter: function (cellvalue, options, rowObject) {
                if(rowObject.isOnCampus){
                    return $.jgrid.formatter.unit(rowObject.unitId)
                }
                return rowObject.outUnit;
            }},
            {label: '用途', name: 'useage', width:200},
            { label:'工作证号', name: 'cadre.code'},
            { label:'姓名', name: 'cadre.realname', formatter: function (cellvalue, options, rowObject) {
                return $.cadre(rowObject.cadreId, cellvalue);
            }},
            {label: '干部任免审批表', name: '_filePath', width: 180, formatter: function (cellvalue, options, rowObject) {

                var viewBtn = ('<button class="openView btn btn-success btn-xs" ' +
                'data-url="${ctx}/sc/scAdUse_preview?view=1&useId={0}"><i class="fa fa-search"></i> 查看</button>')
                        .format(rowObject.id);
                var editBtn = ('<button class="openView btn btn-primary btn-xs" ' +
                'data-url="${ctx}/sc/scAdUse_preview?useId={0}"><i class="fa fa-edit"></i> 编辑</button>')
                        .format(rowObject.id);
                var exportBtn = ('<button class="linkBtn btn btn-warning btn-xs" ' +
                'data-url="${ctx}/sc/scAdUse_download?useId={0}"><i class="fa fa-download"></i> 导出</button>')
                        .format(rowObject.id);

                if(rowObject.isAdformSaved){
                    return viewBtn + "&nbsp;" + editBtn + "&nbsp;" + exportBtn;
                }

                return editBtn;
            }},
            {label: '正式归档扫描件', name: '_pdf', width: 120, formatter: function (cellvalue, options, rowObject) {

                var str = "";
                if($.trim(rowObject.signFilePath)!='') {
                    str += $.swfPreview(rowObject.signFilePath, "干部任免审批表归档扫描件",
                                    '<button class="btn btn-xs btn-primary"><i class="fa fa-search"></i> 查看</button>')
                            + ('&nbsp;<button class="linkBtn btn btn-warning btn-xs" ' +
                            'data-url="${ctx}/attach/download?path={0}&filename={1}"><i class="fa fa-download"></i> 下载</button>')
                                    .format(rowObject.signFilePath, "干部任免审批表归档扫描件("+ rowObject.realname+")")
                }
                return str;
            }},
            {label: '备注', name: 'remark', width:300}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>