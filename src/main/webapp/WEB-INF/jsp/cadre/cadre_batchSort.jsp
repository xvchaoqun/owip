<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>         
  <div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>批量排序(${CADRE_STATUS_MAP.get(status)})</h3>
  </div>
  <div class="modal-body">
      <div class="well">
          第一步： 按当前排序导出数据：<button class="downloadBtn btn btn-warning btn-xs"
                              data-url="${ctx}/cadre_data?export=4&status=${status}"><i class="fa fa-download"></i> 导出</button>
       </div>

    <form class="form-horizontal" autocomplete="off" disableautocomplete id="modalForm"
          enctype="multipart/form-data" action="${ctx}/cadre_batchSort" method="post">


		<div class="form-group">
			<label class="col-xs-5 control-label"><span class="star"></span> 第二步：导入更新后的数据：</label>
			<div class="col-xs-5">
				<input type="hidden" name="status" value="${status}"/>
				<input type="file" name="xlsx" required extension="xlsx"/>
			</div>
		</div>
        </form>
        <div class="well">
        <span class="help-inline">导入的文件请严格按照第一步导出的数据（不要删除或新增干部），此操作仅更新排序。</span>
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
							if(ret.success){
								$("#modal").modal('hide');
								SysMsg.success('批量排序操作成功', '成功',function(){
									$("#jqGrid").trigger("reloadGrid");
								});
							}
							$btn.button('reset');
						}
					});
				}
			});

</script>