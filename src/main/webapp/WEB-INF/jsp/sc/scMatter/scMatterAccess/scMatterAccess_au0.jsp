<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${scMatterAccess!=null}">编辑</c:if><c:if test="${scMatterAccess==null}">添加</c:if>个人有关事项-调阅记录</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/sc/scMatterAccess_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${scMatterAccess.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">调阅日期</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="accessDate" value="${scMatterAccess.accessDate}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">调阅函</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="accessFile" value="${scMatterAccess.accessFile}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">调阅单位</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="unitId" value="${scMatterAccess.unitId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">调阅类型</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="isCopy" value="${scMatterAccess.isCopy}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">调阅用途</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="purpose" value="${scMatterAccess.purpose}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">办理日期</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="handleDate" value="${scMatterAccess.handleDate}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">经办人</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="handleUserId" value="${scMatterAccess.handleUserId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">接收人</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="receiver" value="${scMatterAccess.receiver}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">接收手续</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="receivePdf" value="${scMatterAccess.receivePdf}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">归还时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="returnDate" value="${scMatterAccess.returnDate}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">归还接收人</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="returnUserId" value="${scMatterAccess.returnUserId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="remark" value="${scMatterAccess.remark}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${scMatterAccess!=null}">确定</c:if><c:if test="${scMatterAccess==null}">添加</c:if></button>
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