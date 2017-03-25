<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<table class="table table-unhover table-bordered table-striped">
    <c:forEach items="${sitemaps}" var="topSitemap">
        <tr>
            <td width="200" style="vertical-align: middle;text-align: left;white-space: nowrap">
                <h2>
                    <c:if test="${not empty topSitemap.url}">
                        <i class="ace-icon fa fa-circle"></i> <a href="${topSitemap.url}">${topSitemap.title}</a>
                    </c:if>
                    <c:if test="${empty topSitemap.url}">
                        <i class="ace-icon fa fa-circle"></i> ${topSitemap.title}
                    </c:if>
                </h2>
            </td>
            <td class="bg-left">
                <ul class="list-unstyled spaced2 lead">
                    <c:forEach items="${topSitemap.subSitemaps}" var="subSitemap">
                        <li>
                            <i class="ace-icon fa fa-hand-o-right green"></i>
                            <a href="${subSitemap.url}">${subSitemap.title}</a>
                        </li>
                    </c:forEach>
                </ul>
            </td>
        </tr>
    </c:forEach>
</table>



