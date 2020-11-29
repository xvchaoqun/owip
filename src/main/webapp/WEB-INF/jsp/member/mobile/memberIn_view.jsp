<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/member/constants.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div class="profile-user-info profile-user-info-striped">
            <div class="profile-info-row">
                <div class="profile-info-name td"> 介绍信抬头</div>

                <div class="profile-info-value td">
                    <span class="editable">${memberIn.fromTitle}</span>
                </div>
            </div>
            <div class="profile-info-row">
                <div class="profile-info-name td"> 介绍信有效期天数</div>

                <div class="profile-info-value td">
                    <span class="editable">${memberIn.validDays}</span>
                </div>
            </div>
            <div class="profile-info-row">
                <div class="profile-info-name td"> 类别</div>

                <div class="profile-info-value td">
                    <span class="editable">${cm:getMetaType(memberIn.type).name}</span>
                </div>
            </div>

            <div class="profile-info-row">
                <div class="profile-info-name td"> ${(user.type==USER_TYPE_JZG)?"工作证号":"学号"} </div>

                <div class="profile-info-value td">
                    <span class="editable">${user.code}</span>
                </div>
            </div>

            <div class="profile-info-row">
                <div class="profile-info-name td"> 姓名</div>

                <div class="profile-info-value td">
                    <span class="editable">${user.realname}</span>
                </div>
            </div>

            <div class="profile-info-row">
                <div class="profile-info-name td"> 转出单位</div>

                <div class="profile-info-value td">
                    <span class="editable">${memberIn.fromUnit}</span>
                </div>
            </div>
            <div class="profile-info-row">
                <div class="profile-info-name td"> 转出单位地址</div>

                <div class="profile-info-value td">
                    <span class="editable">${memberIn.fromAddress}</span>
                </div>
            </div>
            <div class="profile-info-row">
                <div class="profile-info-name td"> 转出单位联系电话</div>

                <div class="profile-info-value td">
                    <span class="editable">${memberIn.fromPhone}</span>
                </div>
            </div>
            <div class="profile-info-row">
                <div class="profile-info-name td"> 转出单位传真</div>

                <div class="profile-info-value td">
                    <span class="editable">${memberIn.fromFax}</span>
                </div>
            </div>
            <div class="profile-info-row">
                <div class="profile-info-name td"> 转出单位邮编</div>
                <div class="profile-info-value td">
                    <span class="editable">${memberIn.fromPostCode}</span>
                </div>
            </div>
            <div class="profile-info-row">
                <div class="profile-info-name td"> 党籍状态</div>

                <div class="profile-info-value td">
                    <span class="editable">${MEMBER_POLITICAL_STATUS_MAP.get(memberIn.politicalStatus)}</span>
                </div>
            </div>
            <div class="profile-info-row">
                <div class="profile-info-name td"> 党费缴纳至年月</div>
                <div class="profile-info-value td">
                    <span class="editable">${cm:formatDate(memberIn.payTime,'yyyy-MM')}</span>
                </div>
            </div>
            <div class="profile-info-row">
                <div class="profile-info-name td"> 转出办理时间</div>

                <div class="profile-info-value td">
                    <span class="editable">${cm:formatDate(memberIn.fromHandleTime,'yyyy-MM-dd')}</span>
                </div>
            </div>
            <div class="profile-info-row">
                <div class="profile-info-name td"> 转入办理时间</div>
                <div class="profile-info-value td">
                    <span class="editable">${cm:formatDate(memberIn.handleTime,'yyyy-MM-dd')}</span>
                </div>
            </div>

            <div class="profile-info-row">
                <div class="profile-info-name td"> 提交书面申请书时间</div>
                <div class="profile-info-value td">
                    <span class="editable">${cm:formatDate(memberIn.applyTime,'yyyy-MM-dd')}</span>
                </div>
            </div>
            <div class="profile-info-row">
                <div class="profile-info-name td"> 确定为入党积极分子时间</div>
                <div class="profile-info-value td">
                    <span class="editable">${cm:formatDate(memberIn.activeTime,'yyyy-MM-dd')}</span>
                </div>
            </div>
            <div class="profile-info-row">
                <div class="profile-info-name td"> 确定为发展对象时间</div>
                <div class="profile-info-value td">
                    <span class="editable">${cm:formatDate(memberIn.candidateTime,'yyyy-MM-dd')}</span>
                </div>
            </div>
            <div class="profile-info-row">
                <div class="profile-info-name td"> 入党时间</div>
                <div class="profile-info-value td">
                    <span class="editable">${cm:formatDate(memberIn.growTime,'yyyy-MM-dd')}</span>
                </div>
            </div>
            <div class="profile-info-row">
                <div class="profile-info-name td"> 转正时间</div>
                <div class="profile-info-value td">
                    <span class="editable">${cm:formatDate(memberIn.positiveTime,'yyyy-MM-dd')}</span>
                </div>
            </div>
            <div class="profile-info-row">
                <div class="profile-info-name"> 申请转入组织机构</div>
                <div class="profile-info-value">
                  <span class="editable">
                      ${cm:displayParty(memberIn.partyId, memberIn.branchId)}
                  </span>
                </div>
            </div>
            <div class="profile-info-row">
                <div class="profile-info-name td"> 状态</div>
                <div class="profile-info-value td">
                  <span class="editable">
                    ${MEMBER_IN_STATUS_MAP.get(memberIn.status)}
                    <c:if test="${memberIn.status==MEMBER_IN_STATUS_APPLY}">
                      <small style="margin-left: 10px">
                        <button class="btn btn-white btn-warning btn-xs" onclick="_applyBack()">
                          <i class="fa fa-undo"></i>
                          撤销
                        </button>
                      </small>
                    </c:if>
                  </span>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    function _applyBack() {
        SysMsg.confirm("确定撤销申请吗？", "撤销申请", function () {
            $.post("${ctx}/m/applyBack?type=${OW_ENTER_APPLY_TYPE_MEMBERIN}", function (ret) {
                if (ret.success) {
                    SysMsg.success("撤销成功。", function () {
                        location.reload();
                    });
                }
            });
        });
    }
</script>
