<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>设定年度学习任务</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cet/cetAnnualObj_singleRequire" id="modalForm" method="post">
			<input type="hidden" name="objId" value="${param.id}">

			<div class="form-group">
			<label class="col-xs-5 control-label">姓名</label>
				<div class="col-xs-3 label-text">
                    ${cetAnnualObj.user.realname}
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-5 control-label">年度学习任务</label>
				<div class="col-xs-3">
                        <input required class="form-control period" type="text" name="period" data-rule-min="0.5"
							   value="${cm:trimToZero(cetAnnualObj.period)}">
				</div>
			</div>

			<div class="form-group">
				<label class="col-xs-5 control-label">党校专题培训学时上限</label>
				<div class="col-xs-3">
                        <input class="form-control period" type="text" name="maxSpecialPeriod" value="${cm:trimToZero(cetAnnualObj.maxSpecialPeriod)}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-5 control-label">党校日常培训学时上限</label>
				<div class="col-xs-3">
                        <input class="form-control period" type="text" name="maxDailyPeriod" value="${cm:trimToZero(cetAnnualObj.maxDailyPeriod)}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-5 control-label">二级党校培训学时上限</label>
				<div class="col-xs-3">
                        <input class="form-control period" type="text" name="maxPartyPeriod" value="${cm:trimToZero(cetAnnualObj.maxPartyPeriod)}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-5 control-label">二级单位培训学时上限</label>
				<div class="col-xs-3">
                        <input class="form-control period" type="text" name="maxUnitPeriod" value="${cm:trimToZero(cetAnnualObj.maxUnitPeriod)}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-5 control-label">上级调训学时上限</label>
				<div class="col-xs-3">
                        <input class="form-control period" type="text" name="maxUpperPeriod" value="${cm:trimToZero(cetAnnualObj.maxUpperPeriod)}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-5 control-label">备注</label>
				<div class="col-xs-5">
                        <textarea class="form-control limited"
							  name="remark">${cetAnnualObj.remark}</textarea>
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
                        $("#jqGrid2").trigger("reloadGrid");
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