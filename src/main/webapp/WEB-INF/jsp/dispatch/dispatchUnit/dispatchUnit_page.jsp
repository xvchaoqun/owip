<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content">
            <div class="myTableDiv"
                 data-url-au="${ctx}/dispatchUnit_au"
                 data-url-page="${ctx}/dispatchUnit"
                 data-url-co="${ctx}/dispatchUnit_changeOrder"
                 data-url-export="${ctx}/dispatchUnit_data"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
                <c:set var="_query" value="${not empty param.year ||not empty param.partyId ||not empty param.unitId ||not empty param.type || not empty param.code || not empty param.sort}"/>

                  <div class="tabbable">
                    <jsp:include page="/WEB-INF/jsp/dispatch/dispatch_menu.jsp"/>
                    <div class="tab-content">
                <div class="jqgrid-vertical-offset buttons">
                    <shiro:hasPermission name="dispatchUnit:edit">
                        <a class="openView btn btn-info btn-sm" data-url="${ctx}/dispatch_units"><i class="fa fa-plus"></i> 添加</a>
                    </shiro:hasPermission>
                    <a href="javascript:;" class="jqEditBtn btn btn-primary btn-sm">
                        <i class="fa fa-edit"></i> 修改信息</a>
                    <%--<a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                       data-rel="tooltip" data-placement="top" title="导出当前搜索的全部结果（按照当前排序）"><i class="fa fa-download"></i> 导出</a>--%>
                    <shiro:hasPermission name="dispatchUnit:del">
                        <a class="jqBatchBtn btn btn-danger btn-sm"
                           data-url="${ctx}/dispatchUnit_batchDel" data-title="删除"
                           data-msg="确定删除这{0}条数据吗？"><i class="fa fa-trash"></i> 删除</a>
                    </shiro:hasPermission>
                </div>
                <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs" style="margin-right: 20px">
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
                                            <label>年份</label>
                                                <div class="input-group" style="width: 100px">
                                                    <input class="form-control date-picker" placeholder="请选择年份" name="year" type="text"
                                                           data-date-format="yyyy" data-date-min-view-mode="2" value="${param.year}" />
                                                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                                                </div>
                                        </div>
                                        <div class="form-group">
                                            <label>调整方式</label>
                                                <select data-rel="select2" name="type" data-width="100" data-placeholder="请选择">
                                                    <option></option>
                                                    <c:import url="/metaTypes?__code=mc_dispatch_unit_type"/>
                                                </select>
                                                <script type="text/javascript">
                                                    $("#searchForm select[name=type]").val('${param.type}');
                                                </script>
                                        </div>
                                        <div class="form-group">
                                            <label>内设机构</label>
                                                <select data-rel="select2-ajax" data-ajax-url="${ctx}/unit_selects"
                                                        name="unitId" data-width="340" data-placeholder="请选择">
                                                  <option value="${unit.id}" title="${unit.status==UNIT_STATUS_HISTORY}">${unit.name}</option>
                                                </select>
                                        </div>
                                        <div class="form-group">
                                            <label>组织机构</label>
                                                <select data-rel="select2-ajax" data-ajax-url="${ctx}/party_selects"
                                                        name="partyId" data-width="340" data-placeholder="请选择">
                                                  <option value="${party.id}" title="${party.isDeleted}">${party.name}</option>
                                                </select>
                                        </div>

                                <div class="clearfix form-actions center">
                                    <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>
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
                <table id="jqGrid" class="jqGrid table-striped"> </table>
                <div id="jqGridPager"> </div>
            </div>
        </div>
        </div>
    </div>
        <div id="body-content-view"> </div>
    </div>
    </div>
<jsp:include page="dispatchUnit_colModel.jsp?type=all"/>
<script>
    $("#jqGrid").jqGrid({
        url: '${ctx}/dispatchUnit_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel:colModel
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $.register.date($('.date-picker'));
    $('[data-rel="select2"]').select2();

    $.register.del_select($('[data-rel="select2-ajax"]'));
</script>