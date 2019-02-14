<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.year || not empty param.cadreId || not empty param.code || not empty param.sort}"/>
            <jsp:include page="../scBorder/menu.jsp"/>
            <div class="space-4"></div>

            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="scBorderItem:edit">
                    <button class="popupBtn btn btn-success btn-sm"
                            data-url="${ctx}/sc/scBorderItem_au">
                        <i class="fa fa-plus"></i> 添加</button>
                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/sc/scBorderItem_au"
                       data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                        修改</button>
                </shiro:hasPermission>
                <shiro:hasPermission name="scBorderItem:del">
                    <button data-url="${ctx}/sc/scBorderItem_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
               <%-- <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-url="${ctx}/sc/scBorderItem_data"
                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出</button>--%>
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
                            <div class="form-group">
                            <label>年度</label>
                            <div class="input-group" style="width: 120px">
                                <input required class="form-control date-picker" placeholder="请选择"
                                       name="year"
                                       type="text"
                                       data-date-format="yyyy" data-date-min-view-mode="2"
                                       value="${param.year}"/>
                                <span class="input-group-addon"> <i
                                        class="fa fa-calendar bigger-110"></i></span>
                            </div>
                            </div>
                        <div class="form-group">
                            <label>所属干部</label>
                            <c:set var="cadre" value="${cm:getCadreById(param.cadreId)}"/>
                            <select data-rel="select2-ajax"
                            data-ajax-url="${ctx}/cadre_selects?types=${CADRE_STATUS_MIDDLE},${CADRE_STATUS_LEADER},${CADRE_STATUS_MIDDLE_LEAVE},${CADRE_STATUS_LEADER_LEAVE}"
                            name="cadreId" data-placeholder="请输入账号或姓名或教工号"  data-width="270">
                        <option value="${cadre.id}">${cadre.realname}-${cadre.code}</option>
                    </select>
                        </div>

                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                   data-url="${ctx}/sc/scBorderItem?cls=${cls}"
                                   data-target="#page-content"
                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/sc/scBorderItem?cls=${cls}"
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
        <div id="body-content-view"></div>
    </div>
</div>
<jsp:include page="colModel.jsp?type=list"/>
<script>
    $("#jqGrid").jqGrid({
        rownumbers:true,
        url: '${ctx}/sc/scBorderItem_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: colModel
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    $.register.date($('.date-picker'));
</script>