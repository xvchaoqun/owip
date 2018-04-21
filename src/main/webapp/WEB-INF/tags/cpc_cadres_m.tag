<%@ tag description="干部职数配置" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="cadrePosts" type="java.util.List" required="true" %>
<%@ attribute name="backTo" type="java.lang.String" required="false" %>
<c:forEach items="${cadrePosts}" var="p" varStatus="_vs">
    <a href="javascript:;" class="openView" data-open-by="page" data-url="${ctx}/m/cadre_info?backTo=${backTo}&unitId=${param.unitId}&cadreId=${p.cadre.id}">
    <c:if test="${p.isMainPost}">${p.cadre.realname}</c:if>
    <c:if test="${!p.isMainPost && p.isCpc}"><span class="isCpc">(${p.cadre.realname})</span></c:if>
    <c:if test="${!p.isMainPost && !p.isCpc}"><span class="notCpc">(${p.cadre.realname})</span></c:if>
    </a>
    ${_vs.last?"":"、"}</c:forEach>
