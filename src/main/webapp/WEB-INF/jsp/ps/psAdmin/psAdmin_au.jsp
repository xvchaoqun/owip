<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${psAdmin!=null?'编辑':'添加'}二级党校管理员</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/ps/psAdmin_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${psAdmin.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 所属二级党校</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="psId" value="${psAdmin.psId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 类型</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="type" value="${psAdmin.type}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 管理员</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="userId" value="${psAdmin.userId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 所在单位及职务</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="title" value="${psAdmin.title}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 联系方式</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="mobile" value="${psAdmin.mobile}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 任职起始时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="startDate" value="${psAdmin.startDate}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 任职结束时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="endDate" value="${psAdmin.endDate}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 现任/离任</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="isHistory" value="${psAdmin.isHistory}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 备注</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="remark" value="${psAdmin.remark}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty psAdmin?'确定':'添加'}</button>
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