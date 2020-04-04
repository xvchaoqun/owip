<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
  <li class="<c:if test="${cls==1 && !isDeleted}">active</c:if>">
    <a href="javascript:;" class="loadPage" data-url="${ctx}/schedulerJob?cls=1"><i class="fa fa-list"></i> 任务列表</a>
  </li>
  <li class="<c:if test="${isDeleted}">active</c:if>">
    <a href="javascript:;" class="loadPage" data-url="${ctx}/schedulerJob?isDeleted=1"><i class="fa fa-times"></i> 已删除</a>
  </li>
  <li class="<c:if test="${cls==2}">active</c:if>">
    <a href="javascript:;" class="loadPage" data-url="${ctx}/schedulerJob?cls=2"><i class="fa fa-history"></i> 执行日志</a>
  </li>
</ul>