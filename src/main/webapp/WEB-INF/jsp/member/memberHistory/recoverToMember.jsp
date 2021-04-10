<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>返回党员库</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/member/recoverToMember" autocomplete="off" disableautocomplete id="modalForm" method="post">
		<input type="hidden" name="ids" value="${param.ids}">
		<c:set var="count" value="${fn:length(fn:split(param.ids,\",\"))}"/>
		<div class="form-group">
			<label class="col-xs-3 control-label">已选人数</label>

			<div class="col-xs-6 label-text">
					${count} 个
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label"><span class="star">*</span> 所属${_p_partyName}</label>
			<div class="col-xs-6">
				<select required class="form-control" data-rel="select2-ajax"
						data-ajax-url="${ctx}/party_selects?auth=1"
						name="partyId" data-placeholder="请选择" data-width="270">
					<option value="${party.id}">${party.name}</option>
				</select>
			</div>
		</div>
		<div class="form-group" style="${(empty branch)?'display: none':''}" id="branchDiv">
			<label class="col-xs-3 control-label"><span class="star">*</span> 所属党支部</label>
			<div class="col-xs-6">
				<select class="form-control" data-rel="select2-ajax" data-ajax-url="${ctx}/branch_selects?auth=1"
						name="branchId" data-placeholder="请选择" data-width="270">
					<option value="${branch.id}">${branch.name}</option>
				</select>
			</div>
		</div>
		<script>
			$.register.party_branch_select($("#modalForm"), "branchDiv",
					'${cm:getMetaTypeByCode("mt_direct_branch").id}', "${party.id}", "${party.classId}");
		</script>
		<div class="form-group">
			<label class="col-xs-3 control-label"> 备注</label>
			<div class="col-xs-6">
				<textarea class="form-control" name="remark"></textarea>
			</div>
		</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin'></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> 确定</button>
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
                        _reload();
                    }
                    $btn.button('reset');
                }
            });
        }
    });
	$('textarea.limited').inputlimiter();
    //$("#modalForm :checkbox").bootstrapSwitch();
    $('[data-rel="tooltip"]').tooltip();
	$.register.date($('.date-picker'), {endDate: '${_today}'});
    //$('textarea.limited').inputlimiter();
    $.register.date($('.date-picker'));
</script>