<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
	<button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
	<h3>${SYS_CONFIG_MAP.get(type)}</h3>
</div>
<div class="modal-body">
	<div style="margin-left: auto; margin-right: auto;max-width: 600px; margin-top: 10px;">
		<textarea id="note">
		<c:if test="${type==SYS_CONFIG_APPLY_SELF_NOTE}">${sysConfig.applySelfNote}</c:if>
		<c:if test="${type==SYS_CONFIG_APPLY_SELF_APPROVAL_NOTE}">${sysConfig.applySelfApprovalNote}</c:if>
		</textarea>
	</div>
</div>
<div class="modal-footer"><a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
	<a id="add_entity" class="btn btn-primary" onclick="updateNote()"><c:if test="${sysConfig!=null}">确认</c:if><c:if test="${sysConfig==null}">添加</c:if></a></div>

<script type="text/javascript" src="${ctx}/kindeditor/kindeditor.js"></script>
<script>

	$(function() {
		KE.init({
			id : 'note',
			height : '500px',
			resizeMode : 1,
			width:'600px',
			//scriptPath:"${ctx}/js/kindeditor/",
			//skinsPath : KE.scriptPath + 'skins/',
			items : [
				'fontname', 'fontsize', '|', 'textcolor', 'bgcolor', 'bold', 'italic', 'underline',
				'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'image', 'link', 'unlink', 'fullscreen']
		});

		KE.create('note');
	});
	//KE.util.getData('noticeId')
	function updateNote(){

		$.post('${ctx}/sysConfig_note',{type:"${type}", note:KE.util.getData('note')},function(data){
			if(data.success){
				$(".modal").modal("hide");
				SysMsg.success('操作成功。', '成功');
			}
		});
	}
</script>