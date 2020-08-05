<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
	<button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
	<h3>因私出国境申请变更记录</h3>
</div>
<div class="modal-body">
	<table class="table table-bordered table-striped">
		<tr>
			<th width="100">变更时间</th>
			<th>变更说明</th>
			<th width="200">本人说明材料</th>
		</tr>
		<c:forEach items="${modifyList}" var="modify">
		<tr>
			<td>${cm:formatDate(modify.createTime, "yyyy-MM-dd")}</td>
			<td>${modify.remark}</td>
			<td><a href="javascript:;" class="downloadBtn" data-type="download"
				   data-url="${ctx}/abroad/applySelfModify_download?id=${modify.id}">${modify.modifyProofFileName}</a></td>
		</tr>
		</c:forEach>
	</table>
</div>
<div class="modal-footer">
	<a href="javascript:;" data-dismiss="modal" class="btn btn-default">关闭</a>
</div>