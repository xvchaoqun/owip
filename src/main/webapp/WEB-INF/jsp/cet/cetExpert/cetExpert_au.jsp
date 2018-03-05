<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cetExpert!=null}">编辑</c:if><c:if test="${cetExpert==null}">添加</c:if>专家信息</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cet/cetExpert_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${cetExpert.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">姓名</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="realname" value="${cetExpert.realname}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">所在单位</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="unit" value="${cetExpert.unit}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">职务和职称</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="post" value="${cetExpert.post}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">联系方式</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="contact" value="${cetExpert.contact}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
					<textarea class="form-control limited"
							  name="remark">${cetExpert.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${cetExpert!=null}">确定</c:if><c:if test="${cetExpert==null}">添加</c:if></button>
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