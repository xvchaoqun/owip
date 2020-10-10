<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div id="body-content" class="multi-row-head-table">
    <c:set var="_query" value="${not empty param.timeLevel || not empty param._deposeTime || not empty param.name || not empty param.partyId
        || not empty param.branchId}"/>
    <div class="jqgrid-vertical-offset buttons">
        <div class="type-select" style="float: left;">
            <span class="yearCheckbox">
                <input ${param.year==1?"checked":""} type="checkbox" class="big" value="1"> 应换届时间超过一年
                </span>
        </div>
        <div style="clear: both"/>
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
                    <input type="hidden" name="year" value="${param.year}">
                    <div class="form-group">
                        <label>${_p_partyName}</label>
                        <select class="form-control" data-rel="select2-ajax"
                                data-ajax-url="${ctx}/party_selects?auth=1&notDirect=1"
                                data-width="350"
                                name="partyId" data-placeholder="请选择">
                            <option value="${party.id}">${party.name}</option>
                        </select>
                    </div>
                    <div class="form-group" style="${(empty branch)?'display: none':''}"
                         id="branchDiv">
                        <label>党支部</label>
                        <select class="form-control" data-rel="select2-ajax"
                                data-ajax-url="${ctx}/branch_selects"
                                name="branchId" data-placeholder="请选择党支部">
                            <option value="${branch.id}">${branch.name}</option>
                        </select>
                    </div>
                    <script>
                        $.register.party_branch_select($("#searchForm"), "branchDiv",
                            '${cm:getMetaTypeByCode("mt_direct_branch").id}', "${party.id}", "${party.classId}");
                    </script>
                    <div class="form-group">
                        <label>支部委员会名称</label>
                        <input class="form-control search-query" name="name" type="text" value="${param.name}"
                               placeholder="请输入支部委员会名称">
                    </div>
                    <div class="clearfix form-actions center">
                        <a class="jqSearchBtn btn btn-default btn-sm"
                           data-url="${ctx}/unitTeam?list=3"
                           data-target="#page-content"
                           data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                        <c:if test="${_query}">&nbsp;
                            <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                    data-url="${ctx}/unitTeam?list=3"
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
    <table id="jqGrid" class="jqGrid table-striped"></table>
    <div id="jqGridPager"></div>
</div>
<div id="body-content-view"></div>
<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>
<script>

    $(":checkbox", ".yearCheckbox").click(function () {
        $("#searchForm input[name=year]").val($(this).prop("checked") ? $(this).val() : '');
        $("#searchForm .jqSearchBtn").click();
    })

    $("#jqGrid").jqGrid({
        rownumbers: true,
        url: '${ctx}/branchMemberGroup_data?callback=?&isTranTime=1&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {
                label: '名称',
                name: 'name',
                align: 'left',
                width: 450,
                formatter: function (cellvalue, options, rowObject) {
                    var str = '<i class="fa fa-flag red" title="现任支部委员会"></i> ';
                    return (!rowObject.isDeleted) ? str + cellvalue : cellvalue;
                },
                frozen: true
            },
            {
                label: '所属党组织',
                name: 'partyId',
                align: 'left',
                width: 500,
                formatter: function (cellvalue, options, rowObject) {

                    return $.party(rowObject.partyId, rowObject.branchId);
                }
            },
            {label: '任命时间', name: 'appointTime', width: 130, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
            {
                hidden: true, name: 'isDeleted', formatter: function (cellvalue, options, rowObject) {
                    return (rowObject.isDeleted) ? 1 : 0;
                }
            },
            {label: '应换届时间', name: 'tranTime', width: 130,
                formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'},
                cellattr: function (rowId, val, rowObject, cm, rdata) {
                    if (!rowObject.isDeleted &&
                        rowObject.tranTime <= $.date(new Date(), 'yyyy-MM-dd'))
                        return "class='danger'";
                }
            }
        ]
    }).jqGrid("setFrozenColumns")
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");


    function _reload2() {
        $("#modal").modal('hide');
        $("#jqGrid2").trigger("reloadGrid");
    }

    $('#searchForm [data-rel="select2"]').select2();
    // $('[data-rel="tooltip"]').tooltip();
</script>