<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${scSubsidyDc!=null}">编辑</c:if><c:if test="${scSubsidyDc==null}">添加</c:if>干部任免记录</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/sc/scSubsidyDc_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${scSubsidyDc.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>subsidy_id</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="subsidyId" value="${scSubsidyDc.subsidyId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>cadre_id</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="cadreId" value="${scSubsidyDc.cadreId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>任免日期</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="workTime" value="${scSubsidyDc.workTime}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>类别</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="type" value="${scSubsidyDc.type}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>职务</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="post" value="${scSubsidyDc.post}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>行政级别</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="adminLevel" value="${scSubsidyDc.adminLevel}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${scSubsidyDc!=null}">确定</c:if><c:if test="${scSubsidyDc==null}">添加</c:if></button>
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