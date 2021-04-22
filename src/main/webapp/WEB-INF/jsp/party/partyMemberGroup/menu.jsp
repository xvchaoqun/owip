<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/party/constants.jsp" %>
<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
  <li  class="<c:if test="${status==1&&type==0}">active</c:if>">
    <a href="javascript:;" class="loadPage" data-url="${ctx}/partyMemberGroup?status=1"><i class="fa fa-list"></i> 领导班子</a>
  </li>
  <c:if test="${_p_use_inside_pgb}">
    <li  class="<c:if test="${status==1&&type==1}">active</c:if>">
      <a href="javascript:;" class="loadPage" data-url="${ctx}/partyMemberGroup?status=1&&type=1"><i class="fa fa-th-list"></i> 内设党总支领导班子</a>
    </li>
  </c:if>
  <li  class="<c:if test="${status==2}">active</c:if>">
    <a href="javascript:;" class="loadPage" data-url="${ctx}/partyMemberGroup?status=2"><i class="fa fa-users"></i> 领导班子成员库</a>
  </li>
  <shiro:hasPermission name="party:list">
  <li  class="<c:if test="${status==-1}">active</c:if>">
    <a href="javascript:;" class="loadPage" data-url="${ctx}/partyMemberGroup?status=-1"><i class="fa fa-history"></i> 已换届领导班子</a>
  </li>
  </shiro:hasPermission>
</ul>