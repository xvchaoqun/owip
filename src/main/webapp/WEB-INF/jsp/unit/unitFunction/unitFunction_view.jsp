<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<jsp:include page="menu.jsp"/>
<div style="width: 900px">
	<div class="bg-grey" style="margin-top: 10px;text-indent: 2em;padding: 10px;">
		${unitFunction.content}
		<c:if test="${empty unitFunction.content}">
			暂无职能内容。
		</c:if>
	</div>
	<div class="buttons" style="position: relative; top: 8px; padding-left: 785px;">
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
	<div style="border:1px dashed;margin-top: -27px;min-height: 100px;">
		<c:if test="${not empty unitFunction.imgPath}">
		<img src="${ctx}/pic?path=${cm:encodeURI(unitFunction.imgPath)}&w=895"/>
		</c:if>
		<c:if test="${empty unitFunction.imgPath}">
			<div style="margin: 0 auto;line-height: 100px;text-align: center">暂无相关文件</div>
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