<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" class="myTableDiv"
                 data-url-page="${ctx}/sc/scCommitteeTopic"
                 data-url-export="${ctx}/sc/scCommitteeTopic_data"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.year ||not empty param.committeeId  ||not empty param.holdDate ||not empty param.name || not empty param.code || not empty param.sort}"/>
                <div class="tabbable">
                    <jsp:include page="../scCommittee/menu.jsp"/>
                    <div class="tab-content">
                        <div class="tab-pane in active">
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="scCommitteeTopic:edit">
                    <a class="popupBtn btn btn-info btn-sm"
                       data-width="900"
                       data-url="${ctx}/sc/scCommitteeTopic_au"><i class="fa fa-plus"></i> 添加</a>
                    <a class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/sc/scCommitteeTopic_au"
                       data-grid-id="#jqGrid"
                       data-width="900"
                       ><i class="fa fa-edit"></i>
                        修改</a>
                </shiro:hasPermission>
                <shiro:hasPermission name="scCommitteeTopic:del">
                    <button data-url="${ctx}/sc/scCommitteeTopic_batchDel"
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
                                <label>编号</label>
                                <select required name="committeeId" data-rel="select2"
                                        data-width="240"
                                        data-placeholder="请选择">
                                    <option></option>
                                    <c:forEach var="scCommittee" items="${scCommittees}">
                                        <option value="${scCommittee.id}">党委常委会[${cm:formatDate(scCommittee.holdDate, "yyyyMMdd")}]号</option>
                                    </c:forEach>
                                </select>
                                <script>
                                    $("#searchForm select[name=committeeId]").val("${param.committeeId}");
                                </script>
                            </div>
                            <div class="form-group">
                                <label>干部小组会日期</label>
                                <input required class="form-control date-picker" name="holdDate"
                                       type="text"
                                       data-date-format="yyyy-mm-dd"
                                       value="${param.holdDate}"/>
                            </div>
                        <div class="form-group">
                            <label>议题名称</label>
                            <input class="form-control search-query" name="name" type="text" value="${param.name}"
                                   placeholder="请输入议题名称">
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
        url: '${ctx}/sc/scCommitteeTopic_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '年份', name: 'year', width: 80, frozen: true},
            {
                label: '编号', name: '_num', width: 180, formatter: function (cellvalue, options, rowObject) {
                //console.log(rowObject.holdDate)
                var _num = "党委常委会[{0}]号".format($.date(rowObject.holdDate, "yyyyMMdd"))
                if($.trim(rowObject.filePath)=='') return _num;
                return $.swfPreview(rowObject.filePath, _num);
            }, frozen: true},
            {label: '党委常委会日期', name: 'holdDate', width: 120, formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            { label: '议题名称',name: 'name', width: 400, align:'left'},
            {
                label: '议题内容', name: '_content', width: 80, formatter: function (cellvalue, options, rowObject) {
                return ('<button class="popupBtn btn btn-link btn-xs" ' +
                'data-url="${ctx}/sc/scCommitteeTopic_content?topicId={0}">查看</button>')
                        .format(rowObject.id);
            }
            },
            { label: '干部选拔任用表决',name: 'hasVote', width:140, formatter: function (cellvalue, options, rowObject) {
                if(cellvalue)
                    return ('<button class="openView btn btn-xs btn-primary" ' +
                'data-url="${ctx}/sc/scCommitteeVote_au?topicId={0}"><i class="fa fa-edit"></i> 编辑</button>')
                        .format(rowObject.id)
                            + ('&nbsp;<button class="loadPage btn btn-success btn-xs" ' +
                            'data-url="${ctx}/sc/scCommittee?cls=3&topicId={0}"><i class="fa fa-search"></i> 查看</button>')
                                    .format(rowObject.id);
                return '-'
            }},
            { label: '其他事项表决',name: 'hasOtherVote', width:140, formatter: function (cellvalue, options, rowObject) {
                if(cellvalue)
                    return ('<button class="popupBtn btn btn-xs btn-primary" ' +
                            'data-url="${ctx}/sc/scCommitteeOtherVote_au?topicId={0}"><i class="fa fa-edit"></i> 编辑</button>')
                                    .format(rowObject.id)
                            + ('&nbsp;<button class="loadPage btn btn-success btn-xs" ' +
                            'data-url="${ctx}/sc/scCommittee?cls=4&topicId={0}"><i class="fa fa-search"></i> 查看</button>')
                                    .format(rowObject.id);
                return '-'
            }},
            { label: '常委总数',name: '_total', formatter:function(){
                return '${committeeMemberCount}';
            }},
            { label: '应参会常委数',name: '_total', formatter: function (cellvalue, options, rowObject) {
                return rowObject.count + rowObject.absentCount;
            }},
            {
                label: '实际参会常委数', name: 'count', width: 120, formatter: function (cellvalue, options, rowObject) {
                if(cellvalue==0) return '-'
                return ('<button class="popupBtn btn btn-link btn-xs" ' +
                'data-url="${ctx}/sc/scCommitteeMember?committeeId={0}&isAbsent=0">{1}</button>')
                        .format(rowObject.committeeId, cellvalue);
            }},
            {
                label: '请假常委数', name: 'absentCount', formatter: function (cellvalue, options, rowObject) {
                if(cellvalue==0) return '-'
                return ('<button class="popupBtn btn btn-link btn-xs" ' +
                'data-url="${ctx}/sc/scCommitteeMember?committeeId={0}&isAbsent=1">{1}</button>')
                        .format(rowObject.committeeId, cellvalue);
            }},
            { label: '列席人',name: 'attendUsers', width: 400,align:'left'},
            {label: '会议记录', name: 'logFile', formatter: function (cellvalue, options, rowObject) {
                if(rowObject.logFile==undefined) return '-';
                return $.swfPreview(rowObject.logFile, '会议记录', '查看');
            }},
            {label: '表决票', name: 'voteFilePath', formatter: function (cellvalue, options, rowObject) {
                if(rowObject.voteFilePath==undefined) return '-';
                return $.swfPreview(rowObject.voteFilePath, '表决票', '查看');
            }},
            { label: '备注',name: 'remark', width:300}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");

    $.register.date($('.date-picker'));
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>