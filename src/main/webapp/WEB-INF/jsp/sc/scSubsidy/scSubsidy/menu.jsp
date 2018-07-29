<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
  <li class="<c:if test="${cls==1}">active</c:if>">
    <a href="javascript:;" class="loadPage" data-url="${ctx}/sc/scSubsidy?cls=1"><i
            class="fa fa-files-o"></i> 干部津贴变动文件</a>
  </li>
  <li class="<c:if test="${cls==2}">active</c:if>">
    <a href="javascript:;" class="loadPage" data-url="${ctx}/sc/scSubsidy?cls=2"><i
            class="fa fa-list"></i> 干部津贴变动详情</a>
  </li>
  <li class="<c:if test="${cls==3}">active</c:if>">
    <a href="javascript:;" class="loadPage" data-url="${ctx}/sc/scSubsidy?cls=3"><i
            class="fa fa-file"></i> 按任免文件查询</a>
  </li>
</ul>