<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${cetAnnualObj!=null?'编辑':'添加'}年度学习档案包含的培训对象</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cet/cetAnnualObj_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${cetAnnualObj.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>所属档案</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="recordId" value="${cetAnnualObj.recordId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>培训对象</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="userId" value="${cetAnnualObj.userId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>时任单位及职务</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="title" value="${cetAnnualObj.title}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>行政级别</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="adminLevel" value="${cetAnnualObj.adminLevel}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>职务属性</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="postType" value="${cetAnnualObj.postType}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>任现职时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="lpWorkTime" value="${cetAnnualObj.lpWorkTime}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>年度学习任务</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="period" value="${cetAnnualObj.period}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>已完成学时数</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="finishPeriod" value="${cetAnnualObj.finishPeriod}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>党校专题培训学时上限</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="maxSpecialPeriod" value="${cetAnnualObj.maxSpecialPeriod}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>党校日常培训学时上限</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="maxDailyPeriod" value="${cetAnnualObj.maxDailyPeriod}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>二级党校培训学时上限</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="maxPartyPeriod" value="${cetAnnualObj.maxPartyPeriod}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>二级单位培训学时上限</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="maxUnitPeriod" value="${cetAnnualObj.maxUnitPeriod}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>上级调训学时上限</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="maxUpperPeriod" value="${cetAnnualObj.maxUpperPeriod}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>党校专题培训完成学时</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="specialPeriod" value="${cetAnnualObj.specialPeriod}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>党校日常培训完成学时</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="dailyPeriod" value="${cetAnnualObj.dailyPeriod}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>二级党校培训完成学时</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="partyPeriod" value="${cetAnnualObj.partyPeriod}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>二级单位培训完成学时</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="unitPeriod" value="${cetAnnualObj.unitPeriod}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>上级调训完成学时</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="upperPeriod" value="${cetAnnualObj.upperPeriod}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>排序</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="sortOrder" value="${cetAnnualObj.sortOrder}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>备注</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="remark" value="${cetAnnualObj.remark}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${cetAnnualObj!=null}">确定</c:if><c:if test="${cetAnnualObj==null}">添加</c:if></button>
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