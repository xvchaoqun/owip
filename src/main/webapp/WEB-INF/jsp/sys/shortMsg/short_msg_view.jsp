<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
  <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
  <h3>短信通知</h3>
</div>
<div class="modal-body">
  <p style="padding:30px;font-size:20px;text-indent: 2em; ">
    ${shortMsgBean.content}
  </p>
</div>
<div class="modal-footer">
  <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
  <input type="submit" class="btn btn-primary" value="确定发送"/>
</div>

<script>
  $("#modal input[type=submit]").click(function(){
    $.post("${ctx}/shortMsg", {type:'${param.type}',id: ${param.id}}, function(ret){
      if(ret.success) {
        $("#modal").modal('hide');
        SysMsg.success('通知成功', '提示', function () {
          //page_reload();
        });
      }
    });
  })
</script>