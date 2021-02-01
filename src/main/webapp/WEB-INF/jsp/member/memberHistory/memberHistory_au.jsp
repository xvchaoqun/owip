<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div style="width: 1200px">
	<h3>${empty memberHistory?'添加':'修改'}历史党员</h3>
	<hr/>
	<form class="form-horizontal" action="${ctx}/member/memberHistory_au" autocomplete="off" disableautocomplete id="memberHistoryForm" method="post">
		<input type="hidden" name="id" value="${memberHistory.id}">
		<input type="hidden" name="userId" value="${memberHistory.userId}">
		<div class="row">
			<div class="col-xs-7">
				<div class="form-group">
					<label class="col-xs-4 control-label"><span class="star">*</span>学工号</label>
					<div class="col-xs-6">
						<input required class="form-control" style="width: 170px" type="text" name="code" value="${memberHistory.code}"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-4 control-label"><span class="star">*</span>姓名</label>
					<div class="col-xs-6">
						<input required class="form-control" style="width: 170px" type="text" name="realname" value="${memberHistory.realname}"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-4 control-label">身份证号</label>
					<div class="col-xs-6">
						<input class="form-control" style="width: 170px" type="text" name="idCard" value="${memberHistory.idCard}"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-4 control-label"><span class="star">*</span>人员类型</label>
					<div class="col-xs-6">
						<select required data-rel="select2" name="type" data-placeholder="请选择"
								data-width="170">
							<option></option>
							<c:forEach items="${MEMBER_TYPE_MAP}" var="_type">
								<option value="${_type.key}">${_type.value}</option>
							</c:forEach>
						</select>
						<script>
							$("#memberHistoryForm select[name=type]").val(${memberHistory.type});
						</script>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-4 control-label"> 性别</label>
					<div class="col-xs-6">
						<select data-rel="select2" name="gender" data-placeholder="请选择"
								data-width="170">
							<option></option>
							<c:forEach items="${GENDER_MAP}" var="_gender">
								<option value="${_gender.key}">${_gender.value}</option>
							</c:forEach>
						</select>
						<script>
							$("#memberHistoryForm select[name=gender]").val(${memberHistory.gender});
						</script>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-4 control-label">民族</label>
					<div class="col-xs-6">
						<input class="form-control" style="width: 170px" type="text" name="nation" value="${memberHistory.nation}"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-4 control-label">籍贯</label>
					<div class="col-xs-6">
						<input class="form-control" style="width: 170px" type="text" name="nativePlace" value="${memberHistory.nativePlace}"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-4 control-label">出生时间</label>
					<div class="col-xs-6">
						<div class="input-group" style="width: 170px">
							<input class="form-control date-picker" name="_birth" type="text"
								   data-date-format="yyyy.mm.dd"
								   value="${cm:formatDate(memberHistory.birth,'yyyy.MM.dd')}"/>
							<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-4 control-label"><span class="star">*</span>所属${_p_partyName}</label>
					<div class="col-xs-5">
						<textarea required class="form-control limited noEnter" type="text" maxlength="100"
								  name="partyName">${memberHistory.partyName}</textarea>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-4 control-label">所属党支部</label>
					<div class="col-xs-5">
						<textarea class="form-control limited noEnter" type="text" maxlength="100"
								  name="branchName">${memberHistory.branchName}</textarea>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-4 control-label"><span class="star">*</span>党籍状态</label>
					<div class="col-xs-6">
						<select required data-rel="select2" name="politicalStatus" data-placeholder="请选择"
								data-width="170">
							<option></option>
							<c:forEach items="${MEMBER_POLITICAL_STATUS_MAP}" var="_status">
								<option value="${_status.key}">${_status.value}</option>
							</c:forEach>
						</select>
						<script>
							$("#memberHistoryForm select[name=politicalStatus]").val('${memberHistory.politicalStatus}');
						</script>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-4 control-label">组织关系转入时间</label>
					<div class="col-xs-6">
						<div class="input-group" style="width: 170px">
							<input class="form-control date-picker" name="_transferTime" type="text"
								   data-date-format="yyyy.mm.dd"
								   value="${cm:formatDate(memberHistory.transferTime,'yyyy.mm.dd')}"/>
							<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
						</div>
						<span class="help-block">注：本校发展党员请留空</span>
					</div>
				</div>
			</div>

			<div class="col-xs-5">
				<div class="form-group">
					<label class="col-xs-5 control-label">提交书面申请书时间</label>
					<div class="col-xs-6">
						<div class="input-group" style="width: 170px">
							<input class="form-control date-picker" name="_applyTime" type="text"
								   data-date-format="yyyy.mm.dd" value="${cm:formatDate(memberHistory.applyTime,'yyyy.mm.dd')}"/>
							<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-5 control-label">确定为入党积极分子时间</label>
					<div class="col-xs-6">
						<div class="input-group" style="width: 170px">
							<input class="form-control date-picker" name="_activeTime" type="text"
								   data-date-format="yyyy.mm.dd" value="${cm:formatDate(memberHistory.activeTime,'yyyy.mm.dd')}"/>
							<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-5 control-label">确定为发展对象时间</label>
					<div class="col-xs-6">
						<div class="input-group" style="width: 170px">
							<input class="form-control date-picker" name="_candidateTime" type="text"
								   data-date-format="yyyy.mm.dd"
								   value="${cm:formatDate(memberHistory.candidateTime,'yyyy.mm.dd')}"/>
							<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-5 control-label">入党介绍人</label>
					<div class="col-xs-6">
						<input class="form-control" style="width: 170px" type="text" name="sponsor" value="${memberHistory.sponsor}"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-5 control-label">入党时间</label>
					<div class="col-xs-6">
						<div class="input-group" style="width: 170px">
							<input class="form-control date-picker" name="_growTime" type="text"
								   data-date-format="yyyy.mm.dd" value="${cm:formatDate(memberHistory.growTime,'yyyy.mm.dd')}"/>
							<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
						</div>
					</div>
				</div>
				<div class="form-group" id="positiveTimeDiv">
					<label class="col-xs-5 control-label">转正时间</label>
					<div class="col-xs-6">
						<div  class="input-group" style="width: 170px">
							<input class="form-control date-picker" name="_positiveTime" type="text"
								   data-date-format="yyyy.mm.dd"
								   value="${cm:formatDate(memberHistory.positiveTime,'yyyy.mm.dd')}"/>
							<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-5 control-label">专业技术职务</label>
					<div class="col-xs-6">
						<input class="form-control" style="width: 170px" type="text" name="proPost" value="${memberHistory.proPost}"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-5 control-label">手机</label>
					<div class="col-xs-6">
						<input class="form-control mobile" style="width: 170px" type="text" name="phone" value="${memberHistory.phone}"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-5 control-label">邮箱</label>
					<div class="col-xs-6">
						<input class="form-control" style="width: 170px" type="text" name="email" value="${memberHistory.email}"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-5 control-label">备注1</label>
					<div class="col-xs-6">
						<textarea class="form-control limited noEnter" type="text" maxlength="100"
								  name="remark1">${memberHistory.remark1}</textarea>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-5 control-label">备注2</label>
					<div class="col-xs-6">
						<textarea class="form-control limited noEnter" type="text" maxlength="100"
								  name="remark2">${memberHistory.remark2}</textarea>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-5 control-label">备注3</label>
					<div class="col-xs-6">
						<textarea class="form-control limited noEnter" type="text" maxlength="100"
								  name="remark3">${memberHistory.remark3}</textarea>
					</div>
				</div>
			</div>
		</div>
	</form>
	<div class="clearfix form-actions center">
		<button class="btn btn-info" type="submit"
				data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口">
			<i class="ace-icon fa fa-check bigger-110"></i>
			提交
		</button>

		&nbsp; &nbsp; &nbsp;
		<button class="hideView btn btn-default" type="button">
			<i class="ace-icon fa fa-reply bigger-110"></i>
			返回
		</button>
	</div>
</div>
<script>
	$('textarea.limited').inputlimiter();
	$.register.date($('.date-picker'), {endDate: '${_today}'});

	$("#body-content-view button[type=submit]").click(function () {
		$("#memberHistoryForm").submit();
		return false;
	});
	$("#memberHistoryForm").validate({
		submitHandler: function (form) {
			var $btn = $("#body-content-view button[type=submit]").button('loading');
			$(form).ajaxSubmit({
				success: function (ret) {
					if (ret.success) {
						//SysMsg.success('提交成功。', '成功',function(){
						$("#jqGrid").trigger("reloadGrid");
						$.hideView();
						//});
					}
					$btn.button('reset');
				}
			});
		}
	});
	$('#memberHistoryForm [data-rel="select2"]').select2();
	$('[data-rel="tooltip"]').tooltip();

	function positiveTimeShow(){
		if ($('#memberHistoryForm select[name=politicalStatus]').val()==''||$('#memberHistoryForm select[name=politicalStatus]').val()==null
				||$('#memberHistoryForm select[name=politicalStatus]').val()==${MEMBER_POLITICAL_STATUS_GROW}){
			$('input[name=_positiveTime]').val(null).trigger('change');
			$('#positiveTimeDiv').hide();
		}else if ($('#memberHistoryForm select[name=politicalStatus]').val()==${MEMBER_POLITICAL_STATUS_POSITIVE}){
			$('#positiveTimeDiv').show();
		}
		console.log($('#memberHistoryForm select[name=politicalStatus]').val())
	}
	positiveTimeShow();

	$('select[name=politicalStatus]').on('change', function (){
		if ($(this).val()==''||$(this).val()==${MEMBER_POLITICAL_STATUS_GROW}) {
			$('input[name=positiveTime]').val(null).trigger('change');
			$('#positiveTimeDiv').hide();
		}else {
			$('#positiveTimeDiv').show();
		}
	})
</script>