<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${psAdmin!=null?'编辑':'添加'}二级党校管理员</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/ps/psAdmin_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${psAdmin.id}">
		<input type="hidden" name="psId" value="${psId}">
		<div class="form-group">
			<label class="col-xs-3 control-label"><span class="star">*</span> 管理员类型</label>
			<div class="col-xs-6 ">
				<select required id="typeSelect" name="type" data-placeholder="请选择管理员类型"
						data-rel="select2" data-width="270">
					<option></option>
					<c:forEach items="<%=PsConstants.PS_ADMIN_TYPE_MAP%>" var="PS_ADMIN_TYPE_MAP">
						<option value="${PS_ADMIN_TYPE_MAP.key}">${PS_ADMIN_TYPE_MAP.value}</option>
					</c:forEach>
				</select>
				<script>
					$("#typeSelect").val('${psAdmin.type}');
				</script>
			</div>
		</div>
		<div class="form-group" id="personnelTypeDiv">
			<label class="col-xs-3 control-label"><span class="star">*</span> 姓名</label>
			<div class="col-xs-6">
				<select required name="userId"
						data-rel="select2-ajax"
						data-toggle="userId"
						data-ajax-url="${ctx}/member_selects"
						data-width="270"
						data-placeholder="请输入账号或姓名或学工号">
					<option value="${psAdmin.userId}">${sysUser.realname}-${sysUser.code}</option>
				</select>
			</div>
		</div>
		<c:if test="${not empty psAdmin}">
		<div class="form-group">
			<label class="col-xs-3 control-label"> 所在单位及职务</label>
			<div class="col-xs-6">
				<input class="form-control" type="text" name="title" value="${psAdmin.title}">
			</div>
		</div>
		</c:if>
		<div class="form-group">
			<label class="col-xs-3 control-label"> 任职起始时间</label>
			<div class="col-xs-6">
				<div class="input-group">
					<input class="form-control date-picker" name="_startDate" type="text"
						   data-date-format="yyyy.mm.dd"
						   value="${empty psMember?cm:formatDate(now,'yyyy.MM.dd'):cm:formatDate(psMember.startDate,'yyyy.MM.dd')}"/>
					<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
				</div>
			</div>
		</div>
		<c:if test="${not empty psAdmin}">
		<div class="form-group">
			<label class="col-xs-3 control-label"> 联系方式</label>
			<div class="col-xs-6">
				<input class="form-control" type="text" name="mobile" value="${psAdmin.mobile}">
			</div>
		</div>
		</c:if>
		<div class="form-group">
			<label class="col-xs-3 control-label"> 备注</label>
			<div class="col-xs-6">
				<textarea class="form-control" name="remark">${psAdmin.remark}</textarea>
			</div>
		</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty psAdmin?'确定':'添加'}</button>
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
                        $("#jqGrid2").trigger("reloadGrid");
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    //$("#modalForm :checkbox").bootstrapSwitch();
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $('#modalForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$('textarea.limited').inputlimiter();
    $.register.date($('.date-picker'));
</script>