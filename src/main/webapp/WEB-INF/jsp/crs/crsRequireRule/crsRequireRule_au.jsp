<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${crsRequireRule!=null}">编辑</c:if><c:if test="${crsRequireRule==null}">添加</c:if>规则</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/crsRequireRule_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${crsRequireRule.id}">
        <input type="hidden" name="postRequireId" value="${crsPostRequire.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">规则类型</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="name" value="${crsRequireRule.name}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
                    <textarea class="form-control limited" type="text" name="remark" >${crsRequireRule.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${crsRequireRule!=null}">确定</c:if><c:if test="${crsRequireRule==null}">添加</c:if>"/>
</div>

<script>
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal("hide");
                        $("#jqGrid2").trigger("reloadGrid");
                    }
                }
            });
        }
    });
    $("#modalForm :checkbox").bootstrapSwitch();
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>