<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cetCourse!=null}">编辑</c:if><c:if test="${cetCourse==null}">添加</c:if>实践教学</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cet/cetCourse_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${cetCourse.id}">
        <input type="hidden" name="type" value="${type}">
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>设立时间</label>
				<div class="col-xs-6">
					<div class="input-group">
						<input required class="form-control date-picker" name="foundDate"
							   type="text"
							   data-date-format="yyyy-mm-dd"
							   value="${cm:formatDate(cetCourse.foundDate,'yyyy-MM-dd')}"/>
                                            <span class="input-group-addon"> <i
													class="fa fa-calendar bigger-110"></i></span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">${empty cetCourse?'':'*'}编号</label>
				<div class="col-xs-6">
					<input ${empty cetCourse?'':'required'} class="form-control num" type="text" name="num" value="${cetCourse.num}">
					<c:if test="${empty cetCourse}"><span class="label-inline"> * 留空自动生成</span></c:if>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>实践教学名称</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="name" value="${cetCourse.name}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>实践教学地点</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="address" value="${cetCourse.address}">
				</div>
			</div>

			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>学时</label>
				<div class="col-xs-6">
                        <input required class="form-control period" type="text" name="period" value="${cetCourse.period}">
				</div>
			</div>

			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
					<textarea class="form-control limited"
							  name="remark">${cetCourse.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${cetCourse!=null}">确定</c:if><c:if test="${cetCourse==null}">添加</c:if></button>
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
	$.register.date($('.date-picker'));
</script>