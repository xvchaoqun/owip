<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.type ||not empty param.unitId ||not empty param.userId || not empty param.code || not empty param.sort}"/>
            <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                <li class="active">
                    <a href="javascript:;" class="loadPage"
                       data-url="${ctx}/cadreCompanyList_setting"><i
                            class="fa fa-files-o"></i> 干部兼职管理文件</a>
                </li>
                <li>
                    <a href="javascript:;" class="loadPage"
                       data-load-el="#detail-content" data-callback="$.menu.liSelected"
                       data-url="${ctx}/metaClass_type_list?cls=mc_cadre_company_type"><i
                            class="fa fa-bars"></i> 兼职类型</a>
                </li>
            </ul>
            <div class="space-4"></div>
            <div id="detail-content">
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="cadreCompany:edit">
                    <button class="popupBtn btn btn-info btn-sm"
                            data-url="${ctx}/cadreCompanyFile_au">
                        <i class="fa fa-plus"></i> 添加</button>
                    <button data-url="${ctx}/cadreCompanyFile_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？<br/>（删除后无法恢复，请谨慎操作！！）"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
            </div>
            <div class="space-4"></div>
            <table id="jqGrid" class="jqGrid table-striped"></table>
            <div id="jqGridPager"></div>
            </div>
        </div>
        <div id="body-content-view"></div>
    </div>
</div>
<script>
    $("#jqGrid").jqGrid({
        rownumbers:true,
        url: '${ctx}/cadreCompanyFile_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
                { label: '类型',name: 'type', width:150, formatter: $.jgrid.formatter.TRUEFALSE,
                    formatoptions:{on:'校领导',off:'干部'}},
                { label: '文件名称',name: 'dwf.fileName', width:650, align:'left', formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue==undefined) return '--'
                    return $.trim(cellvalue)
                }},
                <c:if test="${!_query}">
                { label:'排序', width: 80, formatter: $.jgrid.formatter.sortOrder,
                    formatoptions:{url:'${ctx}/cadreCompanyFile_changeOrder'},frozen:true },
                </c:if>
                {
                label: '预览', width: 200, align:'left', formatter: function (cellvalue, options, rowObject) {

                    var ret = "-";
                    var pdfFilePath = rowObject.dwf.pdfFilePath;
                    if ($.trim(pdfFilePath) != '') {
                        var fileName = (rowObject.dwf.fileName || rowObject.dwf.id)+".pdf";
                        //console.log(fileName + " =" + pdfFilePath.substr(pdfFilePath.indexOf(".")))
                        ret = '<button href="javascript:void(0)" data-width="900" data-url="${ctx}/pdf_preview?path={0}&filename={1}"  title="PDF文件预览" class="popupBtn btn btn-xs btn-primary"><i class="fa fa-search"></i> 预览</button>'
                                .format(pdfFilePath, encodeURI(fileName))
                            + '&nbsp;<button data-url="${ctx}/attach_download?path={0}&filename={1}" title="下载PDF文件" class="downloadBtn btn btn-xs btn-warning"><i class="fa fa-file-pdf-o"></i> PDF</button>'
                                .format(pdfFilePath, encodeURI(fileName));
                    }
                    var wordFilePath = rowObject.dwf.wordFilePath;
                    if ($.trim(wordFilePath) != '') {

                        var fileName = (rowObject.dwf.fileName || rowObject.dwf.id) + ".docx";
                        ret += '&nbsp;<button data-url="${ctx}/attach_download?path={0}&filename={1}"  title="下载WORD文件" class="downloadBtn btn btn-xs btn-success"><i class="fa fa-file-word-o"></i> DOC</button>'
                            .format(wordFilePath, encodeURI(fileName));
                    }
                    return ret;
                }
            },
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
</script>