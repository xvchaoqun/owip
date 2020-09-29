<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${pmdFee!=null?'编辑':'添加'}党员缴纳党费</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/pmd/pmdFee_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${pmdFee.id}">
		<c:if test="${hasShowUser}">
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 姓名</label>
				<div class="col-xs-6">
					<select required name="userId"  class="form-control"
							data-rel="select2-ajax" data-width="264"
							data-ajax-url="${ctx}/member_selects?status=${MEMBER_STATUS_NORMAL}"
							data-placeholder="请输入账号或姓名或学工号">
						<option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
					</select>
				</div>
			</div>
		</c:if>
		<div class="form-group">
			<label class="col-xs-3 control-label"><span class="star">*</span>缴费类型</label>
			<div class="col-xs-6">
				<select required data-rel="select2" name="type" data-width="264"
						data-placeholder="请选择">
					<option></option>
					<c:import url="/metaTypes?__id=${cm:getMetaClassByCode('mc_pmd_fee_type').id}"/>
				</select>
				<script type="text/javascript">
					$("#modalForm select[name=type]").val(${pmdFee.type});
				</script>
			</div>
		</div>

		<div class="form-group">
			<label class="col-xs-3 control-label"><span class="star">*</span>缴费月份</label>
			<div class="col-xs-6">
				<div class="input-group" style="width: 264px">
					<input required class="form-control date-picker" name="payMonth" type="text"
						   data-date-format="yyyy.mm" data-date-min-view-mode="1"
						   value="${cm:formatDate(empty pmdFee.payMonth?now:pmdFee.payMonth,'yyyy.MM')}" />
					<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
				</div>
			</div>
		</div>

			<%--<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 所属${_p_partyName}</label>
				<div class="col-xs-6">
					<select disabled data-rel="select2" name="partyId" data-width="264"
							data-placeholder="请选择">
						<option value="${party.id}">${party.name}</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"> 所在党支部</label>
				<div class="col-xs-6">
					<select disabled data-rel="select2" name="branchId" data-width="264"
							data-placeholder="请选择">
						<option value="${branch.id}">${branch.name}</option>
					</select>
				</div>
			</div>--%>
			<div class="form-group">
				<label class="col-xs-3 control-label"> 缴费金额</label>
				<div class="col-xs-6">
                        <input required class="form-control number" type="text" name="amt" value="${cm:stripTrailingZeros(pmdFee.amt)}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"> 缴费原因</label>
				<div class="col-xs-6">
					<textarea class="form-control" name="reason">${pmdFee.reason}</textarea>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 缴费方式</label>
				<div class="col-xs-6">
					<select required data-rel="select2" name="isOnlinePay" data-width="264"
							data-placeholder="请选择">
						<option></option>
						<option value="1">线上缴费</option>
						<option value="0">现金缴费</option>
					</select>
				</div>
			</div>
		<script>
			$("#modalForm select[name=isOnlinePay]").val('${pmdFee.isOnlinePay?"1":"0"}');
		</script>
			<div class="form-group">
				<label class="col-xs-3 control-label"> 备注</label>
				<div class="col-xs-6">
					<textarea class="form-control" name="remark">${pmdFee.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty pmdFee?'确定':'添加'}</button>
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
    $('#modalForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$('textarea.limited').inputlimiter();
    $.register.date($('.date-picker'));
</script>