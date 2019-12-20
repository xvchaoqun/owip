<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="ROLE_DP_PARTY" value="<%=RoleConstants.ROLE_DP_PARTY%>"/>
<c:set var="ROLE_DP_ADMIN" value="<%=RoleConstants.ROLE_DP_ADMIN%>"/>
<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
  <li class="${cls==2?'active':''}">
  <a href="javascript:;" class="loadPage" data-url="${ctx}/dp/dpMember?cls=2"}><i class="fa fa-th${cls==2?'-large':''}"></i> 在职党派成员(${cm:trimToZero(teacher_normalCount)})</a>
  </li>
  <li class="${cls==3?'active':''}">
  <a href="javascript:;" class="loadPage" data-url="${ctx}/dp/dpMember?cls=3"}><i class="fa fa-th${cls==3?'-large':''}"></i> 离退休党派成员(${cm:trimToZero(teacher_retireCount)})</a>
  </li>
  <li class="${cls==10?'active':''}">
    <a href="javascript:;" class="loadPage" data-url="${ctx}/dp/dpMember?cls=10"}><i class="fa fa-th${cls==10?'-large':''}"></i>
      全部民主党派成员(${cm:trimToZero(teacher_normalCount + teacher_retireCount)})</a>
  </li>
  <li class="${cls==7?'active':''}">
    <a href="javascript:;" class="loadPage" data-url="${ctx}/dp/dpMember?cls=7"}><i class="fa fa-th${cls==7?'-large':''}"></i> 已移除党派成员(${cm:trimToZero(teacher_transferCount)})</a>
  </li>
</ul>
