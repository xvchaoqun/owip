<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
  <li class="${cls==1?'active':''}">
  <a href="javascript:;" class="loadPage" data-url="${ctx}/sc/scLetter?cls=1"><i class="fa fa-file-pdf-o ${cls==1?'fa-1g':''}"></i> 函询纪委文件</a>
  </li>
  <li class="${cls==2?'active':''}">
  <a href="javascript:;" class="loadPage" data-url="${ctx}/sc/scLetter?cls=2"><i class="fa fa-share-alt ${cls==2?'fa-1g':''}"></i> 纪委回复情况</a>
  </li>
  <li class="${cls==3?'active':''}">
  <a href="javascript:;" class="loadPage" data-url="${ctx}/sc/scLetter?cls=3"><i class="fa fa-list ${cls==3?'fa-1g':''}"></i> 纪委回复列表</a>
  </li>
  <li class="${cls==4?'active':''}">
  <a href="javascript:;" class="loadPage" data-url="${ctx}/sc/scLetter?cls=4"><i class="fa fa-gear ${cls==4?'fa-1g':''}"></i> 参数设置</a>
  </li>
</ul>
