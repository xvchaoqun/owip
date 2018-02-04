<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/sc/scCommitteeVote"
             data-url-export="${ctx}/sc/scCommitteeVote_data"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query"
                   value="${not empty param.topicId ||not empty param.cadreId || not empty param.code || not empty param.sort}"/>
            <div class="tabbable">
                <jsp:include page="../scCommittee/menu.jsp"/>
                <div class="tab-content">
                    <div id="home4" class="tab-pane in active">
                        <div class="jqgrid-vertical-offset buttons">
                           <%-- <shiro:hasPermission name="scCommitteeVote:edit">
                                <a class="popupBtn btn btn-info btn-sm" data-url="${ctx}/sc/scCommitteeVote_au"><i
                                        class="fa fa-plus"></i> 添加</a>
                                <a class="jqOpenViewBtn btn btn-primary btn-sm"
                                   data-url="${ctx}/sc/scCommitteeVote_au"
                                   data-grid-id="#jqGrid"
                                   data-querystr="&"><i class="fa fa-edit"></i>
                                    修改</a>
                            </shiro:hasPermission>--%>
                            <shiro:hasPermission name="scCommitteeVote:del">
                                <button data-url="${ctx}/sc/scCommitteeVote_batchDel"
                                        data-title="删除"
                                        data-msg="确定删除这{0}条数据？"
                                        data-grid-id="#jqGrid"
                                        class="jqBatchBtn btn btn-danger btn-sm">
                                    <i class="fa fa-trash"></i> 删除
                                </button>
                            </shiro:hasPermission>
                           <%-- <a class="jqExportBtn btn btn-success btn-sm tooltip-success"
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
                                            <label>议题</label>
                                            <select required name="topicId" data-rel="select2"
                                                    data-width="240"
                                                    data-placeholder="请选择">
                                                <option></option>
                                                <c:forEach var="scCommitteeTopic" items="${scCommitteeTopics}">
                                                    <option value="${scCommitteeTopic.id}">${scCommitteeTopic.name}</option>
                                                </c:forEach>
                                            </select>
                                            <script>
                                                $("#searchForm select[name=topicId]").val("${param.topicId}");
                                            </script>
                                        </div>
                                        <div class="form-group">
                                            <label>所属干部</label>
                                            <select data-rel="select2-ajax" data-ajax-url="${ctx}/cadre_selects"
                                                    name="cadreId" data-placeholder="请输入账号或姓名或学工号">
                                                <option value="${cadre.id}">${cadre.realname}-${cadre.code}</option>
                                            </select>
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
        <div id="item-content"></div>
    </div>
</div>
<script>
    $("#jqGrid").jqGrid({
        url: '${ctx}/sc/scCommitteeVote_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '年份', name: 'year', width: 80, frozen: true},
            {
                label: '编号', name: '_num', width: 180, formatter: function (cellvalue, options, rowObject) {
                //console.log(rowObject.holdDate)
                var _num = "党委常委会[{0}]号".format($.date(rowObject.holdDate, "yyyyMMdd"))
                if($.trim(rowObject.filePath)=='') return _num;
                return $.swfPreview(rowObject.filePath, _num);
            }, frozen: true},
            {label: '党委常委会日期', name: 'holdDate', width: 120,
                formatter: 'date', formatoptions: {newformat: 'Y-m-d'}, frozen: true},
            { label: '议题名称',name: 'name', width: 200, align:'left', frozen: true},
            {
                label: '议题内容', name: '_content', width: 80, formatter: function (cellvalue, options, rowObject) {
                return ('<button class="popupBtn btn btn-link btn-xs" ' +
                'data-url="${ctx}/sc/scCommitteeTopic_content?topicId={0}">查看</button>')
                        .format(rowObject.id);
            }, frozen: true
            },
            { label:'类别', name: 'type', width: 80, formatter:function(cellvalue, options, rowObject){
                return _cMap.DISPATCH_CADRE_TYPE_MAP[cellvalue];
            },frozen:true },
            { label:'工作证号', name: 'user.code'},
            { label:'姓名', name: 'user.realname', formatter: function (cellvalue, options, rowObject) {
                return $.cadre(rowObject.cadreId, cellvalue);
            }},
            {label: '原任职务', name: 'originalPost', width: 150,align:'left'},
            {label: '原任职务任职时间', name: 'originalPostTime', width: 130, formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            { label:'干部类型', name: 'cadreTypeId', formatter: $.jgrid.formatter.MetaType},
            { label:'任免方式', name: 'wayId', formatter: $.jgrid.formatter.MetaType},
            { label:'任免程序', name: 'procedureId', formatter: $.jgrid.formatter.MetaType},
            { label:'职务', name: 'post', width: 150,align:'left' },
            { label:'职务属性', name: 'postId', width: 120 , formatter: $.jgrid.formatter.MetaType},
            { label:'行政级别', name: 'adminLevelId', formatter: $.jgrid.formatter.MetaType},
            { label:'单位类型', name: 'unit.typeId', width: 120, formatter: $.jgrid.formatter.MetaType},
            { label:'所属单位', name: 'unit.name', width: 150 },
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
            {label: '参会同意人数', name: 'aggreeCount'},
            { label: '列席人',name: 'attendUsers'},
            {label: '会议记录', name: 'logFile', formatter: function (cellvalue, options, rowObject) {
                if(rowObject.logFile==undefined) return '-';
                return $.swfPreview(rowObject.logFile, '会议记录', '查看');
            }},
            {label: '备注', name: 'remark', width:300}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");

    register_user_select($('[data-rel="select2-ajax"]'));
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>