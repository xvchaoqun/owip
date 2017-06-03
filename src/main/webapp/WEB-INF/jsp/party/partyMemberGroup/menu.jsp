<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
  <li  class="<c:if test="${status==1}">active</c:if>">
    <a href="javascript:;" class="loadPage" data-url="${ctx}/partyMemberGroup?status=1"><i class="fa fa-circle-o-notch fa-spin"></i> 领导班子</a>
  </li>
  <li  class="<c:if test="${status==2}">active</c:if>">
    <a href="javascript:;" class="loadPage" data-url="${ctx}/partyMemberGroup?status=2"><i class="fa fa-users"></i> 分党委委员库</a>
  </li>
  <li  class="<c:if test="${status==-1}">active</c:if>">
    <a href="javascript:;" class="loadPage" data-url="${ctx}/partyMemberGroup?status=-1"><i class="fa fa-trash"></i> 已删除领导班子</a>
  </li>
</ul>