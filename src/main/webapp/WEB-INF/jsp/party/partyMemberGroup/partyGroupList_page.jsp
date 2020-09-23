<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div id="body-content" class="multi-row-head-table">
<%--<div class="jqgrid-vertical-offset buttons">
    <span>列表默认显示已到应换届时间的班子，如需查询其他，请从搜索中查询</span>
</div>--%>
<c:set var="_query" value="${not empty param.timeLevel || not empty param._deposeTime || not empty param.name}"/>
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
                            <label>党委班子名称</label>
                                <input class="form-control search-query" name="name" type="text" value="${param.name}"
                                       placeholder="请输入党委班子名称">
                        </div>

                        <div class="clearfix form-actions center">
                            <a class="jqSearchBtn btn btn-default btn-sm"
                               data-url="${ctx}/unitTeam?list=1"
                               data-target="#page-content"
                               data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                            <c:if test="${_query}">&nbsp;
                                <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                        data-url="${ctx}/unitTeam?list=1"
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

  $("#jqGrid").jqGrid({
        rownumbers: true,
        url: '${ctx}/partyMemberGroup_data?callback=?&isTranTime=1&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {
                label: '名称',
                name: 'name',
                align: 'left',
                width: 550,
                formatter: function (cellvalue, options, rowObject) {
                    //var str = '<span class="label label-sm label-primary" style="display: inline!important;"> 现任班子</span>&nbsp;';
                    var str = '<i class="fa fa-flag red" title="现任领导班子"></i> ';
                    return (!rowObject.isDeleted) ? str + cellvalue : cellvalue;
                },
                frozen: true
            },
            {
                label: '所属${_p_partyName}',
                name: 'partyId',
                align: 'left',
                width: 380,
                formatter: function (cellvalue, options, rowObject) {

                    return $.party(rowObject.partyId);
                }
            },
            {label: '任命时间', name: 'appointTime', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
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
            },
            {
                label: '实际换届时间',
                name: 'actualTranTime',
                width: 130,
                formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: 'Y.m.d'}
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