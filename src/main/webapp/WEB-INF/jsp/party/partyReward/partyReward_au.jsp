<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="OW_PARTY_REPU_MAP" value="<%=OwConstants.OW_PARTY_REPU_MAP%>"/>
<c:set var="OW_PARTY_REPU_PARTY" value="<%=OwConstants.OW_PARTY_REPU_PARTY%>"/>
<c:set var="OW_PARTY_REPU_BRANCH" value="<%=OwConstants.OW_PARTY_REPU_BRANCH%>"/>
<c:set var="OW_PARTY_REPU_MEMBER" value="<%=OwConstants.OW_PARTY_REPU_MEMBER%>"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
	<h3><c:if test="${partyReward!=null}">修改</c:if><c:if test="${partyReward==null}">添加</c:if>党内奖励信息</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/party/partyReward_au" autocomplete="off" disableautocomplete id="modalForm" method="post" enctype="multipart/form-data">
        <input type="hidden" name="id" value="${partyReward.id}">
		<c:if test="${cls==OW_PARTY_REPU_PARTY}">
			<div class="form-group">
				<label class="col-xs-3 control-label">类型</label>
				<div class="col-xs-6 label-text">
					<c:forEach items="${OW_PARTY_REPU_MAP}" var="_type">
						<c:if test="${_type.key==cls}">
							奖励<span>${_type.value}</span>
						</c:if>
					</c:forEach>
				</div>
			</div>
			<div class="form-group">
				<c:if test="${party!=null}">
				<label class="col-xs-3 control-label">${_p_partyName}名称</label>
				<div class="col-xs-6 label-text">
					<input type="hidden" name="partyId" value="${party.id}">
					${party.name}
				</div>
				</c:if>
				<c:if test="${party==null}">
				<label class="col-xs-3 control-label"><span class="star">*</span>${_p_partyName}名称</label>
				<div class="col-xs-6">
					<select required class="form-control" data-rel="select2-ajax"
							data-ajax-url="${ctx}/party_selects?auth=1"
							name="partyId" data-placeholder="请选择" data-width="270">
						<option value="${party.id}">${party.name}</option>
					</select>
				</div>
				</c:if>
			</div>
		</c:if>
		<c:if test="${cls==OW_PARTY_REPU_BRANCH}">
			<div class="form-group">
				<label class="col-xs-3 control-label">类型</label>
				<div class="col-xs-6 label-text">
					<c:forEach items="${OW_PARTY_REPU_MAP}" var="_type">
						<c:if test="${_type.key==cls}">
							奖励<span>${_type.value}</span>
						</c:if>
					</c:forEach>
				</div>
			</div>
				<c:if test="${branch!=null}">
					<div class="form-group">
					<label class="col-xs-3 control-label">${_p_partyName}名称</label>
					<div class="col-xs-6 label-text">
						<input type="hidden" name="branchPartyId" value="${branchParty.id}">
							${branchParty.name}
					</div>
					</div>
				<div class="form-group">
				<label class="col-xs-3 control-label">党支部名称</label>
				<div class="col-xs-6 label-text">
					<input type="hidden" name="branchId" value="${branch.id}">
                    ${branch.name}
				</div>
				</div>
				</c:if>
				<c:if test="${branch==null}">
					<div class="form-group">
						<label class="col-xs-3 control-label"><span class="star">*</span>所属${_p_partyName}</label>
						<div class="col-xs-6">
							<select required class="form-control" data-rel="select2-ajax"
									data-ajax-url="${ctx}/party_selects?auth=1"
									name="branchPartyId" data-placeholder="请选择" data-width="270">
								<option value="${branchParty.id}">${branchParty.name}</option>
							</select>
						</div>
					</div>
				<div class="form-group" id="branchDiv">
				<label class="col-xs-3 control-label"><span class="star">*</span>党支部名称</label>
				<div class="col-xs-6">
					<select class="form-control" data-rel="select2-ajax" data-ajax-url="${ctx}/branch_selects?auth=1"
							name="branchId" data-placeholder="请选择" data-width="270">
						<option value="${branch.id}">${branch.name}</option>
					</select>
				</div>
				</div>
					<script>

						function func($container, branchDivId, mt_direct_branch_id,
								  init_party_id, init_party_class, partyId, branchId, branchIsNotEmpty){

							var $container = $("#modalForm");
							var branchDivId = "branchDiv";
							var mt_direct_branch_id = '${cm:getMetaTypeByCode("mt_direct_branch").id}';
							partyId = partyId || "branchPartyId";
							branchId = branchId || "branchId";
							$('select[name=' + partyId + '], select[name=' + branchId + ']', $container).select2({
								templateResult: function (state) {
									//console.log("-----------")
									return '<span class="{0}">{1}</span>'.format(state.del || $(state.element).attr('delete') == 'true' ? "delete" : "", state.text);
								},
								templateSelection: function (state) {
									//console.log($(state.element).attr('delete'))
									return '<span class="{0}">{1}</span>'.format(state.del || $(state.element).attr('delete') == 'true' ? "delete" : "", state.text);
								},
								escapeMarkup: function (markup) {
									return markup;
								},
								ajax: {
									dataType: 'json',
									delay: 300,
									data: function (params) {
										return {
											searchStr: params.term,
											pageSize: 10,
											pageNo: params.page,
											partyId: $('select[name=' + partyId + ']', $container).val()
										};
									},
									processResults: function (data, params) {
										params.page = params.page || 1;
										return {
											results: data.options, pagination: {
												more: (params.page * 10) < data.totalCount
											}
										};
									},
									cache: true
								}
							});
							$('select[name=' + partyId + ']', $container).on("change", function () {

								$("#" + branchDivId + " select", $container).removeAttr("required");

								var partyOption = $(this).select2("data")[0];
								var $party_class = (partyOption ? $(this).select2("data")[0]['class'] : null) || init_party_class;
								//alert("${party.id}")
								if ($(this).val() != init_party_id)
									$('select[name=' + branchId + ']', $container).val(null).trigger("change");
								if ($(this).val() > 0 && $party_class != mt_direct_branch_id) {
									$("#" + branchDivId, $container).show();
									if (branchIsNotEmpty != undefined && branchIsNotEmpty)
										$("#" + branchDivId + " select", $container).attr("required", "required");
								} else {
									$('select[name=' + branchId + ']', $container).val(null).trigger("change");
									$("#" + branchDivId, $container).hide();
								}
							}).change();
							$('select[name=' + partyId + ']', $container).on("select2:unselect", function () {
								$('select[name=' + branchId + ']', $container).val(null).trigger("change");
								$("#" + branchDivId, $container).hide();
							})
						};
					</script>
				</c:if>
		</c:if>
		<c:if test="${cls==OW_PARTY_REPU_MEMBER}">
			<div class="form-group">
				<label class="col-xs-3 control-label">类型</label>
				<div class="col-xs-6 label-text">
					<c:forEach items="${OW_PARTY_REPU_MAP}" var="_type">
						<c:if test="${_type.key==cls}">
							奖励<span>${_type.value}</span>
						</c:if>
					</c:forEach>
				</div>
			</div>
			<div class="form-group">
				<c:if test="${user!=null}">
				<label class="col-xs-3 control-label">党员姓名</label>
				<div class="col-xs-6 label-text">
					<input type="hidden" name="userId" value="${user.id}">
                     ${user.realname}
				</div>
				</c:if>
				<c:if test="${user==null}">
				<label class="col-xs-3 control-label"><span class="star">*</span>账号</label>
				<div class="col-xs-6">
				<select required class="form-control" data-rel="select2-ajax"
																			   data-ajax-url="${ctx}/member_selects"
																			   name="userId" data-width="270"
																			   data-placeholder="请输入账号或姓名或学工号">
					<option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
				</select>
				</div>
				</c:if>
			</div>
		</c:if>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 获奖日期</label>
				<div class="col-xs-6">
					<div class="input-group date" data-date-format="yyyy.mm.dd">
						<input required class="form-control date-picker" name="rewardTime" type="text" data-width="270"
							   placeholder="格式：yyyy.mm.dd"
							   value="${cm:formatDate(partyReward.rewardTime,'yyyy.MM.dd')}"/>
						<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
					</div>
				</div>
			</div>
			<div class="form-group ${cls==OW_PARTY_REPU_MEMBER?'hidden':''}">
				<label class="col-xs-3 control-label"><span class="star">*</span> 获奖类型</label>
				<div class="col-xs-6">
					<select ${cls==OW_PARTY_REPU_MEMBER?'':'required'} data-rel="select2" name="rewardType" data-width="270"
							data-placehoder="请选择">
						<option></option>
						<c:if test="${cls==OW_PARTY_REPU_PARTY}">
						<c:import url="/metaTypes?__code=mt_party_reward"/>
						</c:if>
						<c:if test="${cls==OW_PARTY_REPU_BRANCH}">
						<c:import url="/metaTypes?__code=mt_branch_reward"/>
						</c:if>
					</select>
					<script type="text/javascript">
						$("#modalForm select[name=rewardType]").val(${partyReward.rewardType});
					</script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 获得奖项</label>
				<div class="col-xs-6">
					<textarea required class="form-control" name="name">${partyReward.name}</textarea>
					<span class="help-block">注：不要加书名号。</span>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">颁奖单位</label>
				<div class="col-xs-6">
						<textarea class="form-control" name="unit">${partyReward.unit}</textarea>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 获奖证书</label>
				<div class="col-xs-6">
					<input ${ empty partyReward.proof?'required':''} class="form-control" type="file" name="_proof"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
                        <input class="form-control" type="text" name="remark" value="${partyReward.remark}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty partyReward?'确定':'添加'}</button>
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
                        $("#jqGrid_reward").trigger("reloadGrid");
                        $("#jqGrid").trigger("reloadGrid");
                    }
                    $btn.button('reset');
                }
            });
        }
    });
	$.fileInput($('#modalForm input[type=file]'));
    $("#modalForm :checkbox").bootstrapSwitch();
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $.register.date($('.date-picker'));
	$.register.date($('.input-group.date'));
</script>