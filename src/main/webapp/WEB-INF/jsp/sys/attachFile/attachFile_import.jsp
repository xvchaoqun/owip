<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>         
  <div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>批量导入更新文件</h3>
  </div>
  <div class="modal-body">
    <form class="form-horizontal" autocomplete="off" disableautocomplete id="modalForm" enctype="multipart/form-data" action="${ctx}/attachFile_import" method="post">
		<div class="form-group">
			<label class="col-xs-4 control-label">单个Excel文件</label>
			<div class="col-xs-6">
				<input type="file" name="xls" extension="xlsx|xls"/>
                <span class="help-block">
                    注：文件名命名方式必须为“[唯一标识]表中文名”
                </span>
			</div>
		</div>
        <div class="form-group">
			<label class="col-xs-4 control-label">或 Zip文件</label>
			<div class="col-xs-6">
				<input type="file" name="zip" extension="zip"/>
			</div>
		</div>
        </form>
  </div>
  <div class="modal-footer">
      <div class="note">
          <ul>
              <li>压缩包文件名命名方式为[sample_cadre]领导干部录入样表.xlsx</li>
              <li>根据方括号内的文件标识，判断是否存在，如果存在则更新文件名和后缀，不存在则插入</li>
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

				    var xls = $('#modalForm input[name=xls]').val();
				    var zip = $('#modalForm input[name=zip]').val();
				    if(xls==''&&zip==''){

				        SysMsg.error("请选择文件");
				        return;
                    }

				     var $btn = $("#submitBtn").button('loading');
					$(form).ajaxSubmit({
						dataType:"json",
						success:function(ret){
							if(ret.success){
								$("#modal").modal('hide');
								$("#jqGrid").trigger("reloadGrid");
								var result = '操作成功，压缩包总计{0}个文件，其中新增{1}个文件，更新{2}个文件';
								SysMsg.success(result.format(ret.total, ret.addCount, ret.updateCount), '成功');
							}
							$btn.button('reset');
						}
					});
				}
			});

</script>