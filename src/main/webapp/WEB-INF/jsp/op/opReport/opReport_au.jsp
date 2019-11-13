<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${opReport!=null?'编辑':'添加'}报送上级部门</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/op/opReport_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${opReport.id}">
		<div class="form-group">
			<label class="col-xs-3 control-label"><span class="star">*</span> 报送日期</label>
			<div class="col-xs-6">
				<div class="input-group" style="width:270px">
					<input class="form-control date-picker" name="reportDate" type="text"
						   data-date-format="yyyy.mm.dd" value="${cm:formatDate(opReport.reportDate, 'yyyy.MM.dd')}"/>
					<span class="input-group-addon"><i class="fa fa-calendar bigger-110"></i></span>
				</div>
			</div>
		</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 上级单位</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="unit" value="${opReport.unit}" placeholder="请输入单位名称">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 统计开始时间</label>
				<div class="col-xs-6">
					<div class="input-group" style="width:270px">
						<input class="form-control date-picker" name="startDate" type="text"
							   data-date-format="yyyy.mm.dd" value="${cm:formatDate(opReport.startDate, 'yyyy.MM.dd')}"/>
						<span class="input-group-addon"><i class="fa fa-calendar bigger-110"></i></span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 统计截止时间</label>
				<div class="col-xs-6">
					<div class="input-group" style="width:270px">
						<input class="form-control date-picker" name="endDate" type="text"
							   data-date-format="yyyy.mm.dd" value="${cm:formatDate(opReport.endDate, 'yyyy.MM.dd')}"/>
						<span class="input-group-addon"><i class="fa fa-calendar bigger-110"></i></span>
					</div>
				</div>
			</div>
		<div class="form-group">
			<label class="col-xs-3 control-label"> 备注</label>
			<div class="col-xs-8" style="width: 295px">
				<textarea class="form-control" name="remark">${opReport.remark}</textarea>
			</div>
		</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty opReport?'确定':'添加'}</button>
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
    $("#modalForm :checkbox").bootstrapSwitch();
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $('textarea.limited').inputlimiter();
    $.register.date($('.date-picker'));
</script>