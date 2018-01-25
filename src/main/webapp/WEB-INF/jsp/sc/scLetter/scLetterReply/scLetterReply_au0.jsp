<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${scLetterReply!=null}">编辑</c:if><c:if test="${scLetterReply==null}">添加</c:if>纪委回复文件</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/sc/scLetterReply_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${scLetterReply.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">函询</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="letterId" value="${scLetterReply.letterId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">回复文件类型</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="type" value="${scLetterReply.type}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">回复文件编号</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="num" value="${scLetterReply.num}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">回复日期</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="replyDate" value="${scLetterReply.replyDate}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">回复情况</label>
				<div class="col-xs-6">
                        <textarea class="form-control" name="content">${scLetterReply.content}</textarea>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="remark" value="${scLetterReply.remark}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${scLetterReply!=null}">确定</c:if><c:if test="${scLetterReply==null}">添加</c:if></button>
</div>

<script>
    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $("#jqGrid").trigger("reloadGrid");
                    }
                }
            });
        }
    });
    $("#modalForm :checkbox").bootstrapSwitch();
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>