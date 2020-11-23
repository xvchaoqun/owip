<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ include file="../constants.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${param.apply!=1}">${cetUnitTrain!=null?'编辑':'添加'}二级党委培训记录</c:if>
		<c:if test="${param.apply==1}">${empty cetUnitTrain?'第二步：请填写培训信息':'修改申请'}</c:if></h3>
</div>
<div class="modal-body">
	<form class="form-horizontal" action="${ctx}/cet/cetUnitTrain_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${cetUnitTrain.id}">
        <input type="hidden" name="projectId" value="${cetUnitProject.id}">
        <input type="hidden" name="apply" value="${param.apply}">

		<div class="col-xs-12">
            <div class="col-xs-6">
			<c:if test="${param.apply==1}">
				<div class="form-group">
					<label class="col-xs-4 control-label"><span class="star">*</span> 培训班名称</label>
					<div class="col-xs-8 label-text">
						${cetUnitProject.projectName}
					</div>
				</div>

			</c:if>
			<c:if test="${param.apply!=1}">
				<div class="form-group">
					<label class="col-xs-4 control-label"><span class="star">*</span>参训人</label>
					<div class="col-xs-6">
						<select required data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects"
								name="userId" data-placeholder="请输入账号或姓名或学工号"  data-width="252">
							<option value="${cetUnitTrain.user.id}">${cetUnitTrain.user.realname}-${cetUnitTrain.user.code}</option>
						</select>
					</div>
				</div>
			</c:if>

			<div class="form-group">
				<label class="col-xs-4 control-label">时任单位及职务</label>
				<div class="col-xs-7">
					<textarea class="form-control noEnter" rows="3"
							   name="title">${cetUnitTrain.title}</textarea>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label">时任职务属性</label>
				<div class="col-xs-6">
					<select  data-rel="select2" name="postType" data-placeholder="请选择时任职务属性" data-width="252">
                        <option></option>
                        <jsp:include page="/metaTypes?__code=mc_post"/>
                    </select>
                    <script type="text/javascript">
                        $("#modalForm select[name=postType]").val(${cetUnitTrain.postType});
                    </script>
				</div>
			</div>

			<c:if test="${cm:getMetaTypes('mc_cet_identity').size()>0}">
				<div class="form-group owAuType">
					<label class="col-xs-4 control-label"> 参训人身份</label>
					<div class="col-xs-8">
						<div class="input-group">
							<c:forEach items="${cm:getMetaTypes('mc_cet_identity')}" var="entity">
								<div class="checkbox checkbox-inline checkbox-sm">
									<input type="checkbox" name="identities" id="identity${entity.key}" value="${entity.key}">
									<label for="identity${entity.key}">${entity.value.name}</label>
								</div>
							</c:forEach>
						</div>
					</div>
				</div>
				<script>
					<c:if test="${not empty cetUnitTrain}">
						var identity = '${cetUnitTrain.identity}';
						var identities = identity.split(',');
						 $.each(identities, function (i, item) {
							$('#modalForm input[name="identities"][value="'+ item +'"]').prop("checked", true);
						})
						//console.log(identity);
					</c:if>
				</script>
			</c:if>
		<div class="form-group">
				<label class="col-xs-4 control-label"><span class="star">*</span> 参训人类型</label>
				<div class="col-xs-6">
					<select required data-rel="select2" name="traineeTypeId" data-placeholder="请选择" data-width="252">
                        <option></option>
                        <c:forEach items="${traineeTypeMap}" var="entity">
							<c:if test="${entity.value.code!='t_reserve' && entity.value.code!='t_candidate'}">
							<option value="${entity.value.id}">${entity.value.name}</option>
							</c:if>
						</c:forEach>
						<option value="0">其他</option>
                    </select>
                    <script type="text/javascript">
                        $("#modalForm select[name=traineeTypeId]").val(${cetUnitTrain.traineeTypeId});
                    </script>
				</div>
			</div>

			<div class="form-group hidden" id="otherTraineeType">
				<div class="col-xs-offset-4 col-xs-7">
					<input class="form-control" name="otherTraineeType" type="text"
						   value="${cetUnitTrain.otherTraineeType}"/>
				</div>
			</div>
				<div class="form-group">
					<label class="col-xs-4 control-label"><span class="star">*</span>完成培训学时</label>
					<div class="col-xs-6">
							<input required class="form-control period" type="text"
								   name="period" value="${empty cetUnitTrain?cetUnitProject.period:cetUnitTrain.period}">
						<span class="help-block">注：此处默认为培训项目的学时，请修改为实际完成学时</span>
					</div>
				</div>
				</div>
            <div class="col-xs-6">

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
			<div class="form-group">
				<label class="col-xs-4 control-label"> 培训成绩</label>
				<div class="col-xs-6">
					<input class="form-control" type="text" name="score"
						   value="${cetUnitTrain.score}" maxlength="20">
				</div>
			</div>
				<c:if test="${_p_cetSupportCert}">
			<div class="form-group">
				<label class="col-xs-4 control-label"><span class="star">*</span>是否结业</label>
				<div class="col-xs-7">
					<div class="input-group">
						<div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
							<input required type="radio" name="isGraduate" id="isGraduate1"
								   ${(empty cetUnitTrain || cetUnitTrain.isGraduate)?"checked":""} value="1">
							<label for="isGraduate1">
								是
							</label>
						</div>
						<div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
							<input required type="radio" name="isGraduate" id="isGraduate0"
								   ${not empty cetUnitTrain && !cetUnitTrain.isGraduate?"checked":""} value="0">
							<label for="isGraduate0">
								否
							</label>
						</div>
					</div>
				</div>
			</div>
				</c:if>
			<div class="form-group">
				<label class="col-xs-4 control-label">备注</label>
				<div class="col-xs-6">
                        <textarea class="form-control limited" rows="2"
							   name="remark">${cetUnitTrain.remark}</textarea>
				</div>
			</div>

			</div>
		</div>
    </form>
</div>
<div class="modal-footer" style="clear: both">
	<c:if test="${param.apply==1}">
		<button class="popupBtn btn btn-default"
				data-url="${ctx}/user/cet/cetUnitTrain_list?userId=${param.userId}"
				data-width="1000"><i class="fa fa-reply"></i> 重新选择项目</button>
		<button id="submitBtn"
				data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
				class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${cetUnitProject!=null}">确定</c:if><c:if test="${cetUnitProject==null}">添加</c:if></button>
	</c:if>
	<c:if test="${param.apply!=1}">
	<a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${cetUnitTrain!=null}">确定</c:if><c:if test="${cetUnitTrain==null}">添加</c:if></button>
	</c:if>
</div>
<script>

	function traineeTypeChange(){
		//alert($("#modalForm select[name=traineeTypeId]").val())
		if ($("#modalForm select[name=traineeTypeId]").val() == "0"){
			$("#otherTraineeType").removeClass("hidden");
			$("input[name=otherTraineeType]", "#otherTraineeType").prop("disabled", false).attr("required", "required");
		}else {
			$("#otherTraineeType").addClass("hidden");
			$("input[name=otherTraineeType]", "#otherTraineeType").val('').prop("disabled", true).removeAttr("required");
		}
	}

	$("#modalForm select[name=traineeTypeId]").on("change", function () {
		traineeTypeChange();
	})
	traineeTypeChange();

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
    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $("#jqGrid").trigger("reloadGrid");
                        $("#jqGrid2").trigger("reloadGrid");
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    var $select = $.register.user_select($('#modalForm select[name=userId]'));
    $select.on("change",function(){
        //console.log($(this).select2("data")[0])
        var title = $(this).select2("data")[0]['title']||'';
        var postType = $(this).select2("data")[0]['postType']||'';
        $('#modalForm textarea[name=title]').val(title);

        $("#modalForm select[name=postType]").val(postType).trigger("change");
    });
    //$("#modalForm :checkbox").bootstrapSwitch();
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    $('#modalForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    $('textarea.limited').inputlimiter();
    //$.register.date($('.date-picker'));
</script>