<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="MEMBER_IN_STATUS_APPLY" value="<%=MemberConstants.MEMBER_IN_STATUS_APPLY%>"/>
<c:set var="HTML_FRAGMENT_MEMBER_IN_NOTE_BACK" value="<%=SystemConstants.HTML_FRAGMENT_MEMBER_IN_NOTE_BACK%>"/>

    <h3><c:if test="${memberIn!=null}">编辑</c:if><c:if test="${memberIn==null}">添加</c:if>组织关系转入
		<c:if test="${not empty cm:getHtmlFragment(HTML_FRAGMENT_MEMBER_IN_NOTE_BACK).content}">
		<a class="popupBtn btn btn-success btn-xs"
		   data-width="800"
		   data-url="${ctx}/hf_content?code=${HTML_FRAGMENT_MEMBER_IN_NOTE_BACK}">
			<i class="fa fa-info-circle"></i> 申请说明</a>
			</c:if>
	</h3>
<hr/>
    <form class="form-horizontal" action="${ctx}/memberIn_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${memberIn.id}">
		<input type="hidden" name="resubmit">
			<div class="row">
				<div class="col-xs-4">
					<div class="form-group">
						<label class="col-xs-5 control-label">同步已转出人员信息</label>
						<div class="col-xs-7">
							<select data-ajax-url="${ctx}/memberOut_selects?noAuth=1"
									name="outUserId" data-placeholder="请输入账号或姓名或学工号">
								<option></option>
							</select>
							<span class="help-block">注：适用于同一个党员在本系统内转出后，再使用不同的工号进行转入操作</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label"><c:if test="${empty userBean}"><span class="star">*</span></c:if>转入账号</label>
						<c:if test="${not empty userBean}">
						<div class="col-xs-6 label-text">
							<input type="hidden" name="userId" value="${userBean.userId}">
						${userBean.realname}
							</div>
						</c:if>
						<c:if test="${empty userBean}">
						<div class="col-xs-6">
							<select required data-rel="select2-ajax"
									data-ajax-url="${ctx}/sysUser_selects?needPrivate=1"
									name="userId" data-placeholder="请输入账号或姓名或学工号">
								<option value="${userBean.userId}">${userBean.realname}</option>
							</select>
						</div>
						</c:if>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label">性别</label>
						<div class="col-xs-6">
							<input disabled class="form-control" name="gender" type="text" value="${GENDER_MAP.get(userBean.gender)}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label">出生年月</label>
						<div class="col-xs-6">
							<input disabled class="form-control" type="text" name="birth"
								   value="${userBean.birth!=null?cm:intervalYearsUntilNow(userBean.birth):''}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label">民族</label>
						<div class="col-xs-6">
							 <select name="nation" data-rel="select2" data-placeholder="请选择"  data-width="150">
								 <option></option>
								<c:forEach items="${cm:getMetaTypes('mc_nation').values()}" var="nation">
									<option value="${nation.name}">${nation.name}</option>
								</c:forEach>
							</select>
							<script>
								$("#modalForm select[name=nation]").val('${cm:ensureEndsWith(userBean.nation, '族')}');
							</script>
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label">身份证号</label>
						<div class="col-xs-6">
							<input disabled class="form-control" type="text" name="idcard" value="${userBean.idcard}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label"><span class="star">*</span>类别</label>
						<div class="col-xs-6">
							<select required data-rel="select2" name="type" data-placeholder="请选择"  data-width="100">
								<option></option>
								<c:import url="/metaTypes?__code=mc_member_in_out_type"/>
							</select>
							<script>
								$("#modalForm select[name=type]").val(${memberIn.type});
							</script>
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label"><span class="star">*</span>转入${_p_partyName}</label>
						<div class="col-xs-6">
							<select required class="form-control"  data-rel="select2-ajax" data-ajax-url="${ctx}/party_selects?auth=1"
									name="partyId" data-placeholder="请选择">
								<option value="${party.id}">${party.name}</option>
							</select>
						</div>
					</div>
					<div class="form-group" id="branchDiv">
						<label class="col-xs-5 control-label"><span class="star">*</span>转入党支部</label>
						<div class="col-xs-6">
							<select class="form-control"  data-rel="select2-ajax" data-ajax-url="${ctx}/branch_selects?auth=1"
									name="branchId" data-placeholder="请选择">
								<option value="${branch.id}">${branch.name}</option>
							</select>
						</div>
					</div>
					<script>
						$.register.party_branch_select($("#modalForm"), "branchDiv",
								'${cm:getMetaTypeByCode("mt_direct_branch").id}',
								"${party.id}", "${party.classId}", "partyId", "branchId", true );
					</script>
				</div>
				<div class="col-xs-4">
					<div class="form-group">
						<label class="col-xs-5 control-label"><span class="star">*</span>介绍信抬头</label>
						<div class="col-xs-6">
							<textarea required class="form-control" name="fromTitle">${memberIn.fromTitle}</textarea>
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label"><span class="star">*</span>介绍信有效期天数</label>
						<div class="col-xs-6" style="width: 90px">
							<input required class="form-control digits" type="text" name="validDays" value="${memberIn.validDays}">
						</div>
					</div>

					<div class="form-group">
						<label class="col-xs-5 control-label"><span class="star">*</span>党籍状态</label>
						<div class="col-xs-6">
							<select required data-rel="select2" name="politicalStatus" data-placeholder="请选择"  data-width="120">
								<option></option>
								<c:forEach items="${MEMBER_POLITICAL_STATUS_MAP}" var="_status">
									<option value="${_status.key}">${_status.value}</option>
								</c:forEach>
							</select>
							<script>
								$("#modalForm select[name=politicalStatus]").val(${memberIn.politicalStatus});
							</script>
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label"><span class="star">*</span>转出单位</label>
						<div class="col-xs-6">
							<textarea required class="form-control" name="fromUnit">${memberIn.fromUnit}</textarea>
						</div>
					</div>

					<div class="form-group">
						<label class="col-xs-5 control-label"><span class="star">*</span>转出单位地址</label>
						<div class="col-xs-6">
							<textarea required class="form-control" name="fromAddress">${memberIn.fromAddress}</textarea>
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label"><span class="star">*</span>转出单位联系电话</label>
						<div class="col-xs-6">
							<input required class="form-control" t	ype="text" name="fromPhone" value="${memberIn.fromPhone}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label">转出单位传真</label>
						<div class="col-xs-6">
							<input class="form-control" type="text" name="fromFax" value="${memberIn.fromFax}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label"><span class="star">*</span>转出单位邮编</label>
						<div class="col-xs-6">
							<input required class="form-control isZipCode" maxlength="6" type="text" name="fromPostCode" value="${memberIn.fromPostCode}">
						</div>
					</div>
				</div>
				<div class="col-xs-4">
					<div class="form-group">
						<label class="col-xs-5 control-label"><span class="star">*</span>党费缴纳至年月</label>
						<div class="col-xs-6">
							<div class="input-group">
								<input required class="form-control date-picker" name="_payTime" type="text"
									   data-date-format="yyyy-mm"
									   data-date-min-view-mode="1" value="${cm:formatDate(memberIn.payTime,'yyyy-MM')}" />
								<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
							</div>
						</div>
					</div>

					<div class="form-group">
						<label class="col-xs-5 control-label"><span class="star">*</span>转出办理时间</label>
						<div class="col-xs-6">
							<div class="input-group">
								<input required class="form-control date-picker" name="_fromHandleTime" type="text"
									   data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberIn.fromHandleTime,'yyyy-MM-dd')}" />
								<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label"><span class="star">*</span>转入办理时间</label>
						<div class="col-xs-6">
							<div class="input-group">
								<c:set var="handleTime" value="${cm:formatDate(memberIn.handleTime,'yyyy-MM-dd')}"/>
								<input required class="form-control date-picker" name="_handleTime" type="text"
									   data-date-format="yyyy-mm-dd" value="${empty handleTime?_today:handleTime}" />
								<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label">提交书面申请书时间</label>
						<div class="col-xs-6">
							<div class="input-group">
								<input class="form-control date-picker" name="_applyTime" type="text"
									   data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberIn.applyTime,'yyyy-MM-dd')}" />
								<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label">确定为入党积极分子时间</label>
						<div class="col-xs-6">
							<div class="input-group">
								<input class="form-control date-picker" name="_activeTime" type="text"
									   data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberIn.activeTime,'yyyy-MM-dd')}" />
								<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label">确定为发展对象时间</label>
						<div class="col-xs-6">
							<div class="input-group">
								<input class="form-control date-picker" name="_candidateTime" type="text"
									   data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberIn.candidateTime,'yyyy-MM-dd')}" />
								<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label">入党时间</label>
						<div class="col-xs-6">
							<div class="input-group">
								<input class="form-control date-picker" name="_growTime" type="text"
									   data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberIn.growTime,'yyyy-MM-dd')}" />
								<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label">转正时间</label>
						<div class="col-xs-6">
							<div class="input-group">
								<input class="form-control date-picker" name="_positiveTime" type="text"
									   data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberIn.positiveTime,'yyyy-MM-dd')}" />
								<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label">是否有回执</label>
						<div class="col-xs-6">
							<label>
								<input name="hasReceipt" ${memberIn.hasReceipt?"checked":""}  type="checkbox" />
								<span class="lbl"></span>
							</label>
						</div>
					</div>
				</div>
			</div>


    </form>
<div class="modal-footer center">
    <a href="javascript:;" class="hideView btn btn-default">返回</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${memberIn!=null}">确定</c:if><c:if test="${memberIn==null}">添加</c:if>"/>
	<c:if test="${memberIn!=null && memberIn.status<MEMBER_IN_STATUS_APPLY}">
		<input type="button" id="resubmit" class="btn btn-warning" value="修改并重新提交"/>
	</c:if>
</div>
<script>
	$("#modalForm :checkbox").bootstrapSwitch();
	$('textarea.limited').inputlimiter();
	$.register.date($('.date-picker'));
	$("#body-content-view input[type=submit]").click(function(){$("#modalForm").submit(); return false;});
	$("#modalForm").validate({
        submitHandler: function (form) {
        	//console.log($("#modalForm #branchDiv").is(":hidden"))
			if(!$("#modalForm #branchDiv").is(":hidden")){
				//console.log($('#modalForm select[name=branchId]').val())
				if($('#modalForm select[name=branchId]').val()=='') {
					SysMsg.warning("请选择支部。", "提示");
					return;
				}
			}
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
						//SysMsg.success('提交成功。', '成功',function(){
							$("#jqGrid").trigger("reloadGrid");
						$.hideView();
						//});
                    }
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
    $('#modalForm select[name=nation]').select2({theme: "default"}).prop("disabled", true);
    $('[data-rel="tooltip"]').tooltip();

	$("#resubmit").click(function(){
		$("input[name=resubmit]", "#modalForm").val("1");
		$("#modalForm").submit();
		return false;
	});

	var $select = $.register.user_select($('#modalForm select[name=userId]'));
	$select.on("change",function(){
		var entity = $(this).select2("data")[0];
		if(entity && entity.id && entity.id>0) {
			var gender = entity.gender || '';
			var birth = '';
			if (entity.birth && entity.birth != '')
				birth = $.date(entity.birth, "yyyy-MM-dd");
			var nation = entity.nation || '';
			var idcard = entity.idcard || '';

			$("#modalForm input[name=gender]").val(_cMap.GENDER_MAP[gender]);
			$("#modalForm input[name=birth]").val(birth);
			$("#modalForm select[name=nation]").val(nation).trigger('change');
			$("#modalForm input[name=idcard]").val(idcard);
		}else{
			$("#modalForm input[name=gender]").val('');
			$("#modalForm input[name=age]").val('');
			$("#modalForm select[name=nation]").val('').trigger('change');
			$("#modalForm input[name=idcard]").val('')
		}
	});

	var $selectOutUserId = $.register.user_select($('#modalForm select[name=outUserId]'));
	$selectOutUserId.on("change",function(){
		var entity = $(this).select2("data")[0];
		//console.log(entity)
		if(entity && entity.id) {
			var record = entity.record;
			$("#modalForm textarea[name=fromTitle]").val(record.toTitle);
			$("#modalForm textarea[name=fromUnit]").val(record.fromUnit);
			$("#modalForm textarea[name=fromAddress]").val(record.fromAddress);
			$("#modalForm input[name=fromPhone]").val(record.fromPhone);
			$("#modalForm input[name=fromFax]").val(record.fromFax);
			$("#modalForm input[name=fromPostCode]").val(record.fromPostCode);
			$("#modalForm input[name=_payTime]").val($.date(record.payTime, "yyyy-MM"));
			$("#modalForm input[name=validDays]").val(record.validDays);
			$("#modalForm input[name=_fromHandleTime]").val($.date(record.handleTime, "yyyy-MM-dd"));

			$("#modalForm select[name=politicalStatus]").val(entity.politicalStatus).trigger("change");
			$("#modalForm input[name=_applyTime]").val($.date(entity.applyTime, "yyyy-MM-dd"));
			$("#modalForm input[name=_activeTime]").val($.date(entity.activeTime, "yyyy-MM-dd"));
			$("#modalForm input[name=_candidateTime]").val($.date(entity.candidateTime, "yyyy-MM-dd"));
			$("#modalForm input[name=_growTime]").val($.date(entity.growTime, "yyyy-MM-dd"));
			$("#modalForm input[name=_positiveTime]").val($.date(entity.positiveTime, "yyyy-MM-dd"));
		}else{
			$("input,textarea", "#modalForm").val('');
			$("select", "#modalForm").not(this).val(null).trigger("change");
		}
	});
</script>