<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/sc/scMatterItem"
             data-url-export="${ctx}/sc/scMatterItem_data"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.year ||not empty param.type
             ||not empty param.userId || not empty param.code || not empty param.sort}"/>
            <div class="tabbable">
                <jsp:include page="../scMatter/menu.jsp"/>
                <div class="tab-content">
                    <div class="tab-pane in active">
                        <div class="jqgrid-vertical-offset buttons">
                            <%--<a class="jqOpenViewBtn btn btn-warning btn-sm"
                               data-url="${ctx}/sc/scMatter_au"
                               data-grid-id="#jqGrid"
                               ><i class="fa fa-send"></i>
                                通知填报</a>
                            <a class="jqOpenViewBtn btn btn-warning btn-sm"
                               data-url="${ctx}/sc/scMatter_au"
                               data-grid-id="#jqGrid"
                               ><i class="fa fa-send"></i>
                                催交通知</a>--%>
                            <a class="jqOpenViewBtn btn btn-success btn-sm"
                               data-url="${ctx}/sc/scMatterAccess"
                               data-grid-id="#jqGrid"
                               data-width="900"
                               data-id-name="matterItemId"
                               data-querystr="&cls=-1"><i class="fa fa-search"></i>
                                调阅记录</a>
                            <shiro:hasPermission name="scMatterItem:del">
                                <button data-url="${ctx}/sc/scMatterItem_batchDel"
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
                                            <label>年度</label>

                                            <div class="input-group">
                                                <input class="form-control date-picker" placeholder="请选择年份"
                                                       name="year"
                                                       type="text"
                                                       data-date-format="yyyy" data-date-min-view-mode="2"
                                                       value="${param.year}"/>
                                                <span class="input-group-addon"> <i
                                                        class="fa fa-calendar bigger-110"></i></span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label>填报类型</label>
                                            <select data-rel="select2" name="type" data-placeholder="请选择">
                                                <option></option>
                                                <option value="0"> 年度集中填报</option>
                                                <option value="1">个别填报</option>
                                            </select>
                                            <script>
                                                $("#searchForm select[name=type]").val('${param.type}')
                                            </script>
                                        </div>
                                        <div class="form-group">
                                            <label>姓名</label>
                                            <select data-rel="select2-ajax" data-ajax-url="${ctx}/sc/scMatterUser_selects"
                                                    name="userId" data-placeholder="请输入账号或姓名或学工号">
                                                <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                            </select>
                                        </div>
                                        <div class="clearfix form-actions center">
                                            <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i>
                                                查找</a>

                                            <c:if test="${_query}">&nbsp;
                                                <button type="button" class="reloadBtn btn btn-warning btn-sm"
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
<jsp:include page="scMatterItem_colModel.jsp?type=admin"/>
<script>
    $("#jqGrid").jqGrid({
        url: '${ctx}/sc/scMatterItem_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: colModel
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");

    $.register.date($('.date-picker'));
    $('#searchForm [data-rel="select2"]').select2();
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $('[data-rel="tooltip"]').tooltip();
</script>