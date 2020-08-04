<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/cet/constants.jsp" %>
<div class="widget-box" style="width:500px">
    <div class="widget-header">
        <h4 class="widget-title">
            选课时间
        </h4>
    </div>
    <div class="widget-body">
        <div class="widget-main" id="qualification-content">
            <form class="form-horizontal" action="${ctx}/cet/cetTrain_detail/time" id="timeForm" method="post">
                <input type="hidden" name="trainId" value="${param.trainId}">
                <input type="hidden" name="projectId" value="${param.projectId}">

                <div class="form-group">
                    <label class="col-xs-3 control-label"><span class="star">*</span>选课开始时间</label>

                    <div class="col-xs-6">
                        <div class="input-group">
                            <input class="form-control datetime-picker" required type="text" name="startTime"
                                   value="${cm:formatDate(startTime, "yyyy-MM-dd HH:mm")}">
                            <span class="input-group-addon">
                            <i class="fa fa-calendar bigger-110"></i>
                        </span>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label"><span class="star">*</span>选课结束时间</label>

                    <div class="col-xs-6">
                        <div class="input-group">
                            <input class="form-control datetime-picker" required type="text" name="endTime"
                                   value="${cm:formatDate(endTime, "yyyy-MM-dd HH:mm")}">
                            <span class="input-group-addon">
                            <i class="fa fa-calendar bigger-110"></i>
                        </span>
                        </div>
                    </div>
                </div>
                <div class="modal-footer center" style="margin-top: 22px;">
                    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
                    <input type="submit" class="btn btn-primary" value="确定"/>
                </div>
            </form>
        </div>
    </div>
</div>
<script>
    $.register.datetime($('.datetime-picker'));

    $("#timeForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        SysMsg.success("设置成功")
                    }
                }
            });
        }
    });
    $("#timeForm :checkbox").bootstrapSwitch();
    $('#timeForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>