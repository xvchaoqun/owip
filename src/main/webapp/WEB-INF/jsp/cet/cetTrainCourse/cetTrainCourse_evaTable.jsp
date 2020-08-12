<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>设置评估表</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cet/cetTrainCourse_evaTable" autocomplete="off" disableautocomplete id="modalForm" method="post">
		<input type="hidden" name="ids" value="${param.ids}">
		<input type="hidden" name="trainId" value="${param.trainId}">
		<c:set var="count" value="${fn:length(fn:split(param.ids,\",\"))}"/>

			<div class="form-group">
				<label class="col-xs-3 control-label">培训班次</label>
				<div class="col-xs-6 label-text">
					${cetTrain.name}
				</div>
			</div>
		<c:if test="${count>1}">
			<div class="form-group">
				<label class="col-xs-3 control-label">培训课程</label>
				<div class="col-xs-4 label-text">
						${count} 个
				</div>
			</div>
		</c:if>

			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>评估表</label>
				<div class="col-xs-6">
					<div class="input-group">
						<select required data-rel="select2" name="evaTableId" data-placeholder="请选择"  data-width="270">
							<option></option>
							<c:forEach items="${cetTrainEvaTableMap}" var="entity">
								<option value="${entity.key}">${entity.value.name}</option>
							</c:forEach>
						</select>
					</div>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="确定"/>
</div>

<script>
	$.register.datetime($('.datetime-picker'));
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
						$("#"+"${param.grid}").trigger("reloadGrid");
                    }
                }
            });
        }
    });
    $("#modalForm :checkbox").bootstrapSwitch();
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>