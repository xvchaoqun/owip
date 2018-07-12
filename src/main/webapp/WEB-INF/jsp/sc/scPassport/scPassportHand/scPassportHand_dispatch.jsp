<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>新提任干部</h3>
</div>
<div class="modal-body ">
    <form class="form-horizontal" action="${ctx}/sc/scPassportHand_dispatch" id="modalForm" method="post">
        <input type="hidden" name="id" value="${scPassportHand.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">年份</label>
				<div class="col-xs-6">
					<div class="input-group">
						<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
						<input style="width: 80px;" class="form-control date-picker" placeholder="请选择年份" name="year" type="text"
							   data-date-format="yyyy" data-date-min-view-mode="2" value="${dispatch.year}"/>
					</div>
				</div>
			</div>
		<div class="form-group">
				<label class="col-xs-3 control-label">发文类型</label>
				<div class="col-xs-6">
					<select data-rel="select2-ajax" data-ajax-url="${ctx}/dispatchType_selects"
							name="dispatchTypeId" data-placeholder="请选择发文类型">
						<option value="${dispatchType.id}">${dispatchType.name}</option>
					</select>
				</div>
			</div>
		<div class="form-group">
				<label class="col-xs-3 control-label">任免文件</label>
				<div class="col-xs-6">
					<select data-rel="select2-ajax" data-ajax-url="${ctx}/dispatch_selects"
							name="dispatchId" data-placeholder="请选择发文">
						<option value="${dispatch.id}">${cm:getDispatchCode(dispatch.code, dispatch.dispatchTypeId, dispatch.year)}</option>
					</select>
					<button type="button" class="btn btn-success btn-sm" onclick="_selectDispatch()"><i class="fa fa-plus"></i> 提取
					</button>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">新提任干部</label>
				<div class="col-xs-9">
					<div style="padding-top: 10px;">
						<div id="itemList">

						</div>
					</div>
				</div>
			</div>

    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${scPassportHand!=null}">确定</c:if><c:if test="${scPassportHand==null}">添加</c:if></button>
</div>
<style>
	#itemList table td {
		text-align: center;
	}
</style>
<script type="text/template" id="itemListTpl">
	<table class="table table-striped table-bordered table-condensed table-unhover2">
		<thead>
		<tr>
			<td colspan="4">新提任干部</td>
		</tr>
		<tr>
			<td>发文号</td>
			<td>姓名</td>
			<td>工号</td>
			<td></td>
		</tr>
		</thead>
		<tbody>
		{{_.each(users, function(u, idx){ }}
		<tr data-id="{{=u.dispatchCadreId}}">
			<td>{{=u.dispatchCode}}</td>
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

	$.register.date($('.date-picker'));
	var $dispatchSelect = $.register.dispatch_select($('#modalForm select[name=dispatchTypeId]'),
			$("#modalForm input[name=year]"), $("#modalForm select[name=dispatchId]"));
	/*$dispatchSelect.on("change", function () {
		var id = $(this).val();
		if (id > 0) {
			//console.log(id)
		}
	});*/

	var selectedUsers = [];
	function _selectDispatch() {

		var $select = $("#modalForm select[name=dispatchId]");
		var dispatchId = $.trim($select.val());
		if (dispatchId == '') {
			$.tip({
				$target: $select.closest("div").find(".select2-container"),
				at: 'top center', my: 'bottom center', type: 'success',
				msg: "请选择任免文件。"
			});
			return;
		}

		$.getJSON("${ctx}/sc/scPassportHand_dispatch_draw",{dispatchId:dispatchId},function(dispatchCadres){

				if(dispatchCadres.length==0){
					$.tip({
						$target: $select.closest("div").find(".select2-container"),
						at: 'top center', my: 'bottom center',
						msg: "没有符合条件的提任干部。"
					});
					return;
				}
				$.each( dispatchCadres, function(i, d){
					//console.log(d)
					var user = {dispatchCadreId: d.id,
						dispatchCode: d.dispatch.dispatchCode,
						realname: d.user.realname, code: d.user.code};
					var contains = false;
					$.each(selectedUsers, function (i, user) {
						if (user.dispatchCadreId == d.id) {
							contains = true;
							return false;
						}
					})
					if(!contains) selectedUsers.push(user);
				})
				//console.log(selectedUsers)
				$("#itemList").empty().append(_.template($("#itemListTpl").html())({users: selectedUsers}));
		})
	}

	$(document).off("click","#itemList .del")
			.on('click', "#itemList .del", function(){
				var $tr = $(this).closest("tr");
				var id = $tr.data("id");
				//console.log("id=" + id)
				$.each(selectedUsers, function (i, user) {
					if (user.dispatchCadreId == id) {
						selectedUsers.splice(i, 1);
						return false;
					}
				})
				$(this).closest("tr").remove();
			})


    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {

			var dispatchCadreIds = $.map(selectedUsers, function (user) {
				return user.dispatchCadreId;
			})

            $(form).ajaxSubmit({
				data: {dispatchCadreIds: dispatchCadreIds},
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
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#modalForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$('textarea.limited').inputlimiter();
    //$.register.date($('.date-picker'));
</script>