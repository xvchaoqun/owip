<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>         
  <div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>批量导入头像</h3>
  </div>
  <div class="modal-body">
    <form class="form-horizontal" autocomplete="off" disableautocomplete id="modalForm"
          enctype="multipart/form-data" action="${ctx}/avatar_import" method="post">
		<div class="form-group">
			<label class="col-xs-offset-1 col-xs-2 control-label"><span class="star">*</span>头像压缩包(zip格式)</label>
			<div class="col-xs-6">
				<input type="file" name="zip" required extension="zip"/>
			</div>
		</div>
        </form>
  </div>
  <div class="modal-footer">
      <div class="note">
          <ul>
              <li>每个头像文件必须以学工号命名</li>
              <li>头像文件名支持的后缀：jpg|png|gif</li>
          </ul>
      </div>
  <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
  <button id="submitBtn" type="button" class="btn btn-primary"
			 data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"> 确定</button>
  </div>

  <script>
	     $.fileInput($('#modalForm input[type=file]'))

		$("#submitBtn").click(function(){$("#modalForm").submit();return false;});
		$("#modalForm").validate({
				submitHandler: function (form) {
				     var $btn = $("#submitBtn").button('loading');
					$(form).ajaxSubmit({
						dataType:"json",
						success:function(ret){
							if(ret.success){
								$("#modal").modal('hide');
								$("#jqGrid").trigger("reloadGrid");
								var result = '压缩包总计{0}个文件，其中成功更新{1}个头像，失败{2}个';
								SysMsg.success(result.format(ret.total, ret.save, ret.error), '上传完成');
							}
							$btn.button('reset');
						}
					});
				}
			});

</script>