<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/pmd/constants.jsp"%>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" class="multi-row-head-table myTableDiv"
                 data-url-page="${ctx}/pmd/pmdBranch"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.hasReport || not empty param.partyId || not empty param.branchId || not empty param.payMonth
             || not empty param.code || not empty param.sort}"/>
            <div class="jqgrid-vertical-offset buttons">

                <c:if test="${cm:attachFileExisted('af_pmd_help')}">
                <a class="popupBtn btn btn-warning btn-sm"
                   data-url="${ctx}/pdf_preview?code=af_pmd_help&np=1"><i class="fa fa-info-circle"></i> 操作说明</a>
                </c:if>
            </div>
            <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
                <div class="widget-header">
                    <h4 class="widget-title">搜索</h4><span class="widget-note">${note_searchbar}</span>

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
                                <label>所在${_p_partyName}</label>
                                <select class="form-control" data-width="250"  data-rel="select2-ajax"
                                        data-ajax-url="${ctx}/party_selects"
                                        name="partyId" data-placeholder="请选择${_p_partyName}">
                                    <option value="${party.id}" delete="${party.isDeleted}">${party.name}</option>
                                </select>
                            </div>
                            <div class="form-group" style="${(empty branch)?'display: none':''}" id="branchDiv">
                                <label>所在党支部</label>
                                <select class="form-control"  data-rel="select2-ajax" data-ajax-url="${ctx}/branch_selects"
                                        name="branchId" data-placeholder="请选择党支部">
                                    <option value="${branch.id}" delete="${branch.isDeleted}">${branch.name}</option>
                                </select>
                            </div>
                            <script>
                                $.register.party_branch_select($("#searchForm"), "branchDiv",
                                        '${cm:getMetaTypeByCode("mt_direct_branch").id}', "${party.id}", "${party.classId}" );
                            </script>
                            <div class="form-group">
                                <label>缴纳月份</label>
                                <div class="input-group" style="width: 120px;">
                                    <input class="form-control date-picker" name="payMonth" type="text"
                                           data-date-format="yyyy-mm"
                                           data-date-min-view-mode="1" value="${param.payMonth}" />
                                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                                </div>
                            </div>
                            <div class="form-group">
                                <label>报送状态</label>
                                <select data-rel="select2" name="hasReport"
                                        data-width="100"
                                        data-placeholder="请选择">
                                    <option></option>
                                    <option value="0">未报送</option>
                                    <option value="1">已报送</option>
                                </select>
                                <script>
                                    $("#searchForm select[name=hasReport]").val("${param.hasReport}")
                                </script>
                            </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>

                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm">
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
        <div id="body-content-view"></div>
    </div>
</div>
<jsp:include page="pmdBranch_colModel.jsp"/>
<script>
    $.register.date($('.date-picker'));
    $("#jqGrid").jqGrid({
        url: '${ctx}/pmd/pmdBranch_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: colModel
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>