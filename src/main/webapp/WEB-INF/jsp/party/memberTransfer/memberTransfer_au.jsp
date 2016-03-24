<%@ page import="sys.constants.SystemConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="MEMBER_TYPE_MAP" value="<%=SystemConstants.MEMBER_TYPE_MAP%>"/>
<c:set var="GENDER_MAP" value="<%=SystemConstants.GENDER_MAP%>"/>
<c:set var="MEMBER_POLITICAL_STATUS_MAP" value="<%=SystemConstants.MEMBER_POLITICAL_STATUS_MAP%>"/>
<c:set var="MEMBER_TRANSFER_STATUS_FROM_VERIFY" value="<%=SystemConstants.MEMBER_TRANSFER_STATUS_FROM_VERIFY%>"/>
<c:set var="MEMBER_TRANSFER_STATUS_TO_VERIFY" value="<%=SystemConstants.MEMBER_TRANSFER_STATUS_TO_VERIFY%>"/>
    <h3><c:if test="${memberTransfer!=null}">编辑</c:if><c:if test="${memberTransfer==null}">添加</c:if>校内组织关系互转</h3>
<hr/>
    <form class="form-horizontal" action="${ctx}/memberTransfer_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${memberTransfer.id}">
		<div class="row">
			<div class="col-xs-4">
			<div class="form-group">
				<label class="col-xs-5 control-label">用户</label>
				<div class="col-xs-6">
					<select required data-rel="select2-ajax" data-ajax-url="${ctx}/member_selects"
							name="userId" data-placeholder="请输入账号或姓名或学工号">
						<option value="${userBean.userId}">${userBean.realname}</option>
					</select>
				</div>
			</div>
				<%--<c:if test="${not empty userBean}">--%>
			<div class="form-group">
				<label class="col-xs-5 control-label">学工号</label>
				<div class="col-xs-6">
                        <input disabled class="form-control" type="text" name="code" value="${userBean.code}">
				</div>
			</div>



						<div class="form-group">
							<label class="col-xs-5 control-label">姓名</label>
							<div class="col-xs-6">
								<input disabled class="form-control" type="text" name="realname" value="${userBean.realname}">
							</div>
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
								<input disabled class="form-control" type="text" name="nation" value="${userBean.nation}">
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-5 control-label">政治面貌</label>
							<div class="col-xs-6">
								<input disabled class="form-control" name="politicalStatus" type="text" value="${MEMBER_POLITICAL_STATUS_MAP.get(userBean.politicalStatus)}">
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-5 control-label">身份证号</label>
							<div class="col-xs-6">
								<input disabled class="form-control" type="text" name="idcard" value="${userBean.idcard}">
							</div>
						</div>

	<%--</c:if>--%>
			</div>
			<div class="col-xs-4">
				<c:if test="${not empty userBean}">
					<div class="form-group">
						<label class="col-xs-5 control-label">转出组织机构</label>
						<div class="col-xs-6">
							<textarea disabled class="form-control">${fromParty.name}<c:if test="${not empty fromBranch}">-${fromBranch.name}</c:if>
							</textarea>
						</div>
					</div>
				</c:if>
				<div class="form-group">
					<label class="col-xs-5 control-label">转入分党委</label>
					<div class="col-xs-6">
						<select required class="form-control"  data-rel="select2-ajax" data-ajax-url="${ctx}/party_selects"
								name="toPartyId" data-placeholder="请选择" >
							<option value="${toParty.id}">${toParty.name}</option>
						</select>
					</div>
				</div>
				<div class="form-group" style="${(empty toBranch)?'display: none':''}" id="toBranchDiv">
					<label class="col-xs-5 control-label">转入党支部</label>
					<div class="col-xs-6">
						<select class="form-control"  data-rel="select2-ajax" data-ajax-url="${ctx}/branch_selects"
								name="toBranchId" data-placeholder="请选择">
							<option value="${toBranch.id}">${toBranch.name}</option>
						</select>
					</div>
				</div>
				<script>
					register_party_branch_select($("#modalForm"), "toBranchDiv",
							'${cm:getMetaTypeByCode("mt_direct_branch").id}',
							"${toParty.id}", "${toParty.classId}" , "toPartyId", "toBranchId");
				</script>
			<div class="form-group">
				<label class="col-xs-5 control-label">转出单位联系电话</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="fromPhone" value="${memberTransfer.fromPhone}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-5 control-label">转出单位传真</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="fromFax" value="${memberTransfer.fromFax}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-5 control-label">党费缴纳至年月</label>
				<div class="col-xs-6">
					<div class="input-group">
						<input required class="form-control date-picker" name="_payTime" type="text"
							   data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberTransfer.payTime,'yyyy-MM-dd')}" />
						<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-5 control-label">介绍信有效期天数</label>
				<div class="col-xs-6">
                        <input required class="form-control digits" type="text" name="validDays" value="${memberTransfer.validDays}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-5 control-label">转出办理时间</label>
				<div class="col-xs-6">
					<div class="input-group">
						<input required class="form-control date-picker" name="_fromHandleTime" type="text"
							   data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberTransfer.fromHandleTime,'yyyy-MM-dd')}" />
						<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-5 control-label">返回修改原因</label>
				<div class="col-xs-6">
					<textarea required class="form-control limited" type="text" name="reason" rows="5">${memberTransfer.reason}</textarea>
				</div>
			</div>
				</div></div>
    </form>

<c:if test="${memberTransfer.status!=MEMBER_TRANSFER_STATUS_TO_VERIFY}">
	<div class="modal-footer center">
		<a href="#" class="btn btn-default closeView">取消</a>
		<input type="submit" class="btn btn-primary" value="<c:if test="${memberTransfer!=null}">确定</c:if><c:if test="${memberTransfer==null}">添加</c:if>"/>
	</div>
</c:if>
<c:if test="${memberTransfer.status==MEMBER_TRANSFER_STATUS_TO_VERIFY}">
	<div class="modal-footer">
		<a href="#" class="btn btn-default closeView">返回</a>
	</div>
</c:if>
<script>

	$('textarea.limited').inputlimiter();
	register_date($('.date-picker'));
	$("#item-content input[type=submit]").click(function(){$("#modalForm").submit(); return false;});
	$("#modalForm").validate({
        submitHandler: function (form) {

			if(!$("#toBranchDiv").is(":hidden")){
				if($('#modalForm select[name=toBranchId]').val()=='') {
					SysMsg.warning("请选择转入支部。", "提示");
					return;
				}
			}

			if(!$("#fromBranchDiv").is(":hidden")){
				if($('#modalForm select[name=fromBranchId]').val()=='') {
					SysMsg.warning("请选择转出支部。", "提示");
					return;
				}
			}
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        page_reload();
                        SysMsg.success('操作成功。', '成功');
                    }
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
	var $select = register_user_select($('#modalForm select[name=userId]'));
	$select.on("change",function(){
		var entity = $(this).select2("data")[0];
		if(entity && entity.id && entity.id>0) {
			console.log(entity)
			var code = entity.user.code || '';
			var realname = entity.user.realname || '';
			var gender = entity.user.gender || '';
			var birth = '';
			if (entity.user.birth && entity.user.birth != '')
				birth = new Date(entity.user.birth).format('yyyy-MM-dd');
			var nation = entity.user.nation || '';
			var politicalStatus = entity.user.politicalStatus || '';
			var idcard = entity.user.idcard || '';

			$("#modalForm input[name=code]").val(code);
			$("#modalForm input[name=realname]").val(realname);
			$("#modalForm input[name=gender]").val(gender == 1 ? '男' : (gender == 2 ? '女' : ''));
			$("#modalForm input[name=birth]").val(birth);
			$("#modalForm input[name=nation]").val(nation);
			$("#modalForm input[name=politicalStatus]").val(politicalStatus == 1 ? '正式党员' : (politicalStatus == 2 ? '预备党员' : ''));
			$("#modalForm input[name=idcard]").val(idcard);
		}else{
			$("#modalForm input[name=code]").val('');
			$("#modalForm input[name=realname]").val('')
			$("#modalForm input[name=gender]").val('')
			$("#modalForm input[name=age]").val('')
			$("#modalForm input[name=nation]").val('')
			$("#modalForm input[name=politicalStatus]").val('')
			$("#modalForm input[name=idcard]").val('')
		}
	});
</script>