<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row rownumbers">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.year ||not empty param.name || not empty param.code || not empty param.sort}"/>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="cetProject:edit">
                    <button class="popupBtn btn btn-info btn-sm"
                            data-url="${ctx}/cet/cetProject_au">
                        <i class="fa fa-plus"></i> 添加</button>
                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/cet/cetProject_au"
                       data-grid-id="#jqGrid"
                       ><i class="fa fa-edit"></i>
                        修改</button>
                </shiro:hasPermission>
                <shiro:hasPermission name="cetProject:del">
                    <button data-url="${ctx}/cet/cetProject_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
                <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-url="${ctx}/cet/cetProject_data"
                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出</button>
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
                            <label>年度</label>
                            <input class="form-control date-picker" placeholder="请选择年份"
                                   name="year" type="text" style="width: 100px;"
                                   data-date-format="yyyy" data-date-min-view-mode="2"
                                   value="${param.year}"/>
                        </div>
                        <div class="form-group">
                            <label>培训班名称</label>
                            <input class="form-control search-query" name="name" type="text" value="${param.name}"
                                   placeholder="请输入培训班名称">
                        </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                        data-url="${ctx}/cet/cetProject"
                                        data-target="#page-content"
                                        data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="resetBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/cet/cetProject"
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
    $.register.date($('.date-picker'));
    $("#jqGrid").jqGrid({
        rownumbers:true,
        url: '${ctx}/cet/cetProject_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '详情', name: '_detail', width:'80', formatter: function (cellvalue, options, rowObject) {
                return ('<button class="openView btn btn-success btn-xs" ' +
                'data-url="${ctx}/cet/cetProject_detail?projectId={0}"><i class="fa fa-search"></i> 详情</button>')
                        .format(rowObject.id);
            }, frozen: true},
            { label: '年度',name: 'year', frozen: true},
            { label: '培训时间',name: 'startDate', width: 200, formatter: function (cellvalue, options, rowObject) {
                return '{0}-{1}'.format($.date(rowObject.startDate, "yyyy-MM-dd"), $.date(rowObject.endDate, "yyyy-MM-dd"))
            }, frozen: true},
            { label: '培训班名称',name: 'name', width: 300, frozen: true},
            {
                label: '培训方案', width: 200, align:'left', formatter: function (cellvalue, options, rowObject) {

                var ret = "";
                var pdfFilePath = rowObject.pdfFilePath;
                if ($.trim(pdfFilePath) != '') {
                    var fileName = (rowObject.fileName || rowObject.id) + (pdfFilePath.substr(pdfFilePath.indexOf(".")));
                    //console.log(fileName + " =" + pdfFilePath.substr(pdfFilePath.indexOf(".")))
                    ret = '<button href="javascript:void(0)" data-url="${ctx}/swf/preview?path={0}&filename={1}"  title="PDF文件预览" class="popupBtn btn btn-xs btn-primary"><i class="fa fa-search"></i> 预览</button>'
                                    .format(encodeURI(pdfFilePath), encodeURI(fileName))
                            + '&nbsp;<button data-url="${ctx}/attach/download?path={0}&filename={1}" title="下载PDF文件" class="linkBtn btn btn-xs btn-warning"><i class="fa fa-file-pdf-o"></i> PDF</button>'
                                    .format(encodeURI(pdfFilePath), encodeURI(fileName));
                }
                var wordFilePath = rowObject.wordFilePath;
                if ($.trim(wordFilePath) != '') {

                    var fileName = (rowObject.fileName || rowObject.id) + (wordFilePath.substr(wordFilePath.indexOf(".")));
                    ret += '&nbsp;<button data-url="${ctx}/attach/download?path={0}&filename={1}"  title="下载WORD文件" class="linkBtn btn btn-xs btn-success"><i class="fa fa-file-word-o"></i> DOC</button>'
                            .format(encodeURI(wordFilePath), encodeURI(fileName));
                }
                return ret;
            }
            },
            { label: '总学时',name: 'period'},
            { label: '参训人数',name: 'objCount'},
            { label: '状态',name: 'status'},
            { label: '备注',name: 'remark', width: 300}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>