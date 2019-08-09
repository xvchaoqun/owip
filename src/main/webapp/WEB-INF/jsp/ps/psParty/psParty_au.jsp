<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${psParty!=null?'编辑':'添加'}二级党校建设单位</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/ps/psParty_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${psParty.id}"/>
		<input type="hidden" name="psId" value="${psId}"/>
		<input type="hidden" name="isHost" value="${isHost}"/>
		<div class="form-group">
			<label class="col-xs-4 control-label"><span class="star">*</span> ${isHost?"主建":"联合建设"}单位名称</label>
			<div class="col-xs-6">
				<select required name="partyId" class="form-control" data-width="272"
						data-rel="select2-ajax"
						data-ajax-url="${ctx}/party_selects"
						data-placeholder="请选择">
					<option value="${party.id}" title="${party.isDeleted}">${party.name}</option>
				</select>
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-4 control-label"> 开始时间</label>
			<div class="col-xs-6">
				<div class="input-group">
					<input class="form-control date-picker" name="startDate" type="text"
						   data-date-format="yyyy.mm"
						   data-date-min-view-mode="1"
						   value="${empty psParty.startDate?'':cm:formatDate(psParty.startDate,'yyyy.MM')}"/>
					<span class="input-group-addon"><i class="fa fa-calendar bigger-110"></i></span>
				</div>
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-4 control-label"> 备注</label>
			<div class="col-xs-6">
				<textarea class="form-control" name="remark">${psParty.remark}</textarea>
			</div>
		</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"
			data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口">
		<i class="fa fa-check"></i>
		${not empty psParty?'确定':'添加'}</button>
</div>
	<script>
		$("#submitBtn").click(function(){$("#modalForm").submit();return false;});
		$("#modalForm").validate({
			submitHandler: function (form) {
				var $btn = $("#submitBtn").button('loading');
				$(form).ajaxSubmit({
					success:function(ret){
						if(ret.success){
							$("#modal").modal('hide');
							<c:if test="${isHost}">
								$("#jqGrid_hostUnit").trigger("reloadGrid");
							</c:if>
							<c:if test="${!isHost}">
								$("#jqGrid_jointUnit").trigger("reloadGrid");
							</c:if>
						}
						$btn.button('reset');
					}
				});
			}
		});
		$('[data-rel="select2"]').select2();
		$.register.user_select($('[data-rel="select2-ajax"]'));
		$.register.date($('.date-picker'));
	</script>