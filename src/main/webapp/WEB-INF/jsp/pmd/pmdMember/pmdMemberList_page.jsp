<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content" class="myTableDiv"
                 data-url-page="${ctx}/pmd/pmdMember"
                 data-url-export="${ctx}/pmd/pmdMember_data"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.userId ||not empty param.hasPay
             || not empty param.isDelay || not empty param.isOnlinePay|| not empty param.partyId}"/>
            <div class="jqgrid-vertical-offset buttons">
                <%--<a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出</a>--%>
                    <button class="jqOpenViewBtn btn btn-info btn-sm"
                            data-url="${ctx}/sysApprovalLog"
                            data-width="800"
                            data-querystr="&displayType=1&hideStatus=1&type=${SYS_APPROVAL_LOG_TYPE_PMD_MEMBER}">
                        <i class="fa fa-history"></i> 操作记录
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
                                <label>所在分党委</label>
                                <select class="form-control" data-width="250"  data-rel="select2-ajax"
                                        data-ajax-url="${ctx}/party_selects?del=0"
                                        name="partyId" data-placeholder="请选择分党委">
                                    <option value="${party.id}" title="${party.isDeleted}">${party.name}</option>
                                </select>
                            </div>
                            <div class="form-group" style="${(empty branch)?'display: none':''}" id="branchDiv">
                                <label>所在党支部</label>
                                <select class="form-control"  data-rel="select2-ajax" data-ajax-url="${ctx}/branch_selects?del=0"
                                        name="branchId" data-placeholder="请选择党支部">
                                    <option value="${branch.id}" title="${branch.isDeleted}">${branch.name}</option>
                                </select>
                            </div>
                            <script>
                                register_party_branch_select($("#searchForm"), "branchDiv",
                                        '${cm:getMetaTypeByCode("mt_direct_branch").id}', "${party.id}", "${party.classId}" );
                            </script>
                            <div class="form-group">
                                <label>姓名</label>
                                <select data-rel="select2-ajax"
                                        data-ajax-url="${ctx}/member_selects?noAuth=1"
                                        name="userId" data-placeholder="请输入账号或姓名或学工号">
                                    <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>缴费状态</label>
                                <select data-rel="select2" name="hasPay"
                                        data-width="100"
                                        data-placeholder="请选择">
                                    <option></option>
                                    <option value="0">未缴费</option>
                                    <option value="1">已缴费</option>
                                </select>
                                <script>
                                    $("#searchForm select[name=hasPay]").val("${param.hasPay}")
                                </script>
                            </div>
                            <div class="form-group">
                                <label>按时/延迟缴费</label>
                                <select data-rel="select2" name="isDelay"
                                        data-width="120"
                                        data-placeholder="请选择">
                                    <option></option>
                                    <option value="0">按时缴费</option>
                                    <option value="1">延迟缴费</option>
                                </select>
                                <script>
                                    $("#searchForm select[name=isDelay]").val("${param.isDelay}")
                                </script>
                            </div>
                            <div class="form-group">
                                <label>缴费方式</label>
                                <select data-rel="select2" name="isOnlinePay"
                                        data-width="120"
                                        data-placeholder="请选择">
                                    <option></option>
                                    <option value="0">现金缴费</option>
                                    <option value="1">线上缴费</option>
                                </select>
                                <script>
                                    $("#searchForm select[name=isOnlinePay]").val("${param.isOnlinePay}")
                                </script>
                            </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>

                                <c:if test="${_query}">&nbsp;
                                    <button type="button"
                                            data-querystr="cls=${cls}"
                                            class="resetBtn btn btn-warning btn-sm">
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
        <div id="item-content"></div>
    </div>
</div>
<jsp:include page="pmdMember_colModel.jsp?type=admin"/>
<script>
    register_user_select($('#searchForm select[name=userId]'));
    $('#searchForm [data-rel="select2"]').select2();
    $("#jqGrid").jqGrid({
        url: '${ctx}/pmd/pmdMember_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel:colModel
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('[data-rel="tooltip"]').tooltip();
</script>