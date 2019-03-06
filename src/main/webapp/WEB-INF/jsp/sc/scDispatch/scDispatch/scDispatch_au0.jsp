<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${scDispatch!=null}">编辑</c:if><c:if test="${scDispatch==null}">添加</c:if>文件起草签发</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/sc/scDispatch_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${scDispatch.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>年份</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="year" value="${scDispatch.year}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>发文类型</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="dispatchTypeId" value="${scDispatch.dispatchTypeId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>发文号</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="code" value="${scDispatch.code}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>党委常委会日期</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="meetingTime" value="${scDispatch.meetingTime}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>起草日期</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="pubTime" value="${scDispatch.pubTime}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>文件签发稿</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="filePath" value="${scDispatch.filePath}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>签发单</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="signFilePath" value="${scDispatch.signFilePath}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>备注</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="remark" value="${scDispatch.remark}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${scDispatch!=null}">确定</c:if><c:if test="${scDispatch==null}">添加</c:if></button>
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