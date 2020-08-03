<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="MEMBER_OUT_STATUS_BACK" value="<%=MemberConstants.MEMBER_OUT_STATUS_BACK%>"/>
<c:set var="MEMBER_OUT_STATUS_PARTY_VERIFY" value="<%=MemberConstants.MEMBER_OUT_STATUS_PARTY_VERIFY%>"/>
<c:set var="MEMBER_OUT_STATUS_OW_VERIFY" value="<%=MemberConstants.MEMBER_OUT_STATUS_OW_VERIFY%>"/>
<c:set var="OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_OUT" value="<%=OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_OUT%>"/>

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
                                组织关系转出申请信息
                                <c:if test="${count>0}">
                                （总共${count}条记录未处理）
                                </c:if>
                            </h1>
                        </div>
                        <c:set var="user" value="${cm:getUserById(memberOut.userId)}"/>
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
                                        <div class="profile-info-name"> 党员本人联系电话 </div>

                                        <div class="profile-info-value">
                                            <span class="editable" ><t:mask src="${memberOut.phone}" type="mobile"/></span>
                                        </div>
                                    </div>
                                <div class="profile-info-row">
                                    <div class="profile-info-name"> 所在党组织 </div>

                                    <div class="profile-info-value">
                                    <span class="editable" style="height: 55px">
                                        <%--${partyMap.get(memberOut.partyId).name}
                                            <c:if test="${memberOut.branchId>0}">
                                                <br/>
                                                -${branchMap.get(memberOut.branchId).name}
                                            </c:if>--%>
                                        ${cm:displayParty(memberOut.partyId, memberOut.branchId)}
                                    </span>
                                    </div>
                                </div>

                                <div class="profile-info-row">
                                    <div class="profile-info-name"> 类别 </div>

                                    <div class="profile-info-value">
                                        <span class="editable" >${cm:getMetaType(memberOut.type).name}</span>
                                    </div>
                                </div>
                                    <div class="profile-info-row">
                                        <div class="profile-info-name"> 党费缴纳至年月 </div>

                                        <div class="profile-info-value">
                                            <span class="editable" >${cm:formatDate(memberOut.payTime,'yyyy-MM')}</span>
                                        </div>
                                    </div>
                                    <div class="profile-info-row">
                                        <div class="profile-info-name"> 介绍信有效期天数 </div>

                                        <div class="profile-info-value">
                                            <span class="editable" >${memberOut.validDays}</span>
                                        </div>
                                    </div>
                                    <div class="profile-info-row">
                                        <div class="profile-info-name"> 是否有回执 </div>

                                        <div class="profile-info-value">
                                            <span class="editable" >${memberOut.hasReceipt?"是":"否"}</span>
                                        </div>
                                    </div>

                            </div></div>
                            <div class="col-xs-6"><div class="profile-user-info profile-user-info-striped">
                                <div class="profile-info-row">
                                    <div class="profile-info-name"> 转入单位 </div>

                                    <div class="profile-info-value">
                                        <span class="editable" >${memberOut.toUnit}</span>
                                    </div>
                                </div>
                                <div class="profile-info-row">
                                    <div class="profile-info-name"> 转入单位抬头 </div>

                                    <div class="profile-info-value">
                                        <span class="editable" >${memberOut.toTitle}</span>
                                    </div>
                                </div>
                                <div class="profile-info-row">
                                    <div class="profile-info-name"> 转出单位 </div>

                                    <div class="profile-info-value">
                                        <span class="editable" >${memberOut.fromUnit}</span>
                                    </div>
                                </div>
                                <div class="profile-info-row">
                                    <div class="profile-info-name"> 转出单位地址 </div>

                                    <div class="profile-info-value">
                                        <span class="editable" >${memberOut.fromAddress}</span>
                                    </div>
                                </div>
                                <div class="profile-info-row">
                                    <div class="profile-info-name"> 转出单位联系电话 </div>

                                    <div class="profile-info-value">
                                        <span class="editable" >${memberOut.fromPhone}</span>
                                    </div>
                                </div>
                                <div class="profile-info-row">
                                    <div class="profile-info-name"> 转出单位传真 </div>

                                    <div class="profile-info-value">
                                        <span class="editable" >${memberOut.fromFax}</span>
                                    </div>
                                </div>
                                <div class="profile-info-row">
                                    <div class="profile-info-name"> 转出单位邮编 </div>

                                    <div class="profile-info-value">
                                        <span class="editable" >${memberOut.fromPostCode}</span>
                                    </div>
                                </div>

                                <div class="profile-info-row">
                                    <div class="profile-info-name"> 办理时间 </div>

                                    <div class="profile-info-value">
                                        <span class="editable" >${cm:formatDate(memberOut.handleTime,'yyyy-MM-dd')}</span>
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
                                      ${cm:formatDate(memberOut.applyTime,'yyyy-MM-dd')}
                                  </span>
                                </li>
                                <c:if test="${memberOut.status <= MEMBER_OUT_STATUS_BACK}">
                                    <li data-step="2" class="active">
                                        <span class="step">1</span>
                                        <span class="title">未通过申请</span>
                                    </li>
                                </c:if>

                                <li data-step="1"  class="${memberOut.status>=MEMBER_OUT_STATUS_PARTY_VERIFY?'complete':''}">
                                    <span class="step">1</span>
                                    <span class="title">${_p_partyName}审核</span>
                                    <%--<span class="subtitle">
                                            通过时间
                                    </span>--%>
                                </li>
                                <li data-step="2" class="${memberOut.status==MEMBER_OUT_STATUS_OW_VERIFY?'complete':''}">
                                    <span class="step">2</span>
                                    <span class="title">组织部审核</span>
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
                                            data-url="${ctx}/memberOut_approval?id=${last.id}&type=${param.type}&cls=${param.cls}"
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
                                        data-url="${ctx}/memberOut_approval?id=${next.id}&type=${param.type}&cls=${param.cls}"
                                        type="button">
                                    下一条 <i class="ace-icon fa fa-angle-double-right fa-lg "></i>
                                </button>
                                    </c:if>
                            </div>
                            <button ${isAdmin?'':'disabled'}  onclick="apply_pass(this,${memberOut.id}, ${param.type}, true)" class="btn btn-success">
                                <i class="fa fa-check"></i> 通过
                            </button>
                            &nbsp;&nbsp;
                            <button ${isAdmin?'':'disabled'}  onclick="apply_deny(${memberOut.id}, ${param.type}, true)" class="btn btn-danger">
                                <i class="fa fa-times"></i> 返回修改
                            </button>
                        </div>
                    </div>


                </div>
            </div><!-- /.widget-main -->
        </div><!-- /.widget-body -->
    </div><!-- /.widget-box -->
    <c:import url="/applyApprovalLogs?id=${memberOut.id}&type=${OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_OUT}"/>
</div>
