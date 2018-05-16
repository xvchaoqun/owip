<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${name}</h3>
</div>
<div class="modal-body">
    <div class="alert alert-info">
        ${content}
    </div>
</div>
<c:if test="${param.tplKey=='ct_cet_msg_1'}">
<div class="modal-footer">
    <div class="red text-left padding4">注：<ul>
        <li>
            只有已启动且已发布的培训班允许发送开班提醒短信；
        </li>
        <li>
            此功能开班前一天点击才有效；
        </li>
        <li>
            批量短信发送有一定的延迟，请勿重复点击发送。
        </li>
    </ul></div>
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <c:set var="canSend" value="${cetProject.status==CET_PROJECT_STATUS_START && cetProject.pubStatus == CET_PROJECT_PUB_STATUS_PUBLISHED}"/>
    <button id="sendBtn" ${canSend?'':'disabled'}
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 发送中，请不要关闭此窗口"
            class="btn btn-primary">确定发送</button>
</div>
</c:if>
<c:if test="${param.tplKey=='ct_cet_msg_2'}">
<div class="modal-footer">
    <div class="red text-left padding4">注：<ul>
        <li>
            只会给当天还未开始上课的课程发送短信；
        </li>
        <li>
            批量短信发送有一定的延迟，请勿重复点击发送。
        </li>
    </ul></div>
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="sendBtn" data-loading-text="<i class='fa fa-spinner fa-spin '></i> 发送中，请不要关闭此窗口"
            class="btn btn-primary">确定发送</button>
</div>
</c:if>
<script>
    $("#sendBtn").click(function(){
        var $btn = $("#sendBtn").button('loading');
        $.post("${ctx}/cet/cetTrain_detail/msg_send", {projectId:'${param.projectId}', trainId:'${param.trainId}', tplKey:'${param.tplKey}'},function(ret){
          if(ret.success){
              SysMsg.info("成功发送{0}条短信。".format(ret.successCount) );
              $("#modal").modal('hide');
          }
          $btn.button('reset');
        })
    })
</script>