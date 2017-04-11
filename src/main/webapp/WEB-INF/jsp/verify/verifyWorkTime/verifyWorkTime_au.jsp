<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
	<button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
	<h3>添加参加工作时间认定</h3>
</div>
<div class="modal-body">
	<form class="form-horizontal" action="${ctx}/verifyWorkTime_au" id="modalForm" method="post">
		<input type="hidden" name="id" value="${verifyWorkTime.id}">
		<div class="form-group">
			<label class="col-xs-3 control-label">所属干部</label>
			<div class="col-xs-6">
				<select data-rel="select2-ajax" data-ajax-url="${ctx}/cadre_selects?type=0"
						name="cadreId" data-placeholder="请输入账号或姓名或学工号"  data-width="270">
					<option value="${cadre.id}">${cadre.user.realname}-${cadre.user.code}</option>
				</select>
			</div>
		</div>
	</form>
</div>
<div class="modal-footer">
	<a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
	<input type="submit" class="btn btn-primary" value="认定"/>
</div>
<script>
	register_user_select($('[data-rel="select2-ajax"]'));
	$("#modalForm").validate({
		submitHandler: function (form) {
			$(form).ajaxSubmit({
				success:function(ret){
					if(ret.success){
						$("#modal").modal('hide');
						$("#jqGrid").trigger("reloadGrid");
					}
				}
			});
		}
	});
</script>