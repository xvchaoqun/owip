<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${applySnRange!=null?'编辑':'添加'}志愿书编码号段</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/applySnRange_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${applySnRange.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 所属年度</label>
				<div class="col-xs-6">
					<div class="input-group date" data-date-format="yyyy" data-date-min-view-mode="2" >
                        <input required class="form-control"
							   placeholder="请选择年份" name="year" type="text"
                               value="${applySnRange.year}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">编码前缀</label>
				<div class="col-xs-6">
                        <input class="form-control" type="text" name="prefix" value="${applySnRange.prefix}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 编码长度</label>
				<div class="col-xs-6">
                        <input required class="form-control digits" type="text" name="len" value="${applySnRange.len}">
					<span class="help-block">注：除编码前缀外的编码位数，少于编码长度的编码系统将自动补足0</span>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 起始编码</label>
				<div class="col-xs-6">
                        <input required class="form-control digits" type="text" name="startSn" value="${applySnRange.startSn}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 结束编码</label>
				<div class="col-xs-6">
                        <input required class="form-control digits" type="text" name="endSn" value="${applySnRange.endSn}">
						<span class="help-block">注：系统将从小到大自动分配起止（含）编码号段之间未使用的编码</span>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
					<textarea class="form-control limited" rows="2"
                                   name="remark">${applySnRange.remark}</textarea>
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
    $.register.date($('.input-group.date'));
</script>