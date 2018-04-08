<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${contentTpl.name}</h3>
</div>
<div class="modal-body">
    <div class="alert alert-info">
        ${contentTpl.content}
    </div>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="button" id="sendBtn" class="btn btn-primary" value="确定发送"/>
</div>

<script>
    $("#sendBtn").click(function(){
        $.post("${ctx}/cet/cetTrain_detail/msg_send", {trainId:'${param.trainId}', tplKey:'${param.tplKey}'},function(ret){

          if(ret.success){
              SysMsg.info("成功发送${fn:length(applicants)}条短信。".format(ret.successCount) );
              $("#modal").modal('hide');
          }
        })
    })
</script>