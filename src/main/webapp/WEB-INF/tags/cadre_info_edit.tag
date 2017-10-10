<%@ tag import="sys.constants.SystemConstants" %>
<%@ tag description="干部信息完整性检查编辑跳转地址" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cm" uri="http://java.sun.com/jsp/jstl/custom" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ attribute name="editUrl" type="java.lang.String" required="true" %>
<%@ attribute name="applyUrl" type="java.lang.String" required="false" %>
<%@ attribute name="notExist" type="java.lang.Boolean" required="true" %>
<%@ attribute name="toEdit" type="java.lang.Boolean" required="true" %>
<%@ attribute name="updateName" type="java.lang.String" required="false"%>

<c:set var="PERMISSION_CADREADMIN" value="<%=SystemConstants.PERMISSION_CADREADMIN%>"/>
<c:set var="_editUrl" value="${ctx}/user/cadre"/>
<c:set var="_editType" value="loadPage"/>
<shiro:hasPermission name="${PERMISSION_CADREADMIN}">
  <c:set var="_editUrl" value="${ctx}/cadre_view"/>
  <c:set var="_editType" value="openView"/>
</shiro:hasPermission>

<c:if test="${notExist}">
  <c:if test="${toEdit}">
    <a href="javascript:;" class="${_editType}" data-url="${_editUrl}${editUrl}">编辑</a>
  </c:if>
  <c:if test="${!toEdit && not empty applyUrl}">
    <a href="javascript:;" class="hashchange" data-url="${applyUrl}">编辑</a>
  </c:if>
  <c:if test="${not empty updateName}">
  <input type="checkbox" ${cm:canUpdate(param.cadreId, updateName)?'':'checked'} data-name="${updateName}" name="check" class="cadre-info-check"> 无此类情况
  </c:if>
</c:if>