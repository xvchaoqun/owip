<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<fmt:message key="upload.path" bundle="${spring}" var="_uploadPath"/>
<c:set value="${cm:forcePdfPath(path)}" var="pdfPath"/>
<c:set value="${_uploadPath}${pdfPath}" var="_fullPath"/>
<c:set var="exists" value="${not empty path && cm:exists(_fullPath)}"/>
<!DOCTYPE html>
<html>
<head>
    <title>${filename}</title>
    <script src="${ctx}/extend/js/lazyload.js"></script>
</head>
<body>
<c:if test="${!exists}">
    文件不存在：${pdfPath}
</c:if>
<c:if test="${exists}">
    <c:forEach begin="1" end="${cm:getPages(_fullPath)}" var="pageNo">
    <img data-src="${ctx}/pdf_image?path=${pdfPath}&pageNo=${pageNo}"
         src="data:image/gif;base64,R0lGODlhAQABAAAAACH5BAEKAAEALAAAAAABAAEAAAICTAEAOw==" onload="lzld(this)" style="width: 700px">
    </c:forEach>
</c:if>
</body>
</html>
