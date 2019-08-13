<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set value="${_pMap['adFormType']}" var="_p_adFormType"/>
<c:set value="<%=CadreConstants.CADRE_ADFORMTYPE_BJ%>" var="CADRE_ADFORMTYPE_BJ"/>
<c:set value="<%=CadreConstants.CADRE_ADFORMTYPE_ZZB_GB2312%>" var="CADRE_ADFORMTYPE_ZZB_GB2312"/>
<c:set value="<%=CadreConstants.CADRE_ADFORMTYPE_ZZB_SONG%>" var="CADRE_ADFORMTYPE_ZZB_SONG"/>
<c:if test="${_p_adFormType==CADRE_ADFORMTYPE_BJ}">
  <jsp:include page="adForm1.jsp"/>
</c:if>
<c:if test="${empty _p_adFormType || _p_adFormType==CADRE_ADFORMTYPE_ZZB_SONG
||_p_adFormType==CADRE_ADFORMTYPE_ZZB_GB2312}">
  <jsp:include page="adForm2.jsp"/>
</c:if>
