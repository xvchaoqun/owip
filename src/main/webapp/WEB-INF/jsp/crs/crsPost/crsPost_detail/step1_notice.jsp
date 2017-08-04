<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="space-4"></div>
<form action="${ctx}/crsPost_uploadNotice"
      enctype="multipart/form-data" method="post"
      class="btn-upload-form">
  <button type="button"
          data-loading-text="<i class='fa fa-spinner fa-spin '></i> 上传中..."
          class="btn btn-sm btn-success">
    <i class="ace-icon fa fa-upload"></i>
    上传（PDF文件）
  </button>
  <input type="hidden" name="id" value="${crsPost.id}">
  <input type="file" name="file" id="upload-file">
</form>
<div class="space-4"></div>
<div class="swf-file-view">
  <c:import url="${ctx}/swf/preview?type=html&path=${crsPost.notice}"/>
</div>