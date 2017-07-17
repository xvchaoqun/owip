<%--
  Created by IntelliJ IDEA.
  User: fafa
  Date: 2015/12/7
  Time: 13:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="widget-box">
    <div class="widget-header">
        <h4 class="widget-title"><i class="fa fa-paw blue"></i> 校内组织关系转接申请</h4>

        <div class="widget-toolbar">
            <a href="javascript:;" data-action="collapse">
                <i class="ace-icon fa fa-chevron-up"></i>
            </a>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main">
            <table class="table table-bordered table-striped">
                <tbody>
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

                    <td class="bg-right">
                        转出组织机构
                    </td>
                    <td class="bg-left" style="min-width: 120px" colspan="5">
                        ${cm:displayParty(userBean.partyId, userBean.branchId)}
                    </td>
                </tr>
                <tr>
                    <td class="bg-right">
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

                    <td class="bg-right">
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
                    <td  class="bg-left" style="min-width: 80px">
                        ${cm:formatDate(memberTransfer.fromHandleTime,'yyyy-MM-dd')}
                    </td>
                    <td class="bg-right">
                        状态
                    </td>
                    <td class="bg-left" style="min-width: 80px" colspan="3">
                        <c:if test="${empty memberTransfer.status}"><span style="color:red">未提交</span></c:if>
                        ${MEMBER_TRANSFER_STATUS_MAP.get(memberTransfer.status)}
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>
