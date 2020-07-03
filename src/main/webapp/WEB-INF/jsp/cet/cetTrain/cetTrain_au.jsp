<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cetTrain!=null}">编辑</c:if><c:if test="${cetTrain==null}">添加</c:if>培训班</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cet/cetTrain_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${cetTrain.id}">
        <input type="hidden" name="planId" value="${planId}">
		<input type="hidden" name="isOnCampus" value="1">

			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>培训班名称</label>
				<div class="col-xs-6">
						<textarea required class="form-control"
								 name="name">${cetTrain.name}</textarea>
				</div>
			</div>

			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>开课日期</label>
				<div class="col-xs-6">
					<div class="input-group">
						<input required class="form-control date-picker" name="startDate"
							   type="text"
							   data-date-format="yyyy-mm-dd"
							   value="${cm:formatDate(cetTrain.startDate,'yyyy-MM-dd')}"/>
                                            <span class="input-group-addon"> <i
													class="fa fa-calendar bigger-110"></i></span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>结课日期</label>
				<div class="col-xs-6">
					<div class="input-group">
						<input required class="form-control date-picker" name="endDate"
							   type="text"
							   data-date-format="yyyy-mm-dd"
							   value="${cm:formatDate(cetTrain.endDate,'yyyy-MM-dd')}"/>
                                            <span class="input-group-addon"> <i
													class="fa fa-calendar bigger-110"></i></span>
					</div>
					<span class="help-block">超过该日期，系统将自动结课，结课后该培训班将不显示在参训人员的[学习培训中心-选课中心]</span>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">是否已结课</label>
				<div class="col-xs-6">
					<input type="checkbox" name="isFinished" ${cetTrain.isFinished?"checked":""}/>
					<span class="help-block">可在结课日期之前提前结课，未结课之前该培训班将显示在参训人员的[学习培训中心-选课中心]</span>
				</div>
			</div>

			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
                       <textarea class="form-control limited"
								 name="remark">${cetTrain.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"
			data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口">
		<i class="fa fa-check"></i> <c:if test="${cetTrain!=null}">确定</c:if><c:if test="${cetTrain==null}">添加</c:if></button>
</div>
<script>
	$("#modalForm :checkbox").bootstrapSwitch();
    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
			var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
						<c:if test="${param.isOnCampus==0}">
                        $("#jqGrid").trigger("reloadGrid");
						</c:if>
						<c:if test="${param.isOnCampus!=0}">
						$("#jqGrid2").trigger("reloadGrid");
						</c:if>
                    }
					$btn.button('reset');
                }
            });
        }
    });
	$('textarea.limited').inputlimiter();
	$.register.date($('.date-picker'));
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>