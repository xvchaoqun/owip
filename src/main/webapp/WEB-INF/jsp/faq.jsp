<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row" style="padding: 0 100px 0 100px">
    <c:if test="${empty param.type}">
        <c:redirect url="/"/>
    </c:if>
    <c:set var="htmlFragment" value="${cm:getHtmlFragment(param.type)}"/>
    <c:if test="${empty htmlFragment}">
        <c:redirect url="/"/>
    </c:if>
    ${cm:htmlUnescape(htmlFragment.content)}
</div>
