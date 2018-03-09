<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set value="${param.type==1?'重点专题':'特色栏目'}" var="typeName"/>
<c:set value="${param.type==1?'重点子专题':'特色子栏目'}" var="subTypeName"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cetColumn!=null}">编辑</c:if><c:if test="${cetColumn==null}">添加</c:if>${param.fid>0?subTypeName:typeName}</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cet/cetColumn_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${cetColumn.id}">
        <input type="hidden" name="isOnline" value="${isOnline}">
        <input type="hidden" name="fid" value="${fid}">
        <input type="hidden" name="type" value="${type}">

		<div class="form-group">
			<label class="col-xs-3 control-label">名称</label>
			<div class="col-xs-6">
					<input required class="form-control" type="text" name="name" value="${cetColumn.name}">
			</div>
		</div>

		<div class="form-group">
			<label class="col-xs-3 control-label">备注</label>
			<div class="col-xs-6">
					<textarea class="form-control limited"
							  name="remark">${cetColumn.remark}</textarea>
			</div>
		</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${cetColumn!=null}">确定</c:if><c:if test="${cetColumn==null}">添加</c:if></button>
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