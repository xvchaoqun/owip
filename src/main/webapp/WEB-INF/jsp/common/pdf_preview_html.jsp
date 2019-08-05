<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<fmt:message key="upload.path" bundle="${spring}" var="_uploadPath"/>
<c:set value="${_uploadPath}${path}" var="_path"/>
<c:set var="exists" value="${not empty path && cm:exists(_path)}"/>
<c:if test="${!exists}">
    <div class="swf-file none well">
            ${empty path?"请上传文件":"文件不存在"}${path}
    </div>
</c:if>
<c:if test="${exists}">
    <img src="${ctx}/pdf_image?path=${path}" style="width: 700px">
</c:if>