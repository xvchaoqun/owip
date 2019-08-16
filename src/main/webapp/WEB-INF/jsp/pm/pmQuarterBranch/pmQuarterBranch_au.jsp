<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${pmQuarterBranch!=null?'编辑':'添加'}三会一课支部季度汇总详情</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/pmQuarterBranch_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${pmQuarterBranch.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 季度汇总id</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="quarterId" value="${pmQuarterBranch.quarterId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 支部名称</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="branchName" value="${pmQuarterBranch.branchName}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 是否在排除召开会议的列表</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="isExclude" value="${pmQuarterBranch.isExclude}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 会议次数</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="meetingNum" value="${pmQuarterBranch.meetingNum}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 备注</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="remark" value="${pmQuarterBranch.remark}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty pmQuarterBranch?'确定':'添加'}</button>
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