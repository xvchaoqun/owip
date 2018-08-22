<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="CET_UPPER_TRAIN_ADD_TYPE_SELF" value="<%=CetConstants.CET_UPPER_TRAIN_ADD_TYPE_SELF%>"/>
<c:set var="CET_UPPER_TRAIN_ADD_TYPE_OW" value="<%=CetConstants.CET_UPPER_TRAIN_ADD_TYPE_OW%>"/>
<c:set var="isAdmin" value="${empty param.id && cm:toByte(param.addType)!=CET_UPPER_TRAIN_ADD_TYPE_SELF}"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cetUpperTrain!=null}">编辑</c:if><c:if test="${cetUpperTrain==null}">添加</c:if>上级调训</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cet/cetUpperTrain_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${cetUpperTrain.id}">
        <input type="hidden" name="type" value="${type}">
        <input type="hidden" name="addType" value="${param.addType}">
		<c:set var="selectWidth" value="223"/>
		<c:if test="${isAdmin}">
		<div class="col-xs-12">
			<div class="col-xs-5">
				<div id="tree3" style="height: 550px"></div>
			</div>
			<div class="col-xs-7">
				</c:if>
	<c:if test="${!isAdmin}">
	<div class="form-group">
		<label class="col-xs-4 control-label">参训人</label>
		<div class="col-xs-6 label-text">
				${cetUpperTrain.user.realname}
		</div>
	</div>
	<c:set var="selectWidth" value="272"/>
	</c:if>
				<div class="form-group">
					<label class="col-xs-4 control-label">培训班主办方</label>
					<div class="col-xs-6">
						<select required data-rel="select2" name="organizer" data-placeholder="请选择" data-width="${selectWidth}">
							<option></option>
							<c:import url="/metaTypes?__code=mc_cet_upper_train_organizer"/>
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
					<label class="col-xs-4 control-label">培训班类型</label>
					<div class="col-xs-6">
						<select required data-rel="select2" name="trainType" data-placeholder="请选择" data-width="${selectWidth}">
							<option></option>
							<c:import url="/metaTypes?__code=mc_cet_upper_train_type"/>
						</select>
						<script type="text/javascript">
							$("#modalForm select[name=trainType]").val(${cetUpperTrain.trainType});
						</script>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-4 control-label">专项培训班</label>
					<div class="col-xs-6">
						<select required data-rel="select2" name="specialType" data-placeholder="请选择" data-width="${selectWidth}">
							<option></option>
							<c:import url="/metaTypes?__code=mc_cet_upper_train_special"/>
							<option value="0">无</option>
						</select>
						<script type="text/javascript">
							$("#modalForm select[name=specialType]").val(${cetUpperTrain.specialType});
						</script>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-4 control-label">培训班名称</label>
					<div class="col-xs-6">
						<textarea required class="form-control noEnter" rows="3"
								  name="trainName">${cetUpperTrain.trainName}</textarea>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-4 control-label">培训开始时间</label>
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
					<label class="col-xs-4 control-label">培训结束时间</label>
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
					<label class="col-xs-4 control-label">培训学时</label>
					<div class="col-xs-6">
						<input required class="form-control period" type="text" name="period" value="${cetUpperTrain.period}">
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-4 control-label">培训地点</label>
					<div class="col-xs-6">
						<input required class="form-control" type="text" name="address" value="${cetUpperTrain.address}">
					</div>
				</div>

				<div class="form-group">
					<label class="col-xs-4 control-label">是否计入年度学习任务</label>
					<div class="col-xs-6">
						<input type="checkbox" class="big" name="isValid" ${cetUpperTrain.isValid?"checked":""}/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-4 control-label">备注</label>
					<div class="col-xs-6">
					 <textarea class="form-control limited" rows="2"
							   name="remark">${cetUpperTrain.remark}</textarea>
					</div>
				</div>
<c:if test="${isAdmin}">
			</div>
		</div>
	</c:if>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${cetUpperTrain!=null}">确定</c:if><c:if test="${cetUpperTrain==null}">添加</c:if></button>
</div>
<script>
	function organizerChange(){
		var $organizer = $("select[name=organizer]");
		if($organizer.val()=='0'){
			$("#otherOrganizerDiv").show();
			$("input[name=otherOrganizer]").prop("disabled", false).attr("required", "required");
		}else{
			$("#otherOrganizerDiv").hide();
			$("input[name=otherOrganizer]").val('').prop("disabled", true).removeAttr("required");
		}
	}
	$("select[name=organizer]").change(function(){
		organizerChange();
	});
	organizerChange();

	<c:if test="${isAdmin}">
	$.getJSON("${ctx}/cet/cetUpperTrain_selectCadres_tree",{addType:${param.addType}},function(data){
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

		<c:if test="${isAdmin}">
		userIds = $.map($("#tree3").dynatree("getSelectedNodes"), function(node){
			if(!node.data.isFolder && !node.data.hideCheckbox)
				return node.data.key;
		});
		if(userIds.length==0){
			$.tip({
				$target: $("#tree3"),
				at: 'top center', my: 'bottom center',
				msg: "请选择参训人员"
			});
		}
		</c:if>

		$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
			<c:if test="${isAdmin}">
			if(userIds.length==0){
				return;
			}
			</c:if>

			$(form).ajaxSubmit({
				data:{userIds:userIds},
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $("#jqGrid").trigger("reloadGrid");
                    }
                }
            });
        }
    });
    $("#modalForm :checkbox").bootstrapSwitch();
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    $('#modalForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    $('textarea.limited').inputlimiter();
    $.register.date($('.date-picker'));
</script>