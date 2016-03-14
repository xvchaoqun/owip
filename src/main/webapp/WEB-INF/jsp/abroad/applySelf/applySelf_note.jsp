<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
	<button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
	<h3>因私出国（境）申请说明</h3>
</div>
<div class="modal-body">
	<div style="margin-left: auto; margin-right: auto;max-width: 600px; margin-top: 10px;">
		<textarea id="noticeId">${sysConfig.applySelfNote}</textarea>
	</div>
</div>
<div class="modal-footer"><a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
	<a id="add_entity" class="btn btn-primary" onclick="updateNotice()"><c:if test="${sysConfig!=null}">确认</c:if><c:if test="${sysConfig==null}">添加</c:if></a></div>

<script type="text/javascript" src="${ctx}/kindeditor/kindeditor.js"></script>
<script>

	$(function() {
		KE.init({
			id : 'noticeId',
			height : '500px',
			resizeMode : 1,
			width:'600px',
			//scriptPath:"${ctx}/js/kindeditor/",
			//skinsPath : KE.scriptPath + 'skins/',
			items : [
				'fontname', 'fontsize', '|', 'textcolor', 'bgcolor', 'bold', 'italic', 'underline',
				'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'image', 'link', 'unlink', 'fullscreen']
		});

		KE.create('noticeId');
	});
	//KE.util.getData('noticeId')
	function updateNotice(){

		$.post('${ctx}/applySelf_note',{notice:KE.util.getData('noticeId')},function(data){
			if(data.success){
				$(".modal").modal("hide");
				SysMsg.success('操作成功。', '成功');
			}
		});
	}
</script>