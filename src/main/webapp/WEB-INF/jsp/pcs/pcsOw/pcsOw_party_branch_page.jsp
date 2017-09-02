<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<div class="myTableDiv rownumbers"
     data-url-page="${ctx}/pcsOw_party_branch_page"
     data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
    <c:set var="_query" value="${not empty param.branchId || not empty param.code || not empty param.sort}"/>

    <c:if test="${!isDirectBranch}">
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
                        <input type="hidden" name="stage" value="${param.stage}"/>
                        <input type="hidden" name="partyId" value="${param.partyId}">
                        <div class="form-group">
                            <label>支部</label>
                            <select class="form-control" data-rel="select2-ajax"
                                    data-ajax-url="${ctx}/branch_selects?partyId=${partyId}&del=0"
                                    name="branchId" data-placeholder="请选择" data-width="320">
                                <option value="${branch.id}">${branch.name}</option>
                            </select>
                        </div>
                        <div class="clearfix form-actions center">
                            <a class="jqSearchBtn btn btn-default btn-sm"
                               data-target="#step-item-content"
                               data-form="#searchForm2"
                                    ><i class="fa fa-search"></i> 查找</a>

                            <c:if test="${_query}">&nbsp;
                                <button type="button" class="resetBtn btn btn-warning btn-sm"
                                        data-target="#step-item-content"
                                        data-querystr="stage=${param.stage}&partyId=${partyId}">
                                    <i class="fa fa-reply"></i> 重置
                                </button>
                            </c:if>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </c:if>
    <div class="space-4"></div>
    <table id="jqGrid2" class="jqGrid2 table-striped"></table>
    <div id="jqGridPager2"></div>

</div>


<script>
    register_ajax_select($('#searchForm2 select[name=branchId]'));
    $("#jqGrid2").jqGrid({
        pager: "#jqGridPager2",
        rownumbers: true,
        multiselect: false,
        url: '${ctx}/pcsOw_party_branch_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '党支部名称', name: 'name', align: 'left', width: 400},
            {
                label: '党员总数', name: 'memberCount', formatter: function (cellvalue, options, rowObject) {
                return ($.trim(cellvalue) == '') ? 0 : cellvalue;
            }
            },
            {label: '应参会党员数', name: 'expectMemberCount', width: 120},
            {label: '实参会党员数', name: 'actualMemberCount', width: 120},
            {
                label: '推荐情况', name: 'isFinished', formatter: function (cellvalue, options, rowObject) {
                var isFinished = (cellvalue == undefined) ? false : cellvalue;
                return ('<button class="loadPage btn {3} btn-xs" data-load-el="#step-item-content" ' +
                'data-url="${ctx}/pcsOw_party_branch_detail?admin=1&stage=${param.stage}&partyId={0}&branchId={1}"><i class="fa {4}"></i> {2}</button>')
                        .format(rowObject.partyId,
                        $.trim(rowObject.branchId),
                        isFinished ? "已推荐" : "推荐情况",
                        isFinished ? "btn-success" : "btn-primary",
                        isFinished ? "fa-check" : "fa-hand-o-right");
            }
            }, {
                hidden: true, key: true, name: '_id', formatter: function (cellvalue, options, rowObject) {
                    return ("{0}-{1}-{2}".format(rowObject.partyId, $.trim(rowObject.branchId) == '' ? 0 : rowObject.branchId, rowObject.configId))
                }
            }
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");
</script>