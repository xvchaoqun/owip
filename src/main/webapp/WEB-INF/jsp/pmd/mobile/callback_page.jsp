<%@ page import="bnu.newpay.BnuPayUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/cet/constants.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content">
            <div class="row">
                <div class="col-xs-12">
                    <div class="alert alert-block alert-success"
                         style="font-size: 18px;font-weight: bolder;margin-bottom: 10px">
                        <i class="ace-icon fa fa-info-circle green"></i>
                        &nbsp;缴费结果
                    </div>
                </div>
                <div style="clear: both"></div>
                <div class="tabbable" style="margin-bottom: 10px;">
                    <div class="tab-content" style="padding:0px 0px 10px;border: none">
                        <div id="title" class="tab-pane active">
                            <div class="profile-user-info profile-user-info-striped">
                                <c:if test="${verifySign}">
                                    <c:if test="${param.state==1}">
                                        <div class="profile-info-row">
                                            <div class="profile-info-name"> 支付成功</div>
                                        </div>
                                        <div class="profile-info-row">
                                            <div class="profile-info-name td"> 订单号</div>
                                            <div class="profile-info-value td">
                                                <span class="editable">${param.thirdorderid}</span>
                                            </div>
                                        </div>
                                        <div class="profile-info-row">
                                            <div class="profile-info-name td"> 支付金额</div>
                                            <div class="profile-info-value td">
                                                <span class="editable">${cm:bigDecimalDivide(param.actulamt, 100)}</span>
                                            </div>
                                        </div>
                                    </c:if>
                                    <c:if test="${param.state!=1}">
                                        <div class="profile-info-row">
                                            <div class="profile-info-name"> 支付失败</div>
                                        </div>
                                    </c:if>
                                </c:if>
                                <c:if test="${!verifySign}">
                                    <div class="profile-info-row">
                                        <div class="profile-info-name"> 签名错误</div>
                                    </div>
                                </c:if>
                            </div>
                        </div>
                        <div class="space"></div>
                        <div class="center">
                            <a href="${ctx}/m/pmd/pmdMember" class="btn btn-primary">
                                <i class="ace-icon fa fa-reply"></i>
                                返回党费缴纳列表
                            </a>
                        </div>
                    </div>
                </div>
                <!-- /.col -->
            </div>
            <div id="body-content-view">
            </div>
        </div>
    </div>
</div>
<style>
    .infobox {
        height: auto;
        padding-left: 2px;
    }

    .course-list .name {
        font-weight: bold;
        color: black;
        overflow: hidden;
    }

    .course-list .status {
        text-align: right;
        /*padding-left:10px;*/
        white-space: nowrap;
    }
</style>