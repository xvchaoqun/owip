<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${cetAnnualRequire!=null?'编辑':'添加'}设定年度学习任务</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cet/cetAnnualRequire_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${cetAnnualRequire.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>所属档案</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="annualId" value="${cetAnnualRequire.annualId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>行政级别</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="adminLevel" value="${cetAnnualRequire.adminLevel}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>年度学习任务</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="period" value="${cetAnnualRequire.period}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>党校专题培训学时上限</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="maxSpecialPeriod" value="${cetAnnualRequire.maxSpecialPeriod}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>党校日常培训学时上限</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="maxDailyPeriod" value="${cetAnnualRequire.maxDailyPeriod}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>二级党校培训学时上限</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="maxPartyPeriod" value="${cetAnnualRequire.maxPartyPeriod}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>二级单位培训学时上限</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="maxUnitPeriod" value="${cetAnnualRequire.maxUnitPeriod}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>上级调训学时上限</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="maxUpperPeriod" value="${cetAnnualRequire.maxUpperPeriod}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>备注</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="remark" value="${cetAnnualRequire.remark}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>影响人数</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="relateCount" value="${cetAnnualRequire.relateCount}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>操作时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="opTime" value="${cetAnnualRequire.opTime}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>操作人</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="opUserId" value="${cetAnnualRequire.opUserId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>ip</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="ip" value="${cetAnnualRequire.ip}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${cetAnnualRequire!=null}">确定</c:if><c:if test="${cetAnnualRequire==null}">添加</c:if></button>
</div>
<script>
    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $("#jqGrid").trigger("reloadGrid");
                    }
                    $btn.button('reset');
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