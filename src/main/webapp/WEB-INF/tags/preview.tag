<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="cm" uri="http://java.sun.com/jsp/jstl/custom" %>
<%@ tag description="显示pdf或图片文件" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<fmt:setBundle basename="spring" var="spring"/>
<fmt:message key="site.hasLoginPage" bundle="${spring}" var="_hasLoginPage"/>
<fmt:message key="upload.path" bundle="${spring}" var="_uploadPath"/>
<%@ attribute name="filePath" type="java.lang.String" required="true" %>
<%@ attribute name="fileName" type="java.lang.String" required="true" %>
<%@ attribute name="label" type="java.lang.String" required="false" %>
<%@ attribute name="np" type="java.lang.Boolean" required="false" %>
<%@ attribute name="nd" type="java.lang.Boolean" required="false" %>
<c:if test="${not empty filePath}">
    <c:if test="${fn:endsWith(fn:toLowerCase(filePath), '.pdf')}"><a href="javascript:;" class="popupBtn" data-url="${ctx}/pdf_preview?path=${cm:encodeURI(filePath)}&filename=${cm:encodeURI(fileName)}&np=${np}&nd=${nd}">${empty label?"查看":label}</a></c:if>
    <c:if test="${!fn:endsWith(fn:toLowerCase(filePath), '.pdf')}">
        <c:set var="isImage" value="${cm:isImage(_uploadPath, filePath)}"/>
        <c:if test="${isImage}">
            <a class="various" title="${fileName}" data-path="${filePath}"
               data-fancybox-type="image" href="${ctx}/pic?path=${filePath}">${empty label?"查看":label}</a>
        </c:if>
        <c:if test="${!isImage}">
            <a class="downloadBtn" data-type="download" href="javascript:;"
                               data-url="${ctx}/res_download?path=${cm:encodeURI(filePath)}&filename=${fileName}&sign=${cm:sign(filePath)}">${empty label?"下载":label}</a>
        </c:if>
    </c:if>
</c:if>