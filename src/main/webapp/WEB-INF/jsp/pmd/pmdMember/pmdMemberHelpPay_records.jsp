<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:if test="${fn:length(pmdMembers)==0}">
  <div class="well" style="font-size: 18px;font-weight: bolder">${pmdMember.user.realname}不存在未缴费记录</div>
</c:if>
<c:if test="${fn:length(pmdMembers)>0}">
<table class="table table-actived table-striped table-bordered table-hover">
  <thead>
  <tr>
    <th>姓名</th>
    <th>学工号</th>
    <th width="100">缴费月份</th>
    <th>缴费金额</th>
    <th width="100">代缴</th>
  </tr>
  </thead>
  <tbody>
  <c:forEach items="${pmdMembers}" var="pmdMember" varStatus="st">
    <tr>
      <td nowrap>${pmdMember.user.realname}</td>
      <td nowrap>${pmdMember.user.code}</td>
      <td nowrap>${cm:formatDate(pmdMember.payMonth, "yyyy年MM月")}</td>
      <td nowrap>${pmdMember.duePay}</td>
      <td nowrap>
        <c:if test="${_user.id!=pmdMember.userId && pmdMember.duePay>0}">
        <button data-url="${ctx}/user/pmd/payConfirm?id=${pmdMember.id}&isSelfPay=0&isMemberHelpPay=1"
                class="popupBtn btn btn-success btn-xs">
          <i class="fa fa-rmb"></i> 代缴
        </button>
        </c:if>
        <c:if test="${_user.id==pmdMember.userId || empty pmdMember.duePay || pmdMember.duePay<=0}">
                -
        </c:if>
      </td>
    </tr>
  </c:forEach>
  </tbody>
</table>
</c:if>