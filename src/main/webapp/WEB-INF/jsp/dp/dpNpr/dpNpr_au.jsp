<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${dpNpr!=null?'编辑':'添加'}党外代表人士</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/dp/dpNpr_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${dpNpr.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 账号</label>
				<div class="col-xs-6">
					<c:if test="${not empty dpNpr}">
						<input type="hidden" value="${dpNpr.userId}" name="userId">
					</c:if>
					<select ${not empty dpNpr?"disabled data-theme='default'":""} required data-rel="select2-ajax"
																				  data-ajax-url="${ctx}/dp/teacher_select"
																				  name="userId" data-width="270"
																				  data-placeholder="请输入账号或姓名或工作证号">
						<option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"> 所属类别</label>
				<div class="col-xs-6">
                        <select data-width="270" class="form-control" name="type" data-rel="select2"
							data-placeholder="请选择类别">
							<option></option>
							<c:import url="/metaTypes?__code=mc_dp_npr_type"/>
						</select>
					<script>
						$("#modalForm select[name=type]").val('${dpNpr.type}');
					</script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"> 所属级别</label>
				<div class="col-xs-6">
                        <select data-width="270" class="form-control" name="level" data-rel="select2"
						data-placeholder="请选择级别">
							<option></option>
							<c:import url="/metaTypes?__code=mc_dp_npr_level"/>
						</select>
					<script>
						$("#modalForm select[name=level]").val("${dpNpr.level}")
					</script>
				</div>
			</div>
		<div class="form-group">
			<label class="col-xs-3 control-label"> 所在单位及职务</label>
			<div class="col-xs-6">
				<textarea class="form-control" rows="3" name="unitPost">${dpNpr.unitPost}</textarea>
			</div>
		</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"> 备注</label>
				<div class="col-xs-6">
					<textarea class="form-control" rows="3" name="remark">${dpNpr.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty dpNpr?'确定':'添加'}</button>
</div>
<script>
    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $("#jqGrid").trigger("reloadGrid");
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    $("#modalForm :checkbox").bootstrapSwitch();
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $('textarea.limited').inputlimiter();
    $.register.date($('.date-picker'));
</script>