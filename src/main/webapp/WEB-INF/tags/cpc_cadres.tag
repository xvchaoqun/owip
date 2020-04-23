<%@ tag import="sys.constants.SystemConstants" %>
<%@ tag description="干部职数配置" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="cm" uri="http://java.sun.com/jsp/jstl/custom" %>
<%@ attribute name="cadrePosts" type="java.util.List" required="true" %>
<c:set var="PERMISSION_CADREADMIN" value="<%=SystemConstants.PERMISSION_CADREADMIN%>"/>
<c:set value="${_pMap['cadrePost_vacant']}" var="cadrePost_vacant"/>
<%--<c:set var="hasCadreViewAuth" value="${cm:isPermitted('cadre:archive') && cm:isPermitted(PERMISSION_CADREADMIN)}"/>--%>
<c:set var="hasCadreViewAuth" value="${cm:isPermitted('cadre:archive')}"/>

<c:forEach items="${cadrePosts}" var="p" varStatus="_vs">
    <c:if test="${p.isMainPost}">
        <c:if test="${cadrePost_vacant=='true'}">
            <c:set var="iscontain" value="0" />
            <c:set var="post" value="" />
            <c:forEach items="${cadrePosts}" var="cadrePost" varStatus="count">
                <c:if test="${cadrePost.cadreId==p.cadreId&&cadrePost.isMainPost}">
                    <c:set var="iscontain" value="${count.index}" />
                    <c:set var="post" value="${cadrePost.post}${post==''?'':'、'}${post}" />
                </c:if>
            </c:forEach>
            <c:if test="${iscontain!='0'&&_vs.index==iscontain}">
                <a <c:if test="${hasCadreViewAuth}">
                    href="${ctx}/#/cadre_view?cadreId=${p.cadre.id}&hideBack=1" target="_blank"
                </c:if>
                        <c:if test="${!hasCadreViewAuth}"> href="javascript:;" </c:if>
                        data-tooltip="tooltip"
                        data-container="body" data-html="true"
                        data-original-title="${post}">${p.cadre.realname}</a>
                ${_vs.last?"":"、"}
            </c:if>
            <c:if test="${iscontain=='0'}">
                <a <c:if test="${hasCadreViewAuth}">
                        href="${ctx}/#/cadre_view?cadreId=${p.cadre.id}&hideBack=1" target="_blank"
                    </c:if>
                    <c:if test="${!hasCadreViewAuth}"> href="javascript:;" </c:if>
                   data-tooltip="tooltip"
                   data-container="body" data-html="true"
                   data-original-title="${p.post}">${p.cadre.realname}</a>
                ${_vs.last?"":"、"}
            </c:if>
        </c:if>

        <c:if test="${cadrePost_vacant=='false'}">
            <a <c:if test="${hasCadreViewAuth}">
                href="${ctx}/#/cadre_view?cadreId=${p.cadre.id}&hideBack=1" target="_blank"
            </c:if>
                    <c:if test="${!hasCadreViewAuth}"> href="javascript:;" </c:if>
                    data-tooltip="tooltip"
                    data-container="body" data-html="true"
                    data-original-title="${p.post}">${p.cadre.realname}</a>
        </c:if>
    </c:if>

    <c:if test="${!p.isMainPost && p.isCpc}">
        <c:if test="${cadrePost_vacant=='true'}">
            <span class="isCpc1"><a <c:if test="${hasCadreViewAuth}">
                                        href="${ctx}/#/cadre_view?cadreId=${p.cadre.id}&hideBack=1" target="_blank"
                                    </c:if>
                                    <c:if test="${!hasCadreViewAuth}"> href="javascript:;" </c:if>
                                    data-tooltip="tooltip" data-container="body" data-html="true"
                                    data-original-title="${p.post}">${p.cadre.realname}(兼任)</a></span>
        </c:if>
        <c:if test="${cadrePost_vacant=='false'}">
            <span class="isCpc">(<a <c:if test="${hasCadreViewAuth}">
                    href="${ctx}/#/cadre_view?cadreId=${p.cadre.id}&hideBack=1" target="_blank"
                </c:if>
                <c:if test="${!hasCadreViewAuth}"> href="javascript:;" </c:if>
                data-tooltip="tooltip" data-container="body" data-html="true"
                data-original-title="${p.post}">${p.cadre.realname}</a>)</span>
        </c:if>
    </c:if>
    <c:if test="${!p.isMainPost && !p.isCpc}">
        <span class="notCpc">(<a <c:if test="${hasCadreViewAuth}">
                                        href="${ctx}/#/cadre_view?cadreId=${p.cadre.id}&hideBack=1" target="_blank"
                                    </c:if>
                                    <c:if test="${!hasCadreViewAuth}"> href="javascript:;"</c:if>
              data-tooltip="tooltip" data-container="body"
              data-html="true"
              data-original-title="${p.post}">${p.cadre.realname}</a>)</span>
    </c:if>
    ${cadrePost_vacant=='true'?(_vs.last||p.isMainPost?"":"、"):(_vs.last?"":"、")}
</c:forEach>
