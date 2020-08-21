<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/pcs/constants.jsp" %>
  <div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>导入党代会推荐人信息</h3>
  </div>
  <div class="modal-body">
    <form class="form-horizontal" autocomplete="off" disableautocomplete id="modalForm" enctype="multipart/form-data"
		  action="${ctx}/pcs/pcsPollCandidate_import" method="post">
        <input type="hidden" name="pollId" value="${param.pollId}"/>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span> 推荐人类型</label>
            <div class="col-xs-6">
                <select required data-rel="select2" name="type" data-width="270"
                        data-placeholder="请选择">
                    <option></option>
                    <c:forEach items="${PCS_USER_TYPE_MAP}" var="entry" begin="${stage==PCS_POLL_THIRD_STAGE?1:0}">
                        <option value="${entry.key}">${entry.value}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
		<div class="form-group">
			<label class="col-xs-offset-1 col-xs-2 control-label"><span class="star">*</span>Excel文件</label>
			<div class="col-xs-6">
				<input type="file" name="xlsx" required extension="xlsx"/>
			</div>
		</div>
        </form>
        <div class="well">
        <span class="help-inline">导入的文件请严格按照<a href="${ctx}/attach?code=sample_pcsPollCandidate">党代会推荐人录入样表.xlsx</a>（点击下载）的数据格式</span>
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
						dataType:"json",
						success:function(ret){
							if(ret && ret.addCount>=0){
								$("#modal").modal('hide');
								$("#jqGrid2").trigger("reloadGrid");
								var result = '操作成功，总共{0}条记录，其中成功导入{1}条记录';
								SysMsg.success(result.format(ret.total, ret.addCount, ret.total-ret.addCount), '成功');
							}
							$btn.button('reset');
						}
					});
				}
			});
      $('#modalForm [data-rel="select2"]').select2();
</script>