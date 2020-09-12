<%@ tag description="数据脱敏" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cm" uri="http://java.sun.com/jsp/jstl/custom" %>
<%@ attribute name="src" type="java.lang.String" required="true" %>
<%@ attribute name="type" type="java.lang.String" required="true" %>
<c:set value="${_pMap['privateDataMask']=='true'}" var="_p_privateDataMask"/>
<c:if test="${_p_privateDataMask}"><span class="mask" data-src="${cm:base64Encode(src)}">${cm:mask(src, type)}</span></c:if>
<c:if test="${!_p_privateDataMask}">${src}</c:if>