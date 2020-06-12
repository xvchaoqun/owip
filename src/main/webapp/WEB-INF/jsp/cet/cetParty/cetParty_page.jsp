<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" class="rownumbers" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.partyId || not empty param.code || not empty param.sort}"/>
                <div class="tabbable">
                    <jsp:include page="menu.jsp"/>

                    <div class="tab-content">
                        <div class="tab-pane in active multi-row-head-table">
                            <div class="jqgrid-vertical-offset buttons">
                                <shiro:hasPermission name="cetParty:edit">
                                    <button class="popupBtn btn btn-info btn-sm"
                                            data-url="${ctx}/cet/cetParty_au">
                                        <i class="fa fa-plus"></i> 添加</button>
                                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                                       data-url="${ctx}/cet/cetParty_au"
                                       data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                                        修改</button>
                                    <button class="confirm btn btn-warning btn-sm" data-title="同步管理员"
                                            data-msg="<div class='confirmMsg'>确定重新同步管理员？（此操作将删除原有的分党委管理员，重新同步最新的分党委管理员为二级党委培训管理员）</div>"
                                            data-callback="_reload"
                                            data-url="${ctx}/cet/cetPartyAdmin_sync"><i class="fa fa-refresh"></i> 同步管理员</button>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="cetParty:del">
                                    <button data-url="${ctx}/cet/cetParty_cancel?delete=1"
                                            data-title="删除"
                                            data-msg="确定删除这{0}条数据？"
                                            data-grid-id="#jqGrid"
                                            class="jqBatchBtn btn btn-danger btn-sm">
                                        <i class="fa fa-trash"></i> 删除
                                    </button>
                                </shiro:hasPermission>
                            </div>
                            <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
                                <div class="widget-header">
                                    <h4 class="widget-title">搜索</h4><span class="widget-note">${note_searchbar}</span>
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
                                            <label>二级党委名称</label>
                                            <select name="partyId" data-rel="select2-ajax" data-ajax-url="${ctx}/party_selects"
                                                    data-placeholder="请选择二级党委">
                                                <option value="${party.id}" delete="${party.isDeleted}">${party.name}</option>
                                            </select>
                                            <script>
                                                $.register.del_select($("#searchForm select[name=partyId]"), 350)
                                            </script>
                                        </div>
                                            <div class="clearfix form-actions center">
                                                <a class="jqSearchBtn btn btn-default btn-sm"
                                                   data-url="${ctx}/cet/cetParty"
                                                   data-target="#page-content"
                                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                                <c:if test="${_query}">&nbsp;
                                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                            data-url="${ctx}/cet/cetParty"
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
                            <table id="jqGrid" class="jqGrid table-striped" data-height-reduce="5"></table>
                            <div id="jqGridPager"></div>
                        </div>
                    </div>
                </div>
        </div>
        <div id="body-content-view"></div>
    </div>
</div>
<script>
    function _reload(){
        $("#jqGrid").trigger("reloadGrid");
    }
    $("#jqGrid").jqGrid({
        rownumbers:true,
        url: '${ctx}/cet/cetParty_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '二级党委名称', name: 'partyName', width:450, align:'left'},
            { label: '管理员',name: 'adminCount', width:150, formatter: function (cellvalue, options, rowObject) {

                var str = null;
                if (rowObject.isDeleted){
                    str = '<button data-url="${ctx}/cet/cetParty_cancel"' +
                        'data-title="恢复" data-msg="确定恢复这条数据？"' +
                        'data-grid-id="#jqGrid" class="jqBatchBtn btn btn-warning btn-xs">' +
                        '<i class="fa fa-reply"></i> 恢复</button>';
                }else {
                    str = '<button data-url="${ctx}/cet/cetParty_cancel"' +
                        'data-title="撤销" data-msg="确定撤销这条数据？（此操作将撤销该分党委下的所有管理员）"' +
                        'data-grid-id="#jqGrid" class="jqBatchBtn btn btn-danger btn-xs">' +
                        '<i class="fa fa-recycle"></i> 撤销</button>';
                }
                return ('<button class="popupBtn btn btn-success btn-xs"' +
                    'data-url="${ctx}/cet/cetPartyAdmin?cetPartyId={0}"><i class="fa fa-cogs"></i> 设置({1})</button>  ' + str)
                    .format(rowObject.id, cellvalue);
                }}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>