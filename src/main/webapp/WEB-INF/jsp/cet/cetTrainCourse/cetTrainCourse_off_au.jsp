<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cetTrainCourse!=null}">编辑</c:if><c:if test="${cetTrainCourse==null}">添加</c:if>培训课程</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cet/cetTrainCourse_off_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${cetTrainCourse.id}">
        <input type="hidden" name="trainId" value="${param.trainId}">
			<div class="form-group">
				<label class="col-xs-3 control-label">培训班次</label>
				<div class="col-xs-6 label-text">
					${cetTrain.name}
				</div>
			</div>
			<c:if test="${empty cetTrainCourse.courseId}">
			<div class="form-group">
				<label class="col-xs-3 control-label">是否专题班测评</label>

				<div class="col-xs-6">
					<label>
						<input name="isGlobal" ${cetTrainCourse.isGlobal?"checked":""} type="checkbox"/>
						<span class="lbl"></span>
					</label>
				</div>
			</div>
			<div class="form-group" id="_name">
				<label class="col-xs-3 control-label"><span class="star">*</span>课程名称</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="name" value="${cetTrainCourse.name}">
				</div>
			</div>
			<div class="form-group" id="_teacher">
				<label class="col-xs-3 control-label"><span class="star">*</span>教师姓名</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="teacher" value="${cetTrainCourse.teacher}">
				</div>
			</div>
			</c:if>
			<c:if test="${not empty cetTrainCourse.courseId}">
			<div class="form-group" id="_name">
				<label class="col-xs-3 control-label">课程名称</label>
				<div class="col-xs-6 label-text">
						${cetTrainCourse.name}
				</div>
			</div>
			<div class="form-group" id="_teacher">
				<label class="col-xs-3 control-label">教师姓名</label>
				<div class="col-xs-6 label-text">
						${cetTrainCourse.teacher}
				</div>
			</div>
			</c:if>
			<div class="form-group" id="_startTime">
				<label class="col-xs-3 control-label"><span class="star">*</span>开始时间</label>
				<div class="col-xs-6">
					<div class="input-group">
						<input class="form-control datetime-picker" required type="text"  name="startTime"
							   value="${cm:formatDate(cetTrainCourse.startTime, "yyyy-MM-dd HH:mm")}">
							<span class="input-group-addon">
                            <i class="fa fa-calendar bigger-110"></i>
                        </span>
					</div>
				</div>
			</div>
			<div class="form-group" id="_endTime">
				<label class="col-xs-3 control-label"><span class="star">*</span>结束时间</label>
				<div class="col-xs-6">
					<div class="input-group">
						<input class="form-control datetime-picker" required type="text"  name="endTime"
							   value="${cm:formatDate(cetTrainCourse.endTime, "yyyy-MM-dd HH:mm")}">
							<span class="input-group-addon">
                            <i class="fa fa-calendar bigger-110"></i>
                        </span>
					</div>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${cetTrainCourse!=null}">确定</c:if><c:if test="${cetTrainCourse==null}">添加</c:if>"/>
</div>

<script>
	<c:if test="${empty cetTrainCourse.courseId}">
	function isGlobalChanged(){
		if($('#modalForm input[name=isGlobal]').bootstrapSwitch("state")) {

			$("#_name label").html("测评标题");
			$("#_name input").val("专题班");
			$("#_teacher").hide();
			$("#_teacher input").removeAttr("required");
			$("#_startTime label").html("测评开始时间");
			$("#_endTime").hide();
			$("#_endTime input").removeAttr("required");
		}else{

			$("#_name label").html("课程名称");
			$("#_name input").val($.trimHtml("${cetTrainCourse.name}"));
			$("#_teacher").show();
			$("#_teacher input").attr("required", "required");
			$("#_startTime label").html("开始时间");
			$("#_endTime").show();
			$("#_endTime input").attr("required", "required");
		}
	}
	$('#modalForm input[name=isGlobal]').on('switchChange.bootstrapSwitch', function(event, state) {
		isGlobalChanged()
	});
	isGlobalChanged()
	</c:if>
	$.register.datetime($('.datetime-picker'));
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