<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/cet/constants.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>编辑上课信息</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cet/cetTrainCourse_info" id="modalForm" method="post">
        <input type="hidden" name="id" value="${cetTrainCourse.id}">
        <div class="form-group">
            <label class="col-xs-3 control-label">课程名称</label>
            <div class="col-xs-6 label-text">
                ${cetTrainCourse.cetCourse.name}
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">选课人数上限</label>
            <div class="col-xs-6">
                <input class="form-control number" type="text" name="applyLimit" value="${cetTrainCourse.applyLimit}">
                * 留空则不设上限
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">开始时间</label>
            <div class="col-xs-6">
                <div class="input-group">
                    <input class="form-control datetime-picker" required type="text" name="startTime"
                           value="${cm:formatDate(cetTrainCourse.startTime, "yyyy-MM-dd HH:mm")}">
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
                    <input class="form-control datetime-picker" required type="text" name="endTime"
                           value="${cm:formatDate(cetTrainCourse.endTime, "yyyy-MM-dd HH:mm")}">
							<span class="input-group-addon">
                            <i class="fa fa-calendar bigger-110"></i>
                        </span>
                </div>
            </div>
        </div>
        <c:if test="${cetTrainCourse.cetCourse.type==CET_COURSE_TYPE_OFFLINE}">
        <div class="form-group">
            <label class="col-xs-3 control-label">上课地点</label>
            <div class="col-xs-6">
                <input required class="form-control" type="text" name="address" value="${cetTrainCourse.address}">
            </div>
        </div>
        </c:if>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="确定"/>
</div>

<script>
    $.register.datetime($('.datetime-picker'));
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        $("#modal").modal('hide');
                        $("#jqGrid2").trigger("reloadGrid");
                    }
                }
            });
        }
    });
</script>