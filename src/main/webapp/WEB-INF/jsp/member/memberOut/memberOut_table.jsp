<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/member/constants.jsp" %>
<tbody>
<tr>
    <td class="bg-right">
        姓名
    </td>
    <td class="bg-left" style="min-width: 80px">
        ${userBean.realname}
    </td>
    <td class="bg-right">
        介绍信编号
    </td>
    <td class="bg-left" style="min-width: 80px">
        ${_use_code_as_identify?userBean.code:memberOut.code}
    </td>
    <td class="bg-right">
        性别
    </td>
    <td class="bg-left" style="min-width: 80px">
        ${GENDER_MAP.get(userBean.gender)}
    </td>
    <td class="bg-right">
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
        <t:mask src="${userBean.idcard}" type="idCard"/>
    </td>
    <td class="bg-right">
        类别
    </td>
    <td class="bg-left" style="min-width: 120px">
        ${cm:getMetaType(memberOut.type).name}
    </td>
</tr>
<tr>
    <td class="bg-right">
        转入单位抬头
    </td>
    <td class="bg-left" style="min-width: 80px">
        ${memberOut.toTitle}
    </td>
    <td class="bg-right">
        转入单位
    </td>
    <td class="bg-left" style="min-width: 80px">
        ${memberOut.toUnit}
    </td>
    <td class="bg-right">
        转出单位
    </td>
    <td class="bg-left" style="min-width: 80px">
        ${memberOut.fromUnit}
    </td>
    <td class="bg-right">
        转出单位地址
    </td>
    <td class="bg-left" style="min-width: 120px">
        ${memberOut.fromAddress}
    </td>
</tr>
<tr>
    <td class="bg-right">
        转出单位联系电话
    </td>
    <td class="bg-left" style="min-width: 80px">
        ${memberOut.fromPhone}
    </td>
    <td class="bg-right">
        转出单位传真
    </td>
    <td class="bg-left" style="min-width: 80px">
        ${memberOut.fromFax}
    </td>
    <td class="bg-right">
        转出单位邮编
    </td>
    <td class="bg-left" style="min-width: 80px">
        ${memberOut.fromPostCode}
    </td>
    <td class="bg-right">
        党费缴纳至年月
    </td>
    <td class="bg-left" style="min-width: 120px">
        ${cm:formatDate(memberOut.payTime,'yyyy-MM')}
    </td>
</tr>
<tr>
    <td class="bg-right">
        介绍信有效期天数
    </td>
    <td class="bg-left" style="min-width: 80px">
        ${memberOut.validDays}
    </td>
    <td class="bg-right">
        办理时间
    </td>
    <td class="bg-left" style="min-width: 80px">
        ${cm:formatDate(memberOut.handleTime,'yyyy-MM-dd')}
    </td>
    <td class="bg-right">
        状态
    </td>
    <td class="bg-left" style="min-width: 80px" colspan="3">
        <c:if test="${empty memberOut.status}"><span style="color:red">未提交</span></c:if>
        ${MEMBER_OUT_STATUS_MAP.get(memberOut.status)}
        <c:if test="${_user.id==memberOut.userId}">
            &nbsp;
            <c:if test="${memberOut.status==MEMBER_OUT_STATUS_APPLY}">
                <button class="btn btn-white btn-warning btn-xs" onclick="_applyBack()">
                    <i class="fa fa-undo"></i>
                    撤销申请
                </button>
            </c:if>
        </c:if>
    </td>
</tr>
</tbody>