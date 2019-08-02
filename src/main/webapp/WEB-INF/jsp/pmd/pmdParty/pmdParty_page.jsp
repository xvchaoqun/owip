<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" class="multi-row-head-table myTableDiv"
                 data-url-page="${ctx}/pmd/pmdParty"
                 data-url-export="${ctx}/pmd/pmdParty_data"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.payMonth ||not empty param.hasReport
             || not empty param.code || not empty param.sort}"/>
            <div class="jqgrid-vertical-offset buttons">

                <a class="popupBtn btn btn-warning btn-sm"
                   data-url="${ctx}/pdf_preview?code=af_pmd_help&np=1"><i class="fa fa-info-circle"></i> 操作说明</a>
               <%-- <a class="popupBtn btn btn-warning btn-sm"
                   data-width="800"
                   data-url="${ctx}/hf_content?code=hf_pmd_party">
                    <i class="fa fa-info-circle"></i> 操作说明</a>--%>
                <%--<shiro:hasPermission name="pmdParty:edit">
                    <a class="popupBtn btn btn-info btn-sm"  data-url="${ctx}/pmdParty_au"><i class="fa fa-plus"></i> 添加</a>
                    <a class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/pmdParty_au"
                       data-grid-id="#jqGrid"
                       ><i class="fa fa-edit"></i>
                        修改</a>
                </shiro:hasPermission>
                <shiro:hasPermission name="pmdParty:del">
                    <button data-url="${ctx}/pmdParty_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>--%>
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
<jsp:include page="pmdParty_colModel.jsp"/>
<script>
    $.register.date($('.date-picker'));
    $("#jqGrid").jqGrid({
        url: '${ctx}/pmd/pmdParty_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: colModel
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>