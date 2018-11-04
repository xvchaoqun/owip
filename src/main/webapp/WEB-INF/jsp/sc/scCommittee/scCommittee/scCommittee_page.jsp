<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" class="myTableDiv  multi-row-head-table"
                 data-url-page="${ctx}/sc/scCommittee"
                 data-url-export="${ctx}/sc/scCommittee_data"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.year ||not empty param.holdDate || not empty param.code || not empty param.sort}"/>
                <div class="tabbable">
                    <jsp:include page="menu.jsp"/>
                    <div class="tab-content">
                        <div class="tab-pane in active">
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="scCommittee:edit">
                    <a class="openView btn btn-info btn-sm"  data-url="${ctx}/sc/scCommittee_au"><i class="fa fa-plus"></i> 添加</a>
                    <a class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/sc/scCommittee_au"
                       data-grid-id="#jqGrid"
                       data-open-by="page"
                       ><i class="fa fa-edit"></i>
                        修改</a>
                </shiro:hasPermission>
                <a class="jqOpenViewBtn btn btn-success btn-sm"
                   data-url="${ctx}/sc/scCommitteeTopic_au"
                   data-open-by="page"
                   data-id-name="committeeId"><i class="fa fa-plus"></i> 添加议题</a>
                <shiro:hasPermission name="scCommittee:del">
                    <button data-url="${ctx}/sc/scCommittee_batchDel"
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
            <div class="jqgrid-vertical-offset widget-box collapsed hidden-sm hidden-xs">
                <div class="widget-header">
                    <h4 class="widget-title">搜索</h4>

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
                                <label>党委常委会日期</label>
                                <input required class="form-control date-picker" name="holdDate"
                                       type="text"
                                       data-date-format="yyyymmdd"
                                       value="${param.holdDate}"/>
                            </div>

                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>

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
    $("#jqGrid").jqGrid({
        url: '${ctx}/sc/scCommittee_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '年份', name: 'year', width: 60, frozen: true},
            {
                label: '编号', name: 'code', width: 180, formatter: function (cellvalue, options, rowObject) {
                if(cellvalue==undefined) return ''
                if($.trim(rowObject.filePath)=='') return cellvalue;
                return $.swfPreview(rowObject.filePath, cellvalue);
            }, frozen: true},
            {label: '党委常委会<br/>日期', name: 'holdDate', width: 95, formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            { label: '议题数量',name: 'topicNum', width: 70},
            { label: '常委总数',name: 'committeeMemberCount', width: 70},
            { label: '应参会<br/>常委数',name: '_total', width: 60, formatter: function (cellvalue, options, rowObject) {
                return rowObject.count + rowObject.absentCount;
            }},
            {
                label: '实际参会<br/>常委数', name: 'count', width: 70, formatter: function (cellvalue, options, rowObject) {
                if(cellvalue==0) return '-'
                return ('<a href="javascript:;" class="popupBtn bolder" ' +
                'data-url="${ctx}/sc/scCommitteeMember?committeeId={0}&isAbsent=0"><u>{1}</u></a>')
                        .format(rowObject.id, cellvalue);
            }},
            {
                label: '请假<br/>常委数', name: 'absentCount', width: 60, formatter: function (cellvalue, options, rowObject) {
                if(cellvalue==0) return '-'
                return ('<a href="javascript:;" class="popupBtn bolder" ' +
                'data-url="${ctx}/sc/scCommitteeMember?committeeId={0}&isAbsent=1"><u>{1}</u></a>')
                        .format(rowObject.id, cellvalue);
            }},
            { label: '列席人',name: 'attendUsers', width: 140, formatter: function (cellvalue, options, rowObject) {
                if(rowObject.attendUsers==undefined) return '-';
                if(rowObject.attendUsers.length<=8) return cellvalue;
                return rowObject.attendUsers.substring(0,8) + "...";
            },cellattr:function(rowId, val, rowObject, cm, rdata) {
                if(rowObject.attendUsers!=undefined)
                    return "title='"+rowObject.attendUsers+"'";
            }},
            /*{ label: '列席人',name: 'attendUsers', width: 400,align:'left'},*/
            {label: '会议记录', name: 'logFile', width: 80, formatter: function (cellvalue, options, rowObject) {
                if(rowObject.logFile==undefined) return '-';
                return $.swfPreview(rowObject.logFile, '会议记录', '<button class="btn btn-xs btn-primary"><i class="fa fa-search"></i> 查看</button>');
            }},
            {label: '上会PPT', name: 'pptFile', width: 80, formatter: function (cellvalue, options, rowObject) {
                if(rowObject.pptFile==undefined) return '-';
                return ('&nbsp;<button class="linkBtn btn btn-warning btn-xs" ' +
                'data-url="${ctx}/attach/download?path={0}&filename={1}"><i class="fa fa-download"></i> 下载</button>')
                        .format(rowObject.pptFile, rowObject.code+"(上会PPT)")
            }},
            {
                label: '查看<br/>讨论议题', width: 80, formatter: function (cellvalue, options, rowObject) {

                return '<button class="loadPage btn btn-primary btn-xs" data-url="${ctx}/sc/scCommittee?cls=2&committeeId={0}"><i class="fa fa-search"></i> 查看</button>'
                        .format(rowObject.id);
            }
            },
            { label: '备注',name: 'remark', width: 320}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");

    $.register.date($('.date-picker'));
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>