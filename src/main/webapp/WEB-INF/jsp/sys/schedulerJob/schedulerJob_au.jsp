<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${op}定时任务</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/schedulerJob_au" autocomplete="off" disableautocomplete id="modalForm" method="post">

        <input type="hidden" name="id" value="${schedulerJob.id}">

        <div class="form-group">
            <label class="col-xs-4 control-label"><span class="star">*</span>名称</label>

            <div class="col-xs-6">
                <input required class="form-control" type="text" name="name" value="${schedulerJob.name}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label"><span class="star">*</span>执行任务类</label>

            <div class="col-xs-6">
                <input required class="form-control" type="text" name="clazz" value="${schedulerJob.clazz}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label"><span class="star">*</span>cron表达式</label>

            <div class="col-xs-6">
                <input required class="form-control" type="text" name="cron" value="${schedulerJob.cron}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label">任务描述</label>
            <div class="col-xs-6">
                <textarea class="form-control limited" name="summary">${schedulerJob.summary}</textarea>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label">是否随系统自动启动</label>
            <div class="col-xs-6">
                <input type="checkbox" class="big"
                       name="isStarted" ${(schedulerJob.isStarted)?"checked":""}/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label">是否保存执行日志</label>
            <div class="col-xs-6">
                <input type="checkbox" class="big"
                       name="needLog" ${(schedulerJob.needLog)?"checked":""}/>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="${op}"/>
</div>

<script>
    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        $("#modal").modal("hide")
                        $("#jqGrid").trigger("reloadGrid");
                    }
                }
            });
        }
    });
    $("#modalForm :checkbox").bootstrapSwitch();
    $('textarea.limited').inputlimiter();
</script>