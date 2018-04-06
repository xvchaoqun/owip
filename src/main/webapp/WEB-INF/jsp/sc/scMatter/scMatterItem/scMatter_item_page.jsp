<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<!-- PAGE CONTENT BEGINS -->
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