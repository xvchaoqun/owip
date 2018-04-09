<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cetTrainCourseObj!=null}">编辑</c:if><c:if test="${cetTrainCourseObj==null}">添加</c:if>选课学员</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cet/cetTrainCourseObj_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${cetTrainCourseObj.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">所属培训课程</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="trainCourseId" value="${cetTrainCourseObj.trainCourseId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">培训对象</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="objId" value="${cetTrainCourseObj.objId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">提交学习心得数</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="num" value="${cetTrainCourseObj.num}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">是否结业</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="isFinished" value="${cetTrainCourseObj.isFinished}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${cetTrainCourseObj!=null}">确定</c:if><c:if test="${cetTrainCourseObj==null}">添加</c:if></button>
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