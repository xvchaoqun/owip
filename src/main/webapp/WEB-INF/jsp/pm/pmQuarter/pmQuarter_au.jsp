<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${pmQuarter!=null?'编辑':'添加'}三会一课季度汇总</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/pmQuarter_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${pmQuarter.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 年度</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="year" value="${pmQuarter.year}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 季度</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="quarter" value="${pmQuarter.quarter}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 季度是否结束</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="isFinish" value="${pmQuarter.isFinish}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 类型 1 分党委 2 支部</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="type" value="${pmQuarter.type}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 分党委id</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="partyId" value="${pmQuarter.partyId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 数量(分党委或支部数量)</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="num" value="${pmQuarter.num}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 应召开党员大会支部数量</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="dueNum" value="${pmQuarter.dueNum}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 已召开党员大会数量</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="finishNum" value="${pmQuarter.finishNum}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 备注</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="remark" value="${pmQuarter.remark}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty pmQuarter?'确定':'添加'}</button>
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