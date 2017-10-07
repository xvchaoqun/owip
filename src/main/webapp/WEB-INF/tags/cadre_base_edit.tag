<%@ tag description="干部基本信息完整性检查编辑跳转地址" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="notExist" type="java.lang.Boolean" required="true" %>
<%@ attribute name="toEdit" type="java.lang.Boolean" required="true" %>
<c:if test="${notExist}">
  <c:if test="${toEdit}">
    <a href="javascript:;" class="loadPage" data-url="${ctx}/cadre_view?cadreId=${param.cadreId}&type=1">编辑</a>
  </c:if>
  <c:if test="${!toEdit}">
    <a href="javascript:;" class="hashchange" data-url="${ctx}/modifyBaseApply">编辑</a>
  </c:if>
</c:if>