<%@ page import="sys.constants.SystemConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="MEMBER_POLITICAL_STATUS_MAP" value="<%=SystemConstants.MEMBER_POLITICAL_STATUS_MAP%>"/>
<c:set var="GENDER_MAP" value="<%=SystemConstants.GENDER_MAP%>"/>
<c:set var="MEMBER_STAY_STATUS_OW_VERIFY" value="<%=SystemConstants.MEMBER_STAY_STATUS_OW_VERIFY%>"/>
    <h3><c:if test="${memberStay!=null}">编辑</c:if><c:if test="${memberStay==null}">添加</c:if>公派留学生党员申请组织关系暂留</h3>
<hr/>
    <form class="form-horizontal" action="${ctx}/memberStay_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${memberStay.id}">
		<div class="row">
			<div class="col-xs-4">
			<div class="form-group">
				<label class="col-xs-3 control-label">用户</label>
				<div class="col-xs-6">
					<select required data-rel="select2-ajax" data-ajax-url="${ctx}/member_selects"
							name="userId" data-placeholder="请输入账号或姓名或学工号">
						<option value="${sysUser.id}">${sysUser.realname}</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">学工号</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="code" value="${memberStay.code}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">姓名</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="realname" value="${memberStay.realname}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">性别</label>
				<div class="col-xs-6">
					<select required data-rel="select2" name="gender" data-placeholder="请选择">
						<option></option>
						<c:forEach items="${GENDER_MAP}" var="_gender">
							<option value="${_gender.key}">${_gender.value}</option>
						</c:forEach>
					</select>
					<script>
						$("#modalForm select[name=gender]").val(${memberStay.gender});
					</script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">年龄</label>
				<div class="col-xs-6">
                        <input required class="form-control digits" type="text" name="age" value="${memberStay.age}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">民族</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="nation" value="${memberStay.nation}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">身份证</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="idcard" value="${memberStay.idcard}">
				</div>
			</div>
				</div>
				<div class="col-xs-4">
			<div class="form-group">
				<label class="col-xs-5 control-label">政治面貌</label>
				<div class="col-xs-6">
					<select required data-rel="select2" name="politicalStatus" data-placeholder="请选择" >
						<option></option>
						<c:forEach items="${MEMBER_POLITICAL_STATUS_MAP}" var="_status">
							<option value="${_status.key}">${_status.value}</option>
						</c:forEach>
					</select>
					<script>
						$("#modalForm select[name=politicalStatus]").val(${memberStay.politicalStatus});
					</script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-5 control-label">入党时间</label>
				<div class="col-xs-6">
					<div class="input-group">
						<input required class="form-control date-picker" name="_growTime" type="text"
							   data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberStay.growTime,'yyyy-MM-dd')}" />
						<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-5 control-label">留学国别</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="country" value="${memberStay.country}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-5 control-label">出国时间</label>
				<div class="col-xs-6">
					<div class="input-group">
						<input required class="form-control date-picker" name="_abroadTime" type="text"
							   data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberStay.abroadTime,'yyyy-MM-dd')}" />
						<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-5 control-label">预计回国时间</label>
				<div class="col-xs-6">
					<div class="input-group">
						<input required class="form-control date-picker" name="_returnTime" type="text"
							   data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberStay.returnTime,'yyyy-MM-dd')}" />
						<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-5 control-label">党费缴纳至年月</label>
				<div class="col-xs-6">
					<div class="input-group">
						<input required class="form-control date-picker" name="_payTime" type="text"
							   data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberStay.payTime,'yyyy-MM-dd')}" />
						<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
					</div>
				</div>
			</div>
				</div>
			<div class="col-xs-4">
			<div class="form-group">
				<label class="col-xs-6 control-label">手机号码</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="mobile" value="${memberStay.mobile}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-6 control-label">电子邮箱</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="email" value="${memberStay.email}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-6 control-label">国内联系人姓名</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="contactName" value="${memberStay.contactName}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-6 control-label">国内联系人手机号码</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="contactMobile" value="${memberStay.contactMobile}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-6 control-label">修改原因</label>
				<div class="col-xs-6">
					<textarea class="form-control limited" type="text" name="reason" rows="5">${memberStay.reason}</textarea>
				</div>
			</div>
				</div>
			</div>
    </form>
<c:if test="${memberStay.status!=MEMBER_STAY_STATUS_OW_VERIFY}">
	<div class="modal-footer">
		<a href="#" class="btn btn-default closeView">取消</a>
		<input type="submit" class="btn btn-primary" value="<c:if test="${memberStay!=null}">确定</c:if><c:if test="${memberStay==null}">添加</c:if>"/>
	</div>
</c:if>
<c:if test="${memberStay.status==MEMBER_STAY_STATUS_OW_VERIFY}">
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
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        page_reload();
                        toastr.success('操作成功。', '成功');
                    }
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();

	register_user_select($('#modalForm select[name=userId]'));
</script>