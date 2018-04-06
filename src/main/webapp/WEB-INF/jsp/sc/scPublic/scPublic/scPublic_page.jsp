<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12 rownumbers">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/sc/scPublic"
             data-url-export="${ctx}/sc/scPublic_data"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query"
                   value="${not empty param.year ||not empty param.committeeId || not empty param.code || not empty param.sort}"/>
            <div class="tabbable">
                <jsp:include page="menu.jsp"/>
                <div class="tab-content">
                    <div id="home4" class="tab-pane in active">
                        <div class="jqgrid-vertical-offset buttons">
                            <shiro:hasPermission name="scPublic:edit">
                                <c:if test="${cls==1}">
                                <a class="openView btn btn-info btn-sm" data-url="${ctx}/sc/scPublic_au"><i
                                        class="fa fa-plus"></i> 添加</a>
                                </c:if>
                                <a class="jqOpenViewBtn btn btn-primary btn-sm"
                                   data-url="${ctx}/sc/scPublic_au"
                                   data-grid-id="#jqGrid"
                                   data-open-by="page"
                                   ><i class="fa fa-edit"></i>
                                    修改</a>
                            </shiro:hasPermission>
                            <c:if test="${cls==1}">
                            <button data-url="${ctx}/sc/scPublic_finish"
                                    data-title="结束公示"
                                    data-msg="确定结束公示（已确认）？"
                                    data-grid-id="#jqGrid"
                                    class="jqBatchBtn btn btn-warning btn-sm">
                                <i class="fa fa-dot-circle-o"></i> 结束公示
                            </button>
                            </c:if>
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
                                            <label>年度</label>
                                            <input class="form-control search-query" name="year" type="text"
                                                   value="${param.year}"
                                                   placeholder="请输入年度">
                                        </div>
                                        <div class="form-group">
                                            <label>所属党委常委会</label>
                                            <input class="form-control search-query" name="committeeId" type="text"
                                                   value="${param.committeeId}"
                                                   placeholder="请输入所属党委常委会">
                                        </div>
                                        <div class="clearfix form-actions center">
                                            <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i>
                                                查找</a>

                                            <c:if test="${_query}">&nbsp;
                                                <button type="button" class="resetBtn btn btn-warning btn-sm"
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
    function _reload(){
        $("#modal").modal('hide');
        $("#jqGrid").trigger("reloadGrid");
    }
    $("#jqGrid").jqGrid({
        rownumbers:true,
        url: '${ctx}/sc/scPublic_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '年度', name: 'year', width: 80, frozen: true},
            {
                label: '公示编号', name: '_num', width: 180, formatter: function (cellvalue, options, rowObject) {
                if(rowObject.num==undefined) return '-'
                var _num = "公示[{0}]{1}号".format(rowObject.year, rowObject.num)
                if(rowObject.pdfFilePath==undefined) return _num;
                return $.swfPreview(rowObject.pdfFilePath, _num);
            }, frozen: true},
            {
                label: '公示文件', width: 200, formatter: function (cellvalue, options, rowObject) {

                var _num = "公示[{0}]{1}号".format(rowObject.year, rowObject.num)
                var ret = "-";
                var pdfFilePath = rowObject.pdfFilePath;
                if ($.trim(pdfFilePath) != '') {
                    //console.log(fileName + " =" + pdfFilePath.substr(pdfFilePath.indexOf(".")))
                    ret = '<button data-url="${ctx}/attach/download?path={0}&filename={1}" title="下载PDF文件" class="linkBtn btn btn-xs btn-warning"><i class="fa fa-file-pdf-o"></i> PDF</button>'
                                    .format(encodeURI(pdfFilePath), _num);
                }
                var wordFilePath = rowObject.wordFilePath;
                if ($.trim(wordFilePath) != '') {

                    ret += '&nbsp;<button data-url="${ctx}/attach/download?path={0}&filename={1}"  title="下载WORD文件" class="linkBtn btn btn-xs btn-success"><i class="fa fa-file-word-o"></i> DOC</button>'
                            .format(encodeURI(wordFilePath), _num);
                }
                return ret;
            }
            },
            {
                label: '党委常委会编号', name: '_num', width: 180, formatter: function (cellvalue, options, rowObject) {
                //console.log(rowObject.holdDate)
                var _num = "党委常委会[{0}]号".format($.date(rowObject.holdDate, "yyyyMMdd"))
                return _num;
            }, frozen: true},
            {label: '党委常委会日期', name: 'holdDate', width: 120, formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {label: '公示时间', name: '_publicDate', width: 220, formatter: function (cellvalue, options, rowObject) {
                if(rowObject.publicStartDate==undefined) return '-'
                return $.date(rowObject.publicStartDate, "yyyy-MM-dd") + "至" + $.date(rowObject.publicEndDate, "yyyy-MM-dd")
            }},
            {label: '发布时间', name: 'publishDate', width: 120, formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {label: '状态', name: 'isFinished', formatter: $.jgrid.formatter.TRUEFALSE,
                formatoptions: {on: '公示结束', off:'正在公示'}},
            <c:if test="${cls==2}">
            {label: '确认', name: 'isConfirmed', formatter: function (cellvalue, options, rowObject) {
                if(cellvalue) return '已确认'
                return ('<button class="confirm btn btn-primary btn-xs" ' +
                        'data-url="${ctx}/sc/scPublic_confirm?ids[]={0}" data-callback="_reload" data-msg="确认公示结束?">'+
                        '<i class="fa fa-check-square-o"></i> 确认</button>').format(rowObject.id);
            }}
            </c:if>
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>