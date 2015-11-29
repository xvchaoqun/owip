<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>  
<c:forEach items="${roles}" var="role" varStatus="st">
${role}<c:if test="${!st.last}">,</c:if>
</c:forEach>