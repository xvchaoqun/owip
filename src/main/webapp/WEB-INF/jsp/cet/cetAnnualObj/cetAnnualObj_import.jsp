<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>         
  <div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>批量导入参训人员信息</h3>
  </div>
  <div class="modal-body">
    <form class="form-horizontal" autocomplete="off" disableautocomplete id="modalForm"
          enctype="multipart/form-data" action="${ctx}/cet/cetAnnualObj_import?annualId=${param.annualId}" method="post">
        <div class="form-group">
            <label class="col-xs-3 control-label"> 参训人员类型</label>
            <div class="col-xs-6 label-text">
                ${cetTraineeType.name}
            </div>
        </div>
        <div class="form-group">
			<label class="col-xs-3 control-label"><span class="star">*</span>Excel文件</label>
			<div class="col-xs-6">
				<input class="form-control" type="file" name="xlsx" required extension="xlsx"/>
			</div>
		</div>
        </form>
        <div class="well">
        <span class="help-inline">导入的文件请严格按照
            <a href="${ctx}/attach?code=sample_cet_annual_obj">
                培训对象导入样表.xlsx</a>（点击下载）的数据格式</span>
        </div>
  </div>
  <div class="modal-footer">
  <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
	  <button id="submitBtn" type="button" class="btn btn-primary"
			  data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口">确定</button>
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
							if(ret && ret.successCount>=0){
							    $("#modal").modal('hide');
							    $("#jqGrid2").trigger("reloadGrid");

								var result = '操作成功，总共{0}条记录，其中成功导入{1}条记录，<font color="red">{2}条已存在</font>';
								SysMsg.success(result.format(ret.total, ret.successCount, ret.total-ret.successCount), '成功');
							}
							$btn.button('reset');
						}
					});
				}
			});
      $('[data-rel="select2"]').select2();
      $('[data-rel="tooltip"]').tooltip();

</script>