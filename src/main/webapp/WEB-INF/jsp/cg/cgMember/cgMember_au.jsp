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
				<select required id="typeSelect" name="type" data-placeholder="请选择人员类型"
						data-rel="select2" data-width="270">
					<option></option>
					<c:forEach items="<%=CgConstants.CG_MEMBER_TYPE_MAY%>" var="cgMemberType">
						<option value="${cgMemberType.key}">${cgMemberType.value}</option>
					</c:forEach>
				</select>
				<script>
					$("#typeSelect").val('${cgMember.type}');
				</script>
			</div>
		</div>
		<div class="form-group hiddenUnitPost" hidden>
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
		<div class="form-group hiddenUnitPost" hidden>
			<label class="col-xs-4 control-label"> 现任干部</label>
			<div class="col-xs-6">
				<select name="userId" class="form-control" data-width="272"
						data-rel="select2-ajax"
						data-ajax-url="${ctx}/cadre_selects?key=1"
						data-placeholder="请选择">
					<option value="${cgMember.userId}">${sysUser.realname}</option>
				</select>
			</div>
		</div>
		<div class="form-group hiddenUser" hidden>
			<label class="col-xs-4 control-label"> 代表类型</label>
			<div class="col-xs-6">
				<input name="tag" type="text" class="form-control">
			</div>
		</div>
		<div class="form-group hiddenUser" hidden>
			<label class="col-xs-4 control-label"> 代表姓名</label>
			<div class="col-xs-6">
				<select name="userIds"
						data-rel="select2-ajax"
						data-ajax-url="${ctx}/sysUser_selects"
						data-placeholder="请输入账号或姓名或学工号">
					<option></option>
				</select>
				<button type="button" class="btn btn-success btn-sm" onclick="_selectUser()"><i
						class="fa fa-plus"></i> 选择
				</button>
				<div style="padding-top: 10px;">
					<div id="itemList" class="itemList" style="max-height: 152px;overflow-y: auto;">

					</div>
				</div>
			</div>
		</div>
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

	if ($("#typeSelect").val().trim()!=''){

		typeChange();
	}
	$("select[name=type]").on("change",function () {
		typeChange();
	});
	function typeChange() {

		if ($("select[name=type]").val()==<%=CgConstants.CG_MEMBER_TYPE_CADRE%>){
			$(".hiddenUnitPost").show();
			$(".hiddenUser").hide();
			$(".hiddenUser input").val(null);
			$(".hiddenUser select").val(null).trigger("change");
		}else {
			$(".hiddenUser").show();
			$(".hiddenUnitPost").hide();
			$(".hiddenUnitPost select").val(null).trigger("change");
		}
	}

	$("select[name=unitPostId]").change(function () {

		if ($("select[name=unitPostId]").val() == null) return;

		var data = $(this).select2("data")[0];
		if (data['up']==undefined) return;
		var up = data['up'];
		var selectUser = $("select[name=userId]");
		if (up != undefined && up.cadre !=undefined && up.cadre.user !=undefined){
			var option = new Option(up.cadre.user.realname, up.cadre.user.id, true, true);
			selectUser.append(option).trigger('change');
		}else {
			selectUser.val(null).trigger('change');
			$.tip({
				$target: selectUser.closest("div").find(".select2-container"),
				at: 'top right', my: 'bottom right', type: 'success',
				msg: "该岗位还未设置干部。"
			});
			return;
		}
	});

    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {

			var userIds = $.map(selectedItems, function (user) {
				return user.userId;
			});

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
        })
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
	var selectedItems = ${cm:toJSONArrayWithFilter(cetDiscussGroup.userIds, "userId,code,realname")};
	$("#userIds").append(_.template($("#itemListTpl").html())({users: selectedItems}));
</script>