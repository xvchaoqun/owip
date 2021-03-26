<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="widget-box transparent">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:;" class="hideView btn btn-xs btn-success">
                <i class="ace-icon fa fa-backward"></i>
                返回</a>
        </h4>

        <div class="widget-toolbar no-border">
            <ul class="nav nav-tabs">
                <li class="active">
                    <a href="javascript:;">公示详情</a>
                </li>
            </ul>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main">
            <div class="tab-content" style="padding-bottom: 0;padding-top: 0">
                <c:set var="_query"
                       value="${not empty param.publicId || not empty param.code || not empty param.sort}"/>
                <div class="jqgrid-vertical-offset buttons">
                    <a class="jqOpenViewBtn btn btn-primary btn-sm"
                           data-url="${ctx}/sc/scPublicUser_selectScRecord" data-width="1050"
                           data-grid-id="#jqGrid2"><i class="fa fa-edit"></i>
                            对应的选任纪实</a>
                    <%--<a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                       data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                        <i class="fa fa-download"></i> 导出</a>--%>
                </div>
                <%--<div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
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
                                    <label>所属公示</label>
                                    <input class="form-control search-query" name="publicId" type="text"
                                           value="${param.publicId}"
                                           placeholder="请输入所属公示">
                                </div>
                                <div class="clearfix form-actions center">
                                    <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>

                                    <c:if test="${_query}">&nbsp;
                                        <button type="button" class="reloadBtn btn btn-warning btn-sm">
                                            <i class="fa fa-reply"></i> 重置
                                        </button>
                                    </c:if>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>--%>
                <div class="space-4"></div>
                <table id="jqGrid2" class="jqGrid2 table-striped"></table>
                <div id="jqGridPager2"></div>
            </div>
        </div>
    </div>
</div>
<script>
    $("#jqGrid2").jqGrid({
        pager:"#jqGridPager2",
        url: '${ctx}/sc/scPublicUser_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '年度', name: 'year', width: 80, frozen: true},
            { label:'工作证号', name: 'cadre.code', frozen: true},
            { label:'姓名', name: 'cadre.realname', frozen: true},
            {label: '原任职务', name: 'originalPost', width: 240,align:'left'},
            { label:'拟任职务', name: 'post', width: 240,align:'left' },
            {
                label: '公示编号', name: '_num', width: 180, formatter: function (cellvalue, options, rowObject) {
                if(rowObject.year==undefined) return '--'
                var _num = rowObject.code;
                if(rowObject.pdfFilePath==undefined) return _num;
                return $.pdfPreview(rowObject.pdfFilePath, _num);
            }},
            {
                label: '公示文件', width: 200, formatter: function (cellvalue, options, rowObject) {

                var _num = rowObject.code
                var ret = "-";
                var pdfFilePath = rowObject.pdfFilePath;
                if ($.trim(pdfFilePath) != '') {
                    //console.log(fileName + " =" + pdfFilePath.substr(pdfFilePath.indexOf(".")))
                    ret = '<button href="javascript:void(0)" data-width="900" data-url="${ctx}/pdf_preview?path={0}&filename={1}"  title="PDF文件预览" class="popupBtn btn btn-xs btn-primary"><i class="fa fa-search"></i> 预览</button>'
                                    .format(pdfFilePath, encodeURI(_num))
                            + '&nbsp;<button data-url="${ctx}/attach_download?path={0}&filename={1}" title="下载PDF文件" class="downloadBtn btn btn-xs btn-warning"><i class="fa fa-file-pdf-o"></i> PDF</button>'
                                    .format(pdfFilePath, encodeURI(_num));
                }
                var wordFilePath = rowObject.wordFilePath;
                if ($.trim(wordFilePath) != '') {

                    ret += '&nbsp;<button data-url="${ctx}/attach_download?path={0}&filename={1}"  title="下载WORD文件" class="downloadBtn btn btn-xs btn-success"><i class="fa fa-file-word-o"></i> DOC</button>'
                            .format(wordFilePath, _num);
                }
                return ret;
            }},
            {label: '党委常委会编号', name: 'scCommittee.code', width: 210, frozen: true},
            {label: '党委常委会日期', name: 'scCommittee.holdDate', width: 120, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
            {label: '发布时间', name: 'publishDate', width: 120, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
            {label: '公示开始时间', name: 'publicStartDate', width: 140,
                formatter: $.jgrid.formatter.date, formatoptions: {srcformat:'Y-m-d H:i:s', newformat: 'Y-m-d H:i'}},
            {label: '公示结束时间', name: 'publicEndDate', width: 140,
                formatter: $.jgrid.formatter.date, formatoptions: {srcformat:'Y-m-d H:i:s', newformat: 'Y-m-d H:i'}},
            {label: '对应的选任纪实', name: 'scRecord.code', width: 200},
            {label: '纪实人员', name: 'recordUser.realname'}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>