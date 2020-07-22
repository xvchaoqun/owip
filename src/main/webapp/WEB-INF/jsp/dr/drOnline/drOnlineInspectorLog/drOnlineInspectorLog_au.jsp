<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>参评人账号个别生成</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/dr/drOnlineInspectorLog_au?onlineId=${onlineId}" autocomplete="off" disableautocomplete id="modalForm" method="post">
		<div class="form-group">
			<label class="col-xs-offset-1 col-xs-3 control-label"><span class="star">*</span> 参评人身份类型</label>
			<div class="col-xs-7">
				<select required name="typeId" data-placeholder="请输入参评人身份类型" data-rel="select2">
					<option></option>
					<c:forEach items="${inspectorTypeMap}" var="inspectorType">
						<option value="${inspectorType.key}">${inspectorType.value.type}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-offset-1 col-xs-3 control-label">所属单位</label>
			<div class="col-xs-7">
				<select name="unitId" data-placeholder="请输入所属单位" data-rel="select2">
					<option></option>
					<c:forEach items="${unitMap}" var="unit">
						<option value="${unit.key}">${unit.value.name}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-offset-1 col-xs-3 control-label"><span class="star">*</span> 数目</label>
			<div class="col-xs-2">
				<input required class="form-control digits" type="text" name="addCount">
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-offset-1 col-xs-3 control-label">是否补发</label>
			<div class="col-xs-8">
				<input type="checkbox" name="isAppended" data-on-label="是" data-off-label="否">
				<span class="help-block">如果选择了"是"，管理员看到的账号状态为"补发"</span>
			</div>
		</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty inspectorLog?'确定':'添加'}</button>
</div>
<script>
	$("#modelform :checkbox").bootstrapSwitch();
    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
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
    //$('textarea.limited').inputlimiter();
    //$.register.date($('.date-picker'));
</script>