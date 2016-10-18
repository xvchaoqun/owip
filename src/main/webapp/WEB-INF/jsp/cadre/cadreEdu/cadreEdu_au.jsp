<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
	<button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
	<h3><c:if test="${cadreEdu!=null}">编辑</c:if><c:if test="${cadreEdu==null}">添加</c:if>学习经历</h3>
</div>
<div class="modal-body">
	<form class="form-horizontal" action="${ctx}/cadreEdu_au" id="modalForm" method="post" enctype="multipart/form-data">
		<div class="row">
			<div class="col-xs-5">
				<input type="hidden" name="id" value="${cadreEdu.id}">
				<input type="hidden" name="cadreId" value="${cadre.id}">
				<div class="form-group">
					<label class="col-xs-5 control-label">学历</label>
					<div class="col-xs-6">
						<select required data-rel="select2" name="eduId"
								data-placeholder="请选择" data-width="162">
							<option></option>
							<c:import url="/metaTypes?__code=mc_edu"/>
						</select>
						<script type="text/javascript">
							$("#modal form select[name=eduId]").val(${cadreEdu.eduId});
						</script>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-5 control-label">毕业/在读</label>
					<div class="col-xs-6 label-text"  style="font-size: 15px;">
						<input type="checkbox" class="big" name="isGraduated" ${cadreEdu.isGraduated?"checked":""} data-off-text="在读" data-on-text="毕业"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-5 control-label">是否最高学历</label>
					<div class="col-xs-6">
						<label>
							<input name="isHighEdu" ${cadreEdu.isHighEdu?"checked":""}  type="checkbox" />
							<span class="lbl"></span>
						</label>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-5 control-label">毕业/在读学校</label>
					<div class="col-xs-6">
						<input required class="form-control" type="text" name="school" value="${cadreEdu.school}">
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-5 control-label">院系</label>
					<div class="col-xs-6">
						<input required class="form-control" type="text" name="dep" value="${cadreEdu.dep}">
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-5 control-label">所学专业</label>
					<div class="col-xs-6">
						<input required class="form-control" type="text" name="major" value="${cadreEdu.major}">
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-5 control-label">学校类型</label>
					<div class="col-xs-6">
						<select required data-rel="select2" name="schoolType"
								data-placeholder="请选择" data-width="162">
							<option></option>
							<c:forEach items="${CADRE_SCHOOL_TYPE_MAP}" var="schoolType">
								<option value="${schoolType.key}">${schoolType.value}</option>
							</c:forEach>
						</select>
						<script type="text/javascript">
							$("#modal form select[name=schoolType]").val(${cadreEdu.schoolType});
						</script>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-5 control-label">入学时间</label>
					<div class="col-xs-6">
						<div class="input-group">
							<input required class="form-control date-picker" name="_enrolTime" type="text"
								   data-date-min-view-mode="1"
								   data-date-format="yyyy.mm" value="${cm:formatDate(cadreEdu.enrolTime,'yyyy.MM')}" />
							<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-5 control-label">毕业时间</label>
					<div class="col-xs-6">
						<div class="input-group">
							<input required class="form-control date-picker" name="_finishTime" type="text"
								   data-date-min-view-mode="1"
								   data-date-format="yyyy.mm" value="${cm:formatDate(cadreEdu.finishTime,'yyyy.MM')}" />
							<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
						</div>
					</div>
				</div>

				<div class="form-group">
					<label class="col-xs-5 control-label">学制</label>
					<div class="col-xs-6">
						<input  class="form-control required digits" type="text" name="schoolLen" value="${cadreEdu.schoolLen}">
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-5 control-label">学习方式</label>
					<div class="col-xs-6">
						<select required data-rel="select2" name="learnStyle"
								data-placeholder="请选择" data-width="162">
							<option></option>
							<c:import url="/metaTypes?__code=mc_learn_style"/>
						</select>
						<script type="text/javascript">
							$("#modal form select[name=learnStyle]").val(${cadreEdu.learnStyle});
						</script>
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="col-xs-4 control-label">是否获得学位</label>
					<div class="col-xs-6">
						<label>
							<input name="hasDegree" ${cadreEdu.hasDegree?"checked":""}  type="checkbox" />
							<span class="lbl"></span>
						</label>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-4 control-label">学位</label>
					<div class="col-xs-8">
						<input disabled class="form-control" placeholder="例子：教育学博士、管理学硕士" type="text" name="degree" value="${cadreEdu.degree}">
					</div>
				</div>

					<div class="form-group">
						<label class="col-xs-4 control-label">是否为最高学位</label>
						<div class="col-xs-6">
							<label>
								<input name="isHighDegree" ${cadreEdu.isHighDegree?"checked":""}  type="checkbox" />
								<span class="lbl"></span>
							</label>
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-4 control-label">学位授予国家</label>
						<div class="col-xs-6">
							<input class="form-control" type="text" name="degreeCountry" value="${cadreEdu.degreeCountry}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-6 control-label">学位授予单位</label>
						<div class="col-xs-6">
							<input required class="form-control" type="text" name="degreeUnit" value="${cadreEdu.degreeUnit}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-6 control-label">学位授予日期</label>
						<div class="col-xs-6">
							<div class="input-group">
								<input  required class="form-control date-picker" name="_degreeTime" type="text"
										data-date-min-view-mode="1"
										data-date-format="yyyy.mm" value="${cm:formatDate(cadreEdu.degreeTime,'yyyy.MM')}" />
								<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-6 control-label">导师姓名</label>
						<div class="col-xs-6">
							<input class="form-control" type="text" name="tutorName" value="${cadreEdu.tutorName}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-6 control-label">导师所在单位及职务（职称）</label>
						<div class="col-xs-6">
							<input class="form-control" type="text" name="tutorTitle" value="${cadreEdu.tutorTitle}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-6 control-label">学历学位证书</label>
						<div class="col-xs-6 file">
							<div class="files">
								<input class="form-control" type="file" name="_files[]"/>
								<input class="form-control" type="file" name="_files[]" />
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-6 control-label">备注</label>
						<div class="col-xs-6">
							<textarea class="form-control" name="remark" rows="5">${cadreEdu.remark}</textarea>
						</div>
					</div>
				</div></div>
	</form>
</div>
<div class="modal-footer">
	<a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
	<input type="submit" class="btn btn-primary" value="<c:if test="${cadreEdu!=null}">确定</c:if><c:if test="${cadreEdu==null}">添加</c:if>"/>
</div>

<script>
	$('#modalForm input[type=file]').ace_file_input({
		no_file:'请选择证书图片 ...',
		btn_choose:'选择',
		btn_change:'更改',
		droppable:false,
		onchange:null,
		thumbnail:false, //| true | large
		allowExt: ['jpg', 'jpeg', 'png', 'gif'],
		allowMime: ['image/jpg', 'image/jpeg', 'image/png', 'image/gif']
	});
	$("#modal :checkbox").bootstrapSwitch();
	function hasDegreeChange(){
		if($("input[name=hasDegree]").bootstrapSwitch("state")){
			$("input[name=degree]").prop("disabled", false).attr("required", "required");
			$("input[name=isHighDegree]").bootstrapSwitch('disabled', false);
			$("input[name=degreeCountry]").val('${cadreEdu.degreeCountry}').prop("disabled", false).attr("required", "required");
			$("input[name=degreeUnit]").val('${cadreEdu.degreeUnit}').prop("disabled", false).attr("required", "required");

			var finishTime = $("input[name=_finishTime]").val();
			//alert(finishTime)
			var degreeTime = $.trim(finishTime)==''?'':finishTime.format("yyyy.MM");
			$("input[name=_degreeTime]").val(degreeTime)
					.prop("disabled", false).attr("required", "required");

			$("input[name=school]").trigger("keyup");
		}else{
			$("input[name=degree]").val('').prop("disabled", true).removeAttr("required");
			$("input[name=isHighDegree]").bootstrapSwitch('state', false).bootstrapSwitch('disabled', true);
			$("input[name=degreeCountry]").val('').prop("disabled", true).removeAttr("required");
			$("input[name=degreeUnit]").val('').prop("disabled", true).removeAttr("required");
			$("input[name=_degreeTime]").val('').prop("disabled", true).removeAttr("required");
		}
	}
	$('input[name=hasDegree]').on('switchChange.bootstrapSwitch', function(event, state) {
		/*console.log(this); // DOM element
		 console.log(event); // jQuery event
		 console.log(state); // true | false*/
		hasDegreeChange();
	});

	function schoolTypeChange(){
		if($("input[name=hasDegree]").bootstrapSwitch("state")){
			var $schoolType = $("select[name=schoolType]");
			if($schoolType.val()=='${CADRE_SCHOOL_TYPE_THIS_SCHOOL}' || $schoolType.val()=='${CADRE_SCHOOL_TYPE_DOMESTIC}'){
				$("input[name=degreeCountry]").val('中国').prop("disabled", true).removeAttr("required");
			}else{
				$("input[name=degreeCountry]").prop("disabled", false).attr("required", "required");
			}
		}
	}
	$("select[name=schoolType]").change(function(){
		schoolTypeChange();
	});
	schoolTypeChange();

	hasDegreeChange();

	function isGraduatedChange(){
		if(!$("input[name=isGraduated]").bootstrapSwitch("state")) {
			$("input[name=hasDegree]").bootstrapSwitch("state", false).bootstrapSwitch('disabled', true);
			$("input[name='_files[]']").prop("disabled", true);

			$("input[name=_finishTime]").val('').prop("disabled", true).removeAttr("required");
			$("input[name=schoolLen]").val('').prop("disabled", true).removeAttr("required");

		}else {
			$("input[name=hasDegree]").bootstrapSwitch('disabled', false);
			$("input[name='_files[]']").prop("disabled", false);

			$("input[name=_finishTime]").prop("disabled", false).attr("required", "required");
			$("input[name=schoolLen]").prop("disabled", false).attr("required", "required");
		}
	}
	$('input[name=isGraduated]').on('switchChange.bootstrapSwitch', function(event, state) {
		isGraduatedChange();
	});
	isGraduatedChange();

	function eduIdChange(){
		var $eduId = $("select[name=eduId]");
		if($eduId.val()=="${cm:getMetaTypeByCode("mt_edu_master").id}" || $eduId.val()=="${cm:getMetaTypeByCode("mt_edu_doctor").id}"){
			$("input[name=tutorName]").prop("disabled", false);
			$("input[name=tutorTitle]").prop("disabled", false);
		}else{
			$("input[name=tutorName]").val('').prop("disabled", true);
			$("input[name=tutorTitle]").val('').prop("disabled", true);
		}
	}
	$("select[name=eduId]").change(function(){
		eduIdChange();
	});
	eduIdChange();

	$("input[name=school]").keyup(function(){
		console.log($("input[name=hasDegree]").bootstrapSwitch("state"));
		if($("input[name=hasDegree]").bootstrapSwitch("state")) {
			var $degreeUnit = $("input[name=degreeUnit]");
			if ($degreeUnit.val() == '' || $(this).val().startWith($degreeUnit.val())) {
				$degreeUnit.val($(this).val());
			}
		}
	});

	$("input[name=_finishTime]").on('changeDate ',function(ev){

		if($("input[name=hasDegree]").bootstrapSwitch("state")) {
			var $_degreeTime = $("input[name=_degreeTime]");
			if ($_degreeTime.val() == '') {
				$_degreeTime.val(ev.date.format("yyyy.MM"));
			}
		}
	});

	register_date($('.date-picker'));

	$("#modal form").validate({
		submitHandler: function (form) {
			$(form).ajaxSubmit({
				success:function(ret){
					if(ret.success){
						$("#modal").modal("hide");
						$("#jqGrid_cadreEdu").trigger("reloadGrid");
					}
				}
			});
		}
	});
	$('#modalForm [data-rel="select2"]').select2();
	$('[data-rel="tooltip"]').tooltip();
</script>