<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/modify/constants.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>删除申请（${MODIFY_TABLE_APPLY_MODULE_MAP.get(module)}）</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/user/modifyTableApply_del" autocomplete="off" disableautocomplete id="modalForm" method="post">
        	<input type="hidden" name="id" value="${param.id}">
        	<input type="hidden" name="module" value="${module}">
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>删除原因</label>
				<div class="col-xs-6">
					<textarea required class="form-control limited" maxlength="100" rows="6" name="reason"></textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" type="button" class="btn btn-primary"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口">
        确定</button>
</div>
<script>
    $('textarea.limited').inputlimiter();

    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modal form").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal("hide");
                        // 必须使用param.module
        				$.hashchange('module=${param.module}&cls=1');
                    }
                    $btn.button('reset');
                }
            });
        }
    });
</script>