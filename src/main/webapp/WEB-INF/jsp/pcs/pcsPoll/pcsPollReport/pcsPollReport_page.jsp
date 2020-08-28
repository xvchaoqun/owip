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
            <jsp:include page="/WEB-INF/jsp/pcs/pcsPoll/pcsPollResult/menu.jsp"/>
            <div class="tab-content padding-8 multi-row-head-table" id="pcsPollResult-content">
                <c:set var="_query" value="${not empty param.userId ||not empty param.partyId ||not empty param.branchId ||not empty param.stage || not empty param.code || not empty param.sort}"/>
                <div class="jqgrid-vertical-offset buttons" id="changeType">
                    <shiro:hasPermission name="pcsPollReport:edit">
                        <c:if test="${!pcsPoll.hasReport}">
                            <button data-url="${ctx}/pcs/pcsPollReport?isCandidate=0&type=${type}&pollId=${param.pollId}"
                                    data-title="取消候选人推荐人选"
                                    data-msg="确定取消这{0}名候选人推荐人选？"
                                    data-grid-id="#jqGrid2"
                                    data-callback="_cancelReload"
                                    class="jqBatchBtn btn btn-danger btn-sm">
                                <i class="ace-icon fa fa-times"></i> 取消候选人推荐人选
                            </button>
                        </c:if>

                            <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                                    data-url="${ctx}/pcs/pcsPollReport_data?pollId=${param.pollId}&type=${type}"
                                    data-grid-id="#jqGrid2"
                                    data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果"><i class="fa fa-download"></i>
                                导出${PCS_USER_TYPE_MAP.get(type)}统计结果
                            </button>
                    </shiro:hasPermission>
                    <div style="font-size: 14pt;float: left;padding-right: 20px">
                    <input type="checkbox" class="big" name="type" id="${PCS_USER_TYPE_DW}" value="${PCS_USER_TYPE_DW}"> 党委委员（${dwCount}）
                    <input type="checkbox" class="big"  name="type" id="${PCS_USER_TYPE_JW}" value="${PCS_USER_TYPE_JW}"> 纪委委员（${jwCount}）
                    <c:if test="${stage!=PCS_POLL_THIRD_STAGE}">
                        <input type="checkbox" class="big" name="type" id="${PCS_USER_TYPE_PR}" value="${PCS_USER_TYPE_PR}"> 代表（${prCount}）
                    </c:if>
                        </div>
                    <script> $("#changeType input[id=${type}]").prop("checked",'true'); </script>
                </div>

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
                                <div class="form-group">
                                    <label>推荐人</label>
                                    <select data-rel="select2-ajax"
                                            data-ajax-url="${ctx}/sysUser_selects"
                                            name="userId" data-placeholder="请输入账号或姓名或学工号">
                                        <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                    </select>
                                </div>
                                </div>
                                <div class="clearfix form-actions center">
                                    <a class="jqSearchBtn btn btn-default btn-sm"
                                       data-url="${ctx}/pcs/pcsPollResult?pollId=${param.pollId}&type=${type}&cls=${cls}"
                                       data-target="#body-content-view"
                                       data-form="#searchForm2"><i class="fa fa-search"></i> 查找</a>
                                    <c:if test="${_query}">&nbsp;
                                        <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                data-url="${ctx}/pcs/pcsPollResult?pollId=${param.pollId}&type=${type}&cls=${cls}"
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
    function _cancelReload(){
        SysMsg.success('取消成功。',function(){
            $("#body-content-view").loadPage("${ctx}/pcs/pcsPollResult?pollId=${param.pollId}&cls=${cls}&type=${type}");
        })
    }

    $("#jqGrid2").jqGrid({
        multiselect:true,
        rownumbers:true,
        pager: "jqGridPager2",
        url: '${ctx}/pcs/pcsPollReport_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
                { label: '学工号',name: 'code',width:120},
                { label: '姓名',name: 'realname'},
                { label: '所在单位',name: 'unit',width:350, align:'left'},
            { label: '推荐提名<br/>党员数',name: 'ballot'},
            { label: '推荐提名<br/>正式党员数',name: 'positiveBallot'},
            { label: '推荐提名<br/>预备党员数',name: 'growBallot'},
            <c:if test="${stage!=PCS_POLL_FIRST_STAGE}">
                { label: '不同意票数',name: 'disagreeBallot'},
                { label: '弃权票数',name: 'abstainBallot'/*, formatter: function (cellvalue, options, rowobject) {
                    var finishCount = ${pcsPoll.inspectorFinishNum};
                    return finishCount-rowobject.ballot-rowobject.disagreeBallot;
                }*/},
            </c:if>
            {hidden:true, key: true, name: 'userId'}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $('[data-rel="tooltip"]').tooltip();

    $("#changeType input[type=checkbox]").click(function () {
        $("#body-content-view").loadPage("${ctx}/pcs/pcsPollResult?pollId=${param.pollId}&cls=${cls}&type="+$(this).val());
    })
</script>