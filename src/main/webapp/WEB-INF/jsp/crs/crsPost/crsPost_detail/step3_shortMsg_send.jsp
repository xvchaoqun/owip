<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${contentTpl.name}</h3>
</div>
<div class="modal-body">
    <div class="alert alert-info">
        ${msg}
    </div>
   <table class="table table-bordered table-striped">
       <thead>
       <tr>
           <td>姓名</td>
           <td>教工号</td>
           <td>手机</td>
       </tr>
       </thead>
       <tbody>
       <c:forEach items="${applicants}" var="applicant" varStatus="vs">
       <tr>
           <td>${applicant.user.realname}</td>
           <td>${applicant.user.code}</td>
           <td><t:mask src="${applicant.user.mobile}" type="mobile"/></td>
       </tr>
       </c:forEach>
       </tbody>
   </table>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="button" id="sendBtn" class="btn btn-primary" value="确定发送"/>
</div>

<script>
    $("#sendBtn").click(function(){
        $.post("${ctx}/crsPost_detail/step3_shortMsg_send",{postId:'${param.postId}', tplKey:'${param.tplKey}'},function(ret){
          if(ret.success){
              SysMsg.info("已发送${fn:length(applicants)}条短信，其中发送成功{0}条。".format(ret.successCount) );
              $("#modal").modal('hide');
          }
        })
    })
</script>