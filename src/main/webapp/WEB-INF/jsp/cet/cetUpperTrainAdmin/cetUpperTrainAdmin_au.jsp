<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="CET_UPPER_TRAIN_UPPER" value="<%=CetConstants.CET_UPPER_TRAIN_UPPER%>"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cetUpperTrainAdmin!=null}">编辑</c:if><c:if test="${cetUpperTrainAdmin==null}">添加</c:if>上级单位管理员</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cet/cetUpperTrainAdmin_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${cetUpperTrainAdmin.id}">
        <input type="hidden" name="upperType" value="${upperType}">
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>管理员类型</label>
                <div class="col-xs-6 choice label-text">
                    <div class="input-group">
                        <input required name="type" type="checkbox" class="big" value="0"
                        ${empty cetUpperTrainAdmin.type ||  cetUpperTrainAdmin.type?'':'checked'}> 单位管理员&nbsp;
                        <input required name="type" type="checkbox" class="big" value="1"
                        ${cetUpperTrainAdmin.type?'checked':''}> 校领导管理员
                    </div>
                </div>
			</div>
            <c:set var="isUnitAdmin" value="${not empty cetUpperTrainAdmin && !cetUpperTrainAdmin.type}"/>
            <c:set var="isLeaderAdmin" value="${cetUpperTrainAdmin.type}"/>
			<div class="form-group" id="unitDiv" style="display: ${isUnitAdmin?'block':'none'}">
				<label class="col-xs-3 control-label">${isUnitAdmin?'*':''}所属单位</label>
				<div class="col-xs-6">
                    <select ${isUnitAdmin?'required':''} data-rel="select2-ajax"
                                                                data-width="273" data-ajax-url="${ctx}/unit_selects"
                                                                name="unitId" data-placeholder="请选择${upperType==CET_UPPER_TRAIN_UPPER?'派出':'组织'}单位">
                        <option value="${cetUpperTrainAdmin.unit.id}">${cetUpperTrainAdmin.unit.name}</option>
                    </select>
				</div>
			</div>
        <div class="form-group" id="cadreDiv" style="display: ${isLeaderAdmin?'block':'none'}">
				<label class="col-xs-3 control-label">${isLeaderAdmin?'*':''}所属校领导</label>
				<div class="col-xs-6">
                    <select ${isLeaderAdmin?'required':''} data-rel="select2-ajax"
                            data-width="273" data-ajax-url="${ctx}/cadre_selects?key=1&status=<%=CadreConstants.CADRE_STATUS_LEADER%>"
                            name="leaderUserId" data-placeholder="请选择">
                        <option value="${cetUpperTrainAdmin.leaderUser.id}">${cetUpperTrainAdmin.leaderUser.realname}</option>
                    </select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>管理员</label>
				<div class="col-xs-6">
                    <select required data-rel="select2-ajax"
                            data-width="273" data-ajax-url="${ctx}/sysUser_selects?types=<%=SystemConstants.USER_TYPE_JZG%>"
                            name="userId" data-placeholder="请选择">
                        <option value="${cetUpperTrainAdmin.user.id}">${cetUpperTrainAdmin.user.realname}</option>
                    </select>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${cetUpperTrainAdmin!=null}">确定</c:if><c:if test="${cetUpperTrainAdmin==null}">添加</c:if></button>
</div>
<script>
    $("#modalForm input[name=type]").click(function(){

        if($(this).prop("checked")){
            $("input[type=checkbox][name=type]").not(this).prop("checked", false);
        }

        if($(this).prop("checked") && $(this).val()=='0'){
            $("#unitDiv").show();
            $("#modalForm select[name=unitId]").prop("disabled", false).attr("required", "required");

            $("#cadreDiv").hide();
            $("#modalForm select[name=leaderUserId]").val(null).trigger("change").prop("disabled", true).removeAttr("required");
        }else if($(this).prop("checked") && $(this).val()=='1'){
            $("#cadreDiv").show();
            $("#modalForm select[name=leaderUserId]").prop("disabled", false).attr("required", "required");

            $("#unitDiv").hide();
            $("#modalForm select[name=unitId]").val(null).trigger("change").prop("disabled", true).removeAttr("required");
        }else{
            $("#unitDiv").hide();
            $("#modalForm select[name=unitId]").val(null).trigger("change").prop("disabled", true).removeAttr("required");
            $("#cadreDiv").hide();
            $("#modalForm select[name=leaderUserId]").val(null).trigger("change").prop("disabled", true).removeAttr("required");
        }
    });

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
    //$("#modalForm :checkbox").bootstrapSwitch();
    $.register.ajax_select($('#modalForm select[name=unitId]'));
    $.register.user_select($('#modalForm select[name=leaderUserId]'));
    $.register.user_select($('#modalForm select[name=userId]'));
    //$('#modalForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$('textarea.limited').inputlimiter();
    //$.register.date($('.date-picker'));
</script>