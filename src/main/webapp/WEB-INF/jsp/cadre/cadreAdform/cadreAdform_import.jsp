<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>         
  <div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>导入干部任免审批表</h3>
  </div>
  <div class="modal-body">
    <form class="form-horizontal" autocomplete="off" disableautocomplete
          id="modalForm" enctype="multipart/form-data" action="${ctx}/cadreAdform_import" method="post">
		<div class="form-group">
			<label class="col-xs-4 text-right">单个部任免审批表文件</label>
			<div class="col-xs-6">
				<input type="file" name="lrmx" extension="lrmx"/>
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-4 text-right">或 批量上传（zip包）</label>
			<div class="col-xs-6">
				<input type="file" name="zip" extension="zip"/>
			</div>
		</div>
        </form>
        <div class="well" style="margin-bottom: 0">
            <ul>
                <li>如需批量上传，请先将中组部格式的任免审批表打包成zip文件</li>
                <li>系统将以审批表内的姓名为依据进行导入，如果存在重名的干部会导入失败</li>
                <li class="red">审批表内的简历无法直接导入，可在学习经历或工作经历中点击【添加】-【查看干部任免审批表简历】进行查看。</li>
                <li>请确保系统中的[元数据-家庭成员称谓]中，已包含任免审批表中的称谓</li>
                <li>家庭成员以姓名为依据进行导入，如果第二次导入会根据姓名进行覆盖</li>
                <li class="red">重复导入任免审批表将进行覆盖操作</li>
            </ul>
        </div>
  </div>
  <div class="modal-footer">
  <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
  <button id="submitBtn" type="button" class="btn btn-primary"
			 data-loading-text="<i class='fa fa-spinner fa-spin '></i> 导入中，请不要关闭此窗口"> 确定</button>
  </div>

  <script>
	  $.fileInput($('#modalForm input[name=lrmx]'),{
            allowExt: ['lrmx']
        })
	  $.fileInput($('#modalForm input[name=zip]'),{
            allowExt: ['zip']
        })

		$("#submitBtn").click(function(){$("#modalForm").submit();return false;});
		$("#modalForm").validate({
				messages: {
                    "lrmx": {
                        required: "请选择干部任免审批表",
                        extension: "请选择中组部格式的干部任免审批表"
                    },
                    "zip": {
                        extension: "请选择zip格式的干部任免审批表压缩包"
                    }
                },
				submitHandler: function (form) {

				    if($('#modalForm input[name=lrmx]').val()=='' && $('#modalForm input[name=zip]')==''){
				        return;
                    }
				    var $btn = $("#submitBtn").button('loading');
					$(form).ajaxSubmit({
						dataType:"json",
						success:function(ret){
							if(ret.success){
								$("#modal").modal('hide');

								var result = ('导入结束，总共导入{0}个干部任免审批表').format(ret.totalCount);
								var failCount = ret.fails.length;
								if(failCount>0){
								    result += ('<br/><font color="red">其中{0}个导入失败：<br/>{1}</font>')
                                        .format(failCount, ret.fails.join("<br/>"));
                                }

								SysMsg.success(result, '导入结果',function(){
									$("#jqGrid").trigger("reloadGrid");
								});
							}
							$btn.button('reset');
						}
					});
				}
			});

</script>