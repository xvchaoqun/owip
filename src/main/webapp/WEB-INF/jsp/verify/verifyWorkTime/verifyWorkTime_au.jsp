<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
	<button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
	<h3>添加参加工作时间认定</h3>
</div>
<div class="modal-body">
	<form class="form-horizontal" action="${ctx}/verifyWorkTime_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
		<input type="hidden" name="id" value="${verifyWorkTime.id}">
		<div class="form-group">
			<label class="col-xs-3 control-label"><span class="star">*</span> 所属干部</label>
			<div class="col-xs-6">
				<select required data-rel="select2-ajax" data-ajax-url="${ctx}/cadre_selects"
						name="cadreId" data-placeholder="请输入账号或姓名或学工号"  data-width="270">
					<option value="${cadre.id}">${cadre.realname}-${cadre.code}</option>
				</select>
			</div>
		</div>
	</form>
</div>
<div class="modal-footer">
	<a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
	<input type="button" id="submitBtn" class="btn btn-primary" value="认定"/>
</div>
<script>
	$.register.user_select($('[data-rel="select2-ajax"]'));

	$("#submitBtn").click(function(){$("#modalForm").submit();return false;});
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