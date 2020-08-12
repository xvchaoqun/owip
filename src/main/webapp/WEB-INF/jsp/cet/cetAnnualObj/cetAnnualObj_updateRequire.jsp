<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>设定年度学习任务</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cet/cetAnnualObj_updateRequire" autocomplete="off" disableautocomplete id="modalForm" method="post">
		 	<input type="hidden" name="ids" value="${param.ids}">
			<div class="form-group">
			<label class="col-xs-5 control-label">已选培训对象</label>
				<div class="col-xs-3">
                    ${fn:length(fn:split(param.ids,","))} 人
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-5 control-label"><span class="star">*</span>年度学习任务(线下)</label>
				<div class="col-xs-3">
                        <input required class="form-control period" type="text" name="periodOffline" data-rule-min="0.5">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-5 control-label"><span class="star">*</span>年度学习任务(网络)</label>
				<div class="col-xs-3">
                        <input required class="form-control period" type="text" name="periodOnline" data-rule-min="0.5">
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
    $('#modalForm [data-rel="select2"]').select2();
    $('textarea.limited').inputlimiter();
</script>