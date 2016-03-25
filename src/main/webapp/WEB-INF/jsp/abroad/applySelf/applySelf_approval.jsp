<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>审批</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/applySelf_approval" id="modalForm" method="post">
        <input type="hidden" name="applySelfId" value="${param.applySelfId}">
        <input type="hidden" name="approvalTypeId" value="${param.approvalTypeId}">
        <input type="hidden" name="status">
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
					<textarea class="form-control limited" type="text" name="remark" rows="2"></textarea>
				</div>
			</div>

    </form>
</div>
<div class="modal-footer">
    <input type="button" id="approval_disagree" class="btn btn-danger btn-primary" value="不通过"/>
    <input type="button" id="approval_agree" class="btn btn-success btn-primary" value="通过"/>
	<a href="#" data-dismiss="modal" class="btn btn-default">返回</a>
</div>
<script src="${ctx}/assets/js/bootstrap-tag.js"></script>
<script src="${ctx}/assets/js/ace/elements.typeahead.js"></script>
<script>

	$("#approval_agree").click(function(){
		//bootbox.confirm("确定<span style='color: green; font-weight: bolder; font-size: 20px'>通过</span>审核吗？", function (result) {
		//	if (result) {
				$("#modalForm input[name=status]").val(1);
				$("#modalForm").submit(); return false;
		//	}
		//});
	});
	$("#approval_disagree").click(function(){
		//bootbox.confirm("确定<span style='color: #ff0000; font-weight: bolder; font-size: 20px'>不通过</span>审核吗？", function (result) {
		//	if (result) {
				$("#modalForm input[name=status]").val(0);
				$("#modalForm").submit(); return false;
		//	}
		//});
	});
	$("#modalForm").validate({
		submitHandler: function (form) {
			$(form).ajaxSubmit({
				success:function(ret){
					if(ret.success){
						page_reload();
						SysMsg.success('操作成功。', '成功');
					}
				}
			});
		}
	});
	$('textarea.limited').inputlimiter();
</script>