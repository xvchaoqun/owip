<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
  <li class="<c:if test="${status==1}">active</c:if>">
    <a href="javascript:;" class="loadPage" data-url="${ctx}/unit?status=1"><i class="fa fa-circle-o-notch fa-spin"></i> 正在运转单位</a>
  </li>
  <li class="<c:if test="${status==2}">active</c:if>">
    <a href="javascript:;" class="loadPage" data-url="${ctx}/unit?status=2"><i class="fa fa-history"></i> 历史单位</a>
  </li>
  <li class="<c:if test="${status==3}">active</c:if>">
    <a href="javascript:;" class="loadPage" data-url="${ctx}/unit?status=3"><i class="fa fa-list"></i> 学校单位列表</a>
  </li>
</ul>