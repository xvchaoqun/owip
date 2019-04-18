<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${organizer!=null?'编辑':'添加'}组织员信息</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/organizer_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${organizer.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">年度</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="year" value="${organizer.year}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">类型</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="type" value="${organizer.type}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">组织员</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="userId" value="${organizer.userId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">联系党委</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="partyId" value="${organizer.partyId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">联系单位</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="units" value="${organizer.units}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">任职时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="appointDate" value="${organizer.appointDate}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">离任时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="dismissDate" value="${organizer.dismissDate}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">状态</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="status" value="${organizer.status}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="remark" value="${organizer.remark}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty organizer?'确定':'添加'}</button>
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