<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>编辑参与党代会的${_p_partyName} </h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/pcs/pcsParty_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${pcsParty.id}">

            <input type="hidden" name="partyId" value="${pcsParty.partyId}">

			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> ${_p_partyName}名称</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="name" value="${pcsParty.name}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 党支部数量</label>
				<div class="col-xs-6">
                        <input required class="form-control num" type="text" name="branchCount" value="${pcsParty.branchCount}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 党员数量</label>
				<div class="col-xs-6">
                        <input required class="form-control num" type="text" name="memberCount" value="${pcsParty.memberCount}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 正式党员数量</label>
				<div class="col-xs-6">
                        <input required class="form-control num" type="text" name="positiveCount" value="${pcsParty.positiveCount}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 学生党员数量</label>
				<div class="col-xs-6">
                        <input required class="form-control num" type="text" name="studentMemberCount" value="${pcsParty.studentMemberCount}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 教师党员数量</label>
				<div class="col-xs-6">
                        <input required class="form-control num" type="text" name="teacherMemberCount" value="${pcsParty.teacherMemberCount}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 离退休党员数量</label>
				<div class="col-xs-6">
                        <input required class="form-control num" type="text" name="retireMemberCount" value="${pcsParty.retireMemberCount}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 班子数量</label>
				<div class="col-xs-6">
                        <input required class="form-control num" type="text" name="groupCount" value="${pcsParty.groupCount}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 现任班子数量</label>
				<div class="col-xs-6">
                        <input required class="form-control num" type="text" name="presentGroupCount" value="${pcsParty.presentGroupCount}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
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