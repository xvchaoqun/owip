<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>移除历史党员</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/member/memberHistory_out" autocomplete="off" disableautocomplete id="modalForm" method="post">
		<input type="hidden" name="ids" value="${param.ids}">
		<c:set var="count" value="${fn:length(fn:split(param.ids,\",\"))}"/>
		<c:if test="${count>=1}">
			<div class="form-group">
				<label class="col-xs-3 control-label">移除历史党员</label>

				<div class="col-xs-6 label-text">
						${count} 个
				</div>
			</div>
		</c:if>
		<div class="form-group">
			<label class="col-xs-3 control-label">移除原因</label>
			<div class="col-xs-6">
						<textarea class="form-control limited noEnter" type="text" maxlength="100"
								  name="outReason">${memberHistory.outReason}</textarea>
			</div>
		</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> 移除</button>
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
                        _reload();
                    }
                    $btn.button('reset');
                }
            });
        }
    });
	$('textarea.limited').inputlimiter();
    //$("#modalForm :checkbox").bootstrapSwitch();
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $('[data-rel="tooltip"]').tooltip();
	$.register.date($('.date-picker'), {endDate: '${_today}'});
    //$('textarea.limited').inputlimiter();
    $.register.date($('.date-picker'));
</script>