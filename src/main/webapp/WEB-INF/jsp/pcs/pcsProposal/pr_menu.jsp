<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
  <li class="<c:if test="${module==1}">active</c:if>">
    <a href="javascript:;" class="hashchange" data-querystr="module=1">
      <i class="fa fa-edit"></i> 撰写提案</a>
  </li>
  <li class="<c:if test="${module==2}">active</c:if>">
    <a href="javascript:;" class="hashchange" data-querystr="module=2">
      <i class="fa fa-handshake-o"></i> 附议提案列表</a>
  </li>
</ul>