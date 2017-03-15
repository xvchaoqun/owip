<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:forEach items="${sitemaps}" var="topSitemap">
    <c:set var="subSitemapCount" value="${fn:length(topSitemap.subSitemaps)}"></c:set>
    <c:if test="${subSitemapCount==0}">
        <a href="${topSitemap.url}">${topSitemap.title}</a>
        <br/>
     </c:if>
    <c:if test="${subSitemapCount>0}">
    ${topSitemap.title}
    <br/>
    <c:forEach items="${topSitemap.subSitemaps}" var="subSitemap">
        <a href="${subSitemap.url}">${subSitemap.title}</a>
        <br/>
    </c:forEach>
    </c:if>
</c:forEach>
