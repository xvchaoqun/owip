<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set value="${_pMap['adFormType']}" var="_p_adFormType"/>
<c:if test="${empty _p_adFormType || _p_adFormType==1}">
  <jsp:include page="adForm1.jsp"/>
</c:if>
<c:if test="${_p_adFormType==2||_p_adFormType==3}">
  <jsp:include page="adForm2.jsp"/>
</c:if>
