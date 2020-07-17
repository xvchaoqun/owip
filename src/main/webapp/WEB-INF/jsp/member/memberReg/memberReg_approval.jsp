<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="OW_APPLY_APPROVAL_LOG_TYPE_USER_REG" value="<%=OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_USER_REG%>"/>
<div class="modal-body">

    <div class="widget-box transparent">
        <div class="widget-header">
            <h4 class="widget-title lighter smaller">
                <a href="javascript:;" class="hashchange btn btn-xs btn-success">
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
                                用户注册申请信息
                                <c:if test="${count>0}">
                                （总共${count}条记录未处理）
                                </c:if>
                            </h1>
                        </div>
                        <div class="col-xs-12">
                            <div class="col-xs-6">
                                <div class="profile-user-info profile-user-info-striped">
                                    <div class="profile-info-row">
                                        <div class="profile-info-name">  注册账号 </div>

                                        <div class="profile-info-value">
                                            <span class="editable">${memberReg.username}</span>
                                        </div>
                                    </div>
                                    <div class="profile-info-row">
                                        <div class="profile-info-name">  类别 </div>
                                        <div class="profile-info-value">
                                            <span class="editable">${USER_TYPE_MAP.get(memberReg.type)}</span>
                                        </div>
                                    </div>
                                <div class="profile-info-row">
                                    <div class="profile-info-name">  ${(memberReg.type==USER_TYPE_JZG)?"教工号":"学号"} </div>

                                    <div class="profile-info-value">
                                        <span class="editable">${memberReg.code}</span>
                                    </div>
                                </div>

                                <div class="profile-info-row">
                                    <div class="profile-info-name"> 所在党组织 </div>

                                    <div class="profile-info-value">
                                    <span class="editable">
                                        ${cm:displayParty(memberReg.partyId, null)}
                                    </span>
                                    </div>
                                </div>

                            </div></div>
                            <div class="col-xs-6"><div class="profile-user-info profile-user-info-striped">
                                <div class="profile-info-row">
                                    <div class="profile-info-name">  真实姓名 </div>

                                    <div class="profile-info-value">
                                        <span class="editable">${memberReg.realname}</span>
                                    </div>
                                </div>
                                <div class="profile-info-row">
                                    <div class="profile-info-name"> 身份证号码 </div>

                                    <div class="profile-info-value">
                                        <span class="editable" >${memberReg.idcard}</span>
                                    </div>
                                </div>
                                <div class="profile-info-row">
                                    <div class="profile-info-name"> 手机号码 </div>

                                    <div class="profile-info-value">
                                        <span class="editable" >${memberReg.phone}</span>
                                    </div>
                                </div>
                                <div class="profile-info-row">
                                    <div class="profile-info-name"> 注册IP </div>

                                    <div class="profile-info-value">
                                        <span class="editable" >${memberReg.ip}</span>
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
                                      ${cm:formatDate(memberReg.createTime,'yyyy-MM-dd')}
                                  </span>
                                </li>
                                <c:set var="USER_REG_STATUS_DENY" value="<%=SystemConstants.USER_REG_STATUS_DENY%>"/>
                                <c:if test="${memberReg.status==USER_REG_STATUS_DENY}">
                                    <li data-step="2" class="active">
                                        <span class="step">1</span>
                                        <span class="title">未通过申请</span>
                                    </li>
                                </c:if>
                                <c:set var="USER_REG_STATUS_PASS" value="<%=SystemConstants.USER_REG_STATUS_PASS%>"/>
                                <li data-step="1"  class="${memberReg.status==USER_REG_STATUS_PASS?'complete':''}">
                                    <span class="step">1</span>
                                    <span class="title">${_p_partyName}审核</span>
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
                                            data-url="${ctx}/memberReg_approval?id=${last.id}&type=${param.type}"
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
                                        data-url="${ctx}/memberReg_approval?id=${next.id}"
                                        type="button">
                                    下一条 <i class="ace-icon fa fa-angle-double-right fa-lg "></i>
                                </button>
                                    </c:if>
                            </div>
                            <button ${isAdmin?'':'disabled'}  onclick="apply_pass(${memberReg.id}, true)" class="btn btn-success">
                                <i class="fa fa-check"></i> 通过
                            </button>
                            &nbsp;&nbsp;
                            <button ${isAdmin?'':'disabled'}  onclick="apply_deny(${memberReg.id}, true)" class="btn btn-danger">
                                <i class="fa fa-times"></i> 拒绝
                            </button>
                        </div>
                    </div>


                </div>
            </div><!-- /.widget-main -->
        </div><!-- /.widget-body -->
    </div><!-- /.widget-box -->
    <c:import url="/applyApprovalLogs?id=${memberReg.id}&type=${OW_APPLY_APPROVAL_LOG_TYPE_USER_REG}"/>
</div>
