<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" class="myTableDiv"
                 data-url-page="${ctx}/pmd/pmdMember"
                 data-url-export="${ctx}/pmd/pmdMember_data"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.userId ||not empty param.hasPay
             || not empty param.isDelay || not empty param.isSelfPay|| not empty param.partyId}"/>
            <div class="jqgrid-vertical-offset buttons">
                <%--<a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出</a>--%>
                    <button class="openView btn btn-success btn-sm"
                            data-url="${ctx}/pmdMemberHelpPay">
                        <i class="fa fa-rmb"></i> 党费代缴
                    </button>
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
                                <label>姓名</label>
                                <select data-rel="select2-ajax"
                                        data-ajax-url="${ctx}/member_selects?noAuth=1"
                                        name="userId" data-placeholder="请输入账号或姓名或学工号">
                                    <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                </select>
                            </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>

                                <c:if test="${_query}">&nbsp;
                                    <button type="button"
                                            data-querystr="cls=${cls}"
                                            class="reloadBtn btn btn-warning btn-sm">
                                        <i class="fa fa-reply"></i> 重置
                                    </button>
                                </c:if>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="space-4"></div>
            <table id="jqGrid2" class="jqGrid table-striped"></table>
            <div id="jqGridPager"></div>
        </div>
        <div id="body-content-view"></div>
    </div>
</div>
<jsp:include page="pmdMember_colModel.jsp"/>
<script>
    $.register.user_select($('#searchForm select[name=userId]'));
    $('#searchForm [data-rel="select2"]').select2();
    $("#jqGrid2").jqGrid({
        url: '${ctx}/pmd/pmdMember_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel:colModel
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid2", "jqGridPager");
    $('[data-rel="tooltip"]').tooltip();
</script>