<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>无党派人士转出</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/dp/dpNpm_transfer" autocomplete="off" disableautocomplete id="modalForm" method="post">
		<input type="hidden" name="ids" value="${param.ids}">
		<c:set var="count" value="${fn:length(fn:split(param.ids,\",\"))}"/>
		<c:if test="${count>=1}">
			<div class="form-group">
				<label class="col-xs-4 control-label">无党派人士转出</label>
				<div class="col-xs-6 label-text">
						${count} 个
				</div>
			</div>
		</c:if>
		<div class="form-group">
			<label class="col-xs-4 control-label"><span class="star">*</span> 转出时间</label>
			<div class="col-xs-6">
				<div class="input-group" style="width: 270px">
					<input required class="form-control date-picker" name="transferTime" type="text"
						   data-date-format="yyyy.mm.dd"
						   value="${cm:formatDate(dpNpm.transferTime, 'yyyy.MM.dd')}"/>
					<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
				</div>
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-4 control-label"><span class="star">*</span>所属党派</label>
			<c:if test="${empty dpParty}">
				<div class="col-xs-6">
					<select required data-rel="select2-ajax" data-width="270"
							data-ajax-url="${ctx}/dp/dpParty_selects?auth=1"
							name="partyId" data-placeholder="请选择">
						<option></option>
					</select>
				</div>
			</c:if>
			<c:if test="${not empty dpParty}">
				<div class="col-xs-6">
					<select required data-rel="select2-ajax" data-width="270"
							data-ajax-url="${ctx}/dp/dpParty_selects?auth=1"
							name="partyId">
						<option value="${dpParty.id}">${dpParty.name}</option>
					</select>
				</div>
			</c:if>
		</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty dpNpm?'确定':'转出'}</button>
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
    $('[data-rel="tooltip"]').tooltip();
	$.register.date($('.date-picker'), {endDate: '${_today}'});
    //$('textarea.limited').inputlimiter();
    $.register.date($('.date-picker'));
</script>