<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="MEMBER_TRANSFER_STATUS_MAP" value="<%=MemberConstants.MEMBER_TRANSFER_STATUS_MAP%>"/>
<c:set var="MEMBER_TRANSFER_STATUS_APPLY" value="<%=MemberConstants.MEMBER_TRANSFER_STATUS_APPLY%>"/>
<tr>
    <td class="bg-right">
        姓名
    </td>
    <td class="bg-left" style="min-width: 80px">
        ${userBean.realname}
    </td>
    <td class="bg-right">
        学工号
    </td>
    <td class="bg-left" style="min-width: 80px">
        ${userBean.code}
    </td>
    <td class="bg-right">
        性别
    </td>
    <td class="bg-left" style="min-width: 80px">
        ${GENDER_MAP.get(userBean.gender)}
    </td>
    <td class="bg-right" nowrap>
        年龄
    </td>
    <td class="bg-left" style="min-width: 120px">
        ${empty userBean.birth?'':cm:intervalYearsUntilNow(userBean.birth)}
    </td>
</tr>
<tr>
    <td class="bg-right">
        民族
    </td>
    <td class="bg-left" style="min-width: 80px">
        ${userBean.nation}
    </td>
    <td class="bg-right">
        党籍状态
    </td>
    <td class="bg-left" style="min-width: 80px">
        ${MEMBER_POLITICAL_STATUS_MAP.get(userBean.politicalStatus)}
    </td>
    <td class="bg-right">
        身份证号
    </td>
    <td class="bg-left" style="min-width: 80px">
        ${userBean.idcard}
    </td>
    <td class="bg-right">
        类别
    </td>
    <td class="bg-left" style="min-width: 120px">
        ${MEMBER_TYP_MAP.get(userBean.type)}
    </td>
</tr>
<tr>
    <td class="bg-right">
        转入组织机构
    </td>
    <td class="bg-left" style="min-width: 80px">
        ${cm:displayParty(memberTransfer.partyId, memberTransfer.branchId)}
    </td>

    <td class="bg-right" nowrap>
        转出组织机构
    </td>
    <td class="bg-left" style="min-width: 120px" colspan="5">
        ${cm:displayParty(userBean.partyId, userBean.branchId)}
    </td>
</tr>
<tr>
    <td class="bg-right" nowrap>
        转出单位联系电话
    </td>
    <td class="bg-left" style="min-width: 80px">
        ${memberTransfer.fromPhone}
    </td>
    <td class="bg-right">
        转出单位传真
    </td>
    <td class="bg-left" style="min-width: 80px">
        ${memberTransfer.fromFax}
    </td>

    <td class="bg-right" nowrap>
        党费缴纳至年月
    </td>
    <td class="bg-left" style="min-width: 120px" colspan="3">
        ${cm:formatDate(memberTransfer.payTime,'yyyy-MM')}
    </td>
</tr>
<tr>
    <td class="bg-right">
        介绍信有效期天数
    </td>
    <td class="bg-left" style="min-width: 80px">
        ${memberTransfer.validDays}
    </td>
    <td class="bg-right">
        办理时间
    </td>
    <td class="bg-left" style="min-width: 80px">
        ${cm:formatDate(memberTransfer.fromHandleTime,'yyyy-MM-dd')}
    </td>
    <td class="bg-right">
        状态
    </td>
    <td class="bg-left" style="min-width: 80px" colspan="3">
        <c:if test="${empty memberTransfer.status}"><span style="color:red">未提交</span></c:if>
        ${MEMBER_TRANSFER_STATUS_MAP.get(memberTransfer.status)}
        <c:if test="${_user.id==memberTransfer.userId}">
          &nbsp;
            <c:if test="${memberTransfer.status==MEMBER_TRANSFER_STATUS_APPLY}">
                <small>
                    <button class="btn btn-white btn-warning " onclick="_applyBack()">
                        <i class="fa fa-undo"></i>
                        撤销申请
                    </button>
                </small>
            </c:if>
        </c:if>
    </td>
</tr>
