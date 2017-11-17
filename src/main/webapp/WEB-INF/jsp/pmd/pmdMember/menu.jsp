<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<ul class="nav nav-tabs">
  <li class="${(cls==1||cls==2)?'active':''}">
    <a href="javascript:;" class="openView"
       data-url="${ctx}/pmd/pmdMember?branchId=${param.branchId}&monthId=${param.monthId}&partyId=${param.partyId}">缴费详情</a>
  </li>
  <li class="${cls==4?'active':''}">
    <a href="javascript:;" class="openView"
       data-url="${ctx}/pmd/pmdMember?cls=4&branchId=${param.branchId}&monthId=${param.monthId}&partyId=${param.partyId}">补缴列表</a>
  </li>
</ul>