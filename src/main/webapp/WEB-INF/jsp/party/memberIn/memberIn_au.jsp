<%@ page import="sys.constants.SystemConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="MEMBER_POLITICAL_STATUS_MAP" value="<%=SystemConstants.MEMBER_POLITICAL_STATUS_MAP%>"/>
<c:set var="MEMBER_INOUT_TYPE_MAP" value="<%=SystemConstants.MEMBER_INOUT_TYPE_MAP%>"/>
<c:set var="GENDER_MAP" value="<%=SystemConstants.GENDER_MAP%>"/>
    <h3><c:if test="${memberIn!=null}">编辑</c:if><c:if test="${memberIn==null}">添加</c:if>组织关系转入</h3>
<hr/>
    <form class="form-horizontal" action="${ctx}/memberIn_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${memberIn.id}">
		<input type="hidden" name="resubmit">
			<div class="row">
				<div class="col-xs-4">
					<div class="form-group">
						<label class="col-xs-5 control-label">用户</label>
						<c:if test="${not empty userBean}">
						<div class="col-xs-6 label-text">
							<input type="hidden" name="userId" value="${userBean.userId}">
						${userBean.realname}
							</div>
						</c:if>
						<c:if test="${empty userBean}">
						<div class="col-xs-6">
							<select required data-rel="select2-ajax"
									data-ajax-url="${ctx}/sysUser_selects"
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
						<label class="col-xs-5 control-label">政治面貌</label>
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
						<label class="col-xs-5 control-label">类别</label>
						<div class="col-xs-6">
							<select required data-rel="select2" name="type" data-placeholder="请选择"  data-width="100">
								<option></option>
								<c:forEach items="${MEMBER_INOUT_TYPE_MAP}" var="_type">
									<option value="${_type.key}">${_type.value}</option>
								</c:forEach>
							</select>
							<script>
								$("#modalForm select[name=type]").val(${memberIn.type});
							</script>
						</div>
					</div>

					<div class="form-group">
						<label class="col-xs-5 control-label">分党委</label>
						<div class="col-xs-6">
							<select required class="form-control"  data-rel="select2-ajax" data-ajax-url="${ctx}/party_selects?auth=1"
									name="partyId" data-placeholder="请选择">
								<option value="${party.id}">${party.name}</option>
							</select>
						</div>
					</div>
					<div class="form-group" style="${(empty branch)?'display: none':''}" id="branchDiv">
						<label class="col-xs-5 control-label">党支部</label>
						<div class="col-xs-6">
							<select class="form-control"  data-rel="select2-ajax" data-ajax-url="${ctx}/branch_selects?auth=1"
									name="branchId" data-placeholder="请选择">
								<option value="${branch.id}">${branch.name}</option>
							</select>
						</div>
					</div>
					<script>
						register_party_branch_select($("#modalForm"), "branchDiv",
								'${cm:getMetaTypeByCode("mt_direct_branch").id}', "${party.id}", "${party.classId}" );
					</script>

				</div>
				<div class="col-xs-4">
					<div class="form-group">
						<label class="col-xs-5 control-label">转出单位</label>
						<div class="col-xs-6">
							<input required class="form-control" type="text" name="fromUnit" value="${memberIn.fromUnit}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label">转出单位抬头</label>
						<div class="col-xs-6">
							<input required class="form-control" type="text" name="fromTitle" value="${memberIn.fromTitle}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label">转出单位地址</label>
						<div class="col-xs-6">
							<input required class="form-control" type="text" name="fromAddress" value="${memberIn.fromAddress}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label">转出单位联系电话</label>
						<div class="col-xs-6">
							<input required class="form-control" type="text" name="fromPhone" value="${memberIn.fromPhone}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label">转出单位传真</label>
						<div class="col-xs-6">
							<input required class="form-control" type="text" name="fromFax" value="${memberIn.fromFax}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label">转出单位邮编</label>
						<div class="col-xs-6">
							<input required class="form-control" type="text" name="fromPostCode" value="${memberIn.fromPostCode}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label">党费缴纳至年月</label>
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
						<label class="col-xs-5 control-label">介绍信有效期天数</label>
						<div class="col-xs-6">
							<input required class="form-control digits" type="text" name="validDays" value="${memberIn.validDays}">
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label">转出办理时间</label>
						<div class="col-xs-6">
							<div class="input-group">
								<input required class="form-control date-picker" name="_fromHandleTime" type="text"
									   data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberIn.fromHandleTime,'yyyy-MM-dd')}" />
								<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-5 control-label">转入办理时间</label>
						<div class="col-xs-6">
							<div class="input-group">
								<c:set var="handleTime" value="${cm:formatDate(memberIn.handleTime,'yyyy-MM-dd')}"/>
								<input required class="form-control date-picker" name="_handleTime" type="text"
									   data-date-format="yyyy-mm-dd" value="${empty handleTime?today:handleTime}" />
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
    <a href="#" class="closeView btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${memberIn!=null}">确定</c:if><c:if test="${memberIn==null}">添加</c:if>"/>
	<c:if test="${memberIn!=null && memberIn.status<MEMBER_IN_STATUS_APPLY}">
		<input type="button" id="resubmit" class="btn btn-warning" value="修改并重新提交"/>
	</c:if>
</div>

<script>
	jgrid_left = $("#jqGrid").closest(".ui-jqgrid-bdiv").scrollLeft();
	jgrid_top = $("#jqGrid").closest(".ui-jqgrid-bdiv").scrollTop();

	$("#modalForm :checkbox").bootstrapSwitch();
	$('textarea.limited').inputlimiter();
	register_date($('.date-picker'));
	$("#item-content input[type=submit]").click(function(){$("#modalForm").submit(); return false;});
	$("#modalForm").validate({
        submitHandler: function (form) {
			if(!$("#branchDiv").is(":hidden")){
				if($('#modalForm select[name=branchId]').val()=='') {
					SysMsg.warning("请选择支部。", "提示");
					return;
				}
			}
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
						SysMsg.success('提交成功。', '成功',function(){
							$("#jqGrid").trigger("reloadGrid");
							$(".closeView").click();
						});
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

	var $select = register_user_select($('#modalForm select[name=userId]'));
	$select.on("change",function(){
		var entity = $(this).select2("data")[0];
		if(entity && entity.id && entity.id>0) {
			console.log(entity)
			var gender = entity.user.gender || '';
			var birth = '';
			if (entity.user.birth && entity.user.birth != '')
				birth = new Date(entity.user.birth).format('yyyy-MM-dd');
			var nation = entity.user.nation || '';
			var idcard = entity.user.idcard || '';

			$("#modalForm input[name=gender]").val(gender == 1 ? '男' : (gender == 2 ? '女' : ''));
			$("#modalForm input[name=birth]").val(birth);
			$("#modalForm input[name=nation]").val(nation);
			$("#modalForm input[name=idcard]").val(idcard);
		}else{
			$("#modalForm input[name=gender]").val('')
			$("#modalForm input[name=age]").val('')
			$("#modalForm input[name=nation]").val('')
			$("#modalForm input[name=idcard]").val('')
		}
	});
</script>