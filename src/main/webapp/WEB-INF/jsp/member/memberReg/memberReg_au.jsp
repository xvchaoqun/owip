<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${memberReg!=null}">编辑用户注册信息</c:if><c:if test="${memberReg==null}">添加系统账号</c:if></h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/memberReg_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${memberReg.id}">
        <input type="hidden" name="username" value="${memberReg.username}">
        <input type="hidden" name="code" value="${memberReg.code}">
        <input type="hidden" name="userId" value="${memberReg.userId}">

			<div class="form-group">
				<label class="col-xs-4 control-label"><span class="star">*</span>联系${_p_partyName}</label>
				<div class="col-xs-6">
					<select required data-rel="select2-ajax"
							data-width="273"
							data-ajax-url="${ctx}/party_selects?del=0&auth=${not empty memberReg?0:1}"
							name="partyId" data-placeholder="请选择">
						<option value="${party.id}">${party.name}</option>
					</select>
					<script>
						$("#modalForm select[name=partyId]").val('${memberReg.partyId}');
					</script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label"><span class="star">*</span>类别</label>
				<div class="col-xs-6 label-text">
					<div class="input-group">
						<c:forEach var="userType" items="${USER_TYPE_MAP}">
							<label>
								<input required name="type" type="radio" class="ace" value="${userType.key}"
									   <c:if test="${memberReg.type==userType.key}">checked</c:if>/>
								<span class="lbl" style="padding-right: 5px;"> ${userType.value}</span>
							</label>
						</c:forEach>
					</div>
				</div>
			</div>
			<c:if test="${not empty memberReg}">
			<div class="form-group">
				<label class="col-xs-4 control-label">用户名</label>
				<div class="col-xs-6 label-text">
					${memberReg.username}
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label">学工号</label>
				<div class="col-xs-6 label-text">
					${memberReg.code}
				</div>
			</div>
			</c:if>
			<div class="form-group">
				<label class="col-xs-4 control-label"><span class="star">*</span>真实姓名</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="realname" value="${memberReg.realname}">
				</div>
			</div>

			<div class="form-group">
				<label class="col-xs-4 control-label"><span class="star">*</span>身份证号码</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="idcard" value="${memberReg.idcard}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label">手机号码</label>
				<div class="col-xs-6">
                        <input class="form-control" type="text" name="phone" value="${memberReg.phone}">
						<span class="help-block">手机号码用于账号本人进行密码找回操作，请正确填写</span>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
	<c:if test="${empty memberReg}">
		<div class="note">注：添加成功后，将保存至“审批通过”列表。</div>
	</c:if>
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" type="button" class="btn btn-primary"
		   data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"><i class="fa fa-check"></i> 确定</button>
</div>

<script>
	$("#submitBtn").click(function () {
        $("#modalForm").submit();
        return false;
    });
    $("#modalForm").validate({
        submitHandler: function (form) {
        	var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
						$("#modal").modal("hide")
						$("#jqGrid").trigger("reloadGrid");
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    $("#modalForm :checkbox").bootstrapSwitch();
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $.register.del_select($('#modalForm select[name=partyId]'));
</script>