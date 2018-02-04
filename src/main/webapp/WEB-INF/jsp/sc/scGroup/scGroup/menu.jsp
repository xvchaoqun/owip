<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
  <li class="${cls==1?'active':''}">
  <a href="javascript:;" class="loadPage" data-url="${ctx}/sc/scGroup?cls=1"><i class="fa fa-sitemap ${cls==1?'fa-1g':''}"></i> 干部小组会构成</a>
  </li>
  <li class="${cls==2?'active':''}">
  <a href="javascript:;" class="loadPage" data-url="${ctx}/sc/scGroup?cls=2"><i class="fa fa-share-alt ${cls==2?'fa-1g':''}"></i> 干部小组会管理</a>
  </li>
  <li class="${cls==3?'active':''}">
  <a href="javascript:;" class="loadPage" data-url="${ctx}/sc/scGroup?cls=3"><i class="fa fa-comments ${cls==3?'fa-1g':''}"></i> 干部小组会议题</a>
  </li>
</ul>
