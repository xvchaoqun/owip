<%@ tag import="sys.constants.SystemConstants" %>
<%@ tag description="干部职数配置" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="cm" uri="http://java.sun.com/jsp/jstl/custom" %>
<%@ attribute name="cadrePosts" type="java.util.List" required="true" %>
<c:set var="PERMISSION_CADREADMIN" value="<%=SystemConstants.PERMISSION_CADREADMIN%>"/>

<c:set var="hasCadreViewAuth" value="${cm:isPermitted('cadre:view') && cm:isPermitted(PERMISSION_CADREADMIN)}"/>
<c:forEach items="${cadrePosts}" var="p" varStatus="_vs"><c:if test="${p.isMainPost}">
    <a <c:if test="${hasCadreViewAuth}">
            href="${ctx}/#/cadre_view?cadreId=${p.cadre.id}&hideBack=1" target="_blank"
        </c:if>
        <c:if test="${!hasCadreViewAuth}"> href="javascript:;" </c:if>
       data-tooltip="tooltip"
       data-container="body" data-html="true"
       data-original-title="${p.post}">${p.cadre.realname}</a></c:if><c:if test="${!p.isMainPost && p.isCpc}">
        <span class="isCpc">(<a <c:if test="${hasCadreViewAuth}">
                                    href="${ctx}/#/cadre_view?cadreId=${p.cadre.id}&hideBack=1" target="_blank"
                                </c:if>
                                <c:if test="${!hasCadreViewAuth}"> href="javascript:;" </c:if>
                                data-tooltip="tooltip" data-container="body" data-html="true"
                                data-original-title="${p.post}">${p.cadre.realname}</a>)</span></c:if><c:if
        test="${!p.isMainPost && !p.isCpc}">
  <span class="notCpc">(<a <c:if test="${hasCadreViewAuth}">
                                    href="${ctx}/#/cadre_view?cadreId=${p.cadre.id}&hideBack=1" target="_blank"
                                </c:if>
                                <c:if test="${!hasCadreViewAuth}"> href="javascript:;" </c:if>
          data-tooltip="tooltip" data-container="body"
          data-html="true"
          data-original-title="${p.post}">${p.cadre.realname}</a>)</span></c:if>${_vs.last?"":"、"}</c:forEach>
