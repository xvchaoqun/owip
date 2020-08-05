<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" class="myTableDiv rownumbers multi-row-head-table"
             data-url-page="${ctx}/sc/scAdArchive"
             data-url-export="${ctx}/sc/scAdArchive_data"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query"
                   value="${not empty param.objId ||not empty param.committeeId ||not empty param.cadreId || not empty param.code || not empty param.sort}"/>
            <div class="tabbable">
                <jsp:include page="menu.jsp"/>
                <div class="tab-content">
                    <div class="tab-pane in active">
                        <div class="jqgrid-vertical-offset buttons">
                            <shiro:hasPermission name="scAdArchive:edit">
                                <a class="popupBtn btn btn-info btn-sm"
                                   data-width="800"
                                   data-url="${ctx}/sc/scAdArchive_au"><i class="fa fa-plus"></i> 添加</a>
                                <a class="jqOpenViewBtn btn btn-success btn-sm"
                                   data-url="${ctx}/sc/scAdArchive_archive"
                                   data-grid-id="#jqGrid"
                                   ><i class="fa fa-edit"></i> 正式归档</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="scAdArchive:del">
                                <button data-url="${ctx}/sc/scAdArchive_batchDel"
                                        data-title="删除"
                                        data-msg="确定删除这{0}条数据？"
                                        data-grid-id="#jqGrid"
                                        class="jqBatchBtn btn btn-danger btn-sm">
                                    <i class="fa fa-trash"></i> 删除
                                </button>
                            </shiro:hasPermission>
                        </div>
                        <div class="jqgrid-vertical-offset widget-box collapsed hidden-sm hidden-xs">
                            <div class="widget-header">
                                <h4 class="widget-title">搜索</h4><span class="widget-note">${note_searchbar}</span>

                                <div class="widget-toolbar">
                                    <a href="#" data-action="collapse">
                                        <i class="ace-icon fa fa-chevron-down"></i>
                                    </a>
                                </div>
                            </div>
                            <div class="widget-body">
                                <div class="widget-main no-padding">
                                    <form class="form-inline search-form" id="searchForm">
                                        <input type="hidden" name="cls" value="${cls}">
                                        <div class="form-group">
                                            <label>党委常委会</label>
                                            <input class="form-control search-query" name="committeeId" type="text"
                                                   value="${param.committeeId}"
                                                   placeholder="请输入党委常委会">
                                        </div>
                                        <div class="form-group">
                                            <label>干部</label>
                                            <input class="form-control search-query" name="cadreId" type="text"
                                                   value="${param.cadreId}"
                                                   placeholder="请输入干部">
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
        rownumbers:true,
        url: '${ctx}/sc/scAdArchive_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '年份', name: 'year', width: 80, frozen: true},
            {
                label: '党委常委会', name: '_num', width: 210, formatter: function (cellvalue, options, rowObject) {
                //console.log(rowObject.holdDate)
                var holdDate = $.date(rowObject.holdDate, "yyyyMMdd");
                var code = "党委常委会〔{0}〕号".format(holdDate)
                /*
                if($.trim(rowObject.committeeFilePath)=='') return _num;
                return $.pdfPreview(rowObject.committeeFilePath, _num);*/

                return ('<a href="javascript:;" class="linkBtn"'
                +'data-url="${ctx}#/sc/scCommittee?year={0}&holdDate={1}"'
                +'data-target="_blank">{2}</a>')
                        .format(rowObject.year, holdDate,code)

            }, frozen: true},
            {label: '党委常委会<br/>日期', name: 'holdDate', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
            { label:'工作证号', name: 'code'},
            { label:'姓名', name: 'realname', formatter: function (cellvalue, options, rowObject) {
                return $.cadre(rowObject.cadreId, cellvalue);
            }},
            {label: '干部任免审批表', name: '_filePath', width: 200, formatter: function (cellvalue, options, rowObject) {

                var viewBtn = ('<button class="openView btn btn-success btn-xs" ' +
                        'data-url="${ctx}/sc/scAdArchive_preview?view=1&archiveId={0}"><i class="fa fa-search"></i> 查看</button>')
                        .format(rowObject.id);
                var editBtn = ('<button class="popupBtn btn btn-primary btn-xs" data-width="1200" ' +
                        'data-url="${ctx}/sc/scAdArchive_selectVotes?cadreId={0}&archiveId={1}"><i class="fa fa-edit"></i> 编辑</button>')
                        .format(rowObject.cadreId, rowObject.id);
                var exportBtn = ('<button class="downloadBtn btn btn-warning btn-xs" ' +
                        'data-url="${ctx}/sc/scAdArchive_download?archiveId={0}"><i class="fa fa-download"></i> 导出</button>')
                        .format(rowObject.id);

                if(rowObject.isAdformSaved){
                    return viewBtn + "&nbsp;" + editBtn + "&nbsp;" + exportBtn;
                }

                return editBtn;
            }},
            {label: '干部任免审批表<br/>归档扫描件', name: '_pdf', width: 120, formatter: function (cellvalue, options, rowObject) {
                var str = "";
                if($.trim(rowObject.signFilePath)!='') {
                    str += $.pdfPreview(rowObject.signFilePath, "干部任免审批表归档扫描件",
                            '<button class="btn btn-xs btn-primary"><i class="fa fa-search"></i> 查看</button>')
                    + ('&nbsp;<button class="downloadBtn btn btn-warning btn-xs" ' +
                    'data-url="${ctx}/sc/scAdArchive_down?id={0}&type=1"><i class="fa fa-download"></i> 下载</button>')
                            .format(rowObject.id/*, "干部任免审批表归档扫描件("+ rowObject.realname+")"*/)
                }
                return str;
            }},
            <shiro:hasPermission name="cisInspectObj:list">
            {label: '干部考察材料', name: '_cisFilePath', width: 180, formatter: function (cellvalue, options, rowObject) {

                if(!rowObject.hasAppoint) return '--'

                var viewBtn = ('<button class="openView btn btn-success btn-xs" ' +
                'data-url="${ctx}/sc/scAdArchive_cisPreview?view=1&archiveId={0}"><i class="fa fa-search"></i> 查看</button>')
                        .format(rowObject.id, rowObject.objId);
                var editBtn = ('<button class="popupBtn btn btn-primary btn-xs" data-width="800" ' +
                'data-url="${ctx}/sc/scAdArchive_selectCisInspectObj?cadreId={0}&archiveId={1}"><i class="fa fa-edit"></i> 编辑</button>')
                        .format(rowObject.cadreId, rowObject.id);
                var exportBtn = ('<button class="downloadBtn btn btn-warning btn-xs" ' +
                'data-url="${ctx}/sc/scAdArchive_cisDownload?archiveId={0}&objId={1}"><i class="fa fa-download"></i> 导出</button>')
                        .format(rowObject.id, rowObject.objId);

                if(rowObject.objId>0){
                    return viewBtn + "&nbsp;" + editBtn + "&nbsp;" + exportBtn;
                }

                return editBtn;
            }},
            </shiro:hasPermission>
            {label: '干部考察材料</br>归档扫描件', name: '_pdf', width: 120, formatter: function (cellvalue, options, rowObject) {

                var str = "";
                if($.trim(rowObject.cisSignFilePath)!='') {
                    str += $.pdfPreview(rowObject.cisSignFilePath, "干部考察报告归档扫描件",
                                    '<button class="btn btn-xs btn-primary"><i class="fa fa-search"></i> 查看</button>')
                            + ('&nbsp;<button class="downloadBtn btn btn-warning btn-xs" ' +
                            'data-url="${ctx}/sc/scAdArchive_down?id={0}&type=2"><i class="fa fa-download"></i> 下载</button>')
                                    .format(rowObject.cisSignFilePath/*, "干部考察报告归档扫描件("+ rowObject.realname+")"*/)
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