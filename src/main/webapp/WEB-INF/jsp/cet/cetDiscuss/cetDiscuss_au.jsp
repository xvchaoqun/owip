<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cetDiscuss!=null}">编辑</c:if><c:if test="${cetDiscuss==null}">添加</c:if>分组研讨</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cet/cetDiscuss_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${cetDiscuss.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">所属培训方案</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="planId" value="${cetDiscuss.planId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">开始日期</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="startDate" value="${cetDiscuss.startDate}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">结束日期</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="endDate" value="${cetDiscuss.endDate}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">研讨会名称</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="name" value="${cetDiscuss.name}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">负责单位类型</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="unitType" value="${cetDiscuss.unitType}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">学时</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="period" value="${cetDiscuss.period}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">排序</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="sortOrder" value="${cetDiscuss.sortOrder}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="remark" value="${cetDiscuss.remark}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${cetDiscuss!=null}">确定</c:if><c:if test="${cetDiscuss==null}">添加</c:if></button>
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