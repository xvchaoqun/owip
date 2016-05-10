<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${sysUserReg!=null}">编辑</c:if><c:if test="${sysUserReg==null}">添加</c:if>用户注册</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/sysUserReg_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${sysUserReg.id}">
        <input type="hidden" name="username" value="${sysUserReg.username}">
        <input type="hidden" name="code" value="${sysUserReg.code}">
        <input type="hidden" name="userId" value="${sysUserReg.userId}">

			<div class="form-group">
				<label class="col-xs-3 control-label">所属分党委</label>
				<div class="col-xs-6">
					<select required name="partyId" data-rel="select2"
							data-placeholder="请选择所属分党委" data-width="350">
						<option></option>
						<c:forEach var="entity" items="${partyMap}">
							<option value="${entity.key}">${entity.value.name}</option>
						</c:forEach>
					</select>
					<script>
						$("#modalForm select[name=partyId]").val('${sysUserReg.partyId}');
					</script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">用户名</label>
				<div class="col-xs-6 label-text">
					${sysUserReg.username}
				</div>
			</div>

			<div class="form-group">
				<label class="col-xs-3 control-label">类别</label>
				<div class="col-xs-6">
					<div class="radio">
						<c:forEach var="userType" items="${USER_TYPE_MAP}">
							<label>
								<input name="type" type="radio" class="ace" value="${userType.key}"
									   <c:if test="${sysUserReg.type==userType.key}">checked</c:if>/>
								<span class="lbl"> ${userType.value}</span>
							</label>
						</c:forEach>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">学工号</label>
				<div class="col-xs-6 label-text">
					${sysUserReg.code}
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">真实姓名</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="realname" value="${sysUserReg.realname}">
				</div>
			</div>

			<div class="form-group">
				<label class="col-xs-3 control-label">身份证</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="idcard" value="${sysUserReg.idcard}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">手机</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="phone" value="${sysUserReg.phone}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="确定"/>
</div>

<script>
	jgrid_left = $("#jqGrid").closest(".ui-jqgrid-bdiv").scrollLeft();
	jgrid_top = $("#jqGrid").closest(".ui-jqgrid-bdiv").scrollTop();
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
						$("#modal").modal("hide")
						SysMsg.success('提交成功。', '成功',function(){
							$("#jqGrid").trigger("reloadGrid");
							//$(".closeView").click();
						});
                    }
                }
            });
        }
    });
    $("#modalForm :checkbox").bootstrapSwitch();
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>