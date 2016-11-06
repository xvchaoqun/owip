<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<tbody>
<tr>
  <td class="bg-right"  style="width: 100px">
    姓名
  </td>
  <td class="bg-left" style="min-width: 80px">
    ${userBean.realname}
  </td>
  <td class="bg-right"  style="width: 80px">
    性别
  </td>
  <td class="bg-left" style="width: 50px">
    ${GENDER_MAP.get(userBean.gender)}
  </td>
  <td class="bg-right" style="width: 100px">
    名族
  </td>
  <td class="bg-left"  style="width: 50px">
    ${userBean.nation}
  </td>
  <td  rowspan="5" style="text-align: center;vertical-align: middle;
				 background-color: #fff;width: 143px;" class="avatar">
    <img src="${ctx}/avatar/${userBean.username}" alt="免冠照片"  class="avatar">
  </td>
</tr>
<tr>
  <td class="bg-right">
    出生年月
  </td>
  <td class="bg-left" style="min-width: 80px;white-space: nowrap">
    ${cm:formatDate(userBean.birth,'yyyy-MM-dd')}
  </td>
  <td class="bg-right"  style="white-space: nowrap">
    入党时间
  </td>
  <td class="bg-left" style="min-width: 80px;white-space: nowrap">
    ${cm:formatDate(userBean.growTime,'yyyy-MM-dd')}
  </td>

  <td class="bg-right">
    籍贯
  </td>
  <td class="bg-left" style="min-width: 80px">
    ${userBean.nativePlace}
  </td>
</tr>
<tr>
  <td class="bg-right">
    人员类别
  </td>
  <td class="bg-left" style="min-width: 80px">
    ${abroadUserTypeMap.get(graduateAbroad.userType).name}
  </td>
  <td class="bg-right">
    身份证号
  </td>
  <td class="bg-left" style="min-width: 80px" colspan="3">
    ${userBean.idcard}
  </td>
</tr>
<tr>
  <td class="bg-right">
    毕业班级
  </td>
  <td class="bg-left" style="min-width: 80px">
    ${student.grade}
  </td>
  <td class="bg-right">
    学号
  </td>
  <td class="bg-left" style="min-width: 80px">
    ${userBean.code}
  </td>
  <td class="bg-right">
    党籍状况
  </td>
  <td class="bg-left" style="min-width: 120px" >
    ${MEMBER_POLITICAL_STATUS_MAP.get(userBean.politicalStatus)}
  </td>
</tr>
<tr>
  <td class="bg-right">
    手机
  </td>
  <td class="bg-left">
    ${graduateAbroad.mobile}
  </td>

  <td class="bg-right">
    家庭电话
  </td>
  <td class="bg-left">
    ${graduateAbroad.phone}
  </td>
  <td class="bg-right">
    QQ号
  </td>
  <td class="bg-left">
    ${graduateAbroad.qq}
  </td>
</tr>
<tr>
  <td class="bg-right">
    电子邮箱
  </td>
  <td class="bg-left" colspan="3">
    ${graduateAbroad.email}
  </td>

  <td class="bg-right">
    微信号
  </td>
  <td class="bg-left" colspan="2">
    ${graduateAbroad.weixin}
  </td>
</tr>
<tr>
  <td class="bg-right" rowspan="2">
    通讯地址
  </td>
  <td class="bg-right"  style="width: 100px">
    国（境）内
  </td>
  <td class="bg-left" colspan="5">
    ${graduateAbroad.inAddress}
  </td>
</tr>
<tr>
  <td class="bg-right">
    国（境）外
  </td>
  <td class="bg-left" colspan="5">
    ${graduateAbroad.outAddress}
  </td>
</tr>
<tr>
  <td class="bg-right" rowspan="3"  style="white-space: nowrap">
    国（境）内第一联系人
  </td>
  <td class="bg-right"  style="white-space: nowrap">
    姓名（与本人关系）
  </td>
  <td class="bg-left" colspan="2">
    ${graduateAbroad.name1} - ${graduateAbroad.relate1}
  </td>
  <td class="bg-right">
    单位
  </td>
  <td class="bg-left" colspan="2">
    ${graduateAbroad.unit1}
  </td>
</tr>
<tr>
  <td class="bg-right">
    职务
  </td>
  <td class="bg-left" colspan="2">
    ${graduateAbroad.post1}
  </td>
  <td class="bg-right">
    办公电话
  </td>
  <td class="bg-left" colspan="2">
    ${graduateAbroad.phone1}
  </td>
</tr>
<tr>
  <td class="bg-right">
    手机号
  </td>
  <td class="bg-left" colspan="2">
    ${graduateAbroad.mobile1}
  </td>
  <td class="bg-right">
    电子邮箱
  </td>
  <td class="bg-left" colspan="2">
    ${graduateAbroad.email1}
  </td>
</tr>
<tr>
  <td class="bg-right" rowspan="3">
    国（境）内第二联系人
  </td>
  <td class="bg-right" style="white-space: nowrap">
    姓名（与本人关系）
  </td>
  <td class="bg-left" colspan="2">
    ${graduateAbroad.name2} - ${graduateAbroad.relate2}
  </td>
  <td class="bg-right">
    单位
  </td>
  <td class="bg-left" colspan="2">
    ${graduateAbroad.unit2}
  </td>
</tr>
<tr>
  <td class="bg-right">
    职务
  </td>
  <td class="bg-left" colspan="2">
    ${graduateAbroad.post2}
  </td>
  <td class="bg-right">
    办公电话
  </td>
  <td class="bg-left" colspan="2">
    ${graduateAbroad.phone2}
  </td>
</tr>
<tr>
  <td class="bg-right">
    手机号
  </td>
  <td class="bg-left" colspan="2">
    ${graduateAbroad.mobile2}
  </td>
  <td class="bg-right">
    电子邮箱
  </td>
  <td class="bg-left" colspan="2">
    ${graduateAbroad.email2}
  </td>
</tr>
<tr>
  <td class="bg-right" colspan="2">
    出国原因
  </td>
  <td class="bg-left" colspan="5">
    ${fn:replace(graduateAbroad.abroadReason, "+++", ",")}
  </td>
</tr>
<tr>
  <td class="bg-right" colspan="2">
    留学国家
  </td>
  <td class="bg-left" colspan="2">
    ${graduateAbroad.country}
  </td>
  <td class="bg-right" style="white-space: nowrap">
    留学学校（院系）
  </td>
  <td class="bg-left" colspan="2">
    ${graduateAbroad.school}
  </td>
</tr>
<tr>
  <td class="bg-right" colspan="2">
    留学起止时间
  </td>
  <td class="bg-left" colspan="2">
    ${cm:formatDate(graduateAbroad.startTime,'yyyy/MM')} 至 ${cm:formatDate(graduateAbroad.endTime,'yyyy/MM')}
  </td>
  <td class="bg-right">
    留学方式
  </td>
  <td class="bg-left" colspan="2">
    ${GRADUATE_ABROAD_TYPE_MAP.get(graduateAbroad.type)}
  </td>
</tr>
<tr>
  <td class="bg-right" colspan="2">
    原组织关系所在党支部名称
  </td>
  <c:set var="isDirectBranch" value="${cm:isDirectBranch(party.id)}"/>
  <td class="bg-left" colspan="${(!isDirectBranch&&graduateAbroad.status>=GRADUATE_ABROAD_STATUS_PARTY_VERIFY)?2:5}">
      ${cm:displayParty(graduateAbroad.partyId, graduateAbroad.branchId)}
  </td>
  <c:if test="${!isDirectBranch&&graduateAbroad.status>=GRADUATE_ABROAD_STATUS_PARTY_VERIFY}">
    <td class="bg-right">
      原组织关系所在党支部负责人姓名、电话
    </td>
    <td class="bg-left" colspan="2">
      ${cm:getUserById(graduateAbroad.orgBranchAdminId).realname} <br/>
      ${graduateAbroad.orgBranchAdminPhone}
    </td>
  </c:if>
</tr>
<tr>
  <td class="bg-right" colspan="2">
    申请保留组织关系起止时间
  </td>
  <td class="bg-left" colspan="2">
    ${cm:formatDate(graduateAbroad.saveStartTime,'yyyy/MM')} 至 ${cm:formatDate(graduateAbroad.saveEndTime,'yyyy/MM')}
  </td>
  <td class="bg-right">
    党费交纳截止时间
  </td>
  <td class="bg-left" colspan="2">
    ${cm:formatDate(graduateAbroad.payTime,'yyyy-MM')}
  </td>
</tr>
<tr>
  <c:if test="${!isDirectBranch&&graduateAbroad.status>=GRADUATE_ABROAD_STATUS_PARTY_VERIFY}">
    <td class="bg-right" colspan="2">
      暂留所在党支部名称
    </td>
    <td class="bg-left" colspan="2">
        ${cm:displayParty(null, graduateAbroad.toBranchId)}
    </td>
  </c:if>
  <td class="bg-right" colspan="${(!isDirectBranch&&graduateAbroad.status>=GRADUATE_ABROAD_STATUS_PARTY_VERIFY)?'':2}">
    状态
  </td>
  <td class="bg-left" style="min-width: 80px" colspan="${(!isDirectBranch&&graduateAbroad.status>=GRADUATE_ABROAD_STATUS_PARTY_VERIFY)?2:5}">
    <c:if test="${empty graduateAbroad.status}"><span style="color:red">未提交</span></c:if>
    ${GRADUATE_ABROAD_STATUS_MAP.get(graduateAbroad.status)}
    <c:if test="${_user.id==graduateAbroad.userId}">
    &nbsp;
    <c:if test="${graduateAbroad.status==GRADUATE_ABROAD_STATUS_APPLY}">
      <small>
        <button class="btn btn-white btn-warning" onclick="_applyBack()">
          <i class="fa fa-undo"></i>
          撤销申请
        </button>
      </small>
    </c:if>
    </c:if>
  </td>
</tr>
</tbody>