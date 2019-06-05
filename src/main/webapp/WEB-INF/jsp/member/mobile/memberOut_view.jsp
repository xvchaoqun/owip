<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="MEMBER_OUT_STATUS_MAP" value="<%=MemberConstants.MEMBER_OUT_STATUS_MAP%>"/>
<c:set var="MEMBER_OUT_STATUS_APPLY" value="<%=MemberConstants.MEMBER_OUT_STATUS_APPLY%>"/>
<div class="row">
    <div class="col-xs-12">
        <div class="profile-user-info profile-user-info-striped">
            <div class="profile-info-row">
                <div class="profile-info-name td"> 类别</div>
                <div class="profile-info-value td">
                    <span class="editable">${cm:getMetaType(memberOut.type).name}</span>
                </div>
            </div>
            <div class="profile-info-row">
                <div class="profile-info-name td"> 姓名</div>
                <div class="profile-info-value td">
                    <span class="editable">${userBean.realname}</span>
                </div>
            </div>
            <div class="profile-info-row">
                <div class="profile-info-name td"> 介绍信编号</div>
                <div class="profile-info-value td">
                    <span class="editable">${userBean.code}</span>
                </div>
            </div>
            <div class="profile-info-row">
                <div class="profile-info-name td"> 性别</div>
                <div class="profile-info-value td">
                    <span class="editable">${GENDER_MAP.get(userBean.gender)}</span>
                </div>
            </div>
            <div class="profile-info-row">
                <div class="profile-info-name td"> 年龄</div>
                <div class="profile-info-value td">
                    <span class="editable"><c:if test="${not empty userBean.birth}">${cm:intervalYearsUntilNow(userBean.birth)}</c:if></span>
                </div>
            </div>
            <div class="profile-info-row">
                <div class="profile-info-name td"> 民族</div>
                <div class="profile-info-value td">
                    <span class="editable">${userBean.nation}</span>
                </div>
            </div>
            <div class="profile-info-row">
                <div class="profile-info-name td"> 党籍状态</div>
                <div class="profile-info-value td">
                    <span class="editable">${MEMBER_POLITICAL_STATUS_MAP.get(userBean.politicalStatus)}</span>
                </div>
            </div>
            <div class="profile-info-row">
                <div class="profile-info-name td"> 身份证号</div>
                <div class="profile-info-value td">
                    <span class="editable">${userBean.idcard}</span>
                </div>
            </div>
            <div class="profile-info-row">
                <div class="profile-info-name td"> 党员本人联系电话</div>
                <div class="profile-info-value td">
                    <span class="editable">${memberOut.phone}</span>
                </div>
            </div>
            <div class="profile-info-row">
                <div class="profile-info-name td"> 转入单位抬头</div>
                <div class="profile-info-value td">
                    <span class="editable">${memberOut.toTitle}</span>
                </div>
            </div>
            <div class="profile-info-row">
                <div class="profile-info-name td"> 转入单位</div>
                <div class="profile-info-value td">
                    <span class="editable">${memberOut.toUnit}</span>
                </div>
            </div>
            <div class="profile-info-row">
                <div class="profile-info-name td"> 转入单位抬头</div>
                <div class="profile-info-value td">
                    <span class="editable">${memberOut.toTitle}</span>
                </div>
            </div>
            <div class="profile-info-row">
                <div class="profile-info-name td"> 转出单位</div>
                <div class="profile-info-value td">
                    <span class="editable">${memberOut.fromUnit}</span>
                </div>
            </div>
            <div class="profile-info-row">
                <div class="profile-info-name td"> 转出单位地址</div>
                <div class="profile-info-value td">
                    <span class="editable">${memberOut.fromAddress}</span>
                </div>
            </div>
            <div class="profile-info-row">
                <div class="profile-info-name td"> 转出单位联系电话</div>
                <div class="profile-info-value td">
                    <span class="editable">${memberOut.fromPhone}</span>
                </div>
            </div>
            <div class="profile-info-row">
                <div class="profile-info-name td"> 转出单位传真</div>
                <div class="profile-info-value td">
                    <span class="editable">${memberOut.fromFax}</span>
                </div>
            </div>
            <div class="profile-info-row">
                <div class="profile-info-name td"> 转出单位邮编</div>
                <div class="profile-info-value td">
                    <span class="editable">${memberOut.fromPostCode}</span>
                </div>
            </div>
            <div class="profile-info-row">
                <div class="profile-info-name td"> 党费缴纳至年月</div>
                <div class="profile-info-value td">
                    <span class="editable">${cm:formatDate(memberOut.payTime,'yyyy-MM')}</span>
                </div>
            </div>
            <div class="profile-info-row">
                <div class="profile-info-name td"> 介绍信有效期天数</div>
                <div class="profile-info-value td">
                    <span class="editable">${memberOut.validDays}</span>
                </div>
            </div>
            <div class="profile-info-row">
                <div class="profile-info-name td"> 办理时间</div>
                <div class="profile-info-value td">
                    <span class="editable">${cm:formatDate(memberOut.handleTime,'yyyy-MM-dd')}</span>
                </div>
            </div>
            <div class="profile-info-row">
                <div class="profile-info-name td"> 状态</div>
                <div class="profile-info-value td">
                  <span class="editable">
                    ${MEMBER_OUT_STATUS_MAP.get(memberOut.status)}
                            &nbsp;
                        <c:if test="${memberOut.status==MEMBER_OUT_STATUS_APPLY}">
                                <button class="btn btn-white btn-warning btn-xs" onclick="_applyBack()">
                                    <i class="fa fa-undo"></i>
                                    撤销申请
                                </button>
                        </c:if>
                  </span>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    function _applyBack() {
        SysMsg.confirm("确定撤销申请吗？", function () {
            $.post("${ctx}/m/memberOut_back", function (ret) {
                    if (ret.success) {
                        SysMsg.success("撤销成功。", function () {
                            location.reload();
                        });
                    }
                });
        });
    }
</script>
