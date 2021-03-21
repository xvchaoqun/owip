<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>设置有效期</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cadreInspect_validTime"
		  autocomplete="off" disableautocomplete id="modalForm" method="post">
		<input type="hidden" name="ids" value="${param.ids}">
			<div class="form-group">
				<label class="col-xs-4 control-label">选择考察对象</label>
				<div class="col-xs-6 label-text">
					${fn:length(fn:split(param.ids,","))} 人
				</div>
			</div>

			<div class="form-group">
				<label class="col-xs-4 control-label">有效期</label>
				<div class="col-xs-6">
					<div class="input-group">
						<input class="form-control date-picker" name="_validTime" type="text"
							   data-date-format="yyyy-mm-dd"/>
						<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
					</div>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> 确定</button>
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
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    //$('[data-rel="tooltip"]').tooltip();
    $('textarea.limited').inputlimiter();
    $.register.date($('.date-picker'));
</script>