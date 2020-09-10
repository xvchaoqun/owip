<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="OW_APPLY_STAGE_MAP" value="<%=OwConstants.OW_APPLY_STAGE_MAP%>"/>
<c:set var="OW_APPLY_STAGE_INIT" value="<%=OwConstants.OW_APPLY_STAGE_INIT%>"/>
<div class="row">
    <div class="col-xs-12">
        <div class="profile-user-info profile-user-info-striped">
            <div class="profile-info-row">
                <div class="profile-info-name td"> ${(_user.type==USER_TYPE_JZG)?"教工号":"学号"} </div>

                <div class="profile-info-value td">
                    <span class="editable" id="username">${_user.code}</span>
                </div>
            </div>

            <div class="profile-info-row">
                <div class="profile-info-name td"> 提交书面申请书时间</div>

                <div class="profile-info-value td">
                    <span class="editable" id="age">${cm:formatDate(memberApply.applyTime,'yyyy-MM-dd')}</span>
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
            <div class="profile-info-row">
                <div class="profile-info-name"> 备注</div>

                <div class="profile-info-value">
                    <span class="editable" id="about">${memberApply.remark}</span>
                </div>
            </div>
            <div class="profile-info-row">
                <div class="profile-info-name"> 当前状态</div>
                <div class="profile-info-value">
                    ${OW_APPLY_STAGE_MAP.get(memberApply.stage)}
                    <c:if test="${memberApply.stage==OW_APPLY_STAGE_INIT}">
                        <small style="margin-left: 10px">
                            <button class="btn btn-white btn-warning btn-xs" onclick="_applyBack()">
                                <i class="fa fa-undo"></i>
                                撤销
                            </button>
                        </small>
                    </c:if>
                </div>
            </div>
        </div>

    </div>
</div>
<script>
    function _applyBack() {
        SysMsg.confirm("确定撤销申请吗？", "撤销申请", function () {
            $.post("${ctx}/m/applyBack", function (ret) {
                if (ret.success) {
                    SysMsg.success("撤销成功。", function () {
                        location.reload();
                    });
                }
            });
        })
    }
</script>
