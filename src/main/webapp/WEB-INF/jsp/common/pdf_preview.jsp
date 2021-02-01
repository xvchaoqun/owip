<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<fmt:message key="upload.path" bundle="${spring}" var="_uploadPath"/>
<c:set value="${cm:forcePdfPath(path)}" var="pdfPath"/>
<c:set value="${_uploadPath}${pdfPath}" var="_fullPath"/>
<c:if test="${empty path || !cm:exists(_fullPath)}">
    <div class="modal-header">
        <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
        <h3>提示</h3>
    </div>
    <div class="modal-body">
        文件不存在：${pdfPath}
    </div>
    <div class="modal-footer">
        <a href="javascript:;" data-dismiss="modal" class="btn btn-default">关闭</a>
    </div>
</c:if>
<c:if test="${not empty path && cm:exists(_fullPath)}">
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>
        ${filename}
    </h3>
</div>
<div class="modal-body" style="background-color: #eaeaea;padding: 0">
    <c:forEach begin="1" end="${cm:getPages(_fullPath)}" var="pageNo" varStatus="vs">
        <div class="block-loading" oncontextmenu="return false"
      onselectstart="return false" ondragstart="return false" οncοpy="return false">
        <img data-src="${ctx}/pdf_image?path=${cm:sign(path)}&pageNo=${pageNo}"
          src="data:image/gif;base64,R0lGODlhAQABAAAAACH5BAEKAAEALAAAAAABAAEAAAICTAEAOw==" onload="lzld(this)"
            style="width: 100%;padding-bottom: ${vs.last?0:5}px;">
        </div>
    </c:forEach>
</div>
<div class="modal-footer">
    <c:if test="${!np}">
        <a href="javascript:;" data-dismiss="modal" class="printBtn btn btn-info"
           data-url="${ctx}/pdf?path=${cm:sign(pdfPath)}"><i class="fa fa-print"></i> 打印</a>
    </c:if>
    <c:if test="${!nd}">
        <a href="javascript:;" data-url="${ctx}/attach_download?path=${cm:sign(path)}&filename=${filename}"
           class="downloadBtn btn btn-success"><i class="fa fa-download"></i> 下载</a>
    </c:if>
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default"><i class="fa fa-times"></i> 关闭</a>
</div>
<style>
    .modal-content {
        min-width: 900px;
    }
</style>
</c:if>