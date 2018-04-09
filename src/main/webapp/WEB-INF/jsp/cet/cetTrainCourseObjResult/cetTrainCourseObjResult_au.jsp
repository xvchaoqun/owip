<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cetTrainCourseObjResult!=null}">编辑</c:if><c:if test="${cetTrainCourseObjResult==null}">添加</c:if>上级网上专题班完成情况</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cet/cetTrainCourseObjResult_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${cetTrainCourseObjResult.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">所属培训课程</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="trainCourseObjId" value="${cetTrainCourseObjResult.trainCourseObjId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">所属课程专题班</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="courseItemId" value="${cetTrainCourseObjResult.courseItemId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">完成课程数</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="courseNum" value="${cetTrainCourseObjResult.courseNum}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">完成学时数</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="period" value="${cetTrainCourseObjResult.period}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${cetTrainCourseObjResult!=null}">确定</c:if><c:if test="${cetTrainCourseObjResult==null}">添加</c:if></button>
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