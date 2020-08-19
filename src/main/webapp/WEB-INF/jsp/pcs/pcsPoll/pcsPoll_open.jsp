<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set value="<%=PcsConstants.PCS_POLL_STAGE_MAP%>" var="PCS_POLL_STAGE_MAP"/>
<c:set value="<%=PcsConstants.PCS_POLL_FIRST_STAGE%>" var="PCS_POLL_FIRST_STAGE"/>
<c:set value="<%=PcsConstants.PCS_POLL_SECOND_STAGE%>" var="PCS_POLL_SECOND_STAGE"/>
<c:set value="<%=PcsConstants.PCS_POLL_THIRD_STAGE%>" var="PCS_POLL_THIRD_STAGE"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>启动支部投票</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/pcs/pcsPoll_open" autocomplete="off" disableautocomplete id="pcsPollForm" method="post">

		<div class="form-group">
			<label class="col-xs-4 control-label"><span class="star">*</span> 所属${_p_partyName}</label>
			<div class="col-xs-6">
				<select required class="form-control" data-rel="select2-ajax" data-callback="_selectPartyCallback"
						data-ajax-url="${ctx}/party_selects?auth=1&pcsConfigId=${_pcsConfig.id}"
						name="partyId" data-placeholder="请选择" data-width="270">
					<option value="${pcsParty.partyId}">${pcsParty.name}</option>
				</select>
			</div>
		</div>
		<script>
			var $party = $.register.ajax_select("#pcsPollForm select[name=partyId]");
			$party.on("change", function () {

				var partyId = $.trim($(this).val());
				$.get("${ctx}/pcs/pcsPoll_stage?partyId="+partyId, function(stage){

					$("#submitBtn").prop("disabled", false);
					if(stage<=0){
						$("#stage").html("${PCS_POLL_STAGE_MAP.get(PCS_POLL_FIRST_STAGE)}");
					}else if(stage=='${PCS_POLL_FIRST_STAGE}'){
						$("#stage").html("${PCS_POLL_STAGE_MAP.get(PCS_POLL_SECOND_STAGE)}");
					}else if(stage=='${PCS_POLL_SECOND_STAGE}'){
						$("#stage").html("${PCS_POLL_STAGE_MAP.get(PCS_POLL_THIRD_STAGE)}");
					}else{
						$("#stage").html("所在党委的支部投票已全部启动，无需在此操作");
						$("#submitBtn").prop("disabled", true);
					}
				})
			})
		</script>
		<div class="form-group">
			<label class="col-xs-4 control-label"><span class="star">*</span> 待启动投票阶段</label>
			<div class="col-xs-6 label-text">
				<span id="stage"></span>
			</div>
		</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> 确定启动支部投票</button>
</div>
<script>

	//控制时间
	$('input[name=startTime]').focus(function () {
		$('#tipSt').text('')
	})
	$('input[name=endTime]').on('change', function () {
		var st = $('input[name=startTime]')
		var et = $('input[name=endTime]')
		//console.log(st.val())
		if (st.val() == undefined || st.val().length == 0){
			et.val('')
			//console.log(et.val())
			$('#tipSt').text('请先填写投票起始时间')
		}else if (st.val() >= et.val()){
			st.val('')
			et.val('')
			$('#tipEt').text('截止时间应晚于起始时间')
		}else if (st.val() < et.val()){
			$('#tipEt').text('')
		}
	})

    $("#submitBtn").click(function(){$("#pcsPollForm").submit();return false;});
    $("#pcsPollForm").validate({
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
    $('#pcsPollForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
	$.register.datetime($('.datetime-picker'));
	$.register.date($('.date-picker'));
</script>