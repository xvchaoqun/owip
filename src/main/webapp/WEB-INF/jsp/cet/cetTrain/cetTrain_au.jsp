<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cetTrain!=null}">编辑</c:if><c:if test="${cetTrain==null}">添加</c:if>培训班</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cet/cetTrain_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${cetTrain.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">年度</label>
				<div class="col-xs-6">
					<div class="input-group">
						<input required class="form-control date-picker" placeholder="请选择年份"
							   name="year"
							   type="text"
							   data-date-format="yyyy" data-date-min-view-mode="2"
							   value="${cetTrain.year}"/>
                                            <span class="input-group-addon"> <i
													class="fa fa-calendar bigger-110"></i></span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">编号</label>
				<div class="col-xs-6">
                        <input required class="form-control num" type="text" name="num" value="${cetTrain.num}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">培训班名称</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="name" value="${cetTrain.name}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">培训主题</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="subject" value="${cetTrain.subject}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">参训人员类型模板</label>
				<div class="col-xs-6">
					<select required name="templateId" data-rel="select2"
							data-width="275"
							data-placeholder="请选择">
						<option></option>
						<c:forEach var="template" items="${CET_TRAINEE_TYPE_TEMPLATE_MAP}">
							<option value="${template.key}">
									${template.value}
							</option>
						</c:forEach>
					</select>
					<script>
						$("#modalForm select[name=templateId]").val("${cetTrain.templateId}");
					</script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">参训人员类型</label>
				<div class="col-xs-6 label-text" id="traineeTypeDiv">

				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">开课日期</label>
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
				<label class="col-xs-3 control-label">结课日期</label>
				<div class="col-xs-6">
					<div class="input-group">
						<input required class="form-control date-picker" name="endDate"
							   type="text"
							   data-date-format="yyyy-mm-dd"
							   value="${cm:formatDate(cetTrain.endDate,'yyyy-MM-dd')}"/>
                                            <span class="input-group-addon"> <i
													class="fa fa-calendar bigger-110"></i></span>
					</div>
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
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${cetTrain!=null}">确定</c:if><c:if test="${cetTrain==null}">添加</c:if></button>
</div>
<script type="text/template" id="traineeType_tpl">
	{{_.each(traineeTypes, function(t, idx){ }}
	<label>
		<input name="traineeTypeIds[]" {{=($.inArray(t.id, traineeTypeIds)>=0?'checked':'')}} type="checkbox" value="{{=t.id}}">{{=t.name}}
		<span class="lbl"></span>
	</label>
	{{});}}
</script>
<script>

	var templateMap = ${cm:toJSONObject(templateMap)};
	var traineeTypeIds = ${cm:toJSONArray(traineeTypeIds)};

	$("#modal select[name=templateId]").change(function(){
		var html = "";
		var templateId = $(this).val();
		if(templateId>0) {
			var traineeTypes = templateMap[templateId];
			html = _.template($("#traineeType_tpl").html().NoMultiSpace())({traineeTypes: traineeTypes,
				traineeTypeIds:traineeTypeIds});
		}

		$("#traineeTypeDiv").html(html);
	}).change();

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

	register_date($('.date-picker'));
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>