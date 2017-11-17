<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content" class="myTableDiv"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.payMonth || not empty param.code || not empty param.sort}"/>
            <div class="jqgrid-vertical-offset buttons">
                    <button class="confirm btn btn-warning btn-sm" data-title="同步管理员"
                            data-msg="<div class='confirmMsg'>确定重新同步管理员？（此操作将删除原有的书记、组织委员，重新同步最新的书记、组织委员为党费收缴管理员）</div>"
                            data-callback="_reload"
                            data-url="${ctx}/pmd/pmdPartyAdmin_sync"><i class="fa fa-refresh"></i> 同步管理员（书记、组织委员）</button>
            </div>
            <%--<div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
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
                            <label>月份</label>
                            <input class="form-control search-query" name="payMonth" type="text" value="${param.payMonth}"
                                   placeholder="请输入月份">
                        </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>

                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="resetBtn btn btn-warning btn-sm">
                                        <i class="fa fa-reply"></i> 重置
                                    </button>
                                </c:if>
                            </div>
                        </form>
                    </div>
                </div>
            </div>--%>
            <div class="space-4"></div>
            <table id="jqGrid" class="jqGrid table-striped"></table>
            <div id="jqGridPager"></div>
        </div>
        <div id="item-content"></div>
    </div>
</div>
<script>
    function _reload(){
        $("#jqGrid").trigger("reloadGrid");
    }
    $("#jqGrid").jqGrid({
        url: '${ctx}/pmd/pmdPayParty_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '党委名称',name: 'name', width:450, align:'left'},
            { label: '管理员',name: 'adminCount', formatter: function (cellvalue, options, rowObject) {

                return ('<button class="popupBtn btn btn-success btn-xs"' +
                'data-url="${ctx}/pmd/pmdPartyAdmin?partyId={0}"><i class="fa fa-cogs"></i> 设置({1})</button>')
                        .format(rowObject.partyId, cellvalue);
            }}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>