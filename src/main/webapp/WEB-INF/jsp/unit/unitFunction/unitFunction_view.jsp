<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<fmt:message key="upload.path" bundle="${spring}" var="_uploadPath"/>
<jsp:include page="menu.jsp"/>
<div style="width: 900px">
    <div class="space-4"></div>
    <div class="buttons">
        <a href="javascript:void(0)" class="reloadBtn btn btn-xs btn-primary" data-target="#unitfunctionDiv"
           data-url="${ctx}/unitFunction?unitId=${unitId}&funId=${unitFunction.id}&isEdit=1">
            <i class="fa fa-edit"></i> 编辑
        </a>
        <button data-url="${ctx}/unitFunction_batchDel?ids[]=${unitFunction.id}"
                data-title="删除"
                data-msg="确定删除此单位职能？"
                data-callback="_reloadUnitFunction"
                class="confirm btn btn-danger btn-xs">
            <i class="fa fa-times"></i> 删除
        </button>
    </div>
    <div class="bg-grey" style="text-indent: 2em;padding: 10px;">
        ${cm:htmlUnescape(unitFunction.content)}
        <c:if test="${empty unitFunction.content}">
            暂无职能内容。
        </c:if>
    </div>
    <c:set value="${_uploadPath}${unitFunction.filePath}" var="_fullPath"/>
    <c:if test="${not empty unitFunction.filePath && cm:exists(_fullPath)}">
    <div style="border:1px dashed;min-height: 100px;max-height: 900px;overflow-y: auto">
        <div class="buttons" style="position: sticky; top: 18px; margin-left: 800px;">
            <a href="javascript:void(0)"
               data-url="${ctx}/attach_download?path=${cm:sign(unitFunction.filePath)}&filename=${unit.name}单位职能(${cm:formatDate(unitFunction.confirmTime, "yyyyMMdd")}).pdf"
               title="下载文件"
               data-type="download"
               class="downloadBtn btn btn-xs btn-warning">
                <i class="fa fa-download"></i> 下载
            </a>
        </div>
        <c:forEach begin="1" end="${cm:getPages(_fullPath)}" var="pageNo">
            <img data-src="${ctx}/pdf_image?path=${unitFunction.filePath}&pageNo=${pageNo}" src="${ctx}/img/px.png"
                 onload="lzld(this)" style="width: 875px">
        </c:forEach>
    </div>
    </c:if>
    <div>
        ${unitFunction.remark}
    </div>
</div>
<script>
    function _reloadUnitFunction() {
        $("#unitfunctionDiv").load("${ctx}/unitFunction?unitId=${unitId}")
    }
</script>