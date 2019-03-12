<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="MEMBER_IN_STATUS_APPLY" value="<%=MemberConstants.MEMBER_IN_STATUS_APPLY%>"/>

    <h3><c:if test="${memberIn!=null}">编辑</c:if><c:if test="${memberIn==null}">添加</c:if>组织关系转入
		<a class="popupBtn btn btn-success btn-xs"
		   data-width="800"
		   data-url="${ctx}/hf_content?code=<%=SystemConstants.HTML_FRAGMENT_MEMBER_IN_NOTE_BACK%>">
			<i class="fa fa-info-circle"></i> 申请说明</a>
	</h3>
<hr/>
    <form class="form-horizontal" action="${ctx}/memberIn_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${memberIn.id}">
		<input type="hidden" name="resubmit">
			<div class="row">
				<div class="col-xs-4">
					<div class="form-group">
						<label class="col-xs-5 control-label"><span class="star">*</span>介绍信抬头</label>
						<div class="col-xs-6">
							<input required class="form-control" type="text" name="fromTitle" value="${memberIn.fromTitle}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label"><span class="star">*</span>介绍信有效期天数</label>
						<div class="col-xs-6">
							<input required class="form-control digits" type="text" name="validDays" value="${memberIn.validDays}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label"><span class="star">*</span>类别</label>
						<div class="col-xs-6">
							<select required data-rel="select2" name="type" data-placeholder="请选择"  data-width="100">
								<option></option>
								<c:forEach items="<%=MemberConstants.MEMBER_INOUT_TYPE_MAP%>" var="_type">
									<option value="${_type.key}">${_type.value}</option>
								</c:forEach>
							</select>
							<script>
								$("#modalForm select[name=type]").val(${memberIn.type});
							</script>
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
						<label class="col-xs-5 control-label"><c:if test="${empty userBean}"><span class="star">*</span></c:if>用户</label>
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
					<%--<c:if test="${not empty userBean}">--%>
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
							<input disabled class="form-control" type="text" name="nation" value="${userBean.nation}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label">身份证号</label>
						<div class="col-xs-6">
							<input disabled class="form-control" type="text" name="idcard" value="${userBean.idcard}">
						</div>
					</div>
					<%--</c:if>--%>



					<div class="form-group">
						<label class="col-xs-5 control-label"><span class="star">*</span>分党委</label>
						<div class="col-xs-6">
							<select required class="form-control"  data-rel="select2-ajax" data-ajax-url="${ctx}/party_selects?auth=1"
									name="partyId" data-placeholder="请选择">
								<option value="${party.id}">${party.name}</option>
							</select>
						</div>
					</div>
					<div class="form-group" id="branchDiv">
						<label class="col-xs-5 control-label"><span class="star">*</span>党支部</label>
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
						<label class="col-xs-5 control-label"><span class="star">*</span>转出单位</label>
						<div class="col-xs-6">
							<input required class="form-control" type="text" name="fromUnit" value="${memberIn.fromUnit}">
						</div>
					</div>

					<div class="form-group">
						<label class="col-xs-5 control-label"><span class="star">*</span>转出单位地址</label>
						<div class="col-xs-6">
							<input required class="form-control" type="text" name="fromAddress" value="${memberIn.fromAddress}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label"><span class="star">*</span>转出单位联系电话</label>
						<div class="col-xs-6">
							<input required class="form-control" t	ype="text" name="fromPhone" value="${memberIn.fromPhone}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label"><span class="star">*</span>转出单位传真</label>
						<div class="col-xs-6">
							<input required class="form-control" type="text" name="fromFax" value="${memberIn.fromFax}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label"><span class="star">*</span>转出单位邮编</label>
						<div class="col-xs-6">
							<input required class="form-control" type="text" name="fromPostCode" value="${memberIn.fromPostCode}">
						</div>
					</div>
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

				</div>
				<div class="col-xs-4">

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
<div class="footer-margin lower"/>
<script>
	jgrid_left = $("#jqGrid").closest(".ui-jqgrid-bdiv").scrollLeft();
	jgrid_top = $("#jqGrid").closest(".ui-jqgrid-bdiv").scrollTop();

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
			$("#modalForm input[name=nation]").val(nation);
			$("#modalForm input[name=idcard]").val(idcard);
		}else{
			$("#modalForm input[name=gender]").val('');
			$("#modalForm input[name=age]").val('');
			$("#modalForm input[name=nation]").val('');
			$("#modalForm input[name=idcard]").val('')
		}
	});
</script>