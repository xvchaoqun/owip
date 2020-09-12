<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>修改党支部信息</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/pcs/pcsBranch_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${pcsBranch.id}">

			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> ${_p_partyName}名称</label>
				<div class="col-xs-6">
					<textarea required class="form-control" name="partyName">${pcsBranch.partyName}</textarea>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 党支部名称</label>
				<div class="col-xs-6">
					<textarea required class="form-control" name="name">${pcsBranch.name}</textarea>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 党员数量</label>
				<div class="col-xs-6">
                        <input required class="form-control num" type="text" name="memberCount" value="${pcsBranch.memberCount}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 正式党员数量</label>
				<div class="col-xs-6">
                        <input required class="form-control num" type="text" name="positiveCount" value="${pcsBranch.positiveCount}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">学生党员数量</label>
				<div class="col-xs-6">
                        <input class="form-control num" type="text" name="studentMemberCount" value="${pcsBranch.studentMemberCount}">
					<span class="help-block">注：不存在请填 0</span>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">教师党员数量</label>
				<div class="col-xs-6">
                        <input class="form-control num" type="text" name="teacherMemberCount" value="${pcsBranch.teacherMemberCount}">
					<span class="help-block">注：不存在请填 0</span>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">离退休党员数量</label>
				<div class="col-xs-6">
                        <input class="form-control num" type="text" name="retireMemberCount" value="${pcsBranch.retireMemberCount}">
					<span class="help-block">注：不存在请填 0</span>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
	<div class="note">注：此处修改后，如果点击“同步”按钮，数据将被覆盖。</div>
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> 确定</button>
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
                        $("#jqGrid").trigger("reloadGrid");
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    //$("#modalForm :checkbox").bootstrapSwitch();
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#modalForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$('textarea.limited').inputlimiter();
    //$.register.date($('.date-picker'));
</script>