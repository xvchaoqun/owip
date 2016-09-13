<%@ page import="sys.constants.SystemConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-body">
    <!-- PAGE CONTENT BEGINS -->
    <div class="widget-box transparent" id="view-box">
        <div class="widget-header">
            <h4 class="widget-title lighter smaller">
                <a href="javascript:" class="closeView btn btn-mini btn-xs btn-success">
                    <i class="ace-icon fa fa-backward"></i>
                    返回</a>
            </h4>
            <div class="widget-toolbar no-border">
                <ul class="nav nav-tabs">
                    <li class="active">
                        <a href="javascript:" data-url="${ctx}/sysUser_view?userId=${param.userId}">账号详情</a>
                    </li>
                </ul>
            </div>
        </div>
        <div class="widget-body">
            <div class="widget-main padding-4">
                <div class="tab-content padding-8">

                    <div class="col-xs-offset-1 col-xs-10">

                        <div class="page-header">
                            <h1>
                                <i class="fa fa-user"></i>
                                ${sysUser.realname}
                            </h1>
                        </div>
                        <div class="row">
                        <div class="col-xs-6">
                        <div class="profile-user-info profile-user-info-striped">
                            <div class="profile-info-row">
                                <div class="profile-info-name"> 所在单位 </div>

                                <div class="profile-info-value">
                                    <span class="editable" >${unit}</span>
                                </div>
                            </div>
                            <div class="profile-info-row">
                                <div class="profile-info-name">  ${(sysUser.type==USER_TYPE_JZG)?"教工号":"学号"} </div>

                                <div class="profile-info-value">
                                    <span class="editable" id="username">${sysUser.code}

                                    </span>
                                </div>
                            </div>

                            <div class="profile-info-row">
                                <div class="profile-info-name">  性别 </div>

                                <div class="profile-info-value">
                                    <span class="editable" >${GENDER_MAP.get(sysUser.gender)}</span>
                                </div>
                            </div>

                            <div class="profile-info-row">
                                <div class="profile-info-name"> 出生年月 </div>

                                <div class="profile-info-value">
                                    <span class="editable"  >${cm:formatDate(sysUser.birth,'yyyy-MM-dd')}</span>
                                </div>
                            </div>

                            <div class="profile-info-row">
                                <div class="profile-info-name"> 身份证 </div>

                                <div class="profile-info-value">
                                    <span class="editable"  >${sysUser.idcard}</span>
                                </div>
                            </div>
                            <div class="profile-info-row">
                                <div class="profile-info-name"> 手机 </div>

                                <div class="profile-info-value">
                                    <span class="editable" >${sysUser.mobile}</span>
                                </div>
                            </div>
                            <div class="profile-info-row">
                                <div class="profile-info-name">邮箱 </div>

                                <div class="profile-info-value">
                                    <span class="editable" >${sysUser.email}</span>
                                </div>
                            </div>
                            <div class="profile-info-row">
                                <div class="profile-info-name">系统角色 </div>
                                <div class="profile-info-value">
                                    <span class="editable" >
                                        <c:forEach items="${fn:split(sysUser.roleIds,',')}" var="id" varStatus="vs">
                                            ${roleMap.get(cm:parseInt(id)).description}
                                            <c:if test="${!vs.last}">,</c:if>
                                        </c:forEach>
                                    </span>
                                </div>
                            </div>
                            <c:if test="${fn:length(adminPartyIdList)>0}">
                                <div class="profile-info-row">
                                    <div class="profile-info-name">管理分党委</div>
                                    <div class="profile-info-value">
                                    <span class="editable" >
                                        <c:forEach items="${adminPartyIdList}" var="partyId" varStatus="vs">
                                            ${partyMap.get(partyId).name}
                                            <c:if test="${!vs.last}">,</c:if>
                                        </c:forEach>
                                    </span>
                                    </div>
                                </div>
                            </c:if>
                            <c:if test="${fn:length(adminBranchIdList)>0}">
                                <div class="profile-info-row">
                                    <div class="profile-info-name">管理党支部</div>
                                    <div class="profile-info-value">
                                    <span class="editable" >
                                        <c:forEach items="${adminBranchIdList}" var="branchId" varStatus="vs">
                                            <c:set var="branch" value="${branchMap.get(branchId)}"/>
                                            ${partyMap.get(branch.partyId).name}-${branch.name}
                                            <c:if test="${!vs.last}">,</c:if>
                                        </c:forEach>
                                    </span>
                                    </div>
                                </div>
                            </c:if>

                        </div>
                        </div>
                            <div>
                                <img src="${ctx}/qrcode?content=${sysUser.code}"/>
                            </div>
                        </div>
                        <div class="clearfix form-actions center">

                            <button class="closeView btn" type="button">
                                <i class="ace-icon fa fa-undo"></i>
                                返回
                            </button>
                        </div>
                    </div>


                </div>
            </div><!-- /.widget-main -->
        </div><!-- /.widget-body -->
    </div><!-- /.widget-box -->
</div>
