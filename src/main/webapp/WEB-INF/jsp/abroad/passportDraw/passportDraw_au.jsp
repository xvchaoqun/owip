<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>领取证件申请（${PASSPORT_DRAW_TYPE_MAP.get(type)}）</h3>
</div>
<div class="modal-body">
	<form  class="form-horizontal">
		<div class="form-group">
			<label class="col-xs-3 control-label">选择干部</label>
			<div class="col-xs-6">
				<select data-rel="select2-ajax" data-ajax-url="${ctx}/cadre_selects" data-width="350"
						name="cadreId" data-placeholder="请选择">
					<option></option>
				</select>
			</div>
		</div>
	</form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input id="submitBtn" type="button" class="btn btn-primary" value="确定"/>
</div>
<script>
	register_user_select($('#modal select[name=cadreId]'));
    $("#modal #submitBtn").click(function(){

		var cadreId = $("#modal select[name=cadreId]").val();
		if(cadreId=="") return;
		<c:if test="${type==PASSPORT_DRAW_TYPE_SELF}">
		$.loadView("${ctx}/user/passportDraw_self?auth=admin&cadreId=" + cadreId);
		</c:if>
		<c:if test="${type==PASSPORT_DRAW_TYPE_TW}">
		$("#modal").modal('hide');
		$.loadPage("${ctx}/user/passportDraw_tw?auth=admin&cadreId=" + cadreId);
		</c:if>
		<c:if test="${type==PASSPORT_DRAW_TYPE_OTHER}">
		$.loadView("${ctx}/user/passportDraw_other?auth=admin&cadreId=" + cadreId);
		</c:if>
	})
</script>