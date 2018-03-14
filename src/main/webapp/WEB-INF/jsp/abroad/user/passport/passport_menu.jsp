<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
  <li  class="<c:if test="${type==1}">active</c:if>">
  <a href="javascript:;" class="hashchange" data-querystr="type=1"><i class="fa fa-credit-card"></i> 所持有的证件</a>
  </li>
  <li  class="<c:if test="${type==2}">active</c:if>">
  <a href="javascript:;" class="hashchange" data-querystr="type=2"><i class="fa fa-hand-paper-o"></i> 申请办理新的证件</a>
  </li>
</ul>