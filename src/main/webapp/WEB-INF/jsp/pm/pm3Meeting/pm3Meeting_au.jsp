<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="../constants.jsp" %>
<div class="widget-box transparent">
	<div class="widget-header">
		<h4 class="widget-title lighter smaller">
			<a href="javascript:;" class="hideView btn btn-xs btn-success">
				<i class="ace-icon fa fa-backward"></i>
				返回</a>
		</h4>
	</div>
	<div class="widget-body">
		<div class="widget-main" style="width: 900px">
			<form class="form-horizontal" action="${ctx}/pm/pm3Meeting_au?cls=${param.cls}&reedit=${param.reedit}" id="pmForm" method="post">
				<table class="table table-bordered table-unhover">
					<input class="form-control" type="hidden" name="id"
						   value="${param.id}">
					<c:if test="${adminOnePartyOrBranch==true}">
						<input class="form-control" type="hidden" name="partyId"
							   value="${pm3Meeting.partyId}">
						<input class="form-control" type="hidden" name="branchId"
							   value="${pm3Meeting.branchId}">
					</c:if>
					<c:if test="${adminOnePartyOrBranch!=true}">
						<tr>
							<div class="form-group">
								<td><span class="star">*</span>所在党组织</td>
								<td colspan="3">
									<c:if test="${!edit}">
										${cm:displayParty(pm3Meeting.partyId,pm3Meeting.branchId)}
									</c:if>

									<c:if test="${edit}">
										<div class="col-xs-5">
											<select required class="form-control" data-rel="select2-ajax"
													data-ajax-url="${ctx}/party_selects?auth=1"
													name="partyId" data-placeholder="请选择${_p_partyName}" data-width="250">
												<option value="${pm3Meeting.partyId}">${pm3Meeting.party.name}</option>
											</select>
										</div>
										<div class="form-group" style="${(empty branch)?'display: none':''}" id="branchDiv">
												<%--<label class="col-xs-3 control-label">选择党支部</label>--%>
											<div class="col-xs-5" style="padding-left: 25px;">
												<select class="form-control"  data-rel="select2-ajax"
														data-ajax-url="${ctx}/branch_selects?del=0&auth=1"
														name="branchId" data-placeholder="请选择党支部" data-width="250">
													<option value="${pm3Meeting.branchId}">${pm3Meeting.branch.name}</option>
												</select>
											</div>
										</div>
									</c:if>
								</td>
							</div>

							<script>
								$.register.party_branch_select($("#pmForm"), "branchDiv",
										'${cm:getMetaTypeByCode("mt_direct_branch").id}', "${pm3Meeting.partyId}",
										"${pm3Meeting.party.classId}", "partyId", "branchId", true);
							</script>
						</tr>
					</c:if>
					<c:if test="${adminOnePartyOrBranch==true}">
						<td><span class="star">*</span>所在党组织</td>
						<td colspan="3">
								${cm:displayParty(pm3Meeting.partyId,pm3Meeting.branchId)}
								<%-- ${cm:displayParty(pm3Meeting.partyId,pm3Meeting.branchId)}--%>
						</td>
					</c:if>
					<tr>
						<td width="100"><span class="star">*</span>会议类型</td>
						<td colspan="3">
							<c:if test="${!edit}">
								${PM_3_BRANCH_MAP.get(pm3Meeting.type)}
							</c:if>
							<c:if test="${edit}">
								<div class="col-xs-5">
									<select required data-rel="select2"
											name="type1" data-width="250" data-placeholder="请选择">
										<option></option>
										<option value="1">支部委员会</option>
										<option value="0">党员集体活动</option>
									</select>
								</div>
								<div class="col-xs-5" id="typeDiv">
									<select data-rel="select2"
											name="type2" data-width="250" data-placeholder="请选择">
										<option></option>
										<c:forEach items="${PM_3_BRANCH_MAP}" var="entity" begin="1">
											<option value="${entity.key}">${entity.value}</option>
										</c:forEach>
									</select>
								</div>
							</c:if>
						</td>
					</tr>
					<tr>
						<td width="100"><span class="star">*</span>主题</td>
						<td colspan="3">
							<c:if test="${!edit}">
								${pm3Meeting.name}
							</c:if>
							<c:if test="${edit}">
								<input  required  class="form-control" type="text" name="name"
										value="${pm3Meeting.name}">
							</c:if>
						</td>
					</tr>
					<tr>
						<td><span class="star">*</span>开始时间</td>
						<td ${param.type!=5?'':'colspan="3"'}>
							<c:if test="${!edit}">
								${cm:formatDate(pm3Meeting.startTime, "yyyy-MM-dd HH:mm")}
							</c:if>
							<c:if test="${edit}">
								<div class="input-group" style="width: 200px">
									<input  required name="_startTime"  class="form-control datetime-picker" type="text"
											value="${cm:formatDate(pm3Meeting.startTime, "yyyy-MM-dd HH:mm")}">
									<span class="input-group-addon">
                                         <i class="fa fa-calendar bigger-110"></i>
                                </span>
								</div>
							</c:if>
						</td>
						<td><span class="star">*</span>结束时间</td>
						<td>
							<c:if test="${!edit}">
								${cm:formatDate(pm3Meeting.endTime, "yyyy-MM-dd HH:mm")}
							</c:if>
							<c:if test="${edit}">
								<div class="input-group" style="width: 200px">
									<input  required name="_endTime" class="form-control datetime-picker " type="text"
											value="${cm:formatDate(pm3Meeting.endTime, "yyyy-MM-dd HH:mm")}">
									<span class="input-group-addon">
                                         <i class="fa fa-calendar bigger-110"></i>
                                </span>
								</div>
							</c:if>
						</td>
					</tr>
					<tr>
						<td><span class="star">*</span>主持人</td>
						<td>
							<c:if test="${!edit}">
								${pm3Meeting.presenterUser.realname}
							</c:if>
							<c:if test="${edit}">
								<select  ${empty pm3Meeting.partyId&&empty pm3Meeting.branchId?'disabled="disabled"':''}
										required data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects"
										data-width="270" id="presenter" name="presenter" data-placeholder="请选择">
									<option value="${pm3Meeting.presenter}">${pm3Meeting.presenterUser.realname}-${pm3Meeting.presenterUser.code}</option>
								</select>
							</c:if>
						</td>

						<td><span class="star">*</span>记录人</td>
						<td>
							<c:if test="${!edit}">
								${pm3Meeting.recorderUser.realname}
							</c:if>
							<c:if test="${edit}">
								<select ${empty pm3Meeting.partyId&&empty pm3Meeting.branchId?'disabled="disabled"':''}
										required data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects"
										data-width="270" id="recorder" name="recorder" data-placeholder="请选择">
									<option value="${pm3Meeting.recorder}">${pm3Meeting.recorderUser.realname}-${pm3Meeting.recorderUser.code}</option>
								</select>
							</c:if>
						</td>
					</tr>

					<tr>
						<td><span class="star">*</span>${param.type!=5?"会议":"活动"}地点</td>
						<td colspan="3">
							<c:if test="${!edit}">
								${pm3Meeting.address}
							</c:if>
							<c:if test="${edit}">
								<input   required class="form-control" type="text" name="address"
										 value="${pm3Meeting.address}">
							</c:if>
						</td>
					</tr>
					<tr>
						<td>请假人员
							<c:if test="${edit}">
								<button type="button" id ="absent" ${empty pm3Meeting.partyId&&empty pm3Meeting.branchId?'disabled="disabled"':''}
										class="popupBtn btn btn-info btn-xs"
										data-width="900"
										data-url="${ctx}/pm/pm3Meeting_member?type=2&partyId=${pm3Meeting.partyId}&branchId=${pm3Meeting.branchId}">
									<i class="fa fa-plus-circle"></i> 选择</button>
							</c:if>
						</td>
						<td colspan="3">
							<div style="max-height:135px; overflow:auto;">
								<table id="absentTable"  class="table table-bordered table-condensed"
									   data-pagination="true" data-side-pagination="client" data-page-size="5">
									<thead>
									<tr>
										<th width="30%">工作证号</th>
										<th>党员姓名</th>
										<th>党内职务</th>
										<th>手机号</th>
										<c:if test="${edit}">
											<th></th>
										</c:if>
									</tr>
									</thead>
									<tbody>

									</tbody>
								</table>
							</div>
						</td>

					</tr>
					<tr>
						<td><span class="star">*</span>实到人数</td>
						<td>
							<c:if test="${!edit}">
								${pm3Meeting.attendNum}
							</c:if>
							<c:if test="${edit}">
								<input required class="form-control digits" type="text" name="attendNum"
									   value="${pm3Meeting.attendNum}">
							</c:if>
						</td>
						<td><span class="star">*</span>请假人数</td>
						<td>
							<c:if test="${!edit}">
								${pm3Meeting.absentNum}
							</c:if>
							<c:if test="${edit}">
								<input required class="form-control digits" type="text" name="absentNum"
									   value="${pm3Meeting.absentNum}">
							</c:if>
						</td>
					</tr>
					<tr>
						<td><span class="star">*</span>请假原因</td>
						<td colspan="3">
							<c:if test="${!edit}">
								${pm3Meeting.absentReason}
							</c:if>
							<c:if test="${edit}">
								<textarea required class="form-control" name="absentReason">${pm3Meeting.absentReason}</textarea>
								<span class="help-block">注：如果无缺席人员，请填写“无”</span>
							</c:if>
						</td>
					</tr>

					<tr>
						<td><span class="star">*</span>应到人数</td>
						<td>
							<c:if test="${!edit}">
								${empty pm3Meeting.dueNum?allMembersNum:pm3Meeting.dueNum}
							</c:if>
							<c:if test="${edit}">
								<input required class="form-control digits" type="text" name="dueNum"
									   value="${pm3Meeting.dueNum}">
							</c:if>
						</td>

						<td><span class="star">*</span>列席人员</td>
						<td>
							<c:if test="${!edit}">
								${pm3Meeting.invitee}
							</c:if>
							<c:if test="${edit}">
								<input required class="form-control" type="text" name="invitee"
									   value="${pm3Meeting.invitee}">
							</c:if>
						</td>
					</tr>

					<tr>
						<td style="padding: 80px"><span class="star">*</span>会议内容</td>
						<td colspan="3">
							<c:if test="${!edit}">
								${pm3Meeting.content}
							</c:if>
							<c:if test="${edit}">
                            <textarea  required name="content" rows="10"
									   style="width: 100%">${pm3Meeting.content}</textarea>
							</c:if>
						</td>
					</tr>

					<tr>
						<td>备注</td>
						<td colspan="3">
							<c:if test="${!edit}">
								${pm3Meeting.remark}
							</c:if>
							<c:if test="${edit}">
								<textarea class="form-control" type="text" name="remark">${pm3Meeting.remark}</textarea>
							</c:if>
						</td>
					</tr>
					<c:if test="${edit}">
						<tr>
							<td colspan="4">

								<div class="modal-footer center">

									<button id="pmSubmitBtn" class="btn btn-success btn-xlg"
											data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口">
										<i class="fa fa-check"></i> 确定
									</button>
								</div>

							</td>
						</tr>
					</c:if>
				</table>
			</form>
		</div>
	</div>
</div>
<style>
	.table.table-unhover > tbody > tr > td:nth-of-type(odd) {
		text-align: center;
		font-size: 18px;
		font-weight: bolder;
		vertical-align: middle;
	}

	textarea[name=content] {
		text-indent: 32px;
		line-height: 25px;
		/*font-family: "Arial";*/
		font-size: 16px;
		padding: 2px;
		margin: 2px;
		border: none;
		background: #FFFFFF url(/img/dot_25.gif);
		resize: none;
	}
</style>
<link href="${ctx}/extend/css/bootstrap-tagsinput.css" rel="stylesheet"/>
<script src="${ctx}/extend/js/bootstrap-tagsinput.min.js"></script>
<script type="text/template" id="seconder_tpl">
	{{_.each(users, function(u, idx){ }}

	<tr data-user-id="{{=u.userId}}">
		<td>{{=u.code}}</td>
		<td>{{=u.realname}}</td>
		<td>{{=u.partyPost}}</td>
		<td>{{=u.mobile}}</td>
		<c:if test="${edit}">
			<td>
				<button ${!allowModify?"":"disabled"} class="delRowBtn btn btn-danger btn-xs"><i class="fa fa-minus-circle"></i> 移除</button>
			</td>
		</c:if>
	</tr>
	{{});}}
</script>
<script>

	$('#pmForm #typeDiv').addClass('hidden');
	var type = $.trim(${pm3Meeting.type});
	function selectType(){
		//console.log(type)
		if (type==null||type==''){
			$('#pmForm #typeDiv select[name=type2]').prop("required",false);
			$('#pmForm #typeDiv').addClass('hidden');
		}else if (type==${PM_3_BRANCH_COMMITTEE}){
			$('#pmForm #typeDiv select[name=type2]').prop("required",false);
			$('#pmForm #typeDiv').addClass('hidden');
			$('#pmForm select[name=type1]').val(type);
		}else {
			$('#pmForm #typeDiv').removeClass('hidden');
			$('#pmForm #typeDiv select[name=type2]').prop('required',true);
			$('#pmForm select[name=type1]').val('0');
			$('#pmForm select[name=type2]').val(type);
		}
	}
	selectType();

	$('#pmForm select[name=type1]').on('change', function (){
		//console.log($('#pmForm select[name=type1]').val())
		if ($('#pmForm select[name=type1]').val()==${PM_3_BRANCH_COMMITTEE}){
			$('#pmForm #typeDiv select[name=type2]').prop("required",false);
			$('#pmForm #typeDiv select[name=type2]').val(null).trigger('change');
			$('#pmForm #typeDiv').addClass('hidden');
		}else {
			$('#pmForm #typeDiv').removeClass('hidden');
			$('#pmForm #typeDiv select[name=type2]').prop("required",true);
		}
	})

	var presenterSelect = $('#pmForm select[name="presenter"]');
	var recorderSelect = $('#pmForm select[name="recorder"]');

	$.register.datetime($('.datetime-picker'));

	$.register.user_select(presenterSelect);
	$.register.user_select(recorderSelect);

	$("#absentTable tbody").append(_.template($("#seconder_tpl").html())({users: ${cm:toJSONArray(pm3Meeting.absentList)}}));

	if(${adminOnePartyOrBranch==true}){
		$("#pmForm input[name=dueNum]").val(${memberCount});
	}

	var partySelect = $('#pmForm select[name="partyId"]');
	partySelect.on('change',function(){
		var partyId=partySelect.val();
		if ($.isBlank(partyId)){
			$("#pmForm input[name=dueNum]").val('');
			presenterSelect.attr("disabled",true);
			recorderSelect.attr("disabled",true);
			$('#absent').attr("disabled",true);
			return;
		}
		var data = partySelect.select2("data")[0];
		if(data.class==${cm:getMetaTypeByCode("mt_direct_branch").id}){


			presenterSelect.data('ajax-url', "${ctx}/member_selects?partyId="+partyId+"&status="+${MEMBER_STATUS_NORMAL});
			recorderSelect.data('ajax-url', "${ctx}/member_selects?partyId="+partyId+"&status="+${MEMBER_STATUS_NORMAL});

			$.register.user_select(presenterSelect);
			$.register.user_select(recorderSelect);

			$('#absent').data('url', "${ctx}/pm/pm3Meeting_member?type=2&partyId="+partyId);

			presenterSelect.removeAttr("disabled");
			recorderSelect.removeAttr("disabled");
			$('#absent').removeAttr("disabled");

			var data = partySelect.select2("data")[0];
			$("#pmForm input[name=dueNum]").val(data['party'].memberCount);
		}

	});

	var branchSelect = $('#pmForm select[name="branchId"]');
	branchSelect.on('change',function(){

		var partyId=partySelect.val();
		var branchId=branchSelect.val();

		if ($.isBlank(branchId)){
			$("#pmForm input[name=dueNum]").val('');
			presenterSelect.attr("disabled",true);
			recorderSelect.attr("disabled",true);
			$('#absent').attr("disabled",true);
			return;
		}

		presenterSelect.data('ajax-url', "${ctx}/member_selects?partyId="+partyId+"&branchId="+branchId+"&status="+${MEMBER_STATUS_NORMAL});
		recorderSelect.data('ajax-url', "${ctx}/member_selects?partyId="+partyId+"&branchId="+branchId+"&status="+${MEMBER_STATUS_NORMAL});

		$.register.user_select(presenterSelect);
		$.register.user_select(recorderSelect);

		$('#absent').data('url', "${ctx}/pm/pm3Meeting_member?type=2&partyId="+partyId+"&branchId="+branchId);

		presenterSelect.removeAttr("disabled");
		recorderSelect.removeAttr("disabled");
		$('#absent').removeAttr("disabled");

		var data = branchSelect.select2("data")[0];
		if(data['branch']) {
			console.log('--------')
			$("#pmForm input[name=dueNum]").val(data['branch'].memberCount);
		}else{
			console.log(data)
		}
	});

	$(document).on("click", "#absentTable button", function () {
		$(this).closest("tr").remove();
		$("#pmForm input[name=absentNum]").val($("#absentTable tbody tr").length);

	});

	$("#pmSubmitBtn").click(function(){$("#pmForm").submit();return false;});
	$("#pmForm").validate({
		submitHandler: function (form) {
			var absentIds = [];
			$("#absentTable tbody tr").each(function(){
				absentIds.push($(this).data("user-id"));
			});
			var data = {absentIds:absentIds};
			var $btn = $("#pmSubmitBtn").button('loading');
			$(form).ajaxSubmit({
				data: data,
				success:function(ret){
					if(ret.success){
						$("#modal").modal('hide');
						SysMsg.success("保存成功。", function () {
							$.hideView();
							pm3_reload();
							$("#jqGrid").trigger("reloadGrid");
						});
					}
					$btn.button('reset');
				}
			});
		}
	});

	$('#pmForm [data-rel="select2"]').select2();
</script>