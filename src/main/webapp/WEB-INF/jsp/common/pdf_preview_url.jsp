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
    <style>
        .block-loading{
            background-image: url('/img/loading.gif');
            background-repeat: no-repeat;
            background-position:center;
            -webkit-touch-callout:none;  /*系统默认菜单被禁用*/
            -webkit-user-select:none; /*webkit浏览器*/
            -khtml-user-select:none; /*早期浏览器*/
            -moz-user-select:none;/*火狐*/
            -ms-user-select:none; /*IE10*/
            user-select:none;
      }
    </style>
</head>
<body style="background-color: #eaeaea;padding: 0;margin:0" oncontextmenu="return false"
      onselectstart="return false" ondragstart="return false" οncοpy="return false">
<c:if test="${!exists}">
    文件不存在：${pdfPath}
</c:if>
<c:if test="${exists}">
    <c:forEach begin="1" end="${cm:getPages(_fullPath)}" var="pageNo" varStatus="vs">
        <div class="block-loading">
        <img data-src="${ctx}/pdf_image?path=${cm:sign(path)}&pageNo=${pageNo}"
          src="data:image/gif;base64,R0lGODlhAQABAAAAACH5BAEKAAEALAAAAAABAAEAAAICTAEAOw==" onload="lzld(this)"
            style="width: 100%;padding-bottom: ${vs.last?0:5}px;">
        </div>
    </c:forEach>
</c:if>
</body>
</html>
