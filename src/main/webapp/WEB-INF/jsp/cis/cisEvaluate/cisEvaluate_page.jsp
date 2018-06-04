<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/cisEvaluate"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query"
                   value="${not empty param.cadreId ||not empty param.type || not empty param.code || not empty param.sort}"/>
            <div class="tabbable">
                <div class="tab-content">
                    <div class="tab-pane in active rownumbers">
                        <div class="jqgrid-vertical-offset buttons">
                            <shiro:hasPermission name="cisEvaluate:edit">
                                <a class="popupBtn btn btn-info btn-sm" data-url="${ctx}/cisEvaluate_au"><i
                                        class="fa fa-plus"></i> 添加</a>
                                <a class="jqOpenViewBtn btn btn-primary btn-sm"
                                   data-url="${ctx}/cisEvaluate_au"
                                   data-grid-id="#jqGrid"
                                   ><i class="fa fa-edit"></i>
                                    修改</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="cisEvaluate:del">
                                <button data-url="${ctx}/cisEvaluate_batchDel"
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
                                    <a href="javascript:;" data-action="collapse">
                                        <i class="ace-icon fa fa-chevron-${_query?'up':'down'}"></i>
                                    </a>
                                </div>
                            </div>
                            <div class="widget-body">
                                <div class="widget-main no-padding">
                                    <form class="form-inline search-form" id="searchForm">
                                        <input type="hidden" name="cls" value="${cls}">
                                        <div class="form-group">
                                            <label>考察对象</label>
                                            <select data-rel="select2-ajax" data-ajax-url="${ctx}/cadre_selects?type=0"
                                                    name="cadreId" data-placeholder="请输入账号或姓名或学工号">
                                                <option value="${cadre.id}">${cadre.realname}-${cadre.code}</option>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label>材料类型</label>
                                            <select required data-rel="select2" name="type" data-placeholder="请选择"  data-width="270">
                                                <option></option>
                                                <c:forEach items="<%=CisConstants.CIS_EVALUATE_TYPE_MAP%>" var="type">
                                                    <option value="${type.key}">${type.value}</option>
                                                </c:forEach>
                                            </select>
                                            <script type="text/javascript">
                                                $("#searchForm select[name=type]").val(${param.type});
                                            </script>
                                        </div>
                                        <div class="clearfix form-actions center">
                                            <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i>
                                                查找</a>
                                            <c:if test="${_query}">&nbsp;
                                                <button type="button" class="resetBtn btn btn-warning btn-sm"  data-querystr="cls=${cls}">
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
<jsp:include page="/WEB-INF/jsp/cadre/colModels.jsp"/>
<script>
    $.register.user_select($('#searchForm select[name=cadreId]'))
    $("#jqGrid").jqGrid({
        rownumbers: true,
        url: '${ctx}/cisEvaluate_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: colModels.cisEvaluate
    }).jqGrid("setFrozenColumns").on("initGrid", function () {
        $(window).triggerHandler('resize.jqGrid');
    })

    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>