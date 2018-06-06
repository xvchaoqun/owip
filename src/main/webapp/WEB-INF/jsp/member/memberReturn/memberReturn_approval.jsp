<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="MEMBER_RETURN_STATUS_DENY" value="<%=MemberConstants.MEMBER_RETURN_STATUS_DENY%>"/>
<c:set var="MEMBER_RETURN_STATUS_BRANCH_VERIFY" value="<%=MemberConstants.MEMBER_RETURN_STATUS_BRANCH_VERIFY%>"/>
<c:set var="MEMBER_RETURN_STATUS_PARTY_VERIFY" value="<%=MemberConstants.MEMBER_RETURN_STATUS_PARTY_VERIFY%>"/>
<c:set var="OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_RETURN" value="<%=OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_RETURN%>"/>

<div class="modal-body">

    <div class="widget-box transparent">
        <div class="widget-header">
            <h4 class="widget-title lighter smaller">
                <a href="javascript:;" class="hideView btn btn-xs btn-success" data-url="${ctx}/memberReturn">
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
                                留学归国党员恢复组织关系申请信息
                                <c:if test="${count>0}">
                                （总共${count}条记录未处理）
                                </c:if>
                            </h1>
                        </div>
                        <c:set var="user" value="${cm:getUserById(memberReturn.userId)}"/>
                        <div class="col-xs-12">
                            <div class="col-xs-6">
                                <div class="profile-user-info profile-user-info-striped">
                                <div class="profile-info-row">
                                    <div class="profile-info-name">  ${(user.type==USER_TYPE_JZG)?"教工号":"学号"} </div>

                                    <div class="profile-info-value">
                                        <span class="editable">${user.code}</span>
                                    </div>
                                </div>

                                <div class="profile-info-row">
                                    <div class="profile-info-name">  姓名 </div>

                                    <div class="profile-info-value">
                                        <span class="editable">${user.realname}</span>
                                    </div>
                                </div>

                                <div class="profile-info-row">
                                    <div class="profile-info-name"> 所属组织机构 </div>

                                    <div class="profile-info-value">
                                    <span class="editable">
                                        ${cm:displayParty(memberReturn.partyId, memberReturn.branchId)}
                                    </span>
                                    </div>
                                </div>

                                <div class="profile-info-row">
                                    <div class="profile-info-name"> 提交书面申请书时间 </div>

                                    <div class="profile-info-value">
                                        <span class="editable" >${cm:formatDate(memberReturn.applyTime,'yyyy-MM-dd')}</span>
                                    </div>
                                </div>
                            </div></div>
                            <div class="col-xs-6"><div class="profile-user-info profile-user-info-striped">
                                <div class="profile-info-row">
                                    <div class="profile-info-name"> 确定为入党积极分子时间 </div>

                                    <div class="profile-info-value">
                                        <span class="editable" >${cm:formatDate(memberReturn.activeTime,'yyyy-MM-dd')}</span>
                                    </div>
                                </div>
                                <div class="profile-info-row">
                                    <div class="profile-info-name"> 确定为发展对象时间 </div>

                                    <div class="profile-info-value">
                                        <span class="editable" >${cm:formatDate(memberReturn.candidateTime,'yyyy-MM-dd')}</span>
                                    </div>
                                </div>
                                <div class="profile-info-row">
                                    <div class="profile-info-name"> 入党时间 </div>

                                    <div class="profile-info-value">
                                        <span class="editable" >${cm:formatDate(memberReturn.growTime,'yyyy-MM-dd')}</span>
                                    </div>
                                </div>
                                <div class="profile-info-row">
                                    <div class="profile-info-name"> 转正时间 </div>

                                    <div class="profile-info-value">
                                        <span class="editable" >${cm:formatDate(memberReturn.positiveTime,'yyyy-MM-dd')}</span>
                                    </div>
                                </div>
                                <div class="profile-info-row">
                                    <div class="profile-info-name"> 备注 </div>

                                    <div class="profile-info-value">
                                        <span class="editable" >${memberReturn.remark}</span>
                                    </div>
                                </div>

                            </div></div>
                        </div>

                        <div style="padding-top: 50px">
                            <ul class="steps">
                                <li data-step="1" class="complete">
                                    <span class="step">0</span>
                                    <span class="title">申请已提交</span>
                                  <span class="subtitle">
                                      ${cm:formatDate(memberReturn.createTime,'yyyy-MM-dd')}
                                  </span>
                                </li>
                                <c:if test="${memberReturn.status==MEMBER_RETURN_STATUS_DENY}">
                                    <li data-step="2" class="active">
                                        <span class="step">1</span>
                                        <span class="title">未通过申请</span>
                                    </li>
                                </c:if>

                                <li data-step="1"  class="${memberReturn.status>=MEMBER_RETURN_STATUS_BRANCH_VERIFY?'complete':''}">
                                    <span class="step">1</span>
                                    <span class="title">党支部审核</span>
                                    <%--<span class="subtitle">
                                            通过时间
                                    </span>--%>
                                </li>
                                <li data-step="2" class="${memberReturn.status==MEMBER_RETURN_STATUS_PARTY_VERIFY?'complete':''}">
                                    <span class="step">2</span>
                                    <span class="title">分党委审核</span>
                                <%--<span class="subtitle">
                                        通过时间
                                </span>--%>
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
                                            data-url="${ctx}/memberReturn_approval?id=${last.id}&type=${param.type}"
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
                                        data-url="${ctx}/memberReturn_approval?id=${next.id}&type=${param.type}"
                                        type="button">
                                    下一条 <i class="ace-icon fa fa-angle-double-right fa-lg "></i>
                                </button>
                                    </c:if>
                            </div>
                            <button ${isAdmin?'':'disabled'}  onclick="apply_pass(${memberReturn.id}, ${param.type}, true)" class="btn btn-success">
                                <i class="fa fa-check"></i> 通过
                            </button>
                            &nbsp;&nbsp;
                            <button ${isAdmin?'':'disabled'}  onclick="apply_deny(${memberReturn.id}, ${param.type}, true)" class="btn btn-danger">
                                <i class="fa fa-times"></i> 返回修改
                            </button>
                        </div>
                    </div>
                </div>
            </div><!-- /.widget-main -->
        </div><!-- /.widget-body -->
    </div><!-- /.widget-box -->
    <c:import url="/applyApprovalLogs?id=${memberReturn.id}&type=${OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_RETURN}"/>
</div>
