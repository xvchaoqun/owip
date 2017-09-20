<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="myTableDiv"
     data-url-page="${ctx}/pcsPrOw_party_candidate_page"
     data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
    <c:set var="_query"
           value="${not empty param.userId|| not empty param.sort}"/>
    <div class="candidate-table rownumbers">
        <div class="space-4"></div>
        <div class="jqgrid-vertical-offset buttons">

            <a href="${ctx}/pcsPrOw_export?file=${empty param.partyId?5:3}&partyId=${param.partyId}&stage=${param.stage}">
                <i class="fa fa-download"></i> 导出：<c:if test="${empty param.partyId}">
                    各分党委酝酿代表候选人${param.stage==PCS_STAGE_FIRST?'初步':'预备'}名单汇总表
                </c:if>
                <c:if test="${not empty param.partyId}">
                    分党委酝酿代表候选人${param.stage==PCS_STAGE_FIRST?'初步':'预备'}名单
                </c:if>
            </a>
        </div>
        <div class="space-4"></div>
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
                    <form class="form-inline search-form" id="searchForm2">
                        <input type="hidden" name="type" value="${type}">
                        <input type="hidden" name="stage" value="${param.stage}">
                        <input type="hidden" name="partyId" value="${param.partyId}">

                        <div class="form-group">
                            <label>被推荐人</label>
                            <select name="userId" data-rel="select2-ajax"
                                    data-ajax-url="${ctx}/member_selects?noAuth=1&politicalStatus=${MEMBER_POLITICAL_STATUS_POSITIVE}&status=${MEMBER_STATUS_NORMAL}"
                                    data-placeholder="请输入账号或姓名或学工号">
                                <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                            </select>
                        </div>
                        <div class="clearfix form-actions center">
                            <a class="jqSearchBtn btn btn-default btn-sm"
                               data-target="#step-item-content"
                               data-form="#searchForm2"><i class="fa fa-search"></i>
                                查找</a>
                            <c:if test="${_query}">&nbsp;
                                <button type="button" class="resetBtn btn btn-warning btn-sm"
                                        data-target="#step-item-content"
                                        data-querystr="partyId=${param.partyId}&stage=${param.stage}&type=${type}">
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
<script>
    $("#jqGrid2").jqGrid({
        pager: "#jqGridPager2",
        rownumbers: true,
        multiselect: false,
        url: '${ctx}/pcsPrOw_party_candidate_data?callback=?&partyId=${param.partyId}&stage=${param.stage}&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {
                label: '党代表类型', name: 'type', width: 150, formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '-';
                return _cMap.PCS_PR_TYPE_MAP[cellvalue]
            }
            },
            {label: '工作证号', name: 'code', width: 120, frozen: true},
            {label: '被推荐人姓名', name: 'realname', width: 150, frozen: true},
            {label: '所在单位', name: 'unitName', width: 200, align:'left', frozen: true},
            {
                label: '性别', name: 'gender', width: 50, formatter: $.jgrid.formatter.GENDER
            },
            {label: '出生年月', name: 'birth', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {label: '年龄', name: 'birth', width: 50, formatter: $.jgrid.formatter.AGE},
            {label: '民族', name: 'nation', width: 60},
            {
                label: '学历学位', name: '_learn', formatter: function (cellvalue, options, rowObject) {
                if (rowObject.userType == '${PCS_PR_USER_TYPE_CADRE}') {
                    return $.jgrid.formatter.MetaType(rowObject.eduId);
                } else if (rowObject.userType == '${PCS_PR_USER_TYPE_TEACHER}') {
                    return $.trim(rowObject.education);
                }
                return "-"
            }
            },/*
            {
                label: '参加工作时间',
                name: 'workTime',
                width: 120,
                sortable: true,
                formatter: 'date',
                formatoptions: {newformat: 'Y-m-d'}
            },*/
            {
                label: '入党时间',
                name: 'growTime',
                width: 120,
                sortable: true,
                formatter: 'date',
                formatoptions: {newformat: 'Y-m-d'}
            },
            {
                label: '职别', name: 'proPost', formatter: function (cellvalue, options, rowObject) {
                if (rowObject.userType == '${PCS_PR_USER_TYPE_CADRE}') {
                    return '干部';
                } else if (rowObject.userType == '${PCS_PR_USER_TYPE_TEACHER}') {
                    return (rowObject.isRetire) ? "离退休" : $.trim(cellvalue);
                }
                return $.trim(rowObject.eduLevel);
            }
            },
            {
                label: '职务', width: 200,
                name: 'post', formatter: function (cellvalue, options, rowObject) {
                if (rowObject.userType == '${PCS_PR_USER_TYPE_CADRE}') {
                    return $.trim(cellvalue);
                }
                return "-"
            }
            },
            {label: '票数', name: 'vote', width: 80}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");
    register_user_select($('#searchForm2 select[name=userId]'));
</script>