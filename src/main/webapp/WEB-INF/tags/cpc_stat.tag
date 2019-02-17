<%@ tag description="干部职数配置统计" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cm" uri="http://java.sun.com/jsp/jstl/custom" %>
<%@ attribute name="dataList" type="java.util.List" required="true" %>
<%@ attribute name="unitType" type="java.lang.String" required="true" %>
<c:forEach items="${dataList}" var="data" varStatus="vs">
    <td class=${((vs.index+1)%4==0 && !vs.first && !vs.last)?'xl672001':'xl662001'} width=54>
        <c:choose>
            <c:when test="${(vs.index>=5 && (vs.index-5)%4==0) || (vs.index>=6 && (vs.index-6)%4==0)}">
                <c:if test="${(vs.index-5)/4==0 || (vs.index-6)/4==0}">
                    <c:set var="adminLevel" value="${cm:getMetaTypeByCode('mt_admin_level_main').id}"/>
                    <c:set var="isMainPost" value="${(vs.index-5)/4==0}"/>
                </c:if>
                <c:if test="${(vs.index-5)/4==1 || (vs.index-6)/4==1}">
                    <c:set var="adminLevel" value="${cm:getMetaTypeByCode('mt_admin_level_vice').id}"/>
                    <c:set var="isMainPost" value="${(vs.index-5)/4==1}"/>
                </c:if>
                <c:if test="${(vs.index-5)/4==2 || (vs.index-6)/4==2}">
                    <c:set var="adminLevel" value="${cm:getMetaTypeByCode('mt_admin_level_none').id}"/>
                    <c:set var="isMainPost" value="${(vs.index-5)/4==2}"/>
                </c:if>
                <c:if test="${data>0}">
                    <a href="javascript:;" class="popupBtn" data-url="${ctx}/unitPost_unitType_cadres?adminLevel=${adminLevel}&isMainPost=${isMainPost}&unitType=${unitType}">${data}</a>
                </c:if>
                <c:if test="${data==0}">0</c:if>
             </c:when>
            <c:otherwise>${data}</c:otherwise>
        </c:choose>
    </td>
</c:forEach>
