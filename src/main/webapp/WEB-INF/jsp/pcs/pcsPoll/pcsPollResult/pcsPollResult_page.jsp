<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/pcs/constants.jsp" %>
<div class="widget-box transparent">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:;" class="hideView btn btn-xs btn-success">
                <i class="ace-icon fa fa-backward"></i>
                返回</a>
        </h4>
    </div>
    <div class="widget-body">
        <div class="widget-main padding-4">
            <jsp:include page="menu.jsp"/>
            <div class="tab-content padding-8 multi-row-head-table" id="pcsPollResult-content">
                <c:set var="_query" value="${not empty param.userId}"/>
                <c:if test="${!pcsPoll.hasReport}">
                    <div class="jqgrid-vertical-offset buttons">
                        <button data-url="${ctx}/pcs/pcsPollReport?isCandidate=1&type=${type}&pollId=${param.pollId}"
                                data-title="候选人推荐人选"
                                data-msg="确定将这{0}名候选人设置为候选人推荐人选？"
                                date-id-name="userId"
                                data-grid-id="#jqGrid2"
                                data-callback="_setReload"
                                class="jqBatchBtn btn btn-success btn-sm">
                            <i class="fa fa-user"></i> 设置为候选人推荐人选
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
                                       data-url="${ctx}/pcs/pcsPollResult?cls=${cls}&pollId=${param.pollId}&type=${param.type}"
                                       data-target="#body-content-view"
                                       data-form="#searchForm2"><i class="fa fa-search"></i> 查找</a>
                                    <c:if test="${_query}">&nbsp;
                                        <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                data-url="${ctx}/pcs/pcsPollResult?cls=${cls}&pollId=${param.pollId}&type=${param.type}"
                                                data-target="#body-content-view">
                                            <i class="fa fa-reply"></i> 重置
                                        </button>
                                    </c:if>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="space-4"></div>
                <table id="jqGrid2" class="jqGrid2 table-striped" data-height-reduce="10"></table>
                <div id="jqGridPager2"></div>
            </div>
        </div>
    </div>
</div>
<script>
    function _setReload(){
        SysMsg.success('设置成功。',function(){
            $("#body-content-view").loadPage("${ctx}/pcs/pcsPollResult?cls=4&type=${type}&pollId=${param.pollId}");
        })
    }

    $("#jqGrid2").jqGrid({
        multiselect:true,
        rownumbers: true,
        pager: "jqGridPager2",
        url: '${ctx}/pcs/pcsPollResult_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '学工号',name: 'user.code',width:120},
            { label: '推荐人',name: 'user.realname'},
            { label: '推荐提名<br/>党员数',name: 'supportNum'},
            { label: '推荐提名<br/>正式党员数',name: 'positiveBallot', width: 120},
            { label: '推荐提名<br/>预备党员数',name: 'growBallot',width:120},
            <c:if test="${stage!=PCS_POLL_FIRST_STAGE}">
            { label: '不同意票数',name: 'notSupportNum'},
            { label: '弃权票数',name: 'notVoteNum', formatter: function (cellvalue, options, rowobject) {
                    var finishCount = ${pcsPoll.inspectorFinishNum};
                    return finishCount-rowobject.supportNum-rowobject.notSupportNum;
                }},
            </c:if>
            {hidden:true, key: true, name: 'userId'}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $('#searchForm2 [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();

</script>