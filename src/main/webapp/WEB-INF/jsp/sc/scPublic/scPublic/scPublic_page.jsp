<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12 rownumbers">

        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/sc/scPublic"
             data-url-export="${ctx}/sc/scPublic_data"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query"
                   value="${not empty param.year ||not empty param.committeeId || not empty param.code || not empty param.sort}"/>
            <div class="tabbable">
               <%-- <jsp:include page="menu.jsp"/>--%>
                <div class="tab-content">
                    <div class="tab-pane in active">
                        <div class="jqgrid-vertical-offset buttons">
                            <shiro:hasPermission name="scPublic:edit">
                                <a class="openView btn btn-info btn-sm" data-url="${ctx}/sc/scPublic_au"><i
                                        class="fa fa-plus"></i> 添加</a>
                                <a class="jqOpenViewBtn btn btn-primary btn-sm"
                                   data-url="${ctx}/sc/scPublic_au"
                                   data-grid-id="#jqGrid"
                                   data-open-by="page"
                                   ><i class="fa fa-edit"></i>
                                    修改</a>
                            </shiro:hasPermission>
                            <button data-url="${ctx}/sc/scPublic_finish"
                                    data-title="结束公示"
                                    data-msg="确认手动结束公示？（系统不会发送提醒）"
                                    data-grid-id="#jqGrid"
                                    class="jqBatchBtn btn btn-success btn-sm">
                                <i class="fa fa-dot-circle-o"></i> 确认结束公示
                            </button>
                            <shiro:hasPermission name="scPublic:del">
                                <button data-url="${ctx}/sc/scPublic_batchDel"
                                        data-title="删除"
                                        data-msg="确定删除这{0}条数据？"
                                        data-grid-id="#jqGrid"
                                        class="jqBatchBtn btn btn-danger btn-sm">
                                    <i class="fa fa-trash"></i> 删除
                                </button>
                            </shiro:hasPermission>
                            <%--<a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                               data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                                <i class="fa fa-download"></i> 导出</a>--%>
                            <span style="padding-left: 15px;" class="red bolder">注：公示自动结束后当天下午6点，系统会自动发送提醒</span>
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
                                        <input type="hidden" name="cls" value="${cls}">
                                        <div class="form-group" style="z-index:1050">
                                            <label>年度</label>
                                            <div class="input-group date"
                                                 data-date-format="yyyy" data-date-min-view-mode="2"
                                                 style="width: 120px;z-index:1050">
                                                <input class="form-control" placeholder="请选择"
                                                       name="year"
                                                       type="text"
                                                       value="${param.year}"/>
                                                <span class="input-group-addon"> <i
                                                        class="fa fa-calendar bigger-110"></i></span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label>所属党委常委会</label>
                                            <select data-rel="select2-ajax"
                                                    data-ajax-url="${ctx}/sc/scCommittee_selects"
                                                    data-width="260" name="committeeId"
                                                    data-placeholder="请选择或输入日期(YYYYMMDD)">
                                                <option value="${scCommittee.id}">${scCommittee.code}</option>
                                            </select>
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
    $.register.ajax_select($('#searchForm select[name=committeeId]'), {maximumInputLength: 8})
    $.register.date($('.input-group.date'));
    function _reload(){
        $("#modal").modal('hide');
        $("#jqGrid").trigger("reloadGrid");
    }
    $("#jqGrid").jqGrid({
        rownumbers:true,
        url: '${ctx}/sc/scPublic_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {
                label: '详情', name: '_detail', width: 80, formatter: function (cellvalue, options, rowObject) {
                return ('<button class="openView btn btn-success btn-xs" ' +
                'data-url="${ctx}/sc/scPublicUser?publicId={0}"><i class="fa fa-search"></i> 详情</button>')
                            .format(rowObject.id);
            }},
            {label: '状态', name: 'isFinished', width: 90, formatter: function (cellvalue, options, rowObject) {
                if(cellvalue){
                  return "公示结束" /*+ ((rowObject.isFinished && !rowObject.isConfirm)?"(未确认)":"")*/
                }else{
                    return '<span class="text-danger bolder"><i class="fa fa-hourglass-half fa-spin"></i> 正在公示<span>';
                }
            }/*, cellattr:function(rowId, val, rowObject, cm, rdata) {
                return (rowObject.isFinished && !rowObject.isConfirm)?"class='danger'":"";
            }*/},
            {label: '年度', name: 'year', width: 80, frozen: true},
            {
                label: '公示编号', name: '_num', width: 180, formatter: function (cellvalue, options, rowObject) {
                if(rowObject.year==undefined) return '--'
                var _num = rowObject.code;
                if($.isBlank(rowObject.pdfFilePath)) return _num;
                return $.pdfPreview(rowObject.pdfFilePath, _num);
            }, frozen: true},
            {
                label: '公示文件', width: 200, formatter: function (cellvalue, options, rowObject) {

                var _num = rowObject.code
                var ret = "-";
                var pdfFilePath = rowObject.pdfFilePath;
                if ($.trim(pdfFilePath) != '') {
                    //console.log(fileName + " =" + pdfFilePath.substr(pdfFilePath.indexOf(".")))
                    ret = '<button href="javascript:void(0)" data-width="900" data-url="${ctx}/pdf_preview?path={0}&filename={1}"  title="PDF文件预览" class="popupBtn btn btn-xs btn-primary"><i class="fa fa-search"></i> 预览</button>'
                                    .format(pdfFilePath, _num)
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
                formatter: $.jgrid.formatter.date, formatoptions: {srcformat:'Y-m-d H:i:s',newformat: 'Y-m-d H:i'}},
            {label: '公示结束时间', name: 'publicEndDate', width: 140,
                formatter: $.jgrid.formatter.date, formatoptions: {srcformat:'Y-m-d H:i:s',newformat: 'Y-m-d H:i'}},
            {label: '纪实人员', name: 'recordUser.realname'},
            /*{label: '确认结束公示', name: 'isConfirmed', formatter: function (cellvalue, options, rowObject) {

                if(!rowObject.isFinished) return '--'

                if(cellvalue) return '已确认'
                return ('<button class="confirm btn btn-primary btn-xs" ' +
                        'data-url="${ctx}/sc/scPublic_confirm?ids={0}" data-callback="_reload" data-msg="确认公示结束?">'+
                        '<i class="fa fa-check-square-o"></i> 确认</button>').format(rowObject.id);
            }}*/
        ],
        rowattr: function(rowData, currentObj, rowId)
        {
            if(currentObj.isFinished && !currentObj.isConfirmed) {
                //console.log(rowData)
                return {'class':'danger'}
            }
        }
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>