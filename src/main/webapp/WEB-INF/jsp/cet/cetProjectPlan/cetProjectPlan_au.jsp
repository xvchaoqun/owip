<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/cet/constants.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cetProjectPlan!=null}">编辑</c:if><c:if test="${cetProjectPlan==null}">添加</c:if>培训方案</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cet/cetProjectPlan_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${cetProjectPlan.id}">
        <input type="hidden" name="projectId" value="${projectId}">
		<div class="form-group">
			<label class="col-xs-3 control-label"><span class="star">*</span>培训时间</label>
			<div class="col-xs-6">
				<div class="input-group">
				<input required class="form-control date-picker" name="startDate"
					   type="text" style="width: 100px;float: left"
					   data-date-format="yyyy.mm.dd"
					   value="${cm:formatDate(cetProjectPlan.startDate,'yyyy.MM.dd')}"/>
				<div style="float: left;margin: 5px 5px 0 5px;"> 至 </div>
				<input required class="form-control date-picker" name="endDate"
					   type="text" style="width: 100px;float: left"
					   data-date-format="yyyy.mm.dd"
					   value="${cm:formatDate(cetProjectPlan.endDate,'yyyy.MM.dd')}"/>
					</div>
			</div>
		</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>培训形式</label>
				<div class="col-xs-6">
					<select required name="type" data-rel="select2"
							data-width="275"
							data-placeholder="请选择">
						<option></option>
						<c:forEach var="_type" items="${CET_PROJECT_PLAN_TYPE_MAP}">
							<option value="${_type.key}">
									${_type.value}
							</option>
						</c:forEach>
					</select>
					<script>
						$("#modalForm select[name=type]").val("${cetProjectPlan.type}");
					</script>
				</div>
			</div>
		<div class="form-group" id="periodDiv" style="display: none">
			<label class="col-xs-3 control-label"><span class="star">*</span>学时</label>
			<div class="col-xs-6">
				<input required class="form-control period" type="text" name="period" value="${cetProjectPlan.period}">
			</div>
		</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
					<textarea class="form-control limited"
							  name="remark">${cetProjectPlan.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${cetProjectPlan!=null}">确定</c:if><c:if test="${cetProjectPlan==null}">添加</c:if></button>
</div>

<script>

	$("#modalForm select[name=type]").change(function () {
		if($(this).val()=='${CET_PROJECT_PLAN_TYPE_WRITE}'){
			$("#periodDiv").show();
			$("#modalForm input[name=period]").prop("required", true)
		}else{
			$("#periodDiv").hide();
			$("#modalForm input[name=period]").prop("required", false)
		}
	}).change();

    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $("#jqGrid2").trigger("reloadGrid");
                    }
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
	$.register.date($('.date-picker'));
	$('textarea.limited').inputlimiter();
</script>