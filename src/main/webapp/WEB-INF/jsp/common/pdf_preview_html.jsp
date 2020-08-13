<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<fmt:message key="upload.path" bundle="${spring}" var="_uploadPath"/>

<c:set value="${cm:forcePdfPath(path)}" var="pdfPath"/>
<c:set value="${_uploadPath}${pdfPath}" var="_fullPath"/>
<c:set var="exists" value="${not empty path && cm:exists(_fullPath)}"/>
<c:if test="${!exists}">
    <div class="swf-file none well">
            ${empty path?"请上传文件":"文件不存在"}${pdfPath}
    </div>
</c:if>
<c:if test="${exists}">
    <c:forEach begin="1" end="${cm:getPages(_fullPath)}" var="pageNo" varStatus="vs">
        <div class="pdf-page" oncontextmenu="return false"
      onselectstart="return false" ondragstart="return false" οncοpy="return false">
        <img data-src="${ctx}/pdf_image?path=${cm:sign(path)}&pageNo=${pageNo}"
          src="data:image/gif;base64,R0lGODlhAQABAAAAACH5BAEKAAEALAAAAAABAAEAAAICTAEAOw==" onload="lzld(this)"
            style="width: 100%;padding-bottom: ${vs.last?0:5}px;">
        </div>
    </c:forEach>
</c:if>