<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cetParty!=null}">编辑</c:if><c:if test="${cetParty==null}">添加</c:if>院系级党委</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cet/cetParty_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${cetParty.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">所属基层党组织</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="partyId" value="${cetParty.partyId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">管理员</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="userId" value="${cetParty.userId}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${cetParty!=null}">确定</c:if><c:if test="${cetParty==null}">添加</c:if></button>
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