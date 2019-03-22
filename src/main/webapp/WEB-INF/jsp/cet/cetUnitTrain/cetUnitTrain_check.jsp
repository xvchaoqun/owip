<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set value="<%=CetConstants.CET_UPPER_TRAIN_STATUS_PASS%>" var="CET_UPPER_TRAIN_STATUS_PASS"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>审批</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cet/cetUnitTrain_check" autocomplete="off" disableautocomplete id="modalForm" method="post">
		<input type="hidden" name="ids[]" value="${param['ids[]']}">
        <c:if test="${empty cetUnitTrain}">
		<div class="form-group">
            <label class="col-xs-3 control-label">审批记录数</label>
            <div class="col-xs-6 label-text">
                ${fn:length(fn:split(param['ids[]'],","))} 条
            </div>
        </div>
		</c:if>
		<c:if test="${not empty cetUnitTrain}">
			<div class="form-group">
				<label class="col-xs-3 control-label">参训人</label>
				<div class="col-xs-6 label-text">
					${cetUnitTrain.cadre.realname}
				</div>
			</div>
			</c:if>
			<div class="form-group">
				<label class="col-xs-3 control-label">审批</label>
				<div class="col-xs-6">
					<input type="checkbox" class="big" name="pass"
					data-on-text="通过" data-off-text="不通过"
					${empty cetUnitTrain || cetUnitTrain.status==CET_UPPER_TRAIN_STATUS_PASS?"checked":""}/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">原因</label>
				<div class="col-xs-6">
                        <textarea class="form-control limited" rows="2"
							   name="backReason">${cetUnitTrain.backReason}</textarea>
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
                        $("#jqGrid2").trigger("reloadGrid");
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