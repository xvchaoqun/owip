<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
  <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
  <h3>
    <c:choose>
      <c:when test="${param.type=='passportApplyPass'}">申请已批准，发送短信通知</c:when>
      <c:when test="${param.type=='passportApplyUnPass'}">不发短信，直接返回</c:when>
      <c:when test="${param.type=='passportApplyDraw'}">催交证件</c:when>
      <c:otherwise>短信通知</c:otherwise>
    </c:choose>
  </h3>
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
    $.post("${ctx}/${_path.startsWith("/m/")?'m/':''}shortMsg", {type:'${param.type}',id: ${param.id}}, function(ret){
      if(ret.success) {
        $("#modal").modal('hide');
        SysMsg.success('通知成功', '提示', function () {
          //page_reload();
        });
      }
    });
  })
</script>