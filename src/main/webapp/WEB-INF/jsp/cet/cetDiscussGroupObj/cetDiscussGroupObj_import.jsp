<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>         
  <div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>上传分组/参会情况</h3>
  </div>
  <div class="modal-body">
    <form class="form-horizontal" id="modalForm" enctype="multipart/form-data"
		  action="${ctx}/cet/cetDiscussGroupObj_import" method="post">
		<div class="form-group">
			<label class="col-xs-offset-1 col-xs-2 control-label">Excel文件</label>
			<div class="col-xs-4">
				<input type="hidden" name="discussGroupId" value="${param.discussGroupId}"/>
				<input type="file" name="xlsx" required extension="xlsx"/>
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-offset-1 col-xs-2 control-label">是否参会</label>
			<div class="col-xs-4">
				<input type="checkbox" class="big" name="isFinished"/>
			</div>
		</div>
        </form>
        <div class="well">
        <span class="help-inline">导入的文件请严格按照<a href="${ctx}/attach?code=sample_cet_group_sign" target="_blank">分组/参会导入样表.xlsx</a>（点击下载）的数据格式</span>
        </div>
  </div>
  <div class="modal-footer">
  <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
  <input type="submit" class="btn btn-primary" value="确定"/>
  </div>
<script type="text/template" id="failed_tpl">
	{{if(failedXlsRows.length>=1){}}
	<div style="color: red;max-height: 150px; overflow-y: auto; font-size: 14px; text-indent: 0px;">
		<table class="table table-bordered table-condensed table-unhover2 table-center">
			<thead>
			<tr>
				<th colspan="2" style="text-align: center">
					{{=failedXlsRows.length}}条失败记录
				</th>
			</tr>
			<tr>
				<th style="width: 180px;">工号</th>
				<th>姓名</th>
			</tr>
			</thead>
			<tbody>
			{{_.each(failedXlsRows, function(r, idx){ }}
			<tr>
				<td>{{=r[0]}}</td>
				<td>{{=r[1]}}</td>
			</tr>
			{{});}}
			</tbody>
		</table>
	</div>
	{{}}}
</script>
  <script>
	  $.fileInput($('#modalForm input[type=file]'))
	  $("#modalForm :checkbox").bootstrapSwitch();
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

								var result = '导入完成，总共{0}条记录，其中成功导入{1}条记录';
								var failed =  _.template($("#failed_tpl").html().NoMultiSpace())({
									failedXlsRows: ret.failedXlsRows
								});

								$("#modal").modal('hide');
								$("#jqGrid2").trigger("reloadGrid");
								SysMsg.success(result.format(ret.total, ret.successCount) + failed, '成功');
							}
						}
					});
				}
			});

</script>