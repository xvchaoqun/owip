<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${scPassportHand!=null}">编辑</c:if><c:if test="${scPassportHand==null}">添加</c:if>新提任干部交证件</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/sc/scPassportHand_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${scPassportHand.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>关联账号</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="userId" value="${scPassportHand.userId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>新提任日期</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="appointDate" value="${scPassportHand.appointDate}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>添加方式</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="addType" value="${scPassportHand.addType}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>备注</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="remark" value="${scPassportHand.remark}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>状态</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="status" value="${scPassportHand.status}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>证件是否已入库</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="isKeep" value="${scPassportHand.isKeep}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>添加时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="addTime" value="${scPassportHand.addTime}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${scPassportHand!=null}">确定</c:if><c:if test="${scPassportHand==null}">添加</c:if></button>
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
    //$("#modalForm :checkbox").bootstrapSwitch();
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#modalForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$('textarea.limited').inputlimiter();
    //$.register.date($('.date-picker'));
</script>