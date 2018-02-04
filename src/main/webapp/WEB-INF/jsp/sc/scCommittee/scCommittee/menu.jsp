<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
  <li class="${cls==1?'active':''}">
  <a href="javascript:;" class="loadPage" data-url="${ctx}/sc/scCommittee?cls=1"><i class="fa fa-share-alt ${cls==1?'fa-1g':''}"></i> 党委常委会管理</a>
  </li>
  <li class="${cls==2?'active':''}">
  <a href="javascript:;" class="loadPage" data-url="${ctx}/sc/scCommittee?cls=2"><i class="fa fa-comments ${cls==2?'fa-1g':''}"></i> 党委常委会议题</a>
  </li>
  <li class="${cls==3?'active':''}">
  <a href="javascript:;" class="loadPage" data-url="${ctx}/sc/scCommittee?cls=3"><i class="fa fa-random ${cls==3?'fa-1g':''}"></i> 干部选拔任用表决</a>
  </li>
  <li class="${cls==4?'active':''}">
  <a href="javascript:;" class="loadPage" data-url="${ctx}/sc/scCommittee?cls=4"><i class="fa fa-crosshairs ${cls==4?'fa-1g':''}"></i> 其他事项表决</a>
  </li>
</ul>
