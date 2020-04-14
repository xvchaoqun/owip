<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set value="<%=SystemConstants.UNIT_POST_STATUS_NORMAL%>" var="UNIT_POST_STATUS_NORMAL"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${drOnlinePost!=null?'编辑':'添加'}推荐职务</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/dr/drOnlinePost_au?onlineId=${onlineId}" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${drOnlinePost.id}">
		<input type="hidden" name="onlineId" value="${onlineId}">
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 推荐职务</label>
				<div class="col-xs-6">
					<select required name="unitPostId" data-rel="select2-ajax" data-ajax-url="${ctx}/unitPost_selects"
							data-width="273"
							data-placeholder="请选择">
						<option value="${unitPost.id}" delete="${unitPost.status!=UNIT_POST_STATUS_NORMAL}">${unitPost.code}-${unitPost.name}</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"> 是否差额</label>
				<div class="col-xs-6">
					<label>
						<input name="hasCompetitive" ${drOnlinePost.hasCompetitive?"checked":""} type="checkbox"/>
						<span class="lbl"></span>
					</label>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 最大推荐人数</label>
				<div class="col-xs-6">
					<input required style="width: 78px;" class="form-control digits" type="text"  name="competitiveNum" placeholder="请填写阿拉伯数字！" value="${drOnlinePost.competitiveNum}">
				</div>
			</div>
			<div class="form-group" id="hasCandidate">
				<label class="col-xs-3 control-label"> 是否有候选人</label>
				<div class="col-xs-6">
					<label>
						<input name="hasCandidate" ${(drOnlinePost.hasCandidate)?"checked":""} type="checkbox"/>
						<span class="lbl"></span>
					</label>
				</div>
			</div>
			<div class="candidate">
				<div class="form-group">
					<label class="col-xs-3 control-label">候选人</label>
					<div class="col-xs-6 selectUsers">
						<select data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects?types=${USER_TYPE_JZG}"
								name="userId" data-placeholder="请输入账号或姓名或学工号">
							<option></option>
						</select>
						<button type="button" class="btn btn-success btn-sm" onclick="_selectUser()"><i class="fa fa-plus"></i> 选择
						</button>
					</div>
				</div>
				<div style="padding: 0 90px 0;margin:0 5px 10px;max-height: 218px;overflow: auto">
					<div id="itemList">

					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6"	>
							<textarea class="form-control limited noEnter" type="text"
									  name="remark" rows="3">${drOnlinePost.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty drOnlinePost?'确定':'添加'}</button>
</div>

<script src="${ctx}/assets/js/bootstrap-tag.js"></script>
<script src="${ctx}/assets/js/ace/elements.typeahead.js"></script>
<script type="text/template" id="itemListTpl">
	<table class="table table-striped table-bordered table-condensed table-center table-unhover2">
		<thead>
		<tr>
			<th>姓名</th>
			<th>工号</th>
			<th></th>
		</tr>
		</thead>
		<tbody>
		{{_.each(users, function(u, idx){ }}
		<tr data-user-id="{{=u.userId}}"
			data-realname="{{=u.realname}}"
			data-code="{{=u.code}}">
			<td>{{=u.realname}}</td>
			<td>{{=u.code}}</td>
			<td>
				<a href="javasciprt:;" class="del">移除</a>
				{{if(idx>0){}}
				<a href="javasciprt:;" class="up">上移</a>
				{{}}}
			</td>
		</tr>
		{{});}}
		</tbody>
	</table>
</script>
<script>

	var selectedUsers = ${cm:toJSONArrayWithFilter(candidates, "userId,code,realname")};
	$("#itemList").append(_.template($("#itemListTpl").html())({users: selectedUsers}));
	function _selectUser() {

		var $select = $("#modalForm select[name=userId]");
		var userId = $.trim($select.val());
		if (userId == '') {
			$.tip({
				$target: $select.closest("div").find(".select2-container"),
				at: 'top center', my: 'bottom center', type: 'success',
				msg: "请选择候选人。"
			});
			return;
		}
		var hasSelected = false;
		$.each(selectedUsers, function (i, user) {
			if (user.userId == userId) {
				hasSelected = true;
				return false;
			}
		})
		if (hasSelected) {
			$.tip({
				$target: $select.closest("div").find(".select2-container"),
				at: 'top center', my: 'bottom center', type: 'success',
				msg: "您已经选择了该候选人。"
			});
			return;
		}

		var realname = $select.select2("data")[0]['text'] || '';
		var code = $select.select2("data")[0]['code'] || '';
		var user = {userId: userId, realname:realname, code: code};

		//控制添加的候选人数
		if(selectedUsers.length < $("input[name=competitiveNum]").val()){
			selectedUsers.push(user);
		}else {
			$.tip({
				$target: $select.closest("div").find(".select2-container"),
				at: 'top center', my: 'bottom center', type: 'fail',
				msg: "候选人数量已达最大推荐人数！"
			});
			return;
		}

		//console.log(selectedUsers.length)
		$("#itemList").empty().append(_.template($("#itemListTpl").html())({users: selectedUsers}));
		_displayItemList();
	}

	$(document).off("click","#itemList .del")
			.on('click', "#itemList .del", function(){
				var $tr = $(this).closest("tr");
				var userId = $tr.data("user-id");
				//console.log("userId=" + userId)
				$.each(selectedUsers, function (i, user) {
					if (user.userId == userId) {
						selectedUsers.splice(i, 1);
						return false;
					}
				})
				$(this).closest("tr").remove();
			})
	$(document).off("click", ".up")
			.on('click', ".up", function () {
				var $tr = $(this).parents("tr");
				if ($tr.index() == 0) {
					//alert("首行数据不可上移");
				} else {
					//$tr.fadeOut().fadeIn();
					//在同辈元素之前插入一个元素
					$tr.prev().before($tr);
				}
				selectedUsers = $.map($("#itemList tbody tr"), function (tr) {
					return {
						userId: $(tr).data("user-id"), realname: $(tr).data("realname"),
						code: $(tr).data("code")
					};
				});

				//console.log(selectedUsers)
				_displayItemList()
			});
	function _displayItemList() {
		var users = selectedUsers;
		if (users.length == 0) return;
		//console.log(users)
		$("#itemList").empty().append(_.template($("#itemListTpl").html())({
			users: users
		}));

	}
	_displayItemList()

	/*$("#modalForm").find(".tags").css("width","270")*/
	/*var canDiv = $("#modalForm .candidate input");
	var maxNum = $("#modalForm input[name=competitiveNum]");
	canDiv.on("change", function () {
		var count = $('.candidate').find('.tags').find('span').size();
		console.log(count)

	})
*/
	var hasCandidate =  $("#modalForm input[name=hasCandidate]");
	var hasCompetitive = $("#modalForm input[name=hasCompetitive]");

	function hasCompetitiveChange() {
		if (!hasCompetitive.bootstrapSwitch("state")){
			$("#modalForm input[name=competitiveNum]").attr("readonly","readonly");
			$("#modalForm input[name=competitiveNum]").val("1");
		}else {
			$("#modalForm input[name=competitiveNum]").removeAttr("readonly")
		}
	}
	hasCompetitive.on('switchChange.bootstrapSwitch', function(event, state) {
		hasCompetitiveChange();
	});
	hasCompetitiveChange();

	function hasCandidateChange(){
		if (hasCandidate.bootstrapSwitch("state")){
			$("#modalForm .candidate").show();
		}else{
			$("#modalForm .candidate").hide();
			selectedUsers = [];
			$("#modalForm select[name=userId]").val("");
			$("#itemList tbody tr").remove();

		}
	}
	hasCandidate.on('switchChange.bootstrapSwitch', function(event, state) {
		hasCandidateChange();
	});
	hasCandidateChange();

	$("#submitBtn").click(function () {
		$("#modalForm").submit();
		return false;
	});

    $("#modalForm").validate({
        submitHandler: function (form) {
			var $btn = $("#committeeBtn").button('loading');
            $(form).ajaxSubmit({
				data: {items: $.base64.encode(JSON.stringify(selectedUsers))},
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
    $("#modalForm :checkbox").bootstrapSwitch();
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $('textarea.limited').inputlimiter();
    $.register.date($('.date-picker'));
</script>