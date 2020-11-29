<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/member/constants.jsp" %>
<div id="body-content">
    <div class="row">
        <div class="col-xs-12">

            <div class="col-xs-offset-1 col-xs-10" style="padding-top: 50px">

                <div class="page-header">
                    <h1>
                        <i class="fa fa-check-square-o"></i>
                        入党申请信息

                    </h1>
                </div>
                <div class="profile-user-info profile-user-info-striped">
                    <div class="profile-info-row">
                        <div class="profile-info-name"> ${(_user.type==USER_TYPE_JZG)?"工作证号":"学号"} </div>

                        <div class="profile-info-value">
                            <span class="editable" id="username">${_user.code}</span>
                        </div>
                    </div>

                    <div class="profile-info-row">
                        <div class="profile-info-name"> 入党申请时间</div>

                        <div class="profile-info-value">
                            <span class="editable">${cm:formatDate(memberApply.joinApplyTime,'yyyy.MM.dd')}</span>
                        </div>
                    </div>

                    <div class="profile-info-row">
                        <div class="profile-info-name"> 提交书面申请书时间</div>

                        <div class="profile-info-value">
                            <span class="editable">${cm:formatDate(memberApply.applyTime,'yyyy.MM.dd')}</span>
                        </div>
                    </div>
                    <div class="profile-info-row">
                        <div class="profile-info-name"> 入党志愿书接收人</div>

                        <div class="profile-info-value">
                            <span class="editable">${memberApply.drawAcceptor}</span>
                        </div>
                    </div>

                    <div class="profile-info-row">
                        <div class="profile-info-name"> 联系${_p_partyName} </div>

                        <div class="profile-info-value">
                            <span class="editable" id="signup">${cm:displayParty(memberApply.partyId, null)}</span>
                        </div>
                    </div>
                    <c:if test="${memberApply.branchId>0}">
                        <div class="profile-info-row">
                            <div class="profile-info-name"> 联系党支部</div>

                            <div class="profile-info-value">
                                <span class="editable" id="login">${cm:displayParty(null, memberApply.branchId)}</span>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${_memberApply_needContinueDevelop && memberApply.applyStage>0}">
                        <div class="profile-info-row" >
                            <div class="profile-info-name" style="color: red"> 申请继续培养</div>

                            <div class="profile-info-value">
                                <a href="javascript:;" data-url="${ctx}/user/memberApply?preview=1" class="openView btn btn-warning btn-xs">
                                    <i class="fa fa-search"></i> 详情</a>
                            </div>
                        </div>
                    </c:if>

                    <div class="profile-info-row">
                        <div class="profile-info-name"> 备注</div>

                        <div class="profile-info-value">
                            <span class="editable" id="about">${memberApply.remark}</span>
                        </div>
                    </div>
                </div>
                <div style="padding-top: 50px">
                    <ul class="steps">
                        <li data-step="1" class="complete">
                            <span class="step">0</span>
                            <span class="title">申请已提交
        <c:if test="${memberApply.stage==OW_APPLY_STAGE_INIT}">
        <small>
          <button class="btn btn-white btn-warning btn-xs" onclick="_applyBack()">
            <i class="fa fa-undo"></i>
            撤销
          </button>
        </small>
        </c:if>
      </span>
                            <span class="subtitle">
                                ${cm:formatDate(memberApply.createTime,'yyyy.MM.dd')}
                            </span>
                        </li>
                        <c:if test="${memberApply.stage==-1}">
                            <li data-step="2" class="active">
                                <span class="step">1</span>
                                <span class="title">未通过申请</span>
                            </li>
                        </c:if>

                        <li data-step="1" <c:if test="${memberApply.stage>0}">class="complete"</c:if>>
                            <span class="step">1</span>
                            <span class="title">申请已通过</span>
                            <c:if test="${memberApply.stage>0}"><span class="subtitle">
                                    ${cm:formatDate(memberApply.passTime,'yyyy.MM.dd')}
                            </span></c:if>
                        </li>
                        <li data-step="2" <c:if test="${memberApply.stage>1}">class="complete"</c:if>>
                            <span class="step">2</span>
                            <span class="title">入党积极分子</span>
                            <c:if test="${memberApply.stage>1}"> <span class="subtitle">
                                    ${cm:formatDate(memberApply.activeTime,'yyyy.MM.dd')}
                            </span></c:if>
                        </li>

                        <li data-step="3" <c:if test="${memberApply.stage>2}">class="complete"</c:if>>
                            <span class="step">3</span>
                            <span class="title">成为发展对象</span>
                            <c:if test="${memberApply.stage>2}"> <span class="subtitle">
                                    ${cm:formatDate(memberApply.candidateTime,'yyyy.MM.dd')}
                            </span></c:if>
                        </li>

                        <li data-step="4" <c:if test="${memberApply.stage>3}">class="complete"</c:if>>
                            <span class="step">4</span>
                            <span class="title">列入发展计划</span>
                            <c:if test="${memberApply.stage>3}"> <span class="subtitle">
                                    ${cm:formatDate(memberApply.planTime,'yyyy.MM.dd')}
                            </span></c:if>
                        </li>
                        <li data-step="5" <c:if test="${memberApply.stage>4}">class="complete"</c:if>>
                            <span class="step">5</span>
                            <span class="title">领取志愿书</span>
                            <c:if test="${memberApply.stage>4}"> <span class="subtitle">
                                    ${cm:formatDate(memberApply.drawTime,'yyyy.MM.dd')}
                            </span></c:if>
                        </li>
                        <li data-step="6" <c:if test="${memberApply.stage>5}">class="complete"</c:if>>
                            <span class="step">6</span>
                            <span class="title">预备党员</span>
                            <c:if test="${memberApply.stage>5}">
        <span class="subtitle">
                ${cm:formatDate(memberApply.growTime,'yyyy.MM.dd')}
        </span>
                            </c:if>
                        </li>

                        <li data-step="7" <c:if test="${memberApply.stage>6}">class="complete"</c:if>>
                            <span class="step">7</span>
                            <span class="title">正式党员</span>
                            <c:if test="${memberApply.stage>6}">
        <span class="subtitle">
                ${cm:formatDate(memberApply.positiveTime,'yyyy.MM.dd')}
        </span>
                            </c:if>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="body-content-view"></div>
</div>
<script>
    function _applyBack() {
        SysMsg.confirm("确定撤销申请吗？", "撤销申请", function () {
            $.post("${ctx}/user/applyBack?type=${OW_ENTER_APPLY_TYPE_MEMBERAPPLY}", function (ret) {
                if (ret.success) {
                    SysMsg.success("撤销成功。", function () {
                        $.hashchange();
                    });
                }
            });
        })
    }
</script>
