<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<jsp:include page="/WEB-INF/jsp/member/memberCertify/colModels.jsp"/>
<c:set var="MEMBER_POLITICAL_STATUS_MAP" value="<%=MemberConstants.MEMBER_POLITICAL_STATUS_MAP%>"/>
<c:set var="MEMBER_POLITICAL_STATUS_GROW" value="<%=MemberConstants.MEMBER_POLITICAL_STATUS_GROW%>"/>
<c:set var="MEMBER_POLITICAL_STATUS_POSITIVE" value="<%=MemberConstants.MEMBER_POLITICAL_STATUS_POSITIVE%>"/>
<c:set var="JASPER_PRINT_TYPE_MEMBER_CERTIFY" value="<%=SystemConstants.JASPER_PRINT_TYPE_MEMBER_CERTIFY%>"/>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content">
            <div class="myTableDiv" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.fromUnit ||not empty param.toTitle||not empty param.toUnit
            ||not empty param.userId ||not empty param.sn ||not empty param.politicalStatus || not empty param.code
            || not empty param.year || not empty param.partyId || not empty param.branchId}"/>
                <div class="jqgrid-vertical-offset buttons">
                    <shiro:hasPermission name="userMemberCertify:edit">
                        <button class="popupBtn btn btn-info btn-sm"
                                data-url="${ctx}/member/memberCertify_au?apply=1">
                            <i class="fa fa-plus"></i> 申请</button>
                        <button class="jqOpenViewBtn btn btn-primary btn-sm"
                           data-url="${ctx}/member/memberCertify_au?apply=1"
                           data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                            修改</button>
                        <button class="jqOpenViewBtn btn btn-info btn-sm"
                                data-url="${ctx}/member/memberCertify_au?apply=1&reapply=1"
                                data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                            重新申请</button>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="userMemberCertify:del">
                        <button data-url="${ctx}/member/memberCertify_batchDel?apply=1"
                                data-title="删除"
                                data-msg="确定删除这{0}条数据？"
                                data-grid-id="#jqGrid"
                                class="jqBatchBtn btn btn-danger btn-sm">
                            <i class="fa fa-trash"></i> 删除
                        </button>
                    </shiro:hasPermission>
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
                                    <label>年度</label>
                                    <input class="form-control date-picker" placeholder="请选择年份"
                                           name="year" type="text" style="width: 100px;"
                                           data-date-format="yyyy" data-date-min-view-mode="2"
                                           value="${param.year}"/>
                                </div>
                                <div class="form-group">
                                    <label>介绍信编号</label>
                                    <input class="form-control search-query" name="sn" type="text" value="${param.sn}"
                                           placeholder="请输入">
                                </div>
                                <div class="form-group">
                                    <label>原单位</label>
                                    <input class="form-control search-query" name="fromUnit" type="text" value="${param.fromUnit}"
                                           placeholder="请输入">
                                </div>
                                <div class="form-group">
                                    <label>介绍信抬头</label>
                                    <input class="form-control search-query" name="toTitle" type="text" value="${param.toTitle}"
                                           placeholder="请输入">
                                </div>
                                <div class="form-group">
                                    <label>拟去往的工作学习单位</label>
                                    <input class="form-control search-query" name="toUnit" type="text" value="${param.toUnit}"
                                           placeholder="请输入">
                                </div>
                                <div class="clearfix form-actions center">
                                    <a class="jqSearchBtn btn btn-default btn-sm"
                                       data-url="${ctx}/member/memberCertify"
                                       data-target="#page-content"
                                       data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                    <c:if test="${_query}">&nbsp;
                                        <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                data-url="${ctx}/member/memberCertify"
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
        <div id="body-content-view">

        </div>
    </div>
</div>
<script>
    $("#jqGrid").jqGrid({
        rownumbers:true,
        url: '${ctx}/member/memberCertify_data?cls=0&callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: colModels.colModel
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    $.register.date($('.date-picker'));
</script>