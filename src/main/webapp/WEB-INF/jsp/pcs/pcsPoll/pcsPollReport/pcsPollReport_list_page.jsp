<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/pcs/constants.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.userId ||not empty param.partyId ||not empty param.branchId || not empty param.type}"/>
            <div class="tabbable">
                <jsp:include page="../menu.jsp"/>
                <div class="tab-content multi-row-head-table">
                    <div class="tab-pane in active">
                        <div class="jqgrid-vertical-offset buttons">
                            <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                               data-url="${ctx}/pcs/pcsPollReport_list_data?stage=${stage}"
                               data-search-form-id="#searchForm"
                               data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                                <i class="fa fa-download"></i> 导出</button>
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
                                    <form class="form-inline search-form" id="searchForm">
                                        <div class="form-group">
                                            <label>候选人类型</label>
                                            <select data-rel="select2" name="type" data-placeholder="请选择">
                                                <option></option>
                                                <c:forEach items="${PCS_USER_TYPE_MAP}" var="entry">
                                                    <option value="${entry.key}">${entry.value}</option>
                                                </c:forEach>
                                            </select>
                                            <script> $('#searchForm select[name=type]').val(${param.type}) </script>
                                        </div>
                                        <div class="form-group">
                                            <label>所在${_p_partyName}</label>
                                            <select class="form-control" data-width="350" data-rel="select2-ajax"
                                                    data-ajax-url="${ctx}/party_selects?auth=1&isPcs=1"
                                                    name="partyId" data-placeholder="请选择">
                                                <option value="${party.id}" delete="${party.isDeleted}">${party.name}</option>
                                            </select>
                                        </div>
                                        <div class="form-group" style="${(empty branch)?'display: none':''}"
                                             id="branchDiv">
                                            <label>所在党支部</label>
                                            <select class="form-control" data-rel="select2-ajax"
                                                    data-ajax-url="${ctx}/branch_selects?auth=1&isPcs=1"
                                                    name="branchId" data-placeholder="请选择党支部">
                                                <option value="${branch.id}" delete="${branch.isDeleted}">${branch.name}</option>
                                            </select>
                                        </div>
                                        <script>
                                            $.register.party_branch_select($("#searchForm"), "branchDiv",
                                                '${cm:getMetaTypeByCode("mt_direct_branch").id}', "${party.id}", "${party.classId}");
                                        </script>
                                        <div class="form-group">
                                            <label>推荐人</label>
                                            <select data-rel="select2-ajax"
                                                    data-ajax-url="${ctx}/sysUser_selects"
                                                    name="userId" data-placeholder="请输入推荐人姓名或学工号">
                                                <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                            </select>
                                        </div>
                                        <div class="clearfix form-actions center">
                                            <a class="jqSearchBtn btn btn-default btn-sm"
                                               data-url="${ctx}/pcs/pcsPoll?cls=${cls}&stage=${stage}"
                                               data-target="#page-content"
                                               data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                            <c:if test="${_query}">&nbsp;
                                                <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                        data-url="${ctx}/pcs/pcsPoll?cls=${cls}&stage=${stage}"
                                                        data-target="#page-content">
                                                    <i class="fa fa-reply"></i> 重置
                                                </button>
                                            </c:if>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <div class="space-4"></div>
                        <table id="jqGrid" class="jqGrid table-striped" data-height-reduce="1"></table>
                        <div id="jqGridPager"></div>
                    </div></div></div></div>
        <div id="body-content-view"></div>
    </div>
</div>
<script>
    $("#jqGrid").jqGrid({
        multiselect:false,
        rownumbers:true,
        url: '${ctx}/pcs/pcsPollReport_list_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '学工号',name: 'code',width:120},
            { label: '姓名',name: 'realname'},
            { label: '所在单位',name: 'unit',width:350, align:'left'},
            { label: '候选人类型',name: 'type',formatter: function (cellvalue, options, rowobject) {
                    return _cMap.PCS_USER_TYPE_MAP[cellvalue];
            }},
            { label: '推荐提名<br/>党支部数',name: 'branchNum'},
            { label: '推荐提名<br/>党员数',name: 'supportNum'},
            { label: '推荐提名<br/>正式党员数',name: 'positiveBallot'},
            { label: '推荐提名<br/>预备党员数',name: 'growBallot'},
            <c:if test="${stage!=PCS_POLL_FIRST_STAGE}">
            { label: '不同意票数',name: 'notSupportNum'},
            { label: '弃权票数',name: 'notVoteNum'},
            </c:if>
            {hidden:true, key: true, name: 'userId'},{hidden:true, name: 'type'}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $.register.user_select($('#searchForm select[name=userId]'));
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>
