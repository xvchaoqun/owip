<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${cgMember!=null?'编辑':'添加'}委员会和领导小组成员</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cg/cgMember_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${cgMember.id}">
		<input type="hidden" name="teamId" value="${param.teamId}">
		<input type="hidden" name="isCurrent" value="${cgMember.isCurrent}">
		<div class="form-group">
			<label class="col-xs-4 control-label"><span class="star">*</span> 职务</label>
			<div class="col-xs-6">
				<select class="col-xs-6" required name="post" data-width="270"
						data-rel="select2" data-placeholder="请选择">
					<option></option>
					<c:import url="/metaTypes?__code=mc_cg_staff"/>
				</select>
			</div>
			<script>
				$("#modalForm select[name=post]").val('${cgMember.post}');
			</script>
		</div>
		<div class="form-group">
			<label class="col-xs-4 control-label"><span class="star">*</span> 人员类型</label>
			<div class="col-xs-6">
				<select required id="typeSelect1" name="type" data-placeholder="请选择人员类型"
						data-rel="select2" data-width="270">
					<option></option>
					<c:forEach items="<%=CgConstants.CG_MEMBER_TYPE_MAY%>" var="cgMemberType">
						<option value="${cgMemberType.key}">${cgMemberType.value}</option>
					</c:forEach>
				</select>
				<script>
					$("#typeSelect1").val('${cgMember.type}');
				</script>
			</div>
		</div>
		<div class="form-group unitPostAndUser" hidden>
			<label class="col-xs-4 control-label"> 岗位名称</label>
			<div class="col-xs-6">
				<select name="unitPostId" class="form-control" data-width="272"
						data-rel="select2-ajax"
						data-ajax-url="${ctx}/unitPost_selects"
						data-placeholder="请选择">
					<option value="${cgMember.unitPostId}">${unitPost.code}-${unitPost.name}</option>
				</select>
			</div>
		</div>
		<div class="form-group unitPostAndUser" hidden>
			<label class="col-xs-4 control-label"><span class="star">*</span> 现任干部</label>
			<div class="col-xs-6">
				<select name="userId" class="form-control" data-width="272"
						data-rel="select2-ajax"
						data-ajax-url="${ctx}/cadre_selects?key=1"
						data-placeholder="请选择">
					<option value="${cgMember.userId}">${sysUser.realname}</option>
				</select>
				<label hidden name="remind" style="color: red">该岗位没有干部。</label>
			</div>
		</div>
		<div class="form-group delegateAndUser" hidden>
			<label class="col-xs-4 control-label"><span class="star">*</span> 代表类型</label>
			<div class="col-xs-6">
				<input name="tag" type="text" class="form-control" value="${cgMember.tag}">
			</div>
		</div>
		<div class="form-group delegateAndUser" hidden>
			<label class="col-xs-4 control-label"> 席位</label>
			<div class="col-xs-6">
				<input name="seat" type="text" class="form-control" value="${cgMember.seat}">
			</div>
		</div>
		<div class="form-group delegateAndUser" hidden>
			<label class="col-xs-4 control-label"><span class="star">*</span> 代表姓名</label>
			<div class="col-xs-6">
				<select name="userIds"
						class="form-control"
						data-rel="select2-ajax"
						data-ajax-url="${ctx}/sysUser_selects"
						data-placeholder="请输入账号或姓名或学工号">
					<option value="${cgMember.userId}">${sysUser.realname}</option>
				</select>
				<c:if test="${empty cgMember}">
				<button type="button" class="btn btn-success btn-sm" onclick="_selectUser()"><i
						class="fa fa-plus"></i> 选择
				</button>
				</c:if>
				<div style="padding-top: 10px;">
					<div id="itemList" class="itemList" style="max-height: 152px;overflow-y: auto;">

					</div>
				</div>
			</div>
		</div>
		<c:if test="${cgMember.needAdjust}">
			<label class="col-xs-4 control-label"/>
			<div class="col-xs-6">
				<label style="color: red">该岗位现任干部有变动，请先撤销后在进行添加。</label>
			</div>
		</c:if>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty cgMember?'确定':'添加'}</button>
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

	var userTypeSelect = $("select[id=typeSelect1]");//人员类型
	var unitPostSelect = $(".unitPostAndUser select[name=unitPostId]");//岗位名称
	var userSelect = $(".unitPostAndUser select[name=userId]");//现任干部
	var tag = $(".delegateAndUser input[name=tag]");//代表类型
	var seat = $(".delegateAndUser input[name=seat]");//席位
	var usersSelect = $(".delegateAndUser select[name=userIds]");//代表姓名
	var remind = $("label[name=remind]");//提醒

	//改变人员类型
	userTypeSelect.on("change",function () {
		typeChange();
	});

	if (unitPostSelect.val() != "") userSelect.prop("disabled",true);

	function typeChange() {//根据人员类型改变数据的约束

		if (userTypeSelect.val()==<%=CgConstants.CG_MEMBER_TYPE_CADRE%>){//人员类型为现任干部

			$(".unitPostAndUser").show();
			$(".delegateAndUser").hide();

			//清空各类代表中的值
			tag.val(null);
			seat.val(null);
			usersSelect.val(null).trigger("change");

			//删除各类代表中“必选”属性
			tag.removeAttr("required");
			usersSelect.removeAttr("required");

			//现任干部添加“必选属性”
			userSelect.attr("required","required");

		}else if (userTypeSelect.val()==<%=CgConstants.CG_MEMBER_TYPE_USER%>) {//人员类型为各类代表

			$(".unitPostAndUser").hide();
			$(".delegateAndUser").show();

			//清空岗位及现任干部的值
			unitPostSelect.val(null).trigger("change");
			userSelect.val(null).trigger("change");

			//删除现任干部的“必选”属性
			userSelect.removeAttr("required");

			//各类代表添加“必选”属性
			tag.attr("required","required");
			usersSelect.attr("required","required");
		}else {//人员类型为空

			$(".unitPostAndUser").hide();
			$(".delegateAndUser").hide();
		}
	}

	unitPostSelect.on("change",function () {

		var data = $(this).select2("data")[0];

		var up = undefined;
		if (data != undefined) up = data['up'];

		if (up != undefined && up.cadre !=undefined && up.cadre.user !=undefined){//岗位的现任干部信息不为空

			var option = new Option(up.cadre.user.realname, up.cadre.user.id, true, true);
			userSelect.append(option).trigger('change');
			userSelect.prop("disabled",true);
			remind.hide();
		}else if (unitPostSelect.val() != "" && unitPostSelect.val() != null) {//岗位的现任干部信息为空

			userSelect.val(null).trigger('change');
			userSelect.prop("disabled",true);
			userSelect.attr("required","required");
			remind.show();
		}else if (unitPostSelect.val() == "" || unitPostSelect.val() == null){//没有选择岗位

			userSelect.val(null).trigger('change');
			userSelect.prop("disabled",false);
			userSelect.attr("required","required");
			remind.hide();
		}
	});

    $("#submitBtn").click(function(){

		userSelect.prop("disabled",false);
		$("#modalForm").submit();

		if (unitPostSelect.val() != "") userSelect.prop("disabled",true);

    	return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {

        	var userIds;
        	if (userTypeSelect.val() == <%=CgConstants.CG_MEMBER_TYPE_USER%>){

				if (${not empty cgMember}) {

					var user = {userId: usersSelect.val()};
					selectedItems.push(user);
				}

				userIds = $.map(selectedItems, function (user) {

					return user.userId;
				});
			}

			userSelect.prop("disabled",false);

            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
				data: {userIdsList: userIds},
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

	var selectedItems = [];

    function _selectUser() {

        var $select = $("#modalForm select[name=userIds]");
        var userId = $.trim($select.val());
        if (userId == '') {
            $.tip({
                $target: $select.closest("div").find(".select2-container"),
                at: 'top center', my: 'bottom center', type: 'success',
                msg: "请选择代表人。"
            });
            return;
        }
        var hasSelected = false;
        $.each(selectedItems, function (i, user) {
            if (user.userId == userId) {
                hasSelected = true;
                return false;
            }
        });
        if (hasSelected) {
            $.tip({
                $target: $select.closest("div").find(".select2-container"),
                at: 'top center', my: 'bottom center', type: 'success',
                msg: "您已经选择了该代表。"
            });
            return;
        }

        var realname = $select.select2("data")[0]['text'] || '';
        var code = $select.select2("data")[0]['code'] || '';
        var user = {userId: userId, realname: realname, code: code};

        selectedItems.push(user);
        $("#itemList").empty().append(_.template($("#itemListTpl").html())({users: selectedItems}));
    }

	$(document).off("click", "#itemList .del")
			.on('click', "#itemList .del", function () {
				var $tr = $(this).closest("tr");
				var userId = $tr.data("user-id");
				//console.log("userId=" + userId)
				$.each(selectedItems, function (i, user) {
					if (user.userId == userId) {
						selectedItems.splice(i, 1);
						return false;
					}
				})
				$tr.remove();
			});

    $.register.user_select($('[data-rel="select2-ajax"]'));
    $('#modalForm [data-rel="select2"]').select2();

    typeChange();
</script>