<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${trainCourse!=null}">编辑</c:if><c:if test="${trainCourse==null}">添加</c:if>培训课程</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/trainCourse_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${trainCourse.id}">
        <input type="hidden" name="trainId" value="${param.trainId}">
			<div class="form-group">
				<label class="col-xs-3 control-label">培训班次</label>
				<div class="col-xs-6 label-text">
					${train.name}
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">名称</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="name" value="${trainCourse.name}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">教师名称</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="teacher" value="${trainCourse.teacher}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">开始时间</label>
				<div class="col-xs-6">
					<div class="input-group">
						<input class="form-control datetime-picker required" type="text"  name="_startTime"
							   value="${cm:formatDate(trainCourse.startTime, "yyyy-MM-dd HH:mm")}">
							<span class="input-group-addon">
                            <i class="fa fa-calendar bigger-110"></i>
                        </span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">结束时间</label>
				<div class="col-xs-6">
					<div class="input-group">
						<input class="form-control datetime-picker required" type="text"  name="_endTime"
							   value="${cm:formatDate(trainCourse.endTime, "yyyy-MM-dd HH:mm")}">
							<span class="input-group-addon">
                            <i class="fa fa-calendar bigger-110"></i>
                        </span>
					</div>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${trainCourse!=null}">确定</c:if><c:if test="${trainCourse==null}">添加</c:if>"/>
</div>

<script>
	register_datetime($('.datetime-picker'));
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
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