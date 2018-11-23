<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
  <li class="${cls==1?'active':''}">
  <a href="javascript:;" class="loadPage" data-url="${ctx}/sc/scBorder?cls=1"><i class="fa fa-file-pdf-o ${cls==1?'fa-1g':''}"></i> 出入境报备记录</a>
  </li>
  <li class="${cls==2?'active':''}">
  <a href="javascript:;" class="loadPage" data-url="${ctx}/sc/scBorder?cls=2"><i class="fa fa-share-alt ${cls==2?'fa-1g':''}"></i> 按干部查询</a>
  </li>
</ul>
