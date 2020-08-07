<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="MEMBER_STAY_ABROAD_TYPE_MAP_MAP" value="<%=MemberConstants.MEMBER_STAY_ABROAD_TYPE_MAP_MAP%>"/>
<c:set var="MEMBER_STAY_STATUS_MAP" value="<%=MemberConstants.MEMBER_STAY_STATUS_MAP%>"/>
<c:set var="MEMBER_STAY_STATUS_APPLY" value="<%=MemberConstants.MEMBER_STAY_STATUS_APPLY%>"/>
<c:set var="MEMBER_STAY_STATUS_PARTY_VERIFY" value="<%=MemberConstants.MEMBER_STAY_STATUS_PARTY_VERIFY%>"/>
<c:set var="MEMBER_STAY_TYPE_ABROAD" value="<%=MemberConstants.MEMBER_STAY_TYPE_ABROAD%>"/>
<c:set var="MEMBER_STAY_TYPE_INTERNAL" value="<%=MemberConstants.MEMBER_STAY_TYPE_INTERNAL%>"/>
<tbody>
<tr>
  <td class="bg-right"  style="width: 100px" >
    编号
  </td>
  <td class="bg-left" colspan="6">
    ${memberStay.code}
  </td>
</tr>
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
    民族
  </td>
  <td class="bg-left"  style="width: 50px">
    ${userBean.nation}
  </td>
  <td  rowspan="5" style="text-align: center;vertical-align: middle;
				 background-color: #fff;width: 143px;" class="avatar">
    <img src="${ctx}/avatar?path=${cm:sign(userBean.avatar)}" alt="免冠照片"  class="avatar">
  </td>
</tr>
<tr>
  <td class="bg-right">
    出生年月
  </td>
  <td class="bg-left" style="min-width: 80px;white-space: nowrap">
    ${cm:formatDate(userBean.birth,'yyyy.MM')}
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
    ${cm:getMetaType(memberStay.userType).name}
  </td>
  <td class="bg-right">
    身份证号
  </td>
  <td class="bg-left" style="min-width: 80px" colspan="3">
    <t:mask src="${userBean.idcard}" type="idCard"/>
  </td>
</tr>
<tr>
  <td class="bg-right">
    所在年级
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
    <t:mask src="${memberStay.mobile}" type="mobile"/>
  </td>

  <td class="bg-right">
    家庭电话
  </td>
  <td class="bg-left">
    <t:mask src="${memberStay.phone}" type="fixedPhone"/>
  </td>
  <td class="bg-right">
    QQ号
  </td>
  <td class="bg-left">
    <t:mask src="${memberStay.qq}" type="fixedPhone"/>
  </td>
</tr>
<tr>
  <td class="bg-right">
    电子邮箱
  </td>
  <td class="bg-left" colspan="3">
    <t:mask src="${memberStay.email}" type="email"/>
  </td>

  <td class="bg-right">
    微信号
  </td>
  <td class="bg-left" colspan="2">
    ${memberStay.weixin}
  </td>
</tr>
<c:if test="${type==MEMBER_STAY_TYPE_ABROAD}">
<tr>
  <td class="bg-right" rowspan="2">
    通讯地址
  </td>
  <td class="bg-right"  style="width: 100px">
    国（境）内
  </td>
  <td class="bg-left" colspan="5">
    ${memberStay.inAddress}
  </td>
</tr>
<tr>
  <td class="bg-right">
    国（境）外
  </td>
  <td class="bg-left" colspan="5">
    ${memberStay.outAddress}
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
    ${memberStay.name1}<c:if test="${not empty memberStay.relate1}">（${memberStay.relate1}）</c:if>
  </td>
  <td class="bg-right">
    单位
  </td>
  <td class="bg-left" colspan="2">
    ${memberStay.unit1}
  </td>
</tr>
<tr>
  <td class="bg-right">
    职务
  </td>
  <td class="bg-left" colspan="2">
    ${memberStay.post1}
  </td>
  <td class="bg-right">
    办公电话
  </td>
  <td class="bg-left" colspan="2">
    <t:mask src="${memberStay.phone1}" type="fixedPhone"/>
  </td>
</tr>
<tr>
  <td class="bg-right">
    手机号
  </td>
  <td class="bg-left" colspan="2">
    <t:mask src="${memberStay.mobile1}" type="mobile"/>
  </td>
  <td class="bg-right">
    电子邮箱
  </td>
  <td class="bg-left" colspan="2">
    <t:mask src="${memberStay.email1}" type="email"/>
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
    ${memberStay.name2}<c:if test="${not empty memberStay.relate2}">（${memberStay.relate2}）</c:if>
  </td>
  <td class="bg-right">
    单位
  </td>
  <td class="bg-left" colspan="2">
    ${memberStay.unit2}
  </td>
</tr>
<tr>
  <td class="bg-right">
    职务
  </td>
  <td class="bg-left" colspan="2">
    ${memberStay.post2}
  </td>
  <td class="bg-right">
    办公电话
  </td>
  <td class="bg-left" colspan="2">
    <t:mask src="${memberStay.phone2}" type="fixedPhone"/>
  </td>
</tr>
<tr>
  <td class="bg-right">
    手机号
  </td>
  <td class="bg-left" colspan="2">
    <t:mask src="${memberStay.mobile2}" type="mobile"/>
  </td>
  <td class="bg-right">
    电子邮箱
  </td>
  <td class="bg-left" colspan="2">
    <t:mask src="${memberStay.email2}" type="email"/>
  </td>
</tr>
<tr>
  <td class="bg-right" colspan="2">
    出国原因
  </td>
  <td class="bg-left" colspan="5">
    ${fn:replace(memberStay.stayReason, "+++", ",")}
  </td>
</tr>
<tr>
  <td class="bg-right" colspan="2">
    接收函/邀请函
  </td>
  <td class="bg-left" colspan="5">
    <c:if test="${not empty memberStay.letter}">
    <a class="various" title="接收函/邀请函"  data-path="${cm:sign(memberStay.letter)}" data-fancybox-type="image" href="${ctx}/pic?path=${cm:sign(memberStay.letter)}">
      查看
    </a>
    </c:if>
  </td>
</tr>
<tr>
  <td class="bg-right" colspan="2">
    留学国家
  </td>
  <td class="bg-left" colspan="2">
    ${memberStay.country}
  </td>
  <td class="bg-right" style="white-space: nowrap">
    留学学校（院系）
  </td>
  <td class="bg-left" colspan="2">
    ${memberStay.school}
  </td>
</tr>
<tr>
  <td class="bg-right" colspan="2">
    留学起止时间
  </td>
  <td class="bg-left" colspan="5">
    ${cm:formatDate(memberStay.startTime,'yyyy/MM')} 至 ${cm:formatDate(memberStay.endTime,'yyyy/MM')}
  </td>

</tr>
<tr>
  <td class="bg-right" colspan="2">
    留学方式
  </td>
  <td class="bg-left" colspan="2">
    ${MEMBER_STAY_ABROAD_TYPE_MAP_MAP.get(memberStay.abroadType)}
  </td>
  <td class="bg-right" >
    预计回国时间
  </td>
  <td class="bg-left" colspan="2">
    ${cm:formatDate(memberStay.overDate,'yyyy-MM')}
  </td>
</tr>
  </c:if>
<c:if test="${type==MEMBER_STAY_TYPE_INTERNAL}">
<tr>
  <td class="bg-right">
    联系人姓名
  </td>
  <td class="bg-left" colspan="3">
      ${memberStay.name1}
  </td>

  <td class="bg-right">
    联系人手机
  </td>
  <td class="bg-left" colspan="2">
      <t:mask src="${memberStay.mobile1}" type="mobile"/>
  </td>
</tr>
  <tr>
    <td class="bg-right">
      暂留原因
    </td>
    <td class="bg-left" colspan="6">
        ${memberStay.stayReason}
    </td>
  </tr>
  <tr>
    <td class="bg-right" colspan="2">
      户档暂留证明
    </td>
    <td class="bg-left" colspan="5">
      <c:if test="${not empty memberStay.letter}">
        <a class="various" title="户档暂留证明"  data-path="${cm:sign(memberStay.letter)}" data-fancybox-type="image" href="${ctx}/pic?path=${cm:sign(memberStay.letter)}">
          查看
        </a>
      </c:if>
    </td>
  </tr>
  </c:if>
<tr>
  <td class="bg-right" colspan="2">
    原组织关系所在党支部名称
  </td>
  <c:set var="isDirectBranch" value="${cm:isDirectBranch(memberStay.partyId)}"/>
  <td class="bg-left" colspan="${(!isDirectBranch&&memberStay.status>=MEMBER_STAY_STATUS_PARTY_VERIFY)?2:5}">
      ${cm:displayParty(memberStay.partyId, memberStay.branchId)}
  </td>
  <c:if test="${!isDirectBranch&&memberStay.status>=MEMBER_STAY_STATUS_PARTY_VERIFY}">
    <td class="bg-right">
      原组织关系所在党支部负责人姓名、电话
    </td>
    <td class="bg-left" colspan="2">
      ${cm:getUserById(memberStay.orgBranchAdminId).realname} <br/>
      <t:mask src="${memberStay.orgBranchAdminPhone}" type="mobile"/>
    </td>
  </c:if>
</tr>
<tr>
  <td class="bg-right" colspan="2">
    申请保留组织关系起止时间
  </td>
  <td class="bg-left" colspan="2">
    ${cm:formatDate(memberStay.saveStartTime,'yyyy/MM')} 至 ${cm:formatDate(memberStay.saveEndTime,'yyyy/MM')}
  </td>
  <td class="bg-right">
    党费交纳截止时间
  </td>
  <td class="bg-left" colspan="2">
    ${cm:formatDate(memberStay.payTime,'yyyy-MM')}
  </td>
</tr>
<tr>
  <td class="bg-right" colspan="2">
    申请时间
  </td>
  <td class="bg-left" colspan="5">
    ${cm:formatDate(memberStay.createTime,'yyyy-MM-dd HH:mm:ss')}
  </td>
</tr>
<tr>
  <c:if test="${!isDirectBranch&&memberStay.status>=MEMBER_STAY_STATUS_PARTY_VERIFY}">
    <td class="bg-right" colspan="2">
      暂留所在党支部名称
    </td>
    <td class="bg-left" colspan="2">
        ${cm:displayParty(null, memberStay.toBranchId)}
    </td>
  </c:if>
  <td class="bg-right" colspan="${(!isDirectBranch&&memberStay.status>=MEMBER_STAY_STATUS_PARTY_VERIFY)?'':2}">
    状态
  </td>
  <td class="bg-left" style="min-width: 80px" colspan="${(!isDirectBranch&&memberStay.status>=MEMBER_STAY_STATUS_PARTY_VERIFY)?2:5}">
    <c:if test="${empty memberStay.status}"><span style="color:red">未提交</span></c:if>
    ${MEMBER_STAY_STATUS_MAP.get(memberStay.status)}
    <c:if test="${_user.id==memberStay.userId}">
    &nbsp;
    <c:if test="${memberStay.status==MEMBER_STAY_STATUS_APPLY}">
      <small>
        <button class="btn btn-white btn-warning" onclick="_applyBack(${memberStay.id})">
          <i class="fa fa-undo"></i>
          撤销申请
        </button>
      </small>
    </c:if>
    </c:if>
  </td>
</tr>
</tbody>
<script>
  $.register.fancybox();
</script>