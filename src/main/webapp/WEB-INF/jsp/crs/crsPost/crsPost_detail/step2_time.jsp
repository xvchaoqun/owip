<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="space-4"></div>
<div style="width: 500px;">
    <div class="modal-header">
        <h3>设置报名时间</h3>
    </div>
    <div class="modal-body">
        <form class="form-horizontal" action="${ctx}/crsPost_detail/step2_time" id="modalForm" method="post">
            <input type="hidden" name="id" value="${crsPost.id}">

            <div class="form-group">
                <label class="col-xs-3 control-label">报名开始时间</label>

                <div class="col-xs-6">
                    <div class="input-group">
                        <input class="form-control datetime-picker" required type="text" name="startTime"
                               value="${cm:formatDate(crsPost.startTime, "yyyy-MM-dd HH:mm")}">
							<span class="input-group-addon">
                            <i class="fa fa-calendar bigger-110"></i>
                        </span>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">报名结束时间</label>

                <div class="col-xs-6">
                    <div class="input-group">
                        <input class="form-control datetime-picker" required type="text" name="endTime"
                               value="${cm:formatDate(crsPost.endTime, "yyyy-MM-dd HH:mm")}">
							<span class="input-group-addon">
                            <i class="fa fa-calendar bigger-110"></i>
                        </span>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">报名开关</label>
                <div class="col-xs-6">
                    <select  class="form-control" name="enrollStatus" data-width="223"
                             data-rel="select2" data-placeholder="请选择">
                        <option></option>
                        <c:forEach items="${CRS_POST_ENROLL_STATUS_MAP}" var="type">
                            <option value="${type.key}">${type.value}</option>
                        </c:forEach>
                    </select>
                    <script>
                        $("#modalForm select[name=enrollStatus]").val('${crsPost.enrollStatus}');
                    </script>
                </div>
            </div>
            <div class="modal-footer center">
                <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
                <input type="submit" class="btn btn-primary" value="确定"/>
            </div>
        </form>
    </div>
</div>
<script>
    register_datetime($('.datetime-picker'));

    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        //$("#modal").modal('hide');
                        $("#step-content li.active .loadPage").click()
                    }
                }
            });
        }
    });
    $("#modalForm :checkbox").bootstrapSwitch();
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>