<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set value="<%=PcsConstants.PCS_POLL_STAGE_MAP%>" var="PCS_POLL_STAGE_MAP"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${pcsPoll!=null?'编辑':'添加'}党代会投票</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/pcs/pcsPoll_au" autocomplete="off" disableautocomplete id="pcsPollForm" method="post">
        <input type="hidden" name="id" value="${pcsPoll.id}">
		<input type="hidden" name="configId" value="${pcsConfig.id}">
		<div class="form-group">
			<label class="col-xs-4 control-label"> 所属党代会</label>
			<div class="col-xs-6 label-text">
				${pcsConfig.name}
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-4 control-label"><span class="star">*</span> 投票名称</label>
			<div class="col-xs-6">
				<input required class="form-control" type="text" name="name" value="${pcsPoll.name}">
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-4 control-label"><span class="star">*</span> 所属${_p_partyName}</label>
			<div class="col-xs-6">
				<select required class="form-control" data-rel="select2-ajax"
						data-ajax-url="${ctx}/party_selects?auth=1"
						name="partyId" data-placeholder="请选择" data-width="270">
					<option value="${party.id}">${party.name}</option>
				</select>
			</div>
		</div>
		<div class="form-group" style="${(empty branch)?'display: none':''}" id="branchDiv">
			<label class="col-xs-4 control-label"><span class="star">*</span> 所属党支部</label>
			<div class="col-xs-6">
				<select required class="form-control" data-rel="select2-ajax" data-ajax-url="${ctx}/branch_selects?auth=1"
						name="branchId" data-placeholder="请选择" data-width="270">
					<option value="${branch.id}">${branch.name}</option>
				</select>
			</div>
		</div>
		<script>
			$.register.party_branch_select($("#pcsPollForm"), "branchDiv",
					'${cm:getMetaTypeByCode("mt_direct_branch").id}', "${party.id}", "${party.classId}");
		</script>
		<div class="form-group">
			<label class="col-xs-4 control-label"><span class="star">*</span> 投票阶段</label>
			<div class="col-xs-6">
				<select required data-rel="select2" name="isSecond" data-width="270"
						data-placeholder="请选择">
					<option></option>
					<c:forEach items="${PCS_POLL_STAGE_MAP}" var="entry">
						<option value="${entry.key}">${entry.value}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<script> $("#pcsPollForm select[name=isSecond]").val(${not empty pcsPoll.isSecond?(pcsPoll.isSecond?1:0):''}) </script>
		<div class="form-group">
			<label class="col-xs-4 control-label"><span class="star">*</span> 代表最大推荐人数</label>
			<div class="col-xs-6">
				<input required style="width: 78px;" ${pcsPoll.hasReport||pcsPoll.inspectorFinishNum>0?"disabled":""} class="form-control digits" type="text"
					   name="prNum" value="${pcsPoll.prNum}" data-rule-min="0">
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-4 control-label"><span class="star">*</span> 党委委员最大推荐人数</label>
			<div class="col-xs-6">
				<input required style="width: 78px;" ${pcsPoll.hasReport||pcsPoll.inspectorFinishNum>0?"disabled":""} class="form-control digits" type="text"
					   name="dwNum" value="${pcsPoll.dwNum}" data-rule-min="0">
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-4 control-label"><span class="star">*</span> 党委委员最大推荐人数</label>
			<div class="col-xs-6">
				<input required style="width: 78px;" ${pcsPoll.hasReport||pcsPoll.inspectorFinishNum>0?"disabled":""} class="form-control digits" type="text"
					   name="jwNum" value="${pcsPoll.jwNum}" data-rule-min="0">
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-4 control-label"><span class="star">*</span> 投票起始时间</label>
			<div class="col-xs-6">
				<div class="input-group" style="width: 150px;">
					<input required class="form-control datetime-picker"
						   name="startTime"
						   type="text"
						   value="${(cm:formatDate(pcsPoll.startTime,'yyyy-MM-dd HH:mm'))}"/>
				</div>
				<span id="tipSt" style="color: red;"></span>
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-4 control-label"><span class="star">*</span> 投票截止时间</label>
			<div class="col-xs-6">
				<div class="input-group" style="width: 150px;">
					<input required class="form-control datetime-picker"
						   name="endTime"
						   type="text"
						   value="${(cm:formatDate(pcsPoll.endTime,'yyyy-MM-dd HH:mm'))}"/>
				</div>
				<span id="tipEt" style="color: red;"></span>
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-4 control-label"> 备注</label>
			<div class="col-xs-6">
                         <textarea class="form-control noEnter" rows="2" maxlength="100"
								   name="remark">${pcsPoll.remark}</textarea>
			</div>
		</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty pcsPoll?'确定':'添加'}</button>
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