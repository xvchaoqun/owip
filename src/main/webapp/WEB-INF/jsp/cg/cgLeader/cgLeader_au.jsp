<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${cgLeader!=null?'编辑':'添加'}办公室主任</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cg/cgLeader_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${cgLeader.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 所属委员会或领导小组</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="teamId" value="${cgLeader.teamId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 是否席位制</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="relatePost" value="${cgLeader.relatePost}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 关联岗位</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="unitPostId" value="${cgLeader.unitPostId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 用户ID</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="userId" value="${cgLeader.userId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 联系方式</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="phone" value="${cgLeader.phone}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 确定时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="confirmDate" value="${cgLeader.confirmDate}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 备注</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="remark" value="${cgLeader.remark}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty cgLeader?'确定':'添加'}</button>
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