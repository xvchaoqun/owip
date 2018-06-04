<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="../constants.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" class="myTableDiv rownumbers"
             data-url-page="${ctx}/pcsRecommend"
             data-url-export="${ctx}/pcsRecommend_data"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.branchId || not empty param.code || not empty param.sort}"/>
            <div class="jqgrid-vertical-offset buttons">
                <a class="popupBtn btn btn-success btn-sm"
                   data-url="${ctx}/pcsRecommend_form_download?stage=${param.stage}">
                    <i class="fa fa-download"></i> ${param.stage==PCS_STAGE_FIRST?"“一下”表格下载":""}
                    ${param.stage==PCS_STAGE_SECOND?"“二下”名单下载":""}
                    ${param.stage==PCS_STAGE_THIRD?"“三下”名单下载":""}</a>
                <c:if test="${param.stage==PCS_STAGE_FIRST}">
                <a class="popupBtn btn btn-warning btn-sm"
                   data-width="750"
                   data-url="${ctx}/hf_content?code=hf_pcs_cm_guide">
                    <i class="fa fa-question-circle"></i> 使用说明</a>
                </c:if>
            </div>
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
                            <form class="form-inline search-form" id="searchForm">
                                <input type="hidden" name="stage" value="${param.stage}"/>
                                <div class="form-group">
                                    <label>支部</label>
                                    <select class="form-control" data-rel="select2-ajax"
                                            data-ajax-url="${ctx}/branch_selects?partyId=${partyId}&del=0"
                                            name="branchId" data-placeholder="请选择" data-width="320">
                                        <option value="${branch.id}">${branch.name}</option>
                                    </select>
                                </div>
                                <div class="clearfix form-actions center">
                                    <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>

                                    <c:if test="${_query}">&nbsp;
                                        <button type="button" class="resetBtn btn btn-warning btn-sm"
                                                data-querystr="stage=${param.stage}">
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
            <table id="jqGrid" class="jqGrid table-striped"></table>
            <div id="jqGridPager"></div>
        </div>
        <div id="body-content-view"></div>
    </div>
</div>

<script>
    $.register.ajax_select($('#searchForm select[name=branchId]'));
    $("#jqGrid").jqGrid({
        rownumbers: true,
        url: '${ctx}/pcsRecommend_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
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
                return ('<button class="openView btn {3} btn-xs" ' +
                'data-url="${ctx}/pcsRecommend_au?stage=${param.stage}&partyId={0}&branchId={1}"><i class="fa {4}"></i> {2}</button>')
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
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>