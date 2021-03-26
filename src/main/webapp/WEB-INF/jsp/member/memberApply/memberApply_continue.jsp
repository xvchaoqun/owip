<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/member/constants.jsp" %>

<c:set var="_query" value="${not empty param.userId ||not empty param.partyId ||not empty param.branchId ||not empty param.growStatus ||not empty param.positiveStatus || not empty param.code || not empty param.sort}"/>
<div class="jqgrid-vertical-offset buttons">

    <c:choose>
        <c:when test="${stage==OW_APPLY_STAGE_INIT || stage==OW_APPLY_STAGE_PASS}">
            <button class="jqBatchBtn btn btn-success btn-sm"
                    data-url="${ctx}/memberApply_continue_check"
                    data-msg="确定通过这{0}条申请？"
                    data-querystr="&isPass=1"
                    data-id-name="userId">
                <i class="fa fa-sign-in"></i> 审核通过
            </button>
            <button class="jqBatchBtn btn btn-danger btn-sm"
                    data-url="${ctx}/memberApply_continue_check"
                    data-msg="确定不通过这{0}条申请？"
                    data-querystr="&isPass=0"
                    data-id-name="userId">
                <i class="fa fa-sign-in"></i> 审核不通过
            </button>
        </c:when>
    </c:choose>
    <button class="jqOpenViewBtn btn btn-info btn-sm"
            data-url="${ctx}/applyApprovalLog"
            data-querystr="&type=<%=OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY%>"
            data-open-by="page">
        <i class="fa fa-search"></i> 操作记录
    </button>
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
                <input type="hidden" name="isApply" value="0"/>
                <div class="form-group">
                    <label>用户</label>
                    <div class="input-group">
                        <input type="hidden" name="cls" value="${cls}">
                        <input type="hidden" name="type" value="${type}">
                        <input type="hidden" name="stage" value="${stage}">
                        <select data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects"
                                name="userId" data-placeholder="请输入账号或姓名或学工号">
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
                                <option value="-1">待组织部审核</option>
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
                <c:if test="${stage>=OW_APPLY_STAGE_DRAW}">
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
        rownumbers: true,
        ondblClickRow: function () {
        },
        url: '${ctx}/memberApply_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            <c:if test="${stage<7}">
            {label: '申请继续培养阶段', name: 'applyStage', width: 200, formatter: function (cellvalue, options, rowObject) {
                return _cMap.OW_APPLY_CONTINUE_MAP[cellvalue]
                }},
            {label: '详情', name: '_detail', width: 80,formatter: function (cellvalue, options, rowObject) {
                return ('<button class="openView btn btn-warning btn-xs" ' +
                'data-url="${ctx}/user/memberApply?userId={0}&preview=1"><i class="fa fa-search"></i> 详情</button>')
                        .format(rowObject.userId);
            }},
            </c:if>
            {label: '${type==MEMBER_TYPE_STUDENT?"学生证号":"工作证号"}', name: 'user.code', width: 120, frozen: true},
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
                label: '提交书面申请书时间', name: 'applyTime', width: 180, formatter: function (cellvalue, options, rowObject) {
                    return $.memberApplyTime(${_memberApply_timeLimit}, cellvalue, rowObject.user.birth, 0);
                }
            },
            {
                label: '确定为入党积极分子时间',
                name: 'activeTime',
                width: 200,
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
            {
                label: '确定为发展对象时间',
                name: 'candidateTime',
                width: 180,
                formatter: function (cellvalue, options, rowObject) {
                    return $.memberApplyTime(${_memberApply_timeLimit}, cellvalue, rowObject.activeTime, 3);
                }
            },
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
            {label: '志愿书编码', name: 'applySn', width: 150},
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
        ]
    }).jqGrid("setFrozenColumns")
    $(window).triggerHandler('resize.jqGrid')
    $.initNavGrid("jqGrid", "jqGridPager");

</script>