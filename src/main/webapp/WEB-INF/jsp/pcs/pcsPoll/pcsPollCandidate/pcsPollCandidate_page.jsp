<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/pcs/constants.jsp" %>
<div class="widget-box transparent">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
        <a href="javascript:;" class="hideView btn btn-xs btn-success">
            <i class="ace-icon fa fa-backward"></i>
            返回</a>
    </h4>
    </div>
    <div class="widget-body">
        <div class="widget-main padding-4">
            <div class="tab-content padding-8">
                <div>
                    <shiro:hasPermission name="pcsPollCandidate:edit">
                        <button class="popupBtn btn btn-primary btn-sm tooltip-success"
                                data-url="${ctx}/pcs/pcsPollCandidate_import?pollId=${param.pollId}"
                                data-rel="tooltip" data-placement="top" title="批量导入二下阶段推荐人名单"><i class="fa fa-upload"></i> 导入</button>
                    </shiro:hasPermission>
                    <%--<shiro:hasPermission name="pcsPollCandidate:del">
                        <button data-url="${ctx}/pcs/pcsPollCandidate_batchDel"
                                data-title="删除"
                                data-msg="确定删除这{0}条数据？"
                                data-grid-id="#jqGrid2"
                                class="jqBatchBtn btn btn-danger btn-sm">
                            <i class="fa fa-trash"></i> 删除
                        </button>
                    </shiro:hasPermission>--%>
                </div>
                <div class="space-4"></div>
                <table id="jqGrid2" class="jqGrid2 table-striped" data-height-reduce="100"></table>
                <div id="jqGridPager2"></div>
            </div>
        </div>
    </div>
</div>
<script>
    $("#jqGrid2").jqGrid({
        pager: "jqGridPager2",
        rownumbers:true,
        url: '${ctx}/pcs/pcsPollCandidate_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '学工号',name: 'user.code', width:120},
            { label: '推荐人',name: 'user.realname'},
            { label: '推荐人类型',name: 'type', width: 120,formatter: function (cellvalue, options, rowObject) {
                    return _cMap.PCS_USER_TYPE_MAP[cellvalue];
                }},
            <c:if test="${!_query}">
            { label:'排序', width: 80, formatter: $.jgrid.formatter.sortOrder,
                formatoptions:{grid:'#jqGrid2',url:'${ctx}/pcs/pcsPollCandidate_changeOrder'},frozen:true },
            </c:if>
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    $('#searchForm2 [data-rel="select2"]').select2();
    $('#searchForm2 [data-rel="tooltip"]').tooltip();
</script>