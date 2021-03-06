<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>缴费状态设置</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/pmd/pmdMonth_payStatus"
		  autocomplete="off" disableautocomplete id="modalForm" method="post">
		<input type="hidden" name="id" value="${param.monthId}">
			<%--<c:if test="${empty cetUnitTrain}">
			<div class="form-group">
				<label class="col-xs-4 control-label">审批记录数</label>
				<div class="col-xs-6 label-text">
					${fn:length(fn:split(param.ids,","))} 条
				</div>
			</div>
			</c:if>--%>

			<div class="form-group">
				<label class="col-xs-4 control-label">缴费状态</label>
				<div class="col-xs-6">
					<input type="checkbox" class="big" ${pmdMonth.payStatus?'checked':''} name="payStatus" data-off-text="未开启" data-on-text="已开启"/>
				</div>
			</div>
	<br/>
			<div class="form-group">
				<label class="col-xs-4 control-label">提示</label>
				<div class="col-xs-6">
                        <textarea class="form-control limited" rows="3"
								  name="payTip">${pmdMonth.payTip}</textarea>
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
    //$.register.date($('.date-picker'));
</script>