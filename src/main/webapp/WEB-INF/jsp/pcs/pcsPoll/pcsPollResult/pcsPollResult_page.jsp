<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="<%=PcsConstants.PCS_POLL_CANDIDATE_PR%>" var="PCS_POLL_CANDIDATE_PR"/>
<c:set value="<%=PcsConstants.PCS_POLL_CANDIDATE_DW%>" var="PCS_POLL_CANDIDATE_DW"/>
<c:set value="<%=PcsConstants.PCS_POLL_CANDIDATE_JW%>" var="PCS_POLL_CANDIDATE_JW"/>
    <ul class="jqgrid-vertical-offset nav nav-tabs padding-12 tab-color-blue background-blue">
        <c:if test="${stage!=PCS_POLL_CANDIDATE_JW}">
            <li class="<c:if test="${param.type==PCS_POLL_CANDIDATE_PR}">active</c:if>">
                <a href="javascript:;" onclick="_changePage(${PCS_POLL_CANDIDATE_PR})">
                    <i class="fa fa-list"></i> 代表（${cm:trimToZero(candidateCountMap.get(PCS_POLL_CANDIDATE_PR))}/${cm:trimToZero(hasCountMap.get(PCS_POLL_CANDIDATE_PR))}）</a>
            </li>
        </c:if>
        <li class="<c:if test="${param.type==PCS_POLL_CANDIDATE_DW}">active</c:if>">
            <a href="javascript:;" onclick="_changePage(${PCS_POLL_CANDIDATE_DW})">
                <i class="fa fa-list"></i> 党委委员（${cm:trimToZero(candidateCountMap.get(PCS_POLL_CANDIDATE_DW))}/${cm:trimToZero(hasCountMap.get(PCS_POLL_CANDIDATE_DW))}）</a>
        </li>
        <li class="<c:if test="${param.type==PCS_POLL_CANDIDATE_JW}">active</c:if>">
            <a href="javascript:;" onclick="_changePage(${PCS_POLL_CANDIDATE_JW})">
                <i class="fa fa-list"></i> 纪委委员（${cm:trimToZero(candidateCountMap.get(PCS_POLL_CANDIDATE_JW))}/${cm:trimToZero(hasCountMap.get(PCS_POLL_CANDIDATE_JW))}）</a>
        </li>
    </ul>
    <div class="tab-content padding-8">
        <c:set var="_query" value="${not empty param.userId}"/>
        <c:if test="${!pcsPoll.hasReport}">
            <div class="jqgrid-vertical-offset buttons">
                <button data-url="${ctx}/pcs/pcsPollResult_cancel?isCandidate=1&type=${param.type}&pollId=${param.pollId}"
                        data-title="设置候选人"
                        data-msg="确定将这{0}条数据设置为候选人？"
                        date-id-name="userId"
                        data-grid-id="#jqGrid2"
                        data-callback="_ReLoadPage"
                        class="jqBatchBtn btn btn-success btn-sm">
                    <i class="fa fa-user"></i> 设置候选人
                </button>
                <button data-url="${ctx}/pcs/pcsPollResult_cancel?isCandidate=0&type=${param.type}&pollId=${param.pollId}"
                        data-title="取消候选人"
                        data-msg="确定取消这{0}条数据的候选人资格？"
                        date-id-name="userId"
                        data-grid-id="#jqGrid2"
                        data-callback="_ReLoadPage"
                        class="jqBatchBtn btn btn-danger btn-sm">
                    <i class="ace-icon fa fa-times"></i> 取消候选人资格
                </button>
                <%--<shiro:hasPermission name="pcsPollResult:edit">
                     <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-url="${ctx}/pcs/pcsPollResult_data"
                   data-search-form-id="#searchForm2"
                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出</button>
                </shiro:hasPermission>--%>
            </div>
        </c:if>
        <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
            <div class="widget-header">
                <h4 class="widget-title">搜索</h4>
                <span class="widget-note">${note_searchbar}</span>
                <div class="widget-toolbar">
                    <a href="#" data-action="collapse">
                        <i class="ace-icon fa fa-chevron-${_query?'up':'down'}"></i>
                    </a>
                </div>
            </div>
            <div class="widget-body">
                <div class="widget-main no-padding">
                    <form class="form-inline search-form" id="searchForm2">
                        <input type="hidden" name="pollId" value="${param.pollId}"/>
                        <input type="hidden" name="type" value="${param.type}"/>
                        <div class="form-group">
                            <label>推荐人</label>
                            <select data-rel="select2-ajax"
                                    data-ajax-url="${ctx}/sysUser_selects"
                                    name="userId" data-placeholder="请输入账号或姓名或学工号">
                                <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                            </select>
                        </div>
                        <div class="clearfix form-actions center">
                            <a class="jqSearchBtn btn btn-default btn-sm"
                               data-url="${ctx}/pcs/pcsPollResult"
                               data-target="#pcsPollResult-content"
                               data-form="#searchForm2"><i class="fa fa-search"></i> 查找</a>
                            <c:if test="${_query}">&nbsp;
                                <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                        data-url="${ctx}/pcs/pcsPollResult?pollId=${param.pollId}&type=${param.type}"
                                        data-target="#pcsPollResult-content">
                                    <i class="fa fa-reply"></i> 重置
                                </button>
                            </c:if>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div class="space-4"></div>
        <table id="jqGrid2" class="jqGrid2 table-striped" data-height-reduce="4"></table>
        <div id="jqGridPager2"></div>
    </div>
<script>
    function _ReLoadPage(){
        $("#body-content-view").loadPage("${ctx}/pcs/pcsPollResult_menu?type=${param.type}&pollId=${param.pollId}");
    }

    $("#jqGrid2").jqGrid({
        multiselect:true,
        rownumbers: true,
        pager: "jqGridPager2",
        url: '${ctx}/pcs/pcsPollResult_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '学工号',name: 'user.code',width:120},
            { label: '推荐人',name: 'user.realname'},
            { label: '得票总数',name: 'supportNum'},
            { label: '提名正式党员数',name: 'positiveBallot', width: 120},
            { label: '提名预备党员数',name: 'growBallot',width:120},
            <c:if test="${stage!=PCS_POLL_CANDIDATE_PR}">
            { label: '不支持票数',name: 'notSupportNum'},
            { label: '弃权票数',name: 'notVoteNum'},
            </c:if>
            { label: '是否是候选人',name: 'isCandidate',formatter: $.jgrid.formatter.TRUEFALSE},
            {hidden:true, key: true, name: 'userId'}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $('#searchForm2 [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();

</script>