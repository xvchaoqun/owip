<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${cetRecord!=null?'编辑':'添加'}培训记录明细汇总表</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cet/cetRecord_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${cetRecord.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 年度</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="year" value="${cetRecord.year}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 参训人</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="userId" value="${cetRecord.userId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 参训人员类型</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="traineeTypeId" value="${cetRecord.traineeTypeId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 其他参训人员类型</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="otherTraineeType" value="${cetRecord.otherTraineeType}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 时任单位及职务</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="title" value="${cetRecord.title}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 起始时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="startDate" value="${cetRecord.startDate}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 结束时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="endDate" value="${cetRecord.endDate}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 培训班名称</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="name" value="${cetRecord.name}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 培训类型</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="type" value="${cetRecord.type}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 培训项目ID</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="typeId" value="${cetRecord.typeId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 培训主办方</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="organizer" value="${cetRecord.organizer}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 完成学时数</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="period" value="${cetRecord.period}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 线上完成学时数</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="onlinePeriod" value="${cetRecord.onlinePeriod}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 应完成学时数</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="shouldFinishPeriod" value="${cetRecord.shouldFinishPeriod}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 是否结业</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="isGraduate" value="${cetRecord.isGraduate}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 是否计入年度学习任务</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="isValid" value="${cetRecord.isValid}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 备注</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="remark" value="${cetRecord.remark}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 归档时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="archiveTime" value="${cetRecord.archiveTime}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty cetRecord?'确定':'添加'}</button>
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