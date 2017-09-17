<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${op}定时任务</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/schedulerJob_au" id="modalForm" method="post">

        <input type="hidden" name="id" value="${schedulerJob.id}">

        <div class="form-group">
            <label class="col-xs-3 control-label">名称</label>

            <div class="col-xs-6">
                <input required class="form-control" type="text" name="name" value="${schedulerJob.name}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">执行任务类</label>

            <div class="col-xs-6">
                <input required class="form-control" type="text" name="clazz" value="${schedulerJob.clazz}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">cron表达式</label>

            <div class="col-xs-6">
                <input required class="form-control" type="text" name="cron" value="${schedulerJob.cron}">
            </div>
        </div>

        <div class="form-group">
            <label class="col-xs-3 control-label">任务描述</label>
            <div class="col-xs-6">
                <textarea class="form-control limited" name="summary">${schedulerJob.summary}</textarea>
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
                        //SysMsg.success('提交成功。', '成功',function(){
                        _reload()
                        //});
                    }
                }
            });
        }
    });
    $('textarea.limited').inputlimiter();
</script>