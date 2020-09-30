<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${spNpc!=null?'编辑':'添加'}人大代表和政协委员</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/sp/spNpc_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${spNpc.id}">
		<div class="form-group">
			<label class="col-xs-3 control-label"><span class="star">*</span> 姓名</label>
			<div class="col-xs-6">
				<select data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects" data-width="270"
						name="userId" data-placeholder="请输入账号或姓名或学工号">
					<option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
				</select>
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label"><span class="star">*</span> 类别</label>
			<div class="col-xs-6">
				<select class="col-xs-6" required name="type" data-width="270"
						data-rel="select2" data-placeholder="请选择">
					<option></option>
					<c:import url="/metaTypes?__code=mc_sp_npc_type"/>
				</select>
			</div>
			<script>
				$("#modalForm select[name=type]").val('${spNpc.type}');
			</script>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label"><span class="star">*</span> 届次</label>
			<div class="col-xs-6">
				<input required class="form-control" type="text" name="th" value="${spNpc.th}">
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label"> 政治面貌</label>
			<div class="col-xs-6">
				<select class="col-xs-6" name="politicsStatus" data-width="270"
						data-rel="select2" data-placeholder="请选择">
					<option></option>
					<c:import url="/metaTypes?__code=mc_political_status"/>
				</select>
			</div>
			<script>
				$("#modalForm select[name=politicsStatus]").val('${spNpc.politicsStatus}');
			</script>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label"> 人大/政协职务</label>
			<div class="col-xs-6">
				<input class="form-control" type="text" name="npcPost" value="${spNpc.npcPost}">
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label"> 所在单位</label>
			<div class="col-xs-6">
				<select name="unitId" data-rel="select2-ajax" data-ajax-url="${ctx}/unit_selects"
						data-width="270" data-placeholder="请选择">
					<option value="${unit.id}" delete="${unit.status==UNIT_STATUS_HISTORY}">${unit.name}</option>
				</select>
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label"> 当选时职务</label>
			<div class="col-xs-6">
				<input class="form-control" type="text" name="electedPost" value="${spNpc.electedPost}">
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label"> 现任职务</label>
			<div class="col-xs-6">
				<input class="form-control" type="text" name="post" value="${spNpc.post}">
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label"> 联系方式</label>
			<div class="col-xs-6">
				<input class="form-control" type="text" name="phone" value="${spNpc.phone}">
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label"> 备注</label>
			<div class="col-xs-6">
				<textarea class="form-control limited noEnter" name="remark">${spNpc.remark}</textarea>
			</div>
		</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i>
		${not empty spNpc?'确定':'添加'}</button>
</div>
<script>

	$("#modalForm select[name=userId]").change(function () {

		var userId = $(this).val();
		$.post("${ctx}/sp/spNpc_details",{userId:userId},function (ret) {

			var cadre = ret.cadre;

			if (cadre != undefined){

				$("input[name=phone]").val(cadre.mobile);
				$("input[name=electedPost]").val(cadre.title);
				$("input[name=post]").val(cadre.title);
			}else {

				$("input[name=phone]").val(null);
				$("input[name=electedPost]").val(null);
				$("input[name=post]").val(null);
			}
		});
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
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    //$("#modalForm :checkbox").bootstrapSwitch();
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $('#modalForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$('textarea.limited').inputlimiter();
    //$.register.date($('.date-picker'));
</script>