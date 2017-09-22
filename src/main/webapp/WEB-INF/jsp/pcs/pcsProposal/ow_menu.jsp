<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
  <li class="<c:if test="${module==1}">active</c:if>">
    <a href="javascript:;" class="hashchange" data-querystr="module=1">
      <i class="fa fa-hourglass-start"></i> 提案审核</a>
  </li>
  <li class="<c:if test="${module==2}">active</c:if>">
    <a href="javascript:;" class="hashchange" data-querystr="module=2">
      <i class="fa fa-circle"></i> 立案处理</a>
  </li>
  <li class="<c:if test="${module==3}">active</c:if>">
    <a href="javascript:;" class="hashchange" data-querystr="module=3">
      <i class="fa fa-check-circle"></i> 处理完成</a>
  </li>
  <li class="<c:if test="${module==4}">active</c:if>">
    <a href="javascript:;" class="hashchange" data-querystr="module=4">
      <i class="fa fa-hand-paper-o"></i> 不予立案</a>
  </li>
</ul>