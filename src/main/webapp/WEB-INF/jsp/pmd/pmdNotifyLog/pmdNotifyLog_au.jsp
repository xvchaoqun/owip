<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${pmdNotifyLog!=null}">编辑</c:if><c:if test="${pmdNotifyLog==null}">添加</c:if>支付通知</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/pmdNotifyLog_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${pmdNotifyLog.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">交易时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="orderDate" value="${pmdNotifyLog.orderDate}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">订单号</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="orderNo" value="${pmdNotifyLog.orderNo}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">订单金额</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="amount" value="${pmdNotifyLog.amount}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">支付平台交易流水号</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="jylsh" value="${pmdNotifyLog.jylsh}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">订单支付状态</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="tranStat" value="${pmdNotifyLog.tranStat}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">通知类型</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="returnType" value="${pmdNotifyLog.returnType}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">签名</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="sign" value="${pmdNotifyLog.sign}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">验签结果</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="verifySign" value="${pmdNotifyLog.verifySign}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">返回时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="retTime" value="${pmdNotifyLog.retTime}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">IP</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="ip" value="${pmdNotifyLog.ip}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input id="submitBtn" type="button" class="btn btn-primary" value="<c:if test="${pmdNotifyLog!=null}">确定</c:if><c:if test="${pmdNotifyLog==null}">添加</c:if>"/>
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