<%@ page import="sys.constants.SystemConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-body">
    <!-- PAGE CONTENT BEGINS -->
    <div class="widget-box transparent">
        <div class="widget-header">
            <h4 class="widget-title lighter smaller">
                <a href="javascript:;" class="closeView reload btn btn-mini btn-xs btn-success">
                    <i class="ace-icon fa fa-backward"></i>
                    返回</a>
            </h4>
            <div class="widget-toolbar no-border">
                <ul class="nav nav-tabs">
                    <li class="active">
                        <a href="javascript:;">申请详情</a>
                    </li>
                </ul>
            </div>
        </div>
        <div class="widget-body">
            <div class="widget-main padding-4">
                <div class="tab-content padding-8">

                    <div class="col-xs-offset-1" style="padding-top: 50px; ">

                        <div class="page-header">
                            <h1>
                                <i class="fa fa-check-square-o"></i>
                                入党申请信息
                                <c:if test="${memberApply.stage!=APPLY_STAGE_DENY}">
                                <c:if test="${count>0}">
                                （总共${count}条记录未处理）
                                </c:if>
                                </c:if>
                            </h1>
                        </div>
                        <c:set var="user" value="${cm:getUserById(memberApply.userId)}"/>
                        <div class="profile-user-info profile-user-info-striped">
                            <div class="profile-info-row">
                                <div class="profile-info-name">  ${(user.type==USER_TYPE_JZG)?"教工号":"学号"} </div>

                                <div class="profile-info-value">
                                    <span class="editable" id="username">${user.code}</span>
                                </div>
                            </div>

                            <div class="profile-info-row">
                                <div class="profile-info-name">  姓名 </div>

                                <div class="profile-info-value">
                                    <span class="editable" id="realname">${user.realname}</span>
                                </div>
                            </div>

                            <div class="profile-info-row">
                                <div class="profile-info-name"> 提交申请书时间 </div>

                                <div class="profile-info-value">
                                    <span class="editable" id="age">${cm:formatDate(memberApply.applyTime,'yyyy-MM-dd')}</span>
                                </div>
                            </div>

                            <div class="profile-info-row">
                                <div class="profile-info-name"> 分党委 </div>

                                <div class="profile-info-value">
                                    <span class="editable" id="signup">${partyMap.get(memberApply.partyId).name}</span>
                                </div>
                            </div>
                            <c:if test="${memberApply.branchId>0}">
                                <div class="profile-info-row">
                                    <div class="profile-info-name"> 党支部 </div>

                                    <div class="profile-info-value">
                                        <span class="editable" id="login">${branchMap.get(memberApply.branchId).name}</span>
                                    </div>
                                </div>
                            </c:if>
                            <div class="profile-info-row">
                                <div class="profile-info-name"> 备注 </div>

                                <div class="profile-info-value">
                                    <span class="editable" id="about">${memberApply.remark}</span>
                                </div>
                            </div>
                        </div>
                        <div style="padding-top: 50px">
                            <ul class="steps">
                                <li data-step="1" class="complete">
                                    <span class="step">0</span>
        <span class="title">申请已提交</span>
      <span class="subtitle">
          ${cm:formatDate(memberApply.createTime,'yyyy-MM-dd')}
      </span>
                                </li>
                                <c:if test="${memberApply.stage==APPLY_STAGE_DENY}">
                                    <li data-step="2" class="active">
                                        <span class="step">1</span>
                                        <span class="title">未通过申请</span>
                                    </li>
                                </c:if>
                                <c:if test="${memberApply.stage!=APPLY_STAGE_DENY}">
                                <li data-step="1" <c:if test="${memberApply.stage>APPLY_STAGE_INIT}">class="complete"</c:if>>
                                    <span class="step">1</span>
                                    <span class="title">申请已通过</span>
                                    <c:if test="${memberApply.stage>=APPLY_STAGE_INIT}"><span class="subtitle">
                                            ${cm:formatDate(memberApply.passTime,'yyyy-MM-dd')}
                                    </span></c:if>
                                </li>
                                </c:if>
                                <li data-step="2" <c:if test="${memberApply.stage>APPLY_STAGE_PASS}">class="complete"</c:if>>
                                    <span class="step">2</span>
                                    <span class="title">入党积极分子</span>
                                    <c:if test="${memberApply.stage>=APPLY_STAGE_PASS}"> <span class="subtitle">
                                            ${cm:formatDate(memberApply.activeTime,'yyyy-MM-dd')}
                                    </span></c:if>
                                </li>

                                <li data-step="3" <c:if test="${memberApply.stage>APPLY_STAGE_ACTIVE}">class="complete"</c:if>>
                                    <span class="step">3</span>
                                    <span class="title">成为发展对象</span>
                                    <c:if test="${memberApply.stage>=APPLY_STAGE_ACTIVE}"> <span class="subtitle">
                                            ${cm:formatDate(memberApply.candidateTime,'yyyy-MM-dd')}
                                        <c:if test="${not empty memberApply.trainTime}">
                                         <br/>（参加培训时间 ${cm:formatDate(memberApply.trainTime,'yyyy-MM-dd')}）
                                        </c:if>
                                    </span>

                                    </c:if>
                                </li>

                                <li data-step="4" <c:if test="${memberApply.stage>APPLY_STAGE_CANDIDATE}">class="complete"</c:if>>
                                    <span class="step">4</span>
                                    <span class="title">列入发展计划</span>
                                    <c:if test="${memberApply.stage>=APPLY_STAGE_CANDIDATE}"> <span class="subtitle">
                                            ${cm:formatDate(memberApply.planTime,'yyyy-MM-dd')}
                                    </span></c:if>
                                </li>
                                <li data-step="5" <c:if test="${memberApply.stage>APPLY_STAGE_PLAN}">class="complete"</c:if>>
                                    <span class="step">5</span>
                                    <span class="title">领取志愿书</span>
                                    <c:if test="${memberApply.stage>=APPLY_STAGE_PLAN}"> <span class="subtitle">
                                            ${cm:formatDate(memberApply.drawTime,'yyyy-MM-dd')}
                                    </span></c:if>
                                </li>
                                <li data-step="6" <c:if test="${memberApply.stage>APPLY_STAGE_DRAW}">class="complete"</c:if>>
                                    <span class="step">6</span>
                                    <span class="title">预备党员</span>
                                    <c:if test="${memberApply.stage>=APPLY_STAGE_DRAW}">
        <span class="subtitle">
                ${cm:formatDate(memberApply.growTime,'yyyy-MM-dd')}
        </span>
                                    </c:if>
                                </li>

                                <li data-step="7" <c:if test="${memberApply.stage>APPLY_STAGE_GROW}">class="complete"</c:if>>
                                    <span class="step">7</span>
                                    <span class="title">正式党员</span>
                                    <c:if test="${memberApply.stage>=APPLY_STAGE_GROW}">
        <span class="subtitle">
                ${cm:formatDate(memberApply.positiveTime,'yyyy-MM-dd')}
        </span>
                                    </c:if>
                                </li>
                            </ul>
                        </div>
                        <div class="clearfix form-actions center">
                            <div class="pull-left">
                                <c:if test="${empty last}">
                                    <button id="last" class="btn disabled" type="button">
                                        <i class="ace-icon fa fa-angle-double-left fa-lg"></i>上一条
                                    </button>
                                </c:if>
                                <c:if test="${not empty last}">
                                    <button id="last" class="openView btn"
                                            data-url="${ctx}/memberApply_approval?type=${param.type}&userId=${last.userId}&stage=${param.stage}&status=${param.status}"
                                            type="button">
                                        <i class="ace-icon fa fa-angle-double-left fa-lg"></i>上一条
                                    </button>
                                </c:if>
                            </div>
                            <div class="pull-right">
                                <c:if test="${empty next}">
                                    <button id="next" class="btn disabled" type="button">
                                        下一条 <i class="ace-icon fa fa-angle-double-right fa-lg "></i>
                                    </button>
                                </c:if>
                                <c:if test="${not empty next}">
                                <button id="next" class="openView btn"
                                        data-url="${ctx}/memberApply_approval?type=${param.type}&userId=${next.userId}&stage=${param.stage}&status=${param.status}"
                                        type="button">
                                    下一条 <i class="ace-icon fa fa-angle-double-right fa-lg "></i>
                                </button>
                                    </c:if>
                            </div>
                            <c:choose>
                                <c:when test="${memberApply.stage==APPLY_STAGE_INIT}">
                                    <button ${isAdmin?'':'disabled'}  onclick="apply_pass(${memberApply.userId}, 1)" class="btn btn-success">
                                        <i class="fa fa-check"></i> 通过
                                    </button>
                                    &nbsp;&nbsp;&nbsp;&nbsp;
                                    <button ${isAdmin?'':'disabled'}  onclick="apply_deny(${memberApply.userId}, 1)" class="btn btn-danger">
                                        <i class="fa fa-trash"></i> 不通过
                                    </button>
                                </c:when>
                                <c:when test="${memberApply.stage==APPLY_STAGE_PASS}">
                                    <button ${isAdmin?'':'disabled'}  onclick="apply_active(${memberApply.userId}, 1)" class="btn btn-success">
                                        <i class="fa fa-check"></i> 确定为入党积极分子
                                    </button>
                                </c:when>
                                <c:when test="${memberApply.stage==APPLY_STAGE_ACTIVE}">
                                    <c:if test="${empty memberApply.candidateStatus}">
                                        <button ${isAdmin?'':'disabled'}  onclick="apply_candidate(${memberApply.userId}, 1)" class="btn btn-success">
                                            <i class="fa fa-check"></i> 确定为发展对象
                                        </button>
                                    </c:if>
                                    <c:if test="${memberApply.candidateStatus==0}">
                                        <button ${isAdmin?'':'disabled'}  onclick="apply_candidate_check(${memberApply.userId}, 1)" class="btn btn-success">
                                            <i class="fa fa-check"></i> 审核
                                        </button>
                                    </c:if>
                                </c:when>

                                <c:when test="${memberApply.stage==APPLY_STAGE_CANDIDATE}">
                                    <c:if test="${empty memberApply.planStatus}">
                                        <button ${isAdmin?'':'disabled'}  onclick="apply_plan(${memberApply.userId}, 1)" class="btn btn-success">
                                            <i class="fa fa-check"></i> 列入发展计划
                                        </button>
                                    </c:if>
                                    <c:if test="${memberApply.planStatus==0}">
                                        <button ${isAdmin?'':'disabled'} onclick="apply_plan_check(${memberApply.userId}, 1)" class="btn btn-success">
                                            <i class="fa fa-check"></i> 审核
                                        </button>
                                    </c:if>
                                </c:when>
                                <c:when test="${memberApply.stage==APPLY_STAGE_PLAN}">
                                    <c:if test="${empty memberApply.drawStatus}">
                                        <button ${isAdmin?'':'disabled'}  onclick="apply_draw(${memberApply.userId}, 1)" class="btn btn-success">
                                            <i class="fa fa-check"></i> 领取志愿书
                                        </button>
                                    </c:if>
                                    <%--<c:if test="${memberApply.drawStatus==0}">
                                        <button ${isAdmin?'':'disabled'}  onclick="apply_draw_check(${memberApply.userId}, 1)" class="btn btn-success">
                                            <i class="fa fa-check"></i> 审核
                                        </button>
                                    </c:if>--%>
                                </c:when>
                                <c:when test="${memberApply.stage==APPLY_STAGE_DRAW}">
                                    <c:if test="${empty memberApply.growStatus}">
                                        <button ${isAdmin?'':'disabled'}  onclick="apply_grow(${memberApply.userId}, 1)" class="btn btn-success">
                                            <i class="fa fa-check"></i> 发展为预备党员
                                        </button>
                                    </c:if>
                                    <c:if test="${memberApply.growStatus==0}">
                                        <button ${isAdmin?'':'disabled'}  onclick="apply_grow_check(${memberApply.userId}, 1)" class="btn btn-success">
                                            <i class="fa fa-check"></i> 审核
                                        </button>
                                    </c:if>
                                    <c:if test="${memberApply.growStatus==1}">
                                        <button ${isAdmin?'':'disabled'}  onclick="apply_grow_check2(${memberApply.userId}, 1)" class="btn btn-success">
                                            <i class="fa fa-check"></i> 组织部审核
                                        </button>
                                    </c:if>
                                </c:when>
                                <c:when test="${memberApply.stage==APPLY_STAGE_GROW}">
                                    <c:if test="${empty memberApply.positiveStatus}">
                                        <button ${isAdmin?'':'disabled'}  onclick="apply_positive(${memberApply.userId}, 1)" class="btn btn-success">
                                            <i class="fa fa-check"></i> 预备党员转正
                                        </button>
                                    </c:if>
                                    <c:if test="${memberApply.positiveStatus==0}">
                                        <button ${isAdmin?'':'disabled'}  onclick="apply_positive_check(${memberApply.userId}, 1)" class="btn btn-success">
                                            <i class="fa fa-check"></i> 审核
                                        </button>
                                    </c:if>
                                    <c:if test="${memberApply.positiveStatus==1}">
                                        <button ${isAdmin?'':'disabled'}  onclick="apply_positive_check2(${memberApply.userId}, 1)" class="btn btn-success">
                                            <i class="fa fa-check"></i> 组织部审核
                                        </button>
                                    </c:if>
                                </c:when>
                            </c:choose>
                        </div>
                    </div>


                </div>
            </div><!-- /.widget-main -->
        </div><!-- /.widget-body -->
    </div><!-- /.widget-box -->

<c:import url="/applyApprovalLogs?idName=userId&userId=${memberApply.userId}&type=${APPLY_APPROVAL_LOG_TYPE_MEMBER_APPLY}"/>
</div>