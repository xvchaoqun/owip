<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${drOnlinePost!=null?'编辑':'添加'}推荐职务</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/dr/drOnlinePost_au?onlineId=${onlineId}" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${drOnlinePost.id}">
		<input type="hidden" name="onlineId" value="${onlineId}">
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 推荐职务</label>
				<div class="col-xs-6">
					<input type="hidden" name="recordId" value="${scRecord.id}">
					<input required oninvalid="" id="postName" readonly class="form-control" type="text" value="${scRecord.postName}">
				</div>
				<button type="button" class="btn btn-sm btn-success" id="selectPostBtn"><i class="fa fa-plus"></i> 选择岗位
				</button>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"> 是否差额</label>
				<div class="col-xs-6">
					<label>
						<input name="hasCompetitive" ${drOnlinePost.hasCompetitive?"checked":""} type="checkbox"/>
						<span class="lbl"></span>
					</label>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 最多推荐人数</label>
				<div class="col-xs-6">
					<input required class="form-control" type="text" oninput="value=value.replace(/[^\d]/g,'')"  name="competitiveNum" placeholder="请填写阿拉伯数字！" value="${drOnlinePost.competitiveNum}">
				</div>
			</div>
			<div class="form-group" id="hasCandidate">
				<label class="col-xs-3 control-label"> 是否有候选人</label>
				<div class="col-xs-6">
					<label>
						<input name="hasCandidate" ${(drOnlinePost.hasCandidate)?"checked":""} type="checkbox"/>
						<span class="lbl"></span>
					</label>
				</div>
			</div>
			<div class="form-group candidate">
				<label class="col-xs-3 control-label"><span class="star">*</span>候选人</label>
				<div class="col-xs-6">
					<select class="selectpicker" multiple data-live-search="true" data-rel="select2-ajax"
						   data-ajax-url="${ctx}/sysUser_selects"
						   name="candidates" data-width="270"
						   data-placeholder="请输入账号或姓名或学工号">
						<option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6"	>
							<textarea class="form-control limited noEnter" type="text"
									  name="remark" rows="3">${drOnlinePost.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty drOnlinePost?'确定':'添加'}</button>
</div>
<style>
	.popover {
		width:auto;
		max-width:900px;
	}
</style>
<script>

	var hasCandidate =  $("#modalForm input[name=hasCandidate]");
	var hasCompetitive = $("#modalForm input[name=hasCompetitive]");

	function hasCompetitiveChange() {
		if (!hasCompetitive.bootstrapSwitch("state")){
			$("#modalForm input[name=competitiveNum]").attr("readonly","readonly");
			$("#modalForm input[name=competitiveNum]").val("1");
		}else {
			$("#modalForm input[name=competitiveNum]").removeAttr("readonly")
		}
	}
	hasCompetitive.on('switchChange.bootstrapSwitch', function(event, state) {
		hasCompetitiveChange();
	});
	hasCompetitiveChange();

	function hasCandidateChange(){
		if (hasCandidate.bootstrapSwitch("state")){
			$("#modalForm .candidate").show();
			$("#modalForm select[name=candidates]").attr("required", "true");
		}else{
			$("#modalForm .candidate").hide();
			$("#modalForm select[name=candidates]").removeAttr("required");
		}
	}
	hasCandidate.on('switchChange.bootstrapSwitch', function(event, state) {
		hasCandidateChange();
	});
	hasCandidateChange();

	$("#modal .closeBtn").click(function () {
		$('#modalForm #postName').popover('hide');
		$("#modal").modal('hide');
	})
	function _closePopover(){
		$('#modalForm #postName')
				.popover('hide');
	}
	$('#modalForm #postName')
			.popover({
				container:'body',
				placement: "bottom",
				title: '请选择岗位（从干部选任纪实-单个岗位调整（选拔方式为“民主推荐”）中选择岗位）<a href="javascript:;" onclick="_closePopover()" class="pull-right">关闭</a>',
				html:true,
				content: '<div id="popoverContent" style="width:850px;text-align: center;"><i class="fa fa-spinner fa-spin"></i> 加载中...</div>',
				trigger: "manual"});
	$("#modalForm #selectPostBtn").click(function () {
		$('#modalForm #postName').popover('show');
	})
	$('#modalForm #postName').on('shown.bs.popover', function () {
		$.get("${ctx}/dr/drOnline_selectPost",function(html){
			$("#popoverContent").html(html);
		})
	})
	function _drOnline_selectPost(scRecordId, postName){

		$("#modalForm input[name=recordId]").val(scRecordId);
		$("#postName").val(postName);
		_closePopover()
	}

	$("#submitBtn").click(function () {
		if($.trim($("#modalForm input[name=recordId]").val())==''){
			$.tip({
				$target: $("#selectPostBtn").closest("form").find("button"),
				at: 'right center', my: 'left center', type: 'info',
				msg: "请选择推荐岗位。"
			});
		}
		$("#modalForm").submit();
		return false;
	});

    $("#modalForm").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $("#jqGrid2").trigger("reloadGrid");
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