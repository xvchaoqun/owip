<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${dispatchUnitRelate!=null}">编辑</c:if><c:if test="${dispatchUnitRelate==null}">添加</c:if>单位发文关联单位</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/dispatchUnitRelate_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${dispatchUnitRelate.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">单位发文</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="dispatchUnitId" value="${dispatchUnitRelate.dispatchUnitId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">关联单位</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="unitId" value="${dispatchUnitRelate.unitId}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${dispatchUnitRelate!=null}">确定</c:if><c:if test="${dispatchUnitRelate==null}">添加</c:if>"/>
</div>

<script>
    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        page_reload();
                        toastr.success('操作成功。', '成功');
                    }
                }
            });
        }
    });
    $('[data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>