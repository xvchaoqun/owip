<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="../constants.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/pcsPrParty"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query"
                   value="${not empty param.userId|| not empty param.sort}"/>
            <div class="tabbable">
                <jsp:include page="menu.jsp"/>
                <div class="candidate-table tab-content">
                    <div class="tab-pane in active rownumbers">
                        <div class="jqgrid-vertical-offset buttons">
                            <a class="popupBtn btn btn-success btn-sm"
                               data-url="${ctx}/pcsPrParty_form_download?stage=${param.stage}">
                                <i class="fa fa-download"></i> ${param.stage==PCS_STAGE_FIRST?"“一下”表格下载":""}
                                ${param.stage==PCS_STAGE_SECOND?"“二下”名单下载":""}
                                ${param.stage==PCS_STAGE_THIRD?"“三下”名单下载":""}</a>
                            <button class="openView btn btn-warning btn-sm"
                                    data-url="${ctx}/pcsPrParty_candidate_au?stage=${param.stage}"
                                    ><i class="fa fa-sign-in"></i> 上传党代表候选人${param.stage==PCS_STAGE_FIRST?'初步':'预备'}人选名单</button>
                            <c:if test="${param.stage==PCS_STAGE_FIRST}">
                            <a class="popupBtn btn btn-warning btn-sm"
                               data-width="750"
                               data-url="${ctx}/hf_content?code=hf_pcs_pr_guide">
                                <i class="fa fa-question-circle"></i> 使用说明</a>
                            </c:if>
                            <a style="margin-left: 20px;" href="${ctx}/pcsPrParty_export?file=3&stage=${param.stage}">
                                <i class="fa fa-download"></i> ${_p_partyName}酝酿党员代表大会代表候选人${param.stage==PCS_STAGE_FIRST?'初步':'预备'}人选名单（“${PCS_STAGE_MAP.get(cm:toByte(param.stage))}”阶段）</a>
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
                                    <form class="form-inline search-form" id="searchForm">
                                        <input type="hidden" name="type" value="${type}">
                                        <input type="hidden" name="stage" value="${param.stage}">

                                        <div class="form-group">
                                            <label>被推荐人</label>
                                            <select name="userId" data-rel="select2-ajax"
                                                    data-ajax-url="${ctx}/member_selects?noAuth=1&partyId=${partyId}&type=${MEMBER_TYPE_TEACHER}&status=${MEMBER_STATUS_NORMAL}"
                                                    data-placeholder="请输入账号或姓名或学工号">
                                                <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                            </select>
                                        </div>
                                        <div class="clearfix form-actions center">
                                            <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i>
                                                查找</a>
                                            <c:if test="${_query}">&nbsp;
                                                <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                        data-querystr="cls=${param.cls}&stage=${param.stage}&type=${type}">
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
        <div id="body-content-view"></div>
    </div>
</div>
<script>
    $("#jqGrid").jqGrid({
        rownumbers: true,
        multiselect: false,
        url: '${ctx}/pcsPrParty_candidate_data?callback=?&stage=${param.stage}&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {
                label: '党代表类型', name: 'type', width: 150, formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '-';
                return _cMap.PCS_PR_TYPE_MAP[cellvalue]
            }
            },
            {label: '工作证号', name: 'code', width: 120, frozen: true},
            {label: '被推荐人姓名', name: 'realname', width: 150, frozen: true},
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
                formatoptions: {newformat: 'Y.m'}
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
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $.register.user_select($('#searchForm select[name=userId]'));
</script>