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
                                流出党员申请信息
                                <c:if test="${count>0}">
                                （总共${count}条记录未处理）
                                </c:if>
                            </h1>
                        </div>
                        <c:set var="user" value="${cm:getUserById(memberOutflow.userId)}"/>
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
                                        ${partyMap.get(memberOutflow.partyId).name}
                                            <c:if test="${memberOutflow.branchId>0}">
                                                -${branchMap.get(memberOutflow.branchId).name}
                                            </c:if>
                                    </span>
                                    </div>
                                </div>

                                <div class="profile-info-row">
                                    <div class="profile-info-name"> 原职业 </div>

                                    <div class="profile-info-value">
                                        <span class="editable" >${jobMap.get(memberOutflow.originalJob).name}</span>
                                    </div>
                                </div>
                                <div class="profile-info-row">
                                    <div class="profile-info-name"> 外出流向 </div>

                                    <div class="profile-info-value">
                                        <span class="editable" >${flowDirectionMap.get(memberOutflow.direction).name}</span>
                                    </div>
                                </div>
                                <div class="profile-info-row">
                                    <div class="profile-info-name"> 流出时间 </div>

                                    <div class="profile-info-value">
                                        <span class="editable" >${cm:formatDate(memberOutflow.flowTime,'yyyy-MM-dd')}</span>
                                    </div>
                                </div>

                            </div></div>
                            <div class="col-xs-6"><div class="profile-user-info profile-user-info-striped">

                                <div class="profile-info-row">
                                    <div class="profile-info-name"> 流出省份 </div>

                                    <div class="profile-info-value">
                                        <span class="editable" >${locationMap.get(memberOutflow.province).name}</span>
                                    </div>
                                </div>
                                <div class="profile-info-row">
                                    <div class="profile-info-name"> 流出原因 </div>

                                    <div class="profile-info-value">
                                        <span class="editable" >${memberOutflow.reason}</span>
                                    </div>
                                </div>
                                <div class="profile-info-row">
                                    <div class="profile-info-name"> 是否持有《中国共产党流动党员活动证》 </div>

                                    <div class="profile-info-value">
                                        <span class="editable" >${memberOutflow.hasPapers?"是":"否"}</span>
                                    </div>
                                </div>
                                <div class="profile-info-row">
                                    <div class="profile-info-name"> 组织关系状态 </div>

                                    <div class="profile-info-value">
                                        <span class="editable" >${OR_STATUS_MAP.get(memberOutflow.orStatus)}</span>
                                    </div>
                                </div>
                                <div class="profile-info-row">
                                    <div class="profile-info-name"> 申请时间 </div>

                                    <div class="profile-info-value">
                                        <span class="editable" >${cm:formatDate(memberOutflow.createTime,'yyyy-MM-dd HH:mm')}</span>
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
                                      ${cm:formatDate(memberOutflow.createTime,'yyyy-MM-dd')}
                                  </span>
                                </li>
                                <c:if test="${memberOutflow.status==MEMBER_OUTFLOW_STATUS_BACK}">
                                    <li data-step="2" class="active">
                                        <span class="step">1</span>
                                        <span class="title">未通过申请</span>
                                    </li>
                                </c:if>

                                <li data-step="1"  class="${memberOutflow.status==MEMBER_OUTFLOW_STATUS_BRANCH_VERIFY?'complete':''}">
                                    <span class="step">1</span>
                                    <span class="title">支部审核</span>
                                    <%--<span class="subtitle">
                                            通过时间
                                    </span>--%>
                                </li>
                                <li data-step="2" class="${memberOutflow.status==MEMBER_OUTFLOW_STATUS_PARTY_VERIFY?'complete':''}">
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
                                            data-url="${ctx}/memberOutflow_approval?id=${last.id}&type=${param.type}"
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
                                        data-url="${ctx}/memberOutflow_approval?id=${next.id}&type=${param.type}"
                                        type="button">
                                    下一条 <i class="ace-icon fa fa-angle-double-right fa-lg "></i>
                                </button>
                                    </c:if>
                            </div>
                            <button ${isAdmin?'':'disabled'}  onclick="apply_pass(${memberOutflow.id}, ${param.type}, true)" class="btn btn-success">
                                <i class="fa fa-check"></i> 通过
                            </button>
                            &nbsp;&nbsp;
                            <button ${isAdmin?'':'disabled'}  onclick="apply_deny(${memberOutflow.id}, ${param.type}, true)" class="btn btn-danger">
                                <i class="fa fa-times"></i> 不通过
                            </button>
                        </div>
                    </div>


                </div>
            </div><!-- /.widget-main -->
        </div><!-- /.widget-body -->
    </div><!-- /.widget-box -->
    <c:import url="/applyApprovalLogs"/>
</div>
