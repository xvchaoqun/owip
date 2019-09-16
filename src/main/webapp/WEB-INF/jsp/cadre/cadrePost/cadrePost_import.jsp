<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>         
  <div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>导入${isMainPost?"干部第一主职情况":"干部兼职情况"}</h3>
  </div>
  <div class="modal-body">
    <form class="form-horizontal" autocomplete="off" disableautocomplete id="modalForm" enctype="multipart/form-data"
          action="${ctx}/cadrePost_import" method="post">
		<div class="form-group">
			<label class="col-xs-offset-1 col-xs-2 control-label"><span class="star">*</span>Excel文件</label>
			<div class="col-xs-6">
				<input type="hidden" name="isMainPost" value="${isMainPost}"/>
				<input type="file" name="xlsx" required extension="xlsx"/>
			</div>
		</div>
        </form>
        <div class="well">
        <span class="help-inline">导入的文件请严格按照
            <a href="${ctx}/attach?code=${isMainPost?"sample_cadreMainPost":"sample_cadreSubPost"}">
                ${isMainPost?"干部第一主职情况":"干部兼职情况"}录入样表.xlsx</a>（点击下载）的数据格式</span>
        </div>
  </div>
  <div class="modal-footer">
  <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
  <button id="submitBtn" type="button" class="btn btn-primary"
			 data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"> 确定</button>
  </div>

  <script>
	  $.fileInput($('#modalForm input[type=file]'))

		$("#submitBtn").click(function(){$("#modalForm").submit();return false;});
		$("#modalForm").validate({
				messages: {
                    "xlsx": {
                        required: "请选择文件",
                        extension: "请上传 xlsx格式的文件"
                    }
                },
				submitHandler: function (form) {
				    var $btn = $("#submitBtn").button('loading');
					$(form).ajaxSubmit({
						dataType:"json",
						success:function(ret){
							if(ret && ret.successCount>=0){
								$("#modal").modal('hide');
								var result = '操作成功，总共{0}条记录，其中成功导入{1}条记录，<font color="red">{2}条覆盖</font>';
								SysMsg.success(result.format(ret.total, ret.successCount, ret.total-ret.successCount), '成功',function(){
									$("#jqGrid").trigger("reloadGrid");
								});
							}
							$btn.button('reset');
						}
					});
				}
			});

</script>