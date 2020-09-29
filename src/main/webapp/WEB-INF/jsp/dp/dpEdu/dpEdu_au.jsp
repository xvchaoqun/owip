<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="_dpEdu_needSubject" value="${_pMap['dpEdu_needSubject']=='true'}"/>
<c:set var="_dpEdu_needCertificate" value="${_pMap['dpEdu_needCertificate']=='true'}"/>

<div class="modal-header">
	<button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
	<h3><c:if test="${dpEdu!=null}">编辑</c:if><c:if test="${dpEdu==null}">添加</c:if>学习经历（${uv.realname}）
</div>
<div class="modal-body">
	<form class="form-horizontal" action="${ctx}/dp/dpEdu_au?userId=${uv.userId}" autocomplete="off" disableautocomplete id="modalForm" method="post" enctype="multipart/form-data">
		<div class="row">
			<div class="col-xs-5">
				<input type="hidden" name="id" value="${dpEdu.id}">
				<div class="form-group">
					<label class="col-xs-5 control-label">学历
					<span class="prompt" data-title="说明"
							  data-prompt="注：如未取得学历证书请留空"><i class="fa fa-question-circle-o"></i></span>
					</label>
					<div class="col-xs-7">
						<select data-rel="select2" name="eduId"
								data-placeholder="请选择" data-width="193">
							<option></option>
							<c:import url="/metaTypes?__id=${cm:getMetaClassByCode('mc_edu').id}"/>
						</select>
						<script type="text/javascript">
							$("#modal form select[name=eduId]").val(${dpEdu.eduId});
						</script>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-5 control-label"><span class="star">*</span>入学时间</label>
					<div class="col-xs-7">
						<div class="input-group date" data-date-min-view-mode="1" data-date-format="yyyy.mm" style="width: 120px">
							<input required class="form-control" name="enrolTime" type="text"
								    placeholder="yyyy.mm" value="${cm:formatDate(dpEdu.enrolTime,'yyyy.MM')}" />
							<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-5 control-label"><span class="star">*</span>毕业时间</label>
					<div class="col-xs-7">
						<div class="input-group date" data-date-min-view-mode="1" data-date-format="yyyy.mm" style="width: 120px">
							<input required class="form-control" name="finishTime" type="text"
								    placeholder="yyyy.mm" value="${cm:formatDate(dpEdu.finishTime,'yyyy.MM')}" />
							<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-5 control-label">毕业/在读</label>
					<div class="col-xs-7">
						<input type="checkbox" class="big" name="isGraduated" ${(dpEdu==null ||dpEdu.isGraduated)?"checked":""} data-off-text="在读" data-on-text="毕业"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-5 control-label">是否最高学历</label>
					<div class="col-xs-7">
						<label>
							<input name="isHighEdu" ${dpEdu.isHighEdu?"checked":""}  type="checkbox" />
							<span class="lbl"></span>
						</label>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-5 control-label"><span class="star">*</span>毕业/在读学校</label>
					<div class="col-xs-7">
						<textarea required class="form-control" name="school">${dpEdu.school}</textarea>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-5 control-label"><span class="star">*</span>院系</label>
					<div class="col-xs-7">
						<input required class="form-control" type="text" name="dep" value="${dpEdu.dep}">
					</div>
				</div>
				<c:if test="${_dpEdu_needSubject}">
				<div class="form-group" id="firstDiv">
					<label class="col-xs-5 control-label"><span class="star">*</span>学科门类</label>
					<div class="col-xs-7">
						<select required data-rel="select2" name="subject"
								data-placeholder="请选择" data-width="193">
							<option></option>
						</select>
					</div>
				</div>
				<div class="form-group"  id="secondDiv">
					<label class="col-xs-5 control-label"><span class="star">*</span>一级学科</label>
					<div class="col-xs-7">
						<select required data-rel="select2" name="firstSubject"
								data-placeholder="请选择" data-width="193">
							<option></option>
						</select>
					</div>
				</div>
					<script>
						$.register.layer_type_select("firstDiv",
								"secondDiv", ${cm:toJSONArrayWithFilter(cm:getLayerTypes("lt_subject"), "id,name,children,children.id,children.name")}
								, '${dpEdu.subject}', '${dpEdu.firstSubject}');
					</script>
				</c:if>
				<div class="form-group">
					<label class="col-xs-5 control-label"><span class="star">*</span>所学专业</label>
					<div class="col-xs-7">
						<input required class="form-control" type="text" name="major" value="${dpEdu.major}">
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-5 control-label"><span class="star">*</span>学校类型</label>
					<div class="col-xs-7">
						<select required data-rel="select2" name="schoolType"
								data-placeholder="请选择" data-width="193">
							<option></option>
							<c:forEach items="<%=CadreConstants.CADRE_SCHOOL_TYPE_MAP%>" var="schoolType">
								<option value="${schoolType.key}">${schoolType.value}</option>
							</c:forEach>
						</select>
						<script type="text/javascript">
							$("#modal form select[name=schoolType]").val(${dpEdu.schoolType});
						</script>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-5 control-label"><span class="star">*</span>学习方式
					<shiro:hasPermission name="cadre:updateWithoutRequired">
							<span class="prompt" data-title="说明"
							  data-prompt="注：学习方式为空时，不计入任免审批表和信息采集表"><i class="fa fa-question-circle-o"></i></span>
						</shiro:hasPermission>
					</label>
					<div class="col-xs-7">
						<select required data-rel="select2" name="learnStyle"
								data-placeholder="请选择" data-width="193">
							<option></option>
							<c:import url="/metaTypes?__id=${cm:getMetaClassByCode('mc_learn_style').id}"/>
						</select>
						<script type="text/javascript">
							$("#modal form select[name=learnStyle]").val(${dpEdu.learnStyle});
						</script>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-5 control-label">是否获得学位</label>
					<div class="col-xs-7">
						<label>
							<input name="hasDegree" ${dpEdu.hasDegree?"checked":""}  type="checkbox" />
							<span class="lbl"></span>
						</label>
					</div>
				</div>
			</div>
			<div class="col-xs-6">

				<div class="form-group">
					<label class="col-xs-4 control-label">学位</label>
					<div class="col-xs-8">
						<select required data-rel="select2" name="degreeType" data-width="150"
								data-placeholder="请选择">
							<option></option>
							<c:forEach items="<%=SystemConstants.DEGREE_TYPE_MAP%>" var="degreeType">
								<option value="${degreeType.key}">${degreeType.value}</option>
							</c:forEach>
						</select>
						<script type="text/javascript">
							$("#modal form select[name=degreeType]").val(${dpEdu.degreeType});
						</script>
						<input style="margin-top: 3px" disabled class="form-control" type="text" name="degree" value="${dpEdu.degree}">
						<span class="help-block" style="font-size: 10px;">例如：文学学士、管理学硕士、教育学博士</span>
					</div>
				</div>

					<div class="form-group">
						<label class="col-xs-4 control-label">是否为最高学位</label>
						<div class="col-xs-8">
							<label style="margin-right: 10px">
								<input name="isHighDegree" ${dpEdu.isHighDegree?"checked":""}  type="checkbox" />
								<span class="lbl"></span>
							</label>
							<span class="secondDegree" style="display: none">
							<input type="checkbox" name="isSecondDegree" ${dpEdu.isSecondDegree?"checked":""}> 第二个最高学位 <span class="prompt" data-title="第二个最高学位说明"
							  data-prompt="如果最高学位是双学位，请在添加第二个最高学位的时候勾选此选项。"><i class="fa fa-question-circle-o"></i></span>
							</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-4 control-label">学位授予国家</label>
						<div class="col-xs-8">
							<input class="form-control" type="text" name="degreeCountry" value="${dpEdu.degreeCountry}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-4 control-label"><span class="star">*</span>学位授予单位</label>
						<div class="col-xs-8">
							<input required class="form-control" type="text" name="degreeUnit" value="${dpEdu.degreeUnit}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-4 control-label"><span class="star">*</span>学位授予日期</label>
						<div class="col-xs-8">
							<div class="input-group date" data-date-min-view-mode="1" data-date-format="yyyy.mm"
                                 style="width: 120px">
								<input required class="form-control" name="degreeTime" type="text"
										placeholder="yyyy.mm" value="${cm:formatDate(dpEdu.degreeTime,'yyyy.MM')}" />
								<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-4 control-label">导师姓名</label>
						<div class="col-xs-8">
							<input class="form-control" type="text" name="tutorName" value="${dpEdu.tutorName}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-7 control-label">导师现所在单位及职务（职称）</label>
						<div class="col-xs-5">
							<input class="form-control" type="text" name="tutorTitle" value="${dpEdu.tutorTitle}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-4 control-label">${_dpEdu_needCertificate?'<span class="star">*</span>':''}学历学位证书</label>
						<div class="col-xs-8 file">
							<div class="files">
								<input class="form-control" type="file" name="file1"/>
								<input class="form-control" type="file" name="file2" />
							</div>
							<span class="help-block" style="font-size: 10px;"><span class="star">*</span>每张图片大小不能超过${cm:stripTrailingZeros(_uploadMaxSize/(2*1024*1024))}M</span>
						</div>
					</div>
				<div class="form-group">
					<label class="col-xs-4 control-label">补充说明</label>
					<div class="col-xs-8">
						<textarea class="form-control" name="note" maxlength="50">${dpEdu.note}</textarea>
						<span class="help-block" style="font-size: 10px;">例如：硕博连读、美国哈佛大学联合培养一年等</span>
					</div>
				</div>
					<div class="form-group">
						<label class="col-xs-4 control-label">备注</label>
						<div class="col-xs-8">
							<textarea class="form-control" name="remark" maxlength="100">${dpEdu.remark}</textarea>
						</div>
					</div>

				</div></div>
	</form>
</div>
<div class="modal-footer">
	<a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
	<button id="submitBtn" type="button" class="btn btn-primary"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中"> ${not empty dpEdu?"确定":"添加"}
    </button>
</div>
<style>
	.form-group {
		margin-bottom: 3px;
	}
	<shiro:hasPermission name="cadre:updateWithoutRequired">
		span.star{color: grey}
	</shiro:hasPermission>
</style>

<script>
	$.fileInput($('#modalForm input[type=file]'),{
		no_file:'请选择证书图片 ...',
		btn_choose:'选择',
		btn_change:'更改',
		droppable:false,
		onchange:null,
		thumbnail:false, //| true | large
		maxSize:${_uploadMaxSize/2},
		allowExt: ['jpg', 'jpeg', 'png', 'gif'],
		allowMime: ['image/jpg', 'image/jpeg', 'image/png', 'image/gif']
	});
	//$("#modal .checkbox").bootstrapSwitch();
	function isHighDegreeChange(){
		if($("#modalForm input[name=isHighDegree]").bootstrapSwitch("state")){
			$("#modalForm .secondDegree").show();
		}else{
			$("#modalForm .secondDegree").hide();
		}
	}
	$('#modalForm input[name=isHighDegree]').on('switchChange.bootstrapSwitch', function(event, state) {
		isHighDegreeChange();
	});
	isHighDegreeChange();

	function hasDegreeChange(){
		if($("#modalForm input[name=hasDegree]").bootstrapSwitch("state")){
			$("#modalForm select[name=degreeType]").requireField(true);
			$("#modalForm input[name=degree]").requireField(true);
			$("#modalForm input[name=isHighDegree]").bootstrapSwitch('disabled', false);
			$("#modalForm input[name=degreeCountry]").val('${dpEdu.degreeCountry}').requireField(true);
			$("#modalForm input[name=degreeUnit]").val('${dpEdu.degreeUnit}').requireField(true);

			var finishTime = $("#modalForm input[name=finishTime]").val();
			//alert(finishTime)
			if($.trim($("#modalForm input[name=degreeTime]").val())==''){
				var degreeTime = $.trim(finishTime)==''?'':finishTime.format("yyyy.MM");
				$("#modalForm input[name=degreeTime]").closest(".input-group.date")
					.datepicker('update', degreeTime);;
			}
			$("#modalForm input[name=degreeTime]").requireField(true);

			$("#modalForm textarea[name=school]").trigger("keyup");
		}else{
			$("#modalForm select[name=degreeType]").val(null).trigger("change").requireField(false, true);
			$("#modalForm input[name=degree]").requireField(false, true);;
			$("#modalForm input[name=isHighDegree]").bootstrapSwitch('state', false).bootstrapSwitch('disabled', true);
			$("#modalForm input[name=degreeCountry]").requireField(false, true);;
			$("#modalForm input[name=degreeUnit]").requireField(false, true);;
			$("#modalForm input[name=degreeTime]").requireField(false, true);;
		}
	}
	$('#modalForm input[name=hasDegree]').on('switchChange.bootstrapSwitch', function(event, state) {
		/*console.log(this); // DOM element
		 console.log(event); // jQuery event
		 console.log(state); // true | false*/
		hasDegreeChange();
	});

	hasDegreeChange();

	function schoolTypeChange(){
		if($("#modalForm input[name=hasDegree]").bootstrapSwitch("state")){
			var $schoolType = $("#modalForm select[name=schoolType]");
			if($schoolType.val()=='<%=CadreConstants.CADRE_SCHOOL_TYPE_THIS_SCHOOL%>'
					|| $schoolType.val()=='<%=CadreConstants.CADRE_SCHOOL_TYPE_DOMESTIC%>'){
				$("#modalForm input[name=degreeCountry]").requireField(false, true).val('中国');
			}else{
				$("#modalForm input[name=degreeCountry]").requireField(true);
			}
		}
	}
	$("#modalForm select[name=schoolType]").change(function(){
		schoolTypeChange();
	});
	schoolTypeChange();

	function eduIdChange(){

		var $eduId = $("#modalForm select[name=eduId]");
		//console.log("$.trim($eduId.val())="+$.trim($eduId.val()))
		// 清除学历
		if($.trim($eduId.val())==''){
			$("#modalForm input[name=isHighEdu]").bootstrapSwitch("state", false).bootstrapSwitch('disabled', true);
		}else{
			$("#modalForm input[name=isHighEdu]").bootstrapSwitch('disabled', false);
		}

		// 学历为高中、中专不需要填写院系和专业
		if($eduId.val()=="${cm:getMetaTypeByCode("mt_edu_gz").id}"
				|| $eduId.val()=="${cm:getMetaTypeByCode("mt_edu_zz").id}"){

			$("#modalForm input[name=dep]").requireField(false);
			$("#modalForm input[name=major]").requireField(false)
		}else{
			$("#modalForm input[name=dep]").requireField(true);
			$("#modalForm input[name=major]").requireField(true);
		}

		if($eduId.val()=="${cm:getMetaTypeByCode("mt_edu_master").id}"
				|| $eduId.val()=="${cm:getMetaTypeByCode("mt_edu_doctor").id}"
				|| $eduId.val()=="${cm:getMetaTypeByCode("mt_edu_sstd").id}"){
			<c:if test="${sysUser.userId!=_user.userId}">
			$("#modalForm input[name=tutorName]").prop("disabled", false);
			$("#modalForm input[name=tutorTitle]").prop("disabled", false);
			</c:if>
			<c:if test="${sysUser.userId==_user.userId}">
			$("#modalForm input[name=tutorName]").requireField(true);
			$("#modalForm input[name=tutorTitle]").requireField(true);
			</c:if>
		}else{
			$("#modalForm input[name=tutorName]").requireField(false, true);;
			$("#modalForm input[name=tutorTitle]").requireField(false, true);;
		}

		if($eduId.val()=="${cm:getMetaTypeByCode("mt_edu_jxxx").id}"){
			$("#modalForm input[name=hasDegree]").bootstrapSwitch("state", false).bootstrapSwitch('disabled', true);
			$("#modalForm input[name=isHighEdu]").bootstrapSwitch("state", false).bootstrapSwitch('disabled', true);
			$("#modalForm input[name=isHighDegreee]").bootstrapSwitch("state", false).bootstrapSwitch('disabled', true);
		}else{
			$("#modalForm input[name=hasDegree]").bootstrapSwitch('disabled', false);
			if($.trim($eduId.val())!='') {
				$("#modalForm input[name=isHighEdu]").bootstrapSwitch('disabled', false);
			}
			$("#modalForm input[name=isHighDegreee]").bootstrapSwitch('disabled', false);
		}
	}
	$("#modalForm select[name=eduId]").change(function(){
		eduIdChange();
	});
	eduIdChange();

	function isGraduatedChange(){
		if(!$("#modalForm input[name=isGraduated]").bootstrapSwitch("state")) {
			$("#modalForm input[name=hasDegree]").bootstrapSwitch("state", false).bootstrapSwitch('disabled', true);
			$("#modalForm input[name=isHighEdu]").bootstrapSwitch("state", false).bootstrapSwitch('disabled', true);
			$("#modalForm input[type=file]").prop("disabled", true);

			$("#modalForm input[name=finishTime]").requireField(false, true);;
			//$("input[name=schoolLen]").requireField(false, true);;

		}else {
			$("#modalForm input[name=hasDegree]").bootstrapSwitch('disabled', false);
			if($.trim($("#modalForm select[name=eduId]").val())!='') {
				$("#modalForm input[name=isHighEdu]").bootstrapSwitch('disabled', false);
			}
			$("#modalForm input[type=file]").prop("disabled", false);

			$("#modalForm input[name=finishTime]").requireField(true);
			//$("input[name=schoolLen]").requireField(true);
		}
	}
	$('#modalForm input[name=isGraduated]').on('switchChange.bootstrapSwitch', function(event, state) {
		isGraduatedChange();
	});
	isGraduatedChange();

	$("#modalForm textarea[name=school]").keyup(function(){
		//console.log($("input[name=hasDegree]").bootstrapSwitch("state"));
		if($("#modalForm input[name=hasDegree]").bootstrapSwitch("state")) {
			var $degreeUnit = $("#modalForm input[name=degreeUnit]");
			/*if ($degreeUnit.val() == '' || $(this).val().startWith($degreeUnit.val())) {
				$degreeUnit.val($(this).val());
			}*/
			$degreeUnit.val($(this).val());
		}
	});
	$("#modalForm input[name=finishTime]").on('changeDate',function(ev){

		if($("#modalForm input[name=hasDegree]").bootstrapSwitch("state")) {
			var $degreeTime = $("#modalForm input[name=degreeTime]");
			//console.log("$degreeTime.val()=" + $degreeTime.val())
			if ($degreeTime.val() == '') {

				$degreeTime.closest(".input-group.date")
					.datepicker('update', ev.date.format("yyyy.MM"));
			}
		}
	});
	$.register.date($('.input-group.date'));

	$("#submitBtn").click(function () {
		<shiro:hasPermission name="cadre:updateWithoutRequired">
			$('input, textarea, select').prop("required", false);
		</shiro:hasPermission>
		<shiro:lacksPermission name="cadre:updateWithoutRequired">
		<c:if test="${_dpEdu_needCertificate}">
		var $eduId = $("#modalForm select[name=eduId]");
		var hasEdu = $eduId.val()>0; // 有学历
		var hasDegree = $("#modalForm input[name=hasDegree]").bootstrapSwitch("state");
		$("#modalForm input[type=file]").prop("required", false)
		var filePaths = '${dpEdu.certificate}'.split(",");
		if(hasEdu && hasDegree && (filePaths=='' || filePaths.length < 2)){
			$("#modalForm input[name='file1']").prop("required", true)
			$("#modalForm input[name='file2']").prop("required", true)
		}else if((hasEdu || hasDegree) && (filePaths=='' || filePaths.length < 1)){
			$("#modalForm input[name='file1']").prop("required", true)
		}
		</c:if>
		</shiro:lacksPermission>
        $("#modalForm").submit();
        return false;
    });
	$("#modalForm").validate({
		submitHandler: function (form) {

			var $btn = $("#submitBtn").button('loading');
			$(form).ajaxSubmit({
				success:function(ret){
					if(ret.success){
						$("#modal").modal("hide");
						$("#jqGrid_dpEdu").trigger("reloadGrid");
					}
					$btn.button('reset');
				}
			});
		}
	});
	$('#modalForm [data-rel="select2"]').select2();
	$('[data-rel="tooltip"]').tooltip();
</script>