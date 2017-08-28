<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/pcsOw"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query"
                   value="${not empty param.userId|| not empty param.sort}"/>
            <div class="tabbable">
                <jsp:include page="menu.jsp"/>
                <div class="candidate-table tab-content">
                    <div class="tab-pane in active rownumbers">

                        <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
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
                                        <input type="hidden" name="stage" value="${param.stage}">
                                        <div class="form-group">
                                            <label>分党委</label>
                                            <select data-rel="select2-ajax" data-ajax-url="${ctx}/party_selects"
                                                    name="partyId" data-placeholder="请选择">
                                                <option value="${party.id}" title="${party.isDeleted}">${party.name}</option>
                                            </select>
                                        </div>
                                        <div class="clearfix form-actions center">
                                            <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i>
                                                查找</a>
                                            <c:if test="${_query}">&nbsp;
                                                <button type="button" class="resetBtn btn btn-warning btn-sm"
                                                        data-querystr="cls=${param.cls}&stage=${param.stage}">
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
                </div>
            </div>
        </div>
        <div id="item-content"></div>
    </div>
</div>
<style>
    .type-select{
        padding: 10px 0 0 5px;
    }
    .type-select .typeCheckbox{
        padding: 10px;
        cursor: pointer;
    }
    .type-select .typeCheckbox.checked{
        color: darkred;
        font-weight: bolder;
    }
    .candidate-table{
        padding-top: 0px !important;
    }
    .candidate-table th.ui-th-column div{
        white-space:normal !important;
        height:auto !important;
        padding:0px;
    }
    .candidate-table .frozen-bdiv.ui-jqgrid-bdiv {
        top: 43px !important;
    }
    #jqGrid_actualMemberCount{
        padding: 0;
    }

    .modal .tip ul{
        margin-left: 150px;
    }
    .modal .tip ul li{
        font-size: 25px;
        text-align: left;
    }
</style>
<script>
    $("#jqGrid").jqGrid({
        rownumbers: true,
        url: '${ctx}/pcsOw_party_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '分党委名称',name: 'name', align:'left', width:400},
            { label: '党支部数',name: 'branchCount'},
            { label: '党员总数',name: 'memberCount', formatter: function (cellvalue, options, rowObject) {
                return ($.trim(cellvalue)=='')?0:cellvalue;
            }},
            { label: '应参会党员数',name: 'expectMemberCount', width:120},
            { label: '实参会党员数',name: 'actualMemberCount', width:120},
            { label: '推荐情况',name: 'reportId', formatter: function (cellvalue, options, rowObject) {
                var hasReport = (cellvalue==undefined)?false:(cellvalue>0);
                if(!hasReport) return "未上报"
                return ('<button class="openView btn btn-success btn-xs" ' +
                'data-url="${ctx}/pcsOw_candidate_page?stage=${param.stage}&partyId={0}"><i class="fa fa-hand-paper-o"></i> 已上报</button>')
                        .format(rowObject.id);
            }}
        ]
    }).jqGrid("setFrozenColumns").on("initGrid",function(){
        $(window).triggerHandler('resize.jqGrid');
    })
    $.initNavGrid("jqGrid", "jqGridPager");
    register_party_select($('#searchForm select[name=partyId]'));
</script>