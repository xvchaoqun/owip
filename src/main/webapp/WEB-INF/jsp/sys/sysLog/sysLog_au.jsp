<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>         
  <div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${log!=null}">修改</c:if><c:if test="${log==null}">添加</c:if></h3>
  </div>
  <div class="modal-body">
    <form class="form-horizontal" action="sysLog_au" id="modalForm" method="post">
    	<input type="hidden" name="id" value="${log.id}">
		<div class="form-group">
			<label class="col-xs-offset-1 col-xs-2 control-label">操作用户</label>
			<div class="col-xs-4">
				<input type="text" name="userId" value="${log.userId}">
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-offset-1 col-xs-2 control-label">内容</label>
			<div class="col-xs-4">
				<input type="text" name="content" value="${log.content}">
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-offset-1 col-xs-2 control-label">类型</label>
			<div class="col-xs-4">
				<input type="text" name="typeId" value="${log.typeId}">
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-offset-1 col-xs-2 control-label">时间</label>
			<div class="col-xs-4">
				<input type="text" name="createTime" value="${log.createTime}">
			</div>
		</div>
        </form>
  </div>
  <div class="modal-footer">
  <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
  <a id="add_entity" class="btn btn-primary"><c:if test="${log!=null}">确认</c:if><c:if test="${log==null}">添加</c:if></a></div>

  <script>
	$(function(){
		
		$("#add_entity").click(function(){
			
			$("#modal form").submit();return false;
		});

		$("#modal form").validate({
				errorElement: 'span',
				errorClass: 'help-inline',
				focusInvalid: true,
				rules: {
					userId: {
						required: true
					}
					,
					content: {
						required: true
						,byteMaxLength:255
					}
					,
					type: {
						required: true
					}
					,
					createTime: {
						required: true
					}
					
				},
		
				invalidHandler: function (event, validator) { //display error alert on form submit   
					//alert(2)	
					//$('.alert-error', $('.login-form')).show();
				},
		
				highlight: function (e) {
					//alert(3)
					$(e).closest('.form-group').removeClass('info').addClass('error');
				},
		
				success: function (e) {
					$(e).closest('.form-group').removeClass('error').addClass('info');
					$(e).remove();
				},
		
				errorPlacement: function (error, element) {
	
					if(element.is(':checkbox') || element.is(':radio')) {
						var controls = element.closest('.controls');
						if(controls.find(':checkbox,:radio').length > 1) controls.append(error);
						else error.insertAfter(element.nextAll('.lbl').eq(0));
					} 
					else if(element.is('.chzn-select')) {
						error.insertAfter(element.nextAll('[class*="chzn-container"]').eq(0));
					}
					else error.insertAfter(element);
				},
		
				submitHandler: function (form) {
					
					$(form).ajaxSubmit({
						success:function(data){
							if(data.success){
								$("#modal").modal('hide');
								$("#content").load("log");
								//SysMsg.success('操作成功。', '成功');
								
							}else if(data.msg=="duplicate"){
								
								SysMsg.warning('该记录已经存在，请不要重复添加。', '重复');
							}
						}
					});
				},
				invalidHandler: function (form) {
				}
			});
	 })
</script>