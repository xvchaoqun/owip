<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/cet/constants.jsp" %>
<c:set var="isPractice" value="${cetProjectPlan.type==CET_PROJECT_PLAN_TYPE_PRACTICE}"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>修改课程信息</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cet/cetTrainCourse_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${cetTrainCourse.id}">
        <input type="hidden" name="projectId" value="${projectId}">
        <input type="hidden" name="trainId" value="${trainId}">

        <c:if test="${cetProject.type== CET_PROJECT_TYPE_PARTY_SPECIAL
                || cetProject.type== CET_PROJECT_TYPE_PARTY_DAILY}">
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>培训形式</label>
            <div class="col-xs-6">
                <div class="input-group">
                    <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                        <input required type="radio" name="isOnline" id="isOnline0"
                               ${(empty cetTrainCourse || !cetTrainCourse.isOnline)?"checked":""} value="0">
                        <label for="isOnline0">
                            线下培训
                        </label>
                    </div>
                    <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                        <input required type="radio" name="isOnline" id="isOnline1"
                               ${cetTrainCourse.isOnline?"checked":""} value="1">
                        <label for="isOnline1">
                            线上培训
                        </label>
                    </div>
                </div>
            </div>
        </div>
        </c:if>

        <div class="form-group" id="_name">
            <label class="col-xs-3 control-label"><span class="star">*</span>${isPractice?'实践教学':'课程'}名称</label>
            <div class="col-xs-6">
                <textarea required class="form-control limited" maxlength="100"
							  name="name">${cetTrainCourse.name}</textarea>
            </div>
        </div>

        <c:if test="${!isPractice}">
        <div class="form-group" id="_teacher">
            <label class="col-xs-3 control-label">主讲人</label>
            <div class="col-xs-6">
                    <input class="form-control" type="text" name="teacher" value="${cetTrainCourse.teacher}">
            </div>
        </div>
        </c:if>
        <div class="form-group">
            <label class="col-xs-3 control-label">选课人数上限</label>
            <div class="col-xs-6">
                <input class="form-control number" type="text" name="applyLimit" value="${cetTrainCourse.applyLimit}">
                * 留空则不设上限
            </div>
        </div>
        <div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>学时</label>
				<div class="col-xs-6">
                        <input required class="form-control period" type="text" name="period" value="${cetTrainCourse.period}">
				</div>
			</div>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>开始时间</label>
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
            <label class="col-xs-3 control-label"><span class="star">*</span>结束时间</label>
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
        <c:if test="${!cetTrainCourse.isOnline}">
        <div class="form-group">
            <label class="col-xs-3 control-label"> ${isPractice?'实践教学':'上课'}地点</label>
            <div class="col-xs-6">
                <textarea class="form-control limited" maxlength="100"
							  name="address">${cetTrainCourse.address}</textarea>
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