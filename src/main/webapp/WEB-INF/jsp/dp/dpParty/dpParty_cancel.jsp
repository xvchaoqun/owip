<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>移除民主党派</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/dp/dpParty_cancel" autocomplete="off" disableautocomplete id="modalForm" method="post">
		<input type="hidden" name="ids[]" value="${param['ids[]']}">
		<c:set var="count" value="${fn:length(fn:split(param['ids[]'],\",\"))}"/>
		<c:if test="${count>=1}">
			<div class="form-group">
				<label class="col-xs-3 control-label">移除民主党派</label>

				<div class="col-xs-6 label-text">
						${count} 个
				</div>
			</div>
		</c:if>
		<div class="form-group">
			<label class="col-xs-3 control-label"><span class="star">*</span> 移除时间</label>
			<div class="col-xs-6">
				<div class="input-group" style="width: 270px">
					<input required class="form-control date-picker" name="deleteTime" type="text"
						   data-date-format="yyyy.mm.dd"
						   value="${cm:formatDate(dpParty.deleteTime, 'yyyy.MM.dd')}"/>
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
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty dpOm?'确定':'移除'}</button>
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
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
	$.register.date($('.date-picker'), {endDate: '${_today}'});
    //$('textarea.limited').inputlimiter();
    $.register.date($('.date-picker'));
</script>