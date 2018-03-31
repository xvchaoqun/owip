<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cetColumnCourse!=null}">编辑</c:if><c:if test="${cetColumnCourse==null}">添加</c:if>课程栏目包含课程</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cet/cetColumnCourse_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${cetColumnCourse.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">栏目</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="columnId" value="${cetColumnCourse.columnId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">课程</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="courseId" value="${cetColumnCourse.courseId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">排序</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="sortOrder" value="${cetColumnCourse.sortOrder}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="remark" value="${cetColumnCourse.remark}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${cetColumnCourse!=null}">确定</c:if><c:if test="${cetColumnCourse==null}">添加</c:if></button>
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
</script>