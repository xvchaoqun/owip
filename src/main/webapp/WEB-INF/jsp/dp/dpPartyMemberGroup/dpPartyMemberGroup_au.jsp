<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${dpPartyMemberGroup!=null?'编辑':'添加'}民主党派委员会</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/dp/dpPartyMemberGroup_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${dpPartyMemberGroup.id}">
		<div class="form-group">
			<label class="col-xs-3 control-label"><span class="star">*</span>所属民主党派</label>
			<c:if test="${empty dpPartyMemberGroup}">

				<div class="col-xs-8">
					<select required data-rel="select2-ajax" data-width="292"
							data-ajax-url="${ctx}/dp/dpParty_selects?auth=1"
							name="partyId" data-placeholder="请选择">
						<option></option>
					</select>
				</div>
			</c:if>
			<c:if test="${not empty dpPartyMemberGroup}">
				<div class="col-xs-8 label-text">
					<input type="hidden" name="partyId" value="${dpParty.id}">
						${dpParty.name}
				</div>
			</c:if>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label">上一届班子</label>
			<div class="col-xs-8">
				<div class="help-block">
					<select class="form-control" name="fid" data-width="292"
							data-rel="select2-ajax" data-ajax-url="${ctx}/dp/dpPartyMemberGroup_selects?partyId=${dpParty.id}"
							data-placeholder="请选择班子">
						<option value="${fDpPartyMemberGroup.id}">${fDpPartyMemberGroup.name}</option>
					</select>
				</div>
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label"><span class="star">*</span>名称</label>
			<div class="col-xs-8" style="width: 312px">
				<textarea required class="form-control" name="name">${dpPartyMemberGroup.name}</textarea>
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label">是否现任班子</label>
			<div class="col-xs-8">
				<label>
					<input name="isPresent" ${dpPartyMemberGroup.isPresent?"checked":""} type="checkbox"/>
					<span class="lbl"></span>
				</label>
				<span class="help-block">注：每个民主党派必须设定一个“委员会”</span>
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label"><span class="star">*</span>任命时间</label>
			<div class="col-xs-8">
				<div class="input-group" style="width: 150px">
					<input required class="form-control date-picker" name="_appointTime" type="text"
						   data-date-format="yyyy-mm-dd"
						   value="${cm:formatDate(dpPartyMemberGroup.appointTime,'yyyy-MM-dd')}"/>
					<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
				</div>
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label"><span class="star">*</span>应换届时间</label>
			<div class="col-xs-8">
				<div class="input-group" style="width: 150px">
					<input required class="form-control date-picker" name="_tranTime" type="text"
						   data-date-format="yyyy-mm-dd"
						   value="${cm:formatDate(dpPartyMemberGroup.tranTime,'yyyy-MM-dd')}"/>
					<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
				</div>
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label">实际换届时间</label>
			<div class="col-xs-8">
				<div class="input-group" style="width: 150px">
					<input class="form-control date-picker" name="_actualTranTime" type="text"
						   data-date-format="yyyy-mm-dd"
						   value="${cm:formatDate(dpPartyMemberGroup.actualTranTime,'yyyy-MM-dd')}"/>
					<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
				</div>
				<span class="help-block">注：实际换届时填写，即将现任班子改为历任班子时</span>
			</div>
		</div>
		<%--<div class="form-group">
            <label class="col-xs-3 control-label">发文</label>
            <div class="col-xs-8">
                <select data-rel="select2-ajax" data-width="272" data-ajax-url="${ctx}/dispatchUnit_selects?unitId=${dp.unitId}"
                        name="dispatchUnitId" data-placeholder="请选择单位发文">
                    <option value="${dpPartyMemberGroup.dispatchUnitId}">${cm:getDispatchCode(dispatch.code, dispatch.dispatchTypeId, dispatch.year )}</option>
                </select>
            </div>
        </div>--%>
	</form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty dpPartyMemberGroup?'确定':'添加'}</button>
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
    $("#modalForm :checkbox").bootstrapSwitch();
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $('textarea.limited').inputlimiter();
	$.register.date($('.date-picker'));
</script>