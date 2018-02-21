<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="widget-box transparent">
  <div class="widget-header">
    <h4 class="widget-title lighter smaller">
      <a href="javascript:;" class="hideView btn btn-xs btn-success">
        <i class="ace-icon fa fa-backward"></i>
        返回</a>
    </h4>

    <div class="widget-toolbar no-border">
      <ul class="nav nav-tabs">
        <li class="active">
          <a href="javascript:;">干部任免审批表预览</a>
        </li>
      </ul>
    </div>
  </div>

  <div class="widget-body">
    <div class="widget-main">
      <div class="tab-content" style="padding-bottom: 0;padding-top: 0">
        <jsp:include page="/WEB-INF/jsp/cadre/cadreAdform/adForm.jsp"/>
      </div>
    </div>
  </div>
  <c:if test="${param.view!=1}">
  <div class="clearfix form-actions center">
    <button class="btn btn-success" type="button" id="saveBtn">
      <i class="ace-icon fa fa-save bigger-110"></i>
      归档保存
    </button>
  </div>
  </c:if>
</div>
<c:if test="${param.view!=1}">
<script>
  $("#saveBtn").click(function(){
    $.post("${ctx}/sc/scAdUse_save", {useId:'${param.useId}'}, function (ret) {
      if (ret.success) {
        $.hideView();
      }
    })
  });
</script>
  </c:if>