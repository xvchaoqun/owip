<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${applySnRange!=null?'编辑':'添加'}入党志愿书编码段</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/applySnRange_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${applySnRange.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">所属年度</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="year" value="${applySnRange.year}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">起始编码</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="startSn" value="${applySnRange.startSn}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">结束编码</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="endSn" value="${applySnRange.endSn}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="remark" value="${applySnRange.remark}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty applySnRange?'确定':'添加'}</button>
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