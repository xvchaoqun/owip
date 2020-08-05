<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.year || not empty param.code || not empty param.sort}"/>
            <jsp:include page="menu.jsp"/>
            <div class="space-4"></div>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="scBorder:edit">
                    <button class="popupBtn btn btn-success btn-sm"
                            data-url="${ctx}/sc/scBorder_au">
                        <i class="fa fa-plus"></i> 添加
                    </button>
                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                            data-url="${ctx}/sc/scBorder_au"
                            data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                        修改
                    </button>
                </shiro:hasPermission>
                <shiro:hasPermission name="scBorder:del">
                    <button data-url="${ctx}/sc/scBorder_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
                <%--<button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                        data-url="${ctx}/sc/scBorder_data"
                        data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出
                </button>--%>
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

                            <div class="form-group">
                            <label>年度</label>
                            <div class="input-group" style="width: 120px">
                                <input class="form-control date-picker" placeholder="请选择"
                                       name="year"
                                       type="text"
                                       data-date-format="yyyy" data-date-min-view-mode="2"
                                       value="${param.year}"/>
                                <span class="input-group-addon"> <i
                                        class="fa fa-calendar bigger-110"></i></span>
                            </div>
                            </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                   data-url="${ctx}/sc/scBorder"
                                   data-target="#page-content"
                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/sc/scBorder"
                                            data-target="#page-content">
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
        <div id="body-content-view"></div>
    </div>
</div>
<script>
    $("#jqGrid").jqGrid({
        rownumbers: true,
        url: '${ctx}/sc/scBorder_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '年度', name: 'year', width: 80},
            {label: '报备编号', name: 'code', width: 250, frozen: true},
            {label: '报备日期', name: 'recordDate', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
            {
                label: '新增表', name: 'newFile', width: 120, formatter: function (cellvalue, options, rowObject) {

                    var ret = "-";
                    var pdfFilePath = rowObject.addFile;
                    if ($.trim(pdfFilePath) != '') {
                        ret = '<button href="javascript:void(0)" data-url="${ctx}/pdf_preview?path={0}&filename={1}"  title="PDF文件预览" class="popupBtn btn btn-xs btn-primary"><i class="fa fa-search"></i> 预览</button>'
                                .format(encodeURI(pdfFilePath), '新增表')
                            + '&nbsp;<button data-url="${ctx}/sc/scBorder_download?id={0}&fileType=1&filename={1}" title="下载PDF文件" class="downloadBtn btn btn-xs btn-warning"><i class="fa fa-file-pdf-o"></i> PDF</button>'
                                .format(rowObject.id, '新增表'/*encodeURI(pdfFilePath),  */);
                    }
                    return ret;
                }
            },
            {
                label: '变更表', name: 'newFile', width: 120, formatter: function (cellvalue, options, rowObject) {

                    var ret = "-";
                    var pdfFilePath = rowObject.changeFile;
                    if ($.trim(pdfFilePath) != '') {
                        ret = '<button href="javascript:void(0)" data-url="${ctx}/pdf_preview?path={0}&filename={1}"  title="PDF文件预览" class="popupBtn btn btn-xs btn-primary"><i class="fa fa-search"></i> 预览</button>'
                                .format(encodeURI(pdfFilePath), '新增表')
                            + '&nbsp;<button data-url="${ctx}/sc/scBorder_download?id={0}&fileType=2&filename={1}" title="下载PDF文件" class="downloadBtn btn btn-xs btn-warning"><i class="fa fa-file-pdf-o"></i> PDF</button>'
                                .format(rowObject.id, '新增表'/*encodeURI(pdfFilePath),  */);
                    }
                    return ret;
                }
            },
            {
                label: '撤销表', name: 'newFile', width: 120, formatter: function (cellvalue, options, rowObject) {

                    var ret = "-";
                    var pdfFilePath = rowObject.deleteFile;
                    if ($.trim(pdfFilePath) != '') {
                        ret = '<button href="javascript:void(0)" data-url="${ctx}/pdf_preview?path={0}&filename={1}"  title="PDF文件预览" class="popupBtn btn btn-xs btn-primary"><i class="fa fa-search"></i> 预览</button>'
                                .format(encodeURI(pdfFilePath), '新增表')
                            + '&nbsp;<button data-url="${ctx}/sc/scBorder_download?id={0}&fileType=3&filename={1}" title="下载PDF文件" class="downloadBtn btn btn-xs btn-warning"><i class="fa fa-file-pdf-o"></i> PDF</button>'
                                .format(rowObject.id, '新增表'/*encodeURI(pdfFilePath),  */);
                    }
                    return ret;
                }
            },
            {
                label: '电子报备', name: 'recordFile', width: 80, formatter: function (cellvalue, options, rowObject) {

                    var ret = "-";
                    var pdfFilePath = rowObject.recordFile;
                    if ($.trim(pdfFilePath) != '') {
                        ret = '<button data-url="${ctx}/sc/scBorder_download?id={0}&fileType=4&filename={1}" title="下载文件" class="downloadBtn btn btn-xs btn-warning"><i class="fa fa-download"></i> 下载</button>'
                                .format(rowObject.id, '电子报备'/*encodeURI(pdfFilePath),  */);
                    }
                    return ret;
                }
            },
            {label: '报备干部', name: '_add', width: 90, formatter: function (cellvalue, options, rowObject) {

                    return ('<button class="openView btn {1} btn-xs" ' +
                        'data-url="${ctx}/sc/scBorder_cadres?borderId={0}"><i class="fa {3}"></i> {2}</button>')
                        .format(rowObject.id,
                        rowObject.itemCount==0?'btn-primary':'btn-success',
                        rowObject.itemCount==0?'编辑':('查看({0})').format(rowObject.itemCount),
                        rowObject.itemCount==0?'fa-edit':'fa-search');
            }},
            {label: '备注', name: 'remark', width: 320, align: 'left'}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    $.register.date($('.date-picker'));
</script>