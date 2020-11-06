<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>         
  <div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>导入干部</h3>
  </div>
  <div class="modal-body">
    <form class="form-horizontal" autocomplete="off" disableautocomplete id="modalForm" enctype="multipart/form-data" action="${ctx}/cadreAll_import" method="post">
        <div class="form-group">
            <label class="col-xs-offset-1 col-xs-4 control-label"><span class="star">*</span> 内设机构起始编码</label>
            <div class="col-xs-6">
                <input type="text" name="unitCode" class="digits" data-rule-min="1000"/>
            </div>
        </div>
		<div class="form-group">
			<label class="col-xs-offset-1 col-xs-4 control-label"><span class="star">*</span> Excel文件</label>
			<div class="col-xs-6">
				<input type="file" name="xlsx" required extension="xlsx"/>
			</div>
		</div>
        </form>
        <div class="well">
            <c:choose>
                <c:when test="${_p_useCadreState}">
                    <c:set var="sampleCode" value="sample_cadre_useCadreState"/>
                </c:when>
                <c:otherwise>
                    <c:set var="sampleCode" value="sample_cadre"/>
                </c:otherwise>
            </c:choose>
        <span class="help-inline">内设机构起始编码可以按照以下格式：年份+001。例如：2020001</span><br>
        <span class="help-inline">导入的文件请严格按照<a href="${ctx}/attach?code=${sampleCode}">干部录入样表.xlsx</a>（点击下载）的数据格式</span>
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

				submitHandler: function (form) {
				    var $btn = $("#submitBtn").button('loading');
					$(form).ajaxSubmit({
						success:function(ret){
							if(ret.success){
								$("#modal").modal('hide');
								var result = '操作成功，导入干部成功，共导入{0}个单位，{1}个岗位，{2}个干部';
								SysMsg.success(result.format(ret.unitCount, ret.unitPostCount, ret.cadreCount), '成功',function(){
									$("#jqGrid").trigger("reloadGrid");
								});
							}
							$btn.button('reset');
						}
					});
				}
			});

</script>