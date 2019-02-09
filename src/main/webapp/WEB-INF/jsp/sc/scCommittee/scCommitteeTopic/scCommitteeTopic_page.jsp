<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" class="myTableDiv multi-row-head-table"
                 data-url-page="${ctx}/sc/scCommitteeTopic"
                 data-url-export="${ctx}/sc/scCommitteeTopic_data"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.year ||not empty param.committeeId
            ||not empty param.holdDate ||not empty param.name ||not empty param.unitIds || not empty param.code || not empty param.sort}"/>
                <div class="tabbable">
                    <jsp:include page="../scCommittee/menu.jsp"/>
                    <div class="tab-content">
                        <div class="tab-pane in active">
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="scCommitteeTopic:edit">
                    <a class="openView btn btn-success btn-sm"
                       data-open-by="page"
                       data-url="${ctx}/sc/scCommitteeTopic_au"><i class="fa fa-plus"></i> 添加</a>
                    <a class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/sc/scCommitteeTopic_au"
                       data-grid-id="#jqGrid"
                       data-open-by="page"
                       ><i class="fa fa-edit"></i>
                        修改</a>
                </shiro:hasPermission>
                <shiro:hasPermission name="scCommitteeTopic:del">
                    <button data-url="${ctx}/sc/scCommitteeTopic_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
                <%--<a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出</a>--%>
            </div>
            <div class="jqgrid-vertical-offset widget-box collapsed hidden-sm hidden-xs">
                <div class="widget-header">
                    <h4 class="widget-title">搜索</h4>

                    <div class="widget-toolbar">
                        <a href="#" data-action="collapse">
                            <i class="ace-icon fa fa-chevron-down"></i>
                        </a>
                    </div>
                </div>
                <div class="widget-body">
                    <div class="widget-main no-padding">
                        <form class="form-inline search-form" id="searchForm">
                            <input type="hidden" name="cls" value="${cls}">
                            <div class="form-group">
                                <label>年份</label>
                                <div class="input-group" style="width: 150px">
                                    <input required class="form-control date-picker" placeholder="请选择年份"
                                           name="year"
                                           type="text"
                                           data-date-format="yyyy" data-date-min-view-mode="2"
                                           value="${param.year}"/>
                                                <span class="input-group-addon"> <i
                                                        class="fa fa-calendar bigger-110"></i></span>
                                </div>
                            </div>
                            <div class="form-group">
                                <label>编号</label>
                                <select required name="committeeId" data-rel="select2"
                                        data-width="240"
                                        data-placeholder="请选择">
                                    <option></option>
                                    <c:forEach var="scCommittee" items="${scCommittees}">
                                        <option value="${scCommittee.id}">党委常委会[${cm:formatDate(scCommittee.holdDate, "yyyyMMdd")}]号</option>
                                    </c:forEach>
                                </select>
                                <script>
                                    $("#searchForm select[name=committeeId]").val("${param.committeeId}");
                                </script>
                            </div>
                            <div class="form-group">
                                <label>干部小组会日期</label>
                                <input required class="form-control date-picker" name="holdDate"
                                       type="text"
                                       data-date-format="yyyy-mm-dd"
                                       value="${param.holdDate}"/>
                            </div>
                        <div class="form-group">
                            <label>议题名称</label>
                            <input class="form-control search-query" name="name" type="text" value="${param.name}"
                                   placeholder="请输入议题名称">
                        </div>
                            <div class="form-group">
                                <label>涉及单位</label>
                                <select class="multiselect" name="unitIds" multiple="">
                                    <optgroup label="正在运转单位">
                                        <c:forEach items="${runUnits}" var="unit">
                                            <option value="${unit.id}">${unit.name}</option>
                                        </c:forEach>
                                    </optgroup>
                                    <optgroup label="历史单位">
                                        <c:forEach items="${historyUnits}" var="unit">
                                            <option value="${unit.id}">${unit.name}</option>
                                        </c:forEach>
                                    </optgroup>
                                </select>
                            </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>

                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="resetBtn btn btn-warning btn-sm"
                                            data-querystr="cls=${cls}">
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
        <div id="body-content-view"></div>
    </div>
</div>
<jsp:include page="scCommitteeTopic_colModel.jsp"/>
<script>
    $("#jqGrid").jqGrid({
        url: '${ctx}/sc/scCommitteeTopic_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: colModel
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");

    $.register.multiselect($('#searchForm select[name="unitIds"]'), ${cm:toJSONArray(selectedUnitIds)},
        {enableClickableOptGroups: true, enableCollapsibleOptGroups: true});
    $.register.date($('.date-picker'));
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>