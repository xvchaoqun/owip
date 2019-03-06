<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${memberReg!=null}">编辑</c:if><c:if test="${memberReg==null}">添加</c:if>用户注册</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/memberReg_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${memberReg.id}">
        <input type="hidden" name="username" value="${memberReg.username}">
        <input type="hidden" name="code" value="${memberReg.code}">
        <input type="hidden" name="userId" value="${memberReg.userId}">

			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>联系分党委</label>
				<div class="col-xs-6">
					<select required name="partyId" data-rel="select2"
							data-placeholder="请选择联系分党委" data-width="350">
						<option></option>
						<c:forEach var="entity" items="${partyMap}">
							<c:if test="${!entity.value.isDeleted}">
							<option value="${entity.key}">${entity.value.name}</option>
							</c:if>
						</c:forEach>
					</select>
					<script>
						$("#modalForm select[name=partyId]").val('${memberReg.partyId}');
					</script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">用户名</label>
				<div class="col-xs-6 label-text">
					${memberReg.username}
				</div>
			</div>

			<div class="form-group">
				<label class="col-xs-3 control-label">类别</label>
				<div class="col-xs-6 label-text">
					<div class="input-group">
						<c:forEach var="userType" items="${USER_TYPE_MAP}">
							<label>
								<input name="type" type="radio" class="ace" value="${userType.key}"
									   <c:if test="${memberReg.type==userType.key}">checked</c:if>/>
								<span class="lbl" style="padding-right: 5px;"> ${userType.value}</span>
							</label>
						</c:forEach>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">学工号</label>
				<div class="col-xs-6 label-text">
					${memberReg.code}
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>真实姓名</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="realname" value="${memberReg.realname}">
				</div>
			</div>

			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>身份证</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="idcard" value="${memberReg.idcard}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>手机</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="phone" value="${memberReg.phone}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
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
						$("#jqGrid").trigger("reloadGrid");
                    }
                }
            });
        }
    });
    $("#modalForm :checkbox").bootstrapSwitch();
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>