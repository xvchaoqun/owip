<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<jsp:include page="/WEB-INF/jsp/cadre/colModels.jsp"/>
<div class="widget-box transparent">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:;" class="hideView btn btn-xs btn-success">
                <i class="ace-icon fa fa-backward"></i>
                返回</a>
        </h4>
        <span class="text text-info bolder" style="cursor: auto;padding-left: 20px;">
            ${unitPost.name}
        </span>
        <div class="widget-toolbar no-border">
            <ul class="nav nav-tabs">
                <c:forEach items="${unitPostLabels}" var="label" varStatus="st">
                    <li class="<c:if test="${cls==cm:toByte(st.count)}">active</c:if>">
                        <a href="javascript:;" class="loadPage" data-load-el="#body-content-view"
                           data-url="${ctx}/unitPost_label?unitPostId=${unitPost.id}&cls=${cm:toByte(st.count)}">
                            <i class="fa fa-tag"></i> ${cm:getMetaType(cm:toInt(label)).name}</a>
                    </li>
                    <c:if test="${cls==cm:toByte(st.count)}">
                        <c:set var="labelId" value="${cm:toInt(label)}"></c:set>
                    </c:if>
                </c:forEach>
            </ul>
        </div>
    </div>
    </div>
    <div class="widget-body">
        <div class="widget-main padding-4">
            <div class="tab-content padding-8">
                <c:set var="_query" value="${not empty param.cadreId ||not empty param.gender ||not empty param.nation
                ||not empty param.startAge||not empty param.endAge||not empty param.startDpAge||not empty param.endDpAge
                ||not empty param.startNowPostAge||not empty param.endNowPostAge
                ||not empty param.startPostMonth||not empty param.endPostMonth
                ||not empty param.startNowLevelAge||not empty param.endNowLevelAge
                ||not empty param._birth||not empty param._cadreGrowTime
                ||not empty param.dpTypes||not empty param.unitIds||not empty param.unitTypes||not empty param.adminLevels
                ||not empty param.maxEdus||not empty param.major ||not empty param.staffTypes ||not empty param.degreeType
                ||not empty param.proPosts ||not empty param.postTypes ||not empty param.proPostLevels
                ||not empty param.isPrincipal ||not empty param.isDouble ||not empty param.hasCrp || not empty param.code
                ||not empty param.leaderTypes  ||not empty param.type
                 ||not empty param.state  ||not empty param.title ||not empty param.workTypes
                 ||not empty param.hasAbroadEdu || not empty param.authorizedTypes}"/>

            <%--  <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="pwUnit:edit">
                    <button class="popupBtn btn btn-info btn-sm"
                            data-url="${ctx}/pw/pwUnit_au?taskId=${param.taskId}">
                        <i class="fa fa-plus"></i> 添加</button>
                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/pw/pwUnit_au"
                       data-grid-id="#jqGrid_unit"><i class="fa fa-edit"></i>
                        修改</button>
                </shiro:hasPermission>
                <shiro:hasPermission name="pwUnit:del">
                    <button data-url="${ctx}/pw/pwUnit_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？<br/>（删除后无法恢复，请谨慎操作！！）"
                            data-grid-id="#jqGrid_unit"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
                <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-url="${ctx}/pw/pwUnit_data"
                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出</button>
            </div>--%>
                <div class="jqgrid-vertical-offset widget-box collapsed hidden-sm hidden-xs">
                    <div class="widget-header">
                        <h4 class="widget-title">搜索</h4><span class="widget-note">${note_searchbar}</span>

                        <div class="widget-toolbar">
                            <a href="javascript:;" data-action="collapse">
                                <i class="ace-icon fa fa-chevron-down"></i>
                            </a>
                        </div>
                    </div>
                    <div class="widget-body" <%--style="position: fixed;z-index: 102"--%>>
                        <div class="widget-main no-padding">
                            <form class="form-inline search-form" id="searchForm">
                                <input type="hidden" name="cols">
                                <input type="hidden" name="sortBy">
                                <input type="hidden" name="status" value="${status}">
                                <div class="columns">
                                    <div class="column">
                                        <label>姓名</label>
                                        <div class="input">
                                            <select data-rel="select2-ajax"
                                                    data-ajax-url="${ctx}/cadre_selects?status=${status}"
                                                    name="cadreId" data-placeholder="请输入账号或姓名或学工号">
                                                <option value="${cadre.id}">${cadre.realname}-${cadre.code}</option>
                                            </select>
                                        </div>
                                    </div>
                                    <jsp:include page="/WEB-INF/jsp/cadre/cadre_searchColumns.jsp"/>
                                </div>
                                <div class="clearfix"></div>

                                <div class="clearfix form-actions center">
                                    <a class="jqSearchBtn btn btn-default btn-sm"
                                       data-url="${ctx}/unitPost_label?unitPostId=${unitPost.id}&cls=${cls}"
                                       data-target="#body-content-view"
                                       data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                    <c:if test="${_query}">&nbsp;
                                        <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                data-url="${ctx}/unitPost_label?unitPostId=${unitPost.id}&cls=${cls}"
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
            <table id="jqGrid_cadre" class="jqGrid2 table-striped"></table>
            <div id="jqGridPager_cadre"></div>
        </div>
    </div>
</div>
</div>

<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>
<script>
    $("#jqGrid_cadre").jqGrid({
        pager:"jqGridPager_cadre",
        rownumbers:true,
        url: '${ctx}/cadre_data?callback=?&labels=${labelId}&status=${status}&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: ${cm:isPermitted("cadre:list")?"colModels.cadre":"colModels.cadre2"}
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid_cadre", "jqGridPager_cadre");
    $('[data-rel="select2"]').select2();
    $.register.user_select($('#searchForm select[name=cadreId]'));
    //$('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    $.register.date($('.date-picker'));
</script>