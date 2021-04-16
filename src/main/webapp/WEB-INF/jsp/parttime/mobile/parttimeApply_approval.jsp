<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>
		${param.pass==1?'同意申请':'不同意申请'}
    </h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/m/parttime/parttimeApply_approval" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="applyId" value="${parttimeApply.id}">
        <input type="hidden" name="approvalTypeId" value="${parttimeApply.flowNode}">
        <input type="hidden" name="pass" value="${param.pass}">
			<div class="form-group">
				<div class="col-xs-12">
					<textarea placeholder="请输入${param.pass==1?'审批意见':'原因'}" class="form-control limited" type="text" name="remark" rows="5"></textarea>
				</div>
			</div>

    </form>
</div>
<div class="modal-footer">
    <input type="button" id="approvalBtn" class="btn ${param.pass==1?"btn-primary":"btn-danger"}" value="确认"/>
	<a href="javascript:;" data-dismiss="modal" parttimess="btn btn-default">返回</a>
</div>

<script>
	$("#approvalBtn").click(function(){
		var applyId = $("input[name=applyId]").val();
		var approvalTypeId = $("input[name=approvalTypeId]").val();
		var pass = $("input[name=pass]").val();
		var remark = $.trim($("textarea[name=remark]").val());
		if(pass==0&&remark==''){
			$("textarea[name=remark]").val('').focus();
			return;
		}
		$.post("${ctx}/m/parttime/parttimeApply_approval",{applyId:applyId,
			approvalTypeId:approvalTypeId, pass:pass, remark:remark },function(ret){
			if(ret.success){
				$("#modal").modal('hide');
				location.href="${ctx}${param.type=='admin'?'/m/parttime/parttimeApply':'/m/parttime/parttimeApplyList'}";
			}
		});
	});
</script>