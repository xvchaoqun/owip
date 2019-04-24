<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>指定负责人</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/user/oa/oaTaskUser_assign" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="taskId" value="${oaTask.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">任务</label>
				<div class="col-xs-6 label-text">
                ${oaTask.name}
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>负责人</label>
				<div class="col-xs-6">
                    <select required data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects?types=${USER_TYPE_JZG}"
                            data-width="275"
                            name="userId" data-placeholder="请输入账号或姓名或教工号">
                        <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                    </select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>手机号码</label>
				<div class="col-xs-6">
                    <input required class="form-control" type="text" name="mobile" value="${oaTaskUser.assignUserMobile}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input id="submitBtn" type="button" class="btn btn-primary" value="确定"/>
</div>
<script>
    $.register.user_select($('#modalForm select[name=userId]'));
    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $("#jqGrid").trigger("reloadGrid");

                        $('#modal').on('hidden.bs.modal', function () {
						 	$.loadModal("${ctx}/user/oa/oaTaskUser_assignMsg?taskId=${oaTask.id}")
						})
                    }
                }
            });
        }
    });
</script>