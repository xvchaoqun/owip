<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${pcsPollResult!=null?'编辑':'添加'}党代会投票结果</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/pcs/pcsPollResult_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${pcsPollResult.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 所属投票</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="pollId" value="${pcsPollResult.pollId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 候选人</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="candidateUserId" value="${pcsPollResult.candidateUserId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 投票人</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="inspectorId" value="${pcsPollResult.inspectorId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 投票阶段 0一下阶段 1二下阶段</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="isSecond" value="${pcsPollResult.isSecond}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 所属二级党组织</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="partyId" value="${pcsPollResult.partyId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 所属党支部</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="branchId" value="${pcsPollResult.branchId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 投票人身份 0预备党员 1正式党员</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="isPositive" value="${pcsPollResult.isPositive}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 推荐结果 1 同意 2 不同意 3 弃权</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="status" value="${pcsPollResult.status}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 推荐人选</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="userId" value="${pcsPollResult.userId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 备注</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="remark" value="${pcsPollResult.remark}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty pcsPollResult?'确定':'添加'}</button>
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