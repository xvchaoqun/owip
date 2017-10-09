<%@ tag import="sys.constants.SystemConstants" %>
<%@ tag description="干部基本信息完整性检查编辑跳转地址" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ attribute name="notExist" type="java.lang.Boolean" required="true" %>
<%@ attribute name="toEdit" type="java.lang.Boolean" required="true" %>

<c:set var="ROLE_ADMIN" value="<%=SystemConstants.ROLE_ADMIN%>"/>
<c:set var="ROLE_CADREADMIN" value="<%=SystemConstants.ROLE_CADREADMIN%>"/>
<c:set var="_editUrl" value="${ctx}/user/cadre"/>
<c:set var="_editType" value="loadPage"/>
<shiro:hasAnyRoles name="${ROLE_ADMIN},${ROLE_CADREADMIN}">
  <c:set var="_editUrl" value="${ctx}/cadre_view"/>
  <c:set var="_editType" value="openView"/>
</shiro:hasAnyRoles>

<c:if test="${notExist}">
  <c:if test="${toEdit}">
    <a href="javascript:;" class="${_editType}" data-url="${_editUrl}?cadreId=${param.cadreId}&type=1">编辑</a>
  </c:if>
  <c:if test="${!toEdit}">
    <a href="javascript:;" class="hashchange" data-url="${ctx}/modifyBaseApply">编辑</a>
  </c:if>
</c:if>