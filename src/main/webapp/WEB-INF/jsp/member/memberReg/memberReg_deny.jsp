<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>用户注册-拒绝申请</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/memberReg_deny" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${param.id}">
		<div class="form-group">
			<label class="col-xs-3 control-label">申请人</label>
			<div class="col-xs-6 label-text">
                ${memberReg.realname}
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label"><span class="star">*</span>原因</label>
			<div class="col-xs-6">
				<textarea required class="form-control limited" type="text" name="reason" rows="5"></textarea>
			</div>
		</div>
    </form>
    </div>
<div class="modal-footer">
    <a href="javascript:;"  data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="确定"/>
</div>

<script>
	$("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal("hide");
                        //SysMsg.success('操作成功。', '成功', function () {
                            goto_next(${param.goToNext==1});
                        //});
                    }
                }
            });
        }
    });
</script>