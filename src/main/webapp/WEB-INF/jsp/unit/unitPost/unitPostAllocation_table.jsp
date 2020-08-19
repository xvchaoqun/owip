<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="${_pMap['upa_displayPosts']}" var="_upa_displayPosts"/>
<c:set value="<%=SystemConstants.UNIT_POST_DISPLAY_NORMAL%>" var="UNIT_POST_DISPLAY_NORMAL"/>
<c:set value="<%=SystemConstants.UNIT_POST_DISPLAY_KEEP%>" var="UNIT_POST_DISPLAY_KEEP"/>
<c:set value="<%=SystemConstants.UNIT_POST_DISPLAY_NOT_CPC%>" var="UNIT_POST_DISPLAY_NOT_CPC"/>
<c:if test="${_upa_displayPosts==UNIT_POST_DISPLAY_NORMAL||_upa_displayPosts==UNIT_POST_DISPLAY_KEEP}">
  <jsp:include page="unitPostAllocation_table1.jsp"/>
</c:if>
<c:if test="${_upa_displayPosts==UNIT_POST_DISPLAY_NOT_CPC}">
  <jsp:include page="unitPostAllocation_table2.jsp"/>
</c:if>