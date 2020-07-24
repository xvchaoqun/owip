<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set value="<%=SystemConstants.UNIT_POST_STATUS_NORMAL%>" var="UNIT_POST_STATUS_NORMAL"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${drOnlinePost!=null?'编辑':'添加'}推荐职务</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/dr/drOnlinePost_au?onlineId=${onlineId}" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${drOnlinePost.id}">
		<input type="hidden" name="onlineId" value="${onlineId}">
		<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>职务名称</label>
				<div class="col-xs-6">
					<textarea class="form-control" type="text" maxlength="80" name="name">${drOnlinePost.name}</textarea>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">关联岗位</label>
				<div class="col-xs-6">
					<select name="unitPostId" data-rel="select2-ajax" data-ajax-url="${ctx}/unitPost_selects"
							data-width="273"
							data-placeholder="请选择">
						<option value="${unitPost.id}" delete="${unitPost.status!=UNIT_POST_STATUS_NORMAL}">${unitPost.code}-${unitPost.name}</option>
					</select>
					<span class="help-block">注：从干部岗位库中选择</span>
				</div>
			</div>

			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 最多推荐人数</label>
				<div class="col-xs-6">
					<input required style="width: 78px;" class="form-control digits" type="text"
						   name="headCount" value="${drOnlinePost.headCount}" data-rule-min="1">
				</div>
			</div>

			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 最少推荐人数</label>
				<div class="col-xs-6">
					<input required style="width: 78px;" class="form-control digits" type="text"
						   name="minCount" value="${drOnlinePost.minCount}" data-rule-min="0">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6"	>
							<textarea class="form-control limited noEnter" type="text"
									  name="remark" rows="3">${drOnlinePost.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty drOnlinePost?'确定':'添加'}</button>
</div>
<script>

	$("#submitBtn").click(function () {
		$("#modalForm").submit();
		return false;
	});

    $("#modalForm").validate({
        submitHandler: function (form) {
			var $btn = $("#committeeBtn").button('loading');
            $(form).ajaxSubmit({

                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
						$("#jqGrid").trigger("reloadGrid");
                        $("#jqGrid2").trigger("reloadGrid");

                    }
                    $btn.button('reset');
                }
            });
        }
    });
    $("#modalForm :checkbox").bootstrapSwitch();
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $('textarea.limited').inputlimiter();
    $.register.date($('.date-picker'));
</script>