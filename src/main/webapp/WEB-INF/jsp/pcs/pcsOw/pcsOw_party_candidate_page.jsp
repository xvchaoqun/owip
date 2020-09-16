<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="../constants.jsp" %>
<div class="myTableDiv"
     data-url-page="${ctx}/pcs/pcsOw_party_candidate_page"
     data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
    <c:set var="_query"
           value="${not empty param.userId|| not empty param.sort}"/>
    <div class="candidate-table rownumbers">
        <div class="jqgrid-vertical-offset buttons">
            <div class="type-select" style="float: inherit">
                <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                    <input type="radio" name="userType"
                           id="type1" ${type==PCS_USER_TYPE_DW?"checked":""}
                           value="${PCS_USER_TYPE_DW}">
                    <label for="type1">
                        党委委员
                    </label>
                </div>
                <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                    <input type="radio" name="userType"
                           id="type0" ${type==PCS_USER_TYPE_JW?"checked":""}
                           value="${PCS_USER_TYPE_JW}">
                    <label for="type0">
                        纪委委员
                    </label>
                </div>
                <shiro:hasPermission name="pcsOw:admin">
                <a href="${ctx}/pcs/pcsOw_export?file=2-1&partyId=${param.partyId}&stage=${param.stage}&type=${type}">
                    <i class="fa fa-download"></i>  ${PCS_USER_TYPE_MAP.get(type)}候选人初步人选推荐提名汇总表（“${PCS_STAGE_MAP.get(cm:toByte(param.stage))}”阶段）</a>
                </shiro:hasPermission>
            </div>
            <%--<div class="type-select" style="float: inherit">
                                <span class="typeCheckbox ${type==PCS_USER_TYPE_DW?"checked":""}">
                                <input ${type==PCS_USER_TYPE_DW?"checked":""} type="checkbox"
                                                                              value="${PCS_USER_TYPE_DW}"> 党委委员
                                </span>
                                <span class="typeCheckbox ${type==PCS_USER_TYPE_JW?"checked":""}">
                                <input class="typeCheckbox" ${type==PCS_USER_TYPE_JW?"checked":""} type="checkbox"
                                       value="${PCS_USER_TYPE_JW}"> 纪委委员
                                </span>

            </div>--%>
        </div>
        <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
            <div class="widget-header">
                <h4 class="widget-title">搜索</h4><span class="widget-note">${note_searchbar}</span>

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
                                    data-ajax-url="${ctx}/member_selects?noAuth=1&type=${MEMBER_TYPE_TEACHER}&status=${MEMBER_STATUS_NORMAL}"
                                    data-placeholder="请输入账号或姓名或学工号">
                                <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                            </select>
                        </div>
                        <div class="clearfix form-actions center">
                            <a class="jqSearchBtn btn btn-default btn-sm"
                               data-target="#step-body-content-view"
                               data-form="#searchForm2"><i class="fa fa-search"></i>
                                查找</a>
                            <c:if test="${_query}">&nbsp;
                                <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                        data-target="#step-body-content-view"
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
<style>
    .candidate-table {
        padding-top: 0px !important;
    }

    .candidate-table th.ui-th-column div {
        white-space: normal !important;
        height: auto !important;
        padding: 0px;
    }

    .candidate-table .frozen-bdiv.ui-jqgrid-bdiv {
        top: 42px !important;
    }

    #jqGrid2_actualMemberCount, #jqGrid2_branchCount,
    #jqGrid2_totalVote, #jqGrid2_totalPositiveVote,
    #jqGrid2_memberCount, #jqGrid2_expectMemberCount {
        padding: 0;
        font-size: 11px;
    }

    .modal .tip ul {
        margin-left: 150px;
    }

    .modal .tip ul li {
        font-size: 25px;
        text-align: left;
    }
</style>
<script>
    $(".type-select input").click(function () {
        var $input = $(this);
        $("#searchForm2 input[name=type]").val($input.val());
        $("#searchForm2 .jqSearchBtn").click();
    })
    $("#jqGrid2").jqGrid({
        pager: "#jqGridPager2",
        rownumbers: true,
        multiselect: false,
        url: '${ctx}/pcs/pcsOw_candidate_data?callback=?&partyId=${param.partyId}&stage=${param.stage}&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '工作证号', name: 'code', width: 120, frozen: true},
            {label: '被推荐提名人姓名', name: 'realname', width: 150, frozen: true},
            {
                label: '推荐提名<div>的党支部数</div>',
                name: 'branchCount'
            },
            {label: '推荐党支部<div>所含党员数</div>', name: 'memberCount'},
            {label: '推荐党支部<div>应参会党员数</div>', name: 'expectMemberCount'},
            {label: '推荐党支部<br/>实参会党员数', name: 'actualMemberCount'},
            {label: '推荐提名<br/>的党员数', name: 'totalVote'},
            {label: '推荐提名<br/>的正式党员数', name: 'totalPositiveVote'},
            {
                label: '性别', name: 'gender', width: 50, formatter: $.jgrid.formatter.GENDER
            },
            {label: '民族', name: 'nation', width: 60},
            {label: '职称', name: 'proPost', width: 200},
            {label: '出生年月', name: 'birth', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}},
            {label: '年龄', name: 'birth', width: 50, formatter: $.jgrid.formatter.AGE, formatoptions: {newformat: 'Y.m',baseDate: '${_ageBaseDate}'}},
            /*{
             label: '参加工作时间',
             name: 'workTime',
             width: 120,
             sortable: true,
             formatter: $.jgrid.formatter.date,
             formatoptions: {newformat: 'Y.m'}
             }*/{
                label: '入党时间',
                name: 'growTime',
                width: 120,
                sortable: true,
                formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: 'Y.m.d'}
            }, {
                label: '所在单位及职务',
                name: '_title',
                width: 350,
                align: 'left',
                formatter: function (cellvalue, options, rowObject) {
                    return ($.trim(rowObject.title) == '') ? $.trim(rowObject.extUnit) : $.trim(rowObject.title);
                }
            }
            <c:if test="${param.stage == PCS_STAGE_SECOND || param.stage == PCS_STAGE_THIRD}">
            , {
                label: '备注',
                name: '_remark',
                width: 300,
                align: 'left',
                formatter: function (cellvalue, options, rowObject) {
                    return rowObject.isFromStage ? "“${param.stage == PCS_STAGE_SECOND?"二下":"三下"}”名单成员" : "另选他人";
                }
            }
            </c:if>
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");
    $.register.user_select($('#searchForm2 select[name=userId]'));
</script>