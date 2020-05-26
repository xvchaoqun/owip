<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>关联岗位分组</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/unitPost_group" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${unitPost.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">岗位分组</label>
				<div class="col-xs-6">
					<select data-ajax-url="${ctx}/unitPostGroup_selects" data-width="258"
							name="groupId" data-placeholder="请选择">
						<option value="${unitPostGroup.id}">${unitPostGroup.name}</option>
					</select>
					<span class="help-block">注：选择岗位分组为空，将不关联岗位分组</span>
				</div>
			</div>

    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" type="button" class="btn btn-primary"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口">确定</button>
</div>
<script>
	$.register.ajax_select($('#modalForm select[name=groupId]'));

    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});

    $("#modal form").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
						$("#jqGrid").trigger("reloadGrid");
                        //SysMsg.success('操作成功。', '成功');
                    }
                    $btn.button('reset');
                }
            });
        }
    });

</script>