<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>         
  <div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>导入党员</h3>
  </div>
  <div class="modal-body">
    <form class="form-horizontal" id="modalForm" enctype="multipart/form-data" action="${ctx}/cadreParty_import" method="post">
		<div class="form-group">
			<label class="col-xs-offset-1 col-xs-2 control-label">Excel文件</label>
			<div class="col-xs-4">
				<input type="hidden" name="status" value="${status}"/>
				<input type="file" name="xlsx" required extension="xlsx"/>
			</div>
		</div>
        </form>
        <div class="well">
        <span class="help-inline">导入的文件请严格按照<a href="${ctx}/attach?code=sample_cadreParty" target="_blank">党员录入样表.xlsx</a>（点击下载）的数据格式</span>
        </div>
  </div>
  <div class="modal-footer">
  <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
  <input type="submit" class="btn btn-primary" value="确定"/>
  </div>

  <script>
	  $.fileInput($('#modalForm input[type=file]'))

		$("#modalForm input[type=submit]").click(function(){$("#modalForm").submit();return false;});
		$("#modalForm").validate({
				messages: {
                    "xlsx": {
                        required: "请选择文件",
                        extension: "请上传 xlsx格式的文件"
                    }
                },
				submitHandler: function (form) {
					$(form).ajaxSubmit({
						dataType:"json",
						success:function(ret){
							if(ret && ret.successCount>=0){
								$("#modal").modal('hide');
								var result = '操作成功，总共{0}行记录，其中成功导入或更新{1}条记录';
								SysMsg.success(result.format(ret.total, ret.successCount, ret.total-ret.successCount), '成功',function(){
									$("#jqGrid").trigger("reloadGrid");
								});
							}
						}
					});
				}
			});

</script>