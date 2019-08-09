<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${psAdminParty!=null?'编辑':'添加'}二级党校管理员管理的单位</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/ps/psAdminParty_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${psAdminParty.id}">
		<input type="hidden" name="adminId" value="${psAdmin.id}">
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span> 管理的单位名称</label>
            <div class="col-xs-6">
                <select name="partyId" required class="form-control"
						data-width="272"
                        data-rel="select2-ajax"
                        data-ajax-url="${ctx}/ps/psParty_selects?psId=${psAdmin.psId}"
                        data-placeholder="请选择">
                    <option value="${party.id}">${party.name}</option>
                </select>
            </div>
        </div>
		<div class="form-group">
			<label class="col-xs-3 control-label"> 开始管理时间</label>
			<div class="col-xs-6">
				<div class="input-group">
					<input class="form-control date-picker" name="startDate" type="text"
						   data-date-format="yyyy.mm.dd"
						   value="${empty psAdminParty?cm:formatDate(now,'yyyy.MM.dd'):cm:formatDate(psAdminParty.startDate,'yyyy.MM.dd')}"/>
					<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
				</div>
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label"> 是否结束管理</label>
			<div class="col-xs-6">
				<input name="isHistory" ${psAdminParty.isHistory?"checked":""}  type="checkbox" />
			</div>
		</div>
		<div class="form-group isHistory" style="${psAdminParty.isHistory?'display:none':''}">
			<label class="col-xs-3 control-label"> 结束管理时间</label>
			<div class="col-xs-6">
				<div class="input-group">
					<input class="form-control date-picker" name="endDate" type="text"
						   data-date-format="yyyy.mm.dd"
						   value="${cm:formatDate(psAdminParty.endDate,'yyyy.MM.dd')}"/>
					<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
				</div>
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label"> 备注</label>
			<div class="col-xs-6">
				<textarea class="form-control" name="remark">${psAdminParty.remark}</textarea>
			</div>
		</div>
    </form>
</div>
<div class="modal-footer">
	<a href="javascript:;" onclick="openView(${psAdmin.id})" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty psAdminParty?'确定':'添加'}</button>
</div>
<script>

	$("#modal :checkbox").bootstrapSwitch();
	function isHistoryChange(){
		if($("#modalForm input[name=isHistory]").bootstrapSwitch("state")){
			$("#modalForm .isHistory").show();
		}else{
			$("#modalForm .isHistory").hide();
		}
	}
	$('#modalForm input[name=isHistory]').on('switchChange.bootstrapSwitch', function(event, state) {
		isHistoryChange();
	});

	isHistoryChange();

    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
						openView(${psAdmin.id});
						$("#jqGrid2").trigger("reloadGrid");
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
    $.register.date($('.date-picker'));
</script>