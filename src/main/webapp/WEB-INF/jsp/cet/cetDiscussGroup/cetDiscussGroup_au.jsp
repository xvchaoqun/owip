<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cetDiscussGroup!=null}">编辑</c:if><c:if test="${cetDiscussGroup==null}">添加</c:if>研讨小组</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cet/cetDiscussGroup_au" id="modalForm" method="post">
        	<input type="hidden" name="id" value="${cetDiscussGroup.id}">
        	<input type="hidden" name="discussId" value="${cetDiscuss.id}">
        	<input type="hidden" name="adminUserId" value="${cetDiscussGroup.adminUserId}">
			<div class="form-group">
				<label class="col-xs-3 control-label">组别</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="name" value="${cetDiscussGroup.name}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">召集人</label>
				<div class="col-xs-6">
					<c:set var="holdUser" value="${cetDiscussGroup.holdUser}"/>
					<select name="holdUserId" data-rel="select2-ajax" data-width="272"
							data-ajax-url="${ctx}/sysUser_selects"
							data-placeholder="请输入账号或姓名或教工号">
						<option value="${holdUser.id}">${holdUser.realname}-${holdUser.code}</option>
					</select>
					<script>
						$.register.user_select($("#modalForm select[name=holdUserId]"))
					</script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">研讨主题</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="subject" value="${cetDiscussGroup.subject}">
				</div>
			</div>
			<c:if test="${cetDiscuss.unitType!=CET_DISCUSS_UNIT_TYPE_OW}">
			<div class="form-group">
				<label class="col-xs-6 control-label">是否允许负责单位修改研讨主题</label>
				<div class="col-xs-3">
					<input type="checkbox" class="big"
						   name="subjectCanModify" ${(cetDiscussGroup.subjectCanModify)?"checked":""}/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">负责单位</label>
				<div class="col-xs-6">
				<c:if test="${cetDiscuss.unitType==CET_DISCUSS_UNIT_TYPE_UNIT}">
					<c:set var="cetUnit" value="${cetDiscussGroup.cetUnit}"/>
					<c:set var="realname" value="${cetUnit.user.realname}"/>
					<select name="unitId" data-rel="select2-ajax" data-ajax-url="${ctx}/cet/cetUnit_selects"
							data-placeholder="请选择内设机构">
						<option value="${cetUnit.id}"
								title="${cetUnit.unitStatus==UNIT_STATUS_HISTORY}">${cetUnit.unitName}</option>
					</select>
				</c:if>
				<c:if test="${cetDiscuss.unitType==CET_DISCUSS_UNIT_TYPE_PARTY}">
					<c:set var="cetParty" value="${cetDiscussGroup.cetParty}"/>
					<c:set var="realname" value="${cetParty.user.realname}"/>
					<select name="unitId" data-rel="select2-ajax" data-ajax-url="${ctx}/cet/cetParty_selects"
							data-placeholder="请选择院系级党委">
						<option value="${cetParty.id}" title="${cetParty.partyIsDeleted}">${cetParty.partyName}</option>
					</select>
				</c:if>
				<c:if test="${cetDiscuss.unitType==CET_DISCUSS_UNIT_TYPE_PARTY_SCHOOL}">
					<c:set var="cetPartySchool" value="${cetDiscussGroup.cetPartySchool}"/>
					<c:set var="realname" value="${cetPartySchool.user.realname}"/>
					<select name="unitId" data-rel="select2-ajax" data-ajax-url="${ctx}/cet/cetPartySchool_selects"
							data-placeholder="请选择二级党校">
						<option value="${cetPartySchool.id}"
								title="${cetPartySchool.partySchoolIsHistory}">${cetPartySchool.partySchoolName}</option>
					</select>
				</c:if>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">负责单位管理员</label>
				<div class="col-xs-6 label-text" id="unitAdmin">
					${realname}
				</div>
			</div>
			</c:if>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
					 <textarea class="form-control limited"
							   name="remark">${cetDiscussGroup.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${cetDiscussGroup!=null}">确定</c:if><c:if test="${cetDiscussGroup==null}">添加</c:if></button>
</div>

<script>
	var $unitSelect = $.register.del_select($("#modalForm select[name=unitId]"), 272);
	$unitSelect.on("change",function(){
		var userId = $(this).select2("data")[0]['userId'] || '';
		var realname = $(this).select2("data")[0]['realname'] || '';
		console.log(userId + "  " + realname)
		$("#unitAdmin").html(realname);
		$("#modalForm input[name=adminUserId]").val(userId);
	}).change();
	$unitSelect.trigger("change");

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
    $("#modalForm :checkbox").bootstrapSwitch();
    //$('#modalForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
</script>