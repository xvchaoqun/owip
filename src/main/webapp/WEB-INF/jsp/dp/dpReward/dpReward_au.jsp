<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${dpReward!=null?'编辑':'添加'}奖励情况</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/dp/dpReward_au?userId=${sysUser.userId}" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${dpReward.id}">
		<div class="form-group">
			<label class="col-xs-3 control-label"> 姓名</label>
			<div class="col-xs-6 label-text">
				${sysUser.realname}
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label"><span class="star">*</span>奖励类别</label>
			<div class="col-xs-6">
				<select required data-rel="select2" name="rewardType"
						data-placeholder="请选择">
					<option></option>
					<c:import url="/metaTypes?__code=mc_dp_reward_type"/>
				</select>
				<script type="text/javascript">
					$("#modalForm select[name=rewardType]").val(${dpReward.rewardType});
				</script>
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label">奖励级别</label>
			<div class="col-xs-6">
				<select data-rel="select2" name="rewardLevel"
						data-placeholder="请选择">
					<option></option>
					<c:import url="/metaTypes?__code=mc_reward_level"/>
				</select>
				<script type="text/javascript">
					$("#modalForm select[name=rewardLevel]").val(${dpReward.rewardLevel});
				</script>
			</div>
		</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">获奖年月</label>
				<div class="col-xs-6">
					<div class="input-group" style="width: 120px">
						<input class="form-control date-picker" name="_rewardTime" type="text"
							   data-date-min-view-mode="1" placeholder="yyyy.mm"
							   data-date-format="yyyy.mm" value="${cm:formatDate(dpReward.rewardTime,'yyyy.MM')}" />
						<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 获得奖项</label>
				<div class="col-xs-6">
					<textarea required class="form-control" name="name">${dpReward.name}</textarea>
					<span class="help-block">注：不要加书名号。</span>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"> 颁奖单位</label>
				<div class="col-xs-6">
					<textarea class="form-control" name="unit">${dpReward.unit}</textarea>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"> 获奖证书</label>
				<div class="col-xs-6">
                        <input class="form-control" type="file" name="_proof">
				</div>
			</div>
		<div class="form-group">
			<label class="col-xs-3 control-label">是否独立获奖</label>
			<div class="col-xs-6">
				<label>
					<input name="isIndependent" ${dpReward.isIndependent?"checked":""}  type="checkbox" />
					<span class="lbl"></span>
				</label>
			</div>
		</div>
		<div class="form-group" id="rankDiv">
			<label class="col-xs-3 control-label">排名</label>
			<div class="col-xs-6">
				第 <input type="text" class="digits" data-rule-min="1" name="rank" style="width: 50px;" value="${dpReward.rank}"> 名
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label">备注</label>
			<div class="col-xs-6">
				<textarea class="form-control" name="remark">${dpReward.remark}</textarea>
			</div>
		</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty dpReward?'确定':'添加'}</button>
</div>
<script src="${ctx}/assets/js/fuelux.spinner.js"></script>
<script src="${ctx}/assets/js/ace/elements.spinner.js"></script>
<script>

	function isIndependentChange(){
		if($("input[name=isIndependent]").bootstrapSwitch("state")){
			$("#rankDiv").hide();
			//$("input[name=rank]").removeAttr("required");
		}else{
			$("#rankDiv").show();
			//$("input[name=rank]").attr("required", "required");
		}
	}
	$('input[name=isIndependent]').on('switchChange.bootstrapSwitch', function(event, state) {
		isIndependentChange();
	});
	isIndependentChange();

    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $("#jqGrid_dpReward").trigger("reloadGrid");
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
	$.register.date($('.date-picker'));
    $.fileInput($('#modalForm input[type=file]'))
</script>