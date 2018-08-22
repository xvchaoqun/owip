<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers multi-row-head-table"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query"
                   value="${not empty param.type ||not empty param.unitId ||not empty param.userId || not empty param.code || not empty param.sort}"/>
            <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                <li class="<c:if test="${cls==1}">active</c:if>">
                    <a href="javascript:;" class="loadPage" data-url="${ctx}/cet/cetUpperTrain?cls=1"><i
                            class="fa fa-list"></i> 调训信息汇总</a>
                </li>
                <li class="<c:if test="${cls==2}">active</c:if>">
                    <a href="javascript:;" class="loadPage" data-url="${ctx}/cet/cetUpperTrain?cls=2"><i
                            class="fa fa-circle-o"></i> 待审核</a>
                </li>
                <li class="<c:if test="${cls==3}">active</c:if>">
                    <a href="javascript:;" class="loadPage" data-url="${ctx}/cet/cetUpperTrain?cls=3"><i
                            class="fa fa-times"></i> 未通过审核</a>
                </li>
            </ul>
            <div class="space-4"></div>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="cetUpperTrain:edit">
                    <button class="popupBtn btn btn-info btn-sm"
                            data-url="${ctx}/cet/cetUpperTrain_au?type=0&addType=<%=CetConstants.CET_UPPER_TRAIN_ADD_TYPE_OW%>"
                            data-width="900">
                        <i class="fa fa-plus"></i> 添加
                    </button>
                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                            data-url="${ctx}/cet/cetUpperTrain_au?addType=<%=CetConstants.CET_UPPER_TRAIN_ADD_TYPE_OW%>"
                            data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                        修改
                    </button>
                </shiro:hasPermission>
                <shiro:hasPermission name="cetUpperTrain:del">
                    <button data-url="${ctx}/cet/cetUpperTrain_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
                <button id="uploadNoteBtn" class="jqOpenViewBtn btn btn-warning btn-sm tooltip-success"
                        data-url="${ctx}/cet/cetUpperTrain_uploadNote"
                        data-grid-id="#jqGrid"
                        data-rel="tooltip" data-placement="top"
                        title="上传培训总结"><i class="fa fa-upload"></i> 上传培训总结</button>

                <button class="jqOpenViewBtn btn btn-info btn-sm"
                        data-url="${ctx}/sysApprovalLog"
                        data-querystr="&type=<%=SystemConstants.SYS_APPROVAL_LOG_TYPE_CET_UPPER_TRAIN%>"
                        data-open-by="page">
                    <i class="fa fa-search"></i> 操作记录
                </button>

                <%--<button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-url="${ctx}/cet/cetUpperTrain_data"
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
                                <label>派出类型</label>
                                <input class="form-control search-query" name="type" type="text" value="${param.type}"
                                       placeholder="请输入派出类型">
                            </div>
                            <div class="form-group">
                                <label>派出单位</label>
                                <input class="form-control search-query" name="unitId" type="text"
                                       value="${param.unitId}"
                                       placeholder="请输入派出单位">
                            </div>
                            <div class="form-group">
                                <label>参训人</label>
                                <input class="form-control search-query" name="userId" type="text"
                                       value="${param.userId}"
                                       placeholder="请输入参训人">
                            </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                   data-url="${ctx}/cet/cetUpperTrain"
                                   data-target="#page-content"
                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="resetBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/cet/cetUpperTrain"
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
<jsp:include page="/WEB-INF/jsp/cet/cetUpperTrain/cetUpperTrain_colModel.jsp"/>
<script>
    $("#jqGrid").jqGrid({
        rownumbers: true,
        url: '${ctx}/cet/cetUpperTrain_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: colModel
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$.register.date($('.date-picker'));
</script>