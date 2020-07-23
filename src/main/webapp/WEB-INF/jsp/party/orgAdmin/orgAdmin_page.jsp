<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="<%=OwConstants.OW_ORG_ADMIN_PARTY%>" var="OW_ORG_ADMIN_PARTY"/>
<c:set value="<%=OwConstants.OW_ORG_ADMIN_BRANCH%>" var="OW_ORG_ADMIN_BRANCH"/>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content">
            <div class="myTableDiv"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
                <c:set var="_query"
                       value="${not empty param.userId||not empty param.classId||not empty param.partyId||not empty param.branchId}"/>
                <div class="tabbable">
                    <c:if test="${type==OW_ORG_ADMIN_PARTY}">
                        <jsp:include page="/WEB-INF/jsp/party/menu.jsp"/>
                    </c:if>
                    <c:if test="${type==OW_ORG_ADMIN_BRANCH}">
                        <jsp:include page="/WEB-INF/jsp/party/branch/menu.jsp"/>
                    </c:if>
                    <div class="tab-content">
                        <div class="tab-pane in active">
                            <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
                                <div class="widget-header">
                                    <h4 class="widget-title">搜索</h4><span class="widget-note">${note_searchbar}</span>
                                    <div class="widget-toolbar">
                                        <a href="javascript:;" data-action="collapse">
                                            <i class="ace-icon fa fa-chevron-${_query?'up':'down'}"></i>
                                        </a>
                                    </div>
                                </div>
                                <div class="widget-body">
                                    <div class="widget-main no-padding">
                                        <form class="form-inline search-form" id="searchForm">
                                            <div class="form-group">
                                                <label>姓名</label>
                                                <div class="input-group">
                                                    <select data-rel="select2-ajax"
                                                            data-ajax-url="${ctx}/sysUser_selects"
                                                            name="userId" data-placeholder="请输入账号或姓名或学工号">
                                                        <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <c:if test="${type==OW_ORG_ADMIN_PARTY}">
                                                <div class="form-group">
                                                    <label>${_p_partyName}类别</label>
                                                    <select name="classId" data-rel="select2" data-placeholder="请选择">
                                                        <option></option>
                                                        <c:import url="/metaTypes?__code=mc_party_class"/>
                                                    </select>
                                                    <script>
                                                        $("#searchForm select[name=classId]").val('${param.classId}');
                                                    </script>
                                                </div>
                                            </c:if>
                                            <div class="form-group">
                                                <label>所在${_p_partyName}</label>
                                                <select class="form-control" data-width="350" data-rel="select2-ajax"
                                                        data-ajax-url="${ctx}/party_selects?auth=1"
                                                        name="partyId" data-placeholder="请选择">
                                                    <option value="${party.id}" delete="${party.isDeleted}">${party.name}</option>
                                                </select>
                                            </div>
                                            <c:if test="${type==OW_ORG_ADMIN_BRANCH}">
                                                <div class="form-group" style="${(empty branch)?'display: none':''}"
                                                     id="branchDiv">
                                                    <label>所在党支部</label>
                                                    <select class="form-control" data-rel="select2-ajax"
                                                            data-ajax-url="${ctx}/branch_selects?auth=1"
                                                            name="branchId" data-placeholder="请选择党支部">
                                                        <option value="${branch.id}" delete="${branch.isDeleted}">${branch.name}</option>
                                                    </select>
                                                </div>
                                                <script>
                                                    $.register.party_branch_select($("#searchForm"), "branchDiv",
                                                        '${cm:getMetaTypeByCode("mt_direct_branch").id}', "${party.id}", "${party.classId}");
                                                </script>
                                            </c:if>

                                            <div class="clearfix form-actions center">
                                                <a class="jqSearchBtn btn btn-default btn-sm"
                                                   data-url="${ctx}/orgAdmin?type=${type}&cls=${cls}"
                                                   data-target="#page-content"
                                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                                <c:if test="${_query}">&nbsp;
                                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                            data-url="${ctx}/orgAdmin?type=${type}&cls=${cls}"
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
                            <table id="jqGrid" class="jqGrid table-striped"></table>
                            <div id="jqGridPager"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="body-content-view"></div>
    </div>
</div>
<script>

    $("#jqGrid").jqGrid({
        url: '${ctx}/orgAdmin_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '学工号', name: 'code', width: 120, frozen: true},
            {label: '姓名', name: 'realname', width: 90, frozen: true},
            {label: '职务', name: 'postId', formatter:$.jgrid.formatter.MetaType},
            <c:if test="${type==OW_ORG_ADMIN_PARTY}">
            {
                label: '所在${_p_partyName}',
                name: 'partyId',
                width: 450,
                align: 'left',
                formatter: function (cellvalue, options, rowObject) {
                    return $.party(rowObject.partyId);
                }
            },
            </c:if>
            <c:if test="${type==OW_ORG_ADMIN_BRANCH}">
            {
                label: '所在党组织',
                name: '_party',
                width: 650,
                align: 'left',
                formatter: function (cellvalue, options, rowObject) {
                    return $.party(rowObject.partyId, rowObject.branchId);
                }
            },
            </c:if>

        ]
    }).jqGrid("setFrozenColumns")
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    <c:if test="${type==OW_ORG_ADMIN_PARTY}">
    $.register.del_select($('#searchForm select[name=partyId]'));
    </c:if>
    $.register.user_select($('#searchForm select[name=userId]'));
    $('[data-rel="select2"]').select2();
</script>