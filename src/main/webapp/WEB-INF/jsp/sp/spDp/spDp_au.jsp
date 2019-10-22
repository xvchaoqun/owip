<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${spDp!=null?'编辑':'添加'}民主党派主委</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/sp/spDp_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${spDp.id}">
			<div class="form-group" id="firstDiv">
				<label class="col-xs-3 control-label"><span class="star">*</span> 民主党派机构</label>
				<div class="col-xs-6">
					<select required data-rel="select2" name="dp"
							data-placeholder="请选择" data-width="270">
						<option></option>
					</select>
				</div>
			</div>
			<div class="form-group" id="secondDiv">
				<label class="col-xs-3 control-label"><span class="star">*</span> 职务</label>
				<div class="col-xs-6">
					<select required data-rel="select2" name="dpPost"
							data-placeholder="请选择" data-width="270">
						<option></option>
					</select>
				</div>
			</div>
		<script>
			$.register.layer_type_select("firstDiv", "secondDiv",
					${cm:toJSONArrayWithFilter(cm:getLayerTypes("lt_spDp"), "id,name,children,children.id,children.name")}
					, '${spDp.dp}', '${spDp.dpPost}');
		</script>

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
				<label class="col-xs-3 control-label"> 所在单位</label>
				<div class="col-xs-6">
					<select name="unitId" data-rel="select2-ajax" data-ajax-url="${ctx}/unit_selects"
							data-width="270" data-placeholder="请选择">
						<option value="${unit.id}" delete="${unit.status==UNIT_STATUS_HISTORY}">${unit.name}</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"> 专业技术职务</label>
				<div class="col-xs-6">
                        <input class="form-control" type="text" name="proPost" value="${spDp.proPost}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"> 联系方式</label>
				<div class="col-xs-6">
                        <input class="form-control" type="text" name="phone" value="${spDp.phone}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"> 备注</label>
				<div class="col-xs-6">
					<textarea class="form-control" type="text" name="remark">${spDp.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty spDp?'确定':'添加'}</button>
</div>
<script>
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