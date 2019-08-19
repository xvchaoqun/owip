<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<fmt:message key="upload.path" bundle="${spring}" var="_uploadPath"/>
<jsp:include page="menu.jsp"/>
<div style="width: 900px">
	<div class="bg-grey" style="margin-top: 10px;text-indent: 2em;padding: 10px;">
		${unitFunction.content}
		<c:if test="${empty unitFunction.content}">
			暂无职能内容。
		</c:if>
	</div>
	<div class="buttons" style="position: relative; top: 8px; padding-left: 755px;">
		<a href="javascript:void(0)" class="reloadBtn btn btn-xs btn-primary" data-target="#unitfunctionDiv"
        data-url="${ctx}/unitFunction?unitId=${unitId}&funId=${unitFunction.id}&isEdit=1">
			<i class="fa fa-edit"></i> 修改
		</a>
         <button data-url="${ctx}/unitFunction_batchDel?ids[]=${unitFunction.id}"
                            data-title="删除"
                            data-msg="确定删除此单位职能？"
                            data-callback="_reloadUnitFunction"
                            class="confirm btn btn-danger btn-xs">
                        <i class="fa fa-times"></i> 删除
		 </button>
	</div>
	<c:set value="${_uploadPath}${unitFunction.filePath}" var="_fullPath"/>
	<div style="border:1px dashed;margin-top: -27px;min-height: 100px;max-height: 900px;overflow-y: auto">
		<c:if test="${not empty unitFunction.filePath && cm:exists(_fullPath)}">
			<c:forEach begin="1" end="${cm:getPages(_fullPath)}" var="pageNo">
			<img data-src="${ctx}/pdf_image?path=${unitFunction.filePath}&pageNo=${pageNo}" src="${ctx}/img/px.png" onload="lzld(this)" style="width: 895px">
			</c:forEach>
		</c:if>
		<c:if test="${empty unitFunction.filePath || !cm:exists(_fullPath)}">
			<div style="margin: 0 auto;line-height: 100px;text-align: center">文件不存在：${unitFunction.filePath}</div>
		</c:if>
	</div>
	<div>
		${unitFunction.remark}
	</div>
</div>
<script>
    function _reloadUnitFunction(){
        $("#unitfunctionDiv").load("${ctx}/unitFunction?unitId=${unitId}")
    }
</script>