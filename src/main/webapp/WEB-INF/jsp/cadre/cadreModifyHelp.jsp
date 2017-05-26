<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:if test="${hasDirectModifyCadreAuth}">
  ${cm:getHtmlFragment('modify_cadre_info_direct').content}
</c:if>
<c:if test="${!hasDirectModifyCadreAuth}">
  ${cm:getHtmlFragment('modify_cadre_info_request').content}
</c:if>

