<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>
		${param.status==1?'同意申请':'不同意申请'}
    </h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/applySelf_approval" id="modalForm" method="post">
        <input type="hidden" name="applySelfId" value="${param.applySelfId}">
        <input type="hidden" name="approvalTypeId" value="${param.approvalTypeId}">
        <input type="hidden" name="status" value="${param.status}">
			<div class="form-group">
				<label class="col-xs-3 control-label">${param.status==1?'审批意见':'原因'}</label>
				<div class="col-xs-6">
					<textarea ${param.status==1?'':'required'} class="form-control limited" type="text" name="remark" rows="5"></textarea>
				</div>
			</div>

    </form>
</div>
<div class="modal-footer">
    <input type="button" id="approvalBtn" class="btn ${param.status==1?"btn-primary":"btn-danger"}" value="确认"/>
	<a href="#" data-dismiss="modal" class="btn btn-default">返回</a>
</div>
<script src="${ctx}/assets/js/bootstrap-tag.js"></script>
<script src="${ctx}/assets/js/ace/elements.typeahead.js"></script>
<script>

	$("#approvalBtn").click(function(){
		$("#modalForm").submit(); return false;
	});
	$("#modalForm").validate({
		submitHandler: function (form) {
			$(form).ajaxSubmit({
				success:function(ret){
					if(ret.success){
						$("#modal").modal('hide');
						//SysMsg.success('提交成功。', '成功',function(){
							$("#item-content").fadeOut("fast",function(){
								$("#body-content").show(0,function(){
									$("#jqGrid").trigger("reloadGrid");
								});
							});
						//});
					}
				}
			});
		}
	});
	$('textarea.limited').inputlimiter();
</script>