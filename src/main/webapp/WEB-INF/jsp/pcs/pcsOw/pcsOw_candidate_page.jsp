<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div class="widget-box transparent" id="view-box">
            <div class="widget-header">
                <h4 class="widget-title lighter smaller">
                    <a href="javascript:" class="hideView btn btn-xs btn-success">
                        <i class="ace-icon fa fa-backward"></i>
                        返回</a>
                </h4>

                <div class="widget-toolbar no-border">
                    <ul class="nav nav-tabs">
                        <li class="active">
                            <a href="javascript:" data-url="-1">推荐情况（${party.name}）</a>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="widget-body">
                <div class="widget-main padding-4">
                    <!-- PAGE CONTENT BEGINS -->
                    <div class="myTableDiv"
                         data-url-page="${ctx}/pcsOw_candidate_page"
                         data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
                        <c:set var="_query"
                               value="${not empty param.userId|| not empty param.sort}"/>
                        <div class="candidate-table rownumbers">
                            <div class="jqgrid-vertical-offset buttons">
                                <div class="type-select">
                                <span class="typeCheckbox ${type==PCS_USER_TYPE_DW?"checked":""}">
                                <input ${type==PCS_USER_TYPE_DW?"checked":""} type="checkbox"
                                                                              value="${PCS_USER_TYPE_DW}"> 党委委员
                                </span>
                                <span class="typeCheckbox ${type==PCS_USER_TYPE_JW?"checked":""}">
                                <input class="typeCheckbox" ${type==PCS_USER_TYPE_JW?"checked":""} type="checkbox"
                                       value="${PCS_USER_TYPE_JW}"> 纪委委员
                                </span>

                                    <a href="${ctx}/pcsOw_export?file=2-1&partyId=${param.partyId}&stage=${param.stage}&type=${type}" >
                                        <i class="fa fa-download"></i> 下载${PCS_USER_TYPE_MAP.get(type)}候选人推荐提名汇总表</a>
                                </div>
                            </div>
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
                                                        data-ajax-url="${ctx}/member_selects?noAuth=1&type=${MEMBER_TYPE_TEACHER}&status=${MEMBER_STATUS_NORMAL}"
                                                        data-placeholder="请输入账号或姓名或学工号">
                                                    <option value="${sysUser.id}">${sysUser.username}-${sysUser.code}</option>
                                                </select>
                                            </div>
                                            <div class="clearfix form-actions center">
                                                <a class="jqSearchBtn btn btn-default btn-sm"
                                                   data-target="#item-content"
                                                   data-form="#searchForm2"><i class="fa fa-search"></i>
                                                    查找</a>
                                                <c:if test="${_query}">&nbsp;
                                                    <button type="button" class="resetBtn btn btn-warning btn-sm"
                                                            data-target="#item-content"
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
                </div>
            </div>
        </div>
    </div>
</div>
<style>
    .type-select {
        padding: 10px 0 0 5px;
    }
    .type-select a{
        padding-left: 20px;
    }
    .type-select .typeCheckbox {
        padding: 10px;
        cursor: pointer;
    }

    .type-select .typeCheckbox.checked {
        color: darkred;
        font-weight: bolder;
    }

    .candidate-table {
        padding-top: 0px !important;
    }

    .candidate-table th.ui-th-column div {
        white-space: normal !important;
        height: auto !important;
        padding: 0px;
    }

    .candidate-table .frozen-bdiv.ui-jqgrid-bdiv {
        top: 43px !important;
    }

    #jqGrid2_actualMemberCount {
        padding: 0;
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
    $(".typeCheckbox").click(function () {
        var $input = $("input", $(this));
        $("#searchForm2 input[name=type]").val($input.val());
        $("#searchForm2 .jqSearchBtn").click();
    })
    $("#jqGrid2").jqGrid({
        pager: "#jqGridPager2",
        rownumbers: true,
        multiselect: false,
        url: '${ctx}/pcsOw_candidate_data?callback=?&partyId=${param.partyId}&stage=${param.stage}&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '工作证号', name: 'code', width: 120, frozen: true},
            {label: '被推荐提名人姓名', name: 'realname', width: 150, frozen: true},
            {
                label: '推荐提名的党支部数',
                name: 'branchCount',
                width: 160
            },
            {label: '推荐党支部所含党员数', name: 'memberCount', width: 170},
            {label: '推荐党支部应参会党员数', name: 'expectMemberCount', width: 190},
            {label: '推荐党支部实参会党员数<div style="font-size: 8px">（推荐提名的党员数）</div>', name: 'actualMemberCount', width: 180},
            {
                label: '性别', name: 'gender', width: 50, formatter: $.jgrid.formatter.GENDER
            },
            {label: '民族', name: 'nation', width: 60},
            {label: '职称', name: 'proPost', width: 200},
            {label: '出生年月', name: 'birth', formatter: 'date', formatoptions: {newformat: 'Y-m'}},
            {label: '年龄', name: 'birth', width: 50, formatter: $.jgrid.formatter.AGE},
            {
                label: '参加工作时间',
                name: 'workTime',
                width: 120,
                sortable: true,
                formatter: 'date',
                formatoptions: {newformat: 'Y-m-d'}
            },{
                label: '所在单位及职务',
                name: '_title',
                width: 350,
                align: 'left',
                formatter: function (cellvalue, options, rowObject) {
                    return ($.trim(rowObject.title) == '') ? $.trim(rowObject.extUnit) : $.trim(rowObject.title);
                }
            }
            <c:if test="${param.stage == PCS_STAGE_SECOND || param.stage == PCS_STAGE_THIRD}">
            ,{
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
    register_user_select($('#searchForm2 select[name=userId]'));
</script>