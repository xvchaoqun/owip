<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="space-4"></div>
<button type="button"
        data-width="750" data-url="${ctx}/crsPost_requirement?id=${param.id}"
        class="popupBtn btn btn-sm btn-success">
    <i class="ace-icon fa fa-edit"></i>
    编辑
</button>
<div>
    ${crsPost.requirement}
</div>