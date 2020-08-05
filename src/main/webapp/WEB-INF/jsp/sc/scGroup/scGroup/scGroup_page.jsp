<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" class="myTableDiv multi-row-head-table"
             data-url-page="${ctx}/sc/scGroup"
             data-url-export="${ctx}/sc/scGroup_data"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query"
                   value="${not empty param.year ||not empty param.holdDate || not empty param.code || not empty param.sort}"/>
            <div class="tabbable">
                <jsp:include page="menu.jsp"/>
                <div class="tab-content">
                    <div class="tab-pane in active">
                        <div class="jqgrid-vertical-offset buttons">
                            <shiro:hasPermission name="scGroup:edit">
                                <a class="openView btn btn-info btn-sm" data-url="${ctx}/sc/scGroup_au"><i
                                        class="fa fa-plus"></i> 添加</a>
                                <a class="jqOpenViewBtn btn btn-success btn-sm"
                                   data-url="${ctx}/sc/scGroupTopic_au"
                                   data-open-by="page"
                                        data-id-name="groupId"><i class="fa fa-plus"></i> 添加讨论议题</a>
                                <a class="jqOpenViewBtn btn btn-primary btn-sm"
                                   data-url="${ctx}/sc/scGroup_au"
                                   data-grid-id="#jqGrid"
                                   data-open-by="page"
                                   ><i class="fa fa-edit"></i>
                                    修改</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="scGroup:del">
                                <button data-url="${ctx}/sc/scGroup_batchDel"
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
                                        <div class="form-group">
                                            <label>年份</label>

                                            <div class="input-group" style="width: 150px">
                                                <input required class="form-control date-picker" placeholder="请选择年份"
                                                       name="year"
                                                       type="text"
                                                       data-date-format="yyyy" data-date-min-view-mode="2"
                                                       value="${param.year}"/>
                                                <span class="input-group-addon"> <i
                                                        class="fa fa-calendar bigger-110"></i></span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label>干部小组会日期</label>
                                            <input required class="form-control date-picker" name="holdDate"
                                                   type="text"
                                                   data-date-format="yyyy-mm-dd"
                                                   value="${param.holdDate}"/>
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
        url: '${ctx}/sc/scGroup_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '年份', name: 'year', width: 60, frozen: true},
            {
                label: '编号', name: '_num', width: 210, formatter: function (cellvalue, options, rowObject) {
                //console.log(rowObject.holdDate)
                var _num = "干部小组会〔{0}〕号".format($.date(rowObject.holdDate, "yyyyMMdd"))
                if($.trim(rowObject.filePath)=='') return _num;
                return $.pdfPreview(rowObject.filePath, _num);
            }, frozen: true},
            {label: '干部小组会<br/>日期', name: 'holdDate', width: 95, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
            {label: '议题数量', name: 'topicNum'},

            {label: '议题word版', name: 'wordFilePath', width: 105, formatter: function (cellvalue, options, rowObject) {
                if($.isBlank(cellvalue)) return '--'
                /*var _num = "干部小组会〔{0}〕号".format($.date(rowObject.holdDate, "yyyyMMdd"));*/
                return '&nbsp;<button data-url="${ctx}/sc/scGroup_download?id={0}"  title="下载WORD文件" class="downloadBtn btn btn-xs btn-success"><i class="fa fa-file-word-o"></i> 下载</button>'
                        .format(rowObject.id/*encodeURI(rowObject.wordFilePath), encodeURI(_num)*/);
            }},
            {
                label: '参会人', name: '_participant', width: 90, formatter: function (cellvalue, options, rowObject) {

                return ('<button class="popupBtn btn btn-primary btn-xs" ' +
                'data-url="${ctx}/sc/scGroupParticipant?groupId={0}"><i class="fa fa-search"></i> 查看</button>')
                        .format(rowObject.id);
            }
            },
            {label: '列席人', name: 'attendUsers'},
            {
                label: '查看讨论议题', width: 130, formatter: function (cellvalue, options, rowObject) {

                return '<button class="loadPage btn btn-primary btn-xs" data-url="${ctx}/sc/scGroup?cls=3&groupId={0}"><i class="fa fa-search"></i> 查看</button>'
                        .format(rowObject.id);
            }
            },
            {label: '会议记录', name: 'logFile', width: 90, formatter: function (cellvalue, options, rowObject) {
                if(rowObject.logFile==undefined) return '--';
                return $.pdfPreview(rowObject.logFile, '会议记录', '<button class="btn btn-xs btn-primary"><i class="fa fa-search"></i> 查看</button>');
            }},
            {label: '纪实人员', name: 'recordUser.realname'},
            {label: '备注', name: 'remark', width: 280}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");

    $.register.date($('.date-picker'));
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>