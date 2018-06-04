<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/cet/constants.jsp" %>
<c:set var="UNIT_STATUS_HISTORY" value="<%=SystemConstants.UNIT_STATUS_HISTORY%>"/>

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

				<div class="col-xs-8">
					<select data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects?types=${USER_TYPE_JZG}"
							name="holdUserId"
							data-width="272" data-placeholder="请输入账号或姓名或学工号">
						<option></option>
					</select>
					<button type="button" class="btn btn-success btn-sm" onclick="_selectUser(1)"><i
							class="fa fa-plus"></i> 选择
					</button>
					<div style="padding-top: 10px;">
						<div id="holdUsers" class="itemList" style="max-height: 152px;overflow-y: auto;">

						</div>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">联络员</label>
				<div class="col-xs-8">
					<select data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects?types=${USER_TYPE_JZG}"
							name="linkUserId"
							data-width="272" data-placeholder="请输入账号或姓名或学工号">
						<option></option>
					</select>
					<button type="button" class="btn btn-success btn-sm" onclick="_selectUser(2)"><i
							class="fa fa-plus"></i> 选择
					</button>
					<div style="padding-top: 10px;">
						<div id="linkUsers" class="itemList" style="max-height: 152px;overflow-y: auto;">

						</div>
					</div>
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
				<c:set var="realname" value=""/>
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
						<option value="${cetParty.id}"
								realname="${realname}"
								title="${cetParty.partyIsDeleted}">${cetParty.partyName}</option>
					</select>
				</c:if>
				<c:if test="${cetDiscuss.unitType==CET_DISCUSS_UNIT_TYPE_PARTY_SCHOOL}">
					<c:set var="cetPartySchool" value="${cetDiscussGroup.cetPartySchool}"/>
					<c:set var="realname" value="${cetPartySchool.user.realname}"/>
					<select name="unitId" data-rel="select2-ajax" data-ajax-url="${ctx}/cet/cetPartySchool_selects"
							data-placeholder="请选择二级党校">
						<option value="${cetPartySchool.id}"
								realname="${realname}"
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
<script type="text/template" id="itemListTpl">
	<table id="itemTable" class="table table-striped table-bordered table-condensed table-unhover2 table-center">
		<thead class="multi">
		<tr>
			<th>姓名</th>
			<th>工号</th>
			<th></th>
		</tr>
		</thead>
		<tbody>
		{{_.each(users, function(u, idx){ }}
		<tr data-user-id="{{=u.userId}}">
			<td>{{=u.realname}}</td>
			<td>{{=u.code}}</td>
			<td>
				<a href="javasciprt:;" class="del">移除</a>
			</td>
		</tr>
		{{});}}
		</tbody>
	</table>
</script>
<script>
	var selectedHoldUsers = ${cm:toJSONArrayWithFilter(cetDiscussGroup.holdUsers, "userId,code,realname")};
	var selectedLinkUsers = ${cm:toJSONArrayWithFilter(cetDiscussGroup.linkUsers, "userId,code,realname")};
	$("#holdUsers").append(_.template($("#itemListTpl").html())({users: selectedHoldUsers}));
	$("#linkUsers").append(_.template($("#itemListTpl").html())({users: selectedLinkUsers}));

	function _selectUser(type) {

		var $select = (type==1)?$("#modalForm select[name=holdUserId]"):$("#modalForm select[name=linkUserId]");
		var userId = $.trim($select.val());
		if (userId == '') {
			$.tip({
				$target: $select.closest("div").find(".select2-container"),
				at: 'top center', my: 'bottom center', type: 'success',
				msg: "请选择。"
			});
			return;
		}
		var hasSelected = false;
		$.each((type==1)?selectedHoldUsers:selectedLinkUsers, function (i, user) {
			if (user.userId == userId) {
				hasSelected = true;
				return false;
			}
		})
		if (hasSelected) {
			$.tip({
				$target: $select.closest("div").find(".select2-container"),
				at: 'top center', my: 'bottom center', type: 'success',
				msg: "您已经选择了该账号。"
			});
			return;
		}

		var realname = $select.select2("data")[0]['text'] || '';
		var code = $select.select2("data")[0]['code'] || '';
		var user = {userId: userId, realname: realname, code: code};

		//console.log(user)
		if(type==1){
			selectedHoldUsers.push(user);
		}else{
			selectedLinkUsers.push(user);
		}

		$((type==1)?"#holdUsers":"#linkUsers").empty().append(_.template($("#itemListTpl").html())({users: (type==1)?selectedHoldUsers:selectedLinkUsers}));
	}
	$(document).off("click", ".itemList .del")
			.on('click', ".itemList .del", function () {
				var $tr = $(this).closest("tr");
				var userId = $tr.data("user-id");
				//console.log("userId=" + userId)
				var isHoldUsers = ($(this).closest(".itemList").attr("id")=="holdUsers");
				$.each(isHoldUsers?selectedHoldUsers:selectedLinkUsers, function (i, user) {
					if (user.userId == userId) {
						if(isHoldUsers) {
							selectedHoldUsers.splice(i, 1);
						}else{
							selectedLinkUsers.splice(i, 1);
						}
						return false;
					}
				})
				$(this).closest("tr").remove();
			});
	$.register.user_select($('#modalForm [data-rel="select2-ajax"]'));

	var $unitSelect = $.register.del_select($("#modalForm select[name=unitId]"), {width:272, allowClear:false});
	$unitSelect.on("change",function(){
		var admin = $(this).select2("data")[0];
		if(admin==undefined) return ;
		var userId = $(this).select2("data")[0]['userId'] || '';
		var realname = $(this).select2("data")[0]['realname'] || '';
		console.log(userId + "  " + realname)
		$("#unitAdmin").html(realname);
		$("#modalForm input[name=adminUserId]").val(userId);
	});

	$("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {

			var holdUserIds = $.map(selectedHoldUsers, function (user) {
				return user.userId;
			});
			var linkUserIds = $.map(selectedLinkUsers, function (user) {
				return user.userId;
			});

            $(form).ajaxSubmit({
				data: {holdUserIds: holdUserIds.join(","), linkUserIds:linkUserIds.join(",")},
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