<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/member/constants.jsp" %>

<c:set var="_query" value="${not empty param.userId
            ||not empty param.partyId ||not empty param.branchId ||not empty param.growStatus ||not empty param.positiveStatus || not empty param.code || not empty param.sort}"/>
<div class="jqgrid-vertical-offset buttons">
    <c:if test="${stage>OW_APPLY_STAGE_OUT }">
        <button class="jqEditBtn btn btn-primary btn-sm"
                data-url="${ctx}/memberApply_au"
                data-open-by="page"
                data-id-name="userId"
                data-querystr="&stage=${stage}&op=update">
            <i class="fa fa-edit"></i> 修改信息
        </button>
    </c:if>
    <shiro:hasPermission name="memberBaseInfo:edit">
        <button class="jqOpenViewBtn btn btn-info btn-sm tooltip-success"
                data-url="${ctx}/memberBaseInfo_au"
                data-open-by="page" data-id-name="userId"
                data-rel="tooltip" data-placement="top"
                title="修改账号基本信息">
            <i class="fa fa-info-circle"></i> 修改基础信息
        </button>
    </shiro:hasPermission>
    <c:if test="${stage==OW_APPLY_STAGE_DENY }">
        <button class="jqBatchBtn btn btn-success btn-sm"
                data-url="${ctx}/memberApply_batchApply"
                data-title="提交"
                data-msg="确定提交申请（已选{0}条记录）？"
                data-grid-id="#jqGrid">
            <i class="fa fa-check-circle-o"></i> 提交申请
        </button>
    </c:if>
    <c:if test="${stage==OW_APPLY_STAGE_DENY || stage==OW_APPLY_STAGE_REMOVE}">
        <button class="jqBatchBtn btn btn-danger btn-sm"
                data-url="${ctx}/memberApply_batchDel"
                data-title="删除"
                data-msg="确定删除申请（已选{0}条记录）？（所有相关操作记录将全部删除，不可恢复，请谨慎操作。）"
                data-grid-id="#jqGrid">
            <i class="fa fa-times"></i> 删除
        </button>
    </c:if>
    <c:choose>
        <c:when test="${stage==OW_APPLY_STAGE_INIT || stage==OW_APPLY_STAGE_PASS}">
            <button id="applyBtn" ${applyCount>0?'':'disabled'}
                    class="jqOpenViewBtn btn btn-success btn-sm"
                    data-url="${ctx}/memberApply_approval"
                    data-open-by="page"
                    data-querystr="&type=${type}&stage=${OW_APPLY_STAGE_INIT}"
                    data-need-id="false"
                    data-id-name="userId"
                    data-count="${applyCount}">
                <i class="fa fa-sign-in"></i> 支部审核（${applyCount}）
            </button>
            <button id="activeBtn" ${activeCount>0?'':'disabled'}
                    class="jqOpenViewBtn btn btn-warning btn-sm"
                    data-url="${ctx}/memberApply_approval"
                    data-open-by="page"
                    data-querystr="&type=${type}&stage=${OW_APPLY_STAGE_PASS}"
                    data-need-id="false"
                    data-id-name="userId"
                    data-count="${activeCount}">
                <i class="fa fa-sign-in"></i>
                支部确定为入党积极分子（${activeCount}）
            </button>
        </c:when>
        <c:when test="${stage==OW_APPLY_STAGE_ACTIVE}">

            <c:if test="${_p_contactUsers_count>0}">
            <button class="jqOpenViewBatchBtn btn btn-warning btn-sm"
                    data-url="${ctx}/apply_active_contact">
                <i class="fa fa-users"></i>
                确定培养联系人
            </button>
            </c:if>
            <button id="candidateBtn" ${candidateCount>0?'':'disabled'}
                    class="jqOpenViewBtn btn btn-success btn-sm"
                    data-url="${ctx}/memberApply_approval"
                    data-open-by="page"
                    data-querystr="&type=${type}&stage=${OW_APPLY_STAGE_ACTIVE}&status=-1"
                    data-need-id="false"
                    data-id-name="userId"
                    data-count="${candidateCount}">
                <i class="fa fa-sign-in"></i>
                支部确定为发展对象（${candidateCount}）
            </button>
            <shiro:hasAnyRoles
                    name="${ROLE_ODADMIN},${ROLE_PARTYADMIN}">
                <button id="candidateCheckBtn" ${candidateCheckCount>0?'':'disabled'}
                        class="jqOpenViewBtn btn btn-warning btn-sm"
                        data-url="${ctx}/memberApply_approval"
                        data-open-by="page"
                        data-querystr="&type=${type}&stage=${OW_APPLY_STAGE_ACTIVE}&status=0"
                        data-need-id="false"
                        data-id-name="userId"
                        data-count="${candidateCheckCount}">
                    <i class="fa fa-sign-in"></i> ${_p_partyName}审核（${candidateCheckCount}）
                </button>
            </shiro:hasAnyRoles>
        </c:when>
        <c:when test="${stage==OW_APPLY_STAGE_CANDIDATE}">

            <c:if test="${_p_sponsorUsers_count>0}">
            <button class="jqOpenViewBatchBtn btn btn-warning btn-sm"
                    data-url="${ctx}/apply_candidate_sponsor">
                <i class="fa fa-users"></i>
                确定入党介绍人
            </button>
            </c:if>
            <button id="planBtn" ${planCount>0?'':'disabled'}
                    class="jqOpenViewBtn btn btn-success btn-sm"
                    data-url="${ctx}/memberApply_approval"
                    data-open-by="page"
                    data-querystr="&type=${type}&stage=${OW_APPLY_STAGE_CANDIDATE}&status=-1"
                    data-need-id="false"
                    data-id-name="userId"
                    data-count="${planCount}">
                <i class="fa fa-sign-in"></i> <c:if test="${_ignore_plan_and_draw}">支部发展为预备党员</c:if><c:if test="${!_ignore_plan_and_draw}">支部列入发展计划</c:if> （${planCount}）
            </button>
            <shiro:hasAnyRoles
                    name="${ROLE_ODADMIN},${ROLE_PARTYADMIN}">
                <button id="planCheckBtn" ${planCheckCount>0?'':'disabled'}
                        class="jqOpenViewBtn btn btn-warning btn-sm"
                        data-url="${ctx}/memberApply_approval"
                        data-open-by="page"
                        data-querystr="&type=${type}&stage=${OW_APPLY_STAGE_CANDIDATE}&status=0"
                        data-need-id="false"
                        data-id-name="userId"
                        data-count="${planCheckCount}">
                    <i class="fa fa-sign-in"></i> ${_p_partyName}审核（${planCheckCount}）
                </button>
            </shiro:hasAnyRoles>
        </c:when>
        <c:when test="${stage==OW_APPLY_STAGE_PLAN&&!_ignore_plan_and_draw}">
            <shiro:hasAnyRoles
                    name="${ROLE_ODADMIN},${ROLE_PARTYADMIN}">
                <button id="drawBtn" ${drawCount>0?'':'disabled'}
                        class="jqOpenViewBtn btn btn-warning btn-sm"
                        data-url="${ctx}/memberApply_approval"
                        data-open-by="page"
                        data-querystr="&type=${type}&stage=${OW_APPLY_STAGE_PLAN}&status=-1"
                        data-need-id="false"
                        data-id-name="userId"
                        data-count="${drawCount}">
                    <i class="fa fa-sign-in"></i> ${_p_partyName}领取志愿书（${drawCount}）
                </button>
            </shiro:hasAnyRoles>
            <%-- <button id="drawCheckCount" ${drawCheckCount>0?'':'disabled'}
                     class="jqOpenViewBtn btn btn-warning btn-sm"
                     data-url="${ctx}/memberApply_approval"
                     data-open-by="page"
                     data-querystr="&type=${type}&stage=${OW_APPLY_STAGE_PLAN}&status=0"
                     data-need-id="false"
                     data-id-name="userId"
                     data-count="${drawCheckCount}">
                 <i class="fa fa-sign-in"></i> ${_p_partyName}审核（${drawCheckCount}）
             </button>--%>
        </c:when>
        <c:when test="${stage==OW_APPLY_STAGE_DRAW&&!_ignore_plan_and_draw}">
            <c:if test="${_p_draw_od_check}">
            <shiro:hasAnyRoles name="${ROLE_ADMIN},${ROLE_ODADMIN}">
                <button id="growOdCheckCount" ${growOdCheckCount>0?'':'disabled'}
                        class="jqOpenViewBtn btn btn-danger btn-sm"
                        data-url="${ctx}/memberApply_approval"
                        data-open-by="page"
                        data-querystr="&type=${type}&stage=${OW_APPLY_STAGE_DRAW}&status=-1"
                        data-need-id="false"
                        data-id-name="userId"
                        data-count="${growOdCheckCount}">
                    <i class="fa fa-sign-in"></i>
                    组织部审核（${growOdCheckCount}）
                </button>
            </shiro:hasAnyRoles>
            </c:if>
            <button id="growBtn" ${growCount>0?'':'disabled'}
                    class="jqOpenViewBtn btn btn-success btn-sm"
                    data-url="${ctx}/memberApply_approval"
                    data-open-by="page"
                    data-querystr="&type=${type}&stage=${OW_APPLY_STAGE_DRAW}&status=2"
                    data-need-id="false"
                    data-id-name="userId"
                    data-count="${growCount}">
                <i class="fa fa-sign-in"></i> 支部发展为预备党员（${growCount}）
            </button>
            <shiro:hasAnyRoles
                    name="${ROLE_ODADMIN},${ROLE_PARTYADMIN}">
                <button id="growCheckBtn" ${growCheckCount>0?'':'disabled'}
                        class="jqOpenViewBtn btn btn-warning btn-sm"
                        data-url="${ctx}/memberApply_approval"
                        data-open-by="page"
                        data-querystr="&type=${type}&stage=${OW_APPLY_STAGE_DRAW}&status=0"
                        data-need-id="false"
                        data-id-name="userId"
                        data-count="${growCheckCount}">
                    <i class="fa fa-sign-in"></i> ${_p_partyName}审核（${growCheckCount}）
                </button>
            </shiro:hasAnyRoles>
            <%-- <button id="growCheckCount" ${growCheckCount>0?'':'disabled'}
                     class="jqOpenViewBtn btn btn-warning btn-sm"
                     data-url="${ctx}/memberApply_approval"
                     data-open-by="page"
                     data-querystr="&type=${type}&stage=${OW_APPLY_STAGE_DRAW}&status=0"
                     data-need-id="false"
                     data-id-name="userId"
                     data-count="${growCheckCount}">
                 <i class="fa fa-sign-in"></i> ${_p_partyName}审核（${growCheckCount}）
             </button>--%>

        </c:when>
        <c:when test="${stage==OW_APPLY_STAGE_GROW}">

            <c:if test="${_p_growContactUsers_count>0}">
            <button class="jqOpenViewBatchBtn btn btn-warning btn-sm"
                    data-url="${ctx}/apply_grow_contact">
                <i class="fa fa-users"></i>
                确定培养联系人
            </button>
            </c:if>

            <button id="positiveBtn" ${positiveCount>0?'':'disabled'}
                    class="jqOpenViewBtn btn btn-success btn-sm"
                    data-url="${ctx}/memberApply_approval"
                    data-open-by="page"
                    data-querystr="&type=${type}&stage=${OW_APPLY_STAGE_GROW}&status=-1"
                    data-need-id="false"
                    data-id-name="userId"
                    data-count="${positiveCount}">
                <i class="fa fa-sign-in"></i>
                支部预备党员转正（${positiveCount}）
            </button>
            <shiro:hasAnyRoles
                    name="${ROLE_ODADMIN},${ROLE_PARTYADMIN}">
                <button id="positiveCheckBtn" ${positiveCheckCount>0?'':'disabled'}
                        class="jqOpenViewBtn btn btn-warning btn-sm"
                        data-url="${ctx}/memberApply_approval"
                        data-open-by="page"
                        data-querystr="&type=${type}&stage=${OW_APPLY_STAGE_GROW}&status=0"
                        data-need-id="false"
                        data-id-name="userId"
                        data-count="${positiveCheckCount}">
                    <i class="fa fa-sign-in"></i> ${_p_partyName}审核（${positiveCheckCount}）
                </button>
            </shiro:hasAnyRoles>
            <shiro:hasAnyRoles name="${ROLE_ADMIN},${ROLE_ODADMIN}">
                <button id="positiveOdCheckBtn" ${positiveOdCheckCount>0?'':'disabled'}
                        class="jqOpenViewBtn btn btn-danger btn-sm"
                        data-url="${ctx}/memberApply_approval"
                        data-open-by="page"
                        data-querystr="&type=${type}&stage=${OW_APPLY_STAGE_GROW}&status=1"
                        data-need-id="false"
                        data-id-name="userId"
                        data-count="${positiveOdCheckCount}">
                    <i class="fa fa-sign-in"></i>
                    组织部审核（${positiveOdCheckCount}）
                </button>
            </shiro:hasAnyRoles>
        </c:when>
    </c:choose>

    <button class="jqOpenViewBtn btn btn-info btn-sm"
            data-url="${ctx}/applyApprovalLog"
            data-querystr="&type=<%=OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY%>"
            data-open-by="page">
        <i class="fa fa-sign-in"></i> 查看操作记录
    </button>
    <shiro:hasAnyRoles name="${ROLE_ODADMIN},${ROLE_PARTYADMIN}">
        <c:if test="${stage>=OW_APPLY_STAGE_INIT}">
            <button class="jqOpenViewBatchBtn btn btn-danger btn-sm"
                    data-url="${ctx}/memberApply_back"
                    data-querystr="stage=${stage}">
                <i class="fa fa-reply-all"></i> 退回申请（批量）
            </button>
        </c:if>
        <c:if test="${stage>=OW_APPLY_STAGE_INIT && stage<OW_APPLY_STAGE_GROW}">
            <button class="jqOpenViewBatchBtn btn btn-warning btn-sm"
                    data-url="${ctx}/memberApply_remove"
                    data-querystr="stage=${stage}&isRemove=1">
                <i class="fa fa-minus"></i> 移除（批量）
            </button>
        </c:if>
        <c:if test="${stage==OW_APPLY_STAGE_REMOVE}">
            <button class="jqOpenViewBatchBtn btn btn-warning btn-sm"
                    data-url="${ctx}/memberApply_remove"
                    data-querystr="isRemove=0">
                <i class="fa fa-reply"></i> 撤销移除（批量）
            </button>
        </c:if>

        <c:if test="${stage>=OW_APPLY_STAGE_INIT && stage<OW_APPLY_STAGE_GROW}">
            <a href="javascript:;"
               class="jqEditBtn btn btn-danger btn-sm"
               data-url="${ctx}/memberApply_changeCode"
               data-id-name="userId">
                <i class="fa fa-refresh"></i> 更换学工号</a>
            <a href="javascript:;"
               class="jqEditBtn btn btn-danger btn-sm"
               data-url="${ctx}/memberApply_changeParty"
               data-width="800"
               data-id-name="userId">
                <i class="fa fa-refresh"></i> 更换党组织</a>
        </c:if>
    </shiro:hasAnyRoles>
</div>
<div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
    <div class="widget-header">
        <h4 class="widget-title">搜索</h4><span
            class="widget-note">${note_searchbar}</span>
        <div class="widget-toolbar">
            <a href="javascript:;" data-action="collapse">
                <i class="ace-icon fa fa-chevron-${_query?'up':'down'}"></i>
            </a>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main no-padding">
            <form class="form-inline search-form" id="searchForm">
                <div class="form-group">
                    <label>用户</label>
                    <div class="input-group">
                        <input type="hidden" name="cls"
                               value="${cls}">
                        <input type="hidden" name="type"
                               value="${type}">
                        <input type="hidden" name="stage"
                               value="${stage}">
                        <select data-rel="select2-ajax"
                                data-ajax-url="${ctx}/sysUser_selects"
                                name="userId"
                                data-placeholder="请输入账号或姓名或学工号">
                            <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label>${_p_partyName}</label>

                    <select class="form-control" data-width="350"
                            data-rel="select2-ajax"
                            data-ajax-url="${ctx}/party_selects?auth=1"
                            name="partyId" data-placeholder="请选择">
                        <option value="${party.id}"
                                delete="${party.isDeleted}">${party.name}</option>
                    </select>
                </div>

                <div class="form-group"
                     style="${(empty branch)?'display: none':''}"
                     id="branchDiv">
                    <label>党支部</label>
                    <select class="form-control"
                            data-rel="select2-ajax"
                            data-ajax-url="${ctx}/branch_selects?auth=1"
                            name="branchId"
                            data-placeholder="请选择党支部">
                        <option value="${branch.id}"
                                delete="${branch.isDeleted}">${branch.name}</option>
                    </select>
                </div>
                <script>
                    $.register.party_branch_select($("#searchForm"), "branchDiv",
                        '${cm:getMetaTypeByCode("mt_direct_branch").id}', "${party.id}", "${party.classId}");
                </script>
                <c:if test="${stage==OW_APPLY_STAGE_DRAW}">
                    <div class="form-group">
                        <label>状态</label>
                        <div class="input-group">
                            <select name="growStatus"
                                    data-rel="select2"
                                    data-placeholder="请选择">
                                <option></option>
                                <c:if test="${_p_draw_od_check}">
                                <option value="-1">待组织部审核</option>
                                    </c:if>
                                <option value="2">
                                    待支部发展为预备党员
                                </option>
                                <option value="0">
                                    支部已提交，待${_p_partyName}审核
                                </option>
                            </select>
                            <script>
                                $("#searchForm select[name=growStatus]").val("${param.growStatus}");
                            </script>
                        </div>
                    </div>
                </c:if>
                <c:if test="${stage>=OW_APPLY_STAGE_DRAW&&!_ignore_plan_and_draw}">
                    <div class="form-group">
                        <label>志愿书编码</label>
                        <input class="form-control search-query"
                               name="applySn" type="text"
                               value="${param.applySn}"
                               placeholder="请输入">
                    </div>
                </c:if>
                <c:if test="${stage==OW_APPLY_STAGE_GROW}">
                    <div class="form-group">
                        <label>状态</label>
                        <div class="input-group">
                            <select name="positiveStatus"
                                    data-rel="select2"
                                    data-placeholder="请选择">
                                <option></option>
                                <option value="-1">待支部提交预备党员转正
                                </option>
                                <option value="0">
                                    支部已提交，待${_p_partyName}审核
                                </option>
                                <option value="1">${_p_partyName}已审核，待组织部审核</option>
                            </select>
                            <script>
                                $("#searchForm select[name=positiveStatus]").val("${param.positiveStatus}");
                            </script>
                        </div>
                    </div>
                </c:if>

                <div class="clearfix form-actions center">
                    <a class="jqSearchBtn btn btn-default btn-sm"><i
                            class="fa fa-search"></i> 查找</a>

                    <c:if test="${_query || not empty param.sort}">&nbsp;
                        <button type="button"
                                class="reloadBtn btn btn-warning btn-sm"
                                data-querystr="type=${type}&stage=${stage}">
                            <i class="fa fa-reply"></i> 重置
                        </button>
                    </c:if>
                </div>
            </form>
        </div>
    </div>
</div>
<div class="space-4"></div>
<table id="jqGrid" class="jqGrid table-striped"
       data-height-reduce="20" data-width-reduce="180"></table>
<div id="jqGridPager"></div>
<style>
    ul#stages > li {
        min-width: 170px;
    }

    <c:if test="${stage==0}">
    #jqGridPager_right {
        width: 150px;
    }

    </c:if>
</style>
<script>
    $("#jqGrid").jqGrid({
        /*multiboxonly:false,*/
        rownumbers: true,
        ondblClickRow: function () {
        },
        url: '${ctx}/memberApply_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            <c:if test="${stage<7}">
            {label: '状态', name: 'applyStatus', width: 200, frozen: true},
            </c:if>
            {label: '${type==OW_APPLY_TYPE_STU?"学生证号":"工作证号"}', name: 'user.code', width: 120, frozen: true},
            {
                label: '姓名', name: 'user.realname', formatter: function (cellvalue, options, rowObject) {
                    <c:if test="${stage<OW_APPLY_STAGE_GROW}">
                    return $.user(rowObject.user.id, cellvalue);
                    </c:if><c:if test="${stage>=OW_APPLY_STAGE_GROW}">
                    return $.member(rowObject.user.id, cellvalue);
                    </c:if>
                }, frozen: true
            },
            <c:if test="${stage<=OW_APPLY_STAGE_REMOVE}">
            {
                label: '所在阶段', name: '_stage', formatter: function (cellvalue, options, rowObject) {
                    if (rowObject.stage == 1) return '申请通过'
                    return _cMap.OW_APPLY_STAGE_MAP[rowObject.stage];
                }, frozen: true
            },
            </c:if>
            <c:if test="${stage==-4}">
            {label: '是否转出', name: 'memberStatus', width: 80, formatter: $.jgrid.formatter.TRUEFALSE},
            {label: '是否移除', name: 'isRemove', width: 80, formatter: $.jgrid.formatter.TRUEFALSE},
            </c:if>
            {
                label: '所在党组织',
                name: 'party',
                align: 'left',
                width: 550,
                formatter: function (cellvalue, options, rowObject) {
                    return $.party(rowObject.partyId, rowObject.branchId);
                }
            },

            <c:if test="${stage<OW_APPLY_STAGE_INIT}">
            {
                label: '提交书面申请书时间', name: 'applyTime', width: 180, formatter: function (cellvalue, options, rowObject) {
                    return $.memberApplyTime(${_memberApply_timeLimit}, cellvalue, rowObject.user.birth, 0);
                }
            },
            </c:if>
            <c:if test="${stage==OW_APPLY_STAGE_INIT || stage<=OW_APPLY_STAGE_OUT}">
            {
                label: '入党申请时间', name: 'joinApplyTime', width:160,formatter: function (cellvalue, options, rowObject) {
                    return $.memberApplyTime(${_memberApply_timeLimit}, cellvalue, rowObject.joinApplyTime, 0);
                }
            },
            {
                label: '提交书面申请书时间', name: 'applyTime', width: 160, formatter: function (cellvalue, options, rowObject) {
                    return $.memberApplyTime(${_memberApply_timeLimit}, cellvalue, rowObject.user.birth, 0);
                }
            },
            {
                label: '入党志愿书接收人', name: 'drawAcceptor', width: 130
            },

            {
                label: '确定为入党积极分子时间',
                name: 'activeTime',
                width: 190,
                formatter: function (cellvalue, options, rowObject) {
                    return $.memberApplyTime(${_memberApply_timeLimit}, cellvalue, rowObject.applyTime, 2);
                }
            },
            </c:if>
            <c:if test="${stage==OW_APPLY_STAGE_ACTIVE || stage<=OW_APPLY_STAGE_OUT}">
            {
                label: '确定为入党积极分子时间',
                name: 'activeTime',
                width: 200,
                formatter: function (cellvalue, options, rowObject) {
                    return $.memberApplyTime(${_memberApply_timeLimit}, cellvalue, rowObject.applyTime, 2);
                }
            },
            <c:if test="${_p_contactUsers_count>0}">
            {
                label: '培养联系人',
                name: 'contactUsers',
                width: 130
            },
            </c:if>
            {
                label: '确定为发展对象时间',
                name: 'candidateTime',
                width: 180,
                formatter: function (cellvalue, options, rowObject) {
                    return $.memberApplyTime(${_memberApply_timeLimit}, cellvalue, rowObject.activeTime, 3);
                }
            },
            <c:if test="${!_memberApply_needCandidateTrain}">
            {
                label: '参加培训时间',
                name: 'candidateTrainStartTime',
                width: 120, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}
            },
            </c:if>
            <c:if test="${_memberApply_needCandidateTrain}">
            {
                label: '参加培训时间',
                name: 'candidateTrainStartTime',
                width: 180,
                formatter: function (cellvalue, options, rowObject) {
                    return $.date(cellvalue, "yyyy.MM.dd") + "~" + $.date(rowObject.candidateTrainEndTime, "yyyy.MM.dd");
                }
            },{
                label: '结业考试成绩',
                name: 'candidateGrade',
                width: 130
            },
            </c:if>
            </c:if>
            <c:if test="${stage==OW_APPLY_STAGE_CANDIDATE || stage<=OW_APPLY_STAGE_OUT}">
            {
                label: '确定为发展对象时间',
                name: 'candidateTime',
                width: 180,
                formatter: function (cellvalue, options, rowObject) {
                    return $.memberApplyTime(${_memberApply_timeLimit}, cellvalue, rowObject.activeTime, 3);
                }
            },
            <c:if test="${_p_sponsorUsers_count>0}">
            {
                label: '入党介绍人',
                name: 'sponsorUsers',
                width: 130
            },
            </c:if>
            {
                label: '列入发展计划时间', name: 'planTime', width: 180, formatter: function (cellvalue, options, rowObject) {
                    return $.memberApplyTime(${_memberApply_timeLimit}, cellvalue, rowObject.candidateTime, 4);
                }
            },
            </c:if>
            <c:if test="${stage==OW_APPLY_STAGE_PLAN || stage<=OW_APPLY_STAGE_OUT}">
            {
                label: '列入发展计划时间', name: 'planTime', width: 180, formatter: function (cellvalue, options, rowObject) {
                    return $.memberApplyTime(${_memberApply_timeLimit}, cellvalue, rowObject.candidateTime, 4);
                }
            },
            </c:if>
            <c:if test="${stage>=OW_APPLY_STAGE_PLAN}">
            <shiro:hasPermission name="partyPublic:list">
            {
                label: '发展公示日期',
                name: 'growPublic.pubDate',
                width: 120,
                formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: 'Y.m.d'}
            },
            </shiro:hasPermission>
            </c:if>
            <c:if test="${stage>=OW_APPLY_STAGE_PLAN || stage<=OW_APPLY_STAGE_OUT}">
            {
                label: '领取志愿书时间', name: 'drawTime', width: 160, formatter: function (cellvalue, options, rowObject) {
                    return $.memberApplyTime(${_memberApply_timeLimit}, cellvalue, rowObject.planTime, 5, rowObject.growPublic);
                }
            },
            </c:if>
            <c:if test="${stage>=OW_APPLY_STAGE_DRAW || stage<=OW_APPLY_STAGE_OUT}">
            <shiro:hasPermission name="applySnRange:list">
            {label: '志愿书编码', name: 'applySn', width: 150},
            </shiro:hasPermission>
            {
                label: '发展时间', name: 'growTime', formatter: function (cellvalue, options, rowObject) {
                    return $.memberApplyTime(${_memberApply_timeLimit}, cellvalue, rowObject.drawTime, 6);
                }
            },
            </c:if>
            <c:if test="${stage==OW_APPLY_STAGE_GROW||stage==OW_APPLY_STAGE_POSITIVE || stage<=OW_APPLY_STAGE_OUT}">
            <shiro:hasPermission name="partyPublic:list">
            {
                label: '转正公示日期',
                name: 'positivePublic.pubDate',
                width: 120,
                formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: 'Y.m.d'}
            },
            </shiro:hasPermission>
            {
                label: '入党时间', name: 'growTime', formatter: function (cellvalue, options, rowObject) {
                    return $.memberApplyTime(${_memberApply_timeLimit}, cellvalue, rowObject.drawTime, 6);
                }
            },
            <c:if test="${_p_growContactUsers_count>0}">
            {
                label: '培养联系人',
                name: 'growContactUsers',
                width: 130
            },
            </c:if>
            {
                label: '转正时间', name: 'positiveTime', formatter: function (cellvalue, options, rowObject) {
                    return $.memberApplyTime(${_memberApply_timeLimit}, cellvalue, rowObject.growTime, 7);
                }
            },
            </c:if>
            <c:if test="${stage==OW_APPLY_STAGE_DENY}">
            {
                label: '党籍状态', name: 'status', width: 90, formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue)
                        return _cMap.MEMBER_STATUS_MAP[cellvalue];
                    return "--";
                }
            },
            </c:if>

            {hidden: true, name: 'stage'},
            {hidden: true, name: 'candidateStatus'},
            {hidden: true, name: 'planStatus'},
            {hidden: true, name: 'drawStatus'},
            {hidden: true, name: 'growStatus'},
            {hidden: true, name: 'positiveStatus'},
            {hidden: true, key: true, name: 'userId'}
        ],
        onSelectRow: function (id, status) {
            saveJqgridSelected("#" + this.id);
            //console.log(id)
            var ids = $(this).getGridParam("selarrrow");
            if (ids.length > 1) {
                $("*[data-count]").each(function () {
                    $(this).prop("disabled", true);
                })
            } else if (ids.length == 1) {

                var rowData = $(this).getRowData(ids[0]);
                $("#applyBtn").prop("disabled", rowData.stage != "${OW_APPLY_STAGE_INIT}");
                $("#activeBtn").prop("disabled", rowData.stage != "${OW_APPLY_STAGE_PASS}");
                $("#candidateBtn").prop("disabled", rowData.candidateStatus != '');
                $("#candidateCheckBtn").prop("disabled", rowData.candidateStatus == '');
                <c:if test="${_ignore_plan_and_draw}">
                    $("#planBtn").prop("disabled", rowData.growStatus != '');
                    $("#planCheckBtn").prop("disabled", rowData.growStatus == '');
                </c:if>
                <c:if test="${!_ignore_plan_and_draw}">
                    $("#planBtn").prop("disabled", rowData.planStatus != '');
                    $("#planCheckBtn").prop("disabled", rowData.planStatus == '');
                </c:if>
                //$("#drawBtn").prop("disabled", rowData.drawStatus != 0);
                $("#drawCheckBtn").prop("disabled", rowData.drawStatus != 1);
                $("#growBtn").prop("disabled", rowData.growStatus != 2);
                $("#growCheckBtn").prop("disabled", rowData.growStatus != 0);
                $("#growOdCheckBtn").prop("disabled", rowData.growStatus != '');
                $("#positiveBtn").prop("disabled", $.trim(rowData.positiveStatus) != '');
                $("#positiveCheckBtn").prop("disabled", parseInt(rowData.positiveStatus) != 0);
                $("#positiveOdCheckBtn").prop("disabled", parseInt(rowData.positiveStatus) != 1);
            } else {
                $("*[data-count]").each(function () {
                    $(this).prop("disabled", $(this).data("count") == 0);
                })
            }
        },
        onSelectAll: function (aRowids, status) {
            saveJqgridSelected("#" + this.id);
            $("*[data-count]").each(function () {
                $(this).prop("disabled", $(this).data("count") == 0);
            })
        }
    }).jqGrid("setFrozenColumns")
    $(window).triggerHandler('resize.jqGrid')

    $.initNavGrid("jqGrid", "jqGridPager");
    <c:if test="${stage==OW_APPLY_STAGE_INIT}">
    $("#jqGrid").navButtonAdd('#jqGridPager', {
        caption: "支部批量通过",
        btnbase: "jqBatchBtn btn btn-success btn-sm",
        buttonicon: "fa fa-check-circle-o",
        props: 'data-url="${ctx}/apply_pass" data-title="通过" data-msg="确定通过这{0}个申请吗？<div>（仅对状态为“待支部审核”的记录有效）</div>" data-callback="page_reload"'
    });

    $("#jqGrid").navButtonAdd('#jqGridPager', {
        caption: "支部批量不通过",
        btnbase: "jqBatchBtn btn btn-danger btn-sm",
        buttonicon: "fa fa-times-circle-o",
        props: 'data-url="${ctx}/apply_deny" data-title="不通过" data-msg="确定退回这{0}个申请吗？<div>（仅对状态为“待支部审核”的记录有效）</div>" data-callback="page_reload"'
    });

    $("#jqGrid").navButtonAdd('#jqGridPager', {
        caption: "支部确定为入党积极分子（批量）",
        btnbase: "jqOpenViewBatchBtn btn btn-warning btn-sm",
        buttonicon: "fa fa-check-circle-o",
        props: 'data-url="${ctx}/apply_active"'
    });
    </c:if>
    <c:if test="${stage==OW_APPLY_STAGE_ACTIVE}">
    $("#jqGrid").navButtonAdd('#jqGridPager', {
        caption: "支部确定为发展对象（批量）",
        btnbase: "jqOpenViewBatchBtn btn btn-success btn-sm",
        buttonicon: "fa fa-check-circle-o",
        props: 'data-url="${ctx}/apply_candidate"'
    });
    <shiro:hasAnyRoles name="${ROLE_PARTYADMIN},${ROLE_ODADMIN}">
    $("#jqGrid").navButtonAdd('#jqGridPager', {
        caption: "${_p_partyName}批量审核",
        btnbase: "jqBatchBtn btn btn-warning btn-sm",
        buttonicon: "fa fa-check-circle-o",
        props: 'data-url="${ctx}/apply_candidate_check" data-title="通过" data-msg="确定通过这{0}个申请吗？" data-callback="page_reload"'
    });
    </shiro:hasAnyRoles>
    </c:if>
    <c:if test="${stage==OW_APPLY_STAGE_CANDIDATE}">

    <c:if test="${_ignore_plan_and_draw}">
        $("#jqGrid").navButtonAdd('#jqGridPager', {
            caption: "支部发展为预备党员（批量）",
            btnbase: "jqOpenViewBatchBtn btn btn-success btn-sm",
            buttonicon: "fa fa-check-circle-o",
            props: 'data-url="${ctx}/apply_grow"'
        });
        <shiro:hasAnyRoles name="${ROLE_PARTYADMIN},${ROLE_ODADMIN}">
        $("#jqGrid").navButtonAdd('#jqGridPager', {
            caption: "${_p_partyName}批量审核",
            btnbase: "jqBatchBtn btn btn-warning btn-sm",
            buttonicon: "fa fa-check-circle-o",
            props: 'data-url="${ctx}/apply_grow_check" data-title="通过" data-msg="确定通过这{0}个申请吗？" data-callback="page_reload"'
        });
        </shiro:hasAnyRoles>
    </c:if>
    <c:if test="${!_ignore_plan_and_draw}">
        $("#jqGrid").navButtonAdd('#jqGridPager', {
            caption: "支部列入发展计划（批量）",
            btnbase: "jqOpenViewBatchBtn btn btn-success btn-sm",
            buttonicon: "fa fa-check-circle-o",
            props: 'data-url="${ctx}/apply_plan"'
        });

        <shiro:hasAnyRoles name="${ROLE_PARTYADMIN},${ROLE_ODADMIN}">
        $("#jqGrid").navButtonAdd('#jqGridPager', {
            caption: "${_p_partyName}批量审核",
            btnbase: "jqBatchBtn btn btn-warning btn-sm",
            buttonicon: "fa fa-check-circle-o",
            props: 'data-url="${ctx}/apply_plan_check" data-title="通过" data-msg="确定通过这{0}个申请吗？" data-callback="page_reload"'
        });
        </shiro:hasAnyRoles>
        </c:if>
    </c:if>
    <c:if test="${stage==OW_APPLY_STAGE_PLAN}">
    <shiro:hasAnyRoles name="${ROLE_PARTYADMIN},${ROLE_ODADMIN}">
    $("#jqGrid").navButtonAdd('#jqGridPager', {
        caption: "${_p_partyName}领取志愿书（批量）",
        btnbase: "jqOpenViewBatchBtn btn btn-warning btn-sm",
        buttonicon: "fa fa-check-circle-o",
        props: 'data-url="${ctx}/apply_draw"'
    });
    </shiro:hasAnyRoles>
    </c:if>

    <c:if test="${stage==OW_APPLY_STAGE_DRAW}">
    <c:if test="${_p_draw_od_check}">
    <shiro:hasRole name="${ROLE_ODADMIN}">
    $("#jqGrid").navButtonAdd('#jqGridPager', {
        caption: "组织部批量审核",
        btnbase: "jqOpenViewBatchBtn btn btn-primary btn-sm",
        buttonicon: "fa fa-check-circle-o",
        props: 'data-url="${ctx}/apply_grow_od_check"'
    });
    </shiro:hasRole>
    </c:if>
    $("#jqGrid").navButtonAdd('#jqGridPager', {
        caption: "支部发展为预备党员（批量）",
        btnbase: "jqOpenViewBatchBtn btn btn-success btn-sm",
        buttonicon: "fa fa-check-circle-o",
        props: 'data-url="${ctx}/apply_grow"'
    });
    <shiro:hasAnyRoles name="${ROLE_PARTYADMIN},${ROLE_ODADMIN}">
    $("#jqGrid").navButtonAdd('#jqGridPager', {
        caption: "${_p_partyName}批量审核",
        btnbase: "jqBatchBtn btn btn-warning btn-sm",
        buttonicon: "fa fa-check-circle-o",
        props: 'data-url="${ctx}/apply_grow_check" data-title="通过" data-msg="确定通过这{0}个申请吗？" data-callback="page_reload"'
    });
    </shiro:hasAnyRoles>
    </c:if>

    <c:if test="${stage==OW_APPLY_STAGE_GROW}">
    $("#jqGrid").navButtonAdd('#jqGridPager', {
        caption: "支部预备党员转正（批量）",
        btnbase: "jqOpenViewBatchBtn btn btn-success btn-sm",
        buttonicon: "fa fa-check-circle-o",
        props: 'data-url="${ctx}/apply_positive"'
    });
    <shiro:hasAnyRoles name="${ROLE_PARTYADMIN},${ROLE_ODADMIN}">
    $("#jqGrid").navButtonAdd('#jqGridPager', {
        caption: "${_p_partyName}批量审核",
        btnbase: "jqBatchBtn btn btn-warning btn-sm",
        buttonicon: "fa fa-check-circle-o",
        props: 'data-url="${ctx}/apply_positive_check" data-title="通过" data-msg="确定通过这{0}个申请吗？" data-callback="page_reload"'
    });
    </shiro:hasAnyRoles>
    <shiro:hasRole name="${ROLE_ODADMIN}">
    $("#jqGrid").navButtonAdd('#jqGridPager', {
        caption: "组织部批量审核",
        btnbase: "jqBatchBtn btn btn-primary btn-sm",
        buttonicon: "fa fa-check-circle-o",
        props: 'data-url="${ctx}/apply_positive_check2" data-title="通过" data-msg="确定通过这{0}个申请吗？" data-callback="page_reload"'
    });
    </shiro:hasRole>
    </c:if>

</script>