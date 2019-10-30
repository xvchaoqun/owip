<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${dpParty!=null}">编辑</c:if><c:if test="${dpParty==null}">添加</c:if>民主党派</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/dp/dpParty_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${dpParty.id}">
		<div class="form-group">
			<label class="col-xs-3 control-label"><span class="star">*</span>编号</label>
			<div class="col-xs-6">
				<input required class="form-control" type="text" name="code" value="${dpParty.code}">
			</div>
		</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 名称</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="name" value="${dpParty.name}">
				</div>
			</div>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span> 简称</label>
            <div class="col-xs-6">
                <input required class="form-control" type="text" name="shortName" value="${dpParty.shortName}">
            </div>
        </div>
		<div class="form-group">
			<label class="col-xs-3 control-label"><span class="star">*</span>所属单位</label>
			<div class="col-xs-6">
				<c:set var="unit" value="${cm:getUnitById(dpParty.unitId)}"/>
				<select required data-width="270" data-rel="select2-ajax" data-ajax-url="${ctx}/unit_selects"
						name="unitId" data-placeholder="请选择">
					<option value="${unit.id}" title="${unit.status==UNIT_STATUS_HISTORY}">${unit.name}</option>
				</select>
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label"><span class="star">*</span>民主党派类别</label>
			<div class="col-xs-6">
				<select required data-width="270" class="form-control" name="classId" data-rel="select2"
						data-placeholder="请选择分类">
					<option></option>
					<c:import url="/metaTypes?__code=mc_dp_party_class"/>
				</select>
				<script>
					$("#modalForm select[name=classId]").val('${dpParty.classId}');
				</script>
			</div>
		</div>
        <div class="form-group">
            <label class="col-xs-3 control-label">联系电话</label>
            <div class="col-xs-6">
                <input class="form-control" type="text" name="phone" value="${dpParty.phone}">
            </div>
        </div>
			<div class="form-group">
				<label class="col-xs-3 control-label">传真</label>
				<div class="col-xs-6">
                        <input class="form-control" type="text" name="fax" value="${dpParty.fax}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">邮箱</label>
				<div class="col-xs-6">
                        <input class="form-control" type="text" name="email" value="${dpParty.email}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">信箱</label>
				<div class="col-xs-6">
                        <input class="form-control" type="text" name="mailbox" value="${dpParty.mailbox}">
				</div>
			</div>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>成立时间</label>
            <div class="col-xs-6" style="width: 223px">
                <div class="input-group date" data-date-format="yyyy.mm.dd">
                    <input required class="form-control date-picker" name="_foundTime" type="text"
                           placeholder="格式：yyyy.mm.dd"
                           value="${cm:formatDate(dpParty.foundTime,'yyyy.MM.dd')}"/>
                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                </div>
            </div>
        </div>
		<c:if test="${cls==2}">
		<div class="form-group">
			<label class="col-xs-3 control-label"><span class="star">*</span>撤销时间</label>
			<div class="col-xs-6" style="width: 223px">
				<div class="input-group date" data-date-format="yyyy.mm.dd">
					<input required class="form-control date-picker" name="deleteTime" type="text"
						   placeholder="格式：yyyy.mm.dd"
						   value="${cm:formatDate(dpParty.deleteTime,'yyyy.MM.dd')}"/>
					<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
				</div>
			</div>
		</div>
		</c:if>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty dpParty?'确定':'添加'}</button>
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
	$('#modalForm select[name=unitTypeId]').change(function () {
		if ($(this).val() == '${cm:getMetaTypeByCode('mt_party_unit_type_enterprise').id}') {
			$(".enterprise").show();
		} else {
			$(".enterprise :checkbox").prop("checked", false);
			$(".enterprise").hide();
		}
	}).change();

	$.register.del_select($('#modalForm select[name=unitId]'));
    $("#modalForm :checkbox").bootstrapSwitch();
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    //$('textarea.limited').inputlimiter();
    $.register.date($('.date-picker'));
    $.register.date($('.input-group.date'));
</script>