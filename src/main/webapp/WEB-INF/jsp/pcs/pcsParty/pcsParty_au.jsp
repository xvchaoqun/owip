<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${pcsParty!=null?'编辑':'添加'}参与党代会的分党委 </h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/pcsParty_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${pcsParty.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 所属党代会</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="configId" value="${pcsParty.configId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 所属分党委ID</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="partyId" value="${pcsParty.partyId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 名称</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="name" value="${pcsParty.name}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 是否直属党支部</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="isDirectBranch" value="${pcsParty.isDirectBranch}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 当前启动的阶段</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="currentStage" value="${pcsParty.currentStage}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 排序</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="sortOrder" value="${pcsParty.sortOrder}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 支部数量</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="branchCount" value="${pcsParty.branchCount}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 党员数量</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="memberCount" value="${pcsParty.memberCount}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 正式党员数量</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="positiveCount" value="${pcsParty.positiveCount}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 学生党员数量</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="studentMemberCount" value="${pcsParty.studentMemberCount}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 教师党员数量</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="teacherMemberCount" value="${pcsParty.teacherMemberCount}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 离退休党员数量</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="retireMemberCount" value="${pcsParty.retireMemberCount}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 班子数量</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="groupCount" value="${pcsParty.groupCount}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 现任班子数量</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="presentGroupCount" value="${pcsParty.presentGroupCount}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty pcsParty?'确定':'添加'}</button>
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