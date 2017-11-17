<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${pmdMember!=null}">编辑</c:if><c:if test="${pmdMember==null}">添加</c:if>党费缴纳记录</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/pmdMember_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${pmdMember.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">缴费订单号</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="orderNo" value="${pmdMember.orderNo}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">党费缴纳月份</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="payMonth" value="${pmdMember.payMonth}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">用户</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="userId" value="${pmdMember.userId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">所在党委</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="partyId" value="${pmdMember.partyId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">所在党支部</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="branchId" value="${pmdMember.branchId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">党员类别</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="type" value="${pmdMember.type}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">应交金额</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="duePay" value="${pmdMember.duePay}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">实交金额</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="realPay" value="${pmdMember.realPay}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">按时/延迟缴费</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="isDelay" value="${pmdMember.isDelay}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">延迟缴费的原因</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="delayReason" value="${pmdMember.delayReason}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">缴费状态</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="hasPay" value="${pmdMember.hasPay}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">缴费方式</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="isOnlinePay" value="${pmdMember.isOnlinePay}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input id="submitBtn" type="button" class="btn btn-primary" value="<c:if test="${pmdMember!=null}">确定</c:if><c:if test="${pmdMember==null}">添加</c:if>"/>
</div>

<script>
    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
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