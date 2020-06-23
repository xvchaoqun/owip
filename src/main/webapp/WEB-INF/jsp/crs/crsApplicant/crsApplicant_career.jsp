<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>         
  <div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>管理工作经历（${crsApplicant.user.realname}）</h3>
  </div>
  <div class="modal-body">
    <pre style="white-space: pre-wrap;font-size: 14pt;text-indent: 3em;">${crsApplicant.career}</pre>
  </div>
  <div class="modal-footer">
    <div class="pull-left" style="position: absolute; font-size: 16pt">
      总共<span id="strCount" style="font-size: 20pt;font-weight: bolder">0</span>个字。</div>
  <a href="javascript:;" data-dismiss="modal" class="btn btn-info">关闭</a></div>
<script>
  var str = $("pre").text().replace(/\s/g, "");
  $("#strCount").html(str.length)
</script>