<%@ page import="sys.constants.SystemConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
    <h3><c:if test="${memberOut!=null}">编辑</c:if><c:if test="${memberOut==null}">添加</c:if>组织关系转出</h3>
	<hr/>

    <form class="form-horizontal" action="${ctx}/memberOut_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${memberOut.id}">
		<div class="row">
			<div class="col-xs-4">
			<div class="form-group">
				<label class="col-xs-3 control-label">用户</label>
				<c:if test="${not empty userBean}">
					<div class="col-xs-6 label-text">
						<input type="hidden" name="userId" value="${userBean.userId}">
					${userBean.realname}
					</div>
				</c:if>
<c:if test="${empty userBean}">
				<div class="col-xs-6">
					<select required data-rel="select2-ajax" data-ajax-url="${ctx}/member_selects"
							name="userId" data-placeholder="请输入账号或姓名或学工号">
						<option value="${userBean.userId}">${userBean.realname}</option>
					</select>
				</div>
	</c:if>
			</div>
				<%--<c:if test="${not empty userBean}">--%>
			<div class="form-group">
				<label class="col-xs-3 control-label">介绍信编号</label>
				<div class="col-xs-6">
                        <input disabled class="form-control" type="text" name="code" value="${userBean.code}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">姓名</label>
				<div class="col-xs-6">
                        <input disabled class="form-control" type="text" name="realname" value="${userBean.realname}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">性别</label>
				<div class="col-xs-6">
					<input disabled class="form-control" type="text" name="gender" value="${GENDER_MAP.get(userBean.gender)}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">出生年月</label>
				<div class="col-xs-6">
                        <input disabled class="form-control" type="text" name="birth"
							   value="${userBean.birth!=null?cm:intervalYearsUntilNow(userBean.birth):''}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">民族</label>
				<div class="col-xs-6">
                        <input disabled class="form-control" type="text" name="nation" value="${userBean.nation}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">党籍状态</label>
				<div class="col-xs-6">
					<input disabled class="form-control" type="text" name="politicalStatus" value="${MEMBER_POLITICAL_STATUS_MAP.get(userBean.politicalStatus)}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">身份证号</label>
				<div class="col-xs-6">
                        <input disabled class="form-control" type="text" name="idcard" value="${userBean.idcard}">
				</div>
			</div>
				<%--</c:if>--%>
				<div class="form-group">
					<label class="col-xs-3 control-label">类别</label>
					<div class="col-xs-6">
						<select required data-rel="select2" name="type" data-placeholder="请选择"  >
							<option></option>
							<c:forEach items="${MEMBER_INOUT_TYPE_MAP}" var="_type">
								<option value="${_type.key}">${_type.value}</option>
							</c:forEach>
						</select>
						<script>
							$("#modalForm select[name=type]").val(${memberOut.type});
						</script>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label">党员本人联系电话</label>
					<div class="col-xs-6">
						<input required class="form-control" maxlength="20" type="text" name="phone" value="${memberOut.phone}">
					</div>
				</div>
				</div>
				<div class="col-xs-4">
			
			<div class="form-group">
				<label class="col-xs-4 control-label">转入单位抬头</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="toTitle" value="${memberOut.toTitle}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label">转入单位</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="toUnit" value="${memberOut.toUnit}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label">转出单位</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="fromUnit" value="${memberOut.fromUnit}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label">转出单位地址</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="fromAddress" value="${memberOut.fromAddress}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label">转出单位联系电话</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="fromPhone" value="${memberOut.fromPhone}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label">转出单位传真</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="fromFax" value="${memberOut.fromFax}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label">转出单位邮编</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="fromPostCode"
							   value="${empty memberOut.fromPostCode?"100875":memberOut.fromPostCode}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label">党费缴纳至年月</label>
				<div class="col-xs-6">
					<div class="input-group">
						<input required class="form-control date-picker" name="_payTime" type="text"
							   data-date-format="yyyy-mm"
							   data-date-min-view-mode="1" value="${cm:formatDate(memberOut.payTime,'yyyy-MM')}" />
						<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
					</div>
				</div>
			</div>
				</div>
			<div class="col-xs-4">
			<div class="form-group">
				<label class="col-xs-4 control-label">介绍信有效期天数</label>
				<div class="col-xs-6">
                        <input required class="form-control digits" type="text" name="validDays" value="${memberOut.validDays}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label">办理时间</label>
				<div class="col-xs-6">
					<div class="input-group">
						<input required class="form-control date-picker" name="_handleTime" type="text"
							   data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberOut.handleTime,'yyyy-MM-dd')}" />
						<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label">是否有回执</label>
				<div class="col-xs-6">
					<label>
						<input name="hasReceipt" ${memberOut.hasReceipt?"checked":""}
							    type="checkbox" />
						<span class="lbl"></span>
					</label>
				</div>
			</div>

			<%--<div class="form-group">
				<label class="col-xs-4 control-label">返回修改原因</label>
				<div class="col-xs-6">
					<textarea class="form-control limited" type="text" name="reason" rows="5">${memberOut.reason}</textarea>
				</div>
			</div>--%>
				</div></div>
    </form>

<div class="modal-footer center">
    <a href="#" class="btn btn-default closeView">返回</a>
    <input type="submit" class="btn btn-primary" value="${memberOut!=null?"确定":"添加"}"/>
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
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
						//SysMsg.success('提交成功。', '成功',function(){
							$("#jqGrid").trigger("reloadGrid");
							$(".closeView").click();
						//});
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
			$("#modalForm input[name=gender]").val(_cMap.GENDER_MAP[gender]);
			$("#modalForm input[name=birth]").val(birth);
			$("#modalForm input[name=nation]").val(nation);
			$("#modalForm input[name=politicalStatus]").val(_cMap.MEMBER_POLITICAL_STATUS_MAP[politicalStatus]);
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