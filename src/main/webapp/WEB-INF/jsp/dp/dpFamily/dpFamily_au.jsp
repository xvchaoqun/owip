<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="dpFamily_noNeedBirth" value="${_pMap['dpFamily_noNeedBirth']=='true'}"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${dpFamily!=null?'编辑':'添加'}${uv.realname}的家庭成员信息</h3>
</div>
<div class="modal-body">
	<div class="col-xs-12">
	<div class="col-xs-7">
    <form class="form-horizontal" action="${ctx}/dp/dpFamily_au?userId=${uv.userId}" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${dpFamily.id}">
		<input type="hidden" name="userId" value="${dpFamily.userId}">
		<div class="form-group">
			<label class="col-xs-4 control-label"><span class="star">*</span>称谓</label>
			<div class="col-xs-3">
				<select required data-rel="select2" name="title" data-placeholder="请选择" data-width="125">
					<option></option>
					<c:import url="/metaTypes?__id=${cm:getMetaClassByCode('mc_family_title').id}"/>
				</select>
				<script>
					$("#modal select[name=title]").val("${dpFamily.title}");
				</script>
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-4 control-label"><span class="star">*</span>姓名</label>
			<div class="col-xs-4">
				<input required class="form-control" type="text" name="realname"
					   value="${dpFamily.realname}">
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-4 control-label">${dpFamily_noNeedBirth?'':'<span class="star">*</span>'}  出生年月</label>
			<div class="col-xs-4">
				<div class="input-group">
					<input ${dpFamily.withGod?'disabled':(dpFamily_noNeedBirth?'':'required')}
							class="form-control date-picker" name="_birthday" type="text"
							data-date-min-view-mode="1" data-date-format="yyyy-mm"
							value="${cm:formatDate(dpFamily.birthday,'yyyy-MM')}"/>
					<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
				</div>
			</div>
			<div class="col-xs-4" style="padding-left: 0px">
				<input type="checkbox" name="withGod"
				${dpFamily.withGod?'checked':''}
					   style="width: 15px;height: 15px;margin-top: 8px; vertical-align: -2px"> 去世
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-4 control-label"><span class="star">*</span>政治面貌</label>
			<div class="col-xs-6">
				<select required data-rel="select2" name="politicalStatus" data-placeholder="请选择"
						data-width="125">
					<option></option>
					<c:import url="/metaTypes?__id=${cm:getMetaClassByCode('mc_political_status').id}"/>
				</select>
				<script type="text/javascript">
					$("#modal form select[name=politicalStatus]").val(${dpFamily.politicalStatus});
				</script>

			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-4 control-label">工作单位及职务</label>
			<div class="col-xs-7">
                            <textarea class="form-control noEnter" rows="3" maxlength="100"
									  name="unit">${dpFamily.unit}</textarea>
			</div>
		</div>
			<div class="form-group">
				<label class="col-xs-4 control-label"> 备注</label>
				<div class="col-xs-7">
                         <textarea class="form-control noEnter" rows="2" maxlength="100"
								   name="remark">${dpFamily.remark}</textarea>
				</div>
			</div>
    </form>
	</div>
		<div class="col-xs-5">
			${cm:getHtmlFragment('hf_dp_family_note').content}
		</div>
	</div>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty dpFamily?'确定':'添加'}</button>
</div>
<script>

	$("#modalForm input[name=withGod]").click(function () {
		if ($(this).is(":checked")) {
			$("input[name=_birthday]").val('').prop("disabled", true);
			<c:if test="${!dpFamily_noNeedBirth}">
			$("input[name=_birthday]").removeAttr("required");
			</c:if>
		} else {
			$("input[name=_birthday]").prop("disabled", false);
			<c:if test="${!dpFamily_noNeedBirth}">
			$("input[name=_birthday]").attr("required", "required");
			</c:if>
		}
	});

    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $("#jqGrid2").trigger("reloadGrid");
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    //$("#modalForm :checkbox").bootstrapSwitch();
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $('textarea.limited').inputlimiter();
    $.register.date($('.date-picker'));
</script>