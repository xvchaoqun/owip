<%@ page import="sys.constants.SystemConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="MEMBER_POLITICAL_STATUS_MAP" value="<%=SystemConstants.MEMBER_POLITICAL_STATUS_MAP%>"/>
<c:set var="MEMBER_INOUT_TYPE_MAP" value="<%=SystemConstants.MEMBER_INOUT_TYPE_MAP%>"/>
<c:set var="GENDER_MAP" value="<%=SystemConstants.GENDER_MAP%>"/>
<c:set var="MEMBER_OUT_STATUS_OW_VERIFY" value="<%=SystemConstants.MEMBER_OUT_STATUS_OW_VERIFY%>"/>
    <h3><c:if test="${memberOut!=null}">编辑</c:if><c:if test="${memberOut==null}">添加</c:if>组织关系转出</h3>
	<hr/>

    <form class="form-horizontal" action="${ctx}/memberOut_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${memberOut.id}">
		<div class="row">
			<div class="col-xs-4">
			<div class="form-group">
				<label class="col-xs-3 control-label">用户</label>
				<div class="col-xs-6">
					<select required data-rel="select2-ajax" data-ajax-url="${ctx}/member_selects"
							name="userId" data-placeholder="请输入账号或姓名或学工号">
						<option value="${userBean.userId}">${userBean.realname}</option>
					</select>
				</div>
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
					<input disabled class="form-control" type="text" value="${GENDER_MAP.get(userBean.gender)}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">年龄</label>
				<div class="col-xs-6">
                        <input disabled class="form-control" type="text" name="age"
							   value="${cm:intervalYearsUntilNow(userBean.birth)}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">民族</label>
				<div class="col-xs-6">
                        <input disabled class="form-control" type="text" name="nation" value="${userBean.nation}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">政治面貌</label>
				<div class="col-xs-6">
					<input disabled class="form-control" type="text" value="${MEMBER_POLITICAL_STATUS_MAP.get(userBean.politicalStatus)}">
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
							   data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberOut.payTime,'yyyy-MM-dd')}" />
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
							   class="ace ace-switch ace-switch-5" type="checkbox" />
						<span class="lbl"></span>
					</label>
				</div>
			</div>

			<div class="form-group">
				<label class="col-xs-4 control-label">返回修改原因</label>
				<div class="col-xs-6">
					<textarea class="form-control limited" type="text" name="reason" rows="5">${memberOut.reason}</textarea>
				</div>
			</div>
				</div></div>
    </form>

<c:if test="${memberOut.status!=MEMBER_OUT_STATUS_OW_VERIFY}">
<div class="modal-footer">
    <a href="#" class="btn btn-default closeView">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${memberOut!=null}">确定</c:if><c:if test="${memberOut==null}">添加</c:if>"/>
</div>
	</c:if>
<c:if test="${memberOut.status==MEMBER_OUT_STATUS_OW_VERIFY}">
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
                        SysMsg.success('操作成功。', '成功');
                    }
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
	register_user_select($('#modalForm select[name=userId]'));
</script>