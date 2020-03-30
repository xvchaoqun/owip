<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${pmdPartyDonate!=null?'编辑':'添加'}党员捐赠党费</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/pmd/pmdPartyDonate_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${pmdPartyDonate.id}">
		<c:if test="${isPmdParty || isPmdBranch}">
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 姓名</label>
				<div class="col-xs-6">
					<select required class="form-control" data-rel="select2-ajax" data-width="272"
							data-ajax-url="${ctx}/sysUser_selects?needPrivate=1"
							name="userId" data-placeholder="请输入账号或姓名或学工号">
						<option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
					</select>
				</div>
			</div>
		</c:if>
		<div class="form-group">
			<label class="col-xs-3 control-label"> 缴费日期</label>
			<div class="col-xs-6">
				<div class="input-group">
					<input class="form-control date-picker" name="donateDate" type="text"
						   data-date-format="yyyy-mm-dd"
						   value="${empty pmdPartyDonate?_today:cm:formatDate(pmdPartyDonate.donateDate,'yyyy-MM-dd')}"/>
					<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
				</div>
			</div>
		</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 金额</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="money" value="${pmdPartyDonate.money}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"> 说明</label>
				<div class="col-xs-6">
                        <textarea class="form-control" name="explain">${pmdPartyDonate.explain}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty pmdPartyDonate?'确定':'添加'}</button>
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
    //$("#modalForm :checkbox").bootstrapSwitch();
    $.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#modalForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$('textarea.limited').inputlimiter();
    $.register.date($('.date-picker'));
</script>