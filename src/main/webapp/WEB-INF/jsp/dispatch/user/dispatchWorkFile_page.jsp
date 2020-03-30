<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="myTableDiv"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <div class="tabbable">
                <div class="tab-content">
                    <div class="tab-pane in active">
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
        url: '${ctx}/user/dispatchWorkFile_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '文件名', width: 620, name: 'fileName', align:'left', frozen: true},
            {
                label: '详情', formatter: function (cellvalue, options, rowObject) {

                var ret = "-";
                var pdfFilePath = rowObject.pdfFilePath;
                if ($.trim(pdfFilePath) != '') {
                    var fileName = (rowObject.fileName || rowObject.id) + (pdfFilePath.substr(pdfFilePath.indexOf(".")));
                    ret = ('<button href="javascript:void(0)" ' +
                        'data-url="${ctx}/pdf_preview?path={0}&filename={1}&nd=1&np=1"'
                        +'  title="PDF文件预览" class="popupBtn btn btn-xs btn-primary" data-width="950">' +
                        '<i class="fa fa-search"></i> 查看</button>')
                                    .format(encodeURI(pdfFilePath), encodeURI(fileName));
                }
                return ret;
            }}
        ]
    }).jqGrid("setFrozenColumns")
    $(window).triggerHandler('resize.jqGrid');
    $.register.date($('.date-picker'));
    $.initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>