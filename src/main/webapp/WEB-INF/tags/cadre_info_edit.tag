<%@ tag import="sys.constants.SystemConstants" %>
<%@ tag import="sys.constants.RoleConstants" %>
<%@ tag description="干部信息完整性检查编辑跳转地址" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cm" uri="http://java.sun.com/jsp/jstl/custom" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ attribute name="editUrl" type="java.lang.String" required="true" description="地址参数" %>
<%@ attribute name="permission" type="java.lang.String" required="false" %>
<%@ attribute name="applyUrl" type="java.lang.String" required="false" %>
<%@ attribute name="notExist" type="java.lang.Boolean" required="true" %>
<%@ attribute name="toEdit" type="java.lang.Boolean" required="true" %>
<%@ attribute name="displayEdit" type="java.lang.Boolean" required="true" %>
<%@ attribute name="updateName" type="java.lang.String" required="false"%>
<c:set var="PERMISSION_CADREADMIN" value="<%=RoleConstants.PERMISSION_CADREADMIN%>"/>
<c:set var="PERMISSION_CADREADMINSELF" value="<%=RoleConstants.PERMISSION_CADREADMINSELF%>"/>

<c:if test="${displayEdit}">
<c:if test="${notExist}">
  <c:if test="${toEdit}">
    <a href="javascript:;" class="cadre-info-check-edit" data-url="${editUrl}">编辑</a>
  </c:if>
  <c:if test="${!toEdit && not empty applyUrl && (empty permission || cm:userIsPermitted(_user.id, permission))}">
    <c:if test="${cm:isPermitted('modifyTableApply:list')}">
    <a href="javascript:;" class="hashchange" data-url="${applyUrl}">编辑</a>
    </c:if>
  </c:if>
  <c:if test="${not empty updateName}">
  <input type="checkbox" ${cm:canUpdate(param.cadreId, updateName)?'':'checked'} data-name="${updateName}" name="check" class="cadre-info-check"> 无此类情况
  </c:if>
</c:if>
</c:if>