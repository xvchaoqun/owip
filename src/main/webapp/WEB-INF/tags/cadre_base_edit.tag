<%@ tag description="干部基本信息完整性检查编辑跳转地址" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cm" uri="http://java.sun.com/jsp/jstl/custom" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ attribute name="notExist" type="java.lang.Boolean" required="true" %>
<%@ attribute name="toEdit" type="java.lang.Boolean" required="true" %>
<c:if test="${cm:isPermitted('cadre:adminSelf')|| cm:isPermitted('cadre:admin')}">
<c:if test="${notExist}">
  <c:if test="${toEdit}">
    <a href="javascript:;" class="cadre-info-check-edit" data-url="?cadreId=${param.cadreId}&type=1">编辑</a>
  </c:if>
  <c:if test="${!toEdit}">
    <c:if test="${cm:isPermitted('modifyBaseApply:list')}">
    <a href="javascript:;" class="hashchange" data-url="${ctx}/modifyBaseApply?admin=0">编辑</a>
    </c:if>
  </c:if>
</c:if>
</c:if>