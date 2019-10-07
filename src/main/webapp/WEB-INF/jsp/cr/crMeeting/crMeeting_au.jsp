<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${crMeeting!=null?'编辑':'添加'}招聘会信息</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/crMeeting_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${crMeeting.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 所属招聘</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="infoId" value="${crMeeting.infoId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 招聘会日期</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="meetingDate" value="${crMeeting.meetingDate}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 招聘岗位</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="postIds" value="${crMeeting.postIds}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 招聘会人数要求</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="applyCount" value="${crMeeting.applyCount}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 备注</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="remark" value="${crMeeting.remark}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty crMeeting?'确定':'添加'}</button>
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