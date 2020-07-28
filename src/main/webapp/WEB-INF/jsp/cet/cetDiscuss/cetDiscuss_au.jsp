<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/cet/constants.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cetDiscuss!=null}">编辑</c:if><c:if test="${cetDiscuss==null}">添加</c:if>分组研讨</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cet/cetDiscuss_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${cetDiscuss.id}">
		<input type="hidden" name="planId" value="${planId}">
		<div class="form-group">
			<label class="col-xs-3 control-label"><span class="star">*</span>开始日期</label>
			<div class="col-xs-6">
				<div class="input-group">
					<input required class="form-control date-picker" name="startDate"
						   type="text"
						   data-date-format="yyyy.mm.dd"
						   value="${cm:formatDate(cetDiscuss.startDate,'yyyy.MM.dd')}"/>
                                            <span class="input-group-addon"> <i
													class="fa fa-calendar bigger-110"></i></span>
				</div>
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label"><span class="star">*</span>结束日期</label>
			<div class="col-xs-6">
				<div class="input-group">
					<input required class="form-control date-picker" name="endDate"
						   type="text"
						   data-date-format="yyyy.mm.dd"
						   value="${cm:formatDate(cetDiscuss.endDate,'yyyy.MM.dd')}"/>
                                            <span class="input-group-addon"> <i
													class="fa fa-calendar bigger-110"></i></span>
				</div>
			</div>
		</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>研讨会名称</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="name" value="${cetDiscuss.name}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>负责单位类型</label>
				<div class="col-xs-6">
					<select required data-rel="select2" name="unitType" data-placeholder="请选择" data-width="272">
						<option></option>
						<c:forEach items="${CET_DISCUSS_UNIT_TYPE_MAP}" var="entity">
						<option value="${entity.key}">${entity.value}</option>
						</c:forEach>
					</select>
					<script type="text/javascript">
						$("#modalForm select[name=unitType]").val(${cetDiscuss.unitType});
					</script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>学时</label>
				<div class="col-xs-6">
                        <input required class="form-control period" type="text" name="period" value="${cetDiscuss.period}">
				</div>
			</div>

			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
                       <textarea class="form-control limited"
								 name="remark">${cetDiscuss.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${cetDiscuss!=null}">确定</c:if><c:if test="${cetDiscuss==null}">添加</c:if></button>
</div>

<script>
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
	$('textarea.limited').inputlimiter();
	$.register.date($('.date-picker'));
    //$("#modalForm :checkbox").bootstrapSwitch();
    $('#modalForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
</script>