<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>同步学校账号信息</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/sync_user_byCode" id="modalForm" method="post">
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>学工号</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="code">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input id="submitBtn" type="button" class="btn btn-primary" value="同步" data-loading-text="同步中..."/>
</div>

<script>
	$("#submitBtn").click(function () {
		$("#modalForm").submit();return false;
	});
    $("#modalForm").validate({
        submitHandler: function (form) {
			var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
						$("#modal").modal('hide');
                        SysMsg.success('同步成功。', '成功');
                    }
					$btn.button('reset');
                }
            });
        }
    });
</script>