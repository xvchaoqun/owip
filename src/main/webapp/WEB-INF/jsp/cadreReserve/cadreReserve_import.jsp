<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set value="<%=CadreConstants.CADRE_RESERVE_TYPE_MAP%>" var="CADRE_RESERVE_TYPE_MAP"/>
  <div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>导入${CADRE_RESERVE_TYPE_MAP.get(reserveType)}</h3>
  </div>
  <div class="modal-body">
    <form class="form-horizontal" id="modalForm" enctype="multipart/form-data" action="${ctx}/cadreReserve_import" method="post">
		<div class="form-group">
			<label class="col-xs-offset-1 col-xs-2 control-label">Excel文件</label>
			<div class="col-xs-4">
				<input type="hidden" name="reserveType" value="${reserveType}"/>
				<input type="file" name="xlsx" required extension="xlsx"/>
			</div>
		</div>
        </form>
        <div class="well">
        <span class="help-inline">导入的文件请严格按照<a href="${ctx}/attach?code=sample_cadreReserve" target="_blank">后备干部录入样表.xlsx</a>（点击下载）的数据格式</span>
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
								var result = '操作成功，总共{0}条记录，其中成功导入{1}条记录，<font color="red">{2}条覆盖</font>';
								SysMsg.success(result.format(ret.total, ret.successCount, ret.total-ret.successCount), '成功',function(){
									$("#modal").modal('hide');
									$("#jqGrid").trigger("reloadGrid");
								});
							}
						}
					});
				}
			});

</script>