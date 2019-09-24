<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
	<button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
	<h3>${branchGroup!=null?'编辑':'添加'}党小组</h3>
</div>
<div class="modal-body">
	<form class="form-horizontal" action="${ctx}/branchGroup_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
		<input type="hidden" name="id" value="${branchGroup.id}">
		<input type="hidden" name="branchId" value="${branchId}">
		<div class="form-group">
			<label class="col-xs-3 control-label"><span class="star">*</span> 小组名称</label>
			<div class="col-xs-6">
				<input required class="form-control" type="text" name="name" value="${branchGroup.name}">
			</div>
		</div>
	</form>
</div>
<div class="modal-footer">
	<a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
	<button id="submitBtn" class="btn btn-primary"
			data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"><i class="fa fa-check"></i>
		${not empty branchGroup?'确定':'添加'}</button>
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
</script>