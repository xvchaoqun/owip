<%@ tag description="干部职数配置" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="cadrePosts" type="java.util.List" required="true" %>
<c:forEach items="${cadrePosts}" var="p" varStatus="_vs"><c:if test="${p.isMainPost}">
    <a href="javascript:;" class="openView"
       data-url="${ctx}/cadre_view?cadreId=${p.cadre.id}" data-tooltip="tooltip"
       data-container="body" data-html="true"
       data-original-title="${p.post}">${p.cadre.realname}</a></c:if><c:if test="${!p.isMainPost && p.isCpc}">
        <span class="isCpc">(<a href="javascript:;" class="openView" data-url="${ctx}/cadre_view?cadreId=${p.cadre.id}"
                                data-tooltip="tooltip" data-container="body" data-html="true"
                                data-original-title="${p.post}">${p.cadre.realname}</a>)</span></c:if><c:if
        test="${!p.isMainPost && !p.isCpc}">
  <span class="notCpc">(<a href="javascript:;" class="openView"
          data-url="${ctx}/cadre_view?cadreId=${p.cadre.id}"
          data-tooltip="tooltip" data-container="body"
          data-html="true"
          data-original-title="${p.post}">${p.cadre.realname}</a>)</span></c:if>${_vs.last?"":"、"}</c:forEach>
