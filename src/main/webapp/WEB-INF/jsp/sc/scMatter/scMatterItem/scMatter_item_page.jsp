<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<div class="widget-box transparent" id="useLogs">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:;" class="hideView btn btn-xs btn-success">
                <i class="ace-icon fa fa-backward"></i>
                返回</a>
        </h4>

        <div class="widget-toolbar no-border">
            <ul class="nav nav-tabs">
                <li class="active">
                    <a href="javascript:;">填报对象列表</a>
                </li>
            </ul>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main padding-4">
            <div class="tab-content padding-8">
                <div class="jqgrid-vertical-offset buttons">
                    <a class="jqOpenViewBtn btn btn-success btn-sm"
                       data-url="${ctx}/sc/scMatterAccess"
                       data-grid-id="#jqGrid2"
                       data-width="900"
                       data-id-name="matterItemId"
                       data-querystr="&cls=-1"><i class="fa fa-search"></i>
                        调阅记录</a>
                    <a class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/sc/scMatterItem_au"
                       data-width="400"
                       data-grid-id="#jqGrid2"
                       ><i class="fa fa-hand-paper-o"></i>
                        上交</a>
                    <shiro:hasPermission name="scMatterItem:del">
                        <button data-url="${ctx}/sc/scMatterItem_batchDel"
                                data-title="删除"
                                data-msg="确定删除这{0}条数据？"
                                data-grid-id="#jqGrid2"
                                class="jqBatchBtn btn btn-danger btn-sm">
                            <i class="fa fa-trash"></i> 删除
                        </button>
                    </shiro:hasPermission>
                   <%-- <a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                       data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                        <i class="fa fa-download"></i> 导出</a>--%>
                </div>
                <c:set var="_query" value="${not empty param.userId ||not empty param.type ||not empty param.backStatus}"></c:set>
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

                            <form class="form-inline search-form" id="searchForm2">

                                <div class="form-group">
                                    <label>填报对象</label>
                                    <select data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects?types=${USER_TYPE_JZG}"
                                            name="userId" data-placeholder="请输入账号或姓名或学工号">
                                        <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                    </select>
                                </div>

                                <div class="form-group">
                                    <label>填报类型</label>
                                    <select data-rel="select2" name="type"  data-width="150"
                                            data-placeholder="请选择">
                                        <option></option>
                                        <option value="0"> 年度集中填报</option>
                                        <option value="1">个别填报</option>
                                    </select>
                                    <script>
                                        $("#searchForm2 select[name=type]").val('${param.type}')
                                    </script>
                                </div>

                                <div class="form-group">
                                    <label>交回状态</label>
                                    <select data-rel="select2" name="backStatus" data-width="100"
                                            data-placeholder="请选择">
                                        <option></option>
                                        <option value="1"> 已交回</option>
                                        <option value="2"> 未交回</option>
                                        <option value="3"> 应交回</option>
                                    </select>
                                    <script>
                                        $("#searchForm2 select[name=backStatus]").val('${param.backStatus}')
                                    </script>
                                </div>
                                <div class="clearfix form-actions center">
                                    <a class="jqSearchBtn btn btn-default btn-sm"
                                       data-url="${ctx}/sc/scMatterItem?cls=-1&matterId=${param.matterId}"
                                       data-target="#body-content-view"
                                       data-form="#searchForm2"><i class="fa fa-search"></i> 查找</a>
                                    <c:if test="${_query}">&nbsp;
                                        <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                data-url="${ctx}/sc/scMatterItem?cls=-1&matterId=${param.matterId}"
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
                <table id="jqGrid2" class="jqGrid2 table-striped"></table>
                <div id="jqGridPager2"></div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="scMatterItem_colModel.jsp?type=admin"/>
<script>
    $('[data-rel="tooltip"]').tooltip();
    $.register.user_select($('#searchForm2 select[name=userId]'));
    $('#searchForm2 [data-rel="select2"]').select2();
    function _reload2() {
        $("#jqGrid2").trigger("reloadGrid");
    }

    $("#jqGrid2").jqGrid({
        pager: "jqGridPager2",
        url: '${ctx}/sc/scMatterItem_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel:colModel
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");
</script>