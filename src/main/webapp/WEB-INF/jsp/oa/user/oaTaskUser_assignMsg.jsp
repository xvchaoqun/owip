<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>短信通知指定负责人</h3>
</div>
<div class="modal-body">
    <p style="padding:30px;font-size:20px;text-indent: 2em; ">
        ${msg}${empty oaTaskUser.assignUserId?'您还没有分配指定负责人':''}
    </p>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" ${empty oaTaskUser.assignUserId?'disabled':''} type="button" class="btn btn-primary"><i class="fa fa-send"></i> 确定发送</button>
</div>
<style>
    .modal-note{
        font-size:16pt;font-weight:bolder;color:red;margin:30px 30px 0;text-indent: 2em
    }
</style>
<script>
    $('#modal').unbind('hidden.bs.modal')
    $("#submitBtn").click(function(){
            $.post("${ctx}/user/oa/oaTaskUser_assignMsg", {taskId:${param.taskId}}, function (ret) {
                if (ret.success) {
                    $("#modal").modal('hide');
                    SysMsg.success('<div class="modal-note">通知短信已发送，有可能会因为短信平台接口问题发送失败。为了保证工作按时完成，请确保负责人已接到通知。</div>');
                }
            });
        })
</script>