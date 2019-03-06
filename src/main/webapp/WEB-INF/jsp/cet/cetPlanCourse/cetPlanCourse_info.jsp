<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/cet/constants.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>编辑课程信息</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cet/cetPlanCourse_info" id="modalForm" method="post">
        <input type="hidden" name="id" value="${cetPlanCourse.id}">
        <div class="form-group" id="_startTime">
            <label class="col-xs-4 control-label"><span class="star">*</span>开始时间</label>
            <div class="col-xs-6">
                <div class="input-group">
                    <input class="form-control datetime-picker" required type="text" name="startTime"
                           value="${cm:formatDate(cetPlanCourse.startTime, "yyyy-MM-dd HH:mm")}">
							<span class="input-group-addon">
                            <i class="fa fa-calendar bigger-110"></i>
                        </span>
                </div>
            </div>
        </div>
        <div class="form-group" id="_endTime">
            <label class="col-xs-4 control-label"><span class="star">*</span>结束时间</label>
            <div class="col-xs-6">
                <div class="input-group">
                    <input class="form-control datetime-picker" required type="text" name="endTime"
                           value="${cm:formatDate(cetPlanCourse.endTime, "yyyy-MM-dd HH:mm")}">
							<span class="input-group-addon">
                            <i class="fa fa-calendar bigger-110"></i>
                        </span>
                </div>
            </div>
        </div>
        <c:if test="${cetProjectPlan.type == CET_PROJECT_PLAN_TYPE_SELF}">
        <div class="form-group">
            <label class="col-xs-4 control-label">是否要求上传学习心得</label>
            <div class="col-xs-6">
                <input type="checkbox" class="big"
                       name="needNote" ${(cetPlanCourse.needNote)?"checked":""}/>
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
    $("#modalForm :checkbox").bootstrapSwitch();
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