<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="CET_UPPER_TRAIN_UPPER" value="<%=CetConstants.CET_UPPER_TRAIN_UPPER%>"/>
<c:set var="CET_UPPER_TRAIN_ADD_TYPE_SELF" value="<%=CetConstants.CET_UPPER_TRAIN_ADD_TYPE_SELF%>"/>
<c:set var="CET_UPPER_TRAIN_ADD_TYPE_OW" value="<%=CetConstants.CET_UPPER_TRAIN_ADD_TYPE_OW%>"/>
<c:set var="CET_UPPER_TRAIN_ADD_TYPE_UNIT" value="<%=CetConstants.CET_UPPER_TRAIN_ADD_TYPE_UNIT%>"/>
<c:set var="CET_UPPER_TRAIN_STATUS_INIT" value="<%=CetConstants.CET_UPPER_TRAIN_STATUS_INIT%>"/>
<c:set var="CET_UPPER_TRAIN_STATUS_UNPASS" value="<%=CetConstants.CET_UPPER_TRAIN_STATUS_UNPASS%>"/>
<c:set var="isMultiSelect" value="${empty param.id && addType!=CET_UPPER_TRAIN_ADD_TYPE_SELF}"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>
		<c:if test="${param.check==1}">审批</c:if>
		<c:if test="${param.check!=1}">
			<c:if test="${cetUpperTrain!=null}">编辑</c:if><c:if test="${cetUpperTrain==null}">添加</c:if>
		</c:if>
	</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cet/cetUpperTrain_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${cetUpperTrain.id}">
        <input type="hidden" name="upperType" value="${upperType}">
		<c:if test="${addType==CET_UPPER_TRAIN_ADD_TYPE_SELF}">
        <input type="hidden" name="userId" value="${_user.id}">
		</c:if>
        <input type="hidden" name="addType" value="${addType}">
		<c:set var="selectWidth" value="${isMultiSelect?223:272}"/>
		<c:if test="${isMultiSelect}">
		<div class="row col-xs-12">
			<div class="col-xs-5">
				<div id="tree3" style="height: 535px"></div>
			</div>
			<div class="col-xs-7">
		</c:if>
	<c:if test="${!isMultiSelect && addType!=CET_UPPER_TRAIN_ADD_TYPE_SELF}">
	<div class="form-group">
		<label class="col-xs-4 control-label">参训人</label>
		<div class="col-xs-6 label-text">
				${cetUpperTrain.user.realname}
		</div>
	</div>
	</c:if>
			<div class="form-group">
				<label class="col-xs-4 control-label"><span class="star">*</span>年度</label>
				<div class="col-xs-6">
					<div class="input-group" style="width: 100px">
						<input required class="form-control date-picker" placeholder="请选择年份"
							   name="year"
							   type="text"
							   data-date-format="yyyy" data-date-min-view-mode="2"
							   value="${empty cetUpperTrain.year?_thisYear:cetUpperTrain.year}"/>
							<span class="input-group-addon"> <i
									class="fa fa-calendar bigger-110"></i></span>
					</div>
				</div>
			</div>
			<div class="form-group">
					<label class="col-xs-4 control-label"><span class="star">*</span>培训班名称</label>
					<div class="col-xs-6">
						<textarea required class="form-control noEnter" rows="2"
								  name="trainName">${cetUpperTrain.trainName}</textarea>
					</div>
				</div>
			<div class="form-group">
					<label class="col-xs-4 control-label"><span class="star">*</span>培训班主办方</label>
					<div class="col-xs-6">
						<select required data-rel="select2" name="organizer" data-placeholder="请选择" data-width="${selectWidth}">
							<option></option>
							<c:import url="/metaTypes?__code=mc_cet_upper_train_organizer${upperType==CET_UPPER_TRAIN_UPPER?'':'2'}"/>
							<option value="0">其他</option>
						</select>
						<script type="text/javascript">
							$("#modalForm select[name=organizer]").val(${cetUpperTrain.organizer});
						</script>
					</div>
				</div>
				<div class="form-group" id="otherOrganizerDiv" style="display: none">
					<div class="col-xs-offset-4 col-xs-6">
						<input class="form-control" type="text" name="otherOrganizer" value="${cetUpperTrain.otherOrganizer}">
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-4 control-label"><span class="star">*</span>培训班类型</label>
					<div class="col-xs-6">
						<select required data-rel="select2" name="trainType" data-placeholder="请选择" data-width="${selectWidth}">
							<option></option>
							<c:import url="/metaTypes?__code=mc_cet_upper_train_type${upperType==CET_UPPER_TRAIN_UPPER?'':'2'}"/>
						</select>
						<script type="text/javascript">
							$("#modalForm select[name=trainType]").val(${cetUpperTrain.trainType});
						</script>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-4 control-label"><span class="star">*</span>培训开始时间</label>
					<div class="col-xs-6">
						<div class="input-group">
							<input required class="form-control date-picker" name="startDate"
								   type="text" autocomplete="off" disableautocomplete
								   data-date-format="yyyy-mm-dd"
								   value="${cm:formatDate(cetUpperTrain.startDate,'yyyy-MM-dd')}"/>
                                            <span class="input-group-addon"> <i
													class="fa fa-calendar bigger-110"></i></span>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-4 control-label"><span class="star">*</span>培训结束时间</label>
					<div class="col-xs-6">
						<div class="input-group">
							<input required class="form-control date-picker" name="endDate"
								   type="text" autocomplete="off" disableautocomplete
								   data-date-format="yyyy-mm-dd"
								   value="${cm:formatDate(cetUpperTrain.endDate,'yyyy-MM-dd')}"/>
                                            <span class="input-group-addon"> <i
													class="fa fa-calendar bigger-110"></i></span>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-4 control-label"><span class="star">*</span>培训学时</label>
					<div class="col-xs-6">
						<input required class="form-control period" type="text" name="period" value="${cetUpperTrain.period}">
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-4 control-label"><span class="star">*</span>培训地点</label>
					<div class="col-xs-6">
						<input required class="form-control" type="text" name="address" value="${cetUpperTrain.address}">
					</div>
				</div>

				<c:if test="${addType==CET_UPPER_TRAIN_ADD_TYPE_SELF}">
				<div class="form-group">
					<label class="col-xs-4 control-label">培训总结</label>
					<div class="col-xs-6">
						<input class="form-control" type="file" name="_word"/>
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-offset-4 col-xs-6">
						<input class="form-control" type="file" name="_pdf"/>
					</div>
				</div>
				</c:if>
				<c:if test="${param.addType!=CET_UPPER_TRAIN_ADD_TYPE_UNIT}">
				<div class="form-group">
					<label class="col-xs-4 control-label"><span class="star">*</span>${upperType==CET_UPPER_TRAIN_UPPER?'派出':'组织'}单位</label>
					<div class="col-xs-8">
						<div class="input-group">
							<div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
								<input required type="radio" name="type" id="type0" value="0">
								<label for="type0">
									党委组织部
								</label>
							</div>
							<div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
								<input required type="radio" name="type" id="type1" value="1">
								<label for="type1">
									其他部门${upperType==CET_UPPER_TRAIN_UPPER?'派出':'组织'}
								</label>
							</div>
						</div>
					</div>
				</div>
				<div class="form-group" id="unitDiv"
					 style="display: ${cetUpperTrain.type?'block':'none'}">
					<div class="col-xs-offset-4 col-xs-6">
						<select ${cetUpperTrain.type?'required':''}
								data-rel="select2" data-width="${selectWidth}" name="unitId" data-placeholder="请选择单位">
							<option></option>
							<c:forEach var="unit" items="${upperUnits}">
								<option value="${unit.id}">${unit.name}</option>
							</c:forEach>
						</select>
						<script>
							$("#modalForm select[name=unitId]").val('${cetUpperTrain.unitId}')
						</script>
					</div>
				</div>
				</c:if>
				<c:if test="${param.addType==CET_UPPER_TRAIN_ADD_TYPE_UNIT}">
				<div class="form-group">
					<label class="col-xs-4 control-label"><span class="star">*</span>${upperType==CET_UPPER_TRAIN_UPPER?'派出':'组织'}单位</label>
					<div class="col-xs-8">
						<input type="hidden" name="type" value="1">

						<select required data-rel="select2" data-width="${selectWidth}" name="unitId" data-placeholder="请选择派出单位">
							<option></option>
							<c:forEach var="unit" items="${upperUnits}">
								<option value="${unit.id}">${unit.name}</option>
							</c:forEach>
						</select>
						<script>
							$("#modalForm select[name=unitId]").val('${cetUpperTrain.unitId}')
						</script>
					</div>
				</div>
				</c:if>
				<div class="form-group">
					<label class="col-xs-4 control-label">备注</label>
					<div class="col-xs-6">
					 <textarea class="form-control limited" rows="2"
							   name="remark">${cetUpperTrain.remark}</textarea>
					</div>
				</div>
			<c:if test="${addType==CET_UPPER_TRAIN_ADD_TYPE_OW}">
				<div class="form-group">
					<label class="col-xs-4 control-label">是否计入年度学习任务</label>
					<div class="col-xs-6">
						<input type="checkbox" class="big" name="isValid" ${cetUpperTrain.isValid?"checked":""}/>
					</div>
				</div>
			</c:if>
			<c:if test="${param.check==1}">
				<input type="hidden" name="check" value="1">
			</c:if>
			<c:if test="${param.check==1 && cetUpperTrain.status==CET_UPPER_TRAIN_STATUS_INIT}">
			<div class="form-group">
				<label class="col-xs-4 control-label"><span class="star">*</span>审批结果</label>
				<div class="col-xs-6">
					<div class="input-group">
						<div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
							<input required type="radio" name="status" id="status1" value="1">
							<label for="status1">
								通过
							</label>
						</div>
						<div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
							<input required type="radio" name="status" id="status2" value="2">
							<label for="status2">
								不通过
							</label>
						</div>
					</div>
				</div>
			</div>
			<div class="form-group" id="backReasonDiv" style="display: none">
				<label class="col-xs-4 control-label">不通过原因</label>
				<div class="col-xs-6">
					 <textarea class="form-control limited" rows="2" name="backReason"></textarea>
				</div>
			</div>
			</c:if>
		<c:if test="${isMultiSelect}">
				</div>
			</div>
		</c:if>
    </form>
</div>
<div class="modal-footer" style="clear: both">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
	<button id="submitBtn" class="btn btn-primary"
			data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口">
		<i class="fa fa-check"></i>
		<c:if test="${cetUpperTrain!=null}">${cetUpperTrain.status==CET_UPPER_TRAIN_STATUS_UNPASS?'重新提交':'确定'}</c:if>
		<c:if test="${cetUpperTrain==null}">添加</c:if></button>
</div>
<style>
	.modal-body{
		max-height: inherit!important;
	}
</style>
<script>
	<c:if test="${addType==CET_UPPER_TRAIN_ADD_TYPE_SELF}">
	$.fileInput($("#modalForm input[name=_word]"),{
		no_file: '请上传word文件...',
		allowExt: ['doc', 'docx'],
		allowMime: ['application/msword','application/vnd.openxmlformats-officedocument.wordprocessingml.document']
	});
	$.fileInput($("#modalForm input[name=_pdf]"),{
		no_file: '请上传pdf文件...',
		allowExt: ['pdf'],
		allowMime: ['application/pdf']
	});
	</c:if>
	$("#modalForm input[name=type]").click(function(){
		if($(this).val()=='1'){
			$("#unitDiv").show();
			$("#modalForm select[name=unitId]").prop("disabled", false).attr("required", "required");
		}else{
			$("#unitDiv").hide();
			$("#modalForm select[name=unitId]").val(null).trigger("change").prop("disabled", true).removeAttr("required");
		}
	});
	<c:if test="${not empty cetUpperTrain.id}">
	$("#modalForm input[name=type][value=${cetUpperTrain.type?1:0}]").prop("checked", true);
	</c:if>
	<c:if test="${empty cetUpperTrain.id && not empty param.type}">
	$("#modalForm input[name=type][value=${param.type}]").click();
	</c:if>
	$("#modalForm input[name=status]").click(function(){

		if($(this).val()=='2'){
			$("#backReasonDiv").show();
			$("#modalForm textarea[name=backReason]").prop("disabled", false).attr("required", "required");
		}else{
			$("#backReasonDiv").hide();
			$("#modalForm textarea[name=backReason]").val('').prop("disabled", true).removeAttr("required");
		}
	});


	function organizerChange(){
		var $organizer = $("#modalForm select[name=organizer]");
		if($organizer.val()=='0'){
			$("#otherOrganizerDiv").show();
			$("#modalForm input[name=otherOrganizer]").prop("disabled", false).attr("required", "required");
		}else{
			$("#otherOrganizerDiv").hide();
			$("#modalForm input[name=otherOrganizer]").val('').prop("disabled", true).removeAttr("required");
		}
	}
	$("#modalForm select[name=organizer]").change(function(){
		organizerChange();
	});
	organizerChange();

	<c:if test="${isMultiSelect}">
	$.getJSON("${ctx}/cet/cetUpperTrain_selectCadres_tree",{addType:${addType}, upperType:${upperType}},function(data){
		var treeData = data.tree;
		treeData.title="选择参训人员"
		$("#tree3").dynatree({
			checkbox: true,
			selectMode: 3,
			children: treeData,
			onSelect: function(select, node) {

				node.expand(node.data.isFolder && node.isSelected());
			},
			cookieId: "dynatree-Cb3",
			idPrefix: "dynatree-Cb3-"
		});
	});
	</c:if>

	var userIds=[];
    $("#submitBtn").click(function(){

		<c:if test="${isMultiSelect}">
		userIds = $.map($("#tree3").dynatree("getSelectedNodes"), function(node){
			if(!node.data.isFolder && !node.data.hideCheckbox)
				return node.data.key;
		});
		if(userIds.length==0){
			$.tip({
				$target: $("#tree3"),
				at: 'bottom center', my: 'top center',
				msg: "请选择参训人员"
			});
		}
		</c:if>

		$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
			<c:if test="${isMultiSelect}">
			if(userIds.length==0){
				return;
			}
			</c:if>
			var $btn = $("#submitBtn").button('loading');
			$(form).ajaxSubmit({
				data:{userIds:userIds},
                success:function(ret){
                    if(ret.success){
						<c:choose>
						<c:when test="${addType==CET_UPPER_TRAIN_ADD_TYPE_SELF
						&& (empty cetUpperTrain.id||cetUpperTrain.status==CET_UPPER_TRAIN_STATUS_UNPASS)}">
						$.loadPage({url:'${ctx}/user/cet/cetUpperTrain?cls=2&upperType=${upperType}'})
						</c:when>
						<c:otherwise>
						$("#modal").modal('hide');
						$("#jqGrid").trigger("reloadGrid");
						</c:otherwise>
						</c:choose>
                    }
					$btn.button('reset');
                }
            });
        }
    });
    $("#modalForm input[name=isValid]").bootstrapSwitch();
    $.register.ajax_select($('[data-rel="select2-ajax"]'));
    $('#modalForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    $('textarea.limited').inputlimiter();
    $.register.date($('.date-picker'));
</script>