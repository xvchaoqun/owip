<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-body">
    <!-- PAGE CONTENT BEGINS -->
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
                                ${MEMBER_STAY_TYPE_MAP.get(type)}申请组织关系暂留申请信息
                                <c:if test="${count>0}">
                                （总共${count}条记录未处理）
                                </c:if>
                            </h1>
                        </div>
                        <div class="col-xs-12">
                        <table class="center-block table table-bordered table-striped"
                               style="width: 900px; border-top: none; border-left: none;">
                           <jsp:include page="memberStay_table.jsp"/>
                        </table>
                        </div>
                        <div style="padding-top: 50px">
                            <ul class="steps">
                                <li data-step="1" class="complete">
                                    <span class="step">0</span>
                                    <span class="title">申请已提交</span>
                                  <span class="subtitle">
                                      ${cm:formatDate(memberStay.createTime,'yyyy-MM-dd')}
                                  </span>
                                </li>
                                <c:if test="${memberStay.status==MEMBER_STAY_STATUS_SELF_BACK || memberStay.status==MEMBER_STAY_STATUS_BACK}">
                                    <li data-step="2" class="active">
                                        <span class="step">1</span>
                                        <span class="title">未通过申请</span>
                                    </li>
                                </c:if>
                                <li data-step="1"  class="${memberStay.status>=MEMBER_STAY_STATUS_BRANCH_VERIFY?'complete':''}">
                                    <span class="step">1</span>
                                    <span class="title">支部审核</span>
                                    <%--<span class="subtitle">
                                            通过时间
                                    </span>--%>
                                </li>
                                <li data-step="2"  class="${memberStay.status>=MEMBER_STAY_STATUS_PARTY_VERIFY?'complete':''}">
                                    <span class="step">2</span>
                                    <span class="title">分党委审核</span>
                                    <%--<span class="subtitle">
                                            通过时间
                                    </span>--%>
                                </li>
                                <li data-step="3" class="${memberStay.status==MEMBER_STAY_STATUS_OW_VERIFY?'complete':''}">
                                    <span class="step">3</span>
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
                                            data-url="${ctx}/memberStay_approval?id=${last.id}&type=${type}&checkType=${param.checkType}&cls=${param.cls}"
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
                                        data-url="${ctx}/memberStay_approval?id=${next.id}&type=${type}&checkType=${param.checkType}&cls=${param.cls}"
                                        type="button">
                                    下一条 <i class="ace-icon fa fa-angle-double-right fa-lg "></i>
                                </button>
                                    </c:if>
                            </div>
                            <button ${isAdmin?'':'disabled'}  onclick="apply_pass(${memberStay.id}, ${param.checkType}, true)" class="btn btn-success">
                                <i class="fa fa-check"></i> 通过
                            </button>
                            &nbsp;&nbsp;
                            <button ${isAdmin?'':'disabled'}  onclick="apply_deny(${memberStay.id}, ${param.checkType}, true)" class="btn btn-danger">
                                <i class="fa fa-times"></i> 返回修改
                            </button>
                        </div>
                    </div>


                </div>
            </div><!-- /.widget-main -->
        </div><!-- /.widget-body -->
    </div><!-- /.widget-box -->
    <c:import url="/applyApprovalLogs?id=${memberStay.id}&type=${APPLY_APPROVAL_LOG_TYPE_MEMBER_STAY}"/>
</div>
