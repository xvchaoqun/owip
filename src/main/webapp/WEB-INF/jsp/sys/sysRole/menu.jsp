<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="<%=SystemConstants.SYS_ROLE_TYPE_ADD%>" var="SYS_ROLE_TYPE_ADD"/>
<c:set value="<%=SystemConstants.SYS_ROLE_TYPE_MINUS%>" var="SYS_ROLE_TYPE_MINUS"/>
<shiro:hasPermission name="sysProperty:*">
<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
  <li class="<c:if test="${type==SYS_ROLE_TYPE_ADD}">active</c:if>">
    <a href="javascript:;" class="loadPage" data-url="${ctx}/sysRole?type=${SYS_ROLE_TYPE_ADD}"><i class="fa fa-plus-circle"></i> 角色列表（加权限）</a>
  </li>
  <li class="<c:if test="${type==SYS_ROLE_TYPE_MINUS}">active</c:if>">
    <a href="javascript:;" class="loadPage" data-url="${ctx}/sysRole?type=${SYS_ROLE_TYPE_MINUS}"><i class="fa fa-minus-circle"></i> 角色列表（减权限）</a>
  </li>
</ul>
  </shiro:hasPermission>