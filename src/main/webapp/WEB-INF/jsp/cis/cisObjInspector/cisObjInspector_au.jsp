<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cisObjInspector!=null}">编辑</c:if><c:if test="${cisObjInspector==null}">添加</c:if>干部考察材料中的考察组成员</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cisObjInspector_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${cisObjInspector.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>obj_id</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="objId" value="${cisObjInspector.objId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>inspector_id</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="inspectorId" value="${cisObjInspector.inspectorId}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${cisObjInspector!=null}">确定</c:if><c:if test="${cisObjInspector==null}">添加</c:if>"/>
</div>

<script>
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