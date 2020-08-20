<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query"
                   value="${not empty param.partyId ||not empty param.branchId || not empty param.code || not empty param.sort}"/>
            <jsp:include page="/WEB-INF/jsp/pcs/pcsParty/menu_pb.jsp"/>
            <div class="space-4"></div>
            <div class="buttons">
                <shiro:hasRole name="${ROLE_SUPER}">
                    <c:if test="${cls==2}">
                        <button data-url="${ctx}/pcs/pcsBranch_exclude"
                                data-title="不参与党代会"
                                data-msg="确定这{0}个党支部不参与党代会？"
                                data-grid-id="#jqGrid"
                                data-querystr="&isDeleted=1"
                                class="jqBatchBtn btn btn-danger btn-sm">
                            <i class="fa fa-times"></i> 不参与党代会的党支部
                        </button>
                    </c:if>
                    <c:if test="${cls==3}">
                        <button data-url="${ctx}/pcs/pcsBranch_exclude"
                                data-title="返回党代会列表"
                                data-msg="确定这{0}个党支部返回党代会列表？"
                                data-grid-id="#jqGrid"
                                data-querystr="&isDeleted=0"
                                class="jqBatchBtn btn btn-success btn-sm">
                            <i class="fa fa-reply"></i> 返回党支部列表
                        </button>
                    </c:if>
                </shiro:hasRole>
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
                                    <label>${_p_partyName}</label>
                                    <select class="form-control" data-width="272" data-rel="select2-ajax"
                                            data-ajax-url="${ctx}/party_selects?auth=1"
                                            name="partyId" data-placeholder="请选择${_p_partyName}">
                                        <option value="${party.id}" delete="${party.isDeleted}">${party.name}</option>
                                    </select>
                                </div>
                                <div class="form-group" style="${(empty branch)?'display: none':''}"
                                     id="branchDiv">
                                    <label>党支部</label>
                                    <select class="form-control" data-rel="select2-ajax"
                                            data-ajax-url="${ctx}/branch_selects?auth=1"
                                            name="branchId" data-placeholder="请选择党支部">
                                        <option value="${branch.id}"
                                                delete="${branch.isDeleted}">${branch.name}</option>
                                    </select>
                                </div>
                                <script>
                                    $.register.party_branch_select($("#searchForm"), "branchDiv",
                                        '${cm:getMetaTypeByCode("mt_direct_branch").id}', "${party.id}", "${party.classId}");
                                </script>
                                <div class="clearfix form-actions center">
                                    <a class="jqSearchBtn btn btn-default btn-sm"
                                       data-url="${ctx}/pcs/pcsBranch?cls=${cls}"
                                       data-target="#page-content"
                                       data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                    <c:if test="${_query}">&nbsp;
                                        <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                data-url="${ctx}/pcs/pcsBranch?cls=${cls}"
                                                data-target="#page-content">
                                            <i class="fa fa-reply"></i> 重置
                                        </button>
                                    </c:if>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <div class="space-4"></div>
            <table id="jqGrid" class="jqGrid table-striped"></table>
            <div id="jqGridPager"></div>
            <div id="body-content-view"></div>
        </div>
    </div>
</div>
<script>
    function _reload() {
        $("#jqGrid").trigger("reloadGrid");
    }

    $("#jqGrid").jqGrid({
        rownumbers: true,
        url: '${ctx}/pcs/pcsBranch_data?callback=?&isDeleted=${cls==2?'0':'1'}&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '所属${_p_partyName}', name: 'partyName', align: 'left', width: 500},
            {label: '党支部名称', name: 'name', width: 300, align: 'left'},
            {label: '党员数量', name: 'memberCount'},
            {label: '正式党员数量', name: 'positiveCount'},
            {label: '学生党员数量', name: 'studentMemberCount'},
            {label: '教师党员数量', name: 'teacherMemberCount'},
            {label: '离退休党员数量', name: 'retireMemberCount', width: 120}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$.register.date($('.date-picker'));
</script>